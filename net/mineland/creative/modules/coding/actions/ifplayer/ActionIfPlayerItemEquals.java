package net.mineland.creative.modules.coding.actions.ifplayer;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import net.mineland.core.bukkit.modules.message.Message;
import net.mineland.core.bukkit.modules.user.User;
import net.mineland.creative.modules.coding.ChestParser;
import net.mineland.creative.modules.coding.actions.Action;
import net.mineland.creative.modules.coding.actions.ActionIf;
import net.mineland.creative.modules.coding.actions.ActionType;
import net.mineland.creative.modules.coding.activators.Activator;
import net.mineland.creative.modules.coding.events.GameEvent;
import net.mineland.creative.modules.coding.events.ItemEvent;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import ua.govnojon.libs.bukkitutil.BukkitUtil;
import ua.govnojon.libs.bukkitutil.ItemData;
import ua.govnojon.libs.bukkitutil.ItemStackUtil;

public class ActionIfPlayerItemEquals extends ActionIf {
   public ActionIfPlayerItemEquals(Activator activator) {
      super(activator);
   }

   public boolean expression(List<Entity> selectedEntities, GameEvent gameEvent) {
      if (!(gameEvent instanceof ItemEvent)) {
         Collection<User> receivers = gameEvent.getPlot().getOnlinePlayers();
         Message condition = new Message("creative.condition.ifplayeritemequals", new String[0]);
         receivers.forEach((user) -> {
            user.sendMessage("creative.несовместимое_событие", "{condition}", condition.translate(user.getLang()));
         });
         return false;
      } else {
         ItemStack eventItem = ((ItemEvent)gameEvent).getItem();
         Iterator var4 = selectedEntities.iterator();

         while(var4.hasNext()) {
            Entity entity = (Entity)var4.next();
            ItemStack[] items = (ItemStack[])this.getVar("items", gameEvent, entity);
            ItemStack[] var7 = items;
            int var8 = items.length;

            for(int var9 = 0; var9 < var8; ++var9) {
               ItemStack item = var7[var9];
               if (!ItemStackUtil.isNullOrAir(item)) {
                  if (item.getAmount() == 1) {
                     if (BukkitUtil.equalsIgnoreAmount(item, eventItem)) {
                        return true;
                     }
                  } else if (BukkitUtil.equalsIgnoreAmount(item, eventItem) && item.getAmount() == eventItem.getAmount()) {
                     return true;
                  }
               }
            }
         }

         return false;
      }
   }

   public ActionType getType() {
      return ActionType.IF_PLAYER_ITEM_EQUALS;
   }

   public Action.Category getCategory() {
      return Action.Category.IF_PLAYER;
   }

   public ItemData getIcon() {
      return new ItemData(Material.WORKBENCH);
   }

   public boolean parseChest(ChestParser chestParser) {
      this.putVar("items", chestParser.getItems(), false);
      return true;
   }
}
