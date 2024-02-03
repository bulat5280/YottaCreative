package ua.govnojon.libs.myjava;

public class BigEntry<K, D, V> {
   protected K key;
   protected D door;
   protected V value;
   protected ElementMap next;
   protected ElementMap back;

   public BigEntry(K key, D door, V value) {
      this.key = key;
      this.door = door;
      this.value = value;
   }

   public K getKey() {
      return this.key;
   }

   public D getDoor() {
      return this.door;
   }

   public V getValue() {
      return this.value;
   }
}
