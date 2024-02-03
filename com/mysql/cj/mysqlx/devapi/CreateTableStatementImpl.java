package com.mysql.cj.mysqlx.devapi;

import com.mysql.cj.api.x.ColumnDefinition;
import com.mysql.cj.api.x.CreateTableStatement;
import com.mysql.cj.api.x.ForeignKeyDefinition;
import com.mysql.cj.api.x.Schema;
import com.mysql.cj.api.x.SelectStatement;
import com.mysql.cj.api.x.Table;
import com.mysql.cj.core.exceptions.FeatureNotAvailableException;
import com.mysql.cj.mysqlx.MysqlxError;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CreateTableStatementImpl implements CreateTableStatement.CreateTableSplitStatement, CreateTableStatement.CreateTableFullStatement, CreateTableStatement.CreateTableLikeStatement {
   private Schema schema;
   private String table;
   private String likeTable;
   private boolean reuseExistingObject = false;
   private List<ColumnDefinition<?>> columns = new ArrayList();
   private List<String> primaryKeys = new ArrayList();
   private Map<String, String[]> indexes = new HashMap();
   private Map<String, String[]> uniqueIndexes = new HashMap();
   private Map<String, ForeignKeyDefinition> foreignKeys = new HashMap();
   private Number initialAutoIncrement;
   private String charset;
   private String collation;
   private String comment;
   private boolean temporary = false;
   private String as;

   public CreateTableStatementImpl(Schema sch, String tableName) {
      this.schema = sch;
      this.table = tableName;
   }

   public CreateTableStatementImpl(Schema sch, String tableName, boolean reuseExistingObject) {
      this.schema = sch;
      this.table = tableName;
      this.reuseExistingObject = reuseExistingObject;
   }

   public CreateTableStatement.CreateTableLikeStatement like(String templateTableName) {
      this.likeTable = templateTableName;
      return this;
   }

   public CreateTableStatement.CreateTableFullStatement addColumn(ColumnDefinition<?> colDef) {
      this.columns.add(colDef);
      return this;
   }

   public CreateTableStatement.CreateTableFullStatement addPrimaryKey(String... pk) {
      this.primaryKeys.addAll(Arrays.asList(pk));
      return this;
   }

   public CreateTableStatement.CreateTableFullStatement addIndex(String name, String... column) {
      this.indexes.put(name, column);
      return this;
   }

   public CreateTableStatement.CreateTableFullStatement addUniqueIndex(String name, String... column) {
      this.uniqueIndexes.put(name, column);
      return this;
   }

   public CreateTableStatement.CreateTableFullStatement addForeignKey(String fkName, ForeignKeyDefinition fkSpec) {
      this.foreignKeys.put(fkName, fkSpec.setName(fkName));
      return this;
   }

   public CreateTableStatement.CreateTableFullStatement setInitialAutoIncrement(Number val) {
      this.initialAutoIncrement = val;
      return this;
   }

   public CreateTableStatement.CreateTableFullStatement setDefaultCharset(String charsetName) {
      this.charset = charsetName;
      return this;
   }

   public CreateTableStatement.CreateTableFullStatement setDefaultCollation(String collationName) {
      this.collation = collationName;
      return this;
   }

   public CreateTableStatement.CreateTableFullStatement setComment(String cmt) {
      this.comment = cmt;
      return this;
   }

   public CreateTableStatement.CreateTableFullStatement temporary() {
      this.temporary = true;
      return this;
   }

   public CreateTableStatement.CreateTableFullStatement as(String select) {
      this.as = select;
      return this;
   }

   public CreateTableStatement.CreateTableFullStatement as(SelectStatement select) {
      throw new FeatureNotAvailableException("Not supported");
   }

   public Table execute() {
      try {
         this.schema.getSession().getMysqlxSession().executeSql(this.toString(), (Object)null);
      } catch (MysqlxError var2) {
         if (var2.getErrorCode() != 1050 || !this.reuseExistingObject) {
            throw var2;
         }
      }

      return this.schema.getTable(this.table);
   }

   public String toString() {
      StringBuilder sb = new StringBuilder("CREATE");
      if (this.temporary) {
         sb.append(" TEMPORARY");
      }

      sb.append(" TABLE");
      if (this.reuseExistingObject) {
         sb.append(" IF NOT EXISTS");
      }

      sb.append(" ").append(this.table);
      if (this.likeTable != null) {
         sb.append(" LIKE ").append(this.likeTable);
         return sb.toString();
      } else {
         if (this.columns.size() > 0) {
            sb.append((String)this.columns.stream().map((ix) -> {
               return ix.toString();
            }).collect(Collectors.joining(",\n ", " (\n ", "")));
            if (this.primaryKeys.size() > 0) {
               sb.append((String)this.primaryKeys.stream().collect(Collectors.joining(", ", ",\n PRIMARY KEY (", ")")));
            }

            String[] keys = (String[])this.indexes.keySet().toArray(new String[0]);

            int i;
            for(i = 0; i < keys.length; ++i) {
               sb.append(",\n INDEX ").append(keys[i]);
               sb.append((String)Arrays.stream((Object[])this.indexes.get(keys[i])).collect(Collectors.joining(", ", " (", ")")));
            }

            keys = (String[])this.uniqueIndexes.keySet().toArray(new String[0]);

            for(i = 0; i < keys.length; ++i) {
               sb.append(",\n UNIQUE INDEX ").append(keys[i]);
               sb.append((String)Arrays.stream((Object[])this.uniqueIndexes.get(keys[i])).collect(Collectors.joining(", ", " (", ")")));
            }

            if (this.foreignKeys.size() > 0) {
               sb.append((String)this.foreignKeys.values().stream().map((ix) -> {
                  return ix.toString();
               }).collect(Collectors.joining(",\n ", ",\n ", "")));
            }

            sb.append(")");
         }

         if (this.initialAutoIncrement != null) {
            sb.append("\n AUTO_INCREMENT = ").append(this.initialAutoIncrement.longValue());
         }

         if (this.charset != null && !this.charset.isEmpty()) {
            sb.append("\n DEFAULT CHARACTER SET = ").append(this.charset);
         }

         if (this.collation != null && !this.collation.isEmpty()) {
            sb.append("\n DEFAULT COLLATE = ").append(this.collation);
         }

         if (this.comment != null && !this.comment.isEmpty()) {
            sb.append("\n COMMENT '").append(this.comment).append("'");
         }

         if (this.as != null && !this.as.isEmpty()) {
            sb.append("\n AS ").append(this.as);
         }

         return sb.toString();
      }
   }
}
