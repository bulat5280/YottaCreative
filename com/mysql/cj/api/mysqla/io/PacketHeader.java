package com.mysql.cj.api.mysqla.io;

public interface PacketHeader {
   byte[] getBuffer();

   int getPacketLength();

   byte getPacketSequence();
}
