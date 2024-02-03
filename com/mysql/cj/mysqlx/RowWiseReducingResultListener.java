package com.mysql.cj.mysqlx;

import com.mysql.cj.api.x.DataStatement;
import com.mysql.cj.core.io.StatementExecuteOk;
import com.mysql.cj.core.result.Field;
import com.mysql.cj.mysqlx.io.ResultListener;
import com.mysql.cj.mysqlx.result.MysqlxRow;
import com.mysql.cj.mysqlx.result.RowToElement;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class RowWiseReducingResultListener<RES_ELEMENT_T, R> implements ResultListener {
   private DataStatement.Reducer<RES_ELEMENT_T, R> reducer;
   private CompletableFuture<R> future;
   private R accumulator;
   private RowWiseReducingResultListener.MetadataToRowToElement<RES_ELEMENT_T> metadataToRowToElement;
   private RowToElement<RES_ELEMENT_T> rowToElement;

   public RowWiseReducingResultListener(R accumulator, DataStatement.Reducer<RES_ELEMENT_T, R> reducer, CompletableFuture<R> future, RowWiseReducingResultListener.MetadataToRowToElement<RES_ELEMENT_T> metadataToRowToElement) {
      this.accumulator = accumulator;
      this.reducer = reducer;
      this.future = future;
      this.metadataToRowToElement = metadataToRowToElement;
   }

   public void onMetadata(ArrayList<Field> metadata) {
      this.rowToElement = (RowToElement)this.metadataToRowToElement.apply(metadata);
   }

   public void onRow(MysqlxRow r) {
      this.accumulator = this.reducer.apply(this.accumulator, this.rowToElement.apply(r));
   }

   public void onComplete(StatementExecuteOk ok) {
      this.future.complete(this.accumulator);
   }

   public void onError(MysqlxError error) {
      this.future.completeExceptionally(error);
   }

   public void onException(Throwable t) {
      this.future.completeExceptionally(t);
   }

   public interface MetadataToRowToElement<T> extends Function<ArrayList<Field>, RowToElement<T>> {
   }
}
