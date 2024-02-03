package org.flywaydb.core.internal.util.scanner.classpath;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Pattern;
import org.flywaydb.core.api.logging.Log;
import org.flywaydb.core.api.logging.LogFactory;
import org.flywaydb.core.internal.util.ClassUtils;
import org.flywaydb.core.internal.util.FeatureDetector;
import org.flywaydb.core.internal.util.Location;
import org.flywaydb.core.internal.util.StringUtils;
import org.flywaydb.core.internal.util.UrlUtils;
import org.flywaydb.core.internal.util.scanner.LoadableResource;
import org.flywaydb.core.internal.util.scanner.classpath.jboss.JBossVFSv2UrlResolver;
import org.flywaydb.core.internal.util.scanner.classpath.jboss.JBossVFSv3ClassPathLocationScanner;

public class ClassPathScanner implements ResourceAndClassScanner {
   private static final Log LOG = LogFactory.getLog(ClassPathScanner.class);
   private final ClassLoader classLoader;
   private final Map<Location, List<URL>> locationUrlCache = new HashMap();
   private final Map<String, ClassPathLocationScanner> locationScannerCache = new HashMap();
   private final Map<ClassPathLocationScanner, Map<URL, Set<String>>> resourceNameCache = new HashMap();

   public ClassPathScanner(ClassLoader classLoader) {
      this.classLoader = classLoader;
   }

   public LoadableResource[] scanForResources(Location path, String prefix, String... suffixes) throws IOException {
      LOG.debug("Scanning for classpath resources at '" + path + "' (Prefix: '" + prefix + "', Suffixes: '" + StringUtils.arrayToCommaDelimitedString(suffixes) + "')");
      Set<LoadableResource> resources = new TreeSet();
      Set<String> resourceNames = this.findResourceNames(path, prefix, suffixes);
      Iterator var6 = resourceNames.iterator();

      while(var6.hasNext()) {
         String resourceName = (String)var6.next();
         resources.add(new ClassPathResource(resourceName, this.classLoader));
         LOG.debug("Found resource: " + resourceName);
      }

      return (LoadableResource[])resources.toArray(new LoadableResource[resources.size()]);
   }

   public Class<?>[] scanForClasses(Location location, Class<?> implementedInterface) throws Exception {
      LOG.debug("Scanning for classes at '" + location + "' (Implementing: '" + implementedInterface.getName() + "')");
      List<Class<?>> classes = new ArrayList();
      Set<String> resourceNames = this.findResourceNames(location, "", ".class");
      Iterator var5 = resourceNames.iterator();

      while(var5.hasNext()) {
         String resourceName = (String)var5.next();
         String className = this.toClassName(resourceName);

         Class clazz;
         try {
            clazz = this.classLoader.loadClass(className);
            if (!implementedInterface.isAssignableFrom(clazz)) {
               continue;
            }

            if (Modifier.isAbstract(clazz.getModifiers()) || clazz.isEnum() || clazz.isAnonymousClass()) {
               LOG.debug("Skipping non-instantiable class: " + className);
               continue;
            }

            ClassUtils.instantiate(className, this.classLoader);
         } catch (InternalError var10) {
            LOG.debug("Skipping invalid class: " + className);
            continue;
         } catch (IncompatibleClassChangeError var11) {
            LOG.warn("Skipping incompatibly changed class: " + className);
            continue;
         } catch (NoClassDefFoundError var12) {
            LOG.debug("Skipping non-loadable class: " + className);
            continue;
         }

         classes.add(clazz);
         LOG.debug("Found class: " + className);
      }

      return (Class[])classes.toArray(new Class[classes.size()]);
   }

   private String toClassName(String resourceName) {
      String nameWithDots = resourceName.replace("/", ".");
      return nameWithDots.substring(0, nameWithDots.length() - ".class".length());
   }

   private Set<String> findResourceNames(Location location, String prefix, String... suffixes) throws IOException {
      Set<String> resourceNames = new TreeSet();
      List<URL> locationUrls = this.getLocationUrlsForPath(location);
      Iterator var6 = locationUrls.iterator();

      while(var6.hasNext()) {
         URL locationUrl = (URL)var6.next();
         LOG.debug("Scanning URL: " + locationUrl.toExternalForm());
         UrlResolver urlResolver = this.createUrlResolver(locationUrl.getProtocol());
         URL resolvedUrl = urlResolver.toStandardJavaUrl(locationUrl);
         String protocol = resolvedUrl.getProtocol();
         ClassPathLocationScanner classPathLocationScanner = this.createLocationScanner(protocol);
         if (classPathLocationScanner == null) {
            String scanRoot = UrlUtils.toFilePath(resolvedUrl);
            LOG.warn("Unable to scan location: " + scanRoot + " (unsupported protocol: " + protocol + ")");
         } else {
            Set<String> names = (Set)((Map)this.resourceNameCache.get(classPathLocationScanner)).get(resolvedUrl);
            if (names == null) {
               names = classPathLocationScanner.findResourceNames(location.getPath(), resolvedUrl);
               ((Map)this.resourceNameCache.get(classPathLocationScanner)).put(resolvedUrl, names);
            }

            resourceNames.addAll(names);
         }
      }

      boolean locationResolved = !locationUrls.isEmpty();
      if (!locationResolved && this.classLoader instanceof URLClassLoader) {
         URLClassLoader urlClassLoader = (URLClassLoader)this.classLoader;
         URL[] var28 = urlClassLoader.getURLs();
         int var29 = var28.length;

         label173:
         for(int var30 = 0; var30 < var29; ++var30) {
            URL url = var28[var30];
            if ("file".equals(url.getProtocol()) && url.getPath().endsWith(".jar") && !url.getPath().matches(".*" + Pattern.quote("/jre/lib/") + ".*")) {
               JarFile jarFile;
               try {
                  try {
                     jarFile = new JarFile(url.toURI().getSchemeSpecificPart());
                  } catch (URISyntaxException var23) {
                     jarFile = new JarFile(url.getPath().substring("file:".length()));
                  }
               } catch (SecurityException var24) {
                  LOG.warn("Skipping unloadable jar file: " + url + " (" + var24.getMessage() + ")");
                  continue;
               }

               try {
                  Enumeration entries = jarFile.entries();

                  while(true) {
                     String entryName;
                     do {
                        if (!entries.hasMoreElements()) {
                           continue label173;
                        }

                        entryName = ((JarEntry)entries.nextElement()).getName();
                     } while(!entryName.startsWith(location.getPath()));

                     locationResolved = true;
                     String[] var15 = suffixes;
                     int var16 = suffixes.length;

                     for(int var17 = 0; var17 < var16; ++var17) {
                        String suffix = var15[var17];
                        if (entryName.endsWith(suffix)) {
                           resourceNames.add(entryName);
                        }
                     }
                  }
               } finally {
                  jarFile.close();
               }
            }
         }
      }

      if (!locationResolved) {
         LOG.warn("Unable to resolve location " + location);
      }

      return this.filterResourceNames(resourceNames, prefix, suffixes);
   }

   private List<URL> getLocationUrlsForPath(Location location) throws IOException {
      if (this.locationUrlCache.containsKey(location)) {
         return (List)this.locationUrlCache.get(location);
      } else {
         LOG.debug("Determining location urls for " + location + " using ClassLoader " + this.classLoader + " ...");
         List<URL> locationUrls = new ArrayList();
         Enumeration urls;
         if (this.classLoader.getClass().getName().startsWith("com.ibm")) {
            urls = this.classLoader.getResources(location.getPath() + "/flyway.location");
            if (!urls.hasMoreElements()) {
               LOG.warn("Unable to resolve location " + location + " (ClassLoader: " + this.classLoader + ") On WebSphere an empty file named flyway.location must be present on the classpath location for WebSphere to find it!");
            }

            while(urls.hasMoreElements()) {
               URL url = (URL)urls.nextElement();
               locationUrls.add(new URL(URLDecoder.decode(url.toExternalForm(), "UTF-8").replace("/flyway.location", "")));
            }
         } else {
            urls = this.classLoader.getResources(location.getPath());

            while(urls.hasMoreElements()) {
               locationUrls.add((URL)urls.nextElement());
            }
         }

         this.locationUrlCache.put(location, locationUrls);
         return locationUrls;
      }
   }

   private UrlResolver createUrlResolver(String protocol) {
      return (UrlResolver)((new FeatureDetector(this.classLoader)).isJBossVFSv2Available() && protocol.startsWith("vfs") ? new JBossVFSv2UrlResolver() : new DefaultUrlResolver());
   }

   private ClassPathLocationScanner createLocationScanner(String protocol) {
      if (this.locationScannerCache.containsKey(protocol)) {
         return (ClassPathLocationScanner)this.locationScannerCache.get(protocol);
      } else if ("file".equals(protocol)) {
         FileSystemClassPathLocationScanner locationScanner = new FileSystemClassPathLocationScanner();
         this.locationScannerCache.put(protocol, locationScanner);
         this.resourceNameCache.put(locationScanner, new HashMap());
         return locationScanner;
      } else if (!"jar".equals(protocol) && !this.isTomcat(protocol) && !this.isWebLogic(protocol) && !this.isWebSphere(protocol)) {
         FeatureDetector featureDetector = new FeatureDetector(this.classLoader);
         if (featureDetector.isJBossVFSv3Available() && "vfs".equals(protocol)) {
            JBossVFSv3ClassPathLocationScanner locationScanner = new JBossVFSv3ClassPathLocationScanner();
            this.locationScannerCache.put(protocol, locationScanner);
            this.resourceNameCache.put(locationScanner, new HashMap());
            return locationScanner;
         } else if (!featureDetector.isOsgiFrameworkAvailable() || !this.isFelix(protocol) && !this.isEquinox(protocol)) {
            return null;
         } else {
            OsgiClassPathLocationScanner locationScanner = new OsgiClassPathLocationScanner();
            this.locationScannerCache.put(protocol, locationScanner);
            this.resourceNameCache.put(locationScanner, new HashMap());
            return locationScanner;
         }
      } else {
         String separator = this.isTomcat(protocol) ? "*/" : "!/";
         ClassPathLocationScanner locationScanner = new JarFileClassPathLocationScanner(separator);
         this.locationScannerCache.put(protocol, locationScanner);
         this.resourceNameCache.put(locationScanner, new HashMap());
         return locationScanner;
      }
   }

   private boolean isEquinox(String protocol) {
      return "bundleresource".equals(protocol);
   }

   private boolean isFelix(String protocol) {
      return "bundle".equals(protocol);
   }

   private boolean isWebSphere(String protocol) {
      return "wsjar".equals(protocol);
   }

   private boolean isWebLogic(String protocol) {
      return "zip".equals(protocol);
   }

   private boolean isTomcat(String protocol) {
      return "war".equals(protocol);
   }

   private Set<String> filterResourceNames(Set<String> resourceNames, String prefix, String[] suffixes) {
      Set<String> filteredResourceNames = new TreeSet();
      Iterator var5 = resourceNames.iterator();

      while(var5.hasNext()) {
         String resourceName = (String)var5.next();
         String fileName = resourceName.substring(resourceName.lastIndexOf("/") + 1);
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
         if ((!StringUtils.hasLength(prefix) || fileName.startsWith(prefix)) && fileName.endsWith(suffix) && fileName.length() > (prefix + suffix).length()) {
            return true;
         }
      }

      return false;
   }
}
