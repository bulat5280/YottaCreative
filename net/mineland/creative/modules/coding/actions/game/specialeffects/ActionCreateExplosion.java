package net.mineland.creative.modules.coding.actions.game.specialeffects;

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
import ua.govnojon.libs.bukkitutil.ItemData;

public class ActionCreateExplosion extends Action {
   public ActionCreateExplosion(Activator activator) {
      super(activator);
   }

   public ActionType getType() {
      return ActionType.GAME_CREATE_EXPLOSION;
   }

   public Action.Category getCategory() {
      return Action.Category.GAME_SPECIAL_EFFECTS;
   }

   public ItemData getIcon() {
      return new ItemData(Material.TNT);
   }

   public boolean run(List<Entity> selectedEntities, GameEvent gameEvent, int stackCounter, AtomicInteger callCounter) {
      selectedEntities.forEach((entity) -> {
         Location location = this.getVarLocation("location", gameEvent, entity);
         if (location != null) {
            double power = this.getVar("power", 1.0D, gameEvent, entity);
            power = power > 4.0D ? 4.0D : (power < -4.0D ? -4.0D : power);
            entity.getWorld().createExplosion(location, (float)power, false);
         }
      });
      return true;
   }

   public boolean parseChest(ChestParser chestParser) {
      this.putVar("location", chestParser.getLocation(0));
      this.putVar("power", chestParser.getNumber(0));
      return true;
   }
}
