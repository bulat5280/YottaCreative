package com.mysql.cj.mysqlx.devapi;

import com.mysql.cj.api.x.CreateCollectionIndexStatement;
import com.mysql.cj.api.x.Result;
import com.mysql.cj.core.io.StatementExecuteOk;
import com.mysql.cj.mysqlx.CreateIndexParams;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class CreateCollectionIndexStatementImpl implements CreateCollectionIndexStatement {
   private CollectionImpl collection;
   private CreateIndexParams createIndexParams;

   CreateCollectionIndexStatementImpl(CollectionImpl collection, String indexName, boolean unique) {
      this.collection = collection;
      this.createIndexParams = new CreateIndexParams(indexName, unique);
   }

   public CreateCollectionIndexStatement field(String docPath, String type, boolean notNull) {
      this.createIndexParams.addField(docPath, type, notNull);
      return this;
   }

   public Result execute() {
      StatementExecuteOk ok = this.collection.getSession().getMysqlxSession().createCollectionIndex(this.collection.getSchema().getName(), this.collection.getName(), this.createIndexParams);
      return new UpdateResult(ok, (List)null);
   }

   public CompletableFuture<Result> executeAsync() {
      CompletableFuture<StatementExecuteOk> okF = this.collection.getSession().getMysqlxSession().asyncCreateCollectionIndex(this.collection.getSchema().getName(), this.collection.getName(), this.createIndexParams);
      return okF.thenApply((ok) -> {
         return new UpdateResult(ok, (List)null);
      });
   }
}
