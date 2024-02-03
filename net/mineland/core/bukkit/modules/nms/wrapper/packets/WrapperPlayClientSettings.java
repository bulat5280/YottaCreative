package net.mineland.core.bukkit.modules.nms.wrapper.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Play.Client;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers.ChatVisibility;

public class WrapperPlayClientSettings extends AbstractPacket {
   public static final PacketType TYPE;

   public WrapperPlayClientSettings() {
      super(new PacketContainer(TYPE), TYPE);
      this.handle.getModifier().writeDefaults();
   }

   public WrapperPlayClientSettings(PacketContainer packet) {
      super(packet, TYPE);
   }

   public String getLocale() {
      return (String)this.handle.getStrings().read(0);
   }

   public void setLocale(String value) {
      this.handle.getStrings().write(0, value);
   }

   public int getViewDistance() {
      return (Integer)this.handle.getIntegers().read(0);
   }

   public void setViewDistance(byte value) {
      this.handle.getIntegers().write(0, Integer.valueOf(value));
   }

   public ChatVisibility getChatFlags() {
      return (ChatVisibility)this.handle.getChatVisibilities().read(0);
   }

   public void setChatFlags(ChatVisibility value) {
      this.handle.getChatVisibilities().write(0, value);
   }

   public boolean getChatColours() {
      return (Boolean)this.handle.getBooleans().read(0);
   }

   public void setChatColours(boolean value) {
      this.handle.getBooleans().write(0, value);
   }

   public int getDisplayedSkinParts() {
      return (Integer)this.handle.getIntegers().read(1);
   }

   public void setDisplayedSkinParts(int value) {
      this.handle.getIntegers().write(1, value);
   }

   static {
      TYPE = Client.SETTINGS;
   }
}
