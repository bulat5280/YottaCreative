package org.jooq;

public interface Record6<T1, T2, T3, T4, T5, T6> extends Record {
   Row6<T1, T2, T3, T4, T5, T6> fieldsRow();

   Row6<T1, T2, T3, T4, T5, T6> valuesRow();

   Field<T1> field1();

   Field<T2> field2();

   Field<T3> field3();

   Field<T4> field4();

   Field<T5> field5();

   Field<T6> field6();

   T1 value1();

   T2 value2();

   T3 value3();

   T4 value4();

   T5 value5();

   T6 value6();

   Record6<T1, T2, T3, T4, T5, T6> value1(T1 var1);

   Record6<T1, T2, T3, T4, T5, T6> value2(T2 var1);

   Record6<T1, T2, T3, T4, T5, T6> value3(T3 var1);

   Record6<T1, T2, T3, T4, T5, T6> value4(T4 var1);

   Record6<T1, T2, T3, T4, T5, T6> value5(T5 var1);

   Record6<T1, T2, T3, T4, T5, T6> value6(T6 var1);

   Record6<T1, T2, T3, T4, T5, T6> values(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6);
}
