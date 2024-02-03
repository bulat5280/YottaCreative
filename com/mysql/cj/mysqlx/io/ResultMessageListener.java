package com.mysql.cj.mysqlx.io;

import com.google.protobuf.GeneratedMessage;
import com.mysql.cj.core.exceptions.WrongArgumentException;
import com.mysql.cj.core.result.Field;
import com.mysql.cj.mysqlx.MysqlxError;
import com.mysql.cj.mysqlx.protobuf.Mysqlx;
import com.mysql.cj.mysqlx.protobuf.MysqlxNotice;
import com.mysql.cj.mysqlx.protobuf.MysqlxResultset;
import com.mysql.cj.mysqlx.protobuf.MysqlxSql;
import com.mysql.cj.mysqlx.result.MysqlxRow;
import java.util.ArrayList;
import java.util.function.Function;

public class ResultMessageListener implements AsyncMessageReader.MessageListener {
   private ResultListener callbacks;
   private ResultMessageListener.ColToFieldTransformer colToField;
   private ArrayList<Field> metadata = new ArrayList();
   private boolean metadataSent = false;
   private StatementExecuteOkBuilder okBuilder = new StatementExecuteOkBuilder();

   public ResultMessageListener(ResultMessageListener.ColToFieldTransformer colToField, ResultListener callbacks) {
      this.callbacks = callbacks;
      this.colToField = colToField;
   }

   private boolean handleColumn(MysqlxResultset.ColumnMetaData col) {
      Field f = (Field)this.colToField.apply(col);
      this.metadata.add(f);
      return false;
   }

   private boolean handleRow(MysqlxResultset.Row r) {
      MysqlxRow row = new MysqlxRow(this.metadata, r);
      this.callbacks.onRow(row);
      return false;
   }

   private boolean handleStmtExecuteOk() {
      this.callbacks.onComplete(this.okBuilder.build());
      return true;
   }

   private boolean handleError(Mysqlx.Error error) {
      MysqlxError e = new MysqlxError(error);
      this.callbacks.onError(e);
      return true;
   }

   private void handleException(Throwable ex) {
      this.callbacks.onException(ex);
   }

   public Boolean apply(Class<? extends GeneratedMessage> msgClass, GeneratedMessage msg) {
      if (MysqlxResultset.ColumnMetaData.class.equals(msgClass)) {
         return this.handleColumn((MysqlxResultset.ColumnMetaData)MysqlxResultset.ColumnMetaData.class.cast(msg));
      } else {
         if (!this.metadataSent) {
            this.callbacks.onMetadata(this.metadata);
            this.metadataSent = true;
         }

         if (MysqlxSql.StmtExecuteOk.class.equals(msgClass)) {
            return this.handleStmtExecuteOk();
         } else if (MysqlxResultset.FetchDone.class.equals(msgClass)) {
            return false;
         } else if (MysqlxResultset.Row.class.equals(msgClass)) {
            return this.handleRow((MysqlxResultset.Row)MysqlxResultset.Row.class.cast(msg));
         } else if (Mysqlx.Error.class.equals(msgClass)) {
            return this.handleError((Mysqlx.Error)Mysqlx.Error.class.cast(msg));
         } else if (MysqlxNotice.Frame.class.equals(msgClass)) {
            this.okBuilder.addNotice((MysqlxNotice.Frame)MysqlxNotice.Frame.class.cast(msg));
            return false;
         } else {
            this.handleException(new WrongArgumentException("Unhandled msg class (" + msgClass + ") + msg=" + msg));
            return false;
         }
      }
   }

   public void closed() {
   }

   public void error(Throwable ex) {
      this.handleException(ex);
   }

   public interface ColToFieldTransformer extends Function<MysqlxResultset.ColumnMetaData, Field> {
   }
}
