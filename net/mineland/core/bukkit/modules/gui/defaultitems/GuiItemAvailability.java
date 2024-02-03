package net.mineland.core.bukkit.modules.gui.defaultitems;

import net.mineland.core.bukkit.modules.gui.AvailabilityType;
import net.mineland.core.bukkit.modules.gui.GuiItem;
import net.mineland.core.bukkit.modules.gui.GuiMenu;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GuiItemAvailability extends GuiItemPanel {
   private GuiItem comp;

   public GuiItemAvailability(GuiMenu guiMenu, int x, int y, AvailabilityType at) {
      super(guiMenu, x, y, at.getPanelColor(), at.getMessage(guiMenu.getUser().getLang()));
      this.comp = null;
   }

   public GuiItemAvailability(GuiMenu guiMenu, int x, int y, AvailabilityType at, GuiItem comp) {
      super(guiMenu, x, y, at.getPanelColor(), at.getMessage(guiMenu.getUser().getLang()));
      this.comp = comp;
   }

   public void setType(AvailabilityType at) {
      this.setColor((short)at.getPanelColor());
      this.setText(at.getMessage(this.getUser().getLang()));
   }

   public void click(InventoryClickEvent event) {
      if (this.comp != null) {
         this.comp.click(event);
      }

   }
}
