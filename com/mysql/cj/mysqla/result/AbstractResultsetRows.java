package com.mysql.cj.mysqla.result;

import com.mysql.cj.api.mysqla.io.ProtocolEntityFactory;
import com.mysql.cj.api.mysqla.result.ColumnDefinition;
import com.mysql.cj.api.mysqla.result.ResultsetRow;
import com.mysql.cj.api.mysqla.result.ResultsetRows;
import com.mysql.cj.api.mysqla.result.ResultsetRowsOwner;

public abstract class AbstractResultsetRows implements ResultsetRows {
   protected static final int BEFORE_START_OF_ROWS = -1;
   protected ColumnDefinition metadata;
   protected int currentPositionInFetchedRows = -1;
   protected boolean wasEmpty = false;
   protected ResultsetRowsOwner owner;
   protected ProtocolEntityFactory<ResultsetRow> rowFactory;

   public void setOwner(ResultsetRowsOwner rs) {
      this.owner = rs;
   }

   public ResultsetRowsOwner getOwner() {
      return this.owner;
   }

   public void setMetadata(ColumnDefinition columnDefinition) {
      this.metadata = columnDefinition;
   }

   public ColumnDefinition getMetadata() {
      return this.metadata;
   }

   public boolean wasEmpty() {
      return this.wasEmpty;
   }
}
