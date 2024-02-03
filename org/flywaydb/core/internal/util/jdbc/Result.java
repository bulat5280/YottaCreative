package org.flywaydb.core.internal.util.jdbc;

public class Result {
   private final long updateCount;

   public Result(long updateCount) {
      this.updateCount = updateCount;
   }

   public long getUpdateCount() {
      return this.updateCount;
   }
}
