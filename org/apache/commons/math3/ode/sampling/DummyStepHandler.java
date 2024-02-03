package org.apache.commons.math3.ode.sampling;

public class DummyStepHandler implements StepHandler {
   private DummyStepHandler() {
   }

   public static DummyStepHandler getInstance() {
      return DummyStepHandler.LazyHolder.INSTANCE;
   }

   public void init(double t0, double[] y0, double t) {
   }

   public void handleStep(StepInterpolator interpolator, boolean isLast) {
   }

   private Object readResolve() {
      return DummyStepHandler.LazyHolder.INSTANCE;
   }

   // $FF: synthetic method
   DummyStepHandler(Object x0) {
      this();
   }

   private static class LazyHolder {
      private static final DummyStepHandler INSTANCE = new DummyStepHandler();
   }
}
