package com.mysql.cj.mysqla.io;

import com.mysql.cj.api.conf.PropertySet;
import com.mysql.cj.api.conf.ReadableProperty;
import com.mysql.cj.api.mysqla.io.PacketHeader;
import com.mysql.cj.api.mysqla.io.PacketPayload;
import com.mysql.cj.api.mysqla.io.ProtocolEntityFactory;
import com.mysql.cj.api.mysqla.io.ProtocolEntityReader;
import com.mysql.cj.api.mysqla.result.ResultsetRow;
import java.io.IOException;
import java.util.Optional;

public class ResultsetRowReader implements ProtocolEntityReader<ResultsetRow> {
   protected MysqlaProtocol protocol;
   protected PropertySet propertySet;
   protected ReadableProperty<Integer> useBufferRowSizeThreshold;

   public ResultsetRowReader(MysqlaProtocol prot) {
      this.protocol = prot;
      this.propertySet = this.protocol.getPropertySet();
      this.useBufferRowSizeThreshold = this.propertySet.getMemorySizeReadableProperty("largeRowSizeThreshold");
   }

   public ResultsetRow read(ProtocolEntityFactory<ResultsetRow> sf) throws IOException {
      AbstractRowFactory rf = (AbstractRowFactory)sf;
      PacketPayload rowPacket = null;
      PacketHeader hdr = this.protocol.getPacketReader().readHeader();
      int packetLength = hdr.getPacketLength();
      rowPacket = this.protocol.getPacketReader().readPayload(rf.canReuseRowPacketForBufferRow() ? Optional.ofNullable(this.protocol.getReusablePacket()) : Optional.empty(), packetLength);
      this.protocol.checkErrorPacket(rowPacket);
      rowPacket.setPosition(rowPacket.getPosition() - 1);
      if ((this.protocol.getServerSession().isEOFDeprecated() || !rowPacket.isEOFPacket()) && (!this.protocol.getServerSession().isEOFDeprecated() || !rowPacket.isResultSetOKPacket())) {
         return (ResultsetRow)sf.createFromPacketPayload(rowPacket);
      } else {
         this.protocol.readServerStatusForResultSets(rowPacket, true);
         return null;
      }
   }
}
