package net.mineland.core.bukkit.modules.command;

import java.io.PrintStream;
import java.io.PrintWriter;

public class CommandException extends RuntimeException {
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
      return "остановка команды, это не ошибка";
   }
}
