package org.jooq;

@FunctionalInterface
public interface ContextTransactionalCallable<T> {
   T run() throws Exception;
}
