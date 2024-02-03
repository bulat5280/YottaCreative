package org.apache.commons.math3.distribution;

import org.apache.commons.math3.exception.NotStrictlyPositiveException;
import org.apache.commons.math3.exception.OutOfRangeException;
import org.apache.commons.math3.exception.util.LocalizedFormats;
import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.random.Well19937c;
import org.apache.commons.math3.special.Beta;
import org.apache.commons.math3.util.ArithmeticUtils;
import org.apache.commons.math3.util.FastMath;

public class PascalDistribution extends AbstractIntegerDistribution {
   private static final long serialVersionUID = 6751309484392813623L;
   private final int numberOfSuccesses;
   private final double probabilityOfSuccess;

   public PascalDistribution(int r, double p) throws NotStrictlyPositiveException, OutOfRangeException {
      this(new Well19937c(), r, p);
   }

   public PascalDistribution(RandomGenerator rng, int r, double p) throws NotStrictlyPositiveException, OutOfRangeException {
      super(rng);
      if (r <= 0) {
         throw new NotStrictlyPositiveException(LocalizedFormats.NUMBER_OF_SUCCESSES, r);
      } else if (!(p < 0.0D) && !(p > 1.0D)) {
         this.numberOfSuccesses = r;
         this.probabilityOfSuccess = p;
      } else {
         throw new OutOfRangeException(p, 0, 1);
      }
   }

   public int getNumberOfSuccesses() {
      return this.numberOfSuccesses;
   }

   public double getProbabilityOfSuccess() {
      return this.probabilityOfSuccess;
   }

   public double probability(int x) {
      double ret;
      if (x < 0) {
         ret = 0.0D;
      } else {
         ret = ArithmeticUtils.binomialCoefficientDouble(x + this.numberOfSuccesses - 1, this.numberOfSuccesses - 1) * FastMath.pow(this.probabilityOfSuccess, this.numberOfSuccesses) * FastMath.pow(1.0D - this.probabilityOfSuccess, x);
      }

      return ret;
   }

   public double cumulativeProbability(int x) {
      double ret;
      if (x < 0) {
         ret = 0.0D;
      } else {
         ret = Beta.regularizedBeta(this.probabilityOfSuccess, (double)this.numberOfSuccesses, (double)x + 1.0D);
      }

      return ret;
   }

   public double getNumericalMean() {
      double p = this.getProbabilityOfSuccess();
      double r = (double)this.getNumberOfSuccesses();
      return r * (1.0D - p) / p;
   }

   public double getNumericalVariance() {
      double p = this.getProbabilityOfSuccess();
      double r = (double)this.getNumberOfSuccesses();
      return r * (1.0D - p) / (p * p);
   }

   public int getSupportLowerBound() {
      return 0;
   }

   public int getSupportUpperBound() {
      return Integer.MAX_VALUE;
   }

   public boolean isSupportConnected() {
      return true;
   }
}
