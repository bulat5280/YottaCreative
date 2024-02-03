package com.mysql.cj.mysqlx.devapi;

import com.mysql.cj.api.x.DeleteStatement;
import com.mysql.cj.api.x.Result;
import com.mysql.cj.core.io.StatementExecuteOk;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class DeleteStatementImpl extends FilterableStatement<DeleteStatement, Result> implements DeleteStatement {
   private TableImpl table;

   DeleteStatementImpl(TableImpl table) {
      super(table.getSchema().getName(), table.getName(), true);
      this.table = table;
   }

   public Result execute() {
      StatementExecuteOk ok = this.table.getSession().getMysqlxSession().deleteRows(this.filterParams);
      return new UpdateResult(ok, (List)null);
   }

   public CompletableFuture<Result> executeAsync() {
      CompletableFuture<StatementExecuteOk> okF = this.table.getSession().getMysqlxSession().asyncDeleteRows(this.filterParams);
      return okF.thenApply((ok) -> {
         return new UpdateResult(ok, (List)null);
      });
   }
}
