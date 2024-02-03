package net.mineland.core.bukkit.modules.nms.wrapper.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Play.Client;
import com.comphenix.protocol.events.PacketContainer;

public class WrapperPlayClientFlying extends AbstractPacket {
   public static final PacketType TYPE;

   public WrapperPlayClientFlying() {
      super(new PacketContainer(TYPE), TYPE);
      this.handle.getModifier().writeDefaults();
   }

   public WrapperPlayClientFlying(PacketContainer packet) {
      super(packet, TYPE);
   }

   public boolean getOnGround() {
      return (Boolean)this.handle.getBooleans().read(0);
   }

   public void setOnGround(boolean value) {
      this.handle.getBooleans().write(0, value);
   }

   static {
      TYPE = Client.FLYING;
   }
}
