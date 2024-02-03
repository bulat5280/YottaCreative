package org.flywaydb.core.internal.util.scanner.classpath;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

public class OsgiClassPathLocationScanner implements ClassPathLocationScanner {
   private static final Pattern bundleIdPattern = Pattern.compile("^\\d+");

   public Set<String> findResourceNames(String location, URL locationUrl) throws IOException {
      Set<String> resourceNames = new TreeSet();
      Bundle bundle = this.getTargetBundleOrCurrent(FrameworkUtil.getBundle(this.getClass()), locationUrl);
      Enumeration<URL> entries = bundle.findEntries(locationUrl.getPath(), "*", true);
      if (entries != null) {
         while(entries.hasMoreElements()) {
            URL entry = (URL)entries.nextElement();
            String resourceName = this.getPathWithoutLeadingSlash(entry);
            resourceNames.add(resourceName);
         }
      }

      return resourceNames;
   }

   private Bundle getTargetBundleOrCurrent(Bundle currentBundle, URL locationUrl) {
      try {
         Bundle targetBundle = currentBundle.getBundleContext().getBundle(this.getBundleId(locationUrl.getHost()));
         return targetBundle != null ? targetBundle : currentBundle;
      } catch (Exception var4) {
         return currentBundle;
      }
   }

   private long getBundleId(String host) {
      Matcher matcher = bundleIdPattern.matcher(host);
      if (matcher.find()) {
         return Double.valueOf(matcher.group()).longValue();
      } else {
         throw new IllegalArgumentException("There's no bundleId in passed URL");
      }
   }

   private String getPathWithoutLeadingSlash(URL entry) {
      String path = entry.getPath();
      return path.startsWith("/") ? path.substring(1) : path;
   }
}
