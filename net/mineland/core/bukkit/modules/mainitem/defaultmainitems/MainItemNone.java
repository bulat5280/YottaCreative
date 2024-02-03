package net.mineland.core.bukkit.modules.mainitem.defaultmainitems;

import net.mineland.core.bukkit.modules.mainitem.MainBar;
import net.mineland.core.bukkit.modules.mainitem.MainItem;
import net.mineland.core.bukkit.modules.myevents.UserInteractMyEvent;
import org.bukkit.inventory.ItemStack;

public class MainItemNone extends MainItem {
   public MainItemNone(MainBar mainBar, ItemStack item, String text, int slot) {
      super(mainBar, item, text, slot);
   }

   public void click(UserInteractMyEvent event) {
   }
}
