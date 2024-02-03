package net.mineland.core.bukkit.modules.myevents;

import net.mineland.core.bukkit.modules.user.User;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public abstract class UserEvent extends PlayerEvent {
   private static final HandlerList handlers = new HandlerList();
   private User user;

   public UserEvent(User user) {
      super(user.getPlayer());
      this.user = user;
   }

   public static HandlerList getHandlerList() {
      return handlers;
   }

   public User getUser() {
      return this.user;
   }

   public HandlerList getHandlers() {
      return handlers;
   }
}
