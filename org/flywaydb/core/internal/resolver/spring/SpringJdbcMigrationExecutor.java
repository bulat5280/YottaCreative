package org.flywaydb.core.internal.resolver.spring;

import java.sql.Connection;
import org.flywaydb.core.api.FlywayException;
import org.flywaydb.core.api.migration.spring.SpringJdbcMigration;
import org.flywaydb.core.api.resolver.MigrationExecutor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

public class SpringJdbcMigrationExecutor implements MigrationExecutor {
   private final SpringJdbcMigration springJdbcMigration;

   SpringJdbcMigrationExecutor(SpringJdbcMigration springJdbcMigration) {
      this.springJdbcMigration = springJdbcMigration;
   }

   public void execute(Connection connection) {
      try {
         this.springJdbcMigration.migrate(new JdbcTemplate(new SingleConnectionDataSource(connection, true)));
      } catch (Exception var3) {
         throw new FlywayException("Migration failed !", var3);
      }
   }

   public boolean executeInTransaction() {
      return true;
   }
}
