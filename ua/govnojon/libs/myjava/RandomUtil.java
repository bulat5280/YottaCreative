package ua.govnojon.libs.myjava;

import java.util.List;
import java.util.Random;

public class RandomUtil {
   private static Random random = new Random();

   public static boolean nextPercent(int percent) {
      return random.nextInt(100) + percent >= 100;
   }

   public static boolean nextPercent(double percent) {
      return (double)random.nextInt(100) + percent >= 100.0D;
   }

   public static int nextInt(int bound) {
      return random.nextInt(bound);
   }

   public static <T> T getRandomObject(T[] array) {
      return array[nextInt(array.length)];
   }

   public static <T> T getRandomObject(List<T> list) {
      return list.get(nextInt(list.size()));
   }

   public static int randInt(int min, int max) {
      return random.nextInt(max - min + 1) + min;
   }
}
