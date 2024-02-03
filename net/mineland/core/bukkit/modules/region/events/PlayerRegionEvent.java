package net.mineland.core.bukkit.modules.region.events;

import net.mineland.core.bukkit.modules.region.region.Region;
import net.mineland.core.bukkit.modules.user.User;
import org.bukkit.Location;
import org.bukkit.event.player.PlayerEvent;

public abstract class PlayerRegionEvent extends PlayerEvent {
   private Region region;
   private Location to;
   private Location from;
   private User settings;

   public PlayerRegionEvent(User settings, Region region, Location to, Location from) {
      super(settings.getPlayer());
      this.region = region;
      this.settings = settings;
      this.from = from;
      this.to = to;
   }

   public Region getRegion() {
      return this.region;
   }

   public User getUser() {
      return this.settings;
   }

   public Location getFrom() {
      return this.from;
   }

   public Location getTo() {
      return this.to;
   }
}
