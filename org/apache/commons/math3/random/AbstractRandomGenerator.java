package org.apache.commons.math3.random;

import org.apache.commons.math3.exception.NotStrictlyPositiveException;
import org.apache.commons.math3.util.FastMath;

public abstract class AbstractRandomGenerator implements RandomGenerator {
   private double cachedNormalDeviate = Double.NaN;

   public void clear() {
      this.cachedNormalDeviate = Double.NaN;
   }

   public void setSeed(int seed) {
      this.setSeed((long)seed);
   }

   public void setSeed(int[] seed) {
      long prime = 4294967291L;
      long combined = 0L;
      int[] arr$ = seed;
      int len$ = seed.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         int s = arr$[i$];
         combined = combined * 4294967291L + (long)s;
      }

      this.setSeed(combined);
   }

   public abstract void setSeed(long var1);

   public void nextBytes(byte[] bytes) {
      int bytesOut = 0;

      while(bytesOut < bytes.length) {
         int randInt = this.nextInt();

         for(int i = 0; i < 3; ++i) {
            if (i > 0) {
               randInt >>= 8;
            }

            bytes[bytesOut++] = (byte)randInt;
            if (bytesOut == bytes.length) {
               return;
            }
         }
      }

   }

   public int nextInt() {
      return (int)((2.0D * this.nextDouble() - 1.0D) * 2.147483647E9D);
   }

   public int nextInt(int n) {
      if (n <= 0) {
         throw new NotStrictlyPositiveException(n);
      } else {
         int result = (int)(this.nextDouble() * (double)n);
         return result < n ? result : n - 1;
      }
   }

   public long nextLong() {
      return (long)((2.0D * this.nextDouble() - 1.0D) * 9.223372036854776E18D);
   }

   public boolean nextBoolean() {
      return this.nextDouble() <= 0.5D;
   }

   public float nextFloat() {
      return (float)this.nextDouble();
   }

   public abstract double nextDouble();

   public double nextGaussian() {
      double v1;
      if (!Double.isNaN(this.cachedNormalDeviate)) {
         v1 = this.cachedNormalDeviate;
         this.cachedNormalDeviate = Double.NaN;
         return v1;
      } else {
         v1 = 0.0D;
         double v2 = 0.0D;

         double s;
         for(s = 1.0D; s >= 1.0D; s = v1 * v1 + v2 * v2) {
            v1 = 2.0D * this.nextDouble() - 1.0D;
            v2 = 2.0D * this.nextDouble() - 1.0D;
         }

         if (s != 0.0D) {
            s = FastMath.sqrt(-2.0D * FastMath.log(s) / s);
         }

         this.cachedNormalDeviate = v2 * s;
         return v1 * s;
      }
   }
}
