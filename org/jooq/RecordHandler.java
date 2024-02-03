package org.jooq;

@FunctionalInterface
public interface RecordHandler<R extends Record> {
   void next(R var1);
}
