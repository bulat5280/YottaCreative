package org.flywaydb.core.internal.exception;

import java.sql.SQLException;
import org.flywaydb.core.api.FlywayException;
import org.flywaydb.core.internal.util.StringUtils;

public class FlywaySqlException extends FlywayException {
   public FlywaySqlException(String message, SQLException sqlException) {
      super(message, sqlException);
   }

   public String getMessage() {
      String title = super.getMessage();
      String underline = StringUtils.trimOrPad("", title.length(), '-');

      SQLException cause;
      for(cause = (SQLException)this.getCause(); cause.getNextException() != null; cause = cause.getNextException()) {
      }

      String message = "\n" + title + "\n" + underline + "\n";
      message = message + "SQL State  : " + cause.getSQLState() + "\n";
      message = message + "Error Code : " + cause.getErrorCode() + "\n";
      if (cause.getMessage() != null) {
         message = message + "Message    : " + cause.getMessage().trim() + "\n";
      }

      return message;
   }
}
