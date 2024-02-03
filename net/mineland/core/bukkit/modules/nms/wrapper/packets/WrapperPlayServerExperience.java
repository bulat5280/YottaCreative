package net.mineland.core.bukkit.modules.nms.wrapper.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Play.Server;
import com.comphenix.protocol.events.PacketContainer;

public class WrapperPlayServerExperience extends AbstractPacket {
   public static final PacketType TYPE;

   public WrapperPlayServerExperience() {
      super(new PacketContainer(TYPE), TYPE);
      this.handle.getModifier().writeDefaults();
   }

   public WrapperPlayServerExperience(PacketContainer packet) {
      super(packet, TYPE);
   }

   public float getExperienceBar() {
      return (Float)this.handle.getFloat().read(0);
   }

   public void setExperienceBar(float value) {
      this.handle.getFloat().write(0, value);
   }

   public int getLevel() {
      return (Integer)this.handle.getIntegers().read(1);
   }

   public void setLevel(int value) {
      this.handle.getIntegers().write(1, value);
   }

   public int getTotalExperience() {
      return (Integer)this.handle.getIntegers().read(0);
   }

   public void setTotalExperience(int value) {
      this.handle.getIntegers().write(0, value);
   }

   static {
      TYPE = Server.EXPERIENCE;
   }
}
