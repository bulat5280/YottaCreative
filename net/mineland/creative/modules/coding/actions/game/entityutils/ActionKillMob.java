package net.mineland.creative.modules.coding.actions.game.entityutils;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import net.mineland.creative.modules.coding.actions.Action;
import net.mineland.creative.modules.coding.actions.ActionType;
import net.mineland.creative.modules.coding.activators.Activator;
import net.mineland.creative.modules.coding.events.GameEvent;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import ua.govnojon.libs.bukkitutil.ItemData;

public class ActionKillMob extends Action {
   public ActionKillMob(Activator activator) {
      super(activator);
   }

   public ActionType getType() {
      return ActionType.MOB_KILL_MOB;
   }

   public Action.Category getCategory() {
      return Action.Category.GAME_ENTITY_UTILS;
   }

   public ItemData getIcon() {
      return new ItemData(Material.BARRIER);
   }

   public boolean run(List<Entity> paramList, GameEvent paramGameEvent, int paramInt, AtomicInteger paramAtomicInteger) {
      this.selectedEntities.forEach((entity) -> {
         LivingEntity ent = (LivingEntity)entity;
         ent.damage(9.99999999E8D);
      });
      return true;
   }
}
