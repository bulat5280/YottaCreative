package net.mineland.core.bukkit.modules.user;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerChangeLangMyEvent extends Event {
   private static final HandlerList handlers = new HandlerList();
   private User user;
   private Lang from;
   private Lang to;

   public PlayerChangeLangMyEvent(User user, Lang from, Lang to) {
      this.user = user;
      this.from = from;
      this.to = to;
   }

   public static HandlerList getHandlerList() {
      return handlers;
   }

   public HandlerList getHandlers() {
      return handlers;
   }

   public User getUser() {
      return this.user;
   }

   public Lang getFrom() {
      return this.from;
   }

   public Lang getTo() {
      return this.to;
   }

   public void setTo(Lang to) {
      this.to = to;
   }
}
