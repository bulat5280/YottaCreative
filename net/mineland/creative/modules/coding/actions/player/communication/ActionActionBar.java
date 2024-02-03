package net.mineland.creative.modules.coding.actions.player.communication;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import net.mineland.creative.modules.coding.actions.Action;
import net.mineland.creative.modules.coding.actions.ActionType;
import net.mineland.creative.modules.coding.activators.Activator;
import net.mineland.creative.modules.coding.events.GameEvent;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ua.govnojon.libs.bukkitutil.ItemData;

public class ActionActionBar extends Action {
   public ActionActionBar(Activator activator) {
      super(activator);
   }

   public boolean run(List<Entity> selectedEntities, GameEvent gameEvent, int stackCounter, AtomicInteger callCounter) {
      selectedEntities.forEach((entity) -> {
         if (entity.getType() == EntityType.PLAYER) {
            Player player = (Player)entity;
            String message = this.getVarString("message", gameEvent, player);
            player.sendActionBar(message);
         }

      });
      return true;
   }

   public ActionType getType() {
      return ActionType.PLAYER_ACTION_BAR;
   }

   public Action.Category getCategory() {
      return Action.Category.PLAYER_COMMUNICATION;
   }

   public ItemData getIcon() {
      return new ItemData(Material.TORCH);
   }

   public boolean needPerm() {
      return true;
   }

   public boolean parseChest(ItemStack[] itemStacks) {
      this.putVar("message", itemStacks[0]);
      return true;
   }
}
