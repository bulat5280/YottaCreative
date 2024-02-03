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

public class ActionVarChangeLocationView extends Action {
   public ActionVarChangeLocationView(Activator activator) {
      super(activator);
   }

   public ActionType getType() {
      return ActionType.VARIABLE_CHANGE_LOCATION_VIEW;
   }

   public Action.Category getCategory() {
      return Action.Category.SET_VARIABLE;
   }

   public ItemData getIcon() {
      return new ItemData(Material.ENDER_PEARL);
   }

   public boolean run(List<Entity> selectedEntities, GameEvent gameEvent, int stackCounter, AtomicInteger callCounter) {
      Iterator var5 = selectedEntities.iterator();

      while(var5.hasNext()) {
         Entity selectedEntity = (Entity)var5.next();
         DynamicVariable dynamicVariable = this.getDynamicVariable("variable", gameEvent, selectedEntity);
         if (dynamicVariable == null) {
            return true;
         }

         List<Number> numbers = this.getVars("numbers", gameEvent, selectedEntity);
         boolean isNull = dynamicVariable.getValue(gameEvent.getPlot()) == null;
         int i = isNull ? 0 : 1;
         if (numbers.size() < 2 + i) {
            return true;
         }

         List<Location> locations = this.getVarsLocations("locations", gameEvent, selectedEntity);
         Location location = null;
         if (locations.size() != i && locations.size() != 0) {
            location = (Location)locations.get(i);
         }

         Location location1 = null;
         Object dynValue = dynamicVariable.getValue(gameEvent.getPlot());
         if (!(dynValue instanceof Location)) {
            if (location == null) {
               return true;
            }
         } else {
            location1 = (Location)dynValue;
         }

         if (location != null) {
            location1 = location;
         }

         float yaw = location1.getYaw() + ((Number)numbers.get(i)).floatValue();
         float pitch = location1.getPitch() + ((Number)numbers.get(1 + i)).floatValue();
         Location finalLoc = new Location(location1.getWorld(), location1.getX(), location1.getY(), location1.getZ(), yaw, pitch);
         dynamicVariable.setValue(gameEvent.getPlot(), finalLoc);
      }

      return true;
   }

   public boolean parseChest(ChestParser chestParser) {
      this.putVar("variable", chestParser.getDynamicVariable(0));
      this.putVar("numbers", chestParser.getNumbers());
      this.putVar("locations", chestParser.getLocations());
      return true;
   }
}
