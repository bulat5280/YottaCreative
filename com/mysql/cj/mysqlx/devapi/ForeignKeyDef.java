package com.mysql.cj.mysqlx.devapi;

import com.mysql.cj.api.x.ForeignKeyDefinition;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ForeignKeyDef implements ForeignKeyDefinition {
   private String name;
   protected String[] columns;
   protected String toTable;
   protected String[] toColumns;
   protected ForeignKeyDefinition.ChangeMode onDelete;
   protected ForeignKeyDefinition.ChangeMode onUpdate;

   public ForeignKeyDef() {
      this.onDelete = ForeignKeyDefinition.ChangeMode.RESTRICT;
      this.onUpdate = ForeignKeyDefinition.ChangeMode.RESTRICT;
   }

   public ForeignKeyDefinition setName(String fkName) {
      this.name = fkName;
      return this;
   }

   public ForeignKeyDefinition fields(String... column) {
      this.columns = column;
      return this;
   }

   public ForeignKeyDefinition refersTo(String table, String... column) {
      this.toTable = table;
      this.toColumns = column;
      return this;
   }

   public ForeignKeyDefinition onDelete(ForeignKeyDefinition.ChangeMode mode) {
      this.onDelete = mode;
      return this;
   }

   public ForeignKeyDefinition onUpdate(ForeignKeyDefinition.ChangeMode mode) {
      this.onUpdate = mode;
      return this;
   }

   public String toString() {
      StringBuilder sb = new StringBuilder("FOREIGN KEY");
      sb.append(" ").append(this.name);
      sb.append((String)Arrays.stream(this.columns).collect(Collectors.joining(", ", " (", ")")));
      sb.append(" REFERENCES ").append(this.toTable);
      sb.append((String)Arrays.stream(this.toColumns).collect(Collectors.joining(", ", " (", ")")));
      if (this.onDelete != ForeignKeyDefinition.ChangeMode.RESTRICT) {
         sb.append(" ON DELETE ").append(this.onDelete.getExpr());
      }

      if (this.onUpdate != ForeignKeyDefinition.ChangeMode.RESTRICT) {
         sb.append(" ON UPDATE ").append(this.onUpdate.getExpr());
      }

      return sb.toString();
   }
}
