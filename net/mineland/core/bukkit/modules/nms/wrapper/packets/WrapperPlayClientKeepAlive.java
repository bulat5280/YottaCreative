package net.mineland.core.bukkit.modules.nms.wrapper.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Play.Client;
import com.comphenix.protocol.events.PacketContainer;

public class WrapperPlayClientKeepAlive extends AbstractPacket {
   public static final PacketType TYPE;

   public WrapperPlayClientKeepAlive() {
      super(new PacketContainer(TYPE), TYPE);
      this.handle.getModifier().writeDefaults();
   }

   public WrapperPlayClientKeepAlive(PacketContainer packet) {
      super(packet, TYPE);
   }

   public int getKeepAliveId() {
      return (Integer)this.handle.getIntegers().read(0);
   }

   public void setKeepAliveId(int value) {
      this.handle.getIntegers().write(0, value);
   }

   public String toString() {
      return "WrapperPlayClientKeepAlive{keepAliveId=" + this.getKeepAliveId() + '}';
   }

   static {
      TYPE = Client.KEEP_ALIVE;
   }
}
