package net.mineland.core.bukkit.modules.nms.wrapper.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Play.Client;
import com.comphenix.protocol.events.PacketContainer;

public class WrapperPlayClientAbilities extends AbstractPacket {
   public static final PacketType TYPE;

   public WrapperPlayClientAbilities() {
      super(new PacketContainer(TYPE), TYPE);
      this.handle.getModifier().writeDefaults();
   }

   public WrapperPlayClientAbilities(PacketContainer packet) {
      super(packet, TYPE);
   }

   public boolean isInvulnerable() {
      return (Boolean)this.handle.getBooleans().read(0);
   }

   public void setInvulnerable(boolean value) {
      this.handle.getBooleans().write(0, value);
   }

   /** @deprecated */
   @Deprecated
   public boolean isInvulnurable() {
      return (Boolean)this.handle.getBooleans().read(0);
   }

   /** @deprecated */
   @Deprecated
   public void setInvulnurable(boolean value) {
      this.handle.getBooleans().write(0, value);
   }

   public boolean isFlying() {
      return (Boolean)this.handle.getBooleans().read(1);
   }

   public void setFlying(boolean value) {
      this.handle.getBooleans().write(1, value);
   }

   public boolean canFly() {
      return (Boolean)this.handle.getBooleans().read(2);
   }

   public void setCanFly(boolean value) {
      this.handle.getBooleans().write(2, value);
   }

   public boolean canInstantlyBuild() {
      return (Boolean)this.handle.getBooleans().read(3);
   }

   public void setCanInstantlyBuild(boolean value) {
      this.handle.getBooleans().write(3, value);
   }

   public float getFlyingSpeed() {
      return (Float)this.handle.getFloat().read(0);
   }

   public void setFlyingSpeed(float value) {
      this.handle.getFloat().write(0, value);
   }

   public float getWalkingSpeed() {
      return (Float)this.handle.getFloat().read(1);
   }

   public void setWalkingSpeed(float value) {
      this.handle.getFloat().write(1, value);
   }

   static {
      TYPE = Client.ABILITIES;
   }
}
