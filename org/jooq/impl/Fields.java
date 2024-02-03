package org.jooq.impl;

import java.sql.SQLWarning;
import java.util.Collection;
import org.jooq.Clause;
import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.RecordType;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.tools.JooqLogger;

final class Fields<R extends Record> extends AbstractQueryPart implements RecordType<R> {
   private static final long serialVersionUID = -6911012275707591576L;
   private static final JooqLogger log = JooqLogger.getLogger(Fields.class);
   Field<?>[] fields;

   Fields(Field<?>... fields) {
      this.fields = fields;
   }

   Fields(Collection<? extends Field<?>> fields) {
      this.fields = (Field[])fields.toArray(Tools.EMPTY_FIELD);
   }

   public final int size() {
      return this.fields.length;
   }

   public final <T> Field<T> field(Field<T> field) {
      if (field == null) {
         return null;
      } else {
         Field[] var2 = this.fields;
         int var3 = var2.length;

         int var4;
         Field f;
         for(var4 = 0; var4 < var3; ++var4) {
            f = var2[var4];
            if (f == field) {
               return f;
            }
         }

         var2 = this.fields;
         var3 = var2.length;

         for(var4 = 0; var4 < var3; ++var4) {
            f = var2[var4];
            if (f.equals(field)) {
               return f;
            }
         }

         Field<?> columnMatch = null;
         Field<?> columnMatch2 = null;
         String tableName = this.tableName(field);
         String fieldName = field.getName();
         Field[] var6 = this.fields;
         int var7 = var6.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            Field<?> f = var6[var8];
            String fName = f.getName();
            if (tableName != null) {
               String tName = this.tableName(f);
               if (tName != null && tableName.equals(tName) && fName.equals(fieldName)) {
                  return f;
               }
            }

            if (fName.equals(fieldName)) {
               if (columnMatch == null) {
                  columnMatch = f;
               } else {
                  columnMatch2 = f;
               }
            }
         }

         if (columnMatch2 != null && log.isInfoEnabled()) {
            log.info("Ambiguous match found for " + fieldName + ". Both " + columnMatch + " and " + columnMatch2 + " match.", (Throwable)(new SQLWarning()));
         }

         return columnMatch;
      }
   }

   private final String tableName(Field<?> field) {
      if (field instanceof TableField) {
         Table<?> table = ((TableField)field).getTable();
         if (table != null) {
            return table.getName();
         }
      }

      return null;
   }

   public final Field<?> field(String fieldName) {
      if (fieldName == null) {
         return null;
      } else {
         Field<?> columnMatch = null;
         Field[] var3 = this.fields;
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Field<?> f = var3[var5];
            if (f.getName().equals(fieldName)) {
               if (columnMatch == null) {
                  columnMatch = f;
               } else {
                  log.info("Ambiguous match found for " + fieldName + ". Both " + columnMatch + " and " + f + " match.", (Throwable)(new SQLWarning()));
               }
            }
         }

         return columnMatch;
      }
   }

   public final <T> Field<T> field(String fieldName, Class<T> type) {
      Field<?> result = this.field(fieldName);
      return result == null ? null : result.coerce(type);
   }

   public final <T> Field<T> field(String fieldName, DataType<T> dataType) {
      Field<?> result = this.field(fieldName);
      return result == null ? null : result.coerce(dataType);
   }

   public final Field<?> field(Name name) {
      return name == null ? null : this.field(DSL.field(name));
   }

   public final <T> Field<T> field(Name fieldName, Class<T> type) {
      Field<?> result = this.field(fieldName);
      return result == null ? null : result.coerce(type);
   }

   public final <T> Field<T> field(Name fieldName, DataType<T> dataType) {
      Field<?> result = this.field(fieldName);
      return result == null ? null : result.coerce(dataType);
   }

   public final Field<?> field(int index) {
      return index >= 0 && index < this.fields.length ? this.fields[index] : null;
   }

   public final <T> Field<T> field(int fieldIndex, Class<T> type) {
      Field<?> result = this.field(fieldIndex);
      return result == null ? null : result.coerce(type);
   }

   public final <T> Field<T> field(int fieldIndex, DataType<T> dataType) {
      Field<?> result = this.field(fieldIndex);
      return result == null ? null : result.coerce(dataType);
   }

   public final Field<?>[] fields() {
      return this.fields;
   }

   public final Field<?>[] fields(Field<?>... f) {
      Field<?>[] result = new Field[f.length];

      for(int i = 0; i < result.length; ++i) {
         result[i] = this.field(f[i]);
      }

      return result;
   }

   public final Field<?>[] fields(String... f) {
      Field<?>[] result = new Field[f.length];

      for(int i = 0; i < result.length; ++i) {
         result[i] = this.field(f[i]);
      }

      return result;
   }

   public final Field<?>[] fields(Name... f) {
      Field<?>[] result = new Field[f.length];

      for(int i = 0; i < result.length; ++i) {
         result[i] = this.field(f[i]);
      }

      return result;
   }

   public final Field<?>[] fields(int... f) {
      Field<?>[] result = new Field[f.length];

      for(int i = 0; i < result.length; ++i) {
         result[i] = this.field(f[i]);
      }

      return result;
   }

   public final int indexOf(Field<?> field) {
      Field<?> compareWith = this.field(field);
      if (compareWith != null) {
         int size = this.fields.length;

         int i;
         for(i = 0; i < size; ++i) {
            if (this.fields[i] == compareWith) {
               return i;
            }
         }

         for(i = 0; i < size; ++i) {
            if (this.fields[i].equals(compareWith)) {
               return i;
            }
         }
      }

      return -1;
   }

   public final int indexOf(String fieldName) {
      return this.indexOf(this.field(fieldName));
   }

   public final int indexOf(Name fieldName) {
      return this.indexOf(this.field(fieldName));
   }

   public final Class<?>[] types() {
      int size = this.fields.length;
      Class<?>[] result = new Class[size];

      for(int i = 0; i < size; ++i) {
         result[i] = this.field(i).getType();
      }

      return result;
   }

   public final Class<?> type(int fieldIndex) {
      return fieldIndex >= 0 && fieldIndex < this.size() ? this.field(fieldIndex).getType() : null;
   }

   public final Class<?> type(String fieldName) {
      return this.type(Tools.indexOrFail((RecordType)this, (String)fieldName));
   }

   public final Class<?> type(Name fieldName) {
      return this.type(Tools.indexOrFail((RecordType)this, (Name)fieldName));
   }

   public final DataType<?>[] dataTypes() {
      int size = this.fields.length;
      DataType<?>[] result = new DataType[size];

      for(int i = 0; i < size; ++i) {
         result[i] = this.field(i).getDataType();
      }

      return result;
   }

   public final DataType<?> dataType(int fieldIndex) {
      return fieldIndex >= 0 && fieldIndex < this.size() ? this.field(fieldIndex).getDataType() : null;
   }

   public final DataType<?> dataType(String fieldName) {
      return this.dataType(Tools.indexOrFail((RecordType)this, (String)fieldName));
   }

   public final DataType<?> dataType(Name fieldName) {
      return this.dataType(Tools.indexOrFail((RecordType)this, (Name)fieldName));
   }

   final int[] indexesOf(Field<?>... f) {
      int[] result = new int[f.length];

      for(int i = 0; i < f.length; ++i) {
         result[i] = Tools.indexOrFail((RecordType)this, (Field)f[i]);
      }

      return result;
   }

   final int[] indexesOf(String... fieldNames) {
      int[] result = new int[fieldNames.length];

      for(int i = 0; i < fieldNames.length; ++i) {
         result[i] = Tools.indexOrFail((RecordType)this, (String)fieldNames[i]);
      }

      return result;
   }

   final int[] indexesOf(Name... fieldNames) {
      int[] result = new int[fieldNames.length];

      for(int i = 0; i < fieldNames.length; ++i) {
         result[i] = Tools.indexOrFail((RecordType)this, (Name)fieldNames[i]);
      }

      return result;
   }

   public final void accept(Context<?> ctx) {
      ctx.visit(new QueryPartList(this.fields));
   }

   public final Clause[] clauses(Context<?> ctx) {
      return null;
   }

   final void add(Field<?> f) {
      Field<?>[] result = new Field[this.fields.length + 1];
      System.arraycopy(this.fields, 0, result, 0, this.fields.length);
      result[this.fields.length] = f;
      this.fields = result;
   }
}
