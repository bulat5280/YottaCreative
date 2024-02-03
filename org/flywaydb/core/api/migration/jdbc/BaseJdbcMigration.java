package org.flywaydb.core.api.migration.jdbc;

import org.flywaydb.core.api.configuration.ConfigurationAware;
import org.flywaydb.core.api.configuration.FlywayConfiguration;

public abstract class BaseJdbcMigration implements JdbcMigration, ConfigurationAware {
   protected FlywayConfiguration flywayConfiguration;

   public void setFlywayConfiguration(FlywayConfiguration flywayConfiguration) {
      this.flywayConfiguration = flywayConfiguration;
   }
}
