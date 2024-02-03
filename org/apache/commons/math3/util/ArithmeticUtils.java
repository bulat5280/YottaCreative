package org.apache.commons.math3.util;

import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicReference;
import org.apache.commons.math3.exception.MathArithmeticException;
import org.apache.commons.math3.exception.NotPositiveException;
import org.apache.commons.math3.exception.NumberIsTooLargeException;
import org.apache.commons.math3.exception.util.Localizable;
import org.apache.commons.math3.exception.util.LocalizedFormats;

public final class ArithmeticUtils {
   static final long[] FACTORIALS = new long[]{1L, 1L, 2L, 6L, 24L, 120L, 720L, 5040L, 40320L, 362880L, 3628800L, 39916800L, 479001600L, 6227020800L, 87178291200L, 1307674368000L, 20922789888000L, 355687428096000L, 6402373705728000L, 121645100408832000L, 2432902008176640000L};
   static final AtomicReference<long[][]> STIRLING_S2 = new AtomicReference((Object)null);

   private ArithmeticUtils() {
   }

   public static int addAndCheck(int x, int y) throws MathArithmeticException {
      long s = (long)x + (long)y;
      if (s >= -2147483648L && s <= 2147483647L) {
         return (int)s;
      } else {
         throw new MathArithmeticException(LocalizedFormats.OVERFLOW_IN_ADDITION, new Object[]{x, y});
      }
   }

   public static long addAndCheck(long a, long b) throws MathArithmeticException {
      return addAndCheck(a, b, LocalizedFormats.OVERFLOW_IN_ADDITION);
   }

   public static long binomialCoefficient(int n, int k) throws NotPositiveException, NumberIsTooLargeException, MathArithmeticException {
      checkBinomial(n, k);
      if (n != k && k != 0) {
         if (k != 1 && k != n - 1) {
            if (k > n / 2) {
               return binomialCoefficient(n, n - k);
            } else {
               long result = 1L;
               int i;
               int j;
               if (n <= 61) {
                  i = n - k + 1;

                  for(j = 1; j <= k; ++j) {
                     result = result * (long)i / (long)j;
                     ++i;
                  }
               } else {
                  long d;
                  if (n <= 66) {
                     i = n - k + 1;

                     for(j = 1; j <= k; ++j) {
                        d = (long)gcd(i, j);
                        result = result / ((long)j / d) * ((long)i / d);
                        ++i;
                     }
                  } else {
                     i = n - k + 1;

                     for(j = 1; j <= k; ++j) {
                        d = (long)gcd(i, j);
                        result = mulAndCheck(result / ((long)j / d), (long)i / d);
                        ++i;
                     }
                  }
               }

               return result;
            }
         } else {
            return (long)n;
         }
      } else {
         return 1L;
      }
   }

   public static double binomialCoefficientDouble(int n, int k) throws NotPositiveException, NumberIsTooLargeException, MathArithmeticException {
      checkBinomial(n, k);
      if (n != k && k != 0) {
         if (k != 1 && k != n - 1) {
            if (k > n / 2) {
               return binomialCoefficientDouble(n, n - k);
            } else if (n < 67) {
               return (double)binomialCoefficient(n, k);
            } else {
               double result = 1.0D;

               for(int i = 1; i <= k; ++i) {
                  result *= (double)(n - k + i) / (double)i;
               }

               return FastMath.floor(result + 0.5D);
            }
         } else {
            return (double)n;
         }
      } else {
         return 1.0D;
      }
   }

   public static double binomialCoefficientLog(int n, int k) throws NotPositiveException, NumberIsTooLargeException, MathArithmeticException {
      checkBinomial(n, k);
      if (n != k && k != 0) {
         if (k != 1 && k != n - 1) {
            if (n < 67) {
               return FastMath.log((double)binomialCoefficient(n, k));
            } else if (n < 1030) {
               return FastMath.log(binomialCoefficientDouble(n, k));
            } else if (k > n / 2) {
               return binomialCoefficientLog(n, n - k);
            } else {
               double logSum = 0.0D;

               int i;
               for(i = n - k + 1; i <= n; ++i) {
                  logSum += FastMath.log((double)i);
               }

               for(i = 2; i <= k; ++i) {
                  logSum -= FastMath.log((double)i);
               }

               return logSum;
            }
         } else {
            return FastMath.log((double)n);
         }
      } else {
         return 0.0D;
      }
   }

   public static long factorial(int n) throws NotPositiveException, MathArithmeticException {
      if (n < 0) {
         throw new NotPositiveException(LocalizedFormats.FACTORIAL_NEGATIVE_PARAMETER, n);
      } else if (n > 20) {
         throw new MathArithmeticException();
      } else {
         return FACTORIALS[n];
      }
   }

   public static double factorialDouble(int n) throws NotPositiveException {
      if (n < 0) {
         throw new NotPositiveException(LocalizedFormats.FACTORIAL_NEGATIVE_PARAMETER, n);
      } else {
         return n < 21 ? (double)FACTORIALS[n] : FastMath.floor(FastMath.exp(factorialLog(n)) + 0.5D);
      }
   }

   public static double factorialLog(int n) throws NotPositiveException {
      if (n < 0) {
         throw new NotPositiveException(LocalizedFormats.FACTORIAL_NEGATIVE_PARAMETER, n);
      } else if (n < 21) {
         return FastMath.log((double)FACTORIALS[n]);
      } else {
         double logSum = 0.0D;

         for(int i = 2; i <= n; ++i) {
            logSum += FastMath.log((double)i);
         }

         return logSum;
      }
   }

   public static int gcd(int p, int q) throws MathArithmeticException {
      int a = p;
      int b = q;
      if (p != 0 && q != 0) {
         long al = (long)p;
         long bl = (long)q;
         boolean useLong = false;
         if (p < 0) {
            if (Integer.MIN_VALUE == p) {
               useLong = true;
            } else {
               a = -p;
            }

            al = -al;
         }

         if (q < 0) {
            if (Integer.MIN_VALUE == q) {
               useLong = true;
            } else {
               b = -q;
            }

            bl = -bl;
         }

         if (useLong) {
            if (al == bl) {
               throw new MathArithmeticException(LocalizedFormats.GCD_OVERFLOW_32_BITS, new Object[]{p, q});
            }

            long blbu = bl;
            bl = al;
            al = blbu % al;
            if (al == 0L) {
               if (bl > 2147483647L) {
                  throw new MathArithmeticException(LocalizedFormats.GCD_OVERFLOW_32_BITS, new Object[]{p, q});
               }

               return (int)bl;
            }

            b = (int)al;
            a = (int)(bl % al);
         }

         return gcdPositive(a, b);
      } else if (p != Integer.MIN_VALUE && q != Integer.MIN_VALUE) {
         return FastMath.abs(p + q);
      } else {
         throw new MathArithmeticException(LocalizedFormats.GCD_OVERFLOW_32_BITS, new Object[]{p, q});
      }
   }

   private static int gcdPositive(int a, int b) {
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
         for(shift = Math.min(aTwos, bTwos); a != b; a >>= Integer.numberOfTrailingZeros(a)) {
            int delta = a - b;
            b = Math.min(a, b);
            a = Math.abs(delta);
         }

         return a << shift;
      }
   }

   public static long gcd(long p, long q) throws MathArithmeticException {
      long u = p;
      long v = q;
      if (p != 0L && q != 0L) {
         if (p > 0L) {
            u = -p;
         }

         if (q > 0L) {
            v = -q;
         }

         int k;
         for(k = 0; (u & 1L) == 0L && (v & 1L) == 0L && k < 63; ++k) {
            u /= 2L;
            v /= 2L;
         }

         if (k == 63) {
            throw new MathArithmeticException(LocalizedFormats.GCD_OVERFLOW_64_BITS, new Object[]{p, q});
         } else {
            long t = (u & 1L) == 1L ? v : -(u / 2L);

            while(true) {
               while((t & 1L) != 0L) {
                  if (t > 0L) {
                     u = -t;
                  } else {
                     v = t;
                  }

                  t = (v - u) / 2L;
                  if (t == 0L) {
                     return -u * (1L << k);
                  }
               }

               t /= 2L;
            }
         }
      } else if (p != Long.MIN_VALUE && q != Long.MIN_VALUE) {
         return FastMath.abs(p) + FastMath.abs(q);
      } else {
         throw new MathArithmeticException(LocalizedFormats.GCD_OVERFLOW_64_BITS, new Object[]{p, q});
      }
   }

   public static int lcm(int a, int b) throws MathArithmeticException {
      if (a != 0 && b != 0) {
         int lcm = FastMath.abs(mulAndCheck(a / gcd(a, b), b));
         if (lcm == Integer.MIN_VALUE) {
            throw new MathArithmeticException(LocalizedFormats.LCM_OVERFLOW_32_BITS, new Object[]{a, b});
         } else {
            return lcm;
         }
      } else {
         return 0;
      }
   }

   public static long lcm(long a, long b) throws MathArithmeticException {
      if (a != 0L && b != 0L) {
         long lcm = FastMath.abs(mulAndCheck(a / gcd(a, b), b));
         if (lcm == Long.MIN_VALUE) {
            throw new MathArithmeticException(LocalizedFormats.LCM_OVERFLOW_64_BITS, new Object[]{a, b});
         } else {
            return lcm;
         }
      } else {
         return 0L;
      }
   }

   public static int mulAndCheck(int x, int y) throws MathArithmeticException {
      long m = (long)x * (long)y;
      if (m >= -2147483648L && m <= 2147483647L) {
         return (int)m;
      } else {
         throw new MathArithmeticException();
      }
   }

   public static long mulAndCheck(long a, long b) throws MathArithmeticException {
      long ret;
      if (a > b) {
         ret = mulAndCheck(b, a);
      } else if (a < 0L) {
         if (b < 0L) {
            if (a < Long.MAX_VALUE / b) {
               throw new MathArithmeticException();
            }

            ret = a * b;
         } else if (b > 0L) {
            if (Long.MIN_VALUE / b > a) {
               throw new MathArithmeticException();
            }

            ret = a * b;
         } else {
            ret = 0L;
         }
      } else if (a > 0L) {
         if (a > Long.MAX_VALUE / b) {
            throw new MathArithmeticException();
         }

         ret = a * b;
      } else {
         ret = 0L;
      }

      return ret;
   }

   public static int subAndCheck(int x, int y) throws MathArithmeticException {
      long s = (long)x - (long)y;
      if (s >= -2147483648L && s <= 2147483647L) {
         return (int)s;
      } else {
         throw new MathArithmeticException(LocalizedFormats.OVERFLOW_IN_SUBTRACTION, new Object[]{x, y});
      }
   }

   public static long subAndCheck(long a, long b) throws MathArithmeticException {
      long ret;
      if (b == Long.MIN_VALUE) {
         if (a >= 0L) {
            throw new MathArithmeticException(LocalizedFormats.OVERFLOW_IN_ADDITION, new Object[]{a, -b});
         }

         ret = a - b;
      } else {
         ret = addAndCheck(a, -b, LocalizedFormats.OVERFLOW_IN_ADDITION);
      }

      return ret;
   }

   public static int pow(int k, int e) throws NotPositiveException {
      if (e < 0) {
         throw new NotPositiveException(LocalizedFormats.EXPONENT, e);
      } else {
         int result = 1;

         for(int k2p = k; e != 0; e >>= 1) {
            if ((e & 1) != 0) {
               result *= k2p;
            }

            k2p *= k2p;
         }

         return result;
      }
   }

   public static int pow(int k, long e) throws NotPositiveException {
      if (e < 0L) {
         throw new NotPositiveException(LocalizedFormats.EXPONENT, e);
      } else {
         int result = 1;

         for(int k2p = k; e != 0L; e >>= 1) {
            if ((e & 1L) != 0L) {
               result *= k2p;
            }

            k2p *= k2p;
         }

         return result;
      }
   }

   public static long pow(long k, int e) throws NotPositiveException {
      if (e < 0) {
         throw new NotPositiveException(LocalizedFormats.EXPONENT, e);
      } else {
         long result = 1L;

         for(long k2p = k; e != 0; e >>= 1) {
            if ((e & 1) != 0) {
               result *= k2p;
            }

            k2p *= k2p;
         }

         return result;
      }
   }

   public static long pow(long k, long e) throws NotPositiveException {
      if (e < 0L) {
         throw new NotPositiveException(LocalizedFormats.EXPONENT, e);
      } else {
         long result = 1L;

         for(long k2p = k; e != 0L; e >>= 1) {
            if ((e & 1L) != 0L) {
               result *= k2p;
            }

            k2p *= k2p;
         }

         return result;
      }
   }

   public static BigInteger pow(BigInteger k, int e) throws NotPositiveException {
      if (e < 0) {
         throw new NotPositiveException(LocalizedFormats.EXPONENT, e);
      } else {
         return k.pow(e);
      }
   }

   public static BigInteger pow(BigInteger k, long e) throws NotPositiveException {
      if (e < 0L) {
         throw new NotPositiveException(LocalizedFormats.EXPONENT, e);
      } else {
         BigInteger result = BigInteger.ONE;

         for(BigInteger k2p = k; e != 0L; e >>= 1) {
            if ((e & 1L) != 0L) {
               result = result.multiply(k2p);
            }

            k2p = k2p.multiply(k2p);
         }

         return result;
      }
   }

   public static BigInteger pow(BigInteger k, BigInteger e) throws NotPositiveException {
      if (e.compareTo(BigInteger.ZERO) < 0) {
         throw new NotPositiveException(LocalizedFormats.EXPONENT, e);
      } else {
         BigInteger result = BigInteger.ONE;

         for(BigInteger k2p = k; !BigInteger.ZERO.equals(e); e = e.shiftRight(1)) {
            if (e.testBit(0)) {
               result = result.multiply(k2p);
            }

            k2p = k2p.multiply(k2p);
         }

         return result;
      }
   }

   public static long stirlingS2(int n, int k) throws NotPositiveException, NumberIsTooLargeException, MathArithmeticException {
      if (k < 0) {
         throw new NotPositiveException(k);
      } else if (k > n) {
         throw new NumberIsTooLargeException(k, n, true);
      } else {
         long[][] stirlingS2 = (long[][])STIRLING_S2.get();
         if (stirlingS2 == null) {
            int maxIndex = true;
            stirlingS2 = new long[26][];
            stirlingS2[0] = new long[]{1L};

            for(int i = 1; i < stirlingS2.length; ++i) {
               stirlingS2[i] = new long[i + 1];
               stirlingS2[i][0] = 0L;
               stirlingS2[i][1] = 1L;
               stirlingS2[i][i] = 1L;

               for(int j = 2; j < i; ++j) {
                  stirlingS2[i][j] = (long)j * stirlingS2[i - 1][j] + stirlingS2[i - 1][j - 1];
               }
            }

            STIRLING_S2.compareAndSet((Object)null, stirlingS2);
         }

         if (n < stirlingS2.length) {
            return stirlingS2[n][k];
         } else if (k == 0) {
            return 0L;
         } else if (k != 1 && k != n) {
            if (k == 2) {
               return (1L << n - 1) - 1L;
            } else if (k == n - 1) {
               return binomialCoefficient(n, 2);
            } else {
               long sum = 0L;
               long sign = (k & 1) == 0 ? 1L : -1L;

               for(int j = 1; j <= k; ++j) {
                  sign = -sign;
                  sum += sign * binomialCoefficient(k, j) * (long)pow(j, n);
                  if (sum < 0L) {
                     throw new MathArithmeticException(LocalizedFormats.ARGUMENT_OUTSIDE_DOMAIN, new Object[]{n, 0, stirlingS2.length - 1});
                  }
               }

               return sum / factorial(k);
            }
         } else {
            return 1L;
         }
      }
   }

   private static long addAndCheck(long a, long b, Localizable pattern) throws MathArithmeticException {
      long ret;
      if (a > b) {
         ret = addAndCheck(b, a, pattern);
      } else if (a < 0L) {
         if (b < 0L) {
            if (Long.MIN_VALUE - b > a) {
               throw new MathArithmeticException(pattern, new Object[]{a, b});
            }

            ret = a + b;
         } else {
            ret = a + b;
         }
      } else {
         if (a > Long.MAX_VALUE - b) {
            throw new MathArithmeticException(pattern, new Object[]{a, b});
         }

         ret = a + b;
      }

      return ret;
   }

   private static void checkBinomial(int n, int k) throws NumberIsTooLargeException, NotPositiveException {
      if (n < k) {
         throw new NumberIsTooLargeException(LocalizedFormats.BINOMIAL_INVALID_PARAMETERS_ORDER, k, n, true);
      } else if (n < 0) {
         throw new NotPositiveException(LocalizedFormats.BINOMIAL_NEGATIVE_PARAMETER, n);
      }
   }

   public static boolean isPowerOfTwo(long n) {
      return n > 0L && (n & n - 1L) == 0L;
   }
}
