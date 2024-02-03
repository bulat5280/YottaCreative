package net.mineland.creative.modules.coding.actions.player.communication;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import net.mineland.creative.modules.coding.actions.Action;
import net.mineland.creative.modules.coding.actions.ActionType;
import net.mineland.creative.modules.coding.activators.Activator;
import net.mineland.creative.modules.coding.events.GameEvent;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import ua.govnojon.libs.bukkitutil.ItemData;

public class ActionClearChat extends Action {
   private static String message;

   public ActionClearChat(Activator activator) {
      super(activator);
   }

   public boolean run(List<Entity> selectedEntities, GameEvent gameEvent, int stackCounter, AtomicInteger callCounter) {
      selectedEntities.forEach((entity) -> {
         entity.sendMessage(message);
      });
      return true;
   }

   public ActionType getType() {
      return ActionType.PLAYER_CLEAR_CHAT;
   }

   public Action.Category getCategory() {
      return Action.Category.PLAYER_COMMUNICATION;
   }

   public ItemData getIcon() {
      return new ItemData(Material.GLASS);
   }

   public boolean needPerm() {
      return true;
   }

   public boolean parseChest(ItemStack[] itemStacks) {
      return false;
   }

   static {
      StringBuilder sb = new StringBuilder();

      for(int i = 0; i < 256; ++i) {
         sb.append(" \n");
      }

      message = sb.toString();
   }
}
