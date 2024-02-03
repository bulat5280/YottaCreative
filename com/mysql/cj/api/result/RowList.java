package com.mysql.cj.api.result;

import com.mysql.cj.core.Messages;
import com.mysql.cj.core.exceptions.CJOperationNotSupportedException;
import com.mysql.cj.core.exceptions.ExceptionFactory;
import java.util.Iterator;

public interface RowList extends Iterator<Row> {
   int RESULT_SET_SIZE_UNKNOWN = -1;

   default Row previous() {
      throw (CJOperationNotSupportedException)ExceptionFactory.createException(CJOperationNotSupportedException.class, Messages.getString("OperationNotSupportedException.0"));
   }

   default Row get(int n) {
      throw (CJOperationNotSupportedException)ExceptionFactory.createException(CJOperationNotSupportedException.class, Messages.getString("OperationNotSupportedException.0"));
   }

   default int getPosition() {
      throw (CJOperationNotSupportedException)ExceptionFactory.createException(CJOperationNotSupportedException.class, Messages.getString("OperationNotSupportedException.0"));
   }

   default int size() {
      return -1;
   }
}
