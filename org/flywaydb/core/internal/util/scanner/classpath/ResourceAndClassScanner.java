package org.flywaydb.core.internal.util.scanner.classpath;

import org.flywaydb.core.internal.util.Location;
import org.flywaydb.core.internal.util.scanner.LoadableResource;

public interface ResourceAndClassScanner {
   LoadableResource[] scanForResources(Location var1, String var2, String[] var3) throws Exception;

   Class<?>[] scanForClasses(Location var1, Class<?> var2) throws Exception;
}
