package org.flywaydb.core.internal.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
   private DateUtils() {
   }

   public static String formatDateAsIsoString(Date date) {
      return date == null ? "" : (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(date);
   }
}
