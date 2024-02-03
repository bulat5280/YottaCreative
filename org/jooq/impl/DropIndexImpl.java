package org.jooq.impl;

import java.util.Arrays;
import org.jooq.Clause;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.DropIndexFinalStep;
import org.jooq.DropIndexOnStep;
import org.jooq.Name;
import org.jooq.SQLDialect;
import org.jooq.Table;

final class DropIndexImpl extends AbstractQuery implements DropIndexOnStep {
   private static final long serialVersionUID = 8904572826501186329L;
   private static final Clause[] CLAUSES;
   private final Name index;
   private final boolean ifExists;
   private Table<?> on;

   DropIndexImpl(Configuration configuration, Name index) {
      this(configuration, index, false);
   }

   DropIndexImpl(Configuration configuration, Name index, boolean ifExists) {
      super(configuration);
      this.index = index;
      this.ifExists = ifExists;
   }

   public final DropIndexFinalStep on(Table<?> table) {
      this.on = table;
      return this;
   }

   public final DropIndexFinalStep on(String tableName) {
      return this.on(DSL.name(tableName));
   }

   public final DropIndexFinalStep on(Name tableName) {
      return this.on(DSL.table(tableName));
   }

   private final boolean supportsIfExists(Context<?> ctx) {
      return !Arrays.asList(SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD).contains(ctx.family());
   }

   public final void accept(Context<?> ctx) {
      if (this.ifExists && !this.supportsIfExists(ctx)) {
         Tools.executeImmediateBegin(ctx, DDLStatementType.DROP_INDEX);
         this.accept0(ctx);
         Tools.executeImmediateEnd(ctx, DDLStatementType.DROP_INDEX);
      } else {
         this.accept0(ctx);
      }

   }

   private void accept0(Context<?> ctx) {
      ctx.keyword("drop index").sql(' ');
      if (this.ifExists && this.supportsIfExists(ctx)) {
         ctx.keyword("if exists").sql(' ');
      }

      ctx.visit(this.index);
      if (this.on != null) {
         ctx.sql(' ').keyword("on").sql(' ').visit(this.on);
      }

   }

   public final Clause[] clauses(Context<?> ctx) {
      return CLAUSES;
   }

   static {
      CLAUSES = new Clause[]{Clause.DROP_INDEX};
   }
}
