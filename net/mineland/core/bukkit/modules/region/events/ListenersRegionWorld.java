package net.mineland.core.bukkit.modules.region.events;

import java.util.Iterator;
import net.mineland.core.bukkit.modules.region.ModuleRegion;
import net.mineland.core.bukkit.modules.region.region.flag.FlagType;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.block.BlockIgniteEvent.IgniteCause;
import org.bukkit.event.entity.ItemSpawnEvent;

public class ListenersRegionWorld implements Listener {
   private ModuleRegion moduleRegion = ModuleRegion.getInstance();
   private boolean isSlimeRetractAvailable = true;

   @EventHandler(
      priority = EventPriority.LOWEST
   )
   public void onBlockFadeEvent(BlockFadeEvent event) {
      Material old = event.getBlock().getType();
      Material neW = event.getNewState().getType();
      String value;
      if (old.equals(Material.SOIL) && neW.equals(Material.DIRT)) {
         value = this.moduleRegion.getFlagValue(event.getBlock().getLocation(), FlagType.SOIL_FADE);
         if (value != null) {
            if (value.equals("allow")) {
               event.setCancelled(false);
            } else if (value.equals("deny")) {
               event.setCancelled(true);
            }
         }
      } else if (old.equals(Material.ICE) && neW.equals(Material.STATIONARY_WATER)) {
         value = this.moduleRegion.getFlagValue(event.getBlock().getLocation(), FlagType.ICE_FADE);
         if (value != null) {
            if (value.equals("allow")) {
               event.setCancelled(false);
            } else if (value.equals("deny")) {
               event.setCancelled(true);
            }
         }
      } else if (old.equals(Material.SNOW) && neW.equals(Material.AIR)) {
         value = this.moduleRegion.getFlagValue(event.getBlock().getLocation(), FlagType.SNOW_MELTS);
         if (value != null) {
            if (value.equals("allow")) {
               event.setCancelled(false);
            } else if (value.equals("deny")) {
               event.setCancelled(true);
            }
         }
      } else if (old.equals(Material.AIR) && neW.equals(Material.SNOW)) {
         value = this.moduleRegion.getFlagValue(event.getBlock().getLocation(), FlagType.SNOWFALL);
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
      priority = EventPriority.LOWEST
   )
   public void on(LeavesDecayEvent event) {
      String value = this.moduleRegion.getFlagValue(event.getBlock().getLocation(), FlagType.LEAF_DECAY);
      if (value != null) {
         if (value.equals("allow")) {
            event.setCancelled(false);
         } else if (value.equals("deny")) {
            event.setCancelled(true);
         }
      }

   }

   @EventHandler
   public void onBlockFormEvent(BlockFormEvent event) {
      Material old = event.getBlock().getType();
      Material neW = event.getNewState().getType();
      String value;
      if (old.equals(Material.AIR) && neW.equals(Material.SNOW)) {
         value = this.moduleRegion.getFlagValue(event.getBlock().getLocation(), FlagType.SNOWFALL);
         if (value != null) {
            if (value.equals("allow")) {
               event.setCancelled(false);
            } else if (value.equals("deny")) {
               event.setCancelled(true);
            }
         }
      } else if (old.equals(Material.STATIONARY_WATER) && neW.equals(Material.ICE)) {
         value = this.moduleRegion.getFlagValue(event.getBlock().getLocation(), FlagType.ICE_FORM);
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
      priority = EventPriority.LOWEST
   )
   public void onItemSpawnEvent(ItemSpawnEvent event) {
      String value = this.moduleRegion.getFlagValue(event.getLocation(), FlagType.DROP);
      if (value != null) {
         if (value.equals("allow")) {
            event.setCancelled(false);
         } else if (value.equals("deny")) {
            event.setCancelled(true);
         }
      }

   }

   @EventHandler(
      priority = EventPriority.LOWEST,
      ignoreCancelled = true
   )
   public void onBlockIgniteEvent(BlockIgniteEvent event) {
      if (!event.getCause().equals(IgniteCause.FLINT_AND_STEEL)) {
         String value = this.moduleRegion.getFlagValue(event.getBlock().getLocation(), FlagType.IGNITE);
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
   public void onExplodeEvent(BlockExplodeEvent event) {
      String value = this.moduleRegion.getFlagValue(event.getBlock().getLocation(), FlagType.ENTITY_EXPLODE);
      if (value != null) {
         if (value.equals("deny")) {
            event.setCancelled(true);
         } else if (value.equals("allow")) {
            event.setCancelled(false);
         }
      }

   }

   @EventHandler(
      priority = EventPriority.LOWEST
   )
   public void onBlockBurnEvent(BlockBurnEvent event) {
      String value = this.moduleRegion.getFlagValue(event.getBlock().getLocation(), FlagType.IGNITE);
      if (value != null) {
         if (value.equals("allow")) {
            event.setCancelled(false);
         } else if (value.equals("deny")) {
            event.setCancelled(true);
         }
      }

   }

   public boolean isInTheSameRegion(Location location1, Location location2) {
      return this.moduleRegion.getRegions(location1).equals(this.moduleRegion.getRegions(location2));
   }

   @EventHandler(
      priority = EventPriority.LOW,
      ignoreCancelled = true
   )
   public void onExtend(BlockPistonExtendEvent event) {
      Location pistonlocation = event.getBlock().getLocation();
      Iterator var3 = event.getBlocks().iterator();

      while(var3.hasNext()) {
         Block block = (Block)var3.next();
         if (!this.isInTheSameRegion(pistonlocation, block.getLocation()) || !this.isInTheSameRegion(pistonlocation, block.getRelative(event.getDirection()).getLocation())) {
            event.setCancelled(true);
            break;
         }
      }

   }

   @EventHandler(
      priority = EventPriority.LOW,
      ignoreCancelled = true
   )
   public void onRetract(BlockPistonRetractEvent event) {
      Location pistonlocation = event.getBlock().getLocation();
      if (event.isSticky()) {
         if (this.isSlimeRetractAvailable) {
            try {
               Iterator var3 = event.getBlocks().iterator();

               while(var3.hasNext()) {
                  Block block = (Block)var3.next();
                  if (!this.isInTheSameRegion(pistonlocation, block.getLocation()) || !this.isInTheSameRegion(pistonlocation, block.getRelative(event.getDirection()).getLocation())) {
                     event.setCancelled(true);
                     break;
                  }
               }

               return;
            } catch (NoSuchMethodError var5) {
               this.isSlimeRetractAvailable = false;
            }
         }

         if (!this.isInTheSameRegion(event.getBlock().getLocation(), event.getRetractLocation())) {
            event.setCancelled(true);
         }
      }

   }
}
