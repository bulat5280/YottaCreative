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

public class ActionSetXpProgress extends Action {
   public ActionSetXpProgress(Activator activator) {
      super(activator);
   }

   public boolean run(List<Entity> selectedEntities, GameEvent gameEvent, int stackCounter, AtomicInteger callCounter) {
      selectedEntities.forEach((entity) -> {
         Player player = (Player)entity;
         int xp = this.getVar("xp", 0, gameEvent, entity);
         xp = xp < 0 ? 0 : (xp > 100 ? 100 : xp);
         player.setExp((float)xp / 100.0F);
      });
      return true;
   }

   public ActionType getType() {
      return ActionType.PLAYER_SET_XP_PROGRESS;
   }

   public Action.Category getCategory() {
      return Action.Category.PLAYER_PARAMETERS;
   }

   public ItemData getIcon() {
      return new ItemData(Material.EMERALD);
   }

   public boolean parseChest(ChestParser chestParser) {
      this.putVar("xp", chestParser.getNumber(0));
      return true;
   }
}
