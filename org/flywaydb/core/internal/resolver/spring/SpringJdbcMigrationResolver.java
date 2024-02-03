package org.flywaydb.core.internal.resolver.spring;

import org.flywaydb.core.api.MigrationType;
import org.flywaydb.core.api.configuration.FlywayConfiguration;
import org.flywaydb.core.api.migration.spring.SpringJdbcMigration;
import org.flywaydb.core.internal.resolver.JavaMigrationResolver;
import org.flywaydb.core.internal.util.Locations;
import org.flywaydb.core.internal.util.scanner.Scanner;

public class SpringJdbcMigrationResolver extends JavaMigrationResolver<SpringJdbcMigration, SpringJdbcMigrationExecutor> {
   public SpringJdbcMigrationResolver(Scanner scanner, Locations locations, FlywayConfiguration configuration) {
      super(scanner, locations, configuration);
   }

   protected String getMigrationTypeStr() {
      return "Spring JDBC";
   }

   protected Class<SpringJdbcMigration> getImplementedInterface() {
      return SpringJdbcMigration.class;
   }

   protected SpringJdbcMigrationExecutor createExecutor(SpringJdbcMigration migration) {
      return new SpringJdbcMigrationExecutor(migration);
   }

   protected MigrationType getMigrationType() {
      return MigrationType.SPRING_JDBC;
   }
}
