package net.mineland.creative.modules.coding.values.objects;

import net.mineland.creative.modules.coding.events.GameEvent;
import org.bukkit.entity.Entity;
import ua.govnojon.libs.bukkitutil.ItemData;

public interface Value {
   Object get(GameEvent var1, Entity var2);

   ItemData getIcon();
}
