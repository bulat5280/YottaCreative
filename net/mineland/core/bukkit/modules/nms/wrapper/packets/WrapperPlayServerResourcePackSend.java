package net.mineland.core.bukkit.modules.nms.wrapper.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Play.Server;
import com.comphenix.protocol.events.PacketContainer;

public class WrapperPlayServerResourcePackSend extends AbstractPacket {
   public static final PacketType TYPE;

   public WrapperPlayServerResourcePackSend() {
      super(new PacketContainer(TYPE), TYPE);
      this.handle.getModifier().writeDefaults();
   }

   public WrapperPlayServerResourcePackSend(PacketContainer packet) {
      super(packet, TYPE);
   }

   public String getUrl() {
      return (String)this.handle.getStrings().read(0);
   }

   public void setUrl(String value) {
      this.handle.getStrings().write(0, value);
   }

   public String getHash() {
      return (String)this.handle.getStrings().read(1);
   }

   public void setHash(String value) {
      this.handle.getStrings().write(1, value);
   }

   static {
      TYPE = Server.RESOURCE_PACK_SEND;
   }
}
