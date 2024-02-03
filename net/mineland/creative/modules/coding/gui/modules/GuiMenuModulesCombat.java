package net.mineland.creative.modules.coding.gui.modules;

import net.mineland.api.user.IUser;
import net.mineland.core.bukkit.modules.gui.GuiMenu;
import net.mineland.core.bukkit.modules.message.Message;
import net.mineland.core.bukkit.modules.user.User;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class GuiMenuModulesCombat extends GuiMenu {
   public GuiMenuModulesCombat(String key, User user) {
      super(key, user, 6, Message.getMessage((IUser)user, "creative.modules.combat"));
      this.init();
   }

   private void init() {
      this.addItem(3, 2, new ItemStack(Material.STAINED_GLASS_PANE), Message.getMessage((IUser)this.getUser(), "creative.модули_не_работают"), (event) -> {
         event.setCancelled(true);
      });
   }
}
