package net.mineland.core.bukkit.modules.nms.wrapper.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Play.Server;
import com.comphenix.protocol.events.PacketContainer;

public class WrapperPlayServerUnloadChunk extends AbstractPacket {
   public static final PacketType TYPE;

   public WrapperPlayServerUnloadChunk() {
      super(new PacketContainer(TYPE), TYPE);
      this.handle.getModifier().writeDefaults();
   }

   public WrapperPlayServerUnloadChunk(PacketContainer packet) {
      super(packet, TYPE);
   }

   public int getChunkX() {
      return (Integer)this.handle.getIntegers().read(0);
   }

   public void setChunkX(int value) {
      this.handle.getIntegers().write(0, value);
   }

   public int getChunkZ() {
      return (Integer)this.handle.getIntegers().read(1);
   }

   public void setChunkZ(int value) {
      this.handle.getIntegers().write(1, value);
   }

   static {
      TYPE = Server.UNLOAD_CHUNK;
   }
}
