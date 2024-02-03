package org.flywaydb.core.internal.util.jdbc;

import java.util.ArrayList;
import java.util.List;
import org.flywaydb.core.api.errorhandler.Context;
import org.flywaydb.core.api.errorhandler.Error;
import org.flywaydb.core.api.errorhandler.Warning;

public class ContextImpl implements Context {
   private final List<Warning> warnings = new ArrayList();
   private final List<Error> errors = new ArrayList();

   public void addWarning(Warning warning) {
      this.warnings.add(warning);
   }

   public void addError(Error error) {
      this.errors.add(error);
   }

   public List<Warning> getWarnings() {
      return this.warnings;
   }

   public List<Error> getErrors() {
      return this.errors;
   }
}
