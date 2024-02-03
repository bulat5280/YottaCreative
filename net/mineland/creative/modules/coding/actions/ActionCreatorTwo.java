package net.mineland.creative.modules.coding.actions;

import net.mineland.creative.modules.coding.worldactivators.WorldActivator;
import org.bukkit.Material;
import ua.govnojon.libs.bukkitutil.ItemData;

@FunctionalInterface
public interface ActionCreatorTwo {
   Action createTwo(WorldActivator var1);

   default ActionType getType() {
      return null;
   }

   default Action.Category getCategory() {
      return null;
   }

   default ItemData getIcon() {
      return new ItemData(Material.BEDROCK);
   }
}
