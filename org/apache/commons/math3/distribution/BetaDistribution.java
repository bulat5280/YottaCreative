package org.apache.commons.math3.distribution;

import org.apache.commons.math3.exception.NumberIsTooSmallException;
import org.apache.commons.math3.exception.util.LocalizedFormats;
import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.random.Well19937c;
import org.apache.commons.math3.special.Beta;
import org.apache.commons.math3.special.Gamma;
import org.apache.commons.math3.util.FastMath;

public class BetaDistribution extends AbstractRealDistribution {
   public static final double DEFAULT_INVERSE_ABSOLUTE_ACCURACY = 1.0E-9D;
   private static final long serialVersionUID = -1221965979403477668L;
   private final double alpha;
   private final double beta;
   private double z;
   private final double solverAbsoluteAccuracy;

   public BetaDistribution(double alpha, double beta) {
      this(alpha, beta, 1.0E-9D);
   }

   public BetaDistribution(double alpha, double beta, double inverseCumAccuracy) {
      this(new Well19937c(), alpha, beta, inverseCumAccuracy);
   }

   public BetaDistribution(RandomGenerator rng, double alpha, double beta, double inverseCumAccuracy) {
      super(rng);
      this.alpha = alpha;
      this.beta = beta;
      this.z = Double.NaN;
      this.solverAbsoluteAccuracy = inverseCumAccuracy;
   }

   public double getAlpha() {
      return this.alpha;
   }

   public double getBeta() {
      return this.beta;
   }

   private void recomputeZ() {
      if (Double.isNaN(this.z)) {
         this.z = Gamma.logGamma(this.alpha) + Gamma.logGamma(this.beta) - Gamma.logGamma(this.alpha + this.beta);
      }

   }

   public double density(double x) {
      this.recomputeZ();
      if (!(x < 0.0D) && !(x > 1.0D)) {
         if (x == 0.0D) {
            if (this.alpha < 1.0D) {
               throw new NumberIsTooSmallException(LocalizedFormats.CANNOT_COMPUTE_BETA_DENSITY_AT_0_FOR_SOME_ALPHA, this.alpha, 1, false);
            } else {
               return 0.0D;
            }
         } else if (x == 1.0D) {
            if (this.beta < 1.0D) {
               throw new NumberIsTooSmallException(LocalizedFormats.CANNOT_COMPUTE_BETA_DENSITY_AT_1_FOR_SOME_BETA, this.beta, 1, false);
            } else {
               return 0.0D;
            }
         } else {
            double logX = FastMath.log(x);
            double log1mX = FastMath.log1p(-x);
            return FastMath.exp((this.alpha - 1.0D) * logX + (this.beta - 1.0D) * log1mX - this.z);
         }
      } else {
         return 0.0D;
      }
   }

   public double cumulativeProbability(double x) {
      if (x <= 0.0D) {
         return 0.0D;
      } else {
         return x >= 1.0D ? 1.0D : Beta.regularizedBeta(x, this.alpha, this.beta);
      }
   }

   protected double getSolverAbsoluteAccuracy() {
      return this.solverAbsoluteAccuracy;
   }

   public double getNumericalMean() {
      double a = this.getAlpha();
      return a / (a + this.getBeta());
   }

   public double getNumericalVariance() {
      double a = this.getAlpha();
      double b = this.getBeta();
      double alphabetasum = a + b;
      return a * b / (alphabetasum * alphabetasum * (alphabetasum + 1.0D));
   }

   public double getSupportLowerBound() {
      return 0.0D;
   }

   public double getSupportUpperBound() {
      return 1.0D;
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
