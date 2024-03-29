package com.mysql.cj.jdbc.exceptions;

import com.mysql.cj.core.Messages;
import java.sql.SQLException;

public class OperationNotSupportedException extends SQLException {
   static final long serialVersionUID = 474918612056813430L;

   public OperationNotSupportedException() {
      super(Messages.getString("RowDataDynamic.10"), "S1009");
   }

   public OperationNotSupportedException(String message) {
      super(message, "S1009");
   }
}
