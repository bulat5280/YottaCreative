package org.jooq.impl;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinPool.ManagedBlocker;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import org.jooq.Attachable;
import org.jooq.AttachableInternal;
import org.jooq.BindContext;
import org.jooq.Catalog;
import org.jooq.Clause;
import org.jooq.Condition;
import org.jooq.ConditionProvider;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.Cursor;
import org.jooq.DataType;
import org.jooq.EnumType;
import org.jooq.ExecuteContext;
import org.jooq.ExecuteListener;
import org.jooq.Field;
import org.jooq.Name;
import org.jooq.Param;
import org.jooq.Query;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.RecordType;
import org.jooq.RenderContext;
import org.jooq.Result;
import org.jooq.Results;
import org.jooq.Row;
import org.jooq.SQLDialect;
import org.jooq.Schema;
import org.jooq.SchemaMapping;
import org.jooq.SelectField;
import org.jooq.SortField;
import org.jooq.Table;
import org.jooq.TableRecord;
import org.jooq.UDT;
import org.jooq.UDTRecord;
import org.jooq.UpdatableRecord;
import org.jooq.conf.BackslashEscaping;
import org.jooq.conf.ParamType;
import org.jooq.conf.Settings;
import org.jooq.conf.SettingsTools;
import org.jooq.exception.DataAccessException;
import org.jooq.exception.MappingException;
import org.jooq.exception.TooManyRowsException;
import org.jooq.tools.JooqLogger;
import org.jooq.tools.StringUtils;
import org.jooq.tools.jdbc.JDBCUtils;
import org.jooq.tools.reflect.Reflect;
import org.jooq.types.UByte;
import org.jooq.types.UInteger;
import org.jooq.types.ULong;
import org.jooq.types.UShort;

final class Tools {
   static final JooqLogger log = JooqLogger.getLogger(Tools.class);
   static final Class<?>[] EMPTY_CLASS = new Class[0];
   static final Clause[] EMPTY_CLAUSE = new Clause[0];
   static final Collection<?>[] EMPTY_COLLECTION = new Collection[0];
   static final ExecuteListener[] EMPTY_EXECUTE_LISTENER = new ExecuteListener[0];
   static final Field<?>[] EMPTY_FIELD = new Field[0];
   static final int[] EMPTY_INT = new int[0];
   static final Param<?>[] EMPTY_PARAM = new Param[0];
   static final Query[] EMPTY_QUERY = new Query[0];
   static final QueryPart[] EMPTY_QUERYPART = new QueryPart[0];
   static final Record[] EMPTY_RECORD = new Record[0];
   static final String[] EMPTY_STRING = new String[0];
   static final TableRecord<?>[] EMPTY_TABLE_RECORD = new TableRecord[0];
   static final UpdatableRecord<?>[] EMPTY_UPDATABLE_RECORD = new UpdatableRecord[0];
   static final String DATA_REFLECTION_CACHE_GET_ANNOTATED_GETTER = new String("org.jooq.configuration.reflection-cache.get-annotated-getter");
   static final String DATA_REFLECTION_CACHE_GET_ANNOTATED_MEMBERS = new String("org.jooq.configuration.reflection-cache.get-annotated-members");
   static final String DATA_REFLECTION_CACHE_GET_ANNOTATED_SETTERS = new String("org.jooq.configuration.reflection-cache.get-annotated-setters");
   static final String DATA_REFLECTION_CACHE_GET_MATCHING_GETTER = new String("org.jooq.configuration.reflection-cache.get-matching-getter");
   static final String DATA_REFLECTION_CACHE_GET_MATCHING_MEMBERS = new String("org.jooq.configuration.reflection-cache.get-matching-members");
   static final String DATA_REFLECTION_CACHE_GET_MATCHING_SETTERS = new String("org.jooq.configuration.reflection-cache.get-matching-setters");
   static final String DATA_REFLECTION_CACHE_HAS_COLUMN_ANNOTATIONS = new String("org.jooq.configuration.reflection-cache.has-column-annotations");
   static final char ESCAPE = '!';
   private static Boolean isJPAAvailable;
   private static int maxConsumedExceptions = 256;
   private static int maxConsumedResults = 65536;
   private static final Pattern DASH_PATTERN = Pattern.compile("(-+)");
   private static final Pattern PLUS_PATTERN = Pattern.compile("\\+(-+)(?=\\+)");
   private static final String WHITESPACE = " \t\n\u000b\f\r";
   private static final String[] JDBC_ESCAPE_PREFIXES = new String[]{"{fn ", "{d ", "{t ", "{ts "};
   private static final String[] NON_BIND_VARIABLE_SUFFIXES = new String[]{"?", "|", "&"};
   private static final Pattern P_PARSE_HTML_ROW = Pattern.compile("<tr>(.*?)</tr>");
   private static final Pattern P_PARSE_HTML_COL_HEAD = Pattern.compile("<th>(.*?)</th>");
   private static final Pattern P_PARSE_HTML_COL_BODY = Pattern.compile("<td>(.*?)</td>");

   static final List<Row> rows(Result<?> result) {
      List<Row> rows = new ArrayList();
      Iterator var2 = result.iterator();

      while(var2.hasNext()) {
         Record record = (Record)var2.next();
         rows.add(record.valuesRow());
      }

      return rows;
   }

   static final <R extends Record> RecordDelegate<R> newRecord(boolean fetched, Class<R> type) {
      return newRecord(fetched, (Class)type, (Field[])null);
   }

   static final <R extends Record> RecordDelegate<R> newRecord(boolean fetched, Class<R> type, Field<?>[] fields) {
      return newRecord(fetched, type, fields, (Configuration)null);
   }

   static final <R extends Record> RecordDelegate<R> newRecord(boolean fetched, Table<R> type) {
      return newRecord(fetched, (Table)type, (Configuration)null);
   }

   static final <R extends Record> RecordDelegate<R> newRecord(boolean fetched, Table<R> type, Configuration configuration) {
      return newRecord(fetched, type.getRecordType(), type.fields(), configuration);
   }

   static final <R extends UDTRecord<R>> RecordDelegate<R> newRecord(boolean fetched, UDT<R> type) {
      return newRecord(fetched, (UDT)type, (Configuration)null);
   }

   static final <R extends UDTRecord<R>> RecordDelegate<R> newRecord(boolean fetched, UDT<R> type, Configuration configuration) {
      return newRecord(fetched, type.getRecordType(), type.fields(), configuration);
   }

   static final <R extends Record> RecordDelegate<R> newRecord(boolean fetched, Class<R> type, Field<?>[] fields, Configuration configuration) {
      return newRecord(fetched, recordFactory(type, fields), configuration);
   }

   static final <R extends Record> RecordDelegate<R> newRecord(boolean fetched, RecordFactory<R> factory, Configuration configuration) {
      try {
         R record = factory.newInstance();
         if (record instanceof AbstractRecord) {
            ((AbstractRecord)record).fetched = fetched;
         }

         return new RecordDelegate(configuration, record);
      } catch (Exception var4) {
         throw new IllegalStateException("Could not construct new record", var4);
      }
   }

   static final <R extends Record> RecordFactory<R> recordFactory(Class<R> type, Field<?>[] fields) {
      if (type != RecordImpl.class && type != Record.class) {
         try {
            final Constructor<R> constructor = (Constructor)Reflect.accessible(type.getDeclaredConstructor());
            return new RecordFactory<R>() {
               public R newInstance() {
                  try {
                     return (Record)constructor.newInstance();
                  } catch (Exception var2) {
                     throw new IllegalStateException("Could not construct new record", var2);
                  }
               }
            };
         } catch (Exception var3) {
            throw new IllegalStateException("Could not construct new record", var3);
         }
      } else {
         final RowImpl row = new RowImpl(fields);
         return new RecordFactory<R>() {
            public R newInstance() {
               return new RecordImpl(row);
            }
         };
      }
   }

   static final void resetChangedOnNotNull(Record record) {
      int size = record.size();

      for(int i = 0; i < size; ++i) {
         if (record.get(i) == null && !record.field(i).getDataType().nullable()) {
            record.changed(i, false);
         }
      }

   }

   static final Configuration getConfiguration(Attachable attachable) {
      return attachable instanceof AttachableInternal ? ((AttachableInternal)attachable).configuration() : null;
   }

   static final Configuration configuration(Attachable attachable) {
      return configuration(attachable instanceof AttachableInternal ? ((AttachableInternal)attachable).configuration() : null);
   }

   static final Configuration configuration(Configuration configuration) {
      return (Configuration)(configuration != null ? configuration : new DefaultConfiguration());
   }

   static final Settings settings(Attachable attachable) {
      return configuration(attachable).settings();
   }

   static final Settings settings(Configuration configuration) {
      return configuration(configuration).settings();
   }

   static final boolean attachRecords(Configuration configuration) {
      if (configuration != null) {
         Settings settings = configuration.settings();
         if (settings != null) {
            return !Boolean.FALSE.equals(settings.isAttachRecords());
         }
      }

      return true;
   }

   static final Field<?>[] fieldArray(Collection<? extends Field<?>> fields) {
      return fields == null ? null : (Field[])fields.toArray(EMPTY_FIELD);
   }

   static final Class<?>[] types(Field<?>[] fields) {
      return types(dataTypes(fields));
   }

   static final Class<?>[] types(DataType<?>[] types) {
      if (types == null) {
         return null;
      } else {
         Class<?>[] result = new Class[types.length];

         for(int i = 0; i < types.length; ++i) {
            if (types[i] != null) {
               result[i] = types[i].getType();
            } else {
               result[i] = Object.class;
            }
         }

         return result;
      }
   }

   static final Class<?>[] types(Object[] values) {
      if (values == null) {
         return null;
      } else {
         Class<?>[] result = new Class[values.length];

         for(int i = 0; i < values.length; ++i) {
            if (values[i] instanceof Field) {
               result[i] = ((Field)values[i]).getType();
            } else if (values[i] != null) {
               result[i] = values[i].getClass();
            } else {
               result[i] = Object.class;
            }
         }

         return result;
      }
   }

   static final DataType<?>[] dataTypes(Field<?>[] fields) {
      if (fields == null) {
         return null;
      } else {
         DataType<?>[] result = new DataType[fields.length];

         for(int i = 0; i < fields.length; ++i) {
            if (fields[i] != null) {
               result[i] = fields[i].getDataType();
            } else {
               result[i] = DSL.getDataType(Object.class);
            }
         }

         return result;
      }
   }

   static final DataType<?>[] dataTypes(Class<?>[] types) {
      if (types == null) {
         return null;
      } else {
         DataType<?>[] result = new DataType[types.length];

         for(int i = 0; i < types.length; ++i) {
            if (types[i] != null) {
               result[i] = DSL.getDataType(types[i]);
            } else {
               result[i] = DSL.getDataType(Object.class);
            }
         }

         return result;
      }
   }

   static final DataType<?>[] dataTypes(Object[] values) {
      return dataTypes(types(values));
   }

   static final SortField<?>[] sortFields(Field<?>[] fields) {
      if (fields == null) {
         return null;
      } else {
         SortField<?>[] result = new SortField[fields.length];

         for(int i = 0; i < fields.length; ++i) {
            result[i] = fields[i].asc();
         }

         return result;
      }
   }

   static final String[] fieldNames(int length) {
      String[] result = new String[length];

      for(int i = 0; i < length; ++i) {
         result[i] = "v" + i;
      }

      return result;
   }

   static final String[] fieldNames(Field<?>[] fields) {
      if (fields == null) {
         return null;
      } else {
         String[] result = new String[fields.length];

         for(int i = 0; i < fields.length; ++i) {
            result[i] = fields[i].getName();
         }

         return result;
      }
   }

   static final Field<?>[] fields(int length) {
      Field<?>[] result = new Field[length];
      String[] names = fieldNames(length);

      for(int i = 0; i < length; ++i) {
         result[i] = DSL.field(DSL.name(names[i]));
      }

      return result;
   }

   static final Field<?>[] aliasedFields(Field<?>[] fields, String[] aliases) {
      if (fields == null) {
         return null;
      } else {
         Field<?>[] result = new Field[fields.length];

         for(int i = 0; i < fields.length; ++i) {
            result[i] = fields[i].as(aliases[i]);
         }

         return result;
      }
   }

   static final Field<?>[] fieldsByName(Collection<String> fieldNames) {
      return fieldsByName((String)null, (String[])((String[])fieldNames.toArray(EMPTY_STRING)));
   }

   static final Field<?>[] fieldsByName(String[] fieldNames) {
      return fieldsByName((String)null, (String[])fieldNames);
   }

   static final Field<?>[] fieldsByName(String tableName, Collection<String> fieldNames) {
      return fieldsByName(tableName, (String[])fieldNames.toArray(EMPTY_STRING));
   }

   static final Field<?>[] fieldsByName(String tableName, String[] fieldNames) {
      if (fieldNames == null) {
         return null;
      } else {
         Field<?>[] result = new Field[fieldNames.length];
         int i;
         if (tableName == null) {
            for(i = 0; i < fieldNames.length; ++i) {
               result[i] = DSL.field(DSL.name(fieldNames[i]));
            }
         } else {
            for(i = 0; i < fieldNames.length; ++i) {
               result[i] = DSL.field(DSL.name(tableName, fieldNames[i]));
            }
         }

         return result;
      }
   }

   static final Field<?>[] fieldsByName(Name[] names) {
      if (names == null) {
         return null;
      } else {
         Field<?>[] result = new Field[names.length];

         for(int i = 0; i < names.length; ++i) {
            result[i] = DSL.field(names[i]);
         }

         return result;
      }
   }

   static final Name[] names(String[] names) {
      if (names == null) {
         return null;
      } else {
         Name[] result = new Name[names.length];

         for(int i = 0; i < names.length; ++i) {
            result[i] = DSL.name(names[i]);
         }

         return result;
      }
   }

   private static final IllegalArgumentException fieldExpected(Object value) {
      return new IllegalArgumentException("Cannot interpret argument of type " + value.getClass() + " as a Field: " + value);
   }

   static final <T> Field<T> field(T value) {
      if (value instanceof Field) {
         return (Field)value;
      } else if (value instanceof QueryPart) {
         throw fieldExpected(value);
      } else {
         return DSL.val(value);
      }
   }

   static final Param<Byte> field(byte value) {
      return DSL.val(value, (DataType)SQLDataType.TINYINT);
   }

   static final Param<Byte> field(Byte value) {
      return DSL.val(value, (DataType)SQLDataType.TINYINT);
   }

   static final Param<UByte> field(UByte value) {
      return DSL.val(value, (DataType)SQLDataType.TINYINTUNSIGNED);
   }

   static final Param<Short> field(short value) {
      return DSL.val(value, (DataType)SQLDataType.SMALLINT);
   }

   static final Param<Short> field(Short value) {
      return DSL.val(value, (DataType)SQLDataType.SMALLINT);
   }

   static final Param<UShort> field(UShort value) {
      return DSL.val(value, (DataType)SQLDataType.SMALLINTUNSIGNED);
   }

   static final Param<Integer> field(int value) {
      return DSL.val(value, (DataType)SQLDataType.INTEGER);
   }

   static final Param<Integer> field(Integer value) {
      return DSL.val(value, (DataType)SQLDataType.INTEGER);
   }

   static final Param<UInteger> field(UInteger value) {
      return DSL.val(value, (DataType)SQLDataType.INTEGERUNSIGNED);
   }

   static final Param<Long> field(long value) {
      return DSL.val(value, (DataType)SQLDataType.BIGINT);
   }

   static final Param<Long> field(Long value) {
      return DSL.val(value, (DataType)SQLDataType.BIGINT);
   }

   static final Param<ULong> field(ULong value) {
      return DSL.val(value, (DataType)SQLDataType.BIGINTUNSIGNED);
   }

   static final Param<Float> field(float value) {
      return DSL.val(value, (DataType)SQLDataType.REAL);
   }

   static final Param<Float> field(Float value) {
      return DSL.val(value, (DataType)SQLDataType.REAL);
   }

   static final Param<Double> field(double value) {
      return DSL.val(value, (DataType)SQLDataType.DOUBLE);
   }

   static final Param<Double> field(Double value) {
      return DSL.val(value, (DataType)SQLDataType.DOUBLE);
   }

   static final Param<Boolean> field(boolean value) {
      return DSL.val(value, (DataType)SQLDataType.BOOLEAN);
   }

   static final Param<Boolean> field(Boolean value) {
      return DSL.val(value, (DataType)SQLDataType.BOOLEAN);
   }

   static final Param<BigDecimal> field(BigDecimal value) {
      return DSL.val(value, (DataType)SQLDataType.DECIMAL);
   }

   static final Param<BigInteger> field(BigInteger value) {
      return DSL.val(value, (DataType)SQLDataType.DECIMAL_INTEGER);
   }

   static final Param<byte[]> field(byte[] value) {
      return DSL.val(value, (DataType)SQLDataType.VARBINARY);
   }

   static final Param<String> field(String value) {
      return DSL.val(value, (DataType)SQLDataType.VARCHAR);
   }

   static final Param<Date> field(Date value) {
      return DSL.val(value, (DataType)SQLDataType.DATE);
   }

   static final Param<Time> field(Time value) {
      return DSL.val(value, (DataType)SQLDataType.TIME);
   }

   static final Param<Timestamp> field(Timestamp value) {
      return DSL.val(value, (DataType)SQLDataType.TIMESTAMP);
   }

   static final Param<LocalDate> field(LocalDate value) {
      return DSL.val(value, (DataType)SQLDataType.LOCALDATE);
   }

   static final Param<LocalTime> field(LocalTime value) {
      return DSL.val(value, (DataType)SQLDataType.LOCALTIME);
   }

   static final Param<LocalDateTime> field(LocalDateTime value) {
      return DSL.val(value, (DataType)SQLDataType.LOCALDATETIME);
   }

   static final Param<OffsetTime> field(OffsetTime value) {
      return DSL.val(value, (DataType)SQLDataType.OFFSETTIME);
   }

   static final Param<OffsetDateTime> field(OffsetDateTime value) {
      return DSL.val(value, (DataType)SQLDataType.OFFSETDATETIME);
   }

   static final Param<UUID> field(UUID value) {
      return DSL.val(value, (DataType)SQLDataType.UUID);
   }

   /** @deprecated */
   @Deprecated
   static final Field<Object> field(Name name) {
      return DSL.field(name);
   }

   static final <T> Field<T> field(Object value, Field<T> field) {
      if (value instanceof Field) {
         return (Field)value;
      } else if (value instanceof QueryPart) {
         throw fieldExpected(value);
      } else {
         return DSL.val(value, field);
      }
   }

   static final <T> Field<T> field(Object value, Class<T> type) {
      if (value instanceof Field) {
         return (Field)value;
      } else if (value instanceof QueryPart) {
         throw fieldExpected(value);
      } else {
         return DSL.val(value, type);
      }
   }

   static final <T> Field<T> field(Object value, DataType<T> type) {
      if (value instanceof Field) {
         return (Field)value;
      } else if (value instanceof QueryPart) {
         throw fieldExpected(value);
      } else {
         return DSL.val(value, type);
      }
   }

   static final <T> List<Field<T>> fields(T[] values) {
      List<Field<T>> result = new ArrayList();
      if (values != null) {
         Object[] var2 = values;
         int var3 = values.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            T value = var2[var4];
            result.add(field(value));
         }
      }

      return result;
   }

   static final List<Field<?>> fields(Object[] values, Field<?> field) {
      List<Field<?>> result = new ArrayList();
      if (values != null && field != null) {
         for(int i = 0; i < values.length; ++i) {
            result.add(field(values[i], field));
         }
      }

      return result;
   }

   static final List<Field<?>> fields(Object[] values, Field<?>[] fields) {
      List<Field<?>> result = new ArrayList();
      if (values != null && fields != null) {
         for(int i = 0; i < values.length && i < fields.length; ++i) {
            result.add(field(values[i], fields[i]));
         }
      }

      return result;
   }

   static final List<Field<?>> fields(Object[] values, Class<?> type) {
      List<Field<?>> result = new ArrayList();
      if (values != null && type != null) {
         for(int i = 0; i < values.length; ++i) {
            result.add(field(values[i], type));
         }
      }

      return result;
   }

   static final List<Field<?>> fields(Object[] values, Class<?>[] types) {
      List<Field<?>> result = new ArrayList();
      if (values != null && types != null) {
         for(int i = 0; i < values.length && i < types.length; ++i) {
            result.add(field(values[i], types[i]));
         }
      }

      return result;
   }

   static final List<Field<?>> fields(Object[] values, DataType<?> type) {
      List<Field<?>> result = new ArrayList();
      if (values != null && type != null) {
         for(int i = 0; i < values.length; ++i) {
            result.add(field(values[i], type));
         }
      }

      return result;
   }

   static final List<Field<?>> fields(Object[] values, DataType<?>[] types) {
      List<Field<?>> result = new ArrayList();
      if (values != null && types != null) {
         for(int i = 0; i < values.length && i < types.length; ++i) {
            result.add(field(values[i], types[i]));
         }
      }

      return result;
   }

   static final List<Field<?>> fields(Collection<? extends SelectField<?>> fields) {
      List<Field<?>> result = new ArrayList();
      if (fields != null) {
         Iterator var2 = fields.iterator();

         while(var2.hasNext()) {
            SelectField<?> field = (SelectField)var2.next();
            result.add(DSL.field(field));
         }
      }

      return result;
   }

   static final List<Field<?>> fields(SelectField<?>... fields) {
      return fields == null ? fields((Collection)Collections.emptyList()) : fields((Collection)Arrays.asList(fields));
   }

   static final <T> List<Field<T>> inline(T[] values) {
      List<Field<T>> result = new ArrayList();
      if (values != null) {
         Object[] var2 = values;
         int var3 = values.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            T value = var2[var4];
            result.add(DSL.inline(value));
         }
      }

      return result;
   }

   static final List<Field<?>> unqualify(List<? extends Field<?>> fields) {
      QueryPartList<Field<?>> result = new QueryPartList();
      Iterator var2 = fields.iterator();

      while(var2.hasNext()) {
         Field<?> field = (Field)var2.next();
         result.add((QueryPart)DSL.field(DSL.name(field.getName())));
      }

      return result;
   }

   static final int indexOrFail(Row row, Field<?> field) {
      int result = row.indexOf(field);
      if (result < 0) {
         throw new IllegalArgumentException("Field (" + field + ") is not contained in Row " + row);
      } else {
         return result;
      }
   }

   static final int indexOrFail(Row row, String fieldName) {
      int result = row.indexOf(fieldName);
      if (result < 0) {
         throw new IllegalArgumentException("Field (" + fieldName + ") is not contained in Row " + row);
      } else {
         return result;
      }
   }

   static final int indexOrFail(Row row, Name fieldName) {
      int result = row.indexOf(fieldName);
      if (result < 0) {
         throw new IllegalArgumentException("Field (" + fieldName + ") is not contained in Row " + row);
      } else {
         return result;
      }
   }

   static final int indexOrFail(RecordType<?> row, Field<?> field) {
      int result = row.indexOf(field);
      if (result < 0) {
         throw new IllegalArgumentException("Field (" + field + ") is not contained in RecordType " + row);
      } else {
         return result;
      }
   }

   static final int indexOrFail(RecordType<?> row, String fieldName) {
      int result = row.indexOf(fieldName);
      if (result < 0) {
         throw new IllegalArgumentException("Field (" + fieldName + ") is not contained in RecordType " + row);
      } else {
         return result;
      }
   }

   static final int indexOrFail(RecordType<?> row, Name fieldName) {
      int result = row.indexOf(fieldName);
      if (result < 0) {
         throw new IllegalArgumentException("Field (" + fieldName + ") is not contained in RecordType " + row);
      } else {
         return result;
      }
   }

   @SafeVarargs
   static final <T> T[] array(T... array) {
      return array;
   }

   @SafeVarargs
   static final <T> List<T> list(T... array) {
      return array == null ? Collections.emptyList() : Arrays.asList(array);
   }

   static final Map<Field<?>, Object> mapOfChangedValues(Record record) {
      Map<Field<?>, Object> result = new LinkedHashMap();
      int size = record.size();

      for(int i = 0; i < size; ++i) {
         if (record.changed(i)) {
            result.put(record.field(i), record.get(i));
         }
      }

      return result;
   }

   static final <T> T first(Iterable<? extends T> iterable) {
      if (iterable == null) {
         return null;
      } else {
         Iterator<? extends T> iterator = iterable.iterator();
         return iterator.hasNext() ? iterator.next() : null;
      }
   }

   static final <R extends Record> R filterOne(List<R> list) throws TooManyRowsException {
      int size = list.size();
      if (size == 1) {
         return (Record)list.get(0);
      } else if (size > 1) {
         throw new TooManyRowsException("Too many rows selected : " + size);
      } else {
         return null;
      }
   }

   static final <R extends Record> R fetchOne(Cursor<R> cursor) throws TooManyRowsException {
      Record var2;
      try {
         R record = cursor.fetchOne();
         if (cursor.hasNext()) {
            throw new TooManyRowsException("Cursor returned more than one result");
         }

         var2 = record;
      } finally {
         cursor.close();
      }

      return var2;
   }

   static final <C extends Context<? super C>> C visitAll(C ctx, Collection<? extends QueryPart> parts) {
      if (parts != null) {
         Iterator var2 = parts.iterator();

         while(var2.hasNext()) {
            QueryPart part = (QueryPart)var2.next();
            ctx.visit(part);
         }
      }

      return ctx;
   }

   static final <C extends Context<? super C>> C visitAll(C ctx, QueryPart[] parts) {
      if (parts != null) {
         QueryPart[] var2 = parts;
         int var3 = parts.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            QueryPart part = var2[var4];
            ctx.visit(part);
         }
      }

      return ctx;
   }

   static final void renderAndBind(Context<?> ctx, String sql, List<QueryPart> substitutes) {
      RenderContext render = (RenderContext)((RenderContext)(ctx instanceof RenderContext ? ctx : null));
      BindContext bind = (BindContext)((BindContext)(ctx instanceof BindContext ? ctx : null));
      int substituteIndex = 0;
      char[] sqlChars = sql.toCharArray();
      if (render == null) {
         render = new DefaultRenderContext(bind.configuration());
      }

      SQLDialect dialect = ((RenderContext)render).configuration().dialect();
      SQLDialect family = dialect.family();
      boolean mysql = Arrays.asList(SQLDialect.MARIADB, SQLDialect.MYSQL).contains(family);
      String[][] quotes = (String[][])Identifiers.QUOTES.get(family);
      boolean needsBackslashEscaping = needsBackslashEscaping(ctx.configuration());

      label247:
      for(int i = 0; i < sqlChars.length; ++i) {
         if (peek(sqlChars, i, "--") || mysql && peek(sqlChars, i, "#")) {
            while(i < sqlChars.length && sqlChars[i] != '\r' && sqlChars[i] != '\n') {
               ((RenderContext)render).sql(sqlChars[i++]);
            }

            if (i < sqlChars.length) {
               ((RenderContext)render).sql(sqlChars[i]);
            }
         } else if (peek(sqlChars, i, "/*")) {
            while(!peek(sqlChars, i, "*/")) {
               ((RenderContext)render).sql(sqlChars[i++]);
            }

            ((RenderContext)render).sql(sqlChars[i++]);
            ((RenderContext)render).sql(sqlChars[i]);
         } else if (sqlChars[i] == '\'') {
            ((RenderContext)render).sql(sqlChars[i++]);

            while(true) {
               if (sqlChars[i] == '\\' && needsBackslashEscaping) {
                  ((RenderContext)render).sql(sqlChars[i++]);
               } else if (peek(sqlChars, i, "''")) {
                  ((RenderContext)render).sql(sqlChars[i++]);
               } else if (peek(sqlChars, i, "'")) {
                  ((RenderContext)render).sql(sqlChars[i]);
                  break;
               }

               ((RenderContext)render).sql(sqlChars[i++]);
            }
         } else {
            int delimiter;
            int d;
            if (peekAny(sqlChars, i, quotes[0])) {
               delimiter = 0;

               for(d = 0; d < quotes[0].length; ++d) {
                  if (peek(sqlChars, i, quotes[0][d])) {
                     delimiter = d;
                     break;
                  }
               }

               for(d = 0; d < quotes[0][delimiter].length(); ++d) {
                  ((RenderContext)render).sql(sqlChars[i++]);
               }

               while(true) {
                  if (peek(sqlChars, i, quotes[2][delimiter])) {
                     for(d = 0; d < quotes[2][delimiter].length(); ++d) {
                        ((RenderContext)render).sql(sqlChars[i++]);
                     }
                  } else if (peek(sqlChars, i, quotes[1][delimiter])) {
                     d = 0;

                     while(true) {
                        if (d >= quotes[1][delimiter].length()) {
                           continue label247;
                        }

                        if (d > 0) {
                           ++i;
                        }

                        ((RenderContext)render).sql(sqlChars[i]);
                        ++d;
                     }
                  }

                  ((RenderContext)render).sql(sqlChars[i++]);
               }
            } else if (substituteIndex < substitutes.size() && (sqlChars[i] == '?' || sqlChars[i] == ':' && i + 1 < sqlChars.length && Character.isJavaIdentifierPart(sqlChars[i + 1]) && (i - 1 < 0 || sqlChars[i - 1] != ':'))) {
               if (sqlChars[i] == '?' && i + 1 < sqlChars.length) {
                  String[] var19 = NON_BIND_VARIABLE_SUFFIXES;
                  d = var19.length;

                  for(int var22 = 0; var22 < d; ++var22) {
                     String suffix = var19[var22];
                     if (peek(sqlChars, i + 1, suffix)) {
                        for(int j = i; i - j <= suffix.length(); ++i) {
                           ((RenderContext)render).sql(sqlChars[i]);
                        }

                        ((RenderContext)render).sql(sqlChars[i]);
                        continue label247;
                     }
                  }
               }

               if (sqlChars[i] == ':') {
                  do {
                     ++i;
                  } while(i < sqlChars.length && Character.isJavaIdentifierPart(sqlChars[i]));
               }

               QueryPart substitute = (QueryPart)substitutes.get(substituteIndex++);
               if (((RenderContext)render).paramType() != ParamType.INLINED && ((RenderContext)render).paramType() != ParamType.NAMED && ((RenderContext)render).paramType() != ParamType.NAMED_OR_INLINED) {
                  RenderContext.CastMode previous = ((RenderContext)render).castMode();
                  ((RenderContext)((RenderContext)render).castMode(RenderContext.CastMode.NEVER).visit(substitute)).castMode(previous);
               } else {
                  ((RenderContext)render).visit(substitute);
               }

               if (bind != null) {
                  bind.visit(substitute);
               }
            } else if (sqlChars[i] != '{') {
               ((RenderContext)render).sql(sqlChars[i]);
            } else if (peekAny(sqlChars, i, JDBC_ESCAPE_PREFIXES, true)) {
               ((RenderContext)render).sql(sqlChars[i]);
            } else {
               ++i;

               for(delimiter = i; i < sqlChars.length && sqlChars[i] != '}'; ++i) {
               }

               String token = sql.substring(delimiter, i);

               try {
                  QueryPart substitute = (QueryPart)substitutes.get(Integer.valueOf(token));
                  ((RenderContext)render).visit(substitute);
                  if (bind != null) {
                     bind.visit(substitute);
                  }
               } catch (NumberFormatException var18) {
                  ((RenderContext)render).keyword(token);
               }
            }
         }
      }

   }

   static final boolean needsBackslashEscaping(Configuration configuration) {
      BackslashEscaping escaping = SettingsTools.getBackslashEscaping(configuration.settings());
      return escaping == BackslashEscaping.ON || escaping == BackslashEscaping.DEFAULT && EnumSet.of(SQLDialect.MARIADB, SQLDialect.MYSQL).contains(configuration.dialect().family());
   }

   static final boolean peek(char[] sqlChars, int index, String peek) {
      return peek(sqlChars, index, peek, false);
   }

   static final boolean peek(char[] sqlChars, int index, String peek, boolean anyWhitespace) {
      char[] peekArray = peek.toCharArray();

      label37:
      for(int i = 0; i < peekArray.length; ++i) {
         if (index + i >= sqlChars.length) {
            return false;
         }

         if (sqlChars[index + i] != peekArray[i]) {
            if (!anyWhitespace || peekArray[i] != ' ') {
               return false;
            }

            for(int j = 0; j < " \t\n\u000b\f\r".length(); ++j) {
               if (sqlChars[index + i] == " \t\n\u000b\f\r".charAt(j)) {
                  continue label37;
               }
            }

            return false;
         }
      }

      return true;
   }

   static final boolean peekAny(char[] sqlChars, int index, String[] peekAny) {
      return peekAny(sqlChars, index, peekAny, false);
   }

   static final boolean peekAny(char[] sqlChars, int index, String[] peekAny, boolean anyWhitespace) {
      String[] var4 = peekAny;
      int var5 = peekAny.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         String peek = var4[var6];
         if (peek(sqlChars, index, peek, anyWhitespace)) {
            return true;
         }
      }

      return false;
   }

   static final List<QueryPart> queryParts(Object... substitutes) {
      if (substitutes == null) {
         return queryParts(null);
      } else {
         List<QueryPart> result = new ArrayList();
         Object[] var2 = substitutes;
         int var3 = substitutes.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Object substitute = var2[var4];
            if (substitute instanceof QueryPart) {
               result.add((QueryPart)substitute);
            } else {
               Class<Object> type = substitute != null ? substitute.getClass() : Object.class;
               result.add(new Val(substitute, DSL.getDataType(type)));
            }
         }

         return result;
      }
   }

   static final void fieldNames(Context<?> context, Fields<?> fields) {
      fieldNames(context, (Collection)list(fields.fields));
   }

   static final void fieldNames(Context<?> context, Field<?>... fields) {
      fieldNames(context, (Collection)list(fields));
   }

   static final void fieldNames(Context<?> context, Collection<? extends Field<?>> list) {
      String separator = "";

      for(Iterator var3 = list.iterator(); var3.hasNext(); separator = ", ") {
         Field<?> field = (Field)var3.next();
         context.sql(separator).literal(field.getName());
      }

   }

   static final void tableNames(Context<?> context, Table<?>... list) {
      tableNames(context, (Collection)list(list));
   }

   static final void tableNames(Context<?> context, Collection<? extends Table<?>> list) {
      String separator = "";

      for(Iterator var3 = list.iterator(); var3.hasNext(); separator = ", ") {
         Table<?> table = (Table)var3.next();
         context.sql(separator).literal(table.getName());
      }

   }

   static final <T> T[] combine(T[] array, T value) {
      T[] result = (Object[])((Object[])java.lang.reflect.Array.newInstance(array.getClass().getComponentType(), array.length + 1));
      System.arraycopy(array, 0, result, 0, array.length);
      result[array.length] = value;
      return result;
   }

   static final Field<?>[] combine(Field<?> field, Field<?>... fields) {
      if (fields == null) {
         return new Field[]{field};
      } else {
         Field<?>[] result = new Field[fields.length + 1];
         result[0] = field;
         System.arraycopy(fields, 0, result, 1, fields.length);
         return result;
      }
   }

   static final Field<?>[] combine(Field<?> field1, Field<?> field2, Field<?>... fields) {
      if (fields == null) {
         return new Field[]{field1, field2};
      } else {
         Field<?>[] result = new Field[fields.length + 2];
         result[0] = field1;
         result[1] = field2;
         System.arraycopy(fields, 0, result, 2, fields.length);
         return result;
      }
   }

   static final Field<?>[] combine(Field<?> field1, Field<?> field2, Field<?> field3, Field<?>... fields) {
      if (fields == null) {
         return new Field[]{field1, field2, field3};
      } else {
         Field<?>[] result = new Field[fields.length + 3];
         result[0] = field1;
         result[1] = field2;
         result[2] = field3;
         System.arraycopy(fields, 0, result, 3, fields.length);
         return result;
      }
   }

   static final DataAccessException translate(String sql, SQLException e) {
      String message = "SQL [" + sql + "]; " + e.getMessage();
      return new DataAccessException(message, e);
   }

   static final void safeClose(ExecuteListener listener, ExecuteContext ctx) {
      safeClose(listener, ctx, false);
   }

   static final void safeClose(ExecuteListener listener, ExecuteContext ctx, boolean keepStatement) {
      safeClose(listener, ctx, keepStatement, true);
   }

   static final void safeClose(ExecuteListener listener, ExecuteContext ctx, boolean keepStatement, boolean keepResultSet) {
      JDBCUtils.safeClose(ctx.resultSet());
      ctx.resultSet((ResultSet)null);
      PreparedStatement statement = ctx.statement();
      if (statement != null) {
         consumeWarnings(ctx, listener);
      }

      if (!keepStatement) {
         if (statement != null) {
            JDBCUtils.safeClose((Statement)statement);
            ctx.statement((PreparedStatement)null);
         } else {
            Connection connection = DefaultExecuteContext.localConnection();
            if (connection != null) {
               ctx.configuration().connectionProvider().release(connection);
            }
         }
      }

      if (keepResultSet) {
         listener.end(ctx);
      }

      DefaultExecuteContext.clean();
   }

   static final <T> void setValue(Record target, Field<T> targetField, Record source, Field<?> sourceField) {
      setValue(target, targetField, source.get(sourceField));
   }

   static final <T> void setValue(Record target, Field<T> targetField, Object value) {
      target.set(targetField, targetField.getDataType().convert(value));
   }

   static final <T> void copyValue(AbstractRecord target, Field<T> targetField, Record source, Field<?> sourceField) {
      DataType<T> targetType = targetField.getDataType();
      int targetIndex = indexOrFail(target.fieldsRow(), targetField);
      int sourceIndex = indexOrFail(source.fieldsRow(), sourceField);
      target.values[targetIndex] = targetType.convert(source.get(sourceIndex));
      target.originals[targetIndex] = targetType.convert(source.original(sourceIndex));
      target.changed.set(targetIndex, source.changed(sourceIndex));
   }

   static final Catalog getMappedCatalog(Configuration configuration, Catalog catalog) {
      if (configuration != null) {
         SchemaMapping mapping = configuration.schemaMapping();
         if (mapping != null) {
            return mapping.map(catalog);
         }
      }

      return catalog;
   }

   static final Schema getMappedSchema(Configuration configuration, Schema schema) {
      if (configuration != null) {
         SchemaMapping mapping = configuration.schemaMapping();
         if (mapping != null) {
            return mapping.map(schema);
         }
      }

      return schema;
   }

   static final <R extends Record> Table<R> getMappedTable(Configuration configuration, Table<R> table) {
      if (configuration != null) {
         SchemaMapping mapping = configuration.schemaMapping();
         if (mapping != null) {
            return mapping.map(table);
         }
      }

      return table;
   }

   static final String getMappedUDTName(Configuration configuration, Class<? extends UDTRecord<?>> type) {
      return getMappedUDTName(configuration, (UDTRecord)newRecord(false, type).operate((RecordOperation)null));
   }

   static final String getMappedUDTName(Configuration configuration, UDTRecord<?> record) {
      UDT<?> udt = record.getUDT();
      Schema mapped = getMappedSchema(configuration, udt.getSchema());
      StringBuilder sb = new StringBuilder();
      if (mapped != null) {
         sb.append(mapped.getName()).append('.');
      }

      sb.append(record.getUDT().getName());
      return sb.toString();
   }

   static final int hash(Object object) {
      return 134217727 & object.hashCode();
   }

   static final Field<String> escapeForLike(Object value) {
      return escapeForLike((Object)value, new DefaultConfiguration());
   }

   static final Field<String> escapeForLike(Object value, Configuration configuration) {
      return value != null && value.getClass() == String.class ? DSL.val(DSL.escape("" + value, '!')) : DSL.val("" + value);
   }

   static final Field<String> escapeForLike(Field<?> field) {
      return escapeForLike((Field)field, new DefaultConfiguration());
   }

   static final Field<String> escapeForLike(Field<?> field, Configuration configuration) {
      return DSL.nullSafe(field).getDataType().isString() ? DSL.escape(field, '!') : field.cast(String.class);
   }

   static final boolean isVal(Field<?> field) {
      return field instanceof Param;
   }

   static final <T> T extractVal(Field<T> field) {
      return isVal(field) ? ((Param)field).getValue() : null;
   }

   static final void addConditions(ConditionProvider query, Record record, Field<?>... keys) {
      Field[] var3 = keys;
      int var4 = keys.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Field<?> field = var3[var5];
         addCondition(query, record, field);
      }

   }

   static final <T> void addCondition(ConditionProvider provider, Record record, Field<T> field) {
      if (SettingsTools.updatablePrimaryKeys(settings((Attachable)record))) {
         provider.addConditions(condition(field, record.original(field)));
      } else {
         provider.addConditions(condition(field, record.get(field)));
      }

   }

   static final <T> Condition condition(Field<T> field, T value) {
      return value == null ? field.isNull() : field.eq(value);
   }

   private static final boolean isJPAAvailable() {
      if (isJPAAvailable == null) {
         try {
            Class.forName(Column.class.getName());
            isJPAAvailable = true;
         } catch (Throwable var1) {
            isJPAAvailable = false;
         }
      }

      return isJPAAvailable;
   }

   static final boolean hasColumnAnnotations(Configuration configuration, final Class<?> type) {
      return (Boolean)Tools.Cache.run(configuration, new Tools.Cache.CachedOperation<Boolean>() {
         public Boolean call() {
            if (!Tools.isJPAAvailable()) {
               return false;
            } else if (type.getAnnotation(Entity.class) != null) {
               return true;
            } else if (type.getAnnotation(javax.persistence.Table.class) != null) {
               return true;
            } else {
               Iterator var1 = Tools.getInstanceMembers(type).iterator();

               java.lang.reflect.Field member;
               do {
                  if (!var1.hasNext()) {
                     var1 = Tools.getInstanceMethods(type).iterator();

                     Method method;
                     do {
                        if (!var1.hasNext()) {
                           return false;
                        }

                        method = (Method)var1.next();
                     } while(method.getAnnotation(Column.class) == null);

                     return true;
                  }

                  member = (java.lang.reflect.Field)var1.next();
                  if (member.getAnnotation(Column.class) != null) {
                     return true;
                  }
               } while(member.getAnnotation(Id.class) == null);

               return true;
            }
         }
      }, DATA_REFLECTION_CACHE_HAS_COLUMN_ANNOTATIONS, type);
   }

   static final List<java.lang.reflect.Field> getAnnotatedMembers(Configuration configuration, final Class<?> type, final String name) {
      return (List)Tools.Cache.run(configuration, new Tools.Cache.CachedOperation<List<java.lang.reflect.Field>>() {
         public List<java.lang.reflect.Field> call() {
            List<java.lang.reflect.Field> result = new ArrayList();
            Iterator var2 = Tools.getInstanceMembers(type).iterator();

            while(var2.hasNext()) {
               java.lang.reflect.Field member = (java.lang.reflect.Field)var2.next();
               Column column = (Column)member.getAnnotation(Column.class);
               if (column != null) {
                  if (Tools.namesMatch(name, column.name())) {
                     result.add(Reflect.accessible(member));
                  }
               } else {
                  Id id = (Id)member.getAnnotation(Id.class);
                  if (id != null && Tools.namesMatch(name, member.getName())) {
                     result.add(Reflect.accessible(member));
                  }
               }
            }

            return result;
         }
      }, DATA_REFLECTION_CACHE_GET_ANNOTATED_MEMBERS, type, name);
   }

   private static final boolean namesMatch(String name, String annotation) {
      return annotation.startsWith("\"") ? ('"' + name + '"').equals(annotation) : name.equalsIgnoreCase(annotation);
   }

   static final List<java.lang.reflect.Field> getMatchingMembers(Configuration configuration, final Class<?> type, final String name) {
      return (List)Tools.Cache.run(configuration, new Tools.Cache.CachedOperation<List<java.lang.reflect.Field>>() {
         public List<java.lang.reflect.Field> call() {
            List<java.lang.reflect.Field> result = new ArrayList();
            String camelCaseLC = StringUtils.toCamelCaseLC(name);
            Iterator var3 = Tools.getInstanceMembers(type).iterator();

            while(var3.hasNext()) {
               java.lang.reflect.Field member = (java.lang.reflect.Field)var3.next();
               if (name.equals(member.getName())) {
                  result.add(Reflect.accessible(member));
               } else if (camelCaseLC.equals(member.getName())) {
                  result.add(Reflect.accessible(member));
               }
            }

            return result;
         }
      }, DATA_REFLECTION_CACHE_GET_MATCHING_MEMBERS, type, name);
   }

   static final List<Method> getAnnotatedSetters(Configuration configuration, final Class<?> type, final String name) {
      return (List)Tools.Cache.run(configuration, new Tools.Cache.CachedOperation<List<Method>>() {
         public List<Method> call() {
            List<Method> result = new ArrayList();
            Iterator var2 = Tools.getInstanceMethods(type).iterator();

            while(var2.hasNext()) {
               Method method = (Method)var2.next();
               Column column = (Column)method.getAnnotation(Column.class);
               if (column != null && Tools.namesMatch(name, column.name())) {
                  if (method.getParameterTypes().length == 1) {
                     result.add(Reflect.accessible(method));
                  } else if (method.getParameterTypes().length == 0) {
                     String m = method.getName();
                     String suffix = m.startsWith("get") ? m.substring(3) : (m.startsWith("is") ? m.substring(2) : null);
                     if (suffix != null) {
                        try {
                           Method setter = type.getMethod("set" + suffix, method.getReturnType());
                           if (setter.getAnnotation(Column.class) == null) {
                              result.add(Reflect.accessible(setter));
                           }
                        } catch (NoSuchMethodException var8) {
                        }
                     }
                  }
               }
            }

            return result;
         }
      }, DATA_REFLECTION_CACHE_GET_ANNOTATED_SETTERS, type, name);
   }

   static final Method getAnnotatedGetter(Configuration configuration, final Class<?> type, final String name) {
      return (Method)Tools.Cache.run(configuration, new Tools.Cache.CachedOperation<Method>() {
         public Method call() {
            Iterator var1 = Tools.getInstanceMethods(type).iterator();

            while(var1.hasNext()) {
               Method method = (Method)var1.next();
               Column column = (Column)method.getAnnotation(Column.class);
               if (column != null && Tools.namesMatch(name, column.name())) {
                  if (method.getParameterTypes().length == 0) {
                     return (Method)Reflect.accessible(method);
                  }

                  if (method.getParameterTypes().length == 1) {
                     String m = method.getName();
                     if (m.startsWith("set")) {
                        Method getter;
                        try {
                           getter = type.getMethod("get" + m.substring(3));
                           if (getter.getAnnotation(Column.class) == null) {
                              return (Method)Reflect.accessible(getter);
                           }
                        } catch (NoSuchMethodException var7) {
                        }

                        try {
                           getter = type.getMethod("is" + m.substring(3));
                           if (getter.getAnnotation(Column.class) == null) {
                              return (Method)Reflect.accessible(getter);
                           }
                        } catch (NoSuchMethodException var6) {
                        }
                     }
                  }
               }
            }

            return null;
         }
      }, DATA_REFLECTION_CACHE_GET_ANNOTATED_GETTER, type, name);
   }

   static final List<Method> getMatchingSetters(Configuration configuration, final Class<?> type, final String name) {
      return (List)Tools.Cache.run(configuration, new Tools.Cache.CachedOperation<List<Method>>() {
         public List<Method> call() {
            List<Method> result = new ArrayList();
            String camelCase = StringUtils.toCamelCase(name);
            String camelCaseLC = StringUtils.toLC(camelCase);
            Iterator var4 = Tools.getInstanceMethods(type).iterator();

            while(var4.hasNext()) {
               Method method = (Method)var4.next();
               Class<?>[] parameterTypes = method.getParameterTypes();
               if (parameterTypes.length == 1) {
                  if (name.equals(method.getName())) {
                     result.add(Reflect.accessible(method));
                  } else if (camelCaseLC.equals(method.getName())) {
                     result.add(Reflect.accessible(method));
                  } else if (("set" + name).equals(method.getName())) {
                     result.add(Reflect.accessible(method));
                  } else if (("set" + camelCase).equals(method.getName())) {
                     result.add(Reflect.accessible(method));
                  }
               }
            }

            return result;
         }
      }, DATA_REFLECTION_CACHE_GET_MATCHING_SETTERS, type, name);
   }

   static final Method getMatchingGetter(Configuration configuration, final Class<?> type, final String name) {
      return (Method)Tools.Cache.run(configuration, new Tools.Cache.CachedOperation<Method>() {
         public Method call() {
            String camelCase = StringUtils.toCamelCase(name);
            String camelCaseLC = StringUtils.toLC(camelCase);
            Iterator var3 = Tools.getInstanceMethods(type).iterator();

            while(var3.hasNext()) {
               Method method = (Method)var3.next();
               if (method.getParameterTypes().length == 0) {
                  if (name.equals(method.getName())) {
                     return (Method)Reflect.accessible(method);
                  }

                  if (camelCaseLC.equals(method.getName())) {
                     return (Method)Reflect.accessible(method);
                  }

                  if (("get" + name).equals(method.getName())) {
                     return (Method)Reflect.accessible(method);
                  }

                  if (("get" + camelCase).equals(method.getName())) {
                     return (Method)Reflect.accessible(method);
                  }

                  if (("is" + name).equals(method.getName())) {
                     return (Method)Reflect.accessible(method);
                  }

                  if (("is" + camelCase).equals(method.getName())) {
                     return (Method)Reflect.accessible(method);
                  }
               }
            }

            return null;
         }
      }, DATA_REFLECTION_CACHE_GET_MATCHING_GETTER, type, name);
   }

   private static final List<Method> getInstanceMethods(Class<?> type) {
      List<Method> result = new ArrayList();
      Method[] var2 = type.getMethods();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Method method = var2[var4];
         if ((method.getModifiers() & 8) == 0) {
            result.add(method);
         }
      }

      return result;
   }

   private static final List<java.lang.reflect.Field> getInstanceMembers(Class<?> type) {
      List<java.lang.reflect.Field> result = new ArrayList();
      java.lang.reflect.Field[] var2 = type.getFields();
      int var3 = var2.length;

      int var4;
      java.lang.reflect.Field field;
      for(var4 = 0; var4 < var3; ++var4) {
         field = var2[var4];
         if ((field.getModifiers() & 8) == 0) {
            result.add(field);
         }
      }

      do {
         var2 = type.getDeclaredFields();
         var3 = var2.length;

         for(var4 = 0; var4 < var3; ++var4) {
            field = var2[var4];
            if ((field.getModifiers() & 8) == 0) {
               result.add(field);
            }
         }

         type = type.getSuperclass();
      } while(type != null);

      return result;
   }

   static final String getPropertyName(String methodName) {
      String name = methodName;
      if (methodName.startsWith("is") && methodName.length() > 2) {
         name = methodName.substring(2, 3).toLowerCase() + methodName.substring(3);
      } else if (methodName.startsWith("get") && methodName.length() > 3) {
         name = methodName.substring(3, 4).toLowerCase() + methodName.substring(4);
      } else if (methodName.startsWith("set") && methodName.length() > 3) {
         name = methodName.substring(3, 4).toLowerCase() + methodName.substring(4);
      }

      return name;
   }

   static final void consumeExceptions(Configuration configuration, PreparedStatement stmt, SQLException previous) {
   }

   static final void consumeWarnings(ExecuteContext ctx, ExecuteListener listener) {
      if (!Boolean.FALSE.equals(ctx.settings().isFetchWarnings())) {
         try {
            ctx.sqlWarning(ctx.statement().getWarnings());
         } catch (SQLException var3) {
            ctx.sqlWarning(new SQLWarning("Could not fetch SQLWarning", var3));
         }
      }

      if (ctx.sqlWarning() != null) {
         listener.warning(ctx);
      }

   }

   static final void consumeResultSets(ExecuteContext ctx, ExecuteListener listener, Results results, Intern intern) throws SQLException {
      boolean anyResults = false;
      int i = false;
      int rows = ctx.resultSet() == null ? ctx.statement().getUpdateCount() : 0;

      int i;
      for(i = 0; i < maxConsumedResults; ++i) {
         if (ctx.resultSet() != null) {
            anyResults = true;
            Field<?>[] fields = (new MetaDataFieldProvider(ctx.configuration(), ctx.resultSet().getMetaData())).getFields();
            Cursor<Record> c = new CursorImpl(ctx, listener, fields, intern != null ? intern.internIndexes(fields) : null, true, false);
            results.resultsOrRows().add(new ResultsImpl.ResultOrRowsImpl(c.fetch()));
         } else {
            if (rows == -1) {
               break;
            }

            results.resultsOrRows().add(new ResultsImpl.ResultOrRowsImpl(rows));
         }

         if (ctx.statement().getMoreResults()) {
            ctx.resultSet(ctx.statement().getResultSet());
         } else {
            if ((rows = ctx.statement().getUpdateCount()) == -1) {
               break;
            }

            ctx.resultSet((ResultSet)null);
         }
      }

      if (i == maxConsumedResults) {
         log.warn("Maximum consumed results reached: " + maxConsumedResults + ". This is probably a bug. Please report to https://github.com/jOOQ/jOOQ/issues/new");
      }

      if (anyResults && ctx.family() != SQLDialect.CUBRID) {
         ctx.statement().getMoreResults(3);
      }

   }

   static final List<String[]> parseTXT(String string, String nullLiteral) {
      String[] strings = string.split("[\\r\\n]+");
      if (strings.length < 2) {
         throw new DataAccessException("String must contain at least two lines");
      } else {
         boolean formatted = string.charAt(0) == '+';
         return formatted ? parseTXTLines(nullLiteral, strings, PLUS_PATTERN, 0, 1, 3, strings.length - 1) : parseTXTLines(nullLiteral, strings, DASH_PATTERN, 1, 0, 2, strings.length);
      }
   }

   private static final List<String[]> parseTXTLines(String nullLiteral, String[] strings, Pattern pattern, int matchLine, int headerLine, int dataLineStart, int dataLineEnd) {
      List<int[]> positions = new ArrayList();
      Matcher m = pattern.matcher(strings[matchLine]);

      while(m.find()) {
         positions.add(new int[]{m.start(1), m.end(1)});
      }

      List<String[]> result = new ArrayList();
      parseTXTLine(positions, result, strings[headerLine], nullLiteral);

      for(int j = dataLineStart; j < dataLineEnd; ++j) {
         parseTXTLine(positions, result, strings[j], nullLiteral);
      }

      return result;
   }

   private static final void parseTXTLine(List<int[]> positions, List<String[]> result, String string, String nullLiteral) {
      String[] fields = new String[positions.size()];
      result.add(fields);
      int length = string.length();

      for(int i = 0; i < fields.length; ++i) {
         int[] position = (int[])positions.get(i);
         if (position[0] < length) {
            fields[i] = string.substring(position[0], Math.min(position[1], length)).trim();
         } else {
            fields[i] = null;
         }

         if (StringUtils.equals(fields[i], nullLiteral)) {
            fields[i] = null;
         }
      }

   }

   static final List<String[]> parseHTML(String string) {
      List<String[]> result = new ArrayList();

      ArrayList col;
      for(Matcher mRow = P_PARSE_HTML_ROW.matcher(string); mRow.find(); result.add(col.toArray(EMPTY_STRING))) {
         String row = mRow.group(1);
         col = new ArrayList();
         Matcher mColBody;
         if (result.isEmpty()) {
            mColBody = P_PARSE_HTML_COL_HEAD.matcher(row);

            while(mColBody.find()) {
               col.add(mColBody.group(1));
            }
         }

         if (col.isEmpty()) {
            mColBody = P_PARSE_HTML_COL_BODY.matcher(row);

            while(mColBody.find()) {
               col.add(mColBody.group(1));
            }

            if (result.isEmpty()) {
               result.add(fieldNames(col.size()));
            }
         }
      }

      return result;
   }

   static final void executeImmediateBegin(Context<?> ctx, DDLStatementType type) {
      switch(ctx.family()) {
      case FIREBIRD:
         ctx.keyword("execute block").formatSeparator().keyword("as").formatSeparator().keyword("begin").formatIndentStart().formatSeparator().keyword("execute statement").sql(" '").stringLiteral(true).formatIndentStart().formatSeparator();
      default:
      }
   }

   static final void executeImmediateEnd(Context<?> ctx, DDLStatementType type) {
      boolean drop = Arrays.asList(DDLStatementType.DROP_INDEX, DDLStatementType.DROP_SEQUENCE, DDLStatementType.DROP_TABLE, DDLStatementType.DROP_VIEW).contains(type);
      switch(ctx.family()) {
      case FIREBIRD:
         ctx.formatIndentEnd().formatSeparator().stringLiteral(false).sql("';").formatSeparator().keyword("when").sql(" sqlcode -607 ").keyword("do").formatIndentStart().formatSeparator().keyword("begin end").formatIndentEnd().formatIndentEnd().formatSeparator().keyword("end");
      default:
      }
   }

   static final void toSQLDDLTypeDeclaration(Context<?> ctx, DataType<?> type) {
      String typeName = type.getTypeName(ctx.configuration());
      if (type.identity()) {
         switch(ctx.family()) {
         case POSTGRES:
            ctx.keyword(type.getType() == Long.class ? "serial8" : "serial");
            return;
         }
      }

      if (type.hasLength()) {
         if (type.length() > 0) {
            ctx.keyword(typeName).sql('(').sql(type.length()).sql(')');
         } else {
            String castTypeName = type.getCastTypeName(ctx.configuration());
            if (!typeName.equals(castTypeName)) {
               ctx.keyword(castTypeName);
            } else {
               ctx.keyword(typeName);
            }
         }
      } else if (type.hasPrecision() && type.precision() > 0) {
         if (type.hasScale()) {
            ctx.keyword(typeName).sql('(').sql(type.precision()).sql(", ").sql(type.scale()).sql(')');
         } else {
            ctx.keyword(typeName).sql('(').sql(type.precision()).sql(')');
         }
      } else {
         ctx.keyword(typeName);
      }

      if (type.identity()) {
         switch(ctx.family()) {
         case CUBRID:
            ctx.sql(' ').keyword("auto_increment");
            break;
         case DERBY:
            ctx.sql(' ').keyword("generated by default as identity");
            break;
         case HSQLDB:
            ctx.sql(' ').keyword("generated by default as identity").sql('(').keyword("start with").sql(" 1)");
         }
      }

   }

   static <T> Supplier<T> blocking(Supplier<T> supplier) {
      return blocking(supplier, false);
   }

   static <T> Supplier<T> blocking(final Supplier<T> supplier, boolean threadLocal) {
      return threadLocal ? supplier : new Supplier<T>() {
         volatile T asyncResult;

         public T get() {
            try {
               ForkJoinPool.managedBlock(new ManagedBlocker() {
                  public boolean block() {
                     asyncResult = supplier.get();
                     return true;
                  }

                  public boolean isReleasable() {
                     return asyncResult != null;
                  }
               });
            } catch (InterruptedException var2) {
               throw new RuntimeException(var2);
            }

            return this.asyncResult;
         }
      };
   }

   static <E extends EnumType> EnumType[] enums(Class<? extends E> type) {
      if (Enum.class.isAssignableFrom(type)) {
         return (EnumType[])type.getEnumConstants();
      } else {
         try {
            Class<?> companionClass = Thread.currentThread().getContextClassLoader().loadClass(type.getName() + "$");
            java.lang.reflect.Field module = companionClass.getField("MODULE$");
            Object companion = module.get(companionClass);
            return (EnumType[])((EnumType[])companionClass.getMethod("values").invoke(companion));
         } catch (Exception var4) {
            throw new MappingException("Error while looking up Scala enum", var4);
         }
      }
   }

   static final boolean hasAmbiguousNames(Collection<? extends Field<?>> fields) {
      if (fields == null) {
         return false;
      } else {
         Set<String> names = new HashSet();
         Iterator var2 = fields.iterator();

         Field field;
         do {
            if (!var2.hasNext()) {
               return false;
            }

            field = (Field)var2.next();
         } while(names.add(field.getName()));

         return true;
      }
   }

   static class Cache {
      private static final Object NULL = new Object();

      static final <V> V run(Configuration configuration, Tools.Cache.CachedOperation<V> operation, String type, Serializable... keys) {
         if (configuration == null) {
            configuration = new DefaultConfiguration();
         }

         if (!SettingsTools.reflectionCaching(((Configuration)configuration).settings())) {
            return operation.call();
         } else {
            Map<Object, Object> cache = (Map)((Configuration)configuration).data(type);
            if (cache == null) {
               synchronized(type) {
                  cache = (Map)((Configuration)configuration).data(type);
                  if (cache == null) {
                     cache = new ConcurrentHashMap();
                     ((Configuration)configuration).data(type, cache);
                  }
               }
            }

            Object key = key(keys);
            Object result = ((Map)cache).get(key);
            if (result == null) {
               synchronized(cache) {
                  result = ((Map)cache).get(key);
                  if (result == null) {
                     result = operation.call();
                     ((Map)cache).put(key, result == null ? NULL : result);
                  }
               }
            }

            return result == NULL ? null : result;
         }
      }

      private static final Object key(Serializable... key) {
         if (key != null && key.length != 0) {
            return key.length == 1 ? key[0] : new Tools.Cache.Key(key);
         } else {
            return key;
         }
      }

      private static class Key implements Serializable {
         private static final long serialVersionUID = 5822370287443922993L;
         private final Serializable[] key;

         Key(Serializable[] key) {
            this.key = key;
         }

         public int hashCode() {
            return Arrays.hashCode(this.key);
         }

         public boolean equals(Object obj) {
            return obj instanceof Tools.Cache.Key ? Arrays.equals(this.key, ((Tools.Cache.Key)obj).key) : false;
         }

         public String toString() {
            return Arrays.asList(this.key).toString();
         }
      }

      interface CachedOperation<V> {
         V call();
      }
   }

   static class ThreadGuard {
      static final <V> V run(Tools.ThreadGuard.Guard guard, Tools.ThreadGuard.GuardedOperation<V> operation) {
         boolean unguarded = guard.tl.get() == null;
         if (unguarded) {
            guard.tl.set(Tools.ThreadGuard.Guard.class);
         }

         Object var3;
         try {
            if (unguarded) {
               var3 = operation.unguarded();
               return var3;
            }

            var3 = operation.guarded();
         } finally {
            if (unguarded) {
               guard.tl.remove();
            }

         }

         return var3;
      }

      abstract static class AbstractGuardedOperation<V> implements Tools.ThreadGuard.GuardedOperation<V> {
         public V guarded() {
            return null;
         }
      }

      interface GuardedOperation<V> {
         V unguarded();

         V guarded();
      }

      static enum Guard {
         RECORD_TOSTRING;

         ThreadLocal<Object> tl = new ThreadLocal();
      }
   }

   static enum DataKey {
      DATA_OMIT_RETURNING_CLAUSE,
      DATA_ROW_VALUE_EXPRESSION_PREDICATE_SUBQUERY,
      DATA_LOCK_ROWS_FOR_UPDATE,
      DATA_COUNT_BIND_VALUES,
      DATA_FORCE_STATIC_STATEMENT,
      DATA_OMIT_CLAUSE_EVENT_EMISSION,
      DATA_WRAP_DERIVED_TABLES_IN_PARENTHESES,
      DATA_LOCALLY_SCOPED_DATA_MAP,
      DATA_WINDOW_DEFINITIONS,
      DATA_DEFAULT_TRANSACTION_PROVIDER_AUTOCOMMIT,
      DATA_DEFAULT_TRANSACTION_PROVIDER_SAVEPOINTS,
      DATA_DEFAULT_TRANSACTION_PROVIDER_CONNECTION,
      DATA_OVERRIDE_ALIASES_IN_ORDER_BY,
      DATA_UNALIAS_ALIASES_IN_ORDER_BY,
      DATA_SELECT_INTO_TABLE,
      DATA_OMIT_INTO_CLAUSE,
      DATA_RENDER_TRAILING_LIMIT_IF_APPLICABLE,
      DATA_LIST_ALREADY_INDENTED,
      DATA_CONSTRAINT_REFERENCE,
      DATA_COLLECT_SEMI_ANTI_JOIN,
      DATA_COLLECTED_SEMI_ANTI_JOIN,
      DATA_INSERT_SELECT_WITHOUT_INSERT_COLUMN_LIST;
   }
}
