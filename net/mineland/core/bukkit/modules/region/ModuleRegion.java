package net.mineland.core.bukkit.modules.region;

import java.io.File;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import net.mineland.core.bukkit.module.BukkitModule;
import net.mineland.core.bukkit.module.Module;
import net.mineland.core.bukkit.modules.material.ModuleMaterial;
import net.mineland.core.bukkit.modules.message.Message;
import net.mineland.core.bukkit.modules.myevents.PlayerMoveOnBlockMyEvent;
import net.mineland.core.bukkit.modules.region.events.ListenersRegionDamage;
import net.mineland.core.bukkit.modules.region.events.ListenersRegionEntity;
import net.mineland.core.bukkit.modules.region.events.ListenersRegionPlayer;
import net.mineland.core.bukkit.modules.region.events.ListenersRegionWorld;
import net.mineland.core.bukkit.modules.region.events.PlayerJoinRegionEvent;
import net.mineland.core.bukkit.modules.region.events.PlayerQuitRegionEvent;
import net.mineland.core.bukkit.modules.region.events.RegionKey;
import net.mineland.core.bukkit.modules.region.region.Region;
import net.mineland.core.bukkit.modules.region.region.flag.FlagType;
import net.mineland.core.bukkit.modules.region.region.territory.Point;
import net.mineland.core.bukkit.modules.user.User;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;
import ua.govnojon.libs.bukkitutil.LocationUtil;
import ua.govnojon.libs.bukkitutil.PlayerUtil;
import ua.govnojon.libs.config.Config;

public class ModuleRegion extends BukkitModule implements Listener {
   private static final Pattern PATTERN_ON_SPACE = Pattern.compile(" ", 16);
   private static ModuleRegion instance;
   private static ModuleMaterial moduleMaterial = (ModuleMaterial)Module.getInstance(ModuleMaterial.class);
   private List<Region> regions = Collections.synchronizedList(new LinkedList());
   private PluginManager manager = Bukkit.getPluginManager();

   public ModuleRegion(int priority, Plugin plugin) {
      super("region", priority, plugin, new Config(plugin, "regions.yml"));
      instance = this;
   }

   public static ModuleRegion getInstance() {
      return instance;
   }

   public void onFirstEnable() {
      this.registerListenersThis();
      this.registerData(new ListenersRegionWorld());
      this.registerData(new ListenersRegionEntity());
      this.registerData(new ListenersRegionPlayer());
      this.registerData(new ListenersRegionDamage());
   }

   public void onEnable() {
      Config config = this.getConfig();
      config.createSectionIfNotExist("regions");
      Iterator var2 = config.getKeys("regions").iterator();

      while(var2.hasNext()) {
         String name = (String)var2.next();

         try {
            Region old = this.getRegion(name);
            if (old != null) {
               this.regions.remove(old);
            }

            Region region = new Region(config, name);
            region.setSave(true);
            this.regions.add(region);
         } catch (Exception var6) {
            this.getLogger().severe("Ошибка при загрузке регоина '" + name + "'.");
            var6.printStackTrace();
         }
      }

   }

   public void onDisable() {
      this.unregisterDataAll();
   }

   public Region getPriorityRegionFlag(Location loc, FlagType type) {
      List<Region> regions = this.getRegions(loc);
      if (regions.isEmpty()) {
         return null;
      } else {
         Region priority = null;
         Iterator var5 = regions.iterator();

         while(true) {
            Region region;
            String result;
            do {
               do {
                  if (!var5.hasNext()) {
                     return priority;
                  }

                  region = (Region)var5.next();
                  result = region.getFlagValue(type);
               } while(result == null);
            } while(priority != null && priority.getPriority() >= region.getPriority());

            priority = region;
         }
      }
   }

   public RegionKey getPriorityRegionFlagKey(Location loc, FlagType type) {
      return this.getPriorityRegionFlagKey(this.getRegions(loc), type);
   }

   public RegionKey getPriorityRegionFlagKey(List<Region> regions, FlagType type) {
      if (regions.isEmpty()) {
         return null;
      } else {
         Region priority = null;
         String last = null;
         Iterator var5 = regions.iterator();

         while(true) {
            Region region;
            String result;
            do {
               do {
                  if (!var5.hasNext()) {
                     return priority == null ? null : new RegionKey(priority, last);
                  }

                  region = (Region)var5.next();
                  result = region.getFlagValue(type);
               } while(result == null);
            } while(priority != null && priority.getPriority() >= region.getPriority());

            priority = region;
            last = result;
         }
      }
   }

   public Region getPriorityRegionFlag(List<Region> regions, FlagType type) {
      Region priority = null;
      Iterator var4 = regions.iterator();

      while(true) {
         Region region;
         String result;
         do {
            do {
               if (!var4.hasNext()) {
                  return priority;
               }

               region = (Region)var4.next();
               result = region.getFlagValue(type);
            } while(result == null);
         } while(priority != null && priority.getPriority() >= region.getPriority());

         priority = region;
      }
   }

   public String getFlagValue(List<Region> regions, FlagType type) {
      Region priority = null;
      String value = null;
      if (!regions.isEmpty()) {
         Iterator var5 = regions.iterator();

         while(true) {
            Region region;
            String result;
            do {
               do {
                  if (!var5.hasNext()) {
                     return value;
                  }

                  region = (Region)var5.next();
                  result = region.getFlagValue(type);
               } while(result == null);
            } while(priority != null && priority.getPriority() >= region.getPriority());

            priority = region;
            value = result;
         }
      } else {
         return value;
      }
   }

   public String getFlagValue(Location loc, FlagType type) {
      Region region = this.getPriorityRegionFlag(loc, type);
      return region == null ? null : region.getFlagValue(type);
   }

   public void registerRegion(Region region) {
      this.unregisterRegion(region.getName());
      this.regions.add(region);
   }

   public void unregisterRegion(String name) {
      Region old = this.getRegion(name);
      if (old != null) {
         this.unregisterRegion(old);
      }

   }

   public void unregisterRegion(Region region) {
      this.regions.remove(region);
   }

   public void save(Region region) {
      region.save = true;
      region.config = this.getConfig();
      region.updateMembers();
      region.updateFlags();
      region.config.set("regions." + region.getName() + ".type", region.getTerritory().getType().name());
      List<String> points = new LinkedList();
      Point[] var3 = region.getTerritory().getPoints();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Point point = var3[var5];
         points.add(point.getWorld().getName() + ":" + point.getX() + ":" + point.getY() + ":" + point.getZ());
      }

      region.config.set("regions." + region.getName() + ".points", points);
      region.config.set("regions." + region.getName() + ".priority", region.getPriority());
      region.config.save();
   }

   public void save(Region region, File file) {
      try {
         region.save = true;
         region.config = new Config(file);
         region.updateMembers();
         region.updateFlags();
         region.config.set("regions." + region.getName() + ".type", region.getTerritory().getType().name());
         List<String> points = new LinkedList();
         Point[] var4 = region.getTerritory().getPoints();
         int var5 = var4.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            Point point = var4[var6];
            points.add(point.getWorld().getName() + ":" + point.getX() + ":" + point.getY() + ":" + point.getZ());
         }

         region.config.set("regions." + region.getName() + ".points", points);
         region.config.set("regions." + region.getName() + ".priority", region.getPriority());
         region.config.save();
         region.save = false;
      } catch (Exception var8) {
         this.getLogger().warning("Не удалось сохранить регион `" + region.getName() + "`:");
         var8.printStackTrace();
      }

   }

   @EventHandler(
      priority = EventPriority.MONITOR
   )
   public void onMonitor(PlayerInteractEvent event) {
      if ((event.getHand() == null || event.getHand().equals(EquipmentSlot.HAND)) && event.hasBlock()) {
         Player player = event.getPlayer();
         if (PlayerUtil.hasMainHand(player, Material.WOOD_AXE)) {
            User user = User.getUser(player);
            Block block = event.getClickedBlock();
            switch(event.getAction()) {
            case RIGHT_CLICK_BLOCK:
               user.setMetadata("selection_pos2", block.getLocation());
               break;
            case LEFT_CLICK_BLOCK:
               user.setMetadata("selection_pos1", block.getLocation());
            }
         }
      }

   }

   @EventHandler(
      priority = EventPriority.MONITOR,
      ignoreCancelled = true
   )
   public void onMonitor(PlayerCommandPreprocessEvent event) {
      Player player = event.getPlayer();
      String lowerCase = event.getMessage().toLowerCase();
      boolean pos1;
      if ((pos1 = StringUtils.contains(lowerCase, "/pos1")) || StringUtils.contains(lowerCase, "/pos2")) {
         User user = User.getUser(player);
         String[] args = PATTERN_ON_SPACE.split(lowerCase);
         if (args.length > 1) {
            String locData = args[1];

            try {
               String[] data = locData.split(",");
               Location loc = new Location(player.getWorld(), (double)Integer.parseInt(data[0]), (double)Integer.parseInt(data[1]), (double)Integer.parseInt(data[2]));
               user.setMetadata(pos1 ? "selection_pos1" : "selection_pos2", loc);
            } catch (Exception var10) {
            }
         } else {
            Location loc = LocationUtil.floorBlock(player.getLocation());
            user.setMetadata(pos1 ? "selection_pos1" : "selection_pos2", loc);
         }
      }

   }

   @NotNull
   public List<Region> getRegions(Location loc) {
      List<Region> regions = new LinkedList();
      this.regions.forEach((region) -> {
         if (region.getTerritory().isContains(loc)) {
            regions.add(region);
         }

      });
      return regions;
   }

   public Region getRegion(String name) {
      return (Region)this.regions.stream().filter((region) -> {
         return region.getName().equals(name);
      }).findFirst().orElse((Object)null);
   }

   public List<Region> getRegions() {
      return this.regions;
   }

   public void sendInfo(CommandSender player, Region region) {
      StringBuilder message = new StringBuilder();
      message.append("§e").append(region.getName()).append(":\n");
      message.append("  §7Type: ").append(region.getTerritory().getType().name()).append("\n");
      message.append("  §7Priority: ").append(region.getPriority()).append("\n");
      message.append("  §7Points: \n");
      Point[] var4 = region.getTerritory().getPoints();
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         Point point = var4[var6];
         message.append("    §7").append(point.getWorld().getName()).append(",").append(point.getX()).append(",").append(point.getY()).append(",").append(point.getZ()).append(";\n");
      }

      message.append("\n");
      message.append("  §7Members: \n");
      User.getModuleUser().getPlayerDatas(region.getMembers(), (playerIds) -> {
         playerIds.stream().filter(Objects::nonNull).forEach((playerId) -> {
            message.append("    §7").append(playerId.getName()).append(" (").append(playerId.getName()).append(");\n");
         });
         message.append("  §7Flags: \n");
         Iterator var4 = region.getFlags().entrySet().iterator();

         while(var4.hasNext()) {
            Entry<FlagType, String> flag = (Entry)var4.next();
            message.append("    §7").append(((FlagType)flag.getKey()).name()).append(": ").append((String)flag.getValue()).append(";\n");
         }

         player.sendMessage(message.toString());
      });
   }

   @EventHandler
   public void on(PlayerInteractEvent event) {
      boolean hasBlock = event.getClickedBlock() != null && !event.getClickedBlock().getType().equals(Material.AIR);
      boolean hasItem = event.getItem() != null && !event.getItem().getType().equals(Material.AIR);
      if (hasBlock && hasItem) {
         Player player = event.getPlayer();
         Material type = event.getItem().getType();
         if (type.equals(Material.STICK) && player.hasPermission("mineland.libs.region.info")) {
            event.setCancelled(true);
            List<Region> regions = this.getRegions(event.getClickedBlock().getLocation());
            if (regions.isEmpty()) {
               player.sendMessage(Message.getMessage(player, "регионы_не_найдены"));
            } else {
               regions.forEach((region) -> {
                  this.sendInfo(player, region);
               });
            }
         }
      }

   }

   @EventHandler(
      priority = EventPriority.HIGH
   )
   public void on(PlayerMoveOnBlockMyEvent event) {
      this.checkCollision(event.getUser(), event.getTo(), event.getFrom());
   }

   @EventHandler(
      priority = EventPriority.HIGH
   )
   public void on(PlayerRespawnEvent event) {
      this.checkCollision(User.getUser(event.getPlayer()), event.getRespawnLocation(), event.getPlayer().getLocation());
   }

   @EventHandler(
      priority = EventPriority.HIGH
   )
   public void on(PlayerTeleportEvent event) {
      this.checkCollision(User.getUser(event.getPlayer()), event.getTo(), event.getFrom());
   }

   public void checkCollision(User user, Location to, Location from) {
      List<Region> join = this.getRegions(to);
      List<Region> quit = (List)user.removeMetadata("last_join_region");
      if (quit == null) {
         quit = Collections.emptyList();
      }

      user.setMetadata("last_join_region", join);
      Iterator var6;
      Region region;
      if (!join.isEmpty() && !quit.isEmpty()) {
         var6 = join.iterator();

         while(var6.hasNext()) {
            region = (Region)var6.next();
            if (!quit.contains(region)) {
               this.manager.callEvent(new PlayerJoinRegionEvent(user, region, to, from));
            }
         }

         var6 = quit.iterator();

         while(var6.hasNext()) {
            region = (Region)var6.next();
            if (!join.contains(region)) {
               this.manager.callEvent(new PlayerQuitRegionEvent(user, region, to, from));
            }
         }
      } else if (!join.isEmpty()) {
         var6 = join.iterator();

         while(var6.hasNext()) {
            region = (Region)var6.next();
            this.manager.callEvent(new PlayerJoinRegionEvent(user, region, to, from));
         }
      } else if (!quit.isEmpty()) {
         var6 = quit.iterator();

         while(var6.hasNext()) {
            region = (Region)var6.next();
            this.manager.callEvent(new PlayerQuitRegionEvent(user, region, to, from));
         }
      }

   }

   public void updatePlayerWeather(Player player, String value) {
      if (value != null) {
         FlagType.Values.Weathers weather = FlagType.Values.Weathers.forName(value);
         if (weather != null && weather.getValue() != null) {
            player.setPlayerWeather(weather.getValue());
            return;
         }
      }

      player.resetPlayerWeather();
   }

   public void updatePlayerTime(Player player, String value) {
      if (value != null) {
         FlagType.Values.Times time = FlagType.Values.Times.forName(value);
         if (time != null && time.getValue() != -1) {
            player.setPlayerTime((long)time.getValue(), false);
            return;
         }
      }

      player.resetPlayerTime();
   }

   public boolean hasNegativePotion(AreaEffectCloud effectCloud) {
      return moduleMaterial.hasNegativePotion(effectCloud);
   }

   public boolean hasNegativePotion(ThrownPotion potion) {
      return moduleMaterial.hasNegativePotion(potion.getEffects());
   }
}
