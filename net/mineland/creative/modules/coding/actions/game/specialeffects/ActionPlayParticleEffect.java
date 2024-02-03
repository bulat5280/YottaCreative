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
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import ua.govnojon.libs.bukkitutil.ItemData;
import ua.govnojon.libs.bukkitutil.WorldUtil;

public class ActionPlayParticleEffect extends Action {
   public ActionPlayParticleEffect(Activator activator) {
      super(activator);
   }

   public ActionType getType() {
      return ActionType.GAME_LAUNCH_FIREWORK;
   }

   public Action.Category getCategory() {
      return Action.Category.GAME_SPECIAL_EFFECTS;
   }

   public ItemData getIcon() {
      return new ItemData(Material.GOLD_NUGGET);
   }

   public boolean run(List<Entity> selectedEntities, GameEvent gameEvent, int stackCounter, AtomicInteger callCounter) {
      selectedEntities.forEach((entity) -> {
         Particle particle = (Particle)this.getVar("particle", gameEvent, entity);
         if (particle != null) {
            Location location = this.getVarLocation("location", gameEvent, entity);
            if (location != null) {
               WorldUtil.easyParticles(location, particle, true, 0.0D, 0.0D, 0.0D, 1, 0.0D, 0.0D, 0.0D, 0.0D);
            }
         }
      });
      return true;
   }

   public boolean parseChest(ChestParser chestParser) {
      this.putVar("particle", chestParser.getParticle(0));
      this.putVar("location", chestParser.getLocation(0));
      return true;
   }
}
