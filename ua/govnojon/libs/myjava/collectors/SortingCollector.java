package ua.govnojon.libs.myjava.collectors;

import java.util.ArrayList;
import java.util.HashMap;

public class SortingCollector {
   private ArrayList<Object> row = new ArrayList();
   private HashMap<Object, Integer> collector = new HashMap();

   public void addObject(Object obj, int n) {
      this.collector.put(obj, n);

      for(int i = 0; i < this.row.size(); ++i) {
         Object object = this.row.get(i);
         Integer objectN = (Integer)this.collector.get(object);
         if (objectN == null) {
            objectN = Integer.MIN_VALUE;
         }

         if (n < objectN) {
            this.row.add(i, obj);
            return;
         }
      }

      this.row.add(obj);
   }

   public ArrayList<Object> getSortedRow() {
      return this.row;
   }
}
