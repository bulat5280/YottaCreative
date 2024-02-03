package org.jooq;

@FunctionalInterface
public interface TransactionalCallable<T> {
   T run(Configuration var1) throws Exception;
}
