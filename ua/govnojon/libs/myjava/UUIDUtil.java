package ua.govnojon.libs.myjava;

public class UUIDUtil {
   public static String fixLength(String uuid) {
      char[] data = uuid.toCharArray();
      if (data.length != 32) {
         throw new RuntimeException("неправильная длина");
      } else {
         char[] value = new char[36];
         System.arraycopy(data, 0, value, 0, 8);
         System.arraycopy(data, 8, value, 9, 4);
         System.arraycopy(data, 12, value, 14, 4);
         System.arraycopy(data, 16, value, 19, 4);
         System.arraycopy(data, 20, value, 24, 12);
         value[8] = value[13] = value[18] = value[23] = '-';
         return new String(value);
      }
   }
}
