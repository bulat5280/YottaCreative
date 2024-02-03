package org.jooq;

public interface TransactionContext extends Scope {
   Transaction transaction();

   TransactionContext transaction(Transaction var1);

   Exception cause();

   TransactionContext cause(Exception var1);
}
