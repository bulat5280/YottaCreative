package org.jooq.util.postgres;

import org.jooq.Field;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.SQLDialect;
import org.jooq.Select;
import org.jooq.Support;
import org.jooq.Table;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;

public class PostgresDSL extends DSL {
   protected PostgresDSL() {
   }

   @Support({SQLDialect.POSTGRES})
   public static <T> Field<T[]> array(Select<? extends Record1<T>> select) {
      return DSL.field("array({0})", ((Field)select.getSelect().get(0)).getDataType().getArrayDataType(), select);
   }

   @Support({SQLDialect.POSTGRES})
   public static <T> Field<T[]> arrayAppend(T[] array, T value) {
      return arrayAppend0(val(array), val(value));
   }

   @Support({SQLDialect.POSTGRES})
   public static <T> Field<T[]> arrayAppend(T[] array, Field<T> value) {
      return arrayAppend0(val(array), value);
   }

   @Support({SQLDialect.POSTGRES})
   public static <T> Field<T[]> arrayAppend(Field<T[]> array, T value) {
      return arrayAppend0(array, val(value));
   }

   @Support({SQLDialect.POSTGRES})
   public static <T> Field<T[]> arrayAppend(Field<T[]> array, Field<T> value) {
      return arrayAppend0(array, value);
   }

   static <T> Field<T[]> arrayAppend0(Field<T[]> array, Field<T> value) {
      return field("{array_append}({0}, {1})", nullSafe(array).getDataType(), new QueryPart[]{nullSafe(array), nullSafe(value)});
   }

   @Support({SQLDialect.POSTGRES})
   public static <T> Field<T[]> arrayPrepend(T value, T[] array) {
      return arrayPrepend0(val(value), val(array));
   }

   @Support({SQLDialect.POSTGRES})
   public static <T> Field<T[]> arrayPrepend(Field<T> value, T[] array) {
      return arrayPrepend0(value, val(array));
   }

   @Support({SQLDialect.POSTGRES})
   public static <T> Field<T[]> arrayPrepend(T value, Field<T[]> array) {
      return arrayPrepend0(val(value), array);
   }

   @Support({SQLDialect.POSTGRES})
   public static <T> Field<T[]> arrayPrepend(Field<T> value, Field<T[]> array) {
      return arrayPrepend0(value, array);
   }

   static <T> Field<T[]> arrayPrepend0(Field<T> value, Field<T[]> array) {
      return field("{array_prepend}({0}, {1})", nullSafe(array).getDataType(), new QueryPart[]{nullSafe(value), nullSafe(array)});
   }

   @Support({SQLDialect.POSTGRES})
   public static <T> Field<T[]> arrayCat(T[] array1, T[] array2) {
      return arrayCat((Field)val(array1), (Field)val(array2));
   }

   @Support({SQLDialect.POSTGRES})
   public static <T> Field<T[]> arrayCat(T[] array1, Field<T[]> array2) {
      return arrayCat((Field)val(array1), (Field)array2);
   }

   @Support({SQLDialect.POSTGRES})
   public static <T> Field<T[]> arrayCat(Field<T[]> array1, T[] array2) {
      return arrayCat((Field)array1, (Field)val(array2));
   }

   @Support({SQLDialect.POSTGRES})
   public static <T> Field<T[]> arrayCat(Field<T[]> array1, Field<T[]> array2) {
      return field("{array_cat}({0}, {1})", nullSafe(array1).getDataType(), new QueryPart[]{nullSafe(array1), nullSafe(array2)});
   }

   @Support({SQLDialect.POSTGRES})
   public static <T> Field<T[]> arrayRemove(T[] array, T element) {
      return arrayRemove0(val(array), val(element));
   }

   @Support({SQLDialect.POSTGRES})
   public static <T> Field<T[]> arrayRemove(Field<T[]> array, T element) {
      return arrayRemove0(nullSafe(array), val(element));
   }

   @Support({SQLDialect.POSTGRES})
   public static <T> Field<T[]> arrayRemove(T[] array, Field<T> element) {
      return arrayRemove0(val(array), nullSafe(element));
   }

   @Support({SQLDialect.POSTGRES})
   public static <T> Field<T[]> arrayRemove(Field<T[]> array, Field<T> element) {
      return arrayRemove0(array, element);
   }

   static <T> Field<T[]> arrayRemove0(Field<T[]> array, Field<T> element) {
      return field("{array_remove}({0}, {1})", array.getDataType(), new QueryPart[]{array, element});
   }

   @Support({SQLDialect.POSTGRES})
   public static <T> Field<T[]> arrayReplace(T[] array, T search, T replace) {
      return arrayReplace0(val(array), val(search), val(replace));
   }

   @Support({SQLDialect.POSTGRES})
   public static <T> Field<T[]> arrayReplace(T[] array, Field<T> search, Field<T> replace) {
      return arrayReplace0(val(array), nullSafe(search), nullSafe(replace));
   }

   @Support({SQLDialect.POSTGRES})
   public static <T> Field<T[]> arrayReplace(Field<T[]> array, T search, T replace) {
      return arrayReplace0(nullSafe(array), val(search), val(replace));
   }

   @Support({SQLDialect.POSTGRES})
   public static <T> Field<T[]> arrayReplace(Field<T[]> array, Field<T> search, Field<T> replace) {
      return arrayReplace0(array, search, replace);
   }

   static <T> Field<T[]> arrayReplace0(Field<T[]> array, Field<T> search, Field<T> replace) {
      return field("{array_replace}({0}, {1}, {2})", array.getDataType(), new QueryPart[]{nullSafe(array), nullSafe(search), nullSafe(replace)});
   }

   @Support({SQLDialect.POSTGRES})
   public static <T> Field<T[]> arrayFill(T value, Integer[] dimensions) {
      return arrayFill((Field)val(value), (Field)val(dimensions));
   }

   @Support({SQLDialect.POSTGRES})
   public static <T> Field<T[]> arrayFill(Field<T> value, Integer[] dimensions) {
      return arrayFill((Field)nullSafe(value), (Field)val(dimensions));
   }

   @Support({SQLDialect.POSTGRES})
   public static <T> Field<T[]> arrayFill(T value, Field<Integer[]> dimensions) {
      return arrayFill((Field)val(value), (Field)nullSafe(dimensions));
   }

   @Support({SQLDialect.POSTGRES})
   public static <T> Field<T[]> arrayFill(Field<T> value, Field<Integer[]> dimensions) {
      return field("{array_fill}({0}, {1})", nullSafe(value).getDataType().getArrayDataType(), new QueryPart[]{nullSafe(value), nullSafe(dimensions)});
   }

   @Support({SQLDialect.POSTGRES})
   public static <T> Field<T[]> arrayFill(T value, Integer[] dimensions, Integer[] bounds) {
      return arrayFill((Field)val(value), (Field)val(dimensions), (Field)val(bounds));
   }

   @Support({SQLDialect.POSTGRES})
   public static <T> Field<T[]> arrayFill(Field<T> value, Integer[] dimensions, Integer[] bounds) {
      return arrayFill((Field)nullSafe(value), (Field)val(dimensions), (Field)val(bounds));
   }

   @Support({SQLDialect.POSTGRES})
   public static <T> Field<T[]> arrayFill(T value, Field<Integer[]> dimensions, Field<Integer[]> bounds) {
      return arrayFill((Field)val(value), (Field)nullSafe(dimensions), (Field)nullSafe(bounds));
   }

   @Support({SQLDialect.POSTGRES})
   public static <T> Field<T[]> arrayFill(Field<T> value, Field<Integer[]> dimensions, Field<Integer[]> bounds) {
      return field("{array_fill}({0}, {1})", nullSafe(value).getDataType().getArrayDataType(), new QueryPart[]{nullSafe(value), nullSafe(dimensions), nullSafe(bounds)});
   }

   @Support({SQLDialect.POSTGRES})
   public static Field<Integer> arrayLength(Object[] array) {
      return arrayLength((Field)val(array));
   }

   @Support({SQLDialect.POSTGRES})
   public static Field<Integer> arrayLength(Field<? extends Object[]> array) {
      return field("{array_length}({0}, 1)", SQLDataType.INTEGER, new QueryPart[]{array});
   }

   @Support({SQLDialect.POSTGRES})
   public static Field<String> arrayToString(Object[] array, String delimiter) {
      return arrayToString((Field)val(array), (Field)val(delimiter, String.class));
   }

   @Support({SQLDialect.POSTGRES})
   public static Field<String> arrayToString(Object[] array, Field<String> delimiter) {
      return arrayToString((Field)val(array), (Field)delimiter);
   }

   @Support({SQLDialect.POSTGRES})
   public static Field<String> arrayToString(Field<? extends Object[]> array, String delimiter) {
      return arrayToString((Field)array, (Field)val(delimiter, String.class));
   }

   @Support({SQLDialect.POSTGRES})
   public static Field<String> arrayToString(Field<? extends Object[]> array, Field<String> delimiter) {
      return field("{array_to_string}({0}, {1})", SQLDataType.VARCHAR, new QueryPart[]{nullSafe(array), nullSafe(delimiter)});
   }

   @Support({SQLDialect.POSTGRES})
   public static Field<String[]> stringToArray(String string, String delimiter) {
      return stringToArray((Field)val(string, String.class), (Field)val(delimiter, String.class));
   }

   @Support({SQLDialect.POSTGRES})
   public static Field<String[]> stringToArray(String string, Field<String> delimiter) {
      return stringToArray((Field)val(string, String.class), (Field)delimiter);
   }

   @Support({SQLDialect.POSTGRES})
   public static Field<String[]> stringToArray(Field<String> string, String delimiter) {
      return stringToArray((Field)string, (Field)val(delimiter, String.class));
   }

   @Support({SQLDialect.POSTGRES})
   public static Field<String[]> stringToArray(Field<String> string, Field<String> delimiter) {
      return field("{string_to_array}({0}, {1})", SQLDataType.VARCHAR.getArrayDataType(), new QueryPart[]{nullSafe(string), nullSafe(delimiter)});
   }

   @Support({SQLDialect.POSTGRES})
   public static Field<String[]> stringToArray(String string, String delimiter, String nullString) {
      return stringToArray((Field)val(string, String.class), (Field)val(delimiter, String.class), (Field)val(nullString, String.class));
   }

   @Support({SQLDialect.POSTGRES})
   public static Field<String[]> stringToArray(String string, Field<String> delimiter, Field<String> nullString) {
      return stringToArray((Field)val(string, String.class), (Field)delimiter, (Field)nullString);
   }

   @Support({SQLDialect.POSTGRES})
   public static Field<String[]> stringToArray(Field<String> string, String delimiter, String nullString) {
      return stringToArray((Field)string, (Field)val(delimiter, String.class), (Field)val(nullString, String.class));
   }

   @Support({SQLDialect.POSTGRES})
   public static Field<String[]> stringToArray(Field<String> string, Field<String> delimiter, Field<String> nullString) {
      return field("{string_to_array}({0}, {1}, {2})", SQLDataType.VARCHAR.getArrayDataType(), new QueryPart[]{nullSafe(string), nullSafe(delimiter), nullSafe(nullString)});
   }

   @Support({SQLDialect.POSTGRES})
   public static Table<Record> only(Table<?> table) {
      return table("{only} {0}", new QueryPart[]{table});
   }

   @Support({SQLDialect.POSTGRES})
   public static Field<Long> oid(Table<?> table) {
      return field("{0}.oid", Long.class, new QueryPart[]{table});
   }
}
