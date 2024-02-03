package org.jooq.exception;

public class ControlFlowSignal extends RuntimeException {
   private static final long serialVersionUID = -3269663230218616147L;

   public ControlFlowSignal() {
   }

   public ControlFlowSignal(String message) {
      super(message);
   }

   public Throwable fillInStackTrace() {
      return this;
   }
}
