package net.mineland.core.bukkit.modules.potions;

import org.bukkit.Material;

public enum PotionBottleType {
   NORMAL(Material.POTION),
   SPLASH(Material.SPLASH_POTION),
   LINGERING(Material.LINGERING_POTION);

   private Material material;

   private PotionBottleType(Material mat) {
      this.material = mat;
   }

   public Material getMaterial() {
      return this.material;
   }
}
