package org.jooq;

public interface Record3<T1, T2, T3> extends Record {
   Row3<T1, T2, T3> fieldsRow();

   Row3<T1, T2, T3> valuesRow();

   Field<T1> field1();

   Field<T2> field2();

   Field<T3> field3();

   T1 value1();

   T2 value2();

   T3 value3();

   Record3<T1, T2, T3> value1(T1 var1);

   Record3<T1, T2, T3> value2(T2 var1);

   Record3<T1, T2, T3> value3(T3 var1);

   Record3<T1, T2, T3> values(T1 var1, T2 var2, T3 var3);
}
