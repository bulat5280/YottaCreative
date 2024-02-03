package org.apache.commons.math3.ode.nonstiff;

public class ThreeEighthesIntegrator extends RungeKuttaIntegrator {
   private static final double[] STATIC_C = new double[]{0.3333333333333333D, 0.6666666666666666D, 1.0D};
   private static final double[][] STATIC_A = new double[][]{{0.3333333333333333D}, {-0.3333333333333333D, 1.0D}, {1.0D, -1.0D, 1.0D}};
   private static final double[] STATIC_B = new double[]{0.125D, 0.375D, 0.375D, 0.125D};

   public ThreeEighthesIntegrator(double step) {
      super("3/8", STATIC_C, STATIC_A, STATIC_B, new ThreeEighthesStepInterpolator(), step);
   }
}
