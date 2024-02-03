package com.mysql.cj.mysqla.io;

import com.mysql.cj.api.mysqla.io.NativeProtocol;
import com.mysql.cj.api.mysqla.io.PacketPayload;
import com.mysql.cj.api.mysqla.io.ProtocolEntityFactory;
import com.mysql.cj.api.mysqla.io.ProtocolEntityReader;
import com.mysql.cj.api.mysqla.result.ColumnDefinition;
import com.mysql.cj.api.mysqla.result.ProtocolEntity;
import com.mysql.cj.api.mysqla.result.Resultset;
import com.mysql.cj.api.mysqla.result.ResultsetRow;
import com.mysql.cj.api.mysqla.result.ResultsetRows;
import com.mysql.cj.mysqla.result.OkPacket;
import com.mysql.cj.mysqla.result.ResultsetRowsCursor;
import com.mysql.cj.mysqla.result.ResultsetRowsStatic;
import com.mysql.cj.mysqla.result.ResultsetRowsStreaming;
import java.io.IOException;
import java.util.ArrayList;

public class BinaryResultsetReader implements ProtocolEntityReader<Resultset> {
   protected MysqlaProtocol protocol;

   public BinaryResultsetReader(MysqlaProtocol prot) {
      this.protocol = prot;
   }

   public Resultset read(int maxRows, boolean streamResults, PacketPayload resultPacket, ColumnDefinition metadata, ProtocolEntityFactory<Resultset> resultSetFactory) throws IOException {
      Resultset rs = null;
      long columnCount = resultPacket.readInteger(NativeProtocol.IntegerDataType.INT_LENENC);
      if (columnCount > 0L) {
         ColumnDefinition cdef = (ColumnDefinition)this.protocol.read(ColumnDefinition.class, new ColumnDefinitionFactory(columnCount, metadata));
         boolean isCursorPosible = (Boolean)this.protocol.getPropertySet().getBooleanReadableProperty("useCursorFetch").getValue() && resultSetFactory.getResultSetType() == Resultset.Type.FORWARD_ONLY;
         if (!this.protocol.getServerSession().isEOFDeprecated() || isCursorPosible) {
            this.protocol.readServerStatusForResultSets(this.protocol.readPacket(this.protocol.getReusablePacket()), true);
         }

         ResultsetRows rows = null;
         if (isCursorPosible) {
            if (this.protocol.getServerSession().cursorExists()) {
               rows = new ResultsetRowsCursor(this.protocol, cdef);
            }
         } else if (streamResults) {
            rows = new ResultsetRowsStreaming(this.protocol, cdef, true, resultSetFactory);
            this.protocol.setStreamingData((ResultsetRows)rows);
         } else {
            BinaryRowFactory brf = new BinaryRowFactory(this.protocol, cdef, resultSetFactory.getResultSetConcurrency(), false);
            ArrayList<ResultsetRow> rowList = new ArrayList();
            ResultsetRow row = (ResultsetRow)this.protocol.read(ResultsetRow.class, brf);

            while(true) {
               if (row == null) {
                  rows = new ResultsetRowsStatic(rowList, cdef);
                  break;
               }

               if (maxRows == -1 || rowList.size() < maxRows) {
                  rowList.add(row);
               }

               row = (ResultsetRow)this.protocol.read(ResultsetRow.class, brf);
            }
         }

         rs = (Resultset)resultSetFactory.createFromProtocolEntity((ProtocolEntity)rows);
      } else {
         if (columnCount == -1L) {
            String charEncoding = (String)this.protocol.getPropertySet().getStringReadableProperty("characterEncoding").getValue();
            String fileName = resultPacket.readString(NativeProtocol.StringSelfDataType.STRING_TERM, this.protocol.doesPlatformDbCharsetMatches() ? charEncoding : null);
            resultPacket = this.protocol.sendFileToServer(fileName);
         }

         OkPacket ok = (OkPacket)this.protocol.readServerStatusForResultSets(resultPacket, false);
         rs = (Resultset)resultSetFactory.createFromProtocolEntity(ok);
      }

      return rs;
   }
}
