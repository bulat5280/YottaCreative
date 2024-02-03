package net.mineland.creative.modules.creative.gui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import net.mineland.api.user.IUser;
import net.mineland.core.bukkit.module.Module;
import net.mineland.core.bukkit.modules.gui.GuiItem;
import net.mineland.core.bukkit.modules.gui.GuiMenu;
import net.mineland.core.bukkit.modules.gui.GuiMenuPages;
import net.mineland.core.bukkit.modules.gui.GuiPage;
import net.mineland.core.bukkit.modules.gui.ModuleGui;
import net.mineland.core.bukkit.modules.message.Message;
import net.mineland.core.bukkit.modules.user.User;
import net.mineland.creative.modules.creative.ModuleCreative;
import net.mineland.creative.modules.creative.Plot;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import ua.govnojon.libs.bukkitutil.ItemData;

public class GuiMenuPlotsOnline extends GuiMenuPages {
   private static ModuleCreative moduleCreative = (ModuleCreative)Module.getInstance(ModuleCreative.class);
   private static ModuleGui moduleGui = (ModuleGui)Module.getInstance(ModuleGui.class);
   private List<Plot> plotList;

   public GuiMenuPlotsOnline(String key, User user) {
      super(key, user, 6, Message.getMessage((IUser)user, "creative.gui.plots_online"), 1);
      this.plotList = new ArrayList(moduleCreative.getPlotManager().getPlots());
      this.plotList.sort(Collections.reverseOrder(Comparator.comparing((plot) -> {
         return plot.getOnlinePlayers().size();
      })));
      this.setMaxPages(Math.max(this.plotList.size() - 1, 0) / 45 + 1);
   }

   public void onLoadPage(GuiPage page) {
      int pos = 0;

      for(int i = Math.min(page.getPage() * 45, this.plotList.size()); i < Math.min(page.getPage() * 45 + 45, this.plotList.size()); ++i) {
         Plot plot = (Plot)this.plotList.get(i);
         this.getCurrentPage().getGuiMenu().addItem(new GuiMenuPlotsOnline.GuiItemPlot(this.getCurrentPage().getGuiMenu(), pos, plot));
         ++pos;
      }

   }

   public static class GuiItemPlot extends GuiItem {
      private Plot plot;

      GuiItemPlot(GuiMenu guiMenu, int pos, Plot plot) {
         super(guiMenu, pos, plot.isClosed() ? new ItemData(Material.BARRIER) : plot.getPlotMode().getIcon());
         if (plot.isOwner(this.getUser())) {
            super.setEnchantEffect(true);
         }

         this.setAmount(plot.getOnlinePlayers().size());
         this.plot = plot;
         this.setText(plot.getGuiDescription(this.getUser().getLang()));
      }

      public void click(InventoryClickEvent event) {
         this.getUser().getPlayer().closeInventory();
         if (this.plot.isClosed() && !this.getUser().hasPermission("creative.enter.bypass") && !this.plot.isOwner(this.getUser()) && !this.plot.getWhitelist().contains(this.getUser().getName())) {
            this.getUser().sendMessage("creative.plot_is_closed");
         } else {
            if (this.getUser().getPlayer().getVehicle() != null) {
               this.getUser().getPlayer().getVehicle().removePassenger(this.getUser().getPlayer());
            }

            this.plot.addPlayer(this.getUser());
         }

      }
   }
}
