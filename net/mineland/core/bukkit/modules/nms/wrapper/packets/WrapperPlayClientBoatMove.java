package net.mineland.core.bukkit.modules.nms.wrapper.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Play.Client;
import com.comphenix.protocol.events.PacketContainer;

public class WrapperPlayClientBoatMove extends AbstractPacket {
   public static final PacketType TYPE;

   public WrapperPlayClientBoatMove() {
      super(new PacketContainer(TYPE), TYPE);
      this.handle.getModifier().writeDefaults();
   }

   public WrapperPlayClientBoatMove(PacketContainer packet) {
      super(packet, TYPE);
   }

   public boolean getLeftOar() {
      return (Boolean)this.handle.getBooleans().read(0);
   }

   public void setLeftOar(boolean value) {
      this.handle.getBooleans().write(0, value);
   }

   public boolean getRightOar() {
      return (Boolean)this.handle.getBooleans().read(1);
   }

   public void setRightOar(boolean value) {
      this.handle.getBooleans().write(1, value);
   }

   static {
      TYPE = Client.BOAT_MOVE;
   }
}
