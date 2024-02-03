package net.mineland.core.bukkit.modules.nms.wrapper.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Play.Server;
import com.comphenix.protocol.events.PacketContainer;

public class WrapperPlayServerEntityDestroy extends AbstractPacket {
   public static final PacketType TYPE;

   public WrapperPlayServerEntityDestroy() {
      super(new PacketContainer(TYPE), TYPE);
      this.handle.getModifier().writeDefaults();
   }

   public WrapperPlayServerEntityDestroy(PacketContainer packet) {
      super(packet, TYPE);
   }

   public int getCount() {
      return ((int[])this.handle.getIntegerArrays().read(0)).length;
   }

   public int[] getEntityIDs() {
      return (int[])this.handle.getIntegerArrays().read(0);
   }

   public void setEntityIds(int[] value) {
      this.handle.getIntegerArrays().write(0, value);
   }

   static {
      TYPE = Server.ENTITY_DESTROY;
   }
}
