package net.mineland.creative.modules.creative.gui.worldsettings;

import net.mineland.api.user.IUser;
import net.mineland.core.bukkit.module.Module;
import net.mineland.core.bukkit.modules.gui.GuiMenu;
import net.mineland.core.bukkit.modules.message.Message;
import net.mineland.core.bukkit.modules.user.User;
import net.mineland.creative.modules.creative.ModuleCreative;
import net.mineland.creative.modules.creative.Plot;
import net.mineland.creative.modules.creative.gui.worldsettings.flags.GuiMenuFlags;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class GuiMenuCategoryWorldSettings extends GuiMenu {
   private static ModuleCreative moduleCreative = (ModuleCreative)Module.getInstance(ModuleCreative.class);

   public GuiMenuCategoryWorldSettings(String key, User user) {
      super(key, user, 3, Message.getMessage((IUser)user, "creative.gui.category_world_settings.title"));
      this.setUnloadOnClose();
      this.init();
   }

   private void init() {
      Plot plot = moduleCreative.getPlotManager().getCurrentPlot(this.getUser());
      this.addItem(2, 2, new ItemStack(Material.NAME_TAG), Message.getMessage((IUser)this.getUser(), "creative.gui.world_settings.name"), (event) -> {
         this.getUser().getPlayer().closeInventory();
         this.getUser().setMetadata("plot.set_name", plot);
         this.getUser().sendMessage("status.напишите_сообщение", "{help}", Message.getMessage((IUser)this.getUser(), "creative.gui.plots.name", "{name}", this.getUser().getPlayer().getDisplayName()));
      });
      this.addItem(4, 2, new ItemStack(Material.ENDER_PEARL), Message.getMessage((IUser)this.getUser(), "creative.gui.world_settings.spawnpoint"), (event) -> {
         switch(event.getClick()) {
         case LEFT:
         case SHIFT_LEFT:
         case WINDOW_BORDER_LEFT:
            Vector center = plot.getCenterPosition();
            Vector max = center.clone().add(new Vector(plot.getSize() * 16, 0, plot.getSize() * 16));
            Vector min = center.clone().subtract(new Vector(plot.getSize() * 16, 0, plot.getSize() * 16));
            Location loc = this.getUser().getPlayer().getLocation();
            if (!loc.getWorld().equals(moduleCreative.getPlotWorld())) {
               this.getUser().sendMessage("нужно.находиться.внутри.плота");
            } else if (loc.getX() >= min.getX() && loc.getZ() >= min.getZ() && loc.getX() <= max.getX() && loc.getZ() <= max.getZ()) {
               plot.setSpawnLocation(loc);
            } else {
               this.getUser().sendMessage("нужно.находиться.внутри.плота");
            }

            return;
         default:
            plot.setSpawnLocation((Location)null);
         }
      });
      this.addItem(5, 2, new ItemStack(Material.BARRIER), Message.getMessage((IUser)this.getUser(), "creative.gui.world_settings.close"), (event) -> {
         plot.setIsClosed(!plot.isClosed());
         this.getUser().sendMessage(plot.isClosed() ? "creative.commands.toggleopen.closed" : "creative.commands.toggleopen.opened");
         this.init();
      }).setEnchantEffect(plot.isClosed());
      this.addItem(6, 2, new ItemStack(Material.BANNER), Message.getMessage((IUser)this.getUser(), "creative.gui.world_settings.flags"), (event) -> {
         moduleGui.openGui(this.getUser(), GuiMenuFlags.class);
      });
   }
}
