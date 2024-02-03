package ua.govnojon.libs.bukkitutil;

import com.boydti.fawe.FaweAPI;
import com.boydti.fawe.object.RunnableVal2;
import com.boydti.fawe.object.FaweQueue.ProgressType;
import com.boydti.fawe.object.schematic.Schematic;
import com.boydti.fawe.util.EditSessionBuilder;
import com.boydti.fawe.util.TaskManager;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.function.mask.Mask;
import com.sk89q.worldedit.regions.CuboidRegion;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import net.mineland.core.bukkit.LibsPlugin;
import net.mineland.core.bukkit.modules.message.Message;
import net.mineland.core.bukkit.modules.nms.NMS;
import net.mineland.core.bukkit.modules.user.User;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.World.Environment;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class WorldUtil {
   public static void clearAllChunksInWorldNoSave(World world) {
      NMS.getManager().clearAllChunksInWorldNoSave(world);
   }

   public static World createOrLoadWorld(String name) {
      ChunkGenerator chunkGenerator = new ChunkGenerator() {
         public byte[] generate(World world, Random random, int cx, int cz) {
            return new byte['耀'];
         }
      };
      return createOrLoadWorld(WorldCreator.name(name).type(WorldType.FLAT).environment(Environment.NORMAL).generator(chunkGenerator));
   }

   public static World createOrLoadWorld(WorldCreator worldCreator) {
      World world = Bukkit.getWorld(worldCreator.name());
      if (world != null) {
         return world;
      } else {
         long time = System.currentTimeMillis();
         world = worldCreator.createWorld();
         if (world == null) {
            return null;
         } else {
            Bukkit.getLogger().info("Мир " + world.getName() + " был загружен за " + (System.currentTimeMillis() - time) + "ms.");
            return world;
         }
      }
   }

   public static World getDefaultWorld() {
      return (World)Bukkit.getWorlds().get(0);
   }

   public static boolean hasWorld(String worldName) {
      return Bukkit.getWorld(worldName) != null;
   }

   public static void unloadWorld(final World world, final boolean save) {
      long time = System.currentTimeMillis();
      List<Player> players = world.getPlayers();
      final Location loc;
      if (!players.isEmpty()) {
         loc = LocationUtil.getDefaultLocation();
         Message.broadcast(User.getUsers(players), "мир_выгружается");
         players.forEach((player) -> {
            player.teleport(loc);
         });
      }

      if (!Bukkit.unloadWorld(world, save)) {
         loc = LocationUtil.getDefaultLocation();
         Bukkit.getLogger().info("Не удалось выгрузить мир '" + world.getName() + "' пробуем снова...");
         int tries = false;
         (new BukkitRunnable() {
            public void run() {
               world.getPlayers().forEach((player) -> {
                  player.teleport(loc);
               });
               if (Bukkit.unloadWorld(world, save)) {
                  this.cancel();
               }

            }
         }).runTaskTimer(LibsPlugin.getInstance(), 20L, 20L);
      } else {
         Bukkit.getLogger().info("Мир " + world.getName() + " был выгружен за " + (System.currentTimeMillis() - time) + "ms.");
      }

   }

   public static void copyWorld(File from, File to) {
      try {
         long time = System.currentTimeMillis();
         FileUtils.deleteDirectory(to);
         FileUtils.copyDirectory(from, to);
         File uid = new File(to + File.separator + "uid.dat");
         if (uid.exists()) {
            uid.delete();
         }

         Bukkit.getLogger().info("Мир " + from.getName() + " был скопирован в " + to.getName() + " за " + (System.currentTimeMillis() - time) + "ms.");
      } catch (IOException var5) {
         var5.printStackTrace();
      }

   }

   public static void deleteFolderWorld(World world) {
      try {
         FileUtils.deleteDirectory(world.getWorldFolder());
      } catch (IOException var2) {
         var2.printStackTrace();
      }

   }

   public static void easyParticles(List<Player> list, Location loc, Particle particle, boolean b, double dX, double dY, double dZ, int count, double offX, double offY, double offZ, double speed, int... args) {
      NMS.getManager().sendParticle((Collection)list, particle, b, loc.getX() + dX, loc.getY() + dY, loc.getZ() + dZ, count, offX, offY, offZ, speed, args);
   }

   public static List<Player> easyParticles(Location loc, Particle particle, boolean b, double dX, double dY, double dZ, int count, double offX, double offY, double offZ, double speed, int... args) {
      List<Player> list = new ArrayList();
      Iterator var20 = loc.getWorld().getPlayers().iterator();

      while(var20.hasNext()) {
         Player player = (Player)var20.next();
         if (player.getEyeLocation().distance(loc) < 40.0D) {
            list.add(player);
         }
      }

      easyParticles(list, loc, particle, b, dX, dY, dZ, count, offX, offY, offZ, speed, args);
      return list;
   }

   public static void dropAtBlock(Block block, ItemStack... itemStacks) {
      Location location = block.getLocation().add(0.5D, 0.5D, 0.5D);
      ItemStack[] var3 = itemStacks;
      int var4 = itemStacks.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         ItemStack itemStack = var3[var5];
         if (!ItemStackUtil.isNullOrAir(itemStack)) {
            block.getWorld().dropItemNaturally(location, itemStack);
         }
      }

   }

   public static void setBlocks(Location pos1, Location pos2, ItemData block) {
      setBlocks(pos1, pos2, block, EditSession::flushQueue);
   }

   public static void setBlocks(CuboidRegion cuboidRegion, ItemData block) {
      setBlocks(cuboidRegion, block, EditSession::flushQueue);
   }

   public static void setBlocks(CuboidRegion cuboidRegion, ItemData block, Consumer<EditSession> consumer) {
      setBlocks((CuboidRegion)cuboidRegion, (ItemData)block, (Consumer)consumer, (Executor)null);
   }

   public static void setBlocks(CuboidRegion cuboidRegion, ItemData block, Consumer<EditSession> consumer, Executor executor) {
      Runnable runnable = () -> {
         com.sk89q.worldedit.world.World world = cuboidRegion.getWorld();

         assert world != null : "CuboidRegion.getWorld() == null";

         EditSession session = (new EditSessionBuilder(world)).fastmode(true).build();
         session.setBlocks(cuboidRegion, new BaseBlock(block.getType().ordinal(), block.getData()));
         consumer.accept(session);
      };
      if (executor != null) {
         executor.execute(runnable);
      } else {
         TaskManager.IMP.async(runnable);
      }

   }

   public static void setBlocks(Location pos1, Location pos2, ItemData block, Consumer<EditSession> consumer) {
      setBlocks(pos1, pos2, block, consumer, (Executor)null);
   }

   public static void setBlocks(Location pos1, Location pos2, ItemData block, Consumer<EditSession> consumer, Executor executor) {
      com.sk89q.worldedit.world.World world = FaweAPI.getWorld(pos1.getWorld().getName());
      CuboidRegion cuboidRegion = new CuboidRegion(world, new Vector(pos1.getX(), pos1.getY(), pos1.getZ()), new Vector(pos2.getX(), pos2.getY(), pos2.getZ()));
      setBlocks(cuboidRegion, block, consumer, executor);
   }

   public static void moveBlocks(Location pos1, Location pos2, BlockFace direction, int distance) {
      moveBlocks(pos1, pos2, direction, distance, EditSession::flushQueue);
   }

   public static void moveBlocks(Location pos1, Location pos2, BlockFace direction, int distance, Consumer<EditSession> consumer) {
      moveBlocks(pos1, pos2, direction, distance, consumer, (Executor)null);
   }

   public static void moveBlocks(Location pos1, Location pos2, BlockFace direction, int distance, Consumer<EditSession> consumer, Executor executor) {
      Runnable runnable = () -> {
         com.sk89q.worldedit.world.World world = FaweAPI.getWorld(pos1.getWorld().getName());
         EditSession session = (new EditSessionBuilder(world)).fastmode(true).build();
         CuboidRegion cuboidRegion = new CuboidRegion(world, new Vector(pos1.getX(), pos1.getY(), pos1.getZ()), new Vector(pos2.getX(), pos2.getY(), pos2.getZ()));
         session.moveRegion(cuboidRegion, new Vector(direction.getModX(), direction.getModY(), direction.getModZ()), distance, true, (BaseBlock)null);
         consumer.accept(session);
      };
      if (executor != null) {
         executor.execute(runnable);
      } else {
         TaskManager.IMP.async(runnable);
      }

   }

   public static void copyBlocks(Location pos1, Location pos2, Location pasteTo) {
      copyBlocks(pos1, pos2, pasteTo, EditSession::flushQueue);
   }

   public static void copyBlocks(Location pos1, Location pos2, Location pasteTo, Consumer<EditSession> consumer) {
      copyBlocks(pos1, pos2, pasteTo, (Mask)null, consumer, (Executor)null);
   }

   public static void copyBlocks(Location pos1, Location pos2, Location pasteTo, Mask mask) {
      copyBlocks(pos1, pos2, pasteTo, mask, EditSession::flushQueue, (Executor)null);
   }

   public static void copyBlocks(Location pos1, Location pos2, Location pasteTo, Mask mask, Consumer<EditSession> consumer, Executor executor) {
      Runnable runnable = () -> {
         com.sk89q.worldedit.world.World world = FaweAPI.getWorld(pos1.getWorld().getName());
         EditSession session = (new EditSessionBuilder(world)).fastmode(true).build();
         if (mask != null) {
            session.setMask(mask);
         }

         CuboidRegion cuboidRegion = new CuboidRegion(world, new Vector(pos1.getX(), pos1.getY(), pos1.getZ()), new Vector(pos2.getX(), pos2.getY(), pos2.getZ()));
         Schematic schematic = new Schematic(cuboidRegion);
         schematic.paste(session, new Vector(pasteTo.getX(), pasteTo.getY(), pasteTo.getZ()));
         consumer.accept(session);
      };
      if (executor != null) {
         executor.execute(runnable);
      } else {
         TaskManager.IMP.async(runnable);
      }

   }

   public static RunnableVal2<ProgressType, Integer> createProgressTracker(final ProgressType progressType, final Runnable runnable) {
      return new RunnableVal2<ProgressType, Integer>() {
         public void run(ProgressType type, Integer amount) {
            if (type == progressType) {
               runnable.run();
            }

         }
      };
   }
}
