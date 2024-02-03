package org.jooq.impl;

import org.jooq.Converter;

public abstract class AbstractConverter<T, U> implements Converter<T, U> {
   private static final long serialVersionUID = 3739249904977790727L;
   private final Class<T> fromType;
   private final Class<U> toType;

   public AbstractConverter(Class<T> fromType, Class<U> toType) {
      this.fromType = fromType;
      this.toType = toType;
   }

   public final Class<T> fromType() {
      return this.fromType;
   }

   public final Class<U> toType() {
      return this.toType;
   }

   public String toString() {
      return "Converter [ " + this.fromType().getName() + " -> " + this.toType().getName() + " ]";
   }
}
