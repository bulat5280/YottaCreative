package org.apache.commons.math3.distribution;

import org.apache.commons.math3.exception.NumberIsTooLargeException;
import org.apache.commons.math3.exception.OutOfRangeException;
import org.apache.commons.math3.exception.util.LocalizedFormats;
import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.random.Well19937c;

public class UniformRealDistribution extends AbstractRealDistribution {
   /** @deprecated */
   @Deprecated
   public static final double DEFAULT_INVERSE_ABSOLUTE_ACCURACY = 1.0E-9D;
   private static final long serialVersionUID = 20120109L;
   private final double lower;
   private final double upper;

   public UniformRealDistribution() {
      this(0.0D, 1.0D);
   }

   public UniformRealDistribution(double lower, double upper) throws NumberIsTooLargeException {
      this(new Well19937c(), lower, upper);
   }

   /** @deprecated */
   @Deprecated
   public UniformRealDistribution(double lower, double upper, double inverseCumAccuracy) throws NumberIsTooLargeException {
      this(new Well19937c(), lower, upper);
   }

   /** @deprecated */
   @Deprecated
   public UniformRealDistribution(RandomGenerator rng, double lower, double upper, double inverseCumAccuracy) {
      this(rng, lower, upper);
   }

   public UniformRealDistribution(RandomGenerator rng, double lower, double upper) throws NumberIsTooLargeException {
      super(rng);
      if (lower >= upper) {
         throw new NumberIsTooLargeException(LocalizedFormats.LOWER_BOUND_NOT_BELOW_UPPER_BOUND, lower, upper, false);
      } else {
         this.lower = lower;
         this.upper = upper;
      }
   }

   public double density(double x) {
      return !(x < this.lower) && !(x > this.upper) ? 1.0D / (this.upper - this.lower) : 0.0D;
   }

   public double cumulativeProbability(double x) {
      if (x <= this.lower) {
         return 0.0D;
      } else {
         return x >= this.upper ? 1.0D : (x - this.lower) / (this.upper - this.lower);
      }
   }

   public double inverseCumulativeProbability(double p) throws OutOfRangeException {
      if (!(p < 0.0D) && !(p > 1.0D)) {
         return p * (this.upper - this.lower) + this.lower;
      } else {
         throw new OutOfRangeException(p, 0, 1);
      }
   }

   public double getNumericalMean() {
      return 0.5D * (this.lower + this.upper);
   }

   public double getNumericalVariance() {
      double ul = this.upper - this.lower;
      return ul * ul / 12.0D;
   }

   public double getSupportLowerBound() {
      return this.lower;
   }

   public double getSupportUpperBound() {
      return this.upper;
   }

   public boolean isSupportLowerBoundInclusive() {
      return true;
   }

   public boolean isSupportUpperBoundInclusive() {
      return true;
   }

   public boolean isSupportConnected() {
      return true;
   }

   public double sample() {
      double u = this.random.nextDouble();
      return u * this.upper + (1.0D - u) * this.lower;
   }
}
