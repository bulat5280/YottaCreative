package org.jooq;

public interface Record5<T1, T2, T3, T4, T5> extends Record {
   Row5<T1, T2, T3, T4, T5> fieldsRow();

   Row5<T1, T2, T3, T4, T5> valuesRow();

   Field<T1> field1();

   Field<T2> field2();

   Field<T3> field3();

   Field<T4> field4();

   Field<T5> field5();

   T1 value1();

   T2 value2();

   T3 value3();

   T4 value4();

   T5 value5();

   Record5<T1, T2, T3, T4, T5> value1(T1 var1);

   Record5<T1, T2, T3, T4, T5> value2(T2 var1);

   Record5<T1, T2, T3, T4, T5> value3(T3 var1);

   Record5<T1, T2, T3, T4, T5> value4(T4 var1);

   Record5<T1, T2, T3, T4, T5> value5(T5 var1);

   Record5<T1, T2, T3, T4, T5> values(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5);
}
