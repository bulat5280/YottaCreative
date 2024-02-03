package com.mysql.cj.jdbc.util;

import com.mysql.cj.api.exceptions.ExceptionInterceptor;
import com.mysql.cj.core.Messages;
import com.mysql.cj.core.exceptions.ExceptionFactory;
import com.mysql.cj.core.exceptions.InvalidConnectionAttributeException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.Properties;
import java.util.TimeZone;

public class TimeUtil {
   static final TimeZone GMT_TIMEZONE = TimeZone.getTimeZone("GMT");
   private static final String TIME_ZONE_MAPPINGS_RESOURCE = "/com/mysql/cj/jdbc/util/TimeZoneMapping.properties";
   private static Properties timeZoneMappings = null;
   protected static final Method systemNanoTimeMethod;

   public static boolean nanoTimeAvailable() {
      return systemNanoTimeMethod != null;
   }

   public static long getCurrentTimeNanosOrMillis() {
      if (systemNanoTimeMethod != null) {
         try {
            return (Long)systemNanoTimeMethod.invoke((Object)null, (Object[])null);
         } catch (IllegalArgumentException var1) {
         } catch (IllegalAccessException var2) {
         } catch (InvocationTargetException var3) {
         }
      }

      return System.currentTimeMillis();
   }

   public static String getCanonicalTimezone(String timezoneStr, ExceptionInterceptor exceptionInterceptor) {
      if (timezoneStr == null) {
         return null;
      } else {
         timezoneStr = timezoneStr.trim();
         if (timezoneStr.length() > 2 && (timezoneStr.charAt(0) == '+' || timezoneStr.charAt(0) == '-') && Character.isDigit(timezoneStr.charAt(1))) {
            return "GMT" + timezoneStr;
         } else {
            Class var2 = TimeUtil.class;
            synchronized(TimeUtil.class) {
               if (timeZoneMappings == null) {
                  loadTimeZoneMappings(exceptionInterceptor);
               }
            }

            String canonicalTz;
            if ((canonicalTz = timeZoneMappings.getProperty(timezoneStr)) != null) {
               return canonicalTz;
            } else {
               throw (InvalidConnectionAttributeException)ExceptionFactory.createException(InvalidConnectionAttributeException.class, Messages.getString("TimeUtil.UnrecognizedTimezoneId", new Object[]{timezoneStr}), exceptionInterceptor);
            }
         }
      }
   }

   public static String formatNanos(int nanos, boolean usingMicros) {
      if (nanos > 999999999) {
         nanos %= 100000000;
      }

      if (usingMicros) {
         nanos /= 1000;
      }

      if (nanos == 0) {
         return "0";
      } else {
         int digitCount = usingMicros ? 6 : 9;
         String nanosString = Integer.toString(nanos);
         String zeroPadding = usingMicros ? "000000" : "000000000";
         nanosString = zeroPadding.substring(0, digitCount - nanosString.length()) + nanosString;

         int pos;
         for(pos = digitCount - 1; nanosString.charAt(pos) == '0'; --pos) {
         }

         nanosString = nanosString.substring(0, pos + 1);
         return nanosString;
      }
   }

   private static void loadTimeZoneMappings(ExceptionInterceptor exceptionInterceptor) {
      timeZoneMappings = new Properties();

      try {
         timeZoneMappings.load(TimeUtil.class.getResourceAsStream("/com/mysql/cj/jdbc/util/TimeZoneMapping.properties"));
      } catch (IOException var5) {
         throw ExceptionFactory.createException(Messages.getString("TimeUtil.LoadTimeZoneMappingError"), exceptionInterceptor);
      }

      String[] var1 = TimeZone.getAvailableIDs();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         String tz = var1[var3];
         if (!timeZoneMappings.containsKey(tz)) {
            timeZoneMappings.put(tz, tz);
         }
      }

   }

   public static Timestamp truncateFractionalSeconds(Timestamp timestamp) {
      Timestamp truncatedTimestamp = new Timestamp(timestamp.getTime());
      truncatedTimestamp.setNanos(0);
      return truncatedTimestamp;
   }

   static {
      Method aMethod;
      try {
         aMethod = System.class.getMethod("nanoTime", (Class[])null);
      } catch (SecurityException var2) {
         aMethod = null;
      } catch (NoSuchMethodException var3) {
         aMethod = null;
      }

      systemNanoTimeMethod = aMethod;
   }
}
