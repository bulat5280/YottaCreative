package org.jooq.impl;

import java.util.Arrays;
import org.jooq.BetweenAndStep1;
import org.jooq.BetweenAndStep10;
import org.jooq.BetweenAndStep11;
import org.jooq.BetweenAndStep12;
import org.jooq.BetweenAndStep13;
import org.jooq.BetweenAndStep14;
import org.jooq.BetweenAndStep15;
import org.jooq.BetweenAndStep16;
import org.jooq.BetweenAndStep17;
import org.jooq.BetweenAndStep18;
import org.jooq.BetweenAndStep19;
import org.jooq.BetweenAndStep2;
import org.jooq.BetweenAndStep20;
import org.jooq.BetweenAndStep21;
import org.jooq.BetweenAndStep22;
import org.jooq.BetweenAndStep3;
import org.jooq.BetweenAndStep4;
import org.jooq.BetweenAndStep5;
import org.jooq.BetweenAndStep6;
import org.jooq.BetweenAndStep7;
import org.jooq.BetweenAndStep8;
import org.jooq.BetweenAndStep9;
import org.jooq.BetweenAndStepN;
import org.jooq.Clause;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.QueryPartInternal;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Record10;
import org.jooq.Record11;
import org.jooq.Record12;
import org.jooq.Record13;
import org.jooq.Record14;
import org.jooq.Record15;
import org.jooq.Record16;
import org.jooq.Record17;
import org.jooq.Record18;
import org.jooq.Record19;
import org.jooq.Record2;
import org.jooq.Record20;
import org.jooq.Record21;
import org.jooq.Record22;
import org.jooq.Record3;
import org.jooq.Record4;
import org.jooq.Record5;
import org.jooq.Record6;
import org.jooq.Record7;
import org.jooq.Record8;
import org.jooq.Record9;
import org.jooq.Row;
import org.jooq.Row1;
import org.jooq.Row10;
import org.jooq.Row11;
import org.jooq.Row12;
import org.jooq.Row13;
import org.jooq.Row14;
import org.jooq.Row15;
import org.jooq.Row16;
import org.jooq.Row17;
import org.jooq.Row18;
import org.jooq.Row19;
import org.jooq.Row2;
import org.jooq.Row20;
import org.jooq.Row21;
import org.jooq.Row22;
import org.jooq.Row3;
import org.jooq.Row4;
import org.jooq.Row5;
import org.jooq.Row6;
import org.jooq.Row7;
import org.jooq.Row8;
import org.jooq.Row9;
import org.jooq.RowN;
import org.jooq.SQLDialect;

final class RowBetweenCondition<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> extends AbstractCondition implements BetweenAndStep1<T1>, BetweenAndStep2<T1, T2>, BetweenAndStep3<T1, T2, T3>, BetweenAndStep4<T1, T2, T3, T4>, BetweenAndStep5<T1, T2, T3, T4, T5>, BetweenAndStep6<T1, T2, T3, T4, T5, T6>, BetweenAndStep7<T1, T2, T3, T4, T5, T6, T7>, BetweenAndStep8<T1, T2, T3, T4, T5, T6, T7, T8>, BetweenAndStep9<T1, T2, T3, T4, T5, T6, T7, T8, T9>, BetweenAndStep10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>, BetweenAndStep11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>, BetweenAndStep12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>, BetweenAndStep13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>, BetweenAndStep14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>, BetweenAndStep15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>, BetweenAndStep16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>, BetweenAndStep17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>, BetweenAndStep18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>, BetweenAndStep19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>, BetweenAndStep20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>, BetweenAndStep21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>, BetweenAndStep22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>, BetweenAndStepN {
   private static final long serialVersionUID = -4666251100802237878L;
   private static final Clause[] CLAUSES_BETWEEN;
   private static final Clause[] CLAUSES_BETWEEN_SYMMETRIC;
   private static final Clause[] CLAUSES_NOT_BETWEEN;
   private static final Clause[] CLAUSES_NOT_BETWEEN_SYMMETRIC;
   private final boolean symmetric;
   private final boolean not;
   private final Row row;
   private final Row minValue;
   private Row maxValue;

   RowBetweenCondition(Row row, Row minValue, boolean not, boolean symmetric) {
      this.row = row;
      this.minValue = minValue;
      this.not = not;
      this.symmetric = symmetric;
   }

   public final Condition and(Field f) {
      return this.maxValue == null ? this.and(DSL.row(f)) : super.and(f);
   }

   public final Condition and(Field<T1> t1, Field<T2> t2) {
      return this.and(DSL.row(t1, t2));
   }

   public final Condition and(Field<T1> t1, Field<T2> t2, Field<T3> t3) {
      return this.and(DSL.row(t1, t2, t3));
   }

   public final Condition and(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4) {
      return this.and(DSL.row(t1, t2, t3, t4));
   }

   public final Condition and(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5) {
      return this.and(DSL.row(t1, t2, t3, t4, t5));
   }

   public final Condition and(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6) {
      return this.and(DSL.row(t1, t2, t3, t4, t5, t6));
   }

   public final Condition and(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7) {
      return this.and(DSL.row(t1, t2, t3, t4, t5, t6, t7));
   }

   public final Condition and(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8) {
      return this.and(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8));
   }

   public final Condition and(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9) {
      return this.and(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9));
   }

   public final Condition and(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10) {
      return this.and(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10));
   }

   public final Condition and(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11) {
      return this.and(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11));
   }

   public final Condition and(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12) {
      return this.and(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12));
   }

   public final Condition and(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13) {
      return this.and(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13));
   }

   public final Condition and(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14) {
      return this.and(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14));
   }

   public final Condition and(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15) {
      return this.and(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15));
   }

   public final Condition and(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16) {
      return this.and(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16));
   }

   public final Condition and(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17) {
      return this.and(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17));
   }

   public final Condition and(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18) {
      return this.and(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18));
   }

   public final Condition and(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18, Field<T19> t19) {
      return this.and(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19));
   }

   public final Condition and(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18, Field<T19> t19, Field<T20> t20) {
      return this.and(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20));
   }

   public final Condition and(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18, Field<T19> t19, Field<T20> t20, Field<T21> t21) {
      return this.and(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21));
   }

   public final Condition and(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18, Field<T19> t19, Field<T20> t20, Field<T21> t21, Field<T22> t22) {
      return this.and(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22));
   }

   public final Condition and(Field<?>... fields) {
      return this.and(DSL.row(fields));
   }

   public final Condition and(T1 t1) {
      return this.and(DSL.row(t1));
   }

   public final Condition and(T1 t1, T2 t2) {
      return this.and(DSL.row(t1, t2));
   }

   public final Condition and(T1 t1, T2 t2, T3 t3) {
      return this.and(DSL.row(t1, t2, t3));
   }

   public final Condition and(T1 t1, T2 t2, T3 t3, T4 t4) {
      return this.and(DSL.row(t1, t2, t3, t4));
   }

   public final Condition and(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5) {
      return this.and(DSL.row(t1, t2, t3, t4, t5));
   }

   public final Condition and(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6) {
      return this.and(DSL.row(t1, t2, t3, t4, t5, t6));
   }

   public final Condition and(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7) {
      return this.and(DSL.row(t1, t2, t3, t4, t5, t6, t7));
   }

   public final Condition and(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8) {
      return this.and(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8));
   }

   public final Condition and(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9) {
      return this.and(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9));
   }

   public final Condition and(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10) {
      return this.and(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10));
   }

   public final Condition and(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11) {
      return this.and(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11));
   }

   public final Condition and(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12) {
      return this.and(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12));
   }

   public final Condition and(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13) {
      return this.and(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13));
   }

   public final Condition and(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14) {
      return this.and(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14));
   }

   public final Condition and(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15) {
      return this.and(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15));
   }

   public final Condition and(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16) {
      return this.and(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16));
   }

   public final Condition and(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17) {
      return this.and(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17));
   }

   public final Condition and(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18) {
      return this.and(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18));
   }

   public final Condition and(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19) {
      return this.and(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19));
   }

   public final Condition and(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19, T20 t20) {
      return this.and(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20));
   }

   public final Condition and(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19, T20 t20, T21 t21) {
      return this.and(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21));
   }

   public final Condition and(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19, T20 t20, T21 t21, T22 t22) {
      return this.and(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22));
   }

   public final Condition and(Object... values) {
      return this.and(DSL.row(values));
   }

   public final Condition and(Row1<T1> r) {
      this.maxValue = r;
      return this;
   }

   public final Condition and(Row2<T1, T2> r) {
      this.maxValue = r;
      return this;
   }

   public final Condition and(Row3<T1, T2, T3> r) {
      this.maxValue = r;
      return this;
   }

   public final Condition and(Row4<T1, T2, T3, T4> r) {
      this.maxValue = r;
      return this;
   }

   public final Condition and(Row5<T1, T2, T3, T4, T5> r) {
      this.maxValue = r;
      return this;
   }

   public final Condition and(Row6<T1, T2, T3, T4, T5, T6> r) {
      this.maxValue = r;
      return this;
   }

   public final Condition and(Row7<T1, T2, T3, T4, T5, T6, T7> r) {
      this.maxValue = r;
      return this;
   }

   public final Condition and(Row8<T1, T2, T3, T4, T5, T6, T7, T8> r) {
      this.maxValue = r;
      return this;
   }

   public final Condition and(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> r) {
      this.maxValue = r;
      return this;
   }

   public final Condition and(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> r) {
      this.maxValue = r;
      return this;
   }

   public final Condition and(Row11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> r) {
      this.maxValue = r;
      return this;
   }

   public final Condition and(Row12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> r) {
      this.maxValue = r;
      return this;
   }

   public final Condition and(Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> r) {
      this.maxValue = r;
      return this;
   }

   public final Condition and(Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> r) {
      this.maxValue = r;
      return this;
   }

   public final Condition and(Row15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> r) {
      this.maxValue = r;
      return this;
   }

   public final Condition and(Row16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> r) {
      this.maxValue = r;
      return this;
   }

   public final Condition and(Row17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> r) {
      this.maxValue = r;
      return this;
   }

   public final Condition and(Row18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> r) {
      this.maxValue = r;
      return this;
   }

   public final Condition and(Row19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> r) {
      this.maxValue = r;
      return this;
   }

   public final Condition and(Row20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> r) {
      this.maxValue = r;
      return this;
   }

   public final Condition and(Row21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> r) {
      this.maxValue = r;
      return this;
   }

   public final Condition and(Row22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> r) {
      this.maxValue = r;
      return this;
   }

   public final Condition and(RowN r) {
      this.maxValue = r;
      return this;
   }

   public final Condition and(Record1<T1> record) {
      return this.and(record.valuesRow());
   }

   public final Condition and(Record2<T1, T2> record) {
      return this.and(record.valuesRow());
   }

   public final Condition and(Record3<T1, T2, T3> record) {
      return this.and(record.valuesRow());
   }

   public final Condition and(Record4<T1, T2, T3, T4> record) {
      return this.and(record.valuesRow());
   }

   public final Condition and(Record5<T1, T2, T3, T4, T5> record) {
      return this.and(record.valuesRow());
   }

   public final Condition and(Record6<T1, T2, T3, T4, T5, T6> record) {
      return this.and(record.valuesRow());
   }

   public final Condition and(Record7<T1, T2, T3, T4, T5, T6, T7> record) {
      return this.and(record.valuesRow());
   }

   public final Condition and(Record8<T1, T2, T3, T4, T5, T6, T7, T8> record) {
      return this.and(record.valuesRow());
   }

   public final Condition and(Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> record) {
      return this.and(record.valuesRow());
   }

   public final Condition and(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> record) {
      return this.and(record.valuesRow());
   }

   public final Condition and(Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> record) {
      return this.and(record.valuesRow());
   }

   public final Condition and(Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> record) {
      return this.and(record.valuesRow());
   }

   public final Condition and(Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> record) {
      return this.and(record.valuesRow());
   }

   public final Condition and(Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> record) {
      return this.and(record.valuesRow());
   }

   public final Condition and(Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> record) {
      return this.and(record.valuesRow());
   }

   public final Condition and(Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> record) {
      return this.and(record.valuesRow());
   }

   public final Condition and(Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> record) {
      return this.and(record.valuesRow());
   }

   public final Condition and(Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> record) {
      return this.and(record.valuesRow());
   }

   public final Condition and(Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> record) {
      return this.and(record.valuesRow());
   }

   public final Condition and(Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> record) {
      return this.and(record.valuesRow());
   }

   public final Condition and(Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> record) {
      return this.and(record.valuesRow());
   }

   public final Condition and(Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> record) {
      return this.and(record.valuesRow());
   }

   public final Condition and(Record record) {
      RowN r = new RowImpl(Tools.fields(record.intoArray(), record.fields()));
      return this.and((RowN)r);
   }

   public final void accept(Context<?> ctx) {
      ctx.visit(this.delegate(ctx.configuration()));
   }

   public final Clause[] clauses(Context<?> ctx) {
      return null;
   }

   private final QueryPartInternal delegate(Configuration configuration) {
      RowN r = (RowN)this.row;
      RowN min = (RowN)this.minValue;
      RowN max = (RowN)this.maxValue;
      if (this.symmetric && Arrays.asList(SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.SQLITE).contains(configuration.family())) {
         return this.not ? (QueryPartInternal)r.notBetween(min, max).and(r.notBetween(max, min)) : (QueryPartInternal)r.between(min, max).or(r.between(max, min));
      } else if (this.row.size() > 1 && Arrays.asList(SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.SQLITE).contains(configuration.family())) {
         Condition result = r.ge(min).and(r.le(max));
         if (this.not) {
            result = result.not();
         }

         return (QueryPartInternal)result;
      } else {
         return new RowBetweenCondition.Native();
      }
   }

   static {
      CLAUSES_BETWEEN = new Clause[]{Clause.CONDITION, Clause.CONDITION_BETWEEN};
      CLAUSES_BETWEEN_SYMMETRIC = new Clause[]{Clause.CONDITION, Clause.CONDITION_BETWEEN_SYMMETRIC};
      CLAUSES_NOT_BETWEEN = new Clause[]{Clause.CONDITION, Clause.CONDITION_NOT_BETWEEN};
      CLAUSES_NOT_BETWEEN_SYMMETRIC = new Clause[]{Clause.CONDITION, Clause.CONDITION_NOT_BETWEEN_SYMMETRIC};
   }

   private class Native extends AbstractQueryPart {
      private static final long serialVersionUID = 2915703568738921575L;

      private Native() {
      }

      public final void accept(Context<?> context) {
         context.visit(RowBetweenCondition.this.row);
         if (RowBetweenCondition.this.not) {
            context.sql(" ").keyword("not");
         }

         context.sql(" ").keyword("between");
         if (RowBetweenCondition.this.symmetric) {
            context.sql(" ").keyword("symmetric");
         }

         context.sql(" ").visit(RowBetweenCondition.this.minValue);
         context.sql(" ").keyword("and");
         context.sql(" ").visit(RowBetweenCondition.this.maxValue);
      }

      public final Clause[] clauses(Context<?> ctx) {
         return RowBetweenCondition.this.not ? (RowBetweenCondition.this.symmetric ? RowBetweenCondition.CLAUSES_NOT_BETWEEN_SYMMETRIC : RowBetweenCondition.CLAUSES_NOT_BETWEEN) : (RowBetweenCondition.this.symmetric ? RowBetweenCondition.CLAUSES_BETWEEN_SYMMETRIC : RowBetweenCondition.CLAUSES_BETWEEN);
      }

      // $FF: synthetic method
      Native(Object x1) {
         this();
      }
   }
}
