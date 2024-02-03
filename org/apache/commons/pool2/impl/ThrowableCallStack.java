package org.apache.commons.pool2.impl;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class ThrowableCallStack implements CallStack {
   private final String messageFormat;
   private final DateFormat dateFormat;
   private volatile ThrowableCallStack.Snapshot snapshot;

   public ThrowableCallStack(String messageFormat, boolean useTimestamp) {
      this.messageFormat = messageFormat;
      this.dateFormat = useTimestamp ? new SimpleDateFormat(messageFormat) : null;
   }

   public synchronized boolean printStackTrace(PrintWriter writer) {
      ThrowableCallStack.Snapshot snapshotRef = this.snapshot;
      if (snapshotRef == null) {
         return false;
      } else {
         String message;
         if (this.dateFormat == null) {
            message = this.messageFormat;
         } else {
            synchronized(this.dateFormat) {
               message = this.dateFormat.format(snapshotRef.timestamp);
            }
         }

         writer.println(message);
         snapshotRef.printStackTrace(writer);
         return true;
      }
   }

   public void fillInStackTrace() {
      this.snapshot = new ThrowableCallStack.Snapshot();
   }

   public void clear() {
      this.snapshot = null;
   }

   private static class Snapshot extends Throwable {
      private static final long serialVersionUID = 1L;
      private final long timestamp;

      private Snapshot() {
         this.timestamp = System.currentTimeMillis();
      }

      // $FF: synthetic method
      Snapshot(Object x0) {
         this();
      }
   }
}
