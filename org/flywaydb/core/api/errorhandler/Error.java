package org.flywaydb.core.api.errorhandler;

public interface Error {
   int getCode();

   String getState();

   String getMessage();
}
