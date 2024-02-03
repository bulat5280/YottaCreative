package org.flywaydb.core.internal.resolver.jdbc;

import org.flywaydb.core.api.MigrationType;
import org.flywaydb.core.api.configuration.FlywayConfiguration;
import org.flywaydb.core.api.migration.jdbc.JdbcMigration;
import org.flywaydb.core.internal.resolver.JavaMigrationResolver;
import org.flywaydb.core.internal.util.Locations;
import org.flywaydb.core.internal.util.scanner.Scanner;

public class JdbcMigrationResolver extends JavaMigrationResolver<JdbcMigration, JdbcMigrationExecutor> {
   public JdbcMigrationResolver(Scanner scanner, Locations locations, FlywayConfiguration configuration) {
      super(scanner, locations, configuration);
   }

   protected String getMigrationTypeStr() {
      return "JDBC";
   }

   protected Class<JdbcMigration> getImplementedInterface() {
      return JdbcMigration.class;
   }

   protected JdbcMigrationExecutor createExecutor(JdbcMigration migration) {
      return new JdbcMigrationExecutor(migration);
   }

   protected MigrationType getMigrationType() {
      return MigrationType.JDBC;
   }
}
