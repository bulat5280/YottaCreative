package org.jooq.impl;

import org.jooq.Configuration;
import org.jooq.Transaction;
import org.jooq.TransactionContext;

class DefaultTransactionContext extends AbstractScope implements TransactionContext {
   Transaction transaction;
   Exception cause;

   DefaultTransactionContext(Configuration configuration) {
      super(configuration);
   }

   public final Transaction transaction() {
      return this.transaction;
   }

   public final TransactionContext transaction(Transaction t) {
      this.transaction = t;
      return this;
   }

   public final Exception cause() {
      return this.cause;
   }

   public final TransactionContext cause(Exception c) {
      this.cause = c;
      return this;
   }
}
