package net.mineland.creative.modules.coding.actions.gui;

import net.mineland.api.user.IUser;
import net.mineland.core.bukkit.module.Module;
import net.mineland.core.bukkit.modules.gui.GuiItem;
import net.mineland.core.bukkit.modules.gui.GuiMenu;
import net.mineland.core.bukkit.modules.message.Message;
import net.mineland.core.bukkit.modules.user.User;
import net.mineland.creative.modules.coding.actions.PlayerSelection;
import net.mineland.creative.modules.creative.ModuleCreative;
import net.mineland.creative.modules.creative.Plot;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.inventory.InventoryClickEvent;
import ua.govnojon.libs.bukkitutil.PlayerUtil;

public class GuiMenuPlayerSelection extends GuiMenu {
   private static ModuleCreative moduleCreative = (ModuleCreative)Module.getInstance(ModuleCreative.class);

   public GuiMenuPlayerSelection(String key, User user) {
      super(key, user, 1, Message.getMessage((IUser)user, "coding.gui.player_selection.title"));
      int pos = 0;
      PlayerSelection[] var4 = PlayerSelection.values();
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         PlayerSelection playerSelection = var4[var6];
         this.addItem(new GuiMenuPlayerSelection.GuiItemPlayerActivator(this, pos, playerSelection));
         ++pos;
      }

   }

   public class GuiItemPlayerActivator extends GuiItem {
      PlayerSelection selection;

      GuiItemPlayerActivator(GuiMenu guiMenu, int pos, PlayerSelection playerSelection) {
         super(guiMenu, pos, playerSelection.getIcon().toItemStack(), Message.getMessage((IUser)guiMenu.getUser(), "coding.gui.selection." + playerSelection.name()));
         this.selection = playerSelection;
      }

      public void click(InventoryClickEvent event) {
         Block block = (Block)this.getUser().removeMetadata("coding.selected_sign");
         Plot plot = GuiMenuPlayerSelection.moduleCreative.getPlotManager().getCurrentPlot(this.getUser());
         if (plot != null && block != null && block.getType() == Material.WALL_SIGN) {
            this.getPlayer().closeInventory();
            PlayerUtil.playSound(this.getPlayer(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP);
            Sign sign = (Sign)block.getState();
            sign.setLine(2, "lang:coding.sign." + this.selection.name());
            sign.update();
            GuiMenuPlayerSelection.moduleCreative.getPlotManager().parseCoding(plot);
         } else {
            this.getPlayer().closeInventory();
         }

      }
   }
}
