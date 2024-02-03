package org.flywaydb.core.api.android;

import android.content.Context;

public class ContextHolder {
   private static Context context;

   private ContextHolder() {
   }

   public static Context getContext() {
      return context;
   }

   public static void setContext(Context context) {
      ContextHolder.context = context;
   }
}
