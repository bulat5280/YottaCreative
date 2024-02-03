package org.jooq.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import org.jooq.Attachable;
import org.jooq.AttachableInternal;
import org.jooq.Configuration;
import org.jooq.ExecuteContext;
import org.jooq.ExecuteListener;
import org.jooq.ExecuteType;
import org.jooq.Param;
import org.jooq.Query;
import org.jooq.RenderContext;
import org.jooq.conf.ParamType;
import org.jooq.conf.SettingsTools;
import org.jooq.exception.ControlFlowSignal;
import org.jooq.exception.DetachedException;
import org.jooq.tools.JooqLogger;

abstract class AbstractQuery extends AbstractQueryPart implements Query, AttachableInternal {
   private static final long serialVersionUID = -8046199737354507547L;
   private static final JooqLogger log = JooqLogger.getLogger(AbstractQuery.class);
   private Configuration configuration;
   private int timeout;
   private boolean keepStatement;
   transient PreparedStatement statement;
   transient AbstractQuery.Rendered rendered;

   AbstractQuery(Configuration configuration) {
      this.configuration = configuration;
   }

   public final void attach(Configuration c) {
      this.configuration = c;
   }

   public final void detach() {
      this.attach((Configuration)null);
   }

   public final Configuration configuration() {
      return this.configuration;
   }

   final void toSQLSemiColon(RenderContext ctx) {
   }

   public final List<Object> getBindValues() {
      return this.create().extractBindValues(this);
   }

   public final Map<String, Param<?>> getParams() {
      return this.create().extractParams(this);
   }

   public final Param<?> getParam(String name) {
      return this.create().extractParam(this, name);
   }

   public Query bind(String param, Object value) {
      try {
         int index = Integer.valueOf(param);
         return this.bind(index, value);
      } catch (NumberFormatException var8) {
         ParamCollector collector = new ParamCollector(this.configuration(), true);
         collector.visit(this);
         List<Param<?>> params = (List)collector.result.get(param);
         if (params != null && params.size() != 0) {
            Iterator var6 = params.iterator();

            while(var6.hasNext()) {
               Param<?> p = (Param)var6.next();
               p.setConverted(value);
               this.closeIfNecessary(p);
            }

            return this;
         } else {
            throw new IllegalArgumentException("No such parameter : " + param);
         }
      }
   }

   public Query bind(int index, Object value) {
      Param<?>[] params = (Param[])this.getParams().values().toArray(Tools.EMPTY_PARAM);
      if (index >= 1 && index <= params.length) {
         Param<?> param = params[index - 1];
         param.setConverted(value);
         this.closeIfNecessary(param);
         return this;
      } else {
         throw new IllegalArgumentException("Index out of range for Query parameters : " + index);
      }
   }

   private final void closeIfNecessary(Param<?> param) {
      if (this.keepStatement() && this.statement != null) {
         if (param.isInline()) {
            this.close();
         } else if (SettingsTools.getParamType(this.configuration().settings()) == ParamType.INLINED) {
            this.close();
         }
      }

   }

   public Query queryTimeout(int t) {
      this.timeout = t;
      return this;
   }

   public Query keepStatement(boolean k) {
      this.keepStatement = k;
      return this;
   }

   protected final boolean keepStatement() {
      return this.keepStatement;
   }

   public final void close() {
      if (this.statement != null) {
         try {
            this.statement.close();
            this.statement = null;
         } catch (SQLException var2) {
            throw Tools.translate(this.rendered.sql, var2);
         }
      }

   }

   public final void cancel() {
      if (this.statement != null) {
         try {
            this.statement.cancel();
         } catch (SQLException var2) {
            throw Tools.translate(this.rendered.sql, var2);
         }
      }

   }

   public final int execute() {
      if (this.isExecutable()) {
         Configuration c = this.configuration();
         DefaultExecuteContext ctx = new DefaultExecuteContext(c, this);
         ExecuteListener listener = new ExecuteListeners(ctx);
         boolean var4 = false;

         int var6;
         try {
            if (this.keepStatement() && this.statement != null) {
               ctx.sql(this.rendered.sql);
               ctx.statement(this.statement);
               ctx.connection(c.connectionProvider(), this.statement.getConnection());
            } else {
               listener.renderStart(ctx);
               this.rendered = this.getSQL0(ctx);
               ctx.sql(this.rendered.sql);
               listener.renderEnd(ctx);
               this.rendered.sql = ctx.sql();
               if (ctx.connection() == null) {
                  throw new DetachedException("Cannot execute query. No Connection configured");
               }

               listener.prepareStart(ctx);
               this.prepare(ctx);
               listener.prepareEnd(ctx);
               this.statement = ctx.statement();
            }

            int t = SettingsTools.getQueryTimeout(this.timeout, ctx.settings());
            if (t != 0) {
               ctx.statement().setQueryTimeout(t);
            }

            if (SettingsTools.executePreparedStatements(c.settings()) && !Boolean.TRUE.equals(ctx.data(Tools.DataKey.DATA_FORCE_STATIC_STATEMENT))) {
               listener.bindStart(ctx);
               if (this.rendered.bindValues != null) {
                  DSL.using(c).bindContext(ctx.statement()).visit(this.rendered.bindValues);
               }

               listener.bindEnd(ctx);
            }

            int result = this.execute(ctx, listener);
            var6 = result;
         } catch (ControlFlowSignal var12) {
            throw var12;
         } catch (RuntimeException var13) {
            ctx.exception(var13);
            listener.exception(ctx);
            throw ctx.exception();
         } catch (SQLException var14) {
            ctx.sqlException(var14);
            listener.exception(ctx);
            throw ctx.exception();
         } finally {
            if (!this.keepResultSet() || ctx.exception() != null) {
               Tools.safeClose(listener, ctx, this.keepStatement());
            }

            if (!this.keepStatement()) {
               this.statement = null;
               this.rendered = null;
            }

         }

         return var6;
      } else {
         if (log.isDebugEnabled()) {
            log.debug("Query is not executable", (Object)this);
         }

         return 0;
      }
   }

   public final CompletionStage<Integer> executeAsync() {
      return this.executeAsync(Tools.configuration((Attachable)this).executorProvider().provide());
   }

   public final CompletionStage<Integer> executeAsync(Executor executor) {
      return ExecutorProviderCompletionStage.of(CompletableFuture.supplyAsync(Tools.blocking(this::execute), executor), () -> {
         return executor;
      });
   }

   protected boolean keepResultSet() {
      return false;
   }

   protected void prepare(ExecuteContext ctx) throws SQLException {
      ctx.statement(ctx.connection().prepareStatement(ctx.sql()));
   }

   protected int execute(ExecuteContext ctx, ExecuteListener listener) throws SQLException {
      int result = 0;
      PreparedStatement stmt = ctx.statement();

      try {
         listener.executeStart(ctx);
         if (!stmt.execute()) {
            result = stmt.getUpdateCount();
            ctx.rows(result);
         }

         listener.executeEnd(ctx);
         return result;
      } catch (SQLException var6) {
         Tools.consumeExceptions(ctx.configuration(), stmt, var6);
         throw var6;
      }
   }

   public boolean isExecutable() {
      return true;
   }

   private final AbstractQuery.Rendered getSQL0(ExecuteContext ctx) {
      AbstractQuery.Rendered result;
      if (ctx.type() == ExecuteType.DDL) {
         ctx.data(Tools.DataKey.DATA_FORCE_STATIC_STATEMENT, true);
         result = new AbstractQuery.Rendered(this.getSQL(ParamType.INLINED));
      } else if (SettingsTools.executePreparedStatements(this.configuration().settings())) {
         try {
            DefaultRenderContext render = new DefaultRenderContext(this.configuration);
            render.data(Tools.DataKey.DATA_COUNT_BIND_VALUES, true);
            render.visit(this);
            result = new AbstractQuery.Rendered(render.render(), render.bindValues());
         } catch (DefaultRenderContext.ForceInlineSignal var4) {
            ctx.data(Tools.DataKey.DATA_FORCE_STATIC_STATEMENT, true);
            result = new AbstractQuery.Rendered(this.getSQL(ParamType.INLINED));
         }
      } else {
         result = new AbstractQuery.Rendered(this.getSQL(ParamType.INLINED));
      }

      return result;
   }

   public final String getSQL() {
      return this.getSQL(SettingsTools.getParamType(Tools.settings(this.configuration())));
   }

   public final String getSQL(ParamType paramType) {
      switch(paramType) {
      case INDEXED:
         return this.create().render(this);
      case INLINED:
         return this.create().renderInlined(this);
      case NAMED:
         return this.create().renderNamedParams(this);
      case NAMED_OR_INLINED:
         return this.create().renderNamedOrInlinedParams(this);
      default:
         throw new IllegalArgumentException("ParamType not supported: " + paramType);
      }
   }

   /** @deprecated */
   @Deprecated
   public final String getSQL(boolean inline) {
      return this.getSQL(inline ? ParamType.INLINED : ParamType.INDEXED);
   }

   static class Rendered {
      String sql;
      QueryPartList<Param<?>> bindValues;

      Rendered(String sql) {
         this(sql, (QueryPartList)null);
      }

      Rendered(String sql, QueryPartList<Param<?>> bindValues) {
         this.sql = sql;
         this.bindValues = bindValues;
      }
   }
}
