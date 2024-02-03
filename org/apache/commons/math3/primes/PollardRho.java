package org.apache.commons.math3.primes;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.math3.util.FastMath;

class PollardRho {
   private PollardRho() {
   }

   public static List<Integer> primeFactors(int n) {
      List<Integer> factors = new ArrayList();
      n = SmallPrimes.smallTrialDivision(n, factors);
      if (1 == n) {
         return factors;
      } else if (SmallPrimes.millerRabinPrimeTest(n)) {
         factors.add(n);
         return factors;
      } else {
         int divisor = rhoBrent(n);
         factors.add(divisor);
         factors.add(n / divisor);
         return factors;
      }
   }

   static int rhoBrent(int n) {
      int x0 = true;
      int m = true;
      int cst = SmallPrimes.PRIMES_LAST;
      int y = 2;
      int r = 1;

      while(true) {
         int x = y;

         int k;
         for(k = 0; k < r; ++k) {
            long y2 = (long)y * (long)y;
            y = (int)((y2 + (long)cst) % (long)n);
         }

         k = 0;

         do {
            int bound = FastMath.min(25, r - k);
            int q = 1;

            int out;
            for(out = -3; out < bound; ++out) {
               long y2 = (long)y * (long)y;
               y = (int)((y2 + (long)cst) % (long)n);
               long divisor = (long)FastMath.abs(x - y);
               if (0L == divisor) {
                  cst += SmallPrimes.PRIMES_LAST;
                  k = -25;
                  y = 2;
                  r = 1;
                  break;
               }

               long prod = divisor * (long)q;
               q = (int)(prod % (long)n);
               if (0 == q) {
                  return gcdPositive(FastMath.abs((int)divisor), n);
               }
            }

            out = gcdPositive(FastMath.abs(q), n);
            if (1 != out) {
               return out;
            }

            k += 25;
         } while(k < r);

         r = 2 * r;
      }
   }

   static int gcdPositive(int a, int b) {
      if (a == 0) {
         return b;
      } else if (b == 0) {
         return a;
      } else {
         int aTwos = Integer.numberOfTrailingZeros(a);
         a >>= aTwos;
         int bTwos = Integer.numberOfTrailingZeros(b);
         b >>= bTwos;

         int shift;
         for(shift = FastMath.min(aTwos, bTwos); a != b; a >>= Integer.numberOfTrailingZeros(a)) {
            int delta = a - b;
            b = FastMath.min(a, b);
            a = FastMath.abs(delta);
         }

         return a << shift;
      }
   }
}
