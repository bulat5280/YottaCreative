package ua.govnojon.libs.myjava;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MapUtil {
   public static <K, V> Map<V, List<K>> invertMap(Map<K, V> map, Map<V, List<K>> result) {
      map.entrySet().forEach((entry) -> {
         List<K> list = (List)result.computeIfAbsent(entry.getValue(), (k) -> {
            return new LinkedList();
         });
         list.add(entry.getKey());
      });
      return result;
   }
}
