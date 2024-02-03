package net.mineland.creative.modules.coding.actions;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import net.mineland.creative.modules.coding.activators.Activator;
import net.mineland.creative.modules.coding.events.GameEvent;
import org.bukkit.entity.Entity;

public abstract class ActionSelect extends Action {
   private ActionIf condition;

   public ActionSelect(Activator activator) {
      super(activator);
   }

   public boolean run(List<Entity> selectedEntities, GameEvent gameEvent, int stackCounter, AtomicInteger callCounter) {
      return true;
   }

   public Action.Category getCategory() {
      return Action.Category.SELECT_OBJECT;
   }

   public ActionIf getCondition() {
      return this.condition;
   }

   public void setCondition(ActionIf condition) {
      this.condition = condition;
   }

   public abstract List<Entity> execute(GameEvent var1);
}
