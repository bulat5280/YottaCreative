package net.mineland.creative.modules.creative;

import com.destroystokyo.paper.event.entity.EntityAddToWorldEvent;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import net.mineland.core.bukkit.module.Module;
import net.mineland.core.bukkit.modules.customdeaths.DamageUser;
import net.mineland.core.bukkit.modules.customdeaths.PlayerDeathMyEvent;
import net.mineland.core.bukkit.modules.gui.ModuleGui;
import net.mineland.core.bukkit.modules.locale.ModuleLocale;
import net.mineland.core.bukkit.modules.myevents.PlayerMoveOnBlockMyEvent;
import net.mineland.core.bukkit.modules.nms.wrapper.packets.WrapperPlayServerRespawn;
import net.mineland.core.bukkit.modules.user.User;
import net.mineland.creative.modules.coding.activators.player.PlayerDeathActivator;
import net.mineland.creative.modules.coding.activators.player.PlayerKillPlayerActivator;
import net.mineland.creative.modules.coding.activators.player.PlayerMobKillPlayerActivator;
import net.mineland.creative.modules.creative.gui.worldsettings.GuiMenuWorldSettings;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.ItemStack;
import ua.govnojon.libs.bukkitutil.PlayerUtil;
import ua.govnojon.libs.bukkitutil.WorldUtil;
import ua.govnojon.libs.bukkitutil.schedule.Schedule;

public class EventListener implements Listener {
   private static ModuleCreative moduleCreative = (ModuleCreative)Module.getInstance(ModuleCreative.class);
   private static ModuleLocale moduleLocale = (ModuleLocale)Module.getInstance(ModuleLocale.class);
   private static ModuleGui moduleGui = (ModuleGui)Module.getInstance(ModuleGui.class);
   private List<EntityType> denyEntityTypes;

   public EventListener() {
      this.denyEntityTypes = Arrays.asList(EntityType.CREEPER, EntityType.PRIMED_TNT, EntityType.MINECART_TNT, EntityType.SLIME);
   }

   @EventHandler(
      priority = EventPriority.LOWEST
   )
   public void on(PlayerJoinEvent event) {
      User user = User.getUser((PlayerEvent)event);
      moduleCreative.getPlotManager().teleportToLobby(user, true);
      Schedule.later(() -> {
         moduleCreative.getPlotManager().updateTab((Plot)null, user);
      }, 5L);
   }

   @EventHandler
   public void on(PlayerQuitEvent event) {
      User user = User.getUser((PlayerEvent)event);
      moduleCreative.getPlotManager().teleportToLobby(user, true);
   }

   @EventHandler(
      ignoreCancelled = true,
      priority = EventPriority.LOW
   )
   public void onAdd(EntityAddToWorldEvent event) {
      Entity entity = event.getEntity();
      Location location = entity.getLocation();
      if (entity.getType() != EntityType.PLAYER) {
         if (this.denyEntityTypes.contains(entity.getType())) {
            entity.remove();
         } else {
            Collection<Entity> entities = location.getWorld().getNearbyEntities(location, 800.0D, 10000.0D, 800.0D);
            if (entities.size() > 100) {
               entity.remove();
               entities.stream().skip(100L).filter(Entity::isValid).filter((e) -> {
                  return !(e instanceof Player);
               }).forEach(Entity::remove);
               Plot plot = moduleCreative.getPlotManager().getPlot(location);
               if (plot != null) {
                  plot.getOnlinePlayers().forEach((user) -> {
                     PlayerUtil.sendMessageDenyKey(user, "лимит_мобов", "%limit%", "100");
                  });
               }

               moduleCreative.getLogger().warning("Лимит мобов " + entities.size() + "/100 в радиусе от " + location.getWorld().getName() + "," + location.getX() + "," + location.getY() + "," + location.getZ() + (plot == null ? "" : ", плот - " + plot));
            }

         }
      }
   }

   @EventHandler(
      priority = EventPriority.LOWEST
   )
   public void on(PlayerTeleportEvent event) {
      User user = User.getUser((PlayerEvent)event);
      Location to = event.getTo();
      Location from = event.getFrom();
      Plot currentPlot = moduleCreative.getPlotManager().getCurrentPlot(user);
      Plot anotherPlot = moduleCreative.getPlotManager().getPlot(to);
      if (event.getCause() == TeleportCause.SPECTATE) {
         if (currentPlot != anotherPlot || to.getWorld() != from.getWorld()) {
            event.setCancelled(true);
         }

      } else {
         if (currentPlot != anotherPlot && anotherPlot != null) {
            event.setCancelled(true);
            anotherPlot.addPlayer(user);
         } else if (currentPlot != null && to.getWorld() == WorldUtil.getDefaultWorld()) {
            moduleCreative.getPlotManager().teleportToLobby(user, true);
            user.getPlayer().teleport(to);
         }

      }
   }

   @EventHandler
   public void on(EntityDamageEvent event) {
      Entity entity = event.getEntity();
      Plot plot = moduleCreative.getPlotManager().getPlot(entity.getLocation());
      if (plot == null) {
         if (entity.getType() == EntityType.PLAYER) {
            switch(event.getCause()) {
            case VOID:
               moduleCreative.getPlotManager().teleportToLobby(User.getUser((CommandSender)entity), true);
               event.setCancelled(true);
               break;
            default:
               event.setCancelled(true);
            }
         }
      } else {
         switch(event.getCause()) {
         case VOID:
            if (entity instanceof LivingEntity) {
               double health = ((LivingEntity)entity).getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
               event.setDamage(health);
            }
            break;
         default:
            switch(plot.getPlotMode()) {
            case BUILDING:
               if (entity.getType() == EntityType.PLAYER) {
                  event.setCancelled(true);
               }
            }
         }
      }

   }

   @EventHandler
   public void on(PlayerMoveOnBlockMyEvent event) {
      User user = event.getUser();
      Plot currentPlot = moduleCreative.getPlotManager().getCurrentPlot(user);
      if (currentPlot != null && !currentPlot.inTerritory(event.getTo())) {
         event.setCancelled(true);
      }

   }

   @EventHandler
   public void on(BlockPlaceEvent event) {
      Block block = event.getBlock();
      Plot plot = moduleCreative.getPlotManager().getPlot(block.getLocation());
      if (plot != null) {
         if (!plot.inTerritory(block.getLocation())) {
            event.setCancelled(true);
         }
      } else {
         event.setCancelled(true);
      }

   }

   @EventHandler
   public void on(BlockFormEvent event) {
      Block block = event.getBlock();
      Plot plot = moduleCreative.getPlotManager().getPlot(block.getLocation());
      if (plot != null && !plot.inTerritory(block.getLocation())) {
         event.setCancelled(true);
      }

   }

   @EventHandler
   public void on(BlockFromToEvent event) {
      Block block = event.getBlock();
      Block toBlock = event.getToBlock();
      Plot plot = moduleCreative.getPlotManager().getPlot(block.getLocation());
      if (plot != null && !plot.inTerritory(block.getLocation())) {
         event.setCancelled(true);
      }

   }

   @EventHandler
   public void on(BlockSpreadEvent event) {
      Block block = event.getBlock();
      Plot plot = moduleCreative.getPlotManager().getPlot(block.getLocation());
      if (plot != null && !plot.inTerritory(block.getLocation())) {
         event.setCancelled(true);
      }

   }

   @EventHandler
   public void on(BlockGrowEvent event) {
      Block block = event.getBlock();
      Plot plot = moduleCreative.getPlotManager().getPlot(block.getLocation());
      if (plot != null && !plot.inTerritory(block.getLocation())) {
         event.setCancelled(true);
      }

   }

   @EventHandler
   public void on(PlayerBucketEmptyEvent event) {
      Block block = event.getBlockClicked().getRelative(event.getBlockFace());
      Plot plot = moduleCreative.getPlotManager().getPlot(block.getLocation());
      if (plot != null && !plot.inTerritory(block.getLocation())) {
         event.setCancelled(true);
      }

   }

   @EventHandler
   public void on(PlayerBucketFillEvent event) {
      Block block = event.getBlockClicked().getRelative(event.getBlockFace());
      Plot plot = moduleCreative.getPlotManager().getPlot(block.getLocation());
      if (plot != null && !plot.inTerritory(block.getLocation())) {
         event.setCancelled(true);
      }

   }

   @EventHandler
   public void on(EntityAddToWorldEvent event) {
      Location location = event.getEntity().getLocation();
      Plot plot = moduleCreative.getPlotManager().getPlot(location);
      if (plot != null && !plot.inTerritory(location)) {
         event.getEntity().remove();
      }

   }

   @EventHandler
   public void on(BlockPistonRetractEvent event) {
      Block relativeBlock = event.getBlock().getRelative(event.getDirection());
      Block secondRelativeBlock = relativeBlock.getRelative(event.getDirection());
      Plot plot = moduleCreative.getPlotManager().getPlot(event.getBlock().getLocation());
      if (plot != null) {
         if (!plot.inTerritory(relativeBlock.getLocation())) {
            event.setCancelled(true);
         }

         if (!plot.inTerritory(secondRelativeBlock.getLocation()) && relativeBlock.getType() != Material.AIR) {
            event.setCancelled(true);
         }
      }

   }

   @EventHandler
   public void on(BlockPistonExtendEvent event) {
      Block relativeBlock = event.getBlock().getRelative(event.getDirection());
      Block secondRelativeBlock = relativeBlock.getRelative(event.getDirection());
      Plot plot = moduleCreative.getPlotManager().getPlot(event.getBlock().getLocation());
      if (plot != null) {
         if (!plot.inTerritory(relativeBlock.getLocation())) {
            event.setCancelled(true);
         }

         if (!plot.inTerritory(secondRelativeBlock.getLocation()) && relativeBlock.getType() != Material.AIR) {
            event.setCancelled(true);
         }
      }

   }

   @EventHandler
   public void on(PlayerBedEnterEvent event) {
      Block block = event.getBed();
      Plot plot = moduleCreative.getPlotManager().getPlot(block.getLocation());
      if (plot != null) {
         int value = plot.getTime().getValue();
         if (12541 > value || value > 23458) {
            event.setCancelled(true);
            User user = User.getUser((PlayerEvent)event);
            user.getPlayer().sendActionBar(moduleLocale.getString("tile.bed.noSleep", user.getLang()));
         }

         if (!plot.inTerritory(block.getLocation())) {
            event.setCancelled(true);
         }
      }

   }

   @EventHandler(
      priority = EventPriority.HIGHEST
   )
   public void onDeath(PlayerDeathMyEvent event) {
      PlayerDeathEvent handle = event.getPlayerDeathEvent();
      User user = User.getUser((PlayerEvent)event);
      Plot plot = moduleCreative.getPlotManager().getCurrentPlot(user);
      Player player = user.getPlayer();
      if (!(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() < 0.5D)) {
         Location deathLocation = player.getLocation();
         GameMode gameMode = player.getGameMode();
         ItemStack[] contents = player.getInventory().getContents();
         handle.setKeepInventory(true);
         handle.setKeepLevel(true);
         PlayerUtil.setDefaultParameters(player, GameMode.SPECTATOR);
         (new WrapperPlayServerRespawn()).sendPacket(player);
         if (plot == null) {
            moduleCreative.getPlotManager().teleportToLobby(user, true);
         } else {
            Schedule.later(() -> {
               PlayerUtil.setDefaultParameters(player, gameMode);
               if (plot == moduleCreative.getPlotManager().getCurrentPlot(user)) {
                  DamageUser.get(user).printDeathMessage(plot.getOnlinePlayers());
                  player.teleport(plot.getSpawnLocation());
                  switch(plot.getPlotMode()) {
                  case PLAYING:
                     if (user.hasMetadata("coding.save_inv")) {
                        user.getPlayer().getInventory().setContents(contents);
                     } else {
                        WorldUtil.dropAtBlock(deathLocation.getBlock(), contents);
                     }

                     PlayerDeathActivator.Event gameEvent = new PlayerDeathActivator.Event(user, plot, event);
                     gameEvent.callEvent();
                     gameEvent.handle();
                     Player damagerPlayer = event.getLastDamage().getDamagerPlayer();
                     if (damagerPlayer != null) {
                        PlayerKillPlayerActivator.Event killEvent = new PlayerKillPlayerActivator.Event(User.getUser(damagerPlayer), plot, event);
                        killEvent.callEvent();
                        killEvent.handle();
                     }

                     Entity damager = event.getLastDamage().getDamager();
                     if (damager != null && damager.getType() != EntityType.PLAYER) {
                        PlayerMobKillPlayerActivator.Event killEventx = new PlayerMobKillPlayerActivator.Event(user, plot, event);
                        killEventx.callEvent();
                        killEventx.handle();
                     }
                  }
               }

               PlayerUtil.playSound(player, Sound.ENTITY_PLAYER_BREATH, 3.0F, 2.0F);
            }, 5L);
         }

      }
   }

   @EventHandler
   public void on(PlayerRespawnEvent event) {
      User user = User.getUser((PlayerEvent)event);
      moduleCreative.getPlotManager().teleportToLobby(user, true);
   }

   @EventHandler(
      priority = EventPriority.LOWEST
   )
   public void onChatEvent(AsyncPlayerChatEvent event) {
      User user = User.getUser(event.getPlayer());
      Plot plot = (Plot)user.removeMetadata("plot.set_name", Plot.class);
      if (plot != null) {
         String msg = event.getMessage().replace("&", "§").replace("§§", "&");
         if (!msg.toLowerCase().startsWith("отмена") && !msg.toLowerCase().startsWith("cancel")) {
            event.setCancelled(true);
            plot.setName(msg);
            user.sendMessage("creative.plot.set_name.success", "{name}", msg);
         } else {
            event.setCancelled(true);
            moduleGui.openGui(user, GuiMenuWorldSettings.class);
         }
      }

   }
}
