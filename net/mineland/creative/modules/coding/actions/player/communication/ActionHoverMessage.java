package net.mineland.creative.modules.coding.actions.player.communication;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import net.mineland.creative.modules.coding.ChestParser;
import net.mineland.creative.modules.coding.actions.Action;
import net.mineland.creative.modules.coding.actions.ActionType;
import net.mineland.creative.modules.coding.activators.Activator;
import net.mineland.creative.modules.coding.events.GameEvent;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import ua.govnojon.libs.bukkitutil.ItemData;
import ua.govnojon.libs.bukkitutil.chatcomponent.ChatComponent;
import ua.govnojon.libs.bukkitutil.chatcomponent.Hover;

public class ActionHoverMessage extends Action {
   public ActionHoverMessage(Activator activator) {
      super(activator);
   }

   public ActionType getType() {
      return ActionType.PLAYER_HOVER_MESSAGE;
   }

   public Action.Category getCategory() {
      return Action.Category.PLAYER_COMMUNICATION;
   }

   public ItemData getIcon() {
      return new ItemData(Material.BOOK_AND_QUILL);
   }

   public boolean run(List<Entity> selectedEntities, GameEvent gameEvent, int paramInt, AtomicInteger paramAtomicInteger) {
      selectedEntities.forEach((entity) -> {
         Player player = (Player)entity;
         ChatComponent component = new ChatComponent();
         String message = this.getVarString("message", gameEvent, entity);
         String hovermess = this.getVarString("hovermess", gameEvent, entity);
         component.addText(message, (Hover)Hover.show_text, hovermess);
         component.send(player);
      });
      return true;
   }

   public boolean parseChest(ChestParser chestParser) {
      this.putVar("message", chestParser.getText(0));
      this.putVar("hovermess", chestParser.getText(1));
      return true;
   }
}
