package net.mineland.creative.modules.coding.values.objects;

import net.mineland.creative.modules.coding.events.GameEvent;
import org.bukkit.entity.Entity;

public abstract class NumberValue implements Value {
   public abstract Number get(GameEvent var1, Entity var2);
}
