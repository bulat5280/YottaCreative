package org.jooq;

public interface Record8<T1, T2, T3, T4, T5, T6, T7, T8> extends Record {
   Row8<T1, T2, T3, T4, T5, T6, T7, T8> fieldsRow();

   Row8<T1, T2, T3, T4, T5, T6, T7, T8> valuesRow();

   Field<T1> field1();

   Field<T2> field2();

   Field<T3> field3();

   Field<T4> field4();

   Field<T5> field5();

   Field<T6> field6();

   Field<T7> field7();

   Field<T8> field8();

   T1 value1();

   T2 value2();

   T3 value3();

   T4 value4();

   T5 value5();

   T6 value6();

   T7 value7();

   T8 value8();

   Record8<T1, T2, T3, T4, T5, T6, T7, T8> value1(T1 var1);

   Record8<T1, T2, T3, T4, T5, T6, T7, T8> value2(T2 var1);

   Record8<T1, T2, T3, T4, T5, T6, T7, T8> value3(T3 var1);

   Record8<T1, T2, T3, T4, T5, T6, T7, T8> value4(T4 var1);

   Record8<T1, T2, T3, T4, T5, T6, T7, T8> value5(T5 var1);

   Record8<T1, T2, T3, T4, T5, T6, T7, T8> value6(T6 var1);

   Record8<T1, T2, T3, T4, T5, T6, T7, T8> value7(T7 var1);

   Record8<T1, T2, T3, T4, T5, T6, T7, T8> value8(T8 var1);

   Record8<T1, T2, T3, T4, T5, T6, T7, T8> values(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8);
}
