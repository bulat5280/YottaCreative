package net.mineland.core.bukkit.modules.gui;

import net.mineland.core.bukkit.modules.user.GetterUser;
import net.mineland.core.bukkit.modules.user.User;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GuiMenuLoadEvent extends Event implements GetterUser {
   private static final HandlerList handlers = new HandlerList();
   private User user;
   private GuiMenu gui;

   public GuiMenuLoadEvent(User user, GuiMenu gui) {
      this.user = user;
      this.gui = gui;
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

   public GuiMenu getGui() {
      return this.gui;
   }
}
