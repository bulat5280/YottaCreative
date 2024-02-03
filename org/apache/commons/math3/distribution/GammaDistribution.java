package org.apache.commons.math3.distribution;

import org.apache.commons.math3.exception.NotStrictlyPositiveException;
import org.apache.commons.math3.exception.util.LocalizedFormats;
import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.random.Well19937c;
import org.apache.commons.math3.special.Gamma;
import org.apache.commons.math3.util.FastMath;

public class GammaDistribution extends AbstractRealDistribution {
   public static final double DEFAULT_INVERSE_ABSOLUTE_ACCURACY = 1.0E-9D;
   private static final long serialVersionUID = 20120524L;
   private final double shape;
   private final double scale;
   private final double shiftedShape;
   private final double densityPrefactor1;
   private final double densityPrefactor2;
   private final double minY;
   private final double maxLogY;
   private final double solverAbsoluteAccuracy;

   public GammaDistribution(double shape, double scale) throws NotStrictlyPositiveException {
      this(shape, scale, 1.0E-9D);
   }

   public GammaDistribution(double shape, double scale, double inverseCumAccuracy) throws NotStrictlyPositiveException {
      this(new Well19937c(), shape, scale, inverseCumAccuracy);
   }

   public GammaDistribution(RandomGenerator rng, double shape, double scale, double inverseCumAccuracy) throws NotStrictlyPositiveException {
      super(rng);
      if (shape <= 0.0D) {
         throw new NotStrictlyPositiveException(LocalizedFormats.SHAPE, shape);
      } else if (scale <= 0.0D) {
         throw new NotStrictlyPositiveException(LocalizedFormats.SCALE, scale);
      } else {
         this.shape = shape;
         this.scale = scale;
         this.solverAbsoluteAccuracy = inverseCumAccuracy;
         this.shiftedShape = shape + 4.7421875D + 0.5D;
         double aux = 2.718281828459045D / (6.283185307179586D * this.shiftedShape);
         this.densityPrefactor2 = shape * FastMath.sqrt(aux) / Gamma.lanczos(shape);
         this.densityPrefactor1 = this.densityPrefactor2 / scale * FastMath.pow(this.shiftedShape, -shape) * FastMath.exp(shape + 4.7421875D);
         this.minY = shape + 4.7421875D - FastMath.log(Double.MAX_VALUE);
         this.maxLogY = FastMath.log(Double.MAX_VALUE) / (shape - 1.0D);
      }
   }

   /** @deprecated */
   @Deprecated
   public double getAlpha() {
      return this.shape;
   }

   public double getShape() {
      return this.shape;
   }

   /** @deprecated */
   @Deprecated
   public double getBeta() {
      return this.scale;
   }

   public double getScale() {
      return this.scale;
   }

   public double density(double x) {
      if (x < 0.0D) {
         return 0.0D;
      } else {
         double y = x / this.scale;
         if (!(y <= this.minY) && !(FastMath.log(y) >= this.maxLogY)) {
            return this.densityPrefactor1 * FastMath.exp(-y) * FastMath.pow(y, this.shape - 1.0D);
         } else {
            double aux1 = (y - this.shiftedShape) / this.shiftedShape;
            double aux2 = this.shape * (FastMath.log1p(aux1) - aux1);
            double aux3 = -y * 5.2421875D / this.shiftedShape + 4.7421875D + aux2;
            return this.densityPrefactor2 / x * FastMath.exp(aux3);
         }
      }
   }

   public double cumulativeProbability(double x) {
      double ret;
      if (x <= 0.0D) {
         ret = 0.0D;
      } else {
         ret = Gamma.regularizedGammaP(this.shape, x / this.scale);
      }

      return ret;
   }

   protected double getSolverAbsoluteAccuracy() {
      return this.solverAbsoluteAccuracy;
   }

   public double getNumericalMean() {
      return this.shape * this.scale;
   }

   public double getNumericalVariance() {
      return this.shape * this.scale * this.scale;
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

   public double sample() {
      double d;
      double bGS;
      double x;
      double v;
      double x2;
      if (this.shape < 1.0D) {
         label29:
         do {
            do {
               d = this.random.nextDouble();
               bGS = 1.0D + this.shape / 2.718281828459045D;
               x = bGS * d;
               if (x <= 1.0D) {
                  v = FastMath.pow(x, 1.0D / this.shape);
                  x2 = this.random.nextDouble();
                  continue label29;
               }

               v = -1.0D * FastMath.log((bGS - x) / this.shape);
               x2 = this.random.nextDouble();
            } while(x2 > FastMath.pow(v, this.shape - 1.0D));

            return this.scale * v;
         } while(x2 > FastMath.exp(-v));

         return this.scale * v;
      } else {
         d = this.shape - 0.3333333333333333D;
         bGS = 1.0D / (3.0D * FastMath.sqrt(d));

         double u;
         do {
            do {
               x = this.random.nextGaussian();
               v = (1.0D + bGS * x) * (1.0D + bGS * x) * (1.0D + bGS * x);
            } while(v <= 0.0D);

            x2 = x * x;
            u = this.random.nextDouble();
            if (u < 1.0D - 0.0331D * x2 * x2) {
               return this.scale * d * v;
            }
         } while(!(FastMath.log(u) < 0.5D * x2 + d * (1.0D - v + FastMath.log(v))));

         return this.scale * d * v;
      }
   }
}
