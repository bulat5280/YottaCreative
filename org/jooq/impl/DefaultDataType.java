package org.jooq.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.temporal.Temporal;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import org.jooq.Binding;
import org.jooq.Configuration;
import org.jooq.Converter;
import org.jooq.Converters;
import org.jooq.DataType;
import org.jooq.EnumType;
import org.jooq.Field;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.UDTRecord;
import org.jooq.exception.MappingException;
import org.jooq.exception.SQLDialectNotSupportedException;
import org.jooq.tools.Convert;
import org.jooq.tools.reflect.Reflect;
import org.jooq.types.Interval;
import org.jooq.types.UByte;
import org.jooq.types.UInteger;
import org.jooq.types.ULong;
import org.jooq.types.UShort;

public class DefaultDataType<T> implements DataType<T> {
   private static final long serialVersionUID = 4155588654449505119L;
   private static final Pattern NORMALISE_PATTERN = Pattern.compile("\"|\\.|\\s|\\(\\w+(\\s*,\\s*\\w+)*\\)|(NOT\\s*NULL)?");
   private static final Pattern TYPE_NAME_PATTERN = Pattern.compile("\\([^\\)]*\\)");
   private static final Map<String, DataType<?>>[] TYPES_BY_NAME = new Map[SQLDialect.values().length];
   private static final Map<Class<?>, DataType<?>>[] TYPES_BY_TYPE = new Map[SQLDialect.values().length];
   private static final Map<DataType<?>, DataType<?>>[] TYPES_BY_SQL_DATATYPE = new Map[SQLDialect.values().length];
   private static final Map<Class<?>, DataType<?>> SQL_DATATYPES_BY_TYPE;
   private static final int LONG_PRECISION = String.valueOf(Long.MAX_VALUE).length();
   private static final int INTEGER_PRECISION = String.valueOf(Integer.MAX_VALUE).length();
   private static final int SHORT_PRECISION = String.valueOf(32767).length();
   private static final int BYTE_PRECISION = String.valueOf(127).length();
   private final SQLDialect dialect;
   private final DataType<T> sqlDataType;
   private final Class<T> type;
   private final Binding<?, T> binding;
   private final Class<T[]> arrayType;
   private final String castTypeName;
   private final String castTypeBase;
   private final String typeName;
   private final boolean nullable;
   private final boolean identity;
   private final Field<T> defaultValue;
   private final int precision;
   private final int scale;
   private final int length;

   public DefaultDataType(SQLDialect dialect, DataType<T> sqlDataType, String typeName) {
      this(dialect, sqlDataType, sqlDataType.getType(), typeName, typeName, sqlDataType.precision(), sqlDataType.scale(), sqlDataType.length(), sqlDataType.nullable(), sqlDataType.defaultValue());
   }

   public DefaultDataType(SQLDialect dialect, DataType<T> sqlDataType, String typeName, String castTypeName) {
      this(dialect, sqlDataType, sqlDataType.getType(), typeName, castTypeName, sqlDataType.precision(), sqlDataType.scale(), sqlDataType.length(), sqlDataType.nullable(), sqlDataType.defaultValue());
   }

   public DefaultDataType(SQLDialect dialect, Class<T> type, String typeName) {
      this(dialect, (DataType)null, (Class)type, typeName, typeName, 0, 0, 0, true, (Field)null);
   }

   public DefaultDataType(SQLDialect dialect, Class<T> type, String typeName, String castTypeName) {
      this(dialect, (DataType)null, (Class)type, typeName, castTypeName, 0, 0, 0, true, (Field)null);
   }

   DefaultDataType(SQLDialect dialect, Class<T> type, String typeName, String castTypeName, int precision, int scale, int length, boolean nullable, Field<T> defaultValue) {
      this(dialect, (DataType)null, (Class)type, typeName, castTypeName, precision, scale, length, nullable, defaultValue);
   }

   DefaultDataType(SQLDialect dialect, Class<T> type, Binding<?, T> binding, String typeName, String castTypeName, int precision, int scale, int length, boolean nullable, Field<T> defaultValue) {
      this(dialect, (DataType)null, type, binding, typeName, castTypeName, precision, scale, length, nullable, defaultValue);
   }

   DefaultDataType(SQLDialect dialect, DataType<T> sqlDataType, Class<T> type, String typeName, String castTypeName, int precision, int scale, int length, boolean nullable, Field<T> defaultValue) {
      this(dialect, sqlDataType, type, (Binding)null, typeName, castTypeName, precision, scale, length, nullable, defaultValue);
   }

   DefaultDataType(SQLDialect dialect, DataType<T> sqlDataType, Class<T> type, Binding<?, T> binding, String typeName, String castTypeName, int precision, int scale, int length, boolean nullable, Field<T> defaultValue) {
      this(dialect, sqlDataType, type, binding, typeName, castTypeName, precision, scale, length, nullable, false, defaultValue);
   }

   DefaultDataType(SQLDialect dialect, DataType<T> sqlDataType, Class<T> type, Binding<?, T> binding, String typeName, String castTypeName, int precision, int scale, int length, boolean nullable, boolean identity, Field<T> defaultValue) {
      this.dialect = dialect;
      this.sqlDataType = (DataType)(dialect == null ? this : sqlDataType);
      this.type = type;
      this.typeName = typeName;
      this.castTypeName = castTypeName;
      this.castTypeBase = TYPE_NAME_PATTERN.matcher(castTypeName).replaceAll("").trim();
      this.arrayType = java.lang.reflect.Array.newInstance(type, 0).getClass();
      this.nullable = nullable;
      this.identity = identity;
      this.defaultValue = defaultValue;
      this.precision = precision0(type, precision);
      this.scale = scale;
      this.length = length;
      int ordinal = dialect == null ? SQLDialect.DEFAULT.ordinal() : dialect.family().ordinal();
      if (!TYPES_BY_NAME[ordinal].containsKey(typeName.toUpperCase())) {
         String normalised = normalise(typeName);
         if (TYPES_BY_NAME[ordinal].get(normalised) == null) {
            TYPES_BY_NAME[ordinal].put(normalised, this);
         }
      }

      if (TYPES_BY_TYPE[ordinal].get(type) == null) {
         TYPES_BY_TYPE[ordinal].put(type, this);
      }

      if (TYPES_BY_SQL_DATATYPE[ordinal].get(sqlDataType) == null) {
         TYPES_BY_SQL_DATATYPE[ordinal].put(sqlDataType, this);
      }

      if (dialect == null && SQL_DATATYPES_BY_TYPE.get(type) == null) {
         SQL_DATATYPES_BY_TYPE.put(type, this);
      }

      this.binding = (Binding)(binding != null ? binding : new DefaultBinding(Converters.identity(type), this.isLob()));
   }

   private DefaultDataType(DefaultDataType<T> t, int precision, int scale, int length, boolean nullable, boolean identity, Field<T> defaultValue) {
      this.dialect = t.dialect;
      this.sqlDataType = t.sqlDataType;
      this.type = t.type;
      this.typeName = t.typeName;
      this.castTypeName = t.castTypeName;
      this.castTypeBase = t.castTypeBase;
      this.arrayType = t.arrayType;
      this.nullable = nullable;
      this.identity = identity;
      this.defaultValue = defaultValue;
      this.precision = precision0(this.type, precision);
      this.scale = scale;
      this.length = length;
      this.binding = t.binding;
   }

   private static final int precision0(Class<?> type, int precision) {
      if (precision == 0) {
         if (type != Long.class && type != ULong.class) {
            if (type != Integer.class && type != UInteger.class) {
               if (type != Short.class && type != UShort.class) {
                  if (type == Byte.class || type == UByte.class) {
                     precision = BYTE_PRECISION;
                  }
               } else {
                  precision = SHORT_PRECISION;
               }
            } else {
               precision = INTEGER_PRECISION;
            }
         } else {
            precision = LONG_PRECISION;
         }
      }

      return precision;
   }

   public final DataType<T> nullable(boolean n) {
      return new DefaultDataType(this, this.precision, this.scale, this.length, n, n ? false : this.identity, this.defaultValue);
   }

   public final boolean nullable() {
      return this.nullable;
   }

   public final DataType<T> identity(boolean i) {
      return new DefaultDataType(this, this.precision, this.scale, this.length, i ? false : this.nullable, i, i ? null : this.defaultValue);
   }

   public final boolean identity() {
      return this.identity;
   }

   public final DataType<T> defaultValue(T d) {
      return this.defaultValue(Tools.field(d, (DataType)this));
   }

   public final DataType<T> defaultValue(Field<T> d) {
      return new DefaultDataType(this, this.precision, this.scale, this.length, this.nullable, d != null ? false : this.identity, d);
   }

   public final Field<T> defaultValue() {
      return this.defaultValue;
   }

   /** @deprecated */
   @Deprecated
   public final DataType<T> defaulted(boolean d) {
      return this.defaultValue(d ? Tools.field((Object)null, (DataType)this) : null);
   }

   public final boolean defaulted() {
      return this.defaultValue != null;
   }

   public final DataType<T> precision(int p) {
      return this.precision(p, this.scale);
   }

   public final DataType<T> precision(int p, int s) {
      if (this.precision == p && this.scale == s) {
         return this;
      } else {
         return this.isLob() ? this : new DefaultDataType(this, p, s, this.length, this.nullable, this.identity, this.defaultValue);
      }
   }

   public final int precision() {
      return this.precision;
   }

   public final boolean hasPrecision() {
      return this.type == BigInteger.class || this.type == BigDecimal.class;
   }

   public final DataType<T> scale(int s) {
      if (this.scale == s) {
         return this;
      } else {
         return this.isLob() ? this : new DefaultDataType(this, this.precision, s, this.length, this.nullable, this.identity, this.defaultValue);
      }
   }

   public final int scale() {
      return this.scale;
   }

   public final boolean hasScale() {
      return this.type == BigDecimal.class;
   }

   public final DataType<T> length(int l) {
      if (this.length == l) {
         return this;
      } else {
         return this.isLob() ? this : new DefaultDataType(this, this.precision, this.scale, l, this.nullable, this.identity, this.defaultValue);
      }
   }

   public final int length() {
      return this.length;
   }

   public final boolean hasLength() {
      return (this.type == byte[].class || this.type == String.class) && !this.isLob();
   }

   public final DataType<T> getSQLDataType() {
      return this.sqlDataType;
   }

   public final DataType<T> getDataType(Configuration configuration) {
      if (this.getDialect() == null) {
         DataType<?> dataType = (DataType)TYPES_BY_SQL_DATATYPE[configuration.dialect().family().ordinal()].get(this.length(0).precision(0, 0));
         if (dataType != null) {
            return dataType.length(this.length).precision(this.precision, this.scale);
         }
      } else {
         if (this.getDialect().family() == configuration.dialect().family()) {
            return this;
         }

         if (this.getSQLDataType() == null) {
            return this;
         }

         this.getSQLDataType().getDataType(configuration);
      }

      return this;
   }

   public int getSQLType() {
      if (this.type == Blob.class) {
         return 2004;
      } else if (this.type == Boolean.class) {
         return 16;
      } else if (this.type == BigInteger.class) {
         return -5;
      } else if (this.type == BigDecimal.class) {
         return 3;
      } else if (this.type == Byte.class) {
         return -6;
      } else if (this.type == byte[].class) {
         return 2004;
      } else if (this.type == Clob.class) {
         return 2005;
      } else if (this.type == Date.class) {
         return 91;
      } else if (this.type == Double.class) {
         return 8;
      } else if (this.type == Float.class) {
         return 6;
      } else if (this.type == Integer.class) {
         return 4;
      } else if (this.type == Long.class) {
         return -5;
      } else if (this.type == Short.class) {
         return 5;
      } else if (this.type == String.class) {
         return 12;
      } else if (this.type == Time.class) {
         return 92;
      } else if (this.type == Timestamp.class) {
         return 93;
      } else if (this.type.isArray()) {
         return 2003;
      } else if (EnumType.class.isAssignableFrom(this.type)) {
         return 12;
      } else if (UDTRecord.class.isAssignableFrom(this.type)) {
         return 2002;
      } else if (Result.class.isAssignableFrom(this.type)) {
         switch(this.dialect.family()) {
         case H2:
            return -10;
         case POSTGRES:
         default:
            return 1111;
         }
      } else {
         return 1111;
      }
   }

   public final Class<T> getType() {
      return this.type;
   }

   public final Binding<?, T> getBinding() {
      return this.binding;
   }

   public final Converter<?, T> getConverter() {
      return this.binding.converter();
   }

   public final Class<T[]> getArrayType() {
      return this.arrayType;
   }

   public final String getTypeName() {
      return this.typeName;
   }

   public String getTypeName(Configuration configuration) {
      return this.getDataType(configuration).getTypeName();
   }

   public final String getCastTypeName() {
      if (this.length != 0 && this.hasLength()) {
         return this.castTypeBase + "(" + this.length + ")";
      } else if (this.precision != 0 && this.hasPrecision()) {
         return this.scale != 0 && this.hasScale() ? this.castTypeBase + "(" + this.precision + ", " + this.scale + ")" : this.castTypeBase + "(" + this.precision + ")";
      } else {
         return this.castTypeName;
      }
   }

   public String getCastTypeName(Configuration configuration) {
      return this.getDataType(configuration).getCastTypeName();
   }

   public final DataType<T[]> getArrayDataType() {
      return new ArrayDataType(this);
   }

   public final <E extends EnumType> DataType<E> asEnumDataType(Class<E> enumDataType) {
      String enumTypeName = Tools.enums(enumDataType)[0].getName();
      return new DefaultDataType(this.dialect, enumDataType, enumTypeName, enumTypeName);
   }

   public final <U> DataType<U> asConvertedDataType(Converter<? super T, U> converter) {
      return this.asConvertedDataType(DefaultBinding.newBinding(converter, this, (Binding)null));
   }

   public final <U> DataType<U> asConvertedDataType(Binding<? super T, U> newBinding) {
      if (this.binding == newBinding) {
         return this;
      } else {
         if (newBinding == null) {
            newBinding = new DefaultBinding(Converters.identity(this.getType()), this.isLob());
         }

         return new ConvertedDataType(this, (Binding)newBinding);
      }
   }

   public final SQLDialect getDialect() {
      return this.dialect;
   }

   public T convert(Object object) {
      if (object == null) {
         return null;
      } else {
         return object.getClass() == this.type ? object : Convert.convert(object, this.type);
      }
   }

   public final T[] convert(Object... objects) {
      return (Object[])Convert.convertArray(objects, this.type);
   }

   public final List<T> convert(Collection<?> objects) {
      return Convert.convert(objects, this.type);
   }

   public static DataType<Object> getDefaultDataType(String typeName) {
      return new DefaultDataType(SQLDialect.DEFAULT, Object.class, typeName, typeName);
   }

   public static DataType<Object> getDefaultDataType(SQLDialect dialect, String typeName) {
      return new DefaultDataType(dialect, Object.class, typeName, typeName);
   }

   public static DataType<?> getDataType(SQLDialect dialect, String typeName) {
      int ordinal = dialect.ordinal();
      DataType<?> result = (DataType)TYPES_BY_NAME[ordinal].get(typeName.toUpperCase());
      if (result == null) {
         typeName = normalise(typeName);
         result = (DataType)TYPES_BY_NAME[ordinal].get(typeName);
      }

      if (result == null) {
         result = (DataType)TYPES_BY_NAME[SQLDialect.DEFAULT.ordinal()].get(typeName);
      }

      if (result == null && dialect.family() == SQLDialect.POSTGRES && typeName.charAt(0) == '_') {
         result = getDataType(dialect, typeName.substring(1)).getArrayDataType();
      }

      if (result == null) {
         throw new SQLDialectNotSupportedException("Type " + typeName + " is not supported in dialect " + dialect, false);
      } else {
         return result;
      }
   }

   public static <T> DataType<T> getDataType(SQLDialect dialect, Class<T> type) {
      return getDataType(dialect, type, (DataType)null);
   }

   public static <T> DataType<T> getDataType(SQLDialect dialect, Class<T> type, DataType<T> fallbackDataType) {
      type = Reflect.wrapper(type);
      if (byte[].class != type && type.isArray()) {
         return getDataType(dialect, type.getComponentType()).getArrayDataType();
      } else {
         DataType<?> result = null;
         if (dialect != null) {
            result = (DataType)TYPES_BY_TYPE[dialect.family().ordinal()].get(type);
         }

         if (result == null) {
            try {
               if (UDTRecord.class.isAssignableFrom(type)) {
                  return ((UDTRecord)type.newInstance()).getUDT().getDataType();
               }

               if (EnumType.class.isAssignableFrom(type)) {
                  return SQLDataType.VARCHAR.asEnumDataType(type);
               }
            } catch (Exception var5) {
               throw new MappingException("Cannot create instance of " + type, var5);
            }
         }

         if (result == null) {
            if (SQL_DATATYPES_BY_TYPE.get(type) != null) {
               return (DataType)SQL_DATATYPES_BY_TYPE.get(type);
            } else if (fallbackDataType != null) {
               return fallbackDataType;
            } else {
               throw new SQLDialectNotSupportedException("Type " + type + " is not supported in dialect " + dialect);
            }
         } else {
            return result;
         }
      }
   }

   public final boolean isNumeric() {
      return Number.class.isAssignableFrom(this.type) && !this.isInterval();
   }

   public final boolean isString() {
      return this.type == String.class;
   }

   public final boolean isDateTime() {
      return java.util.Date.class.isAssignableFrom(this.type) || Temporal.class.isAssignableFrom(this.type);
   }

   public final boolean isTemporal() {
      return this.isDateTime() || this.isInterval();
   }

   public final boolean isInterval() {
      return Interval.class.isAssignableFrom(this.type);
   }

   public final boolean isLob() {
      DataType<T> t = this.getSQLDataType();
      if (t == this) {
         return this.getTypeName().endsWith("lob");
      } else {
         return t == SQLDataType.BLOB || t == SQLDataType.CLOB || t == SQLDataType.NCLOB;
      }
   }

   public final boolean isBinary() {
      return this.type == byte[].class;
   }

   public final boolean isArray() {
      return !this.isBinary() && this.type.isArray();
   }

   public final boolean isUDT() {
      return UDTRecord.class.isAssignableFrom(this.type);
   }

   public String toString() {
      return this.getCastTypeName() + " (" + this.type.getName() + ")";
   }

   public int hashCode() {
      int prime = true;
      int result = 1;
      int result = 31 * result + (this.dialect == null ? 0 : this.dialect.hashCode());
      result = 31 * result + this.length;
      result = 31 * result + this.precision;
      result = 31 * result + this.scale;
      result = 31 * result + (this.type == null ? 0 : this.type.hashCode());
      result = 31 * result + (this.typeName == null ? 0 : this.typeName.hashCode());
      return result;
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj == null) {
         return false;
      } else if (this.getClass() != obj.getClass()) {
         return false;
      } else {
         DefaultDataType<?> other = (DefaultDataType)obj;
         if (this.dialect != other.dialect) {
            return false;
         } else if (this.length != other.length) {
            return false;
         } else if (this.precision != other.precision) {
            return false;
         } else if (this.scale != other.scale) {
            return false;
         } else {
            if (this.type == null) {
               if (other.type != null) {
                  return false;
               }
            } else if (!this.type.equals(other.type)) {
               return false;
            }

            if (this.typeName == null) {
               if (other.typeName != null) {
                  return false;
               }
            } else if (!this.typeName.equals(other.typeName)) {
               return false;
            }

            return true;
         }
      }
   }

   public static String normalise(String typeName) {
      return NORMALISE_PATTERN.matcher(typeName.toUpperCase()).replaceAll("");
   }

   public static DataType<?> getDataType(SQLDialect dialect, String t, int p, int s) throws SQLDialectNotSupportedException {
      DataType<?> result = getDataType(dialect, t);
      if (result.getType() == BigDecimal.class) {
         result = getDataType(dialect, getNumericClass(p, s));
      }

      return result;
   }

   public static Class<?> getType(SQLDialect dialect, String t, int p, int s) throws SQLDialectNotSupportedException {
      return getDataType(dialect, t, p, s).getType();
   }

   private static Class<?> getNumericClass(int precision, int scale) {
      if (scale == 0 && precision != 0) {
         if (precision < BYTE_PRECISION) {
            return Byte.class;
         } else if (precision < SHORT_PRECISION) {
            return Short.class;
         } else if (precision < INTEGER_PRECISION) {
            return Integer.class;
         } else {
            return precision < LONG_PRECISION ? Long.class : BigInteger.class;
         }
      } else {
         return BigDecimal.class;
      }
   }

   static Collection<Class<?>> types() {
      return Collections.unmodifiableCollection(SQL_DATATYPES_BY_TYPE.keySet());
   }

   static Collection<DataType<?>> dataTypes() {
      return Collections.unmodifiableCollection(SQL_DATATYPES_BY_TYPE.values());
   }

   static {
      SQLDialect[] var0 = SQLDialect.values();
      int var1 = var0.length;

      for(int var2 = 0; var2 < var1; ++var2) {
         SQLDialect dialect = var0[var2];
         TYPES_BY_SQL_DATATYPE[dialect.ordinal()] = new LinkedHashMap();
         TYPES_BY_NAME[dialect.ordinal()] = new LinkedHashMap();
         TYPES_BY_TYPE[dialect.ordinal()] = new LinkedHashMap();
      }

      SQL_DATATYPES_BY_TYPE = new LinkedHashMap();

      try {
         Class.forName(SQLDataType.class.getName());
      } catch (Exception var4) {
      }

   }
}
