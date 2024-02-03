package net.mineland.core.bukkit.modules.gui.defaultitems;

import net.mineland.core.bukkit.module.Module;
import net.mineland.core.bukkit.modules.gui.GuiItem;
import net.mineland.core.bukkit.modules.gui.GuiMenu;
import net.mineland.core.bukkit.modules.gui.ModuleGui;
import net.mineland.core.bukkit.modules.message.Message;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GuiItemBack extends GuiItem {
   private static ModuleGui moduleGui = (ModuleGui)Module.getInstance(ModuleGui.class);
   private String keyGuiMenu;

   public GuiItemBack(GuiMenu guiMenu, int x, String keyGuiMenu) {
      super(guiMenu, x, guiMenu.getSize() / guiMenu.getWidth(), Material.SPECTRAL_ARROW);
      this.keyGuiMenu = keyGuiMenu;
      this.setText(Message.getMessage(guiMenu.getPlayer(), "гуи_предмет_вернуться_назад"));
   }

   public GuiItemBack(GuiMenu guiMenu, String keyGuiMenu) {
      super(guiMenu, guiMenu.getWidth() / 2, guiMenu.getHeight(), Material.SPECTRAL_ARROW);
      this.keyGuiMenu = keyGuiMenu;
      this.setText(Message.getMessage(guiMenu.getPlayer(), "гуи_предмет_вернуться_назад"));
   }

   public GuiItemBack(GuiMenu guiMenu, Class<? extends GuiMenu> clazz) {
      this(guiMenu, clazz.getSimpleName().toLowerCase());
   }

   public GuiItemBack(GuiMenu guiMenu, int x, int y, String keyGuiMenu) {
      super(guiMenu, x, y, Material.SPECTRAL_ARROW);
      this.keyGuiMenu = keyGuiMenu;
      this.setText(Message.getMessage(guiMenu.getPlayer(), "гуи_предмет_вернуться_назад"));
   }

   public void setKeyGuiMenu(String keyGuiMenu) {
      this.keyGuiMenu = keyGuiMenu;
   }

   public void click(InventoryClickEvent event) {
      if (this.keyGuiMenu != null) {
         moduleGui.openGui(this.getUser(), this.keyGuiMenu);
      }

      event.setCancelled(true);
   }
}
