package net.mineland.core.bukkit.modules.gui.defaultitems;

import net.mineland.core.bukkit.modules.gui.GuiItem;
import net.mineland.core.bukkit.modules.gui.GuiMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GuiItemCommand extends GuiItem {
   private Player player = this.getPlayer();
   private String command;
   private boolean close;

   public GuiItemCommand(GuiMenu guiMenu, int x, int y, Material material, short data, int amount, String text, String command, boolean close) {
      super(guiMenu, x, y, material, data, amount);
      this.close = close;
      this.command = (!command.isEmpty() && command.charAt(0) != '/' ? "/" : "") + command;
      this.setText(text);
   }

   public GuiItemCommand(GuiMenu guiMenu, int x, int y, Material material, String text, String command, boolean close) {
      super(guiMenu, x, y, material);
      this.close = close;
      this.command = (!command.isEmpty() && command.charAt(0) != '/' ? "/" : "") + command;
      this.setText(text);
   }

   public void click(InventoryClickEvent event) {
      if (this.command != null && !this.command.isEmpty()) {
         ((Player)event.getWhoClicked()).chat(this.command);
      }

      if (this.close) {
         event.getWhoClicked().closeInventory();
      }

   }
}
