package net.mineland.creative.modules.coding.actions.gui;

import net.mineland.api.user.IUser;
import net.mineland.core.bukkit.modules.gui.GuiMenu;
import net.mineland.core.bukkit.modules.message.Message;
import net.mineland.core.bukkit.modules.user.User;
import net.mineland.creative.modules.coding.actions.Action;
import net.mineland.creative.modules.coding.actions.ActionType;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryOpenEvent;
import ua.govnojon.libs.bukkitutil.ItemData;

public class GuiMenuConditionType extends GuiMenu {
   public GuiMenuConditionType(String key, User user) {
      super(key, user, 1, Message.getMessage((IUser)user, "coding.gui.condition_type.title"));
   }

   protected void onInventoryOpenEvent(InventoryOpenEvent event) {
      this.clear();
      ActionType actionType = (ActionType)this.getUser().removeMetadata("coding.select_object");
      switch(actionType) {
      case SELECT_PLAYERS_BY_CONDITION:
         this.addItem(3, 1, (new ItemData(Material.OBSIDIAN)).toItemStack(), "lang:coding.item.if_variable", (click) -> {
            this.getUser().setMetadata("coding.selected_category", Action.Category.IF_VARIABLE);
            moduleGui.openGui(this.getUser(), GuiMenuActions.class);
         });
         this.addItem(7, 1, (new ItemData(Material.WOOD)).toItemStack(), "lang:coding.item.if_player", (click) -> {
            this.getUser().setMetadata("coding.selected_category", Action.Category.IF_PLAYER);
            moduleGui.openGui(this.getUser(), GuiMenuActions.class);
         });
         break;
      case SELECT_ENTITIES_BY_CONDITION:
      case SELECT_MOBS_BY_CONDITION:
         this.addItem(3, 1, (new ItemData(Material.OBSIDIAN)).toItemStack(), "lang:coding.item.if_variable", (click) -> {
            this.getUser().setMetadata("coding.selected_category", Action.Category.IF_VARIABLE);
            moduleGui.openGui(this.getUser(), GuiMenuActions.class);
         });
         this.addItem(7, 1, (new ItemData(Material.BRICK)).toItemStack(), "lang:coding.item.if_entity", (click) -> {
            this.getUser().setMetadata("coding.selected_category", Action.Category.IF_ENTITY);
            moduleGui.openGui(this.getUser(), GuiMenuActions.class);
         });
      }

   }
}
