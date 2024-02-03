package net.mineland.core.bukkit.modules.potions;

import net.mineland.core.bukkit.module.Module;

public class Potions {
   public static ModulePotions getModule() {
      return (ModulePotions)Module.getInstance(ModulePotions.class);
   }
}
