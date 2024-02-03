package org.jooq;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.jooq.exception.DataTypeException;
import org.jooq.exception.MappingException;

public interface Record extends Attachable, Comparable<Record> {
   Row fieldsRow();

   <T> Field<T> field(Field<T> var1);

   Field<?> field(String var1);

   Field<?> field(Name var1);

   Field<?> field(int var1);

   Field<?>[] fields();

   Field<?>[] fields(Field<?>... var1);

   Field<?>[] fields(String... var1);

   Field<?>[] fields(Name... var1);

   Field<?>[] fields(int... var1);

   Row valuesRow();

   <T> T get(Field<T> var1) throws IllegalArgumentException;

   <T> T get(Field<?> var1, Class<? extends T> var2) throws IllegalArgumentException, DataTypeException;

   <T, U> U get(Field<T> var1, Converter<? super T, ? extends U> var2) throws IllegalArgumentException, DataTypeException;

   Object get(String var1) throws IllegalArgumentException;

   <T> T get(String var1, Class<? extends T> var2) throws IllegalArgumentException, DataTypeException;

   <U> U get(String var1, Converter<?, ? extends U> var2) throws IllegalArgumentException, DataTypeException;

   Object get(Name var1) throws IllegalArgumentException;

   <T> T get(Name var1, Class<? extends T> var2) throws IllegalArgumentException, DataTypeException;

   <U> U get(Name var1, Converter<?, ? extends U> var2) throws IllegalArgumentException, DataTypeException;

   Object get(int var1) throws IllegalArgumentException;

   <T> T get(int var1, Class<? extends T> var2) throws IllegalArgumentException, DataTypeException;

   <U> U get(int var1, Converter<?, ? extends U> var2) throws IllegalArgumentException, DataTypeException;

   <T> void set(Field<T> var1, T var2);

   <T, U> void set(Field<T> var1, U var2, Converter<? extends T, ? super U> var3);

   <T> Record with(Field<T> var1, T var2);

   <T, U> Record with(Field<T> var1, U var2, Converter<? extends T, ? super U> var3);

   int size();

   Record original();

   <T> T original(Field<T> var1);

   Object original(int var1);

   Object original(String var1);

   Object original(Name var1);

   boolean changed();

   boolean changed(Field<?> var1);

   boolean changed(int var1);

   boolean changed(String var1);

   boolean changed(Name var1);

   void changed(boolean var1);

   void changed(Field<?> var1, boolean var2);

   void changed(int var1, boolean var2);

   void changed(String var1, boolean var2);

   void changed(Name var1, boolean var2);

   void reset();

   void reset(Field<?> var1);

   void reset(int var1);

   void reset(String var1);

   void reset(Name var1);

   Object[] intoArray();

   List<Object> intoList();

   Stream<Object> intoStream();

   Map<String, Object> intoMap();

   Record into(Field<?>... var1);

   <T1> Record1<T1> into(Field<T1> var1);

   <T1, T2> Record2<T1, T2> into(Field<T1> var1, Field<T2> var2);

   <T1, T2, T3> Record3<T1, T2, T3> into(Field<T1> var1, Field<T2> var2, Field<T3> var3);

   <T1, T2, T3, T4> Record4<T1, T2, T3, T4> into(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4);

   <T1, T2, T3, T4, T5> Record5<T1, T2, T3, T4, T5> into(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5);

   <T1, T2, T3, T4, T5, T6> Record6<T1, T2, T3, T4, T5, T6> into(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6);

   <T1, T2, T3, T4, T5, T6, T7> Record7<T1, T2, T3, T4, T5, T6, T7> into(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7);

   <T1, T2, T3, T4, T5, T6, T7, T8> Record8<T1, T2, T3, T4, T5, T6, T7, T8> into(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8);

   <T1, T2, T3, T4, T5, T6, T7, T8, T9> Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> into(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9);

   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> into(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10);

   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> into(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11);

   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> into(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12);

   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> into(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13);

   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> into(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14);

   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> into(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15);

   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> into(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15, Field<T16> var16);

   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> into(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15, Field<T16> var16, Field<T17> var17);

   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> into(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15, Field<T16> var16, Field<T17> var17, Field<T18> var18);

   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> into(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15, Field<T16> var16, Field<T17> var17, Field<T18> var18, Field<T19> var19);

   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> into(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15, Field<T16> var16, Field<T17> var17, Field<T18> var18, Field<T19> var19, Field<T20> var20);

   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> into(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15, Field<T16> var16, Field<T17> var17, Field<T18> var18, Field<T19> var19, Field<T20> var20, Field<T21> var21);

   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> into(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15, Field<T16> var16, Field<T17> var17, Field<T18> var18, Field<T19> var19, Field<T20> var20, Field<T21> var21, Field<T22> var22);

   <E> E into(Class<? extends E> var1) throws MappingException;

   <E> E into(E var1) throws MappingException;

   <R extends Record> R into(Table<R> var1);

   ResultSet intoResultSet();

   <E> E map(RecordMapper<Record, E> var1);

   void from(Object var1) throws MappingException;

   void from(Object var1, Field<?>... var2) throws MappingException;

   void from(Object var1, String... var2) throws MappingException;

   void from(Object var1, Name... var2) throws MappingException;

   void from(Object var1, int... var2) throws MappingException;

   void fromMap(Map<String, ?> var1);

   void fromMap(Map<String, ?> var1, Field<?>... var2);

   void fromMap(Map<String, ?> var1, String... var2);

   void fromMap(Map<String, ?> var1, Name... var2);

   void fromMap(Map<String, ?> var1, int... var2);

   void fromArray(Object... var1);

   void fromArray(Object[] var1, Field<?>... var2);

   void fromArray(Object[] var1, String... var2);

   void fromArray(Object[] var1, Name... var2);

   void fromArray(Object[] var1, int... var2);

   int hashCode();

   boolean equals(Object var1);

   int compareTo(Record var1);

   <T> T getValue(Field<T> var1) throws IllegalArgumentException;

   /** @deprecated */
   @Deprecated
   <T> T getValue(Field<T> var1, T var2) throws IllegalArgumentException;

   <T> T getValue(Field<?> var1, Class<? extends T> var2) throws IllegalArgumentException, DataTypeException;

   /** @deprecated */
   @Deprecated
   <T> T getValue(Field<?> var1, Class<? extends T> var2, T var3) throws IllegalArgumentException, DataTypeException;

   <T, U> U getValue(Field<T> var1, Converter<? super T, ? extends U> var2) throws IllegalArgumentException, DataTypeException;

   /** @deprecated */
   @Deprecated
   <T, U> U getValue(Field<T> var1, Converter<? super T, ? extends U> var2, U var3) throws IllegalArgumentException, DataTypeException;

   Object getValue(String var1) throws IllegalArgumentException;

   /** @deprecated */
   @Deprecated
   Object getValue(String var1, Object var2) throws IllegalArgumentException;

   <T> T getValue(String var1, Class<? extends T> var2) throws IllegalArgumentException, DataTypeException;

   /** @deprecated */
   @Deprecated
   <T> T getValue(String var1, Class<? extends T> var2, T var3) throws IllegalArgumentException, DataTypeException;

   <U> U getValue(String var1, Converter<?, ? extends U> var2) throws IllegalArgumentException, DataTypeException;

   /** @deprecated */
   @Deprecated
   <U> U getValue(String var1, Converter<?, ? extends U> var2, U var3) throws IllegalArgumentException, DataTypeException;

   Object getValue(Name var1) throws IllegalArgumentException;

   <T> T getValue(Name var1, Class<? extends T> var2) throws IllegalArgumentException, DataTypeException;

   <U> U getValue(Name var1, Converter<?, ? extends U> var2) throws IllegalArgumentException, DataTypeException;

   Object getValue(int var1) throws IllegalArgumentException;

   /** @deprecated */
   @Deprecated
   Object getValue(int var1, Object var2) throws IllegalArgumentException;

   <T> T getValue(int var1, Class<? extends T> var2) throws IllegalArgumentException, DataTypeException;

   /** @deprecated */
   @Deprecated
   <T> T getValue(int var1, Class<? extends T> var2, T var3) throws IllegalArgumentException, DataTypeException;

   <U> U getValue(int var1, Converter<?, ? extends U> var2) throws IllegalArgumentException, DataTypeException;

   /** @deprecated */
   @Deprecated
   <U> U getValue(int var1, Converter<?, ? extends U> var2, U var3) throws IllegalArgumentException, DataTypeException;

   <T> void setValue(Field<T> var1, T var2);

   <T, U> void setValue(Field<T> var1, U var2, Converter<? extends T, ? super U> var3);
}
