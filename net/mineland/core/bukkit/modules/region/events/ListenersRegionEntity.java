package net.mineland.core.bukkit.modules.region.events;

import java.util.Iterator;
import java.util.List;
import net.mineland.core.bukkit.modules.myevents.PlayerEntitySpawnMyEvent;
import net.mineland.core.bukkit.modules.region.ModuleRegion;
import net.mineland.core.bukkit.modules.region.region.Region;
import net.mineland.core.bukkit.modules.region.region.flag.FlagType;
import net.mineland.core.bukkit.modules.user.User;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import ua.govnojon.libs.bukkitutil.PlayerUtil;

public class ListenersRegionEntity implements Listener {
   private ModuleRegion moduleRegion = ModuleRegion.getInstance();

   @EventHandler(
      priority = EventPriority.LOWEST
   )
   public void onCreatureSpawnEvent(CreatureSpawnEvent event) {
      this.onSpawn(event, event.getEntity(), this.moduleRegion.getRegions(event.getLocation()), event.getSpawnReason());
   }

   @EventHandler(
      priority = EventPriority.LOW,
      ignoreCancelled = true
   )
   public void onPlayerEntitySpawnMyEvent(PlayerEntitySpawnMyEvent event) {
      List<Region> regions = this.moduleRegion.getRegions(event.getEntity().getLocation());
      this.onSpawn(event, event.getEntity(), regions, SpawnReason.EGG);
      if (!event.isCancelled()) {
         RegionKey key = this.moduleRegion.getPriorityRegionFlagKey(regions, FlagType.PLAYER_SPAWN_ENTITY);
         if (key != null) {
            String var4 = key.value;
            byte var5 = -1;
            switch(var4.hashCode()) {
            case -1077769574:
               if (var4.equals("member")) {
                  var5 = 2;
               }
               break;
            case 3079692:
               if (var4.equals("deny")) {
                  var5 = 1;
               }
               break;
            case 92906313:
               if (var4.equals("allow")) {
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
               if (!key.region.isMember(event.getPlayer()) && !event.getPlayer().hasPermission("mineland.libs.region.spawningentity")) {
                  PlayerUtil.sendMessageDenyKey(event.getUser(), "вы_не_можете_спавнить_мобов_тут");
                  event.setCancelled(true);
               }
            }
         }
      }

   }

   private void onSpawn(Cancellable event, Entity entity, List<Region> regions, SpawnReason reason) {
      if (!regions.isEmpty()) {
         String value = this.moduleRegion.getFlagValue(regions, FlagType.SPAWN);
         if (value != null) {
            if (value.equals("allow")) {
               event.setCancelled(false);
            } else if (value.equals("deny")) {
               event.setCancelled(true);
            }
         }

         if (!event.isCancelled()) {
            value = this.moduleRegion.getFlagValue(regions, FlagType.DENY_SPAWN_REASON);
            if (value == null) {
               event.setCancelled(false);
            } else if (FlagType.search(value, reason.name())) {
               event.setCancelled(true);
            } else {
               event.setCancelled(false);
            }
         }

         if (event.isCancelled()) {
            value = this.moduleRegion.getFlagValue(regions, FlagType.ALLOW_SPAWN);
            if (value == null) {
               event.setCancelled(true);
            } else if (FlagType.search(value, entity.getType().name())) {
               event.setCancelled(false);
            } else {
               event.setCancelled(true);
            }
         }

         if (!event.isCancelled()) {
            value = this.moduleRegion.getFlagValue(regions, FlagType.DENY_SPAWN);
            if (value == null) {
               event.setCancelled(false);
            } else if (FlagType.search(value, entity.getType().name())) {
               event.setCancelled(true);
            } else {
               event.setCancelled(false);
            }
         }

         if (!event.isCancelled()) {
            RegionKey key = this.moduleRegion.getPriorityRegionFlagKey(regions, FlagType.ENTITY_SPAWN_LIMIT);
            if (key == null) {
               event.setCancelled(false);
            } else {
               Integer limit = null;

               try {
                  limit = Integer.parseInt(key.value);
               } catch (Exception var12) {
                  event.setCancelled(false);
               }

               if (limit == null) {
                  event.setCancelled(false);
               } else {
                  int amount = 0;
                  Iterator var9;
                  if (limit > 0) {
                     var9 = key.region.getEntities().iterator();

                     while(var9.hasNext()) {
                        Entity e = (Entity)var9.next();
                        if (e instanceof LivingEntity && !e.getType().equals(EntityType.PLAYER)) {
                           ++amount;
                        }
                     }
                  }

                  if (amount >= limit) {
                     event.setCancelled(true);
                     var9 = key.region.getMembers().iterator();

                     while(var9.hasNext()) {
                        String id = (String)var9.next();
                        User user = User.getUser(id);
                        if (user != null) {
                           PlayerUtil.sendMessageDenyKey(user, "лимит_мобов", "%limit%", String.valueOf(limit));
                        }
                     }
                  }
               }
            }
         }
      }

   }

   @EventHandler(
      priority = EventPriority.LOWEST
   )
   public void onEntityChangeBlockEvent(EntityChangeBlockEvent event) {
      EntityType type = event.getEntityType();
      if (event.getBlock().getType().equals(Material.SOIL) && type.equals(EntityType.PLAYER)) {
         Region region = this.moduleRegion.getPriorityRegionFlag(event.getBlock().getLocation(), FlagType.TRAMPLE_SOIL);
         if (region != null) {
            String value = region.getFlagValue(FlagType.TRAMPLE_SOIL);
            if (value != null) {
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
                  if (!region.isMember((Player)event.getEntity())) {
                     event.setCancelled(true);
                  }
               }
            }
         }
      } else if (event.getEntity().getType().equals(EntityType.ENDERMAN)) {
         String value = this.moduleRegion.getFlagValue(event.getBlock().getLocation(), FlagType.PROTECT);
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
               event.setCancelled(true);
            }
         }
      }

   }

   @EventHandler(
      priority = EventPriority.LOWEST
   )
   public void onEntityExplodeEvent(EntityExplodeEvent event) {
      String value = this.moduleRegion.getFlagValue(event.getLocation(), FlagType.ENTITY_EXPLODE);
      if (value != null) {
         if (value.equals("allow")) {
            event.setCancelled(false);
         } else if (value.equals("deny")) {
            event.blockList().clear();
         }
      }

   }
}
