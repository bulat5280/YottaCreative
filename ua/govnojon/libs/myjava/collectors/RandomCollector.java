package ua.govnojon.libs.myjava.collectors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Map.Entry;

public class RandomCollector {
   int maxRandomNumber;
   private HashMap<Object, Integer> collector;

   public RandomCollector() {
      this.collector = new HashMap();
   }

   public RandomCollector(HashMap<Object, Integer> collector, int maxRandomNumber) {
      this.collector = collector;
      this.maxRandomNumber = maxRandomNumber;
   }

   public void addObject(Object obj, int chance) {
      this.collector.put(obj, chance);
      this.maxRandomNumber += chance;
   }

   public Object getRandomObject() {
      if (this.maxRandomNumber != 0) {
         int random = 1 + (new Random()).nextInt(this.maxRandomNumber);
         int col = 0;

         Entry entry;
         for(Iterator var3 = this.collector.entrySet().iterator(); var3.hasNext(); col += (Integer)entry.getValue()) {
            entry = (Entry)var3.next();
            if (random > col && random <= col + (Integer)entry.getValue()) {
               return entry.getKey();
            }
         }
      }

      return null;
   }

   public List<Object> getRandomObjects(int count) {
      RandomCollector rc = this.clone();
      List<Object> list = new ArrayList();
      if (this.maxRandomNumber != 0 && count != 0) {
         for(int i = 0; i < count; ++i) {
            Object obj = rc.getRandomObject();
            if (obj == null) {
               break;
            }

            rc.removeObject(obj);
            list.add(obj);
         }
      }

      return list;
   }

   public void removeObject(Object obj) {
      if (this.collector.containsKey(obj)) {
         int n = (Integer)this.collector.remove(obj);
         this.maxRandomNumber -= n;
      }

   }

   public int getObjectsCount() {
      return this.collector.size();
   }

   protected RandomCollector clone() {
      return new RandomCollector((HashMap)this.collector.clone(), this.maxRandomNumber);
   }

   public HashMap<Object, Integer> getCollector() {
      return this.collector;
   }

   public boolean isEmpty() {
      return this.getObjectsCount() == 0;
   }
}
