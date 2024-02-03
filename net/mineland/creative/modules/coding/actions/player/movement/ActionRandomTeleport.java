package net.mineland.creative.modules.coding.actions.player.movement;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import net.mineland.creative.modules.coding.ChestParser;
import net.mineland.creative.modules.coding.actions.Action;
import net.mineland.creative.modules.coding.actions.ActionType;
import net.mineland.creative.modules.coding.activators.Activator;
import net.mineland.creative.modules.coding.events.GameEvent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import ua.govnojon.libs.bukkitutil.ItemData;
import ua.govnojon.libs.myjava.RandomUtil;

public class ActionRandomTeleport extends Action {
   public ActionRandomTeleport(Activator activator) {
      super(activator);
   }

   public boolean run(List<Entity> selectedEntities, GameEvent gameEvent, int stackCounter, AtomicInteger callCounter) {
      selectedEntities.forEach((entity) -> {
         List<Location> locations = this.getVarsLocations("locations", gameEvent, entity);
         if (!locations.isEmpty()) {
            Location location = (Location)RandomUtil.getRandomObject(locations);
            entity.teleport(location);
         }

      });
      return true;
   }

   public ActionType getType() {
      return ActionType.PLAYER_RANDOM_TELEPORT;
   }

   public Action.Category getCategory() {
      return Action.Category.PLAYER_MOVEMENT;
   }

   public ItemData getIcon() {
      return new ItemData(Material.EYE_OF_ENDER);
   }

   public boolean parseChest(ChestParser chestParser) {
      this.putVar("locations", chestParser.getLocations());
      return true;
   }
}
