package net.mineland.creative.modules.coding.values.objects;

import net.mineland.creative.modules.coding.events.GameEvent;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

public abstract class LocationValue implements Value {
   public abstract Location get(GameEvent var1, Entity var2);
}
