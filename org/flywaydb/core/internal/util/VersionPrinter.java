package org.flywaydb.core.internal.util;

import org.flywaydb.core.api.logging.Log;
import org.flywaydb.core.api.logging.LogFactory;
import org.flywaydb.core.internal.util.scanner.classpath.ClassPathResource;

public class VersionPrinter {
   private static final Log LOG = LogFactory.getLog(VersionPrinter.class);
   private static boolean printed;

   private VersionPrinter() {
   }

   public static void printVersion() {
      if (!printed) {
         printed = true;
         String version = (new ClassPathResource("org/flywaydb/core/internal/version.txt", VersionPrinter.class.getClassLoader())).loadAsString("UTF-8");
         LOG.info("Flyway Community Edition " + version + " by Boxfuse");
      }
   }
}
