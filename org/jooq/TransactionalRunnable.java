package org.jooq;

@FunctionalInterface
public interface TransactionalRunnable {
   void run(Configuration var1) throws Exception;
}
