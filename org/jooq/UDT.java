package org.jooq;

import java.util.stream.Stream;

public interface UDT<R extends UDTRecord<R>> extends QueryPart {
   Row fieldsRow();

   Stream<Field<?>> fieldStream();

   <T> Field<T> field(Field<T> var1);

   Field<?> field(String var1);

   Field<?> field(Name var1);

   Field<?> field(int var1);

   Field<?>[] fields();

   Field<?>[] fields(Field<?>... var1);

   Field<?>[] fields(String... var1);

   Field<?>[] fields(Name... var1);

   Field<?>[] fields(int... var1);

   Catalog getCatalog();

   Schema getSchema();

   Package getPackage();

   String getName();

   Class<R> getRecordType();

   R newRecord();

   DataType<R> getDataType();

   boolean isSQLUsable();
}
