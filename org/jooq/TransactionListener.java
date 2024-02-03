package org.jooq;

public interface TransactionListener {
   void beginStart(TransactionContext var1);

   void beginEnd(TransactionContext var1);

   void commitStart(TransactionContext var1);

   void commitEnd(TransactionContext var1);

   void rollbackStart(TransactionContext var1);

   void rollbackEnd(TransactionContext var1);
}
