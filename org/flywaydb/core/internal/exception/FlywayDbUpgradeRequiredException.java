package org.flywaydb.core.internal.exception;

import org.flywaydb.core.api.FlywayException;

public class FlywayDbUpgradeRequiredException extends FlywayException {
   public FlywayDbUpgradeRequiredException(String database, String version, String minimumVersion) {
      super(database + " upgrade required: " + database + " " + version + " is outdated and no longer supported by Flyway. Flyway currently supports " + database + " " + minimumVersion + " and newer.");
   }
}
