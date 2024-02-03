package org.flywaydb.core.internal.util;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;

public class UrlUtils {
   private UrlUtils() {
   }

   public static String toFilePath(URL url) {
      try {
         String filePath = (new File(URLDecoder.decode(url.getPath().replace("+", "%2b"), "UTF-8"))).getAbsolutePath();
         return filePath.endsWith("/") ? filePath.substring(0, filePath.length() - 1) : filePath;
      } catch (UnsupportedEncodingException var2) {
         throw new IllegalStateException("Can never happen", var2);
      }
   }
}
