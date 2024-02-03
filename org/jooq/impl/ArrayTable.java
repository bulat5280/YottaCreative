package org.jooq.impl;

import java.util.ArrayList;
import java.util.List;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Param;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.UDTRecord;
import org.jooq.exception.DataTypeException;
import org.jooq.util.h2.H2DataType;

final class ArrayTable extends AbstractTable<Record> {
   private static final long serialVersionUID = 2380426377794577041L;
   private final Field<?> array;
   private final Fields<Record> field;
   private final String alias;
   private final String[] fieldAliases;

   ArrayTable(Field<?> array) {
      this(array, "array_table");
   }

   ArrayTable(Field<?> array, String alias) {
      this(array, alias, new String[]{"COLUMN_VALUE"});
   }

   ArrayTable(Field<?> array, String alias, String[] fieldAliases) {
      super(alias);
      Class arrayType;
      if (array.getDataType().getType().isArray()) {
         arrayType = array.getDataType().getType().getComponentType();
      } else {
         arrayType = Object.class;
      }

      this.array = array;
      this.alias = alias;
      this.fieldAliases = fieldAliases;
      this.field = init(arrayType, alias, fieldAliases);
   }

   private static final Fields<Record> init(Class<?> arrayType, String alias, String[] fieldAliases) {
      List<Field<?>> result = new ArrayList();
      if (UDTRecord.class.isAssignableFrom(arrayType)) {
         try {
            UDTRecord<?> record = (UDTRecord)arrayType.newInstance();
            Field[] var5 = record.fields();
            int var6 = var5.length;

            for(int var7 = 0; var7 < var6; ++var7) {
               Field<?> f = var5[var7];
               result.add(DSL.field(DSL.name(alias, f.getName()), f.getDataType()));
            }
         } catch (Exception var9) {
            throw new DataTypeException("Bad UDT Type : " + arrayType, var9);
         }
      } else {
         result.add(DSL.field(DSL.name(alias, "COLUMN_VALUE"), DSL.getDataType(arrayType)));
      }

      return new Fields(result);
   }

   public final Class<? extends Record> getRecordType() {
      return RecordImpl.class;
   }

   public final Table<Record> as(String as) {
      return new ArrayTable(this.array, as);
   }

   public final Table<Record> as(String as, String... fieldAliases) {
      return new ArrayTable(this.array, as, fieldAliases);
   }

   public final boolean declaresTables() {
      return true;
   }

   public final void accept(Context<?> ctx) {
      ctx.visit(this.table(ctx.configuration()));
   }

   private final Table<Record> table(Configuration configuration) {
      switch(configuration.family()) {
      case H2:
         return (new ArrayTable.H2ArrayTable()).as(this.alias);
      case HSQLDB:
      case POSTGRES:
         return (new ArrayTable.PostgresHSQLDBTable()).as(this.alias, this.fieldAliases);
      default:
         return (Table)(this.array.getDataType().getType().isArray() && this.array instanceof Param ? this.emulate() : DSL.table("{0}", this.array).as(this.alias));
      }
   }

   private final ArrayTableEmulation emulate() {
      return new ArrayTableEmulation((Object[])((Param)this.array).getValue(), this.alias);
   }

   final Fields<Record> fields0() {
      return this.field;
   }

   private abstract class DialectArrayTable extends AbstractTable<Record> {
      private static final long serialVersionUID = 2662639259338694177L;

      DialectArrayTable() {
         super(ArrayTable.this.alias);
      }

      public final Class<? extends Record> getRecordType() {
         return RecordImpl.class;
      }

      public final Table<Record> as(String as) {
         return new TableAlias(this, as);
      }

      public final Table<Record> as(String as, String... fieldAliases) {
         return new TableAlias(this, as, fieldAliases);
      }

      final Fields<Record> fields0() {
         return ArrayTable.this.fields0();
      }
   }

   private class H2ArrayTable extends ArrayTable.DialectArrayTable {
      private static final long serialVersionUID = 8679404596822098711L;

      private H2ArrayTable() {
         super();
      }

      public final void accept(Context<?> ctx) {
         ctx.keyword("table").sql('(').visit(DSL.name(ArrayTable.this.fieldAliases != null && ArrayTable.this.fieldAliases.length != 0 ? ArrayTable.this.fieldAliases[0] : "COLUMN_VALUE")).sql(' ');
         if (ArrayTable.this.array.getDataType().getType() == Object[].class) {
            ctx.keyword(H2DataType.VARCHAR.getTypeName());
         } else {
            ctx.keyword(ArrayTable.this.array.getDataType().getTypeName());
         }

         ctx.sql(" = ").visit(ArrayTable.this.array).sql(')');
      }

      // $FF: synthetic method
      H2ArrayTable(Object x1) {
         this();
      }
   }

   private class PostgresHSQLDBTable extends ArrayTable.DialectArrayTable {
      private static final long serialVersionUID = 6989279597964488457L;

      private PostgresHSQLDBTable() {
         super();
      }

      public final void accept(Context<?> ctx) {
         ctx.keyword("unnest").sql('(').visit(ArrayTable.this.array).sql(")");
      }

      // $FF: synthetic method
      PostgresHSQLDBTable(Object x1) {
         this();
      }
   }
}
