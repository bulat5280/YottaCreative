package org.flywaydb.core.internal.exception;

import org.flywaydb.core.api.FlywayException;

public class FlywayEnterpriseUpgradeRequiredException extends FlywayException {
   public FlywayEnterpriseUpgradeRequiredException(String vendor, String database, String version) {
      super("Flyway Enterprise or " + database + " upgrade required: " + database + " " + version + " is past regular support by " + vendor + " and no longer supported by Flyway Open Source or Pro, but still supported by Flyway Enterprise.");
   }
}
