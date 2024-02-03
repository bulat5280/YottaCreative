package org.jooq.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;

final class RowsFrom extends AbstractTable<Record> {
   private static final long serialVersionUID = 693765524746506586L;
   private final TableList tables;

   RowsFrom(Table<?>... tables) {
      super("rows from");
      this.tables = new TableList(Arrays.asList(tables));
   }

   public final Class<? extends Record> getRecordType() {
      return RecordImpl.class;
   }

   public final Table<Record> as(String alias) {
      return new TableAlias(this, alias);
   }

   public final Table<Record> as(String alias, String... fieldAliases) {
      return new TableAlias(this, alias, fieldAliases);
   }

   final Fields<Record> fields0() {
      List<Field<?>> fields = new ArrayList();
      Iterator var2 = this.tables.iterator();

      while(var2.hasNext()) {
         Table<?> table = (Table)var2.next();
         Field[] var4 = table.fields();
         int var5 = var4.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            Field<?> field = var4[var6];
            fields.add(DSL.field(DSL.name(field.getName()), field.getDataType()));
         }
      }

      return new Fields(fields);
   }

   public final void accept(Context<?> ctx) {
      boolean declareTables = ctx.declareTables();
      ctx.keyword("rows from").sql(" (").declareTables(true).visit(this.tables).declareTables(declareTables).sql(')');
   }
}
