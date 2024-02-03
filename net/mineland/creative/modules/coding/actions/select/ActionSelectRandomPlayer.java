package net.mineland.creative.modules.coding.actions.select;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import net.mineland.core.bukkit.modules.user.User;
import net.mineland.creative.modules.coding.actions.ActionSelect;
import net.mineland.creative.modules.coding.actions.ActionType;
import net.mineland.creative.modules.coding.activators.Activator;
import net.mineland.creative.modules.coding.events.GameEvent;
import net.mineland.creative.modules.creative.PlayerMode;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import ua.govnojon.libs.bukkitutil.ItemData;
import ua.govnojon.libs.myjava.RandomUtil;

public class ActionSelectRandomPlayer extends ActionSelect {
   public ActionSelectRandomPlayer(Activator activator) {
      super(activator);
   }

   public List<Entity> execute(GameEvent gameEvent) {
      List<Player> players = (List)gameEvent.getPlot().getOnlinePlayers().stream().filter((user) -> {
         return gameEvent.getPlot().getPlayerMode(user) == PlayerMode.PLAYING;
      }).map(User::getPlayer).filter((player) -> {
         return player.getWorld() == moduleCreative.getPlotWorld();
      }).collect(Collectors.toList());
      return Collections.singletonList(RandomUtil.getRandomObject(players));
   }

   public ActionType getType() {
      return ActionType.SELECT_RANDOM_PLAYER;
   }

   public ItemData getIcon() {
      return new ItemData(Material.EYE_OF_ENDER);
   }
}
