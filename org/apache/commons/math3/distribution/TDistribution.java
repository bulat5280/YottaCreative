package org.apache.commons.math3.distribution;

import org.apache.commons.math3.exception.NotStrictlyPositiveException;
import org.apache.commons.math3.exception.util.LocalizedFormats;
import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.random.Well19937c;
import org.apache.commons.math3.special.Beta;
import org.apache.commons.math3.special.Gamma;
import org.apache.commons.math3.util.FastMath;

public class TDistribution extends AbstractRealDistribution {
   public static final double DEFAULT_INVERSE_ABSOLUTE_ACCURACY = 1.0E-9D;
   private static final long serialVersionUID = -5852615386664158222L;
   private final double degreesOfFreedom;
   private final double solverAbsoluteAccuracy;

   public TDistribution(double degreesOfFreedom) throws NotStrictlyPositiveException {
      this(degreesOfFreedom, 1.0E-9D);
   }

   public TDistribution(double degreesOfFreedom, double inverseCumAccuracy) throws NotStrictlyPositiveException {
      this(new Well19937c(), degreesOfFreedom, inverseCumAccuracy);
   }

   public TDistribution(RandomGenerator rng, double degreesOfFreedom, double inverseCumAccuracy) throws NotStrictlyPositiveException {
      super(rng);
      if (degreesOfFreedom <= 0.0D) {
         throw new NotStrictlyPositiveException(LocalizedFormats.DEGREES_OF_FREEDOM, degreesOfFreedom);
      } else {
         this.degreesOfFreedom = degreesOfFreedom;
         this.solverAbsoluteAccuracy = inverseCumAccuracy;
      }
   }

   public double getDegreesOfFreedom() {
      return this.degreesOfFreedom;
   }

   public double density(double x) {
      double n = this.degreesOfFreedom;
      double nPlus1Over2 = (n + 1.0D) / 2.0D;
      return FastMath.exp(Gamma.logGamma(nPlus1Over2) - 0.5D * (FastMath.log(3.141592653589793D) + FastMath.log(n)) - Gamma.logGamma(n / 2.0D) - nPlus1Over2 * FastMath.log(1.0D + x * x / n));
   }

   public double cumulativeProbability(double x) {
      double ret;
      if (x == 0.0D) {
         ret = 0.5D;
      } else {
         double t = Beta.regularizedBeta(this.degreesOfFreedom / (this.degreesOfFreedom + x * x), 0.5D * this.degreesOfFreedom, 0.5D);
         if (x < 0.0D) {
            ret = 0.5D * t;
         } else {
            ret = 1.0D - 0.5D * t;
         }
      }

      return ret;
   }

   protected double getSolverAbsoluteAccuracy() {
      return this.solverAbsoluteAccuracy;
   }

   public double getNumericalMean() {
      double df = this.getDegreesOfFreedom();
      return df > 1.0D ? 0.0D : Double.NaN;
   }

   public double getNumericalVariance() {
      double df = this.getDegreesOfFreedom();
      if (df > 2.0D) {
         return df / (df - 2.0D);
      } else {
         return df > 1.0D && df <= 2.0D ? Double.POSITIVE_INFINITY : Double.NaN;
      }
   }

   public double getSupportLowerBound() {
      return Double.NEGATIVE_INFINITY;
   }

   public double getSupportUpperBound() {
      return Double.POSITIVE_INFINITY;
   }

   public boolean isSupportLowerBoundInclusive() {
      return false;
   }

   public boolean isSupportUpperBoundInclusive() {
      return false;
   }

   public boolean isSupportConnected() {
      return true;
   }
}
