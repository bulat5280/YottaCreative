package net.mineland.creative.modules.creative;

import net.mineland.core.bukkit.module.Module;
import net.mineland.core.bukkit.modules.mainitem.MainBar;
import net.mineland.core.bukkit.modules.mainitem.ModuleMainItem;
import net.mineland.core.bukkit.modules.mainitem.defaultmainitems.MainItemOpenGui;
import net.mineland.core.bukkit.modules.user.User;
import net.mineland.creative.modules.coding.ModuleCoding;
import net.mineland.creative.modules.coding.PlotScoreboard;
import net.mineland.creative.modules.coding.activators.player.PlayerJoinActivator;
import net.mineland.creative.modules.coding.activators.player.PlayerQuitActivator;
import net.mineland.creative.modules.creative.gui.worldsettings.GuiMenuWorldSettings;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import ua.govnojon.libs.bukkitutil.ItemData;
import ua.govnojon.libs.bukkitutil.PlayerUtil;

public enum PlotMode {
   PLAYING(new ItemData(Material.DIAMOND)) {
      public void onLoad(Plot plot) {
         plot.loadRegion();
         PlotMode.moduleCreative.getPlotManager().parseCoding(plot);
      }

      public void onPlayerJoin(User user) {
         Player player = user.getPlayer();
         Plot currentPlot = PlotMode.moduleCreative.getPlotManager().getCurrentPlot(user);
         PlayerUtil.setDefaultParameters(player, GameMode.ADVENTURE);
         player.setAllowFlight(false);
         player.teleport(currentPlot.getSpawnLocation());
         PlayerJoinActivator.Event event = new PlayerJoinActivator.Event(user, currentPlot, (Event)null);
         event.callEvent();
         event.handle();
      }

      public void onPlayerQuit(User user) {
         Player player = user.getPlayer();
         player.setGameMode(GameMode.ADVENTURE);
         Plot currentPlot = PlotMode.moduleCreative.getPlotManager().getCurrentPlot(user);
         PlayerQuitActivator.Event event = new PlayerQuitActivator.Event(user, currentPlot, (Event)null);
         event.callEvent();
         event.handle();
      }
   },
   BUILDING(new ItemData(Material.BRICK)) {
      public void onLoad(Plot plot) {
         plot.loadRegion();
      }

      public void onPlayerJoin(User user) {
         Plot currentPlot = PlotMode.moduleCreative.getPlotManager().getCurrentPlot(user);
         Player player = user.getPlayer();
         player.teleport(currentPlot.getSpawnLocation());
         PlotScoreboard plotScoreboard = (PlotScoreboard)user.getMetadata("plot_scoreboard");
         if (plotScoreboard != null) {
            plotScoreboard.hide(user);
         }

         if (currentPlot.getPlotRegion().isMember(user)) {
            PlayerUtil.setDefaultParameters(player, GameMode.CREATIVE);
            player.setAllowFlight(true);
            if (currentPlot.isOwner(user)) {
               MainBar mainBar = PlotMode.moduleMainItem.getMainBar(user);
               mainBar.addItem((new MainItemOpenGui(mainBar, new ItemData(Material.COMPASS), "creative.world_settings", 8, GuiMenuWorldSettings.class)).setTranslate(true));
            }
         } else {
            PlayerUtil.setDefaultParameters(player, GameMode.ADVENTURE);
            player.setAllowFlight(currentPlot.isAllowFly());
         }

      }

      public void onPlayerQuit(User user) {
         Plot currentPlot = PlotMode.moduleCreative.getPlotManager().getCurrentPlot(user);
         Player player = user.getPlayer();
         player.setGameMode(GameMode.ADVENTURE);
         MainBar mainBar = PlotMode.moduleMainItem.getMainBar(user);
         mainBar.clear();
      }
   };

   private static ModuleCreative moduleCreative = (ModuleCreative)Module.getInstance(ModuleCreative.class);
   private static ModuleCoding moduleCoding = (ModuleCoding)Module.getInstance(ModuleCoding.class);
   private static ModuleMainItem moduleMainItem = (ModuleMainItem)Module.getInstance(ModuleMainItem.class);
   private ItemData icon;

   private PlotMode(ItemData icon) {
      this.icon = icon;
   }

   public ItemData getIcon() {
      return this.icon;
   }

   public void onPlayerJoin(User user) {
   }

   public void onPlayerQuit(User user) {
   }

   public void onLoad(Plot plot) {
   }

   // $FF: synthetic method
   PlotMode(ItemData x2, Object x3) {
      this(x2);
   }
}
