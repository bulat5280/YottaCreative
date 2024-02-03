package org.jooq.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;
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
import org.jooq.Comparator;
import org.jooq.Condition;
import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.Name;
import org.jooq.QuantifiedSelect;
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
import org.jooq.Result;
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
import org.jooq.Select;

final class RowImpl<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> extends AbstractQueryPart implements RowN, Row1<T1>, Row2<T1, T2>, Row3<T1, T2, T3>, Row4<T1, T2, T3, T4>, Row5<T1, T2, T3, T4, T5>, Row6<T1, T2, T3, T4, T5, T6>, Row7<T1, T2, T3, T4, T5, T6, T7>, Row8<T1, T2, T3, T4, T5, T6, T7, T8>, Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9>, Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>, Row11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>, Row12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>, Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>, Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>, Row15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>, Row16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>, Row17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>, Row18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>, Row19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>, Row20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>, Row21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>, Row22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> {
   private static final long serialVersionUID = -929427349071556318L;
   private static final Clause[] CLAUSES;
   final Fields fields;

   RowImpl(Field<?>... fields) {
      this(new Fields(fields));
   }

   RowImpl(Collection<? extends Field<?>> fields) {
      this(new Fields(fields));
   }

   RowImpl(Fields fields) {
      this.fields = fields;
   }

   public final void accept(Context<?> context) {
      context.sql("(");
      String separator = "";
      Field[] var3 = this.fields.fields;
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Field<?> field = var3[var5];
         context.sql(separator);
         context.visit(field);
         separator = ", ";
      }

      context.sql(")");
   }

   public final Clause[] clauses(Context<?> ctx) {
      return CLAUSES;
   }

   public final int size() {
      return this.fields.size();
   }

   public final Stream<Field<?>> fieldStream() {
      return Stream.of(this.fields());
   }

   public final <T> Field<T> field(Field<T> field) {
      return this.fields.field(field);
   }

   public final Field<?> field(String name) {
      return this.fields.field(name);
   }

   public final <T> Field<T> field(String name, Class<T> type) {
      return this.fields.field(name, type);
   }

   public final <T> Field<T> field(String name, DataType<T> dataType) {
      return this.fields.field(name, dataType);
   }

   public final Field<?> field(Name name) {
      return this.fields.field(name);
   }

   public final <T> Field<T> field(Name name, Class<T> type) {
      return this.fields.field(name, type);
   }

   public final <T> Field<T> field(Name name, DataType<T> dataType) {
      return this.fields.field(name, dataType);
   }

   public final Field<?> field(int index) {
      return this.fields.field(index);
   }

   public final <T> Field<T> field(int index, Class<T> type) {
      return this.fields.field(index, type);
   }

   public final <T> Field<T> field(int index, DataType<T> dataType) {
      return this.fields.field(index, dataType);
   }

   public final Field<?>[] fields() {
      return this.fields.fields();
   }

   public final Field<?>[] fields(Field<?>... f) {
      return this.fields.fields(f);
   }

   public final Field<?>[] fields(String... fieldNames) {
      return this.fields.fields(fieldNames);
   }

   public final Field<?>[] fields(Name... fieldNames) {
      return this.fields.fields(fieldNames);
   }

   public final Field<?>[] fields(int... fieldIndexes) {
      return this.fields.fields(fieldIndexes);
   }

   public final int indexOf(Field<?> field) {
      return this.fields.indexOf(field);
   }

   public final int indexOf(String fieldName) {
      return this.fields.indexOf(fieldName);
   }

   public final int indexOf(Name fieldName) {
      return this.fields.indexOf(fieldName);
   }

   public final Class<?>[] types() {
      return this.fields.types();
   }

   public final Class<?> type(int fieldIndex) {
      return this.fields.type(fieldIndex);
   }

   public final Class<?> type(String fieldName) {
      return this.fields.type(fieldName);
   }

   public final Class<?> type(Name fieldName) {
      return this.fields.type(fieldName);
   }

   public final DataType<?>[] dataTypes() {
      return this.fields.dataTypes();
   }

   public final DataType<?> dataType(int fieldIndex) {
      return this.fields.dataType(fieldIndex);
   }

   public final DataType<?> dataType(String fieldName) {
      return this.fields.dataType(fieldName);
   }

   public final DataType<?> dataType(Name fieldName) {
      return this.fields.dataType(fieldName);
   }

   public final Field<T1> field1() {
      return this.fields.field(0);
   }

   public final Field<T2> field2() {
      return this.fields.field(1);
   }

   public final Field<T3> field3() {
      return this.fields.field(2);
   }

   public final Field<T4> field4() {
      return this.fields.field(3);
   }

   public final Field<T5> field5() {
      return this.fields.field(4);
   }

   public final Field<T6> field6() {
      return this.fields.field(5);
   }

   public final Field<T7> field7() {
      return this.fields.field(6);
   }

   public final Field<T8> field8() {
      return this.fields.field(7);
   }

   public final Field<T9> field9() {
      return this.fields.field(8);
   }

   public final Field<T10> field10() {
      return this.fields.field(9);
   }

   public final Field<T11> field11() {
      return this.fields.field(10);
   }

   public final Field<T12> field12() {
      return this.fields.field(11);
   }

   public final Field<T13> field13() {
      return this.fields.field(12);
   }

   public final Field<T14> field14() {
      return this.fields.field(13);
   }

   public final Field<T15> field15() {
      return this.fields.field(14);
   }

   public final Field<T16> field16() {
      return this.fields.field(15);
   }

   public final Field<T17> field17() {
      return this.fields.field(16);
   }

   public final Field<T18> field18() {
      return this.fields.field(17);
   }

   public final Field<T19> field19() {
      return this.fields.field(18);
   }

   public final Field<T20> field20() {
      return this.fields.field(19);
   }

   public final Field<T21> field21() {
      return this.fields.field(20);
   }

   public final Field<T22> field22() {
      return this.fields.field(21);
   }

   public final Condition isNull() {
      return new RowIsNull(this, true);
   }

   public final Condition isNotNull() {
      return new RowIsNull(this, false);
   }

   public final Condition compare(Comparator comparator, RowN row) {
      return new RowCondition(this, row, comparator);
   }

   public final Condition compare(Comparator comparator, Row1<T1> row) {
      return new RowCondition(this, row, comparator);
   }

   public final Condition compare(Comparator comparator, Row2<T1, T2> row) {
      return new RowCondition(this, row, comparator);
   }

   public final Condition compare(Comparator comparator, Row3<T1, T2, T3> row) {
      return new RowCondition(this, row, comparator);
   }

   public final Condition compare(Comparator comparator, Row4<T1, T2, T3, T4> row) {
      return new RowCondition(this, row, comparator);
   }

   public final Condition compare(Comparator comparator, Row5<T1, T2, T3, T4, T5> row) {
      return new RowCondition(this, row, comparator);
   }

   public final Condition compare(Comparator comparator, Row6<T1, T2, T3, T4, T5, T6> row) {
      return new RowCondition(this, row, comparator);
   }

   public final Condition compare(Comparator comparator, Row7<T1, T2, T3, T4, T5, T6, T7> row) {
      return new RowCondition(this, row, comparator);
   }

   public final Condition compare(Comparator comparator, Row8<T1, T2, T3, T4, T5, T6, T7, T8> row) {
      return new RowCondition(this, row, comparator);
   }

   public final Condition compare(Comparator comparator, Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> row) {
      return new RowCondition(this, row, comparator);
   }

   public final Condition compare(Comparator comparator, Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> row) {
      return new RowCondition(this, row, comparator);
   }

   public final Condition compare(Comparator comparator, Row11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> row) {
      return new RowCondition(this, row, comparator);
   }

   public final Condition compare(Comparator comparator, Row12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> row) {
      return new RowCondition(this, row, comparator);
   }

   public final Condition compare(Comparator comparator, Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> row) {
      return new RowCondition(this, row, comparator);
   }

   public final Condition compare(Comparator comparator, Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> row) {
      return new RowCondition(this, row, comparator);
   }

   public final Condition compare(Comparator comparator, Row15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> row) {
      return new RowCondition(this, row, comparator);
   }

   public final Condition compare(Comparator comparator, Row16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> row) {
      return new RowCondition(this, row, comparator);
   }

   public final Condition compare(Comparator comparator, Row17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> row) {
      return new RowCondition(this, row, comparator);
   }

   public final Condition compare(Comparator comparator, Row18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> row) {
      return new RowCondition(this, row, comparator);
   }

   public final Condition compare(Comparator comparator, Row19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> row) {
      return new RowCondition(this, row, comparator);
   }

   public final Condition compare(Comparator comparator, Row20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> row) {
      return new RowCondition(this, row, comparator);
   }

   public final Condition compare(Comparator comparator, Row21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> row) {
      return new RowCondition(this, row, comparator);
   }

   public final Condition compare(Comparator comparator, Row22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> row) {
      return new RowCondition(this, row, comparator);
   }

   public final Condition compare(Comparator comparator, Record record) {
      return new RowCondition(this, record.valuesRow(), comparator);
   }

   public final Condition compare(Comparator comparator, Record1<T1> record) {
      return new RowCondition(this, record.valuesRow(), comparator);
   }

   public final Condition compare(Comparator comparator, Record2<T1, T2> record) {
      return new RowCondition(this, record.valuesRow(), comparator);
   }

   public final Condition compare(Comparator comparator, Record3<T1, T2, T3> record) {
      return new RowCondition(this, record.valuesRow(), comparator);
   }

   public final Condition compare(Comparator comparator, Record4<T1, T2, T3, T4> record) {
      return new RowCondition(this, record.valuesRow(), comparator);
   }

   public final Condition compare(Comparator comparator, Record5<T1, T2, T3, T4, T5> record) {
      return new RowCondition(this, record.valuesRow(), comparator);
   }

   public final Condition compare(Comparator comparator, Record6<T1, T2, T3, T4, T5, T6> record) {
      return new RowCondition(this, record.valuesRow(), comparator);
   }

   public final Condition compare(Comparator comparator, Record7<T1, T2, T3, T4, T5, T6, T7> record) {
      return new RowCondition(this, record.valuesRow(), comparator);
   }

   public final Condition compare(Comparator comparator, Record8<T1, T2, T3, T4, T5, T6, T7, T8> record) {
      return new RowCondition(this, record.valuesRow(), comparator);
   }

   public final Condition compare(Comparator comparator, Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> record) {
      return new RowCondition(this, record.valuesRow(), comparator);
   }

   public final Condition compare(Comparator comparator, Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> record) {
      return new RowCondition(this, record.valuesRow(), comparator);
   }

   public final Condition compare(Comparator comparator, Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> record) {
      return new RowCondition(this, record.valuesRow(), comparator);
   }

   public final Condition compare(Comparator comparator, Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> record) {
      return new RowCondition(this, record.valuesRow(), comparator);
   }

   public final Condition compare(Comparator comparator, Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> record) {
      return new RowCondition(this, record.valuesRow(), comparator);
   }

   public final Condition compare(Comparator comparator, Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> record) {
      return new RowCondition(this, record.valuesRow(), comparator);
   }

   public final Condition compare(Comparator comparator, Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> record) {
      return new RowCondition(this, record.valuesRow(), comparator);
   }

   public final Condition compare(Comparator comparator, Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> record) {
      return new RowCondition(this, record.valuesRow(), comparator);
   }

   public final Condition compare(Comparator comparator, Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> record) {
      return new RowCondition(this, record.valuesRow(), comparator);
   }

   public final Condition compare(Comparator comparator, Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> record) {
      return new RowCondition(this, record.valuesRow(), comparator);
   }

   public final Condition compare(Comparator comparator, Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> record) {
      return new RowCondition(this, record.valuesRow(), comparator);
   }

   public final Condition compare(Comparator comparator, Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> record) {
      return new RowCondition(this, record.valuesRow(), comparator);
   }

   public final Condition compare(Comparator comparator, Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> record) {
      return new RowCondition(this, record.valuesRow(), comparator);
   }

   public final Condition compare(Comparator comparator, Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> record) {
      return new RowCondition(this, record.valuesRow(), comparator);
   }

   public final Condition compare(Comparator comparator, Object... values) {
      return this.compare(comparator, DSL.row(values));
   }

   public final Condition compare(Comparator comparator, T1 t1) {
      return this.compare(comparator, DSL.row(t1));
   }

   public final Condition compare(Comparator comparator, T1 t1, T2 t2) {
      return this.compare(comparator, DSL.row(t1, t2));
   }

   public final Condition compare(Comparator comparator, T1 t1, T2 t2, T3 t3) {
      return this.compare(comparator, DSL.row(t1, t2, t3));
   }

   public final Condition compare(Comparator comparator, T1 t1, T2 t2, T3 t3, T4 t4) {
      return this.compare(comparator, DSL.row(t1, t2, t3, t4));
   }

   public final Condition compare(Comparator comparator, T1 t1, T2 t2, T3 t3, T4 t4, T5 t5) {
      return this.compare(comparator, DSL.row(t1, t2, t3, t4, t5));
   }

   public final Condition compare(Comparator comparator, T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6) {
      return this.compare(comparator, DSL.row(t1, t2, t3, t4, t5, t6));
   }

   public final Condition compare(Comparator comparator, T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7) {
      return this.compare(comparator, DSL.row(t1, t2, t3, t4, t5, t6, t7));
   }

   public final Condition compare(Comparator comparator, T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8) {
      return this.compare(comparator, DSL.row(t1, t2, t3, t4, t5, t6, t7, t8));
   }

   public final Condition compare(Comparator comparator, T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9) {
      return this.compare(comparator, DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9));
   }

   public final Condition compare(Comparator comparator, T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10) {
      return this.compare(comparator, DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10));
   }

   public final Condition compare(Comparator comparator, T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11) {
      return this.compare(comparator, DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11));
   }

   public final Condition compare(Comparator comparator, T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12) {
      return this.compare(comparator, DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12));
   }

   public final Condition compare(Comparator comparator, T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13) {
      return this.compare(comparator, DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13));
   }

   public final Condition compare(Comparator comparator, T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14) {
      return this.compare(comparator, DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14));
   }

   public final Condition compare(Comparator comparator, T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15) {
      return this.compare(comparator, DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15));
   }

   public final Condition compare(Comparator comparator, T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16) {
      return this.compare(comparator, DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16));
   }

   public final Condition compare(Comparator comparator, T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17) {
      return this.compare(comparator, DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17));
   }

   public final Condition compare(Comparator comparator, T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18) {
      return this.compare(comparator, DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18));
   }

   public final Condition compare(Comparator comparator, T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19) {
      return this.compare(comparator, DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19));
   }

   public final Condition compare(Comparator comparator, T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19, T20 t20) {
      return this.compare(comparator, DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20));
   }

   public final Condition compare(Comparator comparator, T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19, T20 t20, T21 t21) {
      return this.compare(comparator, DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21));
   }

   public final Condition compare(Comparator comparator, T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19, T20 t20, T21 t21, T22 t22) {
      return this.compare(comparator, DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22));
   }

   public final Condition compare(Comparator comparator, Field<?>... values) {
      return this.compare(comparator, DSL.row(values));
   }

   public final Condition compare(Comparator comparator, Field<T1> t1) {
      return this.compare(comparator, DSL.row(t1));
   }

   public final Condition compare(Comparator comparator, Field<T1> t1, Field<T2> t2) {
      return this.compare(comparator, DSL.row(t1, t2));
   }

   public final Condition compare(Comparator comparator, Field<T1> t1, Field<T2> t2, Field<T3> t3) {
      return this.compare(comparator, DSL.row(t1, t2, t3));
   }

   public final Condition compare(Comparator comparator, Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4) {
      return this.compare(comparator, DSL.row(t1, t2, t3, t4));
   }

   public final Condition compare(Comparator comparator, Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5) {
      return this.compare(comparator, DSL.row(t1, t2, t3, t4, t5));
   }

   public final Condition compare(Comparator comparator, Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6) {
      return this.compare(comparator, DSL.row(t1, t2, t3, t4, t5, t6));
   }

   public final Condition compare(Comparator comparator, Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7) {
      return this.compare(comparator, DSL.row(t1, t2, t3, t4, t5, t6, t7));
   }

   public final Condition compare(Comparator comparator, Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8) {
      return this.compare(comparator, DSL.row(t1, t2, t3, t4, t5, t6, t7, t8));
   }

   public final Condition compare(Comparator comparator, Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9) {
      return this.compare(comparator, DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9));
   }

   public final Condition compare(Comparator comparator, Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10) {
      return this.compare(comparator, DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10));
   }

   public final Condition compare(Comparator comparator, Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11) {
      return this.compare(comparator, DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11));
   }

   public final Condition compare(Comparator comparator, Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12) {
      return this.compare(comparator, DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12));
   }

   public final Condition compare(Comparator comparator, Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13) {
      return this.compare(comparator, DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13));
   }

   public final Condition compare(Comparator comparator, Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14) {
      return this.compare(comparator, DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14));
   }

   public final Condition compare(Comparator comparator, Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15) {
      return this.compare(comparator, DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15));
   }

   public final Condition compare(Comparator comparator, Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16) {
      return this.compare(comparator, DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16));
   }

   public final Condition compare(Comparator comparator, Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17) {
      return this.compare(comparator, DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17));
   }

   public final Condition compare(Comparator comparator, Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18) {
      return this.compare(comparator, DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18));
   }

   public final Condition compare(Comparator comparator, Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18, Field<T19> t19) {
      return this.compare(comparator, DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19));
   }

   public final Condition compare(Comparator comparator, Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18, Field<T19> t19, Field<T20> t20) {
      return this.compare(comparator, DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20));
   }

   public final Condition compare(Comparator comparator, Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18, Field<T19> t19, Field<T20> t20, Field<T21> t21) {
      return this.compare(comparator, DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21));
   }

   public final Condition compare(Comparator comparator, Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18, Field<T19> t19, Field<T20> t20, Field<T21> t21, Field<T22> t22) {
      return this.compare(comparator, DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22));
   }

   public final Condition compare(Comparator comparator, Select select) {
      return new RowSubqueryCondition(this, select, comparator);
   }

   public final Condition compare(Comparator comparator, QuantifiedSelect select) {
      return new RowSubqueryCondition(this, select, comparator);
   }

   public final Condition equal(RowN row) {
      return this.compare(Comparator.EQUALS, row);
   }

   public final Condition equal(Row1<T1> row) {
      return this.compare(Comparator.EQUALS, row);
   }

   public final Condition equal(Row2<T1, T2> row) {
      return this.compare(Comparator.EQUALS, row);
   }

   public final Condition equal(Row3<T1, T2, T3> row) {
      return this.compare(Comparator.EQUALS, row);
   }

   public final Condition equal(Row4<T1, T2, T3, T4> row) {
      return this.compare(Comparator.EQUALS, row);
   }

   public final Condition equal(Row5<T1, T2, T3, T4, T5> row) {
      return this.compare(Comparator.EQUALS, row);
   }

   public final Condition equal(Row6<T1, T2, T3, T4, T5, T6> row) {
      return this.compare(Comparator.EQUALS, row);
   }

   public final Condition equal(Row7<T1, T2, T3, T4, T5, T6, T7> row) {
      return this.compare(Comparator.EQUALS, row);
   }

   public final Condition equal(Row8<T1, T2, T3, T4, T5, T6, T7, T8> row) {
      return this.compare(Comparator.EQUALS, row);
   }

   public final Condition equal(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> row) {
      return this.compare(Comparator.EQUALS, row);
   }

   public final Condition equal(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> row) {
      return this.compare(Comparator.EQUALS, row);
   }

   public final Condition equal(Row11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> row) {
      return this.compare(Comparator.EQUALS, row);
   }

   public final Condition equal(Row12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> row) {
      return this.compare(Comparator.EQUALS, row);
   }

   public final Condition equal(Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> row) {
      return this.compare(Comparator.EQUALS, row);
   }

   public final Condition equal(Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> row) {
      return this.compare(Comparator.EQUALS, row);
   }

   public final Condition equal(Row15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> row) {
      return this.compare(Comparator.EQUALS, row);
   }

   public final Condition equal(Row16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> row) {
      return this.compare(Comparator.EQUALS, row);
   }

   public final Condition equal(Row17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> row) {
      return this.compare(Comparator.EQUALS, row);
   }

   public final Condition equal(Row18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> row) {
      return this.compare(Comparator.EQUALS, row);
   }

   public final Condition equal(Row19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> row) {
      return this.compare(Comparator.EQUALS, row);
   }

   public final Condition equal(Row20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> row) {
      return this.compare(Comparator.EQUALS, row);
   }

   public final Condition equal(Row21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> row) {
      return this.compare(Comparator.EQUALS, row);
   }

   public final Condition equal(Row22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> row) {
      return this.compare(Comparator.EQUALS, row);
   }

   public final Condition equal(Record record) {
      return this.compare(Comparator.EQUALS, record);
   }

   public final Condition equal(Record1<T1> record) {
      return this.compare(Comparator.EQUALS, record);
   }

   public final Condition equal(Record2<T1, T2> record) {
      return this.compare(Comparator.EQUALS, record);
   }

   public final Condition equal(Record3<T1, T2, T3> record) {
      return this.compare(Comparator.EQUALS, record);
   }

   public final Condition equal(Record4<T1, T2, T3, T4> record) {
      return this.compare(Comparator.EQUALS, record);
   }

   public final Condition equal(Record5<T1, T2, T3, T4, T5> record) {
      return this.compare(Comparator.EQUALS, record);
   }

   public final Condition equal(Record6<T1, T2, T3, T4, T5, T6> record) {
      return this.compare(Comparator.EQUALS, record);
   }

   public final Condition equal(Record7<T1, T2, T3, T4, T5, T6, T7> record) {
      return this.compare(Comparator.EQUALS, record);
   }

   public final Condition equal(Record8<T1, T2, T3, T4, T5, T6, T7, T8> record) {
      return this.compare(Comparator.EQUALS, record);
   }

   public final Condition equal(Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> record) {
      return this.compare(Comparator.EQUALS, record);
   }

   public final Condition equal(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> record) {
      return this.compare(Comparator.EQUALS, record);
   }

   public final Condition equal(Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> record) {
      return this.compare(Comparator.EQUALS, record);
   }

   public final Condition equal(Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> record) {
      return this.compare(Comparator.EQUALS, record);
   }

   public final Condition equal(Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> record) {
      return this.compare(Comparator.EQUALS, record);
   }

   public final Condition equal(Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> record) {
      return this.compare(Comparator.EQUALS, record);
   }

   public final Condition equal(Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> record) {
      return this.compare(Comparator.EQUALS, record);
   }

   public final Condition equal(Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> record) {
      return this.compare(Comparator.EQUALS, record);
   }

   public final Condition equal(Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> record) {
      return this.compare(Comparator.EQUALS, record);
   }

   public final Condition equal(Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> record) {
      return this.compare(Comparator.EQUALS, record);
   }

   public final Condition equal(Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> record) {
      return this.compare(Comparator.EQUALS, record);
   }

   public final Condition equal(Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> record) {
      return this.compare(Comparator.EQUALS, record);
   }

   public final Condition equal(Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> record) {
      return this.compare(Comparator.EQUALS, record);
   }

   public final Condition equal(Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> record) {
      return this.compare(Comparator.EQUALS, record);
   }

   public final Condition equal(Object... values) {
      return this.compare(Comparator.EQUALS, values);
   }

   public final Condition equal(T1 t1) {
      return this.compare(Comparator.EQUALS, t1);
   }

   public final Condition equal(T1 t1, T2 t2) {
      return this.compare(Comparator.EQUALS, t1, t2);
   }

   public final Condition equal(T1 t1, T2 t2, T3 t3) {
      return this.compare(Comparator.EQUALS, t1, t2, t3);
   }

   public final Condition equal(T1 t1, T2 t2, T3 t3, T4 t4) {
      return this.compare(Comparator.EQUALS, t1, t2, t3, t4);
   }

   public final Condition equal(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5) {
      return this.compare(Comparator.EQUALS, t1, t2, t3, t4, t5);
   }

   public final Condition equal(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6) {
      return this.compare(Comparator.EQUALS, t1, t2, t3, t4, t5, t6);
   }

   public final Condition equal(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7) {
      return this.compare(Comparator.EQUALS, t1, t2, t3, t4, t5, t6, t7);
   }

   public final Condition equal(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8) {
      return this.compare(Comparator.EQUALS, t1, t2, t3, t4, t5, t6, t7, t8);
   }

   public final Condition equal(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9) {
      return this.compare(Comparator.EQUALS, t1, t2, t3, t4, t5, t6, t7, t8, t9);
   }

   public final Condition equal(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10) {
      return this.compare(Comparator.EQUALS, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
   }

   public final Condition equal(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11) {
      return this.compare(Comparator.EQUALS, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11);
   }

   public final Condition equal(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12) {
      return this.compare(Comparator.EQUALS, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12);
   }

   public final Condition equal(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13) {
      return this.compare(Comparator.EQUALS, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13);
   }

   public final Condition equal(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14) {
      return this.compare(Comparator.EQUALS, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14);
   }

   public final Condition equal(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15) {
      return this.compare(Comparator.EQUALS, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15);
   }

   public final Condition equal(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16) {
      return this.compare(Comparator.EQUALS, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16);
   }

   public final Condition equal(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17) {
      return this.compare(Comparator.EQUALS, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17);
   }

   public final Condition equal(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18) {
      return this.compare(Comparator.EQUALS, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18);
   }

   public final Condition equal(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19) {
      return this.compare(Comparator.EQUALS, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19);
   }

   public final Condition equal(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19, T20 t20) {
      return this.compare(Comparator.EQUALS, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20);
   }

   public final Condition equal(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19, T20 t20, T21 t21) {
      return this.compare(Comparator.EQUALS, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21);
   }

   public final Condition equal(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19, T20 t20, T21 t21, T22 t22) {
      return this.compare(Comparator.EQUALS, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22);
   }

   public final Condition equal(Field<?>... values) {
      return this.compare(Comparator.EQUALS, values);
   }

   public final Condition equal(Field<T1> t1) {
      return this.compare(Comparator.EQUALS, t1);
   }

   public final Condition equal(Field<T1> t1, Field<T2> t2) {
      return this.compare(Comparator.EQUALS, t1, t2);
   }

   public final Condition equal(Field<T1> t1, Field<T2> t2, Field<T3> t3) {
      return this.compare(Comparator.EQUALS, t1, t2, t3);
   }

   public final Condition equal(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4) {
      return this.compare(Comparator.EQUALS, t1, t2, t3, t4);
   }

   public final Condition equal(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5) {
      return this.compare(Comparator.EQUALS, t1, t2, t3, t4, t5);
   }

   public final Condition equal(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6) {
      return this.compare(Comparator.EQUALS, t1, t2, t3, t4, t5, t6);
   }

   public final Condition equal(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7) {
      return this.compare(Comparator.EQUALS, t1, t2, t3, t4, t5, t6, t7);
   }

   public final Condition equal(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8) {
      return this.compare(Comparator.EQUALS, t1, t2, t3, t4, t5, t6, t7, t8);
   }

   public final Condition equal(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9) {
      return this.compare(Comparator.EQUALS, t1, t2, t3, t4, t5, t6, t7, t8, t9);
   }

   public final Condition equal(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10) {
      return this.compare(Comparator.EQUALS, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
   }

   public final Condition equal(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11) {
      return this.compare(Comparator.EQUALS, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11);
   }

   public final Condition equal(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12) {
      return this.compare(Comparator.EQUALS, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12);
   }

   public final Condition equal(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13) {
      return this.compare(Comparator.EQUALS, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13);
   }

   public final Condition equal(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14) {
      return this.compare(Comparator.EQUALS, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14);
   }

   public final Condition equal(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15) {
      return this.compare(Comparator.EQUALS, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15);
   }

   public final Condition equal(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16) {
      return this.compare(Comparator.EQUALS, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16);
   }

   public final Condition equal(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17) {
      return this.compare(Comparator.EQUALS, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17);
   }

   public final Condition equal(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18) {
      return this.compare(Comparator.EQUALS, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18);
   }

   public final Condition equal(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18, Field<T19> t19) {
      return this.compare(Comparator.EQUALS, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19);
   }

   public final Condition equal(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18, Field<T19> t19, Field<T20> t20) {
      return this.compare(Comparator.EQUALS, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20);
   }

   public final Condition equal(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18, Field<T19> t19, Field<T20> t20, Field<T21> t21) {
      return this.compare(Comparator.EQUALS, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21);
   }

   public final Condition equal(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18, Field<T19> t19, Field<T20> t20, Field<T21> t21, Field<T22> t22) {
      return this.compare(Comparator.EQUALS, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22);
   }

   public final Condition eq(RowN row) {
      return this.equal(row);
   }

   public final Condition eq(Row1<T1> row) {
      return this.equal(row);
   }

   public final Condition eq(Row2<T1, T2> row) {
      return this.equal(row);
   }

   public final Condition eq(Row3<T1, T2, T3> row) {
      return this.equal(row);
   }

   public final Condition eq(Row4<T1, T2, T3, T4> row) {
      return this.equal(row);
   }

   public final Condition eq(Row5<T1, T2, T3, T4, T5> row) {
      return this.equal(row);
   }

   public final Condition eq(Row6<T1, T2, T3, T4, T5, T6> row) {
      return this.equal(row);
   }

   public final Condition eq(Row7<T1, T2, T3, T4, T5, T6, T7> row) {
      return this.equal(row);
   }

   public final Condition eq(Row8<T1, T2, T3, T4, T5, T6, T7, T8> row) {
      return this.equal(row);
   }

   public final Condition eq(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> row) {
      return this.equal(row);
   }

   public final Condition eq(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> row) {
      return this.equal(row);
   }

   public final Condition eq(Row11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> row) {
      return this.equal(row);
   }

   public final Condition eq(Row12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> row) {
      return this.equal(row);
   }

   public final Condition eq(Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> row) {
      return this.equal(row);
   }

   public final Condition eq(Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> row) {
      return this.equal(row);
   }

   public final Condition eq(Row15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> row) {
      return this.equal(row);
   }

   public final Condition eq(Row16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> row) {
      return this.equal(row);
   }

   public final Condition eq(Row17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> row) {
      return this.equal(row);
   }

   public final Condition eq(Row18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> row) {
      return this.equal(row);
   }

   public final Condition eq(Row19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> row) {
      return this.equal(row);
   }

   public final Condition eq(Row20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> row) {
      return this.equal(row);
   }

   public final Condition eq(Row21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> row) {
      return this.equal(row);
   }

   public final Condition eq(Row22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> row) {
      return this.equal(row);
   }

   public final Condition eq(Record record) {
      return this.equal(record);
   }

   public final Condition eq(Record1<T1> record) {
      return this.equal(record);
   }

   public final Condition eq(Record2<T1, T2> record) {
      return this.equal(record);
   }

   public final Condition eq(Record3<T1, T2, T3> record) {
      return this.equal(record);
   }

   public final Condition eq(Record4<T1, T2, T3, T4> record) {
      return this.equal(record);
   }

   public final Condition eq(Record5<T1, T2, T3, T4, T5> record) {
      return this.equal(record);
   }

   public final Condition eq(Record6<T1, T2, T3, T4, T5, T6> record) {
      return this.equal(record);
   }

   public final Condition eq(Record7<T1, T2, T3, T4, T5, T6, T7> record) {
      return this.equal(record);
   }

   public final Condition eq(Record8<T1, T2, T3, T4, T5, T6, T7, T8> record) {
      return this.equal(record);
   }

   public final Condition eq(Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> record) {
      return this.equal(record);
   }

   public final Condition eq(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> record) {
      return this.equal(record);
   }

   public final Condition eq(Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> record) {
      return this.equal(record);
   }

   public final Condition eq(Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> record) {
      return this.equal(record);
   }

   public final Condition eq(Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> record) {
      return this.equal(record);
   }

   public final Condition eq(Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> record) {
      return this.equal(record);
   }

   public final Condition eq(Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> record) {
      return this.equal(record);
   }

   public final Condition eq(Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> record) {
      return this.equal(record);
   }

   public final Condition eq(Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> record) {
      return this.equal(record);
   }

   public final Condition eq(Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> record) {
      return this.equal(record);
   }

   public final Condition eq(Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> record) {
      return this.equal(record);
   }

   public final Condition eq(Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> record) {
      return this.equal(record);
   }

   public final Condition eq(Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> record) {
      return this.equal(record);
   }

   public final Condition eq(Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> record) {
      return this.equal(record);
   }

   public final Condition eq(Object... values) {
      return this.equal(values);
   }

   public final Condition eq(T1 t1) {
      return this.equal(t1);
   }

   public final Condition eq(T1 t1, T2 t2) {
      return this.equal(t1, t2);
   }

   public final Condition eq(T1 t1, T2 t2, T3 t3) {
      return this.equal(t1, t2, t3);
   }

   public final Condition eq(T1 t1, T2 t2, T3 t3, T4 t4) {
      return this.equal(t1, t2, t3, t4);
   }

   public final Condition eq(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5) {
      return this.equal(t1, t2, t3, t4, t5);
   }

   public final Condition eq(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6) {
      return this.equal(t1, t2, t3, t4, t5, t6);
   }

   public final Condition eq(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7) {
      return this.equal(t1, t2, t3, t4, t5, t6, t7);
   }

   public final Condition eq(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8) {
      return this.equal(t1, t2, t3, t4, t5, t6, t7, t8);
   }

   public final Condition eq(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9) {
      return this.equal(t1, t2, t3, t4, t5, t6, t7, t8, t9);
   }

   public final Condition eq(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10) {
      return this.equal(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
   }

   public final Condition eq(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11) {
      return this.equal(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11);
   }

   public final Condition eq(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12) {
      return this.equal(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12);
   }

   public final Condition eq(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13) {
      return this.equal(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13);
   }

   public final Condition eq(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14) {
      return this.equal(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14);
   }

   public final Condition eq(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15) {
      return this.equal(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15);
   }

   public final Condition eq(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16) {
      return this.equal(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16);
   }

   public final Condition eq(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17) {
      return this.equal(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17);
   }

   public final Condition eq(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18) {
      return this.equal(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18);
   }

   public final Condition eq(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19) {
      return this.equal(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19);
   }

   public final Condition eq(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19, T20 t20) {
      return this.equal(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20);
   }

   public final Condition eq(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19, T20 t20, T21 t21) {
      return this.equal(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21);
   }

   public final Condition eq(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19, T20 t20, T21 t21, T22 t22) {
      return this.equal(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22);
   }

   public final Condition eq(Field<?>... values) {
      return this.equal(values);
   }

   public final Condition eq(Field<T1> t1) {
      return this.equal(t1);
   }

   public final Condition eq(Field<T1> t1, Field<T2> t2) {
      return this.equal(t1, t2);
   }

   public final Condition eq(Field<T1> t1, Field<T2> t2, Field<T3> t3) {
      return this.equal(t1, t2, t3);
   }

   public final Condition eq(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4) {
      return this.equal(t1, t2, t3, t4);
   }

   public final Condition eq(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5) {
      return this.equal(t1, t2, t3, t4, t5);
   }

   public final Condition eq(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6) {
      return this.equal(t1, t2, t3, t4, t5, t6);
   }

   public final Condition eq(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7) {
      return this.equal(t1, t2, t3, t4, t5, t6, t7);
   }

   public final Condition eq(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8) {
      return this.equal(t1, t2, t3, t4, t5, t6, t7, t8);
   }

   public final Condition eq(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9) {
      return this.equal(t1, t2, t3, t4, t5, t6, t7, t8, t9);
   }

   public final Condition eq(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10) {
      return this.equal(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
   }

   public final Condition eq(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11) {
      return this.equal(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11);
   }

   public final Condition eq(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12) {
      return this.equal(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12);
   }

   public final Condition eq(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13) {
      return this.equal(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13);
   }

   public final Condition eq(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14) {
      return this.equal(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14);
   }

   public final Condition eq(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15) {
      return this.equal(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15);
   }

   public final Condition eq(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16) {
      return this.equal(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16);
   }

   public final Condition eq(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17) {
      return this.equal(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17);
   }

   public final Condition eq(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18) {
      return this.equal(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18);
   }

   public final Condition eq(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18, Field<T19> t19) {
      return this.equal(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19);
   }

   public final Condition eq(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18, Field<T19> t19, Field<T20> t20) {
      return this.equal(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20);
   }

   public final Condition eq(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18, Field<T19> t19, Field<T20> t20, Field<T21> t21) {
      return this.equal(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21);
   }

   public final Condition eq(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18, Field<T19> t19, Field<T20> t20, Field<T21> t21, Field<T22> t22) {
      return this.equal(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22);
   }

   public final Condition notEqual(RowN row) {
      return this.compare(Comparator.NOT_EQUALS, row);
   }

   public final Condition notEqual(Row1<T1> row) {
      return this.compare(Comparator.NOT_EQUALS, row);
   }

   public final Condition notEqual(Row2<T1, T2> row) {
      return this.compare(Comparator.NOT_EQUALS, row);
   }

   public final Condition notEqual(Row3<T1, T2, T3> row) {
      return this.compare(Comparator.NOT_EQUALS, row);
   }

   public final Condition notEqual(Row4<T1, T2, T3, T4> row) {
      return this.compare(Comparator.NOT_EQUALS, row);
   }

   public final Condition notEqual(Row5<T1, T2, T3, T4, T5> row) {
      return this.compare(Comparator.NOT_EQUALS, row);
   }

   public final Condition notEqual(Row6<T1, T2, T3, T4, T5, T6> row) {
      return this.compare(Comparator.NOT_EQUALS, row);
   }

   public final Condition notEqual(Row7<T1, T2, T3, T4, T5, T6, T7> row) {
      return this.compare(Comparator.NOT_EQUALS, row);
   }

   public final Condition notEqual(Row8<T1, T2, T3, T4, T5, T6, T7, T8> row) {
      return this.compare(Comparator.NOT_EQUALS, row);
   }

   public final Condition notEqual(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> row) {
      return this.compare(Comparator.NOT_EQUALS, row);
   }

   public final Condition notEqual(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> row) {
      return this.compare(Comparator.NOT_EQUALS, row);
   }

   public final Condition notEqual(Row11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> row) {
      return this.compare(Comparator.NOT_EQUALS, row);
   }

   public final Condition notEqual(Row12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> row) {
      return this.compare(Comparator.NOT_EQUALS, row);
   }

   public final Condition notEqual(Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> row) {
      return this.compare(Comparator.NOT_EQUALS, row);
   }

   public final Condition notEqual(Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> row) {
      return this.compare(Comparator.NOT_EQUALS, row);
   }

   public final Condition notEqual(Row15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> row) {
      return this.compare(Comparator.NOT_EQUALS, row);
   }

   public final Condition notEqual(Row16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> row) {
      return this.compare(Comparator.NOT_EQUALS, row);
   }

   public final Condition notEqual(Row17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> row) {
      return this.compare(Comparator.NOT_EQUALS, row);
   }

   public final Condition notEqual(Row18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> row) {
      return this.compare(Comparator.NOT_EQUALS, row);
   }

   public final Condition notEqual(Row19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> row) {
      return this.compare(Comparator.NOT_EQUALS, row);
   }

   public final Condition notEqual(Row20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> row) {
      return this.compare(Comparator.NOT_EQUALS, row);
   }

   public final Condition notEqual(Row21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> row) {
      return this.compare(Comparator.NOT_EQUALS, row);
   }

   public final Condition notEqual(Row22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> row) {
      return this.compare(Comparator.NOT_EQUALS, row);
   }

   public final Condition notEqual(Record record) {
      return this.compare(Comparator.NOT_EQUALS, record);
   }

   public final Condition notEqual(Record1<T1> record) {
      return this.compare(Comparator.NOT_EQUALS, record);
   }

   public final Condition notEqual(Record2<T1, T2> record) {
      return this.compare(Comparator.NOT_EQUALS, record);
   }

   public final Condition notEqual(Record3<T1, T2, T3> record) {
      return this.compare(Comparator.NOT_EQUALS, record);
   }

   public final Condition notEqual(Record4<T1, T2, T3, T4> record) {
      return this.compare(Comparator.NOT_EQUALS, record);
   }

   public final Condition notEqual(Record5<T1, T2, T3, T4, T5> record) {
      return this.compare(Comparator.NOT_EQUALS, record);
   }

   public final Condition notEqual(Record6<T1, T2, T3, T4, T5, T6> record) {
      return this.compare(Comparator.NOT_EQUALS, record);
   }

   public final Condition notEqual(Record7<T1, T2, T3, T4, T5, T6, T7> record) {
      return this.compare(Comparator.NOT_EQUALS, record);
   }

   public final Condition notEqual(Record8<T1, T2, T3, T4, T5, T6, T7, T8> record) {
      return this.compare(Comparator.NOT_EQUALS, record);
   }

   public final Condition notEqual(Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> record) {
      return this.compare(Comparator.NOT_EQUALS, record);
   }

   public final Condition notEqual(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> record) {
      return this.compare(Comparator.NOT_EQUALS, record);
   }

   public final Condition notEqual(Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> record) {
      return this.compare(Comparator.NOT_EQUALS, record);
   }

   public final Condition notEqual(Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> record) {
      return this.compare(Comparator.NOT_EQUALS, record);
   }

   public final Condition notEqual(Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> record) {
      return this.compare(Comparator.NOT_EQUALS, record);
   }

   public final Condition notEqual(Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> record) {
      return this.compare(Comparator.NOT_EQUALS, record);
   }

   public final Condition notEqual(Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> record) {
      return this.compare(Comparator.NOT_EQUALS, record);
   }

   public final Condition notEqual(Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> record) {
      return this.compare(Comparator.NOT_EQUALS, record);
   }

   public final Condition notEqual(Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> record) {
      return this.compare(Comparator.NOT_EQUALS, record);
   }

   public final Condition notEqual(Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> record) {
      return this.compare(Comparator.NOT_EQUALS, record);
   }

   public final Condition notEqual(Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> record) {
      return this.compare(Comparator.NOT_EQUALS, record);
   }

   public final Condition notEqual(Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> record) {
      return this.compare(Comparator.NOT_EQUALS, record);
   }

   public final Condition notEqual(Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> record) {
      return this.compare(Comparator.NOT_EQUALS, record);
   }

   public final Condition notEqual(Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> record) {
      return this.compare(Comparator.NOT_EQUALS, record);
   }

   public final Condition notEqual(Object... values) {
      return this.compare(Comparator.NOT_EQUALS, values);
   }

   public final Condition notEqual(T1 t1) {
      return this.compare(Comparator.NOT_EQUALS, t1);
   }

   public final Condition notEqual(T1 t1, T2 t2) {
      return this.compare(Comparator.NOT_EQUALS, t1, t2);
   }

   public final Condition notEqual(T1 t1, T2 t2, T3 t3) {
      return this.compare(Comparator.NOT_EQUALS, t1, t2, t3);
   }

   public final Condition notEqual(T1 t1, T2 t2, T3 t3, T4 t4) {
      return this.compare(Comparator.NOT_EQUALS, t1, t2, t3, t4);
   }

   public final Condition notEqual(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5) {
      return this.compare(Comparator.NOT_EQUALS, t1, t2, t3, t4, t5);
   }

   public final Condition notEqual(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6) {
      return this.compare(Comparator.NOT_EQUALS, t1, t2, t3, t4, t5, t6);
   }

   public final Condition notEqual(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7) {
      return this.compare(Comparator.NOT_EQUALS, t1, t2, t3, t4, t5, t6, t7);
   }

   public final Condition notEqual(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8) {
      return this.compare(Comparator.NOT_EQUALS, t1, t2, t3, t4, t5, t6, t7, t8);
   }

   public final Condition notEqual(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9) {
      return this.compare(Comparator.NOT_EQUALS, t1, t2, t3, t4, t5, t6, t7, t8, t9);
   }

   public final Condition notEqual(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10) {
      return this.compare(Comparator.NOT_EQUALS, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
   }

   public final Condition notEqual(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11) {
      return this.compare(Comparator.NOT_EQUALS, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11);
   }

   public final Condition notEqual(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12) {
      return this.compare(Comparator.NOT_EQUALS, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12);
   }

   public final Condition notEqual(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13) {
      return this.compare(Comparator.NOT_EQUALS, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13);
   }

   public final Condition notEqual(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14) {
      return this.compare(Comparator.NOT_EQUALS, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14);
   }

   public final Condition notEqual(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15) {
      return this.compare(Comparator.NOT_EQUALS, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15);
   }

   public final Condition notEqual(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16) {
      return this.compare(Comparator.NOT_EQUALS, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16);
   }

   public final Condition notEqual(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17) {
      return this.compare(Comparator.NOT_EQUALS, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17);
   }

   public final Condition notEqual(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18) {
      return this.compare(Comparator.NOT_EQUALS, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18);
   }

   public final Condition notEqual(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19) {
      return this.compare(Comparator.NOT_EQUALS, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19);
   }

   public final Condition notEqual(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19, T20 t20) {
      return this.compare(Comparator.NOT_EQUALS, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20);
   }

   public final Condition notEqual(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19, T20 t20, T21 t21) {
      return this.compare(Comparator.NOT_EQUALS, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21);
   }

   public final Condition notEqual(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19, T20 t20, T21 t21, T22 t22) {
      return this.compare(Comparator.NOT_EQUALS, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22);
   }

   public final Condition notEqual(Field<?>... values) {
      return this.compare(Comparator.NOT_EQUALS, values);
   }

   public final Condition notEqual(Field<T1> t1) {
      return this.compare(Comparator.NOT_EQUALS, t1);
   }

   public final Condition notEqual(Field<T1> t1, Field<T2> t2) {
      return this.compare(Comparator.NOT_EQUALS, t1, t2);
   }

   public final Condition notEqual(Field<T1> t1, Field<T2> t2, Field<T3> t3) {
      return this.compare(Comparator.NOT_EQUALS, t1, t2, t3);
   }

   public final Condition notEqual(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4) {
      return this.compare(Comparator.NOT_EQUALS, t1, t2, t3, t4);
   }

   public final Condition notEqual(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5) {
      return this.compare(Comparator.NOT_EQUALS, t1, t2, t3, t4, t5);
   }

   public final Condition notEqual(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6) {
      return this.compare(Comparator.NOT_EQUALS, t1, t2, t3, t4, t5, t6);
   }

   public final Condition notEqual(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7) {
      return this.compare(Comparator.NOT_EQUALS, t1, t2, t3, t4, t5, t6, t7);
   }

   public final Condition notEqual(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8) {
      return this.compare(Comparator.NOT_EQUALS, t1, t2, t3, t4, t5, t6, t7, t8);
   }

   public final Condition notEqual(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9) {
      return this.compare(Comparator.NOT_EQUALS, t1, t2, t3, t4, t5, t6, t7, t8, t9);
   }

   public final Condition notEqual(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10) {
      return this.compare(Comparator.NOT_EQUALS, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
   }

   public final Condition notEqual(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11) {
      return this.compare(Comparator.NOT_EQUALS, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11);
   }

   public final Condition notEqual(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12) {
      return this.compare(Comparator.NOT_EQUALS, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12);
   }

   public final Condition notEqual(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13) {
      return this.compare(Comparator.NOT_EQUALS, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13);
   }

   public final Condition notEqual(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14) {
      return this.compare(Comparator.NOT_EQUALS, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14);
   }

   public final Condition notEqual(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15) {
      return this.compare(Comparator.NOT_EQUALS, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15);
   }

   public final Condition notEqual(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16) {
      return this.compare(Comparator.NOT_EQUALS, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16);
   }

   public final Condition notEqual(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17) {
      return this.compare(Comparator.NOT_EQUALS, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17);
   }

   public final Condition notEqual(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18) {
      return this.compare(Comparator.NOT_EQUALS, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18);
   }

   public final Condition notEqual(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18, Field<T19> t19) {
      return this.compare(Comparator.NOT_EQUALS, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19);
   }

   public final Condition notEqual(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18, Field<T19> t19, Field<T20> t20) {
      return this.compare(Comparator.NOT_EQUALS, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20);
   }

   public final Condition notEqual(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18, Field<T19> t19, Field<T20> t20, Field<T21> t21) {
      return this.compare(Comparator.NOT_EQUALS, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21);
   }

   public final Condition notEqual(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18, Field<T19> t19, Field<T20> t20, Field<T21> t21, Field<T22> t22) {
      return this.compare(Comparator.NOT_EQUALS, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22);
   }

   public final Condition ne(RowN row) {
      return this.notEqual(row);
   }

   public final Condition ne(Row1<T1> row) {
      return this.notEqual(row);
   }

   public final Condition ne(Row2<T1, T2> row) {
      return this.notEqual(row);
   }

   public final Condition ne(Row3<T1, T2, T3> row) {
      return this.notEqual(row);
   }

   public final Condition ne(Row4<T1, T2, T3, T4> row) {
      return this.notEqual(row);
   }

   public final Condition ne(Row5<T1, T2, T3, T4, T5> row) {
      return this.notEqual(row);
   }

   public final Condition ne(Row6<T1, T2, T3, T4, T5, T6> row) {
      return this.notEqual(row);
   }

   public final Condition ne(Row7<T1, T2, T3, T4, T5, T6, T7> row) {
      return this.notEqual(row);
   }

   public final Condition ne(Row8<T1, T2, T3, T4, T5, T6, T7, T8> row) {
      return this.notEqual(row);
   }

   public final Condition ne(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> row) {
      return this.notEqual(row);
   }

   public final Condition ne(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> row) {
      return this.notEqual(row);
   }

   public final Condition ne(Row11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> row) {
      return this.notEqual(row);
   }

   public final Condition ne(Row12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> row) {
      return this.notEqual(row);
   }

   public final Condition ne(Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> row) {
      return this.notEqual(row);
   }

   public final Condition ne(Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> row) {
      return this.notEqual(row);
   }

   public final Condition ne(Row15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> row) {
      return this.notEqual(row);
   }

   public final Condition ne(Row16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> row) {
      return this.notEqual(row);
   }

   public final Condition ne(Row17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> row) {
      return this.notEqual(row);
   }

   public final Condition ne(Row18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> row) {
      return this.notEqual(row);
   }

   public final Condition ne(Row19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> row) {
      return this.notEqual(row);
   }

   public final Condition ne(Row20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> row) {
      return this.notEqual(row);
   }

   public final Condition ne(Row21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> row) {
      return this.notEqual(row);
   }

   public final Condition ne(Row22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> row) {
      return this.notEqual(row);
   }

   public final Condition ne(Record record) {
      return this.notEqual(record);
   }

   public final Condition ne(Record1<T1> record) {
      return this.notEqual(record);
   }

   public final Condition ne(Record2<T1, T2> record) {
      return this.notEqual(record);
   }

   public final Condition ne(Record3<T1, T2, T3> record) {
      return this.notEqual(record);
   }

   public final Condition ne(Record4<T1, T2, T3, T4> record) {
      return this.notEqual(record);
   }

   public final Condition ne(Record5<T1, T2, T3, T4, T5> record) {
      return this.notEqual(record);
   }

   public final Condition ne(Record6<T1, T2, T3, T4, T5, T6> record) {
      return this.notEqual(record);
   }

   public final Condition ne(Record7<T1, T2, T3, T4, T5, T6, T7> record) {
      return this.notEqual(record);
   }

   public final Condition ne(Record8<T1, T2, T3, T4, T5, T6, T7, T8> record) {
      return this.notEqual(record);
   }

   public final Condition ne(Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> record) {
      return this.notEqual(record);
   }

   public final Condition ne(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> record) {
      return this.notEqual(record);
   }

   public final Condition ne(Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> record) {
      return this.notEqual(record);
   }

   public final Condition ne(Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> record) {
      return this.notEqual(record);
   }

   public final Condition ne(Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> record) {
      return this.notEqual(record);
   }

   public final Condition ne(Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> record) {
      return this.notEqual(record);
   }

   public final Condition ne(Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> record) {
      return this.notEqual(record);
   }

   public final Condition ne(Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> record) {
      return this.notEqual(record);
   }

   public final Condition ne(Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> record) {
      return this.notEqual(record);
   }

   public final Condition ne(Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> record) {
      return this.notEqual(record);
   }

   public final Condition ne(Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> record) {
      return this.notEqual(record);
   }

   public final Condition ne(Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> record) {
      return this.notEqual(record);
   }

   public final Condition ne(Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> record) {
      return this.notEqual(record);
   }

   public final Condition ne(Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> record) {
      return this.notEqual(record);
   }

   public final Condition ne(Object... values) {
      return this.notEqual(values);
   }

   public final Condition ne(T1 t1) {
      return this.notEqual(t1);
   }

   public final Condition ne(T1 t1, T2 t2) {
      return this.notEqual(t1, t2);
   }

   public final Condition ne(T1 t1, T2 t2, T3 t3) {
      return this.notEqual(t1, t2, t3);
   }

   public final Condition ne(T1 t1, T2 t2, T3 t3, T4 t4) {
      return this.notEqual(t1, t2, t3, t4);
   }

   public final Condition ne(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5) {
      return this.notEqual(t1, t2, t3, t4, t5);
   }

   public final Condition ne(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6) {
      return this.notEqual(t1, t2, t3, t4, t5, t6);
   }

   public final Condition ne(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7) {
      return this.notEqual(t1, t2, t3, t4, t5, t6, t7);
   }

   public final Condition ne(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8) {
      return this.notEqual(t1, t2, t3, t4, t5, t6, t7, t8);
   }

   public final Condition ne(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9) {
      return this.notEqual(t1, t2, t3, t4, t5, t6, t7, t8, t9);
   }

   public final Condition ne(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10) {
      return this.notEqual(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
   }

   public final Condition ne(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11) {
      return this.notEqual(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11);
   }

   public final Condition ne(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12) {
      return this.notEqual(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12);
   }

   public final Condition ne(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13) {
      return this.notEqual(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13);
   }

   public final Condition ne(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14) {
      return this.notEqual(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14);
   }

   public final Condition ne(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15) {
      return this.notEqual(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15);
   }

   public final Condition ne(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16) {
      return this.notEqual(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16);
   }

   public final Condition ne(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17) {
      return this.notEqual(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17);
   }

   public final Condition ne(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18) {
      return this.notEqual(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18);
   }

   public final Condition ne(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19) {
      return this.notEqual(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19);
   }

   public final Condition ne(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19, T20 t20) {
      return this.notEqual(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20);
   }

   public final Condition ne(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19, T20 t20, T21 t21) {
      return this.notEqual(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21);
   }

   public final Condition ne(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19, T20 t20, T21 t21, T22 t22) {
      return this.notEqual(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22);
   }

   public final Condition ne(Field<?>... values) {
      return this.notEqual(values);
   }

   public final Condition ne(Field<T1> t1) {
      return this.notEqual(t1);
   }

   public final Condition ne(Field<T1> t1, Field<T2> t2) {
      return this.notEqual(t1, t2);
   }

   public final Condition ne(Field<T1> t1, Field<T2> t2, Field<T3> t3) {
      return this.notEqual(t1, t2, t3);
   }

   public final Condition ne(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4) {
      return this.notEqual(t1, t2, t3, t4);
   }

   public final Condition ne(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5) {
      return this.notEqual(t1, t2, t3, t4, t5);
   }

   public final Condition ne(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6) {
      return this.notEqual(t1, t2, t3, t4, t5, t6);
   }

   public final Condition ne(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7) {
      return this.notEqual(t1, t2, t3, t4, t5, t6, t7);
   }

   public final Condition ne(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8) {
      return this.notEqual(t1, t2, t3, t4, t5, t6, t7, t8);
   }

   public final Condition ne(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9) {
      return this.notEqual(t1, t2, t3, t4, t5, t6, t7, t8, t9);
   }

   public final Condition ne(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10) {
      return this.notEqual(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
   }

   public final Condition ne(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11) {
      return this.notEqual(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11);
   }

   public final Condition ne(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12) {
      return this.notEqual(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12);
   }

   public final Condition ne(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13) {
      return this.notEqual(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13);
   }

   public final Condition ne(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14) {
      return this.notEqual(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14);
   }

   public final Condition ne(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15) {
      return this.notEqual(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15);
   }

   public final Condition ne(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16) {
      return this.notEqual(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16);
   }

   public final Condition ne(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17) {
      return this.notEqual(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17);
   }

   public final Condition ne(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18) {
      return this.notEqual(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18);
   }

   public final Condition ne(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18, Field<T19> t19) {
      return this.notEqual(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19);
   }

   public final Condition ne(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18, Field<T19> t19, Field<T20> t20) {
      return this.notEqual(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20);
   }

   public final Condition ne(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18, Field<T19> t19, Field<T20> t20, Field<T21> t21) {
      return this.notEqual(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21);
   }

   public final Condition ne(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18, Field<T19> t19, Field<T20> t20, Field<T21> t21, Field<T22> t22) {
      return this.notEqual(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22);
   }

   public final Condition lessThan(RowN row) {
      return this.compare(Comparator.LESS, row);
   }

   public final Condition lessThan(Row1<T1> row) {
      return this.compare(Comparator.LESS, row);
   }

   public final Condition lessThan(Row2<T1, T2> row) {
      return this.compare(Comparator.LESS, row);
   }

   public final Condition lessThan(Row3<T1, T2, T3> row) {
      return this.compare(Comparator.LESS, row);
   }

   public final Condition lessThan(Row4<T1, T2, T3, T4> row) {
      return this.compare(Comparator.LESS, row);
   }

   public final Condition lessThan(Row5<T1, T2, T3, T4, T5> row) {
      return this.compare(Comparator.LESS, row);
   }

   public final Condition lessThan(Row6<T1, T2, T3, T4, T5, T6> row) {
      return this.compare(Comparator.LESS, row);
   }

   public final Condition lessThan(Row7<T1, T2, T3, T4, T5, T6, T7> row) {
      return this.compare(Comparator.LESS, row);
   }

   public final Condition lessThan(Row8<T1, T2, T3, T4, T5, T6, T7, T8> row) {
      return this.compare(Comparator.LESS, row);
   }

   public final Condition lessThan(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> row) {
      return this.compare(Comparator.LESS, row);
   }

   public final Condition lessThan(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> row) {
      return this.compare(Comparator.LESS, row);
   }

   public final Condition lessThan(Row11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> row) {
      return this.compare(Comparator.LESS, row);
   }

   public final Condition lessThan(Row12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> row) {
      return this.compare(Comparator.LESS, row);
   }

   public final Condition lessThan(Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> row) {
      return this.compare(Comparator.LESS, row);
   }

   public final Condition lessThan(Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> row) {
      return this.compare(Comparator.LESS, row);
   }

   public final Condition lessThan(Row15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> row) {
      return this.compare(Comparator.LESS, row);
   }

   public final Condition lessThan(Row16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> row) {
      return this.compare(Comparator.LESS, row);
   }

   public final Condition lessThan(Row17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> row) {
      return this.compare(Comparator.LESS, row);
   }

   public final Condition lessThan(Row18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> row) {
      return this.compare(Comparator.LESS, row);
   }

   public final Condition lessThan(Row19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> row) {
      return this.compare(Comparator.LESS, row);
   }

   public final Condition lessThan(Row20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> row) {
      return this.compare(Comparator.LESS, row);
   }

   public final Condition lessThan(Row21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> row) {
      return this.compare(Comparator.LESS, row);
   }

   public final Condition lessThan(Row22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> row) {
      return this.compare(Comparator.LESS, row);
   }

   public final Condition lessThan(Record record) {
      return this.compare(Comparator.LESS, record);
   }

   public final Condition lessThan(Record1<T1> record) {
      return this.compare(Comparator.LESS, record);
   }

   public final Condition lessThan(Record2<T1, T2> record) {
      return this.compare(Comparator.LESS, record);
   }

   public final Condition lessThan(Record3<T1, T2, T3> record) {
      return this.compare(Comparator.LESS, record);
   }

   public final Condition lessThan(Record4<T1, T2, T3, T4> record) {
      return this.compare(Comparator.LESS, record);
   }

   public final Condition lessThan(Record5<T1, T2, T3, T4, T5> record) {
      return this.compare(Comparator.LESS, record);
   }

   public final Condition lessThan(Record6<T1, T2, T3, T4, T5, T6> record) {
      return this.compare(Comparator.LESS, record);
   }

   public final Condition lessThan(Record7<T1, T2, T3, T4, T5, T6, T7> record) {
      return this.compare(Comparator.LESS, record);
   }

   public final Condition lessThan(Record8<T1, T2, T3, T4, T5, T6, T7, T8> record) {
      return this.compare(Comparator.LESS, record);
   }

   public final Condition lessThan(Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> record) {
      return this.compare(Comparator.LESS, record);
   }

   public final Condition lessThan(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> record) {
      return this.compare(Comparator.LESS, record);
   }

   public final Condition lessThan(Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> record) {
      return this.compare(Comparator.LESS, record);
   }

   public final Condition lessThan(Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> record) {
      return this.compare(Comparator.LESS, record);
   }

   public final Condition lessThan(Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> record) {
      return this.compare(Comparator.LESS, record);
   }

   public final Condition lessThan(Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> record) {
      return this.compare(Comparator.LESS, record);
   }

   public final Condition lessThan(Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> record) {
      return this.compare(Comparator.LESS, record);
   }

   public final Condition lessThan(Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> record) {
      return this.compare(Comparator.LESS, record);
   }

   public final Condition lessThan(Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> record) {
      return this.compare(Comparator.LESS, record);
   }

   public final Condition lessThan(Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> record) {
      return this.compare(Comparator.LESS, record);
   }

   public final Condition lessThan(Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> record) {
      return this.compare(Comparator.LESS, record);
   }

   public final Condition lessThan(Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> record) {
      return this.compare(Comparator.LESS, record);
   }

   public final Condition lessThan(Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> record) {
      return this.compare(Comparator.LESS, record);
   }

   public final Condition lessThan(Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> record) {
      return this.compare(Comparator.LESS, record);
   }

   public final Condition lessThan(Object... values) {
      return this.compare(Comparator.LESS, values);
   }

   public final Condition lessThan(T1 t1) {
      return this.compare(Comparator.LESS, t1);
   }

   public final Condition lessThan(T1 t1, T2 t2) {
      return this.compare(Comparator.LESS, t1, t2);
   }

   public final Condition lessThan(T1 t1, T2 t2, T3 t3) {
      return this.compare(Comparator.LESS, t1, t2, t3);
   }

   public final Condition lessThan(T1 t1, T2 t2, T3 t3, T4 t4) {
      return this.compare(Comparator.LESS, t1, t2, t3, t4);
   }

   public final Condition lessThan(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5) {
      return this.compare(Comparator.LESS, t1, t2, t3, t4, t5);
   }

   public final Condition lessThan(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6) {
      return this.compare(Comparator.LESS, t1, t2, t3, t4, t5, t6);
   }

   public final Condition lessThan(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7) {
      return this.compare(Comparator.LESS, t1, t2, t3, t4, t5, t6, t7);
   }

   public final Condition lessThan(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8) {
      return this.compare(Comparator.LESS, t1, t2, t3, t4, t5, t6, t7, t8);
   }

   public final Condition lessThan(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9) {
      return this.compare(Comparator.LESS, t1, t2, t3, t4, t5, t6, t7, t8, t9);
   }

   public final Condition lessThan(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10) {
      return this.compare(Comparator.LESS, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
   }

   public final Condition lessThan(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11) {
      return this.compare(Comparator.LESS, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11);
   }

   public final Condition lessThan(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12) {
      return this.compare(Comparator.LESS, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12);
   }

   public final Condition lessThan(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13) {
      return this.compare(Comparator.LESS, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13);
   }

   public final Condition lessThan(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14) {
      return this.compare(Comparator.LESS, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14);
   }

   public final Condition lessThan(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15) {
      return this.compare(Comparator.LESS, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15);
   }

   public final Condition lessThan(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16) {
      return this.compare(Comparator.LESS, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16);
   }

   public final Condition lessThan(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17) {
      return this.compare(Comparator.LESS, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17);
   }

   public final Condition lessThan(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18) {
      return this.compare(Comparator.LESS, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18);
   }

   public final Condition lessThan(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19) {
      return this.compare(Comparator.LESS, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19);
   }

   public final Condition lessThan(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19, T20 t20) {
      return this.compare(Comparator.LESS, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20);
   }

   public final Condition lessThan(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19, T20 t20, T21 t21) {
      return this.compare(Comparator.LESS, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21);
   }

   public final Condition lessThan(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19, T20 t20, T21 t21, T22 t22) {
      return this.compare(Comparator.LESS, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22);
   }

   public final Condition lessThan(Field<?>... values) {
      return this.compare(Comparator.LESS, values);
   }

   public final Condition lessThan(Field<T1> t1) {
      return this.compare(Comparator.LESS, t1);
   }

   public final Condition lessThan(Field<T1> t1, Field<T2> t2) {
      return this.compare(Comparator.LESS, t1, t2);
   }

   public final Condition lessThan(Field<T1> t1, Field<T2> t2, Field<T3> t3) {
      return this.compare(Comparator.LESS, t1, t2, t3);
   }

   public final Condition lessThan(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4) {
      return this.compare(Comparator.LESS, t1, t2, t3, t4);
   }

   public final Condition lessThan(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5) {
      return this.compare(Comparator.LESS, t1, t2, t3, t4, t5);
   }

   public final Condition lessThan(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6) {
      return this.compare(Comparator.LESS, t1, t2, t3, t4, t5, t6);
   }

   public final Condition lessThan(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7) {
      return this.compare(Comparator.LESS, t1, t2, t3, t4, t5, t6, t7);
   }

   public final Condition lessThan(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8) {
      return this.compare(Comparator.LESS, t1, t2, t3, t4, t5, t6, t7, t8);
   }

   public final Condition lessThan(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9) {
      return this.compare(Comparator.LESS, t1, t2, t3, t4, t5, t6, t7, t8, t9);
   }

   public final Condition lessThan(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10) {
      return this.compare(Comparator.LESS, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
   }

   public final Condition lessThan(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11) {
      return this.compare(Comparator.LESS, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11);
   }

   public final Condition lessThan(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12) {
      return this.compare(Comparator.LESS, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12);
   }

   public final Condition lessThan(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13) {
      return this.compare(Comparator.LESS, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13);
   }

   public final Condition lessThan(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14) {
      return this.compare(Comparator.LESS, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14);
   }

   public final Condition lessThan(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15) {
      return this.compare(Comparator.LESS, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15);
   }

   public final Condition lessThan(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16) {
      return this.compare(Comparator.LESS, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16);
   }

   public final Condition lessThan(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17) {
      return this.compare(Comparator.LESS, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17);
   }

   public final Condition lessThan(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18) {
      return this.compare(Comparator.LESS, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18);
   }

   public final Condition lessThan(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18, Field<T19> t19) {
      return this.compare(Comparator.LESS, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19);
   }

   public final Condition lessThan(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18, Field<T19> t19, Field<T20> t20) {
      return this.compare(Comparator.LESS, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20);
   }

   public final Condition lessThan(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18, Field<T19> t19, Field<T20> t20, Field<T21> t21) {
      return this.compare(Comparator.LESS, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21);
   }

   public final Condition lessThan(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18, Field<T19> t19, Field<T20> t20, Field<T21> t21, Field<T22> t22) {
      return this.compare(Comparator.LESS, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22);
   }

   public final Condition lt(RowN row) {
      return this.lessThan(row);
   }

   public final Condition lt(Row1<T1> row) {
      return this.lessThan(row);
   }

   public final Condition lt(Row2<T1, T2> row) {
      return this.lessThan(row);
   }

   public final Condition lt(Row3<T1, T2, T3> row) {
      return this.lessThan(row);
   }

   public final Condition lt(Row4<T1, T2, T3, T4> row) {
      return this.lessThan(row);
   }

   public final Condition lt(Row5<T1, T2, T3, T4, T5> row) {
      return this.lessThan(row);
   }

   public final Condition lt(Row6<T1, T2, T3, T4, T5, T6> row) {
      return this.lessThan(row);
   }

   public final Condition lt(Row7<T1, T2, T3, T4, T5, T6, T7> row) {
      return this.lessThan(row);
   }

   public final Condition lt(Row8<T1, T2, T3, T4, T5, T6, T7, T8> row) {
      return this.lessThan(row);
   }

   public final Condition lt(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> row) {
      return this.lessThan(row);
   }

   public final Condition lt(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> row) {
      return this.lessThan(row);
   }

   public final Condition lt(Row11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> row) {
      return this.lessThan(row);
   }

   public final Condition lt(Row12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> row) {
      return this.lessThan(row);
   }

   public final Condition lt(Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> row) {
      return this.lessThan(row);
   }

   public final Condition lt(Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> row) {
      return this.lessThan(row);
   }

   public final Condition lt(Row15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> row) {
      return this.lessThan(row);
   }

   public final Condition lt(Row16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> row) {
      return this.lessThan(row);
   }

   public final Condition lt(Row17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> row) {
      return this.lessThan(row);
   }

   public final Condition lt(Row18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> row) {
      return this.lessThan(row);
   }

   public final Condition lt(Row19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> row) {
      return this.lessThan(row);
   }

   public final Condition lt(Row20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> row) {
      return this.lessThan(row);
   }

   public final Condition lt(Row21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> row) {
      return this.lessThan(row);
   }

   public final Condition lt(Row22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> row) {
      return this.lessThan(row);
   }

   public final Condition lt(Record record) {
      return this.lessThan(record);
   }

   public final Condition lt(Record1<T1> record) {
      return this.lessThan(record);
   }

   public final Condition lt(Record2<T1, T2> record) {
      return this.lessThan(record);
   }

   public final Condition lt(Record3<T1, T2, T3> record) {
      return this.lessThan(record);
   }

   public final Condition lt(Record4<T1, T2, T3, T4> record) {
      return this.lessThan(record);
   }

   public final Condition lt(Record5<T1, T2, T3, T4, T5> record) {
      return this.lessThan(record);
   }

   public final Condition lt(Record6<T1, T2, T3, T4, T5, T6> record) {
      return this.lessThan(record);
   }

   public final Condition lt(Record7<T1, T2, T3, T4, T5, T6, T7> record) {
      return this.lessThan(record);
   }

   public final Condition lt(Record8<T1, T2, T3, T4, T5, T6, T7, T8> record) {
      return this.lessThan(record);
   }

   public final Condition lt(Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> record) {
      return this.lessThan(record);
   }

   public final Condition lt(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> record) {
      return this.lessThan(record);
   }

   public final Condition lt(Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> record) {
      return this.lessThan(record);
   }

   public final Condition lt(Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> record) {
      return this.lessThan(record);
   }

   public final Condition lt(Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> record) {
      return this.lessThan(record);
   }

   public final Condition lt(Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> record) {
      return this.lessThan(record);
   }

   public final Condition lt(Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> record) {
      return this.lessThan(record);
   }

   public final Condition lt(Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> record) {
      return this.lessThan(record);
   }

   public final Condition lt(Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> record) {
      return this.lessThan(record);
   }

   public final Condition lt(Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> record) {
      return this.lessThan(record);
   }

   public final Condition lt(Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> record) {
      return this.lessThan(record);
   }

   public final Condition lt(Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> record) {
      return this.lessThan(record);
   }

   public final Condition lt(Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> record) {
      return this.lessThan(record);
   }

   public final Condition lt(Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> record) {
      return this.lessThan(record);
   }

   public final Condition lt(Object... values) {
      return this.lessThan(values);
   }

   public final Condition lt(T1 t1) {
      return this.lessThan(t1);
   }

   public final Condition lt(T1 t1, T2 t2) {
      return this.lessThan(t1, t2);
   }

   public final Condition lt(T1 t1, T2 t2, T3 t3) {
      return this.lessThan(t1, t2, t3);
   }

   public final Condition lt(T1 t1, T2 t2, T3 t3, T4 t4) {
      return this.lessThan(t1, t2, t3, t4);
   }

   public final Condition lt(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5) {
      return this.lessThan(t1, t2, t3, t4, t5);
   }

   public final Condition lt(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6) {
      return this.lessThan(t1, t2, t3, t4, t5, t6);
   }

   public final Condition lt(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7) {
      return this.lessThan(t1, t2, t3, t4, t5, t6, t7);
   }

   public final Condition lt(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8) {
      return this.lessThan(t1, t2, t3, t4, t5, t6, t7, t8);
   }

   public final Condition lt(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9) {
      return this.lessThan(t1, t2, t3, t4, t5, t6, t7, t8, t9);
   }

   public final Condition lt(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10) {
      return this.lessThan(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
   }

   public final Condition lt(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11) {
      return this.lessThan(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11);
   }

   public final Condition lt(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12) {
      return this.lessThan(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12);
   }

   public final Condition lt(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13) {
      return this.lessThan(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13);
   }

   public final Condition lt(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14) {
      return this.lessThan(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14);
   }

   public final Condition lt(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15) {
      return this.lessThan(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15);
   }

   public final Condition lt(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16) {
      return this.lessThan(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16);
   }

   public final Condition lt(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17) {
      return this.lessThan(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17);
   }

   public final Condition lt(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18) {
      return this.lessThan(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18);
   }

   public final Condition lt(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19) {
      return this.lessThan(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19);
   }

   public final Condition lt(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19, T20 t20) {
      return this.lessThan(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20);
   }

   public final Condition lt(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19, T20 t20, T21 t21) {
      return this.lessThan(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21);
   }

   public final Condition lt(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19, T20 t20, T21 t21, T22 t22) {
      return this.lessThan(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22);
   }

   public final Condition lt(Field<?>... values) {
      return this.lessThan(values);
   }

   public final Condition lt(Field<T1> t1) {
      return this.lessThan(t1);
   }

   public final Condition lt(Field<T1> t1, Field<T2> t2) {
      return this.lessThan(t1, t2);
   }

   public final Condition lt(Field<T1> t1, Field<T2> t2, Field<T3> t3) {
      return this.lessThan(t1, t2, t3);
   }

   public final Condition lt(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4) {
      return this.lessThan(t1, t2, t3, t4);
   }

   public final Condition lt(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5) {
      return this.lessThan(t1, t2, t3, t4, t5);
   }

   public final Condition lt(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6) {
      return this.lessThan(t1, t2, t3, t4, t5, t6);
   }

   public final Condition lt(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7) {
      return this.lessThan(t1, t2, t3, t4, t5, t6, t7);
   }

   public final Condition lt(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8) {
      return this.lessThan(t1, t2, t3, t4, t5, t6, t7, t8);
   }

   public final Condition lt(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9) {
      return this.lessThan(t1, t2, t3, t4, t5, t6, t7, t8, t9);
   }

   public final Condition lt(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10) {
      return this.lessThan(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
   }

   public final Condition lt(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11) {
      return this.lessThan(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11);
   }

   public final Condition lt(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12) {
      return this.lessThan(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12);
   }

   public final Condition lt(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13) {
      return this.lessThan(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13);
   }

   public final Condition lt(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14) {
      return this.lessThan(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14);
   }

   public final Condition lt(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15) {
      return this.lessThan(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15);
   }

   public final Condition lt(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16) {
      return this.lessThan(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16);
   }

   public final Condition lt(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17) {
      return this.lessThan(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17);
   }

   public final Condition lt(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18) {
      return this.lessThan(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18);
   }

   public final Condition lt(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18, Field<T19> t19) {
      return this.lessThan(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19);
   }

   public final Condition lt(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18, Field<T19> t19, Field<T20> t20) {
      return this.lessThan(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20);
   }

   public final Condition lt(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18, Field<T19> t19, Field<T20> t20, Field<T21> t21) {
      return this.lessThan(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21);
   }

   public final Condition lt(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18, Field<T19> t19, Field<T20> t20, Field<T21> t21, Field<T22> t22) {
      return this.lessThan(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22);
   }

   public final Condition lessOrEqual(RowN row) {
      return this.compare(Comparator.LESS_OR_EQUAL, row);
   }

   public final Condition lessOrEqual(Row1<T1> row) {
      return this.compare(Comparator.LESS_OR_EQUAL, row);
   }

   public final Condition lessOrEqual(Row2<T1, T2> row) {
      return this.compare(Comparator.LESS_OR_EQUAL, row);
   }

   public final Condition lessOrEqual(Row3<T1, T2, T3> row) {
      return this.compare(Comparator.LESS_OR_EQUAL, row);
   }

   public final Condition lessOrEqual(Row4<T1, T2, T3, T4> row) {
      return this.compare(Comparator.LESS_OR_EQUAL, row);
   }

   public final Condition lessOrEqual(Row5<T1, T2, T3, T4, T5> row) {
      return this.compare(Comparator.LESS_OR_EQUAL, row);
   }

   public final Condition lessOrEqual(Row6<T1, T2, T3, T4, T5, T6> row) {
      return this.compare(Comparator.LESS_OR_EQUAL, row);
   }

   public final Condition lessOrEqual(Row7<T1, T2, T3, T4, T5, T6, T7> row) {
      return this.compare(Comparator.LESS_OR_EQUAL, row);
   }

   public final Condition lessOrEqual(Row8<T1, T2, T3, T4, T5, T6, T7, T8> row) {
      return this.compare(Comparator.LESS_OR_EQUAL, row);
   }

   public final Condition lessOrEqual(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> row) {
      return this.compare(Comparator.LESS_OR_EQUAL, row);
   }

   public final Condition lessOrEqual(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> row) {
      return this.compare(Comparator.LESS_OR_EQUAL, row);
   }

   public final Condition lessOrEqual(Row11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> row) {
      return this.compare(Comparator.LESS_OR_EQUAL, row);
   }

   public final Condition lessOrEqual(Row12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> row) {
      return this.compare(Comparator.LESS_OR_EQUAL, row);
   }

   public final Condition lessOrEqual(Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> row) {
      return this.compare(Comparator.LESS_OR_EQUAL, row);
   }

   public final Condition lessOrEqual(Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> row) {
      return this.compare(Comparator.LESS_OR_EQUAL, row);
   }

   public final Condition lessOrEqual(Row15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> row) {
      return this.compare(Comparator.LESS_OR_EQUAL, row);
   }

   public final Condition lessOrEqual(Row16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> row) {
      return this.compare(Comparator.LESS_OR_EQUAL, row);
   }

   public final Condition lessOrEqual(Row17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> row) {
      return this.compare(Comparator.LESS_OR_EQUAL, row);
   }

   public final Condition lessOrEqual(Row18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> row) {
      return this.compare(Comparator.LESS_OR_EQUAL, row);
   }

   public final Condition lessOrEqual(Row19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> row) {
      return this.compare(Comparator.LESS_OR_EQUAL, row);
   }

   public final Condition lessOrEqual(Row20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> row) {
      return this.compare(Comparator.LESS_OR_EQUAL, row);
   }

   public final Condition lessOrEqual(Row21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> row) {
      return this.compare(Comparator.LESS_OR_EQUAL, row);
   }

   public final Condition lessOrEqual(Row22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> row) {
      return this.compare(Comparator.LESS_OR_EQUAL, row);
   }

   public final Condition lessOrEqual(Record record) {
      return this.compare(Comparator.LESS_OR_EQUAL, record);
   }

   public final Condition lessOrEqual(Record1<T1> record) {
      return this.compare(Comparator.LESS_OR_EQUAL, record);
   }

   public final Condition lessOrEqual(Record2<T1, T2> record) {
      return this.compare(Comparator.LESS_OR_EQUAL, record);
   }

   public final Condition lessOrEqual(Record3<T1, T2, T3> record) {
      return this.compare(Comparator.LESS_OR_EQUAL, record);
   }

   public final Condition lessOrEqual(Record4<T1, T2, T3, T4> record) {
      return this.compare(Comparator.LESS_OR_EQUAL, record);
   }

   public final Condition lessOrEqual(Record5<T1, T2, T3, T4, T5> record) {
      return this.compare(Comparator.LESS_OR_EQUAL, record);
   }

   public final Condition lessOrEqual(Record6<T1, T2, T3, T4, T5, T6> record) {
      return this.compare(Comparator.LESS_OR_EQUAL, record);
   }

   public final Condition lessOrEqual(Record7<T1, T2, T3, T4, T5, T6, T7> record) {
      return this.compare(Comparator.LESS_OR_EQUAL, record);
   }

   public final Condition lessOrEqual(Record8<T1, T2, T3, T4, T5, T6, T7, T8> record) {
      return this.compare(Comparator.LESS_OR_EQUAL, record);
   }

   public final Condition lessOrEqual(Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> record) {
      return this.compare(Comparator.LESS_OR_EQUAL, record);
   }

   public final Condition lessOrEqual(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> record) {
      return this.compare(Comparator.LESS_OR_EQUAL, record);
   }

   public final Condition lessOrEqual(Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> record) {
      return this.compare(Comparator.LESS_OR_EQUAL, record);
   }

   public final Condition lessOrEqual(Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> record) {
      return this.compare(Comparator.LESS_OR_EQUAL, record);
   }

   public final Condition lessOrEqual(Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> record) {
      return this.compare(Comparator.LESS_OR_EQUAL, record);
   }

   public final Condition lessOrEqual(Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> record) {
      return this.compare(Comparator.LESS_OR_EQUAL, record);
   }

   public final Condition lessOrEqual(Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> record) {
      return this.compare(Comparator.LESS_OR_EQUAL, record);
   }

   public final Condition lessOrEqual(Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> record) {
      return this.compare(Comparator.LESS_OR_EQUAL, record);
   }

   public final Condition lessOrEqual(Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> record) {
      return this.compare(Comparator.LESS_OR_EQUAL, record);
   }

   public final Condition lessOrEqual(Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> record) {
      return this.compare(Comparator.LESS_OR_EQUAL, record);
   }

   public final Condition lessOrEqual(Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> record) {
      return this.compare(Comparator.LESS_OR_EQUAL, record);
   }

   public final Condition lessOrEqual(Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> record) {
      return this.compare(Comparator.LESS_OR_EQUAL, record);
   }

   public final Condition lessOrEqual(Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> record) {
      return this.compare(Comparator.LESS_OR_EQUAL, record);
   }

   public final Condition lessOrEqual(Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> record) {
      return this.compare(Comparator.LESS_OR_EQUAL, record);
   }

   public final Condition lessOrEqual(Object... values) {
      return this.compare(Comparator.LESS_OR_EQUAL, values);
   }

   public final Condition lessOrEqual(T1 t1) {
      return this.compare(Comparator.LESS_OR_EQUAL, t1);
   }

   public final Condition lessOrEqual(T1 t1, T2 t2) {
      return this.compare(Comparator.LESS_OR_EQUAL, t1, t2);
   }

   public final Condition lessOrEqual(T1 t1, T2 t2, T3 t3) {
      return this.compare(Comparator.LESS_OR_EQUAL, t1, t2, t3);
   }

   public final Condition lessOrEqual(T1 t1, T2 t2, T3 t3, T4 t4) {
      return this.compare(Comparator.LESS_OR_EQUAL, t1, t2, t3, t4);
   }

   public final Condition lessOrEqual(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5) {
      return this.compare(Comparator.LESS_OR_EQUAL, t1, t2, t3, t4, t5);
   }

   public final Condition lessOrEqual(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6) {
      return this.compare(Comparator.LESS_OR_EQUAL, t1, t2, t3, t4, t5, t6);
   }

   public final Condition lessOrEqual(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7) {
      return this.compare(Comparator.LESS_OR_EQUAL, t1, t2, t3, t4, t5, t6, t7);
   }

   public final Condition lessOrEqual(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8) {
      return this.compare(Comparator.LESS_OR_EQUAL, t1, t2, t3, t4, t5, t6, t7, t8);
   }

   public final Condition lessOrEqual(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9) {
      return this.compare(Comparator.LESS_OR_EQUAL, t1, t2, t3, t4, t5, t6, t7, t8, t9);
   }

   public final Condition lessOrEqual(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10) {
      return this.compare(Comparator.LESS_OR_EQUAL, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
   }

   public final Condition lessOrEqual(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11) {
      return this.compare(Comparator.LESS_OR_EQUAL, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11);
   }

   public final Condition lessOrEqual(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12) {
      return this.compare(Comparator.LESS_OR_EQUAL, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12);
   }

   public final Condition lessOrEqual(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13) {
      return this.compare(Comparator.LESS_OR_EQUAL, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13);
   }

   public final Condition lessOrEqual(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14) {
      return this.compare(Comparator.LESS_OR_EQUAL, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14);
   }

   public final Condition lessOrEqual(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15) {
      return this.compare(Comparator.LESS_OR_EQUAL, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15);
   }

   public final Condition lessOrEqual(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16) {
      return this.compare(Comparator.LESS_OR_EQUAL, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16);
   }

   public final Condition lessOrEqual(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17) {
      return this.compare(Comparator.LESS_OR_EQUAL, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17);
   }

   public final Condition lessOrEqual(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18) {
      return this.compare(Comparator.LESS_OR_EQUAL, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18);
   }

   public final Condition lessOrEqual(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19) {
      return this.compare(Comparator.LESS_OR_EQUAL, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19);
   }

   public final Condition lessOrEqual(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19, T20 t20) {
      return this.compare(Comparator.LESS_OR_EQUAL, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20);
   }

   public final Condition lessOrEqual(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19, T20 t20, T21 t21) {
      return this.compare(Comparator.LESS_OR_EQUAL, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21);
   }

   public final Condition lessOrEqual(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19, T20 t20, T21 t21, T22 t22) {
      return this.compare(Comparator.LESS_OR_EQUAL, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22);
   }

   public final Condition lessOrEqual(Field<?>... values) {
      return this.compare(Comparator.LESS_OR_EQUAL, values);
   }

   public final Condition lessOrEqual(Field<T1> t1) {
      return this.compare(Comparator.LESS_OR_EQUAL, t1);
   }

   public final Condition lessOrEqual(Field<T1> t1, Field<T2> t2) {
      return this.compare(Comparator.LESS_OR_EQUAL, t1, t2);
   }

   public final Condition lessOrEqual(Field<T1> t1, Field<T2> t2, Field<T3> t3) {
      return this.compare(Comparator.LESS_OR_EQUAL, t1, t2, t3);
   }

   public final Condition lessOrEqual(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4) {
      return this.compare(Comparator.LESS_OR_EQUAL, t1, t2, t3, t4);
   }

   public final Condition lessOrEqual(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5) {
      return this.compare(Comparator.LESS_OR_EQUAL, t1, t2, t3, t4, t5);
   }

   public final Condition lessOrEqual(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6) {
      return this.compare(Comparator.LESS_OR_EQUAL, t1, t2, t3, t4, t5, t6);
   }

   public final Condition lessOrEqual(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7) {
      return this.compare(Comparator.LESS_OR_EQUAL, t1, t2, t3, t4, t5, t6, t7);
   }

   public final Condition lessOrEqual(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8) {
      return this.compare(Comparator.LESS_OR_EQUAL, t1, t2, t3, t4, t5, t6, t7, t8);
   }

   public final Condition lessOrEqual(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9) {
      return this.compare(Comparator.LESS_OR_EQUAL, t1, t2, t3, t4, t5, t6, t7, t8, t9);
   }

   public final Condition lessOrEqual(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10) {
      return this.compare(Comparator.LESS_OR_EQUAL, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
   }

   public final Condition lessOrEqual(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11) {
      return this.compare(Comparator.LESS_OR_EQUAL, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11);
   }

   public final Condition lessOrEqual(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12) {
      return this.compare(Comparator.LESS_OR_EQUAL, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12);
   }

   public final Condition lessOrEqual(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13) {
      return this.compare(Comparator.LESS_OR_EQUAL, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13);
   }

   public final Condition lessOrEqual(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14) {
      return this.compare(Comparator.LESS_OR_EQUAL, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14);
   }

   public final Condition lessOrEqual(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15) {
      return this.compare(Comparator.LESS_OR_EQUAL, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15);
   }

   public final Condition lessOrEqual(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16) {
      return this.compare(Comparator.LESS_OR_EQUAL, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16);
   }

   public final Condition lessOrEqual(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17) {
      return this.compare(Comparator.LESS_OR_EQUAL, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17);
   }

   public final Condition lessOrEqual(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18) {
      return this.compare(Comparator.LESS_OR_EQUAL, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18);
   }

   public final Condition lessOrEqual(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18, Field<T19> t19) {
      return this.compare(Comparator.LESS_OR_EQUAL, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19);
   }

   public final Condition lessOrEqual(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18, Field<T19> t19, Field<T20> t20) {
      return this.compare(Comparator.LESS_OR_EQUAL, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20);
   }

   public final Condition lessOrEqual(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18, Field<T19> t19, Field<T20> t20, Field<T21> t21) {
      return this.compare(Comparator.LESS_OR_EQUAL, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21);
   }

   public final Condition lessOrEqual(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18, Field<T19> t19, Field<T20> t20, Field<T21> t21, Field<T22> t22) {
      return this.compare(Comparator.LESS_OR_EQUAL, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22);
   }

   public final Condition le(RowN row) {
      return this.lessOrEqual(row);
   }

   public final Condition le(Row1<T1> row) {
      return this.lessOrEqual(row);
   }

   public final Condition le(Row2<T1, T2> row) {
      return this.lessOrEqual(row);
   }

   public final Condition le(Row3<T1, T2, T3> row) {
      return this.lessOrEqual(row);
   }

   public final Condition le(Row4<T1, T2, T3, T4> row) {
      return this.lessOrEqual(row);
   }

   public final Condition le(Row5<T1, T2, T3, T4, T5> row) {
      return this.lessOrEqual(row);
   }

   public final Condition le(Row6<T1, T2, T3, T4, T5, T6> row) {
      return this.lessOrEqual(row);
   }

   public final Condition le(Row7<T1, T2, T3, T4, T5, T6, T7> row) {
      return this.lessOrEqual(row);
   }

   public final Condition le(Row8<T1, T2, T3, T4, T5, T6, T7, T8> row) {
      return this.lessOrEqual(row);
   }

   public final Condition le(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> row) {
      return this.lessOrEqual(row);
   }

   public final Condition le(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> row) {
      return this.lessOrEqual(row);
   }

   public final Condition le(Row11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> row) {
      return this.lessOrEqual(row);
   }

   public final Condition le(Row12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> row) {
      return this.lessOrEqual(row);
   }

   public final Condition le(Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> row) {
      return this.lessOrEqual(row);
   }

   public final Condition le(Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> row) {
      return this.lessOrEqual(row);
   }

   public final Condition le(Row15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> row) {
      return this.lessOrEqual(row);
   }

   public final Condition le(Row16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> row) {
      return this.lessOrEqual(row);
   }

   public final Condition le(Row17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> row) {
      return this.lessOrEqual(row);
   }

   public final Condition le(Row18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> row) {
      return this.lessOrEqual(row);
   }

   public final Condition le(Row19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> row) {
      return this.lessOrEqual(row);
   }

   public final Condition le(Row20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> row) {
      return this.lessOrEqual(row);
   }

   public final Condition le(Row21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> row) {
      return this.lessOrEqual(row);
   }

   public final Condition le(Row22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> row) {
      return this.lessOrEqual(row);
   }

   public final Condition le(Record record) {
      return this.lessOrEqual(record);
   }

   public final Condition le(Record1<T1> record) {
      return this.lessOrEqual(record);
   }

   public final Condition le(Record2<T1, T2> record) {
      return this.lessOrEqual(record);
   }

   public final Condition le(Record3<T1, T2, T3> record) {
      return this.lessOrEqual(record);
   }

   public final Condition le(Record4<T1, T2, T3, T4> record) {
      return this.lessOrEqual(record);
   }

   public final Condition le(Record5<T1, T2, T3, T4, T5> record) {
      return this.lessOrEqual(record);
   }

   public final Condition le(Record6<T1, T2, T3, T4, T5, T6> record) {
      return this.lessOrEqual(record);
   }

   public final Condition le(Record7<T1, T2, T3, T4, T5, T6, T7> record) {
      return this.lessOrEqual(record);
   }

   public final Condition le(Record8<T1, T2, T3, T4, T5, T6, T7, T8> record) {
      return this.lessOrEqual(record);
   }

   public final Condition le(Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> record) {
      return this.lessOrEqual(record);
   }

   public final Condition le(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> record) {
      return this.lessOrEqual(record);
   }

   public final Condition le(Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> record) {
      return this.lessOrEqual(record);
   }

   public final Condition le(Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> record) {
      return this.lessOrEqual(record);
   }

   public final Condition le(Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> record) {
      return this.lessOrEqual(record);
   }

   public final Condition le(Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> record) {
      return this.lessOrEqual(record);
   }

   public final Condition le(Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> record) {
      return this.lessOrEqual(record);
   }

   public final Condition le(Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> record) {
      return this.lessOrEqual(record);
   }

   public final Condition le(Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> record) {
      return this.lessOrEqual(record);
   }

   public final Condition le(Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> record) {
      return this.lessOrEqual(record);
   }

   public final Condition le(Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> record) {
      return this.lessOrEqual(record);
   }

   public final Condition le(Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> record) {
      return this.lessOrEqual(record);
   }

   public final Condition le(Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> record) {
      return this.lessOrEqual(record);
   }

   public final Condition le(Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> record) {
      return this.lessOrEqual(record);
   }

   public final Condition le(Object... values) {
      return this.lessOrEqual(values);
   }

   public final Condition le(T1 t1) {
      return this.lessOrEqual(t1);
   }

   public final Condition le(T1 t1, T2 t2) {
      return this.lessOrEqual(t1, t2);
   }

   public final Condition le(T1 t1, T2 t2, T3 t3) {
      return this.lessOrEqual(t1, t2, t3);
   }

   public final Condition le(T1 t1, T2 t2, T3 t3, T4 t4) {
      return this.lessOrEqual(t1, t2, t3, t4);
   }

   public final Condition le(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5) {
      return this.lessOrEqual(t1, t2, t3, t4, t5);
   }

   public final Condition le(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6) {
      return this.lessOrEqual(t1, t2, t3, t4, t5, t6);
   }

   public final Condition le(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7) {
      return this.lessOrEqual(t1, t2, t3, t4, t5, t6, t7);
   }

   public final Condition le(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8) {
      return this.lessOrEqual(t1, t2, t3, t4, t5, t6, t7, t8);
   }

   public final Condition le(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9) {
      return this.lessOrEqual(t1, t2, t3, t4, t5, t6, t7, t8, t9);
   }

   public final Condition le(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10) {
      return this.lessOrEqual(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
   }

   public final Condition le(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11) {
      return this.lessOrEqual(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11);
   }

   public final Condition le(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12) {
      return this.lessOrEqual(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12);
   }

   public final Condition le(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13) {
      return this.lessOrEqual(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13);
   }

   public final Condition le(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14) {
      return this.lessOrEqual(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14);
   }

   public final Condition le(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15) {
      return this.lessOrEqual(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15);
   }

   public final Condition le(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16) {
      return this.lessOrEqual(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16);
   }

   public final Condition le(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17) {
      return this.lessOrEqual(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17);
   }

   public final Condition le(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18) {
      return this.lessOrEqual(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18);
   }

   public final Condition le(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19) {
      return this.lessOrEqual(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19);
   }

   public final Condition le(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19, T20 t20) {
      return this.lessOrEqual(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20);
   }

   public final Condition le(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19, T20 t20, T21 t21) {
      return this.lessOrEqual(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21);
   }

   public final Condition le(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19, T20 t20, T21 t21, T22 t22) {
      return this.lessOrEqual(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22);
   }

   public final Condition le(Field<?>... values) {
      return this.lessOrEqual(values);
   }

   public final Condition le(Field<T1> t1) {
      return this.lessOrEqual(t1);
   }

   public final Condition le(Field<T1> t1, Field<T2> t2) {
      return this.lessOrEqual(t1, t2);
   }

   public final Condition le(Field<T1> t1, Field<T2> t2, Field<T3> t3) {
      return this.lessOrEqual(t1, t2, t3);
   }

   public final Condition le(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4) {
      return this.lessOrEqual(t1, t2, t3, t4);
   }

   public final Condition le(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5) {
      return this.lessOrEqual(t1, t2, t3, t4, t5);
   }

   public final Condition le(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6) {
      return this.lessOrEqual(t1, t2, t3, t4, t5, t6);
   }

   public final Condition le(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7) {
      return this.lessOrEqual(t1, t2, t3, t4, t5, t6, t7);
   }

   public final Condition le(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8) {
      return this.lessOrEqual(t1, t2, t3, t4, t5, t6, t7, t8);
   }

   public final Condition le(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9) {
      return this.lessOrEqual(t1, t2, t3, t4, t5, t6, t7, t8, t9);
   }

   public final Condition le(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10) {
      return this.lessOrEqual(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
   }

   public final Condition le(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11) {
      return this.lessOrEqual(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11);
   }

   public final Condition le(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12) {
      return this.lessOrEqual(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12);
   }

   public final Condition le(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13) {
      return this.lessOrEqual(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13);
   }

   public final Condition le(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14) {
      return this.lessOrEqual(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14);
   }

   public final Condition le(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15) {
      return this.lessOrEqual(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15);
   }

   public final Condition le(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16) {
      return this.lessOrEqual(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16);
   }

   public final Condition le(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17) {
      return this.lessOrEqual(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17);
   }

   public final Condition le(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18) {
      return this.lessOrEqual(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18);
   }

   public final Condition le(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18, Field<T19> t19) {
      return this.lessOrEqual(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19);
   }

   public final Condition le(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18, Field<T19> t19, Field<T20> t20) {
      return this.lessOrEqual(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20);
   }

   public final Condition le(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18, Field<T19> t19, Field<T20> t20, Field<T21> t21) {
      return this.lessOrEqual(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21);
   }

   public final Condition le(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18, Field<T19> t19, Field<T20> t20, Field<T21> t21, Field<T22> t22) {
      return this.lessOrEqual(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22);
   }

   public final Condition greaterThan(RowN row) {
      return this.compare(Comparator.GREATER, row);
   }

   public final Condition greaterThan(Row1<T1> row) {
      return this.compare(Comparator.GREATER, row);
   }

   public final Condition greaterThan(Row2<T1, T2> row) {
      return this.compare(Comparator.GREATER, row);
   }

   public final Condition greaterThan(Row3<T1, T2, T3> row) {
      return this.compare(Comparator.GREATER, row);
   }

   public final Condition greaterThan(Row4<T1, T2, T3, T4> row) {
      return this.compare(Comparator.GREATER, row);
   }

   public final Condition greaterThan(Row5<T1, T2, T3, T4, T5> row) {
      return this.compare(Comparator.GREATER, row);
   }

   public final Condition greaterThan(Row6<T1, T2, T3, T4, T5, T6> row) {
      return this.compare(Comparator.GREATER, row);
   }

   public final Condition greaterThan(Row7<T1, T2, T3, T4, T5, T6, T7> row) {
      return this.compare(Comparator.GREATER, row);
   }

   public final Condition greaterThan(Row8<T1, T2, T3, T4, T5, T6, T7, T8> row) {
      return this.compare(Comparator.GREATER, row);
   }

   public final Condition greaterThan(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> row) {
      return this.compare(Comparator.GREATER, row);
   }

   public final Condition greaterThan(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> row) {
      return this.compare(Comparator.GREATER, row);
   }

   public final Condition greaterThan(Row11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> row) {
      return this.compare(Comparator.GREATER, row);
   }

   public final Condition greaterThan(Row12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> row) {
      return this.compare(Comparator.GREATER, row);
   }

   public final Condition greaterThan(Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> row) {
      return this.compare(Comparator.GREATER, row);
   }

   public final Condition greaterThan(Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> row) {
      return this.compare(Comparator.GREATER, row);
   }

   public final Condition greaterThan(Row15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> row) {
      return this.compare(Comparator.GREATER, row);
   }

   public final Condition greaterThan(Row16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> row) {
      return this.compare(Comparator.GREATER, row);
   }

   public final Condition greaterThan(Row17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> row) {
      return this.compare(Comparator.GREATER, row);
   }

   public final Condition greaterThan(Row18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> row) {
      return this.compare(Comparator.GREATER, row);
   }

   public final Condition greaterThan(Row19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> row) {
      return this.compare(Comparator.GREATER, row);
   }

   public final Condition greaterThan(Row20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> row) {
      return this.compare(Comparator.GREATER, row);
   }

   public final Condition greaterThan(Row21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> row) {
      return this.compare(Comparator.GREATER, row);
   }

   public final Condition greaterThan(Row22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> row) {
      return this.compare(Comparator.GREATER, row);
   }

   public final Condition greaterThan(Record record) {
      return this.compare(Comparator.GREATER, record);
   }

   public final Condition greaterThan(Record1<T1> record) {
      return this.compare(Comparator.GREATER, record);
   }

   public final Condition greaterThan(Record2<T1, T2> record) {
      return this.compare(Comparator.GREATER, record);
   }

   public final Condition greaterThan(Record3<T1, T2, T3> record) {
      return this.compare(Comparator.GREATER, record);
   }

   public final Condition greaterThan(Record4<T1, T2, T3, T4> record) {
      return this.compare(Comparator.GREATER, record);
   }

   public final Condition greaterThan(Record5<T1, T2, T3, T4, T5> record) {
      return this.compare(Comparator.GREATER, record);
   }

   public final Condition greaterThan(Record6<T1, T2, T3, T4, T5, T6> record) {
      return this.compare(Comparator.GREATER, record);
   }

   public final Condition greaterThan(Record7<T1, T2, T3, T4, T5, T6, T7> record) {
      return this.compare(Comparator.GREATER, record);
   }

   public final Condition greaterThan(Record8<T1, T2, T3, T4, T5, T6, T7, T8> record) {
      return this.compare(Comparator.GREATER, record);
   }

   public final Condition greaterThan(Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> record) {
      return this.compare(Comparator.GREATER, record);
   }

   public final Condition greaterThan(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> record) {
      return this.compare(Comparator.GREATER, record);
   }

   public final Condition greaterThan(Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> record) {
      return this.compare(Comparator.GREATER, record);
   }

   public final Condition greaterThan(Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> record) {
      return this.compare(Comparator.GREATER, record);
   }

   public final Condition greaterThan(Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> record) {
      return this.compare(Comparator.GREATER, record);
   }

   public final Condition greaterThan(Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> record) {
      return this.compare(Comparator.GREATER, record);
   }

   public final Condition greaterThan(Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> record) {
      return this.compare(Comparator.GREATER, record);
   }

   public final Condition greaterThan(Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> record) {
      return this.compare(Comparator.GREATER, record);
   }

   public final Condition greaterThan(Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> record) {
      return this.compare(Comparator.GREATER, record);
   }

   public final Condition greaterThan(Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> record) {
      return this.compare(Comparator.GREATER, record);
   }

   public final Condition greaterThan(Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> record) {
      return this.compare(Comparator.GREATER, record);
   }

   public final Condition greaterThan(Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> record) {
      return this.compare(Comparator.GREATER, record);
   }

   public final Condition greaterThan(Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> record) {
      return this.compare(Comparator.GREATER, record);
   }

   public final Condition greaterThan(Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> record) {
      return this.compare(Comparator.GREATER, record);
   }

   public final Condition greaterThan(Object... values) {
      return this.compare(Comparator.GREATER, values);
   }

   public final Condition greaterThan(T1 t1) {
      return this.compare(Comparator.GREATER, t1);
   }

   public final Condition greaterThan(T1 t1, T2 t2) {
      return this.compare(Comparator.GREATER, t1, t2);
   }

   public final Condition greaterThan(T1 t1, T2 t2, T3 t3) {
      return this.compare(Comparator.GREATER, t1, t2, t3);
   }

   public final Condition greaterThan(T1 t1, T2 t2, T3 t3, T4 t4) {
      return this.compare(Comparator.GREATER, t1, t2, t3, t4);
   }

   public final Condition greaterThan(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5) {
      return this.compare(Comparator.GREATER, t1, t2, t3, t4, t5);
   }

   public final Condition greaterThan(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6) {
      return this.compare(Comparator.GREATER, t1, t2, t3, t4, t5, t6);
   }

   public final Condition greaterThan(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7) {
      return this.compare(Comparator.GREATER, t1, t2, t3, t4, t5, t6, t7);
   }

   public final Condition greaterThan(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8) {
      return this.compare(Comparator.GREATER, t1, t2, t3, t4, t5, t6, t7, t8);
   }

   public final Condition greaterThan(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9) {
      return this.compare(Comparator.GREATER, t1, t2, t3, t4, t5, t6, t7, t8, t9);
   }

   public final Condition greaterThan(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10) {
      return this.compare(Comparator.GREATER, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
   }

   public final Condition greaterThan(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11) {
      return this.compare(Comparator.GREATER, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11);
   }

   public final Condition greaterThan(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12) {
      return this.compare(Comparator.GREATER, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12);
   }

   public final Condition greaterThan(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13) {
      return this.compare(Comparator.GREATER, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13);
   }

   public final Condition greaterThan(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14) {
      return this.compare(Comparator.GREATER, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14);
   }

   public final Condition greaterThan(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15) {
      return this.compare(Comparator.GREATER, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15);
   }

   public final Condition greaterThan(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16) {
      return this.compare(Comparator.GREATER, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16);
   }

   public final Condition greaterThan(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17) {
      return this.compare(Comparator.GREATER, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17);
   }

   public final Condition greaterThan(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18) {
      return this.compare(Comparator.GREATER, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18);
   }

   public final Condition greaterThan(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19) {
      return this.compare(Comparator.GREATER, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19);
   }

   public final Condition greaterThan(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19, T20 t20) {
      return this.compare(Comparator.GREATER, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20);
   }

   public final Condition greaterThan(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19, T20 t20, T21 t21) {
      return this.compare(Comparator.GREATER, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21);
   }

   public final Condition greaterThan(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19, T20 t20, T21 t21, T22 t22) {
      return this.compare(Comparator.GREATER, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22);
   }

   public final Condition greaterThan(Field<?>... values) {
      return this.compare(Comparator.GREATER, values);
   }

   public final Condition greaterThan(Field<T1> t1) {
      return this.compare(Comparator.GREATER, t1);
   }

   public final Condition greaterThan(Field<T1> t1, Field<T2> t2) {
      return this.compare(Comparator.GREATER, t1, t2);
   }

   public final Condition greaterThan(Field<T1> t1, Field<T2> t2, Field<T3> t3) {
      return this.compare(Comparator.GREATER, t1, t2, t3);
   }

   public final Condition greaterThan(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4) {
      return this.compare(Comparator.GREATER, t1, t2, t3, t4);
   }

   public final Condition greaterThan(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5) {
      return this.compare(Comparator.GREATER, t1, t2, t3, t4, t5);
   }

   public final Condition greaterThan(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6) {
      return this.compare(Comparator.GREATER, t1, t2, t3, t4, t5, t6);
   }

   public final Condition greaterThan(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7) {
      return this.compare(Comparator.GREATER, t1, t2, t3, t4, t5, t6, t7);
   }

   public final Condition greaterThan(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8) {
      return this.compare(Comparator.GREATER, t1, t2, t3, t4, t5, t6, t7, t8);
   }

   public final Condition greaterThan(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9) {
      return this.compare(Comparator.GREATER, t1, t2, t3, t4, t5, t6, t7, t8, t9);
   }

   public final Condition greaterThan(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10) {
      return this.compare(Comparator.GREATER, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
   }

   public final Condition greaterThan(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11) {
      return this.compare(Comparator.GREATER, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11);
   }

   public final Condition greaterThan(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12) {
      return this.compare(Comparator.GREATER, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12);
   }

   public final Condition greaterThan(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13) {
      return this.compare(Comparator.GREATER, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13);
   }

   public final Condition greaterThan(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14) {
      return this.compare(Comparator.GREATER, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14);
   }

   public final Condition greaterThan(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15) {
      return this.compare(Comparator.GREATER, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15);
   }

   public final Condition greaterThan(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16) {
      return this.compare(Comparator.GREATER, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16);
   }

   public final Condition greaterThan(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17) {
      return this.compare(Comparator.GREATER, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17);
   }

   public final Condition greaterThan(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18) {
      return this.compare(Comparator.GREATER, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18);
   }

   public final Condition greaterThan(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18, Field<T19> t19) {
      return this.compare(Comparator.GREATER, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19);
   }

   public final Condition greaterThan(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18, Field<T19> t19, Field<T20> t20) {
      return this.compare(Comparator.GREATER, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20);
   }

   public final Condition greaterThan(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18, Field<T19> t19, Field<T20> t20, Field<T21> t21) {
      return this.compare(Comparator.GREATER, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21);
   }

   public final Condition greaterThan(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18, Field<T19> t19, Field<T20> t20, Field<T21> t21, Field<T22> t22) {
      return this.compare(Comparator.GREATER, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22);
   }

   public final Condition gt(RowN row) {
      return this.greaterThan(row);
   }

   public final Condition gt(Row1<T1> row) {
      return this.greaterThan(row);
   }

   public final Condition gt(Row2<T1, T2> row) {
      return this.greaterThan(row);
   }

   public final Condition gt(Row3<T1, T2, T3> row) {
      return this.greaterThan(row);
   }

   public final Condition gt(Row4<T1, T2, T3, T4> row) {
      return this.greaterThan(row);
   }

   public final Condition gt(Row5<T1, T2, T3, T4, T5> row) {
      return this.greaterThan(row);
   }

   public final Condition gt(Row6<T1, T2, T3, T4, T5, T6> row) {
      return this.greaterThan(row);
   }

   public final Condition gt(Row7<T1, T2, T3, T4, T5, T6, T7> row) {
      return this.greaterThan(row);
   }

   public final Condition gt(Row8<T1, T2, T3, T4, T5, T6, T7, T8> row) {
      return this.greaterThan(row);
   }

   public final Condition gt(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> row) {
      return this.greaterThan(row);
   }

   public final Condition gt(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> row) {
      return this.greaterThan(row);
   }

   public final Condition gt(Row11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> row) {
      return this.greaterThan(row);
   }

   public final Condition gt(Row12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> row) {
      return this.greaterThan(row);
   }

   public final Condition gt(Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> row) {
      return this.greaterThan(row);
   }

   public final Condition gt(Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> row) {
      return this.greaterThan(row);
   }

   public final Condition gt(Row15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> row) {
      return this.greaterThan(row);
   }

   public final Condition gt(Row16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> row) {
      return this.greaterThan(row);
   }

   public final Condition gt(Row17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> row) {
      return this.greaterThan(row);
   }

   public final Condition gt(Row18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> row) {
      return this.greaterThan(row);
   }

   public final Condition gt(Row19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> row) {
      return this.greaterThan(row);
   }

   public final Condition gt(Row20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> row) {
      return this.greaterThan(row);
   }

   public final Condition gt(Row21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> row) {
      return this.greaterThan(row);
   }

   public final Condition gt(Row22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> row) {
      return this.greaterThan(row);
   }

   public final Condition gt(Record record) {
      return this.greaterThan(record);
   }

   public final Condition gt(Record1<T1> record) {
      return this.greaterThan(record);
   }

   public final Condition gt(Record2<T1, T2> record) {
      return this.greaterThan(record);
   }

   public final Condition gt(Record3<T1, T2, T3> record) {
      return this.greaterThan(record);
   }

   public final Condition gt(Record4<T1, T2, T3, T4> record) {
      return this.greaterThan(record);
   }

   public final Condition gt(Record5<T1, T2, T3, T4, T5> record) {
      return this.greaterThan(record);
   }

   public final Condition gt(Record6<T1, T2, T3, T4, T5, T6> record) {
      return this.greaterThan(record);
   }

   public final Condition gt(Record7<T1, T2, T3, T4, T5, T6, T7> record) {
      return this.greaterThan(record);
   }

   public final Condition gt(Record8<T1, T2, T3, T4, T5, T6, T7, T8> record) {
      return this.greaterThan(record);
   }

   public final Condition gt(Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> record) {
      return this.greaterThan(record);
   }

   public final Condition gt(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> record) {
      return this.greaterThan(record);
   }

   public final Condition gt(Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> record) {
      return this.greaterThan(record);
   }

   public final Condition gt(Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> record) {
      return this.greaterThan(record);
   }

   public final Condition gt(Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> record) {
      return this.greaterThan(record);
   }

   public final Condition gt(Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> record) {
      return this.greaterThan(record);
   }

   public final Condition gt(Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> record) {
      return this.greaterThan(record);
   }

   public final Condition gt(Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> record) {
      return this.greaterThan(record);
   }

   public final Condition gt(Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> record) {
      return this.greaterThan(record);
   }

   public final Condition gt(Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> record) {
      return this.greaterThan(record);
   }

   public final Condition gt(Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> record) {
      return this.greaterThan(record);
   }

   public final Condition gt(Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> record) {
      return this.greaterThan(record);
   }

   public final Condition gt(Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> record) {
      return this.greaterThan(record);
   }

   public final Condition gt(Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> record) {
      return this.greaterThan(record);
   }

   public final Condition gt(Object... values) {
      return this.greaterThan(values);
   }

   public final Condition gt(T1 t1) {
      return this.greaterThan(t1);
   }

   public final Condition gt(T1 t1, T2 t2) {
      return this.greaterThan(t1, t2);
   }

   public final Condition gt(T1 t1, T2 t2, T3 t3) {
      return this.greaterThan(t1, t2, t3);
   }

   public final Condition gt(T1 t1, T2 t2, T3 t3, T4 t4) {
      return this.greaterThan(t1, t2, t3, t4);
   }

   public final Condition gt(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5) {
      return this.greaterThan(t1, t2, t3, t4, t5);
   }

   public final Condition gt(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6) {
      return this.greaterThan(t1, t2, t3, t4, t5, t6);
   }

   public final Condition gt(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7) {
      return this.greaterThan(t1, t2, t3, t4, t5, t6, t7);
   }

   public final Condition gt(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8) {
      return this.greaterThan(t1, t2, t3, t4, t5, t6, t7, t8);
   }

   public final Condition gt(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9) {
      return this.greaterThan(t1, t2, t3, t4, t5, t6, t7, t8, t9);
   }

   public final Condition gt(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10) {
      return this.greaterThan(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
   }

   public final Condition gt(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11) {
      return this.greaterThan(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11);
   }

   public final Condition gt(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12) {
      return this.greaterThan(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12);
   }

   public final Condition gt(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13) {
      return this.greaterThan(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13);
   }

   public final Condition gt(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14) {
      return this.greaterThan(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14);
   }

   public final Condition gt(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15) {
      return this.greaterThan(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15);
   }

   public final Condition gt(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16) {
      return this.greaterThan(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16);
   }

   public final Condition gt(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17) {
      return this.greaterThan(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17);
   }

   public final Condition gt(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18) {
      return this.greaterThan(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18);
   }

   public final Condition gt(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19) {
      return this.greaterThan(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19);
   }

   public final Condition gt(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19, T20 t20) {
      return this.greaterThan(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20);
   }

   public final Condition gt(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19, T20 t20, T21 t21) {
      return this.greaterThan(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21);
   }

   public final Condition gt(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19, T20 t20, T21 t21, T22 t22) {
      return this.greaterThan(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22);
   }

   public final Condition gt(Field<?>... values) {
      return this.greaterThan(values);
   }

   public final Condition gt(Field<T1> t1) {
      return this.greaterThan(t1);
   }

   public final Condition gt(Field<T1> t1, Field<T2> t2) {
      return this.greaterThan(t1, t2);
   }

   public final Condition gt(Field<T1> t1, Field<T2> t2, Field<T3> t3) {
      return this.greaterThan(t1, t2, t3);
   }

   public final Condition gt(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4) {
      return this.greaterThan(t1, t2, t3, t4);
   }

   public final Condition gt(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5) {
      return this.greaterThan(t1, t2, t3, t4, t5);
   }

   public final Condition gt(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6) {
      return this.greaterThan(t1, t2, t3, t4, t5, t6);
   }

   public final Condition gt(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7) {
      return this.greaterThan(t1, t2, t3, t4, t5, t6, t7);
   }

   public final Condition gt(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8) {
      return this.greaterThan(t1, t2, t3, t4, t5, t6, t7, t8);
   }

   public final Condition gt(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9) {
      return this.greaterThan(t1, t2, t3, t4, t5, t6, t7, t8, t9);
   }

   public final Condition gt(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10) {
      return this.greaterThan(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
   }

   public final Condition gt(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11) {
      return this.greaterThan(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11);
   }

   public final Condition gt(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12) {
      return this.greaterThan(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12);
   }

   public final Condition gt(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13) {
      return this.greaterThan(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13);
   }

   public final Condition gt(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14) {
      return this.greaterThan(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14);
   }

   public final Condition gt(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15) {
      return this.greaterThan(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15);
   }

   public final Condition gt(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16) {
      return this.greaterThan(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16);
   }

   public final Condition gt(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17) {
      return this.greaterThan(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17);
   }

   public final Condition gt(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18) {
      return this.greaterThan(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18);
   }

   public final Condition gt(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18, Field<T19> t19) {
      return this.greaterThan(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19);
   }

   public final Condition gt(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18, Field<T19> t19, Field<T20> t20) {
      return this.greaterThan(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20);
   }

   public final Condition gt(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18, Field<T19> t19, Field<T20> t20, Field<T21> t21) {
      return this.greaterThan(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21);
   }

   public final Condition gt(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18, Field<T19> t19, Field<T20> t20, Field<T21> t21, Field<T22> t22) {
      return this.greaterThan(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22);
   }

   public final Condition greaterOrEqual(RowN row) {
      return this.compare(Comparator.GREATER_OR_EQUAL, row);
   }

   public final Condition greaterOrEqual(Row1<T1> row) {
      return this.compare(Comparator.GREATER_OR_EQUAL, row);
   }

   public final Condition greaterOrEqual(Row2<T1, T2> row) {
      return this.compare(Comparator.GREATER_OR_EQUAL, row);
   }

   public final Condition greaterOrEqual(Row3<T1, T2, T3> row) {
      return this.compare(Comparator.GREATER_OR_EQUAL, row);
   }

   public final Condition greaterOrEqual(Row4<T1, T2, T3, T4> row) {
      return this.compare(Comparator.GREATER_OR_EQUAL, row);
   }

   public final Condition greaterOrEqual(Row5<T1, T2, T3, T4, T5> row) {
      return this.compare(Comparator.GREATER_OR_EQUAL, row);
   }

   public final Condition greaterOrEqual(Row6<T1, T2, T3, T4, T5, T6> row) {
      return this.compare(Comparator.GREATER_OR_EQUAL, row);
   }

   public final Condition greaterOrEqual(Row7<T1, T2, T3, T4, T5, T6, T7> row) {
      return this.compare(Comparator.GREATER_OR_EQUAL, row);
   }

   public final Condition greaterOrEqual(Row8<T1, T2, T3, T4, T5, T6, T7, T8> row) {
      return this.compare(Comparator.GREATER_OR_EQUAL, row);
   }

   public final Condition greaterOrEqual(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> row) {
      return this.compare(Comparator.GREATER_OR_EQUAL, row);
   }

   public final Condition greaterOrEqual(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> row) {
      return this.compare(Comparator.GREATER_OR_EQUAL, row);
   }

   public final Condition greaterOrEqual(Row11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> row) {
      return this.compare(Comparator.GREATER_OR_EQUAL, row);
   }

   public final Condition greaterOrEqual(Row12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> row) {
      return this.compare(Comparator.GREATER_OR_EQUAL, row);
   }

   public final Condition greaterOrEqual(Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> row) {
      return this.compare(Comparator.GREATER_OR_EQUAL, row);
   }

   public final Condition greaterOrEqual(Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> row) {
      return this.compare(Comparator.GREATER_OR_EQUAL, row);
   }

   public final Condition greaterOrEqual(Row15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> row) {
      return this.compare(Comparator.GREATER_OR_EQUAL, row);
   }

   public final Condition greaterOrEqual(Row16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> row) {
      return this.compare(Comparator.GREATER_OR_EQUAL, row);
   }

   public final Condition greaterOrEqual(Row17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> row) {
      return this.compare(Comparator.GREATER_OR_EQUAL, row);
   }

   public final Condition greaterOrEqual(Row18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> row) {
      return this.compare(Comparator.GREATER_OR_EQUAL, row);
   }

   public final Condition greaterOrEqual(Row19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> row) {
      return this.compare(Comparator.GREATER_OR_EQUAL, row);
   }

   public final Condition greaterOrEqual(Row20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> row) {
      return this.compare(Comparator.GREATER_OR_EQUAL, row);
   }

   public final Condition greaterOrEqual(Row21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> row) {
      return this.compare(Comparator.GREATER_OR_EQUAL, row);
   }

   public final Condition greaterOrEqual(Row22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> row) {
      return this.compare(Comparator.GREATER_OR_EQUAL, row);
   }

   public final Condition greaterOrEqual(Record record) {
      return this.compare(Comparator.GREATER_OR_EQUAL, record);
   }

   public final Condition greaterOrEqual(Record1<T1> record) {
      return this.compare(Comparator.GREATER_OR_EQUAL, record);
   }

   public final Condition greaterOrEqual(Record2<T1, T2> record) {
      return this.compare(Comparator.GREATER_OR_EQUAL, record);
   }

   public final Condition greaterOrEqual(Record3<T1, T2, T3> record) {
      return this.compare(Comparator.GREATER_OR_EQUAL, record);
   }

   public final Condition greaterOrEqual(Record4<T1, T2, T3, T4> record) {
      return this.compare(Comparator.GREATER_OR_EQUAL, record);
   }

   public final Condition greaterOrEqual(Record5<T1, T2, T3, T4, T5> record) {
      return this.compare(Comparator.GREATER_OR_EQUAL, record);
   }

   public final Condition greaterOrEqual(Record6<T1, T2, T3, T4, T5, T6> record) {
      return this.compare(Comparator.GREATER_OR_EQUAL, record);
   }

   public final Condition greaterOrEqual(Record7<T1, T2, T3, T4, T5, T6, T7> record) {
      return this.compare(Comparator.GREATER_OR_EQUAL, record);
   }

   public final Condition greaterOrEqual(Record8<T1, T2, T3, T4, T5, T6, T7, T8> record) {
      return this.compare(Comparator.GREATER_OR_EQUAL, record);
   }

   public final Condition greaterOrEqual(Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> record) {
      return this.compare(Comparator.GREATER_OR_EQUAL, record);
   }

   public final Condition greaterOrEqual(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> record) {
      return this.compare(Comparator.GREATER_OR_EQUAL, record);
   }

   public final Condition greaterOrEqual(Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> record) {
      return this.compare(Comparator.GREATER_OR_EQUAL, record);
   }

   public final Condition greaterOrEqual(Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> record) {
      return this.compare(Comparator.GREATER_OR_EQUAL, record);
   }

   public final Condition greaterOrEqual(Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> record) {
      return this.compare(Comparator.GREATER_OR_EQUAL, record);
   }

   public final Condition greaterOrEqual(Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> record) {
      return this.compare(Comparator.GREATER_OR_EQUAL, record);
   }

   public final Condition greaterOrEqual(Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> record) {
      return this.compare(Comparator.GREATER_OR_EQUAL, record);
   }

   public final Condition greaterOrEqual(Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> record) {
      return this.compare(Comparator.GREATER_OR_EQUAL, record);
   }

   public final Condition greaterOrEqual(Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> record) {
      return this.compare(Comparator.GREATER_OR_EQUAL, record);
   }

   public final Condition greaterOrEqual(Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> record) {
      return this.compare(Comparator.GREATER_OR_EQUAL, record);
   }

   public final Condition greaterOrEqual(Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> record) {
      return this.compare(Comparator.GREATER_OR_EQUAL, record);
   }

   public final Condition greaterOrEqual(Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> record) {
      return this.compare(Comparator.GREATER_OR_EQUAL, record);
   }

   public final Condition greaterOrEqual(Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> record) {
      return this.compare(Comparator.GREATER_OR_EQUAL, record);
   }

   public final Condition greaterOrEqual(Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> record) {
      return this.compare(Comparator.GREATER_OR_EQUAL, record);
   }

   public final Condition greaterOrEqual(Object... values) {
      return this.compare(Comparator.GREATER_OR_EQUAL, values);
   }

   public final Condition greaterOrEqual(T1 t1) {
      return this.compare(Comparator.GREATER_OR_EQUAL, t1);
   }

   public final Condition greaterOrEqual(T1 t1, T2 t2) {
      return this.compare(Comparator.GREATER_OR_EQUAL, t1, t2);
   }

   public final Condition greaterOrEqual(T1 t1, T2 t2, T3 t3) {
      return this.compare(Comparator.GREATER_OR_EQUAL, t1, t2, t3);
   }

   public final Condition greaterOrEqual(T1 t1, T2 t2, T3 t3, T4 t4) {
      return this.compare(Comparator.GREATER_OR_EQUAL, t1, t2, t3, t4);
   }

   public final Condition greaterOrEqual(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5) {
      return this.compare(Comparator.GREATER_OR_EQUAL, t1, t2, t3, t4, t5);
   }

   public final Condition greaterOrEqual(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6) {
      return this.compare(Comparator.GREATER_OR_EQUAL, t1, t2, t3, t4, t5, t6);
   }

   public final Condition greaterOrEqual(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7) {
      return this.compare(Comparator.GREATER_OR_EQUAL, t1, t2, t3, t4, t5, t6, t7);
   }

   public final Condition greaterOrEqual(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8) {
      return this.compare(Comparator.GREATER_OR_EQUAL, t1, t2, t3, t4, t5, t6, t7, t8);
   }

   public final Condition greaterOrEqual(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9) {
      return this.compare(Comparator.GREATER_OR_EQUAL, t1, t2, t3, t4, t5, t6, t7, t8, t9);
   }

   public final Condition greaterOrEqual(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10) {
      return this.compare(Comparator.GREATER_OR_EQUAL, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
   }

   public final Condition greaterOrEqual(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11) {
      return this.compare(Comparator.GREATER_OR_EQUAL, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11);
   }

   public final Condition greaterOrEqual(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12) {
      return this.compare(Comparator.GREATER_OR_EQUAL, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12);
   }

   public final Condition greaterOrEqual(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13) {
      return this.compare(Comparator.GREATER_OR_EQUAL, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13);
   }

   public final Condition greaterOrEqual(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14) {
      return this.compare(Comparator.GREATER_OR_EQUAL, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14);
   }

   public final Condition greaterOrEqual(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15) {
      return this.compare(Comparator.GREATER_OR_EQUAL, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15);
   }

   public final Condition greaterOrEqual(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16) {
      return this.compare(Comparator.GREATER_OR_EQUAL, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16);
   }

   public final Condition greaterOrEqual(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17) {
      return this.compare(Comparator.GREATER_OR_EQUAL, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17);
   }

   public final Condition greaterOrEqual(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18) {
      return this.compare(Comparator.GREATER_OR_EQUAL, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18);
   }

   public final Condition greaterOrEqual(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19) {
      return this.compare(Comparator.GREATER_OR_EQUAL, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19);
   }

   public final Condition greaterOrEqual(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19, T20 t20) {
      return this.compare(Comparator.GREATER_OR_EQUAL, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20);
   }

   public final Condition greaterOrEqual(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19, T20 t20, T21 t21) {
      return this.compare(Comparator.GREATER_OR_EQUAL, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21);
   }

   public final Condition greaterOrEqual(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19, T20 t20, T21 t21, T22 t22) {
      return this.compare(Comparator.GREATER_OR_EQUAL, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22);
   }

   public final Condition greaterOrEqual(Field<?>... values) {
      return this.compare(Comparator.GREATER_OR_EQUAL, values);
   }

   public final Condition greaterOrEqual(Field<T1> t1) {
      return this.compare(Comparator.GREATER_OR_EQUAL, t1);
   }

   public final Condition greaterOrEqual(Field<T1> t1, Field<T2> t2) {
      return this.compare(Comparator.GREATER_OR_EQUAL, t1, t2);
   }

   public final Condition greaterOrEqual(Field<T1> t1, Field<T2> t2, Field<T3> t3) {
      return this.compare(Comparator.GREATER_OR_EQUAL, t1, t2, t3);
   }

   public final Condition greaterOrEqual(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4) {
      return this.compare(Comparator.GREATER_OR_EQUAL, t1, t2, t3, t4);
   }

   public final Condition greaterOrEqual(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5) {
      return this.compare(Comparator.GREATER_OR_EQUAL, t1, t2, t3, t4, t5);
   }

   public final Condition greaterOrEqual(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6) {
      return this.compare(Comparator.GREATER_OR_EQUAL, t1, t2, t3, t4, t5, t6);
   }

   public final Condition greaterOrEqual(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7) {
      return this.compare(Comparator.GREATER_OR_EQUAL, t1, t2, t3, t4, t5, t6, t7);
   }

   public final Condition greaterOrEqual(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8) {
      return this.compare(Comparator.GREATER_OR_EQUAL, t1, t2, t3, t4, t5, t6, t7, t8);
   }

   public final Condition greaterOrEqual(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9) {
      return this.compare(Comparator.GREATER_OR_EQUAL, t1, t2, t3, t4, t5, t6, t7, t8, t9);
   }

   public final Condition greaterOrEqual(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10) {
      return this.compare(Comparator.GREATER_OR_EQUAL, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
   }

   public final Condition greaterOrEqual(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11) {
      return this.compare(Comparator.GREATER_OR_EQUAL, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11);
   }

   public final Condition greaterOrEqual(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12) {
      return this.compare(Comparator.GREATER_OR_EQUAL, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12);
   }

   public final Condition greaterOrEqual(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13) {
      return this.compare(Comparator.GREATER_OR_EQUAL, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13);
   }

   public final Condition greaterOrEqual(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14) {
      return this.compare(Comparator.GREATER_OR_EQUAL, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14);
   }

   public final Condition greaterOrEqual(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15) {
      return this.compare(Comparator.GREATER_OR_EQUAL, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15);
   }

   public final Condition greaterOrEqual(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16) {
      return this.compare(Comparator.GREATER_OR_EQUAL, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16);
   }

   public final Condition greaterOrEqual(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17) {
      return this.compare(Comparator.GREATER_OR_EQUAL, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17);
   }

   public final Condition greaterOrEqual(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18) {
      return this.compare(Comparator.GREATER_OR_EQUAL, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18);
   }

   public final Condition greaterOrEqual(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18, Field<T19> t19) {
      return this.compare(Comparator.GREATER_OR_EQUAL, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19);
   }

   public final Condition greaterOrEqual(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18, Field<T19> t19, Field<T20> t20) {
      return this.compare(Comparator.GREATER_OR_EQUAL, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20);
   }

   public final Condition greaterOrEqual(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18, Field<T19> t19, Field<T20> t20, Field<T21> t21) {
      return this.compare(Comparator.GREATER_OR_EQUAL, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21);
   }

   public final Condition greaterOrEqual(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18, Field<T19> t19, Field<T20> t20, Field<T21> t21, Field<T22> t22) {
      return this.compare(Comparator.GREATER_OR_EQUAL, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22);
   }

   public final Condition ge(RowN row) {
      return this.greaterOrEqual(row);
   }

   public final Condition ge(Row1<T1> row) {
      return this.greaterOrEqual(row);
   }

   public final Condition ge(Row2<T1, T2> row) {
      return this.greaterOrEqual(row);
   }

   public final Condition ge(Row3<T1, T2, T3> row) {
      return this.greaterOrEqual(row);
   }

   public final Condition ge(Row4<T1, T2, T3, T4> row) {
      return this.greaterOrEqual(row);
   }

   public final Condition ge(Row5<T1, T2, T3, T4, T5> row) {
      return this.greaterOrEqual(row);
   }

   public final Condition ge(Row6<T1, T2, T3, T4, T5, T6> row) {
      return this.greaterOrEqual(row);
   }

   public final Condition ge(Row7<T1, T2, T3, T4, T5, T6, T7> row) {
      return this.greaterOrEqual(row);
   }

   public final Condition ge(Row8<T1, T2, T3, T4, T5, T6, T7, T8> row) {
      return this.greaterOrEqual(row);
   }

   public final Condition ge(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> row) {
      return this.greaterOrEqual(row);
   }

   public final Condition ge(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> row) {
      return this.greaterOrEqual(row);
   }

   public final Condition ge(Row11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> row) {
      return this.greaterOrEqual(row);
   }

   public final Condition ge(Row12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> row) {
      return this.greaterOrEqual(row);
   }

   public final Condition ge(Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> row) {
      return this.greaterOrEqual(row);
   }

   public final Condition ge(Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> row) {
      return this.greaterOrEqual(row);
   }

   public final Condition ge(Row15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> row) {
      return this.greaterOrEqual(row);
   }

   public final Condition ge(Row16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> row) {
      return this.greaterOrEqual(row);
   }

   public final Condition ge(Row17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> row) {
      return this.greaterOrEqual(row);
   }

   public final Condition ge(Row18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> row) {
      return this.greaterOrEqual(row);
   }

   public final Condition ge(Row19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> row) {
      return this.greaterOrEqual(row);
   }

   public final Condition ge(Row20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> row) {
      return this.greaterOrEqual(row);
   }

   public final Condition ge(Row21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> row) {
      return this.greaterOrEqual(row);
   }

   public final Condition ge(Row22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> row) {
      return this.greaterOrEqual(row);
   }

   public final Condition ge(Record record) {
      return this.greaterOrEqual(record);
   }

   public final Condition ge(Record1<T1> record) {
      return this.greaterOrEqual(record);
   }

   public final Condition ge(Record2<T1, T2> record) {
      return this.greaterOrEqual(record);
   }

   public final Condition ge(Record3<T1, T2, T3> record) {
      return this.greaterOrEqual(record);
   }

   public final Condition ge(Record4<T1, T2, T3, T4> record) {
      return this.greaterOrEqual(record);
   }

   public final Condition ge(Record5<T1, T2, T3, T4, T5> record) {
      return this.greaterOrEqual(record);
   }

   public final Condition ge(Record6<T1, T2, T3, T4, T5, T6> record) {
      return this.greaterOrEqual(record);
   }

   public final Condition ge(Record7<T1, T2, T3, T4, T5, T6, T7> record) {
      return this.greaterOrEqual(record);
   }

   public final Condition ge(Record8<T1, T2, T3, T4, T5, T6, T7, T8> record) {
      return this.greaterOrEqual(record);
   }

   public final Condition ge(Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> record) {
      return this.greaterOrEqual(record);
   }

   public final Condition ge(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> record) {
      return this.greaterOrEqual(record);
   }

   public final Condition ge(Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> record) {
      return this.greaterOrEqual(record);
   }

   public final Condition ge(Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> record) {
      return this.greaterOrEqual(record);
   }

   public final Condition ge(Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> record) {
      return this.greaterOrEqual(record);
   }

   public final Condition ge(Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> record) {
      return this.greaterOrEqual(record);
   }

   public final Condition ge(Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> record) {
      return this.greaterOrEqual(record);
   }

   public final Condition ge(Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> record) {
      return this.greaterOrEqual(record);
   }

   public final Condition ge(Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> record) {
      return this.greaterOrEqual(record);
   }

   public final Condition ge(Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> record) {
      return this.greaterOrEqual(record);
   }

   public final Condition ge(Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> record) {
      return this.greaterOrEqual(record);
   }

   public final Condition ge(Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> record) {
      return this.greaterOrEqual(record);
   }

   public final Condition ge(Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> record) {
      return this.greaterOrEqual(record);
   }

   public final Condition ge(Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> record) {
      return this.greaterOrEqual(record);
   }

   public final Condition ge(Object... values) {
      return this.greaterOrEqual(values);
   }

   public final Condition ge(T1 t1) {
      return this.greaterOrEqual(t1);
   }

   public final Condition ge(T1 t1, T2 t2) {
      return this.greaterOrEqual(t1, t2);
   }

   public final Condition ge(T1 t1, T2 t2, T3 t3) {
      return this.greaterOrEqual(t1, t2, t3);
   }

   public final Condition ge(T1 t1, T2 t2, T3 t3, T4 t4) {
      return this.greaterOrEqual(t1, t2, t3, t4);
   }

   public final Condition ge(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5) {
      return this.greaterOrEqual(t1, t2, t3, t4, t5);
   }

   public final Condition ge(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6) {
      return this.greaterOrEqual(t1, t2, t3, t4, t5, t6);
   }

   public final Condition ge(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7) {
      return this.greaterOrEqual(t1, t2, t3, t4, t5, t6, t7);
   }

   public final Condition ge(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8) {
      return this.greaterOrEqual(t1, t2, t3, t4, t5, t6, t7, t8);
   }

   public final Condition ge(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9) {
      return this.greaterOrEqual(t1, t2, t3, t4, t5, t6, t7, t8, t9);
   }

   public final Condition ge(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10) {
      return this.greaterOrEqual(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
   }

   public final Condition ge(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11) {
      return this.greaterOrEqual(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11);
   }

   public final Condition ge(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12) {
      return this.greaterOrEqual(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12);
   }

   public final Condition ge(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13) {
      return this.greaterOrEqual(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13);
   }

   public final Condition ge(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14) {
      return this.greaterOrEqual(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14);
   }

   public final Condition ge(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15) {
      return this.greaterOrEqual(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15);
   }

   public final Condition ge(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16) {
      return this.greaterOrEqual(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16);
   }

   public final Condition ge(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17) {
      return this.greaterOrEqual(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17);
   }

   public final Condition ge(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18) {
      return this.greaterOrEqual(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18);
   }

   public final Condition ge(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19) {
      return this.greaterOrEqual(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19);
   }

   public final Condition ge(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19, T20 t20) {
      return this.greaterOrEqual(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20);
   }

   public final Condition ge(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19, T20 t20, T21 t21) {
      return this.greaterOrEqual(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21);
   }

   public final Condition ge(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19, T20 t20, T21 t21, T22 t22) {
      return this.greaterOrEqual(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22);
   }

   public final Condition ge(Field<?>... values) {
      return this.greaterOrEqual(values);
   }

   public final Condition ge(Field<T1> t1) {
      return this.greaterOrEqual(t1);
   }

   public final Condition ge(Field<T1> t1, Field<T2> t2) {
      return this.greaterOrEqual(t1, t2);
   }

   public final Condition ge(Field<T1> t1, Field<T2> t2, Field<T3> t3) {
      return this.greaterOrEqual(t1, t2, t3);
   }

   public final Condition ge(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4) {
      return this.greaterOrEqual(t1, t2, t3, t4);
   }

   public final Condition ge(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5) {
      return this.greaterOrEqual(t1, t2, t3, t4, t5);
   }

   public final Condition ge(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6) {
      return this.greaterOrEqual(t1, t2, t3, t4, t5, t6);
   }

   public final Condition ge(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7) {
      return this.greaterOrEqual(t1, t2, t3, t4, t5, t6, t7);
   }

   public final Condition ge(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8) {
      return this.greaterOrEqual(t1, t2, t3, t4, t5, t6, t7, t8);
   }

   public final Condition ge(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9) {
      return this.greaterOrEqual(t1, t2, t3, t4, t5, t6, t7, t8, t9);
   }

   public final Condition ge(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10) {
      return this.greaterOrEqual(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
   }

   public final Condition ge(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11) {
      return this.greaterOrEqual(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11);
   }

   public final Condition ge(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12) {
      return this.greaterOrEqual(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12);
   }

   public final Condition ge(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13) {
      return this.greaterOrEqual(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13);
   }

   public final Condition ge(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14) {
      return this.greaterOrEqual(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14);
   }

   public final Condition ge(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15) {
      return this.greaterOrEqual(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15);
   }

   public final Condition ge(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16) {
      return this.greaterOrEqual(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16);
   }

   public final Condition ge(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17) {
      return this.greaterOrEqual(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17);
   }

   public final Condition ge(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18) {
      return this.greaterOrEqual(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18);
   }

   public final Condition ge(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18, Field<T19> t19) {
      return this.greaterOrEqual(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19);
   }

   public final Condition ge(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18, Field<T19> t19, Field<T20> t20) {
      return this.greaterOrEqual(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20);
   }

   public final Condition ge(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18, Field<T19> t19, Field<T20> t20, Field<T21> t21) {
      return this.greaterOrEqual(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21);
   }

   public final Condition ge(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18, Field<T19> t19, Field<T20> t20, Field<T21> t21, Field<T22> t22) {
      return this.greaterOrEqual(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22);
   }

   public final BetweenAndStepN between(Object... values) {
      return this.between(DSL.row(values));
   }

   public final BetweenAndStep1<T1> between(T1 t1) {
      return this.between(DSL.row(t1));
   }

   public final BetweenAndStep2<T1, T2> between(T1 t1, T2 t2) {
      return this.between(DSL.row(t1, t2));
   }

   public final BetweenAndStep3<T1, T2, T3> between(T1 t1, T2 t2, T3 t3) {
      return this.between(DSL.row(t1, t2, t3));
   }

   public final BetweenAndStep4<T1, T2, T3, T4> between(T1 t1, T2 t2, T3 t3, T4 t4) {
      return this.between(DSL.row(t1, t2, t3, t4));
   }

   public final BetweenAndStep5<T1, T2, T3, T4, T5> between(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5) {
      return this.between(DSL.row(t1, t2, t3, t4, t5));
   }

   public final BetweenAndStep6<T1, T2, T3, T4, T5, T6> between(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6) {
      return this.between(DSL.row(t1, t2, t3, t4, t5, t6));
   }

   public final BetweenAndStep7<T1, T2, T3, T4, T5, T6, T7> between(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7) {
      return this.between(DSL.row(t1, t2, t3, t4, t5, t6, t7));
   }

   public final BetweenAndStep8<T1, T2, T3, T4, T5, T6, T7, T8> between(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8) {
      return this.between(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8));
   }

   public final BetweenAndStep9<T1, T2, T3, T4, T5, T6, T7, T8, T9> between(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9) {
      return this.between(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9));
   }

   public final BetweenAndStep10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> between(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10) {
      return this.between(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10));
   }

   public final BetweenAndStep11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> between(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11) {
      return this.between(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11));
   }

   public final BetweenAndStep12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> between(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12) {
      return this.between(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12));
   }

   public final BetweenAndStep13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> between(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13) {
      return this.between(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13));
   }

   public final BetweenAndStep14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> between(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14) {
      return this.between(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14));
   }

   public final BetweenAndStep15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> between(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15) {
      return this.between(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15));
   }

   public final BetweenAndStep16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> between(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16) {
      return this.between(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16));
   }

   public final BetweenAndStep17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> between(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17) {
      return this.between(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17));
   }

   public final BetweenAndStep18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> between(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18) {
      return this.between(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18));
   }

   public final BetweenAndStep19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> between(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19) {
      return this.between(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19));
   }

   public final BetweenAndStep20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> between(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19, T20 t20) {
      return this.between(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20));
   }

   public final BetweenAndStep21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> between(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19, T20 t20, T21 t21) {
      return this.between(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21));
   }

   public final BetweenAndStep22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> between(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19, T20 t20, T21 t21, T22 t22) {
      return this.between(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22));
   }

   public final BetweenAndStepN between(Field<?>... values) {
      return this.between(DSL.row(values));
   }

   public final BetweenAndStep1<T1> between(Field<T1> t1) {
      return this.between(DSL.row(t1));
   }

   public final BetweenAndStep2<T1, T2> between(Field<T1> t1, Field<T2> t2) {
      return this.between(DSL.row(t1, t2));
   }

   public final BetweenAndStep3<T1, T2, T3> between(Field<T1> t1, Field<T2> t2, Field<T3> t3) {
      return this.between(DSL.row(t1, t2, t3));
   }

   public final BetweenAndStep4<T1, T2, T3, T4> between(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4) {
      return this.between(DSL.row(t1, t2, t3, t4));
   }

   public final BetweenAndStep5<T1, T2, T3, T4, T5> between(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5) {
      return this.between(DSL.row(t1, t2, t3, t4, t5));
   }

   public final BetweenAndStep6<T1, T2, T3, T4, T5, T6> between(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6) {
      return this.between(DSL.row(t1, t2, t3, t4, t5, t6));
   }

   public final BetweenAndStep7<T1, T2, T3, T4, T5, T6, T7> between(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7) {
      return this.between(DSL.row(t1, t2, t3, t4, t5, t6, t7));
   }

   public final BetweenAndStep8<T1, T2, T3, T4, T5, T6, T7, T8> between(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8) {
      return this.between(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8));
   }

   public final BetweenAndStep9<T1, T2, T3, T4, T5, T6, T7, T8, T9> between(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9) {
      return this.between(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9));
   }

   public final BetweenAndStep10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> between(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10) {
      return this.between(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10));
   }

   public final BetweenAndStep11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> between(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11) {
      return this.between(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11));
   }

   public final BetweenAndStep12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> between(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12) {
      return this.between(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12));
   }

   public final BetweenAndStep13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> between(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13) {
      return this.between(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13));
   }

   public final BetweenAndStep14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> between(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14) {
      return this.between(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14));
   }

   public final BetweenAndStep15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> between(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15) {
      return this.between(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15));
   }

   public final BetweenAndStep16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> between(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16) {
      return this.between(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16));
   }

   public final BetweenAndStep17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> between(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17) {
      return this.between(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17));
   }

   public final BetweenAndStep18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> between(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18) {
      return this.between(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18));
   }

   public final BetweenAndStep19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> between(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18, Field<T19> t19) {
      return this.between(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19));
   }

   public final BetweenAndStep20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> between(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18, Field<T19> t19, Field<T20> t20) {
      return this.between(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20));
   }

   public final BetweenAndStep21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> between(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18, Field<T19> t19, Field<T20> t20, Field<T21> t21) {
      return this.between(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21));
   }

   public final BetweenAndStep22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> between(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18, Field<T19> t19, Field<T20> t20, Field<T21> t21, Field<T22> t22) {
      return this.between(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22));
   }

   public final BetweenAndStepN between(RowN row) {
      return new RowBetweenCondition(this, row, false, false);
   }

   public final BetweenAndStep1<T1> between(Row1<T1> row) {
      return new RowBetweenCondition(this, row, false, false);
   }

   public final BetweenAndStep2<T1, T2> between(Row2<T1, T2> row) {
      return new RowBetweenCondition(this, row, false, false);
   }

   public final BetweenAndStep3<T1, T2, T3> between(Row3<T1, T2, T3> row) {
      return new RowBetweenCondition(this, row, false, false);
   }

   public final BetweenAndStep4<T1, T2, T3, T4> between(Row4<T1, T2, T3, T4> row) {
      return new RowBetweenCondition(this, row, false, false);
   }

   public final BetweenAndStep5<T1, T2, T3, T4, T5> between(Row5<T1, T2, T3, T4, T5> row) {
      return new RowBetweenCondition(this, row, false, false);
   }

   public final BetweenAndStep6<T1, T2, T3, T4, T5, T6> between(Row6<T1, T2, T3, T4, T5, T6> row) {
      return new RowBetweenCondition(this, row, false, false);
   }

   public final BetweenAndStep7<T1, T2, T3, T4, T5, T6, T7> between(Row7<T1, T2, T3, T4, T5, T6, T7> row) {
      return new RowBetweenCondition(this, row, false, false);
   }

   public final BetweenAndStep8<T1, T2, T3, T4, T5, T6, T7, T8> between(Row8<T1, T2, T3, T4, T5, T6, T7, T8> row) {
      return new RowBetweenCondition(this, row, false, false);
   }

   public final BetweenAndStep9<T1, T2, T3, T4, T5, T6, T7, T8, T9> between(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> row) {
      return new RowBetweenCondition(this, row, false, false);
   }

   public final BetweenAndStep10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> between(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> row) {
      return new RowBetweenCondition(this, row, false, false);
   }

   public final BetweenAndStep11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> between(Row11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> row) {
      return new RowBetweenCondition(this, row, false, false);
   }

   public final BetweenAndStep12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> between(Row12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> row) {
      return new RowBetweenCondition(this, row, false, false);
   }

   public final BetweenAndStep13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> between(Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> row) {
      return new RowBetweenCondition(this, row, false, false);
   }

   public final BetweenAndStep14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> between(Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> row) {
      return new RowBetweenCondition(this, row, false, false);
   }

   public final BetweenAndStep15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> between(Row15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> row) {
      return new RowBetweenCondition(this, row, false, false);
   }

   public final BetweenAndStep16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> between(Row16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> row) {
      return new RowBetweenCondition(this, row, false, false);
   }

   public final BetweenAndStep17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> between(Row17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> row) {
      return new RowBetweenCondition(this, row, false, false);
   }

   public final BetweenAndStep18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> between(Row18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> row) {
      return new RowBetweenCondition(this, row, false, false);
   }

   public final BetweenAndStep19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> between(Row19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> row) {
      return new RowBetweenCondition(this, row, false, false);
   }

   public final BetweenAndStep20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> between(Row20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> row) {
      return new RowBetweenCondition(this, row, false, false);
   }

   public final BetweenAndStep21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> between(Row21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> row) {
      return new RowBetweenCondition(this, row, false, false);
   }

   public final BetweenAndStep22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> between(Row22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> row) {
      return new RowBetweenCondition(this, row, false, false);
   }

   public final BetweenAndStepN between(Record record) {
      return this.between(record.valuesRow());
   }

   public final BetweenAndStep1<T1> between(Record1<T1> record) {
      return this.between(record.valuesRow());
   }

   public final BetweenAndStep2<T1, T2> between(Record2<T1, T2> record) {
      return this.between(record.valuesRow());
   }

   public final BetweenAndStep3<T1, T2, T3> between(Record3<T1, T2, T3> record) {
      return this.between(record.valuesRow());
   }

   public final BetweenAndStep4<T1, T2, T3, T4> between(Record4<T1, T2, T3, T4> record) {
      return this.between(record.valuesRow());
   }

   public final BetweenAndStep5<T1, T2, T3, T4, T5> between(Record5<T1, T2, T3, T4, T5> record) {
      return this.between(record.valuesRow());
   }

   public final BetweenAndStep6<T1, T2, T3, T4, T5, T6> between(Record6<T1, T2, T3, T4, T5, T6> record) {
      return this.between(record.valuesRow());
   }

   public final BetweenAndStep7<T1, T2, T3, T4, T5, T6, T7> between(Record7<T1, T2, T3, T4, T5, T6, T7> record) {
      return this.between(record.valuesRow());
   }

   public final BetweenAndStep8<T1, T2, T3, T4, T5, T6, T7, T8> between(Record8<T1, T2, T3, T4, T5, T6, T7, T8> record) {
      return this.between(record.valuesRow());
   }

   public final BetweenAndStep9<T1, T2, T3, T4, T5, T6, T7, T8, T9> between(Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> record) {
      return this.between(record.valuesRow());
   }

   public final BetweenAndStep10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> between(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> record) {
      return this.between(record.valuesRow());
   }

   public final BetweenAndStep11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> between(Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> record) {
      return this.between(record.valuesRow());
   }

   public final BetweenAndStep12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> between(Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> record) {
      return this.between(record.valuesRow());
   }

   public final BetweenAndStep13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> between(Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> record) {
      return this.between(record.valuesRow());
   }

   public final BetweenAndStep14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> between(Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> record) {
      return this.between(record.valuesRow());
   }

   public final BetweenAndStep15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> between(Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> record) {
      return this.between(record.valuesRow());
   }

   public final BetweenAndStep16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> between(Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> record) {
      return this.between(record.valuesRow());
   }

   public final BetweenAndStep17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> between(Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> record) {
      return this.between(record.valuesRow());
   }

   public final BetweenAndStep18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> between(Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> record) {
      return this.between(record.valuesRow());
   }

   public final BetweenAndStep19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> between(Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> record) {
      return this.between(record.valuesRow());
   }

   public final BetweenAndStep20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> between(Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> record) {
      return this.between(record.valuesRow());
   }

   public final BetweenAndStep21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> between(Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> record) {
      return this.between(record.valuesRow());
   }

   public final BetweenAndStep22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> between(Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> record) {
      return this.between(record.valuesRow());
   }

   public final Condition between(RowN minValue, RowN maxValue) {
      return this.between(minValue).and(maxValue);
   }

   public final Condition between(Row1<T1> minValue, Row1<T1> maxValue) {
      return this.between(minValue).and(maxValue);
   }

   public final Condition between(Row2<T1, T2> minValue, Row2<T1, T2> maxValue) {
      return this.between(minValue).and(maxValue);
   }

   public final Condition between(Row3<T1, T2, T3> minValue, Row3<T1, T2, T3> maxValue) {
      return this.between(minValue).and(maxValue);
   }

   public final Condition between(Row4<T1, T2, T3, T4> minValue, Row4<T1, T2, T3, T4> maxValue) {
      return this.between(minValue).and(maxValue);
   }

   public final Condition between(Row5<T1, T2, T3, T4, T5> minValue, Row5<T1, T2, T3, T4, T5> maxValue) {
      return this.between(minValue).and(maxValue);
   }

   public final Condition between(Row6<T1, T2, T3, T4, T5, T6> minValue, Row6<T1, T2, T3, T4, T5, T6> maxValue) {
      return this.between(minValue).and(maxValue);
   }

   public final Condition between(Row7<T1, T2, T3, T4, T5, T6, T7> minValue, Row7<T1, T2, T3, T4, T5, T6, T7> maxValue) {
      return this.between(minValue).and(maxValue);
   }

   public final Condition between(Row8<T1, T2, T3, T4, T5, T6, T7, T8> minValue, Row8<T1, T2, T3, T4, T5, T6, T7, T8> maxValue) {
      return this.between(minValue).and(maxValue);
   }

   public final Condition between(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> minValue, Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> maxValue) {
      return this.between(minValue).and(maxValue);
   }

   public final Condition between(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> minValue, Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> maxValue) {
      return this.between(minValue).and(maxValue);
   }

   public final Condition between(Row11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> minValue, Row11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> maxValue) {
      return this.between(minValue).and(maxValue);
   }

   public final Condition between(Row12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> minValue, Row12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> maxValue) {
      return this.between(minValue).and(maxValue);
   }

   public final Condition between(Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> minValue, Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> maxValue) {
      return this.between(minValue).and(maxValue);
   }

   public final Condition between(Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> minValue, Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> maxValue) {
      return this.between(minValue).and(maxValue);
   }

   public final Condition between(Row15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> minValue, Row15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> maxValue) {
      return this.between(minValue).and(maxValue);
   }

   public final Condition between(Row16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> minValue, Row16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> maxValue) {
      return this.between(minValue).and(maxValue);
   }

   public final Condition between(Row17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> minValue, Row17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> maxValue) {
      return this.between(minValue).and(maxValue);
   }

   public final Condition between(Row18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> minValue, Row18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> maxValue) {
      return this.between(minValue).and(maxValue);
   }

   public final Condition between(Row19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> minValue, Row19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> maxValue) {
      return this.between(minValue).and(maxValue);
   }

   public final Condition between(Row20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> minValue, Row20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> maxValue) {
      return this.between(minValue).and(maxValue);
   }

   public final Condition between(Row21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> minValue, Row21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> maxValue) {
      return this.between(minValue).and(maxValue);
   }

   public final Condition between(Row22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> minValue, Row22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> maxValue) {
      return this.between(minValue).and(maxValue);
   }

   public final Condition between(Record minValue, Record maxValue) {
      return this.between(minValue).and(maxValue);
   }

   public final Condition between(Record1<T1> minValue, Record1<T1> maxValue) {
      return this.between(minValue).and(maxValue);
   }

   public final Condition between(Record2<T1, T2> minValue, Record2<T1, T2> maxValue) {
      return this.between(minValue).and(maxValue);
   }

   public final Condition between(Record3<T1, T2, T3> minValue, Record3<T1, T2, T3> maxValue) {
      return this.between(minValue).and(maxValue);
   }

   public final Condition between(Record4<T1, T2, T3, T4> minValue, Record4<T1, T2, T3, T4> maxValue) {
      return this.between(minValue).and(maxValue);
   }

   public final Condition between(Record5<T1, T2, T3, T4, T5> minValue, Record5<T1, T2, T3, T4, T5> maxValue) {
      return this.between(minValue).and(maxValue);
   }

   public final Condition between(Record6<T1, T2, T3, T4, T5, T6> minValue, Record6<T1, T2, T3, T4, T5, T6> maxValue) {
      return this.between(minValue).and(maxValue);
   }

   public final Condition between(Record7<T1, T2, T3, T4, T5, T6, T7> minValue, Record7<T1, T2, T3, T4, T5, T6, T7> maxValue) {
      return this.between(minValue).and(maxValue);
   }

   public final Condition between(Record8<T1, T2, T3, T4, T5, T6, T7, T8> minValue, Record8<T1, T2, T3, T4, T5, T6, T7, T8> maxValue) {
      return this.between(minValue).and(maxValue);
   }

   public final Condition between(Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> minValue, Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> maxValue) {
      return this.between(minValue).and(maxValue);
   }

   public final Condition between(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> minValue, Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> maxValue) {
      return this.between(minValue).and(maxValue);
   }

   public final Condition between(Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> minValue, Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> maxValue) {
      return this.between(minValue).and(maxValue);
   }

   public final Condition between(Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> minValue, Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> maxValue) {
      return this.between(minValue).and(maxValue);
   }

   public final Condition between(Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> minValue, Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> maxValue) {
      return this.between(minValue).and(maxValue);
   }

   public final Condition between(Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> minValue, Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> maxValue) {
      return this.between(minValue).and(maxValue);
   }

   public final Condition between(Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> minValue, Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> maxValue) {
      return this.between(minValue).and(maxValue);
   }

   public final Condition between(Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> minValue, Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> maxValue) {
      return this.between(minValue).and(maxValue);
   }

   public final Condition between(Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> minValue, Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> maxValue) {
      return this.between(minValue).and(maxValue);
   }

   public final Condition between(Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> minValue, Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> maxValue) {
      return this.between(minValue).and(maxValue);
   }

   public final Condition between(Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> minValue, Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> maxValue) {
      return this.between(minValue).and(maxValue);
   }

   public final Condition between(Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> minValue, Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> maxValue) {
      return this.between(minValue).and(maxValue);
   }

   public final Condition between(Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> minValue, Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> maxValue) {
      return this.between(minValue).and(maxValue);
   }

   public final Condition between(Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> minValue, Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> maxValue) {
      return this.between(minValue).and(maxValue);
   }

   public final BetweenAndStepN betweenSymmetric(Object... values) {
      return this.betweenSymmetric(DSL.row(values));
   }

   public final BetweenAndStep1<T1> betweenSymmetric(T1 t1) {
      return this.betweenSymmetric(DSL.row(t1));
   }

   public final BetweenAndStep2<T1, T2> betweenSymmetric(T1 t1, T2 t2) {
      return this.betweenSymmetric(DSL.row(t1, t2));
   }

   public final BetweenAndStep3<T1, T2, T3> betweenSymmetric(T1 t1, T2 t2, T3 t3) {
      return this.betweenSymmetric(DSL.row(t1, t2, t3));
   }

   public final BetweenAndStep4<T1, T2, T3, T4> betweenSymmetric(T1 t1, T2 t2, T3 t3, T4 t4) {
      return this.betweenSymmetric(DSL.row(t1, t2, t3, t4));
   }

   public final BetweenAndStep5<T1, T2, T3, T4, T5> betweenSymmetric(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5) {
      return this.betweenSymmetric(DSL.row(t1, t2, t3, t4, t5));
   }

   public final BetweenAndStep6<T1, T2, T3, T4, T5, T6> betweenSymmetric(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6) {
      return this.betweenSymmetric(DSL.row(t1, t2, t3, t4, t5, t6));
   }

   public final BetweenAndStep7<T1, T2, T3, T4, T5, T6, T7> betweenSymmetric(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7) {
      return this.betweenSymmetric(DSL.row(t1, t2, t3, t4, t5, t6, t7));
   }

   public final BetweenAndStep8<T1, T2, T3, T4, T5, T6, T7, T8> betweenSymmetric(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8) {
      return this.betweenSymmetric(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8));
   }

   public final BetweenAndStep9<T1, T2, T3, T4, T5, T6, T7, T8, T9> betweenSymmetric(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9) {
      return this.betweenSymmetric(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9));
   }

   public final BetweenAndStep10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> betweenSymmetric(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10) {
      return this.betweenSymmetric(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10));
   }

   public final BetweenAndStep11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> betweenSymmetric(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11) {
      return this.betweenSymmetric(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11));
   }

   public final BetweenAndStep12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> betweenSymmetric(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12) {
      return this.betweenSymmetric(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12));
   }

   public final BetweenAndStep13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> betweenSymmetric(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13) {
      return this.betweenSymmetric(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13));
   }

   public final BetweenAndStep14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> betweenSymmetric(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14) {
      return this.betweenSymmetric(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14));
   }

   public final BetweenAndStep15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> betweenSymmetric(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15) {
      return this.betweenSymmetric(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15));
   }

   public final BetweenAndStep16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> betweenSymmetric(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16) {
      return this.betweenSymmetric(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16));
   }

   public final BetweenAndStep17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> betweenSymmetric(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17) {
      return this.betweenSymmetric(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17));
   }

   public final BetweenAndStep18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> betweenSymmetric(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18) {
      return this.betweenSymmetric(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18));
   }

   public final BetweenAndStep19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> betweenSymmetric(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19) {
      return this.betweenSymmetric(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19));
   }

   public final BetweenAndStep20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> betweenSymmetric(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19, T20 t20) {
      return this.betweenSymmetric(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20));
   }

   public final BetweenAndStep21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> betweenSymmetric(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19, T20 t20, T21 t21) {
      return this.betweenSymmetric(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21));
   }

   public final BetweenAndStep22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> betweenSymmetric(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19, T20 t20, T21 t21, T22 t22) {
      return this.betweenSymmetric(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22));
   }

   public final BetweenAndStepN betweenSymmetric(Field<?>... values) {
      return this.betweenSymmetric(DSL.row(values));
   }

   public final BetweenAndStep1<T1> betweenSymmetric(Field<T1> t1) {
      return this.betweenSymmetric(DSL.row(t1));
   }

   public final BetweenAndStep2<T1, T2> betweenSymmetric(Field<T1> t1, Field<T2> t2) {
      return this.betweenSymmetric(DSL.row(t1, t2));
   }

   public final BetweenAndStep3<T1, T2, T3> betweenSymmetric(Field<T1> t1, Field<T2> t2, Field<T3> t3) {
      return this.betweenSymmetric(DSL.row(t1, t2, t3));
   }

   public final BetweenAndStep4<T1, T2, T3, T4> betweenSymmetric(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4) {
      return this.betweenSymmetric(DSL.row(t1, t2, t3, t4));
   }

   public final BetweenAndStep5<T1, T2, T3, T4, T5> betweenSymmetric(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5) {
      return this.betweenSymmetric(DSL.row(t1, t2, t3, t4, t5));
   }

   public final BetweenAndStep6<T1, T2, T3, T4, T5, T6> betweenSymmetric(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6) {
      return this.betweenSymmetric(DSL.row(t1, t2, t3, t4, t5, t6));
   }

   public final BetweenAndStep7<T1, T2, T3, T4, T5, T6, T7> betweenSymmetric(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7) {
      return this.betweenSymmetric(DSL.row(t1, t2, t3, t4, t5, t6, t7));
   }

   public final BetweenAndStep8<T1, T2, T3, T4, T5, T6, T7, T8> betweenSymmetric(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8) {
      return this.betweenSymmetric(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8));
   }

   public final BetweenAndStep9<T1, T2, T3, T4, T5, T6, T7, T8, T9> betweenSymmetric(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9) {
      return this.betweenSymmetric(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9));
   }

   public final BetweenAndStep10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> betweenSymmetric(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10) {
      return this.betweenSymmetric(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10));
   }

   public final BetweenAndStep11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> betweenSymmetric(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11) {
      return this.betweenSymmetric(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11));
   }

   public final BetweenAndStep12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> betweenSymmetric(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12) {
      return this.betweenSymmetric(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12));
   }

   public final BetweenAndStep13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> betweenSymmetric(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13) {
      return this.betweenSymmetric(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13));
   }

   public final BetweenAndStep14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> betweenSymmetric(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14) {
      return this.betweenSymmetric(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14));
   }

   public final BetweenAndStep15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> betweenSymmetric(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15) {
      return this.betweenSymmetric(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15));
   }

   public final BetweenAndStep16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> betweenSymmetric(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16) {
      return this.betweenSymmetric(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16));
   }

   public final BetweenAndStep17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> betweenSymmetric(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17) {
      return this.betweenSymmetric(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17));
   }

   public final BetweenAndStep18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> betweenSymmetric(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18) {
      return this.betweenSymmetric(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18));
   }

   public final BetweenAndStep19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> betweenSymmetric(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18, Field<T19> t19) {
      return this.betweenSymmetric(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19));
   }

   public final BetweenAndStep20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> betweenSymmetric(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18, Field<T19> t19, Field<T20> t20) {
      return this.betweenSymmetric(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20));
   }

   public final BetweenAndStep21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> betweenSymmetric(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18, Field<T19> t19, Field<T20> t20, Field<T21> t21) {
      return this.betweenSymmetric(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21));
   }

   public final BetweenAndStep22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> betweenSymmetric(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18, Field<T19> t19, Field<T20> t20, Field<T21> t21, Field<T22> t22) {
      return this.betweenSymmetric(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22));
   }

   public final BetweenAndStepN betweenSymmetric(RowN row) {
      return new RowBetweenCondition(this, row, false, true);
   }

   public final BetweenAndStep1<T1> betweenSymmetric(Row1<T1> row) {
      return new RowBetweenCondition(this, row, false, true);
   }

   public final BetweenAndStep2<T1, T2> betweenSymmetric(Row2<T1, T2> row) {
      return new RowBetweenCondition(this, row, false, true);
   }

   public final BetweenAndStep3<T1, T2, T3> betweenSymmetric(Row3<T1, T2, T3> row) {
      return new RowBetweenCondition(this, row, false, true);
   }

   public final BetweenAndStep4<T1, T2, T3, T4> betweenSymmetric(Row4<T1, T2, T3, T4> row) {
      return new RowBetweenCondition(this, row, false, true);
   }

   public final BetweenAndStep5<T1, T2, T3, T4, T5> betweenSymmetric(Row5<T1, T2, T3, T4, T5> row) {
      return new RowBetweenCondition(this, row, false, true);
   }

   public final BetweenAndStep6<T1, T2, T3, T4, T5, T6> betweenSymmetric(Row6<T1, T2, T3, T4, T5, T6> row) {
      return new RowBetweenCondition(this, row, false, true);
   }

   public final BetweenAndStep7<T1, T2, T3, T4, T5, T6, T7> betweenSymmetric(Row7<T1, T2, T3, T4, T5, T6, T7> row) {
      return new RowBetweenCondition(this, row, false, true);
   }

   public final BetweenAndStep8<T1, T2, T3, T4, T5, T6, T7, T8> betweenSymmetric(Row8<T1, T2, T3, T4, T5, T6, T7, T8> row) {
      return new RowBetweenCondition(this, row, false, true);
   }

   public final BetweenAndStep9<T1, T2, T3, T4, T5, T6, T7, T8, T9> betweenSymmetric(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> row) {
      return new RowBetweenCondition(this, row, false, true);
   }

   public final BetweenAndStep10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> betweenSymmetric(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> row) {
      return new RowBetweenCondition(this, row, false, true);
   }

   public final BetweenAndStep11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> betweenSymmetric(Row11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> row) {
      return new RowBetweenCondition(this, row, false, true);
   }

   public final BetweenAndStep12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> betweenSymmetric(Row12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> row) {
      return new RowBetweenCondition(this, row, false, true);
   }

   public final BetweenAndStep13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> betweenSymmetric(Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> row) {
      return new RowBetweenCondition(this, row, false, true);
   }

   public final BetweenAndStep14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> betweenSymmetric(Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> row) {
      return new RowBetweenCondition(this, row, false, true);
   }

   public final BetweenAndStep15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> betweenSymmetric(Row15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> row) {
      return new RowBetweenCondition(this, row, false, true);
   }

   public final BetweenAndStep16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> betweenSymmetric(Row16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> row) {
      return new RowBetweenCondition(this, row, false, true);
   }

   public final BetweenAndStep17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> betweenSymmetric(Row17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> row) {
      return new RowBetweenCondition(this, row, false, true);
   }

   public final BetweenAndStep18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> betweenSymmetric(Row18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> row) {
      return new RowBetweenCondition(this, row, false, true);
   }

   public final BetweenAndStep19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> betweenSymmetric(Row19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> row) {
      return new RowBetweenCondition(this, row, false, true);
   }

   public final BetweenAndStep20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> betweenSymmetric(Row20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> row) {
      return new RowBetweenCondition(this, row, false, true);
   }

   public final BetweenAndStep21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> betweenSymmetric(Row21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> row) {
      return new RowBetweenCondition(this, row, false, true);
   }

   public final BetweenAndStep22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> betweenSymmetric(Row22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> row) {
      return new RowBetweenCondition(this, row, false, true);
   }

   public final BetweenAndStepN betweenSymmetric(Record record) {
      return this.betweenSymmetric(record.valuesRow());
   }

   public final BetweenAndStep1<T1> betweenSymmetric(Record1<T1> record) {
      return this.betweenSymmetric(record.valuesRow());
   }

   public final BetweenAndStep2<T1, T2> betweenSymmetric(Record2<T1, T2> record) {
      return this.betweenSymmetric(record.valuesRow());
   }

   public final BetweenAndStep3<T1, T2, T3> betweenSymmetric(Record3<T1, T2, T3> record) {
      return this.betweenSymmetric(record.valuesRow());
   }

   public final BetweenAndStep4<T1, T2, T3, T4> betweenSymmetric(Record4<T1, T2, T3, T4> record) {
      return this.betweenSymmetric(record.valuesRow());
   }

   public final BetweenAndStep5<T1, T2, T3, T4, T5> betweenSymmetric(Record5<T1, T2, T3, T4, T5> record) {
      return this.betweenSymmetric(record.valuesRow());
   }

   public final BetweenAndStep6<T1, T2, T3, T4, T5, T6> betweenSymmetric(Record6<T1, T2, T3, T4, T5, T6> record) {
      return this.betweenSymmetric(record.valuesRow());
   }

   public final BetweenAndStep7<T1, T2, T3, T4, T5, T6, T7> betweenSymmetric(Record7<T1, T2, T3, T4, T5, T6, T7> record) {
      return this.betweenSymmetric(record.valuesRow());
   }

   public final BetweenAndStep8<T1, T2, T3, T4, T5, T6, T7, T8> betweenSymmetric(Record8<T1, T2, T3, T4, T5, T6, T7, T8> record) {
      return this.betweenSymmetric(record.valuesRow());
   }

   public final BetweenAndStep9<T1, T2, T3, T4, T5, T6, T7, T8, T9> betweenSymmetric(Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> record) {
      return this.betweenSymmetric(record.valuesRow());
   }

   public final BetweenAndStep10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> betweenSymmetric(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> record) {
      return this.betweenSymmetric(record.valuesRow());
   }

   public final BetweenAndStep11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> betweenSymmetric(Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> record) {
      return this.betweenSymmetric(record.valuesRow());
   }

   public final BetweenAndStep12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> betweenSymmetric(Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> record) {
      return this.betweenSymmetric(record.valuesRow());
   }

   public final BetweenAndStep13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> betweenSymmetric(Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> record) {
      return this.betweenSymmetric(record.valuesRow());
   }

   public final BetweenAndStep14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> betweenSymmetric(Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> record) {
      return this.betweenSymmetric(record.valuesRow());
   }

   public final BetweenAndStep15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> betweenSymmetric(Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> record) {
      return this.betweenSymmetric(record.valuesRow());
   }

   public final BetweenAndStep16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> betweenSymmetric(Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> record) {
      return this.betweenSymmetric(record.valuesRow());
   }

   public final BetweenAndStep17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> betweenSymmetric(Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> record) {
      return this.betweenSymmetric(record.valuesRow());
   }

   public final BetweenAndStep18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> betweenSymmetric(Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> record) {
      return this.betweenSymmetric(record.valuesRow());
   }

   public final BetweenAndStep19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> betweenSymmetric(Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> record) {
      return this.betweenSymmetric(record.valuesRow());
   }

   public final BetweenAndStep20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> betweenSymmetric(Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> record) {
      return this.betweenSymmetric(record.valuesRow());
   }

   public final BetweenAndStep21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> betweenSymmetric(Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> record) {
      return this.betweenSymmetric(record.valuesRow());
   }

   public final BetweenAndStep22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> betweenSymmetric(Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> record) {
      return this.betweenSymmetric(record.valuesRow());
   }

   public final Condition betweenSymmetric(RowN minValue, RowN maxValue) {
      return this.betweenSymmetric(minValue).and(maxValue);
   }

   public final Condition betweenSymmetric(Row1<T1> minValue, Row1<T1> maxValue) {
      return this.betweenSymmetric(minValue).and(maxValue);
   }

   public final Condition betweenSymmetric(Row2<T1, T2> minValue, Row2<T1, T2> maxValue) {
      return this.betweenSymmetric(minValue).and(maxValue);
   }

   public final Condition betweenSymmetric(Row3<T1, T2, T3> minValue, Row3<T1, T2, T3> maxValue) {
      return this.betweenSymmetric(minValue).and(maxValue);
   }

   public final Condition betweenSymmetric(Row4<T1, T2, T3, T4> minValue, Row4<T1, T2, T3, T4> maxValue) {
      return this.betweenSymmetric(minValue).and(maxValue);
   }

   public final Condition betweenSymmetric(Row5<T1, T2, T3, T4, T5> minValue, Row5<T1, T2, T3, T4, T5> maxValue) {
      return this.betweenSymmetric(minValue).and(maxValue);
   }

   public final Condition betweenSymmetric(Row6<T1, T2, T3, T4, T5, T6> minValue, Row6<T1, T2, T3, T4, T5, T6> maxValue) {
      return this.betweenSymmetric(minValue).and(maxValue);
   }

   public final Condition betweenSymmetric(Row7<T1, T2, T3, T4, T5, T6, T7> minValue, Row7<T1, T2, T3, T4, T5, T6, T7> maxValue) {
      return this.betweenSymmetric(minValue).and(maxValue);
   }

   public final Condition betweenSymmetric(Row8<T1, T2, T3, T4, T5, T6, T7, T8> minValue, Row8<T1, T2, T3, T4, T5, T6, T7, T8> maxValue) {
      return this.betweenSymmetric(minValue).and(maxValue);
   }

   public final Condition betweenSymmetric(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> minValue, Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> maxValue) {
      return this.betweenSymmetric(minValue).and(maxValue);
   }

   public final Condition betweenSymmetric(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> minValue, Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> maxValue) {
      return this.betweenSymmetric(minValue).and(maxValue);
   }

   public final Condition betweenSymmetric(Row11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> minValue, Row11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> maxValue) {
      return this.betweenSymmetric(minValue).and(maxValue);
   }

   public final Condition betweenSymmetric(Row12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> minValue, Row12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> maxValue) {
      return this.betweenSymmetric(minValue).and(maxValue);
   }

   public final Condition betweenSymmetric(Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> minValue, Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> maxValue) {
      return this.betweenSymmetric(minValue).and(maxValue);
   }

   public final Condition betweenSymmetric(Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> minValue, Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> maxValue) {
      return this.betweenSymmetric(minValue).and(maxValue);
   }

   public final Condition betweenSymmetric(Row15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> minValue, Row15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> maxValue) {
      return this.betweenSymmetric(minValue).and(maxValue);
   }

   public final Condition betweenSymmetric(Row16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> minValue, Row16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> maxValue) {
      return this.betweenSymmetric(minValue).and(maxValue);
   }

   public final Condition betweenSymmetric(Row17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> minValue, Row17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> maxValue) {
      return this.betweenSymmetric(minValue).and(maxValue);
   }

   public final Condition betweenSymmetric(Row18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> minValue, Row18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> maxValue) {
      return this.betweenSymmetric(minValue).and(maxValue);
   }

   public final Condition betweenSymmetric(Row19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> minValue, Row19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> maxValue) {
      return this.betweenSymmetric(minValue).and(maxValue);
   }

   public final Condition betweenSymmetric(Row20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> minValue, Row20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> maxValue) {
      return this.betweenSymmetric(minValue).and(maxValue);
   }

   public final Condition betweenSymmetric(Row21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> minValue, Row21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> maxValue) {
      return this.betweenSymmetric(minValue).and(maxValue);
   }

   public final Condition betweenSymmetric(Row22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> minValue, Row22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> maxValue) {
      return this.betweenSymmetric(minValue).and(maxValue);
   }

   public final Condition betweenSymmetric(Record minValue, Record maxValue) {
      return this.betweenSymmetric(minValue).and(maxValue);
   }

   public final Condition betweenSymmetric(Record1<T1> minValue, Record1<T1> maxValue) {
      return this.betweenSymmetric(minValue).and(maxValue);
   }

   public final Condition betweenSymmetric(Record2<T1, T2> minValue, Record2<T1, T2> maxValue) {
      return this.betweenSymmetric(minValue).and(maxValue);
   }

   public final Condition betweenSymmetric(Record3<T1, T2, T3> minValue, Record3<T1, T2, T3> maxValue) {
      return this.betweenSymmetric(minValue).and(maxValue);
   }

   public final Condition betweenSymmetric(Record4<T1, T2, T3, T4> minValue, Record4<T1, T2, T3, T4> maxValue) {
      return this.betweenSymmetric(minValue).and(maxValue);
   }

   public final Condition betweenSymmetric(Record5<T1, T2, T3, T4, T5> minValue, Record5<T1, T2, T3, T4, T5> maxValue) {
      return this.betweenSymmetric(minValue).and(maxValue);
   }

   public final Condition betweenSymmetric(Record6<T1, T2, T3, T4, T5, T6> minValue, Record6<T1, T2, T3, T4, T5, T6> maxValue) {
      return this.betweenSymmetric(minValue).and(maxValue);
   }

   public final Condition betweenSymmetric(Record7<T1, T2, T3, T4, T5, T6, T7> minValue, Record7<T1, T2, T3, T4, T5, T6, T7> maxValue) {
      return this.betweenSymmetric(minValue).and(maxValue);
   }

   public final Condition betweenSymmetric(Record8<T1, T2, T3, T4, T5, T6, T7, T8> minValue, Record8<T1, T2, T3, T4, T5, T6, T7, T8> maxValue) {
      return this.betweenSymmetric(minValue).and(maxValue);
   }

   public final Condition betweenSymmetric(Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> minValue, Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> maxValue) {
      return this.betweenSymmetric(minValue).and(maxValue);
   }

   public final Condition betweenSymmetric(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> minValue, Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> maxValue) {
      return this.betweenSymmetric(minValue).and(maxValue);
   }

   public final Condition betweenSymmetric(Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> minValue, Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> maxValue) {
      return this.betweenSymmetric(minValue).and(maxValue);
   }

   public final Condition betweenSymmetric(Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> minValue, Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> maxValue) {
      return this.betweenSymmetric(minValue).and(maxValue);
   }

   public final Condition betweenSymmetric(Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> minValue, Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> maxValue) {
      return this.betweenSymmetric(minValue).and(maxValue);
   }

   public final Condition betweenSymmetric(Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> minValue, Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> maxValue) {
      return this.betweenSymmetric(minValue).and(maxValue);
   }

   public final Condition betweenSymmetric(Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> minValue, Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> maxValue) {
      return this.betweenSymmetric(minValue).and(maxValue);
   }

   public final Condition betweenSymmetric(Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> minValue, Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> maxValue) {
      return this.betweenSymmetric(minValue).and(maxValue);
   }

   public final Condition betweenSymmetric(Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> minValue, Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> maxValue) {
      return this.betweenSymmetric(minValue).and(maxValue);
   }

   public final Condition betweenSymmetric(Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> minValue, Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> maxValue) {
      return this.betweenSymmetric(minValue).and(maxValue);
   }

   public final Condition betweenSymmetric(Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> minValue, Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> maxValue) {
      return this.betweenSymmetric(minValue).and(maxValue);
   }

   public final Condition betweenSymmetric(Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> minValue, Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> maxValue) {
      return this.betweenSymmetric(minValue).and(maxValue);
   }

   public final Condition betweenSymmetric(Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> minValue, Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> maxValue) {
      return this.betweenSymmetric(minValue).and(maxValue);
   }

   public final Condition betweenSymmetric(Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> minValue, Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> maxValue) {
      return this.betweenSymmetric(minValue).and(maxValue);
   }

   public final BetweenAndStepN notBetween(Object... values) {
      return this.notBetween(DSL.row(values));
   }

   public final BetweenAndStep1<T1> notBetween(T1 t1) {
      return this.notBetween(DSL.row(t1));
   }

   public final BetweenAndStep2<T1, T2> notBetween(T1 t1, T2 t2) {
      return this.notBetween(DSL.row(t1, t2));
   }

   public final BetweenAndStep3<T1, T2, T3> notBetween(T1 t1, T2 t2, T3 t3) {
      return this.notBetween(DSL.row(t1, t2, t3));
   }

   public final BetweenAndStep4<T1, T2, T3, T4> notBetween(T1 t1, T2 t2, T3 t3, T4 t4) {
      return this.notBetween(DSL.row(t1, t2, t3, t4));
   }

   public final BetweenAndStep5<T1, T2, T3, T4, T5> notBetween(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5) {
      return this.notBetween(DSL.row(t1, t2, t3, t4, t5));
   }

   public final BetweenAndStep6<T1, T2, T3, T4, T5, T6> notBetween(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6) {
      return this.notBetween(DSL.row(t1, t2, t3, t4, t5, t6));
   }

   public final BetweenAndStep7<T1, T2, T3, T4, T5, T6, T7> notBetween(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7) {
      return this.notBetween(DSL.row(t1, t2, t3, t4, t5, t6, t7));
   }

   public final BetweenAndStep8<T1, T2, T3, T4, T5, T6, T7, T8> notBetween(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8) {
      return this.notBetween(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8));
   }

   public final BetweenAndStep9<T1, T2, T3, T4, T5, T6, T7, T8, T9> notBetween(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9) {
      return this.notBetween(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9));
   }

   public final BetweenAndStep10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> notBetween(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10) {
      return this.notBetween(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10));
   }

   public final BetweenAndStep11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> notBetween(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11) {
      return this.notBetween(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11));
   }

   public final BetweenAndStep12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> notBetween(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12) {
      return this.notBetween(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12));
   }

   public final BetweenAndStep13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> notBetween(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13) {
      return this.notBetween(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13));
   }

   public final BetweenAndStep14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> notBetween(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14) {
      return this.notBetween(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14));
   }

   public final BetweenAndStep15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> notBetween(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15) {
      return this.notBetween(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15));
   }

   public final BetweenAndStep16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> notBetween(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16) {
      return this.notBetween(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16));
   }

   public final BetweenAndStep17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> notBetween(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17) {
      return this.notBetween(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17));
   }

   public final BetweenAndStep18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> notBetween(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18) {
      return this.notBetween(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18));
   }

   public final BetweenAndStep19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> notBetween(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19) {
      return this.notBetween(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19));
   }

   public final BetweenAndStep20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> notBetween(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19, T20 t20) {
      return this.notBetween(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20));
   }

   public final BetweenAndStep21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> notBetween(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19, T20 t20, T21 t21) {
      return this.notBetween(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21));
   }

   public final BetweenAndStep22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> notBetween(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19, T20 t20, T21 t21, T22 t22) {
      return this.notBetween(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22));
   }

   public final BetweenAndStepN notBetween(Field<?>... values) {
      return this.notBetween(DSL.row(values));
   }

   public final BetweenAndStep1<T1> notBetween(Field<T1> t1) {
      return this.notBetween(DSL.row(t1));
   }

   public final BetweenAndStep2<T1, T2> notBetween(Field<T1> t1, Field<T2> t2) {
      return this.notBetween(DSL.row(t1, t2));
   }

   public final BetweenAndStep3<T1, T2, T3> notBetween(Field<T1> t1, Field<T2> t2, Field<T3> t3) {
      return this.notBetween(DSL.row(t1, t2, t3));
   }

   public final BetweenAndStep4<T1, T2, T3, T4> notBetween(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4) {
      return this.notBetween(DSL.row(t1, t2, t3, t4));
   }

   public final BetweenAndStep5<T1, T2, T3, T4, T5> notBetween(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5) {
      return this.notBetween(DSL.row(t1, t2, t3, t4, t5));
   }

   public final BetweenAndStep6<T1, T2, T3, T4, T5, T6> notBetween(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6) {
      return this.notBetween(DSL.row(t1, t2, t3, t4, t5, t6));
   }

   public final BetweenAndStep7<T1, T2, T3, T4, T5, T6, T7> notBetween(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7) {
      return this.notBetween(DSL.row(t1, t2, t3, t4, t5, t6, t7));
   }

   public final BetweenAndStep8<T1, T2, T3, T4, T5, T6, T7, T8> notBetween(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8) {
      return this.notBetween(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8));
   }

   public final BetweenAndStep9<T1, T2, T3, T4, T5, T6, T7, T8, T9> notBetween(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9) {
      return this.notBetween(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9));
   }

   public final BetweenAndStep10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> notBetween(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10) {
      return this.notBetween(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10));
   }

   public final BetweenAndStep11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> notBetween(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11) {
      return this.notBetween(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11));
   }

   public final BetweenAndStep12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> notBetween(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12) {
      return this.notBetween(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12));
   }

   public final BetweenAndStep13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> notBetween(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13) {
      return this.notBetween(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13));
   }

   public final BetweenAndStep14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> notBetween(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14) {
      return this.notBetween(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14));
   }

   public final BetweenAndStep15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> notBetween(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15) {
      return this.notBetween(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15));
   }

   public final BetweenAndStep16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> notBetween(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16) {
      return this.notBetween(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16));
   }

   public final BetweenAndStep17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> notBetween(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17) {
      return this.notBetween(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17));
   }

   public final BetweenAndStep18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> notBetween(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18) {
      return this.notBetween(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18));
   }

   public final BetweenAndStep19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> notBetween(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18, Field<T19> t19) {
      return this.notBetween(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19));
   }

   public final BetweenAndStep20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> notBetween(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18, Field<T19> t19, Field<T20> t20) {
      return this.notBetween(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20));
   }

   public final BetweenAndStep21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> notBetween(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18, Field<T19> t19, Field<T20> t20, Field<T21> t21) {
      return this.notBetween(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21));
   }

   public final BetweenAndStep22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> notBetween(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18, Field<T19> t19, Field<T20> t20, Field<T21> t21, Field<T22> t22) {
      return this.notBetween(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22));
   }

   public final BetweenAndStepN notBetween(RowN row) {
      return new RowBetweenCondition(this, row, true, false);
   }

   public final BetweenAndStep1<T1> notBetween(Row1<T1> row) {
      return new RowBetweenCondition(this, row, true, false);
   }

   public final BetweenAndStep2<T1, T2> notBetween(Row2<T1, T2> row) {
      return new RowBetweenCondition(this, row, true, false);
   }

   public final BetweenAndStep3<T1, T2, T3> notBetween(Row3<T1, T2, T3> row) {
      return new RowBetweenCondition(this, row, true, false);
   }

   public final BetweenAndStep4<T1, T2, T3, T4> notBetween(Row4<T1, T2, T3, T4> row) {
      return new RowBetweenCondition(this, row, true, false);
   }

   public final BetweenAndStep5<T1, T2, T3, T4, T5> notBetween(Row5<T1, T2, T3, T4, T5> row) {
      return new RowBetweenCondition(this, row, true, false);
   }

   public final BetweenAndStep6<T1, T2, T3, T4, T5, T6> notBetween(Row6<T1, T2, T3, T4, T5, T6> row) {
      return new RowBetweenCondition(this, row, true, false);
   }

   public final BetweenAndStep7<T1, T2, T3, T4, T5, T6, T7> notBetween(Row7<T1, T2, T3, T4, T5, T6, T7> row) {
      return new RowBetweenCondition(this, row, true, false);
   }

   public final BetweenAndStep8<T1, T2, T3, T4, T5, T6, T7, T8> notBetween(Row8<T1, T2, T3, T4, T5, T6, T7, T8> row) {
      return new RowBetweenCondition(this, row, true, false);
   }

   public final BetweenAndStep9<T1, T2, T3, T4, T5, T6, T7, T8, T9> notBetween(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> row) {
      return new RowBetweenCondition(this, row, true, false);
   }

   public final BetweenAndStep10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> notBetween(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> row) {
      return new RowBetweenCondition(this, row, true, false);
   }

   public final BetweenAndStep11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> notBetween(Row11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> row) {
      return new RowBetweenCondition(this, row, true, false);
   }

   public final BetweenAndStep12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> notBetween(Row12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> row) {
      return new RowBetweenCondition(this, row, true, false);
   }

   public final BetweenAndStep13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> notBetween(Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> row) {
      return new RowBetweenCondition(this, row, true, false);
   }

   public final BetweenAndStep14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> notBetween(Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> row) {
      return new RowBetweenCondition(this, row, true, false);
   }

   public final BetweenAndStep15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> notBetween(Row15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> row) {
      return new RowBetweenCondition(this, row, true, false);
   }

   public final BetweenAndStep16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> notBetween(Row16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> row) {
      return new RowBetweenCondition(this, row, true, false);
   }

   public final BetweenAndStep17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> notBetween(Row17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> row) {
      return new RowBetweenCondition(this, row, true, false);
   }

   public final BetweenAndStep18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> notBetween(Row18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> row) {
      return new RowBetweenCondition(this, row, true, false);
   }

   public final BetweenAndStep19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> notBetween(Row19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> row) {
      return new RowBetweenCondition(this, row, true, false);
   }

   public final BetweenAndStep20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> notBetween(Row20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> row) {
      return new RowBetweenCondition(this, row, true, false);
   }

   public final BetweenAndStep21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> notBetween(Row21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> row) {
      return new RowBetweenCondition(this, row, true, false);
   }

   public final BetweenAndStep22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> notBetween(Row22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> row) {
      return new RowBetweenCondition(this, row, true, false);
   }

   public final BetweenAndStepN notBetween(Record record) {
      return this.notBetween(record.valuesRow());
   }

   public final BetweenAndStep1<T1> notBetween(Record1<T1> record) {
      return this.notBetween(record.valuesRow());
   }

   public final BetweenAndStep2<T1, T2> notBetween(Record2<T1, T2> record) {
      return this.notBetween(record.valuesRow());
   }

   public final BetweenAndStep3<T1, T2, T3> notBetween(Record3<T1, T2, T3> record) {
      return this.notBetween(record.valuesRow());
   }

   public final BetweenAndStep4<T1, T2, T3, T4> notBetween(Record4<T1, T2, T3, T4> record) {
      return this.notBetween(record.valuesRow());
   }

   public final BetweenAndStep5<T1, T2, T3, T4, T5> notBetween(Record5<T1, T2, T3, T4, T5> record) {
      return this.notBetween(record.valuesRow());
   }

   public final BetweenAndStep6<T1, T2, T3, T4, T5, T6> notBetween(Record6<T1, T2, T3, T4, T5, T6> record) {
      return this.notBetween(record.valuesRow());
   }

   public final BetweenAndStep7<T1, T2, T3, T4, T5, T6, T7> notBetween(Record7<T1, T2, T3, T4, T5, T6, T7> record) {
      return this.notBetween(record.valuesRow());
   }

   public final BetweenAndStep8<T1, T2, T3, T4, T5, T6, T7, T8> notBetween(Record8<T1, T2, T3, T4, T5, T6, T7, T8> record) {
      return this.notBetween(record.valuesRow());
   }

   public final BetweenAndStep9<T1, T2, T3, T4, T5, T6, T7, T8, T9> notBetween(Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> record) {
      return this.notBetween(record.valuesRow());
   }

   public final BetweenAndStep10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> notBetween(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> record) {
      return this.notBetween(record.valuesRow());
   }

   public final BetweenAndStep11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> notBetween(Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> record) {
      return this.notBetween(record.valuesRow());
   }

   public final BetweenAndStep12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> notBetween(Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> record) {
      return this.notBetween(record.valuesRow());
   }

   public final BetweenAndStep13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> notBetween(Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> record) {
      return this.notBetween(record.valuesRow());
   }

   public final BetweenAndStep14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> notBetween(Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> record) {
      return this.notBetween(record.valuesRow());
   }

   public final BetweenAndStep15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> notBetween(Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> record) {
      return this.notBetween(record.valuesRow());
   }

   public final BetweenAndStep16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> notBetween(Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> record) {
      return this.notBetween(record.valuesRow());
   }

   public final BetweenAndStep17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> notBetween(Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> record) {
      return this.notBetween(record.valuesRow());
   }

   public final BetweenAndStep18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> notBetween(Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> record) {
      return this.notBetween(record.valuesRow());
   }

   public final BetweenAndStep19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> notBetween(Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> record) {
      return this.notBetween(record.valuesRow());
   }

   public final BetweenAndStep20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> notBetween(Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> record) {
      return this.notBetween(record.valuesRow());
   }

   public final BetweenAndStep21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> notBetween(Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> record) {
      return this.notBetween(record.valuesRow());
   }

   public final BetweenAndStep22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> notBetween(Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> record) {
      return this.notBetween(record.valuesRow());
   }

   public final Condition notBetween(RowN minValue, RowN maxValue) {
      return this.notBetween(minValue).and(maxValue);
   }

   public final Condition notBetween(Row1<T1> minValue, Row1<T1> maxValue) {
      return this.notBetween(minValue).and(maxValue);
   }

   public final Condition notBetween(Row2<T1, T2> minValue, Row2<T1, T2> maxValue) {
      return this.notBetween(minValue).and(maxValue);
   }

   public final Condition notBetween(Row3<T1, T2, T3> minValue, Row3<T1, T2, T3> maxValue) {
      return this.notBetween(minValue).and(maxValue);
   }

   public final Condition notBetween(Row4<T1, T2, T3, T4> minValue, Row4<T1, T2, T3, T4> maxValue) {
      return this.notBetween(minValue).and(maxValue);
   }

   public final Condition notBetween(Row5<T1, T2, T3, T4, T5> minValue, Row5<T1, T2, T3, T4, T5> maxValue) {
      return this.notBetween(minValue).and(maxValue);
   }

   public final Condition notBetween(Row6<T1, T2, T3, T4, T5, T6> minValue, Row6<T1, T2, T3, T4, T5, T6> maxValue) {
      return this.notBetween(minValue).and(maxValue);
   }

   public final Condition notBetween(Row7<T1, T2, T3, T4, T5, T6, T7> minValue, Row7<T1, T2, T3, T4, T5, T6, T7> maxValue) {
      return this.notBetween(minValue).and(maxValue);
   }

   public final Condition notBetween(Row8<T1, T2, T3, T4, T5, T6, T7, T8> minValue, Row8<T1, T2, T3, T4, T5, T6, T7, T8> maxValue) {
      return this.notBetween(minValue).and(maxValue);
   }

   public final Condition notBetween(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> minValue, Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> maxValue) {
      return this.notBetween(minValue).and(maxValue);
   }

   public final Condition notBetween(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> minValue, Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> maxValue) {
      return this.notBetween(minValue).and(maxValue);
   }

   public final Condition notBetween(Row11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> minValue, Row11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> maxValue) {
      return this.notBetween(minValue).and(maxValue);
   }

   public final Condition notBetween(Row12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> minValue, Row12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> maxValue) {
      return this.notBetween(minValue).and(maxValue);
   }

   public final Condition notBetween(Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> minValue, Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> maxValue) {
      return this.notBetween(minValue).and(maxValue);
   }

   public final Condition notBetween(Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> minValue, Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> maxValue) {
      return this.notBetween(minValue).and(maxValue);
   }

   public final Condition notBetween(Row15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> minValue, Row15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> maxValue) {
      return this.notBetween(minValue).and(maxValue);
   }

   public final Condition notBetween(Row16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> minValue, Row16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> maxValue) {
      return this.notBetween(minValue).and(maxValue);
   }

   public final Condition notBetween(Row17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> minValue, Row17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> maxValue) {
      return this.notBetween(minValue).and(maxValue);
   }

   public final Condition notBetween(Row18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> minValue, Row18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> maxValue) {
      return this.notBetween(minValue).and(maxValue);
   }

   public final Condition notBetween(Row19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> minValue, Row19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> maxValue) {
      return this.notBetween(minValue).and(maxValue);
   }

   public final Condition notBetween(Row20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> minValue, Row20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> maxValue) {
      return this.notBetween(minValue).and(maxValue);
   }

   public final Condition notBetween(Row21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> minValue, Row21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> maxValue) {
      return this.notBetween(minValue).and(maxValue);
   }

   public final Condition notBetween(Row22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> minValue, Row22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> maxValue) {
      return this.notBetween(minValue).and(maxValue);
   }

   public final Condition notBetween(Record minValue, Record maxValue) {
      return this.notBetween(minValue).and(maxValue);
   }

   public final Condition notBetween(Record1<T1> minValue, Record1<T1> maxValue) {
      return this.notBetween(minValue).and(maxValue);
   }

   public final Condition notBetween(Record2<T1, T2> minValue, Record2<T1, T2> maxValue) {
      return this.notBetween(minValue).and(maxValue);
   }

   public final Condition notBetween(Record3<T1, T2, T3> minValue, Record3<T1, T2, T3> maxValue) {
      return this.notBetween(minValue).and(maxValue);
   }

   public final Condition notBetween(Record4<T1, T2, T3, T4> minValue, Record4<T1, T2, T3, T4> maxValue) {
      return this.notBetween(minValue).and(maxValue);
   }

   public final Condition notBetween(Record5<T1, T2, T3, T4, T5> minValue, Record5<T1, T2, T3, T4, T5> maxValue) {
      return this.notBetween(minValue).and(maxValue);
   }

   public final Condition notBetween(Record6<T1, T2, T3, T4, T5, T6> minValue, Record6<T1, T2, T3, T4, T5, T6> maxValue) {
      return this.notBetween(minValue).and(maxValue);
   }

   public final Condition notBetween(Record7<T1, T2, T3, T4, T5, T6, T7> minValue, Record7<T1, T2, T3, T4, T5, T6, T7> maxValue) {
      return this.notBetween(minValue).and(maxValue);
   }

   public final Condition notBetween(Record8<T1, T2, T3, T4, T5, T6, T7, T8> minValue, Record8<T1, T2, T3, T4, T5, T6, T7, T8> maxValue) {
      return this.notBetween(minValue).and(maxValue);
   }

   public final Condition notBetween(Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> minValue, Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> maxValue) {
      return this.notBetween(minValue).and(maxValue);
   }

   public final Condition notBetween(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> minValue, Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> maxValue) {
      return this.notBetween(minValue).and(maxValue);
   }

   public final Condition notBetween(Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> minValue, Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> maxValue) {
      return this.notBetween(minValue).and(maxValue);
   }

   public final Condition notBetween(Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> minValue, Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> maxValue) {
      return this.notBetween(minValue).and(maxValue);
   }

   public final Condition notBetween(Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> minValue, Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> maxValue) {
      return this.notBetween(minValue).and(maxValue);
   }

   public final Condition notBetween(Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> minValue, Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> maxValue) {
      return this.notBetween(minValue).and(maxValue);
   }

   public final Condition notBetween(Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> minValue, Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> maxValue) {
      return this.notBetween(minValue).and(maxValue);
   }

   public final Condition notBetween(Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> minValue, Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> maxValue) {
      return this.notBetween(minValue).and(maxValue);
   }

   public final Condition notBetween(Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> minValue, Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> maxValue) {
      return this.notBetween(minValue).and(maxValue);
   }

   public final Condition notBetween(Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> minValue, Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> maxValue) {
      return this.notBetween(minValue).and(maxValue);
   }

   public final Condition notBetween(Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> minValue, Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> maxValue) {
      return this.notBetween(minValue).and(maxValue);
   }

   public final Condition notBetween(Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> minValue, Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> maxValue) {
      return this.notBetween(minValue).and(maxValue);
   }

   public final Condition notBetween(Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> minValue, Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> maxValue) {
      return this.notBetween(minValue).and(maxValue);
   }

   public final Condition notBetween(Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> minValue, Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> maxValue) {
      return this.notBetween(minValue).and(maxValue);
   }

   public final BetweenAndStepN notBetweenSymmetric(Object... values) {
      return this.notBetweenSymmetric(DSL.row(values));
   }

   public final BetweenAndStep1<T1> notBetweenSymmetric(T1 t1) {
      return this.notBetweenSymmetric(DSL.row(t1));
   }

   public final BetweenAndStep2<T1, T2> notBetweenSymmetric(T1 t1, T2 t2) {
      return this.notBetweenSymmetric(DSL.row(t1, t2));
   }

   public final BetweenAndStep3<T1, T2, T3> notBetweenSymmetric(T1 t1, T2 t2, T3 t3) {
      return this.notBetweenSymmetric(DSL.row(t1, t2, t3));
   }

   public final BetweenAndStep4<T1, T2, T3, T4> notBetweenSymmetric(T1 t1, T2 t2, T3 t3, T4 t4) {
      return this.notBetweenSymmetric(DSL.row(t1, t2, t3, t4));
   }

   public final BetweenAndStep5<T1, T2, T3, T4, T5> notBetweenSymmetric(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5) {
      return this.notBetweenSymmetric(DSL.row(t1, t2, t3, t4, t5));
   }

   public final BetweenAndStep6<T1, T2, T3, T4, T5, T6> notBetweenSymmetric(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6) {
      return this.notBetweenSymmetric(DSL.row(t1, t2, t3, t4, t5, t6));
   }

   public final BetweenAndStep7<T1, T2, T3, T4, T5, T6, T7> notBetweenSymmetric(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7) {
      return this.notBetweenSymmetric(DSL.row(t1, t2, t3, t4, t5, t6, t7));
   }

   public final BetweenAndStep8<T1, T2, T3, T4, T5, T6, T7, T8> notBetweenSymmetric(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8) {
      return this.notBetweenSymmetric(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8));
   }

   public final BetweenAndStep9<T1, T2, T3, T4, T5, T6, T7, T8, T9> notBetweenSymmetric(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9) {
      return this.notBetweenSymmetric(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9));
   }

   public final BetweenAndStep10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> notBetweenSymmetric(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10) {
      return this.notBetweenSymmetric(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10));
   }

   public final BetweenAndStep11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> notBetweenSymmetric(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11) {
      return this.notBetweenSymmetric(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11));
   }

   public final BetweenAndStep12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> notBetweenSymmetric(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12) {
      return this.notBetweenSymmetric(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12));
   }

   public final BetweenAndStep13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> notBetweenSymmetric(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13) {
      return this.notBetweenSymmetric(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13));
   }

   public final BetweenAndStep14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> notBetweenSymmetric(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14) {
      return this.notBetweenSymmetric(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14));
   }

   public final BetweenAndStep15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> notBetweenSymmetric(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15) {
      return this.notBetweenSymmetric(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15));
   }

   public final BetweenAndStep16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> notBetweenSymmetric(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16) {
      return this.notBetweenSymmetric(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16));
   }

   public final BetweenAndStep17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> notBetweenSymmetric(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17) {
      return this.notBetweenSymmetric(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17));
   }

   public final BetweenAndStep18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> notBetweenSymmetric(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18) {
      return this.notBetweenSymmetric(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18));
   }

   public final BetweenAndStep19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> notBetweenSymmetric(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19) {
      return this.notBetweenSymmetric(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19));
   }

   public final BetweenAndStep20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> notBetweenSymmetric(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19, T20 t20) {
      return this.notBetweenSymmetric(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20));
   }

   public final BetweenAndStep21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> notBetweenSymmetric(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19, T20 t20, T21 t21) {
      return this.notBetweenSymmetric(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21));
   }

   public final BetweenAndStep22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> notBetweenSymmetric(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19, T20 t20, T21 t21, T22 t22) {
      return this.notBetweenSymmetric(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22));
   }

   public final BetweenAndStepN notBetweenSymmetric(Field<?>... values) {
      return this.notBetweenSymmetric(DSL.row(values));
   }

   public final BetweenAndStep1<T1> notBetweenSymmetric(Field<T1> t1) {
      return this.notBetweenSymmetric(DSL.row(t1));
   }

   public final BetweenAndStep2<T1, T2> notBetweenSymmetric(Field<T1> t1, Field<T2> t2) {
      return this.notBetweenSymmetric(DSL.row(t1, t2));
   }

   public final BetweenAndStep3<T1, T2, T3> notBetweenSymmetric(Field<T1> t1, Field<T2> t2, Field<T3> t3) {
      return this.notBetweenSymmetric(DSL.row(t1, t2, t3));
   }

   public final BetweenAndStep4<T1, T2, T3, T4> notBetweenSymmetric(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4) {
      return this.notBetweenSymmetric(DSL.row(t1, t2, t3, t4));
   }

   public final BetweenAndStep5<T1, T2, T3, T4, T5> notBetweenSymmetric(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5) {
      return this.notBetweenSymmetric(DSL.row(t1, t2, t3, t4, t5));
   }

   public final BetweenAndStep6<T1, T2, T3, T4, T5, T6> notBetweenSymmetric(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6) {
      return this.notBetweenSymmetric(DSL.row(t1, t2, t3, t4, t5, t6));
   }

   public final BetweenAndStep7<T1, T2, T3, T4, T5, T6, T7> notBetweenSymmetric(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7) {
      return this.notBetweenSymmetric(DSL.row(t1, t2, t3, t4, t5, t6, t7));
   }

   public final BetweenAndStep8<T1, T2, T3, T4, T5, T6, T7, T8> notBetweenSymmetric(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8) {
      return this.notBetweenSymmetric(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8));
   }

   public final BetweenAndStep9<T1, T2, T3, T4, T5, T6, T7, T8, T9> notBetweenSymmetric(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9) {
      return this.notBetweenSymmetric(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9));
   }

   public final BetweenAndStep10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> notBetweenSymmetric(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10) {
      return this.notBetweenSymmetric(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10));
   }

   public final BetweenAndStep11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> notBetweenSymmetric(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11) {
      return this.notBetweenSymmetric(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11));
   }

   public final BetweenAndStep12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> notBetweenSymmetric(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12) {
      return this.notBetweenSymmetric(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12));
   }

   public final BetweenAndStep13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> notBetweenSymmetric(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13) {
      return this.notBetweenSymmetric(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13));
   }

   public final BetweenAndStep14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> notBetweenSymmetric(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14) {
      return this.notBetweenSymmetric(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14));
   }

   public final BetweenAndStep15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> notBetweenSymmetric(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15) {
      return this.notBetweenSymmetric(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15));
   }

   public final BetweenAndStep16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> notBetweenSymmetric(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16) {
      return this.notBetweenSymmetric(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16));
   }

   public final BetweenAndStep17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> notBetweenSymmetric(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17) {
      return this.notBetweenSymmetric(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17));
   }

   public final BetweenAndStep18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> notBetweenSymmetric(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18) {
      return this.notBetweenSymmetric(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18));
   }

   public final BetweenAndStep19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> notBetweenSymmetric(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18, Field<T19> t19) {
      return this.notBetweenSymmetric(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19));
   }

   public final BetweenAndStep20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> notBetweenSymmetric(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18, Field<T19> t19, Field<T20> t20) {
      return this.notBetweenSymmetric(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20));
   }

   public final BetweenAndStep21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> notBetweenSymmetric(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18, Field<T19> t19, Field<T20> t20, Field<T21> t21) {
      return this.notBetweenSymmetric(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21));
   }

   public final BetweenAndStep22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> notBetweenSymmetric(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18, Field<T19> t19, Field<T20> t20, Field<T21> t21, Field<T22> t22) {
      return this.notBetweenSymmetric(DSL.row(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22));
   }

   public final BetweenAndStepN notBetweenSymmetric(RowN row) {
      return new RowBetweenCondition(this, row, true, true);
   }

   public final BetweenAndStep1<T1> notBetweenSymmetric(Row1<T1> row) {
      return new RowBetweenCondition(this, row, true, true);
   }

   public final BetweenAndStep2<T1, T2> notBetweenSymmetric(Row2<T1, T2> row) {
      return new RowBetweenCondition(this, row, true, true);
   }

   public final BetweenAndStep3<T1, T2, T3> notBetweenSymmetric(Row3<T1, T2, T3> row) {
      return new RowBetweenCondition(this, row, true, true);
   }

   public final BetweenAndStep4<T1, T2, T3, T4> notBetweenSymmetric(Row4<T1, T2, T3, T4> row) {
      return new RowBetweenCondition(this, row, true, true);
   }

   public final BetweenAndStep5<T1, T2, T3, T4, T5> notBetweenSymmetric(Row5<T1, T2, T3, T4, T5> row) {
      return new RowBetweenCondition(this, row, true, true);
   }

   public final BetweenAndStep6<T1, T2, T3, T4, T5, T6> notBetweenSymmetric(Row6<T1, T2, T3, T4, T5, T6> row) {
      return new RowBetweenCondition(this, row, true, true);
   }

   public final BetweenAndStep7<T1, T2, T3, T4, T5, T6, T7> notBetweenSymmetric(Row7<T1, T2, T3, T4, T5, T6, T7> row) {
      return new RowBetweenCondition(this, row, true, true);
   }

   public final BetweenAndStep8<T1, T2, T3, T4, T5, T6, T7, T8> notBetweenSymmetric(Row8<T1, T2, T3, T4, T5, T6, T7, T8> row) {
      return new RowBetweenCondition(this, row, true, true);
   }

   public final BetweenAndStep9<T1, T2, T3, T4, T5, T6, T7, T8, T9> notBetweenSymmetric(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> row) {
      return new RowBetweenCondition(this, row, true, true);
   }

   public final BetweenAndStep10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> notBetweenSymmetric(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> row) {
      return new RowBetweenCondition(this, row, true, true);
   }

   public final BetweenAndStep11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> notBetweenSymmetric(Row11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> row) {
      return new RowBetweenCondition(this, row, true, true);
   }

   public final BetweenAndStep12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> notBetweenSymmetric(Row12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> row) {
      return new RowBetweenCondition(this, row, true, true);
   }

   public final BetweenAndStep13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> notBetweenSymmetric(Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> row) {
      return new RowBetweenCondition(this, row, true, true);
   }

   public final BetweenAndStep14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> notBetweenSymmetric(Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> row) {
      return new RowBetweenCondition(this, row, true, true);
   }

   public final BetweenAndStep15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> notBetweenSymmetric(Row15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> row) {
      return new RowBetweenCondition(this, row, true, true);
   }

   public final BetweenAndStep16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> notBetweenSymmetric(Row16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> row) {
      return new RowBetweenCondition(this, row, true, true);
   }

   public final BetweenAndStep17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> notBetweenSymmetric(Row17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> row) {
      return new RowBetweenCondition(this, row, true, true);
   }

   public final BetweenAndStep18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> notBetweenSymmetric(Row18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> row) {
      return new RowBetweenCondition(this, row, true, true);
   }

   public final BetweenAndStep19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> notBetweenSymmetric(Row19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> row) {
      return new RowBetweenCondition(this, row, true, true);
   }

   public final BetweenAndStep20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> notBetweenSymmetric(Row20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> row) {
      return new RowBetweenCondition(this, row, true, true);
   }

   public final BetweenAndStep21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> notBetweenSymmetric(Row21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> row) {
      return new RowBetweenCondition(this, row, true, true);
   }

   public final BetweenAndStep22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> notBetweenSymmetric(Row22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> row) {
      return new RowBetweenCondition(this, row, true, true);
   }

   public final BetweenAndStepN notBetweenSymmetric(Record record) {
      return this.notBetweenSymmetric(record.valuesRow());
   }

   public final BetweenAndStep1<T1> notBetweenSymmetric(Record1<T1> record) {
      return this.notBetweenSymmetric(record.valuesRow());
   }

   public final BetweenAndStep2<T1, T2> notBetweenSymmetric(Record2<T1, T2> record) {
      return this.notBetweenSymmetric(record.valuesRow());
   }

   public final BetweenAndStep3<T1, T2, T3> notBetweenSymmetric(Record3<T1, T2, T3> record) {
      return this.notBetweenSymmetric(record.valuesRow());
   }

   public final BetweenAndStep4<T1, T2, T3, T4> notBetweenSymmetric(Record4<T1, T2, T3, T4> record) {
      return this.notBetweenSymmetric(record.valuesRow());
   }

   public final BetweenAndStep5<T1, T2, T3, T4, T5> notBetweenSymmetric(Record5<T1, T2, T3, T4, T5> record) {
      return this.notBetweenSymmetric(record.valuesRow());
   }

   public final BetweenAndStep6<T1, T2, T3, T4, T5, T6> notBetweenSymmetric(Record6<T1, T2, T3, T4, T5, T6> record) {
      return this.notBetweenSymmetric(record.valuesRow());
   }

   public final BetweenAndStep7<T1, T2, T3, T4, T5, T6, T7> notBetweenSymmetric(Record7<T1, T2, T3, T4, T5, T6, T7> record) {
      return this.notBetweenSymmetric(record.valuesRow());
   }

   public final BetweenAndStep8<T1, T2, T3, T4, T5, T6, T7, T8> notBetweenSymmetric(Record8<T1, T2, T3, T4, T5, T6, T7, T8> record) {
      return this.notBetweenSymmetric(record.valuesRow());
   }

   public final BetweenAndStep9<T1, T2, T3, T4, T5, T6, T7, T8, T9> notBetweenSymmetric(Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> record) {
      return this.notBetweenSymmetric(record.valuesRow());
   }

   public final BetweenAndStep10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> notBetweenSymmetric(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> record) {
      return this.notBetweenSymmetric(record.valuesRow());
   }

   public final BetweenAndStep11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> notBetweenSymmetric(Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> record) {
      return this.notBetweenSymmetric(record.valuesRow());
   }

   public final BetweenAndStep12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> notBetweenSymmetric(Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> record) {
      return this.notBetweenSymmetric(record.valuesRow());
   }

   public final BetweenAndStep13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> notBetweenSymmetric(Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> record) {
      return this.notBetweenSymmetric(record.valuesRow());
   }

   public final BetweenAndStep14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> notBetweenSymmetric(Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> record) {
      return this.notBetweenSymmetric(record.valuesRow());
   }

   public final BetweenAndStep15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> notBetweenSymmetric(Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> record) {
      return this.notBetweenSymmetric(record.valuesRow());
   }

   public final BetweenAndStep16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> notBetweenSymmetric(Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> record) {
      return this.notBetweenSymmetric(record.valuesRow());
   }

   public final BetweenAndStep17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> notBetweenSymmetric(Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> record) {
      return this.notBetweenSymmetric(record.valuesRow());
   }

   public final BetweenAndStep18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> notBetweenSymmetric(Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> record) {
      return this.notBetweenSymmetric(record.valuesRow());
   }

   public final BetweenAndStep19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> notBetweenSymmetric(Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> record) {
      return this.notBetweenSymmetric(record.valuesRow());
   }

   public final BetweenAndStep20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> notBetweenSymmetric(Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> record) {
      return this.notBetweenSymmetric(record.valuesRow());
   }

   public final BetweenAndStep21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> notBetweenSymmetric(Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> record) {
      return this.notBetweenSymmetric(record.valuesRow());
   }

   public final BetweenAndStep22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> notBetweenSymmetric(Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> record) {
      return this.notBetweenSymmetric(record.valuesRow());
   }

   public final Condition notBetweenSymmetric(RowN minValue, RowN maxValue) {
      return this.notBetweenSymmetric(minValue).and(maxValue);
   }

   public final Condition notBetweenSymmetric(Row1<T1> minValue, Row1<T1> maxValue) {
      return this.notBetweenSymmetric(minValue).and(maxValue);
   }

   public final Condition notBetweenSymmetric(Row2<T1, T2> minValue, Row2<T1, T2> maxValue) {
      return this.notBetweenSymmetric(minValue).and(maxValue);
   }

   public final Condition notBetweenSymmetric(Row3<T1, T2, T3> minValue, Row3<T1, T2, T3> maxValue) {
      return this.notBetweenSymmetric(minValue).and(maxValue);
   }

   public final Condition notBetweenSymmetric(Row4<T1, T2, T3, T4> minValue, Row4<T1, T2, T3, T4> maxValue) {
      return this.notBetweenSymmetric(minValue).and(maxValue);
   }

   public final Condition notBetweenSymmetric(Row5<T1, T2, T3, T4, T5> minValue, Row5<T1, T2, T3, T4, T5> maxValue) {
      return this.notBetweenSymmetric(minValue).and(maxValue);
   }

   public final Condition notBetweenSymmetric(Row6<T1, T2, T3, T4, T5, T6> minValue, Row6<T1, T2, T3, T4, T5, T6> maxValue) {
      return this.notBetweenSymmetric(minValue).and(maxValue);
   }

   public final Condition notBetweenSymmetric(Row7<T1, T2, T3, T4, T5, T6, T7> minValue, Row7<T1, T2, T3, T4, T5, T6, T7> maxValue) {
      return this.notBetweenSymmetric(minValue).and(maxValue);
   }

   public final Condition notBetweenSymmetric(Row8<T1, T2, T3, T4, T5, T6, T7, T8> minValue, Row8<T1, T2, T3, T4, T5, T6, T7, T8> maxValue) {
      return this.notBetweenSymmetric(minValue).and(maxValue);
   }

   public final Condition notBetweenSymmetric(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> minValue, Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> maxValue) {
      return this.notBetweenSymmetric(minValue).and(maxValue);
   }

   public final Condition notBetweenSymmetric(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> minValue, Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> maxValue) {
      return this.notBetweenSymmetric(minValue).and(maxValue);
   }

   public final Condition notBetweenSymmetric(Row11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> minValue, Row11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> maxValue) {
      return this.notBetweenSymmetric(minValue).and(maxValue);
   }

   public final Condition notBetweenSymmetric(Row12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> minValue, Row12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> maxValue) {
      return this.notBetweenSymmetric(minValue).and(maxValue);
   }

   public final Condition notBetweenSymmetric(Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> minValue, Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> maxValue) {
      return this.notBetweenSymmetric(minValue).and(maxValue);
   }

   public final Condition notBetweenSymmetric(Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> minValue, Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> maxValue) {
      return this.notBetweenSymmetric(minValue).and(maxValue);
   }

   public final Condition notBetweenSymmetric(Row15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> minValue, Row15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> maxValue) {
      return this.notBetweenSymmetric(minValue).and(maxValue);
   }

   public final Condition notBetweenSymmetric(Row16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> minValue, Row16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> maxValue) {
      return this.notBetweenSymmetric(minValue).and(maxValue);
   }

   public final Condition notBetweenSymmetric(Row17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> minValue, Row17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> maxValue) {
      return this.notBetweenSymmetric(minValue).and(maxValue);
   }

   public final Condition notBetweenSymmetric(Row18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> minValue, Row18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> maxValue) {
      return this.notBetweenSymmetric(minValue).and(maxValue);
   }

   public final Condition notBetweenSymmetric(Row19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> minValue, Row19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> maxValue) {
      return this.notBetweenSymmetric(minValue).and(maxValue);
   }

   public final Condition notBetweenSymmetric(Row20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> minValue, Row20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> maxValue) {
      return this.notBetweenSymmetric(minValue).and(maxValue);
   }

   public final Condition notBetweenSymmetric(Row21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> minValue, Row21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> maxValue) {
      return this.notBetweenSymmetric(minValue).and(maxValue);
   }

   public final Condition notBetweenSymmetric(Row22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> minValue, Row22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> maxValue) {
      return this.notBetweenSymmetric(minValue).and(maxValue);
   }

   public final Condition notBetweenSymmetric(Record minValue, Record maxValue) {
      return this.notBetweenSymmetric(minValue).and(maxValue);
   }

   public final Condition notBetweenSymmetric(Record1<T1> minValue, Record1<T1> maxValue) {
      return this.notBetweenSymmetric(minValue).and(maxValue);
   }

   public final Condition notBetweenSymmetric(Record2<T1, T2> minValue, Record2<T1, T2> maxValue) {
      return this.notBetweenSymmetric(minValue).and(maxValue);
   }

   public final Condition notBetweenSymmetric(Record3<T1, T2, T3> minValue, Record3<T1, T2, T3> maxValue) {
      return this.notBetweenSymmetric(minValue).and(maxValue);
   }

   public final Condition notBetweenSymmetric(Record4<T1, T2, T3, T4> minValue, Record4<T1, T2, T3, T4> maxValue) {
      return this.notBetweenSymmetric(minValue).and(maxValue);
   }

   public final Condition notBetweenSymmetric(Record5<T1, T2, T3, T4, T5> minValue, Record5<T1, T2, T3, T4, T5> maxValue) {
      return this.notBetweenSymmetric(minValue).and(maxValue);
   }

   public final Condition notBetweenSymmetric(Record6<T1, T2, T3, T4, T5, T6> minValue, Record6<T1, T2, T3, T4, T5, T6> maxValue) {
      return this.notBetweenSymmetric(minValue).and(maxValue);
   }

   public final Condition notBetweenSymmetric(Record7<T1, T2, T3, T4, T5, T6, T7> minValue, Record7<T1, T2, T3, T4, T5, T6, T7> maxValue) {
      return this.notBetweenSymmetric(minValue).and(maxValue);
   }

   public final Condition notBetweenSymmetric(Record8<T1, T2, T3, T4, T5, T6, T7, T8> minValue, Record8<T1, T2, T3, T4, T5, T6, T7, T8> maxValue) {
      return this.notBetweenSymmetric(minValue).and(maxValue);
   }

   public final Condition notBetweenSymmetric(Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> minValue, Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> maxValue) {
      return this.notBetweenSymmetric(minValue).and(maxValue);
   }

   public final Condition notBetweenSymmetric(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> minValue, Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> maxValue) {
      return this.notBetweenSymmetric(minValue).and(maxValue);
   }

   public final Condition notBetweenSymmetric(Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> minValue, Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> maxValue) {
      return this.notBetweenSymmetric(minValue).and(maxValue);
   }

   public final Condition notBetweenSymmetric(Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> minValue, Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> maxValue) {
      return this.notBetweenSymmetric(minValue).and(maxValue);
   }

   public final Condition notBetweenSymmetric(Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> minValue, Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> maxValue) {
      return this.notBetweenSymmetric(minValue).and(maxValue);
   }

   public final Condition notBetweenSymmetric(Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> minValue, Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> maxValue) {
      return this.notBetweenSymmetric(minValue).and(maxValue);
   }

   public final Condition notBetweenSymmetric(Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> minValue, Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> maxValue) {
      return this.notBetweenSymmetric(minValue).and(maxValue);
   }

   public final Condition notBetweenSymmetric(Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> minValue, Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> maxValue) {
      return this.notBetweenSymmetric(minValue).and(maxValue);
   }

   public final Condition notBetweenSymmetric(Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> minValue, Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> maxValue) {
      return this.notBetweenSymmetric(minValue).and(maxValue);
   }

   public final Condition notBetweenSymmetric(Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> minValue, Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> maxValue) {
      return this.notBetweenSymmetric(minValue).and(maxValue);
   }

   public final Condition notBetweenSymmetric(Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> minValue, Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> maxValue) {
      return this.notBetweenSymmetric(minValue).and(maxValue);
   }

   public final Condition notBetweenSymmetric(Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> minValue, Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> maxValue) {
      return this.notBetweenSymmetric(minValue).and(maxValue);
   }

   public final Condition notBetweenSymmetric(Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> minValue, Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> maxValue) {
      return this.notBetweenSymmetric(minValue).and(maxValue);
   }

   public final Condition notBetweenSymmetric(Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> minValue, Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> maxValue) {
      return this.notBetweenSymmetric(minValue).and(maxValue);
   }

   public final Condition in(RowN... rows) {
      return this.in((Collection)Arrays.asList(rows));
   }

   public final Condition in(Row1<T1>... rows) {
      return this.in((Collection)Arrays.asList(rows));
   }

   public final Condition in(Row2<T1, T2>... rows) {
      return this.in((Collection)Arrays.asList(rows));
   }

   public final Condition in(Row3<T1, T2, T3>... rows) {
      return this.in((Collection)Arrays.asList(rows));
   }

   public final Condition in(Row4<T1, T2, T3, T4>... rows) {
      return this.in((Collection)Arrays.asList(rows));
   }

   public final Condition in(Row5<T1, T2, T3, T4, T5>... rows) {
      return this.in((Collection)Arrays.asList(rows));
   }

   public final Condition in(Row6<T1, T2, T3, T4, T5, T6>... rows) {
      return this.in((Collection)Arrays.asList(rows));
   }

   public final Condition in(Row7<T1, T2, T3, T4, T5, T6, T7>... rows) {
      return this.in((Collection)Arrays.asList(rows));
   }

   public final Condition in(Row8<T1, T2, T3, T4, T5, T6, T7, T8>... rows) {
      return this.in((Collection)Arrays.asList(rows));
   }

   public final Condition in(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9>... rows) {
      return this.in((Collection)Arrays.asList(rows));
   }

   public final Condition in(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>... rows) {
      return this.in((Collection)Arrays.asList(rows));
   }

   public final Condition in(Row11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>... rows) {
      return this.in((Collection)Arrays.asList(rows));
   }

   public final Condition in(Row12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>... rows) {
      return this.in((Collection)Arrays.asList(rows));
   }

   public final Condition in(Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>... rows) {
      return this.in((Collection)Arrays.asList(rows));
   }

   public final Condition in(Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>... rows) {
      return this.in((Collection)Arrays.asList(rows));
   }

   public final Condition in(Row15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>... rows) {
      return this.in((Collection)Arrays.asList(rows));
   }

   public final Condition in(Row16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>... rows) {
      return this.in((Collection)Arrays.asList(rows));
   }

   public final Condition in(Row17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>... rows) {
      return this.in((Collection)Arrays.asList(rows));
   }

   public final Condition in(Row18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>... rows) {
      return this.in((Collection)Arrays.asList(rows));
   }

   public final Condition in(Row19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>... rows) {
      return this.in((Collection)Arrays.asList(rows));
   }

   public final Condition in(Row20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>... rows) {
      return this.in((Collection)Arrays.asList(rows));
   }

   public final Condition in(Row21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>... rows) {
      return this.in((Collection)Arrays.asList(rows));
   }

   public final Condition in(Row22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>... rows) {
      return this.in((Collection)Arrays.asList(rows));
   }

   public final Condition in(Record... records) {
      RowN[] rows = new RowN[records.length];

      for(int i = 0; i < records.length; ++i) {
         rows[i] = (RowN)records[i].valuesRow();
      }

      return this.in(rows);
   }

   public final Condition in(Record1<T1>... records) {
      Row1<T1>[] rows = new Row1[records.length];

      for(int i = 0; i < records.length; ++i) {
         rows[i] = records[i].valuesRow();
      }

      return this.in(rows);
   }

   public final Condition in(Record2<T1, T2>... records) {
      Row2<T1, T2>[] rows = new Row2[records.length];

      for(int i = 0; i < records.length; ++i) {
         rows[i] = records[i].valuesRow();
      }

      return this.in(rows);
   }

   public final Condition in(Record3<T1, T2, T3>... records) {
      Row3<T1, T2, T3>[] rows = new Row3[records.length];

      for(int i = 0; i < records.length; ++i) {
         rows[i] = records[i].valuesRow();
      }

      return this.in(rows);
   }

   public final Condition in(Record4<T1, T2, T3, T4>... records) {
      Row4<T1, T2, T3, T4>[] rows = new Row4[records.length];

      for(int i = 0; i < records.length; ++i) {
         rows[i] = records[i].valuesRow();
      }

      return this.in(rows);
   }

   public final Condition in(Record5<T1, T2, T3, T4, T5>... records) {
      Row5<T1, T2, T3, T4, T5>[] rows = new Row5[records.length];

      for(int i = 0; i < records.length; ++i) {
         rows[i] = records[i].valuesRow();
      }

      return this.in(rows);
   }

   public final Condition in(Record6<T1, T2, T3, T4, T5, T6>... records) {
      Row6<T1, T2, T3, T4, T5, T6>[] rows = new Row6[records.length];

      for(int i = 0; i < records.length; ++i) {
         rows[i] = records[i].valuesRow();
      }

      return this.in(rows);
   }

   public final Condition in(Record7<T1, T2, T3, T4, T5, T6, T7>... records) {
      Row7<T1, T2, T3, T4, T5, T6, T7>[] rows = new Row7[records.length];

      for(int i = 0; i < records.length; ++i) {
         rows[i] = records[i].valuesRow();
      }

      return this.in(rows);
   }

   public final Condition in(Record8<T1, T2, T3, T4, T5, T6, T7, T8>... records) {
      Row8<T1, T2, T3, T4, T5, T6, T7, T8>[] rows = new Row8[records.length];

      for(int i = 0; i < records.length; ++i) {
         rows[i] = records[i].valuesRow();
      }

      return this.in(rows);
   }

   public final Condition in(Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>... records) {
      Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9>[] rows = new Row9[records.length];

      for(int i = 0; i < records.length; ++i) {
         rows[i] = records[i].valuesRow();
      }

      return this.in(rows);
   }

   public final Condition in(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>... records) {
      Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>[] rows = new Row10[records.length];

      for(int i = 0; i < records.length; ++i) {
         rows[i] = records[i].valuesRow();
      }

      return this.in(rows);
   }

   public final Condition in(Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>... records) {
      Row11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>[] rows = new Row11[records.length];

      for(int i = 0; i < records.length; ++i) {
         rows[i] = records[i].valuesRow();
      }

      return this.in(rows);
   }

   public final Condition in(Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>... records) {
      Row12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>[] rows = new Row12[records.length];

      for(int i = 0; i < records.length; ++i) {
         rows[i] = records[i].valuesRow();
      }

      return this.in(rows);
   }

   public final Condition in(Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>... records) {
      Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>[] rows = new Row13[records.length];

      for(int i = 0; i < records.length; ++i) {
         rows[i] = records[i].valuesRow();
      }

      return this.in(rows);
   }

   public final Condition in(Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>... records) {
      Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>[] rows = new Row14[records.length];

      for(int i = 0; i < records.length; ++i) {
         rows[i] = records[i].valuesRow();
      }

      return this.in(rows);
   }

   public final Condition in(Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>... records) {
      Row15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>[] rows = new Row15[records.length];

      for(int i = 0; i < records.length; ++i) {
         rows[i] = records[i].valuesRow();
      }

      return this.in(rows);
   }

   public final Condition in(Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>... records) {
      Row16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>[] rows = new Row16[records.length];

      for(int i = 0; i < records.length; ++i) {
         rows[i] = records[i].valuesRow();
      }

      return this.in(rows);
   }

   public final Condition in(Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>... records) {
      Row17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>[] rows = new Row17[records.length];

      for(int i = 0; i < records.length; ++i) {
         rows[i] = records[i].valuesRow();
      }

      return this.in(rows);
   }

   public final Condition in(Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>... records) {
      Row18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>[] rows = new Row18[records.length];

      for(int i = 0; i < records.length; ++i) {
         rows[i] = records[i].valuesRow();
      }

      return this.in(rows);
   }

   public final Condition in(Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>... records) {
      Row19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>[] rows = new Row19[records.length];

      for(int i = 0; i < records.length; ++i) {
         rows[i] = records[i].valuesRow();
      }

      return this.in(rows);
   }

   public final Condition in(Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>... records) {
      Row20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>[] rows = new Row20[records.length];

      for(int i = 0; i < records.length; ++i) {
         rows[i] = records[i].valuesRow();
      }

      return this.in(rows);
   }

   public final Condition in(Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>... records) {
      Row21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>[] rows = new Row21[records.length];

      for(int i = 0; i < records.length; ++i) {
         rows[i] = records[i].valuesRow();
      }

      return this.in(rows);
   }

   public final Condition in(Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>... records) {
      Row22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>[] rows = new Row22[records.length];

      for(int i = 0; i < records.length; ++i) {
         rows[i] = records[i].valuesRow();
      }

      return this.in(rows);
   }

   public final Condition notIn(RowN... rows) {
      return this.notIn((Collection)Arrays.asList(rows));
   }

   public final Condition notIn(Row1<T1>... rows) {
      return this.notIn((Collection)Arrays.asList(rows));
   }

   public final Condition notIn(Row2<T1, T2>... rows) {
      return this.notIn((Collection)Arrays.asList(rows));
   }

   public final Condition notIn(Row3<T1, T2, T3>... rows) {
      return this.notIn((Collection)Arrays.asList(rows));
   }

   public final Condition notIn(Row4<T1, T2, T3, T4>... rows) {
      return this.notIn((Collection)Arrays.asList(rows));
   }

   public final Condition notIn(Row5<T1, T2, T3, T4, T5>... rows) {
      return this.notIn((Collection)Arrays.asList(rows));
   }

   public final Condition notIn(Row6<T1, T2, T3, T4, T5, T6>... rows) {
      return this.notIn((Collection)Arrays.asList(rows));
   }

   public final Condition notIn(Row7<T1, T2, T3, T4, T5, T6, T7>... rows) {
      return this.notIn((Collection)Arrays.asList(rows));
   }

   public final Condition notIn(Row8<T1, T2, T3, T4, T5, T6, T7, T8>... rows) {
      return this.notIn((Collection)Arrays.asList(rows));
   }

   public final Condition notIn(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9>... rows) {
      return this.notIn((Collection)Arrays.asList(rows));
   }

   public final Condition notIn(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>... rows) {
      return this.notIn((Collection)Arrays.asList(rows));
   }

   public final Condition notIn(Row11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>... rows) {
      return this.notIn((Collection)Arrays.asList(rows));
   }

   public final Condition notIn(Row12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>... rows) {
      return this.notIn((Collection)Arrays.asList(rows));
   }

   public final Condition notIn(Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>... rows) {
      return this.notIn((Collection)Arrays.asList(rows));
   }

   public final Condition notIn(Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>... rows) {
      return this.notIn((Collection)Arrays.asList(rows));
   }

   public final Condition notIn(Row15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>... rows) {
      return this.notIn((Collection)Arrays.asList(rows));
   }

   public final Condition notIn(Row16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>... rows) {
      return this.notIn((Collection)Arrays.asList(rows));
   }

   public final Condition notIn(Row17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>... rows) {
      return this.notIn((Collection)Arrays.asList(rows));
   }

   public final Condition notIn(Row18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>... rows) {
      return this.notIn((Collection)Arrays.asList(rows));
   }

   public final Condition notIn(Row19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>... rows) {
      return this.notIn((Collection)Arrays.asList(rows));
   }

   public final Condition notIn(Row20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>... rows) {
      return this.notIn((Collection)Arrays.asList(rows));
   }

   public final Condition notIn(Row21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>... rows) {
      return this.notIn((Collection)Arrays.asList(rows));
   }

   public final Condition notIn(Row22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>... rows) {
      return this.notIn((Collection)Arrays.asList(rows));
   }

   public final Condition notIn(Record... records) {
      RowN[] rows = new RowN[records.length];

      for(int i = 0; i < records.length; ++i) {
         rows[i] = (RowN)records[i].valuesRow();
      }

      return this.notIn(rows);
   }

   public final Condition notIn(Record1<T1>... records) {
      Row1<T1>[] rows = new Row1[records.length];

      for(int i = 0; i < records.length; ++i) {
         rows[i] = records[i].valuesRow();
      }

      return this.notIn(rows);
   }

   public final Condition notIn(Record2<T1, T2>... records) {
      Row2<T1, T2>[] rows = new Row2[records.length];

      for(int i = 0; i < records.length; ++i) {
         rows[i] = records[i].valuesRow();
      }

      return this.notIn(rows);
   }

   public final Condition notIn(Record3<T1, T2, T3>... records) {
      Row3<T1, T2, T3>[] rows = new Row3[records.length];

      for(int i = 0; i < records.length; ++i) {
         rows[i] = records[i].valuesRow();
      }

      return this.notIn(rows);
   }

   public final Condition notIn(Record4<T1, T2, T3, T4>... records) {
      Row4<T1, T2, T3, T4>[] rows = new Row4[records.length];

      for(int i = 0; i < records.length; ++i) {
         rows[i] = records[i].valuesRow();
      }

      return this.notIn(rows);
   }

   public final Condition notIn(Record5<T1, T2, T3, T4, T5>... records) {
      Row5<T1, T2, T3, T4, T5>[] rows = new Row5[records.length];

      for(int i = 0; i < records.length; ++i) {
         rows[i] = records[i].valuesRow();
      }

      return this.notIn(rows);
   }

   public final Condition notIn(Record6<T1, T2, T3, T4, T5, T6>... records) {
      Row6<T1, T2, T3, T4, T5, T6>[] rows = new Row6[records.length];

      for(int i = 0; i < records.length; ++i) {
         rows[i] = records[i].valuesRow();
      }

      return this.notIn(rows);
   }

   public final Condition notIn(Record7<T1, T2, T3, T4, T5, T6, T7>... records) {
      Row7<T1, T2, T3, T4, T5, T6, T7>[] rows = new Row7[records.length];

      for(int i = 0; i < records.length; ++i) {
         rows[i] = records[i].valuesRow();
      }

      return this.notIn(rows);
   }

   public final Condition notIn(Record8<T1, T2, T3, T4, T5, T6, T7, T8>... records) {
      Row8<T1, T2, T3, T4, T5, T6, T7, T8>[] rows = new Row8[records.length];

      for(int i = 0; i < records.length; ++i) {
         rows[i] = records[i].valuesRow();
      }

      return this.notIn(rows);
   }

   public final Condition notIn(Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>... records) {
      Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9>[] rows = new Row9[records.length];

      for(int i = 0; i < records.length; ++i) {
         rows[i] = records[i].valuesRow();
      }

      return this.notIn(rows);
   }

   public final Condition notIn(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>... records) {
      Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>[] rows = new Row10[records.length];

      for(int i = 0; i < records.length; ++i) {
         rows[i] = records[i].valuesRow();
      }

      return this.notIn(rows);
   }

   public final Condition notIn(Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>... records) {
      Row11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>[] rows = new Row11[records.length];

      for(int i = 0; i < records.length; ++i) {
         rows[i] = records[i].valuesRow();
      }

      return this.notIn(rows);
   }

   public final Condition notIn(Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>... records) {
      Row12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>[] rows = new Row12[records.length];

      for(int i = 0; i < records.length; ++i) {
         rows[i] = records[i].valuesRow();
      }

      return this.notIn(rows);
   }

   public final Condition notIn(Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>... records) {
      Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>[] rows = new Row13[records.length];

      for(int i = 0; i < records.length; ++i) {
         rows[i] = records[i].valuesRow();
      }

      return this.notIn(rows);
   }

   public final Condition notIn(Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>... records) {
      Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>[] rows = new Row14[records.length];

      for(int i = 0; i < records.length; ++i) {
         rows[i] = records[i].valuesRow();
      }

      return this.notIn(rows);
   }

   public final Condition notIn(Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>... records) {
      Row15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>[] rows = new Row15[records.length];

      for(int i = 0; i < records.length; ++i) {
         rows[i] = records[i].valuesRow();
      }

      return this.notIn(rows);
   }

   public final Condition notIn(Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>... records) {
      Row16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>[] rows = new Row16[records.length];

      for(int i = 0; i < records.length; ++i) {
         rows[i] = records[i].valuesRow();
      }

      return this.notIn(rows);
   }

   public final Condition notIn(Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>... records) {
      Row17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>[] rows = new Row17[records.length];

      for(int i = 0; i < records.length; ++i) {
         rows[i] = records[i].valuesRow();
      }

      return this.notIn(rows);
   }

   public final Condition notIn(Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>... records) {
      Row18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>[] rows = new Row18[records.length];

      for(int i = 0; i < records.length; ++i) {
         rows[i] = records[i].valuesRow();
      }

      return this.notIn(rows);
   }

   public final Condition notIn(Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>... records) {
      Row19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>[] rows = new Row19[records.length];

      for(int i = 0; i < records.length; ++i) {
         rows[i] = records[i].valuesRow();
      }

      return this.notIn(rows);
   }

   public final Condition notIn(Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>... records) {
      Row20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>[] rows = new Row20[records.length];

      for(int i = 0; i < records.length; ++i) {
         rows[i] = records[i].valuesRow();
      }

      return this.notIn(rows);
   }

   public final Condition notIn(Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>... records) {
      Row21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>[] rows = new Row21[records.length];

      for(int i = 0; i < records.length; ++i) {
         rows[i] = records[i].valuesRow();
      }

      return this.notIn(rows);
   }

   public final Condition notIn(Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>... records) {
      Row22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>[] rows = new Row22[records.length];

      for(int i = 0; i < records.length; ++i) {
         rows[i] = records[i].valuesRow();
      }

      return this.notIn(rows);
   }

   public final Condition in(Collection rows) {
      QueryPartList<Row> list = new QueryPartList(rows);
      return new RowInCondition(this, list, Comparator.IN);
   }

   public final Condition in(Result result) {
      QueryPartList<Row> list = new QueryPartList(Tools.rows(result));
      return new RowInCondition(this, list, Comparator.IN);
   }

   public final Condition notIn(Collection rows) {
      QueryPartList<Row> list = new QueryPartList(rows);
      return new RowInCondition(this, list, Comparator.NOT_IN);
   }

   public final Condition notIn(Result result) {
      QueryPartList<Row> list = new QueryPartList(Tools.rows(result));
      return new RowInCondition(this, list, Comparator.NOT_IN);
   }

   public final Condition equal(Select select) {
      return this.compare(Comparator.EQUALS, select);
   }

   public final Condition equal(QuantifiedSelect select) {
      return this.compare(Comparator.EQUALS, select);
   }

   public final Condition eq(Select select) {
      return this.equal(select);
   }

   public final Condition eq(QuantifiedSelect select) {
      return this.equal(select);
   }

   public final Condition notEqual(Select select) {
      return this.compare(Comparator.NOT_EQUALS, select);
   }

   public final Condition notEqual(QuantifiedSelect select) {
      return this.compare(Comparator.NOT_EQUALS, select);
   }

   public final Condition ne(Select select) {
      return this.notEqual(select);
   }

   public final Condition ne(QuantifiedSelect select) {
      return this.notEqual(select);
   }

   public final Condition greaterThan(Select select) {
      return this.compare(Comparator.GREATER, select);
   }

   public final Condition greaterThan(QuantifiedSelect select) {
      return this.compare(Comparator.GREATER, select);
   }

   public final Condition gt(Select select) {
      return this.greaterThan(select);
   }

   public final Condition gt(QuantifiedSelect select) {
      return this.greaterThan(select);
   }

   public final Condition greaterOrEqual(Select select) {
      return this.compare(Comparator.GREATER_OR_EQUAL, select);
   }

   public final Condition greaterOrEqual(QuantifiedSelect select) {
      return this.compare(Comparator.GREATER_OR_EQUAL, select);
   }

   public final Condition ge(Select select) {
      return this.greaterOrEqual(select);
   }

   public final Condition ge(QuantifiedSelect select) {
      return this.greaterOrEqual(select);
   }

   public final Condition lessThan(Select select) {
      return this.compare(Comparator.LESS, select);
   }

   public final Condition lessThan(QuantifiedSelect select) {
      return this.compare(Comparator.LESS, select);
   }

   public final Condition lt(Select select) {
      return this.lessThan(select);
   }

   public final Condition lt(QuantifiedSelect select) {
      return this.lessThan(select);
   }

   public final Condition lessOrEqual(Select select) {
      return this.compare(Comparator.LESS_OR_EQUAL, select);
   }

   public final Condition lessOrEqual(QuantifiedSelect select) {
      return this.compare(Comparator.LESS_OR_EQUAL, select);
   }

   public final Condition le(Select select) {
      return this.lessOrEqual(select);
   }

   public final Condition le(QuantifiedSelect select) {
      return this.lessOrEqual(select);
   }

   public final Condition in(Select select) {
      return this.compare(Comparator.IN, select);
   }

   public final Condition notIn(Select select) {
      return this.compare(Comparator.NOT_IN, select);
   }

   public final Condition overlaps(T1 t1, T2 t2) {
      return this.overlaps(DSL.row(t1, t2));
   }

   public final Condition overlaps(Field<T1> t1, Field<T2> t2) {
      return this.overlaps(DSL.row(t1, t2));
   }

   public final Condition overlaps(Row2<T1, T2> row) {
      return new RowOverlapsCondition(this, row);
   }

   static {
      CLAUSES = new Clause[]{Clause.FIELD_ROW};
   }
}
