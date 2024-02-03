package net.mineland.core.bukkit.modules.nms.wrapper.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Play.Client;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers.ResourcePackStatus;

public class WrapperPlayClientResourcePackStatus extends AbstractPacket {
   public static final PacketType TYPE;

   public WrapperPlayClientResourcePackStatus() {
      super(new PacketContainer(TYPE), TYPE);
      this.handle.getModifier().writeDefaults();
   }

   public WrapperPlayClientResourcePackStatus(PacketContainer packet) {
      super(packet, TYPE);
   }

   public ResourcePackStatus getResult() {
      return (ResourcePackStatus)this.handle.getResourcePackStatus().read(0);
   }

   public void setResult(ResourcePackStatus value) {
      this.handle.getResourcePackStatus().write(0, value);
   }

   static {
      TYPE = Client.RESOURCE_PACK_STATUS;
   }
}
