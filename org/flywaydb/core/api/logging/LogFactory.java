package org.flywaydb.core.api.logging;

import org.flywaydb.core.internal.util.FeatureDetector;
import org.flywaydb.core.internal.util.logging.android.AndroidLogCreator;
import org.flywaydb.core.internal.util.logging.apachecommons.ApacheCommonsLogCreator;
import org.flywaydb.core.internal.util.logging.javautil.JavaUtilLogCreator;
import org.flywaydb.core.internal.util.logging.slf4j.Slf4jLogCreator;

public class LogFactory {
   private static LogCreator logCreator;
   private static LogCreator fallbackLogCreator;

   private LogFactory() {
   }

   public static void setLogCreator(LogCreator logCreator) {
      LogFactory.logCreator = logCreator;
   }

   public static void setFallbackLogCreator(LogCreator fallbackLogCreator) {
      LogFactory.fallbackLogCreator = fallbackLogCreator;
   }

   public static Log getLog(Class<?> clazz) {
      if (logCreator == null) {
         FeatureDetector featureDetector = new FeatureDetector(Thread.currentThread().getContextClassLoader());
         if (featureDetector.isAndroidAvailable()) {
            logCreator = new AndroidLogCreator();
         } else if (featureDetector.isSlf4jAvailable()) {
            logCreator = new Slf4jLogCreator();
         } else if (featureDetector.isApacheCommonsLoggingAvailable()) {
            logCreator = new ApacheCommonsLogCreator();
         } else if (fallbackLogCreator == null) {
            logCreator = new JavaUtilLogCreator();
         } else {
            logCreator = fallbackLogCreator;
         }
      }

      return logCreator.createLogger(clazz);
   }
}
