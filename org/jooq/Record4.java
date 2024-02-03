package org.jooq;

public interface Record4<T1, T2, T3, T4> extends Record {
   Row4<T1, T2, T3, T4> fieldsRow();

   Row4<T1, T2, T3, T4> valuesRow();

   Field<T1> field1();

   Field<T2> field2();

   Field<T3> field3();

   Field<T4> field4();

   T1 value1();

   T2 value2();

   T3 value3();

   T4 value4();

   Record4<T1, T2, T3, T4> value1(T1 var1);

   Record4<T1, T2, T3, T4> value2(T2 var1);

   Record4<T1, T2, T3, T4> value3(T3 var1);

   Record4<T1, T2, T3, T4> value4(T4 var1);

   Record4<T1, T2, T3, T4> values(T1 var1, T2 var2, T3 var3, T4 var4);
}
