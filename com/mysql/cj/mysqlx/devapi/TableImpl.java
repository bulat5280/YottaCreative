package com.mysql.cj.mysqlx.devapi;

import com.mysql.cj.api.x.BaseSession;
import com.mysql.cj.api.x.DatabaseObject;
import com.mysql.cj.api.x.DeleteStatement;
import com.mysql.cj.api.x.InsertStatement;
import com.mysql.cj.api.x.Schema;
import com.mysql.cj.api.x.SelectStatement;
import com.mysql.cj.api.x.Table;
import com.mysql.cj.api.x.UpdateStatement;
import com.mysql.cj.mysqlx.ExprUnparser;
import java.util.List;
import java.util.Map;

public class TableImpl implements Table {
   private SchemaImpl schema;
   private String name;
   private Boolean isView = null;

   TableImpl(SchemaImpl schema, String name) {
      this.schema = schema;
      this.name = name;
   }

   TableImpl(SchemaImpl schema, DatabaseObjectDescription descr) {
      this.schema = schema;
      this.name = descr.getObjectName();
      this.isView = descr.getObjectType() == DatabaseObject.DbObjectType.VIEW;
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

   public InsertStatement insert() {
      return new InsertStatementImpl(this, new String[0]);
   }

   public InsertStatement insert(String... fields) {
      return new InsertStatementImpl(this, fields);
   }

   public InsertStatement insert(Map<String, Object> fieldsAndValues) {
      return new InsertStatementImpl(this, fieldsAndValues);
   }

   public SelectStatement select(String... projection) {
      return new SelectStatementImpl(this, projection);
   }

   public UpdateStatement update() {
      return new UpdateStatementImpl(this);
   }

   public DeleteStatement delete() {
      return new DeleteStatementImpl(this);
   }

   public long count() {
      return this.schema.getSession().getMysqlxSession().tableCount(this.schema.getName(), this.name);
   }

   public boolean equals(Object other) {
      return other != null && other.getClass() == TableImpl.class && ((TableImpl)other).schema.equals(this.schema) && ((TableImpl)other).schema.getSession() == this.schema.getSession() ? this.name.equals(((TableImpl)other).name) : false;
   }

   public int hashCode() {
      assert false : "hashCode not designed";

      return 0;
   }

   public String toString() {
      StringBuilder sb = new StringBuilder("Table(");
      sb.append(ExprUnparser.quoteIdentifier(this.schema.getName()));
      sb.append(".");
      sb.append(ExprUnparser.quoteIdentifier(this.name));
      sb.append(")");
      return sb.toString();
   }

   public boolean isView() {
      if (this.isView == null) {
         List<DatabaseObjectDescription> objects = this.getSession().getMysqlxSession().listObjects(this.schema.getName(), this.name);
         if (objects.isEmpty()) {
            return false;
         }

         this.isView = ((DatabaseObjectDescription)objects.get(0)).getObjectType() == DatabaseObject.DbObjectType.VIEW;
      }

      return this.isView;
   }

   public void setView(boolean isView) {
      this.isView = isView;
   }
}
