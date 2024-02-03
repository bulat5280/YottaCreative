package net.mineland.core.bukkit.modules.mainitem;

import java.util.LinkedList;
import java.util.List;
import net.mineland.core.bukkit.modules.myevents.UserInteractMyEvent;
import net.mineland.core.bukkit.modules.user.User;
import org.bukkit.Material;

public class MainBar {
   private User user;
   private List<MainItem> items = new LinkedList();

   public MainBar(User user) {
      this.user = user;
   }

   public User getUser() {
      return this.user;
   }

   public MainItem addItem(MainItem mainItem) {
      this.items.add(mainItem);
      mainItem.show();
      return mainItem;
   }

   public void removeMainItem(MainItem mainItem) {
      if (mainItem != null) {
         mainItem.hide();
         this.items.remove(mainItem);
      }

   }

   public List<MainItem> getItems() {
      return this.items;
   }

   public void showAll() {
      this.items.forEach(MainItem::show);
   }

   public void hideAll() {
      this.items.forEach(MainItem::hide);
   }

   public void clear() {
      this.hideAll();
      this.items.clear();
   }

   public void removeMainItemAll(List<MainItem> mainItem) {
      mainItem.forEach(MainItem::hide);
      this.items.removeAll(mainItem);
   }

   public MainItem addItem(Material item, String text, int slot, final RunnableClickEvent clickEvent) {
      return this.addItem(new MainItem(this, item, text, slot) {
         public void click(UserInteractMyEvent event) {
            clickEvent.click(event);
         }
      });
   }
}
