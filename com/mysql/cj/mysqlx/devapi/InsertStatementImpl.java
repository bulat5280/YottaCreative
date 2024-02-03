package com.mysql.cj.mysqlx.devapi;

import com.mysql.cj.api.x.InsertStatement;
import com.mysql.cj.api.x.Result;
import com.mysql.cj.core.io.StatementExecuteOk;
import com.mysql.cj.mysqlx.InsertParams;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class InsertStatementImpl implements InsertStatement {
   private TableImpl table;
   private InsertParams insertParams = new InsertParams();

   InsertStatementImpl(TableImpl table, String[] fields) {
      this.table = table;
      this.insertParams.setProjection(fields);
   }

   InsertStatementImpl(TableImpl table, Map<String, Object> fieldsAndValues) {
      this.table = table;
      this.insertParams.setFieldsAndValues(fieldsAndValues);
   }

   public Result execute() {
      StatementExecuteOk ok = this.table.getSession().getMysqlxSession().insertRows(this.table.getSchema().getName(), this.table.getName(), this.insertParams);
      return new UpdateResult(ok, (List)null);
   }

   public CompletableFuture<Result> executeAsync() {
      CompletableFuture<StatementExecuteOk> okF = this.table.getSession().getMysqlxSession().asyncInsertRows(this.table.getSchema().getName(), this.table.getName(), this.insertParams);
      return okF.thenApply((ok) -> {
         return new UpdateResult(ok, (List)null);
      });
   }

   public InsertStatement values(List<Object> row) {
      this.insertParams.addRow(row);
      return this;
   }
}
