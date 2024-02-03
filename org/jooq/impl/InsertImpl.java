package org.jooq.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.jooq.Configuration;
import org.jooq.Field;
import org.jooq.FieldLike;
import org.jooq.InsertOnConflictDoUpdateStep;
import org.jooq.InsertOnDuplicateSetMoreStep;
import org.jooq.InsertQuery;
import org.jooq.InsertResultStep;
import org.jooq.InsertSetMoreStep;
import org.jooq.InsertSetStep;
import org.jooq.InsertValuesStep1;
import org.jooq.InsertValuesStep10;
import org.jooq.InsertValuesStep11;
import org.jooq.InsertValuesStep12;
import org.jooq.InsertValuesStep13;
import org.jooq.InsertValuesStep14;
import org.jooq.InsertValuesStep15;
import org.jooq.InsertValuesStep16;
import org.jooq.InsertValuesStep17;
import org.jooq.InsertValuesStep18;
import org.jooq.InsertValuesStep19;
import org.jooq.InsertValuesStep2;
import org.jooq.InsertValuesStep20;
import org.jooq.InsertValuesStep21;
import org.jooq.InsertValuesStep22;
import org.jooq.InsertValuesStep3;
import org.jooq.InsertValuesStep4;
import org.jooq.InsertValuesStep5;
import org.jooq.InsertValuesStep6;
import org.jooq.InsertValuesStep7;
import org.jooq.InsertValuesStep8;
import org.jooq.InsertValuesStep9;
import org.jooq.InsertValuesStepN;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Result;
import org.jooq.Select;
import org.jooq.Table;

class InsertImpl<R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> extends AbstractDelegatingQuery<InsertQuery<R>> implements InsertValuesStep1<R, T1>, InsertValuesStep2<R, T1, T2>, InsertValuesStep3<R, T1, T2, T3>, InsertValuesStep4<R, T1, T2, T3, T4>, InsertValuesStep5<R, T1, T2, T3, T4, T5>, InsertValuesStep6<R, T1, T2, T3, T4, T5, T6>, InsertValuesStep7<R, T1, T2, T3, T4, T5, T6, T7>, InsertValuesStep8<R, T1, T2, T3, T4, T5, T6, T7, T8>, InsertValuesStep9<R, T1, T2, T3, T4, T5, T6, T7, T8, T9>, InsertValuesStep10<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>, InsertValuesStep11<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>, InsertValuesStep12<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>, InsertValuesStep13<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>, InsertValuesStep14<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>, InsertValuesStep15<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>, InsertValuesStep16<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>, InsertValuesStep17<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>, InsertValuesStep18<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>, InsertValuesStep19<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>, InsertValuesStep20<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>, InsertValuesStep21<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>, InsertValuesStep22<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>, InsertValuesStepN<R>, InsertSetStep<R>, InsertSetMoreStep<R>, InsertOnDuplicateSetMoreStep<R>, InsertOnConflictDoUpdateStep<R>, InsertResultStep<R> {
   private static final long serialVersionUID = 4222898879771679107L;
   private final Table<R> into;
   private Field<?>[] fields;
   private boolean onDuplicateKeyUpdate;

   InsertImpl(Configuration configuration, WithImpl with, Table<R> into) {
      super(new InsertQueryImpl(configuration, with, into));
      this.into = into;
   }

   InsertImpl(Configuration configuration, WithImpl with, Table<R> into, Collection<? extends Field<?>> fields) {
      this(configuration, with, into);
      this.columns(fields);
   }

   public final InsertImpl select(Select select) {
      ((InsertQuery)this.getDelegate()).setSelect(this.fields, select);
      return this;
   }

   public final InsertImpl values(T1 value1) {
      return this.values(value1);
   }

   public final InsertImpl values(T1 value1, T2 value2) {
      return this.values(value1, value2);
   }

   public final InsertImpl values(T1 value1, T2 value2, T3 value3) {
      return this.values(value1, value2, value3);
   }

   public final InsertImpl values(T1 value1, T2 value2, T3 value3, T4 value4) {
      return this.values(value1, value2, value3, value4);
   }

   public final InsertImpl values(T1 value1, T2 value2, T3 value3, T4 value4, T5 value5) {
      return this.values(value1, value2, value3, value4, value5);
   }

   public final InsertImpl values(T1 value1, T2 value2, T3 value3, T4 value4, T5 value5, T6 value6) {
      return this.values(value1, value2, value3, value4, value5, value6);
   }

   public final InsertImpl values(T1 value1, T2 value2, T3 value3, T4 value4, T5 value5, T6 value6, T7 value7) {
      return this.values(value1, value2, value3, value4, value5, value6, value7);
   }

   public final InsertImpl values(T1 value1, T2 value2, T3 value3, T4 value4, T5 value5, T6 value6, T7 value7, T8 value8) {
      return this.values(value1, value2, value3, value4, value5, value6, value7, value8);
   }

   public final InsertImpl values(T1 value1, T2 value2, T3 value3, T4 value4, T5 value5, T6 value6, T7 value7, T8 value8, T9 value9) {
      return this.values(value1, value2, value3, value4, value5, value6, value7, value8, value9);
   }

   public final InsertImpl values(T1 value1, T2 value2, T3 value3, T4 value4, T5 value5, T6 value6, T7 value7, T8 value8, T9 value9, T10 value10) {
      return this.values(value1, value2, value3, value4, value5, value6, value7, value8, value9, value10);
   }

   public final InsertImpl values(T1 value1, T2 value2, T3 value3, T4 value4, T5 value5, T6 value6, T7 value7, T8 value8, T9 value9, T10 value10, T11 value11) {
      return this.values(value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11);
   }

   public final InsertImpl values(T1 value1, T2 value2, T3 value3, T4 value4, T5 value5, T6 value6, T7 value7, T8 value8, T9 value9, T10 value10, T11 value11, T12 value12) {
      return this.values(value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12);
   }

   public final InsertImpl values(T1 value1, T2 value2, T3 value3, T4 value4, T5 value5, T6 value6, T7 value7, T8 value8, T9 value9, T10 value10, T11 value11, T12 value12, T13 value13) {
      return this.values(value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13);
   }

   public final InsertImpl values(T1 value1, T2 value2, T3 value3, T4 value4, T5 value5, T6 value6, T7 value7, T8 value8, T9 value9, T10 value10, T11 value11, T12 value12, T13 value13, T14 value14) {
      return this.values(value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14);
   }

   public final InsertImpl values(T1 value1, T2 value2, T3 value3, T4 value4, T5 value5, T6 value6, T7 value7, T8 value8, T9 value9, T10 value10, T11 value11, T12 value12, T13 value13, T14 value14, T15 value15) {
      return this.values(value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15);
   }

   public final InsertImpl values(T1 value1, T2 value2, T3 value3, T4 value4, T5 value5, T6 value6, T7 value7, T8 value8, T9 value9, T10 value10, T11 value11, T12 value12, T13 value13, T14 value14, T15 value15, T16 value16) {
      return this.values(value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16);
   }

   public final InsertImpl values(T1 value1, T2 value2, T3 value3, T4 value4, T5 value5, T6 value6, T7 value7, T8 value8, T9 value9, T10 value10, T11 value11, T12 value12, T13 value13, T14 value14, T15 value15, T16 value16, T17 value17) {
      return this.values(value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16, value17);
   }

   public final InsertImpl values(T1 value1, T2 value2, T3 value3, T4 value4, T5 value5, T6 value6, T7 value7, T8 value8, T9 value9, T10 value10, T11 value11, T12 value12, T13 value13, T14 value14, T15 value15, T16 value16, T17 value17, T18 value18) {
      return this.values(value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16, value17, value18);
   }

   public final InsertImpl values(T1 value1, T2 value2, T3 value3, T4 value4, T5 value5, T6 value6, T7 value7, T8 value8, T9 value9, T10 value10, T11 value11, T12 value12, T13 value13, T14 value14, T15 value15, T16 value16, T17 value17, T18 value18, T19 value19) {
      return this.values(value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16, value17, value18, value19);
   }

   public final InsertImpl values(T1 value1, T2 value2, T3 value3, T4 value4, T5 value5, T6 value6, T7 value7, T8 value8, T9 value9, T10 value10, T11 value11, T12 value12, T13 value13, T14 value14, T15 value15, T16 value16, T17 value17, T18 value18, T19 value19, T20 value20) {
      return this.values(value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16, value17, value18, value19, value20);
   }

   public final InsertImpl values(T1 value1, T2 value2, T3 value3, T4 value4, T5 value5, T6 value6, T7 value7, T8 value8, T9 value9, T10 value10, T11 value11, T12 value12, T13 value13, T14 value14, T15 value15, T16 value16, T17 value17, T18 value18, T19 value19, T20 value20, T21 value21) {
      return this.values(value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16, value17, value18, value19, value20, value21);
   }

   public final InsertImpl values(T1 value1, T2 value2, T3 value3, T4 value4, T5 value5, T6 value6, T7 value7, T8 value8, T9 value9, T10 value10, T11 value11, T12 value12, T13 value13, T14 value14, T15 value15, T16 value16, T17 value17, T18 value18, T19 value19, T20 value20, T21 value21, T22 value22) {
      return this.values(value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16, value17, value18, value19, value20, value21, value22);
   }

   public final InsertImpl values(Object... values) {
      if (this.fields.length != values.length) {
         throw new IllegalArgumentException("The number of values must match the number of fields");
      } else {
         ((InsertQuery)this.getDelegate()).newRecord();

         for(int i = 0; i < this.fields.length; ++i) {
            this.addValue((InsertQuery)this.getDelegate(), this.fields[i], values[i]);
         }

         return this;
      }
   }

   public final InsertImpl values(Collection<?> values) {
      return this.values(values.toArray());
   }

   private <T> void addValue(InsertQuery<R> delegate, Field<T> field, Object object) {
      if (object instanceof Field) {
         delegate.addValue(field, (Field)object);
      } else if (object instanceof FieldLike) {
         delegate.addValue(field, ((FieldLike)object).asField());
      } else {
         delegate.addValue(field, field.getDataType().convert(object));
      }

   }

   public final InsertImpl values(Field<T1> value1) {
      return this.values(value1);
   }

   public final InsertImpl values(Field<T1> value1, Field<T2> value2) {
      return this.values(value1, value2);
   }

   public final InsertImpl values(Field<T1> value1, Field<T2> value2, Field<T3> value3) {
      return this.values(value1, value2, value3);
   }

   public final InsertImpl values(Field<T1> value1, Field<T2> value2, Field<T3> value3, Field<T4> value4) {
      return this.values(value1, value2, value3, value4);
   }

   public final InsertImpl values(Field<T1> value1, Field<T2> value2, Field<T3> value3, Field<T4> value4, Field<T5> value5) {
      return this.values(value1, value2, value3, value4, value5);
   }

   public final InsertImpl values(Field<T1> value1, Field<T2> value2, Field<T3> value3, Field<T4> value4, Field<T5> value5, Field<T6> value6) {
      return this.values(value1, value2, value3, value4, value5, value6);
   }

   public final InsertImpl values(Field<T1> value1, Field<T2> value2, Field<T3> value3, Field<T4> value4, Field<T5> value5, Field<T6> value6, Field<T7> value7) {
      return this.values(value1, value2, value3, value4, value5, value6, value7);
   }

   public final InsertImpl values(Field<T1> value1, Field<T2> value2, Field<T3> value3, Field<T4> value4, Field<T5> value5, Field<T6> value6, Field<T7> value7, Field<T8> value8) {
      return this.values(value1, value2, value3, value4, value5, value6, value7, value8);
   }

   public final InsertImpl values(Field<T1> value1, Field<T2> value2, Field<T3> value3, Field<T4> value4, Field<T5> value5, Field<T6> value6, Field<T7> value7, Field<T8> value8, Field<T9> value9) {
      return this.values(value1, value2, value3, value4, value5, value6, value7, value8, value9);
   }

   public final InsertImpl values(Field<T1> value1, Field<T2> value2, Field<T3> value3, Field<T4> value4, Field<T5> value5, Field<T6> value6, Field<T7> value7, Field<T8> value8, Field<T9> value9, Field<T10> value10) {
      return this.values(value1, value2, value3, value4, value5, value6, value7, value8, value9, value10);
   }

   public final InsertImpl values(Field<T1> value1, Field<T2> value2, Field<T3> value3, Field<T4> value4, Field<T5> value5, Field<T6> value6, Field<T7> value7, Field<T8> value8, Field<T9> value9, Field<T10> value10, Field<T11> value11) {
      return this.values(value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11);
   }

   public final InsertImpl values(Field<T1> value1, Field<T2> value2, Field<T3> value3, Field<T4> value4, Field<T5> value5, Field<T6> value6, Field<T7> value7, Field<T8> value8, Field<T9> value9, Field<T10> value10, Field<T11> value11, Field<T12> value12) {
      return this.values(value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12);
   }

   public final InsertImpl values(Field<T1> value1, Field<T2> value2, Field<T3> value3, Field<T4> value4, Field<T5> value5, Field<T6> value6, Field<T7> value7, Field<T8> value8, Field<T9> value9, Field<T10> value10, Field<T11> value11, Field<T12> value12, Field<T13> value13) {
      return this.values(value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13);
   }

   public final InsertImpl values(Field<T1> value1, Field<T2> value2, Field<T3> value3, Field<T4> value4, Field<T5> value5, Field<T6> value6, Field<T7> value7, Field<T8> value8, Field<T9> value9, Field<T10> value10, Field<T11> value11, Field<T12> value12, Field<T13> value13, Field<T14> value14) {
      return this.values(value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14);
   }

   public final InsertImpl values(Field<T1> value1, Field<T2> value2, Field<T3> value3, Field<T4> value4, Field<T5> value5, Field<T6> value6, Field<T7> value7, Field<T8> value8, Field<T9> value9, Field<T10> value10, Field<T11> value11, Field<T12> value12, Field<T13> value13, Field<T14> value14, Field<T15> value15) {
      return this.values(value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15);
   }

   public final InsertImpl values(Field<T1> value1, Field<T2> value2, Field<T3> value3, Field<T4> value4, Field<T5> value5, Field<T6> value6, Field<T7> value7, Field<T8> value8, Field<T9> value9, Field<T10> value10, Field<T11> value11, Field<T12> value12, Field<T13> value13, Field<T14> value14, Field<T15> value15, Field<T16> value16) {
      return this.values(value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16);
   }

   public final InsertImpl values(Field<T1> value1, Field<T2> value2, Field<T3> value3, Field<T4> value4, Field<T5> value5, Field<T6> value6, Field<T7> value7, Field<T8> value8, Field<T9> value9, Field<T10> value10, Field<T11> value11, Field<T12> value12, Field<T13> value13, Field<T14> value14, Field<T15> value15, Field<T16> value16, Field<T17> value17) {
      return this.values(value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16, value17);
   }

   public final InsertImpl values(Field<T1> value1, Field<T2> value2, Field<T3> value3, Field<T4> value4, Field<T5> value5, Field<T6> value6, Field<T7> value7, Field<T8> value8, Field<T9> value9, Field<T10> value10, Field<T11> value11, Field<T12> value12, Field<T13> value13, Field<T14> value14, Field<T15> value15, Field<T16> value16, Field<T17> value17, Field<T18> value18) {
      return this.values(value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16, value17, value18);
   }

   public final InsertImpl values(Field<T1> value1, Field<T2> value2, Field<T3> value3, Field<T4> value4, Field<T5> value5, Field<T6> value6, Field<T7> value7, Field<T8> value8, Field<T9> value9, Field<T10> value10, Field<T11> value11, Field<T12> value12, Field<T13> value13, Field<T14> value14, Field<T15> value15, Field<T16> value16, Field<T17> value17, Field<T18> value18, Field<T19> value19) {
      return this.values(value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16, value17, value18, value19);
   }

   public final InsertImpl values(Field<T1> value1, Field<T2> value2, Field<T3> value3, Field<T4> value4, Field<T5> value5, Field<T6> value6, Field<T7> value7, Field<T8> value8, Field<T9> value9, Field<T10> value10, Field<T11> value11, Field<T12> value12, Field<T13> value13, Field<T14> value14, Field<T15> value15, Field<T16> value16, Field<T17> value17, Field<T18> value18, Field<T19> value19, Field<T20> value20) {
      return this.values(value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16, value17, value18, value19, value20);
   }

   public final InsertImpl values(Field<T1> value1, Field<T2> value2, Field<T3> value3, Field<T4> value4, Field<T5> value5, Field<T6> value6, Field<T7> value7, Field<T8> value8, Field<T9> value9, Field<T10> value10, Field<T11> value11, Field<T12> value12, Field<T13> value13, Field<T14> value14, Field<T15> value15, Field<T16> value16, Field<T17> value17, Field<T18> value18, Field<T19> value19, Field<T20> value20, Field<T21> value21) {
      return this.values(value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16, value17, value18, value19, value20, value21);
   }

   public final InsertImpl values(Field<T1> value1, Field<T2> value2, Field<T3> value3, Field<T4> value4, Field<T5> value5, Field<T6> value6, Field<T7> value7, Field<T8> value8, Field<T9> value9, Field<T10> value10, Field<T11> value11, Field<T12> value12, Field<T13> value13, Field<T14> value14, Field<T15> value15, Field<T16> value16, Field<T17> value17, Field<T18> value18, Field<T19> value19, Field<T20> value20, Field<T21> value21, Field<T22> value22) {
      return this.values(value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16, value17, value18, value19, value20, value21, value22);
   }

   public final InsertImpl values(Field<?>... values) {
      List<Field<?>> values1 = Arrays.asList(values);
      if (this.fields.length != values1.size()) {
         throw new IllegalArgumentException("The number of values must match the number of fields");
      } else {
         ((InsertQuery)this.getDelegate()).newRecord();

         for(int i = 0; i < this.fields.length; ++i) {
            ((InsertQuery)this.getDelegate()).addValue(this.fields[i], (Field)values1.get(i));
         }

         return this;
      }
   }

   public final <T1> InsertImpl columns(Field<T1> field1) {
      return this.columns(field1);
   }

   public final <T1, T2> InsertImpl columns(Field<T1> field1, Field<T2> field2) {
      return this.columns(field1, field2);
   }

   public final <T1, T2, T3> InsertImpl columns(Field<T1> field1, Field<T2> field2, Field<T3> field3) {
      return this.columns(field1, field2, field3);
   }

   public final <T1, T2, T3, T4> InsertImpl columns(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4) {
      return this.columns(field1, field2, field3, field4);
   }

   public final <T1, T2, T3, T4, T5> InsertImpl columns(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5) {
      return this.columns(field1, field2, field3, field4, field5);
   }

   public final <T1, T2, T3, T4, T5, T6> InsertImpl columns(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6) {
      return this.columns(field1, field2, field3, field4, field5, field6);
   }

   public final <T1, T2, T3, T4, T5, T6, T7> InsertImpl columns(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7) {
      return this.columns(field1, field2, field3, field4, field5, field6, field7);
   }

   public final <T1, T2, T3, T4, T5, T6, T7, T8> InsertImpl columns(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8) {
      return this.columns(field1, field2, field3, field4, field5, field6, field7, field8);
   }

   public final <T1, T2, T3, T4, T5, T6, T7, T8, T9> InsertImpl columns(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9) {
      return this.columns(field1, field2, field3, field4, field5, field6, field7, field8, field9);
   }

   public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> InsertImpl columns(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10) {
      return this.columns(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10);
   }

   public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> InsertImpl columns(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11) {
      return this.columns(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11);
   }

   public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> InsertImpl columns(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12) {
      return this.columns(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12);
   }

   public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> InsertImpl columns(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13) {
      return this.columns(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13);
   }

   public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> InsertImpl columns(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14) {
      return this.columns(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14);
   }

   public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> InsertImpl columns(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15) {
      return this.columns(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15);
   }

   public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> InsertImpl columns(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16) {
      return this.columns(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16);
   }

   public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> InsertImpl columns(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17) {
      return this.columns(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17);
   }

   public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> InsertImpl columns(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18) {
      return this.columns(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18);
   }

   public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> InsertImpl columns(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19) {
      return this.columns(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19);
   }

   public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> InsertImpl columns(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20) {
      return this.columns(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20);
   }

   public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> InsertImpl columns(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20, Field<T21> field21) {
      return this.columns(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21);
   }

   public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> InsertImpl columns(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20, Field<T21> field21, Field<T22> field22) {
      return this.columns(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21, field22);
   }

   public final InsertImpl columns(Field<?>... f) {
      this.fields = f != null && f.length != 0 ? f : this.into.fields();
      return this;
   }

   public final InsertImpl columns(Collection<? extends Field<?>> f) {
      return this.columns((Field[])f.toArray(Tools.EMPTY_FIELD));
   }

   public final InsertImpl defaultValues() {
      ((InsertQuery)this.getDelegate()).setDefaultValues();
      return this;
   }

   public final InsertImpl doUpdate() {
      return this.onDuplicateKeyUpdate();
   }

   public final InsertImpl doNothing() {
      return this.onDuplicateKeyIgnore();
   }

   public final InsertImpl onConflict(Field<?>... keys) {
      return this.onConflict((Collection)Arrays.asList(keys));
   }

   public final InsertImpl onConflict(Collection<? extends Field<?>> keys) {
      ((InsertQuery)this.getDelegate()).onConflict(keys);
      return this;
   }

   public final InsertImpl onConflictDoNothing() {
      this.onConflict().doNothing();
      return this;
   }

   public final InsertImpl onDuplicateKeyUpdate() {
      this.onDuplicateKeyUpdate = true;
      ((InsertQuery)this.getDelegate()).onDuplicateKeyUpdate(true);
      return this;
   }

   public final InsertImpl onDuplicateKeyIgnore() {
      ((InsertQuery)this.getDelegate()).onDuplicateKeyIgnore(true);
      return this;
   }

   public final <T> InsertImpl set(Field<T> field, T value) {
      if (this.onDuplicateKeyUpdate) {
         ((InsertQuery)this.getDelegate()).addValueForUpdate(field, value);
      } else {
         ((InsertQuery)this.getDelegate()).addValue(field, value);
      }

      return this;
   }

   public final <T> InsertImpl set(Field<T> field, Field<T> value) {
      if (this.onDuplicateKeyUpdate) {
         ((InsertQuery)this.getDelegate()).addValueForUpdate(field, value);
      } else {
         ((InsertQuery)this.getDelegate()).addValue(field, value);
      }

      return this;
   }

   public final <T> InsertImpl set(Field<T> field, Select<? extends Record1<T>> value) {
      return this.set(field, value.asField());
   }

   public final InsertImpl set(Map<? extends Field<?>, ?> map) {
      if (this.onDuplicateKeyUpdate) {
         ((InsertQuery)this.getDelegate()).addValuesForUpdate(map);
      } else {
         ((InsertQuery)this.getDelegate()).addValues(map);
      }

      return this;
   }

   public final InsertImpl set(Record record) {
      return this.set(Tools.mapOfChangedValues(record));
   }

   public final InsertImpl newRecord() {
      ((InsertQuery)this.getDelegate()).newRecord();
      return this;
   }

   public final InsertImpl returning() {
      ((InsertQuery)this.getDelegate()).setReturning();
      return this;
   }

   public final InsertImpl returning(Field<?>... f) {
      ((InsertQuery)this.getDelegate()).setReturning(f);
      return this;
   }

   public final InsertImpl returning(Collection<? extends Field<?>> f) {
      ((InsertQuery)this.getDelegate()).setReturning(f);
      return this;
   }

   public final Result<R> fetch() {
      ((InsertQuery)this.getDelegate()).execute();
      return ((InsertQuery)this.getDelegate()).getReturnedRecords();
   }

   public final R fetchOne() {
      ((InsertQuery)this.getDelegate()).execute();
      return ((InsertQuery)this.getDelegate()).getReturnedRecord();
   }

   public final Optional<R> fetchOptional() {
      return Optional.ofNullable(this.fetchOne());
   }
}
