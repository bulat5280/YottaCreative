package org.jooq;

import org.jooq.exception.DataAccessException;

public interface TransactionProvider {
   void begin(TransactionContext var1) throws DataAccessException;

   void commit(TransactionContext var1) throws DataAccessException;

   void rollback(TransactionContext var1) throws DataAccessException;
}
