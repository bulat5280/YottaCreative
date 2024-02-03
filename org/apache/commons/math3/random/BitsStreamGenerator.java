package org.apache.commons.math3.random;

import java.io.Serializable;
import org.apache.commons.math3.exception.NotStrictlyPositiveException;
import org.apache.commons.math3.util.FastMath;

public abstract class BitsStreamGenerator implements RandomGenerator, Serializable {
   private static final long serialVersionUID = 20130104L;
   private double nextGaussian = Double.NaN;

   public abstract void setSeed(int var1);

   public abstract void setSeed(int[] var1);

   public abstract void setSeed(long var1);

   protected abstract int next(int var1);

   public boolean nextBoolean() {
      return this.next(1) != 0;
   }

   public void nextBytes(byte[] bytes) {
      int i = 0;

      int random;
      for(int iEnd = bytes.length - 3; i < iEnd; i += 4) {
         random = this.next(32);
         bytes[i] = (byte)(random & 255);
         bytes[i + 1] = (byte)(random >> 8 & 255);
         bytes[i + 2] = (byte)(random >> 16 & 255);
         bytes[i + 3] = (byte)(random >> 24 & 255);
      }

      for(random = this.next(32); i < bytes.length; random >>= 8) {
         bytes[i++] = (byte)(random & 255);
      }

   }

   public double nextDouble() {
      long high = (long)this.next(26) << 26;
      int low = this.next(26);
      return (double)(high | (long)low) * 2.220446049250313E-16D;
   }

   public float nextFloat() {
      return (float)this.next(23) * 1.1920929E-7F;
   }

   public double nextGaussian() {
      double random;
      if (Double.isNaN(this.nextGaussian)) {
         double x = this.nextDouble();
         double y = this.nextDouble();
         double alpha = 6.283185307179586D * x;
         double r = FastMath.sqrt(-2.0D * FastMath.log(y));
         random = r * FastMath.cos(alpha);
         this.nextGaussian = r * FastMath.sin(alpha);
      } else {
         random = this.nextGaussian;
         this.nextGaussian = Double.NaN;
      }

      return random;
   }

   public int nextInt() {
      return this.next(32);
   }

   public int nextInt(int n) throws IllegalArgumentException {
      if (n <= 0) {
         throw new NotStrictlyPositiveException(n);
      } else if ((n & -n) == n) {
         return (int)((long)n * (long)this.next(31) >> 31);
      } else {
         int bits;
         int val;
         do {
            bits = this.next(31);
            val = bits % n;
         } while(bits - val + (n - 1) < 0);

         return val;
      }
   }

   public long nextLong() {
      long high = (long)this.next(32) << 32;
      long low = (long)this.next(32) & 4294967295L;
      return high | low;
   }

   public long nextLong(long n) throws IllegalArgumentException {
      if (n <= 0L) {
         throw new NotStrictlyPositiveException(n);
      } else {
         long bits;
         long val;
         do {
            bits = (long)this.next(31) << 32;
            bits |= (long)this.next(32) & 4294967295L;
            val = bits % n;
         } while(bits - val + (n - 1L) < 0L);

         return val;
      }
   }

   public void clear() {
      this.nextGaussian = Double.NaN;
   }
}
