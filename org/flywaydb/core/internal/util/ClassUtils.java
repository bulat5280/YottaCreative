package org.flywaydb.core.internal.util;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLDecoder;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.List;
import org.flywaydb.core.api.FlywayException;
import org.flywaydb.core.api.logging.Log;
import org.flywaydb.core.api.logging.LogFactory;

public class ClassUtils {
   private static final Log LOG = LogFactory.getLog(ClassUtils.class);

   private ClassUtils() {
   }

   public static synchronized <T> T instantiate(String className, ClassLoader classLoader) {
      try {
         return Class.forName(className, true, classLoader).getDeclaredConstructor().newInstance();
      } catch (Exception var3) {
         throw new FlywayException("Unable to instantiate class " + className + " : " + var3.getMessage(), var3);
      }
   }

   public static <T> List<T> instantiateAll(String[] classes, ClassLoader classLoader) {
      List<T> clazzes = new ArrayList();
      String[] var3 = classes;
      int var4 = classes.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String clazz = var3[var5];
         if (StringUtils.hasLength(clazz)) {
            clazzes.add(instantiate(clazz, classLoader));
         }
      }

      return clazzes;
   }

   public static boolean isPresent(String className, ClassLoader classLoader) {
      try {
         classLoader.loadClass(className);
         return true;
      } catch (Throwable var3) {
         return false;
      }
   }

   public static String getShortName(Class<?> aClass) {
      String name = aClass.getName();
      return name.substring(name.lastIndexOf(".") + 1);
   }

   public static String getLocationOnDisk(Class<?> aClass) {
      try {
         ProtectionDomain protectionDomain = aClass.getProtectionDomain();
         if (protectionDomain == null) {
            return null;
         } else {
            CodeSource codeSource = protectionDomain.getCodeSource();
            if (codeSource == null) {
               return null;
            } else {
               String url = codeSource.getLocation().getPath();
               return URLDecoder.decode(url, "UTF-8");
            }
         }
      } catch (UnsupportedEncodingException var4) {
         return null;
      }
   }

   public static ClassLoader addJarOrDirectoryToClasspath(ClassLoader classLoader, String name) throws IOException {
      LOG.debug("Adding location to classpath: " + name);

      try {
         URL url = (new File(name)).toURI().toURL();
         return new URLClassLoader(new URL[]{url}, classLoader);
      } catch (Exception var3) {
         throw new FlywayException("Unable to load " + name, var3);
      }
   }
}
