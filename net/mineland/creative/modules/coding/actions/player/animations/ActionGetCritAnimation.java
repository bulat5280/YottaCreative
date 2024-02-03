package net.mineland.creative.modules.coding.actions.player.animations;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import net.mineland.core.bukkit.modules.nms.NMS;
import net.mineland.core.bukkit.modules.user.User;
import net.mineland.creative.modules.coding.ChestParser;
import net.mineland.creative.modules.coding.actions.Action;
import net.mineland.creative.modules.coding.actions.ActionType;
import net.mineland.creative.modules.coding.activators.Activator;
import net.mineland.creative.modules.coding.events.GameEvent;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import ua.govnojon.libs.bukkitutil.ItemData;

public class ActionGetCritAnimation extends Action {
   public ActionGetCritAnimation(Activator activator) {
      super(activator);
   }

   public boolean run(List<Entity> selectedEntities, GameEvent gameEvent, int stackCounter, AtomicInteger callCounter) {
      selectedEntities.forEach((entity) -> {
         Player player = (Player)entity;
         Collection<Player> players = (Collection)gameEvent.getPlot().getOnlinePlayers().stream().map(User::getPlayer).collect(Collectors.toList());
         NMS.getManager().sendEntityAnimation(players, player, 4);
      });
      return true;
   }

   public ActionType getType() {
      return ActionType.PLAYER_CRIT_ANIMATION;
   }

   public Action.Category getCategory() {
      return Action.Category.PLAYER_ANIMATIONS;
   }

   public ItemData getIcon() {
      return new ItemData(Material.IRON_SWORD);
   }

   public boolean parseChest(ChestParser chestParser) {
      return false;
   }
}
