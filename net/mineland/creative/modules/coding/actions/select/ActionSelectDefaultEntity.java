package net.mineland.creative.modules.coding.actions.select;

import java.util.Collections;
import java.util.List;
import net.mineland.creative.modules.coding.actions.ActionSelect;
import net.mineland.creative.modules.coding.actions.ActionType;
import net.mineland.creative.modules.coding.activators.Activator;
import net.mineland.creative.modules.coding.events.GameEvent;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import ua.govnojon.libs.bukkitutil.ItemData;

public class ActionSelectDefaultEntity extends ActionSelect {
   public ActionSelectDefaultEntity(Activator activator) {
      super(activator);
   }

   public List<Entity> execute(GameEvent gameEvent) {
      Entity defaultEntity = gameEvent.getDefaultEntity();
      return defaultEntity != null && defaultEntity.getType() != EntityType.PLAYER ? Collections.singletonList(defaultEntity) : Collections.singletonList(gameEvent.getPlot().getCodeHandler().getLastSpawnedEntity());
   }

   public ActionType getType() {
      return ActionType.SELECT_DEFAULT_ENTITY;
   }

   public ItemData getIcon() {
      return new ItemData(Material.CARROT_ITEM);
   }
}
