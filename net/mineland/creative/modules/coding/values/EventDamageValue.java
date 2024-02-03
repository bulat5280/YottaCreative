package net.mineland.creative.modules.coding.values;

import net.mineland.creative.modules.coding.events.DamageEvent;
import net.mineland.creative.modules.coding.events.GameEvent;
import net.mineland.creative.modules.coding.values.objects.NumberValue;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import ua.govnojon.libs.bukkitutil.ItemData;

public class EventDamageValue extends NumberValue {
   public Number get(GameEvent gameEvent, Entity entity) {
      return (Number)(gameEvent instanceof DamageEvent ? ((DamageEvent)gameEvent).getDamage() : 0);
   }

   public ItemData getIcon() {
      return new ItemData(Material.IRON_SWORD);
   }
}
