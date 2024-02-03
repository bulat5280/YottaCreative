package net.mineland.creative.modules.coding.actions.select;

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

public class ActionSelectAllEntities extends ActionSelect {
   public ActionSelectAllEntities(Activator activator) {
      super(activator);
   }

   public List<Entity> execute(GameEvent gameEvent) {
      return (List)gameEvent.getPlot().getPlotTerritory().getEntities().stream().filter((entity) -> {
         return entity.getType() != EntityType.PLAYER;
      }).collect(Collectors.toList());
   }

   public ActionType getType() {
      return ActionType.SELECT_ALL_ENTITIES;
   }

   public ItemData getIcon() {
      return new ItemData(Material.STORAGE_MINECART);
   }
}
