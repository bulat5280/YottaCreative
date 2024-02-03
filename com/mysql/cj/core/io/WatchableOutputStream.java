package com.mysql.cj.core.io;

import com.mysql.cj.api.io.OutputStreamWatcher;
import com.mysql.cj.api.io.WatchableStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class WatchableOutputStream extends ByteArrayOutputStream implements WatchableStream {
   private OutputStreamWatcher watcher;

   public void close() throws IOException {
      super.close();
      if (this.watcher != null) {
         this.watcher.streamClosed(this);
      }

   }

   public void setWatcher(OutputStreamWatcher watcher) {
      this.watcher = watcher;
   }
}
