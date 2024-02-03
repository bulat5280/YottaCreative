package com.mysql.cj.mysqla.result;

import com.mysql.cj.api.MysqlConnection;
import com.mysql.cj.api.ProfilerEventHandler;
import com.mysql.cj.api.exceptions.ExceptionInterceptor;
import com.mysql.cj.api.exceptions.StreamingNotifiable;
import com.mysql.cj.api.io.Protocol;
import com.mysql.cj.api.mysqla.io.PacketPayload;
import com.mysql.cj.api.mysqla.io.ProtocolEntityFactory;
import com.mysql.cj.api.mysqla.result.ColumnDefinition;
import com.mysql.cj.api.mysqla.result.ProtocolEntity;
import com.mysql.cj.api.mysqla.result.Resultset;
import com.mysql.cj.api.mysqla.result.ResultsetRow;
import com.mysql.cj.api.mysqla.result.ResultsetRows;
import com.mysql.cj.api.result.Row;
import com.mysql.cj.core.Constants;
import com.mysql.cj.core.Messages;
import com.mysql.cj.core.exceptions.CJException;
import com.mysql.cj.core.exceptions.ExceptionFactory;
import com.mysql.cj.core.profiler.ProfilerEventHandlerFactory;
import com.mysql.cj.core.profiler.ProfilerEventImpl;
import com.mysql.cj.core.util.Util;
import com.mysql.cj.jdbc.StatementImpl;
import com.mysql.cj.mysqla.io.BinaryRowFactory;
import com.mysql.cj.mysqla.io.MysqlaProtocol;
import com.mysql.cj.mysqla.io.TextRowFactory;

public class ResultsetRowsStreaming<T extends ProtocolEntity> extends AbstractResultsetRows implements ResultsetRows {
   private MysqlaProtocol protocol;
   private boolean isAfterEnd = false;
   private boolean noMoreRows = false;
   private boolean isBinaryEncoded = false;
   private Row nextRow;
   private boolean streamerClosed = false;
   private ExceptionInterceptor exceptionInterceptor;
   private ProtocolEntityFactory<T> resultSetFactory;

   public ResultsetRowsStreaming(MysqlaProtocol io, ColumnDefinition columnDefinition, boolean isBinaryEncoded, ProtocolEntityFactory<T> resultSetFactory) {
      this.protocol = io;
      this.isBinaryEncoded = isBinaryEncoded;
      this.metadata = columnDefinition;
      this.exceptionInterceptor = this.protocol.getExceptionInterceptor();
      this.resultSetFactory = resultSetFactory;
      this.rowFactory = (ProtocolEntityFactory)(this.isBinaryEncoded ? new BinaryRowFactory(this.protocol, this.metadata, Resultset.Concurrency.READ_ONLY, true) : new TextRowFactory(this.protocol, this.metadata, Resultset.Concurrency.READ_ONLY, true));
   }

   public void close() {
      Object mutex = this;
      MysqlConnection conn = null;
      if (this.owner != null) {
         conn = this.owner.getConnection();
         if (conn != null) {
            mutex = conn.getConnectionMutex();
         }
      }

      boolean hadMore = false;
      int howMuchMore = 0;
      synchronized(mutex) {
         while(this.next() != null) {
            hadMore = true;
            ++howMuchMore;
            if (howMuchMore % 100 == 0) {
               Thread.yield();
            }
         }

         if (conn != null) {
            if (!(Boolean)this.protocol.getPropertySet().getBooleanReadableProperty("clobberStreamingResults").getValue() && (Integer)this.protocol.getPropertySet().getIntegerReadableProperty("netTimeoutForStreamingResults").getValue() > 0) {
               int oldValue = this.protocol.getServerSession().getServerVariable("net_write_timeout", 60);
               this.protocol.clearInputStream();

               try {
                  this.protocol.sqlQueryDirect((StatementImpl)null, "SET net_write_timeout=" + oldValue, (String)this.protocol.getPropertySet().getStringReadableProperty("characterEncoding").getValue(), (PacketPayload)null, -1, false, (String)null, (ColumnDefinition)null, (Protocol.GetProfilerEventHandlerInstanceFunction)null, this.resultSetFactory);
               } catch (Exception var9) {
                  throw ExceptionFactory.createException((String)var9.getMessage(), (Throwable)var9, (ExceptionInterceptor)this.exceptionInterceptor);
               }
            }

            if ((Boolean)this.protocol.getPropertySet().getBooleanReadableProperty("useUsageAdvisor").getValue() && hadMore) {
               ProfilerEventHandler eventSink = ProfilerEventHandlerFactory.getInstance(conn.getSession());
               eventSink.consumeEvent(new ProfilerEventImpl((byte)0, "", this.owner.getCurrentCatalog(), this.owner.getConnectionId(), this.owner.getOwningStatementId(), -1, System.currentTimeMillis(), 0L, Constants.MILLIS_I18N, (String)null, (String)null, Messages.getString("RowDataDynamic.2") + howMuchMore + Messages.getString("RowDataDynamic.3") + Messages.getString("RowDataDynamic.4") + Messages.getString("RowDataDynamic.5") + Messages.getString("RowDataDynamic.6") + this.owner.getPointOfOrigin()));
            }
         }
      }

      this.metadata = null;
      this.owner = null;
   }

   public boolean hasNext() {
      boolean hasNext = this.nextRow != null;
      if (!hasNext && !this.streamerClosed) {
         this.protocol.closeStreamer(this);
         this.streamerClosed = true;
      }

      return hasNext;
   }

   public boolean isAfterLast() {
      return this.isAfterEnd;
   }

   public boolean isBeforeFirst() {
      return this.currentPositionInFetchedRows < 0;
   }

   public Row next() {
      try {
         if (!this.noMoreRows) {
            this.nextRow = (Row)this.protocol.read(ResultsetRow.class, this.rowFactory);
            if (this.nextRow == null) {
               this.noMoreRows = true;
               this.isAfterEnd = true;
               if (this.currentPositionInFetchedRows == -1) {
                  this.wasEmpty = true;
               }
            }
         } else {
            this.nextRow = null;
            this.isAfterEnd = true;
         }

         if (this.nextRow == null && !this.streamerClosed) {
            if (this.protocol.getServerSession().hasMoreResults()) {
               this.protocol.readNextResultset((ProtocolEntity)this.owner, this.owner.getOwningStatementMaxRows(), true, this.isBinaryEncoded, this.resultSetFactory);
            } else {
               this.protocol.closeStreamer(this);
               this.streamerClosed = true;
            }
         }

         if (this.nextRow != null && this.currentPositionInFetchedRows != Integer.MAX_VALUE) {
            ++this.currentPositionInFetchedRows;
         }

         return this.nextRow;
      } catch (CJException var5) {
         if (var5 instanceof StreamingNotifiable) {
            ((StreamingNotifiable)var5).setWasStreamingResults();
         }

         this.noMoreRows = true;
         throw var5;
      } catch (Exception var6) {
         String exceptionType = var6.getClass().getName();
         String exceptionMessage = var6.getMessage();
         exceptionMessage = exceptionMessage + Messages.getString("RowDataDynamic.7");
         exceptionMessage = exceptionMessage + Util.stackTraceToString(var6);
         CJException cjEx = ExceptionFactory.createException((String)(Messages.getString("RowDataDynamic.8") + exceptionType + Messages.getString("RowDataDynamic.9") + exceptionMessage), (Throwable)var6, (ExceptionInterceptor)this.exceptionInterceptor);
         throw cjEx;
      }
   }
}
