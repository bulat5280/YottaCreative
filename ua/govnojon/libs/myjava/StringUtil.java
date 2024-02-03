package ua.govnojon.libs.myjava;

public class StringUtil {
   public static String toString(long l) {
      boolean negative = l < 0L;
      if (negative) {
         l = -l;
      }

      char[] number = String.valueOf(l).toCharArray();
      double countData = (double)number.length / 3.0D;
      int count = (int)(countData % 1.0D != 0.0D ? countData : countData - 1.0D);
      char[] text = new char[number.length + count];
      int pos = 0;

      for(int i = number.length - 1; i >= 0; --i) {
         text[i + count] = number[i];
         ++pos;
         if (pos == 3 && count > 0) {
            pos = 0;
            --count;
            text[i + count] = '.';
         }
      }

      return (negative ? "-" : "") + new String(text);
   }

   public static String toStringLong(double i) {
      return toString((long)i);
   }

   public static String removeLast(String trace, int n) {
      if (n <= 0) {
         return trace;
      } else {
         char[] value = trace.toCharArray();
         if (value.length <= n) {
            return "";
         } else {
            char[] valueNew = new char[value.length - n];
            System.arraycopy(value, 0, valueNew, 0, valueNew.length);
            return new String(valueNew);
         }
      }
   }
}
