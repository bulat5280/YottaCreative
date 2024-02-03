package org.apache.commons.math3.ode.nonstiff;

import java.util.Arrays;
import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.MaxCountExceededException;
import org.apache.commons.math3.exception.NoBracketingException;
import org.apache.commons.math3.exception.NumberIsTooSmallException;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrixPreservingVisitor;
import org.apache.commons.math3.ode.EquationsMapper;
import org.apache.commons.math3.ode.ExpandableStatefulODE;
import org.apache.commons.math3.ode.sampling.NordsieckStepInterpolator;
import org.apache.commons.math3.util.FastMath;

public class AdamsMoultonIntegrator extends AdamsIntegrator {
   private static final String METHOD_NAME = "Adams-Moulton";

   public AdamsMoultonIntegrator(int nSteps, double minStep, double maxStep, double scalAbsoluteTolerance, double scalRelativeTolerance) throws NumberIsTooSmallException {
      super("Adams-Moulton", nSteps, nSteps + 1, minStep, maxStep, scalAbsoluteTolerance, scalRelativeTolerance);
   }

   public AdamsMoultonIntegrator(int nSteps, double minStep, double maxStep, double[] vecAbsoluteTolerance, double[] vecRelativeTolerance) throws IllegalArgumentException {
      super("Adams-Moulton", nSteps, nSteps + 1, minStep, maxStep, vecAbsoluteTolerance, vecRelativeTolerance);
   }

   public void integrate(ExpandableStatefulODE equations, double t) throws NumberIsTooSmallException, DimensionMismatchException, MaxCountExceededException, NoBracketingException {
      this.sanityChecks(equations, t);
      this.setEquations(equations);
      boolean forward = t > equations.getTime();
      double[] y0 = equations.getCompleteState();
      double[] y = (double[])y0.clone();
      double[] yDot = new double[y.length];
      double[] yTmp = new double[y.length];
      double[] predictedScaled = new double[y.length];
      Array2DRowRealMatrix nordsieckTmp = null;
      NordsieckStepInterpolator interpolator = new NordsieckStepInterpolator();
      interpolator.reinitialize(y, forward, equations.getPrimaryMapper(), equations.getSecondaryMappers());
      this.initIntegration(equations.getTime(), y0, t);
      this.start(equations.getTime(), y, t);
      interpolator.reinitialize(this.stepStart, this.stepSize, this.scaled, this.nordsieck);
      interpolator.storeTime(this.stepStart);
      double hNew = this.stepSize;
      interpolator.rescale(hNew);
      this.isLastStep = false;

      do {
         double error = 10.0D;

         double stepEnd;
         double factor;
         while(error >= 1.0D) {
            this.stepSize = hNew;
            stepEnd = this.stepStart + this.stepSize;
            interpolator.setInterpolatedTime(stepEnd);
            ExpandableStatefulODE expandable = this.getExpandable();
            EquationsMapper primary = expandable.getPrimaryMapper();
            primary.insertEquationData(interpolator.getInterpolatedState(), yTmp);
            int index = 0;
            EquationsMapper[] arr$ = expandable.getSecondaryMappers();
            int len$ = arr$.length;

            for(int i$ = 0; i$ < len$; ++i$) {
               EquationsMapper secondary = arr$[i$];
               secondary.insertEquationData(interpolator.getInterpolatedSecondaryState(index), yTmp);
               ++index;
            }

            this.computeDerivatives(stepEnd, yTmp, yDot);

            for(int j = 0; j < y0.length; ++j) {
               predictedScaled[j] = this.stepSize * yDot[j];
            }

            nordsieckTmp = this.updateHighOrderDerivativesPhase1(this.nordsieck);
            this.updateHighOrderDerivativesPhase2(this.scaled, predictedScaled, nordsieckTmp);
            error = nordsieckTmp.walkInOptimizedOrder(new AdamsMoultonIntegrator.Corrector(y, predictedScaled, yTmp));
            if (error >= 1.0D) {
               factor = this.computeStepGrowShrinkFactor(error);
               hNew = this.filterStep(this.stepSize * factor, forward, false);
               interpolator.rescale(hNew);
            }
         }

         stepEnd = this.stepStart + this.stepSize;
         this.computeDerivatives(stepEnd, yTmp, yDot);
         double[] correctedScaled = new double[y0.length];

         for(int j = 0; j < y0.length; ++j) {
            correctedScaled[j] = this.stepSize * yDot[j];
         }

         this.updateHighOrderDerivativesPhase2(predictedScaled, correctedScaled, nordsieckTmp);
         System.arraycopy(yTmp, 0, y, 0, y.length);
         interpolator.reinitialize(stepEnd, this.stepSize, correctedScaled, nordsieckTmp);
         interpolator.storeTime(this.stepStart);
         interpolator.shift();
         interpolator.storeTime(stepEnd);
         this.stepStart = this.acceptStep(interpolator, y, yDot, t);
         this.scaled = correctedScaled;
         this.nordsieck = nordsieckTmp;
         if (!this.isLastStep) {
            interpolator.storeTime(this.stepStart);
            if (this.resetOccurred) {
               this.start(this.stepStart, y, t);
               interpolator.reinitialize(this.stepStart, this.stepSize, this.scaled, this.nordsieck);
            }

            double factor = this.computeStepGrowShrinkFactor(error);
            factor = this.stepSize * factor;
            double nextT = this.stepStart + factor;
            boolean nextIsLast = forward ? nextT >= t : nextT <= t;
            hNew = this.filterStep(factor, forward, nextIsLast);
            double filteredNextT = this.stepStart + hNew;
            boolean filteredNextIsLast = forward ? filteredNextT >= t : filteredNextT <= t;
            if (filteredNextIsLast) {
               hNew = t - this.stepStart;
            }

            interpolator.rescale(hNew);
         }
      } while(!this.isLastStep);

      equations.setTime(this.stepStart);
      equations.setCompleteState(y);
      this.resetInternalState();
   }

   private class Corrector implements RealMatrixPreservingVisitor {
      private final double[] previous;
      private final double[] scaled;
      private final double[] before;
      private final double[] after;

      public Corrector(double[] previous, double[] scaled, double[] state) {
         this.previous = previous;
         this.scaled = scaled;
         this.after = state;
         this.before = (double[])state.clone();
      }

      public void start(int rows, int columns, int startRow, int endRow, int startColumn, int endColumn) {
         Arrays.fill(this.after, 0.0D);
      }

      public void visit(int row, int column, double value) {
         double[] var10000;
         if ((row & 1) == 0) {
            var10000 = this.after;
            var10000[column] -= value;
         } else {
            var10000 = this.after;
            var10000[column] += value;
         }

      }

      public double end() {
         double error = 0.0D;

         for(int i = 0; i < this.after.length; ++i) {
            double[] var10000 = this.after;
            var10000[i] += this.previous[i] + this.scaled[i];
            if (i < AdamsMoultonIntegrator.this.mainSetDimension) {
               double yScale = FastMath.max(FastMath.abs(this.previous[i]), FastMath.abs(this.after[i]));
               double tol = AdamsMoultonIntegrator.this.vecAbsoluteTolerance == null ? AdamsMoultonIntegrator.this.scalAbsoluteTolerance + AdamsMoultonIntegrator.this.scalRelativeTolerance * yScale : AdamsMoultonIntegrator.this.vecAbsoluteTolerance[i] + AdamsMoultonIntegrator.this.vecRelativeTolerance[i] * yScale;
               double ratio = (this.after[i] - this.before[i]) / tol;
               error += ratio * ratio;
            }
         }

         return FastMath.sqrt(error / (double)AdamsMoultonIntegrator.this.mainSetDimension);
      }
   }
}
