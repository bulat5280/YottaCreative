package net.mineland.core.bukkit.modules.region.events;

import net.mineland.core.bukkit.modules.region.region.Region;
import net.mineland.core.bukkit.modules.user.User;
import org.bukkit.Location;
import org.bukkit.event.HandlerList;

public class PlayerQuitRegionEvent extends PlayerRegionEvent {
   private static final HandlerList handlers = new HandlerList();

   public PlayerQuitRegionEvent(User settings, Region region, Location to, Location from) {
      super(settings, region, to, from);
   }

   public static HandlerList getHandlerList() {
      return handlers;
   }

   public HandlerList getHandlers() {
      return handlers;
   }
}
