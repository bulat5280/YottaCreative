package org.jooq.impl;

import java.sql.CallableStatement;
import java.util.Map;
import org.jooq.BindingGetStatementContext;
import org.jooq.Configuration;
import org.jooq.Converter;

class DefaultBindingGetStatementContext<U> extends AbstractScope implements BindingGetStatementContext<U> {
   private final CallableStatement statement;
   private final int index;
   private U value;

   DefaultBindingGetStatementContext(Configuration configuration, Map<Object, Object> data, CallableStatement statement, int index) {
      super(configuration, data);
      this.statement = statement;
      this.index = index;
   }

   public final CallableStatement statement() {
      return this.statement;
   }

   public final int index() {
      return this.index;
   }

   public void value(U v) {
      this.value = v;
   }

   final U value() {
      return this.value;
   }

   public final <T> BindingGetStatementContext<T> convert(final Converter<? super T, ? extends U> converter) {
      return new DefaultBindingGetStatementContext<T>(this.configuration, this.data, this.statement, this.index) {
         public void value(T v) {
            DefaultBindingGetStatementContext.this.value = converter.from(v);
         }
      };
   }

   public String toString() {
      return "DefaultBindingGetStatementContext [index=" + this.index + ", value=" + this.value + "]";
   }
}
