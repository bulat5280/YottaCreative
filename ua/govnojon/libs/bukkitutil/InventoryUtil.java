package ua.govnojon.libs.bukkitutil;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import net.mineland.core.bukkit.modules.nms.NMS;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import ua.govnojon.libs.config.Config;

public class InventoryUtil {
   public static void setContents(Inventory inventory, ItemStack[] contents) {
      if (inventory.getSize() < contents.length) {
         contents = (ItemStack[])Arrays.copyOf(contents, inventory.getSize());
      }

      inventory.setContents(contents);
   }

   public static void setName(Inventory inventory, String name) {
      NMS.getManager().setInventoryTitle(inventory, name);
   }

   public static boolean isContainsInInventory(Inventory inventory, ItemStack item, int amount) {
      return getAmount(inventory, item) >= amount;
   }

   public static boolean isContainsInInventory(Inventory inventory, ItemData itemData, int amount) {
      return getAmount(inventory, itemData) >= amount;
   }

   public static int getAmount(Inventory inventory, ItemStack item) {
      ItemStack[] items = inventory.getContents();
      int search = 0;
      ItemStack[] var4 = items;
      int var5 = items.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         ItemStack i = var4[var6];
         if (i != null && BukkitUtil.equalsIgnoreAmount(i, item)) {
            search += i.getAmount();
         }
      }

      return search;
   }

   public static int getAmount(Inventory inventory, ItemData itemData) {
      ItemStack[] items = inventory.getContents();
      int search = 0;
      ItemStack[] var4 = items;
      int var5 = items.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         ItemStack i = var4[var6];
         if (i != null) {
            ItemData d = new ItemData(i);
            if (d.equals(itemData)) {
               search += i.getAmount();
            }
         }
      }

      return search;
   }

   public static List<ItemStack> deserialize(String data) {
      Config config = new Config(data);
      return config.getItemStackList("items");
   }

   public static String serialize(List<ItemStack> items) {
      Config config = new Config("");
      config.set("items", items);
      return config.saveToString();
   }

   public static List<ItemStack> removeItems(Player player, ItemStack object, int amount) {
      return removeItems((Inventory)player.getInventory(), (ItemStack)object, amount);
   }

   public static List<ItemStack> removeItems(Inventory inventory, ItemStack object, int amount) {
      List<ItemStack> removed = new LinkedList();
      ItemStack[] items = inventory.getContents();

      for(int i = 0; i < items.length; ++i) {
         ItemStack item = items[i];
         if (BukkitUtil.equalsIgnoreAmount(item, object)) {
            int am = item.getAmount();
            if (amount <= am) {
               if (amount < am) {
                  item.setAmount(am - amount);
                  ItemStack old = inventory.getItem(i).clone();
                  old.setAmount(amount);
                  removed.add(old);
                  inventory.setItem(i, item);
                  return removed;
               }

               removed.add(inventory.getItem(i));
               inventory.clear(i);
               return removed;
            }

            amount -= am;
            removed.add(item);
            inventory.clear(i);
         }
      }

      return removed;
   }

   public static List<ItemStack> removeItems(Inventory inventory, ItemData itemData, int amount) {
      List<ItemStack> removed = new LinkedList();
      ItemStack[] items = inventory.getContents();

      for(int i = 0; i < items.length; ++i) {
         ItemStack item = items[i];
         if (!ItemStackUtil.isNullOrAir(item) && (new ItemData(item)).equals(itemData)) {
            int am = item.getAmount();
            if (amount <= am) {
               if (amount < am) {
                  item.setAmount(am - amount);
                  ItemStack old = inventory.getItem(i).clone();
                  old.setAmount(amount);
                  removed.add(old);
                  inventory.setItem(i, item);
                  return removed;
               }

               removed.add(inventory.getItem(i));
               inventory.clear(i);
               return removed;
            }

            amount -= am;
            removed.add(item);
            inventory.clear(i);
         }
      }

      return removed;
   }

   public static void giveItems(Inventory inventory, ItemStack object, int amount) {
      ItemStack item;
      for(boolean next = true; next; inventory.addItem(new ItemStack[]{item})) {
         item = object.clone();
         if (amount >= item.getMaxStackSize()) {
            item.setAmount(item.getMaxStackSize());
            amount -= item.getMaxStackSize();
         } else {
            item.setAmount(amount);
            next = false;
         }
      }

   }

   public static boolean isFull(Inventory inv) {
      ItemStack[] var1 = inv.getContents();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         ItemStack item = var1[var3];
         if (item == null) {
            return false;
         }

         if (item.getAmount() < item.getMaxStackSize()) {
            return false;
         }
      }

      return true;
   }

   public static boolean isFull(Inventory inv, ItemStack object) {
      Material material = object.getType();
      short data = object.getDurability();
      ItemStack[] var4 = inv.getContents();
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         ItemStack item = var4[var6];
         if (item == null) {
            return false;
         }

         if (item.getType().equals(material) && item.getDurability() == data && item.getAmount() < item.getMaxStackSize()) {
            return false;
         }
      }

      return true;
   }

   public static boolean isFull(Inventory inv, ItemStack object, int amount) {
      object = NMS.getManager().fixItem(object);
      int search = 0;
      ItemStack[] var4 = inv.getContents();
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         ItemStack item = var4[var6];
         if (inv instanceof PlayerInventory) {
            PlayerInventory var8 = (PlayerInventory)inv;
         }

         if (item == null) {
            return false;
         }

         if (BukkitUtil.equalsIgnoreAmount(object, item)) {
            search += item.getMaxStackSize() - item.getAmount();
            if (search >= amount) {
               return false;
            }
         }
      }

      return true;
   }

   public static boolean isEmpty(Inventory inv) {
      return Arrays.stream(inv.getContents()).noneMatch(Objects::nonNull);
   }

   public static int getWidth(InventoryType type) {
      switch(type) {
      case CHEST:
      case PLAYER:
      case ENDER_CHEST:
      case SHULKER_BOX:
         return 9;
      case DISPENSER:
      case DROPPER:
      case WORKBENCH:
         return 3;
      case HOPPER:
         return 5;
      default:
         return -1;
      }
   }
}
