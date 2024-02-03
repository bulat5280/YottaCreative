package org.jooq;

public interface SelectField<T> extends QueryPart {
   String getName();

   Converter<?, T> getConverter();

   Binding<?, T> getBinding();

   Class<T> getType();

   DataType<T> getDataType();

   DataType<T> getDataType(Configuration var1);
}
