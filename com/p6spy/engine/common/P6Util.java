package com.p6spy.engine.common;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;
import java.util.regex.Pattern;

public class P6Util {
   static Pattern lineBreakPattern = Pattern.compile("(\\r?\\n)+");

   public static String singleLine(String str) {
      return lineBreakPattern.matcher(str).replaceAll(" ");
   }

   public static int parseInt(String i, int defaultValue) {
      if (i != null && !i.isEmpty()) {
         try {
            return Integer.parseInt(i);
         } catch (NumberFormatException var3) {
            P6LogQuery.error("NumberFormatException occured parsing value " + i);
            return defaultValue;
         }
      } else {
         return defaultValue;
      }
   }

   public static boolean isTrue(String s, boolean defaultValue) {
      if (s == null) {
         return defaultValue;
      } else {
         return "1".equals(s) || "true".equalsIgnoreCase(s.trim());
      }
   }

   public static URL locateFile(String file) {
      URL result = null;

      try {
         File fp = new File(file);
         if (fp.exists()) {
            result = fp.toURI().toURL();
         }

         if (result == null) {
            result = locateOnClassPath(file);
         }
      } catch (Exception var4) {
      }

      return result;
   }

   public static URL locateOnClassPath(String filename) {
      URL result = Thread.currentThread().getContextClassLoader().getResource(filename);
      if (result == null) {
         result = P6Util.class.getClassLoader().getResource(filename);
      }

      if (result == null) {
         result = ClassLoader.getSystemResource(filename);
      }

      return result;
   }

   public static Class<?> forName(String name) throws ClassNotFoundException {
      ClassLoader ctxLoader = null;

      try {
         ctxLoader = Thread.currentThread().getContextClassLoader();
         return Class.forName(name, true, ctxLoader);
      } catch (ClassNotFoundException var3) {
      } catch (SecurityException var4) {
      }

      return Class.forName(name);
   }

   public static String getPath(URL theURL) {
      String file = theURL.getFile();
      String path = null;
      if (file != null) {
         int q = file.lastIndexOf(63);
         if (q != -1) {
            path = file.substring(0, q);
         } else {
            path = file;
         }
      }

      return path;
   }

   public static Map<String, String> getPropertiesMap(Properties properties) {
      if (null == properties) {
         return null;
      } else {
         HashMap<String, String> map = new HashMap();
         Iterator var2 = properties.entrySet().iterator();

         while(var2.hasNext()) {
            Entry<Object, Object> entry = (Entry)var2.next();
            map.put((String)entry.getKey(), (String)entry.getValue());
         }

         return map;
      }
   }

   public static List<String> parseCSVList(String csv) {
      if (csv == null) {
         return null;
      } else {
         return (List)(csv.isEmpty() ? Collections.emptyList() : new ArrayList(Arrays.asList(csv.split(","))));
      }
   }

   public static Properties getProperties(Map<String, String> map) {
      if (map == null) {
         return null;
      } else {
         Properties properties = new Properties();
         properties.putAll(map);
         return properties;
      }
   }

   public static String joinNullSafe(Collection<String> collection, String separator) {
      if (null != collection && !collection.isEmpty()) {
         if (null == separator) {
            separator = "";
         }

         StringBuilder sb = new StringBuilder();

         String str;
         for(Iterator var3 = collection.iterator(); var3.hasNext(); sb.append(str)) {
            str = (String)var3.next();
            if (sb.length() > 0) {
               sb.append(separator);
            }
         }

         return sb.toString();
      } else {
         return "";
      }
   }
}
