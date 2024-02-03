package net.mineland.core.bukkit.modules.nms.wrapper.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Play.Client;
import com.comphenix.protocol.events.PacketContainer;

public class WrapperPlayClientPositionLook extends AbstractPacket {
   public static final PacketType TYPE;

   public WrapperPlayClientPositionLook() {
      super(new PacketContainer(TYPE), TYPE);
      this.handle.getModifier().writeDefaults();
   }

   public WrapperPlayClientPositionLook(PacketContainer packet) {
      super(packet, TYPE);
   }

   public double getX() {
      return (Double)this.handle.getDoubles().read(0);
   }

   public void setX(double value) {
      this.handle.getDoubles().write(0, value);
   }

   public double getY() {
      return (Double)this.handle.getDoubles().read(1);
   }

   public void setY(double value) {
      this.handle.getDoubles().write(1, value);
   }

   public double getZ() {
      return (Double)this.handle.getDoubles().read(2);
   }

   public void setZ(double value) {
      this.handle.getDoubles().write(2, value);
   }

   public float getYaw() {
      return (Float)this.handle.getFloat().read(0);
   }

   public void setYaw(float value) {
      this.handle.getFloat().write(0, value);
   }

   public float getPitch() {
      return (Float)this.handle.getFloat().read(1);
   }

   public void setPitch(float value) {
      this.handle.getFloat().write(1, value);
   }

   public boolean getOnGround() {
      return (Boolean)this.handle.getBooleans().read(0);
   }

   public void setOnGround(boolean value) {
      this.handle.getBooleans().write(0, value);
   }

   public String toString() {
      return "WrapperPlayClientPositionLook{x=" + this.getX() + ", y=" + this.getY() + ", z=" + this.getZ() + ", yaw=" + this.getYaw() + ", pitch=" + this.getPitch() + ", onGround=" + this.getOnGround() + '}';
   }

   static {
      TYPE = Client.POSITION_LOOK;
   }
}
