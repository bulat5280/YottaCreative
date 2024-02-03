package net.mineland.core.bukkit.modules.nms.wrapper.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Play.Server;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.EnumWrappers.ChatType;
import java.util.Arrays;

public class WrapperPlayServerChat extends AbstractPacket {
   public static final PacketType TYPE;

   public WrapperPlayServerChat() {
      super(new PacketContainer(TYPE), TYPE);
      this.handle.getModifier().writeDefaults();
   }

   public WrapperPlayServerChat(PacketContainer packet) {
      super(packet, TYPE);
   }

   public WrappedChatComponent getMessage() {
      return (WrappedChatComponent)this.handle.getChatComponents().read(0);
   }

   public void setMessage(WrappedChatComponent value) {
      this.handle.getChatComponents().write(0, value);
   }

   public ChatType getChatType() {
      return (ChatType)this.handle.getChatTypes().read(0);
   }

   public void setChatType(ChatType type) {
      this.handle.getChatTypes().write(0, type);
   }

   /** @deprecated */
   @Deprecated
   public byte getPosition() {
      Byte position = (Byte)this.handle.getBytes().readSafely(0);
      return position != null ? position : this.getChatType().getId();
   }

   /** @deprecated */
   @Deprecated
   public void setPosition(byte value) {
      this.handle.getBytes().writeSafely(0, value);
      if (EnumWrappers.getChatTypeClass() != null) {
         Arrays.stream(ChatType.values()).filter((t) -> {
            return t.getId() == value;
         }).findAny().ifPresent((t) -> {
            this.handle.getChatTypes().writeSafely(0, t);
         });
      }

   }

   static {
      TYPE = Server.CHAT;
   }
}
