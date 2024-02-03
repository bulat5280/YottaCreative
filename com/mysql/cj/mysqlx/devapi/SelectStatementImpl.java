package com.mysql.cj.mysqlx.devapi;

import com.mysql.cj.api.x.DataStatement;
import com.mysql.cj.api.x.Row;
import com.mysql.cj.api.x.RowResult;
import com.mysql.cj.api.x.SelectStatement;
import com.mysql.cj.mysqlx.FindParams;
import com.mysql.cj.mysqlx.TableFindParams;
import java.util.concurrent.CompletableFuture;

public class SelectStatementImpl extends FilterableStatement<SelectStatement, RowResult> implements SelectStatement {
   private TableImpl table;
   private FindParams findParams;

   SelectStatementImpl(TableImpl table, String projection) {
      super(new TableFindParams(table.getSchema().getName(), table.getName()));
      this.findParams = (TableFindParams)this.filterParams;
      this.table = table;
      if (projection != null && projection.length() > 0) {
         this.findParams.setFields(projection);
      }

   }

   SelectStatementImpl(TableImpl table, String... projection) {
      super(new TableFindParams(table.getSchema().getName(), table.getName()));
      this.findParams = (TableFindParams)this.filterParams;
      this.table = table;
      if (projection != null && projection.length > 0) {
         this.findParams.setFields(projection);
      }

   }

   public RowResultImpl execute() {
      return this.table.getSession().getMysqlxSession().selectRows(this.findParams);
   }

   public CompletableFuture<RowResult> executeAsync() {
      return this.table.getSession().getMysqlxSession().asyncSelectRows(this.findParams);
   }

   public <R> CompletableFuture<R> executeAsync(R id, DataStatement.Reducer<Row, R> reducer) {
      return this.table.getSession().getMysqlxSession().asyncSelectRowsReduce(this.findParams, id, reducer);
   }

   public SelectStatement groupBy(String... groupBy) {
      this.findParams.setGrouping(groupBy);
      return this;
   }

   public SelectStatement having(String having) {
      this.findParams.setGroupingCriteria(having);
      return this;
   }
}
