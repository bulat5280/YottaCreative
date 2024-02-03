package net.mineland.core.bukkit.modules.sign;

import net.mineland.core.bukkit.modules.user.User;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class SignSendEvent extends PlayerEvent {
   private static final HandlerList handlers = new HandlerList();
   private User settings;
   private String[] lines;

   public SignSendEvent(User user, String[] lines) {
      super(user.getPlayer());
      this.settings = user;
      this.lines = lines;
   }

   public static HandlerList getHandlerList() {
      return handlers;
   }

   public HandlerList getHandlers() {
      return handlers;
   }

   public User getUser() {
      return this.settings;
   }

   public void setLine(int i, String line) {
      this.lines[i] = line;
   }

   public String getLine(int i) {
      return this.lines[i];
   }

   public String[] getLines() {
      return this.lines;
   }

   public void setLines(String[] lines) {
      System.arraycopy(lines, 0, this.lines, 0, this.lines.length);
   }
}
