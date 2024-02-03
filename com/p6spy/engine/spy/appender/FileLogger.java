package com.p6spy.engine.spy.appender;

import com.p6spy.engine.spy.P6SpyOptions;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class FileLogger extends StdoutLogger {
   private String fileName = null;
   private PrintStream printStream = null;

   private void init() {
      if (this.fileName == null) {
         throw new IllegalStateException("setLogfile() must be called before init()");
      } else {
         try {
            this.printStream = new PrintStream(new FileOutputStream(this.fileName, P6SpyOptions.getActiveInstance().getAppend()));
         } catch (IOException var2) {
            var2.printStackTrace(System.err);
         }

      }
   }

   protected PrintStream getStream() {
      if (this.printStream == null) {
         synchronized(this) {
            if (this.printStream == null) {
               this.init();
            }
         }
      }

      return this.printStream;
   }

   public void setLogfile(String fileName) {
      this.fileName = fileName;
   }
}
