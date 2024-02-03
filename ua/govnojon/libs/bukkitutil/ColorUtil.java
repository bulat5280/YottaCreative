package ua.govnojon.libs.bukkitutil;

import java.util.Random;

public class ColorUtil {
   private static final Random random = new Random();
   private static final char[] colors = "0123456789abcdef".toCharArray();

   public static String getIntegerInColorCode(int code) {
      StringBuilder text = new StringBuilder();
      char[] var2 = String.valueOf(code).toCharArray();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         char c = var2[var4];
         text.append("§").append(c);
      }

      return text.toString();
   }

   public static String getColoredValue(int value, int max) {
      double percent = (double)value / (double)max;
      String color;
      if (percent > 0.6D) {
         color = "§a";
      } else if (percent > 0.3D) {
         color = "§e";
      } else {
         color = "§c";
      }

      return color + value + "/" + max;
   }

   public static String getValueMinMax(int size, int min, int max) {
      StringBuilder stage = new StringBuilder();
      if (size < min) {
         stage.append("§c");
      } else if (size < max) {
         stage.append("§a");
      } else {
         stage.append("§a§l");
      }

      stage.append(size).append("/").append(max);
      return stage.toString();
   }

   public static String randomColor() {
      return "§" + colors[random.nextInt(colors.length)];
   }
}
