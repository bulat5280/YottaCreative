package com.mysql.cj.mysqlx;

import com.mysql.cj.api.result.Row;
import com.mysql.cj.api.result.RowList;
import com.mysql.cj.core.io.StatementExecuteOk;
import com.mysql.cj.core.result.BufferedRowList;
import com.mysql.cj.core.result.Field;
import com.mysql.cj.mysqlx.io.ResultListener;
import com.mysql.cj.mysqlx.result.MysqlxRow;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;

public class ResultCreatingResultListener<RES_T> implements ResultListener {
   private ArrayList<Field> metadata;
   private List<Row> rows = new ArrayList();
   private MysqlxSession.ResultCtor<? extends RES_T> resultCtor;
   private CompletableFuture<RES_T> future;

   public ResultCreatingResultListener(MysqlxSession.ResultCtor<? extends RES_T> resultCtor, CompletableFuture<RES_T> future) {
      this.resultCtor = resultCtor;
      this.future = future;
   }

   public void onMetadata(ArrayList<Field> metadataFields) {
      this.metadata = metadataFields;
   }

   public void onRow(MysqlxRow r) {
      this.rows.add(r);
   }

   public void onComplete(StatementExecuteOk ok) {
      RowList rowList = new BufferedRowList(this.rows);
      RES_T result = ((BiFunction)this.resultCtor.apply(this.metadata)).apply(rowList, () -> {
         return ok;
      });
      this.future.complete(result);
   }

   public void onError(MysqlxError error) {
      this.future.completeExceptionally(error);
   }

   public void onException(Throwable t) {
      this.future.completeExceptionally(t);
   }
}
