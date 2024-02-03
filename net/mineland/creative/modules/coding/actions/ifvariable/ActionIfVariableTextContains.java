package net.mineland.creative.modules.coding.actions.ifvariable;

import java.util.Iterator;
import java.util.List;
import net.mineland.creative.modules.coding.ChestParser;
import net.mineland.creative.modules.coding.actions.Action;
import net.mineland.creative.modules.coding.actions.ActionIf;
import net.mineland.creative.modules.coding.actions.ActionType;
import net.mineland.creative.modules.coding.activators.Activator;
import net.mineland.creative.modules.coding.dynamicvariables.DynamicVariable;
import net.mineland.creative.modules.coding.events.GameEvent;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import ua.govnojon.libs.bukkitutil.ItemData;

public class ActionIfVariableTextContains extends ActionIf {
   public ActionIfVariableTextContains(Activator activator) {
      super(activator);
   }

   public boolean expression(List<Entity> selectedEntities, GameEvent gameEvent) {
      Iterator var3 = selectedEntities.iterator();

      String string;
      String text;
      do {
         if (!var3.hasNext()) {
            return true;
         }

         Entity entity = (Entity)var3.next();
         DynamicVariable dynamicVariable = this.getDynamicVariable("variable", gameEvent, entity);
         if (dynamicVariable == null) {
            return false;
         }

         Object object = dynamicVariable.getValue(gameEvent.getPlot());
         string = object instanceof Double ? object.toString().replace(".0", "") : (object != null ? object.toString() : null);
         text = this.getVarString("text", gameEvent, entity);
         if (text == null && dynamicVariable.getValue(gameEvent.getPlot()) != null) {
            return false;
         }
      } while(string == null || text == null || string.contains(text));

      return false;
   }

   public ActionType getType() {
      return ActionType.IF_VARIABLE_TEXT_EQUALS;
   }

   public Action.Category getCategory() {
      return Action.Category.IF_VARIABLE;
   }

   public ItemData getIcon() {
      return new ItemData(Material.NAME_TAG);
   }

   public boolean parseChest(ChestParser chestParser) {
      this.putVar("variable", chestParser.getDynamicVariable(0));
      this.putVar("text", chestParser.getText(1));
      return true;
   }
}
