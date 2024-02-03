package net.mineland.creative.modules.coding.actions.player.movement;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import net.mineland.creative.modules.coding.ChestParser;
import net.mineland.creative.modules.coding.actions.Action;
import net.mineland.creative.modules.coding.actions.ActionType;
import net.mineland.creative.modules.coding.activators.Activator;
import net.mineland.creative.modules.coding.events.GameEvent;
import net.mineland.creative.modules.creative.Plot;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import ua.govnojon.libs.bukkitutil.ItemData;

public class ActionTeleportSequence extends Action {
   public ActionTeleportSequence(Activator activator) {
      super(activator);
   }

   public boolean run(List<Entity> selectedEntities, GameEvent gameEvent, int stackCounter, AtomicInteger callCounter) {
      if (this.getActivator() == null) {
         return true;
      } else {
         Plot plot = this.getActivator().getPlot();
         selectedEntities.forEach((entity) -> {
            int delay = this.getVar("delay", 20, gameEvent, entity);
            List<Location> locations = this.getVarsLocations("locations", gameEvent, entity);

            for(int i = 0; i < locations.size(); ++i) {
               Location location = (Location)locations.get(i);
               plot.getCodeHandler().schedule(() -> {
                  entity.teleport(location);
               }).later((long)(i * delay));
            }

         });
         return true;
      }
   }

   public ActionType getType() {
      return ActionType.PLAYER_RANDOM_TELEPORT;
   }

   public Action.Category getCategory() {
      return Action.Category.PLAYER_MOVEMENT;
   }

   public ItemData getIcon() {
      return new ItemData(Material.CHORUS_FRUIT);
   }

   public boolean needPerm() {
      return true;
   }

   public boolean parseChest(ChestParser chestParser) {
      this.putVar("delay", chestParser.getNumber(0));
      this.putVar("locations", chestParser.getLocations());
      return true;
   }
}
