package com.mysql.cj.mysqlx.devapi;

import com.mysql.cj.api.x.ColumnDefinition;
import com.mysql.cj.api.x.Type;
import com.mysql.cj.core.util.StringUtils;

public final class GeneratedColumnDef extends AbstractColumnDef<ColumnDefinition.GeneratedColumnDefinition> implements ColumnDefinition.GeneratedColumnDefinition {
   private String expr;
   private boolean isStored = false;

   public GeneratedColumnDef(String columnName, Type columnType, String expression) {
      this.name = columnName;
      this.type = columnType;
      this.expr = expression;
   }

   public GeneratedColumnDef(String columnName, Type columnType, int length, String expression) {
      this.name = columnName;
      this.type = columnType;
      this.length = length;
      this.expr = expression;
   }

   ColumnDefinition.GeneratedColumnDefinition self() {
      return this;
   }

   public ColumnDefinition.GeneratedColumnDefinition stored() {
      this.isStored = true;
      return this.self();
   }

   public String toString() {
      StringBuilder sb = new StringBuilder(this.name);
      sb.append(" ").append(this.getMysqlType());
      sb.append(" AS (").append(this.expr).append(")");
      if (this.isStored) {
         sb.append(" STORED");
      }

      if (this.uniqueIndex) {
         sb.append(" UNIQUE KEY");
      }

      if (this.comment != null && !this.comment.isEmpty()) {
         sb.append(" COMMENT ").append(StringUtils.quoteIdentifier(this.comment, "'", true));
      }

      if (this.notNull != null) {
         sb.append(this.notNull ? " NOT NULL" : " NULL");
      }

      if (this.primaryKey) {
         sb.append(" PRIMARY KEY");
      }

      return sb.toString();
   }
}
