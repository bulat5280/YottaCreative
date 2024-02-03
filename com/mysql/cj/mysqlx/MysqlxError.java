package com.mysql.cj.mysqlx;

import com.mysql.cj.core.exceptions.CJException;
import com.mysql.cj.mysqlx.protobuf.Mysqlx;

public class MysqlxError extends CJException {
   private static final long serialVersionUID = 6991120628391138584L;
   private Mysqlx.Error msg;

   public MysqlxError(Mysqlx.Error msg) {
      super(getFullErrorDescription(msg));
      this.msg = msg;
   }

   public MysqlxError(MysqlxError fromOtherThread) {
      super(getFullErrorDescription(fromOtherThread.msg), fromOtherThread);
      this.msg = fromOtherThread.msg;
   }

   private static String getFullErrorDescription(Mysqlx.Error msg) {
      StringBuilder stringMessage = new StringBuilder("ERROR ");
      stringMessage.append(msg.getCode());
      stringMessage.append(" (");
      stringMessage.append(msg.getSqlState());
      stringMessage.append(") ");
      stringMessage.append(msg.getMsg());
      return stringMessage.toString();
   }

   public int getErrorCode() {
      return this.msg.getCode();
   }

   public String getSQLState() {
      return this.msg.getSqlState();
   }
}
