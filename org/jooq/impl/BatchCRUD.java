package org.jooq.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.jooq.AttachableInternal;
import org.jooq.Batch;
import org.jooq.BatchBindStep;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.ExecuteContext;
import org.jooq.ExecuteListenerProvider;
import org.jooq.Query;
import org.jooq.TableRecord;
import org.jooq.UpdatableRecord;
import org.jooq.conf.SettingsTools;
import org.jooq.exception.ControlFlowSignal;
import org.jooq.exception.DataAccessException;

final class BatchCRUD implements Batch {
   private static final long serialVersionUID = -2935544935267715011L;
   private final DSLContext create;
   private final Configuration configuration;
   private final TableRecord<?>[] records;
   private final BatchCRUD.Action action;

   BatchCRUD(Configuration configuration, BatchCRUD.Action action, TableRecord<?>[] records) {
      this.create = DSL.using(configuration);
      this.configuration = configuration;
      this.action = action;
      this.records = records;
   }

   public final int size() {
      return this.records.length;
   }

   public final int[] execute() throws DataAccessException {
      return SettingsTools.executeStaticStatements(this.configuration.settings()) ? this.executeStatic() : this.executePrepared();
   }

   private final int[] executePrepared() {
      Map<String, List<Query>> queries = new LinkedHashMap();
      BatchCRUD.QueryCollector collector = new BatchCRUD.QueryCollector();
      Configuration local = this.configuration.derive((ExecuteListenerProvider[])Tools.combine((Object[])this.configuration.executeListenerProviders(), (Object)(new DefaultExecuteListenerProvider(collector))));
      local.data(Tools.DataKey.DATA_OMIT_RETURNING_CLAUSE, true);
      local.settings().setExecuteLogging(false);

      for(int i = 0; i < this.records.length; ++i) {
         Configuration previous = ((AttachableInternal)this.records[i]).configuration();

         try {
            this.records[i].attach(local);
            this.executeAction(i);
         } catch (BatchCRUD.QueryCollectorSignal var15) {
            Query query = var15.getQuery();
            String sql = var15.getSQL();
            if (query.isExecutable()) {
               List<Query> list = (List)queries.get(sql);
               if (list == null) {
                  list = new ArrayList();
                  queries.put(sql, list);
               }

               ((List)list).add(query);
            }
         } finally {
            this.records[i].attach(previous);
         }
      }

      List<Integer> result = new ArrayList();
      Iterator var18 = queries.entrySet().iterator();

      while(var18.hasNext()) {
         Entry<String, List<Query>> entry = (Entry)var18.next();
         BatchBindStep batch = this.create.batch((Query)((List)entry.getValue()).get(0));
         Iterator var22 = ((List)entry.getValue()).iterator();

         while(var22.hasNext()) {
            Query query = (Query)var22.next();
            batch.bind(query.getBindValues().toArray());
         }

         int[] array = batch.execute();
         int[] var25 = array;
         int var10 = array.length;

         for(int var11 = 0; var11 < var10; ++var11) {
            int i = var25[var11];
            result.add(i);
         }
      }

      int[] array = new int[result.size()];

      for(int i = 0; i < result.size(); ++i) {
         array[i] = (Integer)result.get(i);
      }

      this.updateChangedFlag();
      return array;
   }

   private final int[] executeStatic() {
      List<Query> queries = new ArrayList();
      BatchCRUD.QueryCollector collector = new BatchCRUD.QueryCollector();
      Configuration local = this.configuration.derive((ExecuteListenerProvider[])Tools.combine((Object[])this.configuration.executeListenerProviders(), (Object)(new DefaultExecuteListenerProvider(collector))));

      for(int i = 0; i < this.records.length; ++i) {
         Configuration previous = ((AttachableInternal)this.records[i]).configuration();

         try {
            this.records[i].attach(local);
            this.executeAction(i);
         } catch (BatchCRUD.QueryCollectorSignal var11) {
            Query query = var11.getQuery();
            if (query.isExecutable()) {
               queries.add(query);
            }
         } finally {
            this.records[i].attach(previous);
         }
      }

      int[] result = this.create.batch((Collection)queries).execute();
      this.updateChangedFlag();
      return result;
   }

   private void executeAction(int i) {
      switch(this.action) {
      case STORE:
         ((UpdatableRecord)this.records[i]).store();
         break;
      case INSERT:
         this.records[i].insert();
         break;
      case UPDATE:
         ((UpdatableRecord)this.records[i]).update();
         break;
      case DELETE:
         ((UpdatableRecord)this.records[i]).delete();
      }

   }

   private final void updateChangedFlag() {
      TableRecord[] var1 = this.records;
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         TableRecord<?> record = var1[var3];
         record.changed(this.action == BatchCRUD.Action.DELETE);
         if (record instanceof AbstractRecord) {
            ((AbstractRecord)record).fetched = this.action != BatchCRUD.Action.DELETE;
         }
      }

   }

   private static class QueryCollectorSignal extends ControlFlowSignal {
      private static final long serialVersionUID = -9047250761846931903L;
      private final String sql;
      private final Query query;

      QueryCollectorSignal(String sql, Query query) {
         this.sql = sql;
         this.query = query;
      }

      String getSQL() {
         return this.sql;
      }

      Query getQuery() {
         return this.query;
      }
   }

   private static class QueryCollector extends DefaultExecuteListener {
      private static final long serialVersionUID = 7399239846062763212L;

      private QueryCollector() {
      }

      public void renderEnd(ExecuteContext ctx) {
         throw new BatchCRUD.QueryCollectorSignal(ctx.sql(), ctx.query());
      }

      // $FF: synthetic method
      QueryCollector(Object x0) {
         this();
      }
   }

   static enum Action {
      STORE,
      INSERT,
      UPDATE,
      DELETE;
   }
}
