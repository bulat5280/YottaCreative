package org.apache.commons.math3.ode.nonstiff;

import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.MaxCountExceededException;
import org.apache.commons.math3.exception.NoBracketingException;
import org.apache.commons.math3.exception.NumberIsTooSmallException;
import org.apache.commons.math3.exception.util.LocalizedFormats;
import org.apache.commons.math3.ode.AbstractIntegrator;
import org.apache.commons.math3.ode.ExpandableStatefulODE;
import org.apache.commons.math3.util.FastMath;

public abstract class AdaptiveStepsizeIntegrator extends AbstractIntegrator {
   protected double scalAbsoluteTolerance;
   protected double scalRelativeTolerance;
   protected double[] vecAbsoluteTolerance;
   protected double[] vecRelativeTolerance;
   protected int mainSetDimension;
   private double initialStep;
   private double minStep;
   private double maxStep;

   public AdaptiveStepsizeIntegrator(String name, double minStep, double maxStep, double scalAbsoluteTolerance, double scalRelativeTolerance) {
      super(name);
      this.setStepSizeControl(minStep, maxStep, scalAbsoluteTolerance, scalRelativeTolerance);
      this.resetInternalState();
   }

   public AdaptiveStepsizeIntegrator(String name, double minStep, double maxStep, double[] vecAbsoluteTolerance, double[] vecRelativeTolerance) {
      super(name);
      this.setStepSizeControl(minStep, maxStep, vecAbsoluteTolerance, vecRelativeTolerance);
      this.resetInternalState();
   }

   public void setStepSizeControl(double minimalStep, double maximalStep, double absoluteTolerance, double relativeTolerance) {
      this.minStep = FastMath.abs(minimalStep);
      this.maxStep = FastMath.abs(maximalStep);
      this.initialStep = -1.0D;
      this.scalAbsoluteTolerance = absoluteTolerance;
      this.scalRelativeTolerance = relativeTolerance;
      this.vecAbsoluteTolerance = null;
      this.vecRelativeTolerance = null;
   }

   public void setStepSizeControl(double minimalStep, double maximalStep, double[] absoluteTolerance, double[] relativeTolerance) {
      this.minStep = FastMath.abs(minimalStep);
      this.maxStep = FastMath.abs(maximalStep);
      this.initialStep = -1.0D;
      this.scalAbsoluteTolerance = 0.0D;
      this.scalRelativeTolerance = 0.0D;
      this.vecAbsoluteTolerance = (double[])absoluteTolerance.clone();
      this.vecRelativeTolerance = (double[])relativeTolerance.clone();
   }

   public void setInitialStepSize(double initialStepSize) {
      if (!(initialStepSize < this.minStep) && !(initialStepSize > this.maxStep)) {
         this.initialStep = initialStepSize;
      } else {
         this.initialStep = -1.0D;
      }

   }

   protected void sanityChecks(ExpandableStatefulODE equations, double t) throws DimensionMismatchException, NumberIsTooSmallException {
      super.sanityChecks(equations, t);
      this.mainSetDimension = equations.getPrimaryMapper().getDimension();
      if (this.vecAbsoluteTolerance != null && this.vecAbsoluteTolerance.length != this.mainSetDimension) {
         throw new DimensionMismatchException(this.mainSetDimension, this.vecAbsoluteTolerance.length);
      } else if (this.vecRelativeTolerance != null && this.vecRelativeTolerance.length != this.mainSetDimension) {
         throw new DimensionMismatchException(this.mainSetDimension, this.vecRelativeTolerance.length);
      }
   }

   public double initializeStep(boolean forward, int order, double[] scale, double t0, double[] y0, double[] yDot0, double[] y1, double[] yDot1) throws MaxCountExceededException, DimensionMismatchException {
      if (this.initialStep > 0.0D) {
         return forward ? this.initialStep : -this.initialStep;
      } else {
         double yOnScale2 = 0.0D;
         double yDotOnScale2 = 0.0D;

         double ratio;
         for(int j = 0; j < scale.length; ++j) {
            ratio = y0[j] / scale[j];
            yOnScale2 += ratio * ratio;
            ratio = yDot0[j] / scale[j];
            yDotOnScale2 += ratio * ratio;
         }

         double h = !(yOnScale2 < 1.0E-10D) && !(yDotOnScale2 < 1.0E-10D) ? 0.01D * FastMath.sqrt(yOnScale2 / yDotOnScale2) : 1.0E-6D;
         if (!forward) {
            h = -h;
         }

         for(int j = 0; j < y0.length; ++j) {
            y1[j] = y0[j] + h * yDot0[j];
         }

         this.computeDerivatives(t0 + h, y1, yDot1);
         double yDDotOnScale = 0.0D;

         for(int j = 0; j < scale.length; ++j) {
            ratio = (yDot1[j] - yDot0[j]) / scale[j];
            yDDotOnScale += ratio * ratio;
         }

         yDDotOnScale = FastMath.sqrt(yDDotOnScale) / h;
         double maxInv2 = FastMath.max(FastMath.sqrt(yDotOnScale2), yDDotOnScale);
         double h1 = maxInv2 < 1.0E-15D ? FastMath.max(1.0E-6D, 0.001D * FastMath.abs(h)) : FastMath.pow(0.01D / maxInv2, 1.0D / (double)order);
         h = FastMath.min(100.0D * FastMath.abs(h), h1);
         h = FastMath.max(h, 1.0E-12D * FastMath.abs(t0));
         if (h < this.getMinStep()) {
            h = this.getMinStep();
         }

         if (h > this.getMaxStep()) {
            h = this.getMaxStep();
         }

         if (!forward) {
            h = -h;
         }

         return h;
      }
   }

   protected double filterStep(double h, boolean forward, boolean acceptSmall) throws NumberIsTooSmallException {
      double filteredH = h;
      if (FastMath.abs(h) < this.minStep) {
         if (!acceptSmall) {
            throw new NumberIsTooSmallException(LocalizedFormats.MINIMAL_STEPSIZE_REACHED_DURING_INTEGRATION, FastMath.abs(h), this.minStep, true);
         }

         filteredH = forward ? this.minStep : -this.minStep;
      }

      if (filteredH > this.maxStep) {
         filteredH = this.maxStep;
      } else if (filteredH < -this.maxStep) {
         filteredH = -this.maxStep;
      }

      return filteredH;
   }

   public abstract void integrate(ExpandableStatefulODE var1, double var2) throws NumberIsTooSmallException, DimensionMismatchException, MaxCountExceededException, NoBracketingException;

   public double getCurrentStepStart() {
      return this.stepStart;
   }

   protected void resetInternalState() {
      this.stepStart = Double.NaN;
      this.stepSize = FastMath.sqrt(this.minStep * this.maxStep);
   }

   public double getMinStep() {
      return this.minStep;
   }

   public double getMaxStep() {
      return this.maxStep;
   }
}
