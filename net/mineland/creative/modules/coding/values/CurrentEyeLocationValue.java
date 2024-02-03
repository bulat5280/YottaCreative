package net.mineland.creative.modules.coding.values;

import net.mineland.creative.modules.coding.events.GameEvent;
import net.mineland.creative.modules.coding.values.objects.LocationValue;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import ua.govnojon.libs.bukkitutil.ItemData;

public class CurrentEyeLocationValue extends LocationValue {
   public Location get(GameEvent gameEvent, Entity entity) {
      return entity instanceof LivingEntity ? ((LivingEntity)entity).getEyeLocation() : null;
   }

   public ItemData getIcon() {
      return new ItemData(Material.EYE_OF_ENDER);
   }
}
