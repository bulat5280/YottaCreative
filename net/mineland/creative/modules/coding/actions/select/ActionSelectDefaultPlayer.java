package net.mineland.creative.modules.coding.actions.select;

import java.util.ArrayList;
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

public class ActionSelectDefaultPlayer extends ActionSelect {
   public ActionSelectDefaultPlayer(Activator activator) {
      super(activator);
   }

   public List<Entity> execute(GameEvent gameEvent) {
      Entity defaultEntity = gameEvent.getDefaultEntity();
      return (List)(defaultEntity != null && defaultEntity.getType() == EntityType.PLAYER ? Collections.singletonList(defaultEntity) : new ArrayList());
   }

   public ActionType getType() {
      return ActionType.SELECT_DEFAULT_PLAYER;
   }

   public ItemData getIcon() {
      return new ItemData(Material.POTATO_ITEM);
   }
}
