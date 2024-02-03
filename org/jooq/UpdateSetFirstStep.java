package org.jooq;

public interface UpdateSetFirstStep<R extends Record> extends UpdateSetStep<R> {
   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   <T1> UpdateFromStep<R> set(Row1<T1> var1, Row1<T1> var2);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   <T1, T2> UpdateFromStep<R> set(Row2<T1, T2> var1, Row2<T1, T2> var2);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   <T1, T2, T3> UpdateFromStep<R> set(Row3<T1, T2, T3> var1, Row3<T1, T2, T3> var2);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   <T1, T2, T3, T4> UpdateFromStep<R> set(Row4<T1, T2, T3, T4> var1, Row4<T1, T2, T3, T4> var2);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   <T1, T2, T3, T4, T5> UpdateFromStep<R> set(Row5<T1, T2, T3, T4, T5> var1, Row5<T1, T2, T3, T4, T5> var2);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   <T1, T2, T3, T4, T5, T6> UpdateFromStep<R> set(Row6<T1, T2, T3, T4, T5, T6> var1, Row6<T1, T2, T3, T4, T5, T6> var2);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   <T1, T2, T3, T4, T5, T6, T7> UpdateFromStep<R> set(Row7<T1, T2, T3, T4, T5, T6, T7> var1, Row7<T1, T2, T3, T4, T5, T6, T7> var2);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   <T1, T2, T3, T4, T5, T6, T7, T8> UpdateFromStep<R> set(Row8<T1, T2, T3, T4, T5, T6, T7, T8> var1, Row8<T1, T2, T3, T4, T5, T6, T7, T8> var2);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   <T1, T2, T3, T4, T5, T6, T7, T8, T9> UpdateFromStep<R> set(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> var1, Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> var2);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> UpdateFromStep<R> set(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> var1, Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> var2);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> UpdateFromStep<R> set(Row11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> var1, Row11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> var2);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> UpdateFromStep<R> set(Row12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> var1, Row12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> var2);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> UpdateFromStep<R> set(Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> var1, Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> var2);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> UpdateFromStep<R> set(Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> var1, Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> var2);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> UpdateFromStep<R> set(Row15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> var1, Row15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> var2);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> UpdateFromStep<R> set(Row16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> var1, Row16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> var2);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> UpdateFromStep<R> set(Row17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> var1, Row17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> var2);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> UpdateFromStep<R> set(Row18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> var1, Row18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> var2);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> UpdateFromStep<R> set(Row19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> var1, Row19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> var2);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> UpdateFromStep<R> set(Row20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> var1, Row20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> var2);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> UpdateFromStep<R> set(Row21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> var1, Row21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> var2);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> UpdateFromStep<R> set(Row22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> var1, Row22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> var2);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   UpdateFromStep<R> set(RowN var1, RowN var2);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES_9_5})
   <T1> UpdateFromStep<R> set(Row1<T1> var1, Select<? extends Record1<T1>> var2);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES_9_5})
   <T1, T2> UpdateFromStep<R> set(Row2<T1, T2> var1, Select<? extends Record2<T1, T2>> var2);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES_9_5})
   <T1, T2, T3> UpdateFromStep<R> set(Row3<T1, T2, T3> var1, Select<? extends Record3<T1, T2, T3>> var2);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES_9_5})
   <T1, T2, T3, T4> UpdateFromStep<R> set(Row4<T1, T2, T3, T4> var1, Select<? extends Record4<T1, T2, T3, T4>> var2);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES_9_5})
   <T1, T2, T3, T4, T5> UpdateFromStep<R> set(Row5<T1, T2, T3, T4, T5> var1, Select<? extends Record5<T1, T2, T3, T4, T5>> var2);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES_9_5})
   <T1, T2, T3, T4, T5, T6> UpdateFromStep<R> set(Row6<T1, T2, T3, T4, T5, T6> var1, Select<? extends Record6<T1, T2, T3, T4, T5, T6>> var2);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES_9_5})
   <T1, T2, T3, T4, T5, T6, T7> UpdateFromStep<R> set(Row7<T1, T2, T3, T4, T5, T6, T7> var1, Select<? extends Record7<T1, T2, T3, T4, T5, T6, T7>> var2);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES_9_5})
   <T1, T2, T3, T4, T5, T6, T7, T8> UpdateFromStep<R> set(Row8<T1, T2, T3, T4, T5, T6, T7, T8> var1, Select<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> var2);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES_9_5})
   <T1, T2, T3, T4, T5, T6, T7, T8, T9> UpdateFromStep<R> set(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> var1, Select<? extends Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> var2);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES_9_5})
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> UpdateFromStep<R> set(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> var1, Select<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> var2);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES_9_5})
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> UpdateFromStep<R> set(Row11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> var1, Select<? extends Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> var2);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES_9_5})
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> UpdateFromStep<R> set(Row12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> var1, Select<? extends Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> var2);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES_9_5})
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> UpdateFromStep<R> set(Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> var1, Select<? extends Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> var2);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES_9_5})
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> UpdateFromStep<R> set(Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> var1, Select<? extends Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> var2);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES_9_5})
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> UpdateFromStep<R> set(Row15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> var1, Select<? extends Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> var2);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES_9_5})
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> UpdateFromStep<R> set(Row16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> var1, Select<? extends Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> var2);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES_9_5})
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> UpdateFromStep<R> set(Row17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> var1, Select<? extends Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>> var2);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES_9_5})
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> UpdateFromStep<R> set(Row18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> var1, Select<? extends Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>> var2);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES_9_5})
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> UpdateFromStep<R> set(Row19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> var1, Select<? extends Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>> var2);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES_9_5})
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> UpdateFromStep<R> set(Row20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> var1, Select<? extends Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>> var2);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES_9_5})
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> UpdateFromStep<R> set(Row21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> var1, Select<? extends Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>> var2);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES_9_5})
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> UpdateFromStep<R> set(Row22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> var1, Select<? extends Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>> var2);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES_9_5})
   UpdateFromStep<R> set(RowN var1, Select<?> var2);
}
