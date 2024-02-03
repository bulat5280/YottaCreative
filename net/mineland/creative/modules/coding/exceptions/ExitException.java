package net.mineland.creative.modules.coding.exceptions;

import java.io.PrintStream;
import java.io.PrintWriter;

public abstract class ExitException extends RuntimeException {
   public StackTraceElement[] getStackTrace() {
      return new StackTraceElement[0];
   }

   public void printStackTrace() {
   }

   public void printStackTrace(PrintStream s) {
   }

   public void printStackTrace(PrintWriter s) {
   }

   public String getMessage() {
      return "прерывание программы, это не ошибка";
   }
}
