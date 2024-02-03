package net.mineland.creative.modules.coding.values;

import net.mineland.creative.modules.coding.events.GameEvent;
import net.mineland.creative.modules.coding.values.objects.NumberValue;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import ua.govnojon.libs.bukkitutil.ItemData;

public class CurrentFireTicksValue extends NumberValue {
   public Number get(GameEvent gameEvent, Entity entity) {
      return entity.getFireTicks();
   }

   public ItemData getIcon() {
      return new ItemData(Material.BLAZE_POWDER);
   }
}
