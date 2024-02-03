package net.mineland.core.bukkit.modules.nms.wrapper.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Status.Client;
import com.comphenix.protocol.events.PacketContainer;

public class WrapperStatusClientPing extends AbstractPacket {
   public static final PacketType TYPE;

   public WrapperStatusClientPing() {
      super(new PacketContainer(TYPE), TYPE);
      this.handle.getModifier().writeDefaults();
   }

   public WrapperStatusClientPing(PacketContainer packet) {
      super(packet, TYPE);
   }

   public long getTime() {
      return (Long)this.handle.getLongs().read(0);
   }

   public void setTime(long value) {
      this.handle.getLongs().write(0, value);
   }

   static {
      TYPE = Client.PING;
   }
}
