package org.jooq;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public interface DataType<T> extends Serializable {
   DataType<T> getSQLDataType();

   DataType<T> getDataType(Configuration var1);

   int getSQLType();

   Binding<?, T> getBinding();

   Converter<?, T> getConverter();

   Class<T> getType();

   Class<T[]> getArrayType();

   DataType<T[]> getArrayDataType();

   <E extends EnumType> DataType<E> asEnumDataType(Class<E> var1);

   <U> DataType<U> asConvertedDataType(Converter<? super T, U> var1);

   <U> DataType<U> asConvertedDataType(Binding<? super T, U> var1);

   String getTypeName();

   String getTypeName(Configuration var1);

   String getCastTypeName();

   String getCastTypeName(Configuration var1);

   SQLDialect getDialect();

   T convert(Object var1);

   T[] convert(Object... var1);

   List<T> convert(Collection<?> var1);

   DataType<T> nullable(boolean var1);

   boolean nullable();

   DataType<T> identity(boolean var1);

   boolean identity();

   DataType<T> defaultValue(T var1);

   DataType<T> defaultValue(Field<T> var1);

   Field<T> defaultValue();

   /** @deprecated */
   @Deprecated
   DataType<T> defaulted(boolean var1);

   boolean defaulted();

   DataType<T> precision(int var1);

   DataType<T> precision(int var1, int var2);

   int precision();

   boolean hasPrecision();

   DataType<T> scale(int var1);

   int scale();

   boolean hasScale();

   DataType<T> length(int var1);

   int length();

   boolean hasLength();

   boolean isNumeric();

   boolean isString();

   boolean isDateTime();

   boolean isTemporal();

   boolean isInterval();

   boolean isBinary();

   boolean isLob();

   boolean isArray();

   boolean isUDT();
}
