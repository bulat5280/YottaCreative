package net.mineland.creative.modules.coding.actions.player.settings;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import net.mineland.core.bukkit.modules.user.User;
import net.mineland.creative.modules.coding.ChestParser;
import net.mineland.creative.modules.coding.actions.Action;
import net.mineland.creative.modules.coding.actions.ActionType;
import net.mineland.creative.modules.coding.activators.Activator;
import net.mineland.creative.modules.coding.events.GameEvent;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import ua.govnojon.libs.bukkitutil.ItemData;

public class ActionDisableDeathDrops extends Action {
   public ActionDisableDeathDrops(Activator activator) {
      super(activator);
   }

   public boolean run(List<Entity> selectedEntities, GameEvent gameEvent, int stackCounter, AtomicInteger callCounter) {
      selectedEntities.forEach((entity) -> {
         User user = User.getUser((CommandSender)entity);
         user.setMetadata("coding.save_inv", true);
      });
      return true;
   }

   public ActionType getType() {
      return ActionType.PLAYER_ENABLE_DEATH_DROPS;
   }

   public Action.Category getCategory() {
      return Action.Category.PLAYER_SETTINGS;
   }

   public ItemData getIcon() {
      return new ItemData(Material.MELON_SEEDS);
   }

   public boolean parseChest(ChestParser chestParser) {
      return false;
   }
}
