package net.mineland.core.bukkit.modules.nms.wrapper.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Play.Server;
import com.comphenix.protocol.events.PacketContainer;

public class WrapperPlayServerVehicleMove extends AbstractPacket {
   public static final PacketType TYPE;

   public WrapperPlayServerVehicleMove() {
      super(new PacketContainer(TYPE), TYPE);
      this.handle.getModifier().writeDefaults();
   }

   public WrapperPlayServerVehicleMove(PacketContainer packet) {
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

   static {
      TYPE = Server.VEHICLE_MOVE;
   }
}
