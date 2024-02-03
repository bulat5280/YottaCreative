package net.mineland.creative.modules.coding.actions.variable;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import net.mineland.creative.modules.coding.ChestParser;
import net.mineland.creative.modules.coding.actions.Action;
import net.mineland.creative.modules.coding.actions.ActionType;
import net.mineland.creative.modules.coding.activators.Activator;
import net.mineland.creative.modules.coding.dynamicvariables.DynamicVariable;
import net.mineland.creative.modules.coding.events.GameEvent;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import ua.govnojon.libs.bukkitutil.ItemData;

public class ActionVarIncrement extends Action {
   public ActionVarIncrement(Activator activator) {
      super(activator);
   }

   public ActionType getType() {
      return ActionType.VARIABLE_INCREMENT;
   }

   public Action.Category getCategory() {
      return Action.Category.SET_VARIABLE;
   }

   public ItemData getIcon() {
      return new ItemData(Material.SUGAR);
   }

   public boolean run(List<Entity> selectedEntities, GameEvent gameEvent, int stackCounter, AtomicInteger callCounter) {
      Iterator var5 = selectedEntities.iterator();

      while(var5.hasNext()) {
         Entity selectedEntity = (Entity)var5.next();
         DynamicVariable dynamicVariable = this.getDynamicVariable("variable", gameEvent, selectedEntity);
         if (dynamicVariable == null) {
            return true;
         }

         boolean isNull = dynamicVariable.getValue(gameEvent.getPlot()) == null;
         List<Number> numbers = this.getVars("numbers", gameEvent, selectedEntity);
         if (isNull) {
            if (numbers.size() < 1) {
               return true;
            }
         } else if (numbers.size() < 2) {
            return true;
         }

         double value = isNull ? 0.0D : ((Number)numbers.get(0)).doubleValue();

         for(int i = isNull ? 0 : 1; i < numbers.size(); ++i) {
            value += ((Number)numbers.get(i)).doubleValue();
         }

         dynamicVariable.setValue(gameEvent.getPlot(), value);
      }

      return true;
   }

   public boolean parseChest(ChestParser chestParser) {
      this.putVar("variable", chestParser.getDynamicVariable(0));
      this.putVar("numbers", chestParser.getNumbers());
      return true;
   }
}
