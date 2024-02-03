package org.apache.commons.math3.ode.nonstiff;

public class ClassicalRungeKuttaIntegrator extends RungeKuttaIntegrator {
   private static final double[] STATIC_C = new double[]{0.5D, 0.5D, 1.0D};
   private static final double[][] STATIC_A = new double[][]{{0.5D}, {0.0D, 0.5D}, {0.0D, 0.0D, 1.0D}};
   private static final double[] STATIC_B = new double[]{0.16666666666666666D, 0.3333333333333333D, 0.3333333333333333D, 0.16666666666666666D};

   public ClassicalRungeKuttaIntegrator(double step) {
      super("classical Runge-Kutta", STATIC_C, STATIC_A, STATIC_B, new ClassicalRungeKuttaStepInterpolator(), step);
   }
}
