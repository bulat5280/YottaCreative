package com.mysql.cj.api.result;

import com.mysql.cj.api.io.ValueFactory;
import com.mysql.cj.api.mysqla.result.ColumnDefinition;
import com.mysql.cj.core.Messages;
import com.mysql.cj.core.exceptions.CJOperationNotSupportedException;
import com.mysql.cj.core.exceptions.ExceptionFactory;

public interface Row {
   <T> T getValue(int var1, ValueFactory<T> var2);

   default Row setMetadata(ColumnDefinition columnDefinition) {
      throw (CJOperationNotSupportedException)ExceptionFactory.createException(CJOperationNotSupportedException.class, Messages.getString("OperationNotSupportedException.0"));
   }

   default byte[] getBytes(int columnIndex) {
      throw (CJOperationNotSupportedException)ExceptionFactory.createException(CJOperationNotSupportedException.class, Messages.getString("OperationNotSupportedException.0"));
   }

   default void setBytes(int columnIndex, byte[] value) {
      throw (CJOperationNotSupportedException)ExceptionFactory.createException(CJOperationNotSupportedException.class, Messages.getString("OperationNotSupportedException.0"));
   }

   boolean getNull(int var1);

   boolean wasNull();
}
