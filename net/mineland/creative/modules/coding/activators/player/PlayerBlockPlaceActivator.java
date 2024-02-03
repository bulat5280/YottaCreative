package net.mineland.creative.modules.coding.activators.player;

import net.mineland.core.bukkit.modules.user.User;
import net.mineland.creative.modules.coding.activators.Activator;
import net.mineland.creative.modules.coding.activators.ActivatorType;
import net.mineland.creative.modules.coding.events.BlockEvent;
import net.mineland.creative.modules.coding.events.GamePlayerEvent;
import net.mineland.creative.modules.creative.Plot;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.Cancellable;
import org.bukkit.event.block.BlockPlaceEvent;
import ua.govnojon.libs.bukkitutil.ItemData;

public class PlayerBlockPlaceActivator extends Activator {
   public PlayerBlockPlaceActivator(Plot plot) {
      super(plot);
   }

   public ActivatorType getType() {
      return ActivatorType.PLAYER_BLOCK_PLACE;
   }

   public ItemData getIcon() {
      return new ItemData(Material.STONE);
   }

   public static class Event extends GamePlayerEvent implements Cancellable, BlockEvent {
      public Event(User user, Plot plot, BlockPlaceEvent event) {
         super(user, plot, event);
      }

      public Block getBlock() {
         return ((BlockPlaceEvent)this.getHandleEvent()).getBlock();
      }

      public boolean isCancelled() {
         return ((BlockPlaceEvent)this.getHandleEvent()).isCancelled();
      }

      public void setCancelled(boolean b) {
         ((BlockPlaceEvent)this.getHandleEvent()).setCancelled(b);
      }
   }
}
