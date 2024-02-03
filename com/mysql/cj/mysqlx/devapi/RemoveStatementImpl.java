package com.mysql.cj.mysqlx.devapi;

import com.mysql.cj.api.x.RemoveStatement;
import com.mysql.cj.api.x.Result;
import com.mysql.cj.core.io.StatementExecuteOk;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class RemoveStatementImpl extends FilterableStatement<RemoveStatement, Result> implements RemoveStatement {
   private CollectionImpl collection;

   public RemoveStatementImpl(CollectionImpl collection, String criteria) {
      super(collection.getSchema().getName(), collection.getName(), false);
      this.collection = collection;
      if (criteria != null && criteria.length() > 0) {
         this.filterParams.setCriteria(criteria);
      }

   }

   public Result execute() {
      StatementExecuteOk ok = this.collection.getSession().getMysqlxSession().deleteDocs(this.filterParams);
      return new UpdateResult(ok, (List)null);
   }

   public CompletableFuture<Result> executeAsync() {
      CompletableFuture<StatementExecuteOk> okF = this.collection.getSession().getMysqlxSession().asyncDeleteDocs(this.filterParams);
      return okF.thenApply((ok) -> {
         return new UpdateResult(ok, (List)null);
      });
   }
}
