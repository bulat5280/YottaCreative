package com.mysql.cj.mysqla.result;

import com.mysql.cj.api.mysqla.result.ColumnDefinition;
import com.mysql.cj.api.mysqla.result.Resultset;
import com.mysql.cj.api.mysqla.result.ResultsetRows;
import com.mysql.cj.api.result.Row;
import com.mysql.cj.core.result.Field;
import java.util.HashMap;

public class MysqlaResultset implements Resultset {
   protected ColumnDefinition columnDefinition;
   protected ResultsetRows rowData;
   protected Resultset nextResultset = null;
   protected int resultId;
   protected long updateCount;
   protected long updateId = -1L;
   protected String serverInfo = null;
   protected Row thisRow = null;

   public MysqlaResultset() {
   }

   public MysqlaResultset(OkPacket ok) {
      this.updateCount = ok.getUpdateCount();
      this.updateId = ok.getUpdateID();
      this.serverInfo = ok.getInfo();
      this.columnDefinition = new MysqlaColumnDefinition(new Field[0]);
   }

   public MysqlaResultset(ResultsetRows rows) {
      this.columnDefinition = rows.getMetadata();
      this.rowData = rows;
      this.updateCount = (long)this.rowData.size();
      if (this.rowData.size() > 0) {
         if (this.updateCount == 1L && this.thisRow == null) {
            this.rowData.close();
            this.updateCount = -1L;
         }
      } else {
         this.thisRow = null;
      }

   }

   public void setColumnDefinition(ColumnDefinition metadata) {
      this.columnDefinition = metadata;
   }

   public ColumnDefinition getColumnDefinition() {
      return this.columnDefinition;
   }

   public boolean hasRows() {
      return this.rowData != null;
   }

   public int getResultId() {
      return this.resultId;
   }

   public void initRowsWithMetadata() {
      this.rowData.setMetadata(this.columnDefinition);
      this.columnDefinition.setColumnToIndexCache(new HashMap());
   }

   public synchronized void setNextResultset(Resultset nextResultset) {
      this.nextResultset = nextResultset;
   }

   public synchronized Resultset getNextResultset() {
      return this.nextResultset;
   }

   public synchronized void clearNextResultset() {
      this.nextResultset = null;
   }

   public long getUpdateCount() {
      return this.updateCount;
   }

   public long getUpdateID() {
      return this.updateId;
   }

   public String getServerInfo() {
      return this.serverInfo;
   }
}
