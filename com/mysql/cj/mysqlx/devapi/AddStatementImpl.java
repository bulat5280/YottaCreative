package com.mysql.cj.mysqlx.devapi;

import com.mysql.cj.api.x.AddStatement;
import com.mysql.cj.api.x.Result;
import com.mysql.cj.core.exceptions.AssertionFailedException;
import com.mysql.cj.core.io.StatementExecuteOk;
import com.mysql.cj.mysqlx.DocumentID;
import com.mysql.cj.x.json.DbDoc;
import com.mysql.cj.x.json.JsonParser;
import com.mysql.cj.x.json.JsonString;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class AddStatementImpl implements AddStatement {
   private CollectionImpl collection;
   private List<DbDoc> newDocs;

   AddStatementImpl(CollectionImpl collection, DbDoc newDoc) {
      this.collection = collection;
      this.newDocs = new ArrayList();
      this.newDocs.add(newDoc);
   }

   AddStatementImpl(CollectionImpl collection, DbDoc[] newDocs) {
      this.collection = collection;
      this.newDocs = new ArrayList();
      this.newDocs.addAll(Arrays.asList(newDocs));
   }

   public AddStatement add(String jsonString) {
      try {
         DbDoc doc = JsonParser.parseDoc(new StringReader(jsonString));
         return this.add(doc);
      } catch (IOException var3) {
         throw AssertionFailedException.shouldNotHappen((Exception)var3);
      }
   }

   public AddStatement add(DbDoc... docs) {
      this.newDocs.addAll(Arrays.asList(docs));
      return this;
   }

   private List<String> assignIds() {
      return (List)this.newDocs.stream().filter((d) -> {
         return d.get("_id") == null;
      }).map((d) -> {
         String newId = DocumentID.generate();
         d.put("_id", (new JsonString()).setValue(newId));
         return newId;
      }).collect(Collectors.toList());
   }

   private List<String> serializeDocs() {
      return (List)this.newDocs.stream().map(DbDoc::toPackedString).collect(Collectors.toList());
   }

   public Result execute() {
      if (this.newDocs.size() == 0) {
         StatementExecuteOk ok = new StatementExecuteOk(0L, (Long)null, new ArrayList());
         return new UpdateResult(ok, new ArrayList());
      } else {
         List<String> newIds = this.assignIds();
         StatementExecuteOk ok = this.collection.getSession().getMysqlxSession().addDocs(this.collection.getSchema().getName(), this.collection.getName(), this.serializeDocs());
         return new UpdateResult(ok, newIds);
      }
   }

   public CompletableFuture<Result> executeAsync() {
      List<String> newIds = this.assignIds();
      CompletableFuture<StatementExecuteOk> okF = this.collection.getSession().getMysqlxSession().asyncAddDocs(this.collection.getSchema().getName(), this.collection.getName(), this.serializeDocs());
      return okF.thenApply((ok) -> {
         return new UpdateResult(ok, newIds);
      });
   }
}
