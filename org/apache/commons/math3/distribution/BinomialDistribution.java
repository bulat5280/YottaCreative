package org.apache.commons.math3.distribution;

import org.apache.commons.math3.exception.NotPositiveException;
import org.apache.commons.math3.exception.OutOfRangeException;
import org.apache.commons.math3.exception.util.LocalizedFormats;
import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.random.Well19937c;
import org.apache.commons.math3.special.Beta;
import org.apache.commons.math3.util.FastMath;

public class BinomialDistribution extends AbstractIntegerDistribution {
   private static final long serialVersionUID = 6751309484392813623L;
   private final int numberOfTrials;
   private final double probabilityOfSuccess;

   public BinomialDistribution(int trials, double p) {
      this(new Well19937c(), trials, p);
   }

   public BinomialDistribution(RandomGenerator rng, int trials, double p) {
      super(rng);
      if (trials < 0) {
         throw new NotPositiveException(LocalizedFormats.NUMBER_OF_TRIALS, trials);
      } else if (!(p < 0.0D) && !(p > 1.0D)) {
         this.probabilityOfSuccess = p;
         this.numberOfTrials = trials;
      } else {
         throw new OutOfRangeException(p, 0, 1);
      }
   }

   public int getNumberOfTrials() {
      return this.numberOfTrials;
   }

   public double getProbabilityOfSuccess() {
      return this.probabilityOfSuccess;
   }

   public double probability(int x) {
      double ret;
      if (x >= 0 && x <= this.numberOfTrials) {
         ret = FastMath.exp(SaddlePointExpansion.logBinomialProbability(x, this.numberOfTrials, this.probabilityOfSuccess, 1.0D - this.probabilityOfSuccess));
      } else {
         ret = 0.0D;
      }

      return ret;
   }

   public double cumulativeProbability(int x) {
      double ret;
      if (x < 0) {
         ret = 0.0D;
      } else if (x >= this.numberOfTrials) {
         ret = 1.0D;
      } else {
         ret = 1.0D - Beta.regularizedBeta(this.probabilityOfSuccess, (double)x + 1.0D, (double)(this.numberOfTrials - x));
      }

      return ret;
   }

   public double getNumericalMean() {
      return (double)this.numberOfTrials * this.probabilityOfSuccess;
   }

   public double getNumericalVariance() {
      double p = this.probabilityOfSuccess;
      return (double)this.numberOfTrials * p * (1.0D - p);
   }

   public int getSupportLowerBound() {
      return this.probabilityOfSuccess < 1.0D ? 0 : this.numberOfTrials;
   }

   public int getSupportUpperBound() {
      return this.probabilityOfSuccess > 0.0D ? this.numberOfTrials : 0;
   }

   public boolean isSupportConnected() {
      return true;
   }
}
