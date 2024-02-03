package org.flywaydb.core.internal.util.scanner;

public interface LoadableResource extends Resource {
   String loadAsString(String var1);

   byte[] loadAsBytes();
}
