package net.mineland.core.bukkit.modules.region.events;

import java.util.List;
import net.mineland.core.bukkit.modules.myevents.EntityDamageByEntityMyEvent;
import net.mineland.core.bukkit.modules.region.ModuleRegion;
import net.mineland.core.bukkit.modules.region.region.Region;
import net.mineland.core.bukkit.modules.region.region.flag.FlagType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingBreakEvent.RemoveCause;
import ua.govnojon.libs.bukkitutil.PlayerUtil;

public class ListenersRegionDamage implements Listener {
   private ModuleRegion moduleRegion = ModuleRegion.getInstance();

   @EventHandler(
      priority = EventPriority.LOWEST,
      ignoreCancelled = true
   )
   public void onEntityDamageEvent(EntityDamageEvent event) {
      List<Region> regions = this.moduleRegion.getRegions(event.getEntity().getLocation());
      if (event.getEntity() instanceof Player) {
         String value = this.moduleRegion.getFlagValue(regions, FlagType.DAMAGE_PLAYER);
         if (value != null) {
            if (value.equals("allow")) {
               event.setCancelled(false);
            } else if (value.equals("deny")) {
               event.setCancelled(true);
            }
         }
      }

   }

   @EventHandler(
      priority = EventPriority.LOWEST,
      ignoreCancelled = true
   )
   public void on(HangingBreakByEntityEvent event) {
      if (event.getCause().equals(RemoveCause.EXPLOSION)) {
         String value = this.moduleRegion.getFlagValue(event.getEntity().getLocation(), FlagType.ENTITY_EXPLODE);
         if (value != null) {
            if (value.equals("allow")) {
               event.setCancelled(false);
            } else if (value.equals("deny")) {
               event.setCancelled(true);
            }
         }
      }

   }

   @EventHandler(
      priority = EventPriority.LOWEST,
      ignoreCancelled = true
   )
   private void on(EntityDamageByEntityMyEvent event) {
      Entity damagerEntity = event.getDamager();
      Entity entity = event.getEntity();
      Player damager = PlayerUtil.getDamager(damagerEntity);
      EntityType type = entity.getType();
      if (damager != null) {
         if (type.equals(EntityType.PLAYER)) {
            Player player = (Player)entity;
            Region region = this.moduleRegion.getPriorityRegionFlag(damager.getLocation(), FlagType.PVP);
            String value;
            byte var10;
            if (region != null) {
               value = region.getFlagValue(FlagType.PVP);
               var10 = -1;
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
                  event.setCancelled(true);
                  break;
               case 2:
                  if (!region.isMember(player) || !region.isMember(damager)) {
                     event.setCancelled(true);
                     PlayerUtil.sendMessageDenyKey(damager, "на_этой_тере_пвп_офф_для_вас");
                  }
               }
            }

            if (!event.isCancelled()) {
               region = this.moduleRegion.getPriorityRegionFlag(player.getLocation(), FlagType.PVP);
               if (region != null) {
                  value = region.getFlagValue(FlagType.PVP);
                  var10 = -1;
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
                     event.setCancelled(true);
                     break;
                  case 2:
                     if (!region.isMember(player) || !region.isMember(damager)) {
                        PlayerUtil.sendMessageDenyKey(damager, "на_этой_тере_пвп_офф_для_вас");
                        event.setCancelled(true);
                     }
                  }
               }
            }
         } else if (!type.equals(EntityType.PAINTING) && !type.equals(EntityType.ITEM_FRAME)) {
            Region region = this.moduleRegion.getPriorityRegionFlag(entity.getLocation(), FlagType.DAMAGE_ENTITY);
            if (region != null) {
               String value = region.getFlagValue(FlagType.DAMAGE_ENTITY);
               byte var9 = -1;
               switch(value.hashCode()) {
               case -1077769574:
                  if (value.equals("member")) {
                     var9 = 2;
                  }
                  break;
               case 3079692:
                  if (value.equals("deny")) {
                     var9 = 1;
                  }
                  break;
               case 92906313:
                  if (value.equals("allow")) {
                     var9 = 0;
                  }
               }

               switch(var9) {
               case 0:
                  event.setCancelled(false);
                  break;
               case 1:
                  event.setCancelled(true);
                  break;
               case 2:
                  if (!region.isMember(damager) && !damager.hasPermission("mineland.libs.region.damageentity")) {
                     event.setCancelled(true);
                     PlayerUtil.sendMessageDenyKey(damager, "на_этой_тере_дамаг_офф_для_вас");
                  }
               }
            }
         } else {
            List<Region> regions = this.moduleRegion.getRegions(entity.getLocation());
            RegionUtil.checkProtect(regions, damager, event);
         }
      }

   }
}
