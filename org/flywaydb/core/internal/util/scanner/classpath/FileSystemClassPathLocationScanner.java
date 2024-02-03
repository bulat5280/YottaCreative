package org.flywaydb.core.internal.util.scanner.classpath;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Set;
import java.util.TreeSet;
import org.flywaydb.core.api.logging.Log;
import org.flywaydb.core.api.logging.LogFactory;
import org.flywaydb.core.internal.util.UrlUtils;

public class FileSystemClassPathLocationScanner implements ClassPathLocationScanner {
   private static final Log LOG = LogFactory.getLog(FileSystemClassPathLocationScanner.class);

   public Set<String> findResourceNames(String location, URL locationUrl) throws IOException {
      String filePath = UrlUtils.toFilePath(locationUrl);
      File folder = new File(filePath);
      if (!folder.isDirectory()) {
         LOG.debug("Skipping path as it is not a directory: " + filePath);
         return new TreeSet();
      } else {
         String classPathRootOnDisk = filePath.substring(0, filePath.length() - location.length());
         if (!classPathRootOnDisk.endsWith(File.separator)) {
            classPathRootOnDisk = classPathRootOnDisk + File.separator;
         }

         LOG.debug("Scanning starting at classpath root in filesystem: " + classPathRootOnDisk);
         return this.findResourceNamesFromFileSystem(classPathRootOnDisk, location, folder);
      }
   }

   Set<String> findResourceNamesFromFileSystem(String classPathRootOnDisk, String scanRootLocation, File folder) throws IOException {
      LOG.debug("Scanning for resources in path: " + folder.getPath() + " (" + scanRootLocation + ")");
      Set<String> resourceNames = new TreeSet();
      File[] files = folder.listFiles();
      File[] var6 = files;
      int var7 = files.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         File file = var6[var8];
         if (file.canRead()) {
            if (file.isDirectory()) {
               resourceNames.addAll(this.findResourceNamesFromFileSystem(classPathRootOnDisk, scanRootLocation, file));
            } else {
               resourceNames.add(this.toResourceNameOnClasspath(classPathRootOnDisk, file));
            }
         }
      }

      return resourceNames;
   }

   private String toResourceNameOnClasspath(String classPathRootOnDisk, File file) throws IOException {
      String fileName = file.getAbsolutePath().replace("\\", "/");
      return fileName.substring(classPathRootOnDisk.length());
   }
}
