package net.mineland.core.bukkit.modules.nms.wrapper.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Play.Server;
import com.comphenix.protocol.events.PacketContainer;

public class WrapperPlayServerRecipes extends AbstractPacket {
   public static final PacketType TYPE;

   public WrapperPlayServerRecipes() {
      super(new PacketContainer(TYPE), TYPE);
      this.handle.getModifier().writeDefaults();
   }

   public WrapperPlayServerRecipes(PacketContainer packet) {
      super(packet, TYPE);
   }

   public WrapperPlayServerRecipes.Action getAction() {
      return (WrapperPlayServerRecipes.Action)this.handle.getEnumModifier(WrapperPlayServerRecipes.Action.class, 0).readSafely(0);
   }

   public void setAction(WrapperPlayServerRecipes.Action value) {
      this.handle.getEnumModifier(WrapperPlayServerRecipes.Action.class, 0).writeSafely(0, value);
   }

   public boolean getCraftingBookOpen() {
      return (Boolean)this.handle.getBooleans().read(0);
   }

   public void setCraftingBookOpen(boolean value) {
      this.handle.getBooleans().write(0, value);
   }

   public boolean getFilteringCraftable() {
      return (Boolean)this.handle.getBooleans().read(0);
   }

   public void setFilteringCraftable(boolean value) {
      this.handle.getBooleans().write(0, value);
   }

   static {
      TYPE = Server.RECIPES;
   }

   public static enum Action {
      INIT,
      ADD,
      REMOVE;
   }
}
