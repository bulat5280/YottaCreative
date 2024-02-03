package net.mineland.core.bukkit.modules.mainitem.defaultmainitems;

import net.mineland.core.bukkit.LibsPlugin;
import net.mineland.core.bukkit.modules.gui.GuiMenu;
import net.mineland.core.bukkit.modules.gui.ModuleGui;
import net.mineland.core.bukkit.modules.mainitem.MainBar;
import net.mineland.core.bukkit.modules.mainitem.MainItem;
import net.mineland.core.bukkit.modules.myevents.UserInteractMyEvent;
import net.mineland.core.bukkit.modules.user.User;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import ua.govnojon.libs.bukkitutil.ItemData;

public class MainItemOpenGui extends MainItem {
   private static ModuleGui moduleGui = LibsPlugin.getModuleGui();
   private String keyGui;

   public MainItemOpenGui(MainBar mainBar, ItemData item, String text, int slot, Class<? extends GuiMenu> clazz) {
      super(mainBar, item.toItemStack(), text, slot);
      this.keyGui = clazz.getSimpleName().toLowerCase();
   }

   public MainItemOpenGui(MainBar mainBar, ItemStack item, String text, int slot, String keyGui) {
      super(mainBar, item, text, slot);
      this.keyGui = keyGui;
   }

   public MainItemOpenGui(MainBar mainBar, Material material, String text, int slot, String keyGui) {
      super(mainBar, new ItemStack(material), text, slot);
      this.keyGui = keyGui;
   }

   /** @deprecated */
   @Deprecated
   public MainItemOpenGui(String key, User player, ItemStack item, String text, int slot, String keyGui) {
      super(key, player, item, text, slot);
      this.keyGui = keyGui;
   }

   /** @deprecated */
   @Deprecated
   public MainItemOpenGui(User player, ItemStack item, String text, int slot, String keyGui) {
      super("", player, item, text, slot);
      this.keyGui = keyGui;
   }

   public static ModuleGui getModuleGui() {
      return moduleGui;
   }

   public String getKeyGui() {
      return this.keyGui;
   }

   public void click(UserInteractMyEvent event) {
      GuiMenu menu = moduleGui.getGuiPlayer(this.getUser(), this.keyGui);
      if (menu != null) {
         menu.openOwner();
      }

      event.setCancelled(true);
   }
}
