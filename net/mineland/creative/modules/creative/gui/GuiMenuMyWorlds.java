package net.mineland.creative.modules.creative.gui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import net.mineland.api.user.IUser;
import net.mineland.core.bukkit.module.Module;
import net.mineland.core.bukkit.modules.confirm.ModuleConfirm;
import net.mineland.core.bukkit.modules.gui.GuiItem;
import net.mineland.core.bukkit.modules.gui.GuiMenu;
import net.mineland.core.bukkit.modules.message.Message;
import net.mineland.core.bukkit.modules.mysql.SQL;
import net.mineland.core.bukkit.modules.region.region.territory.Point;
import net.mineland.core.bukkit.modules.user.User;
import net.mineland.creative.modules.coding.ModuleCoding;
import net.mineland.creative.modules.coding.Param;
import net.mineland.creative.modules.creative.ModuleCreative;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jooq.Record;
import org.jooq.Result;
import ua.govnojon.libs.bukkitutil.ItemData;
import ua.govnojon.libs.bukkitutil.PlayerUtil;
import ua.govnojon.libs.bukkitutil.WorldUtil;

public class GuiMenuMyWorlds extends GuiMenu {
   private static ModuleCreative moduleCreative = (ModuleCreative)Module.getInstance(ModuleCreative.class);
   private static ModuleCoding moduleCoding = (ModuleCoding)Module.getInstance(ModuleCoding.class);
   private static ModuleConfirm moduleConfirm = (ModuleConfirm)Module.getInstance(ModuleConfirm.class);

   public GuiMenuMyWorlds(String key, User user) {
      super(key, user, 3, Message.getMessage((IUser)user, "creative.gui.my_worlds"));
      this.loadOrGetPlotRecords(user, (records) -> {
         int i;
         for(i = 0; i < records.size(); ++i) {
            GuiMenuMyWorlds.PlotData plotData = (GuiMenuMyWorlds.PlotData)records.get(i);
            this.addItem(new GuiMenuMyWorlds.GuiItemLoadPlot(this, 10 + i, plotData.getId(), plotData.getDescription()));
         }

         int availablePlots = this.getAvailablePlots(user);

         for(int j = records.size(); j < availablePlots; ++j) {
            this.addItem(10 + i++, new ItemData(Material.STAINED_GLASS), "lang:creative.gui.plots.create", (event) -> {
               moduleGui.openGui(this.getUser(), GuiMenuGenerator.class);
            });
         }

      });
   }

   private void loadOrGetPlotRecords(User user, Consumer<List<GuiMenuMyWorlds.PlotData>> consumer) {
      user.getMetadata("creative.plots", consumer, (load) -> {
         SQL.async((create) -> {
            Result<Record> fetch = create.fetch("SELECT `id`, `location_x`, `location_z`, `size` FROM `" + moduleCreative.tablePlots + "` WHERE `owner_id`=? AND `is_removed`=FALSE order by id", this.getUser().getName());
            return fetch.map((record) -> {
               int plotId = (Integer)record.get("id", Integer.TYPE);
               String plotDescription = moduleCreative.getPlotManager().syncGetPlotDescription(plotId, (Param)null, this.getUser(), this.getUser().getLang());
               return new GuiMenuMyWorlds.PlotData(plotId, plotDescription);
            });
         }, load);
      });
   }

   private int getAvailablePlots(User user) {
      if (user.hasPermission("mineland.creative.plot_size.youtube")) {
         return 6;
      } else if (user.hasPermission("mineland.creative.plot_size.hero")) {
         return 6;
      } else if (user.hasPermission("mineland.creative.plot_size.expert")) {
         return 5;
      } else if (user.hasPermission("mineland.creative.plot_size.skilled")) {
         return 4;
      } else if (user.hasPermission("mineland.creative.plot_size.gamer")) {
         return 4;
      } else if (user.hasPermission("mineland.creative.plots.upgrade2")) {
         return 3;
      } else {
         return user.hasPermission("mineland.creative.plots.upgrade1") ? 2 : 1;
      }
   }

   private static class PlotData {
      private final int id;
      private final String description;

      public PlotData(int id, String description) {
         this.id = id;
         this.description = description;
      }

      public int getId() {
         return this.id;
      }

      public String getDescription() {
         return this.description;
      }

      public boolean equals(Object o) {
         if (o == this) {
            return true;
         } else if (!(o instanceof GuiMenuMyWorlds.PlotData)) {
            return false;
         } else {
            GuiMenuMyWorlds.PlotData other = (GuiMenuMyWorlds.PlotData)o;
            if (!other.canEqual(this)) {
               return false;
            } else if (this.getId() != other.getId()) {
               return false;
            } else {
               Object this$description = this.getDescription();
               Object other$description = other.getDescription();
               if (this$description == null) {
                  if (other$description != null) {
                     return false;
                  }
               } else if (!this$description.equals(other$description)) {
                  return false;
               }

               return true;
            }
         }
      }

      protected boolean canEqual(Object other) {
         return other instanceof GuiMenuMyWorlds.PlotData;
      }

      public int hashCode() {
         int PRIME = true;
         int result = 1;
         int result = result * 59 + this.getId();
         Object $description = this.getDescription();
         result = result * 59 + ($description == null ? 43 : $description.hashCode());
         return result;
      }

      public String toString() {
         return "GuiMenuMyWorlds.PlotData(id=" + this.getId() + ", description=" + this.getDescription() + ")";
      }
   }

   public class GuiItemLoadPlot extends GuiItem {
      private int plotId;

      GuiItemLoadPlot(GuiMenu guiMenu, int pos, int plotId, String plotDescription) {
         super(guiMenu, pos, new ItemData(Material.BARRIER));
         this.plotId = plotId;
         this.setTextKey("item.loading", new String[0]);
         this.setItem(new ItemStack(Material.GRASS));
         this.setTextKey("creative.gui.plots.load", new String[]{"{info}", plotDescription});
      }

      public void click(InventoryClickEvent event) {
         switch(event.getClick()) {
         case RIGHT:
         case SHIFT_RIGHT:
            GuiMenuMyWorlds.moduleConfirm.confirm(this.getUser(), Message.getMessage((IUser)this.getUser(), "creative.gui.plots.confirm_delete"), (result) -> {
               if (result) {
                  GuiMenuMyWorlds.moduleCreative.getLogger().info("Игрок " + this.getUser().getName() + " удалил свой плот id " + this.plotId);
                  SQL.sync("UPDATE `" + GuiMenuMyWorlds.moduleCreative.tablePlots + "` SET `is_removed`=TRUE WHERE `id`=?", this.plotId);
                  GuiMenuMyWorlds.moduleCreative.getPlotManager().getPlots().stream().filter((p) -> {
                     return p.getId() == this.plotId;
                  }).findFirst().ifPresent((plot) -> {
                     (new ArrayList(plot.getOnlinePlayers())).forEach((user) -> {
                        GuiMenuMyWorlds.moduleCreative.getPlotManager().teleportToLobby(user, true);
                     });
                  });
                  PlayerUtil.sendTitleKey(this.getUser(), "creative.gui.plots.deleted", 0, 60, 10);
                  this.getUser().removeMetadata("creative.plots");
                  GuiMenuMyWorlds.this.reload();
                  SQL.async((create) -> {
                     Record record = create.fetchOne("SELECT `location_x`, `location_z`\nFROM `" + GuiMenuMyWorlds.moduleCreative.tablePlots + "` \nWHERE `id`=? \nLIMIT 1", this.plotId);
                     if (record != null) {
                        Location centerPosition = new Location(GuiMenuMyWorlds.moduleCoding.getCodingWorld(), (Double)record.get("location_x", Double.TYPE), 255.0D, (Double)record.get("location_z", Double.TYPE));
                        Point pos1 = new Point(GuiMenuMyWorlds.moduleCoding.getCodingWorld(), centerPosition.getBlockX() + -GuiMenuMyWorlds.moduleCoding.getCodingSize() * 16, 0, centerPosition.getBlockZ() + -GuiMenuMyWorlds.moduleCoding.getCodingSize() * 16);
                        Point pos2 = new Point(GuiMenuMyWorlds.moduleCoding.getCodingWorld(), centerPosition.getBlockX() + (GuiMenuMyWorlds.moduleCoding.getCodingSize() * 16 - 1), Integer.MAX_VALUE, centerPosition.getBlockZ() + (GuiMenuMyWorlds.moduleCoding.getCodingSize() * 16 - 1));
                        WorldUtil.setBlocks(new Location(pos1.getWorld(), (double)pos1.getX(), 1.0D, (double)pos1.getZ()), new Location(pos2.getWorld(), (double)pos2.getX(), 255.0D, (double)pos2.getZ()), new ItemData(Material.AIR));
                        File file = new File(GuiMenuMyWorlds.moduleCoding.getCodingPlotsDir(), "coding-plot." + this.plotId + ".schematic");
                        if (file.exists()) {
                           file.delete();
                        }
                     }

                  });
               } else {
                  GuiMenuMyWorlds.this.reopen();
               }

            });
            break;
         default:
            if (this.getUser().getPlayer().getVehicle() != null) {
               this.getUser().getPlayer().getVehicle().removePassenger(this.getUser().getPlayer());
            }

            this.getUser().getPlayer().closeInventory();
            PlayerUtil.sendTitleKey(this.getUser(), "creative.plot.loading", 0, 200, 20);
            GuiMenuMyWorlds.moduleCreative.getLogger().info("Игрок загружает свой плот " + this.plotId + " Владелец: " + this.getUser().getPlayer().getName());
            GuiMenuMyWorlds.moduleCreative.getPlotManager().loadPlot(this.plotId, (plot) -> {
               GuiMenuMyWorlds.moduleCreative.getLogger().info("Загрузили плот " + plot.getId() + " Владелец: " + this.getUser().getPlayer().getName());
               plot.addPlayer(this.getUser());
               PlayerUtil.sendTitle(this.getUser().getPlayer(), "", "", 0, 20, 0);
            });
         }

      }
   }
}
