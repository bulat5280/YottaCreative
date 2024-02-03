package net.mineland.core.bukkit.modules.nms.wrapper.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Login.Server;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;

public class WrapperLoginServerDisconnect extends AbstractPacket {
   public static final PacketType TYPE;

   public WrapperLoginServerDisconnect() {
      super(new PacketContainer(TYPE), TYPE);
      this.handle.getModifier().writeDefaults();
   }

   public WrapperLoginServerDisconnect(PacketContainer packet) {
      super(packet, TYPE);
   }

   public WrappedChatComponent getReason() {
      return (WrappedChatComponent)this.handle.getChatComponents().read(0);
   }

   public void setReason(WrappedChatComponent value) {
      this.handle.getChatComponents().write(0, value);
   }

   /** @deprecated */
   @Deprecated
   public WrappedChatComponent getJsonData() {
      return this.getReason();
   }

   /** @deprecated */
   @Deprecated
   public void setJsonData(WrappedChatComponent value) {
      this.setReason(value);
   }

   static {
      TYPE = Server.DISCONNECT;
   }
}
