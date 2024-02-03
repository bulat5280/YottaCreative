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
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import ua.govnojon.libs.bukkitutil.ItemData;
import ua.govnojon.libs.bukkitutil.ItemStackUtil;
import ua.govnojon.libs.bukkitutil.schedule.Schedule;

public class ActionPlayFireworkExplosion extends Action {
   public ActionPlayFireworkExplosion(Activator activator) {
      super(activator);
   }

   public ActionType getType() {
      return ActionType.GAME_PLAY_FIREWORK_EXPLOSION;
   }

   public Action.Category getCategory() {
      return Action.Category.GAME_SPECIAL_EFFECTS;
   }

   public ItemData getIcon() {
      return new ItemData(Material.FIREWORK);
   }

   public boolean run(List<Entity> selectedEntities, GameEvent gameEvent, int stackCounter, AtomicInteger callCounter) {
      selectedEntities.forEach((entity) -> {
         ItemStack item = (ItemStack)this.getVar("item", gameEvent, entity);
         if (!ItemStackUtil.isNullOrAir(item)) {
            if (item.getType() == Material.FIREWORK) {
               Location location = this.getVarLocation("location", gameEvent, entity);
               if (location != null) {
                  FireworkMeta fireworkMeta = (FireworkMeta)item.getItemMeta();
                  Firework firework = (Firework)location.getWorld().spawnEntity(location, EntityType.FIREWORK);
                  firework.setFireworkMeta(fireworkMeta);
                  Schedule.later(firework::detonate, 1L);
               }
            }
         }
      });
      return true;
   }

   public boolean parseChest(ChestParser chestParser) {
      this.putVar("item", chestParser.getItem(0), false);
      this.putVar("location", chestParser.getLocation(0));
      return true;
   }
}
