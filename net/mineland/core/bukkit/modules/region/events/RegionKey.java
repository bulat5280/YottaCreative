package net.mineland.core.bukkit.modules.region.events;

import net.mineland.core.bukkit.modules.region.region.Region;

public class RegionKey {
   public Region region;
   public String value;

   public RegionKey(Region region, String value) {
      this.region = region;
      this.value = value;
   }
}
