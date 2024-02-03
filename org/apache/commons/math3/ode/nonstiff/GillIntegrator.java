package org.apache.commons.math3.ode.nonstiff;

import org.apache.commons.math3.util.FastMath;

public class GillIntegrator extends RungeKuttaIntegrator {
   private static final double[] STATIC_C = new double[]{0.5D, 0.5D, 1.0D};
   private static final double[][] STATIC_A = new double[][]{{0.5D}, {(FastMath.sqrt(2.0D) - 1.0D) / 2.0D, (2.0D - FastMath.sqrt(2.0D)) / 2.0D}, {0.0D, -FastMath.sqrt(2.0D) / 2.0D, (2.0D + FastMath.sqrt(2.0D)) / 2.0D}};
   private static final double[] STATIC_B = new double[]{0.16666666666666666D, (2.0D - FastMath.sqrt(2.0D)) / 6.0D, (2.0D + FastMath.sqrt(2.0D)) / 6.0D, 0.16666666666666666D};

   public GillIntegrator(double step) {
      super("Gill", STATIC_C, STATIC_A, STATIC_B, new GillStepInterpolator(), step);
   }
}
