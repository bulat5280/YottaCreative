package org.flywaydb.core.internal.util.scanner;

public interface Resource {
   String getLocation();

   String getLocationOnDisk();

   String getFilename();
}
