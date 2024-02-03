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
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import ua.govnojon.libs.bukkitutil.ItemData;

public class ActionVarLocsDistance extends Action {
   public ActionVarLocsDistance(Activator activator) {
      super(activator);
   }

   public ActionType getType() {
      return ActionType.VARIABLE_LOCATIONS_DISTANCE;
   }

   public Action.Category getCategory() {
      return Action.Category.SET_VARIABLE;
   }

   public ItemData getIcon() {
      return new ItemData(Material.RAILS);
   }

   public boolean run(List<Entity> selectedEntities, GameEvent gameEvent, int stackCounter, AtomicInteger callCounter) {
      Iterator var5 = selectedEntities.iterator();

      while(var5.hasNext()) {
         Entity selectedEntity = (Entity)var5.next();
         DynamicVariable dynamicVariable = this.getDynamicVariable("variable", gameEvent, selectedEntity);
         if (dynamicVariable == null) {
            return true;
         }

         List<Location> locations = this.getVarsLocations("locations", gameEvent, selectedEntity);
         if (locations.size() < 2) {
            return true;
         }

         Location loc1 = (Location)locations.get(0);
         Location loc2 = (Location)locations.get(1);
         double distance = loc1.distance(loc2);
         dynamicVariable.setValue(gameEvent.getPlot(), distance);
      }

      return true;
   }

   public boolean parseChest(ChestParser chestParser) {
      this.putVar("variable", chestParser.getDynamicVariable(0));
      this.putVar("locations", chestParser.getLocations());
      return true;
   }
}
