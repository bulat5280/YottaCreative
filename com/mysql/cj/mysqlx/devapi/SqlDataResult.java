package com.mysql.cj.mysqlx.devapi;

import com.mysql.cj.api.result.RowList;
import com.mysql.cj.api.x.SqlResult;
import com.mysql.cj.core.exceptions.FeatureNotAvailableException;
import com.mysql.cj.core.io.StatementExecuteOk;
import com.mysql.cj.core.result.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;
import java.util.function.Supplier;

public class SqlDataResult extends RowResultImpl implements SqlResult {
   public SqlDataResult(ArrayList<Field> metadata, TimeZone defaultTimeZone, RowList rows, Supplier<StatementExecuteOk> completer) {
      super(metadata, defaultTimeZone, rows, completer);
   }

   public boolean nextResult() {
      throw new FeatureNotAvailableException("Not a multi-result");
   }

   public long getAffectedItemsCount() {
      return this.getStatementExecuteOk().getRowsAffected();
   }

   public Long getAutoIncrementValue() {
      return this.getStatementExecuteOk().getLastInsertId();
   }

   public List<String> getLastDocumentIds() {
      throw new FeatureNotAvailableException("Document IDs are not assigned for SQL statements");
   }
}
