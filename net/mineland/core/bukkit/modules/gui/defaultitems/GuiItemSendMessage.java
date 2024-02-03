package net.mineland.core.bukkit.modules.gui.defaultitems;

import net.mineland.core.bukkit.modules.gui.GuiItem;
import net.mineland.core.bukkit.modules.gui.GuiMenu;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class GuiItemSendMessage extends GuiItem {
   private boolean close;
   private String message;

   public GuiItemSendMessage(GuiMenu guiMenu, int x, int y, Material material, short data, int amount, String text, String message, boolean close) {
      super(guiMenu, x, y, material, data, amount);
      this.setText(text);
      this.close = close;
      this.message = message;
   }

   public GuiItemSendMessage(GuiMenu guiMenu, int x, int y, ItemStack item, String text, String message, boolean close) {
      super(guiMenu, x, y, item);
      this.setText(text);
      this.close = close;
      this.message = message;
   }

   public void click(InventoryClickEvent event) {
      if (this.close) {
         event.getWhoClicked().closeInventory();
      }

      event.getWhoClicked().sendMessage(this.message);
   }
}
