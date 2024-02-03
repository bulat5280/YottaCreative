package net.mineland.core.bukkit.modules.nms;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.PacketType.Play.Server;
import com.comphenix.protocol.events.PacketContainer;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multisets;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import net.minecraft.server.v1_12_R1.BlockPosition;
import net.minecraft.server.v1_12_R1.Blocks;
import net.minecraft.server.v1_12_R1.ChunkSection;
import net.minecraft.server.v1_12_R1.IBlockAccess;
import net.minecraft.server.v1_12_R1.IBlockData;
import net.minecraft.server.v1_12_R1.MapIcon;
import net.minecraft.server.v1_12_R1.Material;
import net.minecraft.server.v1_12_R1.MaterialMapColor;
import net.minecraft.server.v1_12_R1.NibbleArray;
import net.minecraft.server.v1_12_R1.Packet;
import net.minecraft.server.v1_12_R1.PacketPlayOutMap;
import net.minecraft.server.v1_12_R1.WorldMap;
import net.minecraft.server.v1_12_R1.MapIcon.Type;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_12_R1.map.CraftMapView;
import org.bukkit.entity.Player;
import org.bukkit.map.MapView;
import org.bukkit.util.Vector;
import ua.govnojon.libs.bukkitutil.RegionFile;
import ua.govnojon.libs.bukkitutil.nbt.CompressedStreamTools;
import ua.govnojon.libs.bukkitutil.nbt.NBTBase;
import ua.govnojon.libs.bukkitutil.nbt.NBTTagCompound;

public class NMSItemMap {
   private Field worldMapField;
   private ProtocolManager manager = ProtocolLibrary.getProtocolManager();
   private PacketContainer packetMap;

   public NMSItemMap() {
      try {
         this.worldMapField = CraftMapView.class.getDeclaredField("worldMap");
         this.worldMapField.setAccessible(true);
      } catch (Exception var2) {
         var2.printStackTrace();
      }

      this.packetMap = this.manager.createPacket(Server.MAP);
      this.packetMap.getIntegers().write(3, 128).write(4, 128);
      this.packetMap.getBytes().write(0, (byte)0);
      this.packetMap.getBooleans().write(0, false);
   }

   public void setMapData(MapView view, byte[] data) {
      WorldMap worldmap = this.getWorldMap(view);
      System.arraycopy(data, 0, worldmap.colors, 0, data.length);
   }

   public NMSItemMap.WorldMapData scanWorld(File worldDir, int centerX, int centerZ, int radius) throws IOException {
      int size = radius * 2;
      byte[] data = new byte[size * size];
      byte[] heightData = new byte[size * size];
      MaterialMapColor bedrockColor = Blocks.BEDROCK.getBlockData().getMaterial().r();
      Arrays.fill(data, (byte)bedrockColor.ad);
      int minX = centerX - radius;
      int maxX = centerX + radius - 1;
      int minZ = centerZ - radius;
      int maxZ = centerZ + radius - 1;
      int minChunkX = minX >> 4;
      int maxChunkX = maxX >> 4;
      int minChunkZ = minZ >> 4;
      int maxChunkZ = maxZ >> 4;
      int minRegionX = minChunkX >> 5;
      int maxRegionX = maxChunkX >> 5;
      int minRegionZ = minChunkZ >> 5;
      int maxRegionZ = maxChunkZ >> 5;

      for(int regionX = minRegionX; regionX <= maxRegionX; ++regionX) {
         for(int regionZ = minRegionZ; regionZ <= maxRegionZ; ++regionZ) {
            int maxOffChunkX = Math.min(31, maxChunkX - (regionX << 5));
            int maxOffChunkZ = Math.min(31, maxChunkZ - (regionZ << 5));
            int minOffChunkX = Math.max(0, minChunkX - (regionX << 5));
            int minOffChunkZ = Math.max(0, minChunkZ - (regionZ << 5));
            File file = new File(worldDir + File.separator + "region", "r." + regionX + "." + regionZ + ".mca");
            if (file.exists()) {
               RegionFile region = new RegionFile(file);
               Throwable var29 = null;

               try {
                  for(int offChunkX = minOffChunkX; offChunkX <= maxOffChunkX; ++offChunkX) {
                     for(int offChunkZ = minOffChunkZ; offChunkZ <= maxOffChunkZ; ++offChunkZ) {
                        if (region.hasChunk(offChunkX, offChunkZ)) {
                           DataInputStream stream = region.getChunkDataInputStream(offChunkX, offChunkZ);
                           Throwable var34 = null;

                           NBTTagCompound tag;
                           try {
                              tag = CompressedStreamTools.read(stream);
                           } catch (Throwable var64) {
                              var34 = var64;
                              throw var64;
                           } finally {
                              if (stream != null) {
                                 if (var34 != null) {
                                    try {
                                       stream.close();
                                    } catch (Throwable var63) {
                                       var34.addSuppressed(var63);
                                    }
                                 } else {
                                    stream.close();
                                 }
                              }

                           }

                           NBTTagCompound level = tag.getCompoundTag("Level");
                           ChunkSection[] sections = new ChunkSection[16];
                           Iterator var35 = level.getTagList("Sections").getTagList().iterator();

                           while(var35.hasNext()) {
                              NBTBase base = (NBTBase)var35.next();
                              NBTTagCompound sectionTag = (NBTTagCompound)base;
                              byte[] ids = sectionTag.getByteArray("Blocks");
                              byte[] datas = sectionTag.getByteArray("Data");
                              byte[] adds = sectionTag.hasKey("Add") ? sectionTag.getByteArray("Add") : null;
                              byte offY = sectionTag.getByte("Y");
                              ChunkSection section = sections[offY] = new ChunkSection(offY << 4, true);
                              section.getBlocks().a(ids, new NibbleArray(datas), adds == null ? null : new NibbleArray(adds));
                           }

                           int maxOffX = Math.min(15, maxX - ((regionX << 5) + offChunkX << 4));
                           int maxOffZ = Math.min(15, maxZ - ((regionZ << 5) + offChunkZ << 4));
                           int minOffX = Math.max(0, minX - ((regionX << 5) + offChunkX << 4));
                           int minOffZ = Math.max(0, minZ - ((regionZ << 5) + offChunkZ << 4));

                           for(int offX = minOffX; offX <= maxOffX; ++offX) {
                              for(int offZ = minOffZ; offZ <= maxOffZ; ++offZ) {
                                 int height = 256;

                                 MaterialMapColor color;
                                 do {
                                    --height;
                                    ChunkSection section = sections[height >> 4];
                                    IBlockData blockData;
                                    if (section == null) {
                                       blockData = Blocks.AIR.getBlockData();
                                       height &= 15;
                                    } else {
                                       blockData = section.getType(offX, height & 15, offZ);
                                    }

                                    color = blockData.a((IBlockAccess)null, (BlockPosition)null);
                                 } while(color.ad == 0 && height > 0);

                                 if (color.ad == 0) {
                                    color = bedrockColor;
                                 }

                                 int posX = ((regionX << 5) + offChunkX << 4) + offX - minX;
                                 int posZ = ((regionZ << 5) + offChunkZ << 4) + offZ - minZ;
                                 heightData[posX + posZ * size] = (byte)height;
                                 data[posX + posZ * size] = (byte)color.ad;
                              }
                           }
                        }
                     }
                  }
               } catch (Throwable var66) {
                  var29 = var66;
                  throw var66;
               } finally {
                  if (region != null) {
                     if (var29 != null) {
                        try {
                           region.close();
                        } catch (Throwable var62) {
                           var29.addSuppressed(var62);
                        }
                     } else {
                        region.close();
                     }
                  }

               }
            }
         }
      }

      return new NMSItemMap.WorldMapData(size, centerX, centerZ, data, heightData);
   }

   public byte[] renderMap(Vector center, int radius, NMSItemMap.WorldMapData mapData) {
      byte[] data = new byte[16384];
      int centerX = center.getBlockX();
      int centerZ = center.getBlockZ();
      byte bedrock = (byte)Material.STONE.r().ad;
      byte colorO = (byte)MaterialMapColor.o.ad;
      radius = Math.max(radius, 64);
      double scale = (double)radius / 64.0D;

      for(double x = (double)(centerX - radius); x < (double)(centerX + radius); x += scale) {
         double d0 = 0.0D;

         for(double z = (double)(centerZ - radius) - scale; z < (double)(centerZ + radius); z += scale) {
            HashMultiset<Byte> colors = HashMultiset.create((int)Math.pow(scale, 2.0D));
            double d1 = 0.0D;

            double d2;
            for(double blockX = x; blockX < x + scale; ++blockX) {
               for(d2 = z; d2 < z + scale; ++d2) {
                  int pos = (int)blockX - mapData.centerX + mapData.size / 2 + ((int)d2 - mapData.centerZ + mapData.size / 2) * mapData.size;

                  try {
                     d1 += (double)mapData.height[pos] / (scale * scale);
                     colors.add(mapData.colors[pos]);
                  } catch (Exception var28) {
                     var28.getCause();
                  }
               }
            }

            int posX = (int)((x - (double)(centerX - radius)) / scale);
            int posZ = (int)((z - (double)(centerZ - radius)) / scale);
            d2 = (d1 - d0) * 4.0D / (scale + 4.0D) + ((double)(posX + posZ & 1) - 0.5D) * 0.4D;
            byte b0 = 1;
            if (d2 > 0.6D) {
               b0 = 2;
            }

            if (d2 < -0.6D) {
               b0 = 0;
            }

            byte color = (Byte)Iterables.getFirst(Multisets.copyHighestCountFirst(colors), bedrock);
            if (color == colorO) {
               d2 = 0.0D + (double)(posX + posZ & 1) * 0.2D;
               b0 = 1;
               if (d2 < 0.5D) {
                  b0 = 2;
               }

               if (d2 > 0.9D) {
                  b0 = 0;
               }
            }

            d0 = d1;
            if (posZ >= 0) {
               byte b1 = data[posX + posZ * 128];
               byte b2 = (byte)(color * 4 + b0);
               if (b1 != b2) {
                  data[posX + posZ * 128] = b2;
               }
            }
         }
      }

      return data;
   }

   public void sendPlayersMapAndMap(List<Player> players, int mapId, byte[] canvas, Location center, double scale, List<Player> icons) {
      Collection<MapIcon> mapIcons = Arrays.asList(this.buildMapIcon(icons, center, scale));
      PacketPlayOutMap packet = new PacketPlayOutMap(mapId, (byte)0, false, mapIcons, canvas, 0, 0, 128, 128);
      this.broadcast(players, packet);
   }

   public void sendPlayersMap(Collection<Player> players, int mapId, Location center, double scale, List<Player> icons) {
      Collection<MapIcon> mapIcons = Arrays.asList(this.buildMapIcon(icons, center, scale));
      PacketPlayOutMap packet = new PacketPlayOutMap(mapId, (byte)0, false, mapIcons, (byte[])null, 0, 0, 0, 0);
      this.broadcast(players, packet);
   }

   private MapIcon[] buildMapIcon(List<Player> icons, Location center, double scale) {
      int centerX = center.getBlockX();
      int centerZ = center.getBlockZ();
      return (MapIcon[])icons.stream().map((player) -> {
         Type mapicon_type = Type.PLAYER;
         Location loc = player.getLocation();
         double d0 = loc.getX();
         double d1 = loc.getZ();
         double d2 = (double)loc.getYaw();
         World world = loc.getWorld();
         float f = (float)(d0 - (double)centerX) / (float)scale;
         float f1 = (float)(d1 - (double)centerZ) / (float)scale;
         byte b0 = (byte)((int)((double)(f * 2.0F) + 0.5D));
         byte b1 = (byte)((int)((double)(f1 * 2.0F) + 0.5D));
         byte b2;
         if (f >= -63.0F && f1 >= -63.0F && f <= 63.0F && f1 <= 63.0F) {
            d2 += d2 < 0.0D ? -8.0D : 8.0D;
            b2 = (byte)((int)(d2 * 16.0D / 360.0D));
            if (((CraftWorld)world).getHandle().dimension < 0) {
               int j = (int)(((CraftWorld)world).getHandle().getWorldData().getDayTime() / 10L);
               b2 = (byte)(j * j * 34187121 + j * 121 >> 15 & 15);
            }
         } else {
            if (Math.abs(f) < 320.0F && Math.abs(f1) < 320.0F) {
               mapicon_type = Type.PLAYER_OFF_MAP;
            } else {
               mapicon_type = Type.PLAYER_OFF_LIMITS;
            }

            b2 = 0;
            if (f <= -63.0F) {
               b0 = -128;
            }

            if (f1 <= -63.0F) {
               b1 = -128;
            }

            if (f >= 63.0F) {
               b0 = 127;
            }

            if (f1 >= 63.0F) {
               b1 = 127;
            }
         }

         return new MapIcon(mapicon_type, b0, b1, b2);
      }).toArray((x$0) -> {
         return new MapIcon[x$0];
      });
   }

   public WorldMap getWorldMap(MapView view) {
      try {
         return (WorldMap)this.worldMapField.get(view);
      } catch (IllegalAccessException var3) {
         throw new RuntimeException(var3);
      }
   }

   private void broadcast(Collection<Player> players, Packet packet) {
      NMS.getSenderPacket().submit(() -> {
         players.forEach((player) -> {
            ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
         });
      });
   }

   public static class WorldMapData {
      public int centerX;
      public int centerZ;
      public int size;
      public byte[] colors;
      public byte[] height;

      public WorldMapData(int size, int centerX, int centerZ, byte[] colors, byte[] height) {
         this.size = size;
         this.centerX = centerX;
         this.centerZ = centerZ;
         this.colors = colors;
         this.height = height;
      }

      public int getSize() {
         return this.size;
      }
   }
}
