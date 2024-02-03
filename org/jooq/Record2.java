package org.jooq;

public interface Record2<T1, T2> extends Record {
   Row2<T1, T2> fieldsRow();

   Row2<T1, T2> valuesRow();

   Field<T1> field1();

   Field<T2> field2();

   T1 value1();

   T2 value2();

   Record2<T1, T2> value1(T1 var1);

   Record2<T1, T2> value2(T2 var1);

   Record2<T1, T2> values(T1 var1, T2 var2);
}
