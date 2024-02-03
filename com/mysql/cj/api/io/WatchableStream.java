package com.mysql.cj.api.io;

public interface WatchableStream {
   void setWatcher(OutputStreamWatcher var1);

   int size();

   byte[] toByteArray();

   void write(byte[] var1, int var2, int var3);
}
