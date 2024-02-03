package org.jooq;

@FunctionalInterface
public interface TransactionListenerProvider {
   TransactionListener provide();
}
