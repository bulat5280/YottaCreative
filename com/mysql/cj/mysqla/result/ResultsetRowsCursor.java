package com.mysql.cj.mysqla.result;

import com.mysql.cj.api.mysqla.io.NativeProtocol;
import com.mysql.cj.api.mysqla.io.PacketPayload;
import com.mysql.cj.api.mysqla.result.ColumnDefinition;
import com.mysql.cj.api.mysqla.result.Resultset;
import com.mysql.cj.api.mysqla.result.ResultsetRow;
import com.mysql.cj.api.mysqla.result.ResultsetRows;
import com.mysql.cj.api.result.Row;
import com.mysql.cj.core.Messages;
import com.mysql.cj.core.exceptions.ExceptionFactory;
import com.mysql.cj.mysqla.io.BinaryRowFactory;
import com.mysql.cj.mysqla.io.MysqlaProtocol;
import java.util.ArrayList;
import java.util.List;

public class ResultsetRowsCursor extends AbstractResultsetRows implements ResultsetRows {
   private List<Row> fetchedRows;
   private int currentPositionInEntireResult = -1;
   private boolean lastRowFetched = false;
   private MysqlaProtocol protocol;
   private boolean firstFetchCompleted = false;

   public ResultsetRowsCursor(MysqlaProtocol ioChannel, ColumnDefinition columnDefinition) {
      this.currentPositionInEntireResult = -1;
      this.metadata = columnDefinition;
      this.protocol = ioChannel;
      this.rowFactory = new BinaryRowFactory(this.protocol, this.metadata, Resultset.Concurrency.READ_ONLY, false);
   }

   public boolean isAfterLast() {
      return this.lastRowFetched && this.currentPositionInFetchedRows > this.fetchedRows.size();
   }

   public boolean isBeforeFirst() {
      return this.currentPositionInEntireResult < 0;
   }

   public int getPosition() {
      return this.currentPositionInEntireResult + 1;
   }

   public boolean isEmpty() {
      return this.isBeforeFirst() && this.isAfterLast();
   }

   public boolean isFirst() {
      return this.currentPositionInEntireResult == 0;
   }

   public boolean isLast() {
      return this.lastRowFetched && this.currentPositionInFetchedRows == this.fetchedRows.size() - 1;
   }

   public void close() {
      this.metadata = null;
      this.owner = null;
   }

   public boolean hasNext() {
      if (this.fetchedRows != null && this.fetchedRows.size() == 0) {
         return false;
      } else {
         if (this.owner != null) {
            int maxRows = this.owner.getOwningStatementMaxRows();
            if (maxRows != -1 && this.currentPositionInEntireResult + 1 > maxRows) {
               return false;
            }
         }

         if (this.currentPositionInEntireResult != -1) {
            if (this.currentPositionInFetchedRows < this.fetchedRows.size() - 1) {
               return true;
            } else if (this.currentPositionInFetchedRows == this.fetchedRows.size() && this.lastRowFetched) {
               return false;
            } else {
               this.fetchMoreRows();
               return this.fetchedRows.size() > 0;
            }
         } else {
            this.fetchMoreRows();
            return this.fetchedRows.size() > 0;
         }
      }
   }

   public Row next() {
      if (this.fetchedRows == null && this.currentPositionInEntireResult != -1) {
         throw ExceptionFactory.createException(Messages.getString("ResultSet.Operation_not_allowed_after_ResultSet_closed_144"), this.protocol.getExceptionInterceptor());
      } else if (!this.hasNext()) {
         return null;
      } else {
         ++this.currentPositionInEntireResult;
         ++this.currentPositionInFetchedRows;
         if (this.fetchedRows != null && this.fetchedRows.size() == 0) {
            return null;
         } else {
            if (this.fetchedRows == null || this.currentPositionInFetchedRows > this.fetchedRows.size() - 1) {
               this.fetchMoreRows();
               this.currentPositionInFetchedRows = 0;
            }

            Row row = (Row)this.fetchedRows.get(this.currentPositionInFetchedRows);
            row.setMetadata(this.metadata);
            return row;
         }
      }
   }

   private void fetchMoreRows() {
      if (this.lastRowFetched) {
         this.fetchedRows = new ArrayList(0);
      } else {
         synchronized(this.owner.getConnection().getConnectionMutex()) {
            try {
               boolean oldFirstFetchCompleted = this.firstFetchCompleted;
               if (!this.firstFetchCompleted) {
                  this.firstFetchCompleted = true;
               }

               int numRowsToFetch = this.owner.getOwnerFetchSize();
               if (numRowsToFetch == 0) {
                  numRowsToFetch = this.owner.getOwningStatementFetchSize();
               }

               if (numRowsToFetch == Integer.MIN_VALUE) {
                  numRowsToFetch = 1;
               }

               if (this.fetchedRows == null) {
                  this.fetchedRows = new ArrayList(numRowsToFetch);
               } else {
                  this.fetchedRows.clear();
               }

               PacketPayload sharedSendPacket = this.protocol.getSharedSendPacket();
               sharedSendPacket.setPosition(0);
               sharedSendPacket.writeInteger(NativeProtocol.IntegerDataType.INT1, 28L);
               sharedSendPacket.writeInteger(NativeProtocol.IntegerDataType.INT4, this.owner.getOwningStatementServerId());
               sharedSendPacket.writeInteger(NativeProtocol.IntegerDataType.INT4, (long)numRowsToFetch);
               this.protocol.sendCommand(28, (String)null, sharedSendPacket, true, (String)null, 0);
               Row row = null;

               while((row = (Row)this.protocol.read(ResultsetRow.class, this.rowFactory)) != null) {
                  this.fetchedRows.add(row);
               }

               this.currentPositionInFetchedRows = -1;
               if (this.protocol.getServerSession().isLastRowSent()) {
                  this.lastRowFetched = true;
                  if (!oldFirstFetchCompleted && this.fetchedRows.size() == 0) {
                     this.wasEmpty = true;
                  }
               }
            } catch (Exception var7) {
               throw ExceptionFactory.createException((String)var7.getMessage(), (Throwable)var7);
            }

         }
      }
   }
}
