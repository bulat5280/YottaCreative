package net.mineland.core.bukkit.modules.region.region.territory;

import net.mineland.core.bukkit.modules.region.region.territory.types.TerritoryHepler;
import net.mineland.core.bukkit.modules.region.region.territory.types.TerritoryHeplerCube;
import net.mineland.core.bukkit.modules.region.region.territory.types.TerritoryHeplerCyl;

public enum TerritoryType {
   CUBE(new TerritoryHeplerCube()),
   CYL(new TerritoryHeplerCyl());

   private TerritoryHepler hepler;

   private TerritoryType(TerritoryHepler hepler) {
      this.hepler = hepler;
   }

   public static TerritoryType forName(String name) {
      name = name.toUpperCase();
      return valueOf(name);
   }

   public TerritoryHepler getHepler() {
      return this.hepler;
   }
}
