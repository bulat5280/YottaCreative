package org.apache.commons.pool2.impl;

import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SecurityManagerCallStack implements CallStack {
   private final String messageFormat;
   private final DateFormat dateFormat;
   private final SecurityManagerCallStack.PrivateSecurityManager securityManager;
   private volatile SecurityManagerCallStack.Snapshot snapshot;

   public SecurityManagerCallStack(String messageFormat, boolean useTimestamp) {
      this.messageFormat = messageFormat;
      this.dateFormat = useTimestamp ? new SimpleDateFormat(messageFormat) : null;
      this.securityManager = (SecurityManagerCallStack.PrivateSecurityManager)AccessController.doPrivileged(new PrivilegedAction<SecurityManagerCallStack.PrivateSecurityManager>() {
         public SecurityManagerCallStack.PrivateSecurityManager run() {
            return new SecurityManagerCallStack.PrivateSecurityManager();
         }
      });
   }

   public boolean printStackTrace(PrintWriter writer) {
      SecurityManagerCallStack.Snapshot snapshotRef = this.snapshot;
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
         Iterator i$ = snapshotRef.stack.iterator();

         while(i$.hasNext()) {
            WeakReference<Class<?>> reference = (WeakReference)i$.next();
            writer.println(reference.get());
         }

         return true;
      }
   }

   public void fillInStackTrace() {
      this.snapshot = new SecurityManagerCallStack.Snapshot(this.securityManager.getCallStack());
   }

   public void clear() {
      this.snapshot = null;
   }

   private static class Snapshot {
      private final long timestamp;
      private final List<WeakReference<Class<?>>> stack;

      private Snapshot(List<WeakReference<Class<?>>> stack) {
         this.timestamp = System.currentTimeMillis();
         this.stack = stack;
      }

      // $FF: synthetic method
      Snapshot(List x0, Object x1) {
         this(x0);
      }
   }

   private static class PrivateSecurityManager extends SecurityManager {
      private PrivateSecurityManager() {
      }

      private List<WeakReference<Class<?>>> getCallStack() {
         Class<?>[] classes = this.getClassContext();
         List<WeakReference<Class<?>>> stack = new ArrayList(classes.length);
         Class[] arr$ = classes;
         int len$ = classes.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            Class<?> klass = arr$[i$];
            stack.add(new WeakReference(klass));
         }

         return stack;
      }

      // $FF: synthetic method
      PrivateSecurityManager(Object x0) {
         this();
      }
   }
}
