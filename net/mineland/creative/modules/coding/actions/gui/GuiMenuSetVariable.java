package net.mineland.creative.modules.coding.actions.gui;

import net.mineland.core.bukkit.modules.user.User;
import net.mineland.creative.modules.coding.actions.Action;
import net.mineland.creative.modules.coding.actions.ActionType;

public class GuiMenuSetVariable extends GuiMenuActions {
   public GuiMenuSetVariable(String key, User user) {
      super(key, user);
   }

   public void init(Action.Category category) {
      this.addItem(new GuiMenuActions.GuiItemPlayerAction(this, 0, ActionType.VARIABLE_SET));
      this.addItem(new GuiMenuActions.GuiItemPlayerAction(this, 1, ActionType.VARIABLE_SUM));
      this.addItem(new GuiMenuActions.GuiItemPlayerAction(this, 2, ActionType.VARIABLE_DIFFERENCE));
      this.addItem(new GuiMenuActions.GuiItemPlayerAction(this, 3, ActionType.VARIABLE_PRODUCT));
      this.addItem(new GuiMenuActions.GuiItemPlayerAction(this, 4, ActionType.VARIABLE_QUOTIENT));
      this.addItem(new GuiMenuActions.GuiItemPlayerAction(this, 5, ActionType.VARIABLE_REMAINDER));
      this.addItem(new GuiMenuActions.GuiItemPlayerAction(this, 6, ActionType.VARIABLE_INCREMENT));
      this.addItem(new GuiMenuActions.GuiItemPlayerAction(this, 7, ActionType.VARIABLE_DECREMENT));
      this.addItem(new GuiMenuActions.GuiItemPlayerAction(this, 8, ActionType.VARIABLE_PARSE_NUMBER));
      this.addItem(new GuiMenuActions.GuiItemPlayerAction(this, 9, ActionType.VARIABLE_RANDOM_NUMBER));
      this.addItem(new GuiMenuActions.GuiItemPlayerAction(this, 10, ActionType.VARIABLE_GET_X));
      this.addItem(new GuiMenuActions.GuiItemPlayerAction(this, 11, ActionType.VARIABLE_GET_Y));
      this.addItem(new GuiMenuActions.GuiItemPlayerAction(this, 12, ActionType.VARIABLE_GET_Z));
      this.addItem(new GuiMenuActions.GuiItemPlayerAction(this, 13, ActionType.VARIABLE_GET_YAW));
      this.addItem(new GuiMenuActions.GuiItemPlayerAction(this, 14, ActionType.VARIABLE_GET_PITCH));
      this.addItem(new GuiMenuActions.GuiItemPlayerAction(this, 15, ActionType.VARIABLE_LOCATIONS_DISTANCE));
      this.addItem(new GuiMenuActions.GuiItemPlayerAction(this, 16, ActionType.VARIABLE_CHANGE_LOCATION_COORDINATES));
      this.addItem(new GuiMenuActions.GuiItemPlayerAction(this, 17, ActionType.VARIABLE_SET_LOCATION_COORDINATES));
      this.addItem(new GuiMenuActions.GuiItemPlayerAction(this, 18, ActionType.VARIABLE_CHANGE_LOCATION_VIEW));
      this.addItem(new GuiMenuActions.GuiItemPlayerAction(this, 19, ActionType.VARIABLE_SET_LOCATION_VIEW));
   }
}
