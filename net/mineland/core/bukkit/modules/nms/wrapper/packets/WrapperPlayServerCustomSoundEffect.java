package net.mineland.core.bukkit.modules.nms.wrapper.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Play.Server;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers.SoundCategory;

public class WrapperPlayServerCustomSoundEffect extends AbstractPacket {
   public static final PacketType TYPE;

   public WrapperPlayServerCustomSoundEffect() {
      super(new PacketContainer(TYPE), TYPE);
      this.handle.getModifier().writeDefaults();
   }

   public WrapperPlayServerCustomSoundEffect(PacketContainer packet) {
      super(packet, TYPE);
   }

   public String getSoundName() {
      return (String)this.handle.getStrings().read(0);
   }

   public void setSoundName(String value) {
      this.handle.getStrings().write(0, value);
   }

   public SoundCategory getSoundCategory() {
      return (SoundCategory)this.handle.getSoundCategories().read(0);
   }

   public void setSoundCategory(SoundCategory value) {
      this.handle.getSoundCategories().write(0, value);
   }

   public int getX() {
      return (Integer)this.handle.getIntegers().read(0);
   }

   public void setX(int value) {
      this.handle.getIntegers().write(0, value);
   }

   public int getY() {
      return (Integer)this.handle.getIntegers().read(1);
   }

   public void setY(int value) {
      this.handle.getIntegers().write(1, value);
   }

   public int getZ() {
      return (Integer)this.handle.getIntegers().read(2);
   }

   public void setZ(int value) {
      this.handle.getIntegers().write(2, value);
   }

   public float getVolume() {
      return (Float)this.handle.getFloat().read(0);
   }

   public void setVolume(float value) {
      this.handle.getFloat().write(0, value);
   }

   public float getPitch() {
      return (Float)this.handle.getFloat().read(1);
   }

   public void setPitch(float value) {
      this.handle.getFloat().write(1, value);
   }

   public String toString() {
      return "WrapperPlayServerCustomSoundEffect{soundName='" + this.getSoundName() + '\'' + ", soundCategory=" + this.getSoundCategory() + ", x=" + this.getX() + ", y=" + this.getY() + ", z=" + this.getZ() + ", volume=" + this.getVolume() + ", pitch=" + this.getPitch() + '}';
   }

   static {
      TYPE = Server.CUSTOM_SOUND_EFFECT;
   }
}
