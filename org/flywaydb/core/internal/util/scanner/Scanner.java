package org.flywaydb.core.internal.util.scanner;

import org.flywaydb.core.api.FlywayException;
import org.flywaydb.core.internal.util.FeatureDetector;
import org.flywaydb.core.internal.util.Location;
import org.flywaydb.core.internal.util.scanner.classpath.ClassPathScanner;
import org.flywaydb.core.internal.util.scanner.classpath.ResourceAndClassScanner;
import org.flywaydb.core.internal.util.scanner.classpath.android.AndroidScanner;
import org.flywaydb.core.internal.util.scanner.filesystem.FileSystemScanner;

public class Scanner {
   private final ResourceAndClassScanner resourceAndClassScanner;
   private final ClassLoader classLoader;
   private final FileSystemScanner fileSystemScanner = new FileSystemScanner();

   public Scanner(ClassLoader classLoader) {
      this.classLoader = classLoader;
      if ((new FeatureDetector(classLoader)).isAndroidAvailable()) {
         this.resourceAndClassScanner = new AndroidScanner(classLoader);
      } else {
         this.resourceAndClassScanner = new ClassPathScanner(classLoader);
      }

   }

   public LoadableResource[] scanForResources(Location location, String prefix, String[] suffixes) {
      try {
         return location.isFileSystem() ? this.fileSystemScanner.scanForResources(location, prefix, suffixes) : this.resourceAndClassScanner.scanForResources(location, prefix, suffixes);
      } catch (Exception var5) {
         throw new FlywayException("Unable to scan for SQL migrations in location: " + location, var5);
      }
   }

   public Class<?>[] scanForClasses(Location location, Class<?> implementedInterface) throws Exception {
      return this.resourceAndClassScanner.scanForClasses(location, implementedInterface);
   }

   public ClassLoader getClassLoader() {
      return this.classLoader;
   }
}
