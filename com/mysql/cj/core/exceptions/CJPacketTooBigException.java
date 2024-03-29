package com.mysql.cj.core.exceptions;

import com.mysql.cj.core.Messages;

public class CJPacketTooBigException extends CJException {
   private static final long serialVersionUID = 7186090399276725363L;

   public CJPacketTooBigException() {
   }

   public CJPacketTooBigException(String message) {
      super(message);
   }

   public CJPacketTooBigException(Throwable cause) {
      super(cause);
   }

   public CJPacketTooBigException(String message, Throwable cause) {
      super(message, cause);
   }

   public CJPacketTooBigException(long packetSize, long maximumPacketSize) {
      super(Messages.getString("PacketTooBigException.0", new Object[]{packetSize, maximumPacketSize}));
   }
}
