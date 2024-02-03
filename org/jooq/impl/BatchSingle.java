package org.jooq.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.jooq.BatchBindStep;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.DataType;
import org.jooq.ExecuteContext;
import org.jooq.ExecuteListener;
import org.jooq.Field;
import org.jooq.Param;
import org.jooq.Query;
import org.jooq.conf.ParamType;
import org.jooq.conf.SettingsTools;
import org.jooq.exception.ControlFlowSignal;
import org.jooq.tools.JooqLogger;

final class BatchSingle implements BatchBindStep {
   private static final long serialVersionUID = 3793967258181493207L;
   private static final JooqLogger log = JooqLogger.getLogger(BatchSingle.class);
   private final DSLContext create;
   private final Configuration configuration;
   private final Query query;
   private final Map<String, List<Integer>> nameToIndexMapping;
   private final List<Object[]> allBindValues;
   private final int expectedBindValues;

   public BatchSingle(Configuration configuration, Query query) {
      int i = 0;
      ParamCollector collector = new ParamCollector(configuration, false);
      collector.visit(query);
      this.create = DSL.using(configuration);
      this.configuration = configuration;
      this.query = query;
      this.allBindValues = new ArrayList();
      this.nameToIndexMapping = new LinkedHashMap();
      this.expectedBindValues = collector.resultList.size();

      Object list;
      for(Iterator var5 = collector.resultList.iterator(); var5.hasNext(); ((List)list).add(i++)) {
         Entry<String, Param<?>> entry = (Entry)var5.next();
         list = (List)this.nameToIndexMapping.get(entry.getKey());
         if (list == null) {
            list = new ArrayList();
            this.nameToIndexMapping.put(entry.getKey(), list);
         }
      }

   }

   public final BatchSingle bind(Object... bindValues) {
      this.allBindValues.add(bindValues);
      return this;
   }

   public final BatchSingle bind(Object[]... bindValues) {
      Object[][] var2 = bindValues;
      int var3 = bindValues.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Object[] v = var2[var4];
         this.bind(v);
      }

      return this;
   }

   public final BatchSingle bind(Map<String, Object> namedBindValues) {
      return this.bind(namedBindValues);
   }

   @SafeVarargs
   public final BatchSingle bind(Map<String, Object>... namedBindValues) {
      List<Object> defaultValues = this.query.getBindValues();
      Object[][] bindValues = new Object[namedBindValues.length][];

      label32:
      for(int row = 0; row < bindValues.length; ++row) {
         bindValues[row] = defaultValues.toArray();
         Iterator var5 = namedBindValues[row].entrySet().iterator();

         while(true) {
            Entry entry;
            List indexes;
            do {
               if (!var5.hasNext()) {
                  continue label32;
               }

               entry = (Entry)var5.next();
               indexes = (List)this.nameToIndexMapping.get(entry.getKey());
            } while(indexes == null);

            int index;
            for(Iterator var8 = indexes.iterator(); var8.hasNext(); bindValues[row][index] = entry.getValue()) {
               index = (Integer)var8.next();
            }
         }
      }

      this.bind(bindValues);
      return this;
   }

   public final int size() {
      return this.allBindValues.size();
   }

   public final int[] execute() {
      if (this.allBindValues.isEmpty()) {
         log.info("Single batch", (Object)"No bind variables have been provided with a single statement batch execution. This may be due to accidental API misuse");
         return BatchMultiple.execute(this.configuration, new Query[]{this.query});
      } else {
         this.checkBindValues();
         return SettingsTools.executeStaticStatements(this.configuration.settings()) ? this.executeStatic() : this.executePrepared();
      }
   }

   private final void checkBindValues() {
      if (this.expectedBindValues > 0) {
         for(int i = 0; i < this.allBindValues.size(); ++i) {
            if (((Object[])this.allBindValues.get(i)).length != this.expectedBindValues) {
               log.info("Bind value count", (Object)("Batch bind value set " + i + " has " + ((Object[])this.allBindValues.get(i)).length + " values when " + this.expectedBindValues + " values were expected"));
            }
         }
      }

   }

   private final int[] executePrepared() {
      ExecuteContext ctx = new DefaultExecuteContext(this.configuration, new Query[]{this.query});
      ExecuteListener listener = new ExecuteListeners(ctx);
      Connection connection = ctx.connection();
      ParamCollector collector = new ParamCollector(this.configuration, false);
      collector.visit(this.query);
      List<Param<?>> params = new ArrayList();
      Iterator var6 = collector.resultList.iterator();

      while(var6.hasNext()) {
         Entry<String, Param<?>> entry = (Entry)var6.next();
         params.add(entry.getValue());
      }

      DataType[] paramTypes = Tools.dataTypes((Field[])params.toArray(Tools.EMPTY_FIELD));

      try {
         listener.renderStart(ctx);
         ctx.sql(this.create.render(this.query));
         listener.renderEnd(ctx);
         listener.prepareStart(ctx);
         ctx.statement(connection.prepareStatement(ctx.sql()));
         listener.prepareEnd(ctx);
         Iterator var20 = this.allBindValues.iterator();

         while(var20.hasNext()) {
            Object[] bindValues = (Object[])var20.next();
            listener.bindStart(ctx);
            Tools.visitAll(new DefaultBindContext(this.configuration, ctx.statement()), (Collection)(paramTypes.length > 0 ? Tools.fields(bindValues, paramTypes) : Tools.fields(bindValues)));
            listener.bindEnd(ctx);
            ctx.statement().addBatch();
         }

         listener.executeStart(ctx);
         int[] result = ctx.statement().executeBatch();
         int[] batchRows = ctx.batchRows();

         for(int i = 0; i < batchRows.length && i < result.length; ++i) {
            batchRows[i] = result[i];
         }

         listener.executeEnd(ctx);
         int[] var23 = result;
         return var23;
      } catch (ControlFlowSignal var15) {
         throw var15;
      } catch (RuntimeException var16) {
         ctx.exception(var16);
         listener.exception(ctx);
         throw ctx.exception();
      } catch (SQLException var17) {
         ctx.sqlException(var17);
         listener.exception(ctx);
         throw ctx.exception();
      } finally {
         Tools.safeClose(listener, ctx);
      }
   }

   private final int[] executeStatic() {
      List<Query> queries = new ArrayList();
      Iterator var2 = this.allBindValues.iterator();

      while(var2.hasNext()) {
         Object[] bindValues = (Object[])var2.next();

         for(int i = 0; i < bindValues.length; ++i) {
            this.query.bind(i + 1, bindValues[i]);
         }

         queries.add(this.create.query(this.query.getSQL(ParamType.INLINED)));
      }

      return this.create.batch((Collection)queries).execute();
   }
}
