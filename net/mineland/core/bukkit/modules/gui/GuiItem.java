package net.mineland.core.bukkit.modules.gui;

import java.util.Iterator;
import javax.annotation.Nullable;
import net.mineland.api.user.IUser;
import net.mineland.core.bukkit.modules.message.Message;
import net.mineland.core.bukkit.modules.nms.NMS;
import net.mineland.core.bukkit.modules.user.User;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ua.govnojon.libs.bukkitutil.BukkitUtil;
import ua.govnojon.libs.bukkitutil.ColorUtil;
import ua.govnojon.libs.bukkitutil.ItemData;
import ua.govnojon.libs.bukkitutil.ItemStackUtil;

public abstract class GuiItem {
   protected String text;
   private int x;
   private int y;
   private int pos;
   private ItemStack item;
   private GuiMenu guiMenu;
   private boolean enchantEffect;
   private ItemFlag[] itemFlags;

   public GuiItem(GuiMenu guiMenu, int x, int y, ItemStack item) {
      this(guiMenu, x, y, item, "");
   }

   public GuiItem(GuiMenu guiMenu, int x, int y, Material material, short data, int amount) {
      this(guiMenu, x, y, new ItemStack(material, amount, data));
   }

   public GuiItem(GuiMenu guiMenu, int x, int y, Material material) {
      this(guiMenu, x, y, new ItemStack(material));
   }

   public GuiItem(GuiMenu guiMenu, int x, int y, ItemData itemData) {
      this(guiMenu, x, y, itemData.toItemStack());
   }

   public GuiItem(GuiMenu guiMenu, int x, int y, ItemStack item, String text) {
      this.text = "";
      this.enchantEffect = false;
      this.itemFlags = ItemFlag.values();
      this.text = text;
      this.guiMenu = guiMenu;
      this.set(x, y, item);
   }

   public GuiItem(GuiMenu guiMenu, int pos, ItemStack item, String text) {
      this.text = "";
      this.enchantEffect = false;
      this.itemFlags = ItemFlag.values();
      this.guiMenu = guiMenu;
      this.text = text;
      this.set(pos, item);
   }

   public GuiItem(GuiMenu guiMenu, int pos, ItemStack item) {
      this(guiMenu, pos, item, "");
   }

   public GuiItem(GuiMenu guiMenu, int pos, Material material) {
      this(guiMenu, pos, new ItemStack(material));
   }

   public GuiItem(GuiMenu guiMenu, int pos, ItemData itemData) {
      this(guiMenu, pos, itemData.toItemStack());
   }

   public GuiItem(GuiMenu guiMenu) {
      this.text = "";
      this.enchantEffect = false;
      this.itemFlags = ItemFlag.values();
      this.guiMenu = guiMenu;
   }

   public void set(int x, int y, ItemStack item) {
      if (x >= 1 && y >= 1 && x <= this.guiMenu.getWidth() && y <= this.guiMenu.getHeight()) {
         this.x = x;
         this.y = y;
         this.pos = this.getPos(x, y);
         ItemMeta meta = item.getItemMeta();
         meta.removeItemFlags(ItemFlag.values());
         meta.addItemFlags(this.itemFlags);
         item.setItemMeta(meta);
         this.item = NMS.getManager().fixItem(item);
         this.setText(this.text);
         this.setEnchantEffect(this.enchantEffect);
      } else {
         throw new IndexOutOfBoundsException("x = " + x + " должен быть от 1 до " + this.guiMenu.getWidth() + ", y = " + y + " должен быть от 1 до " + this.guiMenu.getHeight());
      }
   }

   public void set(int pos, ItemStack item) {
      int[] xy = this.getPos(pos);
      this.set(xy[0], xy[1], item);
   }

   public boolean equals(ItemStack item) {
      return BukkitUtil.equalsIgnoreAmount(this.item, item);
   }

   public void hide() {
      if (this.equals(this.guiMenu.getInventory().getItem(this.pos))) {
         this.guiMenu.getInventory().clear(this.pos);
      }

   }

   public void show() {
      this.update();
   }

   public abstract void click(InventoryClickEvent var1);

   public void setPos(int x, int y) {
      if (this.x != x || this.y != y) {
         this.hide();
         this.pos = this.getPos(x, y);
         this.x = x;
         this.y = y;
         this.update();
      }
   }

   private int getPos(int x, int y) {
      --x;
      --y;
      return y * this.guiMenu.getWidth() + x;
   }

   private int[] getPos(int pos) {
      int y = pos / this.guiMenu.getWidth();
      int x = pos - y * this.guiMenu.getWidth();
      ++y;
      ++x;
      return new int[]{x, y};
   }

   public void setAmount(int amount) {
      if (amount > 0 && amount < 65) {
         this.item.setAmount(amount);
         this.update();
      }

   }

   public void setText(String key, String... replaced) {
      this.setText(Message.getMessage((IUser)this.getUser(), key, replaced));
   }

   protected String makeUniqueText(String text) {
      String edit = text;
      int var3 = 0;

      while(true) {
         Iterator var4 = this.guiMenu.getItems().iterator();

         GuiItem item;
         do {
            if (!var4.hasNext()) {
               return edit;
            }

            item = (GuiItem)var4.next();
         } while(edit == null || item.equals(this) || !edit.equals(item.getText()));

         edit = text + ColorUtil.getIntegerInColorCode(var3++);
      }
   }

   public String getText() {
      return this.text;
   }

   public GuiItem setText(String text) {
      this.text = text = this.makeUniqueText(text);
      ItemStackUtil.setText(this.item, text);
      this.update();
      return this;
   }

   public void setEnchantEffect(boolean enchantEffect) {
      this.enchantEffect = enchantEffect;
      ItemMeta meta = this.item.getItemMeta();
      if (meta != null) {
         if (enchantEffect) {
            meta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
         } else {
            meta.getEnchants().keySet().forEach(meta::removeEnchant);
         }

         this.item.setItemMeta(meta);
      }

      this.update();
   }

   public boolean hasEffectEnchant() {
      return this.enchantEffect;
   }

   public void update() {
      this.guiMenu.getInventory().setItem(this.pos, this.item);
   }

   public ItemStack getItem() {
      return this.item;
   }

   public void setItem(ItemStack item) {
      this.set(this.x, this.y, item);
   }

   public <T extends GuiMenu> T getGuiMenu() {
      return this.guiMenu;
   }

   public int getX() {
      return this.x;
   }

   public int getY() {
      return this.y;
   }

   public int getPos() {
      return this.pos;
   }

   public void setPos(int pos) {
      int[] xy = this.getPos(pos);
      this.setPos(xy[0], xy[1]);
   }

   public Player getPlayer() {
      return this.guiMenu.getPlayer();
   }

   public User getUser() {
      return this.guiMenu.getUser();
   }

   public void setTextKey(String key, String... replaced) {
      this.setText(Message.getMessage((IUser)this.getUser(), key, replaced));
   }

   public ItemFlag[] getItemFlags() {
      return this.itemFlags;
   }

   public void setItemFlags(@Nullable ItemFlag... itemFlags) {
      this.itemFlags = itemFlags == null ? new ItemFlag[0] : itemFlags;
      this.setItem(this.item);
   }
}
