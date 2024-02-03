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

public class ActionVarSet extends Action {
   public ActionVarSet(Activator activator) {
      super(activator);
   }

   public ActionType getType() {
      return ActionType.VARIABLE_SET;
   }

   public Action.Category getCategory() {
      return Action.Category.SET_VARIABLE;
   }

   public ItemData getIcon() {
      return new ItemData(Material.GOLD_INGOT);
   }

   public boolean run(List<Entity> selectedEntities, GameEvent gameEvent, int stackCounter, AtomicInteger callCounter) {
      Iterator var5 = selectedEntities.iterator();

      while(true) {
         while(var5.hasNext()) {
            Entity selectedEntity = (Entity)var5.next();
            DynamicVariable dynamicVariable = this.getDynamicVariable("variable", gameEvent, selectedEntity);
            if (dynamicVariable == null) {
               return true;
            }

            List<Object> objects = this.getVars("values", gameEvent, selectedEntity);
            if (objects.size() == 1) {
               (new DynamicVariable(dynamicVariable.getName())).setValue(gameEvent.getPlot(), objects.get(0));
            } else {
               StringBuilder sb = new StringBuilder();

               for(int i = 1; i < objects.size(); ++i) {
                  if (objects.size() == 2) {
                     (new DynamicVariable(dynamicVariable.getName())).setValue(gameEvent.getPlot(), objects.get(i));
                     return true;
                  }

                  sb.append(objects.get(i));
               }

               (new DynamicVariable(dynamicVariable.getName())).setValue(gameEvent.getPlot(), sb.toString());
            }
         }

         return true;
      }
   }

   public boolean parseChest(ChestParser chestParser) {
      this.putVar("variable", chestParser.getDynamicVariable(0));
      this.putVar("values", chestParser.getItems());
      return true;
   }
}
