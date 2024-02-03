package org.apache.commons.math3.analysis.integration;

import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.exception.MaxCountExceededException;
import org.apache.commons.math3.exception.NotStrictlyPositiveException;
import org.apache.commons.math3.exception.NumberIsTooLargeException;
import org.apache.commons.math3.exception.NumberIsTooSmallException;
import org.apache.commons.math3.exception.TooManyEvaluationsException;
import org.apache.commons.math3.util.FastMath;

public class TrapezoidIntegrator extends BaseAbstractUnivariateIntegrator {
   public static final int TRAPEZOID_MAX_ITERATIONS_COUNT = 64;
   private double s;

   public TrapezoidIntegrator(double relativeAccuracy, double absoluteAccuracy, int minimalIterationCount, int maximalIterationCount) throws NotStrictlyPositiveException, NumberIsTooSmallException, NumberIsTooLargeException {
      super(relativeAccuracy, absoluteAccuracy, minimalIterationCount, maximalIterationCount);
      if (maximalIterationCount > 64) {
         throw new NumberIsTooLargeException(maximalIterationCount, 64, false);
      }
   }

   public TrapezoidIntegrator(int minimalIterationCount, int maximalIterationCount) throws NotStrictlyPositiveException, NumberIsTooSmallException, NumberIsTooLargeException {
      super(minimalIterationCount, maximalIterationCount);
      if (maximalIterationCount > 64) {
         throw new NumberIsTooLargeException(maximalIterationCount, 64, false);
      }
   }

   public TrapezoidIntegrator() {
      super(3, 64);
   }

   double stage(BaseAbstractUnivariateIntegrator baseIntegrator, int n) throws TooManyEvaluationsException {
      double sum;
      if (n == 0) {
         double max = baseIntegrator.getMax();
         sum = baseIntegrator.getMin();
         this.s = 0.5D * (max - sum) * (baseIntegrator.computeObjectiveValue(sum) + baseIntegrator.computeObjectiveValue(max));
         return this.s;
      } else {
         long np = 1L << n - 1;
         sum = 0.0D;
         double max = baseIntegrator.getMax();
         double min = baseIntegrator.getMin();
         double spacing = (max - min) / (double)np;
         double x = min + 0.5D * spacing;

         for(long i = 0L; i < np; ++i) {
            sum += baseIntegrator.computeObjectiveValue(x);
            x += spacing;
         }

         this.s = 0.5D * (this.s + sum * spacing);
         return this.s;
      }
   }

   protected double doIntegrate() throws MathIllegalArgumentException, TooManyEvaluationsException, MaxCountExceededException {
      double oldt = this.stage(this, 0);
      this.iterations.incrementCount();

      while(true) {
         int i = this.iterations.getCount();
         double t = this.stage(this, i);
         if (i >= this.getMinimalIterationCount()) {
            double delta = FastMath.abs(t - oldt);
            double rLimit = this.getRelativeAccuracy() * (FastMath.abs(oldt) + FastMath.abs(t)) * 0.5D;
            if (delta <= rLimit || delta <= this.getAbsoluteAccuracy()) {
               return t;
            }
         }

         oldt = t;
         this.iterations.incrementCount();
      }
   }
}
