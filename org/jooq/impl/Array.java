package org.jooq.impl;

import java.util.Collection;
import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.SQLDialect;

final class Array<T> extends AbstractField<T[]> {
   private static final long serialVersionUID = -6629785423729163857L;
   private final Fields<Record> fields;

   Array(Collection<? extends Field<T>> fields) {
      super("array", type(fields));
      this.fields = new Fields(fields);
   }

   private static <T> DataType<T[]> type(Collection<? extends Field<T>> fields) {
      return fields != null && !fields.isEmpty() ? ((Field)fields.iterator().next()).getDataType().getArrayDataType() : SQLDataType.OTHER.getArrayDataType();
   }

   public final void accept(Context<?> ctx) {
      switch(ctx.family()) {
      case H2:
         ctx.sql('(').visit(this.fields).sql(')');
         break;
      case HSQLDB:
      case POSTGRES:
      default:
         ctx.keyword("array").sql('[').visit(this.fields).sql(']');
         if (this.fields.fields.length == 0 && ctx.family() == SQLDialect.POSTGRES) {
            ctx.sql("::").keyword("int[]");
         }
      }

   }
}
