package org.apache.commons.math3.util;

public class Pair<K, V> {
   private final K key;
   private final V value;

   public Pair(K k, V v) {
      this.key = k;
      this.value = v;
   }

   public Pair(Pair<? extends K, ? extends V> entry) {
      this(entry.getKey(), entry.getValue());
   }

   public K getKey() {
      return this.key;
   }

   public V getValue() {
      return this.value;
   }

   public K getFirst() {
      return this.key;
   }

   public V getSecond() {
      return this.value;
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof Pair)) {
         return false;
      } else {
         boolean var10000;
         label43: {
            label29: {
               Pair<?, ?> oP = (Pair)o;
               if (this.key == null) {
                  if (oP.key != null) {
                     break label29;
                  }
               } else if (!this.key.equals(oP.key)) {
                  break label29;
               }

               if (this.value == null) {
                  if (oP.value == null) {
                     break label43;
                  }
               } else if (this.value.equals(oP.value)) {
                  break label43;
               }
            }

            var10000 = false;
            return var10000;
         }

         var10000 = true;
         return var10000;
      }
   }

   public int hashCode() {
      int result = this.key == null ? 0 : this.key.hashCode();
      int h = this.value == null ? 0 : this.value.hashCode();
      result = 37 * result + h ^ h >>> 16;
      return result;
   }
}
