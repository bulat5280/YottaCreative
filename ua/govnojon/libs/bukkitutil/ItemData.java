package ua.govnojon.libs.bukkitutil;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import ua.govnojon.libs.config.Config;

public class ItemData {
   private Material type;
   private int data;
   private ItemStack original;

   public ItemData(Material type, int data) {
      this.type = type;
      this.data = data;
   }

   public ItemData(ItemStack itemStack) {
      this((Material)itemStack.getType(), itemStack.getData().getData());
      this.original = itemStack;
   }

   public ItemData(Material type) {
      this((Material)type, 0);
   }

   public ItemData(int id, int data) {
      this(Material.getMaterial(id), data);
   }

   public ItemData(int id) {
      this((Material)Material.getMaterial(id), 0);
   }

   public ItemData(String name, int data) {
      this(Material.getMaterial(name), data);
   }

   public ItemData(String name) {
      this((Material)Material.getMaterial(name), 0);
   }

   public static ItemData byString(String str) {
      return Config.toItemData(str);
   }

   public Material getType() {
      return this.type;
   }

   public void setType(Material type) {
      this.type = type;
   }

   public int getData() {
      return this.data;
   }

   public void setData(int data) {
      this.data = data;
   }

   public String toString() {
      return this.type.name() + ":" + this.data;
   }

   public ItemStack toItemStack() {
      return new ItemStack(this.type, 1, (short)Math.max(0, this.data));
   }

   public ItemStack toItemStack(int amount) {
      return new ItemStack(this.type, amount, (short)Math.max(0, this.data));
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         ItemData itemData = (ItemData)o;
         return this.data == itemData.data && this.type == itemData.type;
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.type.ordinal() << 8 ^ (byte)this.getData();
   }

   public ItemData clone() {
      try {
         return (ItemData)super.clone();
      } catch (CloneNotSupportedException var2) {
         throw new Error(var2);
      }
   }

   public boolean isTrueStack(ItemStack stack) {
      return stack.getType().equals(this.getType()) && (this.getData() == -1 || this.getData() == stack.getData().getData());
   }

   public boolean isTrueBlock(Block block) {
      return block.getType().equals(this.getType()) && (this.getData() == -1 || this.getData() == block.getData());
   }

   public boolean isBlock() {
      return this.type.isBlock();
   }

   public ItemStack getOriginal() {
      return this.original;
   }
}
