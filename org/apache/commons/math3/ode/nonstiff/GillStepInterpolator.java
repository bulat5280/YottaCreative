package org.apache.commons.math3.ode.nonstiff;

import org.apache.commons.math3.ode.sampling.StepInterpolator;
import org.apache.commons.math3.util.FastMath;

class GillStepInterpolator extends RungeKuttaStepInterpolator {
   private static final double ONE_MINUS_INV_SQRT_2 = 1.0D - FastMath.sqrt(0.5D);
   private static final double ONE_PLUS_INV_SQRT_2 = 1.0D + FastMath.sqrt(0.5D);
   private static final long serialVersionUID = 20111120L;

   public GillStepInterpolator() {
   }

   public GillStepInterpolator(GillStepInterpolator interpolator) {
      super(interpolator);
   }

   protected StepInterpolator doCopy() {
      return new GillStepInterpolator(this);
   }

   protected void computeInterpolatedStateAndDerivatives(double theta, double oneMinusThetaH) {
      double twoTheta = 2.0D * theta;
      double fourTheta2 = twoTheta * twoTheta;
      double coeffDot1 = theta * (twoTheta - 3.0D) + 1.0D;
      double cDot23 = twoTheta * (1.0D - theta);
      double coeffDot2 = cDot23 * ONE_MINUS_INV_SQRT_2;
      double coeffDot3 = cDot23 * ONE_PLUS_INV_SQRT_2;
      double coeffDot4 = theta * (twoTheta - 1.0D);
      double yDot1;
      double yDot2;
      double yDot3;
      double yDot4;
      double s;
      double c23;
      double coeff1;
      double coeff2;
      double coeff3;
      double coeff4;
      int i;
      if (this.previousState != null && theta <= 0.5D) {
         s = theta * this.h / 6.0D;
         c23 = s * (6.0D * theta - fourTheta2);
         coeff1 = s * (6.0D - 9.0D * theta + fourTheta2);
         coeff2 = c23 * ONE_MINUS_INV_SQRT_2;
         coeff3 = c23 * ONE_PLUS_INV_SQRT_2;
         coeff4 = s * (-3.0D * theta + fourTheta2);

         for(i = 0; i < this.interpolatedState.length; ++i) {
            yDot1 = this.yDotK[0][i];
            yDot2 = this.yDotK[1][i];
            yDot3 = this.yDotK[2][i];
            yDot4 = this.yDotK[3][i];
            this.interpolatedState[i] = this.previousState[i] + coeff1 * yDot1 + coeff2 * yDot2 + coeff3 * yDot3 + coeff4 * yDot4;
            this.interpolatedDerivatives[i] = coeffDot1 * yDot1 + coeffDot2 * yDot2 + coeffDot3 * yDot3 + coeffDot4 * yDot4;
         }
      } else {
         s = oneMinusThetaH / 6.0D;
         c23 = s * (2.0D + twoTheta - fourTheta2);
         coeff1 = s * (1.0D - 5.0D * theta + fourTheta2);
         coeff2 = c23 * ONE_MINUS_INV_SQRT_2;
         coeff3 = c23 * ONE_PLUS_INV_SQRT_2;
         coeff4 = s * (1.0D + theta + fourTheta2);

         for(i = 0; i < this.interpolatedState.length; ++i) {
            yDot1 = this.yDotK[0][i];
            yDot2 = this.yDotK[1][i];
            yDot3 = this.yDotK[2][i];
            yDot4 = this.yDotK[3][i];
            this.interpolatedState[i] = this.currentState[i] - coeff1 * yDot1 - coeff2 * yDot2 - coeff3 * yDot3 - coeff4 * yDot4;
            this.interpolatedDerivatives[i] = coeffDot1 * yDot1 + coeffDot2 * yDot2 + coeffDot3 * yDot3 + coeffDot4 * yDot4;
         }
      }

   }
}
