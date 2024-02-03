package net.mineland.core.bukkit.modules.nms.wrapper.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Play.Server;
import com.comphenix.protocol.events.PacketContainer;

public class WrapperPlayServerMapChunk extends AbstractPacket {
   public static final PacketType TYPE;

   public WrapperPlayServerMapChunk() {
      super(new PacketContainer(TYPE), TYPE);
      this.handle.getModifier().writeDefaults();
   }

   public WrapperPlayServerMapChunk(PacketContainer packet) {
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

   public Object getChunkMap() {
      return this.handle.getModifier().read(2);
   }

   public void setChunkMap(Object value) {
      this.handle.getModifier().write(2, value);
   }

   public boolean getGroundUpContinuous() {
      return (Boolean)this.handle.getBooleans().read(0);
   }

   public void setGroundUpContinuous(boolean value) {
      this.handle.getBooleans().write(0, value);
   }

   static {
      TYPE = Server.MAP_CHUNK;
   }
}
