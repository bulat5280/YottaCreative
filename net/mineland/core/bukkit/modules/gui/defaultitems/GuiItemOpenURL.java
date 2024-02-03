package net.mineland.core.bukkit.modules.gui.defaultitems;

import net.mineland.core.bukkit.module.Module;
import net.mineland.core.bukkit.modules.confirm.ModuleConfirm;
import net.mineland.core.bukkit.modules.gui.GuiMenu;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class GuiItemOpenURL extends GuiItemText {
   private static ModuleConfirm moduleConfirm = (ModuleConfirm)Module.getInstance(ModuleConfirm.class);
   private String url;

   public GuiItemOpenURL(GuiMenu guiMenu, int x, int y, Material material, String url, String key, String... replaced) {
      super(guiMenu, x, y, material, key, replaced);
      this.url = url;
   }

   public GuiItemOpenURL(GuiMenu guiMenu, int x, int y, ItemStack itemStack, String url, String key, String... replaced) {
      super(guiMenu, x, y, itemStack, key, replaced);
      this.url = url;
   }

   public GuiItemOpenURL(GuiMenu guiMenu, int x, int y, ItemStack itemStack, String url) {
      super(guiMenu, x, y, itemStack);
      this.url = url;
   }

   public GuiItemOpenURL(GuiMenu guiMenu, int x, int y, Material material, String url) {
      super(guiMenu, x, y, material);
      this.url = url;
   }

   public GuiItemOpenURL(GuiMenu guiMenu, String url, String key, String... replaced) {
      super(guiMenu, 5, guiMenu.getSize() / 9, (ItemStack)(new ItemStack(Material.SIGN)), key, replaced);
      this.url = url;
   }

   public void click(InventoryClickEvent event) {
      moduleConfirm.openURL(this.getUser(), this.url, () -> {
         this.getGuiMenu().openOwner();
      });
   }
}
