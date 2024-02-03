package org.flywaydb.core.api.errorhandler;

public interface Warning {
   int getCode();

   String getState();

   String getMessage();
}
