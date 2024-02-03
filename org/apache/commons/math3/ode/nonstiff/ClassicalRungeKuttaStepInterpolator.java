package org.apache.commons.math3.ode.nonstiff;

import org.apache.commons.math3.ode.sampling.StepInterpolator;

class ClassicalRungeKuttaStepInterpolator extends RungeKuttaStepInterpolator {
   private static final long serialVersionUID = 20111120L;

   public ClassicalRungeKuttaStepInterpolator() {
   }

   public ClassicalRungeKuttaStepInterpolator(ClassicalRungeKuttaStepInterpolator interpolator) {
      super(interpolator);
   }

   protected StepInterpolator doCopy() {
      return new ClassicalRungeKuttaStepInterpolator(this);
   }

   protected void computeInterpolatedStateAndDerivatives(double theta, double oneMinusThetaH) {
      double oneMinusTheta = 1.0D - theta;
      double oneMinus2Theta = 1.0D - 2.0D * theta;
      double coeffDot1 = oneMinusTheta * oneMinus2Theta;
      double coeffDot23 = 2.0D * theta * oneMinusTheta;
      double coeffDot4 = -theta * oneMinus2Theta;
      double fourTheta;
      double s;
      double coeff1;
      double coeff23;
      double coeff4;
      int i;
      double yDot1;
      double yDot23;
      double yDot4;
      if (this.previousState != null && theta <= 0.5D) {
         fourTheta = 4.0D * theta * theta;
         s = theta * this.h / 6.0D;
         coeff1 = s * (6.0D - 9.0D * theta + fourTheta);
         coeff23 = s * (6.0D * theta - fourTheta);
         coeff4 = s * (-3.0D * theta + fourTheta);

         for(i = 0; i < this.interpolatedState.length; ++i) {
            yDot1 = this.yDotK[0][i];
            yDot23 = this.yDotK[1][i] + this.yDotK[2][i];
            yDot4 = this.yDotK[3][i];
            this.interpolatedState[i] = this.previousState[i] + coeff1 * yDot1 + coeff23 * yDot23 + coeff4 * yDot4;
            this.interpolatedDerivatives[i] = coeffDot1 * yDot1 + coeffDot23 * yDot23 + coeffDot4 * yDot4;
         }
      } else {
         fourTheta = 4.0D * theta;
         s = oneMinusThetaH / 6.0D;
         coeff1 = s * ((-fourTheta + 5.0D) * theta - 1.0D);
         coeff23 = s * ((fourTheta - 2.0D) * theta - 2.0D);
         coeff4 = s * ((-fourTheta - 1.0D) * theta - 1.0D);

         for(i = 0; i < this.interpolatedState.length; ++i) {
            yDot1 = this.yDotK[0][i];
            yDot23 = this.yDotK[1][i] + this.yDotK[2][i];
            yDot4 = this.yDotK[3][i];
            this.interpolatedState[i] = this.currentState[i] + coeff1 * yDot1 + coeff23 * yDot23 + coeff4 * yDot4;
            this.interpolatedDerivatives[i] = coeffDot1 * yDot1 + coeffDot23 * yDot23 + coeffDot4 * yDot4;
         }
      }

   }
}
