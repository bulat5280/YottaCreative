package org.jooq.impl;

import java.sql.ResultSet;
import java.util.Map;
import org.jooq.BindingGetResultSetContext;
import org.jooq.Configuration;
import org.jooq.Converter;

class DefaultBindingGetResultSetContext<U> extends AbstractScope implements BindingGetResultSetContext<U> {
   private final ResultSet resultSet;
   private int index;
   private U value;

   DefaultBindingGetResultSetContext(Configuration configuration, Map<Object, Object> data, ResultSet resultSet, int index) {
      super(configuration, data);
      this.resultSet = resultSet;
      this.index = index;
   }

   public final ResultSet resultSet() {
      return this.resultSet;
   }

   public final int index() {
      return this.index;
   }

   final void index(int i) {
      this.index = i;
   }

   public void value(U v) {
      this.value = v;
   }

   final U value() {
      return this.value;
   }

   public final <T> BindingGetResultSetContext<T> convert(final Converter<? super T, ? extends U> converter) {
      return new DefaultBindingGetResultSetContext<T>(this.configuration, this.data, this.resultSet, this.index) {
         public void value(T v) {
            DefaultBindingGetResultSetContext.this.value = converter.from(v);
         }
      };
   }

   public String toString() {
      return "DefaultBindingGetResultSetContext [index=" + this.index + ", value=" + this.value + "]";
   }
}
