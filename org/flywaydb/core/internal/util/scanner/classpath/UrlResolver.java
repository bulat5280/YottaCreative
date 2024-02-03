package org.flywaydb.core.internal.util.scanner.classpath;

import java.io.IOException;
import java.net.URL;

public interface UrlResolver {
   URL toStandardJavaUrl(URL var1) throws IOException;
}
