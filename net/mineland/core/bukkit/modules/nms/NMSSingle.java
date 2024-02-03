package net.mineland.core.bukkit.modules.nms;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import net.minecraft.server.v1_12_R1.AxisAlignedBB;
import net.minecraft.server.v1_12_R1.Block;
import net.minecraft.server.v1_12_R1.BlockPosition;
import net.minecraft.server.v1_12_R1.Chunk;
import net.minecraft.server.v1_12_R1.ChunkSection;
import net.minecraft.server.v1_12_R1.Entity;
import net.minecraft.server.v1_12_R1.EntityLiving;
import net.minecraft.server.v1_12_R1.EntityPlayer;
import net.minecraft.server.v1_12_R1.EntityTrackerEntry;
import net.minecraft.server.v1_12_R1.IBlockData;
import net.minecraft.server.v1_12_R1.MathHelper;
import net.minecraft.server.v1_12_R1.MovingObjectPosition;
import net.minecraft.server.v1_12_R1.NBTBase;
import net.minecraft.server.v1_12_R1.NBTTagCompound;
import net.minecraft.server.v1_12_R1.NibbleArray;
import net.minecraft.server.v1_12_R1.PacketPlayOutTileEntityData;
import net.minecraft.server.v1_12_R1.PlayerChunk;
import net.minecraft.server.v1_12_R1.PlayerConnection;
import net.minecraft.server.v1_12_R1.TileEntity;
import net.minecraft.server.v1_12_R1.TileEntitySign;
import net.minecraft.server.v1_12_R1.TileEntitySkull;
import net.minecraft.server.v1_12_R1.Vec3D;
import net.minecraft.server.v1_12_R1.World;
import net.minecraft.server.v1_12_R1.WorldServer;
import net.mineland.core.bukkit.modules.message.Message;
import net.mineland.core.bukkit.modules.user.Lang;
import net.mineland.core.bukkit.modules.user.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftArmorStand;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.util.Vector;
import ua.govnojon.libs.bukkitutil.LocationUtil;
import ua.govnojon.libs.bukkitutil.RegionFile;
import ua.govnojon.libs.bukkitutil.Vec3;
import ua.govnojon.libs.bukkitutil.chatcomponent.ChatComponentUtil;
import ua.govnojon.libs.bukkitutil.nbt.CompressedStreamTools;
import ua.govnojon.libs.myjava.CollectionUtil;
import ua.govnojon.libs.myjava.MyObject;
import ua.govnojon.libs.myjava.Try;

public class NMSSingle {
   private Field strengthBlock = (Field)Try.unchecked(() -> {
      Field strength = Block.class.getDeclaredField("strength");
      strength.setAccessible(true);
      return strength;
   });
   private Field Entity_au = (Field)Try.unchecked(() -> {
      Field au = Entity.class.getDeclaredField("au");
      au.setAccessible(true);
      return au;
   });
   private Field Entity_tracker = FieldUtils.getDeclaredField(Entity.class, "tracker", true);
   private Field EntityTrackerEntry_e = FieldUtils.getDeclaredField(EntityTrackerEntry.class, "e", true);

   public void rayTrace(World world, List<NMSSingle.BukkitMovingObjectPosition> list, Vec3D vec3d, Vec3D vec3d1) {
      if (!Double.isNaN(vec3d.x) && !Double.isNaN(vec3d.y) && !Double.isNaN(vec3d.z) && !Double.isNaN(vec3d1.x) && !Double.isNaN(vec3d1.y) && !Double.isNaN(vec3d1.z)) {
         int i = MathHelper.floor(vec3d1.x);
         int j = MathHelper.floor(vec3d1.y);
         int k = MathHelper.floor(vec3d1.z);
         int l = MathHelper.floor(vec3d.x);
         int i1 = MathHelper.floor(vec3d.y);
         int j1 = MathHelper.floor(vec3d.z);
         BlockPosition blockposition = new BlockPosition(l, i1, j1);
         IBlockData iblockdata = world.getType(blockposition);
         Block block = iblockdata.getBlock();
         if (block.a(iblockdata, false)) {
            MovingObjectPosition movingobjectposition1 = iblockdata.a(world, blockposition, vec3d, vec3d1);
            if (movingobjectposition1 != null) {
               list.add(new NMSSingle.BukkitMovingObjectPosition(movingobjectposition1));
            }
         }

         int var40 = 200;

         while(var40-- >= 0) {
            if (Double.isNaN(vec3d.x) || Double.isNaN(vec3d.y) || Double.isNaN(vec3d.z)) {
               return;
            }

            if (l == i && i1 == j && j1 == k) {
               return;
            }

            boolean flag3 = true;
            boolean flag4 = true;
            boolean flag5 = true;
            double d0 = 999.0D;
            double d1 = 999.0D;
            double d2 = 999.0D;
            if (i > l) {
               d0 = (double)l + 1.0D;
            } else if (i < l) {
               d0 = (double)l + 0.0D;
            } else {
               flag3 = false;
            }

            if (j > i1) {
               d1 = (double)i1 + 1.0D;
            } else if (j < i1) {
               d1 = (double)i1 + 0.0D;
            } else {
               flag4 = false;
            }

            if (k > j1) {
               d2 = (double)j1 + 1.0D;
            } else if (k < j1) {
               d2 = (double)j1 + 0.0D;
            } else {
               flag5 = false;
            }

            double d3 = 999.0D;
            double d4 = 999.0D;
            double d5 = 999.0D;
            double d6 = vec3d1.x - vec3d.x;
            double d7 = vec3d1.y - vec3d.y;
            double d8 = vec3d1.z - vec3d.z;
            if (flag3) {
               d3 = (d0 - vec3d.x) / d6;
            }

            if (flag4) {
               d4 = (d1 - vec3d.y) / d7;
            }

            if (flag5) {
               d5 = (d2 - vec3d.z) / d8;
            }

            if (d3 == -0.0D) {
               d3 = -1.0E-4D;
            }

            if (d4 == -0.0D) {
               d4 = -1.0E-4D;
            }

            if (d5 == -0.0D) {
               d5 = -1.0E-4D;
            }

            net.minecraft.server.v1_12_R1.EnumDirection enumdirection;
            if (d3 < d4 && d3 < d5) {
               enumdirection = i > l ? net.minecraft.server.v1_12_R1.EnumDirection.WEST : net.minecraft.server.v1_12_R1.EnumDirection.EAST;
               vec3d = new Vec3D(d0, vec3d.y + d7 * d3, vec3d.z + d8 * d3);
            } else if (d4 < d5) {
               enumdirection = j > i1 ? net.minecraft.server.v1_12_R1.EnumDirection.DOWN : net.minecraft.server.v1_12_R1.EnumDirection.UP;
               vec3d = new Vec3D(vec3d.x + d6 * d4, d1, vec3d.z + d8 * d4);
            } else {
               enumdirection = k > j1 ? net.minecraft.server.v1_12_R1.EnumDirection.NORTH : net.minecraft.server.v1_12_R1.EnumDirection.SOUTH;
               vec3d = new Vec3D(vec3d.x + d6 * d5, vec3d.y + d7 * d5, d2);
            }

            l = MathHelper.floor(vec3d.x) - (enumdirection == net.minecraft.server.v1_12_R1.EnumDirection.EAST ? 1 : 0);
            i1 = MathHelper.floor(vec3d.y) - (enumdirection == net.minecraft.server.v1_12_R1.EnumDirection.UP ? 1 : 0);
            j1 = MathHelper.floor(vec3d.z) - (enumdirection == net.minecraft.server.v1_12_R1.EnumDirection.SOUTH ? 1 : 0);
            blockposition = new BlockPosition(l, i1, j1);
            IBlockData iblockdata1 = world.getType(blockposition);
            Block block1 = iblockdata1.getBlock();
            if (block1.a(iblockdata1, false)) {
               MovingObjectPosition movingobjectposition2 = iblockdata1.a(world, blockposition, vec3d, vec3d1);
               if (movingobjectposition2 != null) {
                  list.add(new NMSSingle.BukkitMovingObjectPosition(movingobjectposition2));
               }
            }
         }
      }

   }

   public List<NMSSingle.BukkitMovingObjectPosition> scanCollision(org.bukkit.World bukkitWorld, Vec3 loc, Vec3 mot, int radius, List<Integer> idsIgnore) {
      return this.scanCollision(bukkitWorld, loc.getX(), loc.getY(), loc.getZ(), mot.getX(), mot.getY(), mot.getZ(), radius, idsIgnore);
   }

   public List<NMSSingle.BukkitMovingObjectPosition> scanCollision(org.bukkit.World bukkitWorld, double locX, double locY, double locZ, double motX, double motY, double motZ, int radius, List<Integer> idsIgnore) {
      List<NMSSingle.BukkitMovingObjectPosition> list = new LinkedList();
      WorldServer world = ((CraftWorld)bukkitWorld).getHandle();
      Vec3D from = new Vec3D(locX, locY, locZ);
      Vec3D to = new Vec3D(locX + motX, locY + motY, locZ + motZ);
      this.rayTrace(world, list, from, to);
      from = new Vec3D(locX, locY, locZ);
      to = new Vec3D(locX + motX, locY + motY, locZ + motZ);
      List<Entity> entities = world.getEntities((Entity)null, (new AxisAlignedBB(locX - (double)radius, locY - (double)radius, locZ - (double)radius, locX + (double)radius, locY + (double)radius, locZ + (double)radius)).b(motX, motY, motZ).g(1.0D));
      Iterator var21 = entities.iterator();

      while(var21.hasNext()) {
         Entity find = (Entity)var21.next();
         if (find.isInteractable() && !idsIgnore.contains(find.getId()) && find instanceof EntityLiving) {
            AxisAlignedBB axisalignedbb = find.getBoundingBox().g(0.30000001192092896D);
            MovingObjectPosition temp = axisalignedbb.b(from, to);
            if (temp != null) {
               list.add(new NMSSingle.BukkitMovingObjectPosition(new MovingObjectPosition(find)));
            }
         }
      }

      Vector fromVec = new Vector(from.x, from.y, from.z);
      list.sort(Comparator.comparingInt((o) -> {
         return (int)o.pos.distanceSquared(fromVec);
      }));
      return list;
   }

   public ItemStack setSkinHead(String value) {
      ItemStack itemStack = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
      SkullMeta skullMeta = (SkullMeta)itemStack.getItemMeta();
      (new MyObject(skullMeta)).setField("profile", this.createProfile(value));
      itemStack.setItemMeta(skullMeta);
      return itemStack;
   }

   public String getSkinHead(ItemStack itemStack) {
      SkullMeta skullMeta = (SkullMeta)itemStack.getItemMeta();
      GameProfile profile = (GameProfile)(new MyObject(skullMeta)).get("profile");
      Collection<Property> textures = profile.getProperties().get("textures");
      return ((Property)textures.stream().findAny().get()).getName();
   }

   public void setSkinSkull(org.bukkit.block.Block block, String value) {
      if (block.getType() == Material.SKULL) {
         TileEntitySkull tileEntitySkull = (TileEntitySkull)((CraftWorld)block.getWorld()).getHandle().getTileEntity(new BlockPosition(block.getX(), block.getY(), block.getZ()));
         GameProfile profile = this.createProfile(value);
         if (tileEntitySkull != null) {
            tileEntitySkull.setGameProfile(profile);
         }

      }
   }

   private GameProfile createProfile(String texture) {
      GameProfile gameProfile = new GameProfile(UUID.nameUUIDFromBytes(texture.getBytes()), texture);
      gameProfile.getProperties().put("textures", new Property("textures", texture));
      return gameProfile;
   }

   public List<org.bukkit.entity.Entity> getNearbyEntities(Location min, Location max) {
      double minX = min.getX();
      double minY = min.getY();
      double minZ = min.getZ();
      double maxX = max.getX();
      double maxY = max.getY();
      double maxZ = max.getZ();
      return (List)((CraftWorld)min.getWorld()).getHandle().entityList.stream().filter((entity) -> {
         return entity.locX >= minX && entity.locX <= maxX && entity.locY >= minY && entity.locY <= maxY && entity.locZ >= minZ && entity.locZ <= maxZ;
      }).map(Entity::getBukkitEntity).filter(Objects::nonNull).collect(Collectors.toList());
   }

   public void fixHidePlayer(Player player, Player other) {
      EntityPlayer handle = ((CraftPlayer)other).getHandle();
      boolean joining = handle.joining;
      handle.joining = false;
      player.hidePlayer(other);
      handle.joining = joining;
   }

   public ItemStack checkFix(ItemStack item, Pattern pattern) {
      net.minecraft.server.v1_12_R1.ItemStack itemStack = CraftItemStack.asNMSCopy(item);
      NBTTagCompound tag = itemStack.getTag();
      if (tag == null) {
         return null;
      } else {
         try {
            boolean change = false;
            Iterator var6 = tag.getCompound("BlockEntityTag").getList("Items", 10).list.iterator();

            while(var6.hasNext()) {
               NBTBase base = (NBTBase)var6.next();
               NBTTagCompound display = ((NBTTagCompound)base).getCompound("tag").getCompound("display");
               String data = display.getString("Name");
               if (StringUtils.contains(data, "§l")) {
                  data = pattern.matcher(data).replaceAll("§r$0");
                  display.setString("Name", data);
                  change |= true;
               }
            }

            if (change) {
               itemStack.setTag(tag);
               return CraftItemStack.asCraftMirror(itemStack);
            } else {
               return null;
            }
         } catch (Exception var10) {
            return null;
         }
      }
   }

   public ItemStack checkTranslateBox(ItemStack item, Lang lang) {
      net.minecraft.server.v1_12_R1.ItemStack itemStack = CraftItemStack.asNMSCopy(item);
      NBTTagCompound tag = itemStack.getTag();
      if (tag == null) {
         return null;
      } else {
         try {
            boolean change = false;
            Iterator var6 = tag.getCompound("BlockEntityTag").getList("Items", 10).list.iterator();

            while(var6.hasNext()) {
               NBTBase base = (NBTBase)var6.next();
               NBTTagCompound display = ((NBTTagCompound)base).getCompound("tag").getCompound("display");
               String data = display.getString("Name");
               if (data.startsWith("lang:")) {
                  String message = Message.getMessage(lang, data.substring(5));
                  display.setString("Name", message.split("(::|\n|\r)")[0]);
                  change |= true;
               }
            }

            if (change) {
               itemStack.setTag(tag);
               return CraftItemStack.asCraftMirror(itemStack);
            } else {
               return null;
            }
         } catch (Exception var11) {
            return null;
         }
      }
   }

   public void sendSigns(User user, Location toLoc, Location fromLoc, int distance) {
      Player player = user.getPlayer();
      List<TileEntitySign> to = this.getSigns(((CraftPlayer)player).getHandle(), toLoc, distance);
      List<TileEntitySign> from = this.getSigns(((CraftPlayer)player).getHandle(), fromLoc, distance);
      CollectionUtil.removeSharedAll(to, from);
      this.sendRemoveAllSign(player, from);
      this.sendSpawnAllSign(player, to);
   }

   public void sendSpawnAllSign(Player player, List<TileEntitySign> to) {
      PlayerConnection connection = ((CraftPlayer)player).getHandle().playerConnection;
      NMS.getSenderPacket().submit(() -> {
         Iterator var2 = to.iterator();

         while(var2.hasNext()) {
            TileEntity entity = (TileEntity)var2.next();
            if (entity instanceof TileEntitySign) {
               connection.sendPacket(entity.getUpdatePacket());
            }
         }

      });
   }

   private void sendRemoveAllSign(Player player, List<TileEntitySign> from) {
      PlayerConnection connection = ((CraftPlayer)player).getHandle().playerConnection;
      NMS.getSenderPacket().submit(() -> {
         Iterator var2 = from.iterator();

         while(var2.hasNext()) {
            TileEntitySign entity = (TileEntitySign)var2.next();
            NBTTagCompound compound = entity.d();

            for(int i = 0; i < 4; ++i) {
               compound.setString("Text" + (i + 1), ChatComponentUtil.textToJson(""));
            }

            connection.sendPacket(new PacketPlayOutTileEntityData(entity.getPosition(), 9, compound));
         }

      });
   }

   private List<TileEntitySign> getSigns(EntityPlayer entityPlayer, Location loc, int distance) {
      int centerX = loc.getBlockX() >> 4;
      int centerZ = loc.getBlockZ() >> 4;
      int radius = distance >> 4;
      List<TileEntitySign> signs = new LinkedList();

      for(int chunkX = centerX - radius; chunkX <= centerX + radius; ++chunkX) {
         for(int chunkZ = centerZ - radius; chunkZ <= centerZ + radius; ++chunkZ) {
            PlayerChunk playerChunk = ((CraftWorld)loc.getWorld()).getHandle().getPlayerChunkMap().getChunk(chunkX, chunkZ);
            if (playerChunk != null && playerChunk.d(entityPlayer) && playerChunk.e()) {
               Chunk chunk = playerChunk.chunk;
               Iterator var12 = chunk.getTileEntities().values().iterator();

               while(var12.hasNext()) {
                  TileEntity tileEntity = (TileEntity)var12.next();
                  if (tileEntity instanceof TileEntitySign) {
                     BlockPosition pos = tileEntity.getPosition();
                     if (!LocationUtil.hasDistance((double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), loc.getX(), loc.getY(), loc.getZ(), (double)distance)) {
                        signs.add((TileEntitySign)tileEntity);
                     }
                  }
               }
            }
         }
      }

      return signs;
   }

   public void sendSpawnAllSign(Player player, Location loc, int distance) {
      List<TileEntitySign> to = this.getSigns(((CraftPlayer)player).getHandle(), loc, distance);
      this.sendSpawnAllSign(player, to);
   }

   public float getStrength(Material m) {
      return (Float)Try.unchecked(() -> {
         return (Float)this.strengthBlock.get(Block.getById(m.getId()));
      });
   }

   public List<NMSSingle.ResultScan> scanMap(File dirMap, List<Integer> search) {
      List<NMSSingle.ResultScan> result = new ArrayList();
      File regionDir = new File(dirMap, "region");
      if (regionDir.exists()) {
         File[] var5 = regionDir.listFiles((filex) -> {
            return filex.getName().endsWith(".mca");
         });
         int var6 = var5.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            File file = var5[var7];
            String[] dataName = StringUtils.substring(file.getName(), 2, -4).split("\\.");
            int regionX = Integer.parseInt(dataName[0]);
            int regionZ = Integer.parseInt(dataName[1]);

            try {
               RegionFile region = new RegionFile(file);
               Throwable var13 = null;

               try {
                  for(int offChunkX = 0; offChunkX < 32; ++offChunkX) {
                     for(int offChunkZ = 0; offChunkZ < 32; ++offChunkZ) {
                        if (region.hasChunk(offChunkX, offChunkZ)) {
                           DataInputStream stream = region.getChunkDataInputStream(offChunkX, offChunkZ);
                           Throwable var18 = null;

                           ua.govnojon.libs.bukkitutil.nbt.NBTTagCompound tag;
                           try {
                              tag = CompressedStreamTools.read(stream);
                           } catch (Throwable var51) {
                              var18 = var51;
                              throw var51;
                           } finally {
                              if (stream != null) {
                                 if (var18 != null) {
                                    try {
                                       stream.close();
                                    } catch (Throwable var50) {
                                       var18.addSuppressed(var50);
                                    }
                                 } else {
                                    stream.close();
                                 }
                              }

                           }

                           ua.govnojon.libs.bukkitutil.nbt.NBTTagCompound level = tag.getCompoundTag("Level");
                           Iterator var57 = level.getTagList("Sections").getTagList().iterator();

                           while(var57.hasNext()) {
                              ua.govnojon.libs.bukkitutil.nbt.NBTBase base = (ua.govnojon.libs.bukkitutil.nbt.NBTBase)var57.next();
                              ua.govnojon.libs.bukkitutil.nbt.NBTTagCompound sectionTag = (ua.govnojon.libs.bukkitutil.nbt.NBTTagCompound)base;
                              byte[] ids = sectionTag.getByteArray("Blocks");
                              byte[] datas = sectionTag.getByteArray("Data");
                              byte[] adds = sectionTag.hasKey("Add") ? sectionTag.getByteArray("Add") : null;
                              byte offSectionY = sectionTag.getByte("Y");
                              ChunkSection section = new ChunkSection(offSectionY << 4, true);
                              section.getBlocks().a(ids, new NibbleArray(datas), adds == null ? null : new NibbleArray(adds));

                              for(int offX = 0; offX < 16; ++offX) {
                                 for(int offZ = 0; offZ < 16; ++offZ) {
                                    for(int offY = 0; offY < 16; ++offY) {
                                       int id = Block.getCombinedId(section.getType(offX, offY, offZ));
                                       if (search.contains(id)) {
                                          result.add(new NMSSingle.ResultScan(id, new Vector(((regionX << 5) + offChunkX << 4) + offX, (offSectionY << 4) + offY, ((regionZ << 5) + offChunkZ << 4) + offZ)));
                                       }
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }
               } catch (Throwable var53) {
                  var13 = var53;
                  throw var53;
               } finally {
                  if (region != null) {
                     if (var13 != null) {
                        try {
                           region.close();
                        } catch (Throwable var49) {
                           var13.addSuppressed(var49);
                        }
                     } else {
                        region.close();
                     }
                  }

               }
            } catch (IOException var55) {
               var55.printStackTrace();
            }
         }
      }

      return result;
   }

   public void setVehicleOnly(org.bukkit.entity.Entity entity, org.bukkit.entity.Entity vehicle) {
      try {
         this.Entity_au.set(((CraftEntity)entity).getHandle(), ((CraftEntity)vehicle).getHandle());
      } catch (Throwable var4) {
         throw var4;
      }
   }

   public void addPassengerListOnly(ArmorStand stand, Player player) {
      ((CraftArmorStand)stand).getHandle().passengers.add(((CraftPlayer)player).getHandle());
   }

   public void clearPassengerListOnly(ArmorStand armorStand) {
      ((CraftArmorStand)armorStand).getHandle().passengers.clear();
   }

   public void addPlayerToTrackerEntity(org.bukkit.entity.Entity entity, Player player) {
      try {
         Entity handle = ((CraftEntity)entity).getHandle();
         EntityTrackerEntry tracker = (EntityTrackerEntry)this.Entity_tracker.get(handle);
         if (tracker == null) {
            this.Entity_tracker.set(handle, tracker = new EntityTrackerEntry(handle, 16, 16, 16, true));
         }

         tracker.trackedPlayerMap.put(((CraftPlayer)player).getHandle(), true);
      } catch (Throwable var5) {
         throw var5;
      }
   }

   public void removePlayerFromTrackerEntity(org.bukkit.entity.Entity entity, Player player) {
      try {
         Entity handle = ((CraftEntity)entity).getHandle();
         EntityTrackerEntry tracker = (EntityTrackerEntry)this.Entity_tracker.get(handle);
         if (tracker != null) {
            tracker.trackedPlayerMap.remove(((CraftPlayer)player).getHandle());
         }

      } catch (Throwable var5) {
         throw var5;
      }
   }

   public void setTrackerRangeBlocks(org.bukkit.entity.Entity entity, int rangeBlocks) {
      try {
         EntityTrackerEntry entry = (EntityTrackerEntry)this.Entity_tracker.get(((CraftEntity)entity).getHandle());
         this.EntityTrackerEntry_e.set(entry, rangeBlocks);
      } catch (Throwable var4) {
         throw var4;
      }
   }

   public static class BukkitMovingObjectPosition {
      public Vector block;
      public Vector pos;
      public LivingEntity entity;

      public BukkitMovingObjectPosition(MovingObjectPosition position) {
         this.block = position.a() == null ? null : new Vector(position.a().getX(), position.a().getY(), position.a().getZ());
         this.pos = new Vector(position.pos.x, position.pos.y, position.pos.z);
         this.entity = position.entity == null ? null : (LivingEntity)position.entity.getBukkitEntity();
      }

      public boolean equals(Object o) {
         if (this == o) {
            return true;
         } else if (o != null && this.getClass() == o.getClass()) {
            NMSSingle.BukkitMovingObjectPosition position = (NMSSingle.BukkitMovingObjectPosition)o;
            if (this.block != null) {
               return Objects.equals(this.block, position.block);
            } else if (this.entity != null) {
               return Objects.equals(this.entity, position.entity);
            } else {
               throw new AssertionError();
            }
         } else {
            return false;
         }
      }
   }

   public static class ResultScan {
      private int id;
      private Vector vector;

      public ResultScan(int id, Vector vector) {
         this.id = id;
         this.vector = vector;
      }

      public Vector getVector() {
         return this.vector;
      }

      public int getId() {
         return this.id;
      }
   }
}
