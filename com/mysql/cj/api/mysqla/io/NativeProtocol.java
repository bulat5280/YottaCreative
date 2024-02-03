package com.mysql.cj.api.mysqla.io;

import com.mysql.cj.api.io.Protocol;
import com.mysql.cj.api.mysqla.result.ColumnDefinition;
import com.mysql.cj.api.mysqla.result.ProtocolEntity;
import com.mysql.cj.api.mysqla.result.Resultset;
import java.io.IOException;
import java.io.InputStream;

public interface NativeProtocol extends Protocol {
   void rejectProtocol(PacketPayload var1);

   PacketReader getPacketReader();

   PacketPayload readPacket(PacketPayload var1);

   PacketPayload checkErrorPacket();

   void send(PacketPayload var1, int var2);

   PacketPayload sendCommand(int var1, String var2, PacketPayload var3, boolean var4, String var5, int var6);

   <T extends ProtocolEntity> T read(Class<T> var1, ProtocolEntityFactory<T> var2) throws IOException;

   <T extends ProtocolEntity> T read(Class<Resultset> var1, int var2, boolean var3, PacketPayload var4, boolean var5, ColumnDefinition var6, ProtocolEntityFactory<T> var7) throws IOException;

   void setLocalInfileInputStream(InputStream var1);

   InputStream getLocalInfileInputStream();

   public static enum StringSelfDataType {
      STRING_TERM,
      STRING_LENENC,
      STRING_EOF;
   }

   public static enum StringLengthDataType {
      STRING_FIXED,
      STRING_VAR;
   }

   public static enum IntegerDataType {
      INT1,
      INT2,
      INT3,
      INT4,
      INT6,
      INT8,
      INT_LENENC;
   }
}
