package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.exception.SQLDialectNotSupportedException;

final class FunctionTable<R extends Record> extends AbstractTable<R> {
   private static final long serialVersionUID = 2380426377794577041L;
   private final Field<?> function;

   FunctionTable(Field<?> function) {
      super("function_table");
      this.function = function;
   }

   public final Class<? extends R> getRecordType() {
      return RecordImpl.class;
   }

   public final Table<R> as(String as) {
      return new TableAlias(new FunctionTable(this.function), as);
   }

   public final Table<R> as(String as, String... fieldAliases) {
      return new TableAlias(new FunctionTable(this.function), as, fieldAliases);
   }

   public final void accept(Context<?> ctx) {
      switch(ctx.configuration().dialect()) {
      case HSQLDB:
         ctx.keyword("table(").visit(this.function).sql(')');
         break;
      case POSTGRES:
         ctx.visit(this.function);
         break;
      default:
         throw new SQLDialectNotSupportedException("FUNCTION TABLE is not supported for " + ctx.configuration().dialect());
      }

   }

   final Fields<R> fields0() {
      return new Fields(new Field[0]);
   }
}
