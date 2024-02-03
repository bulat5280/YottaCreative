package org.jooq.impl;

import org.jooq.Binding;
import org.jooq.Configuration;
import org.jooq.DataType;
import org.jooq.SQLDialect;

/** @deprecated */
@Deprecated
final class ConvertedDataType<T, U> extends DefaultDataType<U> {
   private static final long serialVersionUID = -2321926692580974126L;
   private final DataType<T> delegate;

   ConvertedDataType(DataType<T> delegate, Binding<? super T, U> binding) {
      super((SQLDialect)null, (Class)binding.converter().toType(), (Binding)binding, delegate.getTypeName(), delegate.getCastTypeName(), delegate.precision(), delegate.scale(), delegate.length(), delegate.nullable(), delegate.defaultValue());
      this.delegate = delegate;
   }

   public int getSQLType() {
      return this.delegate.getSQLType();
   }

   public String getTypeName(Configuration configuration) {
      return this.delegate.getTypeName(configuration);
   }

   public String getCastTypeName(Configuration configuration) {
      return this.delegate.getCastTypeName(configuration);
   }

   public U convert(Object object) {
      return this.getConverter().toType().isInstance(object) ? object : this.getConverter().from(this.delegate.convert(object));
   }
}
