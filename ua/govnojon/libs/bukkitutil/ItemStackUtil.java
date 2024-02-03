package ua.govnojon.libs.bukkitutil;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import net.minecraft.server.v1_12_R1.NBTTagCompound;
import net.mineland.core.bukkit.modules.nms.NMS;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import ua.govnojon.libs.config.Config;

public class ItemStackUtil {
   private static char[] spec = "§☭§r".toCharArray();

   /** @deprecated */
   @Deprecated
   public static ItemStack setSkinSkullMetaData(ItemStack item, String owner) {
      SkullMeta meta = (SkullMeta)item.getItemMeta();
      meta.setOwner(owner);
      item.setItemMeta(meta);
      return item;
   }

   public static ItemStack createSkinSkullByValue(String value) {
      return NMS.getManagerSingle().setSkinHead(value);
   }

   public static boolean isNullOrAir(ItemStack item) {
      return item == null || item.getType() == Material.AIR;
   }

   public static boolean isSword(Material material) {
      switch(material) {
      case STONE_SWORD:
      case DIAMOND_SWORD:
      case GOLD_SWORD:
      case IRON_SWORD:
      case WOOD_SWORD:
         return true;
      default:
         return false;
      }
   }

   public static ItemStack setText(ItemStack item, String text) {
      if (item == null) {
         return null;
      } else {
         if (text != null) {
            String[] data = text.split("(::|\n|\r)");
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(data[0]);
            meta.setLore(data.length > 1 ? Arrays.asList(data).subList(1, data.length) : null);
            item.setItemMeta(meta);
         } else {
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName((String)null);
            meta.setLore((List)null);
            item.setItemMeta(meta);
         }

         return item;
      }
   }

   public static ItemStack createColoredPane(DyeColor gray) {
      return new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)gray.getWoolData());
   }

   public static ItemStack createBanner(List<Pattern> patterns) {
      ItemStack item = new ItemStack(Material.BANNER);
      BannerMeta banner = (BannerMeta)item.getItemMeta();
      Iterator var3 = patterns.iterator();

      while(var3.hasNext()) {
         Pattern pattern = (Pattern)var3.next();
         banner.addPattern(pattern);
      }

      item.setItemMeta(banner);
      return item;
   }

   public static boolean equalsIgnoreAmount(ItemStack item1, ItemStack item2) {
      return BukkitUtil.equalsIgnoreAmount(item1, item2);
   }

   public static boolean equalsIgnoreDurability(ItemStack item1, ItemStack item2) {
      if (!isNullOrAir(item1) && !isNullOrAir(item2)) {
         if (item1.isSimilar(item2)) {
            return true;
         } else {
            return item1.getType() == item2.getType() && item1.getAmount() == item2.getAmount() && Objects.equals(item1.getItemMeta(), item2.getItemMeta());
         }
      } else {
         return false;
      }
   }

   public static ItemStack setMetadata(ItemStack stack, String metadata) {
      ItemMeta meta = stack.getItemMeta();
      if (meta != null) {
         meta.setLocalizedName(metadata);
         stack.setItemMeta(meta);
      }

      return stack;
   }

   public static String getMetadata(ItemStack stack) {
      ItemMeta meta = stack.getItemMeta();
      return meta == null ? null : meta.getLocalizedName();
   }

   public static boolean hasMetadata(ItemStack stack, String metadata) {
      ItemMeta meta = stack.getItemMeta();
      return meta != null && metadata.equals(meta.getLocalizedName());
   }

   private static boolean has(String text, String data) {
      int index = data.length() * 2 + 1;
      if (text.length() - 1 < index) {
         return false;
      } else if (text.charAt(index) == 9773) {
         char[] chars = data.toCharArray();
         char[] textChars = text.toCharArray();

         for(int i = 0; i < chars.length; ++i) {
            if (chars[i] != textChars[i * 2 + 1]) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   private static String decode(String code) {
      int index = StringUtils.indexOf(code, 9773);
      if (index == -1) {
         return null;
      } else {
         char[] chars = code.toCharArray();
         char[] data = new char[(index - 1) / 2];

         for(int i = 0; i < data.length; ++i) {
            data[i] = chars[i * 2 + 1];
         }

         return new String(data);
      }
   }

   private static String encode(String data) {
      char[] chars = data.toCharArray();
      char[] codeData = new char[chars.length * 2 + 4];

      for(int i = 0; i < chars.length; ++i) {
         codeData[i * 2] = 167;
         codeData[i * 2 + 1] = chars[i];
      }

      System.arraycopy(spec, 0, codeData, codeData.length - 4, spec.length);
      return new String(codeData);
   }

   public static ItemStack[] deserializeArray(String[] data) {
      ItemStack[] items = new ItemStack[data.length];

      for(int i = 0; i < data.length; ++i) {
         items[i] = deserializeFromJson(data[i]);
      }

      return items;
   }

   public static List<ItemStack> deserializeList(String data) {
      Config config = new Config(data);
      return config.getItemStackList("items");
   }

   public static ItemStack deserialize(String data) {
      Config config = new Config(data);
      return config.getItemStack("items");
   }

   public static String[] serializeArray(ItemStack[] items) {
      String[] strings = new String[items.length];

      for(int i = 0; i < items.length; ++i) {
         strings[i] = serializeToJson(items[i]);
      }

      return strings;
   }

   public static String serializeList(List<ItemStack> items) {
      Config config = new Config("");
      config.set("items", items);
      return config.saveToString();
   }

   public static String serialize(ItemStack item) {
      Config config = new Config("");
      config.set("items", item);
      return config.saveToString();
   }

   public static String serializeToJson(ItemStack itemStack) {
      NBTTagCompound nbtTagCompound = new NBTTagCompound();
      CraftItemStack.asNMSCopy(itemStack).save(nbtTagCompound);
      return nbtTagCompound.toString();
   }

   public static ItemStack deserializeFromJson(String string) {
      NBTTagCompound nbtTagCompound = JsonToNBT.getTagFromJson(string);
      net.minecraft.server.v1_12_R1.ItemStack itemStack = new net.minecraft.server.v1_12_R1.ItemStack(nbtTagCompound);
      return CraftItemStack.asBukkitCopy(itemStack);
   }

   public static ItemStack createSkinSkullByUser(String name) {
      ItemStack stack = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
      SkullMeta itemMeta = (SkullMeta)stack.getItemMeta();
      itemMeta.setOwner(name);
      stack.setItemMeta(itemMeta);
      return stack;
   }
}
