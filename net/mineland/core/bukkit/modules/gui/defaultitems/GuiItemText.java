package net.mineland.core.bukkit.modules.gui.defaultitems;

import net.mineland.api.user.IUser;
import net.mineland.core.bukkit.modules.gui.GuiItem;
import net.mineland.core.bukkit.modules.gui.GuiMenu;
import net.mineland.core.bukkit.modules.message.Message;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class GuiItemText extends GuiItem {
   public GuiItemText(GuiMenu guiMenu, int x, int y, Material material, short data, int amount) {
      this(guiMenu, x, y, new ItemStack(material, amount, data), "");
   }

   public GuiItemText(GuiMenu guiMenu, int x, int y, ItemStack item) {
      this(guiMenu, x, y, item, "");
   }

   public GuiItemText(GuiMenu guiMenu, int x, int y, Material material) {
      this(guiMenu, x, y, material, "");
   }

   public GuiItemText(GuiMenu guiMenu, int x, int y, Material material, String key, String... replaced) {
      this(guiMenu, x, y, new ItemStack(material), key, replaced);
   }

   public GuiItemText(GuiMenu guiMenu, int x, int y, ItemStack item, String key, String... replaced) {
      super(guiMenu, x, y, item);
      this.setText(Message.getMessage((IUser)this.getUser(), key, replaced));
   }

   public GuiItemText(GuiMenu guiMenu, int x, int y, ItemStack item, String text) {
      super(guiMenu, x, y, item);
      this.setText(text);
   }

   public void click(InventoryClickEvent event) {
      event.setCancelled(true);
   }
}
