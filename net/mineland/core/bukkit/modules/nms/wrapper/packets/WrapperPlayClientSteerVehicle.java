package net.mineland.core.bukkit.modules.nms.wrapper.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Play.Client;
import com.comphenix.protocol.events.PacketContainer;

public class WrapperPlayClientSteerVehicle extends AbstractPacket {
   public static final PacketType TYPE;

   public WrapperPlayClientSteerVehicle() {
      super(new PacketContainer(TYPE), TYPE);
      this.handle.getModifier().writeDefaults();
   }

   public WrapperPlayClientSteerVehicle(PacketContainer packet) {
      super(packet, TYPE);
   }

   public float getSideways() {
      return (Float)this.handle.getFloat().read(0);
   }

   public void setSideways(float value) {
      this.handle.getFloat().write(0, value);
   }

   public float getForward() {
      return (Float)this.handle.getFloat().read(1);
   }

   public void setForward(float value) {
      this.handle.getFloat().write(1, value);
   }

   public boolean isJump() {
      return (Boolean)this.handle.getBooleans().read(0);
   }

   public void setJump(boolean value) {
      this.handle.getBooleans().write(0, value);
   }

   public boolean isUnmount() {
      return (Boolean)this.handle.getBooleans().read(1);
   }

   public void setUnmount(boolean value) {
      this.handle.getBooleans().write(1, value);
   }

   static {
      TYPE = Client.STEER_VEHICLE;
   }
}
