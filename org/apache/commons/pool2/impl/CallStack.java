package org.apache.commons.pool2.impl;

import java.io.PrintWriter;

public interface CallStack {
   boolean printStackTrace(PrintWriter var1);

   void fillInStackTrace();

   void clear();
}
