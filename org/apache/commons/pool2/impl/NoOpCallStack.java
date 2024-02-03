package org.apache.commons.pool2.impl;

import java.io.PrintWriter;

public class NoOpCallStack implements CallStack {
   public static final CallStack INSTANCE = new NoOpCallStack();

   private NoOpCallStack() {
   }

   public boolean printStackTrace(PrintWriter writer) {
      return false;
   }

   public void fillInStackTrace() {
   }

   public void clear() {
   }
}
