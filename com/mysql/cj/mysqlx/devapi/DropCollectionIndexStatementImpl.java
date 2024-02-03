package com.mysql.cj.mysqlx.devapi;

import com.mysql.cj.api.x.DropCollectionIndexStatement;
import com.mysql.cj.api.x.Result;
import com.mysql.cj.core.io.StatementExecuteOk;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class DropCollectionIndexStatementImpl implements DropCollectionIndexStatement {
   private CollectionImpl collection;
   private String indexName;

   DropCollectionIndexStatementImpl(CollectionImpl collection, String indexName) {
      this.collection = collection;
      this.indexName = indexName;
   }

   public Result execute() {
      StatementExecuteOk ok = this.collection.getSession().getMysqlxSession().dropCollectionIndex(this.collection.getSchema().getName(), this.collection.getName(), this.indexName);
      return new UpdateResult(ok, (List)null);
   }

   public CompletableFuture<Result> executeAsync() {
      CompletableFuture<StatementExecuteOk> okF = this.collection.getSession().getMysqlxSession().asyncDropCollectionIndex(this.collection.getSchema().getName(), this.collection.getName(), this.indexName);
      return okF.thenApply((ok) -> {
         return new UpdateResult(ok, (List)null);
      });
   }
}
