package net.mineland.core.bukkit.modules.user;

import java.util.Arrays;
import org.bukkit.DyeColor;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ua.govnojon.libs.bukkitutil.ItemStackUtil;

public enum Lang {
   RU(ItemStackUtil.createBanner(Arrays.asList(new Pattern(DyeColor.BLUE, PatternType.HALF_HORIZONTAL_MIRROR), new Pattern(DyeColor.BLUE, PatternType.HALF_HORIZONTAL), new Pattern(DyeColor.RED, PatternType.STRIPE_BOTTOM), new Pattern(DyeColor.WHITE, PatternType.STRIPE_TOP))), "АаБбВвГгДдЕеЁёЖжЗзИиЙйКкЛлМмНнОоПпРрСсТтУуФфХхЦцЧчШшЩщЪъЫыЬьЭэЮюЯя"),
   EN(ItemStackUtil.createBanner(Arrays.asList(new Pattern(DyeColor.BLUE, PatternType.HALF_HORIZONTAL), new Pattern(DyeColor.BLUE, PatternType.HALF_HORIZONTAL_MIRROR), new Pattern(DyeColor.WHITE, PatternType.STRIPE_DOWNLEFT), new Pattern(DyeColor.WHITE, PatternType.STRIPE_DOWNRIGHT), new Pattern(DyeColor.WHITE, PatternType.STRIPE_CENTER), new Pattern(DyeColor.WHITE, PatternType.STRIPE_MIDDLE), new Pattern(DyeColor.RED, PatternType.STRAIGHT_CROSS), new Pattern(DyeColor.RED, PatternType.CROSS))), "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz");

   private String lang;
   private ItemStack icon;
   private char[] letters;

   private Lang(ItemStack icon, String letters) {
      this.icon = icon;
      this.letters = letters.toCharArray();
      this.lang = this.name().toLowerCase();
   }

   public static Lang forName(String s) {
      if (s == null) {
         return null;
      } else {
         s = s.toLowerCase();
         Lang[] var1 = values();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            Lang lang = var1[var3];
            if (lang.getLang().equals(s)) {
               return lang;
            }
         }

         return null;
      }
   }

   public static Lang forNumber(int number) {
      Lang[] var1 = values();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         Lang lang = var1[var3];
         if (lang.getNumber() == number) {
            return lang;
         }
      }

      return null;
   }

   public static Lang getLang(CommandSender sender) {
      return sender instanceof Player ? User.getUser(sender).getLang() : RU;
   }

   public static Lang forSender(CommandSender sender) {
      return sender instanceof Player ? User.getUser(sender).getLang() : RU;
   }

   public static Lang getByLocale(String locale) {
      locale = locale.toLowerCase();
      byte var2 = -1;
      switch(locale.hashCode()) {
      case 93608403:
         if (locale.equals("be_by")) {
            var2 = 0;
         }
         break;
      case 108861887:
         if (locale.equals("ru_ru")) {
            var2 = 2;
         }
         break;
      case 111334613:
         if (locale.equals("uk_ua")) {
            var2 = 1;
         }
      }

      switch(var2) {
      case 0:
      case 1:
      case 2:
         return RU;
      default:
         return EN;
      }
   }

   public String getLang() {
      return this.lang;
   }

   public char[] getLetters() {
      return this.letters;
   }

   public ItemStack getIcon() {
      return this.icon;
   }

   public int getNumber() {
      Lang[] langs = values();

      for(int i = 0; i < langs.length; ++i) {
         if (langs[i].equals(this)) {
            return i;
         }
      }

      return 0;
   }
}
