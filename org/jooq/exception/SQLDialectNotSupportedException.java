package org.jooq.exception;

import org.jooq.tools.JooqLogger;

public class SQLDialectNotSupportedException extends RuntimeException {
   private static final long serialVersionUID = 8119718690889460970L;
   private static final JooqLogger log = JooqLogger.getLogger(SQLDialectNotSupportedException.class);

   public SQLDialectNotSupportedException(String message) {
      this(message, true);
   }

   public SQLDialectNotSupportedException(String message, boolean warn) {
      super(message);
      if (warn) {
         log.warn("Not supported by dialect", (Object)message);
      }

   }
}
