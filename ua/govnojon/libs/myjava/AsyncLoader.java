package ua.govnojon.libs.myjava;

import com.google.common.cache.Cache;
import java.util.Map;
import java.util.function.Consumer;

public class AsyncLoader<K, V> {
   private Map<K, Object> map;

   public AsyncLoader(Map<K, V> map) {
      this.map = map;
   }

   public AsyncLoader(Cache<K, V> cache) {
      this((Map)cache.asMap());
   }

   public void loadOrGet(K key, Consumer<V> consumer, Consumer<Consumer<V>> load) {
      Object o = this.map.get(key);
      AsyncLoader.Container container;
      if (o != null) {
         if (o instanceof AsyncLoader.Container) {
            container = (AsyncLoader.Container)o;
            Consumer<V> previously = container.consumer;
            container.consumer = (v) -> {
               Try.ignore(() -> {
                  previously.accept(v);
               });
               consumer.accept(v);
            };
         } else {
            consumer.accept(o);
         }
      } else {
         container = new AsyncLoader.Container(key, consumer);
         this.map.put(key, container);
         load.accept(container);
      }

   }

   public Map<K, V> getMap() {
      return this.map;
   }

   private class Container<VInner> implements Consumer<VInner> {
      private K key;
      private Consumer<VInner> consumer;
      private Consumer<VInner> consumerOriginal;

      Container(K key, Consumer<VInner> consumer) {
         this.key = key;
         this.consumer = this.consumerOriginal = consumer;
      }

      public void accept(VInner v) {
         AsyncLoader.this.map.put(this.key, v);
         this.consumer.accept(v);
      }

      public String toString() {
         return this.consumerOriginal + " at " + super.toString();
      }
   }
}
