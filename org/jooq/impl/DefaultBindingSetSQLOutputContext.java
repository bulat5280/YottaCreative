package org.jooq.impl;

import java.sql.SQLOutput;
import java.util.Map;
import org.jooq.BindingSetSQLOutputContext;
import org.jooq.Configuration;
import org.jooq.Converter;

class DefaultBindingSetSQLOutputContext<U> extends AbstractScope implements BindingSetSQLOutputContext<U> {
   private final SQLOutput output;
   private final U value;

   DefaultBindingSetSQLOutputContext(Configuration configuration, Map<Object, Object> data, SQLOutput output, U value) {
      super(configuration, data);
      this.output = output;
      this.value = value;
   }

   public final SQLOutput output() {
      return this.output;
   }

   public final U value() {
      return this.value;
   }

   public final <T> BindingSetSQLOutputContext<T> convert(Converter<? extends T, ? super U> converter) {
      return new DefaultBindingSetSQLOutputContext(this.configuration, this.data, this.output, converter.to(this.value));
   }

   public String toString() {
      return "DefaultBindingSetSQLOutputContext [value=" + this.value + "]";
   }
}
