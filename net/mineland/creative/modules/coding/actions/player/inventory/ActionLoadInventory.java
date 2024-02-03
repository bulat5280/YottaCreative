package net.mineland.creative.modules.coding.actions.player.inventory;

import java.io.File;
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
import org.bukkit.inventory.ItemStack;
import ua.govnojon.libs.bukkitutil.ItemData;
import ua.govnojon.libs.config.Config;

public class ActionLoadInventory extends Action {
   public ActionLoadInventory(Activator activator) {
      super(activator);
   }

   public ActionType getType() {
      return ActionType.PLAYER_LOAD_INVENTORY;
   }

   public Action.Category getCategory() {
      return Action.Category.PLAYER_INVENTORY;
   }

   public ItemData getIcon() {
      return new ItemData(Material.END_BRICKS);
   }

   public boolean run(List<Entity> selectedEntities, GameEvent gameEvent, int stackCounter, AtomicInteger callCounter) {
      selectedEntities.forEach((entity) -> {
         User user = User.getUser((CommandSender)entity);
         String idUser = user.getName();
         ItemStack[] items = new ItemStack[]{null};
         Config config = new Config(moduleCreative.getPlugin(), File.separator + "inventories" + File.separator + "saved_inventory" + File.separator + gameEvent.getPlot().getId() + "_" + idUser);

         List list;
         try {
            list = config.getItemStackList("inventory");
         } catch (NullPointerException var8) {
            user.getPlayer().getInventory().setContents(items);
            return;
         }

         if (list != null) {
            items = (ItemStack[])list.toArray(new ItemStack[0]);
         }

         user.getPlayer().getInventory().setContents(items);
      });
      return true;
   }

   public boolean parseChest(ChestParser chestParser) {
      return false;
   }
}
