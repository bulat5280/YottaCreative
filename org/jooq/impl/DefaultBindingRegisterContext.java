package org.jooq.impl;

import java.sql.CallableStatement;
import java.util.Map;
import org.jooq.BindingRegisterContext;
import org.jooq.Configuration;
import org.jooq.Converter;

class DefaultBindingRegisterContext<U> extends AbstractScope implements BindingRegisterContext<U> {
   private final CallableStatement statement;
   private final int index;

   DefaultBindingRegisterContext(Configuration configuration, Map<Object, Object> data, CallableStatement statement, int index) {
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

   public final <T> BindingRegisterContext<T> convert(Converter<? super T, ? extends U> converter) {
      return new DefaultBindingRegisterContext(this.configuration, this.data, this.statement, this.index);
   }

   public String toString() {
      return "DefaultBindingRegisterContext [index=" + this.index + "]";
   }
}
