package net.mineland.creative.modules.coding.activators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import net.mineland.core.bukkit.module.Module;
import net.mineland.creative.modules.coding.actions.Action;
import net.mineland.creative.modules.coding.events.GameEvent;
import net.mineland.creative.modules.creative.ModuleCreative;
import net.mineland.creative.modules.creative.Plot;
import org.bukkit.entity.Entity;
import ua.govnojon.libs.bukkitutil.ItemData;

public abstract class Activator {
   protected static ModuleCreative moduleCreative = (ModuleCreative)Module.getInstance(ModuleCreative.class);
   private Plot plot;
   private List<Action> actionList = new LinkedList();
   private List<Entity> selectedEntities = new ArrayList();

   public Activator(Plot plot) {
      this.plot = plot;
   }

   public abstract ActivatorType getType();

   public abstract ItemData getIcon();

   public List<Action> getActionList() {
      return this.actionList;
   }

   public Plot getPlot() {
      return this.plot;
   }

   public List<Entity> getSelectedEntities() {
      return this.selectedEntities;
   }

   public void setSelectedEntities(List<Entity> selectedEntities) {
      this.selectedEntities = selectedEntities;
   }

   public void execute(GameEvent gameEvent, int stackCounter, AtomicInteger callCounter) {
      this.execute(gameEvent, Collections.singletonList(gameEvent.getDefaultEntity()), stackCounter, callCounter);
   }

   public void execute(GameEvent gameEvent, List<Entity> selectedEntities, int stackCounter, AtomicInteger callCounter) {
      this.selectedEntities = selectedEntities;
      ActivatorType.executeActions(this, gameEvent, this.getActionList(), stackCounter, callCounter);
   }
}
