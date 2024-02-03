package org.flywaydb.core.api.resolver;

import java.sql.Connection;
import java.sql.SQLException;

public interface MigrationExecutor {
   void execute(Connection var1) throws SQLException;

   boolean executeInTransaction();
}
