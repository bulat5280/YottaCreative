package org.flywaydb.core.internal.util.scanner.classpath;

import java.io.IOException;
import java.net.URL;
import java.util.Set;

public interface ClassPathLocationScanner {
   Set<String> findResourceNames(String var1, URL var2) throws IOException;
}
