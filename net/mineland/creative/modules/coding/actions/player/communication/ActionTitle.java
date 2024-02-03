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

public class ActionTitle extends Action {
   public ActionTitle(Activator activator) {
      super(activator);
   }

   public boolean run(List<Entity> selectedEntities, GameEvent gameEvent, int stackCounter, AtomicInteger callCounter) {
      selectedEntities.forEach((entity) -> {
         if (entity.getType() == EntityType.PLAYER) {
            Player player = (Player)entity;
            String title = this.getVarString("title", gameEvent, entity);
            if (title == null) {
               title = "";
            }

            String subTitle = this.getVarString("subtitle", gameEvent, entity);
            if (subTitle == null) {
               subTitle = "";
            }

            int duration = this.getVar("duration", 30, gameEvent, entity);
            int fadeIn = this.getVar("fadeIn", 5, gameEvent, entity);
            int fadeOut = this.getVar("fadeout", 5, gameEvent, entity);
            player.sendTitle(title, subTitle, fadeIn, duration, fadeOut);
         }

      });
      return true;
   }

   public ActionType getType() {
      return ActionType.PLAYER_TITLE;
   }

   public Action.Category getCategory() {
      return Action.Category.PLAYER_COMMUNICATION;
   }

   public ItemData getIcon() {
      return new ItemData(Material.SIGN);
   }

   public boolean needPerm() {
      return true;
   }

   public boolean parseChest(ItemStack[] itemStacks) {
      this.putVar("title", itemStacks[0]);
      this.putVar("subtitle", itemStacks[1]);
      this.putVar("duration", itemStacks[2]);
      this.putVar("fadein", itemStacks[3]);
      this.putVar("fadeout", itemStacks[4]);
      return true;
   }
}
