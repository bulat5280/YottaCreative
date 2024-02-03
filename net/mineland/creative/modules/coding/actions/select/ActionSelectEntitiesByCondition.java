package net.mineland.creative.modules.coding.actions.select;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import net.mineland.creative.modules.coding.actions.ActionSelect;
import net.mineland.creative.modules.coding.actions.ActionType;
import net.mineland.creative.modules.coding.activators.Activator;
import net.mineland.creative.modules.coding.events.GameEvent;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import ua.govnojon.libs.bukkitutil.ItemData;

public class ActionSelectEntitiesByCondition extends ActionSelect {
   public ActionSelectEntitiesByCondition(Activator activator) {
      super(activator);
   }

   public List<Entity> execute(GameEvent gameEvent) {
      return (List)(this.getCondition() != null ? (List)gameEvent.getPlot().getPlotTerritory().getEntities().stream().filter((entity) -> {
         return entity instanceof LivingEntity;
      }).filter((entity) -> {
         return entity.getType() != EntityType.PLAYER;
      }).filter((entity) -> {
         return this.getCondition().condition(Collections.singletonList(entity), gameEvent);
      }).collect(Collectors.toList()) : new ArrayList());
   }

   public ActionType getType() {
      return ActionType.SELECT_MOBS_BY_CONDITION;
   }

   public ItemData getIcon() {
      return new ItemData(Material.SEEDS);
   }
}
