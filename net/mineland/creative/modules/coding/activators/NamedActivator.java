package net.mineland.creative.modules.coding.activators;

import net.mineland.creative.modules.creative.Plot;

public abstract class NamedActivator extends Activator {
   private String customName;

   public NamedActivator(Plot plot) {
      super(plot);
   }

   public String getCustomName() {
      return this.customName;
   }

   public void setCustomName(String customName) {
      this.customName = customName;
   }
}
