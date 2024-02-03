package org.jooq.impl;

import org.jooq.Converter;
import org.jooq.ConverterProvider;

/** @deprecated */
@Deprecated
public class DefaultConverterProvider implements ConverterProvider {
   public <T, U> Converter<T, U> provide(Class<T> tType, Class<U> uType) {
      throw new UnsupportedOperationException();
   }
}
