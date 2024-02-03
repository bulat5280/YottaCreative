package com.mysql.cj.mysqlx.devapi;

import com.mysql.cj.api.x.ColumnDefinition;
import com.mysql.cj.api.x.Type;
import com.mysql.cj.core.util.StringUtils;
import java.util.HashMap;
import java.util.Map;

public final class ColumnDef extends AbstractColumnDef<ColumnDefinition.StaticColumnDefinition> implements ColumnDefinition.StaticColumnDefinition {
   protected String defaultExpr = null;
   protected boolean defaultExprWasSet = false;
   protected boolean autoIncrement = false;
   protected Map<String, String[]> foreignKey = new HashMap();

   public ColumnDef(String columnName, Type columnType) {
      this.name = columnName;
      this.type = columnType;
   }

   public ColumnDef(String columnName, Type columnType, int length) {
      this.name = columnName;
      this.type = columnType;
      this.length = length;
   }

   ColumnDefinition.StaticColumnDefinition self() {
      return this;
   }

   public ColumnDefinition.StaticColumnDefinition setDefault(String expr) {
      this.defaultExpr = expr;
      this.defaultExprWasSet = true;
      return this.self();
   }

   public ColumnDefinition.StaticColumnDefinition autoIncrement() {
      this.autoIncrement = true;
      return this.self();
   }

   public ColumnDefinition.StaticColumnDefinition foreignKey(String tableName, String... foreignColumnName) {
      this.foreignKey.put(tableName, foreignColumnName);
      return this.self();
   }

   public String toString() {
      StringBuilder sb = new StringBuilder(this.name);
      sb.append(" ").append(this.getMysqlType());
      if (this.notNull != null) {
         sb.append(this.notNull ? " NOT NULL" : " NULL");
      }

      if (this.defaultExprWasSet) {
         sb.append(" DEFAULT ").append(this.defaultExpr);
      }

      if (this.autoIncrement) {
         sb.append(" AUTO_INCREMENT");
      }

      if (this.primaryKey) {
         sb.append(" PRIMARY KEY");
      } else if (this.uniqueIndex) {
         sb.append(" UNIQUE KEY");
      }

      if (this.comment != null && !this.comment.isEmpty()) {
         sb.append(" COMMENT ").append(StringUtils.quoteIdentifier(this.comment, "'", true));
      }

      return sb.toString();
   }
}
