package net.mineland.core.bukkit.modules.nms.wrapper.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Play.Server;
import com.comphenix.protocol.events.PacketContainer;

public class WrapperPlayServerKeepAlive extends AbstractPacket {
   public static final PacketType TYPE;

   public WrapperPlayServerKeepAlive() {
      super(new PacketContainer(TYPE), TYPE);
      this.handle.getModifier().writeDefaults();
   }

   public WrapperPlayServerKeepAlive(PacketContainer packet) {
      super(packet, TYPE);
   }

   public int getKeepAliveId() {
      return (Integer)this.handle.getIntegers().read(0);
   }

   public void setKeepAliveId(int value) {
      this.handle.getIntegers().write(0, value);
   }

   static {
      TYPE = Server.KEEP_ALIVE;
   }
}
