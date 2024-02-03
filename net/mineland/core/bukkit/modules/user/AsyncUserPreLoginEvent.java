package net.mineland.core.bukkit.modules.user;

import java.net.InetAddress;
import java.util.UUID;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

public class AsyncUserPreLoginEvent extends AsyncPlayerPreLoginEvent {
   private static final HandlerList handlers = new HandlerList();
   private User user;

   public AsyncUserPreLoginEvent(String name, InetAddress ipAddress, UUID uniqueId, User user) {
      super(name, ipAddress, uniqueId);
      this.user = user;
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
}
