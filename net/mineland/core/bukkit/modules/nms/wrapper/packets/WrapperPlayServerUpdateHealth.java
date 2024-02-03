package net.mineland.core.bukkit.modules.nms.wrapper.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Play.Server;
import com.comphenix.protocol.events.PacketContainer;

public class WrapperPlayServerUpdateHealth extends AbstractPacket {
   public static final PacketType TYPE;

   public WrapperPlayServerUpdateHealth() {
      super(new PacketContainer(TYPE), TYPE);
      this.handle.getModifier().writeDefaults();
   }

   public WrapperPlayServerUpdateHealth(PacketContainer packet) {
      super(packet, TYPE);
   }

   public float getHealth() {
      return (Float)this.handle.getFloat().read(0);
   }

   public void setHealth(float value) {
      this.handle.getFloat().write(0, value);
   }

   public int getFood() {
      return (Integer)this.handle.getIntegers().read(0);
   }

   public void setFood(int value) {
      this.handle.getIntegers().write(0, value);
   }

   public float getFoodSaturation() {
      return (Float)this.handle.getFloat().read(1);
   }

   public void setFoodSaturation(float value) {
      this.handle.getFloat().write(1, value);
   }

   static {
      TYPE = Server.UPDATE_HEALTH;
   }
}
