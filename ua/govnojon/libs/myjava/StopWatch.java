package ua.govnojon.libs.myjava;

public class StopWatch {
   private long time;

   public StopWatch() {
      this.reset(false);
   }

   public void resetAndPrint() {
      this.resetAndPrint("StopWatch");
   }

   public void resetAndPrint(String comment) {
      this.print(comment);
      this.reset(false);
   }

   public void resetAndPrint(long delay, String comment) {
      this.printIsTime(delay, comment);
      this.reset(false);
   }

   private void print(String comment) {
      String info = comment + ": Время: " + (System.currentTimeMillis() - this.time) + "ms";
      System.out.println(info);
   }

   public void print() {
      this.print("StopWatch");
   }

   public void reset(boolean print) {
      this.time = System.currentTimeMillis();
      if (print) {
         System.out.println("Время перезапущено.");
      }

   }

   public void printIsTime(long delay, String comment) {
      long period = System.currentTimeMillis() - this.time;
      if (period >= delay) {
         String info = comment + ": Время: " + period + "ms";
         System.out.println(info);
      }

   }
}
