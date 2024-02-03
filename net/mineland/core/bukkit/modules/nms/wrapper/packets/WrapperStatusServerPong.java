package net.mineland.core.bukkit.modules.nms.wrapper.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Status.Server;
import com.comphenix.protocol.events.PacketContainer;

public class WrapperStatusServerPong extends AbstractPacket {
   public static final PacketType TYPE;

   public WrapperStatusServerPong() {
      super(new PacketContainer(TYPE), TYPE);
      this.handle.getModifier().writeDefaults();
   }

   public WrapperStatusServerPong(PacketContainer packet) {
      super(packet, TYPE);
   }

   public long getTime() {
      return (Long)this.handle.getLongs().read(0);
   }

   public void setTime(long value) {
      this.handle.getLongs().write(0, value);
   }

   static {
      TYPE = Server.PONG;
   }
}
