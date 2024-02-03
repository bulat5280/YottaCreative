package org.flywaydb.core.internal.util.logging.android;

import org.flywaydb.core.api.logging.Log;

public class AndroidLog implements Log {
   private static final String TAG = "Flyway";

   public void debug(String message) {
      android.util.Log.d("Flyway", message);
   }

   public void info(String message) {
      android.util.Log.i("Flyway", message);
   }

   public void warn(String message) {
      android.util.Log.w("Flyway", message);
   }

   public void error(String message) {
      android.util.Log.e("Flyway", message);
   }

   public void error(String message, Exception e) {
      android.util.Log.e("Flyway", message, e);
   }
}
