package net.mineland.creative.modules.coding.actions.select;

import java.util.Collections;
import java.util.List;
import net.mineland.creative.modules.coding.actions.ActionSelect;
import net.mineland.creative.modules.coding.actions.ActionType;
import net.mineland.creative.modules.coding.activators.Activator;
import net.mineland.creative.modules.coding.events.GameEvent;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import ua.govnojon.libs.bukkitutil.ItemData;

public class ActionSelectLastSpawnedMob extends ActionSelect {
   public ActionSelectLastSpawnedMob(Activator activator) {
      super(activator);
   }

   public List<Entity> execute(GameEvent gameEvent) {
      return Collections.singletonList(gameEvent.getPlot().getCodeHandler().getLastSpawnedEntity());
   }

   public ActionType getType() {
      return ActionType.SELECT_LAST_SPAWNED_MOB;
   }

   public ItemData getIcon() {
      return new ItemData(Material.EGG);
   }
}
