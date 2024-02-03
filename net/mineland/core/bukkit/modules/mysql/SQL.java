package net.mineland.core.bukkit.modules.mysql;

import java.sql.SQLException;
import java.util.concurrent.Future;
import java.util.function.Consumer;
import net.mineland.core.bukkit.module.Module;
import org.bukkit.Bukkit;
import org.jooq.DSLContext;
import ua.govnojon.libs.bukkitutil.schedule.Schedule;

public class SQL {
   private static ModuleMySQL moduleMySQL = (ModuleMySQL)Module.getInstance(ModuleMySQL.class);

   public static DSLContext getCreate() {
      return moduleMySQL.getCreate();
   }

   public static Future<?> async(String query, Object... bindings) {
      return async((create) -> {
         create.execute(query, bindings);
      });
   }

   public static Future<?> async(SQLConsumer create) {
      return moduleMySQL.getService().submit(() -> {
         printTime(create, () -> {
            try {
               create.run(moduleMySQL.getCreate());
            } catch (Exception var2) {
               var2.printStackTrace();
            }

         });
      });
   }

   public static <R> Future<?> async(SQLFunction<R> create, Consumer<R> result) {
      return moduleMySQL.getService().submit(() -> {
         printTime(create, () -> {
            try {
               R apply = create.apply(moduleMySQL.getCreate());
               Schedule.run(() -> {
                  long time = System.currentTimeMillis();
                  boolean var10 = false;

                  try {
                     var10 = true;
                     result.accept(apply);
                     var10 = false;
                  } finally {
                     if (var10) {
                        long after = System.currentTimeMillis() - time;
                        if (after > 10L) {
                           Bukkit.getLogger().warning("Таск для обработки sql результата " + result + " выполнялся " + after + "ms.");
                        }

                     }
                  }

                  long afterx = System.currentTimeMillis() - time;
                  if (afterx > 10L) {
                     Bukkit.getLogger().warning("Таск для обработки sql результата " + result + " выполнялся " + afterx + "ms.");
                  }

               });
            } catch (SQLException var3) {
               var3.printStackTrace();
            }

         });
      });
   }

   public static void sync(String sql, Object... bindings) {
      printTime(sql, () -> {
         getCreate().execute(sql, bindings);
      });
   }

   private static void printTime(Object print, Runnable runnable) {
      long start = System.currentTimeMillis();
      boolean var10 = false;

      try {
         var10 = true;
         runnable.run();
         var10 = false;
      } finally {
         if (var10) {
            long after = System.currentTimeMillis() - start;
            if (after > 500L) {
               Bukkit.getLogger().warning("SQL задержка, объект " + print + " выполнялся " + after + "ms.");
            }

         }
      }

      long after = System.currentTimeMillis() - start;
      if (after > 500L) {
         Bukkit.getLogger().warning("SQL задержка, объект " + print + " выполнялся " + after + "ms.");
      }

   }
}
