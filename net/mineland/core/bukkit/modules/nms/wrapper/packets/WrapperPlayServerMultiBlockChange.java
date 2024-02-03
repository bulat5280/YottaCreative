package net.mineland.core.bukkit.modules.nms.wrapper.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Play.Server;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.ChunkCoordIntPair;
import com.comphenix.protocol.wrappers.MultiBlockChangeInfo;

public class WrapperPlayServerMultiBlockChange extends AbstractPacket {
   public static final PacketType TYPE;

   public WrapperPlayServerMultiBlockChange() {
      super(new PacketContainer(TYPE), TYPE);
      this.handle.getModifier().writeDefaults();
   }

   public WrapperPlayServerMultiBlockChange(PacketContainer packet) {
      super(packet, TYPE);
   }

   public ChunkCoordIntPair getChunk() {
      return (ChunkCoordIntPair)this.handle.getChunkCoordIntPairs().read(0);
   }

   public void setChunk(ChunkCoordIntPair value) {
      this.handle.getChunkCoordIntPairs().write(0, value);
   }

   public MultiBlockChangeInfo[] getRecords() {
      return (MultiBlockChangeInfo[])this.handle.getMultiBlockChangeInfoArrays().read(0);
   }

   public void setRecords(MultiBlockChangeInfo[] value) {
      this.handle.getMultiBlockChangeInfoArrays().write(0, value);
   }

   static {
      TYPE = Server.MULTI_BLOCK_CHANGE;
   }
}
