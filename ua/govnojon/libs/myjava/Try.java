package ua.govnojon.libs.myjava;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Try {
   public static Try.ThrowableHandler throwableHandler = Throwable::printStackTrace;

   public static <T> T unchecked(Try.SupplierThrows<T> supplier) {
      try {
         return supplier.get();
      } catch (Exception var2) {
         doThrow0(var2);
         throw new AssertionError();
      }
   }

   public static void unchecked(Try.RunnableThrows runnable) {
      try {
         runnable.run();
      } catch (Exception var2) {
         doThrow0(var2);
         throw new AssertionError();
      }
   }

   public static <T> Predicate<T> unchecked(Try.PredicateThrows<T> predicate) {
      return (t) -> {
         try {
            return predicate.test(t);
         } catch (Exception var3) {
            doThrow0(var3);
            throw new AssertionError();
         }
      };
   }

   public static <T> T ignore(Try.SupplierThrows<T> supplier, T def) {
      try {
         return supplier.get();
      } catch (Exception var3) {
         throwableHandler.handle(var3);
         return def;
      }
   }

   public static Optional<Exception> ignore(Try.RunnableThrows runnable) {
      try {
         runnable.run();
      } catch (Exception var2) {
         throwableHandler.handle(var2);
         return Optional.of(var2);
      }

      return Optional.empty();
   }

   public static Optional<Exception> ignore(Try.RunnableThrows runnable, Consumer<Exception> consumer) {
      try {
         runnable.run();
      } catch (Exception var3) {
         consumer.accept(var3);
         throwableHandler.handle(var3);
         return Optional.of(var3);
      }

      return Optional.empty();
   }

   public static <T> Predicate<T> ignore(Try.PredicateThrows<T> predicate, boolean def) {
      return (t) -> {
         try {
            return predicate.test(t);
         } catch (Exception var4) {
            throwableHandler.handle(var4);
            return def;
         }
      };
   }

   private static <E extends Exception> void doThrow0(Exception e) throws E {
      throw e;
   }

   public interface ThrowableHandler {
      void handle(Throwable var1);
   }

   public interface PredicateThrows<T> {
      boolean test(T var1) throws Exception;
   }

   public interface RunnableThrows {
      void run() throws Exception;
   }

   public interface SupplierThrows<T> {
      T get() throws Exception;
   }
}
