package net.mineland.core.bukkit.modules.gui.defaultitems;

import java.util.Arrays;
import net.mineland.core.bukkit.modules.gui.GuiMenu;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public abstract class GuiItemSwitch extends GuiItemFlag<GuiItemFlag.Value> {
   public GuiItemSwitch(GuiMenu guiMenu, int x, int y, ItemStack item) {
      super(guiMenu, x, y, item);
   }

   public GuiItemSwitch(GuiMenu guiMenu, int x, int y, Material material) {
      super(guiMenu, x, y, material);
   }

   public void updateStage() {
      boolean enable = this.getCurrent() == GuiItemFlag.Value.ENABLE;
      this.setEnchantEffect(enable);
      this.onEnable(enable);
   }

   public GuiItemSwitch setEnable(boolean enable, String first) {
      GuiItemFlag.Value current = enable ? GuiItemFlag.Value.ENABLE : GuiItemFlag.Value.DISABLE;
      this.setValues(first, current, Arrays.asList(GuiItemFlag.Value.ENABLE, GuiItemFlag.Value.DISABLE));
      this.setEnchantEffect(enable);
      return this;
   }

   public abstract void onEnable(boolean var1);
}
