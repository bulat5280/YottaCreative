package com.mysql.cj.mysqlx.io;

import com.google.protobuf.GeneratedMessage;
import com.mysql.cj.core.exceptions.CJCommunicationsException;
import com.mysql.cj.core.exceptions.WrongArgumentException;
import com.mysql.cj.core.io.StatementExecuteOk;
import com.mysql.cj.mysqlx.MysqlxError;
import com.mysql.cj.mysqlx.protobuf.Mysqlx;
import com.mysql.cj.mysqlx.protobuf.MysqlxNotice;
import com.mysql.cj.mysqlx.protobuf.MysqlxResultset;
import com.mysql.cj.mysqlx.protobuf.MysqlxSql;
import java.util.concurrent.CompletableFuture;

public class StatementExecuteOkMessageListener implements AsyncMessageReader.MessageListener {
   private StatementExecuteOkBuilder builder = new StatementExecuteOkBuilder();
   private CompletableFuture<StatementExecuteOk> future = new CompletableFuture();

   public StatementExecuteOkMessageListener(CompletableFuture<StatementExecuteOk> future) {
      this.future = future;
   }

   public Boolean apply(Class<? extends GeneratedMessage> msgClass, GeneratedMessage msg) {
      if (MysqlxNotice.Frame.class.equals(msgClass)) {
         this.builder.addNotice((MysqlxNotice.Frame)MysqlxNotice.Frame.class.cast(msg));
         return false;
      } else if (MysqlxSql.StmtExecuteOk.class.equals(msgClass)) {
         this.future.complete(this.builder.build());
         return true;
      } else if (Mysqlx.Error.class.equals(msgClass)) {
         this.future.completeExceptionally(new MysqlxError((Mysqlx.Error)Mysqlx.Error.class.cast(msg)));
         return true;
      } else if (MysqlxResultset.FetchDone.class.equals(msgClass)) {
         return false;
      } else {
         this.future.completeExceptionally(new WrongArgumentException("Unhandled msg class (" + msgClass + ") + msg=" + msg));
         return true;
      }
   }

   public void closed() {
      this.future.completeExceptionally(new CJCommunicationsException("Sock was closed"));
   }

   public void error(Throwable ex) {
      this.future.completeExceptionally(ex);
   }
}
