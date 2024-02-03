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
import org.bukkit.util.Vector;
import ua.govnojon.libs.bukkitutil.ItemData;
import ua.govnojon.libs.bukkitutil.WorldUtil;

public class ActionCreateParticleLine extends Action {
   public ActionCreateParticleLine(Activator activator) {
      super(activator);
   }

   public ActionType getType() {
      return ActionType.GAME_CREATE_PARTICLE_LINE;
   }

   public Action.Category getCategory() {
      return Action.Category.GAME_SPECIAL_EFFECTS;
   }

   public ItemData getIcon() {
      return new ItemData(Material.STICK);
   }

   public boolean run(List<Entity> selectedEntities, GameEvent gameEvent, int stackCounter, AtomicInteger callCounter) {
      selectedEntities.forEach((entity) -> {
         Particle particle = (Particle)this.getVar("particle", gameEvent, entity);
         if (particle != null) {
            Location startLocation = this.getVarLocation("start_location", gameEvent, entity);
            if (startLocation != null) {
               Location endLocation = this.getVarLocation("end_location", gameEvent, entity);
               this.display(particle, startLocation, endLocation);
            }
         }
      });
      return true;
   }

   public void display(Particle particle, Location start, Location end) {
      Vector vector = end.toVector().subtract(start.toVector());

      for(double i = 1.0D; i <= start.distance(end); i += 0.5D) {
         vector.multiply(i);
         start.add(vector);
         WorldUtil.easyParticles(start, particle, true, 0.0D, 0.0D, 0.0D, 1, 0.0D, 0.0D, 0.0D, 0.0D);
         start.subtract(vector);
         vector.normalize();
      }

   }

   public boolean parseChest(ChestParser chestParser) {
      this.putVar("particle", chestParser.getParticle(0));
      this.putVar("start_location", chestParser.getLocation(0));
      this.putVar("end_location", chestParser.getLocation(0));
      return true;
   }
}
