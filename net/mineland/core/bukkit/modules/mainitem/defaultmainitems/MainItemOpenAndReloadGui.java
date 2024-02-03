package net.mineland.core.bukkit.modules.mainitem.defaultmainitems;

import net.mineland.core.bukkit.modules.gui.GuiMenu;
import net.mineland.core.bukkit.modules.mainitem.MainBar;
import net.mineland.core.bukkit.modules.myevents.UserInteractMyEvent;
import net.mineland.core.bukkit.modules.user.User;
import org.bukkit.inventory.ItemStack;

public class MainItemOpenAndReloadGui extends MainItemOpenGui {
   public MainItemOpenAndReloadGui(String key, User player, ItemStack item, String text, int slot, String keyGui) {
      super(key, player, item, text, slot, keyGui);
   }

   public MainItemOpenAndReloadGui(User player, ItemStack item, String text, int slot, String keyGui) {
      super(player, item, text, slot, keyGui);
   }

   public MainItemOpenAndReloadGui(MainBar mainBar, ItemStack item, String text, int slot, String keyGui) {
      super(mainBar, item, text, slot, keyGui);
   }

   public void click(UserInteractMyEvent event) {
      User user = event.getUser();
      GuiMenu menu = getModuleGui().getGuiPlayer(this.getUser(), this.getKeyGui());
      if (getModuleGui().isLoadedGui(user.getPlayer(), this.getKeyGui())) {
         getModuleGui().reloadGui(user.getPlayer(), this.getKeyGui());
         menu.openOwner();
      }

   }
}
