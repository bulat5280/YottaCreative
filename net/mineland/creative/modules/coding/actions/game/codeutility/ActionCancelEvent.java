package net.mineland.creative.modules.coding.actions.game.codeutility;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import net.mineland.creative.modules.coding.ChestParser;
import net.mineland.creative.modules.coding.actions.Action;
import net.mineland.creative.modules.coding.actions.ActionType;
import net.mineland.creative.modules.coding.activators.Activator;
import net.mineland.creative.modules.coding.events.GameEvent;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.event.Cancellable;
import ua.govnojon.libs.bukkitutil.ItemData;

public class ActionCancelEvent extends Action {
   public ActionCancelEvent(Activator activator) {
      super(activator);
   }

   public ActionType getType() {
      return ActionType.GAME_CANCEL_EVENT;
   }

   public Action.Category getCategory() {
      return Action.Category.GAME_CODE_UTILITY;
   }

   public ItemData getIcon() {
      return new ItemData(Material.BARRIER);
   }

   public boolean run(List<Entity> selectedEntities, GameEvent gameEvent, int stackCounter, AtomicInteger callCounter) {
      if (gameEvent instanceof Cancellable) {
         ((Cancellable)gameEvent).setCancelled(true);
      }

      return true;
   }

   public boolean parseChest(ChestParser chestParser) {
      return false;
   }
}
