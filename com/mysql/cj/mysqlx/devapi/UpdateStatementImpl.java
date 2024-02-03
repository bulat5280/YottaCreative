package com.mysql.cj.mysqlx.devapi;

import com.mysql.cj.api.x.Result;
import com.mysql.cj.api.x.UpdateStatement;
import com.mysql.cj.core.io.StatementExecuteOk;
import com.mysql.cj.mysqlx.UpdateParams;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class UpdateStatementImpl extends FilterableStatement<UpdateStatement, Result> implements UpdateStatement {
   private TableImpl table;
   private UpdateParams updateParams = new UpdateParams();

   UpdateStatementImpl(TableImpl table) {
      super(table.getSchema().getName(), table.getName(), true);
      this.table = table;
   }

   public Result execute() {
      StatementExecuteOk ok = this.table.getSession().getMysqlxSession().updateRows(this.filterParams, this.updateParams);
      return new UpdateResult(ok, (List)null);
   }

   public CompletableFuture<Result> executeAsync() {
      CompletableFuture<StatementExecuteOk> okF = this.table.getSession().getMysqlxSession().asyncUpdateRows(this.filterParams, this.updateParams);
      return okF.thenApply((ok) -> {
         return new UpdateResult(ok, (List)null);
      });
   }

   public UpdateStatement set(Map<String, Object> fieldsAndValues) {
      this.updateParams.setUpdates(fieldsAndValues);
      return this;
   }

   public UpdateStatement set(String field, Object value) {
      this.updateParams.addUpdate(field, value);
      return this;
   }
}
