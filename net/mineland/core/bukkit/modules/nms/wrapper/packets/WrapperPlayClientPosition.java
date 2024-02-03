package net.mineland.core.bukkit.modules.nms.wrapper.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Play.Client;
import com.comphenix.protocol.events.PacketContainer;

public class WrapperPlayClientPosition extends AbstractPacket {
   public static final PacketType TYPE;

   public WrapperPlayClientPosition() {
      super(new PacketContainer(TYPE), TYPE);
      this.handle.getModifier().writeDefaults();
   }

   public WrapperPlayClientPosition(PacketContainer packet) {
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

   public boolean getOnGround() {
      return (Boolean)this.handle.getBooleans().read(0);
   }

   public void setOnGround(boolean value) {
      this.handle.getBooleans().write(0, value);
   }

   public String toString() {
      return "WrapperPlayClientPosition{x=" + this.getX() + ", y=" + this.getY() + ", z=" + this.getZ() + ", onGround=" + this.getOnGround() + '}';
   }

   static {
      TYPE = Client.POSITION;
   }
}
