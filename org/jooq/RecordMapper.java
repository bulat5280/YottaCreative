package org.jooq;

@FunctionalInterface
public interface RecordMapper<R extends Record, E> {
   E map(R var1);
}
