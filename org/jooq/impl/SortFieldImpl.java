package org.jooq.impl;

import org.jooq.Clause;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.SortField;
import org.jooq.SortOrder;

final class SortFieldImpl<T> extends AbstractQueryPart implements SortField<T> {
   private static final long serialVersionUID = 1223739398544155873L;
   private final Field<T> field;
   private final SortOrder order;
   private boolean nullsFirst;
   private boolean nullsLast;

   SortFieldImpl(Field<T> field, SortOrder order) {
      this.field = field;
      this.order = order;
   }

   public final String getName() {
      return this.field.getName();
   }

   public final SortOrder getOrder() {
      return this.order;
   }

   final Field<T> getField() {
      return this.field;
   }

   final boolean getNullsFirst() {
      return this.nullsFirst;
   }

   final boolean getNullsLast() {
      return this.nullsLast;
   }

   public final SortField<T> nullsFirst() {
      this.nullsFirst = true;
      this.nullsLast = false;
      return this;
   }

   public final SortField<T> nullsLast() {
      this.nullsFirst = false;
      this.nullsLast = true;
      return this;
   }

   public final void accept(Context<?> ctx) {
      if (!this.nullsFirst && !this.nullsLast) {
         ctx.visit(this.field).sql(' ').keyword(this.order.toSQL());
      } else {
         switch(ctx.configuration().dialect().family()) {
         case CUBRID:
         case MARIADB:
         case MYSQL:
         case SQLITE:
            Field<Integer> ifNull = this.nullsFirst ? DSL.zero() : DSL.one();
            Field<Integer> ifNotNull = this.nullsFirst ? DSL.one() : DSL.zero();
            ctx.visit(DSL.nvl2(this.field, (Field)ifNotNull, (Field)ifNull)).sql(", ").visit(this.field).sql(' ').keyword(this.order.toSQL());
            break;
         default:
            ctx.visit(this.field).sql(' ').keyword(this.order.toSQL());
            if (this.nullsFirst) {
               ctx.sql(' ').keyword("nulls first");
            } else {
               ctx.sql(' ').keyword("nulls last");
            }
         }
      }

   }

   public final Clause[] clauses(Context<?> ctx) {
      return null;
   }
}
