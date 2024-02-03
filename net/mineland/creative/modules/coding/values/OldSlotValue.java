package net.mineland.creative.modules.coding.values;

import net.mineland.creative.modules.coding.activators.player.PlayerChangeSlotActivator;
import net.mineland.creative.modules.coding.events.GameEvent;
import net.mineland.creative.modules.coding.values.objects.NumberValue;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import ua.govnojon.libs.bukkitutil.ItemData;

public class OldSlotValue extends NumberValue {
   public Number get(GameEvent gameEvent, Entity entity) {
      return gameEvent instanceof PlayerChangeSlotActivator.Event ? ((PlayerChangeSlotActivator.Event)gameEvent).getPreviousSlot() : 0;
   }

   public ItemData getIcon() {
      return new ItemData(Material.MAGMA_CREAM);
   }
}
