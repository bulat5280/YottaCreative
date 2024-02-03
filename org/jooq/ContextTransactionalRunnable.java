package org.jooq;

@FunctionalInterface
public interface ContextTransactionalRunnable {
   void run() throws Exception;
}
