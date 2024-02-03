package net.mineland.creative.modules.coding.values;

import net.mineland.creative.modules.coding.events.GameEvent;
import net.mineland.creative.modules.coding.values.objects.NumberValue;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import ua.govnojon.libs.bukkitutil.ItemData;

public class CurrentHeldSlotValue extends NumberValue {
   public Number get(GameEvent gameEvent, Entity entity) {
      return entity instanceof Player ? ((Player)entity).getInventory().getHeldItemSlot() : 0;
   }

   public ItemData getIcon() {
      return new ItemData(Material.COMMAND_REPEATING);
   }
}
