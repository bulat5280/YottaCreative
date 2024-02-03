package net.mineland.core.bukkit.modules.nms.wrapper.packets;

import com.comphenix.protocol.PacketType.Play.Server;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.reflect.StructureModifier;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.World.Environment;

public class ChunkPacketProcessor {
   /** @deprecated */
   @Deprecated
   public static final int BLOCK_ID_LENGHT = 4096;
   public static final int BLOCK_ID_LENGTH = 4096;
   /** @deprecated */
   @Deprecated
   public static final int DATA_LENGHT = 2048;
   public static final int DATA_LENGTH = 2048;
   public static final int BIOME_ARRAY_LENGTH = 256;
   protected static final int BYTES_PER_NIBBLE_PART = 2048;
   protected static final int CHUNK_SEGMENTS = 16;
   protected static final int NIBBLES_REQUIRED = 4;
   private boolean hasContinuous = true;
   private int startIndex;
   private int chunkX;
   private int chunkZ;
   private int chunkMask;
   private int extraMask;
   private int chunkSectionNumber;
   private int extraSectionNumber;
   private int size;
   private World world;
   private byte[] data;

   private ChunkPacketProcessor() {
   }

   public static ChunkPacketProcessor fromMapPacket(PacketContainer packet, World world) {
      if (!packet.getType().equals(Server.MAP_CHUNK)) {
         throw new IllegalArgumentException(packet + " must be a MAP_CHUNK packet.");
      } else {
         StructureModifier<Integer> ints = packet.getIntegers();
         StructureModifier<byte[]> byteArray = packet.getByteArrays();
         ChunkPacketProcessor processor = new ChunkPacketProcessor();
         processor.world = world;
         processor.chunkX = (Integer)ints.read(0);
         processor.chunkZ = (Integer)ints.read(1);
         processor.chunkMask = (Integer)ints.read(2);
         processor.extraMask = (Integer)ints.read(3);
         processor.data = (byte[])byteArray.read(1);
         processor.startIndex = 0;
         if (packet.getBooleans().size() > 0) {
            processor.hasContinuous = (Boolean)packet.getBooleans().read(0);
         }

         return processor;
      }
   }

   public void process(ChunkPacketProcessor.ChunkletProcessor processor) {
      int i;
      for(i = 0; i < 16; ++i) {
         if ((this.chunkMask & 1 << i) > 0) {
            ++this.chunkSectionNumber;
         }

         if ((this.extraMask & 1 << i) > 0) {
            ++this.extraSectionNumber;
         }
      }

      i = this.getSkylightCount();
      this.size = 2048 * ((4 + i) * this.chunkSectionNumber + this.extraSectionNumber) + (this.hasContinuous ? 256 : 0);
      if (this.getOffset(2) - this.startIndex <= this.data.length) {
         if (this.isChunkLoaded(this.world, this.chunkX, this.chunkZ)) {
            this.translate(processor);
         }

      }
   }

   protected int getSkylightCount() {
      return this.world.getEnvironment() == Environment.NORMAL ? 1 : 0;
   }

   private int getOffset(int nibbles) {
      return this.startIndex + nibbles * this.chunkSectionNumber * 2048;
   }

   private void translate(ChunkPacketProcessor.ChunkletProcessor processor) {
      int current = 4;
      ChunkPacketProcessor.ChunkOffsets offsets = new ChunkPacketProcessor.ChunkOffsets(this.getOffset(0), this.getOffset(2), this.getOffset(3), this.getSkylightCount() > 0 ? this.getOffset(current++) : -1, this.extraSectionNumber > 0 ? this.getOffset(current++) : -1);

      for(int i = 0; i < 16; ++i) {
         if ((this.chunkMask & 1 << i) > 0) {
            Location origin = new Location(this.world, (double)(this.chunkX << 4), (double)(i * 16), (double)(this.chunkZ << 4));
            processor.processChunklet(origin, this.data, offsets);
            offsets.incrementIdIndex();
         }

         if ((this.extraMask & 1 << i) > 0) {
            offsets.incrementExtraIndex();
         }
      }

      if (this.hasContinuous) {
         processor.processBiomeArray(new Location(this.world, (double)(this.chunkX << 4), 0.0D, (double)(this.chunkZ << 4)), this.data, this.startIndex + this.size - 256);
      }

   }

   private boolean isChunkLoaded(World world, int x, int z) {
      return world.isChunkLoaded(x, z);
   }

   public static class ChunkOffsets {
      private int blockIdOffset;
      private int dataOffset;
      private int lightOffset;
      private int skylightOffset;
      private int extraOffset;

      private ChunkOffsets(int blockIdOffset, int dataOffset, int lightOffset, int skylightOffset, int extraOffset) {
         this.blockIdOffset = blockIdOffset;
         this.dataOffset = dataOffset;
         this.lightOffset = lightOffset;
         this.skylightOffset = skylightOffset;
         this.extraOffset = extraOffset;
      }

      private void incrementIdIndex() {
         this.blockIdOffset += 4096;
         this.dataOffset += 2048;
         this.dataOffset += 2048;
         if (this.skylightOffset >= 0) {
            this.skylightOffset += 2048;
         }

      }

      private void incrementExtraIndex() {
         if (this.extraOffset >= 0) {
            this.extraOffset += 2048;
         }

      }

      public int getBlockIdOffset() {
         return this.blockIdOffset;
      }

      public int getDataOffset() {
         return this.dataOffset;
      }

      public int getLightOffset() {
         return this.lightOffset;
      }

      public int getSkylightOffset() {
         return this.skylightOffset;
      }

      public boolean hasSkylightOffset() {
         return this.skylightOffset >= 0;
      }

      public int getExtraOffset() {
         return this.extraOffset;
      }

      public boolean hasExtraOffset() {
         return this.extraOffset > 0;
      }

      // $FF: synthetic method
      ChunkOffsets(int x0, int x1, int x2, int x3, int x4, Object x5) {
         this(x0, x1, x2, x3, x4);
      }
   }

   public interface ChunkletProcessor {
      void processChunklet(Location var1, byte[] var2, ChunkPacketProcessor.ChunkOffsets var3);

      void processBiomeArray(Location var1, byte[] var2, int var3);
   }
}
