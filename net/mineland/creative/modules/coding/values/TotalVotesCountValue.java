package net.mineland.creative.modules.coding.values;

import net.mineland.creative.modules.coding.events.GameEvent;
import net.mineland.creative.modules.coding.values.objects.NumberValue;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import ua.govnojon.libs.bukkitutil.ItemData;

public class TotalVotesCountValue extends NumberValue {
   public Number get(GameEvent gameEvent, Entity entity) {
      return gameEvent.getPlot().getVotes().size();
   }

   public ItemData getIcon() {
      return new ItemData(Material.GOLD_NUGGET);
   }
}
