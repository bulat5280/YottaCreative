package net.mineland.core.bukkit.modules.nms.wrapper.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.PacketType.Play.Server;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.injector.PacketConstructor;
import com.comphenix.protocol.reflect.IntEnum;
import java.util.UUID;
import org.bukkit.World;
import org.bukkit.entity.Entity;

public class WrapperPlayServerSpawnEntity extends AbstractPacket {
   public static final PacketType TYPE;
   private static PacketConstructor entityConstructor;

   public WrapperPlayServerSpawnEntity() {
      super(new PacketContainer(TYPE), TYPE);
      this.handle.getModifier().writeDefaults();
   }

   public WrapperPlayServerSpawnEntity(PacketContainer packet) {
      super(packet, TYPE);
   }

   public WrapperPlayServerSpawnEntity(Entity entity, int type, int objectData) {
      super(fromEntity(entity, type, objectData), TYPE);
   }

   private static PacketContainer fromEntity(Entity entity, int type, int objectData) {
      if (entityConstructor == null) {
         entityConstructor = ProtocolLibrary.getProtocolManager().createPacketConstructor(TYPE, new Object[]{entity, type, objectData});
      }

      return entityConstructor.createPacket(new Object[]{entity, type, objectData});
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

   public double getX() {
      return (Double)this.handle.getDoubles().read(0);
   }

   public void setX(double value) {
      this.handle.getDoubles().write(0, value);
   }

   public UUID getUniqueId() {
      return (UUID)this.handle.getUUIDs().read(0);
   }

   public void setUniqueId(UUID value) {
      this.handle.getUUIDs().write(0, value);
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

   public double getOptionalSpeedX() {
      return (double)(Integer)this.handle.getIntegers().read(1) / 8000.0D;
   }

   public void setOptionalSpeedX(double value) {
      this.handle.getIntegers().write(1, (int)(value * 8000.0D));
   }

   public double getOptionalSpeedY() {
      return (double)(Integer)this.handle.getIntegers().read(2) / 8000.0D;
   }

   public void setOptionalSpeedY(double value) {
      this.handle.getIntegers().write(2, (int)(value * 8000.0D));
   }

   public double getOptionalSpeedZ() {
      return (double)(Integer)this.handle.getIntegers().read(3) / 8000.0D;
   }

   public void setOptionalSpeedZ(double value) {
      this.handle.getIntegers().write(3, (int)(value * 8000.0D));
   }

   public float getPitch() {
      return (float)(Integer)this.handle.getIntegers().read(4) * 360.0F / 256.0F;
   }

   public void setPitch(float value) {
      this.handle.getIntegers().write(4, (int)(value * 256.0F / 360.0F));
   }

   public float getYaw() {
      return (float)(Integer)this.handle.getIntegers().read(5) * 360.0F / 256.0F;
   }

   public void setYaw(float value) {
      this.handle.getIntegers().write(5, (int)(value * 256.0F / 360.0F));
   }

   public int getType() {
      return (Integer)this.handle.getIntegers().read(6);
   }

   public void setType(int value) {
      this.handle.getIntegers().write(6, value);
   }

   public int getObjectData() {
      return (Integer)this.handle.getIntegers().read(7);
   }

   public void setObjectData(int value) {
      this.handle.getIntegers().write(7, value);
   }

   public String toString() {
      return "WrapperPlayServerSpawnEntity{entityID=" + this.getEntityID() + ", x=" + this.getX() + ", uniqueId=" + this.getUniqueId() + ", y=" + this.getY() + ", z=" + this.getZ() + ", optionalSpeedX=" + this.getOptionalSpeedX() + ", optionalSpeedY=" + this.getOptionalSpeedY() + ", optionalSpeedZ=" + this.getOptionalSpeedZ() + ", pitch=" + this.getPitch() + ", yaw=" + this.getYaw() + ", type=" + this.getType() + ", objectData=" + this.getObjectData() + '}';
   }

   static {
      TYPE = Server.SPAWN_ENTITY;
   }

   public static class ObjectTypes extends IntEnum {
      public static final int BOAT = 1;
      public static final int ITEM_STACK = 2;
      public static final int AREA_EFFECT_CLOUD = 3;
      public static final int MINECART = 10;
      public static final int ACTIVATED_TNT = 50;
      public static final int ENDER_CRYSTAL = 51;
      public static final int TIPPED_ARROW_PROJECTILE = 60;
      public static final int SNOWBALL_PROJECTILE = 61;
      public static final int EGG_PROJECTILE = 62;
      public static final int GHAST_FIREBALL = 63;
      public static final int BLAZE_FIREBALL = 64;
      public static final int THROWN_ENDERPEARL = 65;
      public static final int WITHER_SKULL_PROJECTILE = 66;
      public static final int SHULKER_BULLET = 67;
      public static final int FALLING_BLOCK = 70;
      public static final int ITEM_FRAME = 71;
      public static final int EYE_OF_ENDER = 72;
      public static final int THROWN_POTION = 73;
      public static final int THROWN_EXP_BOTTLE = 75;
      public static final int FIREWORK_ROCKET = 76;
      public static final int LEASH_KNOT = 77;
      public static final int ARMORSTAND = 78;
      public static final int FISHING_FLOAT = 90;
      public static final int SPECTRAL_ARROW = 91;
      public static final int DRAGON_FIREBALL = 93;
      private static WrapperPlayServerSpawnEntity.ObjectTypes INSTANCE = new WrapperPlayServerSpawnEntity.ObjectTypes();

      public static WrapperPlayServerSpawnEntity.ObjectTypes getInstance() {
         return INSTANCE;
      }
   }
}
