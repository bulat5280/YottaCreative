package net.mineland.core.bukkit.modules.nms.wrapper.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Play.Server;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers.WorldBorderAction;

public class WrapperPlayServerWorldBorder extends AbstractPacket {
   public static final PacketType TYPE;

   public WrapperPlayServerWorldBorder() {
      super(new PacketContainer(TYPE), TYPE);
      this.handle.getModifier().writeDefaults();
   }

   public WrapperPlayServerWorldBorder(PacketContainer packet) {
      super(packet, TYPE);
   }

   public WorldBorderAction getAction() {
      return (WorldBorderAction)this.handle.getWorldBorderActions().read(0);
   }

   public void setAction(WorldBorderAction value) {
      this.handle.getWorldBorderActions().write(0, value);
   }

   public int getPortalTeleportBoundary() {
      return (Integer)this.handle.getIntegers().read(0);
   }

   public void setPortalTeleportBoundary(int value) {
      this.handle.getIntegers().write(0, value);
   }

   public double getCenterX() {
      return (Double)this.handle.getDoubles().read(0);
   }

   public void setCenterX(double value) {
      this.handle.getDoubles().write(0, value);
   }

   public double getCenterZ() {
      return (Double)this.handle.getDoubles().read(1);
   }

   public void setCenterZ(double value) {
      this.handle.getDoubles().write(1, value);
   }

   public double getOldRadius() {
      return (Double)this.handle.getDoubles().read(2);
   }

   public void setOldRadius(double value) {
      this.handle.getDoubles().write(2, value);
   }

   public double getRadius() {
      return (Double)this.handle.getDoubles().read(3);
   }

   public void setRadius(double value) {
      this.handle.getDoubles().write(3, value);
   }

   public long getSpeed() {
      return (Long)this.handle.getLongs().read(0);
   }

   public void setSpeed(long value) {
      this.handle.getLongs().write(0, value);
   }

   public int getWarningTime() {
      return (Integer)this.handle.getIntegers().read(1);
   }

   public void setWarningTime(int value) {
      this.handle.getIntegers().write(1, value);
   }

   public int getWarningDistance() {
      return (Integer)this.handle.getIntegers().read(2);
   }

   public void setWarningDistance(int value) {
      this.handle.getIntegers().write(2, value);
   }

   static {
      TYPE = Server.WORLD_BORDER;
   }
}
