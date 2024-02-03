package ua.govnojon.libs.myjava;

public class Debugger {
   public static void timings(Object object, Runnable runnable) {
      long time = System.currentTimeMillis();
      runnable.run();
      System.out.println(object.getClass().getName() + " = " + (System.currentTimeMillis() - time));
   }
}
