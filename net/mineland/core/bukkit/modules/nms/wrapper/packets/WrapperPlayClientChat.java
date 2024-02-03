package net.mineland.core.bukkit.modules.nms.wrapper.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Play.Client;
import com.comphenix.protocol.events.PacketContainer;

public class WrapperPlayClientChat extends AbstractPacket {
   public static final PacketType TYPE;

   public WrapperPlayClientChat() {
      super(new PacketContainer(TYPE), TYPE);
      this.handle.getModifier().writeDefaults();
   }

   public WrapperPlayClientChat(PacketContainer packet) {
      super(packet, TYPE);
   }

   public String getMessage() {
      return (String)this.handle.getStrings().read(0);
   }

   public void setMessage(String value) {
      this.handle.getStrings().write(0, value);
   }

   static {
      TYPE = Client.CHAT;
   }
}
