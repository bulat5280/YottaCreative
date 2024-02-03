package org.flywaydb.core.internal.util;

import java.io.File;
import org.flywaydb.core.api.FlywayException;

public final class Location implements Comparable<Location> {
   private static final String CLASSPATH_PREFIX = "classpath:";
   public static final String FILESYSTEM_PREFIX = "filesystem:";
   private final String prefix;
   private String path;

   public Location(String descriptor) {
      String normalizedDescriptor = descriptor.trim().replace("\\\\", "\\").replace("\\", "/");
      if (normalizedDescriptor.contains(":")) {
         this.prefix = normalizedDescriptor.substring(0, normalizedDescriptor.indexOf(":") + 1);
         this.path = normalizedDescriptor.substring(normalizedDescriptor.indexOf(":") + 1);
      } else {
         this.prefix = "classpath:";
         this.path = normalizedDescriptor;
      }

      if (this.isClassPath()) {
         this.path = this.path.replace(".", "/");
         if (this.path.startsWith("/")) {
            this.path = this.path.substring(1);
         }
      } else {
         if (!this.isFileSystem()) {
            throw new FlywayException("Unknown prefix for location (should be either filesystem: or classpath:): " + normalizedDescriptor);
         }

         this.path = (new File(this.path)).getPath().replace("\\", "/");
      }

      if (this.path.endsWith("/")) {
         this.path = this.path.substring(0, this.path.length() - 1);
      }

   }

   public boolean isClassPath() {
      return "classpath:".equals(this.prefix);
   }

   public boolean isFileSystem() {
      return "filesystem:".equals(this.prefix);
   }

   public boolean isParentOf(Location other) {
      return (other.getDescriptor() + "/").startsWith(this.getDescriptor() + "/");
   }

   public String getPrefix() {
      return this.prefix;
   }

   public String getPath() {
      return this.path;
   }

   public String getDescriptor() {
      return this.prefix + this.path;
   }

   public int compareTo(Location o) {
      return this.getDescriptor().compareTo(o.getDescriptor());
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         Location location = (Location)o;
         return this.getDescriptor().equals(location.getDescriptor());
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.getDescriptor().hashCode();
   }

   public String toString() {
      return this.getDescriptor();
   }
}
