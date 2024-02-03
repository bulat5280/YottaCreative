package org.jooq.tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import org.jooq.Configuration;
import org.jooq.ExecuteContext;
import org.jooq.ExecuteType;
import org.jooq.Field;
import org.jooq.Param;
import org.jooq.Parameter;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.Routine;
import org.jooq.VisitContext;
import org.jooq.VisitListenerProvider;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultExecuteListener;
import org.jooq.impl.DefaultVisitListener;
import org.jooq.impl.DefaultVisitListenerProvider;

public class LoggerListener extends DefaultExecuteListener {
   private static final long serialVersionUID = 7399239846062763212L;
   private static final JooqLogger log = JooqLogger.getLogger(LoggerListener.class);
   private static final int maxLength = 2000;

   public void renderEnd(ExecuteContext ctx) {
      if (log.isDebugEnabled()) {
         Configuration configuration = ctx.configuration();
         String newline = Boolean.TRUE.equals(configuration.settings().isRenderFormatted()) ? "\n" : "";
         if (!log.isTraceEnabled()) {
            configuration = this.abbreviateBindVariables(configuration);
         }

         String[] batchSQL = ctx.batchSQL();
         String inlined;
         if (ctx.query() != null) {
            log.debug("Executing query", (Object)(newline + ctx.sql()));
            inlined = DSL.using(configuration).renderInlined(ctx.query());
            if (!ctx.sql().equals(inlined)) {
               log.debug("-> with bind values", (Object)(newline + inlined));
            }
         } else if (ctx.routine() != null) {
            log.debug("Calling routine", (Object)(newline + ctx.sql()));
            inlined = DSL.using(configuration).renderInlined(ctx.routine());
            if (!ctx.sql().equals(inlined)) {
               log.debug("-> with bind values", (Object)(newline + inlined));
            }
         } else if (!StringUtils.isBlank(ctx.sql())) {
            if (ctx.type() == ExecuteType.BATCH) {
               log.debug("Executing batch query", (Object)(newline + ctx.sql()));
            } else {
               log.debug("Executing query", (Object)(newline + ctx.sql()));
            }
         } else if (batchSQL.length > 0 && batchSQL[batchSQL.length - 1] != null) {
            String[] var9 = batchSQL;
            int var6 = batchSQL.length;

            for(int var7 = 0; var7 < var6; ++var7) {
               String sql = var9[var7];
               log.debug("Executing batch query", (Object)(newline + sql));
            }
         }
      }

   }

   public void recordEnd(ExecuteContext ctx) {
      if (log.isTraceEnabled() && ctx.record() != null) {
         this.logMultiline("Record fetched", ctx.record().toString(), Level.FINER);
      }

   }

   public void resultEnd(ExecuteContext ctx) {
      if (ctx.result() != null) {
         if (log.isTraceEnabled()) {
            this.logMultiline("Fetched result", ctx.result().format(500), Level.FINE);
         } else if (log.isDebugEnabled()) {
            this.logMultiline("Fetched result", ctx.result().format(5), Level.FINE);
         }
      }

   }

   public void executeEnd(ExecuteContext ctx) {
      if (ctx.rows() >= 0 && log.isDebugEnabled()) {
         log.debug("Affected row(s)", (Object)ctx.rows());
      }

   }

   public void outEnd(ExecuteContext ctx) {
      if (ctx.routine() != null && log.isDebugEnabled()) {
         this.logMultiline("Fetched OUT parameters", "" + this.record(ctx.configuration(), ctx.routine()), Level.FINE);
      }

   }

   public void exception(ExecuteContext ctx) {
      if (log.isDebugEnabled()) {
         log.debug("Exception", (Throwable)ctx.exception());
      }

   }

   private Record record(Configuration configuration, Routine<?> routine) {
      Record result = null;
      List<Field<?>> fields = new ArrayList();
      Parameter<?> returnParam = routine.getReturnParameter();
      if (returnParam != null) {
         fields.add(DSL.field(DSL.name(returnParam.getName()), returnParam.getDataType()));
      }

      Iterator var6 = routine.getOutParameters().iterator();

      while(var6.hasNext()) {
         Parameter<?> param = (Parameter)var6.next();
         fields.add(DSL.field(DSL.name(param.getName()), param.getDataType()));
      }

      result = DSL.using(configuration).newRecord((Field[])fields.toArray(new Field[0]));
      int i = 0;
      if (returnParam != null) {
         result.setValue((Field)fields.get(i++), routine.getValue(returnParam));
      }

      Iterator var10 = routine.getOutParameters().iterator();

      while(var10.hasNext()) {
         Parameter<?> param = (Parameter)var10.next();
         result.setValue((Field)fields.get(i++), routine.getValue(param));
      }

      result.changed(false);
      return result;
   }

   private void logMultiline(String comment, String message, Level level) {
      String[] var4 = message.split("\n");
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         String line = var4[var6];
         if (level == Level.FINE) {
            log.debug(comment, (Object)line);
         } else {
            log.trace(comment, (Object)line);
         }

         comment = "";
      }

   }

   private final Configuration abbreviateBindVariables(Configuration configuration) {
      VisitListenerProvider[] oldProviders = configuration.visitListenerProviders();
      VisitListenerProvider[] newProviders = new VisitListenerProvider[oldProviders.length + 1];
      System.arraycopy(oldProviders, 0, newProviders, 0, oldProviders.length);
      newProviders[newProviders.length - 1] = new DefaultVisitListenerProvider(new LoggerListener.BindValueAbbreviator());
      return configuration.derive(newProviders);
   }

   private static class BindValueAbbreviator extends DefaultVisitListener {
      private boolean anyAbbreviations;

      private BindValueAbbreviator() {
         this.anyAbbreviations = false;
      }

      public void visitStart(VisitContext context) {
         if (context.renderContext() != null) {
            QueryPart part = context.queryPart();
            if (part instanceof Param) {
               Param<?> param = (Param)part;
               Object value = param.getValue();
               if (value instanceof String && ((String)value).length() > 2000) {
                  this.anyAbbreviations = true;
                  context.queryPart(DSL.val(StringUtils.abbreviate((String)value, 2000)));
               } else if (value instanceof byte[] && ((byte[])((byte[])value)).length > 2000) {
                  this.anyAbbreviations = true;
                  context.queryPart(DSL.val(Arrays.copyOf((byte[])((byte[])value), 2000)));
               }
            }
         }

      }

      public void visitEnd(VisitContext context) {
         if (this.anyAbbreviations && context.queryPartsLength() == 1) {
            context.renderContext().sql(" -- Bind values may have been abbreviated for DEBUG logging. Use TRACE logging for very large bind variables.");
         }

      }

      // $FF: synthetic method
      BindValueAbbreviator(Object x0) {
         this();
      }
   }
}
