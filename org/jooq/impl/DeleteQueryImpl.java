package org.jooq.impl;

import java.util.Arrays;
import java.util.Collection;
import org.jooq.Clause;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.DeleteQuery;
import org.jooq.Operator;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.Table;

final class DeleteQueryImpl<R extends Record> extends AbstractDMLQuery<R> implements DeleteQuery<R> {
   private static final long serialVersionUID = -1943687511774150929L;
   private static final Clause[] CLAUSES;
   private final ConditionProviderImpl condition = new ConditionProviderImpl();

   DeleteQueryImpl(Configuration configuration, WithImpl with, Table<R> table) {
      super(configuration, with, table);
   }

   final Condition getWhere() {
      return this.condition.getWhere();
   }

   public final void addConditions(Collection<? extends Condition> conditions) {
      this.condition.addConditions(conditions);
   }

   public final void addConditions(Condition... conditions) {
      this.condition.addConditions(conditions);
   }

   public final void addConditions(Operator operator, Condition... conditions) {
      this.condition.addConditions(operator, conditions);
   }

   public final void addConditions(Operator operator, Collection<? extends Condition> conditions) {
      this.condition.addConditions(operator, conditions);
   }

   final void accept0(Context<?> ctx) {
      boolean declare = ctx.declareTables();
      ctx.start(Clause.DELETE_DELETE).keyword("delete").sql(' ');
      if (Arrays.asList(SQLDialect.MARIADB, SQLDialect.MYSQL).contains(ctx.configuration().dialect()) && (this.table instanceof TableAlias || this.table instanceof TableImpl && ((TableImpl)this.table).getAliasedTable() != null)) {
         ctx.visit(this.table).sql(' ');
      }

      ctx.keyword("from").sql(' ').declareTables(true).visit(this.table).declareTables(declare).end(Clause.DELETE_DELETE).start(Clause.DELETE_WHERE);
      if (!(this.getWhere() instanceof TrueCondition)) {
         ctx.formatSeparator().keyword("where").sql(' ').visit(this.getWhere());
      }

      ctx.end(Clause.DELETE_WHERE).start(Clause.DELETE_RETURNING);
      this.toSQLReturning(ctx);
      ctx.end(Clause.DELETE_RETURNING);
   }

   public final Clause[] clauses(Context<?> ctx) {
      return CLAUSES;
   }

   static {
      CLAUSES = new Clause[]{Clause.DELETE};
   }
}
