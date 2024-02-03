package org.jooq;

public interface Parameter<T> extends QueryPart {
   String getName();

   Class<T> getType();

   Converter<?, T> getConverter();

   Binding<?, T> getBinding();

   DataType<T> getDataType();

   DataType<T> getDataType(Configuration var1);

   boolean isDefaulted();

   boolean isUnnamed();
}
