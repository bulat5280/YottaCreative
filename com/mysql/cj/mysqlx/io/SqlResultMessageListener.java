package com.mysql.cj.mysqlx.io;

import com.google.protobuf.GeneratedMessage;
import com.mysql.cj.api.x.SqlResult;
import com.mysql.cj.core.exceptions.CJCommunicationsException;
import com.mysql.cj.core.io.StatementExecuteOk;
import com.mysql.cj.mysqlx.MysqlxSession;
import com.mysql.cj.mysqlx.ResultCreatingResultListener;
import com.mysql.cj.mysqlx.devapi.SqlDataResult;
import com.mysql.cj.mysqlx.devapi.SqlUpdateResult;
import com.mysql.cj.mysqlx.protobuf.Mysqlx;
import com.mysql.cj.mysqlx.protobuf.MysqlxResultset;
import java.util.TimeZone;
import java.util.concurrent.CompletableFuture;

public class SqlResultMessageListener implements AsyncMessageReader.MessageListener {
   private SqlResultMessageListener.ResultType resultType;
   private CompletableFuture<SqlResult> resultF;
   private StatementExecuteOkMessageListener okListener;
   private ResultMessageListener resultListener;
   private ResultCreatingResultListener<SqlResult> resultCreator;

   public SqlResultMessageListener(CompletableFuture<SqlResult> resultF, ResultMessageListener.ColToFieldTransformer colToField, TimeZone defaultTimeZone) {
      this.resultF = resultF;
      MysqlxSession.ResultCtor<SqlResult> resultCtor = (metadata) -> {
         return (rows, task) -> {
            return new SqlDataResult(metadata, defaultTimeZone, rows, task);
         };
      };
      this.resultCreator = new ResultCreatingResultListener(resultCtor, resultF);
      this.resultListener = new ResultMessageListener(colToField, this.resultCreator);
      CompletableFuture<StatementExecuteOk> okF = new CompletableFuture();
      okF.whenComplete((ok, ex) -> {
         if (ex != null) {
            this.resultF.completeExceptionally(ex);
         } else {
            this.resultF.complete(new SqlUpdateResult(ok));
         }

      });
      this.okListener = new StatementExecuteOkMessageListener(okF);
   }

   public Boolean apply(Class<? extends GeneratedMessage> msgClass, GeneratedMessage msg) {
      if (this.resultType == null) {
         if (MysqlxResultset.ColumnMetaData.class.equals(msgClass)) {
            this.resultType = SqlResultMessageListener.ResultType.DATA;
         } else if (!Mysqlx.Error.class.equals(msgClass)) {
            this.resultType = SqlResultMessageListener.ResultType.UPDATE;
         }
      }

      return this.resultType == SqlResultMessageListener.ResultType.DATA ? this.resultListener.apply(msgClass, msg) : this.okListener.apply(msgClass, msg);
   }

   public void closed() {
      this.resultF.completeExceptionally(new CJCommunicationsException("Sock was closed"));
   }

   public void error(Throwable ex) {
      this.resultF.completeExceptionally(ex);
   }

   private static enum ResultType {
      UPDATE,
      DATA;
   }
}
