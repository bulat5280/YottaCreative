package ua.govnojon.libs.myjava;

import java.util.function.Supplier;

public class LazyInitializer<T> {
   private T object;
   private Supplier<T> supplier;
   private boolean init = false;

   public LazyInitializer(Supplier<T> supplier) {
      this.supplier = supplier;
   }

   public T get() {
      if (this.init) {
         return this.object;
      } else {
         this.object = this.supplier.get();
         this.init = true;
         return this.object;
      }
   }
}
