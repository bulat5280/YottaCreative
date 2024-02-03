package net.mineland.core.bukkit.modules.nms.wrapper.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Play.Client;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.MinecraftKey;

public class WrapperPlayClientAdvancements extends AbstractPacket {
   public static final PacketType TYPE;

   public WrapperPlayClientAdvancements() {
      super(new PacketContainer(TYPE), TYPE);
      this.handle.getModifier().writeDefaults();
   }

   public WrapperPlayClientAdvancements(PacketContainer packet) {
      super(packet, TYPE);
   }

   public WrapperPlayClientAdvancements.Status getAction() {
      return (WrapperPlayClientAdvancements.Status)this.handle.getEnumModifier(WrapperPlayClientAdvancements.Status.class, 0).readSafely(0);
   }

   public void setAction(WrapperPlayClientAdvancements.Status value) {
      this.handle.getEnumModifier(WrapperPlayClientAdvancements.Status.class, 0).writeSafely(0, value);
   }

   public MinecraftKey getTabId() {
      return (MinecraftKey)this.handle.getMinecraftKeys().readSafely(0);
   }

   public void setTabId(MinecraftKey value) {
      this.handle.getMinecraftKeys().writeSafely(0, value);
   }

   static {
      TYPE = Client.ADVANCEMENTS;
   }

   public static enum Status {
      OPENED_TAB,
      CLOSED_SCREEN;
   }
}
