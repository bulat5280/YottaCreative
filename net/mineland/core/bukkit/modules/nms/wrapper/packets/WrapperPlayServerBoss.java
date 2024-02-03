package net.mineland.core.bukkit.modules.nms.wrapper.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Play.Server;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import java.util.UUID;
import org.bukkit.boss.BarColor;

public class WrapperPlayServerBoss extends AbstractPacket {
   public static final PacketType TYPE;

   public WrapperPlayServerBoss() {
      super(new PacketContainer(TYPE), TYPE);
      this.handle.getModifier().writeDefaults();
   }

   public WrapperPlayServerBoss(PacketContainer packet) {
      super(packet, TYPE);
   }

   public UUID getUniqueId() {
      return (UUID)this.handle.getUUIDs().read(0);
   }

   public void setUniqueId(UUID value) {
      this.handle.getUUIDs().write(0, value);
   }

   public WrapperPlayServerBoss.Action getAction() {
      return (WrapperPlayServerBoss.Action)this.handle.getEnumModifier(WrapperPlayServerBoss.Action.class, 1).read(0);
   }

   public void setAction(WrapperPlayServerBoss.Action value) {
      this.handle.getEnumModifier(WrapperPlayServerBoss.Action.class, 1).write(0, value);
   }

   public WrappedChatComponent getTitle() {
      return (WrappedChatComponent)this.handle.getChatComponents().read(0);
   }

   public void setTitle(WrappedChatComponent value) {
      this.handle.getChatComponents().write(0, value);
   }

   public float getHealth() {
      return (Float)this.handle.getFloat().read(0);
   }

   public void setHealth(float value) {
      this.handle.getFloat().write(0, value);
   }

   public BarColor getColor() {
      return (BarColor)this.handle.getEnumModifier(BarColor.class, 4).read(0);
   }

   public void setColor(BarColor value) {
      this.handle.getEnumModifier(BarColor.class, 4).write(0, value);
   }

   public WrapperPlayServerBoss.BarStyle getStyle() {
      return (WrapperPlayServerBoss.BarStyle)this.handle.getEnumModifier(WrapperPlayServerBoss.BarStyle.class, 5).read(0);
   }

   public void setStyle(WrapperPlayServerBoss.BarStyle value) {
      this.handle.getEnumModifier(WrapperPlayServerBoss.BarStyle.class, 5).write(0, value);
   }

   public boolean isDarkenSky() {
      return (Boolean)this.handle.getBooleans().read(0);
   }

   public void setDarkenSky(boolean value) {
      this.handle.getBooleans().write(0, value);
   }

   public boolean isPlayMusic() {
      return (Boolean)this.handle.getBooleans().read(1);
   }

   public void setPlayMusic(boolean value) {
      this.handle.getBooleans().write(1, value);
   }

   public boolean isCreateFog() {
      return (Boolean)this.handle.getBooleans().read(2);
   }

   public void setCreateFog(boolean value) {
      this.handle.getBooleans().write(2, value);
   }

   public String toString() {
      return "WrapperPlayServerBoss{uniqueId=" + this.getUniqueId() + ", action=" + this.getAction() + ", title=" + this.getTitle() + ", health=" + this.getHealth() + ", color=" + this.getColor() + ", style=" + this.getStyle() + ", darkenSky=" + this.isDarkenSky() + ", playMusic=" + this.isPlayMusic() + ", createFog=" + this.isCreateFog() + '}';
   }

   static {
      TYPE = Server.BOSS;
   }

   public static enum BarStyle {
      PROGRESS,
      NOTCHED_6,
      NOTCHED_10,
      NOTCHED_12,
      NOTCHED_20;
   }

   public static enum Action {
      ADD,
      REMOVE,
      UPDATE_PCT,
      UPDATE_NAME,
      UPDATE_STYLE,
      UPDATE_PROPERTIES;
   }
}
