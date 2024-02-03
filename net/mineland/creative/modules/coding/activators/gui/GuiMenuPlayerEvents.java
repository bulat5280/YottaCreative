package net.mineland.creative.modules.coding.activators.gui;

import net.mineland.api.user.IUser;
import net.mineland.core.bukkit.modules.gui.GuiItem;
import net.mineland.core.bukkit.modules.gui.GuiMenu;
import net.mineland.core.bukkit.modules.message.Message;
import net.mineland.core.bukkit.modules.user.User;
import net.mineland.creative.modules.coding.activators.ActivatorType;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.inventory.InventoryClickEvent;
import ua.govnojon.libs.bukkitutil.PlayerUtil;

public class GuiMenuPlayerEvents extends GuiMenu {
   public GuiMenuPlayerEvents(String key, User user) {
      super(key, user, 4, Message.getMessage((IUser)user, "coding.gui.player_events.title"));
      this.init();
   }

   public void init() {
      int pos = 0;
      ActivatorType[] var2 = ActivatorType.values();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         ActivatorType activatorType = var2[var4];
         if (activatorType.name().startsWith("PLAYER")) {
            this.addItem(new GuiMenuPlayerEvents.GuiItemPlayerActivator(this, pos, activatorType));
            ++pos;
         }
      }

   }

   public class GuiItemPlayerActivator extends GuiItem {
      ActivatorType activatorType;

      GuiItemPlayerActivator(GuiMenu guiMenu, int pos, ActivatorType activatorType) {
         super(guiMenu, pos, activatorType.getIcon().toItemStack(), Message.getMessage((IUser)guiMenu.getUser(), "coding.gui.activator." + activatorType.name()));
         this.activatorType = activatorType;
      }

      public void click(InventoryClickEvent event) {
         Block block = (Block)this.getUser().removeMetadata("coding.selected_sign");
         if (block != null) {
            PlayerUtil.playSound(this.getPlayer(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP);
            this.getPlayer().closeInventory();
            if (block.getType() == Material.WALL_SIGN) {
               Sign sign = (Sign)block.getState();
               sign.setLine(1, "lang:coding.sign." + this.activatorType.name());
               sign.update();
            }
         }

      }
   }
}
