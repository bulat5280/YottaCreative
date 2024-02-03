package org.jooq.impl;

import java.util.Arrays;
import org.jooq.Clause;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.DropTableStep;
import org.jooq.SQLDialect;
import org.jooq.Table;

final class DropTableImpl extends AbstractQuery implements DropTableStep {
   private static final long serialVersionUID = 8904572826501186329L;
   private static final Clause[] CLAUSES;
   private final Table<?> table;
   private final boolean ifExists;
   private boolean cascade;

   DropTableImpl(Configuration configuration, Table<?> table) {
      this(configuration, table, false);
   }

   DropTableImpl(Configuration configuration, Table<?> table, boolean ifExists) {
      super(configuration);
      this.table = table;
      this.ifExists = ifExists;
   }

   public final DropTableImpl cascade() {
      this.cascade = true;
      return this;
   }

   public final DropTableImpl restrict() {
      this.cascade = false;
      return this;
   }

   private final boolean supportsIfExists(Context<?> ctx) {
      return !Arrays.asList(SQLDialect.DERBY, SQLDialect.FIREBIRD).contains(ctx.family());
   }

   public final void accept(Context<?> ctx) {
      if (this.ifExists && !this.supportsIfExists(ctx)) {
         Tools.executeImmediateBegin(ctx, DDLStatementType.DROP_TABLE);
         this.accept0(ctx);
         Tools.executeImmediateEnd(ctx, DDLStatementType.DROP_TABLE);
      } else {
         this.accept0(ctx);
      }

   }

   private void accept0(Context<?> ctx) {
      ctx.start(Clause.DROP_TABLE_TABLE).keyword("drop table").sql(' ');
      if (this.ifExists && this.supportsIfExists(ctx)) {
         ctx.keyword("if exists").sql(' ');
      }

      ctx.visit(this.table);
      if (this.cascade) {
         ctx.sql(' ').keyword("cascade");
      }

      ctx.end(Clause.DROP_TABLE_TABLE);
   }

   public final Clause[] clauses(Context<?> ctx) {
      return CLAUSES;
   }

   static {
      CLAUSES = new Clause[]{Clause.DROP_TABLE};
   }
}
