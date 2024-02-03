package net.mineland.creative.modules.coding.actions.player.statistics;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import net.mineland.creative.modules.coding.ChestParser;
import net.mineland.creative.modules.coding.actions.Action;
import net.mineland.creative.modules.coding.actions.ActionType;
import net.mineland.creative.modules.coding.activators.Activator;
import net.mineland.creative.modules.coding.events.GameEvent;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import ua.govnojon.libs.bukkitutil.ItemData;

public class ActionSetMaxHealth extends Action {
   public ActionSetMaxHealth(Activator activator) {
      super(activator);
   }

   public boolean run(List<Entity> selectedEntities, GameEvent gameEvent, int stackCounter, AtomicInteger callCounter) {
      selectedEntities.forEach((entity) -> {
         Player player = (Player)entity;
         Number heal = new Double((double)this.getVar("health", 1, gameEvent, entity));
         double health = heal.doubleValue();
         health = health < 1.0D ? 1.0D : health;
         player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health);
      });
      return true;
   }

   public ActionType getType() {
      return ActionType.PLAYER_SET_MAX_HEALTH;
   }

   public Action.Category getCategory() {
      return Action.Category.PLAYER_PARAMETERS;
   }

   public ItemData getIcon() {
      return new ItemData(Material.COOKED_CHICKEN);
   }

   public boolean parseChest(ChestParser chestParser) {
      this.putVar("health", chestParser.getNumber(0));
      return true;
   }
}
