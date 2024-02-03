package net.mineland.creative.modules.coding.actions.player.communication;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import net.mineland.creative.modules.coding.ChestParser;
import net.mineland.creative.modules.coding.actions.Action;
import net.mineland.creative.modules.coding.actions.ActionType;
import net.mineland.creative.modules.coding.activators.Activator;
import net.mineland.creative.modules.coding.events.GameEvent;
import net.mineland.creative.modules.creative.Plot;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import ua.govnojon.libs.bukkitutil.ItemData;

public class ActionDialogue extends Action {
   public ActionDialogue(Activator activator) {
      super(activator);
   }

   public boolean run(List<Entity> selectedEntities, GameEvent gameEvent, int stackCounter, AtomicInteger callCounter) {
      if (this.getActivator() == null) {
         return true;
      } else {
         Plot plot = this.getActivator().getPlot();
         selectedEntities.forEach((entity) -> {
            int delay = this.getVar("delay", 20, gameEvent, entity);
            List<String> messages = this.getVarsStrings("messages", gameEvent, entity);
            if (!messages.isEmpty()) {
               for(int i = 0; i < messages.size(); ++i) {
                  String message = (String)messages.get(i);
                  plot.getCodeHandler().schedule(() -> {
                     entity.sendMessage(message);
                  }).later((long)(i * delay));
               }

            }
         });
         return true;
      }
   }

   public ActionType getType() {
      return ActionType.PLAYER_DIALOGUE;
   }

   public Action.Category getCategory() {
      return Action.Category.PLAYER_COMMUNICATION;
   }

   public ItemData getIcon() {
      return new ItemData(Material.BOOKSHELF);
   }

   public boolean needPerm() {
      return true;
   }

   public boolean parseChest(ChestParser chestParser) {
      this.putVar("delay", chestParser.getNumber(0));
      this.putVar("messages", chestParser.getTexts());
      return true;
   }
}
