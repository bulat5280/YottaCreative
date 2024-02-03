package ua.govnojon.libs.myjava;

public class MathUtil {
   public static int getPercent(int value, int max) {
      return (int)((double)value / (double)max * 100.0D);
   }

   public static boolean isInt(String s) {
      try {
         Integer.parseInt(s);
         return true;
      } catch (Exception var2) {
         return false;
      }
   }

   public static boolean isLong(String s) {
      try {
         Long.parseLong(s);
         return true;
      } catch (Exception var2) {
         return false;
      }
   }

   public static boolean isBoolean(String s) {
      try {
         Boolean.parseBoolean(s);
         return true;
      } catch (Exception var2) {
         return false;
      }
   }
}
