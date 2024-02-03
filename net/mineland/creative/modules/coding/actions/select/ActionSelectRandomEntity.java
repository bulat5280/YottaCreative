package net.mineland.creative.modules.coding.actions.select;

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
import ua.govnojon.libs.bukkitutil.ItemData;
import ua.govnojon.libs.myjava.RandomUtil;

public class ActionSelectRandomEntity extends ActionSelect {
   public ActionSelectRandomEntity(Activator activator) {
      super(activator);
   }

   public List<Entity> execute(GameEvent gameEvent) {
      List<Entity> collect = (List)gameEvent.getPlot().getPlotTerritory().getEntities().stream().filter((entity) -> {
         return entity.getType() != EntityType.PLAYER;
      }).collect(Collectors.toList());
      return Collections.singletonList(RandomUtil.getRandomObject(collect));
   }

   public ActionType getType() {
      return ActionType.SELECT_RANDOM_ENTITY;
   }

   public ItemData getIcon() {
      return new ItemData(Material.SNOW_BALL);
   }
}
