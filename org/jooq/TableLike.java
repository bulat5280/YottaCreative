package org.jooq;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

public interface TableLike<R extends Record> extends QueryPart {
   Row fieldsRow();

   Stream<Field<?>> fieldStream();

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

   @Support
   Table<R> asTable();

   @Support
   Table<R> asTable(String var1);

   @Support
   Table<R> asTable(String var1, String... var2);

   @Support
   Table<R> asTable(String var1, Function<? super Field<?>, ? extends String> var2);

   @Support
   Table<R> asTable(String var1, BiFunction<? super Field<?>, ? super Integer, ? extends String> var2);
}
