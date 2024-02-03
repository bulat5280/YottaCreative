package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Table;
import org.jooq.UniqueKey;

final class CountTable extends Function<Integer> {
   private static final long serialVersionUID = 7292087943334025737L;
   private final Table<?> table;
   private final boolean distinct;

   CountTable(Table<?> table, boolean distinct) {
      super("count", distinct, SQLDataType.INTEGER, DSL.field("{0}", DSL.name(table.getName())));
      this.table = table;
      this.distinct = distinct;
   }

   public final void accept(Context<?> ctx) {
      switch(ctx.family()) {
      case POSTGRES:
         super.accept(ctx);
         break;
      default:
         UniqueKey<?> pk = this.table.getPrimaryKey();
         if (pk != null) {
            ctx.visit(new Function("count", this.distinct, SQLDataType.INTEGER, this.table.fields(pk.getFieldsArray())));
         } else {
            super.accept(ctx);
         }
      }

   }
}
