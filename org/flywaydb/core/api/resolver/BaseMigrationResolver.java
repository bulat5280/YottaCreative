package org.flywaydb.core.api.resolver;

import org.flywaydb.core.api.configuration.ConfigurationAware;
import org.flywaydb.core.api.configuration.FlywayConfiguration;

public abstract class BaseMigrationResolver implements MigrationResolver, ConfigurationAware {
   protected FlywayConfiguration flywayConfiguration;

   public void setFlywayConfiguration(FlywayConfiguration flywayConfiguration) {
      this.flywayConfiguration = flywayConfiguration;
   }
}
