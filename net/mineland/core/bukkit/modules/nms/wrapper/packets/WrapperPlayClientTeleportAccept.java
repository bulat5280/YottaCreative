package net.mineland.core.bukkit.modules.nms.wrapper.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Play.Client;
import com.comphenix.protocol.events.PacketContainer;

public class WrapperPlayClientTeleportAccept extends AbstractPacket {
   public static final PacketType TYPE;

   public WrapperPlayClientTeleportAccept() {
      super(new PacketContainer(TYPE), TYPE);
      this.handle.getModifier().writeDefaults();
   }

   public WrapperPlayClientTeleportAccept(PacketContainer packet) {
      super(packet, TYPE);
   }

   public int getTeleportId() {
      return (Integer)this.handle.getIntegers().read(0);
   }

   public void setTeleportId(int value) {
      this.handle.getIntegers().write(0, value);
   }

   public String toString() {
      return "WrapperPlayClientTeleportAccept{teleportId=" + this.getTeleportId() + '}';
   }

   static {
      TYPE = Client.TELEPORT_ACCEPT;
   }
}
