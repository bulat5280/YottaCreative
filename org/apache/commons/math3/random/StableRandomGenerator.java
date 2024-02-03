package org.apache.commons.math3.random;

import org.apache.commons.math3.exception.NullArgumentException;
import org.apache.commons.math3.exception.OutOfRangeException;
import org.apache.commons.math3.exception.util.LocalizedFormats;
import org.apache.commons.math3.util.FastMath;

public class StableRandomGenerator implements NormalizedRandomGenerator {
   private final RandomGenerator generator;
   private final double alpha;
   private final double beta;
   private final double zeta;

   public StableRandomGenerator(RandomGenerator generator, double alpha, double beta) throws NullArgumentException, OutOfRangeException {
      if (generator == null) {
         throw new NullArgumentException();
      } else if (alpha > 0.0D && alpha <= 2.0D) {
         if (beta >= -1.0D && beta <= 1.0D) {
            this.generator = generator;
            this.alpha = alpha;
            this.beta = beta;
            if (alpha < 2.0D && beta != 0.0D) {
               this.zeta = beta * FastMath.tan(3.141592653589793D * alpha / 2.0D);
            } else {
               this.zeta = 0.0D;
            }

         } else {
            throw new OutOfRangeException(LocalizedFormats.OUT_OF_RANGE_SIMPLE, beta, -1, 1);
         }
      } else {
         throw new OutOfRangeException(LocalizedFormats.OUT_OF_RANGE_LEFT, alpha, 0, 2);
      }
   }

   public double nextNormalizedDouble() {
      double omega = -FastMath.log(this.generator.nextDouble());
      double phi = 3.141592653589793D * (this.generator.nextDouble() - 0.5D);
      if (this.alpha == 2.0D) {
         return FastMath.sqrt(2.0D * omega) * FastMath.sin(phi);
      } else {
         double x;
         if (this.beta == 0.0D) {
            if (this.alpha == 1.0D) {
               x = FastMath.tan(phi);
            } else {
               x = FastMath.pow(omega * FastMath.cos((1.0D - this.alpha) * phi), 1.0D / this.alpha - 1.0D) * FastMath.sin(this.alpha * phi) / FastMath.pow(FastMath.cos(phi), 1.0D / this.alpha);
            }
         } else {
            double cosPhi = FastMath.cos(phi);
            double alphaPhi;
            if (FastMath.abs(this.alpha - 1.0D) > 1.0E-8D) {
               alphaPhi = this.alpha * phi;
               double invAlphaPhi = phi - alphaPhi;
               x = (FastMath.sin(alphaPhi) + this.zeta * FastMath.cos(alphaPhi)) / cosPhi * (FastMath.cos(invAlphaPhi) + this.zeta * FastMath.sin(invAlphaPhi)) / FastMath.pow(omega * cosPhi, (1.0D - this.alpha) / this.alpha);
            } else {
               alphaPhi = 1.5707963267948966D + this.beta * phi;
               x = 0.6366197723675814D * (alphaPhi * FastMath.tan(phi) - this.beta * FastMath.log(1.5707963267948966D * omega * cosPhi / alphaPhi));
               if (this.alpha != 1.0D) {
                  x += this.beta * FastMath.tan(3.141592653589793D * this.alpha / 2.0D);
               }
            }
         }

         return x;
      }
   }
}
