package net.mineland.core.bukkit.modules.nms.wrapper.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Play.Server;
import com.comphenix.protocol.events.PacketContainer;

public class WrapperPlayServerAutoRecipe extends AbstractPacket {
   public static final PacketType TYPE;

   public WrapperPlayServerAutoRecipe() {
      super(new PacketContainer(TYPE), TYPE);
      this.handle.getModifier().writeDefaults();
   }

   public WrapperPlayServerAutoRecipe(PacketContainer packet) {
      super(packet, TYPE);
   }

   public int getWindowId() {
      return (Integer)this.handle.getIntegers().read(0);
   }

   public void setWindowId(int value) {
      this.handle.getIntegers().write(0, value);
   }

   static {
      TYPE = Server.AUTO_RECIPE;
   }
}
