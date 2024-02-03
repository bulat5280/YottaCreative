package net.mineland.creative.modules.coding.values;

import net.mineland.creative.modules.coding.events.GameEvent;
import net.mineland.creative.modules.coding.values.objects.NumberValue;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import ua.govnojon.libs.bukkitutil.ItemData;

public class CurrentArmorPointsValue extends NumberValue {
   public Number get(GameEvent gameEvent, Entity entity) {
      return (Number)(entity instanceof LivingEntity ? ((LivingEntity)entity).getAttribute(Attribute.GENERIC_ARMOR).getValue() : 0);
   }

   public ItemData getIcon() {
      return new ItemData(Material.IRON_CHESTPLATE);
   }
}
