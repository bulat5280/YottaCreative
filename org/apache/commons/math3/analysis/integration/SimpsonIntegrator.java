package org.apache.commons.math3.analysis.integration;

import org.apache.commons.math3.exception.MaxCountExceededException;
import org.apache.commons.math3.exception.NotStrictlyPositiveException;
import org.apache.commons.math3.exception.NumberIsTooLargeException;
import org.apache.commons.math3.exception.NumberIsTooSmallException;
import org.apache.commons.math3.exception.TooManyEvaluationsException;
import org.apache.commons.math3.util.FastMath;

public class SimpsonIntegrator extends BaseAbstractUnivariateIntegrator {
   public static final int SIMPSON_MAX_ITERATIONS_COUNT = 64;

   public SimpsonIntegrator(double relativeAccuracy, double absoluteAccuracy, int minimalIterationCount, int maximalIterationCount) throws NotStrictlyPositiveException, NumberIsTooSmallException, NumberIsTooLargeException {
      super(relativeAccuracy, absoluteAccuracy, minimalIterationCount, maximalIterationCount);
      if (maximalIterationCount > 64) {
         throw new NumberIsTooLargeException(maximalIterationCount, 64, false);
      }
   }

   public SimpsonIntegrator(int minimalIterationCount, int maximalIterationCount) throws NotStrictlyPositiveException, NumberIsTooSmallException, NumberIsTooLargeException {
      super(minimalIterationCount, maximalIterationCount);
      if (maximalIterationCount > 64) {
         throw new NumberIsTooLargeException(maximalIterationCount, 64, false);
      }
   }

   public SimpsonIntegrator() {
      super(3, 64);
   }

   protected double doIntegrate() throws TooManyEvaluationsException, MaxCountExceededException {
      TrapezoidIntegrator qtrap = new TrapezoidIntegrator();
      if (this.getMinimalIterationCount() == 1) {
         return (4.0D * qtrap.stage(this, 1) - qtrap.stage(this, 0)) / 3.0D;
      } else {
         double olds = 0.0D;
         double oldt = qtrap.stage(this, 0);

         while(true) {
            double t = qtrap.stage(this, this.iterations.getCount());
            this.iterations.incrementCount();
            double s = (4.0D * t - oldt) / 3.0D;
            if (this.iterations.getCount() >= this.getMinimalIterationCount()) {
               double delta = FastMath.abs(s - olds);
               double rLimit = this.getRelativeAccuracy() * (FastMath.abs(olds) + FastMath.abs(s)) * 0.5D;
               if (delta <= rLimit || delta <= this.getAbsoluteAccuracy()) {
                  return s;
               }
            }

            olds = s;
            oldt = t;
         }
      }
   }
}
