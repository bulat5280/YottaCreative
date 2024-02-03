package net.mineland.core.bukkit.modules.nms.wrapper.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Play.Client;
import com.comphenix.protocol.events.PacketContainer;

public class WrapperPlayClientRecipeDisplayed extends AbstractPacket {
   public static final PacketType TYPE;

   public WrapperPlayClientRecipeDisplayed() {
      super(new PacketContainer(TYPE), TYPE);
      this.handle.getModifier().writeDefaults();
   }

   public WrapperPlayClientRecipeDisplayed(PacketContainer packet) {
      super(packet, TYPE);
   }

   public WrapperPlayClientRecipeDisplayed.Status getStatus() {
      return (WrapperPlayClientRecipeDisplayed.Status)this.handle.getEnumModifier(WrapperPlayClientRecipeDisplayed.Status.class, 0).readSafely(0);
   }

   public void setStatus(WrapperPlayClientRecipeDisplayed.Status value) {
      this.handle.getEnumModifier(WrapperPlayClientRecipeDisplayed.Status.class, 0).writeSafely(0, value);
   }

   public boolean isBookOpen() {
      return (Boolean)this.handle.getBooleans().read(0);
   }

   public void setBookOpen(boolean value) {
      this.handle.getBooleans().write(0, value);
   }

   public boolean isFilterActive() {
      return (Boolean)this.handle.getBooleans().read(1);
   }

   public void setFilterActive(boolean value) {
      this.handle.getBooleans().write(1, value);
   }

   static {
      TYPE = Client.RECIPE_DISPLAYED;
   }

   public static enum Status {
      SHOWN,
      SETTINGS;
   }
}
