package org.jooq.impl;

import java.sql.PreparedStatement;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import org.jooq.BindContext;
import org.jooq.Clause;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.QueryPart;
import org.jooq.QueryPartInternal;
import org.jooq.RenderContext;
import org.jooq.SQLDialect;
import org.jooq.VisitContext;
import org.jooq.VisitListener;
import org.jooq.VisitListenerProvider;
import org.jooq.conf.ParamType;
import org.jooq.conf.Settings;
import org.jooq.conf.SettingsTools;
import org.jooq.conf.StatementType;

abstract class AbstractContext<C extends Context<C>> extends AbstractScope implements Context<C> {
   final PreparedStatement stmt;
   boolean declareFields;
   boolean declareTables;
   boolean declareAliases;
   boolean declareWindows;
   boolean declareCTE;
   boolean subquery;
   int stringLiteral;
   String stringLiteralEscapedApos = "'";
   int index;
   final VisitListener[] visitListeners;
   private final Deque<Clause> visitClauses;
   private final AbstractContext<C>.DefaultVisitContext visitContext;
   private final Deque<QueryPart> visitParts;
   final ParamType forcedParamType;
   ParamType paramType;
   boolean qualifySchema;
   boolean qualifyCatalog;
   RenderContext.CastMode castMode;

   AbstractContext(Configuration configuration, PreparedStatement stmt) {
      super(configuration);
      this.paramType = ParamType.INDEXED;
      this.qualifySchema = true;
      this.qualifyCatalog = true;
      this.castMode = RenderContext.CastMode.DEFAULT;
      this.stmt = stmt;
      VisitListenerProvider[] providers = configuration.visitListenerProviders();
      boolean userInternalVisitListener = false;
      this.visitListeners = new VisitListener[providers.length + (userInternalVisitListener ? 1 : 0)];

      for(int i = 0; i < providers.length; ++i) {
         this.visitListeners[i] = providers[i].provide();
      }

      if (this.visitListeners.length > 0) {
         this.visitContext = new AbstractContext.DefaultVisitContext();
         this.visitParts = new ArrayDeque();
         this.visitClauses = new ArrayDeque();
      } else {
         this.visitContext = null;
         this.visitParts = null;
         this.visitClauses = null;
      }

      this.forcedParamType = SettingsTools.getStatementType(this.settings()) == StatementType.STATIC_STATEMENT ? ParamType.INLINED : null;
   }

   public final C visit(QueryPart part) {
      if (part != null) {
         Clause[] clauses = this.visitListeners.length > 0 ? this.clause(part) : null;
         if (clauses != null) {
            for(int i = 0; i < clauses.length; ++i) {
               this.start(clauses[i]);
            }
         }

         QueryPart replacement = this.start(part);
         if (part == replacement) {
            this.visit0(part);
         } else {
            this.visit0(replacement);
         }

         this.end(replacement);
         if (clauses != null) {
            for(int i = clauses.length - 1; i >= 0; --i) {
               this.end(clauses[i]);
            }
         }
      }

      return this;
   }

   private final Clause[] clause(QueryPart part) {
      return part instanceof QueryPartInternal && this.data(Tools.DataKey.DATA_OMIT_CLAUSE_EVENT_EMISSION) == null ? ((QueryPartInternal)part).clauses(this) : null;
   }

   public final C start(Clause clause) {
      if (clause != null && this.visitClauses != null) {
         this.visitClauses.addLast(clause);
         VisitListener[] var2 = this.visitListeners;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            VisitListener listener = var2[var4];
            listener.clauseStart(this.visitContext);
         }
      }

      return this;
   }

   public final C end(Clause clause) {
      if (clause != null && this.visitClauses != null) {
         VisitListener[] var2 = this.visitListeners;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            VisitListener listener = var2[var4];
            listener.clauseEnd(this.visitContext);
         }

         if (this.visitClauses.removeLast() != clause) {
            throw new IllegalStateException("Mismatch between visited clauses!");
         }
      }

      return this;
   }

   private final QueryPart start(QueryPart part) {
      if (this.visitParts == null) {
         return part;
      } else {
         this.visitParts.addLast(part);
         VisitListener[] var2 = this.visitListeners;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            VisitListener listener = var2[var4];
            listener.visitStart(this.visitContext);
         }

         return (QueryPart)this.visitParts.peekLast();
      }
   }

   private final void end(QueryPart part) {
      if (this.visitParts != null) {
         VisitListener[] var2 = this.visitListeners;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            VisitListener listener = var2[var4];
            listener.visitEnd(this.visitContext);
         }

         if (this.visitParts.removeLast() != part) {
            throw new RuntimeException("Mismatch between visited query parts");
         }
      }

   }

   private final C visit0(QueryPart part) {
      if (part != null) {
         QueryPartInternal internal = (QueryPartInternal)part;
         boolean aliases;
         if (this.declareFields() && !internal.declaresFields()) {
            aliases = this.declareAliases();
            this.declareFields(false);
            this.visit0(internal);
            this.declareFields(true);
            this.declareAliases(aliases);
         } else if (this.declareTables() && !internal.declaresTables()) {
            aliases = this.declareAliases();
            this.declareTables(false);
            this.visit0(internal);
            this.declareTables(true);
            this.declareAliases(aliases);
         } else if (this.declareWindows() && !internal.declaresWindows()) {
            this.declareWindows(false);
            this.visit0(internal);
            this.declareWindows(true);
         } else if (this.declareCTE() && !internal.declaresCTE()) {
            this.declareCTE(false);
            this.visit0(internal);
            this.declareCTE(true);
         } else if (this.castMode() != RenderContext.CastMode.DEFAULT && !internal.generatesCast()) {
            RenderContext.CastMode previous = this.castMode();
            this.castMode(RenderContext.CastMode.DEFAULT);
            this.visit0(internal);
            this.castMode(previous);
         } else {
            this.visit0(internal);
         }
      }

      return this;
   }

   protected abstract void visit0(QueryPartInternal var1);

   public final boolean declareFields() {
      return this.declareFields;
   }

   public final C declareFields(boolean d) {
      this.declareFields = d;
      this.declareAliases(d);
      return this;
   }

   public final boolean declareTables() {
      return this.declareTables;
   }

   public final C declareTables(boolean d) {
      this.declareTables = d;
      this.declareAliases(d);
      return this;
   }

   public final boolean declareAliases() {
      return this.declareAliases;
   }

   public final C declareAliases(boolean d) {
      this.declareAliases = d;
      return this;
   }

   public final boolean declareWindows() {
      return this.declareWindows;
   }

   public final C declareWindows(boolean d) {
      this.declareWindows = d;
      return this;
   }

   public final boolean declareCTE() {
      return this.declareCTE;
   }

   public final C declareCTE(boolean d) {
      this.declareCTE = d;
      return this;
   }

   public final boolean subquery() {
      return this.subquery;
   }

   public final C subquery(boolean s) {
      this.subquery = s;
      return this;
   }

   public final boolean stringLiteral() {
      return this.stringLiteral > 0;
   }

   public final C stringLiteral(boolean s) {
      if (s) {
         ++this.stringLiteral;
         this.stringLiteralEscapedApos = this.stringLiteralEscapedApos + this.stringLiteralEscapedApos;
      } else {
         --this.stringLiteral;
         this.stringLiteralEscapedApos = this.stringLiteralEscapedApos.substring(0, this.stringLiteralEscapedApos.length() / 2);
      }

      return this;
   }

   public final int nextIndex() {
      return ++this.index;
   }

   public final int peekIndex() {
      return this.index + 1;
   }

   public final ParamType paramType() {
      return this.forcedParamType != null ? this.forcedParamType : this.paramType;
   }

   public final C paramType(ParamType p) {
      this.paramType = p == null ? ParamType.INDEXED : p;
      return this;
   }

   public final boolean qualify() {
      return this.qualifySchema();
   }

   public final C qualify(boolean q) {
      return this.qualifySchema(q);
   }

   public final boolean qualifySchema() {
      return this.qualifySchema;
   }

   public final C qualifySchema(boolean q) {
      this.qualifySchema = q;
      return this;
   }

   public final boolean qualifyCatalog() {
      return this.qualifyCatalog;
   }

   public final C qualifyCatalog(boolean q) {
      this.qualifyCatalog = q;
      return this;
   }

   public final RenderContext.CastMode castMode() {
      return this.castMode;
   }

   public final C castMode(RenderContext.CastMode mode) {
      this.castMode = mode;
      return this;
   }

   /** @deprecated */
   @Deprecated
   public final Boolean cast() {
      switch(this.castMode) {
      case ALWAYS:
         return true;
      case NEVER:
         return false;
      default:
         return null;
      }
   }

   /** @deprecated */
   @Deprecated
   public final C castModeSome(SQLDialect... dialects) {
      return this;
   }

   public final PreparedStatement statement() {
      return this.stmt;
   }

   void toString(StringBuilder sb) {
      sb.append("bind index   [");
      sb.append(this.index);
      sb.append("]");
      sb.append("\ndeclaring    [");
      if (this.declareFields) {
         sb.append("fields");
         if (this.declareAliases) {
            sb.append(" and aliases");
         }
      } else if (this.declareTables) {
         sb.append("tables");
         if (this.declareAliases) {
            sb.append(" and aliases");
         }
      } else if (this.declareWindows) {
         sb.append("windows");
      } else if (this.declareCTE) {
         sb.append("cte");
      } else {
         sb.append("-");
      }

      sb.append("]\nsubquery     [");
      sb.append(this.subquery);
      sb.append("]");
   }

   private class DefaultVisitContext implements VisitContext {
      private DefaultVisitContext() {
      }

      public final Map<Object, Object> data() {
         return AbstractContext.this.data();
      }

      public final Object data(Object key) {
         return AbstractContext.this.data(key);
      }

      public final Object data(Object key, Object value) {
         return AbstractContext.this.data(key, value);
      }

      public final Configuration configuration() {
         return AbstractContext.this.configuration();
      }

      public final Settings settings() {
         return Tools.settings(this.configuration());
      }

      public final SQLDialect dialect() {
         return Tools.configuration(this.configuration()).dialect();
      }

      public final SQLDialect family() {
         return this.dialect().family();
      }

      public final Clause clause() {
         return (Clause)AbstractContext.this.visitClauses.peekLast();
      }

      public final Clause[] clauses() {
         return (Clause[])AbstractContext.this.visitClauses.toArray(Tools.EMPTY_CLAUSE);
      }

      public final int clausesLength() {
         return AbstractContext.this.visitClauses.size();
      }

      public final QueryPart queryPart() {
         return (QueryPart)AbstractContext.this.visitParts.peekLast();
      }

      public final void queryPart(QueryPart part) {
         AbstractContext.this.visitParts.pollLast();
         AbstractContext.this.visitParts.addLast(part);
      }

      public final QueryPart[] queryParts() {
         return (QueryPart[])AbstractContext.this.visitParts.toArray(Tools.EMPTY_QUERYPART);
      }

      public final int queryPartsLength() {
         return AbstractContext.this.visitParts.size();
      }

      public final Context<?> context() {
         return AbstractContext.this;
      }

      public final RenderContext renderContext() {
         return this.context() instanceof RenderContext ? (RenderContext)this.context() : null;
      }

      public final BindContext bindContext() {
         return this.context() instanceof BindContext ? (BindContext)this.context() : null;
      }

      // $FF: synthetic method
      DefaultVisitContext(Object x1) {
         this();
      }
   }
}
