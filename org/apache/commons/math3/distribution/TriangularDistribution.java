package org.apache.commons.math3.distribution;

import org.apache.commons.math3.exception.NumberIsTooLargeException;
import org.apache.commons.math3.exception.NumberIsTooSmallException;
import org.apache.commons.math3.exception.OutOfRangeException;
import org.apache.commons.math3.exception.util.LocalizedFormats;
import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.random.Well19937c;
import org.apache.commons.math3.util.FastMath;

public class TriangularDistribution extends AbstractRealDistribution {
   private static final long serialVersionUID = 20120112L;
   private final double a;
   private final double b;
   private final double c;
   private final double solverAbsoluteAccuracy;

   public TriangularDistribution(double a, double c, double b) throws NumberIsTooLargeException, NumberIsTooSmallException {
      this(new Well19937c(), a, c, b);
   }

   public TriangularDistribution(RandomGenerator rng, double a, double c, double b) throws NumberIsTooLargeException, NumberIsTooSmallException {
      super(rng);
      if (a >= b) {
         throw new NumberIsTooLargeException(LocalizedFormats.LOWER_BOUND_NOT_BELOW_UPPER_BOUND, a, b, false);
      } else if (c < a) {
         throw new NumberIsTooSmallException(LocalizedFormats.NUMBER_TOO_SMALL, c, a, true);
      } else if (c > b) {
         throw new NumberIsTooLargeException(LocalizedFormats.NUMBER_TOO_LARGE, c, b, true);
      } else {
         this.a = a;
         this.c = c;
         this.b = b;
         this.solverAbsoluteAccuracy = FastMath.max(FastMath.ulp(a), FastMath.ulp(b));
      }
   }

   public double getMode() {
      return this.c;
   }

   protected double getSolverAbsoluteAccuracy() {
      return this.solverAbsoluteAccuracy;
   }

   public double density(double x) {
      if (x < this.a) {
         return 0.0D;
      } else {
         double divident;
         double divisor;
         if (this.a <= x && x < this.c) {
            divident = 2.0D * (x - this.a);
            divisor = (this.b - this.a) * (this.c - this.a);
            return divident / divisor;
         } else if (x == this.c) {
            return 2.0D / (this.b - this.a);
         } else if (this.c < x && x <= this.b) {
            divident = 2.0D * (this.b - x);
            divisor = (this.b - this.a) * (this.b - this.c);
            return divident / divisor;
         } else {
            return 0.0D;
         }
      }
   }

   public double cumulativeProbability(double x) {
      if (x < this.a) {
         return 0.0D;
      } else {
         double divident;
         double divisor;
         if (this.a <= x && x < this.c) {
            divident = (x - this.a) * (x - this.a);
            divisor = (this.b - this.a) * (this.c - this.a);
            return divident / divisor;
         } else if (x == this.c) {
            return (this.c - this.a) / (this.b - this.a);
         } else if (this.c < x && x <= this.b) {
            divident = (this.b - x) * (this.b - x);
            divisor = (this.b - this.a) * (this.b - this.c);
            return 1.0D - divident / divisor;
         } else {
            return 1.0D;
         }
      }
   }

   public double getNumericalMean() {
      return (this.a + this.b + this.c) / 3.0D;
   }

   public double getNumericalVariance() {
      return (this.a * this.a + this.b * this.b + this.c * this.c - this.a * this.b - this.a * this.c - this.b * this.c) / 18.0D;
   }

   public double getSupportLowerBound() {
      return this.a;
   }

   public double getSupportUpperBound() {
      return this.b;
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

   public double inverseCumulativeProbability(double p) throws OutOfRangeException {
      if (!(p < 0.0D) && !(p > 1.0D)) {
         if (p == 0.0D) {
            return this.a;
         } else if (p == 1.0D) {
            return this.b;
         } else {
            return p < (this.c - this.a) / (this.b - this.a) ? this.a + FastMath.sqrt(p * (this.b - this.a) * (this.c - this.a)) : this.b - FastMath.sqrt((1.0D - p) * (this.b - this.a) * (this.b - this.c));
         }
      } else {
         throw new OutOfRangeException(p, 0, 1);
      }
   }
}
