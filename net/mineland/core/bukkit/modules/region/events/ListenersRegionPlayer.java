package net.mineland.core.bukkit.modules.region.events;

import java.util.List;
import net.mineland.core.bukkit.module.Module;
import net.mineland.core.bukkit.modules.material.ModuleMaterial;
import net.mineland.core.bukkit.modules.region.ModuleRegion;
import net.mineland.core.bukkit.modules.region.region.Region;
import net.mineland.core.bukkit.modules.region.region.flag.FlagType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.Event.Result;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.EntityBlockFormEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.projectiles.ProjectileSource;
import ua.govnojon.libs.bukkitutil.PlayerUtil;
import ua.govnojon.libs.bukkitutil.schedule.Schedule;

public class ListenersRegionPlayer implements Listener {
   private ModuleRegion moduleRegion = ModuleRegion.getInstance();
   private ModuleMaterial moduleMaterial = (ModuleMaterial)Module.getInstance(ModuleMaterial.class);

   @EventHandler(
      priority = EventPriority.LOWEST
   )
   public void onPlayerQuitEvent(PlayerQuitEvent event) {
      String value = this.moduleRegion.getFlagValue(event.getPlayer().getLocation(), FlagType.RELOG);
      if (value != null) {
         byte var4 = -1;
         switch(value.hashCode()) {
         case 3079692:
            if (value.equals("deny")) {
               var4 = 1;
            }
            break;
         case 92906313:
            if (value.equals("allow")) {
               var4 = 0;
            }
         }

         switch(var4) {
         case 0:
         default:
            break;
         case 1:
            event.getPlayer().setHealth(0.0D);
         }
      }

   }

   @EventHandler(
      priority = EventPriority.LOWEST
   )
   public void onPlayerJoinRegionEvent(PlayerJoinRegionEvent event) {
      Player player = event.getPlayer();
      String value = event.getRegion().getFlagValue(FlagType.EXECUTE_COMMAND);
      if (value != null && value.length() != 0) {
         String[] var4 = value.split(";");
         int var5 = var4.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            String cmd = var4[var6];
            player.chat(cmd);
         }
      }

      List<Region> regions = this.moduleRegion.getRegions(event.getTo());
      Runnable runnable = () -> {
         this.moduleRegion.updatePlayerTime(player, this.moduleRegion.getFlagValue(regions, FlagType.TIME));
         this.moduleRegion.updatePlayerWeather(player, this.moduleRegion.getFlagValue(regions, FlagType.WEATHER));
      };
      if (event.getTo().getWorld().equals(event.getFrom().getWorld())) {
         runnable.run();
      } else {
         Bukkit.getScheduler().runTaskLater(this.moduleRegion.getPlugin(), runnable, 3L);
      }

   }

   @EventHandler(
      priority = EventPriority.LOWEST
   )
   public void onPlayerQuitRegionEvent(PlayerQuitRegionEvent event) {
      Player player = event.getPlayer();
      List<Region> regions = this.moduleRegion.getRegions(event.getTo());
      Runnable runnable = () -> {
         this.moduleRegion.updatePlayerTime(player, this.moduleRegion.getFlagValue(regions, FlagType.TIME));
         this.moduleRegion.updatePlayerWeather(player, this.moduleRegion.getFlagValue(regions, FlagType.WEATHER));
      };
      if (event.getTo().getWorld().equals(event.getFrom().getWorld())) {
         runnable.run();
      } else {
         Bukkit.getScheduler().runTaskLater(this.moduleRegion.getPlugin(), runnable, 3L);
      }

   }

   public void checkBlockBreak(List<Region> regions, Player player, Cancellable event) {
      Region region = this.moduleRegion.getPriorityRegionFlag(regions, FlagType.PLACE);
      if (region != null) {
         String value = region.getFlagValue(FlagType.PLACE);
         byte var7 = -1;
         switch(value.hashCode()) {
         case -1077769574:
            if (value.equals("member")) {
               var7 = 2;
            }
            break;
         case 3079692:
            if (value.equals("deny")) {
               var7 = 1;
            }
            break;
         case 92906313:
            if (value.equals("allow")) {
               var7 = 0;
            }
         }

         switch(var7) {
         case 0:
            event.setCancelled(false);
            break;
         case 1:
            event.setCancelled(!player.hasPermission("mineland.libs.region.place.deny"));
            break;
         case 2:
            if (!region.isMember(player) && !player.hasPermission("mineland.libs.region.place.member")) {
               event.setCancelled(true);
            }
         }
      }

   }

   @EventHandler(
      priority = EventPriority.LOWEST
   )
   public void onBlockBreakEvent(BlockBreakEvent event) {
      Block block = event.getBlock();
      List<Region> regions = this.moduleRegion.getRegions(block.getLocation());
      if (!regions.isEmpty()) {
         this.checkBlockBreak(regions, event.getPlayer(), event);
      }

   }

   @EventHandler(
      priority = EventPriority.LOWEST
   )
   public void boatbreaklily(EntityChangeBlockEvent event) {
      Block block = event.getBlock();
      Entity breaked = event.getEntity();
      if (block.getType() == Material.WATER_LILY) {
         if (breaked instanceof Boat) {
            Boat boatbreaker = (Boat)breaked;
            Player driver = (Player)boatbreaker.getPassengers().stream().findFirst().orElse((Object)null);
            if (driver != null) {
               List<Region> regions = this.moduleRegion.getRegions(block.getLocation());
               if (!regions.isEmpty()) {
                  this.checkBlockBreak(regions, driver, event);
               }

            }
         }
      }
   }

   @EventHandler(
      priority = EventPriority.LOWEST
   )
   public void onBlockPlaceEvent(BlockPlaceEvent event) {
      Block block = event.getBlock();
      List<Region> regions = this.moduleRegion.getRegions(block.getLocation());
      if (!regions.isEmpty()) {
         this.checkBlockBreak(regions, event.getPlayer(), event);
         if (event.isCancelled()) {
            String value = this.moduleRegion.getFlagValue(regions, FlagType.ALLOW_PLACE);
            if (value != null && FlagType.search(value, String.valueOf(block.getType().getId()))) {
               event.setCancelled(false);
            }
         }
      }

   }

   @EventHandler(
      priority = EventPriority.LOWEST
   )
   public void onHangingPlaceEvent(HangingPlaceEvent event) {
      Block block = event.getBlock();
      List<Region> regions = this.moduleRegion.getRegions(block.getLocation());
      if (!regions.isEmpty()) {
         this.checkBlockBreak(regions, event.getPlayer(), event);
      }

   }

   @EventHandler(
      priority = EventPriority.LOWEST
   )
   public void onHangingBreakByEntityEvent(HangingBreakByEntityEvent event) {
      if (event.getRemover().getType().equals(EntityType.PLAYER)) {
         List<Region> regions = this.moduleRegion.getRegions(event.getEntity().getLocation());
         if (!regions.isEmpty()) {
            this.checkBlockBreak(regions, (Player)event.getRemover(), event);
         }

      }
   }

   @EventHandler(
      priority = EventPriority.LOWEST
   )
   public void onFoodLevelChangeEvent(FoodLevelChangeEvent event) {
      if (event.getEntity().getType().equals(EntityType.PLAYER)) {
         Player player = (Player)event.getEntity();
         if (player.getFoodLevel() > event.getFoodLevel()) {
            Region region = this.moduleRegion.getPriorityRegionFlag(event.getEntity().getLocation(), FlagType.HUNGER);
            if (region != null) {
               String value = region.getFlagValue(FlagType.HUNGER);
               byte var6 = -1;
               switch(value.hashCode()) {
               case -1077769574:
                  if (value.equals("member")) {
                     var6 = 2;
                  }
                  break;
               case 3079692:
                  if (value.equals("deny")) {
                     var6 = 1;
                  }
                  break;
               case 92906313:
                  if (value.equals("allow")) {
                     var6 = 0;
                  }
               }

               switch(var6) {
               case 0:
                  event.setCancelled(false);
                  break;
               case 1:
                  event.setCancelled(true);
                  break;
               case 2:
                  if (region.isMember((Player)event.getEntity())) {
                     event.setCancelled(true);
                  }
               }
            }
         }
      }

   }

   @EventHandler(
      priority = EventPriority.LOWEST
   )
   public void onPlayerInteractEvent(PlayerInteractEvent event) {
      if (!event.useInteractedBlock().equals(Result.DENY) || !event.useItemInHand().equals(Result.DENY)) {
         if (!event.getPlayer().hasPermission("mineland.libs.region.interact")) {
            boolean hasBlock = event.getClickedBlock() != null && !event.getClickedBlock().getType().equals(Material.AIR);
            boolean hasItem = event.getItem() != null;
            if (hasBlock || hasItem) {
               boolean noMess = false;
               Player player = event.getPlayer();
               if (hasBlock) {
                  List<Region> regions = this.moduleRegion.getRegions(event.getClickedBlock().getLocation());
                  if (regions.isEmpty()) {
                     return;
                  }

                  String value;
                  if (this.moduleMaterial.isProtected(event.getClickedBlock().getType())) {
                     if (RegionUtil.checkProtect(regions, player, event)) {
                        noMess = true;
                     }
                  } else if (this.moduleMaterial.isUsed(event.getClickedBlock().getType())) {
                     RegionKey regionKey = this.moduleRegion.getPriorityRegionFlagKey(regions, FlagType.USE);
                     if (regionKey != null) {
                        value = regionKey.value;
                        byte var10 = -1;
                        switch(value.hashCode()) {
                        case -1077769574:
                           if (value.equals("member")) {
                              var10 = 2;
                           }
                           break;
                        case 3079692:
                           if (value.equals("deny")) {
                              var10 = 1;
                           }
                           break;
                        case 92906313:
                           if (value.equals("allow")) {
                              var10 = 0;
                           }
                        }

                        switch(var10) {
                        case 0:
                           event.setCancelled(false);
                           break;
                        case 1:
                           event.setUseInteractedBlock(Result.DENY);
                           event.setUseItemInHand(Result.DENY);
                           noMess = true;
                           break;
                        case 2:
                           if (!regionKey.region.isMember(player)) {
                              event.setUseInteractedBlock(Result.DENY);
                              event.setUseItemInHand(Result.DENY);
                           }
                        }
                     }
                  }

                  Material material = event.getClickedBlock().getType();
                  switch(material) {
                  case CHEST:
                  case FURNACE:
                  case BURNING_FURNACE:
                  case DROPPER:
                  case DISPENSER:
                  case FLOWER_POT:
                  case TRAPPED_CHEST:
                  case HOPPER:
                     RegionKey regionKey = this.moduleRegion.getPriorityRegionFlagKey(regions, FlagType.CONTAINER);
                     if (regionKey != null) {
                        String value = regionKey.value;
                        byte var11 = -1;
                        switch(value.hashCode()) {
                        case -1077769574:
                           if (value.equals("member")) {
                              var11 = 2;
                           }
                           break;
                        case 3079692:
                           if (value.equals("deny")) {
                              var11 = 1;
                           }
                           break;
                        case 92906313:
                           if (value.equals("allow")) {
                              var11 = 0;
                           }
                        }

                        switch(var11) {
                        case 0:
                           event.setCancelled(false);
                           break;
                        case 1:
                           event.setUseInteractedBlock(Result.DENY);
                           event.setUseItemInHand(Result.DENY);
                           Schedule.run(player::updateInventory);
                           noMess = true;
                           break;
                        case 2:
                           if (!regionKey.region.isMember(player)) {
                              event.setUseInteractedBlock(Result.DENY);
                              event.setUseItemInHand(Result.DENY);
                              Schedule.run(player::updateInventory);
                           }
                        }
                     }
                     break;
                  default:
                     if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.useInteractedBlock() == Result.ALLOW && event.hasItem()) {
                        Block relativeBlock = event.getClickedBlock().getRelative(event.getBlockFace());
                        if (!event.isCancelled() && event.getItem().getType().isBlock() && relativeBlock.getType() != Material.AIR && relativeBlock.getType().isTransparent()) {
                           this.checkBlockBreak(regions, player, event);
                           if (event.isCancelled()) {
                              event.setCancelled(false);
                              event.setUseInteractedBlock(Result.DENY);
                              Schedule.run(player::updateInventory);
                           }
                        }
                     }
                  }

                  if (event.useInteractedBlock().equals(Result.DENY)) {
                     value = this.moduleRegion.getFlagValue(regions, FlagType.ALLOW_INTERACT_BLOCKS);
                     if (value != null && FlagType.search(value, String.valueOf(event.getClickedBlock().getType().getId()))) {
                        event.setUseInteractedBlock(Result.ALLOW);
                     }
                  }
               }

               if (hasItem && event.useItemInHand().equals(Result.DENY)) {
                  String value = this.moduleRegion.getFlagValue(hasBlock ? event.getClickedBlock().getLocation() : player.getLocation(), FlagType.ALLOW_INTERACT_ITEMS);
                  if (value != null) {
                     if (FlagType.search(value, String.valueOf(event.getItem().getType().getId()))) {
                        event.setUseItemInHand(Result.ALLOW);
                     } else if (event.getItem().getType().isEdible() && FlagType.search(value, "eat")) {
                        event.setUseItemInHand(Result.ALLOW);
                     }
                  }
               }

               if (event.useInteractedBlock().equals(Result.DENY) && event.useItemInHand().equals(Result.DENY) && !noMess) {
                  PlayerUtil.sendMessageDenyKey(event.getPlayer(), "территория_защищена_для_вас");
               }

            }
         }
      }
   }

   @EventHandler(
      priority = EventPriority.LOWEST
   )
   public void onPlayerDropItemEvent(PlayerDropItemEvent event) {
      RegionKey regionKey = this.moduleRegion.getPriorityRegionFlagKey(event.getPlayer().getLocation(), FlagType.DROP_PLAYER);
      if (regionKey != null) {
         String value = regionKey.value;
         if (value != null) {
            byte var5 = -1;
            switch(value.hashCode()) {
            case -1077769574:
               if (value.equals("member")) {
                  var5 = 2;
               }
               break;
            case 3079692:
               if (value.equals("deny")) {
                  var5 = 1;
               }
               break;
            case 92906313:
               if (value.equals("allow")) {
                  var5 = 0;
               }
            }

            switch(var5) {
            case 0:
               event.setCancelled(false);
               break;
            case 1:
               event.setCancelled(true);
               break;
            case 2:
               if (!regionKey.region.isMember(event.getPlayer())) {
                  event.setCancelled(true);
               }
            }
         }
      }

   }

   @EventHandler(
      priority = EventPriority.LOWEST
   )
   public void onProjectileLaunch(ProjectileLaunchEvent event) {
      ProjectileSource projectileSource = event.getEntity().getShooter();
      if (projectileSource instanceof Player) {
         Player player = (Player)projectileSource;
         RegionKey regionKey = this.moduleRegion.getPriorityRegionFlagKey(player.getLocation(), FlagType.LAUNCH_PROJECTILE);
         if (regionKey != null) {
            String value = regionKey.value;
            if (value != null) {
               byte var7 = -1;
               switch(value.hashCode()) {
               case -1077769574:
                  if (value.equals("member")) {
                     var7 = 2;
                  }
                  break;
               case 3079692:
                  if (value.equals("deny")) {
                     var7 = 1;
                  }
                  break;
               case 92906313:
                  if (value.equals("allow")) {
                     var7 = 0;
                  }
               }

               switch(var7) {
               case 0:
                  event.setCancelled(false);
                  break;
               case 1:
                  event.setCancelled(true);
                  break;
               case 2:
                  if (!regionKey.region.isMember(player)) {
                     event.setCancelled(true);
                  }
               }
            }
         }
      }

   }

   @EventHandler(
      priority = EventPriority.LOWEST
   )
   public void onPlayerInteractEntityEvent(PlayerInteractEntityEvent event) {
      List<Region> regions = this.moduleRegion.getRegions(event.getRightClicked().getLocation());
      RegionUtil.checkProtect(regions, event.getPlayer(), event);
   }

   @EventHandler(
      priority = EventPriority.LOWEST
   )
   public void onPlayerArmorStandManipulateEvent(PlayerArmorStandManipulateEvent event) {
      List<Region> regions = this.moduleRegion.getRegions(event.getRightClicked().getLocation());
      RegionUtil.checkProtect(regions, event.getPlayer(), event);
   }

   @EventHandler(
      priority = EventPriority.LOWEST
   )
   public void onPlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent event) {
      if (event.getPlayer() != null) {
         List<Region> regions = this.moduleRegion.getRegions(event.getPlayer().getLocation());
         if (!regions.isEmpty()) {
            String value = this.moduleRegion.getFlagValue(regions, FlagType.COMMAND);
            if (value == null) {
               event.setCancelled(false);
            } else if (value.equals("allow")) {
               event.setCancelled(false);
            } else if (value.equals("deny")) {
               event.setCancelled(true);
            }

            String c;
            if (!event.isCancelled()) {
               value = this.moduleRegion.getFlagValue(regions, FlagType.DENY_COMMAND);
               if (value == null) {
                  event.setCancelled(false);
               } else {
                  c = event.getMessage();
                  if (FlagType.searchIgnoreCase(value, c)) {
                     event.setCancelled(true);
                  }
               }
            }

            if (event.isCancelled()) {
               value = this.moduleRegion.getFlagValue(regions, FlagType.ALLOW_COMMAND);
               if (value == null) {
                  event.setCancelled(true);
               } else {
                  c = event.getMessage();
                  if (FlagType.searchStartIngoreCase(value, c)) {
                     event.setCancelled(false);
                  }
               }
            }

            if (event.isCancelled()) {
               PlayerUtil.sendMessageDenyKey(event.getPlayer(), "тут_команда_запрещена");
            }
         }

      }
   }

   @EventHandler(
      priority = EventPriority.LOWEST
   )
   public void onEntityBlockFormEvent(EntityBlockFormEvent event) {
      if (event.getEntity().getType().equals(EntityType.PLAYER)) {
         Player player = (Player)event.getEntity();
         List<Region> regions = this.moduleRegion.getRegions(event.getBlock().getLocation());
         RegionUtil.checkProtect(regions, player, event);
      }
   }

   @EventHandler(
      priority = EventPriority.LOWEST
   )
   public void onPlayerTeleportEvent(PlayerTeleportEvent event) {
      String value = this.moduleRegion.getFlagValue(event.getFrom(), FlagType.DENY_TELEPORT_CAUSE);
      if (value == null) {
         event.setCancelled(false);
      } else if (FlagType.search(value, event.getCause().name())) {
         event.setCancelled(true);
         if (event.getCause().equals(TeleportCause.ENDER_PEARL)) {
            PlayerUtil.sendMessageDenyKey(event.getPlayer(), "эндер_перл_запрещен");
         }
      } else {
         event.setCancelled(false);
      }

   }

   @EventHandler(
      priority = EventPriority.LOWEST
   )
   public void onPlayerBucketFillEvent(PlayerBucketFillEvent event) {
      Block fill = event.getBlockClicked().getRelative(event.getBlockFace());
      List<Region> regions = this.moduleRegion.getRegions(fill.getLocation());
      RegionUtil.checkProtect(regions, event.getPlayer(), event);
   }

   @EventHandler(
      priority = EventPriority.LOWEST
   )
   public void onPlayerBucketEmptyEvent(PlayerBucketEmptyEvent event) {
      Block fill = event.getBlockClicked().getRelative(event.getBlockFace());
      List<Region> regions = this.moduleRegion.getRegions(fill.getLocation());
      RegionUtil.checkProtect(regions, event.getPlayer(), event);
   }

   @EventHandler(
      priority = EventPriority.LOWEST
   )
   public void onPlayerShearEntityEvent(PlayerShearEntityEvent event) {
      List<Region> regions = this.moduleRegion.getRegions(event.getEntity().getLocation());
      RegionUtil.checkProtect(regions, event.getPlayer(), event);
   }

   @EventHandler(
      priority = EventPriority.LOWEST
   )
   public void onPlayerPickupItemEvent(PlayerPickupItemEvent event) {
      if (event.getPlayer().hasPermission("mineland.libs.region.pickup")) {
         event.setCancelled(false);
      } else {
         RegionKey regionKey = this.moduleRegion.getPriorityRegionFlagKey(event.getItem().getLocation(), FlagType.PICKUP);
         if (regionKey != null) {
            String var3 = regionKey.value;
            byte var4 = -1;
            switch(var3.hashCode()) {
            case -1077769574:
               if (var3.equals("member")) {
                  var4 = 2;
               }
               break;
            case 3079692:
               if (var3.equals("deny")) {
                  var4 = 1;
               }
               break;
            case 92906313:
               if (var3.equals("allow")) {
                  var4 = 0;
               }
            }

            switch(var4) {
            case 0:
               event.setCancelled(false);
               break;
            case 1:
               event.setCancelled(true);
               break;
            case 2:
               if (!regionKey.region.isMember(event.getPlayer())) {
                  PlayerUtil.sendMessageDenyKey(event.getPlayer(), "вам_нельзя_тут_подбирать");
                  event.setCancelled(true);
               }
            }
         }

      }
   }
}
