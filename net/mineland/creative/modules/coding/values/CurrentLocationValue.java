package net.mineland.creative.modules.coding.values;

import net.mineland.creative.modules.coding.events.GameEvent;
import net.mineland.creative.modules.coding.values.objects.LocationValue;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import ua.govnojon.libs.bukkitutil.ItemData;

public class CurrentLocationValue extends LocationValue {
   public Location get(GameEvent gameEvent, Entity entity) {
      return entity.getLocation();
   }

   public ItemData getIcon() {
      return new ItemData(Material.PAPER);
   }
}
