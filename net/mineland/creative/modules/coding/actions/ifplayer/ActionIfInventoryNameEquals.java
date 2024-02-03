package net.mineland.creative.modules.coding.actions.ifplayer;

import java.util.List;
import net.mineland.creative.modules.coding.ChestParser;
import net.mineland.creative.modules.coding.actions.Action;
import net.mineland.creative.modules.coding.actions.ActionIf;
import net.mineland.creative.modules.coding.actions.ActionType;
import net.mineland.creative.modules.coding.activators.Activator;
import net.mineland.creative.modules.coding.events.GameEvent;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import ua.govnojon.libs.bukkitutil.ItemData;

public class ActionIfInventoryNameEquals extends ActionIf {
   public ActionIfInventoryNameEquals(Activator activator) {
      super(activator);
   }

   public boolean expression(List<Entity> list, GameEvent gameEvent) {
      Player player = (Player)gameEvent.getDefaultEntity();
      List<String> names = this.getVarsStrings("name", gameEvent, player);
      return this.equals(player, names);
   }

   private boolean equals(Player player, List<String> name) {
      return player.getInventory().getName().equals(name);
   }

   public ActionType getType() {
      return ActionType.IF_INVENTORY_NAME_EQUALS;
   }

   public Action.Category getCategory() {
      return Action.Category.IF_PLAYER;
   }

   public ItemData getIcon() {
      return new ItemData(Material.TRAPPED_CHEST);
   }

   public boolean parseChest(ChestParser chestParser) {
      this.putVar("name", chestParser.getTexts(), false);
      return true;
   }
}
