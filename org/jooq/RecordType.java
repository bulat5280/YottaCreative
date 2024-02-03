package org.jooq;

public interface RecordType<R extends Record> {
   int size();

   <T> Field<T> field(Field<T> var1);

   Field<?> field(String var1);

   <T> Field<T> field(String var1, Class<T> var2);

   <T> Field<T> field(String var1, DataType<T> var2);

   Field<?> field(Name var1);

   <T> Field<T> field(Name var1, Class<T> var2);

   <T> Field<T> field(Name var1, DataType<T> var2);

   Field<?> field(int var1);

   <T> Field<T> field(int var1, Class<T> var2);

   <T> Field<T> field(int var1, DataType<T> var2);

   Field<?>[] fields();

   Field<?>[] fields(Field<?>... var1);

   Field<?>[] fields(String... var1);

   Field<?>[] fields(Name... var1);

   Field<?>[] fields(int... var1);

   int indexOf(Field<?> var1);

   int indexOf(String var1);

   int indexOf(Name var1);

   Class<?>[] types();

   Class<?> type(int var1);

   Class<?> type(String var1);

   Class<?> type(Name var1);

   DataType<?>[] dataTypes();

   DataType<?> dataType(int var1);

   DataType<?> dataType(String var1);

   DataType<?> dataType(Name var1);
}
