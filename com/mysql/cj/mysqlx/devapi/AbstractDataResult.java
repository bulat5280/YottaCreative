package com.mysql.cj.mysqlx.devapi;

import com.mysql.cj.api.result.Row;
import com.mysql.cj.api.result.RowList;
import com.mysql.cj.api.x.Warning;
import com.mysql.cj.core.exceptions.WrongArgumentException;
import com.mysql.cj.core.io.StatementExecuteOk;
import com.mysql.cj.core.result.BufferedRowList;
import com.mysql.cj.mysqlx.io.ResultStreamer;
import com.mysql.cj.mysqlx.result.RowToElement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Supplier;

public abstract class AbstractDataResult<T> implements ResultStreamer, Iterator<T> {
   protected int position = -1;
   protected int count = -1;
   protected RowList rows;
   protected Supplier<StatementExecuteOk> completer;
   protected StatementExecuteOk ok;
   protected RowToElement<T> rowToData;
   protected List<T> all;

   public AbstractDataResult(RowList rows, Supplier<StatementExecuteOk> completer, RowToElement<T> rowToData) {
      this.rows = rows;
      this.completer = completer;
      this.rowToData = rowToData;
   }

   public T next() {
      if (this.all != null) {
         throw new WrongArgumentException("Cannot iterate after fetchAll()");
      } else {
         Row r = (Row)this.rows.next();
         if (r == null) {
            throw new NoSuchElementException();
         } else {
            ++this.position;
            return this.rowToData.apply(r);
         }
      }
   }

   public List<T> fetchAll() {
      if (this.position > -1) {
         throw new WrongArgumentException("Cannot fetchAll() after starting iteration");
      } else {
         if (this.all == null) {
            this.all = new ArrayList((int)this.count());
            this.rows.forEachRemaining((r) -> {
               this.all.add(this.rowToData.apply(r));
            });
            this.all = Collections.unmodifiableList(this.all);
         }

         return this.all;
      }
   }

   public long count() {
      this.finishStreaming();
      return (long)this.count;
   }

   public boolean hasNext() {
      return this.rows.hasNext();
   }

   public StatementExecuteOk getStatementExecuteOk() {
      this.finishStreaming();
      return this.ok;
   }

   public void finishStreaming() {
      if (this.ok == null) {
         BufferedRowList remainingRows = new BufferedRowList(this.rows);
         this.count = 1 + this.position + remainingRows.size();
         this.rows = remainingRows;
         this.ok = (StatementExecuteOk)this.completer.get();
      }

   }

   public int getWarningsCount() {
      return this.getStatementExecuteOk().getWarnings().size();
   }

   public Iterator<Warning> getWarnings() {
      return this.getStatementExecuteOk().getWarnings().iterator();
   }
}
