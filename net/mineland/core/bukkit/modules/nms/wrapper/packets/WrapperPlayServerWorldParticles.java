package net.mineland.core.bukkit.modules.nms.wrapper.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Play.Server;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers.Particle;

public class WrapperPlayServerWorldParticles extends AbstractPacket {
   public static final PacketType TYPE;

   public WrapperPlayServerWorldParticles() {
      super(new PacketContainer(TYPE), TYPE);
      this.handle.getModifier().writeDefaults();
   }

   public WrapperPlayServerWorldParticles(PacketContainer packet) {
      super(packet, TYPE);
   }

   public Particle getParticleType() {
      return (Particle)this.handle.getParticles().read(0);
   }

   public void setParticleType(Particle value) {
      this.handle.getParticles().write(0, value);
   }

   public float getX() {
      return (Float)this.handle.getFloat().read(0);
   }

   public void setX(float value) {
      this.handle.getFloat().write(0, value);
   }

   public float getY() {
      return (Float)this.handle.getFloat().read(1);
   }

   public void setY(float value) {
      this.handle.getFloat().write(1, value);
   }

   public float getZ() {
      return (Float)this.handle.getFloat().read(2);
   }

   public void setZ(float value) {
      this.handle.getFloat().write(2, value);
   }

   public float getOffsetX() {
      return (Float)this.handle.getFloat().read(3);
   }

   public void setOffsetX(float value) {
      this.handle.getFloat().write(3, value);
   }

   public float getOffsetY() {
      return (Float)this.handle.getFloat().read(4);
   }

   public void setOffsetY(float value) {
      this.handle.getFloat().write(4, value);
   }

   public float getOffsetZ() {
      return (Float)this.handle.getFloat().read(5);
   }

   public void setOffsetZ(float value) {
      this.handle.getFloat().write(5, value);
   }

   public float getParticleData() {
      return (Float)this.handle.getFloat().read(6);
   }

   public void setParticleData(float value) {
      this.handle.getFloat().write(6, value);
   }

   public int getNumberOfParticles() {
      return (Integer)this.handle.getIntegers().read(0);
   }

   public void setNumberOfParticles(int value) {
      this.handle.getIntegers().write(0, value);
   }

   public boolean getLongDistance() {
      return (Boolean)this.handle.getBooleans().read(0);
   }

   public void setLongDistance(boolean value) {
      this.handle.getBooleans().write(0, value);
   }

   public int[] getData() {
      return (int[])this.handle.getIntegerArrays().read(0);
   }

   public void setData(int[] value) {
      this.handle.getIntegerArrays().write(0, value);
   }

   static {
      TYPE = Server.WORLD_PARTICLES;
   }
}
