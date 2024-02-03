package ua.govnojon.libs.myjava;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

public class CollectionUtil {
   private static Random random = new Random();

   public static <T> void removeSharedAll(Collection<T> one, Collection<T> two) {
      one.removeIf((el) -> {
         if (two.contains(el)) {
            two.remove(el);
            return true;
         } else {
            return false;
         }
      });
   }

   public static <T> T random(Collection<T> collection) {
      return random(collection, random);
   }

   public static <T> T random(Collection<T> collection, Random random) {
      if (collection.isEmpty()) {
         throw new NoSuchElementException();
      } else {
         int rand = random.nextInt(collection.size());
         return collection instanceof List ? ((List)collection).get(rand) : get(collection, rand);
      }
   }

   public static <T> T get(Collection<T> collection, int pos) {
      int i = 0;

      for(Iterator var3 = collection.iterator(); var3.hasNext(); ++i) {
         T t = var3.next();
         if (i == pos) {
            return t;
         }
      }

      throw new IndexOutOfBoundsException("size = " + collection.size() + " index = " + pos);
   }
}
