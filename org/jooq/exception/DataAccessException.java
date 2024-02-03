package org.jooq.exception;

import java.sql.SQLException;

public class DataAccessException extends RuntimeException {
   private static final long serialVersionUID = 491834858363345767L;

   public DataAccessException(String message) {
      super(message);
   }

   public DataAccessException(String message, Throwable cause) {
      super(message, cause);
   }

   public String sqlState() {
      Throwable t = this.getCause();
      return t instanceof SQLException ? ((SQLException)t).getSQLState() : "00000";
   }

   public SQLStateClass sqlStateClass() {
      Throwable t = this.getCause();
      return t instanceof SQLException ? SQLStateClass.fromCode(((SQLException)t).getSQLState()) : SQLStateClass.NONE;
   }

   public SQLStateSubclass sqlStateSubclass() {
      Throwable t = this.getCause();
      return t instanceof SQLException ? SQLStateSubclass.fromCode(((SQLException)t).getSQLState()) : SQLStateSubclass.NONE;
   }

   public StackTraceElement[] getStackTrace() {
      return super.getStackTrace();
   }
}
