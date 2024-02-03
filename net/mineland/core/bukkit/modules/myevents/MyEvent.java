package net.mineland.core.bukkit.modules.myevents;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class MyEvent extends Event {
   private static final HandlerList handlers = new HandlerList();

   public static HandlerList getHandlerList() {
      return handlers;
   }

   public HandlerList getHandlers() {
      return handlers;
   }
}
