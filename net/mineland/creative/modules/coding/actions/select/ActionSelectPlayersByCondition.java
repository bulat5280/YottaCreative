package net.mineland.creative.modules.coding.actions.select;

import java.util.ArrayList;
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
import ua.govnojon.libs.bukkitutil.ItemData;

public class ActionSelectPlayersByCondition extends ActionSelect {
   public ActionSelectPlayersByCondition(Activator activator) {
      super(activator);
   }

   public List<Entity> execute(GameEvent gameEvent) {
      return (List)(this.getCondition() != null ? (List)gameEvent.getPlot().getOnlinePlayers().stream().filter((user) -> {
         return gameEvent.getPlot().getPlayerMode(user) == PlayerMode.PLAYING;
      }).map(User::getPlayer).filter((player) -> {
         return player.getWorld() == moduleCreative.getPlotWorld();
      }).filter((player) -> {
         return this.getCondition().condition(Collections.singletonList(player), gameEvent);
      }).collect(Collectors.toList()) : new ArrayList());
   }

   public ActionType getType() {
      return ActionType.SELECT_PLAYERS_BY_CONDITION;
   }

   public ItemData getIcon() {
      return new ItemData(Material.MELON_SEEDS);
   }
}
