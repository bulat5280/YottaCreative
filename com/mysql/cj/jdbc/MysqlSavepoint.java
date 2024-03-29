package com.mysql.cj.jdbc;

import com.mysql.cj.api.exceptions.ExceptionInterceptor;
import com.mysql.cj.core.Messages;
import com.mysql.cj.core.exceptions.CJException;
import com.mysql.cj.jdbc.exceptions.SQLError;
import com.mysql.cj.jdbc.exceptions.SQLExceptionsMapping;
import java.rmi.server.UID;
import java.sql.SQLException;
import java.sql.Savepoint;

public class MysqlSavepoint implements Savepoint {
   private String savepointName;
   private ExceptionInterceptor exceptionInterceptor;

   private static String getUniqueId() {
      String uidStr = (new UID()).toString();
      int uidLength = uidStr.length();
      StringBuilder safeString = new StringBuilder(uidLength + 1);
      safeString.append('_');

      for(int i = 0; i < uidLength; ++i) {
         char c = uidStr.charAt(i);
         if (!Character.isLetter(c) && !Character.isDigit(c)) {
            safeString.append('_');
         } else {
            safeString.append(c);
         }
      }

      return safeString.toString();
   }

   MysqlSavepoint(ExceptionInterceptor exceptionInterceptor) throws SQLException {
      this(getUniqueId(), exceptionInterceptor);
   }

   MysqlSavepoint(String name, ExceptionInterceptor exceptionInterceptor) throws SQLException {
      if (name != null && name.length() != 0) {
         this.savepointName = name;
         this.exceptionInterceptor = exceptionInterceptor;
      } else {
         throw SQLError.createSQLException(Messages.getString("MysqlSavepoint.0"), "S1009", exceptionInterceptor);
      }
   }

   public int getSavepointId() throws SQLException {
      try {
         throw SQLError.createSQLException(Messages.getString("MysqlSavepoint.1"), "S1C00", this.exceptionInterceptor);
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.exceptionInterceptor);
      }
   }

   public String getSavepointName() throws SQLException {
      try {
         return this.savepointName;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.exceptionInterceptor);
      }
   }
}
