package net.mineland.core.bukkit.module;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ModuleReloadEvent extends Event {
   private static final HandlerList handlers = new HandlerList();
   private Module module;

   public ModuleReloadEvent(Module module) {
      this.module = module;
   }

   public static HandlerList getHandlerList() {
      return handlers;
   }

   public Module getModule() {
      return this.module;
   }

   public HandlerList getHandlers() {
      return handlers;
   }
}
