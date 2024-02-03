package net.mineland.creative.modules.creative.gui.worldsettings.flags;

import java.util.Arrays;
import java.util.Iterator;
import net.mineland.core.bukkit.modules.gui.GuiItem;
import net.mineland.core.bukkit.modules.gui.GuiManager;
import net.mineland.core.bukkit.modules.gui.GuiMenu;
import net.mineland.core.bukkit.modules.gui.ModuleGui;
import net.mineland.core.bukkit.modules.gui.defaultitems.GuiItemFlag;
import net.mineland.core.bukkit.modules.message.Message;
import net.mineland.core.bukkit.modules.user.User;
import net.mineland.creative.modules.creative.Plot;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GuiItemFlagIsland extends GuiItemFlag<GuiItemFlag.Value> {
   private static ModuleGui moduleGui;
   private Plot plot;
   private String label;

   GuiItemFlagIsland(GuiMenu guiMenu, Plot plot, int x, int y, Material material, short data, int amount, String text, String label, GuiItemFlag.Value[] values) {
      super(guiMenu, x, y, material, data, amount);
      this.plot = plot;
      this.label = label;

      try {
         Integer value = plot.getSettings(label);
         if (value == null) {
            value = 1;
         }

         this.setValues(text, values[value], Arrays.asList(values));
      } catch (Exception var12) {
         moduleGui.getLogger().severe("Ошибка при загрузке флага '" + label + "' острова " + plot.getId() + ".");
         var12.printStackTrace();
      }

   }

   public void click(InventoryClickEvent event) {
      Player player = this.getPlayer();
      if (this.plot.getOwner().getName() != this.getUser().getName()) {
         player.sendMessage(Message.getMessage(this.getUser().getLang(), "вы_не_создатель"));
         player.closeInventory();
      } else {
         super.click(event);
      }
   }

   public void updateStage() {
      this.plot.setSettings(this.label, this.getPosValue((GuiItemFlag.Value)this.getCurrent()));
      Iterator var1 = this.plot.getOnlinePlayers().iterator();

      while(true) {
         while(true) {
            User online;
            do {
               do {
                  if (!var1.hasNext()) {
                     return;
                  }

                  online = (User)var1.next();
               } while(this.getUser().equals(online));
            } while(!moduleGui.isLoadedGui(online, "flags_island"));

            GuiMenuFlags guiMenuFlags = (GuiMenuFlags)moduleGui.getGuiPlayer(online, "flags_island");
            Iterator var4 = guiMenuFlags.getItems().iterator();

            while(var4.hasNext()) {
               GuiItem guiItem = (GuiItem)var4.next();
               if (guiItem instanceof GuiItemFlagIsland) {
                  GuiItemFlagIsland guiItemFlagIsland = (GuiItemFlagIsland)guiItem;
                  if (guiItemFlagIsland.getLabel().equals(this.label)) {
                     guiItemFlagIsland.setValue(this.getCurrent());
                     break;
                  }
               }
            }
         }
      }
   }

   private int getPosValue(GuiItemFlag.Value neW) {
      int i = 0;

      for(Iterator var3 = this.getValues().iterator(); var3.hasNext(); ++i) {
         GuiItemFlag.Value value = (GuiItemFlag.Value)var3.next();
         if (value.equals(neW)) {
            return i;
         }
      }

      return 0;
   }

   private String getLabel() {
      return this.label;
   }

   public Plot getPlot() {
      return this.plot;
   }

   static {
      moduleGui = GuiManager.moduleGui;
   }
}
