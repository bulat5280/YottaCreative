package net.mineland.creative.modules.coding.values;

import net.mineland.creative.modules.coding.events.BlockEvent;
import net.mineland.creative.modules.coding.events.GameEvent;
import net.mineland.creative.modules.coding.values.objects.LocationValue;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import ua.govnojon.libs.bukkitutil.ItemData;

public class EventBlockLocationValue extends LocationValue {
   public Location get(GameEvent gameEvent, Entity entity) {
      return gameEvent instanceof BlockEvent ? ((BlockEvent)gameEvent).getBlock().getLocation().add(0.5D, 0.5D, 0.5D) : null;
   }

   public ItemData getIcon() {
      return new ItemData(Material.COBBLESTONE);
   }
}
