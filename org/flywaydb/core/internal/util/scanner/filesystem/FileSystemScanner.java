package org.flywaydb.core.internal.util.scanner.filesystem;

import java.io.File;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import org.flywaydb.core.api.logging.Log;
import org.flywaydb.core.api.logging.LogFactory;
import org.flywaydb.core.internal.util.Location;
import org.flywaydb.core.internal.util.StringUtils;
import org.flywaydb.core.internal.util.scanner.LoadableResource;

public class FileSystemScanner {
   private static final Log LOG = LogFactory.getLog(FileSystemScanner.class);

   public LoadableResource[] scanForResources(Location location, String prefix, String... suffixes) {
      String path = location.getPath();
      LOG.debug("Scanning for filesystem resources at '" + path + "' (Prefix: '" + prefix + "', Suffixes: '" + StringUtils.arrayToCommaDelimitedString(suffixes) + "')");
      File dir = new File(path);
      if (dir.isDirectory() && dir.canRead()) {
         Set<LoadableResource> resources = new TreeSet();
         Set<String> resourceNames = this.findResourceNames(path, prefix, suffixes);
         Iterator var8 = resourceNames.iterator();

         while(var8.hasNext()) {
            String resourceName = (String)var8.next();
            resources.add(new FileSystemResource(resourceName));
            LOG.debug("Found filesystem resource: " + resourceName);
         }

         return (LoadableResource[])resources.toArray(new LoadableResource[resources.size()]);
      } else {
         LOG.warn("Unable to resolve location filesystem:" + path);
         return new LoadableResource[0];
      }
   }

   private Set<String> findResourceNames(String path, String prefix, String[] suffixes) {
      Set<String> resourceNames = this.findResourceNamesFromFileSystem(path, new File(path));
      return this.filterResourceNames(resourceNames, prefix, suffixes);
   }

   private Set<String> findResourceNamesFromFileSystem(String scanRootLocation, File folder) {
      LOG.debug("Scanning for resources in path: " + folder.getPath() + " (" + scanRootLocation + ")");
      Set<String> resourceNames = new TreeSet();
      File[] files = folder.listFiles();
      File[] var5 = files;
      int var6 = files.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         File file = var5[var7];
         if (file.canRead()) {
            if (file.isDirectory()) {
               resourceNames.addAll(this.findResourceNamesFromFileSystem(scanRootLocation, file));
            } else {
               resourceNames.add(file.getPath());
            }
         }
      }

      return resourceNames;
   }

   private Set<String> filterResourceNames(Set<String> resourceNames, String prefix, String[] suffixes) {
      Set<String> filteredResourceNames = new TreeSet();
      Iterator var5 = resourceNames.iterator();

      while(var5.hasNext()) {
         String resourceName = (String)var5.next();
         String fileName = resourceName.substring(resourceName.lastIndexOf(File.separator) + 1);
         if (this.fileNameMatches(fileName, prefix, suffixes)) {
            filteredResourceNames.add(resourceName);
         } else {
            LOG.debug("Filtering out resource: " + resourceName + " (filename: " + fileName + ")");
         }
      }

      return filteredResourceNames;
   }

   private boolean fileNameMatches(String fileName, String prefix, String[] suffixes) {
      String[] var4 = suffixes;
      int var5 = suffixes.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         String suffix = var4[var6];
         if (fileName.startsWith(prefix) && fileName.endsWith(suffix) && fileName.length() > (prefix + suffix).length()) {
            return true;
         }
      }

      return false;
   }
}
