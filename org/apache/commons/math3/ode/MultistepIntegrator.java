package org.apache.commons.math3.ode;

import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.MaxCountExceededException;
import org.apache.commons.math3.exception.NoBracketingException;
import org.apache.commons.math3.exception.NumberIsTooSmallException;
import org.apache.commons.math3.exception.util.LocalizedFormats;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.ode.nonstiff.AdaptiveStepsizeIntegrator;
import org.apache.commons.math3.ode.nonstiff.DormandPrince853Integrator;
import org.apache.commons.math3.ode.sampling.StepHandler;
import org.apache.commons.math3.ode.sampling.StepInterpolator;
import org.apache.commons.math3.util.FastMath;

public abstract class MultistepIntegrator extends AdaptiveStepsizeIntegrator {
   protected double[] scaled;
   protected Array2DRowRealMatrix nordsieck;
   private FirstOrderIntegrator starter;
   private final int nSteps;
   private double exp;
   private double safety;
   private double minReduction;
   private double maxGrowth;

   protected MultistepIntegrator(String name, int nSteps, int order, double minStep, double maxStep, double scalAbsoluteTolerance, double scalRelativeTolerance) throws NumberIsTooSmallException {
      super(name, minStep, maxStep, scalAbsoluteTolerance, scalRelativeTolerance);
      if (nSteps < 2) {
         throw new NumberIsTooSmallException(LocalizedFormats.INTEGRATION_METHOD_NEEDS_AT_LEAST_TWO_PREVIOUS_POINTS, nSteps, 2, true);
      } else {
         this.starter = new DormandPrince853Integrator(minStep, maxStep, scalAbsoluteTolerance, scalRelativeTolerance);
         this.nSteps = nSteps;
         this.exp = -1.0D / (double)order;
         this.setSafety(0.9D);
         this.setMinReduction(0.2D);
         this.setMaxGrowth(FastMath.pow(2.0D, -this.exp));
      }
   }

   protected MultistepIntegrator(String name, int nSteps, int order, double minStep, double maxStep, double[] vecAbsoluteTolerance, double[] vecRelativeTolerance) {
      super(name, minStep, maxStep, vecAbsoluteTolerance, vecRelativeTolerance);
      this.starter = new DormandPrince853Integrator(minStep, maxStep, vecAbsoluteTolerance, vecRelativeTolerance);
      this.nSteps = nSteps;
      this.exp = -1.0D / (double)order;
      this.setSafety(0.9D);
      this.setMinReduction(0.2D);
      this.setMaxGrowth(FastMath.pow(2.0D, -this.exp));
   }

   public ODEIntegrator getStarterIntegrator() {
      return this.starter;
   }

   public void setStarterIntegrator(FirstOrderIntegrator starterIntegrator) {
      this.starter = starterIntegrator;
   }

   protected void start(double t0, double[] y0, double t) throws DimensionMismatchException, NumberIsTooSmallException, MaxCountExceededException, NoBracketingException {
      this.starter.clearEventHandlers();
      this.starter.clearStepHandlers();
      this.starter.addStepHandler(new MultistepIntegrator.NordsieckInitializer(this.nSteps, y0.length));

      try {
         if (this.starter instanceof AbstractIntegrator) {
            ((AbstractIntegrator)this.starter).integrate(this.getExpandable(), t);
         } else {
            this.starter.integrate(new FirstOrderDifferentialEquations() {
               public int getDimension() {
                  return MultistepIntegrator.this.getExpandable().getTotalDimension();
               }

               public void computeDerivatives(double t, double[] y, double[] yDot) {
                  MultistepIntegrator.this.getExpandable().computeDerivatives(t, y, yDot);
               }
            }, t0, y0, t, new double[y0.length]);
         }
      } catch (MultistepIntegrator.InitializationCompletedMarkerException var7) {
         this.getEvaluationsCounter().incrementCount(this.starter.getEvaluations());
      }

      this.starter.clearStepHandlers();
   }

   protected abstract Array2DRowRealMatrix initializeHighOrderDerivatives(double var1, double[] var3, double[][] var4, double[][] var5);

   public double getMinReduction() {
      return this.minReduction;
   }

   public void setMinReduction(double minReduction) {
      this.minReduction = minReduction;
   }

   public double getMaxGrowth() {
      return this.maxGrowth;
   }

   public void setMaxGrowth(double maxGrowth) {
      this.maxGrowth = maxGrowth;
   }

   public double getSafety() {
      return this.safety;
   }

   public void setSafety(double safety) {
      this.safety = safety;
   }

   protected double computeStepGrowShrinkFactor(double error) {
      return FastMath.min(this.maxGrowth, FastMath.max(this.minReduction, this.safety * FastMath.pow(error, this.exp)));
   }

   private static class InitializationCompletedMarkerException extends RuntimeException {
      private static final long serialVersionUID = -1914085471038046418L;

      public InitializationCompletedMarkerException() {
         super((Throwable)null);
      }
   }

   private class NordsieckInitializer implements StepHandler {
      private int count = 0;
      private final double[] t;
      private final double[][] y;
      private final double[][] yDot;

      public NordsieckInitializer(int nSteps, int n) {
         this.t = new double[nSteps];
         this.y = new double[nSteps][n];
         this.yDot = new double[nSteps][n];
      }

      public void handleStep(StepInterpolator interpolator, boolean isLast) throws MaxCountExceededException {
         double prev = interpolator.getPreviousTime();
         double curr = interpolator.getCurrentTime();
         ExpandableStatefulODE expandable;
         EquationsMapper primary;
         int index;
         EquationsMapper[] arr$;
         int len$;
         int i$;
         EquationsMapper secondary;
         if (this.count == 0) {
            interpolator.setInterpolatedTime(prev);
            this.t[0] = prev;
            expandable = MultistepIntegrator.this.getExpandable();
            primary = expandable.getPrimaryMapper();
            primary.insertEquationData(interpolator.getInterpolatedState(), this.y[this.count]);
            primary.insertEquationData(interpolator.getInterpolatedDerivatives(), this.yDot[this.count]);
            index = 0;
            arr$ = expandable.getSecondaryMappers();
            len$ = arr$.length;

            for(i$ = 0; i$ < len$; ++i$) {
               secondary = arr$[i$];
               secondary.insertEquationData(interpolator.getInterpolatedSecondaryState(index), this.y[this.count]);
               secondary.insertEquationData(interpolator.getInterpolatedSecondaryDerivatives(index), this.yDot[this.count]);
               ++index;
            }
         }

         ++this.count;
         interpolator.setInterpolatedTime(curr);
         this.t[this.count] = curr;
         expandable = MultistepIntegrator.this.getExpandable();
         primary = expandable.getPrimaryMapper();
         primary.insertEquationData(interpolator.getInterpolatedState(), this.y[this.count]);
         primary.insertEquationData(interpolator.getInterpolatedDerivatives(), this.yDot[this.count]);
         index = 0;
         arr$ = expandable.getSecondaryMappers();
         len$ = arr$.length;

         for(i$ = 0; i$ < len$; ++i$) {
            secondary = arr$[i$];
            secondary.insertEquationData(interpolator.getInterpolatedSecondaryState(index), this.y[this.count]);
            secondary.insertEquationData(interpolator.getInterpolatedSecondaryDerivatives(index), this.yDot[this.count]);
            ++index;
         }

         if (this.count == this.t.length - 1) {
            MultistepIntegrator.this.stepStart = this.t[0];
            MultistepIntegrator.this.stepSize = (this.t[this.t.length - 1] - this.t[0]) / (double)(this.t.length - 1);
            MultistepIntegrator.this.scaled = (double[])this.yDot[0].clone();

            for(int j = 0; j < MultistepIntegrator.this.scaled.length; ++j) {
               double[] var10000 = MultistepIntegrator.this.scaled;
               var10000[j] *= MultistepIntegrator.this.stepSize;
            }

            MultistepIntegrator.this.nordsieck = MultistepIntegrator.this.initializeHighOrderDerivatives(MultistepIntegrator.this.stepSize, this.t, this.y, this.yDot);
            throw new MultistepIntegrator.InitializationCompletedMarkerException();
         }
      }

      public void init(double t0, double[] y0, double time) {
      }
   }

   public interface NordsieckTransformer {
      Array2DRowRealMatrix initializeHighOrderDerivatives(double var1, double[] var3, double[][] var4, double[][] var5);
   }
}
