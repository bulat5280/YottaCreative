package ua.govnojon.libs.bukkitutil;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.World;
import ua.govnojon.libs.bukkitutil.nbt.CompressedStreamTools;
import ua.govnojon.libs.bukkitutil.nbt.NBTBase;
import ua.govnojon.libs.bukkitutil.nbt.NBTTagCompound;

public class WorldReader implements Closeable {
   private File worldDir;
   private Map<WorldReader.Point, Optional<WorldReader.Region>> regionCache = new HashMap();

   public WorldReader(File worldDir) {
      this.worldDir = worldDir;
   }

   public WorldReader(World world) {
      this.worldDir = world.getWorldFolder();
   }

   public void forEachBlockId(WorldReader.ConsumerBlockId consumer) {
      File[] var2 = (new File(this.worldDir + File.separator + "region")).listFiles((filex) -> {
         return filex.getName().endsWith(".mca");
      });
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         File file = var2[var4];
         WorldReader.Point regionPoint = this.getRegionPoint(file);
         WorldReader.Region region = this.getRegion(regionPoint.getX(), regionPoint.getZ());
         if (region != null) {
            WorldReader.Point point = this.getRegionPoint(file);

            for(int offChunkX = 0; offChunkX < 32; ++offChunkX) {
               for(int offChunkZ = 0; offChunkZ < 32; ++offChunkZ) {
                  WorldReader.Chunk chunk = region.getChunk(offChunkX, offChunkZ);
                  int x = (point.getX() << 5) + offChunkX << 4;
                  int z = (point.getZ() << 5) + offChunkZ << 4;
                  if (chunk != null) {
                     WorldReader.Chunk.Section[] sections = chunk.getSections();

                     for(int sectionY = 0; sectionY < sections.length; ++sectionY) {
                        WorldReader.Chunk.Section section = sections[sectionY];
                        if (section != null) {
                           for(int offX = 0; offX < 16; ++offX) {
                              for(int offZ = 0; offZ < 16; ++offZ) {
                                 for(int offY = 0; offY < 16; ++offY) {
                                    consumer.accept(x + offX, (sectionY << 4) + offY, z + offZ, section.getBlockId(offX, offY, offZ));
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }

   }

   public int getBlockId(int x, int y, int z) {
      WorldReader.Chunk chunk = this.getChunk(x >> 4, z >> 4);
      return chunk == null ? 0 : chunk.getBlockId(x & 15, y, z & 15);
   }

   public int getHeight(int x, int z) {
      WorldReader.Chunk chunk = this.getChunk(x >> 4, z >> 4);
      return chunk == null ? 0 : chunk.getHeight(x & 15, z & 15);
   }

   public int getBlockData(int x, int y, int z) {
      WorldReader.Chunk chunk = this.getChunk(x >> 4, z >> 4);
      return chunk == null ? 0 : chunk.getBlockData(x & 15, y, z & 15);
   }

   public WorldReader.Chunk getChunk(int chunkX, int chunkZ) {
      WorldReader.Region region = this.getRegion(chunkX >> 5, chunkZ >> 5);
      return region == null ? null : region.getChunk(chunkX & 31, chunkZ & 31);
   }

   private WorldReader.Region getRegion(int regionX, int regionZ) {
      return (WorldReader.Region)((Optional)this.regionCache.computeIfAbsent(new WorldReader.Point(regionX, regionZ), (key) -> {
         File file = new File(this.worldDir + File.separator + "region", "r." + regionX + "." + regionZ + ".mca");
         return file.exists() ? Optional.of(new WorldReader.Region(new RegionFile(file))) : Optional.empty();
      })).orElse((Object)null);
   }

   private WorldReader.Point getRegionPoint(File file) {
      String[] dataName = StringUtils.substring(file.getName(), 2, -4).split("\\.");
      int regionX = Integer.parseInt(dataName[0]);
      int regionZ = Integer.parseInt(dataName[1]);
      return new WorldReader.Point(regionX, regionZ);
   }

   public void close() {
      this.regionCache.values().forEach((optional) -> {
         optional.ifPresent((region) -> {
            try {
               region.close();
            } catch (IOException var2) {
               throw new RuntimeException(var2);
            }
         });
      });
      this.regionCache.clear();
   }

   public static class Chunk implements Closeable {
      private DataInputStream stream;
      private NBTTagCompound tag;
      private WorldReader.Chunk.Section[] sections = new WorldReader.Chunk.Section[16];
      private int[] heightMap;

      public Chunk(WorldReader.Point point, RegionFile region) {
         this.stream = region.getChunkDataInputStream(point.getX(), point.getZ());

         try {
            this.tag = CompressedStreamTools.read(this.stream);
         } catch (IOException var10) {
            throw new RuntimeException(var10);
         }

         NBTTagCompound level = this.tag.getCompoundTag("Level");
         this.heightMap = level.getIntArray("HeightMap");

         NBTTagCompound sectionTag;
         byte[] ids;
         byte[] datas;
         byte[] adds;
         for(Iterator var4 = level.getTagList("Sections").getTagList().iterator(); var4.hasNext(); this.sections[sectionTag.getByte("Y")] = new WorldReader.Chunk.Section(ids, datas, adds)) {
            NBTBase base = (NBTBase)var4.next();
            sectionTag = (NBTTagCompound)base;
            ids = sectionTag.getByteArray("Blocks");
            datas = sectionTag.getByteArray("Data");
            adds = sectionTag.hasKey("Add") ? sectionTag.getByteArray("Add") : null;
         }

      }

      public WorldReader.Chunk.Section[] getSections() {
         return this.sections;
      }

      public int getHeight(int offX, int offZ) {
         return this.heightMap[offX << 4 | offZ];
      }

      public void close() throws IOException {
         this.stream.close();
      }

      public int getBlockId(int offX, int y, int offZ) {
         WorldReader.Chunk.Section section = this.sections[y >> 4];
         return section == null ? 0 : section.getBlockId(offX, y & 15, offZ);
      }

      public int getBlockData(int offX, int y, int offZ) {
         WorldReader.Chunk.Section section = this.sections[y >> 4];
         return section == null ? 0 : section.getBlockData(offX, y & 15, offZ);
      }

      private static class Section {
         private byte[] ids;
         private byte[] datas;
         private byte[] adds;

         private Section(byte[] ids, byte[] datas, byte[] adds) {
            this.ids = ids;
            this.datas = datas;
            this.adds = adds;
         }

         private static byte nibble4(byte[] arr, int index) {
            return (byte)(index % 2 == 0 ? arr[index / 2] & 15 : arr[index / 2] >> 4 & 15);
         }

         public int getBlockId(int offX, int offY, int offZ) {
            int blockPos = offY * 16 * 16 + offZ * 16 + offX;
            byte blockID_a = this.ids[blockPos];
            byte blockID_b = this.adds == null ? 0 : nibble4(this.adds, blockPos);
            return blockID_a + (blockID_b << 8);
         }

         public int getBlockData(int offX, int offY, int offZ) {
            int blockPos = offY * 16 * 16 + offZ * 16 + offX;
            return nibble4(this.datas, blockPos);
         }

         // $FF: synthetic method
         Section(byte[] x0, byte[] x1, byte[] x2, Object x3) {
            this(x0, x1, x2);
         }
      }
   }

   private static class Region implements Closeable {
      private RegionFile region;
      private Map<WorldReader.Point, Optional<WorldReader.Chunk>> chunkCache;

      private Region(RegionFile region) {
         this.chunkCache = new HashMap();
         this.region = region;
      }

      public WorldReader.Chunk getChunk(int offChunkX, int offChunkZ) {
         return (WorldReader.Chunk)((Optional)this.chunkCache.computeIfAbsent(new WorldReader.Point(offChunkX, offChunkZ), (point) -> {
            return this.region.hasChunk(point.getX(), point.getZ()) ? Optional.of(new WorldReader.Chunk(point, this.region)) : Optional.empty();
         })).orElse((Object)null);
      }

      public void close() throws IOException {
         this.chunkCache.values().forEach((optional) -> {
            optional.ifPresent((chunk) -> {
               try {
                  chunk.close();
               } catch (IOException var2) {
                  throw new RuntimeException(var2);
               }
            });
         });
         this.chunkCache.clear();
         this.region.close();
      }

      // $FF: synthetic method
      Region(RegionFile x0, Object x1) {
         this(x0);
      }
   }

   private static class Point {
      private final int x;
      private final int z;

      private Point(int x, int z) {
         this.x = x;
         this.z = z;
      }

      public int getX() {
         return this.x;
      }

      public int getZ() {
         return this.z;
      }

      public boolean equals(Object o) {
         if (this == o) {
            return true;
         } else if (o != null && this.getClass() == o.getClass()) {
            WorldReader.Point point = (WorldReader.Point)o;
            return this.x == point.x && this.z == point.z;
         } else {
            return false;
         }
      }

      public int hashCode() {
         return this.x * 31 + this.z;
      }

      // $FF: synthetic method
      Point(int x0, int x1, Object x2) {
         this(x0, x1);
      }
   }

   public interface ConsumerBlockId {
      void accept(int var1, int var2, int var3, int var4);
   }
}
