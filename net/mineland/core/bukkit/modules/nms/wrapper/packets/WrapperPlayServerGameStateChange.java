package net.mineland.core.bukkit.modules.nms.wrapper.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Play.Server;
import com.comphenix.protocol.events.PacketContainer;

public class WrapperPlayServerGameStateChange extends AbstractPacket {
   public static final PacketType TYPE;

   public WrapperPlayServerGameStateChange() {
      super(new PacketContainer(TYPE), TYPE);
      this.handle.getModifier().writeDefaults();
   }

   public WrapperPlayServerGameStateChange(PacketContainer packet) {
      super(packet, TYPE);
   }

   public int getReason() {
      return (Integer)this.handle.getIntegers().read(0);
   }

   public void setReason(int value) {
      this.handle.getIntegers().write(0, value);
   }

   public float getValue() {
      return (Float)this.handle.getFloat().read(0);
   }

   public void setValue(float value) {
      this.handle.getFloat().write(0, value);
   }

   static {
      TYPE = Server.GAME_STATE_CHANGE;
   }
}
