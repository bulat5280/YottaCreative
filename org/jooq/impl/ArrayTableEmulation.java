package org.jooq.impl;

import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Select;
import org.jooq.SelectField;
import org.jooq.Table;

final class ArrayTableEmulation extends AbstractTable<Record> {
   private static final long serialVersionUID = 2392515064450536343L;
   private final Object[] array;
   private final Fields<Record> field;
   private final String alias;
   private final String fieldAlias;
   private transient Table<Record> table;

   ArrayTableEmulation(Object[] array) {
      this(array, "array_table", (String)null);
   }

   ArrayTableEmulation(Object[] array, String alias) {
      this(array, alias, (String)null);
   }

   ArrayTableEmulation(Object[] array, String alias, String fieldAlias) {
      super(alias);
      this.array = array;
      this.alias = alias;
      this.fieldAlias = fieldAlias == null ? "COLUMN_VALUE" : fieldAlias;
      this.field = new Fields(new Field[]{DSL.field(DSL.name(alias, this.fieldAlias), DSL.getDataType(array.getClass().getComponentType()))});
   }

   public final Class<? extends Record> getRecordType() {
      return RecordImpl.class;
   }

   public final Table<Record> as(String as) {
      return new ArrayTableEmulation(this.array, as);
   }

   public final Table<Record> as(String as, String... fieldAliases) {
      if (fieldAliases == null) {
         return new ArrayTableEmulation(this.array, as);
      } else if (fieldAliases.length == 1) {
         return new ArrayTableEmulation(this.array, as, fieldAliases[0]);
      } else {
         throw new IllegalArgumentException("Array table simulations can only have a single field alias");
      }
   }

   public final boolean declaresTables() {
      return true;
   }

   public final void accept(Context<?> ctx) {
      ctx.visit(this.table(ctx.configuration()));
   }

   final Fields<Record> fields0() {
      return this.field;
   }

   private final Table<Record> table(Configuration configuration) {
      if (this.table == null) {
         Select<Record> select = null;
         Object[] var3 = this.array;
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Object element = var3[var5];
            Field<?> val = DSL.val(element, this.field.fields[0].getDataType());
            Select<Record> subselect = DSL.using(configuration).select((SelectField)val.as("COLUMN_VALUE")).select();
            if (select == null) {
               select = subselect;
            } else {
               select = ((Select)select).unionAll(subselect);
            }
         }

         if (select == null) {
            select = DSL.using(configuration).select((SelectField)DSL.one().as("COLUMN_VALUE")).select().where(new Condition[]{DSL.falseCondition()});
         }

         this.table = ((Select)select).asTable(this.alias);
      }

      return this.table;
   }
}
