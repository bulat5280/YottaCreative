package org.flywaydb.core.api.migration.spring;

import org.flywaydb.core.api.configuration.ConfigurationAware;
import org.flywaydb.core.api.configuration.FlywayConfiguration;

public abstract class BaseSpringJdbcMigration implements SpringJdbcMigration, ConfigurationAware {
   protected FlywayConfiguration flywayConfiguration;

   public void setFlywayConfiguration(FlywayConfiguration flywayConfiguration) {
      this.flywayConfiguration = flywayConfiguration;
   }
}
