package net.mineland.creative.modules.coding.values;

import net.mineland.creative.modules.coding.CodeUtils;
import net.mineland.creative.modules.coding.events.GameEvent;
import net.mineland.creative.modules.coding.values.objects.StringValue;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import ua.govnojon.libs.bukkitutil.ItemData;

public class CurrentNameValue extends StringValue {
   public String get(GameEvent gameEvent, Entity entity) {
      return CodeUtils.getEntityName(entity);
   }

   public ItemData getIcon() {
      return new ItemData(Material.NAME_TAG);
   }
}
