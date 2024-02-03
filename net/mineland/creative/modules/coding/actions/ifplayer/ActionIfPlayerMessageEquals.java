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
import net.mineland.creative.modules.coding.events.ChatEvent;
import net.mineland.creative.modules.coding.events.GameEvent;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import ua.govnojon.libs.bukkitutil.ItemData;

public class ActionIfPlayerMessageEquals extends ActionIf {
   public ActionIfPlayerMessageEquals(Activator activator) {
      super(activator);
   }

   public boolean expression(List<Entity> selectedEntities, GameEvent gameEvent) {
      if (gameEvent instanceof ChatEvent) {
         ChatEvent chatEvent = (ChatEvent)gameEvent;
         String message = chatEvent.getMessage();
         if (message == null) {
            return false;
         } else {
            Iterator var5 = selectedEntities.iterator();

            List names;
            do {
               if (!var5.hasNext()) {
                  return true;
               }

               Entity entity = (Entity)var5.next();
               names = this.getVarsStrings("name", gameEvent, entity);
            } while(!names.stream().noneMatch((name) -> {
               return StringUtils.equals(message, name);
            }));

            return false;
         }
      } else {
         Collection<User> receivers = gameEvent.getPlot().getOnlinePlayers();
         Message condition = new Message("creative.condition.ifplayermessageequals", new String[0]);
         receivers.forEach((user) -> {
            user.sendMessage("creative.несовместимое_событие", "{condition}", condition.translate(user.getLang()));
         });
         return false;
      }
   }

   public ActionType getType() {
      return ActionType.IF_PLAYER_MESSAGE_EQUALS;
   }

   public Action.Category getCategory() {
      return Action.Category.IF_PLAYER;
   }

   public ItemData getIcon() {
      return new ItemData(Material.BOOK_AND_QUILL);
   }

   public boolean parseChest(ChestParser chestParser) {
      this.putVar("name", chestParser.getTexts());
      return true;
   }
}
