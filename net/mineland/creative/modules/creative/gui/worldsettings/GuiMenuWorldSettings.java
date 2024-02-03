package net.mineland.creative.modules.creative.gui.worldsettings;

import net.mineland.api.user.IUser;
import net.mineland.core.bukkit.module.Module;
import net.mineland.core.bukkit.modules.gui.GuiMenu;
import net.mineland.core.bukkit.modules.gui.defaultitems.GuiItemText;
import net.mineland.core.bukkit.modules.message.Message;
import net.mineland.core.bukkit.modules.user.User;
import net.mineland.creative.modules.creative.ModuleCreative;
import net.mineland.creative.modules.creative.Plot;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import ua.govnojon.libs.bukkitutil.ItemStackUtil;

public class GuiMenuWorldSettings extends GuiMenu {
   private static ModuleCreative moduleCreative = (ModuleCreative)Module.getInstance(ModuleCreative.class);

   public GuiMenuWorldSettings(String key, User user) {
      super(key, user, 3, Message.getMessage((IUser)user, "creative.gui.world_settings.title"));
      this.setUnloadOnClose();
      this.init();
   }

   private void init() {
      this.addItem(3, 2, new ItemStack(Material.SMOOTH_BRICK), Message.getMessage((IUser)this.getUser(), "creative.gui.category_world_settings"), (event) -> {
         moduleGui.openGui(this.getUser(), GuiMenuCategoryWorldSettings.class);
      });
      this.addItem(5, 2, new ItemStack(ItemStackUtil.createSkinSkullByUser(String.valueOf(this.getUser()))), Message.getMessage((IUser)this.getUser(), "creative.gui.category_rights_settings"), (event) -> {
         moduleGui.openGui(this.getUser(), GuiMenuCategoryRightsSettings.class);
      });
      this.addItem(7, 2, new ItemStack(Material.COMMAND_CHAIN), Message.getMessage((IUser)this.getUser(), "creative.gui.category_dev_settings"), (event) -> {
         moduleGui.openGui(this.getUser(), GuiMenuCategoryDevSettings.class);
      });
      Plot plot = moduleCreative.getPlotManager().getCurrentPlot(this.getUser());
      if (plot != null) {
         this.addItem(new GuiItemText(this, 5, 3, Material.PAPER, "creative.gui.world_info", new String[]{"{name}", plot.getName(), "{id}", String.valueOf(plot.getId()), "{votes}", String.valueOf(plot.getVotes().size())}));
      }
   }
}
