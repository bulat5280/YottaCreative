package org.flywaydb.core.internal.util.scanner.classpath;

import java.io.IOException;
import java.net.URL;

public class DefaultUrlResolver implements UrlResolver {
   public URL toStandardJavaUrl(URL url) throws IOException {
      return url;
   }
}
