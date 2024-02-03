package com.mysql.cj.mysqlx.devapi;

import com.mysql.cj.api.x.ModifyStatement;
import com.mysql.cj.api.x.Result;
import com.mysql.cj.core.exceptions.FeatureNotAvailableException;
import com.mysql.cj.core.io.StatementExecuteOk;
import com.mysql.cj.mysqlx.UpdateSpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class ModifyStatementImpl extends FilterableStatement<ModifyStatement, Result> implements ModifyStatement {
   private CollectionImpl collection;
   private List<UpdateSpec> updates = new ArrayList();

   ModifyStatementImpl(CollectionImpl collection, String criteria) {
      super(collection.getSchema().getName(), collection.getName(), false);
      this.collection = collection;
      if (criteria != null && criteria.length() > 0) {
         this.filterParams.setCriteria(criteria);
      }

   }

   public Result execute() {
      StatementExecuteOk ok = this.collection.getSession().getMysqlxSession().updateDocs(this.filterParams, this.updates);
      return new UpdateResult(ok, (List)null);
   }

   public CompletableFuture<Result> executeAsync() {
      CompletableFuture<StatementExecuteOk> okF = this.collection.getSession().getMysqlxSession().asyncUpdateDocs(this.filterParams, this.updates);
      return okF.thenApply((ok) -> {
         return new UpdateResult(ok, (List)null);
      });
   }

   public ModifyStatement set(String docPath, Object value) {
      this.updates.add((new UpdateSpec(UpdateSpec.UpdateType.ITEM_SET, docPath)).setValue(value));
      return this;
   }

   public ModifyStatement change(String docPath, Object value) {
      this.updates.add((new UpdateSpec(UpdateSpec.UpdateType.ITEM_REPLACE, docPath)).setValue(value));
      return this;
   }

   public ModifyStatement unset(String... fields) {
      this.updates.addAll((Collection)Arrays.stream(fields).map((docPath) -> {
         return new UpdateSpec(UpdateSpec.UpdateType.ITEM_REMOVE, docPath);
      }).collect(Collectors.toList()));
      return this;
   }

   public ModifyStatement merge(String document) {
      throw new FeatureNotAvailableException("TODO: not supported in xplugin");
   }

   public ModifyStatement arrayInsert(String field, Object value) {
      this.updates.add((new UpdateSpec(UpdateSpec.UpdateType.ARRAY_INSERT, field)).setValue(value));
      return this;
   }

   public ModifyStatement arrayAppend(String docPath, Object value) {
      this.updates.add((new UpdateSpec(UpdateSpec.UpdateType.ARRAY_APPEND, docPath)).setValue(value));
      return this;
   }

   public ModifyStatement arrayDelete(String field, int position) {
      throw new FeatureNotAvailableException("TODO: not supported in xplugin");
   }
}
