package net.mineland.core.bukkit.modules.mainitem.defaultmainitems;

import net.mineland.core.bukkit.modules.mainitem.MainBar;
import net.mineland.core.bukkit.modules.mainitem.MainItem;
import net.mineland.core.bukkit.modules.myevents.UserInteractMyEvent;
import net.mineland.core.bukkit.modules.user.User;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class MainItemCommand extends MainItem {
   private String command;

   public MainItemCommand(String key, User player, ItemStack item, String text, int slot, String command) {
      super(key, player, item, text, slot);
      this.command = command;
   }

   public MainItemCommand(MainBar mainBar, ItemStack item, String text, int slot, String command) {
      super(mainBar, item, text, slot);
      this.command = command;
   }

   public MainItemCommand(MainBar mainBar, Material material, String text, int slot, String command) {
      super(mainBar, material, text, slot);
      this.command = command;
   }

   public void click(UserInteractMyEvent event) {
      this.getUser().getPlayer().chat(this.command);
      event.setCancelled(true);
   }

   public String getCommand() {
      return this.command;
   }
}
