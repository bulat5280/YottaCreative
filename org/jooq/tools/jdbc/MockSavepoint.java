package org.jooq.tools.jdbc;

import java.sql.SQLException;
import java.sql.Savepoint;

public class MockSavepoint implements Savepoint {
   private final String name;

   MockSavepoint() {
      this((String)null);
   }

   MockSavepoint(String name) {
      this.name = name;
   }

   public int getSavepointId() throws SQLException {
      return this.name != null ? this.name.hashCode() : this.hashCode();
   }

   public String getSavepointName() throws SQLException {
      return this.name != null ? this.name : this.toString();
   }
}
