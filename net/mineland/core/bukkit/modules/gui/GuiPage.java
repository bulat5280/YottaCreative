package net.mineland.core.bukkit.modules.gui;

import java.util.HashSet;
import java.util.Set;
import net.mineland.api.user.IUser;
import net.mineland.core.bukkit.modules.locale.PacketAdapterLocale;
import net.mineland.core.bukkit.modules.message.Message;
import net.mineland.core.bukkit.modules.user.User;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class GuiPage {
   private GuiMenuPages menu;
   private int page;
   private GuiItemOpenPage next;
   private GuiItemOpenPage back;
   private Inventory inventory;
   private Set<GuiItem> items = new HashSet();

   GuiPage(GuiMenuPages menu, int page) {
      this.menu = menu;
      this.page = page;
      this.inventory = Bukkit.createInventory((InventoryHolder)null, menu.getDefSize() * 9, getTitle(menu.getTitle(), page, menu.getMaxPages(), menu.getUser()));
      this.updateNextBack();
   }

   private static String getTitle(String title, int page, int maxPages, User user) {
      title = PacketAdapterLocale.localizeString(user.getLang(), title);
      return title + (maxPages > 1 ? Message.getMessage((IUser)user, "гуи_страница", "%size%", String.valueOf(maxPages), "%page%", String.valueOf(page + 1)) : "");
   }

   public GuiPage use() {
      GuiPage current = this.menu.getCurrentPage();
      this.menu.setInventory(this.inventory);
      this.menu.setItems(this.items);
      return current;
   }

   public void change(Runnable runnable) {
      GuiPage use = this.use();

      try {
         runnable.run();
      } catch (Exception var4) {
         var4.printStackTrace();
      }

      if (use != null) {
         use.use();
      }

   }

   public void updateNextBack() {
      if (this.page < this.menu.getMaxPages() - 1 && this.next == null) {
         this.change(() -> {
            this.next = (GuiItemOpenPage)this.menu.addItem(new GuiItemOpenPage(this, this.page + 1, 6));
         });
      }

      if (this.page > 0 && this.back == null) {
         this.change(() -> {
            this.back = (GuiItemOpenPage)this.menu.addItem(new GuiItemOpenPage(this, this.page - 1, 4));
         });
      }

   }

   public boolean isUse() {
      return this.menu.getInventory().equals(this.inventory);
   }

   public boolean isOpened() {
      return this.isUse() && this.menu.isOpened();
   }

   public Set<GuiItem> getItems() {
      return this.items;
   }

   public void updateTitle() {
      this.change(() -> {
         this.menu.setTitle(getTitle(this.menu.getTitle(), this.page, this.menu.getMaxPages(), this.menu.getUser()));
      });
   }

   public GuiMenuPages getGuiMenu() {
      return this.menu;
   }

   public int getPage() {
      return this.page;
   }

   public void open() {
      this.menu.openPage(this);
   }
}
