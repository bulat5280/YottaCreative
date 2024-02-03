package net.mineland.core.bukkit.modules.nms.wrapper.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Play.Client;
import com.comphenix.protocol.events.PacketContainer;

public class WrapperPlayClientAutoRecipe extends AbstractPacket {
   public static final PacketType TYPE;

   public WrapperPlayClientAutoRecipe() {
      super(new PacketContainer(TYPE), TYPE);
      this.handle.getModifier().writeDefaults();
   }

   public WrapperPlayClientAutoRecipe(PacketContainer packet) {
      super(packet, TYPE);
   }

   public int getWindowId() {
      return (Integer)this.handle.getIntegers().read(0);
   }

   public void setWindowId(int value) {
      this.handle.getIntegers().write(0, value);
   }

   public boolean isMakeAll() {
      return (Boolean)this.handle.getBooleans().read(0);
   }

   public void setMakeAll(boolean value) {
      this.handle.getBooleans().write(0, value);
   }

   static {
      TYPE = Client.AUTO_RECIPE;
   }
}
