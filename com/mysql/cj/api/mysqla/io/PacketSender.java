package com.mysql.cj.api.mysqla.io;

import java.io.IOException;

public interface PacketSender {
   void send(byte[] var1, int var2, byte var3) throws IOException;

   PacketSender undecorateAll();

   PacketSender undecorate();
}
