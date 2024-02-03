package org.jooq.impl;

import java.sql.SQLInput;
import java.util.Map;
import org.jooq.BindingGetSQLInputContext;
import org.jooq.Configuration;
import org.jooq.Converter;

class DefaultBindingGetSQLInputContext<U> extends AbstractScope implements BindingGetSQLInputContext<U> {
   private final SQLInput input;
   private U value;

   DefaultBindingGetSQLInputContext(Configuration configuration, Map<Object, Object> data, SQLInput input) {
      super(configuration, data);
      this.input = input;
   }

   public final SQLInput input() {
      return this.input;
   }

   public void value(U v) {
      this.value = v;
   }

   final U value() {
      return this.value;
   }

   public final <T> BindingGetSQLInputContext<T> convert(final Converter<? super T, ? extends U> converter) {
      return new DefaultBindingGetSQLInputContext<T>(this.configuration, this.data, this.input) {
         public void value(T v) {
            DefaultBindingGetSQLInputContext.this.value = converter.from(v);
         }
      };
   }

   public String toString() {
      return "DefaultBindingGetSQLInputContext [value=" + this.value + "]";
   }
}
