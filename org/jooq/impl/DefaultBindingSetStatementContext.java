package org.jooq.impl;

import java.sql.PreparedStatement;
import java.util.Map;
import org.jooq.BindingSetStatementContext;
import org.jooq.Configuration;
import org.jooq.Converter;

class DefaultBindingSetStatementContext<U> extends AbstractScope implements BindingSetStatementContext<U> {
   private final PreparedStatement statement;
   private final int index;
   private final U value;

   DefaultBindingSetStatementContext(Configuration configuration, Map<Object, Object> data, PreparedStatement statement, int index, U value) {
      super(configuration, data);
      this.statement = statement;
      this.index = index;
      this.value = value;
   }

   public final PreparedStatement statement() {
      return this.statement;
   }

   public final int index() {
      return this.index;
   }

   public final U value() {
      return this.value;
   }

   public final <T> BindingSetStatementContext<T> convert(Converter<? extends T, ? super U> converter) {
      return new DefaultBindingSetStatementContext(this.configuration, this.data, this.statement, this.index, converter.to(this.value));
   }

   public String toString() {
      return "DefaultBindingSetStatementContext [index=" + this.index + ", value=" + this.value + "]";
   }
}
