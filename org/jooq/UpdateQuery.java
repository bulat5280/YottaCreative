package org.jooq;

import java.util.Collection;

public interface UpdateQuery<R extends Record> extends StoreQuery<R>, ConditionProvider, Update<R> {
   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   <T1> void addValues(Row1<T1> var1, Row1<T1> var2);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   <T1, T2> void addValues(Row2<T1, T2> var1, Row2<T1, T2> var2);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   <T1, T2, T3> void addValues(Row3<T1, T2, T3> var1, Row3<T1, T2, T3> var2);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   <T1, T2, T3, T4> void addValues(Row4<T1, T2, T3, T4> var1, Row4<T1, T2, T3, T4> var2);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   <T1, T2, T3, T4, T5> void addValues(Row5<T1, T2, T3, T4, T5> var1, Row5<T1, T2, T3, T4, T5> var2);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   <T1, T2, T3, T4, T5, T6> void addValues(Row6<T1, T2, T3, T4, T5, T6> var1, Row6<T1, T2, T3, T4, T5, T6> var2);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   <T1, T2, T3, T4, T5, T6, T7> void addValues(Row7<T1, T2, T3, T4, T5, T6, T7> var1, Row7<T1, T2, T3, T4, T5, T6, T7> var2);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   <T1, T2, T3, T4, T5, T6, T7, T8> void addValues(Row8<T1, T2, T3, T4, T5, T6, T7, T8> var1, Row8<T1, T2, T3, T4, T5, T6, T7, T8> var2);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   <T1, T2, T3, T4, T5, T6, T7, T8, T9> void addValues(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> var1, Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> var2);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> void addValues(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> var1, Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> var2);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> void addValues(Row11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> var1, Row11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> var2);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> void addValues(Row12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> var1, Row12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> var2);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> void addValues(Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> var1, Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> var2);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> void addValues(Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> var1, Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> var2);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> void addValues(Row15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> var1, Row15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> var2);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> void addValues(Row16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> var1, Row16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> var2);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> void addValues(Row17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> var1, Row17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> var2);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> void addValues(Row18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> var1, Row18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> var2);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> void addValues(Row19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> var1, Row19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> var2);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> void addValues(Row20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> var1, Row20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> var2);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> void addValues(Row21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> var1, Row21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> var2);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> void addValues(Row22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> var1, Row22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> var2);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   void addValues(RowN var1, RowN var2);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB})
   <T1> void addValues(Row1<T1> var1, Select<? extends Record1<T1>> var2);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB})
   <T1, T2> void addValues(Row2<T1, T2> var1, Select<? extends Record2<T1, T2>> var2);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB})
   <T1, T2, T3> void addValues(Row3<T1, T2, T3> var1, Select<? extends Record3<T1, T2, T3>> var2);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB})
   <T1, T2, T3, T4> void addValues(Row4<T1, T2, T3, T4> var1, Select<? extends Record4<T1, T2, T3, T4>> var2);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB})
   <T1, T2, T3, T4, T5> void addValues(Row5<T1, T2, T3, T4, T5> var1, Select<? extends Record5<T1, T2, T3, T4, T5>> var2);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB})
   <T1, T2, T3, T4, T5, T6> void addValues(Row6<T1, T2, T3, T4, T5, T6> var1, Select<? extends Record6<T1, T2, T3, T4, T5, T6>> var2);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB})
   <T1, T2, T3, T4, T5, T6, T7> void addValues(Row7<T1, T2, T3, T4, T5, T6, T7> var1, Select<? extends Record7<T1, T2, T3, T4, T5, T6, T7>> var2);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB})
   <T1, T2, T3, T4, T5, T6, T7, T8> void addValues(Row8<T1, T2, T3, T4, T5, T6, T7, T8> var1, Select<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> var2);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB})
   <T1, T2, T3, T4, T5, T6, T7, T8, T9> void addValues(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> var1, Select<? extends Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> var2);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB})
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> void addValues(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> var1, Select<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> var2);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB})
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> void addValues(Row11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> var1, Select<? extends Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> var2);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB})
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> void addValues(Row12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> var1, Select<? extends Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> var2);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB})
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> void addValues(Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> var1, Select<? extends Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> var2);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB})
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> void addValues(Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> var1, Select<? extends Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> var2);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB})
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> void addValues(Row15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> var1, Select<? extends Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> var2);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB})
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> void addValues(Row16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> var1, Select<? extends Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> var2);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB})
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> void addValues(Row17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> var1, Select<? extends Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>> var2);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB})
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> void addValues(Row18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> var1, Select<? extends Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>> var2);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB})
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> void addValues(Row19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> var1, Select<? extends Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>> var2);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB})
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> void addValues(Row20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> var1, Select<? extends Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>> var2);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB})
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> void addValues(Row21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> var1, Select<? extends Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>> var2);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB})
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> void addValues(Row22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> var1, Select<? extends Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>> var2);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB})
   void addValues(RowN var1, Select<?> var2);

   @Support({SQLDialect.POSTGRES})
   void addFrom(TableLike<?> var1);

   @Support({SQLDialect.POSTGRES})
   void addFrom(TableLike<?>... var1);

   @Support({SQLDialect.POSTGRES})
   void addFrom(Collection<? extends TableLike<?>> var1);

   @Support
   void addConditions(Condition... var1);

   @Support
   void addConditions(Collection<? extends Condition> var1);

   @Support
   void addConditions(Operator var1, Condition... var2);

   @Support
   void addConditions(Operator var1, Collection<? extends Condition> var2);

   @Support({SQLDialect.FIREBIRD, SQLDialect.POSTGRES})
   void setReturning();

   @Support({SQLDialect.FIREBIRD, SQLDialect.POSTGRES})
   void setReturning(Identity<R, ?> var1);

   @Support({SQLDialect.FIREBIRD, SQLDialect.POSTGRES})
   void setReturning(Field<?>... var1);

   @Support({SQLDialect.FIREBIRD, SQLDialect.POSTGRES})
   void setReturning(Collection<? extends Field<?>> var1);

   @Support({SQLDialect.FIREBIRD, SQLDialect.POSTGRES})
   R getReturnedRecord();

   @Support({SQLDialect.FIREBIRD, SQLDialect.POSTGRES})
   Result<R> getReturnedRecords();
}
