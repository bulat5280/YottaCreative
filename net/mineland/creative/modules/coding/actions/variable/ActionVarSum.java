package net.mineland.creative.modules.coding.actions.variable;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import net.mineland.creative.modules.coding.ChestParser;
import net.mineland.creative.modules.coding.CodeUtils;
import net.mineland.creative.modules.coding.actions.Action;
import net.mineland.creative.modules.coding.actions.ActionType;
import net.mineland.creative.modules.coding.activators.Activator;
import net.mineland.creative.modules.coding.dynamicvariables.DynamicVariable;
import net.mineland.creative.modules.coding.events.GameEvent;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import ua.govnojon.libs.bukkitutil.ItemData;

public class ActionVarSum extends Action {
   public ActionVarSum(Activator activator) {
      super(activator);
   }

   public ActionType getType() {
      return ActionType.VARIABLE_SUM;
   }

   public Action.Category getCategory() {
      return Action.Category.SET_VARIABLE;
   }

   public ItemData getIcon() {
      return new ItemData(Material.IRON_INGOT);
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
         List<String> texts = this.getVarsStrings("texts", gameEvent, selectedEntity);
         List<Number> numbers = this.getVars("numbers", gameEvent, selectedEntity);
         String text = StringUtils.join(texts, "");
         if (!text.isEmpty() && !CodeUtils.NUMBER.matcher(text).matches()) {
            dynamicVariable.setValue(gameEvent.getPlot(), text);
            return true;
         }

         double currentValue = 0.0D;

         for(int i = isNull ? 0 : 1; i < numbers.size(); ++i) {
            currentValue += ((Number)numbers.get(i)).doubleValue();
         }

         dynamicVariable.setValue(gameEvent.getPlot(), currentValue);
      }

      return true;
   }

   public boolean parseChest(ChestParser chestParser) {
      this.putVar("variable", chestParser.getDynamicVariable(0));
      this.putVar("numbers", chestParser.getNumbers());
      this.putVar("texts", chestParser.getTexts());
      return true;
   }
}
