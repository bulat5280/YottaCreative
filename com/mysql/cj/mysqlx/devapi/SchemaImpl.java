package com.mysql.cj.mysqlx.devapi;

import com.mysql.cj.api.x.BaseSession;
import com.mysql.cj.api.x.Collection;
import com.mysql.cj.api.x.CreateTableStatement;
import com.mysql.cj.api.x.DatabaseObject;
import com.mysql.cj.api.x.Schema;
import com.mysql.cj.api.x.Table;
import com.mysql.cj.core.exceptions.WrongArgumentException;
import com.mysql.cj.mysqlx.ExprUnparser;
import com.mysql.cj.mysqlx.MysqlxError;
import java.util.List;
import java.util.stream.Collectors;

public class SchemaImpl implements Schema {
   private BaseSession session;
   private String name;

   SchemaImpl(BaseSession session, String name) {
      this.session = session;
      this.name = name;
   }

   public BaseSession getSession() {
      return this.session;
   }

   public Schema getSchema() {
      return this;
   }

   public String getName() {
      return this.name;
   }

   public DatabaseObject.DbObjectStatus existsInDatabase() {
      return this.session.getMysqlxSession().schemaExists(this.name) ? DatabaseObject.DbObjectStatus.EXISTS : DatabaseObject.DbObjectStatus.NOT_EXISTS;
   }

   public List<Collection> getCollections() {
      return (List)this.session.getMysqlxSession().getObjectNamesOfType(this.name, DatabaseObject.DbObjectType.COLLECTION).stream().map(this::getCollection).collect(Collectors.toList());
   }

   public List<Collection> getCollections(String pattern) {
      return (List)this.session.getMysqlxSession().getObjectNamesOfType(this.name, DatabaseObject.DbObjectType.COLLECTION, pattern).stream().map(this::getCollection).collect(Collectors.toList());
   }

   public List<Table> getTables() {
      return (List)this.session.getMysqlxSession().listObjects(this.name).stream().filter((descr) -> {
         return descr.getObjectType() == DatabaseObject.DbObjectType.TABLE || descr.getObjectType() == DatabaseObject.DbObjectType.VIEW;
      }).map(this::getTable).collect(Collectors.toList());
   }

   public List<Table> getTables(String pattern) {
      return (List)this.session.getMysqlxSession().listObjects(this.name, pattern).stream().filter((descr) -> {
         return descr.getObjectType() == DatabaseObject.DbObjectType.TABLE || descr.getObjectType() == DatabaseObject.DbObjectType.VIEW;
      }).map(this::getTable).collect(Collectors.toList());
   }

   public Collection getCollection(String collectionName) {
      return new CollectionImpl(this, collectionName);
   }

   public Collection getCollection(String collectionName, boolean requireExists) {
      CollectionImpl coll = new CollectionImpl(this, collectionName);
      if (requireExists && coll.existsInDatabase() != DatabaseObject.DbObjectStatus.EXISTS) {
         throw new WrongArgumentException(coll.toString() + " doesn't exist");
      } else {
         return coll;
      }
   }

   public Table getCollectionAsTable(String collectionName) {
      return this.getTable(collectionName);
   }

   public Table getTable(String tableName) {
      return new TableImpl(this, tableName);
   }

   Table getTable(DatabaseObjectDescription descr) {
      return new TableImpl(this, descr);
   }

   public Table getTable(String tableName, boolean requireExists) {
      TableImpl table = new TableImpl(this, tableName);
      if (requireExists && table.existsInDatabase() != DatabaseObject.DbObjectStatus.EXISTS) {
         throw new WrongArgumentException(table.toString() + " doesn't exist");
      } else {
         return table;
      }
   }

   public Collection createCollection(String collectionName) {
      this.session.getMysqlxSession().createCollection(this.name, collectionName);
      return new CollectionImpl(this, collectionName);
   }

   public Collection createCollection(String collectionName, boolean reuseExistingObject) {
      try {
         return this.createCollection(collectionName);
      } catch (MysqlxError var4) {
         if (var4.getErrorCode() == 1050) {
            return this.getCollection(collectionName);
         } else {
            throw var4;
         }
      }
   }

   public CreateTableStatement.CreateTableSplitStatement createTable(String tableName) {
      return new CreateTableStatementImpl(this, tableName);
   }

   public CreateTableStatement.CreateTableSplitStatement createTable(String tableName, boolean reuseExistingObject) {
      return new CreateTableStatementImpl(this, tableName, reuseExistingObject);
   }

   public boolean equals(Object other) {
      return other != null && other.getClass() == SchemaImpl.class && ((SchemaImpl)other).session == this.session ? this.name.equals(((SchemaImpl)other).name) : false;
   }

   public int hashCode() {
      assert false : "hashCode not designed";

      return 0;
   }

   public String toString() {
      StringBuilder sb = new StringBuilder("Schema(");
      sb.append(ExprUnparser.quoteIdentifier(this.name));
      sb.append(")");
      return sb.toString();
   }
}
