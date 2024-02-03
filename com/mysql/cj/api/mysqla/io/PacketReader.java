package com.mysql.cj.api.mysqla.io;

import java.io.IOException;
import java.util.Optional;

public interface PacketReader {
   PacketHeader readHeader() throws IOException;

   PacketPayload readPayload(Optional<PacketPayload> var1, int var2) throws IOException;

   byte getPacketSequence();

   void resetPacketSequence();

   PacketReader undecorateAll();

   PacketReader undecorate();
}
