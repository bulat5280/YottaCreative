package org.apache.commons.math3.ode.nonstiff;

import org.apache.commons.math3.ode.AbstractIntegrator;
import org.apache.commons.math3.ode.EquationsMapper;
import org.apache.commons.math3.ode.sampling.StepInterpolator;

class DormandPrince54StepInterpolator extends RungeKuttaStepInterpolator {
   private static final double A70 = 0.09114583333333333D;
   private static final double A72 = 0.44923629829290207D;
   private static final double A73 = 0.6510416666666666D;
   private static final double A74 = -0.322376179245283D;
   private static final double A75 = 0.13095238095238096D;
   private static final double D0 = -1.1270175653862835D;
   private static final double D2 = 2.675424484351598D;
   private static final double D3 = -5.685526961588504D;
   private static final double D4 = 3.5219323679207912D;
   private static final double D5 = -1.7672812570757455D;
   private static final double D6 = 2.382468931778144D;
   private static final long serialVersionUID = 20111120L;
   private double[] v1;
   private double[] v2;
   private double[] v3;
   private double[] v4;
   private boolean vectorsInitialized;

   public DormandPrince54StepInterpolator() {
      this.v1 = null;
      this.v2 = null;
      this.v3 = null;
      this.v4 = null;
      this.vectorsInitialized = false;
   }

   public DormandPrince54StepInterpolator(DormandPrince54StepInterpolator interpolator) {
      super(interpolator);
      if (interpolator.v1 == null) {
         this.v1 = null;
         this.v2 = null;
         this.v3 = null;
         this.v4 = null;
         this.vectorsInitialized = false;
      } else {
         this.v1 = (double[])interpolator.v1.clone();
         this.v2 = (double[])interpolator.v2.clone();
         this.v3 = (double[])interpolator.v3.clone();
         this.v4 = (double[])interpolator.v4.clone();
         this.vectorsInitialized = interpolator.vectorsInitialized;
      }

   }

   protected StepInterpolator doCopy() {
      return new DormandPrince54StepInterpolator(this);
   }

   public void reinitialize(AbstractIntegrator integrator, double[] y, double[][] yDotK, boolean forward, EquationsMapper primaryMapper, EquationsMapper[] secondaryMappers) {
      super.reinitialize(integrator, y, yDotK, forward, primaryMapper, secondaryMappers);
      this.v1 = null;
      this.v2 = null;
      this.v3 = null;
      this.v4 = null;
      this.vectorsInitialized = false;
   }

   public void storeTime(double t) {
      super.storeTime(t);
      this.vectorsInitialized = false;
   }

   protected void computeInterpolatedStateAndDerivatives(double theta, double oneMinusThetaH) {
      if (!this.vectorsInitialized) {
         if (this.v1 == null) {
            this.v1 = new double[this.interpolatedState.length];
            this.v2 = new double[this.interpolatedState.length];
            this.v3 = new double[this.interpolatedState.length];
            this.v4 = new double[this.interpolatedState.length];
         }

         for(int i = 0; i < this.interpolatedState.length; ++i) {
            double yDot0 = this.yDotK[0][i];
            double yDot2 = this.yDotK[2][i];
            double yDot3 = this.yDotK[3][i];
            double yDot4 = this.yDotK[4][i];
            double yDot5 = this.yDotK[5][i];
            double yDot6 = this.yDotK[6][i];
            this.v1[i] = 0.09114583333333333D * yDot0 + 0.44923629829290207D * yDot2 + 0.6510416666666666D * yDot3 + -0.322376179245283D * yDot4 + 0.13095238095238096D * yDot5;
            this.v2[i] = yDot0 - this.v1[i];
            this.v3[i] = this.v1[i] - this.v2[i] - yDot6;
            this.v4[i] = -1.1270175653862835D * yDot0 + 2.675424484351598D * yDot2 + -5.685526961588504D * yDot3 + 3.5219323679207912D * yDot4 + -1.7672812570757455D * yDot5 + 2.382468931778144D * yDot6;
         }

         this.vectorsInitialized = true;
      }

      double eta = 1.0D - theta;
      double twoTheta = 2.0D * theta;
      double dot2 = 1.0D - twoTheta;
      double dot3 = theta * (2.0D - 3.0D * theta);
      double dot4 = twoTheta * (1.0D + theta * (twoTheta - 3.0D));
      int i;
      if (this.previousState != null && theta <= 0.5D) {
         for(i = 0; i < this.interpolatedState.length; ++i) {
            this.interpolatedState[i] = this.previousState[i] + theta * this.h * (this.v1[i] + eta * (this.v2[i] + theta * (this.v3[i] + eta * this.v4[i])));
            this.interpolatedDerivatives[i] = this.v1[i] + dot2 * this.v2[i] + dot3 * this.v3[i] + dot4 * this.v4[i];
         }
      } else {
         for(i = 0; i < this.interpolatedState.length; ++i) {
            this.interpolatedState[i] = this.currentState[i] - oneMinusThetaH * (this.v1[i] - theta * (this.v2[i] + theta * (this.v3[i] + eta * this.v4[i])));
            this.interpolatedDerivatives[i] = this.v1[i] + dot2 * this.v2[i] + dot3 * this.v3[i] + dot4 * this.v4[i];
         }
      }

   }
}
