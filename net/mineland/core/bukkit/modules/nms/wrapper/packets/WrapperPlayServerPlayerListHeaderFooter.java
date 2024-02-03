package net.mineland.core.bukkit.modules.nms.wrapper.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Play.Server;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;

public class WrapperPlayServerPlayerListHeaderFooter extends AbstractPacket {
   public static final PacketType TYPE;

   public WrapperPlayServerPlayerListHeaderFooter() {
      super(new PacketContainer(TYPE), TYPE);
      this.handle.getModifier().writeDefaults();
   }

   public WrapperPlayServerPlayerListHeaderFooter(PacketContainer packet) {
      super(packet, TYPE);
   }

   public WrappedChatComponent getHeader() {
      return (WrappedChatComponent)this.handle.getChatComponents().read(0);
   }

   public void setHeader(WrappedChatComponent value) {
      this.handle.getChatComponents().write(0, value);
   }

   public WrappedChatComponent getFooter() {
      return (WrappedChatComponent)this.handle.getChatComponents().read(1);
   }

   public void setFooter(WrappedChatComponent value) {
      this.handle.getChatComponents().write(1, value);
   }

   static {
      TYPE = Server.PLAYER_LIST_HEADER_FOOTER;
   }
}
