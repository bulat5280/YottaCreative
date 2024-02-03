package net.mineland.core.bukkit.modules.gui.defaultitems;

import net.mineland.core.bukkit.module.Module;
import net.mineland.core.bukkit.modules.gui.GuiItem;
import net.mineland.core.bukkit.modules.gui.GuiMenu;
import net.mineland.core.bukkit.modules.gui.ModuleGui;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import ua.govnojon.libs.bukkitutil.ItemData;

public class GuiItemOpenGui extends GuiItem {
   private static ModuleGui moduleGui = (ModuleGui)Module.getInstance(ModuleGui.class);
   private String keyGui;

   public GuiItemOpenGui(GuiMenu guiMenu, int x, int y, Material material, short data, int amount, String text, String keyGui) {
      super(guiMenu, x, y, material, data, amount);
      this.setText(text);
      this.keyGui = keyGui;
   }

   public GuiItemOpenGui(GuiMenu guiMenu, int x, int y, ItemStack item, String text, String keyGui) {
      super(guiMenu, x, y, item);
      this.setText(text);
      this.keyGui = keyGui;
   }

   public GuiItemOpenGui(GuiMenu guiMenu, int x, int y, Material material, String text, String keyGui) {
      super(guiMenu, x, y, material);
      this.setText(text);
      this.keyGui = keyGui;
   }

   public GuiItemOpenGui(GuiMenu guiMenu, int x, int y, ItemData itemData, String text, Class<? extends GuiMenu> clazz) {
      super(guiMenu, x, y, itemData.toItemStack());
      this.setText(text);
      this.keyGui = clazz.getSimpleName().toLowerCase();
   }

   public void click(InventoryClickEvent event) {
      moduleGui.openGui(this.getUser(), this.keyGui);
   }
}
