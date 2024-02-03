package com.mysql.cj.mysqla.io;

import com.mysql.cj.api.log.Log;
import com.mysql.cj.api.mysqla.io.PacketSender;
import com.mysql.cj.core.util.StringUtils;
import java.io.IOException;

public class TracingPacketSender implements PacketSender {
   private PacketSender packetSender;
   private String host;
   private long serverThreadId;
   private Log log;

   public TracingPacketSender(PacketSender packetSender, Log log, String host, long serverThreadId) {
      this.packetSender = packetSender;
      this.host = host;
      this.serverThreadId = serverThreadId;
      this.log = log;
   }

   public void setServerThreadId(long serverThreadId) {
      this.serverThreadId = serverThreadId;
   }

   private void logPacket(byte[] packet, int packetLen, byte packetSequence) {
      StringBuilder traceMessageBuf = new StringBuilder();
      traceMessageBuf.append("send packet payload:\n");
      traceMessageBuf.append("host: '");
      traceMessageBuf.append(this.host);
      traceMessageBuf.append("' serverThreadId: '");
      traceMessageBuf.append(this.serverThreadId);
      traceMessageBuf.append("' packetLen: '");
      traceMessageBuf.append(packetLen);
      traceMessageBuf.append("' packetSequence: '");
      traceMessageBuf.append(packetSequence);
      traceMessageBuf.append("'\n");
      traceMessageBuf.append(StringUtils.dumpAsHex(packet, packetLen));
      this.log.logTrace(traceMessageBuf.toString());
   }

   public void send(byte[] packet, int packetLen, byte packetSequence) throws IOException {
      this.logPacket(packet, packetLen, packetSequence);
      this.packetSender.send(packet, packetLen, packetSequence);
   }

   public PacketSender undecorateAll() {
      return this.packetSender.undecorateAll();
   }

   public PacketSender undecorate() {
      return this.packetSender;
   }
}
