package net.mineland.creative.modules.coding.actions.player.settings;

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

public class ActionDisableFlight extends Action {
   public ActionDisableFlight(Activator activator) {
      super(activator);
   }

   public boolean run(List<Entity> selectedEntities, GameEvent gameEvent, int stackCounter, AtomicInteger callCounter) {
      selectedEntities.forEach((entity) -> {
         Player player = (Player)entity;
         player.setAllowFlight(false);
         player.setFlying(false);
      });
      return true;
   }

   public ActionType getType() {
      return ActionType.PLAYER_DISABLE_FLIGHT;
   }

   public Action.Category getCategory() {
      return Action.Category.PLAYER_SETTINGS;
   }

   public ItemData getIcon() {
      return new ItemData(Material.LEATHER_BOOTS);
   }

   public boolean parseChest(ChestParser chestParser) {
      return false;
   }
}
