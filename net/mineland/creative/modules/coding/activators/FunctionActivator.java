package net.mineland.creative.modules.coding.activators;

import net.mineland.creative.modules.creative.Plot;
import org.bukkit.Location;
import org.bukkit.Material;
import ua.govnojon.libs.bukkitutil.ItemData;

public class FunctionActivator extends NamedActivator {
   private ItemData functionIcon;
   private Location location;

   public FunctionActivator(Plot plot) {
      super(plot);
      this.functionIcon = new ItemData(Material.LEASH);
   }

   public ActivatorType getType() {
      return ActivatorType.GAME_LOOP;
   }

   public ItemData getIcon() {
      return new ItemData(Material.EMERALD_BLOCK);
   }

   public ItemData getFunctionIcon() {
      return this.functionIcon;
   }

   public void setFunctionIcon(ItemData functionIcon) {
      this.functionIcon = functionIcon;
   }

   public Location getLocation() {
      return this.location;
   }

   public void setLocation(Location location) {
      this.location = location;
   }
}
