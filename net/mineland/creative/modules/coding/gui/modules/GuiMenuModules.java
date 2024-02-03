package net.mineland.creative.modules.coding.gui.modules;

import net.mineland.api.user.IUser;
import net.mineland.core.bukkit.modules.gui.GuiMenu;
import net.mineland.core.bukkit.modules.message.Message;
import net.mineland.core.bukkit.modules.user.User;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class GuiMenuModules extends GuiMenu {
   public GuiMenuModules(String key, User user) {
      super("modules", user, 6, Message.getMessage((IUser)user, "creative.gui.modules"));
      this.setUnloadOnClose();
      this.init();
   }

   private void init() {
      this.addItem(3, 3, new ItemStack(Material.IRON_SWORD), Message.getMessage((IUser)this.getUser(), "creative.gui.modules.combat"), (event) -> {
         moduleGui.openGui(this.getUser(), GuiMenuModulesCombat.class);
      });
   }
}
