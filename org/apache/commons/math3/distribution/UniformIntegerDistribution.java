package org.apache.commons.math3.distribution;

import org.apache.commons.math3.exception.NumberIsTooLargeException;
import org.apache.commons.math3.exception.util.LocalizedFormats;
import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.random.Well19937c;
import org.apache.commons.math3.util.FastMath;

public class UniformIntegerDistribution extends AbstractIntegerDistribution {
   private static final long serialVersionUID = 20120109L;
   private final int lower;
   private final int upper;

   public UniformIntegerDistribution(int lower, int upper) throws NumberIsTooLargeException {
      this(new Well19937c(), lower, upper);
   }

   public UniformIntegerDistribution(RandomGenerator rng, int lower, int upper) throws NumberIsTooLargeException {
      super(rng);
      if (lower >= upper) {
         throw new NumberIsTooLargeException(LocalizedFormats.LOWER_BOUND_NOT_BELOW_UPPER_BOUND, lower, upper, false);
      } else {
         this.lower = lower;
         this.upper = upper;
      }
   }

   public double probability(int x) {
      return x >= this.lower && x <= this.upper ? 1.0D / (double)(this.upper - this.lower + 1) : 0.0D;
   }

   public double cumulativeProbability(int x) {
      if (x < this.lower) {
         return 0.0D;
      } else {
         return x > this.upper ? 1.0D : ((double)(x - this.lower) + 1.0D) / ((double)(this.upper - this.lower) + 1.0D);
      }
   }

   public double getNumericalMean() {
      return 0.5D * (double)(this.lower + this.upper);
   }

   public double getNumericalVariance() {
      double n = (double)(this.upper - this.lower + 1);
      return (n * n - 1.0D) / 12.0D;
   }

   public int getSupportLowerBound() {
      return this.lower;
   }

   public int getSupportUpperBound() {
      return this.upper;
   }

   public boolean isSupportConnected() {
      return true;
   }

   public int sample() {
      double r = this.random.nextDouble();
      double scaled = r * (double)this.upper + (1.0D - r) * (double)this.lower + r;
      return (int)FastMath.floor(scaled);
   }
}
