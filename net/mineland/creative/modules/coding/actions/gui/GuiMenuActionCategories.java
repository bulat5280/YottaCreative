package net.mineland.creative.modules.coding.actions.gui;

import net.mineland.api.user.IUser;
import net.mineland.core.bukkit.module.Module;
import net.mineland.core.bukkit.modules.gui.GuiItem;
import net.mineland.core.bukkit.modules.gui.GuiMenu;
import net.mineland.core.bukkit.modules.gui.ModuleGui;
import net.mineland.core.bukkit.modules.message.Message;
import net.mineland.core.bukkit.modules.user.User;
import net.mineland.creative.modules.coding.actions.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

public class GuiMenuActionCategories extends GuiMenu {
   private static ModuleGui moduleGui = (ModuleGui)Module.getInstance(ModuleGui.class);

   public GuiMenuActionCategories(String key, User user) {
      super(key, user, 1, "");
   }

   protected void onInventoryOpenEvent(InventoryOpenEvent event) {
      this.clear();
      int pos = 0;
      String categories = (String)this.getUser().removeMetadata("coding.open_categories");
      this.setTitle(Message.getMessage((IUser)this.getUser(), "coding.gui." + categories + "_action.categories.title"));
      Action.Category[] var4 = Action.Category.values();
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         Action.Category category = var4[var6];
         if (category.name().toLowerCase().startsWith(categories)) {
            this.addItem(new GuiMenuActionCategories.GuiItemPlayerActionCategory(this, pos, category));
            ++pos;
         }
      }

   }

   class GuiItemPlayerActionCategory extends GuiItem {
      Action.Category category;

      GuiItemPlayerActionCategory(GuiMenu guiMenu, int pos, Action.Category category) {
         super(guiMenu, pos, category.getIcon().toItemStack(), Message.getMessage((IUser)guiMenu.getUser(), "coding.gui.player_action.category." + category.name()));
         this.category = category;
      }

      public void click(InventoryClickEvent event) {
         this.getUser().setMetadata("coding.selected_category", this.category);
         GuiMenuActionCategories.moduleGui.openGui(this.getUser(), GuiMenuActions.class);
      }
   }
}
