package net.mineland.creative.modules.coding.values;

import net.mineland.creative.modules.coding.events.GameEvent;
import net.mineland.creative.modules.coding.values.objects.NumberValue;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import ua.govnojon.libs.bukkitutil.ItemData;

public class CurrentAirRemainingValue extends NumberValue {
   public Number get(GameEvent gameEvent, Entity entity) {
      return entity instanceof LivingEntity ? ((LivingEntity)entity).getRemainingAir() : 0;
   }

   public ItemData getIcon() {
      return new ItemData(Material.RAW_FISH);
   }
}
