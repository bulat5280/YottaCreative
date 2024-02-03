package org.flywaydb.core.internal.util.scanner.classpath;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import org.flywaydb.core.api.FlywayException;
import org.flywaydb.core.internal.util.FileCopyUtils;
import org.flywaydb.core.internal.util.scanner.LoadableResource;

public class ClassPathResource implements Comparable<ClassPathResource>, LoadableResource {
   private String location;
   private ClassLoader classLoader;

   public ClassPathResource(String location, ClassLoader classLoader) {
      this.location = location;
      this.classLoader = classLoader;
   }

   public String getLocation() {
      return this.location;
   }

   public String getLocationOnDisk() {
      URL url = this.getUrl();
      if (url == null) {
         throw new FlywayException("Unable to location resource on disk: " + this.location);
      } else {
         try {
            return (new File(URLDecoder.decode(url.getPath(), "UTF-8"))).getAbsolutePath();
         } catch (UnsupportedEncodingException var3) {
            throw new FlywayException("Unknown encoding: UTF-8", var3);
         }
      }
   }

   private URL getUrl() {
      return this.classLoader.getResource(this.location);
   }

   public String loadAsString(String encoding) {
      try {
         InputStream inputStream = this.classLoader.getResourceAsStream(this.location);
         if (inputStream == null) {
            throw new FlywayException("Unable to obtain inputstream for resource: " + this.location);
         } else {
            Reader reader = new InputStreamReader(inputStream, Charset.forName(encoding));
            return FileCopyUtils.copyToString(reader);
         }
      } catch (IOException var4) {
         throw new FlywayException("Unable to load resource: " + this.location + " (encoding: " + encoding + ")", var4);
      }
   }

   public byte[] loadAsBytes() {
      try {
         InputStream inputStream = this.classLoader.getResourceAsStream(this.location);
         if (inputStream == null) {
            throw new FlywayException("Unable to obtain inputstream for resource: " + this.location);
         } else {
            return FileCopyUtils.copyToByteArray(inputStream);
         }
      } catch (IOException var2) {
         throw new FlywayException("Unable to load resource: " + this.location, var2);
      }
   }

   public String getFilename() {
      return this.location.substring(this.location.lastIndexOf("/") + 1);
   }

   public boolean exists() {
      return this.getUrl() != null;
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         ClassPathResource that = (ClassPathResource)o;
         return this.location.equals(that.location);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.location.hashCode();
   }

   public int compareTo(ClassPathResource o) {
      return this.location.compareTo(o.location);
   }
}
