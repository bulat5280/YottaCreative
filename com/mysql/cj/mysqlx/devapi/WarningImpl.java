package com.mysql.cj.mysqlx.devapi;

import com.mysql.cj.api.x.Warning;
import com.mysql.cj.mysqlx.protobuf.MysqlxNotice;

public class WarningImpl implements Warning {
   private MysqlxNotice.Warning message;

   public WarningImpl(MysqlxNotice.Warning message) {
      this.message = message;
   }

   public int getLevel() {
      return this.message.getLevel().getNumber();
   }

   public long getCode() {
      return Integer.toUnsignedLong(this.message.getCode());
   }

   public String getMessage() {
      return this.message.getMsg();
   }
}
