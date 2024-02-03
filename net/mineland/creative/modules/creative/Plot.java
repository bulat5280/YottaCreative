package net.mineland.creative.modules.creative;

import com.boydti.fawe.FaweAPI;
import com.boydti.fawe.object.clipboard.ReadOnlyClipboard;
import com.boydti.fawe.object.schematic.Schematic;
import com.boydti.fawe.regions.FaweMask;
import com.boydti.fawe.util.EditSessionBuilder;
import com.boydti.fawe.util.TaskManager;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.Vector2D;
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.function.mask.Mask;
import com.sk89q.worldedit.regions.CuboidRegion;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import net.mineland.core.bukkit.module.Module;
import net.mineland.core.bukkit.modules.mainitem.ModuleMainItem;
import net.mineland.core.bukkit.modules.message.Message;
import net.mineland.core.bukkit.modules.mysql.SQL;
import net.mineland.core.bukkit.modules.nms.NMS;
import net.mineland.core.bukkit.modules.region.ModuleRegion;
import net.mineland.core.bukkit.modules.region.region.Region;
import net.mineland.core.bukkit.modules.region.region.flag.FlagType;
import net.mineland.core.bukkit.modules.region.region.territory.Point;
import net.mineland.core.bukkit.modules.region.region.territory.Territory;
import net.mineland.core.bukkit.modules.region.region.territory.TerritoryType;
import net.mineland.core.bukkit.modules.user.Lang;
import net.mineland.core.bukkit.modules.user.User;
import net.mineland.creative.modules.coding.CodeHandler;
import net.mineland.creative.modules.coding.ModuleCoding;
import net.mineland.creative.modules.coding.Param;
import net.mineland.creative.modules.coding.PlotScoreboard;
import net.mineland.creative.modules.coding.exceptions.ExitException;
import net.mineland.creative.modules.creative.generators.biomes.PlotGenerator;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.Nullable;
import org.jooq.Record;
import ua.govnojon.libs.bukkitutil.BukkitUtil;
import ua.govnojon.libs.bukkitutil.schedule.Schedule;
import ua.govnojon.libs.myjava.Try;

public class Plot implements Mask {
   private static ModuleCoding moduleCoding = (ModuleCoding)Module.getInstance(ModuleCoding.class);
   private static ModuleMainItem moduleMainItem = (ModuleMainItem)Module.getInstance(ModuleMainItem.class);
   private static ModuleCreative moduleCreative = (ModuleCreative)Module.getInstance(ModuleCreative.class);
   private static ModuleRegion moduleRegion = (ModuleRegion)Module.getInstance(ModuleRegion.class);
   private static String denySpawn;
   private int id;
   private Vector centerPosition;
   private Vector min;
   private Vector max;
   private int size;
   private List<User> onlinePlayers = new LinkedList();
   private String ownerName;
   private PlotMode plotMode;
   private Territory plotTerritory;
   private Territory codingTerritory;
   private Region plotRegion;
   private Region codingRegion;
   private Param param;
   private List<String> votes;
   private List<String> whitelist;
   private List<String> blacklist;
   private Boolean isClosed;
   private int devLevels;
   private Map<String, Integer> flags;
   private BukkitTask updateVotesTask;
   private CodeHandler codeHandler;
   private LinkedList<Long> worldEditLimit;
   private Schematic codingPlot;
   private boolean codingPlotNull;
   private boolean saveCodingPlotOnUnload;
   public int actionCounter;
   public int startActionTick;
   private int exitExceptionCounter;
   private int startExceptionTick;
   public boolean stoppedCode;
   private Vector buildMinVector;
   private Vector buildMaxVector;
   private FaweMask faweMask;

   public Plot(int id) {
      this.plotMode = PlotMode.BUILDING;
      this.votes = new ArrayList();
      this.whitelist = new ArrayList();
      this.blacklist = new ArrayList();
      this.isClosed = false;
      this.devLevels = 1;
      this.flags = new HashMap();
      this.worldEditLimit = new LinkedList();
      this.codingPlotNull = false;
      this.saveCodingPlotOnUnload = false;
      this.stoppedCode = false;
      this.id = id;
      this.loadPlotSync();
      this.loadRegion();
      this.loadCodingPlot();
   }

   private void loadPlotSync() {
      Record record = SQL.getCreate().fetchOne("SELECT * FROM `" + moduleCreative.tablePlots + "` WHERE `id`=?", this.id);
      if ((Boolean)record.get("is_removed", Boolean.TYPE)) {
         SQL.sync("UPDATE `" + moduleCreative.tablePlots + "` SET `is_removed`=FALSE WHERE `id`=?", this.id);
      }

      this.ownerName = (String)record.get("owner_id", String.class);
      this.centerPosition = new Vector((Integer)record.get("location_x", Integer.TYPE), 255, (Integer)record.get("location_z", Integer.TYPE));
      this.size = (Integer)record.get("size", Integer.TYPE);
      this.syncCheckChangeSize();
      int var10003 = this.centerPosition.getBlockX();
      ModuleCreative var10004 = moduleCreative;
      var10003 -= 300;
      int var10005 = this.centerPosition.getBlockZ();
      ModuleCreative var10006 = moduleCreative;
      this.min = new Vector(var10003, Integer.MIN_VALUE, var10005 - 300);
      var10003 = this.centerPosition.getBlockX();
      var10004 = moduleCreative;
      var10003 += 300;
      var10005 = this.centerPosition.getBlockZ();
      var10006 = moduleCreative;
      this.max = new Vector(var10003, Integer.MAX_VALUE, var10005 + 300);
      this.plotTerritory = new Territory(TerritoryType.CUBE, new Point[]{new Point(moduleCreative.getPlotWorld(), this.centerPosition.getBlockX() + -this.size * 16, Integer.MIN_VALUE, this.centerPosition.getBlockZ() + -this.size * 16), new Point(moduleCreative.getPlotWorld(), this.centerPosition.getBlockX() + (this.size * 16 - 1), Integer.MAX_VALUE, this.centerPosition.getBlockZ() + (this.size * 16 - 1))});
      this.codingTerritory = new Territory(TerritoryType.CUBE, new Point[]{new Point(moduleCoding.getCodingWorld(), this.getCenterPosition().getBlockX() + -moduleCoding.getCodingSize() * 16, 0, this.getCenterPosition().getBlockZ() + -moduleCoding.getCodingSize() * 16), new Point(moduleCoding.getCodingWorld(), this.getCenterPosition().getBlockX() + (moduleCoding.getCodingSize() * 16 - 1), Integer.MAX_VALUE, this.getCenterPosition().getBlockZ() + (moduleCoding.getCodingSize() * 16 - 1))});
      Record settingsRecord = SQL.getCreate().fetchOne("SELECT * FROM `" + moduleCreative.tablePlotsSettings + "` WHERE `plot_id`=?", this.id);
      String settings = (String)settingsRecord.get("settings", String.class);
      if (settings != null) {
         try {
            this.param = new Param(settings);
         } catch (Exception var6) {
            moduleCreative.getLogger().severe("Ошибка парсинга настроек острова " + this.id + ", обнуляем их. Настройки: " + settings);
            var6.printStackTrace();
            this.param = new Param();
         }
      } else {
         this.param = new Param();
      }

      this.updateVotesTask = Schedule.timer(this::updateVotes, 0L, 60L, TimeUnit.SECONDS);
      String[] whitelistUsers = this.param.get("whitelist", new String[0]);
      this.whitelist.addAll(Arrays.asList(whitelistUsers));
      String[] blacklistUsers = this.param.get("blacklist", new String[0]);
      this.blacklist.addAll(Arrays.asList(blacklistUsers));
      this.devLevels = this.param.get("dev_levels", 1);
      this.calculateBuildVectors();
   }

   private void syncCheckChangeSize() {
      User owner = this.getOwner();
      if (owner != null) {
         int permSize = PlotGenerator.getPlotSizeByUser(owner);
         if (permSize > this.size) {
            moduleCreative.getLogger().info("увеличиваем плот игрока " + owner.getPlayer().getName() + " с " + this.size + " -> " + permSize);
            SQL.sync("UPDATE `" + moduleCreative.tablePlots + "` SET `size`=? WHERE `id`=?", permSize, this.id);
            this.size = permSize;
         }
      }

   }

   private void calculateBuildVectors() {
      Vector center = this.getCenterPosition();
      this.buildMaxVector = center.clone().add(new Vector(this.getSize() * 16, 0, this.getSize() * 16));
      this.buildMinVector = center.clone().subtract(new Vector(this.getSize() * 16, 256, this.getSize() * 16));
      this.faweMask = new FaweMask(new CuboidRegion(new com.sk89q.worldedit.Vector(this.buildMinVector.getX(), this.buildMinVector.getY(), this.buildMinVector.getZ()), new com.sk89q.worldedit.Vector(this.buildMaxVector.getX() - 1.0D, this.buildMaxVector.getY(), this.buildMaxVector.getZ() - 1.0D)), "plot_" + this.id);
   }

   public FaweMask getFaweMask() {
      return this.faweMask;
   }

   public boolean test(com.sk89q.worldedit.Vector vector) {
      return vector.getX() >= this.buildMinVector.getX() && vector.getZ() >= this.buildMinVector.getZ() && vector.getX() < this.buildMaxVector.getX() && vector.getZ() < this.buildMaxVector.getZ();
   }

   public void loadRegion() {
      if (this.plotRegion != null) {
         moduleRegion.unregisterRegion(this.plotRegion);
      }

      if (this.codingRegion != null) {
         moduleRegion.unregisterRegion(this.codingRegion);
      }

      this.plotRegion = new Region("plot_id_" + this.id, new Territory(TerritoryType.CUBE, new Point[]{new Point(this.min.toLocation(moduleCreative.getPlotWorld())), new Point(this.max.toLocation(moduleCreative.getPlotWorld()))}));
      this.codingRegion = new Region("coding_id_" + this.id, new Territory(TerritoryType.CUBE, new Point[]{new Point(this.min.toLocation(moduleCoding.getCodingWorld())), new Point(this.max.toLocation(moduleCoding.getCodingWorld()))}));
      this.plotRegion.addPlayer(this.ownerName);
      this.codingRegion.addPlayer(this.ownerName);
      this.whitelist.forEach((id) -> {
         this.plotRegion.addPlayer(id);
         this.codingRegion.addPlayer(id);
      });
      if (this.getPlotMode() == PlotMode.BUILDING) {
         this.plotRegion.setFlag(FlagType.SPAWN, "allow");
         this.plotRegion.setFlag(FlagType.TRAMPLE_SOIL, "member");
         this.plotRegion.setFlag(FlagType.DAMAGE_ENTITY, "member");
         this.plotRegion.setFlag(FlagType.PLAYER_SPAWN_ENTITY, "member");
         this.plotRegion.setFlag(FlagType.PLACE, "member");
         this.plotRegion.setFlag(FlagType.TIME, this.getTime().name());
         this.plotRegion.setFlag(FlagType.WEATHER, FlagType.Values.Weathers.CLEAR.name());
         this.plotRegion.setFlag(FlagType.DENY_SPAWN_REASON, denySpawn);
         this.plotRegion.setFlag(FlagType.PROTECT, "member");
         this.plotRegion.setFlag(FlagType.USE, "member");
         this.plotRegion.setFlag(FlagType.IGNITE, "deny");
         this.plotRegion.setFlag(FlagType.HUNGER, "deny");
      } else if (this.getPlotMode() == PlotMode.PLAYING) {
         this.plotRegion.setFlag(FlagType.SPAWN, "allow");
         this.plotRegion.setFlag(FlagType.TRAMPLE_SOIL, "member");
         this.plotRegion.setFlag(FlagType.DAMAGE_ENTITY, "allow");
         this.plotRegion.setFlag(FlagType.PLAYER_SPAWN_ENTITY, "allow");
         this.plotRegion.setFlag(FlagType.PLACE, "allow");
         this.plotRegion.setFlag(FlagType.TIME, this.getTime().name());
         this.plotRegion.setFlag(FlagType.WEATHER, FlagType.Values.Weathers.CLEAR.name());
         this.plotRegion.setFlag(FlagType.DENY_SPAWN_REASON, denySpawn);
         this.plotRegion.setFlag(FlagType.PROTECT, "allow");
         this.plotRegion.setFlag(FlagType.USE, "allow");
         this.plotRegion.setFlag(FlagType.IGNITE, "allow");
         this.plotRegion.setFlag(FlagType.HUNGER, "allow");
      }

      this.codingRegion.setFlag(FlagType.SPAWN, "allow");
      this.codingRegion.setFlag(FlagType.PLAYER_SPAWN_ENTITY, "deny");
      this.codingRegion.setFlag(FlagType.PLACE, "member");
      this.codingRegion.setFlag(FlagType.TIME, FlagType.Values.Times.v12.name());
      this.codingRegion.setFlag(FlagType.WEATHER, FlagType.Values.Weathers.CLEAR.name());
      this.codingRegion.setFlag(FlagType.DENY_SPAWN_REASON, denySpawn);
      if (this.isAllowBuild()) {
         this.getOnlinePlayers().forEach((user) -> {
            this.plotRegion.addPlayer(user);
         });
      }

      moduleRegion.registerRegion(this.plotRegion);
      moduleRegion.registerRegion(this.codingRegion);
   }

   public void updateVotes() {
      moduleCreative.getPlotManager().getVotes(this.getId(), (result) -> {
         this.votes = result;
      });
   }

   public void unload() {
      moduleCreative.getLogger().info("Выгружаем плот " + this);
      moduleCreative.getPlotManager().getPlots().remove(this);
      this.codeHandler.cancelSchedulers();
      this.unregisterCodeHandler();
      this.updateVotesTask.cancel();
      this.param.put("whitelist", (String[])this.whitelist.toArray(new String[0]));
      this.param.put("blacklist", (String[])this.blacklist.toArray(new String[0]));
      moduleRegion.unregisterRegion(this.plotRegion);
      moduleRegion.unregisterRegion(this.codingRegion);
      if (this.saveCodingPlotOnUnload) {
         this.saveCodingPlot();
      }

      SQL.async((create) -> {
         create.execute("UPDATE `" + moduleCreative.tablePlots + "` SET last_activity=CURRENT_TIMESTAMP WHERE id=?", this.id);
         create.execute("UPDATE `" + moduleCreative.tablePlotsSettings + "` SET `settings`='" + this.param.toString() + "' WHERE `plot_id`=?", this.id);
      });
   }

   private void loadCodingPlot() {
      Schematic schematic = this.getCodingPlot();
      if (schematic == null) {
         moduleCoding.getLogger().info("Файл плота кодинга отсутсвует. plot id = " + this.getId());
      } else {
         TaskManager.IMP.async(() -> {
            EditSession session = (new EditSessionBuilder(FaweAPI.getWorld(moduleCoding.getCodingWorld().getName()))).fastmode(true).build();
            moduleCoding.getLogger().info("Загружаем плот кодинга. plot id = " + this.getId());
            schematic.paste(session, new com.sk89q.worldedit.Vector(this.getCenterPosition().getBlockX(), 0, this.getCenterPosition().getBlockZ()));
            session.flushQueue();
         });
      }

   }

   public void saveCodingPlot() {
      try {
         com.sk89q.worldedit.Vector min = null;
         com.sk89q.worldedit.Vector max = null;
         World world = this.getCodingRegion().getWorld();
         Iterator var4 = this.getCodingRegion().getChunks().iterator();

         while(var4.hasNext()) {
            Chunk chunk = (Chunk)var4.next();
            BlockState[] var6 = chunk.getTileEntities();
            int var7 = var6.length;

            for(int var8 = 0; var8 < var7; ++var8) {
               BlockState blockState = var6[var8];
               Block block = blockState.getBlock();
               if (block.getType() == Material.WALL_SIGN) {
                  if (min == null) {
                     min = new com.sk89q.worldedit.Vector(block.getX(), block.getY(), block.getZ());
                     max = new com.sk89q.worldedit.Vector(block.getX(), block.getY(), block.getZ());
                  } else {
                     min.mutX(Math.min((double)block.getX(), min.getX()));
                     min.mutY(Math.min((double)block.getY(), min.getY()));
                     min.mutZ(Math.min((double)block.getZ(), min.getZ()));
                     max.mutX(Math.max((double)block.getX(), max.getX()));
                     max.mutY(Math.max((double)block.getY(), max.getY()));
                     max.mutZ(Math.max((double)block.getZ(), max.getZ()));
                  }
               }
            }
         }

         if (min == null) {
            moduleCoding.getLogger().info("Файл кодинг плота удаляем, поскольку плот кодинга пуст. plot id = " + this.id);
            File file = new File(moduleCoding.getCodingPlotsDir(), "coding-plot." + this.id + ".schematic");
            Try.ignore(file::delete);
         } else {
            max.mutZ(max.getZ() + 1.0D);
            max.mutY(max.getY() + 1.0D);
            min.mutX(min.getX() - 3.0D);
            moduleCreative.getCodeSaverService().execute(() -> {
               EditSession session = (new EditSessionBuilder(FaweAPI.getWorld(world.getName()))).fastmode(true).build();
               com.sk89q.worldedit.regions.Region region = new CuboidRegion(session, min, max);
               BlockArrayClipboard clipboard = new BlockArrayClipboard(region, ReadOnlyClipboard.of(session, region, false, false));
               clipboard.setOrigin(new com.sk89q.worldedit.Vector(this.getCenterPosition().getBlockX(), 0, this.getCenterPosition().getBlockZ()));
               Schematic schematic = new Schematic(clipboard);
               moduleCoding.getLogger().info("Обновляем кодинг плот. plot id = " + this.id);
               File file = new File(moduleCoding.getCodingPlotsDir(), "coding-plot." + this.id + ".schematic");
               Try.unchecked(() -> {
                  schematic.save(file, ClipboardFormat.SCHEMATIC);
               });
            });
         }
      } catch (Throwable var11) {
         throw var11;
      }
   }

   public List<Chunk> getChunksWithCode() {
      if (this.saveCodingPlotOnUnload) {
         return this.getCodingRegion().getChunks();
      } else {
         Schematic schematic = this.getCodingPlot();
         if (schematic == null) {
            return Collections.emptyList();
         } else {
            World world = moduleCoding.getCodingWorld();
            Set<Vector2D> chunks = schematic.getClipboard().getRegion().getChunks();
            return (List)chunks.stream().map((vector2D) -> {
               return world.getChunkAt(vector2D.getBlockX(), vector2D.getBlockZ());
            }).collect(Collectors.toList());
         }
      }
   }

   @Nullable
   public Schematic getCodingPlot() {
      try {
         if (this.codingPlot == null && !this.codingPlotNull) {
            File file = null;

            try {
               file = new File(moduleCoding.getCodingPlotsDir(), "coding-plot." + this.id + ".schematic");
               if (file.exists()) {
                  this.codingPlot = ClipboardFormat.SCHEMATIC.load(file);
               }
            } catch (Exception var3) {
               if (file != null) {
                  Try.ignore(file::delete);
               }
            }

            if (this.codingPlot == null) {
               this.codingPlotNull = true;
            }
         }

         return this.codingPlot;
      } catch (Throwable var4) {
         throw var4;
      }
   }

   public void setSaveCodingPlotOnUnload(boolean resaveCodingPlot) {
      this.saveCodingPlotOnUnload = resaveCodingPlot;
   }

   public boolean isSaveCodingPlotOnUnload() {
      return this.saveCodingPlotOnUnload;
   }

   public boolean inTerritory(Location location) {
      return this.getCodingTerritory().isContains(location) || this.getPlotTerritory().isContains(location);
   }

   public void setSettings(String flag, int value) {
      this.flags.put(flag, value);
      byte var4 = -1;
      switch(flag.hashCode()) {
      case -1309148959:
         if (flag.equals("explode")) {
            var4 = 4;
         }
         break;
      case -1190402166:
         if (flag.equals("ignite")) {
            var4 = 3;
         }
         break;
      case -988476804:
         if (flag.equals("pickup")) {
            var4 = 5;
         }
         break;
      case -629494755:
         if (flag.equals("spawn_monsters")) {
            var4 = 1;
         }
         break;
      case 111402:
         if (flag.equals("pvp")) {
            var4 = 0;
         }
         break;
      case 116103:
         if (flag.equals("use")) {
            var4 = 2;
         }
         break;
      case 3560141:
         if (flag.equals("time")) {
            var4 = 6;
         }
         break;
      case 1223440372:
         if (flag.equals("weather")) {
            var4 = 7;
         }
      }

      switch(var4) {
      case 0:
         switch(value) {
         case 0:
            this.plotRegion.setFlag(FlagType.PVP, "deny");
            return;
         case 1:
            this.plotRegion.setFlag(FlagType.PVP, "allow");
            return;
         case 2:
            this.plotRegion.setFlag(FlagType.PVP, "member");
            return;
         default:
            return;
         }
      case 1:
         if (value == 0) {
            this.plotRegion.setFlag(FlagType.DENY_SPAWN_REASON, denySpawn);
         } else if (value == 1) {
            this.plotRegion.removeFlag(FlagType.DENY_SPAWN_REASON);
         }
         break;
      case 2:
         if (value == 0) {
            this.plotRegion.setFlag(FlagType.USE, "member");
         } else if (value == 1) {
            this.plotRegion.setFlag(FlagType.USE, "allow");
         }
         break;
      case 3:
         if (value == 0) {
            this.plotRegion.setFlag(FlagType.IGNITE, "deny");
         } else if (value == 1) {
            this.plotRegion.setFlag(FlagType.IGNITE, "allow");
         }
         break;
      case 4:
         if (value == 0) {
            this.plotRegion.setFlag(FlagType.ENTITY_EXPLODE, "deny");
         } else if (value == 1) {
            this.plotRegion.setFlag(FlagType.ENTITY_EXPLODE, "allow");
         }
         break;
      case 5:
         if (value == 0) {
            this.plotRegion.setFlag(FlagType.PICKUP, "member");
         } else if (value == 1) {
            this.plotRegion.setFlag(FlagType.PICKUP, "allow");
         }
         break;
      case 6:
         String time = FlagType.TIME.getValues()[value];
         if (time != null) {
            this.plotRegion.setFlag(FlagType.TIME, time);
         }
         break;
      case 7:
         String weather = FlagType.WEATHER.getValues()[value];
         if (weather != null) {
            this.plotRegion.setFlag(FlagType.WEATHER, weather);
         }
      }

   }

   public Integer getSettings(String key) {
      return (Integer)this.flags.getOrDefault(key, 1);
   }

   public void addPlayer(User user) {
      if (user != null) {
         if (!this.blacklist.contains(user.getName()) || user.hasPermission("creative.blacklist.bypass")) {
            moduleCreative.getLogger().info("Игрок " + user + " входит на плот " + this);
            Plot currentPlot = moduleCreative.getPlotManager().getCurrentPlot(user);
            if (currentPlot != null) {
               currentPlot.removePlayer(user);
            }

            user.setMetadata("current-plot", this);
            moduleMainItem.getMainBar(user).clear();
            user.getPlayer().teleport(this.getSpawnLocation(moduleCreative.getPlotWorld()));
            this.onlinePlayers.add(user);
            if (this.isOwner(user)) {
               this.setOwner(user);
            }

            if (this.isAllowBuild()) {
               this.getPlotRegion().addPlayer(user);
            } else if (this.whitelist.contains(user.getName())) {
               this.getPlotRegion().addPlayer(user);
               this.getCodingRegion().addPlayer(user);
            }

            this.setPlayerMode(user, PlayerMode.PLAYING);
            this.plotMode.onPlayerJoin(user);
            (new Message("creative.joined_the_world", new String[]{"{player}", user.getPlayer().getDisplayName()})).broadcast((Collection)this.getOnlinePlayers());
            moduleCreative.getPlotManager().updateTab(this, user);
         }
      }
   }

   public void removeFromWhitelist(User user) {
      this.getPlotRegion().removePlayer(user);
      this.getCodingRegion().removePlayer(user);
   }

   public void removePlayer(User user) {
      Try.ignore(() -> {
         this.removePassengerOrVehicle(user);
      });
      if (user != null) {
         moduleCreative.getLogger().info("Игрок " + user + " выходит из плота " + this);
         this.plotMode.onPlayerQuit(user);
         this.getPlayerMode(user).onPlayerQuit(this, user);
         this.onlinePlayers.remove(user);
         PlotScoreboard plotScoreboard = (PlotScoreboard)user.getMetadata("plot_scoreboard");
         if (plotScoreboard != null) {
            plotScoreboard.hide(user);
         }

         user.setMetadata("current-plot", (Object)null);
         if (this.onlinePlayers.size() == 0) {
            this.unload();
         }

         (new Message("creative.left_the_world", new String[]{"{player}", user.getPlayer().getDisplayName()})).broadcast((Collection)this.getOnlinePlayers());
         Try.ignore(() -> {
            this.cancelUserTasks(user);
         });
      }
   }

   private void removePassengerOrVehicle(User user) {
      Player player = user.getPlayer();
      Entity vehicle = player.getVehicle();
      if (vehicle != null) {
         player.leaveVehicle();
      }

      player.getPassengers().forEach(player::removePassenger);
   }

   private void cancelUserTasks(User user) {
      List<BukkitTask> tasks = this.getUserTasks(user);
      tasks.forEach(BukkitUtil::cancelTask);
      tasks.clear();
   }

   public List<BukkitTask> getUserTasks(User user) {
      return (List)user.getMetadata("creative.user.tasks", ArrayList::new);
   }

   public CodeHandler getCodeHandler() {
      return this.codeHandler;
   }

   public void registerCodeHandler(CodeHandler codeHandler) {
      this.codeHandler = codeHandler;
   }

   public void unregisterCodeHandler() {
      this.codeHandler = new CodeHandler(new ArrayList(0), this);
   }

   public int getId() {
      return this.id;
   }

   public Vector getCenterPosition() {
      return this.centerPosition.clone();
   }

   public void setCenterPosition(Vector centerPosition) {
      this.centerPosition = centerPosition;
   }

   public Location getSpawnLocation() {
      return this.getSpawnLocation((World)null);
   }

   public void setSpawnLocation(Location spawnLocation) {
      if (spawnLocation == null) {
         spawnLocation = moduleCreative.getPlotWorld().getHighestBlockAt(this.getCenterPosition().toLocation(moduleCreative.getPlotWorld())).getLocation().add(0.0D, 1.0D, 0.0D);
      }

      this.param.put("spawn_location", spawnLocation);
   }

   public Location getSpawnLocation(World world) {
      if (world == null) {
         world = moduleCreative.getPlotWorld();
      }

      Location location;
      if (world == moduleCreative.getPlotWorld()) {
         location = this.getCenterPosition().toLocation(moduleCreative.getPlotWorld());
         Location spawnLocation = this.param.get("spawn_location", location);
         if (spawnLocation.equals(location)) {
            this.setSpawnLocation((Location)null);
         }

         return spawnLocation;
      } else if (world == moduleCoding.getCodingWorld()) {
         location = this.getCenterPosition().toLocation(moduleCoding.getCodingWorld()).add((double)(moduleCoding.getCodingSize() * 16 - 3), 0.0D, (double)(moduleCoding.getCodingSize() * 16 - 3));
         location.setY(1.0D);
         return location;
      } else {
         return this.getCenterPosition().toLocation(moduleCreative.getPlotWorld());
      }
   }

   public List<User> getOnlinePlayers() {
      return this.onlinePlayers;
   }

   public PlotMode getPlotMode() {
      return this.plotMode;
   }

   public void setPlotMode(PlotMode plotMode) {
      this.getOnlinePlayers().stream().filter((user) -> {
         return this.getPlayerMode(user) != PlayerMode.CODING;
      }).forEach((user) -> {
         this.getPlotMode().onPlayerQuit(user);
      });
      this.codeHandler.cancelSchedulers();
      this.plotMode = plotMode;
      plotMode.onLoad(this);
      this.whitelist.forEach((id) -> {
         this.getPlotRegion().addPlayer(id);
      });
      this.getOnlinePlayers().stream().filter((user) -> {
         return this.getPlayerMode(user) != PlayerMode.CODING;
      }).forEach((user) -> {
         this.getPlotMode().onPlayerJoin(user);
      });
   }

   public void setPlayerMode(User user, PlayerMode playerMode) {
      PlayerMode currentMode = (PlayerMode)user.removeMetadata("creative.player_mode");
      if (currentMode != null) {
         currentMode.onPlayerQuit(this, user);
      }

      playerMode.onPlayerJoin(this, user);
      user.setMetadata("creative.player_mode", playerMode);
   }

   public PlayerMode getPlayerMode(User user) {
      PlayerMode playerMode = (PlayerMode)user.getMetadata("creative.player_mode");
      if (playerMode == null) {
         if (user.getPlayer().getWorld() == moduleCreative.getPlotWorld()) {
            this.setPlayerMode(user, PlayerMode.PLAYING);
            return PlayerMode.PLAYING;
         }

         if (user.getPlayer().getWorld() == moduleCoding.getCodingWorld()) {
            this.setPlayerMode(user, PlayerMode.CODING);
            return PlayerMode.CODING;
         }
      }

      return playerMode;
   }

   public void onExitException(ExitException e) {
      if (!this.stoppedCode) {
         int tick = NMS.getManager().getTick();
         if (tick - this.startExceptionTick >= 20) {
            this.startExceptionTick = tick;
            this.exitExceptionCounter = 0;
         } else {
            ++this.exitExceptionCounter;
            if (this.exitExceptionCounter > 10) {
               try {
                  this.stoppedCode = true;
                  if (this.getOwner() != null) {
                     this.getOwner().sendMessage("креатив.лимит.ошибок", "{size}", String.valueOf(this.exitExceptionCounter), "{limit}", String.valueOf(10));
                  }

                  moduleCreative.getLogger().warning("Отключаем режим PLAYING " + this.getId() + ", кол-во ошибок кода лимит " + this.exitExceptionCounter + "/" + 10 + " за 20 тиков");
                  this.setPlotMode(PlotMode.BUILDING);
               } finally {
                  this.stoppedCode = false;
               }
            }
         }

      }
   }

   public int getDevLevels() {
      return this.devLevels;
   }

   public void setDevLevels(int devLevels) {
      int prev = this.devLevels;
      this.param.put("dev_levels", devLevels);
      this.devLevels = devLevels;
   }

   public User getOwner() {
      return User.getUser(this.ownerName);
   }

   public String getOwnerName() {
      return this.ownerName;
   }

   public void setOwner(User owner) {
      this.ownerName = owner.getName();
   }

   public boolean isOwner(User user) {
      return this.ownerName.equals(user.getName());
   }

   public void setIsClosed(Boolean close) {
      this.isClosed = close;
   }

   public boolean isClosed() {
      return this.isClosed;
   }

   public int getSize() {
      return this.size;
   }

   public Territory getPlotTerritory() {
      return this.plotTerritory;
   }

   public Territory getCodingTerritory() {
      return this.codingTerritory;
   }

   public Region getPlotRegion() {
      return this.plotRegion;
   }

   public Region getCodingRegion() {
      return this.codingRegion;
   }

   public List<String> getVotes() {
      return this.votes;
   }

   public String getGuiDescription(Lang lang) {
      return moduleCreative.getPlotManager().syncGetPlotDescription(this.getId(), this.param, this.getOwner(), lang);
   }

   public FlagType.Values.Times getTime() {
      return FlagType.Values.Times.v12;
   }

   public String getName() {
      return this.param.get("name");
   }

   public void setName(String name) {
      if (name == null) {
         this.param.remove("name");
      } else {
         this.param.put("name", "§r" + name);
      }

   }

   public List<String> getWhitelist() {
      return this.whitelist;
   }

   public List<String> getBlacklist() {
      return this.blacklist;
   }

   public boolean isAllowBuild() {
      return this.param.get("build", false);
   }

   public void setAllowBuild(boolean value) {
      this.param.put("build", value);
   }

   public boolean isAllowFly() {
      return this.param.get("fly", false);
   }

   public void setAllowFly(boolean value) {
      this.param.put("fly", value);
   }

   public void setVariables(String config) {
      this.param.put("variables", config);
   }

   public String getVariables() {
      return this.param.get("variables");
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         Plot plot = (Plot)o;
         return this.id == plot.id;
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Objects.hash(new Object[]{this.id});
   }

   public LinkedList<Long> getWorldEditLimit() {
      return this.worldEditLimit;
   }

   public String toString() {
      return "Plot{id=" + this.id + ", ownerName='" + this.ownerName + '\'' + '}';
   }

   static {
      denySpawn = SpawnReason.NATURAL.name() + ";" + SpawnReason.JOCKEY.name() + ";" + SpawnReason.VILLAGE_INVASION.name() + ";" + SpawnReason.SLIME_SPLIT.name();
   }
}
