package net.mineland.core.bukkit.modules.nms.wrapper.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Play.Client;
import com.comphenix.protocol.events.PacketContainer;
import java.util.UUID;

public class WrapperPlayClientSpectate extends AbstractPacket {
   public static final PacketType TYPE;

   public WrapperPlayClientSpectate() {
      super(new PacketContainer(TYPE), TYPE);
      this.handle.getModifier().writeDefaults();
   }

   public WrapperPlayClientSpectate(PacketContainer packet) {
      super(packet, TYPE);
   }

   public UUID getTargetPlayer() {
      return (UUID)this.handle.getUUIDs().read(0);
   }

   public void setTargetPlayer(UUID value) {
      this.handle.getUUIDs().write(0, value);
   }

   static {
      TYPE = Client.SPECTATE;
   }
}
