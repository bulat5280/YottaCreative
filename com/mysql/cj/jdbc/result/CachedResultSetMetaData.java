package com.mysql.cj.jdbc.result;

import com.mysql.cj.api.mysqla.result.ColumnDefinition;
import com.mysql.cj.mysqla.result.MysqlaColumnDefinition;

public class CachedResultSetMetaData extends MysqlaColumnDefinition implements ColumnDefinition {
   java.sql.ResultSetMetaData metadata;

   public java.sql.ResultSetMetaData getMetadata() {
      return this.metadata;
   }
}
