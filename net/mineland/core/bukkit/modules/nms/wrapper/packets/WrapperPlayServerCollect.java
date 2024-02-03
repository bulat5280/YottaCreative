package net.mineland.core.bukkit.modules.nms.wrapper.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Play.Server;
import com.comphenix.protocol.events.PacketContainer;

public class WrapperPlayServerCollect extends AbstractPacket {
   public static final PacketType TYPE;

   public WrapperPlayServerCollect() {
      super(new PacketContainer(TYPE), TYPE);
      this.handle.getModifier().writeDefaults();
   }

   public WrapperPlayServerCollect(PacketContainer packet) {
      super(packet, TYPE);
   }

   public int getCollectedEntityId() {
      return (Integer)this.handle.getIntegers().read(0);
   }

   public void setCollectedEntityId(int value) {
      this.handle.getIntegers().write(0, value);
   }

   public int getCollectorEntityId() {
      return (Integer)this.handle.getIntegers().read(1);
   }

   public void setCollectorEntityId(int value) {
      this.handle.getIntegers().write(1, value);
   }

   static {
      TYPE = Server.COLLECT;
   }
}
