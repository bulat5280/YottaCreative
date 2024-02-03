package org.jooq.util;

import java.io.File;

public class TextWriter extends GeneratorWriter<TextWriter> {
   protected TextWriter(File file) {
      this(file, (String)null);
   }

   protected TextWriter(File file, String encoding) {
      super(file, encoding);
   }
}
