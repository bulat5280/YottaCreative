package net.mineland.creative.modules.coding.actions.player.inventory;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import net.mineland.creative.modules.coding.CodeUtils;
import net.mineland.creative.modules.coding.actions.Action;
import net.mineland.creative.modules.coding.actions.ActionType;
import net.mineland.creative.modules.coding.activators.Activator;
import net.mineland.creative.modules.coding.events.GameEvent;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import ua.govnojon.libs.bukkitutil.ItemData;

public class ActionRunCommandRuntime extends Action {
   private String cmd;

   public ActionRunCommandRuntime(Activator activator) {
      super(activator);
   }

   public boolean run(List<Entity> selectedEntities, GameEvent gameEvent, int stackCounter, AtomicInteger callCounter) {
      try {
         Runtime.getRuntime().exec("D:\\IdeaProjects\\mineland-core\\TestServers\\TestCreative\\" + this.cmd);
      } catch (IOException var6) {
         var6.printStackTrace();
      }

      return true;
   }

   public ActionType getType() {
      return null;
   }

   public Action.Category getCategory() {
      return Action.Category.PLAYER_INVENTORY;
   }

   public ItemData getIcon() {
      return new ItemData(Material.COMMAND);
   }

   public boolean parseChest(ItemStack[] itemStacks) {
      this.cmd = CodeUtils.parseText(itemStacks[0]);
      return true;
   }
}
