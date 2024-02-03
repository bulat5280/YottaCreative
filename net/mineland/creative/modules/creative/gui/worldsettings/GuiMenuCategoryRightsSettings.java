package net.mineland.creative.modules.creative.gui.worldsettings;

import net.mineland.api.user.IUser;
import net.mineland.core.bukkit.module.Module;
import net.mineland.core.bukkit.modules.gui.GuiMenu;
import net.mineland.core.bukkit.modules.message.Message;
import net.mineland.core.bukkit.modules.user.User;
import net.mineland.creative.modules.creative.ModuleCreative;
import net.mineland.creative.modules.creative.Plot;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class GuiMenuCategoryRightsSettings extends GuiMenu {
   private static ModuleCreative moduleCreative = (ModuleCreative)Module.getInstance(ModuleCreative.class);

   public GuiMenuCategoryRightsSettings(String key, User user) {
      super(key, user, 3, Message.getMessage((IUser)user, "creative.gui.category_rights_settings.title"));
      this.setUnloadOnClose();
      this.init();
   }

   private void init() {
      Plot plot = moduleCreative.getPlotManager().getCurrentPlot(this.getUser());
      this.addItem(2, 2, new ItemStack(Material.QUARTZ_BLOCK), Message.getMessage((IUser)this.getUser(), "creative.gui.world_settings.whitelist"), (event) -> {
         switch(event.getClick()) {
         case LEFT:
         case SHIFT_LEFT:
         case WINDOW_BORDER_LEFT:
            moduleGui.openGui(this.getUser(), GuiMenuWorldAddWhitelist.class);
            return;
         default:
            moduleGui.openGui(this.getUser(), GuiMenuWorldRemoveWhitelist.class);
         }
      });
      this.addItem(3, 2, new ItemStack(Material.COAL_BLOCK), Message.getMessage((IUser)this.getUser(), "creative.gui.world_settings.blacklist"), (event) -> {
         switch(event.getClick()) {
         case LEFT:
         case SHIFT_LEFT:
         case WINDOW_BORDER_LEFT:
            moduleGui.openGui(this.getUser(), GuiMenuWorldAddBlacklist.class);
            return;
         default:
            moduleGui.openGui(this.getUser(), GuiMenuWorldRemoveBlacklist.class);
         }
      });
      this.addItem(5, 2, new ItemStack(Material.FEATHER), Message.getMessage((IUser)this.getUser(), "creative.gui.world_settings.fly"), (event) -> {
         switch(event.getClick()) {
         case LEFT:
         case SHIFT_LEFT:
         case WINDOW_BORDER_LEFT:
            moduleGui.openGui(this.getUser(), GuiMenuWorldFly.class);
            return;
         default:
            plot.setAllowFly(!plot.isAllowFly());
            this.init();
         }
      }).setEnchantEffect(plot.isAllowFly());
      this.addItem(7, 2, new ItemStack(Material.BRICK), Message.getMessage((IUser)this.getUser(), "creative.gui.world_settings.build"), (event) -> {
         switch(event.getClick()) {
         case LEFT:
         case SHIFT_LEFT:
         case WINDOW_BORDER_LEFT:
            moduleGui.openGui(this.getUser(), GuiMenuWorldBuild.class);
            return;
         default:
            plot.setAllowBuild(!plot.isAllowBuild());
            this.init();
         }
      }).setEnchantEffect(plot.isAllowBuild());
      this.addItem(8, 2, new ItemStack(Material.COMMAND), Message.getMessage((IUser)this.getUser(), "creative.gui.world_settings.code"), (event) -> {
         moduleGui.openGui(this.getUser(), GuiMenuWorldCode.class);
      });
   }
}
