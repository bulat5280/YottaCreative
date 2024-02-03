package ua.govnojon.libs.bukkitutil.schedule;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import net.mineland.core.bukkit.LibsPlugin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import ua.govnojon.libs.bukkitutil.BukkitUtil;
import ua.govnojon.libs.myjava.PredicateZero;

public class Schedule {
   private static Plugin plugin = LibsPlugin.getInstance();
   private static BukkitScheduler bukkitScheduler = Bukkit.getScheduler();
   private Schedule.ScheduleRunnable schedule;

   public Schedule(Runnable runnable) {
      this.schedule = new Schedule.ScheduleRunnable(runnable);
   }

   public Schedule(Consumer<Schedule> consumer) {
      this.schedule = new Schedule.ScheduleRunnable(() -> {
         consumer.accept(this);
      });
   }

   public static Schedule create(Runnable runnable) {
      return new Schedule(runnable);
   }

   public static Schedule create(Consumer<Schedule> consumer) {
      return new Schedule(consumer);
   }

   public static BukkitTask run(Runnable runnable) {
      return bukkitScheduler.runTask(plugin, runnable);
   }

   public static BukkitTask runAsync(Runnable runnable) {
      return bukkitScheduler.runTaskAsynchronously(plugin, runnable);
   }

   public static BukkitTask later(Runnable runnable, long delay, TimeUnit time) {
      return bukkitScheduler.runTaskLater(plugin, runnable, time.toSeconds(delay) * 20L);
   }

   public static BukkitTask timer(Runnable runnable, long delay, long period, TimeUnit time) {
      return bukkitScheduler.runTaskTimer(plugin, runnable, time.toSeconds(delay) * 20L, time.toSeconds(period) * 20L);
   }

   public static BukkitTask later(Runnable runnable, long delay) {
      return bukkitScheduler.runTaskLater(plugin, runnable, delay);
   }

   public static BukkitTask timer(Runnable runnable, long delay, long period) {
      return bukkitScheduler.runTaskTimer(plugin, runnable, delay, period);
   }

   public static BukkitTask timerAsync(Runnable runnable, long delay, long period) {
      return bukkitScheduler.runTaskTimerAsynchronously(plugin, runnable, delay, period);
   }

   public static void stopTimer(int runnableID) {
      bukkitScheduler.cancelTask(runnableID);
   }

   public static long toTicks(long value, TimeUnit time) {
      return time.toSeconds(value) * 20L;
   }

   public Schedule predicate(PredicateZero predicate) {
      this.schedule.predicate = predicate;
      return this;
   }

   public Schedule count(int count) {
      this.schedule.count = count;
      return this;
   }

   public Schedule timer(long delay, long period, TimeUnit time) {
      this.schedule.runTaskTimer(plugin, this.toTime(delay, time), this.toTime(period, time));
      return this;
   }

   public Schedule timer(long delay, long period) {
      this.schedule.runTaskTimer(plugin, delay, period);
      return this;
   }

   public Schedule later(long delay, TimeUnit time) {
      this.schedule.runTaskLater(plugin, this.toTime(delay, time));
      return this;
   }

   public Schedule later(long delay) {
      this.schedule.runTaskLater(plugin, delay);
      return this;
   }

   public Schedule run() {
      this.schedule.runTask(plugin);
      return this;
   }

   private long toTime(long ticks, TimeUnit time) {
      return time.toSeconds(ticks) * 20L;
   }

   public void cancel() {
      BukkitUtil.cancelTask((BukkitRunnable)this.schedule);
   }

   private class ScheduleRunnable extends BukkitRunnable {
      private Runnable runnable;
      private PredicateZero predicate;
      private int count = -1;
      private int i = 0;
      private boolean timer = false;

      ScheduleRunnable(Runnable runnable) {
         this.runnable = runnable;
      }

      public void run() {
         this.checkCount();
         if (this.hasPredicate()) {
            this.runnable.run();
         } else {
            Schedule.this.cancel();
         }

      }

      private boolean hasPredicate() {
         return this.predicate == null || this.predicate.test();
      }

      private void checkCount() {
         if (this.timer && this.count != -1 && ++this.i >= this.count) {
            Schedule.this.cancel();
         }

      }

      public synchronized BukkitTask runTaskTimer(Plugin plugin, long delay, long period) throws IllegalArgumentException, IllegalStateException {
         this.timer = true;
         return super.runTaskTimer(plugin, delay, period);
      }
   }
}
