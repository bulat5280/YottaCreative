package net.mineland.creative.modules.creative.generators.biomes;

import com.boydti.fawe.FaweAPI;
import com.boydti.fawe.util.EditSessionBuilder;
import com.boydti.fawe.util.TaskManager;
import com.sk89q.worldedit.BlockVector2D;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.Vector2D;
import com.sk89q.worldedit.function.FlatRegionFunction;
import com.sk89q.worldedit.function.FlatRegionMaskingFilter;
import com.sk89q.worldedit.function.biome.BiomeReplace;
import com.sk89q.worldedit.function.mask.Mask;
import com.sk89q.worldedit.function.mask.Mask2D;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.function.pattern.BlockPattern;
import com.sk89q.worldedit.function.visitor.FlatRegionVisitor;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Regions;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldedit.world.biome.BaseBiome;
import com.sk89q.worldedit.world.biome.Biomes;
import com.sk89q.worldedit.world.registry.BiomeRegistry;
import java.util.List;
import java.util.stream.Collectors;
import net.mineland.core.bukkit.module.Module;
import net.mineland.core.bukkit.modules.region.region.territory.Point;
import net.mineland.core.bukkit.modules.region.region.territory.Territory;
import net.mineland.core.bukkit.modules.user.User;
import net.mineland.creative.modules.coding.ModuleCoding;
import net.mineland.creative.modules.creative.ModuleCreative;
import net.mineland.creative.modules.creative.Plot;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import ua.govnojon.libs.bukkitutil.ItemData;
import ua.govnojon.libs.bukkitutil.schedule.Schedule;

public abstract class PlotGenerator {
   private static ModuleCreative moduleCreative = (ModuleCreative)Module.getInstance(ModuleCreative.class);
   private static ModuleCoding moduleCoding = (ModuleCoding)Module.getInstance(ModuleCoding.class);
   private static BiomeRegistry biomeRegistry = FaweAPI.getWorld("world").getWorldData().getBiomeRegistry();
   private User user;
   private Location centerPosition;

   public PlotGenerator(User user, Location pos) {
      this.user = user;
      this.centerPosition = pos;
   }

   public static int getPlotSizeByUser(User user) {
      if (user.hasPermission("mineland.creative.plot_size.hero")) {
         return 11;
      } else if (user.hasPermission("mineland.creative.plot_size.expert")) {
         return 9;
      } else if (user.hasPermission("mineland.creative.plot_size.skilled")) {
         return 7;
      } else {
         return user.hasPermission("mineland.creative.plot_size.gamer") ? 5 : 3;
      }
   }

   public User getUser() {
      return this.user;
   }

   abstract void generate(EditSession var1);

   public void setBiome(EditSession editSession, Biome biome) {
      try {
         BaseBiome target = Biomes.findBiomeByName(biomeRegistry.getBiomes(), biome.name(), biomeRegistry);
         Mask mask = editSession.getMask();
         Mask2D mask2d = mask != null ? mask.toMask2D() : null;
         CuboidRegion region = this.getCuboidRegion(0, 255);
         FlatRegionFunction replace = new BiomeReplace(editSession, target);
         if (mask2d != null) {
            replace = new FlatRegionMaskingFilter(mask2d, (FlatRegionFunction)replace);
         }

         FlatRegionVisitor visitor = new FlatRegionVisitor(Regions.asFlatRegion(region), (FlatRegionFunction)replace);
         Operations.completeLegacy(visitor);
         editSession.flushQueue();
      } catch (MaxChangedBlocksException var9) {
         var9.printStackTrace();
      }

   }

   public static void generateCodingPlot(Plot plot, Runnable done) {
      Territory territory = plot.getCodingTerritory();
      TaskManager.IMP.laterAsync(() -> {
         World world = FaweAPI.getWorld(territory.getWorld().getName());
         EditSession session = (new EditSessionBuilder(world)).autoQueue(false).fastmode(true).build();
         Point pos1 = territory.getPoints()[0];
         Point pos2 = territory.getPoints()[1];
         BlockPattern color1 = new BlockPattern(Material.STAINED_GLASS.ordinal(), 3);
         BlockPattern color2 = new BlockPattern(Material.STAINED_GLASS.ordinal(), 8);
         BlockPattern color3 = new BlockPattern(Material.STAINED_GLASS.ordinal(), 0);

         for(int i = 0; i < plot.getDevLevels(); ++i) {
            int levelY = i * 10;
            CuboidRegion cuboidRegion;
            if (i == 0) {
               cuboidRegion = new CuboidRegion(new Vector(pos1.getX(), levelY, pos1.getZ()), new Vector(pos2.getX(), levelY, pos2.getZ()));
            } else {
               cuboidRegion = new CuboidRegion(new Vector(pos1.getX() + 3, levelY, pos1.getZ() + 3), new Vector(pos2.getX() - 4, levelY, pos2.getZ() - 4));
            }

            session.setBlocks(cuboidRegion, color3);
            session.flushQueue();

            int z;
            for(z = pos1.getX() + 3; z <= pos2.getX() - 4 - 2; z += 2) {
               for(int zx = pos1.getZ() + 3; zx <= pos2.getZ() - 4; zx += 4) {
                  session.setBlock(z, levelY, zx, color2);
               }
            }

            for(z = pos1.getZ() + 3; z <= pos2.getZ() - 4; z += 4) {
               session.setBlock(pos2.getX() - 4, levelY, z, color1);
            }

            session.flushQueue();
         }

         session.flushQueue();
         Schedule.run(done);
      }, 5);
   }

   public void setBlocks(EditSession editSession, CuboidRegion cuboidRegion, ItemData blockData) {
      editSession.getQueue().setBlocks(cuboidRegion, blockData.getType().ordinal(), blockData.getData());
      editSession.flushQueue();
   }

   public CuboidRegion getCuboidRegion(int minY, int maxY) {
      int size = getPlotSizeByUser(this.user);
      World world = FaweAPI.getWorld(moduleCreative.getPlotWorld().getName());
      return new CuboidRegion(world, new Vector(this.centerPosition.getBlockX() + -size * 16, minY, this.centerPosition.getBlockZ() + -size * 16), new Vector(this.centerPosition.getBlockX() + (size * 16 - 1), maxY, this.centerPosition.getBlockZ() + (size * 16 - 1)));
   }

   public void create(boolean clear, Runnable done) {
      TaskManager.IMP.async(() -> {
         try {
            World world = FaweAPI.getWorld(moduleCreative.getPlotWorld().getName());
            EditSession session = (new EditSessionBuilder(world)).autoQueue(false).fastmode(true).build();
            if (clear) {
               moduleCreative.getLogger().info("Очищаем место в мире для плота " + this.user.getName());
               int var10005 = this.centerPosition.getBlockX();
               ModuleCreative var10006 = moduleCreative;
               var10005 -= 300;
               int var10007 = this.centerPosition.getBlockZ();
               ModuleCreative var10008 = moduleCreative;
               Vector var10003 = new Vector(var10005, 0, var10007 - 300);
               int var10 = this.centerPosition.getBlockX();
               ModuleCreative var11 = moduleCreative;
               var10 += 300;
               int var12 = this.centerPosition.getBlockZ();
               ModuleCreative var10009 = moduleCreative;
               CuboidRegion fullRegion = new CuboidRegion(world, var10003, new Vector(var10, 255, var12 + 300));
               session.getQueue().setBlocks(fullRegion, 0, 0);
               session.flushQueue();
               List<BlockVector2D> chunks = (List)fullRegion.getChunks().stream().map(Vector2D::toBlockVector2D).collect(Collectors.toList());
               session.fixLighting(chunks);
               session.flushQueue();
            }

            this.generate(session);
            session.flushQueue();
            moduleCreative.getLogger().info("Сгенерировали плот для игрока " + this.user.getName() + ", позиция в мире " + this.centerPosition);
         } finally {
            Schedule.run(done);
         }

      });
   }

   public Location getCenterPosition() {
      return this.centerPosition;
   }
}
