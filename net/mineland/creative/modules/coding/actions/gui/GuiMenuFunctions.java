package net.mineland.creative.modules.coding.actions.gui;

import java.util.Iterator;
import net.mineland.api.user.IUser;
import net.mineland.core.bukkit.module.Module;
import net.mineland.core.bukkit.modules.gui.GuiItem;
import net.mineland.core.bukkit.modules.gui.GuiMenu;
import net.mineland.core.bukkit.modules.message.Message;
import net.mineland.core.bukkit.modules.user.User;
import net.mineland.creative.modules.coding.activators.FunctionActivator;
import net.mineland.creative.modules.creative.ModuleCreative;
import net.mineland.creative.modules.creative.Plot;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import ua.govnojon.libs.bukkitutil.PlayerUtil;

public class GuiMenuFunctions extends GuiMenu {
   private static ModuleCreative moduleCreative = (ModuleCreative)Module.getInstance(ModuleCreative.class);

   public GuiMenuFunctions(String key, User user) {
      super(key, user, 4, Message.getMessage((IUser)user, "coding.gui.functions.title"));
   }

   protected void onInventoryOpenEvent(InventoryOpenEvent event) {
      this.clear();
      Plot plot = moduleCreative.getPlotManager().getCurrentPlot(this.getUser());
      int pos = 0;

      for(Iterator var4 = plot.getCodeHandler().getFunctions().iterator(); var4.hasNext(); ++pos) {
         FunctionActivator function = (FunctionActivator)var4.next();
         this.addItem(new GuiMenuFunctions.GuiItemPlayerActivator(this, pos, function));
      }

   }

   public class GuiItemPlayerActivator extends GuiItem {
      FunctionActivator function;

      GuiItemPlayerActivator(GuiMenu guiMenu, int pos, FunctionActivator functionActivator) {
         super(guiMenu, pos, functionActivator.getFunctionIcon().toItemStack(), Message.getMessage((IUser)guiMenu.getUser(), "coding.gui.function.item", "{name}", functionActivator.getCustomName()));
         this.function = functionActivator;
      }

      public void click(InventoryClickEvent event) {
         Block block = (Block)this.getUser().removeMetadata("coding.selected_sign");
         Plot plot = GuiMenuFunctions.moduleCreative.getPlotManager().getCurrentPlot(this.getUser());
         if (block != null && block.getType() == Material.WALL_SIGN) {
            this.getPlayer().closeInventory();
            switch(event.getClick()) {
            case RIGHT:
            case SHIFT_RIGHT:
               if (this.function.getLocation() != null) {
                  this.getPlayer().teleport(this.function.getLocation());
               }
               break;
            default:
               PlayerUtil.playSound(this.getPlayer(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP);
               Sign sign = (Sign)block.getState();
               sign.setLine(1, this.function.getCustomName());
               sign.update();
               GuiMenuFunctions.moduleCreative.getPlotManager().parseCoding(plot);
            }
         } else {
            this.getPlayer().closeInventory();
         }

      }
   }
}
