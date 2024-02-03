package net.mineland.creative.modules.coding.activators.player;

import net.mineland.core.bukkit.modules.user.User;
import net.mineland.creative.modules.coding.activators.Activator;
import net.mineland.creative.modules.coding.activators.ActivatorType;
import net.mineland.creative.modules.coding.events.ChatEvent;
import net.mineland.creative.modules.coding.events.GamePlayerEvent;
import net.mineland.creative.modules.creative.Plot;
import org.bukkit.Material;
import org.bukkit.event.Cancellable;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import ua.govnojon.libs.bukkitutil.ItemData;

public class PlayerChatActivator extends Activator {
   public PlayerChatActivator(Plot plot) {
      super(plot);
   }

   public ActivatorType getType() {
      return ActivatorType.PLAYER_CHAT;
   }

   public ItemData getIcon() {
      return new ItemData(Material.FEATHER);
   }

   public static class Event extends GamePlayerEvent implements Cancellable, ChatEvent {
      public Event(User user, Plot plot, AsyncPlayerChatEvent event) {
         super(user, plot, event);
      }

      public String getMessage() {
         return ((AsyncPlayerChatEvent)this.getHandleEvent()).getMessage();
      }

      public boolean isCancelled() {
         return ((Cancellable)this.getHandleEvent()).isCancelled();
      }

      public void setCancelled(boolean b) {
         ((Cancellable)this.getHandleEvent()).setCancelled(b);
      }
   }
}
