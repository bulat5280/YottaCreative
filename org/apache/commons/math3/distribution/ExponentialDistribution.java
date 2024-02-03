package org.apache.commons.math3.distribution;

import org.apache.commons.math3.exception.NotStrictlyPositiveException;
import org.apache.commons.math3.exception.OutOfRangeException;
import org.apache.commons.math3.exception.util.LocalizedFormats;
import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.random.Well19937c;
import org.apache.commons.math3.util.ArithmeticUtils;
import org.apache.commons.math3.util.FastMath;
import org.apache.commons.math3.util.ResizableDoubleArray;

public class ExponentialDistribution extends AbstractRealDistribution {
   public static final double DEFAULT_INVERSE_ABSOLUTE_ACCURACY = 1.0E-9D;
   private static final long serialVersionUID = 2401296428283614780L;
   private static final double[] EXPONENTIAL_SA_QI;
   private final double mean;
   private final double solverAbsoluteAccuracy;

   public ExponentialDistribution(double mean) {
      this(mean, 1.0E-9D);
   }

   public ExponentialDistribution(double mean, double inverseCumAccuracy) {
      this(new Well19937c(), mean, inverseCumAccuracy);
   }

   public ExponentialDistribution(RandomGenerator rng, double mean, double inverseCumAccuracy) throws NotStrictlyPositiveException {
      super(rng);
      if (mean <= 0.0D) {
         throw new NotStrictlyPositiveException(LocalizedFormats.MEAN, mean);
      } else {
         this.mean = mean;
         this.solverAbsoluteAccuracy = inverseCumAccuracy;
      }
   }

   public double getMean() {
      return this.mean;
   }

   public double density(double x) {
      return x < 0.0D ? 0.0D : FastMath.exp(-x / this.mean) / this.mean;
   }

   public double cumulativeProbability(double x) {
      double ret;
      if (x <= 0.0D) {
         ret = 0.0D;
      } else {
         ret = 1.0D - FastMath.exp(-x / this.mean);
      }

      return ret;
   }

   public double inverseCumulativeProbability(double p) throws OutOfRangeException {
      if (!(p < 0.0D) && !(p > 1.0D)) {
         double ret;
         if (p == 1.0D) {
            ret = Double.POSITIVE_INFINITY;
         } else {
            ret = -this.mean * FastMath.log(1.0D - p);
         }

         return ret;
      } else {
         throw new OutOfRangeException(p, 0.0D, 1.0D);
      }
   }

   public double sample() {
      double a = 0.0D;

      double u;
      for(u = this.random.nextDouble(); u < 0.5D; u *= 2.0D) {
         a += EXPONENTIAL_SA_QI[0];
      }

      u += u - 1.0D;
      if (u <= EXPONENTIAL_SA_QI[0]) {
         return this.mean * (a + u);
      } else {
         int i = 0;
         double u2 = this.random.nextDouble();
         double umin = u2;

         do {
            ++i;
            u2 = this.random.nextDouble();
            if (u2 < umin) {
               umin = u2;
            }
         } while(u > EXPONENTIAL_SA_QI[i]);

         return this.mean * (a + umin * EXPONENTIAL_SA_QI[0]);
      }
   }

   protected double getSolverAbsoluteAccuracy() {
      return this.solverAbsoluteAccuracy;
   }

   public double getNumericalMean() {
      return this.getMean();
   }

   public double getNumericalVariance() {
      double m = this.getMean();
      return m * m;
   }

   public double getSupportLowerBound() {
      return 0.0D;
   }

   public double getSupportUpperBound() {
      return Double.POSITIVE_INFINITY;
   }

   public boolean isSupportLowerBoundInclusive() {
      return true;
   }

   public boolean isSupportUpperBoundInclusive() {
      return false;
   }

   public boolean isSupportConnected() {
      return true;
   }

   static {
      double LN2 = FastMath.log(2.0D);
      double qi = 0.0D;
      int i = 1;

      ResizableDoubleArray ra;
      for(ra = new ResizableDoubleArray(20); qi < 1.0D; ++i) {
         qi += FastMath.pow(LN2, i) / (double)ArithmeticUtils.factorial(i);
         ra.addElement(qi);
      }

      EXPONENTIAL_SA_QI = ra.getElements();
   }
}
