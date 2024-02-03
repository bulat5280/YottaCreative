package net.mineland.core.bukkit.modules.nms.wrapper.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Play.Server;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.World;
import org.bukkit.entity.Entity;

public class WrapperPlayServerRelEntityMoveLook extends AbstractPacket {
   public static final PacketType TYPE;

   public WrapperPlayServerRelEntityMoveLook() {
      super(new PacketContainer(TYPE), TYPE);
      this.handle.getModifier().writeDefaults();
   }

   public WrapperPlayServerRelEntityMoveLook(PacketContainer packet) {
      super(packet, TYPE);
   }

   public int getEntityID() {
      return (Integer)this.handle.getIntegers().read(0);
   }

   public void setEntityID(int value) {
      this.handle.getIntegers().write(0, value);
   }

   public Entity getEntity(World world) {
      return (Entity)this.handle.getEntityModifier(world).read(0);
   }

   public Entity getEntity(PacketEvent event) {
      return this.getEntity(event.getPlayer().getWorld());
   }

   public double getDx() {
      return (double)(Integer)this.handle.getIntegers().read(1) / 4096.0D;
   }

   public void setDx(double value) {
      this.handle.getIntegers().write(1, (int)(value * 4096.0D));
   }

   public double getDy() {
      return (double)(Integer)this.handle.getIntegers().read(2) / 4096.0D;
   }

   public void setDy(double value) {
      this.handle.getIntegers().write(2, (int)(value * 4096.0D));
   }

   public double getDz() {
      return (double)(Integer)this.handle.getIntegers().read(3) / 4096.0D;
   }

   public void setDz(double value) {
      this.handle.getIntegers().write(3, (int)(value * 4096.0D));
   }

   public float getYaw() {
      return (float)(Byte)this.handle.getBytes().read(0) * 360.0F / 256.0F;
   }

   public void setYaw(float value) {
      this.handle.getBytes().write(0, (byte)((int)(value * 256.0F / 360.0F)));
   }

   public float getPitch() {
      return (float)(Byte)this.handle.getBytes().read(1) * 360.0F / 256.0F;
   }

   public void setPitch(float value) {
      this.handle.getBytes().write(1, (byte)((int)(value * 256.0F / 360.0F)));
   }

   public boolean getOnGround() {
      return (Boolean)this.handle.getBooleans().read(0);
   }

   public void setOnGround(boolean value) {
      this.handle.getBooleans().write(0, value);
   }

   static {
      TYPE = Server.REL_ENTITY_MOVE_LOOK;
   }
}
