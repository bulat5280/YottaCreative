package org.flywaydb.core.internal.util.scanner.classpath.android;

import android.content.Context;
import dalvik.system.DexFile;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import org.flywaydb.core.api.FlywayException;
import org.flywaydb.core.api.android.ContextHolder;
import org.flywaydb.core.api.logging.Log;
import org.flywaydb.core.api.logging.LogFactory;
import org.flywaydb.core.internal.util.ClassUtils;
import org.flywaydb.core.internal.util.Location;
import org.flywaydb.core.internal.util.scanner.LoadableResource;
import org.flywaydb.core.internal.util.scanner.classpath.ResourceAndClassScanner;

public class AndroidScanner implements ResourceAndClassScanner {
   private static final Log LOG = LogFactory.getLog(AndroidScanner.class);
   private final Context context;
   private final ClassLoader classLoader;

   public AndroidScanner(ClassLoader classLoader) {
      this.classLoader = classLoader;
      this.context = ContextHolder.getContext();
      if (this.context == null) {
         throw new FlywayException("Unable to scan for Migrations! Context not set. Within an activity you can fix this with org.flywaydb.core.api.android.ContextHolder.setContext(this);");
      }
   }

   public LoadableResource[] scanForResources(Location location, String prefix, String[] suffixes) throws Exception {
      List<LoadableResource> resources = new ArrayList();
      String path = location.getPath();
      String[] var6 = this.context.getAssets().list(path);
      int var7 = var6.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         String asset = var6[var8];
         if (this.assetMatches(asset, prefix, suffixes)) {
            resources.add(new AndroidResource(this.context.getAssets(), path, asset));
         } else {
            LOG.debug("Filtering out asset: " + asset);
         }
      }

      return (LoadableResource[])resources.toArray(new LoadableResource[resources.size()]);
   }

   private boolean assetMatches(String asset, String prefix, String[] suffixes) {
      String[] var4 = suffixes;
      int var5 = suffixes.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         String suffix = var4[var6];
         if (asset.startsWith(prefix) && asset.endsWith(suffix) && asset.length() > (prefix + suffix).length()) {
            return true;
         }
      }

      return false;
   }

   public Class<?>[] scanForClasses(Location location, Class<?> implementedInterface) throws Exception {
      String pkg = location.getPath().replace("/", ".");
      List<Class> classes = new ArrayList();
      DexFile dex = null;

      try {
         dex = new DexFile(this.context.getApplicationInfo().sourceDir);
         Enumeration entries = dex.entries();

         while(entries.hasMoreElements()) {
            String className = (String)entries.nextElement();
            if (className.startsWith(pkg)) {
               Class<?> clazz = this.classLoader.loadClass(className);
               if (Modifier.isAbstract(clazz.getModifiers())) {
                  LOG.debug("Skipping abstract class: " + className);
               } else if (implementedInterface.isAssignableFrom(clazz)) {
                  ClassUtils.instantiate(className, this.classLoader);
                  classes.add(clazz);
                  LOG.debug("Found class: " + className);
               }
            }
         }
      } finally {
         if (dex != null) {
            dex.close();
         }

      }

      return (Class[])classes.toArray(new Class[classes.size()]);
   }
}
