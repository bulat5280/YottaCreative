package org.jooq;

public interface Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> extends Record {
   Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> fieldsRow();

   Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> valuesRow();

   Field<T1> field1();

   Field<T2> field2();

   Field<T3> field3();

   Field<T4> field4();

   Field<T5> field5();

   Field<T6> field6();

   Field<T7> field7();

   Field<T8> field8();

   Field<T9> field9();

   T1 value1();

   T2 value2();

   T3 value3();

   T4 value4();

   T5 value5();

   T6 value6();

   T7 value7();

   T8 value8();

   T9 value9();

   Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> value1(T1 var1);

   Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> value2(T2 var1);

   Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> value3(T3 var1);

   Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> value4(T4 var1);

   Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> value5(T5 var1);

   Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> value6(T6 var1);

   Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> value7(T7 var1);

   Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> value8(T8 var1);

   Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> value9(T9 var1);

   Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> values(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8, T9 var9);
}
