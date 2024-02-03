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
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TNTPrimed;
import ua.govnojon.libs.bukkitutil.ItemData;

public class ActionSpawnTnt extends Action {
   public ActionSpawnTnt(Activator activator) {
      super(activator);
   }

   public ActionType getType() {
      return ActionType.GAME_SPAWN_TNT;
   }

   public Action.Category getCategory() {
      return Action.Category.GAME_ENTITY_SPAWNING;
   }

   public ItemData getIcon() {
      return new ItemData(Material.TNT);
   }

   public boolean run(List<Entity> selectedEntities, GameEvent gameEvent, int stackCounter, AtomicInteger callCounter) {
      selectedEntities.forEach((entity) -> {
         Location location = this.getVarLocation("location", gameEvent, entity);
         if (location != null) {
            int power = this.getVar("power", 4, gameEvent, entity);
            power = power > 4 ? 4 : (power < -4 ? -4 : power);
            int fuse = this.getVar("fuse", 0, gameEvent, entity);
            TNTPrimed tnt = (TNTPrimed)location.getWorld().spawnEntity(location, EntityType.PRIMED_TNT);
            tnt.setYield((float)power);
            tnt.setFuseTicks(fuse);
         }
      });
      return true;
   }

   public boolean parseChest(ChestParser chestParser) {
      this.putVar("location", chestParser.getLocation(0));
      this.putVar("power", chestParser.getNumber(0));
      this.putVar("fuse", chestParser.getNumber(1));
      return true;
   }
}
