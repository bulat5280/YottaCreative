package net.mineland.core.bukkit.modules.nms.wrapper.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Play.Server;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.MinecraftKey;

public class WrapperPlayServerSelectAdvancementTab extends AbstractPacket {
   public static final PacketType TYPE;

   public WrapperPlayServerSelectAdvancementTab() {
      super(new PacketContainer(TYPE), TYPE);
      this.handle.getModifier().writeDefaults();
   }

   public WrapperPlayServerSelectAdvancementTab(PacketContainer packet) {
      super(packet, TYPE);
   }

   public MinecraftKey getKey() {
      return (MinecraftKey)this.handle.getMinecraftKeys().readSafely(0);
   }

   public void setKey(MinecraftKey key) {
      this.handle.getMinecraftKeys().writeSafely(0, key);
   }

   static {
      TYPE = Server.SELECT_ADVANCEMENT_TAB;
   }
}
