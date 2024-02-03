package com.mysql.cj.mysqlx.devapi;

import com.mysql.cj.api.x.DataStatement;
import com.mysql.cj.api.x.DocResult;
import com.mysql.cj.api.x.Expression;
import com.mysql.cj.api.x.FindStatement;
import com.mysql.cj.mysqlx.DocFindParams;
import com.mysql.cj.x.json.DbDoc;
import java.util.concurrent.CompletableFuture;

public class FindStatementImpl extends FilterableStatement<FindStatement, DocResult> implements FindStatement {
   private CollectionImpl collection;
   private DocFindParams findParams;

   FindStatementImpl(CollectionImpl collection, String criteria) {
      super(new DocFindParams(collection.getSchema().getName(), collection.getName()));
      this.findParams = (DocFindParams)this.filterParams;
      this.collection = collection;
      if (criteria != null && criteria.length() > 0) {
         this.findParams.setCriteria(criteria);
      }

   }

   public DocResultImpl execute() {
      return this.collection.getSession().getMysqlxSession().findDocs(this.findParams);
   }

   public CompletableFuture<DocResult> executeAsync() {
      return this.collection.getSession().getMysqlxSession().asyncFindDocs(this.findParams);
   }

   public <R> CompletableFuture<R> executeAsync(R id, DataStatement.Reducer<DbDoc, R> reducer) {
      return this.collection.getSession().getMysqlxSession().asyncFindDocsReduce(this.findParams, id, reducer);
   }

   public FindStatement fields(String... projection) {
      this.findParams.setFields(projection);
      return this;
   }

   public FindStatement fields(Expression docProjection) {
      this.findParams.setFields(docProjection);
      return this;
   }

   public FindStatement groupBy(String... groupBy) {
      this.findParams.setGrouping(groupBy);
      return this;
   }

   public FindStatement having(String having) {
      this.findParams.setGroupingCriteria(having);
      return this;
   }
}
