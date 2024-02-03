package net.mineland.core.bukkit.modules.myevents;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import net.mineland.core.bukkit.modules.region.ModuleRegion;
import net.mineland.core.bukkit.modules.region.events.RegionKey;
import net.mineland.core.bukkit.modules.region.region.Region;
import net.mineland.core.bukkit.modules.region.region.flag.FlagType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.AreaEffectCloudApplyEvent;
import org.bukkit.event.entity.EntityCombustByEntityEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.vehicle.VehicleDamageEvent;

public class EventsDamage implements Listener {
   private ModuleRegion moduleRegion = ModuleRegion.getInstance();

   @EventHandler(
      priority = EventPriority.LOWEST
   )
   public void onAreaEffectCloudApplyEvent(AreaEffectCloudApplyEvent event) {
      this.potion(event, (Cancellable)null, event.getEntity(), event.getAffectedEntities(), this.moduleRegion.hasNegativePotion(event.getEntity()));
   }

   @EventHandler(
      priority = EventPriority.LOWEST
   )
   public void onPotionSplashEvent(PotionSplashEvent event) {
      this.potion(event, event, event.getEntity(), event.getAffectedEntities(), this.moduleRegion.hasNegativePotion(event.getPotion()));
   }

   private void potion(Event original, Cancellable event, Entity entity, Collection<LivingEntity> affected, boolean negative) {
      List<Region> regions = this.moduleRegion.getRegions(entity.getLocation());
      if (!regions.isEmpty()) {
         if (event != null) {
            RegionKey key = this.moduleRegion.getPriorityRegionFlagKey(regions, FlagType.POTION_SPLASH);
            if (key != null) {
               if (key.value.equals("allow")) {
                  event.setCancelled(false);
               } else if (key.value.equals("deny")) {
                  event.setCancelled(true);
               }
            }

            if (event.isCancelled()) {
               return;
            }
         }

         if (negative) {
            Iterator iterator = affected.iterator();

            while(iterator.hasNext()) {
               LivingEntity next = (LivingEntity)iterator.next();
               Cancellable cancellable = new Cancellable() {
                  private boolean cancel = false;

                  public boolean isCancelled() {
                     return this.cancel;
                  }

                  public void setCancelled(boolean cancel) {
                     this.cancel = cancel;
                  }
               };
               this.entityDamageByEntity(original, cancellable, entity, next);
               if (cancellable.isCancelled()) {
                  iterator.remove();
               }
            }
         }
      }

   }

   @EventHandler(
      priority = EventPriority.LOWEST
   )
   public void onEntityCombustByEntityEvent(EntityCombustByEntityEvent event) {
      this.entityDamageByEntity(event, event, event.getCombuster(), event.getEntity());
   }

   @EventHandler(
      priority = EventPriority.LOWEST
   )
   public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
      this.entityDamageByEntity(event, event, event.getDamager(), event.getEntity());
   }

   @EventHandler(
      priority = EventPriority.LOWEST
   )
   public void on(VehicleDamageEvent event) {
      this.entityDamageByEntity(event, event, event.getAttacker(), event.getVehicle());
   }

   private void entityDamageByEntity(Event original, Cancellable event, Entity damagerEntity, Entity entity) {
      if (damagerEntity != null) {
         DamageCause cause;
         if (original instanceof EntityDamageByEntityEvent) {
            cause = ((EntityDamageByEntityEvent)original).getCause();
         } else if (original instanceof EntityCombustByEntityEvent) {
            cause = DamageCause.FIRE_TICK;
         } else if (original instanceof VehicleDamageEvent) {
            cause = DamageCause.ENTITY_ATTACK;
         } else if (original instanceof AreaEffectCloudApplyEvent) {
            cause = DamageCause.MAGIC;
         } else {
            if (!(original instanceof PotionSplashEvent)) {
               throw new UnsupportedOperationException(original.getClass() + " unsupported damage event");
            }

            cause = DamageCause.MAGIC;
         }

         EntityDamageByEntityMyEvent myEvent = new EntityDamageByEntityMyEvent(original, entity, damagerEntity, cause, event.isCancelled());
         Bukkit.getPluginManager().callEvent(myEvent);
         event.setCancelled(myEvent.isCancelled());
      }
   }
}
