package com.mysql.cj.api.mysqla.io;

public interface PacketPayload {
   int NO_LENGTH_LIMIT = -1;
   long NULL_LENGTH = -1L;
   short TYPE_ID_ERROR = 255;
   short TYPE_ID_EOF = 254;
   short TYPE_ID_AUTH_SWITCH = 254;
   short TYPE_ID_LOCAL_INFILE = 251;
   short TYPE_ID_OK = 0;

   int getCapacity();

   void ensureCapacity(int var1);

   byte[] getByteBuffer();

   void setByteBuffer(byte[] var1);

   int getPayloadLength();

   void setPayloadLength(int var1);

   int getPosition();

   void setPosition(int var1);

   boolean isEOFPacket();

   boolean isAuthMethodSwitchRequestPacket();

   boolean isOKPacket();

   boolean isResultSetOKPacket();

   boolean isAuthMoreData();

   void writeInteger(NativeProtocol.IntegerDataType var1, long var2);

   long readInteger(NativeProtocol.IntegerDataType var1);

   void writeBytes(NativeProtocol.StringLengthDataType var1, byte[] var2);

   void writeBytes(NativeProtocol.StringSelfDataType var1, byte[] var2);

   void writeBytes(NativeProtocol.StringLengthDataType var1, byte[] var2, int var3, int var4);

   void writeBytes(NativeProtocol.StringSelfDataType var1, byte[] var2, int var3, int var4);

   byte[] readBytes(NativeProtocol.StringSelfDataType var1);

   void skipBytes(NativeProtocol.StringSelfDataType var1);

   byte[] readBytes(NativeProtocol.StringLengthDataType var1, int var2);

   String readString(NativeProtocol.StringSelfDataType var1, String var2);

   String readString(NativeProtocol.StringLengthDataType var1, String var2, int var3);
}
