package net.mineland.creative.modules.creative.gui.worldsettings;

import net.mineland.api.user.IUser;
import net.mineland.core.bukkit.module.Module;
import net.mineland.core.bukkit.modules.confirm.ModuleConfirm;
import net.mineland.core.bukkit.modules.gui.GuiMenu;
import net.mineland.core.bukkit.modules.gui.defaultitems.GuiItemCommand;
import net.mineland.core.bukkit.modules.message.Message;
import net.mineland.core.bukkit.modules.user.User;
import net.mineland.creative.modules.creative.ModuleCreative;
import net.mineland.creative.modules.creative.Plot;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class GuiMenuCategoryDevSettings extends GuiMenu {
   private static ModuleCreative moduleCreative = (ModuleCreative)Module.getInstance(ModuleCreative.class);
   private static ModuleConfirm moduleConfirm = (ModuleConfirm)Module.getInstance(ModuleConfirm.class);

   public GuiMenuCategoryDevSettings(String key, User user) {
      super(key, user, 3, Message.getMessage((IUser)user, "creative.gui.category_dev_settings.title"));
      this.setUnloadOnClose();
      this.init();
   }

   private void init() {
      Plot plot = moduleCreative.getPlotManager().getCurrentPlot(this.getUser());
      GuiItemCommand viewVars = new GuiItemCommand(this, 5, 2, Material.SLIME_BALL, Message.getMessage((IUser)this.getUser(), "creative.gui.world_settings.view_variables"), "/plot vars", true);
      this.addItem(viewVars);
      this.addItem(3, 2, new ItemStack(Material.MAGMA_CREAM), Message.getMessage((IUser)this.getUser(), "creative.gui.world_settings.clear_variables"), (event) -> {
         event.getWhoClicked().closeInventory();
         User user = this.getUser();
         if (plot != null && plot.isOwner(user)) {
            int amount = plot.getCodeHandler().getDynamicVariables().size();
            if (amount == 0) {
               user.sendMessage("creative.commands.no_varsclear");
            } else {
               plot.getCodeHandler().getDynamicVariables().clear();
               user.sendMessage("creative.переменные_очищены");
            }
         }

      });
   }
}
