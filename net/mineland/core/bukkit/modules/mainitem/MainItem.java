package net.mineland.core.bukkit.modules.mainitem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import javax.annotation.Nullable;
import net.mineland.api.user.IUser;
import net.mineland.core.bukkit.modules.message.Message;
import net.mineland.core.bukkit.modules.myevents.UserInteractMyEvent;
import net.mineland.core.bukkit.modules.nms.NMS;
import net.mineland.core.bukkit.modules.user.GetterUser;
import net.mineland.core.bukkit.modules.user.User;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import ua.govnojon.libs.bukkitutil.BukkitUtil;
import ua.govnojon.libs.bukkitutil.ColorUtil;
import ua.govnojon.libs.bukkitutil.ItemStackUtil;

public abstract class MainItem implements GetterUser {
   private static ModuleMainItem moduleMainItem = ModuleMainItem.getInstance();
   private ItemStack item;
   private MainBar mainBar;
   private int slot;
   private String textDisplay;
   private String text;
   private boolean translate;
   private boolean hide;
   private long lastClick;
   private long delayClick;
   private ItemFlag[] itemFlags;
   private boolean fixLeftAir;
   private boolean allowInteractWithBlocks;
   private boolean allowInteractWithEntities;

   public MainItem(MainBar mainBar, Material item, String text, int slot) {
      this(mainBar, new ItemStack(item), text, slot);
   }

   public MainItem(MainBar mainBar, ItemStack item, String text, int slot) {
      this.textDisplay = "";
      this.text = "";
      this.hide = true;
      this.lastClick = 0L;
      this.delayClick = (long)moduleMainItem.delayClick;
      this.itemFlags = ItemFlag.values();
      this.fixLeftAir = false;
      this.allowInteractWithBlocks = false;
      this.allowInteractWithEntities = false;
      this.mainBar = mainBar;
      ItemMeta meta = item.getItemMeta();
      meta.removeItemFlags(ItemFlag.values());
      meta.addItemFlags(this.itemFlags);
      item.setItemMeta(meta);
      this.item = NMS.getManager().fixItem(item);
      this.slot = slot;
      this.setText(text, false);
   }

   public MainItem(MainBar mainBar, ItemStack item, int slot) {
      this(mainBar, item, "", slot);
   }

   /** @deprecated */
   @Deprecated
   public MainItem(String key, User user, ItemStack item, String text, int slot) {
      this(moduleMainItem.getMainBar(user), item, text, slot);
   }

   public abstract void click(UserInteractMyEvent var1);

   protected void setText(String text, boolean save) {
      this.text = text;
      int realSlot = this.getRealSlot();
      this.textDisplay = text = this.fixUnicalText(this.translate ? Message.getMessage((IUser)this.getUser(), text) : text);
      ItemStackUtil.setText(this.item, text);
      ItemStackUtil.setMetadata(this.item, "mainitem");
      if (save) {
         this.update(realSlot);
      }

   }

   public ItemFlag[] getItemFlags() {
      return this.itemFlags;
   }

   public void setItemFlags(@Nullable ItemFlag... itemFlags) {
      this.itemFlags = itemFlags == null ? new ItemFlag[0] : itemFlags;
      this.setItem(this.item);
   }

   protected boolean equals(ItemStack item) {
      return BukkitUtil.equalsIgnoreAmount(this.item, item);
   }

   public ItemStack getItem() {
      return this.item;
   }

   public void setItem(ItemStack item) {
      int realSlot = this.getRealSlot();
      ItemMeta meta = item.getItemMeta();
      meta.removeItemFlags(ItemFlag.values());
      meta.addItemFlags(this.itemFlags);
      item.setItemMeta(meta);
      this.item = NMS.getManager().fixItem(item);
      this.setText(this.textDisplay, false);
      this.update(realSlot);
   }

   public MainBar getMainBar() {
      return this.mainBar;
   }

   public void show() {
      this.clearInCursor();
      Inventory inventory = this.mainBar.getUser().getPlayer().getInventory();
      ItemStack old = inventory.getItem(this.slot);
      inventory.setItem(this.slot, this.item);
      if (old != null && !BukkitUtil.equalsIgnoreAmount(old, this.item)) {
         inventory.addItem(new ItemStack[]{old});
      }

      this.hide = false;
   }

   private void clearInCursor() {
      if (this.isInCursor()) {
         this.getPlayer().setItemOnCursor((ItemStack)null);
      }

   }

   public boolean isInCursor() {
      ItemStack cursor = this.getPlayer().getItemOnCursor();
      return !ItemStackUtil.isNullOrAir(cursor) && this.equals(cursor);
   }

   public void update() {
      this.update(this.getRealSlot());
   }

   private void update(int slot) {
      if (slot != -1) {
         this.clearInCursor();
         this.getUser().getPlayer().getInventory().setItem(slot, this.item);
      }

   }

   public int getRealSlot() {
      int i = 0;
      ArrayList<ItemStack> items = new ArrayList();
      PlayerInventory inventory = this.getUser().getPlayer().getInventory();
      items.addAll(Arrays.asList(inventory.getContents()));
      items.addAll(Arrays.asList(inventory.getArmorContents()));
      items.addAll(Arrays.asList(inventory.getExtraContents()));

      for(Iterator var4 = items.iterator(); var4.hasNext(); ++i) {
         ItemStack item = (ItemStack)var4.next();
         if (!ItemStackUtil.isNullOrAir(item) && this.equals(item)) {
            return i;
         }
      }

      return -1;
   }

   public int getSlot() {
      return this.slot;
   }

   public User getUser() {
      return this.mainBar.getUser();
   }

   public void hide() {
      this.clearInCursor();
      int realSlot = this.getRealSlot();
      if (realSlot == -1) {
         ItemStack cursor = this.getUser().getPlayer().getItemOnCursor();
         if (!ItemStackUtil.isNullOrAir(cursor) && this.equals(cursor)) {
            this.getUser().getPlayer().setItemOnCursor((ItemStack)null);
         }
      } else {
         this.getUser().getPlayer().getInventory().clear(realSlot);
      }

      this.hide = true;
   }

   public String getTextDisplay() {
      return this.textDisplay;
   }

   protected long getLastClick() {
      return this.lastClick;
   }

   protected void setLastClick(long lastClick) {
      this.lastClick = lastClick;
   }

   public boolean isHide() {
      return this.hide;
   }

   public void setHide(boolean hide) {
      if (hide) {
         this.hide();
      } else {
         this.show();
      }

   }

   public long getDelayClick() {
      return this.delayClick;
   }

   public void setDelayClick(long delayClick) {
      this.delayClick = delayClick;
   }

   boolean isAllowLeftAir() {
      if (!this.fixLeftAir) {
         return true;
      } else if (System.currentTimeMillis() - this.lastClick > 75L) {
         this.fixLeftAir = false;
         return true;
      } else {
         return false;
      }
   }

   void setFixLeftAir(boolean fixLeftAir) {
      this.fixLeftAir = fixLeftAir;
   }

   private String fixUnicalText(String text) {
      String edit = text;
      int var3 = 0;

      while(true) {
         Iterator var4 = this.mainBar.getItems().iterator();

         MainItem item;
         do {
            if (!var4.hasNext()) {
               return edit;
            }

            item = (MainItem)var4.next();
         } while(this.equals(item) || !item.getTextDisplay().equals(edit));

         edit = text + ColorUtil.getIntegerInColorCode(var3++);
      }
   }

   public boolean isTranslate() {
      return this.translate;
   }

   public MainItem setTranslate(boolean translate) {
      this.translate = translate;
      this.setText(this.text, !this.hide);
      return this;
   }

   public String getText() {
      return this.text;
   }

   public void setText(String key) {
      this.setText(key, true);
   }

   public void onInventoryClickEvent(InventoryClickEvent event) {
   }

   public MainItem selectHotBar() {
      this.getUser().getPlayer().getInventory().setHeldItemSlot(this.slot);
      return this;
   }

   public boolean isAllowInteractWithBlocks() {
      return this.allowInteractWithBlocks;
   }

   public void setAllowInteractWithBlocks(boolean allowInteractWithBlocks) {
      this.allowInteractWithBlocks = allowInteractWithBlocks;
   }

   public boolean isAllowInteractWithEntities() {
      return this.allowInteractWithEntities;
   }

   public void setAllowInteractWithEntities(boolean allowInteractWithEntities) {
      this.allowInteractWithEntities = allowInteractWithEntities;
   }
}
