package net.mineland.core.bukkit.modules.nms.wrapper.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Play.Server;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.reflect.StructureModifier;
import com.comphenix.protocol.utility.MinecraftReflection;
import com.comphenix.protocol.wrappers.EnumWrappers;
import java.util.Set;

public class WrapperPlayServerPosition extends AbstractPacket {
   public static final PacketType TYPE;
   private static final Class<?> FLAGS_CLASS;

   public WrapperPlayServerPosition() {
      super(new PacketContainer(TYPE), TYPE);
      this.handle.getModifier().writeDefaults();
   }

   public WrapperPlayServerPosition(PacketContainer packet) {
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

   private StructureModifier<Set<WrapperPlayServerPosition.PlayerTeleportFlag>> getFlagsModifier() {
      return this.handle.getSets(EnumWrappers.getGenericConverter(FLAGS_CLASS, WrapperPlayServerPosition.PlayerTeleportFlag.class));
   }

   public Set<WrapperPlayServerPosition.PlayerTeleportFlag> getFlags() {
      return (Set)this.getFlagsModifier().read(0);
   }

   public void setFlags(Set<WrapperPlayServerPosition.PlayerTeleportFlag> value) {
      this.getFlagsModifier().write(0, value);
   }

   static {
      TYPE = Server.POSITION;
      FLAGS_CLASS = MinecraftReflection.getMinecraftClass("EnumPlayerTeleportFlags", new String[]{"PacketPlayOutPosition$EnumPlayerTeleportFlags"});
   }

   public static enum PlayerTeleportFlag {
      X,
      Y,
      Z,
      Y_ROT,
      X_ROT;
   }
}
