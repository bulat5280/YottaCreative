package net.mineland.creative.modules.coding.actions.player.inventory;

import java.io.File;
import java.util.Arrays;
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
import ua.govnojon.libs.config.Config;

public class ActionSaveInventory extends Action {
   public ActionSaveInventory(Activator activator) {
      super(activator);
   }

   public ActionType getType() {
      return ActionType.PLAYER_SAVE_INVENTORY;
   }

   public Action.Category getCategory() {
      return Action.Category.PLAYER_INVENTORY;
   }

   public ItemData getIcon() {
      return new ItemData(Material.ENDER_STONE);
   }

   public boolean run(List<Entity> selectedEntities, GameEvent gameEvent, int stackCounter, AtomicInteger callCounter) {
      selectedEntities.forEach((entity) -> {
         User user = User.getUser((CommandSender)entity);
         String idUser = user.getName();
         Config config = new Config(moduleCreative.getPlugin(), File.separator + "inventories" + File.separator + "saved_inventory" + File.separator + gameEvent.getPlot().getId() + "_" + idUser);
         config.set("inventory", Arrays.asList(user.getPlayer().getInventory().getContents()));
         config.save();
      });
      return true;
   }

   public boolean parseChest(ChestParser chestParser) {
      return false;
   }
}
