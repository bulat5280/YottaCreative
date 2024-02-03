package org.jooq.impl;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.jooq.Attachable;
import org.jooq.Converter;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.Name;
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
import org.jooq.RecordMapper;
import org.jooq.Result;
import org.jooq.Row;
import org.jooq.Table;
import org.jooq.UniqueKey;
import org.jooq.conf.SettingsTools;
import org.jooq.exception.InvalidResultException;
import org.jooq.exception.MappingException;
import org.jooq.tools.Convert;
import org.jooq.tools.StringUtils;

abstract class AbstractRecord extends AbstractStore implements Record {
   private static final long serialVersionUID = -6052512608911220404L;
   final RowImpl fields;
   final Object[] values;
   final Object[] originals;
   final BitSet changed;
   boolean fetched;

   AbstractRecord(Collection<? extends Field<?>> fields) {
      this(new RowImpl(fields));
   }

   AbstractRecord(Field<?>... fields) {
      this(new RowImpl(fields));
   }

   AbstractRecord(RowImpl fields) {
      int size = fields.size();
      this.fields = fields;
      this.values = new Object[size];
      this.originals = new Object[size];
      this.changed = new BitSet(size);
   }

   final List<Attachable> getAttachables() {
      List<Attachable> result = null;
      int size = this.size();

      for(int i = 0; i < size; ++i) {
         if (this.values[i] instanceof Attachable) {
            if (result == null) {
               result = new ArrayList();
            }

            result.add((Attachable)this.values[i]);
         }
      }

      return (List)(result == null ? Collections.emptyList() : result);
   }

   public final <T> Field<T> field(Field<T> field) {
      return this.fieldsRow().field(field);
   }

   public final Field<?> field(String name) {
      return this.fieldsRow().field(name);
   }

   public final Field<?> field(Name name) {
      return this.fieldsRow().field(name);
   }

   public final Field<?> field(int index) {
      return index >= 0 && index < this.fields.size() ? this.fields.field(index) : null;
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

   public final int size() {
      return this.fields.size();
   }

   public final <T> T get(Field<T> field) {
      return this.get(Tools.indexOrFail(this.fieldsRow(), field));
   }

   public final <T> T get(Field<?> field, Class<? extends T> type) {
      return Convert.convert(this.get(field), type);
   }

   public final <T, U> U get(Field<T> field, Converter<? super T, ? extends U> converter) {
      return converter.from(this.get(field));
   }

   public final Object get(int index) {
      return this.values[this.safeIndex(index)];
   }

   public final <T> T get(int index, Class<? extends T> type) {
      return Convert.convert(this.get(index), type);
   }

   public final <U> U get(int index, Converter<?, ? extends U> converter) {
      return Convert.convert(this.get(index), converter);
   }

   public final Object get(String fieldName) {
      return this.get(Tools.indexOrFail(this.fieldsRow(), fieldName));
   }

   public final <T> T get(String fieldName, Class<? extends T> type) {
      return Convert.convert(this.get(fieldName), type);
   }

   public final <U> U get(String fieldName, Converter<?, ? extends U> converter) {
      return Convert.convert(this.get(fieldName), converter);
   }

   public final Object get(Name fieldName) {
      return this.get(Tools.indexOrFail(this.fieldsRow(), fieldName));
   }

   public final <T> T get(Name fieldName, Class<? extends T> type) {
      return Convert.convert(this.get(fieldName), type);
   }

   public final <U> U get(Name fieldName, Converter<?, ? extends U> converter) {
      return Convert.convert(this.get(fieldName), converter);
   }

   /** @deprecated */
   @Deprecated
   protected final void setValue(int index, Object value) {
      this.set(index, value);
   }

   protected final void set(int index, Object value) {
      this.set(index, this.field(index), value);
   }

   public final <T> void set(Field<T> field, T value) {
      this.set(Tools.indexOrFail((Row)this.fields, (Field)field), field, value);
   }

   private final <T> void set(int index, Field<T> field, T value) {
      UniqueKey<?> key = this.getPrimaryKey();
      if (key != null && key.getFields().contains(field)) {
         if (this.changed.get(index)) {
            this.changed.set(index);
         } else if (SettingsTools.updatablePrimaryKeys(Tools.settings((Attachable)this))) {
            this.changed.set(index);
         } else if (this.originals[index] == null) {
            this.changed.set(index);
         } else {
            this.changed.set(index, this.changed.get(index) || !StringUtils.equals(this.values[index], value));
            if (this.changed.get(index)) {
               this.changed(true);
            }
         }
      } else {
         this.changed.set(index);
      }

      this.values[index] = value;
   }

   public final <T, U> void set(Field<T> field, U value, Converter<? extends T, ? super U> converter) {
      this.set(field, converter.to(value));
   }

   public <T> Record with(Field<T> field, T value) {
      this.set(field, value);
      return this;
   }

   public <T, U> Record with(Field<T> field, U value, Converter<? extends T, ? super U> converter) {
      this.set(field, value, converter);
      return this;
   }

   final void setValues(Field<?>[] fields, AbstractRecord record) {
      this.fetched = record.fetched;
      Field[] var3 = fields;
      int var4 = fields.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Field<?> field = var3[var5];
         int targetIndex = Tools.indexOrFail(this.fieldsRow(), field);
         int sourceIndex = Tools.indexOrFail(record.fieldsRow(), field);
         this.values[targetIndex] = record.get(sourceIndex);
         this.originals[targetIndex] = record.original(sourceIndex);
         this.changed.set(targetIndex, record.changed(sourceIndex));
      }

   }

   final void intern0(int fieldIndex) {
      this.safeIndex(fieldIndex);
      if (this.field(fieldIndex).getType() == String.class) {
         this.values[fieldIndex] = ((String)this.values[fieldIndex]).intern();
         this.originals[fieldIndex] = ((String)this.originals[fieldIndex]).intern();
      }

   }

   final int safeIndex(int index) {
      if (index >= 0 && index < this.values.length) {
         return index;
      } else {
         throw new IllegalArgumentException("No field at index " + index + " in Record type " + this.fieldsRow());
      }
   }

   UniqueKey<?> getPrimaryKey() {
      return null;
   }

   public Record original() {
      return Tools.newRecord(this.fetched, this.getClass(), this.fields.fields.fields, this.configuration()).operate(new RecordOperation<AbstractRecord, RuntimeException>() {
         public AbstractRecord operate(AbstractRecord record) throws RuntimeException {
            for(int i = 0; i < AbstractRecord.this.originals.length; ++i) {
               record.values[i] = AbstractRecord.this.originals[i];
               record.originals[i] = AbstractRecord.this.originals[i];
            }

            return record;
         }
      });
   }

   public final <T> T original(Field<T> field) {
      return this.original(Tools.indexOrFail(this.fieldsRow(), field));
   }

   public final Object original(int fieldIndex) {
      return this.originals[this.safeIndex(fieldIndex)];
   }

   public final Object original(String fieldName) {
      return this.original(Tools.indexOrFail(this.fieldsRow(), fieldName));
   }

   public final Object original(Name fieldName) {
      return this.original(Tools.indexOrFail(this.fieldsRow(), fieldName));
   }

   public final boolean changed() {
      return !this.changed.isEmpty();
   }

   public final boolean changed(Field<?> field) {
      return this.changed(Tools.indexOrFail(this.fieldsRow(), field));
   }

   public final boolean changed(int fieldIndex) {
      return this.changed.get(this.safeIndex(fieldIndex));
   }

   public final boolean changed(String fieldName) {
      return this.changed(Tools.indexOrFail(this.fieldsRow(), fieldName));
   }

   public final boolean changed(Name fieldName) {
      return this.changed(Tools.indexOrFail(this.fieldsRow(), fieldName));
   }

   public final void changed(boolean c) {
      this.changed.set(0, this.values.length, c);
      if (!c) {
         System.arraycopy(this.values, 0, this.originals, 0, this.values.length);
      }

   }

   public final void changed(Field<?> field, boolean c) {
      this.changed(Tools.indexOrFail(this.fieldsRow(), field), c);
   }

   public final void changed(int fieldIndex, boolean c) {
      this.safeIndex(fieldIndex);
      this.changed.set(fieldIndex, c);
      if (!c) {
         this.originals[fieldIndex] = this.values[fieldIndex];
      }

   }

   public final void changed(String fieldName, boolean c) {
      this.changed(Tools.indexOrFail(this.fieldsRow(), fieldName), c);
   }

   public final void changed(Name fieldName, boolean c) {
      this.changed(Tools.indexOrFail(this.fieldsRow(), fieldName), c);
   }

   public final void reset() {
      this.changed.clear();
      System.arraycopy(this.originals, 0, this.values, 0, this.originals.length);
   }

   public final void reset(Field<?> field) {
      this.reset(Tools.indexOrFail(this.fieldsRow(), field));
   }

   public final void reset(int fieldIndex) {
      this.safeIndex(fieldIndex);
      this.changed.clear(fieldIndex);
      this.values[fieldIndex] = this.originals[fieldIndex];
   }

   public final void reset(String fieldName) {
      this.reset(Tools.indexOrFail(this.fieldsRow(), fieldName));
   }

   public final void reset(Name fieldName) {
      this.reset(Tools.indexOrFail(this.fieldsRow(), fieldName));
   }

   public final Object[] intoArray() {
      return (Object[])this.into(Object[].class);
   }

   public final List<Object> intoList() {
      return Arrays.asList(this.intoArray());
   }

   public final Stream<Object> intoStream() {
      return (Stream)this.into(Stream.class);
   }

   public final Map<String, Object> intoMap() {
      Map<String, Object> map = new LinkedHashMap();
      int size = this.fields.size();

      for(int i = 0; i < size; ++i) {
         Field<?> field = this.fields.field(i);
         if (map.put(field.getName(), this.get(i)) != null) {
            throw new InvalidResultException("Field " + field.getName() + " is not unique in Record : " + this);
         }
      }

      return map;
   }

   public final Record into(Field<?>... f) {
      return Tools.newRecord(this.fetched, Record.class, f, this.configuration()).operate(new AbstractRecord.TransferRecordState(f));
   }

   public final <T1> Record1<T1> into(Field<T1> field1) {
      return (Record1)this.into(field1);
   }

   public final <T1, T2> Record2<T1, T2> into(Field<T1> field1, Field<T2> field2) {
      return (Record2)this.into(field1, field2);
   }

   public final <T1, T2, T3> Record3<T1, T2, T3> into(Field<T1> field1, Field<T2> field2, Field<T3> field3) {
      return (Record3)this.into(field1, field2, field3);
   }

   public final <T1, T2, T3, T4> Record4<T1, T2, T3, T4> into(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4) {
      return (Record4)this.into(field1, field2, field3, field4);
   }

   public final <T1, T2, T3, T4, T5> Record5<T1, T2, T3, T4, T5> into(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5) {
      return (Record5)this.into(field1, field2, field3, field4, field5);
   }

   public final <T1, T2, T3, T4, T5, T6> Record6<T1, T2, T3, T4, T5, T6> into(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6) {
      return (Record6)this.into(field1, field2, field3, field4, field5, field6);
   }

   public final <T1, T2, T3, T4, T5, T6, T7> Record7<T1, T2, T3, T4, T5, T6, T7> into(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7) {
      return (Record7)this.into(field1, field2, field3, field4, field5, field6, field7);
   }

   public final <T1, T2, T3, T4, T5, T6, T7, T8> Record8<T1, T2, T3, T4, T5, T6, T7, T8> into(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8) {
      return (Record8)this.into(field1, field2, field3, field4, field5, field6, field7, field8);
   }

   public final <T1, T2, T3, T4, T5, T6, T7, T8, T9> Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> into(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9) {
      return (Record9)this.into(field1, field2, field3, field4, field5, field6, field7, field8, field9);
   }

   public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> into(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10) {
      return (Record10)this.into(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10);
   }

   public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> into(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11) {
      return (Record11)this.into(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11);
   }

   public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> into(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12) {
      return (Record12)this.into(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12);
   }

   public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> into(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13) {
      return (Record13)this.into(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13);
   }

   public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> into(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14) {
      return (Record14)this.into(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14);
   }

   public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> into(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15) {
      return (Record15)this.into(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15);
   }

   public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> into(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16) {
      return (Record16)this.into(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16);
   }

   public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> into(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17) {
      return (Record17)this.into(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17);
   }

   public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> into(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18) {
      return (Record18)this.into(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18);
   }

   public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> into(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19) {
      return (Record19)this.into(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19);
   }

   public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> into(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20) {
      return (Record20)this.into(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20);
   }

   public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> into(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20, Field<T21> field21) {
      return (Record21)this.into(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21);
   }

   public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> into(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20, Field<T21> field21, Field<T22> field22) {
      return (Record22)this.into(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21, field22);
   }

   public final <E> E into(Class<? extends E> type) {
      return Tools.configuration((Attachable)this).recordMapperProvider().provide(this.fields.fields, type).map(this);
   }

   public final <E> E into(E object) {
      if (object == null) {
         throw new NullPointerException("Cannot copy Record into null");
      } else {
         Class type = object.getClass();

         try {
            return (new DefaultRecordMapper(this.fields.fields, type, object, this.configuration())).map(this);
         } catch (MappingException var4) {
            throw var4;
         } catch (Exception var5) {
            throw new MappingException("An error ocurred when mapping record to " + type, var5);
         }
      }
   }

   public final <R extends Record> R into(Table<R> table) {
      return Tools.newRecord(this.fetched, table, this.configuration()).operate(new AbstractRecord.TransferRecordState(table.fields()));
   }

   final <R extends Record> R intoRecord(Class<R> type) {
      return Tools.newRecord(this.fetched, type, this.fields(), this.configuration()).operate(new AbstractRecord.TransferRecordState((Field[])null));
   }

   public final ResultSet intoResultSet() {
      ResultImpl<Record> result = new ResultImpl(this.configuration(), this.fields.fields.fields);
      result.add((Record)this);
      return result.intoResultSet();
   }

   public final <E> E map(RecordMapper<Record, E> mapper) {
      return mapper.map(this);
   }

   public final void from(Object source) {
      if (source != null) {
         if (source instanceof Map) {
            this.fromMap((Map)source);
         } else if (source instanceof Object[]) {
            this.fromArray((Object[])((Object[])source));
         } else {
            this.from(source, this.fields());
         }

      }
   }

   public final void from(Object source, Field<?>... f) {
      if (source != null) {
         if (source instanceof Map) {
            this.fromMap((Map)source, f);
         } else if (source instanceof Object[]) {
            this.fromArray((Object[])((Object[])source), f);
         } else {
            Class type = source.getClass();

            try {
               boolean useAnnotations = Tools.hasColumnAnnotations(this.configuration(), type);
               Field[] var5 = f;
               int var6 = f.length;

               for(int var7 = 0; var7 < var6; ++var7) {
                  Field<?> field = var5[var7];
                  List members;
                  Method method;
                  if (useAnnotations) {
                     members = Tools.getAnnotatedMembers(this.configuration(), type, field.getName());
                     method = Tools.getAnnotatedGetter(this.configuration(), type, field.getName());
                  } else {
                     members = Tools.getMatchingMembers(this.configuration(), type, field.getName());
                     method = Tools.getMatchingGetter(this.configuration(), type, field.getName());
                  }

                  if (method != null) {
                     Tools.setValue(this, field, method.invoke(source));
                  } else if (members.size() > 0) {
                     this.from(source, (java.lang.reflect.Field)members.get(0), field);
                  }
               }
            } catch (Exception var11) {
               throw new MappingException("An error ocurred when mapping record from " + type, var11);
            }
         }

         Tools.resetChangedOnNotNull(this);
      }
   }

   public final void from(Object source, String... fieldNames) {
      this.from(source, this.fields(fieldNames));
   }

   public final void from(Object source, Name... fieldNames) {
      this.from(source, this.fields(fieldNames));
   }

   public final void from(Object source, int... fieldIndexes) {
      this.from(source, this.fields(fieldIndexes));
   }

   public final void fromMap(Map<String, ?> map) {
      this.from(map, (Field[])this.fields());
   }

   public final void fromMap(Map<String, ?> map, Field<?>... f) {
      for(int i = 0; i < f.length; ++i) {
         String name = f[i].getName();
         if (map.containsKey(name)) {
            Tools.setValue(this, f[i], map.get(name));
         }
      }

   }

   public final void fromMap(Map<String, ?> map, String... fieldNames) {
      this.fromMap(map, this.fields(fieldNames));
   }

   public final void fromMap(Map<String, ?> map, Name... fieldNames) {
      this.fromMap(map, this.fields(fieldNames));
   }

   public final void fromMap(Map<String, ?> map, int... fieldIndexes) {
      this.fromMap(map, this.fields(fieldIndexes));
   }

   public final void fromArray(Object... array) {
      this.fromArray(array, this.fields());
   }

   public final void fromArray(Object[] array, Field<?>... f) {
      Fields accept = new Fields(f);
      int size = this.fields.size();

      for(int i = 0; i < size && i < array.length; ++i) {
         Field field = this.fields.field(i);
         if (accept.field(field) != null) {
            Tools.setValue(this, field, array[i]);
         }
      }

   }

   public final void fromArray(Object[] array, String... fieldNames) {
      this.fromArray(array, this.fields(fieldNames));
   }

   public final void fromArray(Object[] array, Name... fieldNames) {
      this.fromArray(array, this.fields(fieldNames));
   }

   public final void fromArray(Object[] array, int... fieldIndexes) {
      this.fromArray(array, this.fields(fieldIndexes));
   }

   protected final void from(Record source) {
      Field[] var2 = this.fields.fields.fields;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Field<?> field = var2[var4];
         Field<?> sourceField = source.field(field);
         if (sourceField != null) {
            Tools.setValue(this, field, source, sourceField);
         }
      }

   }

   private final void from(Object source, java.lang.reflect.Field member, Field<?> field) throws IllegalAccessException {
      Class<?> mType = member.getType();
      if (mType.isPrimitive()) {
         if (mType == Byte.TYPE) {
            Tools.setValue(this, field, member.getByte(source));
         } else if (mType == Short.TYPE) {
            Tools.setValue(this, field, member.getShort(source));
         } else if (mType == Integer.TYPE) {
            Tools.setValue(this, field, member.getInt(source));
         } else if (mType == Long.TYPE) {
            Tools.setValue(this, field, member.getLong(source));
         } else if (mType == Float.TYPE) {
            Tools.setValue(this, field, member.getFloat(source));
         } else if (mType == Double.TYPE) {
            Tools.setValue(this, field, member.getDouble(source));
         } else if (mType == Boolean.TYPE) {
            Tools.setValue(this, field, member.getBoolean(source));
         } else if (mType == Character.TYPE) {
            Tools.setValue(this, field, member.getChar(source));
         }
      } else {
         Tools.setValue(this, field, member.get(source));
      }

   }

   public String toString() {
      return (String)Tools.ThreadGuard.run(Tools.ThreadGuard.Guard.RECORD_TOSTRING, new Tools.ThreadGuard.GuardedOperation<String>() {
         public String unguarded() {
            Result<AbstractRecord> result = new ResultImpl(AbstractRecord.this.configuration(), AbstractRecord.this.fields.fields.fields);
            result.add(AbstractRecord.this);
            return result.toString();
         }

         public String guarded() {
            return AbstractRecord.this.valuesRow().toString();
         }
      });
   }

   public int compareTo(Record that) {
      if (that == null) {
         throw new NullPointerException();
      } else if (this.size() != that.size()) {
         throw new ClassCastException(String.format("Trying to compare incomparable records (wrong degree):\n%s\n%s", this, that));
      } else {
         Class<?>[] thisTypes = this.fieldsRow().types();
         Class<?>[] thatTypes = that.fieldsRow().types();
         if (!Arrays.asList(thisTypes).equals(Arrays.asList(thatTypes))) {
            throw new ClassCastException(String.format("Trying to compare incomparable records (type mismatch):\n%s\n%s", this, that));
         } else {
            for(int i = 0; i < this.size(); ++i) {
               Object thisValue = this.get(i);
               Object thatValue = that.get(i);
               if (thisValue != null || thatValue != null) {
                  if (thisValue == null) {
                     return 1;
                  }

                  if (thatValue == null) {
                     return -1;
                  }

                  int compare;
                  if (thisValue.getClass().isArray() && thatValue.getClass().isArray()) {
                     if (thisValue.getClass() == byte[].class) {
                        compare = this.compare((byte[])((byte[])thisValue), (byte[])((byte[])thatValue));
                        if (compare != 0) {
                           return compare;
                        }
                     } else {
                        if (thisValue.getClass().getComponentType().isPrimitive()) {
                           throw new ClassCastException(String.format("Unsupported data type in natural ordering: %s", thisValue.getClass()));
                        }

                        compare = this.compare((Object[])((Object[])thisValue), (Object[])((Object[])thatValue));
                        if (compare != 0) {
                           return compare;
                        }
                     }
                  } else {
                     compare = ((Comparable)thisValue).compareTo(thatValue);
                     if (compare != 0) {
                        return compare;
                     }
                  }
               }
            }

            return 0;
         }
      }
   }

   final int compare(byte[] array1, byte[] array2) {
      int length = Math.min(array1.length, array2.length);

      for(int i = 0; i < length; ++i) {
         int v1 = array1[i] & 255;
         int v2 = array2[i] & 255;
         if (v1 != v2) {
            return v1 < v2 ? -1 : 1;
         }
      }

      return array1.length - array2.length;
   }

   final int compare(Object[] array1, Object[] array2) {
      int length = Math.min(array1.length, array2.length);

      for(int i = 0; i < length; ++i) {
         int compare = ((Comparable)array1[i]).compareTo(array2[i]);
         if (compare != 0) {
            return compare;
         }
      }

      return array1.length - array2.length;
   }

   public final <T> T getValue(Field<T> field) {
      return this.get(field);
   }

   /** @deprecated */
   @Deprecated
   public final <T> T getValue(Field<T> field, T defaultValue) {
      T result = this.getValue(field);
      return result != null ? result : defaultValue;
   }

   public final <T> T getValue(Field<?> field, Class<? extends T> type) {
      return this.get(field, type);
   }

   /** @deprecated */
   @Deprecated
   public final <T> T getValue(Field<?> field, Class<? extends T> type, T defaultValue) {
      T result = this.get(field, type);
      return result == null ? defaultValue : result;
   }

   public final <T, U> U getValue(Field<T> field, Converter<? super T, ? extends U> converter) {
      return this.get(field, converter);
   }

   /** @deprecated */
   @Deprecated
   public final <T, U> U getValue(Field<T> field, Converter<? super T, ? extends U> converter, U defaultValue) {
      U result = this.get(field, converter);
      return result == null ? defaultValue : result;
   }

   public final Object getValue(int index) {
      return this.get(index);
   }

   /** @deprecated */
   @Deprecated
   public final Object getValue(int index, Object defaultValue) {
      Object result = this.get(index);
      return result == null ? defaultValue : result;
   }

   public final <T> T getValue(int index, Class<? extends T> type) {
      return this.get(index, type);
   }

   /** @deprecated */
   @Deprecated
   public final <T> T getValue(int index, Class<? extends T> type, T defaultValue) {
      T result = this.get(index, type);
      return result == null ? defaultValue : result;
   }

   public final <U> U getValue(int index, Converter<?, ? extends U> converter) {
      return this.get(index, converter);
   }

   /** @deprecated */
   @Deprecated
   public final <U> U getValue(int index, Converter<?, ? extends U> converter, U defaultValue) {
      U result = this.get(index, converter);
      return result == null ? defaultValue : result;
   }

   public final Object getValue(String fieldName) {
      return this.get(fieldName);
   }

   /** @deprecated */
   @Deprecated
   public final Object getValue(String fieldName, Object defaultValue) {
      return this.getValue(Tools.indexOrFail(this.fieldsRow(), fieldName), defaultValue);
   }

   public final <T> T getValue(String fieldName, Class<? extends T> type) {
      return this.get(fieldName, type);
   }

   /** @deprecated */
   @Deprecated
   public final <T> T getValue(String fieldName, Class<? extends T> type, T defaultValue) {
      T result = this.get(fieldName, type);
      return result == null ? defaultValue : result;
   }

   public final <U> U getValue(String fieldName, Converter<?, ? extends U> converter) {
      return this.get(fieldName, converter);
   }

   /** @deprecated */
   @Deprecated
   public final <U> U getValue(String fieldName, Converter<?, ? extends U> converter, U defaultValue) {
      U result = this.get(fieldName, converter);
      return result == null ? defaultValue : result;
   }

   public final Object getValue(Name fieldName) {
      return this.get(fieldName);
   }

   public final <T> T getValue(Name fieldName, Class<? extends T> type) {
      return this.get(fieldName, type);
   }

   public final <U> U getValue(Name fieldName, Converter<?, ? extends U> converter) {
      return this.get(fieldName, converter);
   }

   public final <T> void setValue(Field<T> field, T value) {
      this.set(field, value);
   }

   public final <T, U> void setValue(Field<T> field, U value, Converter<? extends T, ? super U> converter) {
      this.set(field, value, converter);
   }

   private class TransferRecordState<R extends Record> implements RecordOperation<R, MappingException> {
      private final Field<?>[] targetFields;

      TransferRecordState(Field<?>[] targetFields) {
         this.targetFields = targetFields;
      }

      public R operate(R target) throws MappingException {
         AbstractRecord source = AbstractRecord.this;

         try {
            int targetIndex;
            if (target instanceof AbstractRecord) {
               AbstractRecord t = (AbstractRecord)target;

               for(targetIndex = 0; targetIndex < (this.targetFields != null ? this.targetFields.length : t.size()); ++targetIndex) {
                  Field<?> targetFieldx = this.targetFields != null ? this.targetFields[targetIndex] : t.field(targetIndex);
                  int sourceIndex = AbstractRecord.this.fields.indexOf(targetFieldx);
                  if (sourceIndex >= 0) {
                     DataType<?> targetType = targetFieldx.getDataType();
                     t.values[targetIndex] = targetType.convert(AbstractRecord.this.values[sourceIndex]);
                     t.originals[targetIndex] = targetType.convert(AbstractRecord.this.originals[sourceIndex]);
                     t.changed.set(targetIndex, AbstractRecord.this.changed.get(sourceIndex));
                  }
               }
            } else {
               Field[] var9 = target.fields();
               targetIndex = var9.length;

               for(int var10 = 0; var10 < targetIndex; ++var10) {
                  Field<?> targetField = var9[var10];
                  Field<?> sourceField = AbstractRecord.this.field(targetField);
                  if (sourceField != null) {
                     Tools.setValue(target, targetField, source, sourceField);
                  }
               }
            }

            return target;
         } catch (Exception var8) {
            throw new MappingException("An error ocurred when mapping record to " + target, var8);
         }
      }
   }
}
