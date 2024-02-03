package org.apache.commons.math3.ode.nonstiff;

import org.apache.commons.math3.ode.sampling.StepInterpolator;

class HighamHall54StepInterpolator extends RungeKuttaStepInterpolator {
   private static final long serialVersionUID = 20111120L;

   public HighamHall54StepInterpolator() {
   }

   public HighamHall54StepInterpolator(HighamHall54StepInterpolator interpolator) {
      super(interpolator);
   }

   protected StepInterpolator doCopy() {
      return new HighamHall54StepInterpolator(this);
   }

   protected void computeInterpolatedStateAndDerivatives(double theta, double oneMinusThetaH) {
      double bDot0 = 1.0D + theta * (-7.5D + theta * (16.0D - 10.0D * theta));
      double bDot2 = theta * (28.6875D + theta * (-91.125D + 67.5D * theta));
      double bDot3 = theta * (-44.0D + theta * (152.0D - 120.0D * theta));
      double bDot4 = theta * (23.4375D + theta * (-78.125D + 62.5D * theta));
      double bDot5 = theta * 5.0D / 8.0D * (2.0D * theta - 1.0D);
      double yDot3;
      double yDot4;
      double yDot5;
      double theta2;
      double b0;
      double b2;
      double b3;
      double b4;
      double b5;
      int i;
      double yDot0;
      double yDot2;
      if (this.previousState != null && theta <= 0.5D) {
         theta2 = this.h * theta;
         b0 = theta2 * (1.0D + theta * (-3.75D + theta * (5.333333333333333D - 2.5D * theta)));
         b2 = theta2 * theta * (14.34375D + theta * (-30.375D + theta * 135.0D / 8.0D));
         b3 = theta2 * theta * (-22.0D + theta * (50.666666666666664D + theta * -30.0D));
         b4 = theta2 * theta * (11.71875D + theta * (-26.041666666666668D + theta * 125.0D / 8.0D));
         b5 = theta2 * theta * (-0.3125D + theta * 5.0D / 12.0D);

         for(i = 0; i < this.interpolatedState.length; ++i) {
            yDot0 = this.yDotK[0][i];
            yDot2 = this.yDotK[2][i];
            yDot3 = this.yDotK[3][i];
            yDot4 = this.yDotK[4][i];
            yDot5 = this.yDotK[5][i];
            this.interpolatedState[i] = this.previousState[i] + b0 * yDot0 + b2 * yDot2 + b3 * yDot3 + b4 * yDot4 + b5 * yDot5;
            this.interpolatedDerivatives[i] = bDot0 * yDot0 + bDot2 * yDot2 + bDot3 * yDot3 + bDot4 * yDot4 + bDot5 * yDot5;
         }
      } else {
         theta2 = theta * theta;
         b0 = this.h * (-0.08333333333333333D + theta * (1.0D + theta * (-3.75D + theta * (5.333333333333333D + theta * -5.0D / 2.0D))));
         b2 = this.h * (-0.84375D + theta2 * (14.34375D + theta * (-30.375D + theta * 135.0D / 8.0D)));
         b3 = this.h * (1.3333333333333333D + theta2 * (-22.0D + theta * (50.666666666666664D + theta * -30.0D)));
         b4 = this.h * (-1.3020833333333333D + theta2 * (11.71875D + theta * (-26.041666666666668D + theta * 125.0D / 8.0D)));
         b5 = this.h * (-0.10416666666666667D + theta2 * (-0.3125D + theta * 5.0D / 12.0D));

         for(i = 0; i < this.interpolatedState.length; ++i) {
            yDot0 = this.yDotK[0][i];
            yDot2 = this.yDotK[2][i];
            yDot3 = this.yDotK[3][i];
            yDot4 = this.yDotK[4][i];
            yDot5 = this.yDotK[5][i];
            this.interpolatedState[i] = this.currentState[i] + b0 * yDot0 + b2 * yDot2 + b3 * yDot3 + b4 * yDot4 + b5 * yDot5;
            this.interpolatedDerivatives[i] = bDot0 * yDot0 + bDot2 * yDot2 + bDot3 * yDot3 + bDot4 * yDot4 + bDot5 * yDot5;
         }
      }

   }
}
