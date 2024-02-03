package org.jooq.impl;

import java.util.Map;
import org.jooq.BindingSQLContext;
import org.jooq.Configuration;
import org.jooq.Converter;
import org.jooq.RenderContext;

class DefaultBindingSQLContext<U> extends AbstractScope implements BindingSQLContext<U> {
   private final RenderContext render;
   private final U value;
   private final String variable;

   DefaultBindingSQLContext(Configuration configuration, Map<Object, Object> data, RenderContext render, U value) {
      this(configuration, data, render, value, "?");
   }

   DefaultBindingSQLContext(Configuration configuration, Map<Object, Object> data, RenderContext render, U value, String variable) {
      super(configuration, data);
      this.render = render;
      this.value = value;
      this.variable = variable;
   }

   public final RenderContext render() {
      return this.render;
   }

   public final U value() {
      return this.value;
   }

   public final String variable() {
      return this.variable;
   }

   public <T> BindingSQLContext<T> convert(Converter<? extends T, ? super U> converter) {
      return new DefaultBindingSQLContext(this.configuration, this.data, this.render, converter.to(this.value), this.variable);
   }

   public String toString() {
      return "DefaultBindingSQLContext [value=" + this.value + ", variable=" + this.variable + "]";
   }
}
