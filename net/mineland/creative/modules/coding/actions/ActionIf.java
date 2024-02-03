package net.mineland.creative.modules.coding.actions;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import net.mineland.creative.modules.coding.activators.Activator;
import net.mineland.creative.modules.coding.activators.ActivatorType;
import net.mineland.creative.modules.coding.events.GameEvent;
import org.bukkit.entity.Entity;

public abstract class ActionIf extends ArrayAction {
   private boolean inverted = false;

   protected ActionIf(Activator activator) {
      super(activator);
   }

   public void setInverted(boolean inverted) {
      this.inverted = inverted;
   }

   public boolean isInverted() {
      return this.inverted;
   }

   public boolean run(List<Entity> selectedEntities, GameEvent gameEvent, int stackCounter, AtomicInteger callCounter) {
      return ActivatorType.executeActions(this.getActivator(), gameEvent, this.getActions(), stackCounter, callCounter);
   }

   public boolean condition(List<Entity> selectedEntities, GameEvent gameEvent) {
      this.selectedEntities = (List)this.selection.select(selectedEntities, gameEvent).stream().filter((entity) -> {
         return gameEvent.getPlot().getPlotTerritory().isContains(entity.getLocation());
      }).collect(Collectors.toList());
      return this.isInverted() != this.expression(this.getSelectedEntities(), gameEvent);
   }

   protected abstract boolean expression(List<Entity> var1, GameEvent var2);
}
