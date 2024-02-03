package org.jooq.impl;

import java.sql.Connection;
import java.sql.SQLException;
import org.jooq.Batch;
import org.jooq.Configuration;
import org.jooq.ExecuteContext;
import org.jooq.ExecuteListener;
import org.jooq.Query;
import org.jooq.exception.ControlFlowSignal;

final class BatchMultiple implements Batch {
   private static final long serialVersionUID = -7337667281292354043L;
   private final Configuration configuration;
   private final Query[] queries;

   public BatchMultiple(Configuration configuration, Query... queries) {
      this.configuration = configuration;
      this.queries = queries;
   }

   public final int size() {
      return this.queries.length;
   }

   public final int[] execute() {
      return execute(this.configuration, this.queries);
   }

   static int[] execute(Configuration configuration, Query[] queries) {
      ExecuteContext ctx = new DefaultExecuteContext(configuration, queries);
      ExecuteListener listener = new ExecuteListeners(ctx);
      Connection connection = ctx.connection();

      try {
         ctx.statement(new SettingsEnabledPreparedStatement(connection));
         String[] batchSQL = ctx.batchSQL();

         for(int i = 0; i < queries.length; ++i) {
            listener.renderStart(ctx);
            batchSQL[i] = DSL.using(configuration).renderInlined(queries[i]);
            listener.renderEnd(ctx);
         }

         String[] var19 = batchSQL;
         int var7 = batchSQL.length;

         int i;
         for(i = 0; i < var7; ++i) {
            String sql = var19[i];
            ctx.sql(sql);
            listener.prepareStart(ctx);
            ctx.statement().addBatch(sql);
            listener.prepareEnd(ctx);
            ctx.sql((String)null);
         }

         listener.executeStart(ctx);
         int[] result = ctx.statement().executeBatch();
         int[] batchRows = ctx.batchRows();

         for(i = 0; i < batchRows.length && i < result.length; ++i) {
            batchRows[i] = result[i];
         }

         listener.executeEnd(ctx);
         int[] var22 = result;
         return var22;
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
}
