package net.mineland.creative.modules.coding.values;

import net.mineland.creative.modules.coding.events.GameEvent;
import net.mineland.creative.modules.coding.values.objects.NumberValue;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import ua.govnojon.libs.bukkitutil.ItemData;

public class CurrentSaturationLevelValue extends NumberValue {
   public Number get(GameEvent gameEvent, Entity entity) {
      return (Number)(entity instanceof Player ? ((Player)entity).getSaturation() : 0);
   }

   public ItemData getIcon() {
      return new ItemData(Material.COOKED_CHICKEN);
   }
}
