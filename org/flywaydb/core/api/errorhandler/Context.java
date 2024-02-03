package org.flywaydb.core.api.errorhandler;

import java.util.List;

public interface Context {
   List<Warning> getWarnings();

   List<Error> getErrors();
}
