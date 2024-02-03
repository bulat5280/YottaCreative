package net.mineland.core.bukkit.modules.gui.defaultitems;

import net.mineland.core.bukkit.module.Module;
import net.mineland.core.bukkit.modules.gui.GuiItem;
import net.mineland.core.bukkit.modules.gui.GuiMenu;
import net.mineland.core.bukkit.modules.gui.ModuleGui;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class GuiItemOpenGuiWithPerm extends GuiItem {
   private static ModuleGui moduleGui = (ModuleGui)Module.getInstance(ModuleGui.class);
   private String keyGui;
   private String permission;

   public GuiItemOpenGuiWithPerm(GuiMenu guiMenu, int x, int y, Material material, short data, int amount, String text, String keyGui, String permission) {
      super(guiMenu, x, y, material, data, amount);
      this.permission = permission;
      this.setText(text);
      this.keyGui = keyGui;
   }

   public GuiItemOpenGuiWithPerm(GuiMenu guiMenu, int x, int y, ItemStack item, String text, String keyGui, String permission) {
      super(guiMenu, x, y, item);
      this.permission = permission;
      this.setText(text);
      this.keyGui = keyGui;
   }

   public GuiItemOpenGuiWithPerm(GuiMenu guiMenu, int x, int y, Material material, String text, String keyGui, String permission) {
      super(guiMenu, x, y, material);
      this.permission = permission;
      this.setText(text);
      this.keyGui = keyGui;
   }

   public void click(InventoryClickEvent event) {
      if (this.getUser().hasPermission(this.permission)) {
         moduleGui.openGui(this.getUser(), this.keyGui);
      }

   }
}
