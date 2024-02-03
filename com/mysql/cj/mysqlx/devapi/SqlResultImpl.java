package com.mysql.cj.mysqlx.devapi;

import com.mysql.cj.api.x.Column;
import com.mysql.cj.api.x.Row;
import com.mysql.cj.api.x.SqlResult;
import com.mysql.cj.api.x.Warning;
import com.mysql.cj.core.exceptions.WrongArgumentException;
import com.mysql.cj.mysqlx.io.ResultStreamer;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;

public class SqlResultImpl implements SqlResult, ResultStreamer {
   private Supplier<SqlResult> resultStream;
   private SqlResult currentResult;

   public SqlResultImpl(Supplier<SqlResult> resultStream) {
      this.resultStream = resultStream;
      this.currentResult = (SqlResult)resultStream.get();
   }

   private SqlResult getCurrentResult() {
      if (this.currentResult == null) {
         throw new WrongArgumentException("No active result");
      } else {
         return this.currentResult;
      }
   }

   public boolean nextResult() {
      if (this.currentResult == null) {
         return false;
      } else {
         try {
            if (ResultStreamer.class.isAssignableFrom(this.currentResult.getClass())) {
               ((ResultStreamer)this.currentResult).finishStreaming();
            }
         } finally {
            this.currentResult = null;
         }

         this.currentResult = (SqlResult)this.resultStream.get();
         return this.currentResult != null;
      }
   }

   public void finishStreaming() {
      while(this.nextResult()) {
      }

   }

   public boolean hasData() {
      return this.getCurrentResult().hasData();
   }

   public long getAffectedItemsCount() {
      return this.getCurrentResult().getAffectedItemsCount();
   }

   public Long getAutoIncrementValue() {
      return this.getCurrentResult().getAutoIncrementValue();
   }

   public List<String> getLastDocumentIds() {
      return this.getCurrentResult().getLastDocumentIds();
   }

   public int getWarningsCount() {
      return this.getCurrentResult().getWarningsCount();
   }

   public Iterator<Warning> getWarnings() {
      return this.getCurrentResult().getWarnings();
   }

   public int getColumnCount() {
      return this.getCurrentResult().getColumnCount();
   }

   public List<Column> getColumns() {
      return this.getCurrentResult().getColumns();
   }

   public List<String> getColumnNames() {
      return this.getCurrentResult().getColumnNames();
   }

   public long count() {
      return this.getCurrentResult().count();
   }

   public List<Row> fetchAll() {
      return this.getCurrentResult().fetchAll();
   }

   public Row next() {
      return (Row)this.getCurrentResult().next();
   }

   public boolean hasNext() {
      return this.getCurrentResult().hasNext();
   }
}
