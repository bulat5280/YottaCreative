package org.jooq.tools.jdbc;

import java.io.Serializable;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import org.jooq.AttachableInternal;
import org.jooq.Configuration;
import org.jooq.Field;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.DSL;
import org.jooq.types.UNumber;

public class MockResultSetMetaData implements ResultSetMetaData, Serializable {
   private static final long serialVersionUID = -6859273409631070434L;
   private final MockResultSet rs;

   public MockResultSetMetaData(MockResultSet rs) {
      this.rs = rs;
   }

   public <T> T unwrap(Class<T> iface) throws SQLException {
      return null;
   }

   public boolean isWrapperFor(Class<?> iface) throws SQLException {
      return false;
   }

   public int getColumnCount() throws SQLException {
      this.rs.checkNotClosed();
      return this.rs.result.fieldsRow().size();
   }

   public boolean isAutoIncrement(int column) throws SQLException {
      this.rs.checkNotClosed();
      return false;
   }

   public boolean isCaseSensitive(int column) throws SQLException {
      this.rs.checkNotClosed();
      return true;
   }

   public boolean isSearchable(int column) throws SQLException {
      this.rs.checkNotClosed();
      return true;
   }

   public boolean isCurrency(int column) throws SQLException {
      this.rs.checkNotClosed();
      return false;
   }

   public int isNullable(int column) throws SQLException {
      this.rs.checkNotClosed();
      return 2;
   }

   public boolean isSigned(int column) throws SQLException {
      this.rs.checkNotClosed();
      Field<?> field = this.rs.result.field(column - 1);
      Class<?> type = field.getType();
      return Number.class.isAssignableFrom(type) && !UNumber.class.isAssignableFrom(type);
   }

   public int getColumnDisplaySize(int column) throws SQLException {
      return 0;
   }

   public String getColumnLabel(int column) throws SQLException {
      return this.getColumnName(column);
   }

   public String getColumnName(int column) throws SQLException {
      this.rs.checkNotClosed();
      return this.rs.result.field(column - 1).getName();
   }

   public String getSchemaName(int column) throws SQLException {
      this.rs.checkNotClosed();
      Field<?> field = this.rs.result.field(column - 1);
      if (field instanceof TableField) {
         Table<?> table = ((TableField)field).getTable();
         if (table != null) {
            Schema schema = table.getSchema();
            if (schema != null) {
               Configuration configuration = ((AttachableInternal)this.rs.result).configuration();
               Schema mapped = null;
               if (configuration != null) {
                  mapped = DSL.using(configuration).map(schema);
               }

               if (mapped != null) {
                  return mapped.getName();
               }

               return schema.getName();
            }
         }
      }

      return "";
   }

   public int getPrecision(int column) throws SQLException {
      this.rs.checkNotClosed();
      return 0;
   }

   public int getScale(int column) throws SQLException {
      this.rs.checkNotClosed();
      return 0;
   }

   public String getTableName(int column) throws SQLException {
      this.rs.checkNotClosed();
      Field<?> field = this.rs.result.field(column - 1);
      if (field instanceof TableField) {
         Table<?> table = ((TableField)field).getTable();
         if (table != null) {
            return table.getName();
         }
      }

      return "";
   }

   public String getCatalogName(int column) throws SQLException {
      this.rs.checkNotClosed();
      return "";
   }

   public int getColumnType(int column) throws SQLException {
      this.rs.checkNotClosed();
      return this.rs.result.field(column - 1).getDataType().getSQLType();
   }

   public String getColumnTypeName(int column) throws SQLException {
      this.rs.checkNotClosed();
      return this.rs.result.field(column - 1).getDataType().getTypeName();
   }

   public boolean isReadOnly(int column) throws SQLException {
      this.rs.checkNotClosed();
      return true;
   }

   public boolean isWritable(int column) throws SQLException {
      this.rs.checkNotClosed();
      return false;
   }

   public boolean isDefinitelyWritable(int column) throws SQLException {
      this.rs.checkNotClosed();
      return false;
   }

   public String getColumnClassName(int column) throws SQLException {
      this.rs.checkNotClosed();
      return this.rs.result.field(column - 1).getType().getName();
   }
}
