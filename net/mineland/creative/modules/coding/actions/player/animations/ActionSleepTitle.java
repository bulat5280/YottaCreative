package net.mineland.creative.modules.coding.actions.player.animations;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import net.mineland.core.bukkit.modules.nms.NMS;
import net.mineland.creative.modules.coding.ChestParser;
import net.mineland.creative.modules.coding.actions.Action;
import net.mineland.creative.modules.coding.actions.ActionType;
import net.mineland.creative.modules.coding.activators.Activator;
import net.mineland.creative.modules.coding.events.GameEvent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import ua.govnojon.libs.bukkitutil.ItemData;

public class ActionSleepTitle extends Action {
   public ActionSleepTitle(Activator activator) {
      super(activator);
   }

   public boolean run(List<Entity> selectedEntities, GameEvent gameEvent, int stackCounter, AtomicInteger callCounter) {
      selectedEntities.forEach((entity) -> {
         Location location = this.getVarLocation("location", gameEvent, entity);
         if (location != null) {
            Player player = (Player)entity;
            NMS.getManager().sendPlayerUseBed(Collections.singleton(player), player, location);
         }
      });
      return true;
   }

   public ActionType getType() {
      return ActionType.PLAYER_SLEEP_TITLE;
   }

   public Action.Category getCategory() {
      return Action.Category.PLAYER_ANIMATIONS;
   }

   public ItemData getIcon() {
      return new ItemData(Material.BED, 7);
   }

   public boolean parseChest(ChestParser chestParser) {
      this.putVar("location", chestParser.getLocation(0));
      return true;
   }
}
