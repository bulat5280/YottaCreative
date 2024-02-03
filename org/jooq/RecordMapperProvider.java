package org.jooq;

@FunctionalInterface
public interface RecordMapperProvider {
   <R extends Record, E> RecordMapper<R, E> provide(RecordType<R> var1, Class<? extends E> var2);
}
