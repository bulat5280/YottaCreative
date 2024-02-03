package org.flywaydb.core.internal.util.jdbc;

import java.sql.Savepoint;

public class RollbackWithSavepointException extends RuntimeException {
   private final Savepoint savepoint;

   public RollbackWithSavepointException(Savepoint savepoint, RuntimeException cause) {
      super(cause);
      this.savepoint = savepoint;
   }

   public Savepoint getSavepoint() {
      return this.savepoint;
   }
}
