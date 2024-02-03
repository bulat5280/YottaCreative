package net.mineland.core.bukkit.modules.gui.defaultitems;

import net.mineland.core.bukkit.modules.gui.GuiItem;
import net.mineland.core.bukkit.modules.gui.GuiMenu;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class GuiItemSnowBall extends GuiItem {
   public GuiItemSnowBall(GuiMenu guiMenu, int x, int y, String text, int amount) {
      super(guiMenu, x, y, new ItemStack(Material.SNOW_BALL, amount));
      this.setText("§r" + text);
   }

   public void click(InventoryClickEvent event) {
   }
}
