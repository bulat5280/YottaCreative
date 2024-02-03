package net.mineland.creative.modules.coding.actions.game.entityspawning;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import net.mineland.creative.modules.coding.ChestParser;
import net.mineland.creative.modules.coding.actions.Action;
import net.mineland.creative.modules.coding.actions.ActionType;
import net.mineland.creative.modules.coding.activators.Activator;
import net.mineland.creative.modules.coding.events.GameEvent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ExperienceOrb;
import ua.govnojon.libs.bukkitutil.ItemData;

public class ActionSpawnXpOrb extends Action {
   public ActionSpawnXpOrb(Activator activator) {
      super(activator);
   }

   public ActionType getType() {
      return ActionType.GAME_SPAWN_XP_ORB;
   }

   public Action.Category getCategory() {
      return Action.Category.GAME_ENTITY_SPAWNING;
   }

   public ItemData getIcon() {
      return new ItemData(Material.EXP_BOTTLE);
   }

   public boolean run(List<Entity> selectedEntities, GameEvent gameEvent, int stackCounter, AtomicInteger callCounter) {
      selectedEntities.forEach((entity) -> {
         Location location = this.getVarLocation("location", gameEvent, entity);
         if (location != null) {
            int amount = this.getVar("amount", 0, gameEvent, entity);
            String name = this.getVarString("name", gameEvent, entity);
            ExperienceOrb orb = (ExperienceOrb)location.getWorld().spawn(location, ExperienceOrb.class);
            if (amount != 0) {
               orb.setExperience(amount);
            }

            if (name != null && !name.isEmpty()) {
               orb.setCustomNameVisible(true);
               orb.setCustomName(name);
            }

         }
      });
      return true;
   }

   public boolean parseChest(ChestParser chestParser) {
      this.putVar("location", chestParser.getLocation(0));
      this.putVar("amount", chestParser.getNumber(0));
      this.putVar("name", chestParser.getText(0));
      return true;
   }
}
