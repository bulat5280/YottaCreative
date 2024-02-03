package net.mineland.creative.modules.creative;

import com.boydti.fawe.FaweAPI;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import net.mineland.core.bukkit.module.BukkitModule;
import net.mineland.core.bukkit.module.Module;
import net.mineland.core.bukkit.modules.command.CommandManager;
import net.mineland.core.bukkit.modules.gui.GuiMenu;
import net.mineland.core.bukkit.modules.gui.ModuleGui;
import net.mineland.core.bukkit.modules.mainitem.ModuleMainItem;
import net.mineland.core.bukkit.modules.mysql.SQL;
import net.mineland.core.bukkit.modules.permission.PermissionMap;
import net.mineland.core.bukkit.modules.region.region.Region;
import net.mineland.core.bukkit.modules.region.region.flag.FlagType;
import net.mineland.core.bukkit.modules.region.region.territory.Point;
import net.mineland.core.bukkit.modules.region.region.territory.Territory;
import net.mineland.core.bukkit.modules.region.region.territory.TerritoryType;
import net.mineland.core.bukkit.modules.user.User;
import net.mineland.creative.modules.coding.exceptions.WorldLimitExitException;
import net.mineland.creative.modules.creative.commands.CommandLobby;
import net.mineland.creative.modules.creative.commands.CommandPlot;
import net.mineland.creative.modules.creative.commands.CommandVote;
import net.mineland.creative.modules.creative.gui.GuiMenuGenerator;
import net.mineland.creative.modules.creative.gui.GuiMenuMyWorlds;
import net.mineland.creative.modules.creative.gui.GuiMenuPlotsOnline;
import net.mineland.creative.modules.creative.gui.worldsettings.GuiMenuWorldAddBlacklist;
import net.mineland.creative.modules.creative.gui.worldsettings.GuiMenuWorldAddWhitelist;
import net.mineland.creative.modules.creative.gui.worldsettings.GuiMenuWorldBuild;
import net.mineland.creative.modules.creative.gui.worldsettings.GuiMenuWorldCode;
import net.mineland.creative.modules.creative.gui.worldsettings.GuiMenuWorldFly;
import net.mineland.creative.modules.creative.gui.worldsettings.GuiMenuWorldRemoveBlacklist;
import net.mineland.creative.modules.creative.gui.worldsettings.GuiMenuWorldRemoveWhitelist;
import net.mineland.creative.modules.creative.gui.worldsettings.GuiMenuWorldSettings;
import net.mineland.creative.modules.creative.gui.worldsettings.flags.GuiMenuFlags;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.World.Environment;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.Plugin;
import ua.govnojon.libs.bukkitutil.LocationUtil;
import ua.govnojon.libs.bukkitutil.WorldUtil;
import ua.govnojon.libs.bukkitutil.schedule.Schedule;
import ua.govnojon.libs.config.Config;
import ua.govnojon.libs.myjava.Try;

public class ModuleCreative extends BukkitModule {
   private static ModuleGui moduleGui = (ModuleGui)Module.getInstance(ModuleGui.class);
   private ModuleMainItem moduleMainItem;
   public static final int radiusPrivate = 300;
   public static final int distancePlots = 400;
   public static final int maxDevLevels = 5;
   public static final int devLevelHeight = 10;
   public String tablePlots;
   public String tablePlotsSettings;
   public String tableVotes;
   private PlotManager plotManager;
   private World plotWorld;
   private Location lobbyLocation;
   private PermissionMap<Integer> callLimitPerm;
   private PermissionMap<Integer> stackLimitPerm;
   private PermissionMap<WorldLimit> worldLimitPerm;
   private ExecutorService worldService;
   private ExecutorService codeSaverService;
   private String commandPrefix = "@";
   private WorldLimit defaultWorldLimit = new WorldLimit(1000L, 10);

   public ModuleCreative(int priority, Plugin plugin) {
      super("creative", priority, plugin, new Config(plugin, "creative.yml"));
   }

   public static World createPlotWorld(String name) {
      ChunkGenerator chunkGenerator = new ChunkGenerator() {
         public byte[] generate(World world, Random random, int cx, int cz) {
            return new byte['耀'];
         }
      };
      return WorldUtil.createOrLoadWorld(WorldCreator.name(name).type(WorldType.NORMAL).environment(Environment.NORMAL).generator(chunkGenerator));
   }

   public void onFirstEnable() {
      this.moduleMainItem = (ModuleMainItem)Module.getInstance(ModuleMainItem.class);
      this.tablePlots = "creative_plots";
      this.tablePlotsSettings = "creative_plot_settings";
      this.tableVotes = "creative_votes";
      SQL.sync("CREATE TABLE IF NOT EXISTS `{table}` (  `id` int(5) NOT NULL,  `owner_id` varchar(16) NULL DEFAULT NULL,  `location_x` int(9) NOT NULL,  `location_z` int(9) NOT NULL,  `size` int(9) NOT NULL DEFAULT '50',  `node` int(9) NOT NULL DEFAULT '0',  `last_reset` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,  `last_activity` timestamp NOT NULL DEFAULT '1971-01-01 00:00:00',  `is_removed` tinyint(1) NOT NULL DEFAULT '0',  PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;".replace("{table}", this.tablePlots));
      SQL.sync("CREATE TABLE IF NOT EXISTS `{table}` (  `plot_id` int(11) NOT NULL,  `settings` TEXT NULL DEFAULT NULL,  PRIMARY KEY (`plot_id`),  FOREIGN KEY (`plot_id`) REFERENCES `{islands}` (`id`) ON DELETE CASCADE ON UPDATE CASCADE ) ENGINE=InnoDB DEFAULT CHARSET=utf8;".replace("{table}", this.tablePlotsSettings).replace("{islands}", this.tablePlots));
      SQL.sync("CREATE TABLE IF NOT EXISTS `{table}` ( `id` INT(11) NOT NULL AUTO_INCREMENT, `plot_id` INT(11) NOT NULL DEFAULT '0', `player_id` varchar(16) NOT NULL DEFAULT '0', `last_vote` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, PRIMARY KEY (`id`), INDEX `plot_id` (`plot_id`), CONSTRAINT `FK_creative_votes_creative_plots` FOREIGN KEY (`plot_id`) REFERENCES `creative_plots` (`id`) ON UPDATE CASCADE ON DELETE CASCADE) ENGINE=InnoDB DEFAULT CHARSET=utf8;".replace("{table}", this.tableVotes));
      this.plotWorld = createPlotWorld("plot_world");
      this.plotWorld.setGameRuleValue("doDaylightCycle", "false");
      this.plotWorld.setGameRuleValue("doWeatherCycle", "false");
      this.plotWorld.setTime(6000L);
      Schedule.timer(() -> {
         User.getUsers().stream().map((user) -> {
            return moduleGui.getOpenGui(user);
         }).filter(Objects::nonNull).filter((guiMenu) -> {
            return guiMenu instanceof GuiMenuPlotsOnline;
         }).forEach(GuiMenu::reload);
      }, 10L, 10L, TimeUnit.SECONDS);
      this.lobbyLocation = this.getConfig().getLocation("lobby-location");
      Region region = new Region("spawn", new Territory(TerritoryType.CUBE, new Point[]{new Point(this.lobbyLocation.getWorld(), -300000, 0, -300000), new Point(this.lobbyLocation.getWorld(), 300000, 255, 300000)}));
      region.setFlag(FlagType.IGNITE, "deny");
      region.setFlag(FlagType.PROTECT, "deny");
      region.setFlag(FlagType.PLAYER_SPAWN_ENTITY, "deny");
      region.setFlag(FlagType.USE, "deny");
      region.setFlag(FlagType.ENTITY_EXPLODE, "deny");
      region.setFlag(FlagType.PLACE, "deny");
      region.setFlag(FlagType.ALLOW_INTERACT_ITEMS, "deny");
      region.setFlag(FlagType.SPAWN, "deny");
      region.registerRegion();
      Schedule.timer(() -> {
         this.getLogger().info("Сохраняем все изменные кодинг плоты (раз в 5 минут).");
         this.plotManager.getPlots().stream().filter(Plot::isSaveCodingPlotOnUnload).forEach((plot) -> {
            Try.ignore(plot::saveCodingPlot);
         });
      }, 5L, 5L, TimeUnit.MINUTES);
      FaweAPI.addMaskManager(new PlotSquaredFeature(this));
   }

   public World getPlotWorld() {
      return this.plotWorld;
   }

   public PlotManager getPlotManager() {
      return this.plotManager;
   }

   public Location getLobbyLocation() {
      return this.lobbyLocation;
   }

   public void onEnable() {
      this.getConfig().setDescription("stack-limit-perm - права на лимит стака\ncall-limit-perm - права на лимит размера цепочки вызовов\n формат прав - ПРАВО:ЗНАЧЕНИЕ_ЛИМИТА\ncommand-prefix - через какой символ нужно писать команды в кодинге (можно несколько символов)");
      this.registerListenersThis();
      this.registerData(new EventListener());
      this.registerData(new PacketListener());
      CommandManager.registerCommand(new CommandLobby());
      CommandManager.registerCommand(new CommandVote());
      CommandManager.registerCommand(new CommandPlot());
      this.plotManager = new PlotManager(this);
      this.getConfig().setIfNotExist("lobby-location", LocationUtil.getDefaultLocation());
      this.lobbyLocation = this.getConfig().getLocation("lobby-location");
      World lobbyWorld = this.lobbyLocation.getWorld();
      lobbyWorld.setGameRuleValue("doDaylightCycle", "false");
      lobbyWorld.setGameRuleValue("doWeatherCycle", "false");
      lobbyWorld.setTime(0L);
      moduleGui.registerGui(GuiMenuPlotsOnline.class, GuiMenuPlotsOnline::new);
      moduleGui.registerGui(GuiMenuMyWorlds.class, GuiMenuMyWorlds::new);
      moduleGui.registerGui(GuiMenuWorldSettings.class, GuiMenuWorldSettings::new);
      moduleGui.registerGui(GuiMenuWorldBuild.class, GuiMenuWorldBuild::new);
      moduleGui.registerGui(GuiMenuWorldCode.class, GuiMenuWorldCode::new);
      moduleGui.registerGui(GuiMenuWorldAddWhitelist.class, GuiMenuWorldAddWhitelist::new);
      moduleGui.registerGui(GuiMenuWorldRemoveWhitelist.class, GuiMenuWorldRemoveWhitelist::new);
      moduleGui.registerGui(GuiMenuWorldAddBlacklist.class, GuiMenuWorldAddBlacklist::new);
      moduleGui.registerGui(GuiMenuWorldRemoveBlacklist.class, GuiMenuWorldRemoveBlacklist::new);
      moduleGui.registerGui(GuiMenuWorldFly.class, GuiMenuWorldFly::new);
      moduleGui.registerGui(GuiMenuGenerator.class, GuiMenuGenerator::new);
      moduleGui.registerGui(GuiMenuFlags.class, GuiMenuFlags::new);
      this.loadConfig();
      this.loadPerms();
      this.initWorldExecutor();
   }

   private void loadConfig() {
      this.commandPrefix = (String)this.getConfig().getOrSet("command-prefix", "@");
      if (this.commandPrefix.isEmpty()) {
         throw new RuntimeException("в конфиге command-prefix содержит пустую строку, нужен минимум 1 символ");
      }
   }

   public void onDisable() {
      Iterator var1 = User.getUsers().iterator();

      while(var1.hasNext()) {
         User user = (User)var1.next();

         try {
            this.getPlotManager().teleportToLobby(user, true);
         } catch (Exception var4) {
            var4.printStackTrace();
         }
      }

      this.stopWorldExecutor();
   }

   private void initWorldExecutor() {
      this.stopWorldExecutor();
      this.worldService = new ThreadPoolExecutor(0, 5, 5L, TimeUnit.MINUTES, new ArrayBlockingQueue(64, true), (new ThreadFactoryBuilder()).setNameFormat("Creative WorldEdit-%d").build());
      this.codeSaverService = new ThreadPoolExecutor(0, 1, 5L, TimeUnit.MINUTES, new ArrayBlockingQueue(64, true), (new ThreadFactoryBuilder()).setNameFormat("Coding WorldEdit Saver-%d").build());
   }

   private void stopWorldExecutor() {
      try {
         Try.ignore(() -> {
            if (this.worldService != null) {
               this.worldService.shutdownNow();
               this.worldService = null;
            }

         });
         Try.ignore(() -> {
            if (this.codeSaverService != null) {
               this.codeSaverService.shutdown();
               this.codeSaverService.awaitTermination(30L, TimeUnit.SECONDS);
               this.codeSaverService = null;
            }

         });
      } catch (Throwable var2) {
         throw var2;
      }
   }

   public ExecutorService getWorldService() {
      return this.worldService;
   }

   private void loadPerms() {
      Config config = this.getConfig();
      this.stackLimitPerm = new PermissionMap();
      Iterator var2 = ((List)config.getOrSet("stack-limit-perm", Arrays.asList("creative.stacklimit.default:10", "creative.stacklimit.hero:20"))).iterator();

      String key;
      String[] data;
      while(var2.hasNext()) {
         key = (String)var2.next();

         try {
            data = key.split(":");
            this.stackLimitPerm.put(data[0], Integer.parseInt(data[1]));
         } catch (Exception var6) {
            this.getLogger().severeLoadParameter(key);
         }
      }

      this.callLimitPerm = new PermissionMap();
      var2 = ((List)config.getOrSet("call-limit-perm", Arrays.asList("creative.calllimit.default:20", "creative.calllimit.hero:40"))).iterator();

      while(var2.hasNext()) {
         key = (String)var2.next();

         try {
            data = key.split(":");
            this.callLimitPerm.put(data[0], Integer.parseInt(data[1]));
         } catch (Exception var5) {
            this.getLogger().severeLoadParameter(key);
         }
      }

      if (!config.contains("worldedit-limits")) {
         config.set("worldedit-limits.1.perm", "creative.worldeditlimits.default");
         config.set("worldedit-limits.1.time-millis", 1000);
         config.setAndSave("worldedit-limits.1.limit-operations", 10);
      }

      this.worldLimitPerm = new PermissionMap();
      var2 = config.getKeys("worldedit-limits").iterator();

      while(var2.hasNext()) {
         key = (String)var2.next();
         this.worldLimitPerm.put(config.getString("worldedit-limits." + key + ".perm"), new WorldLimit(config.getOrSetNumber("worldedit-limits." + key + ".time-millis", 1000).longValue(), config.getOrSetNumber("worldedit-limits." + key + ".limit-operations", 10).intValue()));
      }

   }

   public int getCallLimit(User user) {
      return user == null ? 20 : (Integer)this.callLimitPerm.get(user, 20);
   }

   public int getStackLimit(User user) {
      return user == null ? 10 : (Integer)this.stackLimitPerm.get(user, 10);
   }

   public WorldLimit getWorldLimit(User user) {
      return user == null ? this.defaultWorldLimit : (WorldLimit)this.worldLimitPerm.get(user, this.defaultWorldLimit);
   }

   public boolean tryWorldOperation(Plot plot) throws WorldLimitExitException {
      LinkedList<Long> times = plot.getWorldEditLimit();
      long current = System.currentTimeMillis();
      WorldLimit worldLimit = this.getWorldLimit(plot.getOwner());
      times.removeIf((time) -> {
         return current - time > worldLimit.getTimeMillis();
      });
      if (times.size() >= worldLimit.getLimitOperations()) {
         if (plot.getOwner() != null) {
            plot.getOwner().sendMessage("креатив.лимит.мира", "{count}", String.valueOf(worldLimit.getLimitOperations()), "{time.limit}", String.valueOf(worldLimit.getTimeMillis()));
         }

         throw new WorldLimitExitException();
      } else {
         times.add(current);
         return true;
      }
   }

   public String getCommandPrefix() {
      return this.commandPrefix;
   }

   public ExecutorService getCodeSaverService() {
      return this.codeSaverService;
   }
}
