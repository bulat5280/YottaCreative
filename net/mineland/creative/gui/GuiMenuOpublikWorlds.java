package net.mineland.creative.gui;

import net.mineland.api.user.IUser;
import net.mineland.core.bukkit.module.Module;
import net.mineland.core.bukkit.modules.gui.GuiItem;
import net.mineland.core.bukkit.modules.gui.GuiMenu;
import net.mineland.core.bukkit.modules.message.Message;
import net.mineland.core.bukkit.modules.user.User;
import net.mineland.creative.modules.creative.ModuleCreative;
import net.mineland.creative.modules.creative.Plot;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class GuiMenuOpublikWorlds extends GuiMenu {
   private static ModuleCreative moduleCreative = (ModuleCreative)Module.getInstance(ModuleCreative.class);

   public GuiMenuOpublikWorlds(String key, User user) {
      super(user, 6, Message.getMessage((IUser)user, "creative.опубликованные_миры"));
      this.init();
   }

   private void init() {
      Plot plot = moduleCreative.getPlotManager().getCurrentPlot(this.getUser());
   }

   private static class GuiItemWorld extends GuiItem {
      public GuiItemWorld(GuiMenu guiMenu, int x, int y, ItemStack item) {
         super(guiMenu, x, y, item);
         String text = "";
      }

      public void click(InventoryClickEvent inventoryClickEvent) {
      }
   }
}
