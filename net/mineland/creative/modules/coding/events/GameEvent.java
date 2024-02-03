package net.mineland.creative.modules.coding.events;

import net.mineland.creative.modules.creative.Plot;
import org.bukkit.entity.Entity;

public interface GameEvent {
   Plot getPlot();

   Entity getDefaultEntity();
}
