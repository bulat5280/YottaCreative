package net.mineland.creative.modules.coding.actions.player.movement;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import net.mineland.creative.modules.coding.actions.Action;
import net.mineland.creative.modules.coding.actions.ActionType;
import net.mineland.creative.modules.coding.activators.Activator;
import net.mineland.creative.modules.coding.events.GameEvent;
import net.mineland.creative.modules.creative.Plot;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import ua.govnojon.libs.bukkitutil.ItemData;
import ua.govnojon.libs.bukkitutil.schedule.Schedule;

public class ActionRideEntity extends Action {
   public ActionRideEntity(Activator activator) {
      super(activator);
   }

   public boolean run(List<Entity> selectedEntities, GameEvent gameEvent, int stackCounter, AtomicInteger callCounter) {
      if (this.getActivator() == null) {
         return true;
      } else {
         Plot plot = this.getActivator().getPlot();
         selectedEntities.forEach((entity) -> {
            String name = this.getVarString("name", gameEvent, entity);
            if (!name.equalsIgnoreCase(entity.getName())) {
               plot.getPlotTerritory().getEntities().stream().filter((e) -> {
                  return e.getName().equals(name);
               }).findFirst().ifPresent((nameEntity) -> {
                  entity.teleport(nameEntity);
                  Schedule.run(() -> {
                     nameEntity.addPassenger(entity);
                  });
               });
            }
         });
         return true;
      }
   }

   public ActionType getType() {
      return ActionType.PLAYER_RIDE_ENTITY;
   }

   public Action.Category getCategory() {
      return Action.Category.PLAYER_MOVEMENT;
   }

   public ItemData getIcon() {
      return new ItemData(Material.SADDLE);
   }

   public boolean needPerm() {
      return true;
   }

   public boolean parseChest(ItemStack[] itemStacks) {
      this.putVar("name", itemStacks[0]);
      return true;
   }
}
