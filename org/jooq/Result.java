package org.jooq;

import java.io.OutputStream;
import java.io.Writer;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.jooq.exception.DataAccessException;
import org.jooq.exception.DataTypeException;
import org.jooq.exception.IOException;
import org.jooq.exception.InvalidResultException;
import org.jooq.exception.MappingException;
import org.w3c.dom.Document;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

public interface Result<R extends Record> extends List<R>, Attachable {
   RecordType<R> recordType();

   Row fieldsRow();

   <T> Field<T> field(Field<T> var1);

   Field<?> field(String var1);

   <T> Field<T> field(String var1, Class<T> var2);

   <T> Field<T> field(String var1, DataType<T> var2);

   Field<?> field(Name var1);

   <T> Field<T> field(Name var1, Class<T> var2);

   <T> Field<T> field(Name var1, DataType<T> var2);

   Field<?> field(int var1);

   <T> Field<T> field(int var1, Class<T> var2);

   <T> Field<T> field(int var1, DataType<T> var2);

   Field<?>[] fields();

   Field<?>[] fields(Field<?>... var1);

   Field<?>[] fields(String... var1);

   Field<?>[] fields(Name... var1);

   Field<?>[] fields(int... var1);

   <T> T getValue(int var1, Field<T> var2) throws IndexOutOfBoundsException, IllegalArgumentException;

   /** @deprecated */
   @Deprecated
   <T> T getValue(int var1, Field<T> var2, T var3) throws IndexOutOfBoundsException, IllegalArgumentException;

   Object getValue(int var1, int var2) throws IndexOutOfBoundsException, IllegalArgumentException;

   /** @deprecated */
   @Deprecated
   Object getValue(int var1, int var2, Object var3) throws IndexOutOfBoundsException, IllegalArgumentException;

   Object getValue(int var1, String var2) throws IndexOutOfBoundsException, IllegalArgumentException;

   /** @deprecated */
   @Deprecated
   Object getValue(int var1, String var2, Object var3) throws IndexOutOfBoundsException, IllegalArgumentException;

   <T> List<T> getValues(Field<T> var1) throws IllegalArgumentException;

   <T> List<T> getValues(Field<?> var1, Class<? extends T> var2) throws IllegalArgumentException;

   <T, U> List<U> getValues(Field<T> var1, Converter<? super T, ? extends U> var2) throws IllegalArgumentException;

   List<?> getValues(int var1) throws IllegalArgumentException;

   <T> List<T> getValues(int var1, Class<? extends T> var2) throws IllegalArgumentException, DataTypeException;

   <U> List<U> getValues(int var1, Converter<?, ? extends U> var2) throws IllegalArgumentException, DataTypeException;

   List<?> getValues(String var1) throws IllegalArgumentException;

   <T> List<T> getValues(String var1, Class<? extends T> var2) throws IllegalArgumentException, DataTypeException;

   <U> List<U> getValues(String var1, Converter<?, ? extends U> var2) throws IllegalArgumentException, DataTypeException;

   List<?> getValues(Name var1) throws IllegalArgumentException;

   <T> List<T> getValues(Name var1, Class<? extends T> var2) throws IllegalArgumentException, DataTypeException;

   <U> List<U> getValues(Name var1, Converter<?, ? extends U> var2) throws IllegalArgumentException, DataTypeException;

   boolean isEmpty();

   boolean isNotEmpty();

   String format();

   String format(int var1);

   String formatHTML();

   String formatCSV();

   String formatCSV(char var1);

   String formatCSV(char var1, String var2);

   String formatCSV(boolean var1);

   String formatCSV(boolean var1, char var2);

   String formatCSV(boolean var1, char var2, String var3);

   String formatCSV(CSVFormat var1);

   String formatJSON();

   String formatJSON(JSONFormat var1);

   String formatXML();

   String formatInsert();

   String formatInsert(Table<?> var1, Field<?>... var2);

   void format(OutputStream var1) throws IOException;

   void format(OutputStream var1, int var2) throws IOException;

   void formatHTML(OutputStream var1) throws IOException;

   void formatCSV(OutputStream var1) throws IOException;

   void formatCSV(OutputStream var1, char var2) throws IOException;

   void formatCSV(OutputStream var1, char var2, String var3) throws IOException;

   void formatCSV(OutputStream var1, boolean var2) throws IOException;

   void formatCSV(OutputStream var1, boolean var2, char var3) throws IOException;

   void formatCSV(OutputStream var1, boolean var2, char var3, String var4) throws IOException;

   void formatCSV(OutputStream var1, CSVFormat var2) throws IOException;

   void formatJSON(OutputStream var1) throws IOException;

   void formatJSON(OutputStream var1, JSONFormat var2) throws IOException;

   void formatXML(OutputStream var1) throws IOException;

   void formatInsert(OutputStream var1) throws IOException;

   void formatInsert(OutputStream var1, Table<?> var2, Field<?>... var3) throws IOException;

   void format(Writer var1) throws IOException;

   void format(Writer var1, int var2) throws IOException;

   void formatHTML(Writer var1) throws IOException;

   void formatCSV(Writer var1) throws IOException;

   void formatCSV(Writer var1, char var2) throws IOException;

   void formatCSV(Writer var1, char var2, String var3) throws IOException;

   void formatCSV(Writer var1, boolean var2) throws IOException;

   void formatCSV(Writer var1, boolean var2, char var3) throws IOException;

   void formatCSV(Writer var1, boolean var2, char var3, String var4) throws IOException;

   void formatCSV(Writer var1, CSVFormat var2) throws IOException;

   void formatJSON(Writer var1) throws IOException;

   void formatJSON(Writer var1, JSONFormat var2) throws IOException;

   void formatXML(Writer var1) throws IOException;

   void formatInsert(Writer var1) throws IOException;

   void formatInsert(Writer var1, Table<?> var2, Field<?>... var3) throws IOException;

   Document intoXML();

   <H extends ContentHandler> H intoXML(H var1) throws SAXException;

   List<Map<String, Object>> intoMaps();

   <K> Map<K, R> intoMap(Field<K> var1) throws IllegalArgumentException, InvalidResultException;

   Map<?, R> intoMap(int var1) throws IllegalArgumentException, InvalidResultException;

   Map<?, R> intoMap(String var1) throws IllegalArgumentException, InvalidResultException;

   Map<?, R> intoMap(Name var1) throws IllegalArgumentException, InvalidResultException;

   <K, V> Map<K, V> intoMap(Field<K> var1, Field<V> var2) throws IllegalArgumentException, InvalidResultException;

   Map<?, ?> intoMap(int var1, int var2) throws IllegalArgumentException, InvalidResultException;

   Map<?, ?> intoMap(String var1, String var2) throws IllegalArgumentException, InvalidResultException;

   Map<?, ?> intoMap(Name var1, Name var2) throws IllegalArgumentException, InvalidResultException;

   <K, E> Map<K, E> intoMap(Field<K> var1, Class<? extends E> var2) throws IllegalArgumentException, InvalidResultException, MappingException;

   <E> Map<?, E> intoMap(int var1, Class<? extends E> var2) throws IllegalArgumentException, InvalidResultException, MappingException;

   <E> Map<?, E> intoMap(String var1, Class<? extends E> var2) throws IllegalArgumentException, InvalidResultException, MappingException;

   <E> Map<?, E> intoMap(Name var1, Class<? extends E> var2) throws IllegalArgumentException, InvalidResultException, MappingException;

   <K, E> Map<K, E> intoMap(Field<K> var1, RecordMapper<? super R, E> var2) throws IllegalArgumentException, InvalidResultException, MappingException;

   <E> Map<?, E> intoMap(int var1, RecordMapper<? super R, E> var2) throws IllegalArgumentException, InvalidResultException, MappingException;

   <E> Map<?, E> intoMap(String var1, RecordMapper<? super R, E> var2) throws IllegalArgumentException, InvalidResultException, MappingException;

   <E> Map<?, E> intoMap(Name var1, RecordMapper<? super R, E> var2) throws IllegalArgumentException, InvalidResultException, MappingException;

   Map<Record, R> intoMap(Field<?>[] var1) throws IllegalArgumentException, InvalidResultException;

   Map<Record, R> intoMap(int[] var1) throws IllegalArgumentException, InvalidResultException;

   Map<Record, R> intoMap(String[] var1) throws IllegalArgumentException, InvalidResultException;

   Map<Record, R> intoMap(Name[] var1) throws IllegalArgumentException, InvalidResultException;

   <E> Map<List<?>, E> intoMap(Field<?>[] var1, Class<? extends E> var2) throws IllegalArgumentException, InvalidResultException, MappingException;

   <E> Map<List<?>, E> intoMap(int[] var1, Class<? extends E> var2) throws IllegalArgumentException, InvalidResultException, MappingException;

   <E> Map<List<?>, E> intoMap(String[] var1, Class<? extends E> var2) throws IllegalArgumentException, InvalidResultException, MappingException;

   <E> Map<List<?>, E> intoMap(Name[] var1, Class<? extends E> var2) throws IllegalArgumentException, InvalidResultException, MappingException;

   <E> Map<List<?>, E> intoMap(Field<?>[] var1, RecordMapper<? super R, E> var2) throws IllegalArgumentException, InvalidResultException, MappingException;

   <E> Map<List<?>, E> intoMap(int[] var1, RecordMapper<? super R, E> var2) throws IllegalArgumentException, InvalidResultException, MappingException;

   <E> Map<List<?>, E> intoMap(String[] var1, RecordMapper<? super R, E> var2) throws IllegalArgumentException, InvalidResultException, MappingException;

   <E> Map<List<?>, E> intoMap(Name[] var1, RecordMapper<? super R, E> var2) throws IllegalArgumentException, InvalidResultException, MappingException;

   <K> Map<K, R> intoMap(Class<? extends K> var1) throws MappingException, InvalidResultException;

   <K, V> Map<K, V> intoMap(Class<? extends K> var1, Class<? extends V> var2) throws MappingException, InvalidResultException;

   <K, V> Map<K, V> intoMap(Class<? extends K> var1, RecordMapper<? super R, V> var2) throws InvalidResultException, MappingException;

   <K> Map<K, R> intoMap(RecordMapper<? super R, K> var1) throws InvalidResultException, MappingException;

   <K, V> Map<K, V> intoMap(RecordMapper<? super R, K> var1, Class<V> var2) throws InvalidResultException, MappingException;

   <K, V> Map<K, V> intoMap(RecordMapper<? super R, K> var1, RecordMapper<? super R, V> var2) throws InvalidResultException, MappingException;

   <S extends Record> Map<S, R> intoMap(Table<S> var1) throws IllegalArgumentException, InvalidResultException;

   <E, S extends Record> Map<S, E> intoMap(Table<S> var1, Class<? extends E> var2) throws IllegalArgumentException, InvalidResultException, MappingException;

   <E, S extends Record> Map<S, E> intoMap(Table<S> var1, RecordMapper<? super R, E> var2) throws IllegalArgumentException, InvalidResultException, MappingException;

   <K> Map<K, Result<R>> intoGroups(Field<K> var1) throws IllegalArgumentException;

   Map<?, Result<R>> intoGroups(int var1) throws IllegalArgumentException;

   Map<?, Result<R>> intoGroups(String var1) throws IllegalArgumentException;

   Map<?, Result<R>> intoGroups(Name var1) throws IllegalArgumentException;

   <K, V> Map<K, List<V>> intoGroups(Field<K> var1, Field<V> var2) throws IllegalArgumentException;

   Map<?, List<?>> intoGroups(int var1, int var2) throws IllegalArgumentException;

   Map<?, List<?>> intoGroups(String var1, String var2) throws IllegalArgumentException;

   Map<?, List<?>> intoGroups(Name var1, Name var2) throws IllegalArgumentException;

   <K, E> Map<K, List<E>> intoGroups(Field<K> var1, Class<? extends E> var2) throws IllegalArgumentException, MappingException;

   <E> Map<?, List<E>> intoGroups(int var1, Class<? extends E> var2) throws IllegalArgumentException, MappingException;

   <E> Map<?, List<E>> intoGroups(String var1, Class<? extends E> var2) throws IllegalArgumentException, MappingException;

   <E> Map<?, List<E>> intoGroups(Name var1, Class<? extends E> var2) throws IllegalArgumentException, MappingException;

   <K, E> Map<K, List<E>> intoGroups(Field<K> var1, RecordMapper<? super R, E> var2) throws IllegalArgumentException, MappingException;

   <E> Map<?, List<E>> intoGroups(int var1, RecordMapper<? super R, E> var2) throws IllegalArgumentException, MappingException;

   <E> Map<?, List<E>> intoGroups(String var1, RecordMapper<? super R, E> var2) throws IllegalArgumentException, MappingException;

   <E> Map<?, List<E>> intoGroups(Name var1, RecordMapper<? super R, E> var2) throws IllegalArgumentException, MappingException;

   Map<Record, Result<R>> intoGroups(Field<?>[] var1) throws IllegalArgumentException;

   Map<Record, Result<R>> intoGroups(int[] var1) throws IllegalArgumentException;

   Map<Record, Result<R>> intoGroups(String[] var1) throws IllegalArgumentException;

   Map<Record, Result<R>> intoGroups(Name[] var1) throws IllegalArgumentException;

   <E> Map<Record, List<E>> intoGroups(Field<?>[] var1, Class<? extends E> var2) throws IllegalArgumentException, MappingException;

   <E> Map<Record, List<E>> intoGroups(int[] var1, Class<? extends E> var2) throws IllegalArgumentException, MappingException;

   <E> Map<Record, List<E>> intoGroups(String[] var1, Class<? extends E> var2) throws IllegalArgumentException, MappingException;

   <E> Map<Record, List<E>> intoGroups(Name[] var1, Class<? extends E> var2) throws IllegalArgumentException, MappingException;

   <E> Map<Record, List<E>> intoGroups(Field<?>[] var1, RecordMapper<? super R, E> var2) throws IllegalArgumentException, MappingException;

   <E> Map<Record, List<E>> intoGroups(int[] var1, RecordMapper<? super R, E> var2) throws IllegalArgumentException, MappingException;

   <E> Map<Record, List<E>> intoGroups(String[] var1, RecordMapper<? super R, E> var2) throws IllegalArgumentException, MappingException;

   <E> Map<Record, List<E>> intoGroups(Name[] var1, RecordMapper<? super R, E> var2) throws IllegalArgumentException, MappingException;

   <K> Map<K, Result<R>> intoGroups(Class<? extends K> var1) throws MappingException;

   <K, V> Map<K, List<V>> intoGroups(Class<? extends K> var1, Class<? extends V> var2) throws MappingException;

   <K, V> Map<K, List<V>> intoGroups(Class<? extends K> var1, RecordMapper<? super R, V> var2) throws MappingException;

   <K> Map<K, Result<R>> intoGroups(RecordMapper<? super R, K> var1) throws MappingException;

   <K, V> Map<K, List<V>> intoGroups(RecordMapper<? super R, K> var1, Class<V> var2) throws MappingException;

   <K, V> Map<K, List<V>> intoGroups(RecordMapper<? super R, K> var1, RecordMapper<? super R, V> var2) throws MappingException;

   <S extends Record> Map<S, Result<R>> intoGroups(Table<S> var1) throws IllegalArgumentException;

   <E, S extends Record> Map<S, List<E>> intoGroups(Table<S> var1, Class<? extends E> var2) throws IllegalArgumentException, MappingException;

   <E, S extends Record> Map<S, List<E>> intoGroups(Table<S> var1, RecordMapper<? super R, E> var2) throws IllegalArgumentException, MappingException;

   /** @deprecated */
   @Deprecated
   Object[][] intoArray();

   Object[][] intoArrays();

   Object[] intoArray(int var1) throws IllegalArgumentException;

   <T> T[] intoArray(int var1, Class<? extends T> var2) throws IllegalArgumentException, DataTypeException;

   <U> U[] intoArray(int var1, Converter<?, ? extends U> var2) throws IllegalArgumentException, DataTypeException;

   Object[] intoArray(String var1) throws IllegalArgumentException;

   <T> T[] intoArray(String var1, Class<? extends T> var2) throws IllegalArgumentException, DataTypeException;

   <U> U[] intoArray(String var1, Converter<?, ? extends U> var2) throws IllegalArgumentException, DataTypeException;

   Object[] intoArray(Name var1) throws IllegalArgumentException;

   <T> T[] intoArray(Name var1, Class<? extends T> var2) throws IllegalArgumentException, DataTypeException;

   <U> U[] intoArray(Name var1, Converter<?, ? extends U> var2) throws IllegalArgumentException, DataTypeException;

   <T> T[] intoArray(Field<T> var1) throws IllegalArgumentException;

   <T> T[] intoArray(Field<?> var1, Class<? extends T> var2) throws IllegalArgumentException, DataTypeException;

   <T, U> U[] intoArray(Field<T> var1, Converter<? super T, ? extends U> var2) throws IllegalArgumentException, DataTypeException;

   Set<?> intoSet(int var1) throws IllegalArgumentException;

   <T> Set<T> intoSet(int var1, Class<? extends T> var2) throws IllegalArgumentException, DataTypeException;

   <U> Set<U> intoSet(int var1, Converter<?, ? extends U> var2) throws IllegalArgumentException, DataTypeException;

   Set<?> intoSet(String var1) throws IllegalArgumentException;

   <T> Set<T> intoSet(String var1, Class<? extends T> var2) throws IllegalArgumentException, DataTypeException;

   <U> Set<U> intoSet(String var1, Converter<?, ? extends U> var2) throws IllegalArgumentException, DataTypeException;

   Set<?> intoSet(Name var1) throws IllegalArgumentException;

   <T> Set<T> intoSet(Name var1, Class<? extends T> var2) throws IllegalArgumentException, DataTypeException;

   <U> Set<U> intoSet(Name var1, Converter<?, ? extends U> var2) throws IllegalArgumentException, DataTypeException;

   <T> Set<T> intoSet(Field<T> var1) throws IllegalArgumentException;

   <T> Set<T> intoSet(Field<?> var1, Class<? extends T> var2) throws IllegalArgumentException, DataTypeException;

   <T, U> Set<U> intoSet(Field<T> var1, Converter<? super T, ? extends U> var2) throws IllegalArgumentException, DataTypeException;

   Result<Record> into(Field<?>... var1);

   <T1> Result<Record1<T1>> into(Field<T1> var1);

   <T1, T2> Result<Record2<T1, T2>> into(Field<T1> var1, Field<T2> var2);

   <T1, T2, T3> Result<Record3<T1, T2, T3>> into(Field<T1> var1, Field<T2> var2, Field<T3> var3);

   <T1, T2, T3, T4> Result<Record4<T1, T2, T3, T4>> into(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4);

   <T1, T2, T3, T4, T5> Result<Record5<T1, T2, T3, T4, T5>> into(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5);

   <T1, T2, T3, T4, T5, T6> Result<Record6<T1, T2, T3, T4, T5, T6>> into(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6);

   <T1, T2, T3, T4, T5, T6, T7> Result<Record7<T1, T2, T3, T4, T5, T6, T7>> into(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7);

   <T1, T2, T3, T4, T5, T6, T7, T8> Result<Record8<T1, T2, T3, T4, T5, T6, T7, T8>> into(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8);

   <T1, T2, T3, T4, T5, T6, T7, T8, T9> Result<Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> into(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9);

   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> Result<Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> into(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10);

   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> Result<Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> into(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11);

   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> Result<Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> into(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12);

   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> Result<Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> into(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13);

   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> Result<Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> into(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14);

   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> Result<Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> into(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15);

   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> Result<Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> into(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15, Field<T16> var16);

   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> Result<Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>> into(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15, Field<T16> var16, Field<T17> var17);

   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> Result<Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>> into(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15, Field<T16> var16, Field<T17> var17, Field<T18> var18);

   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> Result<Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>> into(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15, Field<T16> var16, Field<T17> var17, Field<T18> var18, Field<T19> var19);

   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> Result<Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>> into(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15, Field<T16> var16, Field<T17> var17, Field<T18> var18, Field<T19> var19, Field<T20> var20);

   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> Result<Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>> into(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15, Field<T16> var16, Field<T17> var17, Field<T18> var18, Field<T19> var19, Field<T20> var20, Field<T21> var21);

   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> Result<Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>> into(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15, Field<T16> var16, Field<T17> var17, Field<T18> var18, Field<T19> var19, Field<T20> var20, Field<T21> var21, Field<T22> var22);

   <E> List<E> into(Class<? extends E> var1) throws MappingException;

   <Z extends Record> Result<Z> into(Table<Z> var1) throws MappingException;

   <H extends RecordHandler<? super R>> H into(H var1);

   ResultSet intoResultSet();

   <E> List<E> map(RecordMapper<? super R, E> var1);

   <T extends Comparable<? super T>> Result<R> sortAsc(Field<T> var1) throws IllegalArgumentException;

   <T extends Comparable<? super T>> Result<R> sortDesc(Field<T> var1) throws IllegalArgumentException;

   Result<R> sortAsc(int var1) throws IllegalArgumentException;

   Result<R> sortDesc(int var1) throws IllegalArgumentException;

   Result<R> sortAsc(String var1) throws IllegalArgumentException;

   Result<R> sortDesc(String var1) throws IllegalArgumentException;

   Result<R> sortAsc(Name var1) throws IllegalArgumentException;

   Result<R> sortDesc(Name var1) throws IllegalArgumentException;

   <T> Result<R> sortAsc(Field<T> var1, java.util.Comparator<? super T> var2) throws IllegalArgumentException;

   <T> Result<R> sortDesc(Field<T> var1, java.util.Comparator<? super T> var2) throws IllegalArgumentException;

   Result<R> sortAsc(int var1, java.util.Comparator<?> var2) throws IllegalArgumentException;

   Result<R> sortDesc(int var1, java.util.Comparator<?> var2) throws IllegalArgumentException;

   Result<R> sortAsc(String var1, java.util.Comparator<?> var2) throws IllegalArgumentException;

   Result<R> sortDesc(String var1, java.util.Comparator<?> var2) throws IllegalArgumentException;

   Result<R> sortAsc(Name var1, java.util.Comparator<?> var2) throws IllegalArgumentException;

   Result<R> sortDesc(Name var1, java.util.Comparator<?> var2) throws IllegalArgumentException;

   Result<R> sortAsc(java.util.Comparator<? super R> var1);

   Result<R> sortDesc(java.util.Comparator<? super R> var1);

   Result<R> intern(Field<?>... var1);

   Result<R> intern(int... var1);

   Result<R> intern(String... var1);

   Result<R> intern(Name... var1);

   <O extends UpdatableRecord<O>> Result<O> fetchParents(ForeignKey<R, O> var1) throws DataAccessException;

   <O extends TableRecord<O>> Result<O> fetchChildren(ForeignKey<O, R> var1) throws DataAccessException;

   void attach(Configuration var1);

   void detach();
}
