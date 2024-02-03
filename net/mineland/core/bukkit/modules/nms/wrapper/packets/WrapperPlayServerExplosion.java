package net.mineland.core.bukkit.modules.nms.wrapper.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Play.Server;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.BlockPosition;
import java.util.List;

public class WrapperPlayServerExplosion extends AbstractPacket {
   public static final PacketType TYPE;

   public WrapperPlayServerExplosion() {
      super(new PacketContainer(TYPE), TYPE);
      this.handle.getModifier().writeDefaults();
   }

   public WrapperPlayServerExplosion(PacketContainer packet) {
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

   public float getRadius() {
      return (Float)this.handle.getFloat().read(0);
   }

   public void setRadius(float value) {
      this.handle.getFloat().write(0, value);
   }

   public List<BlockPosition> getRecords() {
      return (List)this.handle.getBlockPositionCollectionModifier().read(0);
   }

   public void setRecords(List<BlockPosition> value) {
      this.handle.getBlockPositionCollectionModifier().write(0, value);
   }

   /** @deprecated */
   @Deprecated
   public List<BlockPosition> getRecors() {
      return (List)this.handle.getBlockPositionCollectionModifier().read(0);
   }

   public float getPlayerVelocityX() {
      return (Float)this.handle.getFloat().read(0);
   }

   public void setPlayerVelocityX(float value) {
      this.handle.getFloat().write(0, value);
   }

   public float getPlayerVelocityY() {
      return (Float)this.handle.getFloat().read(1);
   }

   public void setPlayerVelocityY(float value) {
      this.handle.getFloat().write(1, value);
   }

   public float getPlayerVelocityZ() {
      return (Float)this.handle.getFloat().read(2);
   }

   public void setPlayerVelocityZ(float value) {
      this.handle.getFloat().write(2, value);
   }

   static {
      TYPE = Server.EXPLOSION;
   }
}
