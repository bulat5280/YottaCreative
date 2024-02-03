package org.flywaydb.core.internal.exception;

import org.flywaydb.core.api.FlywayException;

public class FlywayProUpgradeRequiredException extends FlywayException {
   public FlywayProUpgradeRequiredException(String feature) {
      super("Flyway Pro or Flyway Enterprise upgrade required: " + feature + " is not supported by Flyway Open Source.");
   }
}
