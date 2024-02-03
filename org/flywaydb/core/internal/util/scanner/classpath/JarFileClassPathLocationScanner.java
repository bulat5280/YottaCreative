package org.flywaydb.core.internal.util.scanner.classpath;

import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.Set;
import java.util.TreeSet;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class JarFileClassPathLocationScanner implements ClassPathLocationScanner {
   private final String separator;

   JarFileClassPathLocationScanner(String separator) {
      this.separator = separator;
   }

   public Set<String> findResourceNames(String location, URL locationUrl) throws IOException {
      JarFile jarFile = this.getJarFromUrl(locationUrl);

      Set var5;
      try {
         String prefix = jarFile.getName().toLowerCase().endsWith(".war") ? "WEB-INF/classes/" : "";
         var5 = this.findResourceNamesFromJarFile(jarFile, prefix, location);
      } finally {
         jarFile.close();
      }

      return var5;
   }

   private JarFile getJarFromUrl(URL locationUrl) throws IOException {
      URLConnection con = locationUrl.openConnection();
      if (con instanceof JarURLConnection) {
         JarURLConnection jarCon = (JarURLConnection)con;
         jarCon.setUseCaches(false);
         return jarCon.getJarFile();
      } else {
         String urlFile = locationUrl.getFile();
         int separatorIndex = urlFile.indexOf(this.separator);
         if (separatorIndex != -1) {
            String jarFileUrl = urlFile.substring(0, separatorIndex);
            if (jarFileUrl.startsWith("file:")) {
               try {
                  return new JarFile((new URL(jarFileUrl)).toURI().getSchemeSpecificPart());
               } catch (URISyntaxException var7) {
                  return new JarFile(jarFileUrl.substring("file:".length()));
               }
            } else {
               return new JarFile(jarFileUrl);
            }
         } else {
            return new JarFile(urlFile);
         }
      }
   }

   private Set<String> findResourceNamesFromJarFile(JarFile jarFile, String prefix, String location) throws IOException {
      String toScan = prefix + location + (location.endsWith("/") ? "" : "/");
      Set<String> resourceNames = new TreeSet();
      Enumeration entries = jarFile.entries();

      while(entries.hasMoreElements()) {
         String entryName = ((JarEntry)entries.nextElement()).getName();
         if (entryName.startsWith(toScan)) {
            resourceNames.add(entryName.substring(prefix.length()));
         }
      }

      return resourceNames;
   }
}
