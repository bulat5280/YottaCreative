package ua.govnojon.libs.myjava.collectors;

import java.util.Iterator;
import java.util.LinkedList;
import ua.govnojon.libs.myjava.BigEntry;

public class IntervalCollector {
   private LinkedList<BigEntry<Integer, Integer, Object>> collector = new LinkedList();

   public void addObject(int min, int max, Object obj) {
      this.collector.add(new BigEntry(min, max, obj));
   }

   public Object getObject(int n) {
      Iterator var2 = this.collector.iterator();

      BigEntry entry;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         entry = (BigEntry)var2.next();
      } while(n < (Integer)entry.getKey() || n > (Integer)entry.getDoor());

      return entry.getValue();
   }

   public void removeObject(Object obj) {
      this.collector.removeIf((entry) -> {
         return entry.getValue().equals(obj);
      });
   }

   public LinkedList<BigEntry<Integer, Integer, Object>> getCollector() {
      return this.collector;
   }

   public boolean isEmpty() {
      return this.collector.isEmpty();
   }

   public int size() {
      return this.collector.size();
   }
}
