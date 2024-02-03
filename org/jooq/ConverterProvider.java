package org.jooq;

/** @deprecated */
@Deprecated
@FunctionalInterface
public interface ConverterProvider {
   <T, U> Converter<T, U> provide(Class<T> var1, Class<U> var2);
}
