package net.mineland.core.bukkit.modules.mainitem;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import net.mineland.core.bukkit.module.BukkitModule;
import net.mineland.core.bukkit.modules.myevents.UserInteractMyEvent;
import net.mineland.core.bukkit.modules.user.PlayerChangeLangMyEvent;
import net.mineland.core.bukkit.modules.user.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import ua.govnojon.libs.bukkitutil.ItemStackUtil;
import ua.govnojon.libs.bukkitutil.schedule.Schedule;
import ua.govnojon.libs.config.Config;

public class ModuleMainItem extends BukkitModule {
   private static ModuleMainItem instance;
   protected int delayClick;

   public ModuleMainItem(int priority, Plugin plugin) {
      super("main_item", priority, plugin, new Config(plugin, "gui-items.yml"));
      instance = this;
   }

   public static ModuleMainItem getInstance() {
      return instance;
   }

   public void onFirstEnable() {
      Schedule.timer(() -> {
         Iterator var1 = User.getUsers().iterator();

         while(var1.hasNext()) {
            User user = (User)var1.next();
            MainBar mainBar = this.getMainBar(user);
            Iterator var4 = mainBar.getItems().iterator();

            while(var4.hasNext()) {
               MainItem mainItem = (MainItem)var4.next();
               int realSlot = mainItem.getRealSlot();
               if (!mainItem.isHide() && !mainItem.isInCursor() && realSlot == -1) {
                  mainItem.show();
               }
            }
         }

      }, 2L, 2L, TimeUnit.SECONDS);
   }

   public void onEnable() {
      this.registerListenersThis();
      Config config = this.getConfig();
      config.setIfNotExist("delay-click", 500);
      this.delayClick = config.getInt("delay-click");
   }

   public void onDisable() {
   }

   @EventHandler
   public void onInventoryClickEvent(InventoryClickEvent event) {
      User user = User.getUser((Player)event.getWhoClicked());
      ItemStack cursor = event.getCursor();
      Inventory clicked = event.getClickedInventory();
      if (clicked == null) {
         if (this.isMainItem(cursor)) {
            event.setCancelled(true);
         }

      } else {
         Inventory top = event.getView().getTopInventory();
         Inventory bottom = event.getView().getBottomInventory();
         ClickType click = event.getClick();
         ItemStack current = event.getCurrentItem();
         boolean isCurrent = this.isMainItem(current);
         boolean isCursor = this.isMainItem(cursor);
         boolean isCurrentOrCursor = isCurrent || isCursor;
         if (isCurrentOrCursor && !top.getType().equals(InventoryType.CHEST) && !top.getType().equals(InventoryType.CRAFTING)) {
            event.setCancelled(true);
         } else if (clicked.equals(top)) {
            if (click == ClickType.NUMBER_KEY && this.isMainItem(bottom.getItem(event.getHotbarButton()))) {
               event.setCancelled(true);
            } else if (isCursor) {
               event.setCancelled(true);
            }

         } else if (isCurrent && (click.equals(ClickType.SHIFT_LEFT) || click.equals(ClickType.SHIFT_RIGHT))) {
            event.setCancelled(true);
         } else {
            SlotType slot = event.getSlotType();
            if (isCursor && !slot.equals(SlotType.CONTAINER) && !slot.equals(SlotType.QUICKBAR)) {
               event.setCancelled(true);
            } else {
               if (isCurrentOrCursor) {
                  MainItem mainItem;
                  if (isCurrent) {
                     mainItem = this.getMainItem(user, current);
                  } else {
                     mainItem = this.getMainItem(user, cursor);
                  }

                  if (mainItem != null) {
                     mainItem.onInventoryClickEvent(event);
                  }
               }

            }
         }
      }
   }

   @EventHandler(
      priority = EventPriority.MONITOR
   )
   public void onPlayerChangeLangMyEvent(PlayerChangeLangMyEvent event) {
      MainBar mainBar = this.getMainBar(event.getUser());
      mainBar.getItems().stream().filter(MainItem::isTranslate).forEach((item) -> {
         item.setText(item.getText());
      });
   }

   @EventHandler
   public void onPlayerDropItemEvent(PlayerDropItemEvent event) {
      if (this.isMainItem(event.getItemDrop().getItemStack())) {
         event.setCancelled(true);
      }

   }

   @EventHandler
   public void onDragItem(InventoryDragEvent event) {
      if (this.isMainItem(event.getOldCursor())) {
         event.setCancelled(true);
      }

   }

   @EventHandler(
      priority = EventPriority.LOW
   )
   public void onPlayerQuitEvent(PlayerQuitEvent event) {
      this.getMainBar(event.getPlayer()).hideAll();
   }

   @EventHandler
   public void onPlayerRespawnEvent(PlayerRespawnEvent event) {
      this.getMainBar(event.getPlayer()).showAll();
   }

   @EventHandler
   public void onPlayerDeathEvent(PlayerDeathEvent event) {
      List<ItemStack> remove = new LinkedList();
      Iterator var3 = event.getDrops().iterator();

      while(var3.hasNext()) {
         ItemStack item = (ItemStack)var3.next();
         if (this.isMainItem(item)) {
            remove.add(item);
         }
      }

      event.getDrops().removeAll(remove);
   }

   @EventHandler
   public void on(UserInteractMyEvent event) {
      ItemStack item = event.getItem();
      if (!ItemStackUtil.isNullOrAir(item) && this.isMainItem(item)) {
         User user = User.getUser(event.getPlayer());
         MainItem mainItem = this.getMainItem(user, item);
         if (mainItem != null) {
            if (!event.isCancelled()) {
               if (event.hasEntity() && !mainItem.isAllowInteractWithEntities()) {
                  event.setCancelled(true);
               }

               if (event.hasBlock() && !mainItem.isAllowInteractWithBlocks()) {
                  event.setCancelledBlockUse(true);
                  event.setCancelled(true);
               }
            }

            if (event.getHand().equals(EquipmentSlot.HAND)) {
               long time = System.currentTimeMillis();
               if (time - mainItem.getLastClick() > mainItem.getDelayClick()) {
                  mainItem.click(event);
                  mainItem.setLastClick(time);
               }
            }
         }
      }

   }

   public boolean isMainItem(ItemStack itemStack) {
      return itemStack != null && ItemStackUtil.hasMetadata(itemStack, "mainitem");
   }

   public MainItem getMainItem(User user, ItemStack item) {
      MainBar mainBar = this.getMainBar(user);
      Iterator var4 = mainBar.getItems().iterator();

      MainItem mainItem;
      do {
         if (!var4.hasNext()) {
            return null;
         }

         mainItem = (MainItem)var4.next();
      } while(!mainItem.equals(item));

      return mainItem;
   }

   public MainBar getMainBar(Player player) {
      return this.getMainBar(User.getUser(player));
   }

   public MainBar getMainBar(User user) {
      MainBar mainBar = (MainBar)user.getMetadata("mainbar");
      if (mainBar == null) {
         mainBar = new MainBar(user);
         user.setMetadata("mainbar", mainBar);
      }

      return mainBar;
   }
}
