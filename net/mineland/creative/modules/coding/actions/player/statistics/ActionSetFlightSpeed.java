package net.mineland.creative.modules.coding.actions.player.statistics;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import net.mineland.creative.modules.coding.ChestParser;
import net.mineland.creative.modules.coding.actions.Action;
import net.mineland.creative.modules.coding.actions.ActionType;
import net.mineland.creative.modules.coding.activators.Activator;
import net.mineland.creative.modules.coding.events.GameEvent;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import ua.govnojon.libs.bukkitutil.ItemData;

public class ActionSetFlightSpeed extends Action {
   public ActionSetFlightSpeed(Activator activator) {
      super(activator);
   }

   public boolean run(List<Entity> selectedEntities, GameEvent gameEvent, int stackCounter, AtomicInteger callCounter) {
      selectedEntities.forEach((entity) -> {
         Player player = (Player)entity;
         Number spe = new Double((double)this.getVar("speed", 1, gameEvent, entity));
         double speed = spe.doubleValue();
         player.setFlySpeed((float)((speed + 100.0D) / 1000.0D));
      });
      return true;
   }

   public ActionType getType() {
      return ActionType.PLAYER_SET_FLIGHT_SPEED;
   }

   public Action.Category getCategory() {
      return Action.Category.PLAYER_PARAMETERS;
   }

   public ItemData getIcon() {
      return new ItemData(Material.ELYTRA);
   }

   public boolean needPerm() {
      return true;
   }

   public boolean parseChest(ChestParser chestParser) {
      this.putVar("speed", chestParser.getNumber(0));
      return true;
   }
}
