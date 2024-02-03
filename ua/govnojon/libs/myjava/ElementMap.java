package ua.govnojon.libs.myjava;

public class ElementMap<K, V> {
   protected K key;
   protected V value;
   protected ElementMap next;
   protected ElementMap back;

   public ElementMap(K key, V value) {
      this.key = key;
      this.value = value;
   }

   public K getKey() {
      return this.key;
   }

   public V getValue() {
      return this.value;
   }
}
