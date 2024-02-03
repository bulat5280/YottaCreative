package net.mineland.creative.modules.creative.gui;

import net.mineland.api.user.IUser;
import net.mineland.core.bukkit.module.Module;
import net.mineland.core.bukkit.modules.gui.GuiItem;
import net.mineland.core.bukkit.modules.gui.GuiMenu;
import net.mineland.core.bukkit.modules.mainitem.MainBar;
import net.mineland.core.bukkit.modules.mainitem.ModuleMainItem;
import net.mineland.core.bukkit.modules.message.Message;
import net.mineland.core.bukkit.modules.user.User;
import net.mineland.creative.modules.creative.ModuleCreative;
import net.mineland.creative.modules.creative.generators.biomes.GeneratorType;
import org.bukkit.Location;
import org.bukkit.event.inventory.InventoryClickEvent;
import ua.govnojon.libs.bukkitutil.PlayerUtil;
import ua.govnojon.libs.bukkitutil.schedule.Schedule;
import ua.govnojon.libs.myjava.Try;

public class GuiMenuGenerator extends GuiMenu {
   private static ModuleMainItem moduleMainItem = (ModuleMainItem)Module.getInstance(ModuleMainItem.class);
   private static ModuleCreative moduleCreative = (ModuleCreative)Module.getInstance(ModuleCreative.class);

   public GuiMenuGenerator(String key, User user) {
      super(key, user, 2, Message.getMessage((IUser)user, "creative.gui.generator.title"));
      this.init();
   }

   private void init() {
      this.clear();
      GeneratorType[] generatorTypes = GeneratorType.values();

      for(int i = 0; i < generatorTypes.length; ++i) {
         this.addItem(new GuiMenuGenerator.GuiItemCreatePlot(this, i, generatorTypes[i]));
      }

   }

   public static class GuiItemCreatePlot extends GuiItem {
      private GeneratorType generatorType;

      GuiItemCreatePlot(GuiMenu guiMenu, int pos, GeneratorType generatorType) {
         super(guiMenu, pos, generatorType.getIcon());
         this.generatorType = generatorType;
         this.setTextKey("creative.gui.plots.generate." + generatorType.name(), new String[0]);
      }

      public void click(InventoryClickEvent event) {
         if (!this.getUser().hasPermission("creative.generate." + this.generatorType.name())) {
            this.getUser().sendMessage("нет_прав");
         } else {
            MainBar mainBar = GuiMenuGenerator.moduleMainItem.getMainBar(this.getUser());
            mainBar.clear();
            if (this.getUser().getPlayer().getVehicle() != null) {
               this.getUser().getPlayer().getVehicle().removePassenger(this.getUser().getPlayer());
            }

            this.getUser().getPlayer().closeInventory();
            PlayerUtil.sendTitleKey(this.getUser(), "creative.plot.loading", 0, 200, 20);
            Try.ignore(() -> {
               GuiMenuGenerator.moduleGui.reloadGui(this.getUser(), GuiMenuMyWorlds.class);
            });
            this.getUser().removeMetadata("creative.plots");
            GuiMenuGenerator.moduleCreative.getLogger().info("Игрок " + this.getUser().getName() + " создает новый плот " + this.generatorType.name());
            GuiMenuGenerator.moduleCreative.getPlotManager().createPlot(this.generatorType, this.getUser(), (plot) -> {
               GuiMenuGenerator.moduleCreative.getLogger().info("Создали плот " + plot.getId() + " Владелец: " + this.getUser().getPlayer().getName());
               plot.addPlayer(this.getUser());
               Schedule.later(() -> {
                  if (plot.getSpawnLocation(GuiMenuGenerator.moduleCreative.getPlotWorld()).toVector().equals(plot.getCenterPosition())) {
                     Location location = plot.getCenterPosition().toLocation(GuiMenuGenerator.moduleCreative.getPlotWorld());
                     location.setY((double)(location.getWorld().getHighestBlockYAt(location) + 1));
                     plot.setSpawnLocation(location);
                     this.getUser().getPlayer().teleport(location);
                  }

               }, 10L);
               PlayerUtil.sendTitle(this.getUser().getPlayer(), "", "", 0, 20, 0);
            });
         }

      }
   }
}
