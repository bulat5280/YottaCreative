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

public class ActionHeal extends Action {
   public ActionHeal(Activator activator) {
      super(activator);
   }

   public boolean run(List<Entity> selectedEntities, GameEvent gameEvent, int stackCounter, AtomicInteger callCounter) {
      selectedEntities.forEach((entity) -> {
         Player player = (Player)entity;
         Number heal = new Double((double)this.getVar("heal", 1, gameEvent, entity));
         double health = player.getHealth() + heal.doubleValue();
         double maxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
         health = health < 0.0D ? 0.0D : (health > maxHealth ? maxHealth : health);
         player.setHealth(health);
      });
      return true;
   }

   public ActionType getType() {
      return ActionType.PLAYER_HEAL;
   }

   public Action.Category getCategory() {
      return Action.Category.PLAYER_PARAMETERS;
   }

   public ItemData getIcon() {
      return new ItemData(Material.COOKIE);
   }

   public boolean parseChest(ChestParser chestParser) {
      this.putVar("heal", chestParser.getNumber(0));
      return true;
   }
}
