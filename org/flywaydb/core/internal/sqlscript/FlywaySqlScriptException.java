package org.flywaydb.core.internal.sqlscript;

import java.sql.SQLException;
import org.flywaydb.core.internal.exception.FlywaySqlException;
import org.flywaydb.core.internal.util.scanner.Resource;

public class FlywaySqlScriptException extends FlywaySqlException {
   private final Resource resource;
   private final SqlStatement statement;

   public FlywaySqlScriptException(Resource resource, SqlStatement statement, SQLException sqlException) {
      super(resource == null ? "Script failed" : "Migration " + resource.getFilename() + " failed", sqlException);
      this.resource = resource;
      this.statement = statement;
   }

   public Resource getResource() {
      return this.resource;
   }

   public int getLineNumber() {
      return this.statement == null ? -1 : this.statement.getLineNumber();
   }

   public String getStatement() {
      return this.statement == null ? "" : this.statement.getSql();
   }

   public SqlStatement getSqlStatement() {
      return this.statement;
   }

   public String getMessage() {
      String message = super.getMessage();
      if (this.resource != null) {
         message = message + "Location   : " + this.resource.getLocation() + " (" + this.resource.getLocationOnDisk() + ")\n";
      }

      if (this.statement != null) {
         message = message + "Line       : " + this.getLineNumber() + "\n";
         message = message + "Statement  : " + this.getStatement() + "\n";
      }

      return message;
   }
}
