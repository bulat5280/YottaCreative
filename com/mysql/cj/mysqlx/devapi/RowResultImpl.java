package com.mysql.cj.mysqlx.devapi;

import com.mysql.cj.api.result.RowList;
import com.mysql.cj.api.x.Column;
import com.mysql.cj.api.x.Row;
import com.mysql.cj.api.x.RowResult;
import com.mysql.cj.core.io.StatementExecuteOk;
import com.mysql.cj.core.result.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class RowResultImpl extends AbstractDataResult<Row> implements RowResult {
   private ArrayList<Field> metadata;
   protected TimeZone defaultTimeZone;

   public RowResultImpl(ArrayList<Field> metadata, TimeZone defaultTimeZone, RowList rows, Supplier<StatementExecuteOk> completer) {
      super(rows, completer, new DevapiRowFactory(metadata, defaultTimeZone));
      this.metadata = metadata;
      this.defaultTimeZone = defaultTimeZone;
   }

   public int getColumnCount() {
      return this.metadata.size();
   }

   public List<Column> getColumns() {
      return (List)this.metadata.stream().map(ColumnImpl::new).collect(Collectors.toList());
   }

   public List<String> getColumnNames() {
      return (List)this.metadata.stream().map(Field::getColumnLabel).collect(Collectors.toList());
   }
}
