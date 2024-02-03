package net.mineland.core.bukkit.modules.nms.wrapper.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Play.Server;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.BlockPosition;

public class WrapperPlayServerSpawnPosition extends AbstractPacket {
   public static final PacketType TYPE;

   public WrapperPlayServerSpawnPosition() {
      super(new PacketContainer(TYPE), TYPE);
      this.handle.getModifier().writeDefaults();
   }

   public WrapperPlayServerSpawnPosition(PacketContainer packet) {
      super(packet, TYPE);
   }

   public BlockPosition getLocation() {
      return (BlockPosition)this.handle.getBlockPositionModifier().read(0);
   }

   public void setLocation(BlockPosition value) {
      this.handle.getBlockPositionModifier().write(0, value);
   }

   static {
      TYPE = Server.SPAWN_POSITION;
   }
}
