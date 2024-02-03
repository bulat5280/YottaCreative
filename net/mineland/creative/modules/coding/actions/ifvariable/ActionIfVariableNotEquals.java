package net.mineland.creative.modules.coding.actions.ifvariable;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
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

public class ActionIfVariableNotEquals extends ActionIf {
   public ActionIfVariableNotEquals(Activator activator) {
      super(activator);
   }

   public boolean expression(List<Entity> selectedEntities, GameEvent gameEvent) {
      Iterator var3 = selectedEntities.iterator();

      DynamicVariable dynamicVariable;
      Object object;
      do {
         if (!var3.hasNext()) {
            return true;
         }

         Entity entity = (Entity)var3.next();
         dynamicVariable = this.getDynamicVariable("variable", gameEvent, entity);
         if (dynamicVariable == null) {
            return false;
         }

         object = this.getVar("object", gameEvent, entity);
      } while(!Objects.equals(dynamicVariable.getValue(gameEvent.getPlot()), object));

      return false;
   }

   public ActionType getType() {
      return ActionType.IF_VARIABLE_EQUALS;
   }

   public Action.Category getCategory() {
      return Action.Category.IF_VARIABLE;
   }

   public ItemData getIcon() {
      return new ItemData(Material.BARRIER);
   }

   public boolean parseChest(ChestParser chestParser) {
      this.putVar("variable", chestParser.getDynamicVariable(0));
      this.putVar("object", chestParser.getItem(1));
      return true;
   }
}
