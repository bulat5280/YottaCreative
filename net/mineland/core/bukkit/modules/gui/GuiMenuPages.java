package net.mineland.core.bukkit.modules.gui;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import net.mineland.core.bukkit.modules.user.User;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryOpenEvent;

public abstract class GuiMenuPages extends GuiMenu {
   private int maxPages = 1;
   private String title;
   private int size;
   private Map<Integer, GuiPage> pages = new HashMap();
   private GuiPage currentPage;

   public GuiMenuPages(String key, User user, int size, String title, int maxPages) {
      super(key, user);
      if (maxPages < 1) {
         throw new IllegalArgumentException("maxPages < 1" + this.maxPages);
      } else {
         this.size = size;
         this.title = title;
         this.maxPages = maxPages;
      }
   }

   public void openOwner() {
      this.checkLoadFirst();
      super.openOwner();
   }

   public void open(Player player) {
      this.checkLoadFirst();
      super.open(player);
   }

   private void checkLoadFirst() {
      if (this.currentPage == null) {
         Integer open = (Integer)this.getUser().removeMetadata("open.page." + this.getKey());
         this.setCurrentPage(this.getPage(open == null ? 0 : Math.min(open, this.maxPages - 1)));
      }

   }

   protected void onInventoryOpenEvent(InventoryOpenEvent event) {
      super.onInventoryOpenEvent(event);
      if (!event.isCancelled()) {
         this.getUser().setMetadata("open.page." + this.getKey(), this.getCurrentPage().getPage());
      }

   }

   public boolean isOpened() {
      return this.currentPage != null && this.getPlayer().getOpenInventory().getTopInventory().equals(this.currentPage.getGuiMenu().getInventory());
   }

   public GuiPage getPage(int page) {
      if (page >= 0 && page < this.maxPages) {
         GuiPage guiPage = (GuiPage)this.pages.get(page);
         if (guiPage != null) {
            return guiPage;
         } else {
            this.pages.put(page, guiPage = new GuiPage(this, page));
            if (this.currentPage == null) {
               this.currentPage = guiPage;
            }

            guiPage.change(() -> {
               this.onLoadPage(guiPage);
            });
            return guiPage;
         }
      } else {
         throw new IllegalArgumentException("page=" + page + " < 1 OR page > maxPages=" + this.maxPages);
      }
   }

   public void openPage(int openPage) {
      this.openPage(this.getPage(openPage));
   }

   public void openPage(GuiPage page) {
      this.setCurrentPage(page);
      this.openOwner();
   }

   public int getMaxPages() {
      return this.maxPages;
   }

   public void setMaxPages(int newSize) {
      if (newSize < 1) {
         throw new IllegalArgumentException("size < 1");
      } else if (this.maxPages != newSize) {
         if (this.maxPages > newSize) {
            List var10000 = (List)this.pages.keySet().stream().filter((i) -> {
               return i >= newSize;
            }).collect(Collectors.toList());
            Map var10001 = this.pages;
            var10000.forEach(var10001::remove);
            this.setCurrentPage(this.getPage(newSize - 1));
         }

         this.maxPages = newSize;
         this.pages.values().forEach((guiPage) -> {
            guiPage.updateNextBack();
            guiPage.updateTitle();
         });
      }
   }

   public GuiPage getCurrentPage() {
      return this.currentPage;
   }

   void setCurrentPage(GuiPage page) {
      this.currentPage = page;
      this.currentPage.use();
   }

   public abstract void onLoadPage(GuiPage var1);

   public String getTitle() {
      return this.title;
   }

   int getDefSize() {
      return this.size;
   }

   public Collection<GuiPage> getLoadPages() {
      return this.pages.values();
   }
}
