package org.flywaydb.core.api.callback;

import java.sql.Connection;
import org.flywaydb.core.api.MigrationInfo;
import org.flywaydb.core.api.configuration.ConfigurationAware;
import org.flywaydb.core.api.configuration.FlywayConfiguration;

public abstract class BaseFlywayCallback implements FlywayCallback, ConfigurationAware {
   protected FlywayConfiguration flywayConfiguration;

   public void setFlywayConfiguration(FlywayConfiguration flywayConfiguration) {
      this.flywayConfiguration = flywayConfiguration;
   }

   public void beforeClean(Connection connection) {
   }

   public void afterClean(Connection connection) {
   }

   public void beforeMigrate(Connection connection) {
   }

   public void afterMigrate(Connection connection) {
   }

   public void beforeEachMigrate(Connection connection, MigrationInfo info) {
   }

   public void afterEachMigrate(Connection connection, MigrationInfo info) {
   }

   public void beforeUndo(Connection connection) {
   }

   public void beforeEachUndo(Connection connection, MigrationInfo info) {
   }

   public void afterEachUndo(Connection connection, MigrationInfo info) {
   }

   public void afterUndo(Connection connection) {
   }

   public void beforeValidate(Connection connection) {
   }

   public void afterValidate(Connection connection) {
   }

   public void beforeBaseline(Connection connection) {
   }

   public void afterBaseline(Connection connection) {
   }

   public void beforeRepair(Connection connection) {
   }

   public void afterRepair(Connection connection) {
   }

   public void beforeInfo(Connection connection) {
   }

   public void afterInfo(Connection connection) {
   }
}
