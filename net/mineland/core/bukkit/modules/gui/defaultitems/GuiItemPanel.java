package net.mineland.core.bukkit.modules.gui.defaultitems;

import net.mineland.core.bukkit.modules.gui.GuiItem;
import net.mineland.core.bukkit.modules.gui.GuiMenu;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class GuiItemPanel extends GuiItem {
   public GuiItemPanel(GuiMenu guiMenu, int x, int y, int color) {
      super(guiMenu, x, y, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)color));
      this.setText("§r");
   }

   public GuiItemPanel(GuiMenu guiMenu, int x, int y, int color, String text) {
      this(guiMenu, x, y, color, text, 1);
   }

   public GuiItemPanel(GuiMenu guiMenu, int x, int y, int color, String text, int amount) {
      super(guiMenu, x, y, new ItemStack(Material.STAINED_GLASS_PANE, amount, (short)color));
      this.setText("§r" + text);
   }

   public void click(InventoryClickEvent event) {
   }

   public void setColor(int color) {
      this.getItem().setDurability((short)color);
      this.update();
   }
}
