package net.mineland.creative.modules.coding.actions.player.movement;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import net.mineland.core.bukkit.modules.user.User;
import net.mineland.creative.modules.coding.ChestParser;
import net.mineland.creative.modules.coding.actions.Action;
import net.mineland.creative.modules.coding.actions.ActionType;
import net.mineland.creative.modules.coding.activators.Activator;
import net.mineland.creative.modules.coding.events.GameEvent;
import net.mineland.creative.modules.creative.Plot;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import ua.govnojon.libs.bukkitutil.ItemData;

public class ActionKick extends Action {
   public ActionKick(Activator activator) {
      super(activator);
   }

   public ActionType getType() {
      return ActionType.PLAYER_KICK;
   }

   public Action.Category getCategory() {
      return Action.Category.PLAYER_MOVEMENT;
   }

   public ItemData getIcon() {
      return new ItemData(Material.BARRIER);
   }

   public boolean parseChest(ChestParser chestParser) {
      return false;
   }

   public boolean run(List<Entity> list, GameEvent gameEvent, int i, AtomicInteger atomicInteger) {
      Plot plot = gameEvent.getPlot();
      this.selectedEntities.forEach((entity) -> {
         User selected = User.getUser((CommandSender)entity);
         if (plot.getOnlinePlayers().contains(selected)) {
            moduleCreative.getPlotManager().teleportToLobby(selected, true);
         }

      });
      return true;
   }
}
