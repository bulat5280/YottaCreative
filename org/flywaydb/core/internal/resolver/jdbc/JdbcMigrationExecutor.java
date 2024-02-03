package org.flywaydb.core.internal.resolver.jdbc;

import java.sql.Connection;
import org.flywaydb.core.api.FlywayException;
import org.flywaydb.core.api.migration.jdbc.JdbcMigration;
import org.flywaydb.core.api.resolver.MigrationExecutor;

public class JdbcMigrationExecutor implements MigrationExecutor {
   private final JdbcMigration jdbcMigration;

   JdbcMigrationExecutor(JdbcMigration jdbcMigration) {
      this.jdbcMigration = jdbcMigration;
   }

   public void execute(Connection connection) {
      try {
         this.jdbcMigration.migrate(connection);
      } catch (Exception var3) {
         throw new FlywayException("Migration failed !", var3);
      }
   }

   public boolean executeInTransaction() {
      return true;
   }
}
