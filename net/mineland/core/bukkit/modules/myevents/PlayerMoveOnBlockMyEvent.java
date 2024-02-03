package net.mineland.core.bukkit.modules.myevents;

import net.mineland.core.bukkit.modules.user.User;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PlayerMoveOnBlockMyEvent extends PlayerEvent implements Cancellable {
   private static final HandlerList handlers = new HandlerList();
   private User settings;
   private Location from;
   private Location to;
   private boolean cancel;
   private boolean isEdit = false;

   public PlayerMoveOnBlockMyEvent(Player player, Location from, Location to, boolean cancel) {
      super(player);
      this.settings = User.getUser(player);
      this.from = from;
      this.to = to;
      this.cancel = cancel;
   }

   public static HandlerList getHandlerList() {
      return handlers;
   }

   public User getUser() {
      return this.settings;
   }

   public boolean isCancelled() {
      return this.cancel;
   }

   public void setCancelled(boolean b) {
      this.cancel = b;
      this.isEdit = true;
   }

   public Location getFrom() {
      return this.from;
   }

   public void setFrom(Location from) {
      this.from = from;
      this.isEdit = true;
   }

   public Location getTo() {
      return this.to;
   }

   public void setTo(Location to) {
      this.to = to;
      this.isEdit = true;
   }

   public boolean isEdit() {
      return this.isEdit;
   }

   public HandlerList getHandlers() {
      return handlers;
   }
}
