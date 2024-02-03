package com.mysql.cj.mysqla.io;

import com.mysql.cj.api.mysqla.io.NativeProtocol;
import com.mysql.cj.api.mysqla.io.PacketPayload;
import com.mysql.cj.api.mysqla.io.ProtocolEntityFactory;
import com.mysql.cj.api.mysqla.io.ProtocolEntityReader;
import com.mysql.cj.api.mysqla.result.ColumnDefinition;
import com.mysql.cj.core.MysqlType;
import com.mysql.cj.core.result.Field;
import com.mysql.cj.core.util.LazyString;
import com.mysql.cj.mysqla.result.MysqlaColumnDefinition;

public class ColumnDefinitionReader implements ProtocolEntityReader<ColumnDefinition> {
   private MysqlaProtocol protocol;

   public ColumnDefinitionReader(MysqlaProtocol prot) {
      this.protocol = prot;
   }

   public ColumnDefinition read(ProtocolEntityFactory<ColumnDefinition> sf) {
      ColumnDefinitionFactory cdf = (ColumnDefinitionFactory)sf;
      long columnCount = cdf.getColumnCount();
      ColumnDefinition cdef = cdf.getColumnDefinitionFromCache();
      if (cdef != null) {
         for(int i = 0; (long)i < columnCount; ++i) {
            this.protocol.skipPacket();
         }

         return cdef;
      } else {
         Field[] fields = null;
         boolean checkEOF = !this.protocol.getServerSession().isEOFDeprecated();
         fields = new Field[(int)columnCount];

         for(int i = 0; (long)i < columnCount; ++i) {
            PacketPayload fieldPacket = this.protocol.readPacket((PacketPayload)null);
            if (checkEOF && fieldPacket.isEOFPacket()) {
               break;
            }

            fields[i] = this.unpackField(fieldPacket, this.protocol.getServerSession().getCharacterSetMetadata());
         }

         return new MysqlaColumnDefinition(fields);
      }
   }

   protected Field unpackField(PacketPayload packet, String characterSetMetadata) {
      int length = (int)packet.readInteger(NativeProtocol.IntegerDataType.INT_LENENC);
      packet.setPosition(packet.getPosition() + length);
      length = (int)packet.readInteger(NativeProtocol.IntegerDataType.INT_LENENC);
      int offset = packet.getPosition();
      LazyString databaseName = new LazyString(packet.getByteBuffer(), offset, length, characterSetMetadata);
      packet.setPosition(packet.getPosition() + length);
      length = (int)packet.readInteger(NativeProtocol.IntegerDataType.INT_LENENC);
      offset = packet.getPosition();
      LazyString tableName = new LazyString(packet.getByteBuffer(), offset, length, characterSetMetadata);
      packet.setPosition(packet.getPosition() + length);
      length = (int)packet.readInteger(NativeProtocol.IntegerDataType.INT_LENENC);
      offset = packet.getPosition();
      LazyString originalTableName = new LazyString(packet.getByteBuffer(), offset, length, characterSetMetadata);
      packet.setPosition(packet.getPosition() + length);
      length = (int)packet.readInteger(NativeProtocol.IntegerDataType.INT_LENENC);
      offset = packet.getPosition();
      LazyString columnName = new LazyString(packet.getByteBuffer(), offset, length, characterSetMetadata);
      packet.setPosition(packet.getPosition() + length);
      length = (int)packet.readInteger(NativeProtocol.IntegerDataType.INT_LENENC);
      offset = packet.getPosition();
      LazyString originalColumnName = new LazyString(packet.getByteBuffer(), offset, length, characterSetMetadata);
      packet.setPosition(packet.getPosition() + length);
      packet.readInteger(NativeProtocol.IntegerDataType.INT1);
      short collationIndex = (short)((int)packet.readInteger(NativeProtocol.IntegerDataType.INT2));
      long colLength = packet.readInteger(NativeProtocol.IntegerDataType.INT4);
      int colType = (int)packet.readInteger(NativeProtocol.IntegerDataType.INT1);
      short colFlag = (short)((int)packet.readInteger(this.protocol.getServerSession().hasLongColumnInfo() ? NativeProtocol.IntegerDataType.INT2 : NativeProtocol.IntegerDataType.INT1));
      int colDecimals = (int)packet.readInteger(NativeProtocol.IntegerDataType.INT1);
      String encoding = this.protocol.getServerSession().getEncodingForIndex(collationIndex);
      MysqlType mysqlType = MysqlaProtocol.findMysqlType(this.protocol.getPropertySet(), colType, colFlag, colLength, tableName, originalTableName, collationIndex, encoding);
      return new Field(databaseName, tableName, originalTableName, columnName, originalColumnName, colLength, colType, colFlag, colDecimals, collationIndex, encoding, mysqlType);
   }
}
