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
import com.mysql.cj.mysqla.result.ResultsetRowsStatic;
import com.mysql.cj.mysqla.result.ResultsetRowsStreaming;
import java.io.IOException;
import java.util.ArrayList;

public class TextResultsetReader implements ProtocolEntityReader<Resultset> {
   protected MysqlaProtocol protocol;

   public TextResultsetReader(MysqlaProtocol prot) {
      this.protocol = prot;
   }

   public Resultset read(int maxRows, boolean streamResults, PacketPayload resultPacket, ColumnDefinition metadata, ProtocolEntityFactory<Resultset> resultSetFactory) throws IOException {
      Resultset rs = null;
      long columnCount = resultPacket.readInteger(NativeProtocol.IntegerDataType.INT_LENENC);
      if (columnCount > 0L) {
         ColumnDefinition cdef = (ColumnDefinition)this.protocol.read(ColumnDefinition.class, new ColumnDefinitionFactory(columnCount, metadata));
         if (!this.protocol.getServerSession().isEOFDeprecated()) {
            this.protocol.skipPacket();
         }

         ResultsetRows rows = null;
         if (streamResults) {
            rows = new ResultsetRowsStreaming(this.protocol, cdef, false, resultSetFactory);
            this.protocol.setStreamingData((ResultsetRows)rows);
         } else {
            TextRowFactory trf = new TextRowFactory(this.protocol, cdef, resultSetFactory.getResultSetConcurrency(), false);
            ArrayList<ResultsetRow> rowList = new ArrayList();
            ResultsetRow row = (ResultsetRow)this.protocol.read(ResultsetRow.class, trf);

            while(true) {
               if (row == null) {
                  rows = new ResultsetRowsStatic(rowList, cdef);
                  break;
               }

               if (maxRows == -1 || rowList.size() < maxRows) {
                  rowList.add(row);
               }

               row = (ResultsetRow)this.protocol.read(ResultsetRow.class, trf);
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
