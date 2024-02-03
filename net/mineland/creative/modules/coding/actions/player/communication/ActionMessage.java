package net.mineland.creative.modules.coding.actions.player.communication;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import net.mineland.creative.modules.coding.ChestParser;
import net.mineland.creative.modules.coding.actions.Action;
import net.mineland.creative.modules.coding.actions.ActionType;
import net.mineland.creative.modules.coding.activators.Activator;
import net.mineland.creative.modules.coding.events.GameEvent;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import ua.govnojon.libs.bukkitutil.ItemData;

public class ActionMessage extends Action {
   public ActionMessage(Activator activator) {
      super(activator);
   }

   public boolean run(List<Entity> selectedEntities, GameEvent gameEvent, int stackCounter, AtomicInteger callCounter) {
      selectedEntities.forEach((entity) -> {
         List<String> texts = this.getVarsStrings("texts", gameEvent, entity);
         if (!texts.isEmpty()) {
            entity.sendMessage(StringUtils.join(texts, " "));
         }

      });
      return true;
   }

   public ActionType getType() {
      return ActionType.PLAYER_MESSAGE;
   }

   public Action.Category getCategory() {
      return Action.Category.PLAYER_COMMUNICATION;
   }

   public ItemData getIcon() {
      return new ItemData(Material.BOOK);
   }

   public boolean parseChest(ChestParser chestParser) {
      this.putVar("texts", chestParser.getTexts());
      return true;
   }
}
