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
import org.bukkit.event.block.BlockBreakEvent;
import ua.govnojon.libs.bukkitutil.ItemData;

public class PlayerBlockBreakActivator extends Activator {
   public PlayerBlockBreakActivator(Plot plot) {
      super(plot);
   }

   public ActivatorType getType() {
      return ActivatorType.PLAYER_BLOCK_BREAK;
   }

   public ItemData getIcon() {
      return new ItemData(Material.COBBLESTONE);
   }

   public static class Event extends GamePlayerEvent implements Cancellable, BlockEvent {
      public Event(User user, Plot plot, BlockBreakEvent event) {
         super(user, plot, event);
      }

      public Block getBlock() {
         return ((BlockBreakEvent)this.getHandleEvent()).getBlock();
      }

      public boolean isCancelled() {
         return ((BlockBreakEvent)this.getHandleEvent()).isCancelled();
      }

      public void setCancelled(boolean b) {
         ((BlockBreakEvent)this.getHandleEvent()).setCancelled(b);
      }
   }
}
