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

public class ActionIfVariableExist extends ActionIf {
   public ActionIfVariableExist(Activator activator) {
      super(activator);
   }

   protected boolean expression(List<Entity> selectedEntities, GameEvent gameEvent) {
      Iterator<Entity> iterator = selectedEntities.iterator();
      if (iterator.hasNext()) {
         Entity entity = (Entity)iterator.next();
         DynamicVariable dynamicVariable = this.getDynamicVariable("variable", gameEvent, entity);
         return dynamicVariable == null ? false : gameEvent.getPlot().getCodeHandler().getDynamicVariables().containsKey(dynamicVariable.getName());
      } else {
         return true;
      }
   }

   public ActionType getType() {
      return ActionType.IF_VARIABLE_EXIST;
   }

   public Action.Category getCategory() {
      return Action.Category.IF_VARIABLE;
   }

   public ItemData getIcon() {
      return new ItemData(Material.SLIME_BALL);
   }

   public boolean parseChest(ChestParser chestParser) {
      this.putVar("variable", chestParser.getDynamicVariable(0));
      return true;
   }
}
