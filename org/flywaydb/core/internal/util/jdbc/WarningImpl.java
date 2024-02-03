package org.flywaydb.core.internal.util.jdbc;

import org.flywaydb.core.api.errorhandler.Warning;

public class WarningImpl implements Warning {
   private final int code;
   private final String state;
   private final String message;

   public WarningImpl(int code, String state, String message) {
      this.code = code;
      this.state = state;
      this.message = message;
   }

   public int getCode() {
      return this.code;
   }

   public String getState() {
      return this.state;
   }

   public String getMessage() {
      return this.message;
   }
}
