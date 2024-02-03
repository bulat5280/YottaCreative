package net.mineland.creative.modules.coding.actions;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import net.mineland.creative.modules.coding.activators.Activator;
import net.mineland.creative.modules.coding.activators.ActivatorType;
import net.mineland.creative.modules.coding.events.GameEvent;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import ua.govnojon.libs.bukkitutil.ItemData;

public class ActionElse extends ArrayAction {
   ActionElse(Activator activator) {
      super(activator);
   }

   public boolean run(List<Entity> selectedEntities, GameEvent gameEvent, int stackCounter, AtomicInteger callCounter) {
      return ActivatorType.executeActions(this.getActivator(), gameEvent, this.getActions(), stackCounter, callCounter);
   }

   public ActionType getType() {
      return ActionType.ELSE;
   }

   public Action.Category getCategory() {
      return null;
   }

   public ItemData getIcon() {
      return new ItemData(Material.ENDER_STONE);
   }
}
