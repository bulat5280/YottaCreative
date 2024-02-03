package org.jooq.impl;

import java.util.Arrays;
import org.jooq.Clause;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.DropViewFinalStep;
import org.jooq.SQLDialect;
import org.jooq.Table;

final class DropViewImpl extends AbstractQuery implements DropViewFinalStep {
   private static final long serialVersionUID = 8904572826501186329L;
   private static final Clause[] CLAUSES;
   private final Table<?> table;
   private final boolean ifExists;

   DropViewImpl(Configuration configuration, Table<?> table) {
      this(configuration, table, false);
   }

   DropViewImpl(Configuration configuration, Table<?> table, boolean ifExists) {
      super(configuration);
      this.table = table;
      this.ifExists = ifExists;
   }

   private final boolean supportsIfExists(Context<?> ctx) {
      return !Arrays.asList(SQLDialect.DERBY, SQLDialect.FIREBIRD).contains(ctx.family());
   }

   public final void accept(Context<?> ctx) {
      if (this.ifExists && !this.supportsIfExists(ctx)) {
         Tools.executeImmediateBegin(ctx, DDLStatementType.DROP_VIEW);
         this.accept0(ctx);
         Tools.executeImmediateEnd(ctx, DDLStatementType.DROP_VIEW);
      } else {
         this.accept0(ctx);
      }

   }

   private void accept0(Context<?> ctx) {
      ctx.start(Clause.DROP_VIEW_TABLE).keyword("drop view").sql(' ');
      if (this.ifExists && this.supportsIfExists(ctx)) {
         ctx.keyword("if exists").sql(' ');
      }

      ctx.visit(this.table);
      ctx.end(Clause.DROP_VIEW_TABLE);
   }

   public final Clause[] clauses(Context<?> ctx) {
      return CLAUSES;
   }

   static {
      CLAUSES = new Clause[]{Clause.DROP_VIEW};
   }
}
