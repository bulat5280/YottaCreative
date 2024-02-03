package com.mysql.cj.mysqlx.devapi;

import com.mysql.cj.api.x.AddStatement;
import com.mysql.cj.api.x.BaseSession;
import com.mysql.cj.api.x.Collection;
import com.mysql.cj.api.x.CreateCollectionIndexStatement;
import com.mysql.cj.api.x.DatabaseObject;
import com.mysql.cj.api.x.DropCollectionIndexStatement;
import com.mysql.cj.api.x.FindStatement;
import com.mysql.cj.api.x.ModifyStatement;
import com.mysql.cj.api.x.RemoveStatement;
import com.mysql.cj.api.x.Schema;
import com.mysql.cj.core.exceptions.AssertionFailedException;
import com.mysql.cj.core.exceptions.FeatureNotAvailableException;
import com.mysql.cj.mysqlx.ExprUnparser;
import com.mysql.cj.x.json.DbDoc;
import com.mysql.cj.x.json.JsonParser;
import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

public class CollectionImpl implements Collection {
   private SchemaImpl schema;
   private String name;

   CollectionImpl(SchemaImpl schema, String name) {
      this.schema = schema;
      this.name = name;
   }

   public BaseSession getSession() {
      return this.schema.getSession();
   }

   public Schema getSchema() {
      return this.schema;
   }

   public String getName() {
      return this.name;
   }

   public DatabaseObject.DbObjectStatus existsInDatabase() {
      return this.schema.getSession().getMysqlxSession().tableExists(this.schema.getName(), this.name) ? DatabaseObject.DbObjectStatus.EXISTS : DatabaseObject.DbObjectStatus.NOT_EXISTS;
   }

   public AddStatement add(Map<String, ?> doc) {
      throw new FeatureNotAvailableException("TODO: ");
   }

   public AddStatement add(String... jsonString) {
      try {
         DbDoc[] docs = new DbDoc[jsonString.length];

         for(int i = 0; i < jsonString.length; ++i) {
            docs[i] = JsonParser.parseDoc(new StringReader(jsonString[i]));
         }

         return this.add(docs);
      } catch (IOException var4) {
         throw AssertionFailedException.shouldNotHappen((Exception)var4);
      }
   }

   public AddStatement add(DbDoc doc) {
      return new AddStatementImpl(this, doc);
   }

   public AddStatement add(DbDoc... docs) {
      return new AddStatementImpl(this, docs);
   }

   public FindStatement find() {
      return this.find((String)null);
   }

   public FindStatement find(String searchCondition) {
      return new FindStatementImpl(this, searchCondition);
   }

   public ModifyStatement modify() {
      return this.modify((String)null);
   }

   public ModifyStatement modify(String searchCondition) {
      return new ModifyStatementImpl(this, searchCondition);
   }

   public RemoveStatement remove() {
      return this.remove((String)null);
   }

   public RemoveStatement remove(String searchCondition) {
      return new RemoveStatementImpl(this, searchCondition);
   }

   public CreateCollectionIndexStatement createIndex(String indexName, boolean isUnique) {
      return new CreateCollectionIndexStatementImpl(this, indexName, isUnique);
   }

   public DropCollectionIndexStatement dropIndex(String indexName) {
      return new DropCollectionIndexStatementImpl(this, indexName);
   }

   public long count() {
      return this.schema.getSession().getMysqlxSession().tableCount(this.schema.getName(), this.name);
   }

   public DbDoc newDoc() {
      return new DbDoc();
   }

   public boolean equals(Object other) {
      return other != null && other.getClass() == CollectionImpl.class && ((CollectionImpl)other).schema.equals(this.schema) && ((CollectionImpl)other).schema.getSession() == this.schema.getSession() ? this.name.equals(((CollectionImpl)other).name) : false;
   }

   public int hashCode() {
      assert false : "hashCode not designed";

      return 0;
   }

   public String toString() {
      StringBuilder sb = new StringBuilder("Collection(");
      sb.append(ExprUnparser.quoteIdentifier(this.schema.getName()));
      sb.append(".");
      sb.append(ExprUnparser.quoteIdentifier(this.name));
      sb.append(")");
      return sb.toString();
   }
}
