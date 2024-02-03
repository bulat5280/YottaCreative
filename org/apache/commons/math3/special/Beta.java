package org.apache.commons.math3.special;

import org.apache.commons.math3.exception.NumberIsTooSmallException;
import org.apache.commons.math3.exception.OutOfRangeException;
import org.apache.commons.math3.util.ContinuedFraction;
import org.apache.commons.math3.util.FastMath;

public class Beta {
   private static final double DEFAULT_EPSILON = 1.0E-14D;
   private static final double HALF_LOG_TWO_PI = 0.9189385332046727D;
   private static final double[] DELTA = new double[]{0.08333333333333333D, -2.777777777777778E-5D, 7.936507936507937E-8D, -5.952380952380953E-10D, 8.417508417508329E-12D, -1.917526917518546E-13D, 6.410256405103255E-15D, -2.955065141253382E-16D, 1.7964371635940225E-17D, -1.3922896466162779E-18D, 1.338028550140209E-19D, -1.542460098679661E-20D, 1.9770199298095743E-21D, -2.3406566479399704E-22D, 1.713480149663986E-23D};

   private Beta() {
   }

   public static double regularizedBeta(double x, double a, double b) {
      return regularizedBeta(x, a, b, 1.0E-14D, Integer.MAX_VALUE);
   }

   public static double regularizedBeta(double x, double a, double b, double epsilon) {
      return regularizedBeta(x, a, b, epsilon, Integer.MAX_VALUE);
   }

   public static double regularizedBeta(double x, double a, double b, int maxIterations) {
      return regularizedBeta(x, a, b, 1.0E-14D, maxIterations);
   }

   public static double regularizedBeta(double x, final double a, final double b, double epsilon, int maxIterations) {
      double ret;
      if (!Double.isNaN(x) && !Double.isNaN(a) && !Double.isNaN(b) && !(x < 0.0D) && !(x > 1.0D) && !(a <= 0.0D) && !(b <= 0.0D)) {
         if (x > (a + 1.0D) / (a + b + 2.0D)) {
            ret = 1.0D - regularizedBeta(1.0D - x, b, a, epsilon, maxIterations);
         } else {
            ContinuedFraction fraction = new ContinuedFraction() {
               protected double getB(int n, double x) {
                  double ret;
                  double m;
                  if (n % 2 == 0) {
                     m = (double)n / 2.0D;
                     ret = m * (b - m) * x / ((a + 2.0D * m - 1.0D) * (a + 2.0D * m));
                  } else {
                     m = ((double)n - 1.0D) / 2.0D;
                     ret = -((a + m) * (a + b + m) * x) / ((a + 2.0D * m) * (a + 2.0D * m + 1.0D));
                  }

                  return ret;
               }

               protected double getA(int n, double x) {
                  return 1.0D;
               }
            };
            ret = FastMath.exp(a * FastMath.log(x) + b * FastMath.log(1.0D - x) - FastMath.log(a) - logBeta(a, b)) * 1.0D / fraction.evaluate(x, epsilon, maxIterations);
         }
      } else {
         ret = Double.NaN;
      }

      return ret;
   }

   /** @deprecated */
   @Deprecated
   public static double logBeta(double a, double b, double epsilon, int maxIterations) {
      return logBeta(a, b);
   }

   private static double logGammaSum(double a, double b) throws OutOfRangeException {
      if (!(a < 1.0D) && !(a > 2.0D)) {
         if (!(b < 1.0D) && !(b > 2.0D)) {
            double x = a - 1.0D + (b - 1.0D);
            if (x <= 0.5D) {
               return Gamma.logGamma1p(1.0D + x);
            } else {
               return x <= 1.5D ? Gamma.logGamma1p(x) + FastMath.log1p(x) : Gamma.logGamma1p(x - 1.0D) + FastMath.log(x * (1.0D + x));
            }
         } else {
            throw new OutOfRangeException(b, 1.0D, 2.0D);
         }
      } else {
         throw new OutOfRangeException(a, 1.0D, 2.0D);
      }
   }

   private static double logGammaMinusLogGammaSum(double a, double b) throws NumberIsTooSmallException {
      if (a < 0.0D) {
         throw new NumberIsTooSmallException(a, 0.0D, true);
      } else if (b < 10.0D) {
         throw new NumberIsTooSmallException(b, 10.0D, true);
      } else {
         double d;
         double w;
         if (a <= b) {
            d = b + (a - 0.5D);
            w = deltaMinusDeltaSum(a, b);
         } else {
            d = a + (b - 0.5D);
            w = deltaMinusDeltaSum(b, a);
         }

         double u = d * FastMath.log1p(a / b);
         double v = a * (FastMath.log(b) - 1.0D);
         return u <= v ? w - u - v : w - v - u;
      }
   }

   private static double deltaMinusDeltaSum(double a, double b) throws OutOfRangeException, NumberIsTooSmallException {
      if (!(a < 0.0D) && !(a > b)) {
         if (b < 10.0D) {
            throw new NumberIsTooSmallException(b, 10, true);
         } else {
            double h = a / b;
            double p = h / (1.0D + h);
            double q = 1.0D / (1.0D + h);
            double q2 = q * q;
            double[] s = new double[DELTA.length];
            s[0] = 1.0D;

            for(int i = 1; i < s.length; ++i) {
               s[i] = 1.0D + q + q2 * s[i - 1];
            }

            double sqrtT = 10.0D / b;
            double t = sqrtT * sqrtT;
            double w = DELTA[DELTA.length - 1] * s[s.length - 1];

            for(int i = DELTA.length - 2; i >= 0; --i) {
               w = t * w + DELTA[i] * s[i];
            }

            return w * p / b;
         }
      } else {
         throw new OutOfRangeException(a, 0, b);
      }
   }

   private static double sumDeltaMinusDeltaSum(double p, double q) {
      if (p < 10.0D) {
         throw new NumberIsTooSmallException(p, 10.0D, true);
      } else if (q < 10.0D) {
         throw new NumberIsTooSmallException(q, 10.0D, true);
      } else {
         double a = FastMath.min(p, q);
         double b = FastMath.max(p, q);
         double sqrtT = 10.0D / a;
         double t = sqrtT * sqrtT;
         double z = DELTA[DELTA.length - 1];

         for(int i = DELTA.length - 2; i >= 0; --i) {
            z = t * z + DELTA[i];
         }

         return z / a + deltaMinusDeltaSum(a, b);
      }
   }

   public static double logBeta(double p, double q) {
      if (!Double.isNaN(p) && !Double.isNaN(q) && !(p <= 0.0D) && !(q <= 0.0D)) {
         double a = FastMath.min(p, q);
         double b = FastMath.max(p, q);
         double prod1;
         double ared;
         double prod2;
         double bred;
         if (a >= 10.0D) {
            prod1 = sumDeltaMinusDeltaSum(a, b);
            ared = a / b;
            prod2 = ared / (1.0D + ared);
            bred = -(a - 0.5D) * FastMath.log(prod2);
            double v = b * FastMath.log1p(ared);
            return bred <= v ? -0.5D * FastMath.log(b) + 0.9189385332046727D + prod1 - bred - v : -0.5D * FastMath.log(b) + 0.9189385332046727D + prod1 - v - bred;
         } else if (a > 2.0D) {
            if (b > 1000.0D) {
               int n = (int)FastMath.floor(a - 1.0D);
               double prod = 1.0D;
               double ared = a;

               for(int i = 0; i < n; ++i) {
                  --ared;
                  prod *= ared / (1.0D + ared / b);
               }

               return FastMath.log(prod) - (double)n * FastMath.log(b) + Gamma.logGamma(ared) + logGammaMinusLogGammaSum(ared, b);
            } else {
               prod1 = 1.0D;

               for(ared = a; ared > 2.0D; prod1 *= prod2 / (1.0D + prod2)) {
                  --ared;
                  prod2 = ared / b;
               }

               if (!(b < 10.0D)) {
                  return FastMath.log(prod1) + Gamma.logGamma(ared) + logGammaMinusLogGammaSum(ared, b);
               } else {
                  prod2 = 1.0D;

                  for(bred = b; bred > 2.0D; prod2 *= bred / (ared + bred)) {
                     --bred;
                  }

                  return FastMath.log(prod1) + FastMath.log(prod2) + Gamma.logGamma(ared) + (Gamma.logGamma(bred) - logGammaSum(ared, bred));
               }
            }
         } else if (!(a >= 1.0D)) {
            return b >= 10.0D ? Gamma.logGamma(a) + logGammaMinusLogGammaSum(a, b) : FastMath.log(Gamma.gamma(a) * Gamma.gamma(b) / Gamma.gamma(a + b));
         } else if (!(b > 2.0D)) {
            return Gamma.logGamma(a) + Gamma.logGamma(b) - logGammaSum(a, b);
         } else if (!(b < 10.0D)) {
            return Gamma.logGamma(a) + logGammaMinusLogGammaSum(a, b);
         } else {
            prod1 = 1.0D;

            for(ared = b; ared > 2.0D; prod1 *= ared / (a + ared)) {
               --ared;
            }

            return FastMath.log(prod1) + Gamma.logGamma(a) + (Gamma.logGamma(ared) - logGammaSum(a, ared));
         }
      } else {
         return Double.NaN;
      }
   }
}
