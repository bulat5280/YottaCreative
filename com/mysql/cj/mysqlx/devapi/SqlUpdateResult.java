package com.mysql.cj.mysqlx.devapi;

import com.mysql.cj.api.x.Column;
import com.mysql.cj.api.x.Row;
import com.mysql.cj.api.x.SqlResult;
import com.mysql.cj.core.exceptions.FeatureNotAvailableException;
import com.mysql.cj.core.io.StatementExecuteOk;
import java.util.List;

public class SqlUpdateResult extends UpdateResult implements SqlResult {
   public SqlUpdateResult(StatementExecuteOk ok) {
      super(ok, (List)null);
   }

   public boolean hasData() {
      return false;
   }

   public boolean nextResult() {
      throw new FeatureNotAvailableException("Not a multi-result");
   }

   public List<String> getLastDocumentIds() {
      throw new FeatureNotAvailableException("Document IDs are not assigned for SQL statements");
   }

   public List<Row> fetchAll() {
      throw new FeatureNotAvailableException("No data");
   }

   public Row next() {
      throw new FeatureNotAvailableException("No data");
   }

   public boolean hasNext() {
      throw new FeatureNotAvailableException("No data");
   }

   public int getColumnCount() {
      throw new FeatureNotAvailableException("No data");
   }

   public List<Column> getColumns() {
      throw new FeatureNotAvailableException("No data");
   }

   public List<String> getColumnNames() {
      throw new FeatureNotAvailableException("No data");
   }

   public long count() {
      throw new FeatureNotAvailableException("No data");
   }
}
