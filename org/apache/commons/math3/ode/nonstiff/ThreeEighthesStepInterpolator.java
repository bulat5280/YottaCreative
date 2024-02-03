package org.apache.commons.math3.ode.nonstiff;

import org.apache.commons.math3.ode.sampling.StepInterpolator;

class ThreeEighthesStepInterpolator extends RungeKuttaStepInterpolator {
   private static final long serialVersionUID = 20111120L;

   public ThreeEighthesStepInterpolator() {
   }

   public ThreeEighthesStepInterpolator(ThreeEighthesStepInterpolator interpolator) {
      super(interpolator);
   }

   protected StepInterpolator doCopy() {
      return new ThreeEighthesStepInterpolator(this);
   }

   protected void computeInterpolatedStateAndDerivatives(double theta, double oneMinusThetaH) {
      double coeffDot3 = 0.75D * theta;
      double coeffDot1 = coeffDot3 * (4.0D * theta - 5.0D) + 1.0D;
      double coeffDot2 = coeffDot3 * (5.0D - 6.0D * theta);
      double coeffDot4 = coeffDot3 * (2.0D * theta - 1.0D);
      double yDot4;
      double s;
      double fourTheta2;
      double coeff1;
      double coeff2;
      double coeff3;
      double coeff4;
      int i;
      double yDot1;
      double yDot2;
      double yDot3;
      if (this.previousState != null && theta <= 0.5D) {
         s = theta * this.h / 8.0D;
         fourTheta2 = 4.0D * theta * theta;
         coeff1 = s * (8.0D - 15.0D * theta + 2.0D * fourTheta2);
         coeff2 = 3.0D * s * (5.0D * theta - fourTheta2);
         coeff3 = 3.0D * s * theta;
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
         s = oneMinusThetaH / 8.0D;
         fourTheta2 = 4.0D * theta * theta;
         coeff1 = s * (1.0D - 7.0D * theta + 2.0D * fourTheta2);
         coeff2 = 3.0D * s * (1.0D + theta - fourTheta2);
         coeff3 = 3.0D * s * (1.0D + theta);
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
