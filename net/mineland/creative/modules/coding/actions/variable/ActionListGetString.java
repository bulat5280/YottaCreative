package net.mineland.creative.modules.coding.actions.variable;

import java.io.File;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import net.mineland.creative.modules.coding.ChestParser;
import net.mineland.creative.modules.coding.actions.Action;
import net.mineland.creative.modules.coding.actions.ActionType;
import net.mineland.creative.modules.coding.activators.Activator;
import net.mineland.creative.modules.coding.events.GameEvent;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import ua.govnojon.libs.bukkitutil.ItemData;
import ua.govnojon.libs.config.Config;

public class ActionListGetString extends Action {
   public ActionListGetString(Activator activator) {
      super(activator);
   }

   public ActionType getType() {
      return ActionType.PLAYER_LIST_GETSTRING;
   }

   public Action.Category getCategory() {
      return Action.Category.GAME_CONFIGS;
   }

   public ItemData getIcon() {
      return new ItemData(Material.BOOK);
   }

   public boolean parseChest(ChestParser chestParser) {
      this.putVar("name", chestParser.getText(0));
      this.putVar("section", chestParser.getText(1));
      return true;
   }

   public boolean run(List<Entity> selectedEntities, GameEvent gameEvent, int paramInt, AtomicInteger paramAtomicInteger) {
      selectedEntities.forEach((entity) -> {
         String name = this.getVarString("name", gameEvent, entity);
         String section = this.getVarString("section", gameEvent, entity).replace(" ", ".");
         Config config = new Config(moduleCreative.getPlugin(), File.separator + "worldlists" + File.separator + "list" + File.separator + gameEvent.getPlot().getId() + "_" + name + ".yml");
         config.getString(section);
      });
      return true;
   }
}
