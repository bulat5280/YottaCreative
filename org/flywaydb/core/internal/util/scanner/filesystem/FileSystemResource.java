package org.flywaydb.core.internal.util.scanner.filesystem;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import org.flywaydb.core.api.FlywayException;
import org.flywaydb.core.internal.util.FileCopyUtils;
import org.flywaydb.core.internal.util.scanner.LoadableResource;

public class FileSystemResource implements LoadableResource, Comparable<FileSystemResource> {
   private File location;

   public FileSystemResource(String location) {
      this.location = new File(location.replace("\\", "/").replace("//", "/"));
   }

   public String getLocation() {
      return this.location.getPath().replace("\\", "/");
   }

   public String getLocationOnDisk() {
      return this.location.getAbsolutePath();
   }

   public String loadAsString(String encoding) {
      try {
         InputStream inputStream = new FileInputStream(this.location);
         Reader reader = new InputStreamReader(inputStream, Charset.forName(encoding));
         return FileCopyUtils.copyToString(reader);
      } catch (IOException var4) {
         throw new FlywayException("Unable to load filesystem resource: " + this.location.getPath() + " (encoding: " + encoding + ")", var4);
      }
   }

   public byte[] loadAsBytes() {
      try {
         InputStream inputStream = new FileInputStream(this.location);
         return FileCopyUtils.copyToByteArray(inputStream);
      } catch (IOException var2) {
         throw new FlywayException("Unable to load filesystem resource: " + this.location.getPath(), var2);
      }
   }

   public String getFilename() {
      return this.location.getName();
   }

   public int compareTo(FileSystemResource o) {
      return this.location.compareTo(o.location);
   }
}
