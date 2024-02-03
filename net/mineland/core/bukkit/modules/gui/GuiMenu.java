package net.mineland.core.bukkit.modules.gui;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.mineland.core.bukkit.module.Module;
import net.mineland.core.bukkit.modules.nms.NMS;
import net.mineland.core.bukkit.modules.user.User;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ua.govnojon.libs.bukkitutil.InventoryUtil;
import ua.govnojon.libs.bukkitutil.ItemData;

public abstract class GuiMenu {
   protected static ModuleGui moduleGui = (ModuleGui)Module.getInstance(ModuleGui.class);
   private String key;
   private User player;
   private Inventory inventory;
   private Set<GuiItem> items = new HashSet();
   private boolean defaultCancelled = true;
   private boolean unloadOnClose = false;

   public GuiMenu(String key, User user, int size, String title) {
      this.key = key.toLowerCase();
      this.player = user;
      this.inventory = Bukkit.createInventory((InventoryHolder)null, size * 9, title.length() > 32 ? title.substring(0, 32) : title);
   }

   GuiMenu(String key, User player) {
      this.key = key;
      this.player = player;
   }

   public GuiMenu(User user, int size, String title) {
      this.key = this.getClass().getSimpleName().toLowerCase();
      this.player = user;
      this.inventory = Bukkit.createInventory((InventoryHolder)null, size * 9, title.length() > 32 ? title.substring(0, 32) : title);
   }

   public GuiMenu(String key, User user, InventoryType type, String title) {
      this.key = key.toLowerCase();
      this.player = user;
      this.inventory = Bukkit.createInventory((InventoryHolder)null, type, title);
   }

   public static int getPos(int size, int width, int x, int y) {
      --y;
      --x;
      int pos = y * width + x;
      if (pos < 0) {
         pos = 0;
      } else if (pos >= size) {
         pos = size - 1;
      }

      return pos;
   }

   public static int[] getPos(int pos, int width) {
      int y = pos / width + 1;
      int x = pos - (y - 1) * width + 1;
      return new int[]{x, y};
   }

   public void setTitle(String newTitle) {
      NMS.getManager().setInventoryTitle(this.inventory, newTitle);
   }

   public void open(Player player) {
      player.openInventory(this.inventory);
   }

   public void open(User user) {
      this.open(user.getPlayer());
   }

   public void openOwner() {
      if (!this.isOpened()) {
         this.player.getPlayer().openInventory(this.inventory);
      }

   }

   public boolean equals(Inventory inventory) {
      return inventory != null && Objects.equals(inventory, this.inventory);
   }

   public void onInventoryClickEvent(InventoryClickEvent event) {
      InventoryAction action = event.getAction();
      if (Objects.equals(event.getClickedInventory(), this.getInventory()) || !action.equals(InventoryAction.PICKUP_ALL) && !action.equals(InventoryAction.PLACE_ALL)) {
         event.setCancelled(this.defaultCancelled);
      }

      ItemStack item = event.getCurrentItem();
      if (item != null && event.getClick() != ClickType.DOUBLE_CLICK) {
         Iterator var4 = this.items.iterator();

         while(var4.hasNext()) {
            GuiItem guiItem = (GuiItem)var4.next();
            if (guiItem.equals(item)) {
               guiItem.click(event);
               break;
            }
         }

      }
   }

   protected void setUnloadOnClose() {
      this.unloadOnClose = true;
   }

   boolean isUnloadOnClose() {
      return this.unloadOnClose;
   }

   public <T extends GuiItem> T addItem(T item) {
      this.items.add(item);
      item.update();
      return item;
   }

   public GuiItem addItem(int pos, ItemStack icon, String text, final RunnableClickEvent runnableClickEvent) {
      GuiItem guiItem = new GuiItem(this, pos, icon, text) {
         public void click(InventoryClickEvent event) {
            runnableClickEvent.click(event);
         }
      };
      this.addItem(guiItem);
      return guiItem;
   }

   public GuiItem addItem(int pos, ItemData itemData, String text, RunnableClickEvent runnableClickEvent) {
      return this.addItem(pos, itemData.toItemStack(), text, runnableClickEvent);
   }

   public GuiItem addItem(int pos, ItemStack icon, final RunnableClickEvent runnableClickEvent) {
      GuiItem guiItem = new GuiItem(this, pos, icon) {
         public void click(InventoryClickEvent event) {
            runnableClickEvent.click(event);
         }
      };
      this.addItem(guiItem);
      return guiItem;
   }

   public GuiItem addItem(int x, int y, ItemStack icon, final RunnableClickEvent runnableClickEvent) {
      GuiItem guiItem = new GuiItem(this, x, y, icon) {
         public void click(InventoryClickEvent event) {
            runnableClickEvent.click(event);
         }
      };
      this.addItem(guiItem);
      return guiItem;
   }

   public GuiItem addItem(int x, int y, Material icon, RunnableClickEvent runnableClickEvent) {
      return this.addItem(x, y, new ItemStack(icon), runnableClickEvent);
   }

   public GuiItem addItem(int x, int y, ItemStack icon, String text, final RunnableClickEvent runnableClickEvent) {
      GuiItem guiItem = new GuiItem(this, x, y, icon, text) {
         public void click(InventoryClickEvent event) {
            runnableClickEvent.click(event);
         }
      };
      this.addItem(guiItem);
      return guiItem;
   }

   public void removeItem(GuiItem item) {
      item.hide();
      this.items.remove(item);
   }

   public Collection<GuiItem> getItems() {
      return this.items;
   }

   void setItems(Set<GuiItem> items) {
      this.items = items;
   }

   public Inventory getInventory() {
      return this.inventory;
   }

   void setInventory(Inventory inventory) {
      this.inventory = inventory;
   }

   public int getSize() {
      return this.inventory.getSize();
   }

   public void setSize(int newSize) {
      NMS.getManager().setInventorySize(this.inventory, newSize);
   }

   private int getPos(int x, int y, int width) {
      return getPos(this.getSize(), width, x, y);
   }

   public boolean isOpened() {
      return this.player.getPlayer().getOpenInventory().getTopInventory().equals(this.getInventory());
   }

   protected void fillInventory(ItemStack item) {
      ItemMeta meta = item.getItemMeta();
      meta.setDisplayName("§a");
      item.setItemMeta(meta);
      Inventory inv = this.getInventory();

      for(int i = 0; i < inv.getSize(); ++i) {
         if (inv.getItem(i) == null) {
            inv.setItem(i, item.clone());
         }
      }

   }

   public String getKey() {
      return this.key;
   }

   public Player getPlayer() {
      return this.player.getPlayer();
   }

   public User getUser() {
      return this.player;
   }

   protected void setDefaultCancelled(boolean defaultCancelled) {
      this.defaultCancelled = defaultCancelled;
   }

   protected void onInventoryOpenEvent(InventoryOpenEvent event) {
   }

   protected void onInventoryCloseEvent(InventoryCloseEvent event) {
   }

   public GuiItem getItemByPos(int x, int y) {
      Iterator var3 = this.items.iterator();

      GuiItem item;
      do {
         if (!var3.hasNext()) {
            return null;
         }

         item = (GuiItem)var3.next();
      } while(item.getX() != x || item.getY() != y);

      return item;
   }

   public void reload() {
      moduleGui.reloadGui(this.player, this.key);
   }

   public <T extends GuiItem> List<T> getItemsByClass(Class<? extends T> guiItemClass) {
      Stream var10000 = this.items.stream();
      guiItemClass.getClass();
      var10000 = var10000.filter(guiItemClass::isInstance);
      guiItemClass.getClass();
      return (List)var10000.map(guiItemClass::cast).collect(Collectors.toList());
   }

   public <T extends GuiItem> Collection<T> removeItemsAll(Collection<T> items) {
      items.forEach(GuiItem::hide);
      this.items.removeAll(items);
      return items;
   }

   public GuiItem getGuiItemByItem(ItemStack item) {
      return (GuiItem)this.items.stream().filter((guiItem) -> {
         return guiItem.equals(item);
      }).findFirst().orElse((Object)null);
   }

   public int getWidth() {
      return InventoryUtil.getWidth(this.inventory.getType());
   }

   public int getHeight() {
      return this.getSize() / this.getWidth();
   }

   public void reopen() {
      this.getPlayer().closeInventory();
      moduleGui.openGui(this.getUser(), this.getKey());
   }

   public void clear() {
      this.getInventory().clear();
      this.getItems().clear();
   }
}
