package org.apache.commons.math3.util;

import java.io.PrintStream;

public class FastMath {
   public static final double PI = 3.141592653589793D;
   public static final double E = 2.718281828459045D;
   static final int EXP_INT_TABLE_MAX_INDEX = 750;
   static final int EXP_INT_TABLE_LEN = 1500;
   static final int LN_MANT_LEN = 1024;
   static final int EXP_FRAC_TABLE_LEN = 1025;
   private static final double LOG_MAX_VALUE = StrictMath.log(Double.MAX_VALUE);
   private static final boolean RECOMPUTE_TABLES_AT_RUNTIME = false;
   private static final double LN_2_A = 0.6931470632553101D;
   private static final double LN_2_B = 1.1730463525082348E-7D;
   private static final double[][] LN_QUICK_COEF = new double[][]{{1.0D, 5.669184079525E-24D}, {-0.25D, -0.25D}, {0.3333333134651184D, 1.986821492305628E-8D}, {-0.25D, -6.663542893624021E-14D}, {0.19999998807907104D, 1.1921056801463227E-8D}, {-0.1666666567325592D, -7.800414592973399E-9D}, {0.1428571343421936D, 5.650007086920087E-9D}, {-0.12502530217170715D, -7.44321345601866E-11D}, {0.11113807559013367D, 9.219544613762692E-9D}};
   private static final double[][] LN_HI_PREC_COEF = new double[][]{{1.0D, -6.032174644509064E-23D}, {-0.25D, -0.25D}, {0.3333333134651184D, 1.9868161777724352E-8D}, {-0.2499999701976776D, -2.957007209750105E-8D}, {0.19999954104423523D, 1.5830993332061267E-10D}, {-0.16624879837036133D, -2.6033824355191673E-8D}};
   private static final int SINE_TABLE_LEN = 14;
   private static final double[] SINE_TABLE_A = new double[]{0.0D, 0.1246747374534607D, 0.24740394949913025D, 0.366272509098053D, 0.4794255495071411D, 0.5850973129272461D, 0.6816387176513672D, 0.7675435543060303D, 0.8414709568023682D, 0.902267575263977D, 0.9489846229553223D, 0.9808930158615112D, 0.9974949359893799D, 0.9985313415527344D};
   private static final double[] SINE_TABLE_B = new double[]{0.0D, -4.068233003401932E-9D, 9.755392680573412E-9D, 1.9987994582857286E-8D, -1.0902938113007961E-8D, -3.9986783938944604E-8D, 4.23719669792332E-8D, -5.207000323380292E-8D, 2.800552834259E-8D, 1.883511811213715E-8D, -3.5997360512765566E-9D, 4.116164446561962E-8D, 5.0614674548127384E-8D, -1.0129027912496858E-9D};
   private static final double[] COSINE_TABLE_A = new double[]{1.0D, 0.9921976327896118D, 0.9689123630523682D, 0.9305076599121094D, 0.8775825500488281D, 0.8109631538391113D, 0.7316888570785522D, 0.6409968137741089D, 0.5403022766113281D, 0.4311765432357788D, 0.3153223395347595D, 0.19454771280288696D, 0.07073719799518585D, -0.05417713522911072D};
   private static final double[] COSINE_TABLE_B = new double[]{0.0D, 3.4439717236742845E-8D, 5.865827662008209E-8D, -3.7999795083850525E-8D, 1.184154459111628E-8D, -3.43338934259355E-8D, 1.1795268640216787E-8D, 4.438921624363781E-8D, 2.925681159240093E-8D, -2.6437112632041807E-8D, 2.2860509143963117E-8D, -4.813899778443457E-9D, 3.6725170580355583E-9D, 2.0217439756338078E-10D};
   private static final double[] TANGENT_TABLE_A = new double[]{0.0D, 0.1256551444530487D, 0.25534194707870483D, 0.3936265707015991D, 0.5463024377822876D, 0.7214844226837158D, 0.9315965175628662D, 1.1974215507507324D, 1.5574076175689697D, 2.092571258544922D, 3.0095696449279785D, 5.041914939880371D, 14.101419448852539D, -18.430862426757812D};
   private static final double[] TANGENT_TABLE_B = new double[]{0.0D, -7.877917738262007E-9D, -2.5857668567479893E-8D, 5.2240336371356666E-9D, 5.206150291559893E-8D, 1.8307188599677033E-8D, -5.7618793749770706E-8D, 7.848361555046424E-8D, 1.0708593250394448E-7D, 1.7827257129423813E-8D, 2.893485277253286E-8D, 3.1660099222737955E-7D, 4.983191803254889E-7D, -3.356118100840571E-7D};
   private static final long[] RECIP_2PI = new long[]{2935890503282001226L, 9154082963658192752L, 3952090531849364496L, 9193070505571053912L, 7910884519577875640L, 113236205062349959L, 4577762542105553359L, -5034868814120038111L, 4208363204685324176L, 5648769086999809661L, 2819561105158720014L, -4035746434778044925L, -302932621132653753L, -2644281811660520851L, -3183605296591799669L, 6722166367014452318L, -3512299194304650054L, -7278142539171889152L};
   private static final long[] PI_O_4_BITS = new long[]{-3958705157555305932L, -4267615245585081135L};
   private static final double[] EIGHTHS = new double[]{0.0D, 0.125D, 0.25D, 0.375D, 0.5D, 0.625D, 0.75D, 0.875D, 1.0D, 1.125D, 1.25D, 1.375D, 1.5D, 1.625D};
   private static final double[] CBRTTWO = new double[]{0.6299605249474366D, 0.7937005259840998D, 1.0D, 1.2599210498948732D, 1.5874010519681994D};
   private static final long HEX_40000000 = 1073741824L;
   private static final long MASK_30BITS = -1073741824L;
   private static final int MASK_NON_SIGN_INT = Integer.MAX_VALUE;
   private static final long MASK_NON_SIGN_LONG = Long.MAX_VALUE;
   private static final double TWO_POWER_52 = 4.503599627370496E15D;
   private static final double TWO_POWER_53 = 9.007199254740992E15D;
   private static final double F_1_3 = 0.3333333333333333D;
   private static final double F_1_5 = 0.2D;
   private static final double F_1_7 = 0.14285714285714285D;
   private static final double F_1_9 = 0.1111111111111111D;
   private static final double F_1_11 = 0.09090909090909091D;
   private static final double F_1_13 = 0.07692307692307693D;
   private static final double F_1_15 = 0.06666666666666667D;
   private static final double F_1_17 = 0.058823529411764705D;
   private static final double F_3_4 = 0.75D;
   private static final double F_15_16 = 0.9375D;
   private static final double F_13_14 = 0.9285714285714286D;
   private static final double F_11_12 = 0.9166666666666666D;
   private static final double F_9_10 = 0.9D;
   private static final double F_7_8 = 0.875D;
   private static final double F_5_6 = 0.8333333333333334D;
   private static final double F_1_2 = 0.5D;
   private static final double F_1_4 = 0.25D;

   private FastMath() {
   }

   private static double doubleHighPart(double d) {
      if (d > -Precision.SAFE_MIN && d < Precision.SAFE_MIN) {
         return d;
      } else {
         long xl = Double.doubleToRawLongBits(d);
         xl &= -1073741824L;
         return Double.longBitsToDouble(xl);
      }
   }

   public static double sqrt(double a) {
      return Math.sqrt(a);
   }

   public static double cosh(double x) {
      if (x != x) {
         return x;
      } else {
         double t;
         if (x > 20.0D) {
            if (x >= LOG_MAX_VALUE) {
               t = exp(0.5D * x);
               return 0.5D * t * t;
            } else {
               return 0.5D * exp(x);
            }
         } else if (x < -20.0D) {
            if (x <= -LOG_MAX_VALUE) {
               t = exp(-0.5D * x);
               return 0.5D * t * t;
            } else {
               return 0.5D * exp(-x);
            }
         } else {
            double[] hiPrec = new double[2];
            if (x < 0.0D) {
               x = -x;
            }

            exp(x, 0.0D, hiPrec);
            double ya = hiPrec[0] + hiPrec[1];
            double yb = -(ya - hiPrec[0] - hiPrec[1]);
            double temp = ya * 1.073741824E9D;
            double yaa = ya + temp - temp;
            double yab = ya - yaa;
            double recip = 1.0D / ya;
            temp = recip * 1.073741824E9D;
            double recipa = recip + temp - temp;
            double recipb = recip - recipa;
            recipb += (1.0D - yaa * recipa - yaa * recipb - yab * recipa - yab * recipb) * recip;
            recipb += -yb * recip * recip;
            temp = ya + recipa;
            yb += -(temp - ya - recipa);
            ya = temp;
            temp += recipb;
            yb += -(temp - ya - recipb);
            double result = temp + yb;
            result *= 0.5D;
            return result;
         }
      }
   }

   public static double sinh(double x) {
      boolean negate = false;
      if (x != x) {
         return x;
      } else {
         double result;
         if (x > 20.0D) {
            if (x >= LOG_MAX_VALUE) {
               result = exp(0.5D * x);
               return 0.5D * result * result;
            } else {
               return 0.5D * exp(x);
            }
         } else if (x < -20.0D) {
            if (x <= -LOG_MAX_VALUE) {
               result = exp(-0.5D * x);
               return -0.5D * result * result;
            } else {
               return -0.5D * exp(-x);
            }
         } else if (x == 0.0D) {
            return x;
         } else {
            if (x < 0.0D) {
               x = -x;
               negate = true;
            }

            double[] hiPrec;
            double ya;
            double yb;
            double temp;
            double yaa;
            double yab;
            double recip;
            double recipa;
            double recipb;
            if (x > 0.25D) {
               hiPrec = new double[2];
               exp(x, 0.0D, hiPrec);
               ya = hiPrec[0] + hiPrec[1];
               yb = -(ya - hiPrec[0] - hiPrec[1]);
               temp = ya * 1.073741824E9D;
               yaa = ya + temp - temp;
               yab = ya - yaa;
               recip = 1.0D / ya;
               temp = recip * 1.073741824E9D;
               recipa = recip + temp - temp;
               recipb = recip - recipa;
               recipb += (1.0D - yaa * recipa - yaa * recipb - yab * recipa - yab * recipb) * recip;
               recipb += -yb * recip * recip;
               recipa = -recipa;
               recipb = -recipb;
               temp = ya + recipa;
               yb += -(temp - ya - recipa);
               ya = temp;
               temp += recipb;
               yb += -(temp - ya - recipb);
               result = temp + yb;
               result *= 0.5D;
            } else {
               hiPrec = new double[2];
               expm1(x, hiPrec);
               ya = hiPrec[0] + hiPrec[1];
               yb = -(ya - hiPrec[0] - hiPrec[1]);
               temp = 1.0D + ya;
               yaa = 1.0D / temp;
               yab = -(temp - 1.0D - ya) + yb;
               recip = ya * yaa;
               recipa = recip * 1.073741824E9D;
               recipb = recip + recipa - recipa;
               double rb = recip - recipb;
               recipa = temp * 1.073741824E9D;
               double za = temp + recipa - recipa;
               double zb = temp - za;
               rb += (ya - za * recipb - za * rb - zb * recipb - zb * rb) * yaa;
               rb += yb * yaa;
               rb += -ya * yab * yaa * yaa;
               recipa = ya + recipb;
               yb += -(recipa - ya - recipb);
               ya = recipa;
               recipa += rb;
               yb += -(recipa - ya - rb);
               result = recipa + yb;
               result *= 0.5D;
            }

            if (negate) {
               result = -result;
            }

            return result;
         }
      }
   }

   public static double tanh(double x) {
      boolean negate = false;
      if (x != x) {
         return x;
      } else if (x > 20.0D) {
         return 1.0D;
      } else if (x < -20.0D) {
         return -1.0D;
      } else if (x == 0.0D) {
         return x;
      } else {
         if (x < 0.0D) {
            x = -x;
            negate = true;
         }

         double result;
         double[] hiPrec;
         double ya;
         double yb;
         double temp;
         double da;
         double db;
         double daa;
         double dab;
         double ratio;
         double ratioa;
         double ratiob;
         if (x >= 0.5D) {
            hiPrec = new double[2];
            exp(x * 2.0D, 0.0D, hiPrec);
            ya = hiPrec[0] + hiPrec[1];
            yb = -(ya - hiPrec[0] - hiPrec[1]);
            double na = -1.0D + ya;
            double nb = -(na + 1.0D - ya);
            temp = na + yb;
            nb += -(temp - na - yb);
            na = temp;
            da = 1.0D + ya;
            db = -(da - 1.0D - ya);
            temp = da + yb;
            db += -(temp - da - yb);
            da = temp;
            temp *= 1.073741824E9D;
            daa = da + temp - temp;
            dab = da - daa;
            ratio = na / da;
            temp = ratio * 1.073741824E9D;
            ratioa = ratio + temp - temp;
            ratiob = ratio - ratioa;
            ratiob += (na - daa * ratioa - daa * ratiob - dab * ratioa - dab * ratiob) / da;
            ratiob += nb / da;
            ratiob += -db * na / da / da;
            result = ratioa + ratiob;
         } else {
            hiPrec = new double[2];
            expm1(x * 2.0D, hiPrec);
            ya = hiPrec[0] + hiPrec[1];
            yb = -(ya - hiPrec[0] - hiPrec[1]);
            temp = 2.0D + ya;
            da = -(temp - 2.0D - ya);
            db = temp + yb;
            da += -(db - temp - yb);
            temp = db;
            db *= 1.073741824E9D;
            daa = temp + db - db;
            dab = temp - daa;
            ratio = ya / temp;
            db = ratio * 1.073741824E9D;
            ratioa = ratio + db - db;
            ratiob = ratio - ratioa;
            ratiob += (ya - daa * ratioa - daa * ratiob - dab * ratioa - dab * ratiob) / temp;
            ratiob += yb / temp;
            ratiob += -da * ya / temp / temp;
            result = ratioa + ratiob;
         }

         if (negate) {
            result = -result;
         }

         return result;
      }
   }

   public static double acosh(double a) {
      return log(a + sqrt(a * a - 1.0D));
   }

   public static double asinh(double a) {
      boolean negative = false;
      if (a < 0.0D) {
         negative = true;
         a = -a;
      }

      double absAsinh;
      if (a > 0.167D) {
         absAsinh = log(sqrt(a * a + 1.0D) + a);
      } else {
         double a2 = a * a;
         if (a > 0.097D) {
            absAsinh = a * (1.0D - a2 * (0.3333333333333333D - a2 * (0.2D - a2 * (0.14285714285714285D - a2 * (0.1111111111111111D - a2 * (0.09090909090909091D - a2 * (0.07692307692307693D - a2 * (0.06666666666666667D - a2 * 0.058823529411764705D * 0.9375D) * 0.9285714285714286D) * 0.9166666666666666D) * 0.9D) * 0.875D) * 0.8333333333333334D) * 0.75D) * 0.5D);
         } else if (a > 0.036D) {
            absAsinh = a * (1.0D - a2 * (0.3333333333333333D - a2 * (0.2D - a2 * (0.14285714285714285D - a2 * (0.1111111111111111D - a2 * (0.09090909090909091D - a2 * 0.07692307692307693D * 0.9166666666666666D) * 0.9D) * 0.875D) * 0.8333333333333334D) * 0.75D) * 0.5D);
         } else if (a > 0.0036D) {
            absAsinh = a * (1.0D - a2 * (0.3333333333333333D - a2 * (0.2D - a2 * (0.14285714285714285D - a2 * 0.1111111111111111D * 0.875D) * 0.8333333333333334D) * 0.75D) * 0.5D);
         } else {
            absAsinh = a * (1.0D - a2 * (0.3333333333333333D - a2 * 0.2D * 0.75D) * 0.5D);
         }
      }

      return negative ? -absAsinh : absAsinh;
   }

   public static double atanh(double a) {
      boolean negative = false;
      if (a < 0.0D) {
         negative = true;
         a = -a;
      }

      double absAtanh;
      if (a > 0.15D) {
         absAtanh = 0.5D * log((1.0D + a) / (1.0D - a));
      } else {
         double a2 = a * a;
         if (a > 0.087D) {
            absAtanh = a * (1.0D + a2 * (0.3333333333333333D + a2 * (0.2D + a2 * (0.14285714285714285D + a2 * (0.1111111111111111D + a2 * (0.09090909090909091D + a2 * (0.07692307692307693D + a2 * (0.06666666666666667D + a2 * 0.058823529411764705D))))))));
         } else if (a > 0.031D) {
            absAtanh = a * (1.0D + a2 * (0.3333333333333333D + a2 * (0.2D + a2 * (0.14285714285714285D + a2 * (0.1111111111111111D + a2 * (0.09090909090909091D + a2 * 0.07692307692307693D))))));
         } else if (a > 0.003D) {
            absAtanh = a * (1.0D + a2 * (0.3333333333333333D + a2 * (0.2D + a2 * (0.14285714285714285D + a2 * 0.1111111111111111D))));
         } else {
            absAtanh = a * (1.0D + a2 * (0.3333333333333333D + a2 * 0.2D));
         }
      }

      return negative ? -absAtanh : absAtanh;
   }

   public static double signum(double a) {
      return a < 0.0D ? -1.0D : (a > 0.0D ? 1.0D : a);
   }

   public static float signum(float a) {
      return a < 0.0F ? -1.0F : (a > 0.0F ? 1.0F : a);
   }

   public static double nextUp(double a) {
      return nextAfter(a, Double.POSITIVE_INFINITY);
   }

   public static float nextUp(float a) {
      return nextAfter(a, Double.POSITIVE_INFINITY);
   }

   public static double random() {
      return Math.random();
   }

   public static double exp(double x) {
      return exp(x, 0.0D, (double[])null);
   }

   private static double exp(double x, double extra, double[] hiPrec) {
      double intPartA;
      double intPartB;
      int intVal;
      if (x < 0.0D) {
         intVal = (int)(-x);
         if (intVal > 746) {
            if (hiPrec != null) {
               hiPrec[0] = 0.0D;
               hiPrec[1] = 0.0D;
            }

            return 0.0D;
         }

         double result;
         if (intVal > 709) {
            result = exp(x + 40.19140625D, extra, hiPrec) / 2.85040095144011776E17D;
            if (hiPrec != null) {
               hiPrec[0] /= 2.85040095144011776E17D;
               hiPrec[1] /= 2.85040095144011776E17D;
            }

            return result;
         }

         if (intVal == 709) {
            result = exp(x + 1.494140625D, extra, hiPrec) / 4.455505956692757D;
            if (hiPrec != null) {
               hiPrec[0] /= 4.455505956692757D;
               hiPrec[1] /= 4.455505956692757D;
            }

            return result;
         }

         ++intVal;
         intPartA = FastMath.ExpIntTable.EXP_INT_TABLE_A[750 - intVal];
         intPartB = FastMath.ExpIntTable.EXP_INT_TABLE_B[750 - intVal];
         intVal = -intVal;
      } else {
         intVal = (int)x;
         if (intVal > 709) {
            if (hiPrec != null) {
               hiPrec[0] = Double.POSITIVE_INFINITY;
               hiPrec[1] = 0.0D;
            }

            return Double.POSITIVE_INFINITY;
         }

         intPartA = FastMath.ExpIntTable.EXP_INT_TABLE_A[750 + intVal];
         intPartB = FastMath.ExpIntTable.EXP_INT_TABLE_B[750 + intVal];
      }

      int intFrac = (int)((x - (double)intVal) * 1024.0D);
      double fracPartA = FastMath.ExpFracTable.EXP_FRAC_TABLE_A[intFrac];
      double fracPartB = FastMath.ExpFracTable.EXP_FRAC_TABLE_B[intFrac];
      double epsilon = x - ((double)intVal + (double)intFrac / 1024.0D);
      double z = 0.04168701738764507D;
      z = z * epsilon + 0.1666666505023083D;
      z = z * epsilon + 0.5000000000042687D;
      z = z * epsilon + 1.0D;
      z = z * epsilon + -3.940510424527919E-20D;
      double tempA = intPartA * fracPartA;
      double tempB = intPartA * fracPartB + intPartB * fracPartA + intPartB * fracPartB;
      double tempC = tempB + tempA;
      double result;
      if (extra != 0.0D) {
         result = tempC * extra * z + tempC * extra + tempC * z + tempB + tempA;
      } else {
         result = tempC * z + tempB + tempA;
      }

      if (hiPrec != null) {
         hiPrec[0] = tempA;
         hiPrec[1] = tempC * extra * z + tempC * extra + tempC * z + tempB;
      }

      return result;
   }

   public static double expm1(double x) {
      return expm1(x, (double[])null);
   }

   private static double expm1(double x, double[] hiPrecOut) {
      if (x == x && x != 0.0D) {
         if (!(x <= -1.0D) && !(x >= 1.0D)) {
            boolean negative = false;
            if (x < 0.0D) {
               x = -x;
               negative = true;
            }

            int intFrac = (int)(x * 1024.0D);
            double tempA = FastMath.ExpFracTable.EXP_FRAC_TABLE_A[intFrac] - 1.0D;
            double tempB = FastMath.ExpFracTable.EXP_FRAC_TABLE_B[intFrac];
            double temp = tempA + tempB;
            tempB = -(temp - tempA - tempB);
            tempA = temp;
            temp *= 1.073741824E9D;
            double baseA = tempA + temp - temp;
            double baseB = tempB + (tempA - baseA);
            double epsilon = x - (double)intFrac / 1024.0D;
            double zb = 0.008336750013465571D;
            zb = zb * epsilon + 0.041666663879186654D;
            zb = zb * epsilon + 0.16666666666745392D;
            zb = zb * epsilon + 0.49999999999999994D;
            zb *= epsilon;
            zb *= epsilon;
            double temp = epsilon + zb;
            zb = -(temp - epsilon - zb);
            double za = temp;
            temp *= 1.073741824E9D;
            temp = za + temp - temp;
            zb += za - temp;
            za = temp;
            double ya = temp * baseA;
            temp = ya + temp * baseB;
            double yb = -(temp - ya - za * baseB);
            ya = temp;
            temp += zb * baseA;
            yb += -(temp - ya - zb * baseA);
            ya = temp;
            temp += zb * baseB;
            yb += -(temp - ya - zb * baseB);
            ya = temp;
            temp += baseA;
            yb += -(temp - baseA - ya);
            ya = temp;
            temp += za;
            yb += -(temp - ya - za);
            ya = temp;
            temp += baseB;
            yb += -(temp - ya - baseB);
            ya = temp;
            temp += zb;
            yb += -(temp - ya - zb);
            ya = temp;
            if (negative) {
               double denom = 1.0D + temp;
               double denomr = 1.0D / denom;
               double denomb = -(denom - 1.0D - temp) + yb;
               double ratio = temp * denomr;
               temp = ratio * 1.073741824E9D;
               double ra = ratio + temp - temp;
               double rb = ratio - ra;
               temp = denom * 1.073741824E9D;
               za = denom + temp - temp;
               zb = denom - za;
               rb += (ya - za * ra - za * rb - zb * ra - zb * rb) * denomr;
               rb += yb * denomr;
               rb += -ya * denomb * denomr * denomr;
               ya = -ra;
               yb = -rb;
            }

            if (hiPrecOut != null) {
               hiPrecOut[0] = ya;
               hiPrecOut[1] = yb;
            }

            return ya + yb;
         } else {
            double[] hiPrec = new double[2];
            exp(x, 0.0D, hiPrec);
            if (x > 0.0D) {
               return -1.0D + hiPrec[0] + hiPrec[1];
            } else {
               double ra = -1.0D + hiPrec[0];
               double rb = -(ra + 1.0D - hiPrec[0]);
               rb += hiPrec[1];
               return ra + rb;
            }
         }
      } else {
         return x;
      }
   }

   public static double log(double x) {
      return log(x, (double[])null);
   }

   private static double log(double x, double[] hiPrec) {
      if (x == 0.0D) {
         return Double.NEGATIVE_INFINITY;
      } else {
         long bits = Double.doubleToRawLongBits(x);
         if (((bits & Long.MIN_VALUE) != 0L || x != x) && x != 0.0D) {
            if (hiPrec != null) {
               hiPrec[0] = Double.NaN;
            }

            return Double.NaN;
         } else if (x == Double.POSITIVE_INFINITY) {
            if (hiPrec != null) {
               hiPrec[0] = Double.POSITIVE_INFINITY;
            }

            return Double.POSITIVE_INFINITY;
         } else {
            int exp = (int)(bits >> 52) - 1023;
            if ((bits & 9218868437227405312L) == 0L) {
               if (x == 0.0D) {
                  if (hiPrec != null) {
                     hiPrec[0] = Double.NEGATIVE_INFINITY;
                  }

                  return Double.NEGATIVE_INFINITY;
               }

               for(bits <<= 1; (bits & 4503599627370496L) == 0L; bits <<= 1) {
                  --exp;
               }
            }

            double ab;
            double xa;
            if ((exp == -1 || exp == 0) && x < 1.01D && x > 0.99D && hiPrec == null) {
               double xa = x - 1.0D;
               double xb = xa - x + 1.0D;
               double tmp = xa * 1.073741824E9D;
               double aa = xa + tmp - tmp;
               double ab = xa - aa;
               xa = aa;
               xb = ab;
               double[] lnCoef_last = LN_QUICK_COEF[LN_QUICK_COEF.length - 1];
               ab = lnCoef_last[0];
               xa = lnCoef_last[1];

               for(int i = LN_QUICK_COEF.length - 2; i >= 0; --i) {
                  aa = ab * xa;
                  ab = ab * xb + xa * xa + xa * xb;
                  tmp = aa * 1.073741824E9D;
                  ab = aa + tmp - tmp;
                  xa = aa - ab + ab;
                  double[] lnCoef_i = LN_QUICK_COEF[i];
                  aa = ab + lnCoef_i[0];
                  ab = xa + lnCoef_i[1];
                  tmp = aa * 1.073741824E9D;
                  ab = aa + tmp - tmp;
                  xa = aa - ab + ab;
               }

               aa = ab * xa;
               ab = ab * xb + xa * xa + xa * xb;
               tmp = aa * 1.073741824E9D;
               ab = aa + tmp - tmp;
               xa = aa - ab + ab;
               return ab + xa;
            } else {
               double[] lnm = FastMath.lnMant.LN_MANT[(int)((bits & 4499201580859392L) >> 42)];
               double epsilon = (double)(bits & 4398046511103L) / (4.503599627370496E15D + (double)(bits & 4499201580859392L));
               double lnza = 0.0D;
               double lnzb = 0.0D;
               double tmp;
               double aa;
               if (hiPrec != null) {
                  tmp = epsilon * 1.073741824E9D;
                  aa = epsilon + tmp - tmp;
                  ab = epsilon - aa;
                  xa = aa;
                  double numer = (double)(bits & 4398046511103L);
                  double denom = 4.503599627370496E15D + (double)(bits & 4499201580859392L);
                  aa = numer - aa * denom - ab * denom;
                  double xb = ab + aa / denom;
                  double[] lnCoef_last = LN_HI_PREC_COEF[LN_HI_PREC_COEF.length - 1];
                  double ya = lnCoef_last[0];
                  double yb = lnCoef_last[1];

                  for(int i = LN_HI_PREC_COEF.length - 2; i >= 0; --i) {
                     aa = ya * xa;
                     ab = ya * xb + yb * xa + yb * xb;
                     tmp = aa * 1.073741824E9D;
                     ya = aa + tmp - tmp;
                     yb = aa - ya + ab;
                     double[] lnCoef_i = LN_HI_PREC_COEF[i];
                     aa = ya + lnCoef_i[0];
                     ab = yb + lnCoef_i[1];
                     tmp = aa * 1.073741824E9D;
                     ya = aa + tmp - tmp;
                     yb = aa - ya + ab;
                  }

                  aa = ya * xa;
                  ab = ya * xb + yb * xa + yb * xb;
                  lnza = aa + ab;
                  lnzb = -(lnza - aa - ab);
               } else {
                  lnza = -0.16624882440418567D;
                  lnza = lnza * epsilon + 0.19999954120254515D;
                  lnza = lnza * epsilon + -0.2499999997677497D;
                  lnza = lnza * epsilon + 0.3333333333332802D;
                  lnza = lnza * epsilon + -0.5D;
                  lnza = lnza * epsilon + 1.0D;
                  lnza *= epsilon;
               }

               tmp = 0.6931470632553101D * (double)exp;
               aa = 0.0D;
               ab = tmp + lnm[0];
               xa = -(ab - tmp - lnm[0]);
               tmp = ab;
               aa += xa;
               ab += lnza;
               xa = -(ab - tmp - lnza);
               tmp = ab;
               aa += xa;
               ab += 1.1730463525082348E-7D * (double)exp;
               xa = -(ab - tmp - 1.1730463525082348E-7D * (double)exp);
               tmp = ab;
               aa += xa;
               ab += lnm[1];
               xa = -(ab - tmp - lnm[1]);
               tmp = ab;
               aa += xa;
               ab += lnzb;
               xa = -(ab - tmp - lnzb);
               aa += xa;
               if (hiPrec != null) {
                  hiPrec[0] = ab;
                  hiPrec[1] = aa;
               }

               return ab + aa;
            }
         }
      }
   }

   public static double log1p(double x) {
      if (x == -1.0D) {
         return Double.NEGATIVE_INFINITY;
      } else if (x == Double.POSITIVE_INFINITY) {
         return Double.POSITIVE_INFINITY;
      } else {
         double xpa;
         if (!(x > 1.0E-6D) && !(x < -1.0E-6D)) {
            xpa = (x * 0.3333333333333333D - 0.5D) * x + 1.0D;
            return xpa * x;
         } else {
            xpa = 1.0D + x;
            double xpb = -(xpa - 1.0D - x);
            double[] hiPrec = new double[2];
            double lores = log(xpa, hiPrec);
            if (Double.isInfinite(lores)) {
               return lores;
            } else {
               double fx1 = xpb / xpa;
               double epsilon = 0.5D * fx1 + 1.0D;
               return epsilon * fx1 + hiPrec[1] + hiPrec[0];
            }
         }
      }
   }

   public static double log10(double x) {
      double[] hiPrec = new double[2];
      double lores = log(x, hiPrec);
      if (Double.isInfinite(lores)) {
         return lores;
      } else {
         double tmp = hiPrec[0] * 1.073741824E9D;
         double lna = hiPrec[0] + tmp - tmp;
         double lnb = hiPrec[0] - lna + hiPrec[1];
         double rln10a = 0.4342944622039795D;
         double rln10b = 1.9699272335463627E-8D;
         return 1.9699272335463627E-8D * lnb + 1.9699272335463627E-8D * lna + 0.4342944622039795D * lnb + 0.4342944622039795D * lna;
      }
   }

   public static double log(double base, double x) {
      return log(x) / log(base);
   }

   public static double pow(double x, double y) {
      double[] lns = new double[2];
      if (y == 0.0D) {
         return 1.0D;
      } else if (x != x) {
         return x;
      } else {
         long yi;
         if (x == 0.0D) {
            yi = Double.doubleToRawLongBits(x);
            if ((yi & Long.MIN_VALUE) != 0L) {
               long yi = (long)y;
               if (y < 0.0D && y == (double)yi && (yi & 1L) == 1L) {
                  return Double.NEGATIVE_INFINITY;
               }

               if (y > 0.0D && y == (double)yi && (yi & 1L) == 1L) {
                  return -0.0D;
               }
            }

            if (y < 0.0D) {
               return Double.POSITIVE_INFINITY;
            } else {
               return y > 0.0D ? 0.0D : Double.NaN;
            }
         } else if (x == Double.POSITIVE_INFINITY) {
            if (y != y) {
               return y;
            } else {
               return y < 0.0D ? 0.0D : Double.POSITIVE_INFINITY;
            }
         } else if (y == Double.POSITIVE_INFINITY) {
            if (x * x == 1.0D) {
               return Double.NaN;
            } else {
               return x * x > 1.0D ? Double.POSITIVE_INFINITY : 0.0D;
            }
         } else {
            if (x == Double.NEGATIVE_INFINITY) {
               if (y != y) {
                  return y;
               }

               if (y < 0.0D) {
                  yi = (long)y;
                  if (y == (double)yi && (yi & 1L) == 1L) {
                     return -0.0D;
                  }

                  return 0.0D;
               }

               if (y > 0.0D) {
                  yi = (long)y;
                  if (y == (double)yi && (yi & 1L) == 1L) {
                     return Double.NEGATIVE_INFINITY;
                  }

                  return Double.POSITIVE_INFINITY;
               }
            }

            if (y == Double.NEGATIVE_INFINITY) {
               if (x * x == 1.0D) {
                  return Double.NaN;
               } else {
                  return x * x < 1.0D ? Double.POSITIVE_INFINITY : 0.0D;
               }
            } else if (x < 0.0D) {
               if (!(y >= 9.007199254740992E15D) && !(y <= -9.007199254740992E15D)) {
                  if (y == (double)((long)y)) {
                     return ((long)y & 1L) == 0L ? pow(-x, y) : -pow(-x, y);
                  } else {
                     return Double.NaN;
                  }
               } else {
                  return pow(-x, y);
               }
            } else {
               double ya;
               double yb;
               double lores;
               double lna;
               if (y < 8.0E298D && y > -8.0E298D) {
                  lores = y * 1.073741824E9D;
                  ya = y + lores - lores;
                  yb = y - ya;
               } else {
                  lores = y * 9.313225746154785E-10D;
                  lna = lores * 9.313225746154785E-10D;
                  ya = (lores + lna - lores) * 1.073741824E9D * 1.073741824E9D;
                  yb = y - ya;
               }

               lores = log(x, lns);
               if (Double.isInfinite(lores)) {
                  return lores;
               } else {
                  lna = lns[0];
                  double lnb = lns[1];
                  double tmp1 = lna * 1.073741824E9D;
                  double tmp2 = lna + tmp1 - tmp1;
                  lnb += lna - tmp2;
                  double aa = tmp2 * ya;
                  double ab = tmp2 * yb + lnb * ya + lnb * yb;
                  lna = aa + ab;
                  lnb = -(lna - aa - ab);
                  double z = 0.008333333333333333D;
                  z = z * lnb + 0.041666666666666664D;
                  z = z * lnb + 0.16666666666666666D;
                  z = z * lnb + 0.5D;
                  z = z * lnb + 1.0D;
                  z *= lnb;
                  double result = exp(lna, z, (double[])null);
                  return result;
               }
            }
         }
      }
   }

   public static double pow(double d, int e) {
      if (e == 0) {
         return 1.0D;
      } else {
         if (e < 0) {
            e = -e;
            d = 1.0D / d;
         }

         int splitFactor = 134217729;
         double cd = 1.34217729E8D * d;
         double d1High = cd - (cd - d);
         double d1Low = d - d1High;
         double resultHigh = 1.0D;
         double resultLow = 0.0D;
         double d2p = d;
         double d2pHigh = d1High;

         for(double d2pLow = d1Low; e != 0; e >>= 1) {
            double tmpHigh;
            double cD2pH;
            double d2pHH;
            double d2pHL;
            double tmpLow;
            if ((e & 1) != 0) {
               tmpHigh = resultHigh * d2p;
               cD2pH = 1.34217729E8D * resultHigh;
               d2pHH = cD2pH - (cD2pH - resultHigh);
               d2pHL = resultHigh - d2pHH;
               tmpLow = d2pHL * d2pLow - (tmpHigh - d2pHH * d2pHigh - d2pHL * d2pHigh - d2pHH * d2pLow);
               resultHigh = tmpHigh;
               resultLow = resultLow * d2p + tmpLow;
            }

            tmpHigh = d2pHigh * d2p;
            cD2pH = 1.34217729E8D * d2pHigh;
            d2pHH = cD2pH - (cD2pH - d2pHigh);
            d2pHL = d2pHigh - d2pHH;
            tmpLow = d2pHL * d2pLow - (tmpHigh - d2pHH * d2pHigh - d2pHL * d2pHigh - d2pHH * d2pLow);
            double cTmpH = 1.34217729E8D * tmpHigh;
            d2pHigh = cTmpH - (cTmpH - tmpHigh);
            d2pLow = d2pLow * d2p + tmpLow + (tmpHigh - d2pHigh);
            d2p = d2pHigh + d2pLow;
         }

         return resultHigh + resultLow;
      }
   }

   private static double polySine(double x) {
      double x2 = x * x;
      double p = 2.7553817452272217E-6D;
      p = p * x2 + -1.9841269659586505E-4D;
      p = p * x2 + 0.008333333333329196D;
      p = p * x2 + -0.16666666666666666D;
      p = p * x2 * x;
      return p;
   }

   private static double polyCosine(double x) {
      double x2 = x * x;
      double p = 2.479773539153719E-5D;
      p = p * x2 + -0.0013888888689039883D;
      p = p * x2 + 0.041666666666621166D;
      p = p * x2 + -0.49999999999999994D;
      p *= x2;
      return p;
   }

   private static double sinQ(double xa, double xb) {
      int idx = (int)(xa * 8.0D + 0.5D);
      double epsilon = xa - EIGHTHS[idx];
      double sintA = SINE_TABLE_A[idx];
      double sintB = SINE_TABLE_B[idx];
      double costA = COSINE_TABLE_A[idx];
      double costB = COSINE_TABLE_B[idx];
      double sinEpsB = polySine(epsilon);
      double cosEpsA = 1.0D;
      double cosEpsB = polyCosine(epsilon);
      double temp = epsilon * 1.073741824E9D;
      double temp2 = epsilon + temp - temp;
      sinEpsB += epsilon - temp2;
      double a = 0.0D;
      double b = 0.0D;
      double c = a + sintA;
      double d = -(c - a - sintA);
      a = c;
      b += d;
      double t = costA * temp2;
      c += t;
      d = -(c - a - t);
      a = c;
      b += d;
      b = b + sintA * cosEpsB + costA * sinEpsB;
      b = b + sintB + costB * temp2 + sintB * cosEpsB + costB * sinEpsB;
      if (xb != 0.0D) {
         t = ((costA + costB) * (1.0D + cosEpsB) - (sintA + sintB) * (temp2 + sinEpsB)) * xb;
         c += t;
         d = -(c - a - t);
         a = c;
         b += d;
      }

      double result = a + b;
      return result;
   }

   private static double cosQ(double xa, double xb) {
      double pi2a = 1.5707963267948966D;
      double pi2b = 6.123233995736766E-17D;
      double a = 1.5707963267948966D - xa;
      double b = -(a - 1.5707963267948966D + xa);
      b += 6.123233995736766E-17D - xb;
      return sinQ(a, b);
   }

   private static double tanQ(double xa, double xb, boolean cotanFlag) {
      int idx = (int)(xa * 8.0D + 0.5D);
      double epsilon = xa - EIGHTHS[idx];
      double sintA = SINE_TABLE_A[idx];
      double sintB = SINE_TABLE_B[idx];
      double costA = COSINE_TABLE_A[idx];
      double costB = COSINE_TABLE_B[idx];
      double sinEpsB = polySine(epsilon);
      double cosEpsA = 1.0D;
      double cosEpsB = polyCosine(epsilon);
      double temp = epsilon * 1.073741824E9D;
      double temp2 = epsilon + temp - temp;
      sinEpsB += epsilon - temp2;
      double a = 0.0D;
      double b = 0.0D;
      double c = a + sintA;
      double d = -(c - a - sintA);
      a = c;
      b += d;
      double t = costA * temp2;
      c += t;
      d = -(c - a - t);
      b += d;
      b = b + sintA * cosEpsB + costA * sinEpsB;
      b = b + sintB + costB * temp2 + sintB * cosEpsB + costB * sinEpsB;
      double sina = c + b;
      double sinb = -(sina - c - b);
      d = 0.0D;
      c = 0.0D;
      b = 0.0D;
      a = 0.0D;
      t = costA * 1.0D;
      c = a + t;
      d = -(c - a - t);
      a = c;
      b += d;
      t = -sintA * temp2;
      c += t;
      d = -(c - a - t);
      b += d;
      b = b + costB * 1.0D + costA * cosEpsB + costB * cosEpsB;
      b -= sintB * temp2 + sintA * sinEpsB + sintB * sinEpsB;
      double cosa = c + b;
      double cosb = -(cosa - c - b);
      double est;
      if (cotanFlag) {
         est = cosa;
         cosa = sina;
         sina = est;
         est = cosb;
         cosb = sinb;
         sinb = est;
      }

      est = sina / cosa;
      temp = est * 1.073741824E9D;
      double esta = est + temp - temp;
      double estb = est - esta;
      temp = cosa * 1.073741824E9D;
      double cosaa = cosa + temp - temp;
      double cosab = cosa - cosaa;
      double err = (sina - esta * cosaa - esta * cosab - estb * cosaa - estb * cosab) / cosa;
      err += sinb / cosa;
      err += -sina * cosb / cosa / cosa;
      if (xb != 0.0D) {
         double xbadj = xb + est * est * xb;
         if (cotanFlag) {
            xbadj = -xbadj;
         }

         err += xbadj;
      }

      return est + err;
   }

   private static void reducePayneHanek(double x, double[] result) {
      long inbits = Double.doubleToRawLongBits(x);
      int exponent = (int)(inbits >> 52 & 2047L) - 1023;
      inbits &= 4503599627370495L;
      inbits |= 4503599627370496L;
      ++exponent;
      inbits <<= 11;
      int idx = exponent >> 6;
      int shift = exponent - (idx << 6);
      long shpi0;
      long shpiA;
      long shpiB;
      if (shift != 0) {
         shpi0 = idx == 0 ? 0L : RECIP_2PI[idx - 1] << shift;
         shpi0 |= RECIP_2PI[idx] >>> 64 - shift;
         shpiA = RECIP_2PI[idx] << shift | RECIP_2PI[idx + 1] >>> 64 - shift;
         shpiB = RECIP_2PI[idx + 1] << shift | RECIP_2PI[idx + 2] >>> 64 - shift;
      } else {
         shpi0 = idx == 0 ? 0L : RECIP_2PI[idx - 1];
         shpiA = RECIP_2PI[idx];
         shpiB = RECIP_2PI[idx + 1];
      }

      long a = inbits >>> 32;
      long b = inbits & 4294967295L;
      long c = shpiA >>> 32;
      long d = shpiA & 4294967295L;
      long ac = a * c;
      long bd = b * d;
      long bc = b * c;
      long ad = a * d;
      long prodB = bd + (ad << 32);
      long prodA = ac + (ad >>> 32);
      boolean bita = (bd & Long.MIN_VALUE) != 0L;
      boolean bitb = (ad & 2147483648L) != 0L;
      boolean bitsum = (prodB & Long.MIN_VALUE) != 0L;
      if (bita && bitb || (bita || bitb) && !bitsum) {
         ++prodA;
      }

      bita = (prodB & Long.MIN_VALUE) != 0L;
      bitb = (bc & 2147483648L) != 0L;
      prodB += bc << 32;
      prodA += bc >>> 32;
      bitsum = (prodB & Long.MIN_VALUE) != 0L;
      if (bita && bitb || (bita || bitb) && !bitsum) {
         ++prodA;
      }

      c = shpiB >>> 32;
      d = shpiB & 4294967295L;
      ac = a * c;
      bc = b * c;
      ad = a * d;
      ac += bc + ad >>> 32;
      bita = (prodB & Long.MIN_VALUE) != 0L;
      bitb = (ac & Long.MIN_VALUE) != 0L;
      prodB += ac;
      bitsum = (prodB & Long.MIN_VALUE) != 0L;
      if (bita && bitb || (bita || bitb) && !bitsum) {
         ++prodA;
      }

      c = shpi0 >>> 32;
      d = shpi0 & 4294967295L;
      bd = b * d;
      bc = b * c;
      ad = a * d;
      prodA += bd + (bc + ad << 32);
      int intPart = (int)(prodA >>> 62);
      prodA <<= 2;
      prodA |= prodB >>> 62;
      prodB <<= 2;
      a = prodA >>> 32;
      b = prodA & 4294967295L;
      c = PI_O_4_BITS[0] >>> 32;
      d = PI_O_4_BITS[0] & 4294967295L;
      ac = a * c;
      bd = b * d;
      bc = b * c;
      ad = a * d;
      long prod2B = bd + (ad << 32);
      long prod2A = ac + (ad >>> 32);
      bita = (bd & Long.MIN_VALUE) != 0L;
      bitb = (ad & 2147483648L) != 0L;
      bitsum = (prod2B & Long.MIN_VALUE) != 0L;
      if (bita && bitb || (bita || bitb) && !bitsum) {
         ++prod2A;
      }

      bita = (prod2B & Long.MIN_VALUE) != 0L;
      bitb = (bc & 2147483648L) != 0L;
      prod2B += bc << 32;
      prod2A += bc >>> 32;
      bitsum = (prod2B & Long.MIN_VALUE) != 0L;
      if (bita && bitb || (bita || bitb) && !bitsum) {
         ++prod2A;
      }

      c = PI_O_4_BITS[1] >>> 32;
      d = PI_O_4_BITS[1] & 4294967295L;
      ac = a * c;
      bc = b * c;
      ad = a * d;
      ac += bc + ad >>> 32;
      bita = (prod2B & Long.MIN_VALUE) != 0L;
      bitb = (ac & Long.MIN_VALUE) != 0L;
      prod2B += ac;
      bitsum = (prod2B & Long.MIN_VALUE) != 0L;
      if (bita && bitb || (bita || bitb) && !bitsum) {
         ++prod2A;
      }

      a = prodB >>> 32;
      b = prodB & 4294967295L;
      c = PI_O_4_BITS[0] >>> 32;
      d = PI_O_4_BITS[0] & 4294967295L;
      ac = a * c;
      bc = b * c;
      ad = a * d;
      ac += bc + ad >>> 32;
      bita = (prod2B & Long.MIN_VALUE) != 0L;
      bitb = (ac & Long.MIN_VALUE) != 0L;
      prod2B += ac;
      bitsum = (prod2B & Long.MIN_VALUE) != 0L;
      if (bita && bitb || (bita || bitb) && !bitsum) {
         ++prod2A;
      }

      double tmpA = (double)(prod2A >>> 12) / 4.503599627370496E15D;
      double tmpB = (double)(((prod2A & 4095L) << 40) + (prod2B >>> 24)) / 4.503599627370496E15D / 4.503599627370496E15D;
      double sumA = tmpA + tmpB;
      double sumB = -(sumA - tmpA - tmpB);
      result[0] = (double)intPart;
      result[1] = sumA * 2.0D;
      result[2] = sumB * 2.0D;
   }

   public static double sin(double x) {
      boolean negative = false;
      int quadrant = 0;
      double xb = 0.0D;
      double xa = x;
      if (x < 0.0D) {
         negative = true;
         xa = -x;
      }

      if (xa == 0.0D) {
         long bits = Double.doubleToRawLongBits(x);
         return bits < 0L ? -0.0D : 0.0D;
      } else if (xa == xa && xa != Double.POSITIVE_INFINITY) {
         if (xa > 3294198.0D) {
            double[] reduceResults = new double[3];
            reducePayneHanek(xa, reduceResults);
            quadrant = (int)reduceResults[0] & 3;
            xa = reduceResults[1];
            xb = reduceResults[2];
         } else if (xa > 1.5707963267948966D) {
            FastMath.CodyWaite cw = new FastMath.CodyWaite(xa);
            quadrant = cw.getK() & 3;
            xa = cw.getRemA();
            xb = cw.getRemB();
         }

         if (negative) {
            quadrant ^= 2;
         }

         switch(quadrant) {
         case 0:
            return sinQ(xa, xb);
         case 1:
            return cosQ(xa, xb);
         case 2:
            return -sinQ(xa, xb);
         case 3:
            return -cosQ(xa, xb);
         default:
            return Double.NaN;
         }
      } else {
         return Double.NaN;
      }
   }

   public static double cos(double x) {
      int quadrant = 0;
      double xa = x;
      if (x < 0.0D) {
         xa = -x;
      }

      if (xa == xa && xa != Double.POSITIVE_INFINITY) {
         double xb = 0.0D;
         if (xa > 3294198.0D) {
            double[] reduceResults = new double[3];
            reducePayneHanek(xa, reduceResults);
            quadrant = (int)reduceResults[0] & 3;
            xa = reduceResults[1];
            xb = reduceResults[2];
         } else if (xa > 1.5707963267948966D) {
            FastMath.CodyWaite cw = new FastMath.CodyWaite(xa);
            quadrant = cw.getK() & 3;
            xa = cw.getRemA();
            xb = cw.getRemB();
         }

         switch(quadrant) {
         case 0:
            return cosQ(xa, xb);
         case 1:
            return -sinQ(xa, xb);
         case 2:
            return -cosQ(xa, xb);
         case 3:
            return sinQ(xa, xb);
         default:
            return Double.NaN;
         }
      } else {
         return Double.NaN;
      }
   }

   public static double tan(double x) {
      boolean negative = false;
      int quadrant = 0;
      double xa = x;
      if (x < 0.0D) {
         negative = true;
         xa = -x;
      }

      if (xa == 0.0D) {
         long bits = Double.doubleToRawLongBits(x);
         return bits < 0L ? -0.0D : 0.0D;
      } else if (xa == xa && xa != Double.POSITIVE_INFINITY) {
         double xb = 0.0D;
         if (xa > 3294198.0D) {
            double[] reduceResults = new double[3];
            reducePayneHanek(xa, reduceResults);
            quadrant = (int)reduceResults[0] & 3;
            xa = reduceResults[1];
            xb = reduceResults[2];
         } else if (xa > 1.5707963267948966D) {
            FastMath.CodyWaite cw = new FastMath.CodyWaite(xa);
            quadrant = cw.getK() & 3;
            xa = cw.getRemA();
            xb = cw.getRemB();
         }

         double result;
         if (xa > 1.5D) {
            result = 1.5707963267948966D;
            double pi2b = 6.123233995736766E-17D;
            double a = 1.5707963267948966D - xa;
            double b = -(a - 1.5707963267948966D + xa);
            b += 6.123233995736766E-17D - xb;
            xa = a + b;
            xb = -(xa - a - b);
            quadrant ^= 1;
            negative ^= true;
         }

         if ((quadrant & 1) == 0) {
            result = tanQ(xa, xb, false);
         } else {
            result = -tanQ(xa, xb, true);
         }

         if (negative) {
            result = -result;
         }

         return result;
      } else {
         return Double.NaN;
      }
   }

   public static double atan(double x) {
      return atan(x, 0.0D, false);
   }

   private static double atan(double xa, double xb, boolean leftPlane) {
      boolean negate = false;
      if (xa == 0.0D) {
         return leftPlane ? copySign(3.141592653589793D, xa) : xa;
      } else {
         if (xa < 0.0D) {
            xa = -xa;
            xb = -xb;
            negate = true;
         }

         if (xa > 1.633123935319537E16D) {
            return negate ^ leftPlane ? -1.5707963267948966D : 1.5707963267948966D;
         } else {
            int idx;
            double epsA;
            if (xa < 1.0D) {
               idx = (int)((-1.7168146928204135D * xa * xa + 8.0D) * xa + 0.5D);
            } else {
               epsA = 1.0D / xa;
               idx = (int)(-((-1.7168146928204135D * epsA * epsA + 8.0D) * epsA) + 13.07D);
            }

            epsA = xa - TANGENT_TABLE_A[idx];
            double epsB = -(epsA - xa + TANGENT_TABLE_A[idx]);
            epsB += xb - TANGENT_TABLE_B[idx];
            double temp = epsA + epsB;
            epsB = -(temp - epsA - epsB);
            epsA = temp;
            temp = xa * 1.073741824E9D;
            double ya = xa + temp - temp;
            double yb = xb + xa - ya;
            xb += yb;
            double epsA2;
            double za;
            double zb;
            double result;
            double resultb;
            double pia;
            double pib;
            if (idx == 0) {
               epsA2 = 1.0D / (1.0D + (ya + xb) * (TANGENT_TABLE_A[idx] + TANGENT_TABLE_B[idx]));
               ya = epsA * epsA2;
               yb = epsB * epsA2;
            } else {
               epsA2 = ya * TANGENT_TABLE_A[idx];
               za = 1.0D + epsA2;
               zb = -(za - 1.0D - epsA2);
               epsA2 = xb * TANGENT_TABLE_A[idx] + ya * TANGENT_TABLE_B[idx];
               temp = za + epsA2;
               zb += -(temp - za - epsA2);
               za = temp;
               zb += xb * TANGENT_TABLE_B[idx];
               ya = epsA / temp;
               temp = ya * 1.073741824E9D;
               result = ya + temp - temp;
               resultb = ya - result;
               temp = za * 1.073741824E9D;
               pia = za + temp - temp;
               pib = za - pia;
               yb = (epsA - result * pia - result * pib - resultb * pia - resultb * pib) / za;
               yb += -epsA * zb / za / za;
               yb += epsB / za;
            }

            epsB = yb;
            epsA2 = ya * ya;
            yb = 0.07490822288864472D;
            yb = yb * epsA2 + -0.09088450866185192D;
            yb = yb * epsA2 + 0.11111095942313305D;
            yb = yb * epsA2 + -0.1428571423679182D;
            yb = yb * epsA2 + 0.19999999999923582D;
            yb = yb * epsA2 + -0.33333333333333287D;
            yb = yb * epsA2 * ya;
            temp = ya + yb;
            yb = -(temp - ya - yb);
            yb += epsB / (1.0D + ya * ya);
            za = EIGHTHS[idx] + temp;
            zb = -(za - EIGHTHS[idx] - temp);
            temp = za + yb;
            zb += -(temp - za - yb);
            result = temp + zb;
            resultb = -(result - temp - zb);
            if (leftPlane) {
               pia = 3.141592653589793D;
               pib = 1.2246467991473532E-16D;
               za = 3.141592653589793D - result;
               zb = -(za - 3.141592653589793D + result);
               zb += 1.2246467991473532E-16D - resultb;
               result = za + zb;
               double var10000 = -(result - za - zb);
            }

            if (negate ^ leftPlane) {
               result = -result;
            }

            return result;
         }
      }
   }

   public static double atan2(double y, double x) {
      if (x == x && y == y) {
         double r;
         double ra;
         double rb;
         if (y == 0.0D) {
            r = x * y;
            ra = 1.0D / x;
            rb = 1.0D / y;
            if (ra == 0.0D) {
               return x > 0.0D ? y : copySign(3.141592653589793D, y);
            } else if (!(x < 0.0D) && !(ra < 0.0D)) {
               return r;
            } else {
               return !(y < 0.0D) && !(rb < 0.0D) ? 3.141592653589793D : -3.141592653589793D;
            }
         } else if (y == Double.POSITIVE_INFINITY) {
            if (x == Double.POSITIVE_INFINITY) {
               return 0.7853981633974483D;
            } else {
               return x == Double.NEGATIVE_INFINITY ? 2.356194490192345D : 1.5707963267948966D;
            }
         } else if (y == Double.NEGATIVE_INFINITY) {
            if (x == Double.POSITIVE_INFINITY) {
               return -0.7853981633974483D;
            } else {
               return x == Double.NEGATIVE_INFINITY ? -2.356194490192345D : -1.5707963267948966D;
            }
         } else {
            if (x == Double.POSITIVE_INFINITY) {
               if (y > 0.0D || 1.0D / y > 0.0D) {
                  return 0.0D;
               }

               if (y < 0.0D || 1.0D / y < 0.0D) {
                  return -0.0D;
               }
            }

            if (x == Double.NEGATIVE_INFINITY) {
               if (y > 0.0D || 1.0D / y > 0.0D) {
                  return 3.141592653589793D;
               }

               if (y < 0.0D || 1.0D / y < 0.0D) {
                  return -3.141592653589793D;
               }
            }

            if (x == 0.0D) {
               if (y > 0.0D || 1.0D / y > 0.0D) {
                  return 1.5707963267948966D;
               }

               if (y < 0.0D || 1.0D / y < 0.0D) {
                  return -1.5707963267948966D;
               }
            }

            r = y / x;
            if (Double.isInfinite(r)) {
               return atan(r, 0.0D, x < 0.0D);
            } else {
               ra = doubleHighPart(r);
               rb = r - ra;
               double xa = doubleHighPart(x);
               double xb = x - xa;
               rb += (y - ra * xa - ra * xb - rb * xa - rb * xb) / x;
               double temp = ra + rb;
               rb = -(temp - ra - rb);
               ra = temp;
               if (temp == 0.0D) {
                  ra = copySign(0.0D, y);
               }

               double result = atan(ra, rb, x < 0.0D);
               return result;
            }
         }
      } else {
         return Double.NaN;
      }
   }

   public static double asin(double x) {
      if (x != x) {
         return Double.NaN;
      } else if (!(x > 1.0D) && !(x < -1.0D)) {
         if (x == 1.0D) {
            return 1.5707963267948966D;
         } else if (x == -1.0D) {
            return -1.5707963267948966D;
         } else if (x == 0.0D) {
            return x;
         } else {
            double temp = x * 1.073741824E9D;
            double xa = x + temp - temp;
            double xb = x - xa;
            double ya = xa * xa;
            double yb = xa * xb * 2.0D + xb * xb;
            ya = -ya;
            yb = -yb;
            double za = 1.0D + ya;
            double zb = -(za - 1.0D - ya);
            temp = za + yb;
            zb += -(temp - za - yb);
            za = temp;
            double y = sqrt(temp);
            temp = y * 1.073741824E9D;
            ya = y + temp - temp;
            yb = y - ya;
            yb += (za - ya * ya - 2.0D * ya * yb - yb * yb) / (2.0D * y);
            double dx = zb / (2.0D * y);
            double r = x / y;
            temp = r * 1.073741824E9D;
            double ra = r + temp - temp;
            double rb = r - ra;
            rb += (x - ra * ya - ra * yb - rb * ya - rb * yb) / y;
            rb += -x * dx / y / y;
            temp = ra + rb;
            rb = -(temp - ra - rb);
            return atan(temp, rb, false);
         }
      } else {
         return Double.NaN;
      }
   }

   public static double acos(double x) {
      if (x != x) {
         return Double.NaN;
      } else if (!(x > 1.0D) && !(x < -1.0D)) {
         if (x == -1.0D) {
            return 3.141592653589793D;
         } else if (x == 1.0D) {
            return 0.0D;
         } else if (x == 0.0D) {
            return 1.5707963267948966D;
         } else {
            double temp = x * 1.073741824E9D;
            double xa = x + temp - temp;
            double xb = x - xa;
            double ya = xa * xa;
            double yb = xa * xb * 2.0D + xb * xb;
            ya = -ya;
            yb = -yb;
            double za = 1.0D + ya;
            double zb = -(za - 1.0D - ya);
            temp = za + yb;
            zb += -(temp - za - yb);
            za = temp;
            double y = sqrt(temp);
            temp = y * 1.073741824E9D;
            ya = y + temp - temp;
            yb = y - ya;
            yb += (za - ya * ya - 2.0D * ya * yb - yb * yb) / (2.0D * y);
            yb += zb / (2.0D * y);
            y = ya + yb;
            yb = -(y - ya - yb);
            double r = y / x;
            if (Double.isInfinite(r)) {
               return 1.5707963267948966D;
            } else {
               double ra = doubleHighPart(r);
               double rb = r - ra;
               rb += (y - ra * xa - ra * xb - rb * xa - rb * xb) / x;
               rb += yb / x;
               temp = ra + rb;
               rb = -(temp - ra - rb);
               return atan(temp, rb, x < 0.0D);
            }
         }
      } else {
         return Double.NaN;
      }
   }

   public static double cbrt(double x) {
      long inbits = Double.doubleToRawLongBits(x);
      int exponent = (int)(inbits >> 52 & 2047L) - 1023;
      boolean subnormal = false;
      if (exponent == -1023) {
         if (x == 0.0D) {
            return x;
         }

         subnormal = true;
         x *= 1.8014398509481984E16D;
         inbits = Double.doubleToRawLongBits(x);
         exponent = (int)(inbits >> 52 & 2047L) - 1023;
      }

      if (exponent == 1024) {
         return x;
      } else {
         int exp3 = exponent / 3;
         double p2 = Double.longBitsToDouble(inbits & Long.MIN_VALUE | (long)(exp3 + 1023 & 2047) << 52);
         double mant = Double.longBitsToDouble(inbits & 4503599627370495L | 4607182418800017408L);
         double est = -0.010714690733195933D;
         est = est * mant + 0.0875862700108075D;
         est = est * mant + -0.3058015757857271D;
         est = est * mant + 0.7249995199969751D;
         est = est * mant + 0.5039018405998233D;
         est *= CBRTTWO[exponent % 3 + 2];
         double xs = x / (p2 * p2 * p2);
         est += (xs - est * est * est) / (3.0D * est * est);
         est += (xs - est * est * est) / (3.0D * est * est);
         double temp = est * 1.073741824E9D;
         double ya = est + temp - temp;
         double yb = est - ya;
         double za = ya * ya;
         double zb = ya * yb * 2.0D + yb * yb;
         temp = za * 1.073741824E9D;
         double temp2 = za + temp - temp;
         zb += za - temp2;
         zb = temp2 * yb + ya * zb + zb * yb;
         za = temp2 * ya;
         double na = xs - za;
         double nb = -(na - xs + za);
         nb -= zb;
         est += (na + nb) / (3.0D * est * est);
         est *= p2;
         if (subnormal) {
            est *= 3.814697265625E-6D;
         }

         return est;
      }
   }

   public static double toRadians(double x) {
      if (!Double.isInfinite(x) && x != 0.0D) {
         double facta = 0.01745329052209854D;
         double factb = 1.997844754509471E-9D;
         double xa = doubleHighPart(x);
         double xb = x - xa;
         double result = xb * 1.997844754509471E-9D + xb * 0.01745329052209854D + xa * 1.997844754509471E-9D + xa * 0.01745329052209854D;
         if (result == 0.0D) {
            result *= x;
         }

         return result;
      } else {
         return x;
      }
   }

   public static double toDegrees(double x) {
      if (!Double.isInfinite(x) && x != 0.0D) {
         double facta = 57.2957763671875D;
         double factb = 3.145894820876798E-6D;
         double xa = doubleHighPart(x);
         double xb = x - xa;
         return xb * 3.145894820876798E-6D + xb * 57.2957763671875D + xa * 3.145894820876798E-6D + xa * 57.2957763671875D;
      } else {
         return x;
      }
   }

   public static int abs(int x) {
      int i = x >>> 31;
      return (x ^ ~i + 1) + i;
   }

   public static long abs(long x) {
      long l = x >>> 63;
      return (x ^ ~l + 1L) + l;
   }

   public static float abs(float x) {
      return Float.intBitsToFloat(Integer.MAX_VALUE & Float.floatToRawIntBits(x));
   }

   public static double abs(double x) {
      return Double.longBitsToDouble(Long.MAX_VALUE & Double.doubleToRawLongBits(x));
   }

   public static double ulp(double x) {
      return Double.isInfinite(x) ? Double.POSITIVE_INFINITY : abs(x - Double.longBitsToDouble(Double.doubleToRawLongBits(x) ^ 1L));
   }

   public static float ulp(float x) {
      return Float.isInfinite(x) ? Float.POSITIVE_INFINITY : abs(x - Float.intBitsToFloat(Float.floatToIntBits(x) ^ 1));
   }

   public static double scalb(double d, int n) {
      if (n > -1023 && n < 1024) {
         return d * Double.longBitsToDouble((long)(n + 1023) << 52);
      } else if (!Double.isNaN(d) && !Double.isInfinite(d) && d != 0.0D) {
         if (n < -2098) {
            return d > 0.0D ? 0.0D : -0.0D;
         } else if (n > 2097) {
            return d > 0.0D ? Double.POSITIVE_INFINITY : Double.NEGATIVE_INFINITY;
         } else {
            long bits = Double.doubleToRawLongBits(d);
            long sign = bits & Long.MIN_VALUE;
            int exponent = (int)(bits >>> 52) & 2047;
            long mantissa = bits & 4503599627370495L;
            int scaledExponent = exponent + n;
            if (n < 0) {
               if (scaledExponent > 0) {
                  return Double.longBitsToDouble(sign | (long)scaledExponent << 52 | mantissa);
               } else if (scaledExponent > -53) {
                  mantissa |= 4503599627370496L;
                  long mostSignificantLostBit = mantissa & 1L << -scaledExponent;
                  mantissa >>>= 1 - scaledExponent;
                  if (mostSignificantLostBit != 0L) {
                     ++mantissa;
                  }

                  return Double.longBitsToDouble(sign | mantissa);
               } else {
                  return sign == 0L ? 0.0D : -0.0D;
               }
            } else if (exponent != 0) {
               if (scaledExponent < 2047) {
                  return Double.longBitsToDouble(sign | (long)scaledExponent << 52 | mantissa);
               } else {
                  return sign == 0L ? Double.POSITIVE_INFINITY : Double.NEGATIVE_INFINITY;
               }
            } else {
               while(mantissa >>> 52 != 1L) {
                  mantissa <<= 1;
                  --scaledExponent;
               }

               ++scaledExponent;
               mantissa &= 4503599627370495L;
               if (scaledExponent < 2047) {
                  return Double.longBitsToDouble(sign | (long)scaledExponent << 52 | mantissa);
               } else {
                  return sign == 0L ? Double.POSITIVE_INFINITY : Double.NEGATIVE_INFINITY;
               }
            }
         }
      } else {
         return d;
      }
   }

   public static float scalb(float f, int n) {
      if (n > -127 && n < 128) {
         return f * Float.intBitsToFloat(n + 127 << 23);
      } else if (!Float.isNaN(f) && !Float.isInfinite(f) && f != 0.0F) {
         if (n < -277) {
            return f > 0.0F ? 0.0F : -0.0F;
         } else if (n > 276) {
            return f > 0.0F ? Float.POSITIVE_INFINITY : Float.NEGATIVE_INFINITY;
         } else {
            int bits = Float.floatToIntBits(f);
            int sign = bits & Integer.MIN_VALUE;
            int exponent = bits >>> 23 & 255;
            int mantissa = bits & 8388607;
            int scaledExponent = exponent + n;
            if (n < 0) {
               if (scaledExponent > 0) {
                  return Float.intBitsToFloat(sign | scaledExponent << 23 | mantissa);
               } else if (scaledExponent > -24) {
                  mantissa |= 8388608;
                  int mostSignificantLostBit = mantissa & 1 << -scaledExponent;
                  mantissa >>>= 1 - scaledExponent;
                  if (mostSignificantLostBit != 0) {
                     ++mantissa;
                  }

                  return Float.intBitsToFloat(sign | mantissa);
               } else {
                  return sign == 0 ? 0.0F : -0.0F;
               }
            } else if (exponent != 0) {
               if (scaledExponent < 255) {
                  return Float.intBitsToFloat(sign | scaledExponent << 23 | mantissa);
               } else {
                  return sign == 0 ? Float.POSITIVE_INFINITY : Float.NEGATIVE_INFINITY;
               }
            } else {
               while(mantissa >>> 23 != 1) {
                  mantissa <<= 1;
                  --scaledExponent;
               }

               ++scaledExponent;
               mantissa &= 8388607;
               if (scaledExponent < 255) {
                  return Float.intBitsToFloat(sign | scaledExponent << 23 | mantissa);
               } else {
                  return sign == 0 ? Float.POSITIVE_INFINITY : Float.NEGATIVE_INFINITY;
               }
            }
         }
      } else {
         return f;
      }
   }

   public static double nextAfter(double d, double direction) {
      if (!Double.isNaN(d) && !Double.isNaN(direction)) {
         if (d == direction) {
            return direction;
         } else if (Double.isInfinite(d)) {
            return d < 0.0D ? -1.7976931348623157E308D : Double.MAX_VALUE;
         } else if (d == 0.0D) {
            return direction < 0.0D ? -4.9E-324D : Double.MIN_VALUE;
         } else {
            long bits = Double.doubleToRawLongBits(d);
            long sign = bits & Long.MIN_VALUE;
            return direction < d ^ sign == 0L ? Double.longBitsToDouble(sign | (bits & Long.MAX_VALUE) + 1L) : Double.longBitsToDouble(sign | (bits & Long.MAX_VALUE) - 1L);
         }
      } else {
         return Double.NaN;
      }
   }

   public static float nextAfter(float f, double direction) {
      if (!Double.isNaN((double)f) && !Double.isNaN(direction)) {
         if ((double)f == direction) {
            return (float)direction;
         } else if (Float.isInfinite(f)) {
            return f < 0.0F ? -3.4028235E38F : Float.MAX_VALUE;
         } else if (f == 0.0F) {
            return direction < 0.0D ? -1.4E-45F : Float.MIN_VALUE;
         } else {
            int bits = Float.floatToIntBits(f);
            int sign = bits & Integer.MIN_VALUE;
            return direction < (double)f ^ sign == 0 ? Float.intBitsToFloat(sign | (bits & Integer.MAX_VALUE) + 1) : Float.intBitsToFloat(sign | (bits & Integer.MAX_VALUE) - 1);
         }
      } else {
         return Float.NaN;
      }
   }

   public static double floor(double x) {
      if (x != x) {
         return x;
      } else if (!(x >= 4.503599627370496E15D) && !(x <= -4.503599627370496E15D)) {
         long y = (long)x;
         if (x < 0.0D && (double)y != x) {
            --y;
         }

         return y == 0L ? x * (double)y : (double)y;
      } else {
         return x;
      }
   }

   public static double ceil(double x) {
      if (x != x) {
         return x;
      } else {
         double y = floor(x);
         if (y == x) {
            return y;
         } else {
            ++y;
            return y == 0.0D ? x * y : y;
         }
      }
   }

   public static double rint(double x) {
      double y = floor(x);
      double d = x - y;
      if (d > 0.5D) {
         return y == -1.0D ? -0.0D : y + 1.0D;
      } else if (d < 0.5D) {
         return y;
      } else {
         long z = (long)y;
         return (z & 1L) == 0L ? y : y + 1.0D;
      }
   }

   public static long round(double x) {
      return (long)floor(x + 0.5D);
   }

   public static int round(float x) {
      return (int)floor((double)(x + 0.5F));
   }

   public static int min(int a, int b) {
      return a <= b ? a : b;
   }

   public static long min(long a, long b) {
      return a <= b ? a : b;
   }

   public static float min(float a, float b) {
      if (a > b) {
         return b;
      } else if (a < b) {
         return a;
      } else if (a != b) {
         return Float.NaN;
      } else {
         int bits = Float.floatToRawIntBits(a);
         return bits == Integer.MIN_VALUE ? a : b;
      }
   }

   public static double min(double a, double b) {
      if (a > b) {
         return b;
      } else if (a < b) {
         return a;
      } else if (a != b) {
         return Double.NaN;
      } else {
         long bits = Double.doubleToRawLongBits(a);
         return bits == Long.MIN_VALUE ? a : b;
      }
   }

   public static int max(int a, int b) {
      return a <= b ? b : a;
   }

   public static long max(long a, long b) {
      return a <= b ? b : a;
   }

   public static float max(float a, float b) {
      if (a > b) {
         return a;
      } else if (a < b) {
         return b;
      } else if (a != b) {
         return Float.NaN;
      } else {
         int bits = Float.floatToRawIntBits(a);
         return bits == Integer.MIN_VALUE ? b : a;
      }
   }

   public static double max(double a, double b) {
      if (a > b) {
         return a;
      } else if (a < b) {
         return b;
      } else if (a != b) {
         return Double.NaN;
      } else {
         long bits = Double.doubleToRawLongBits(a);
         return bits == Long.MIN_VALUE ? b : a;
      }
   }

   public static double hypot(double x, double y) {
      if (!Double.isInfinite(x) && !Double.isInfinite(y)) {
         if (!Double.isNaN(x) && !Double.isNaN(y)) {
            int expX = getExponent(x);
            int expY = getExponent(y);
            if (expX > expY + 27) {
               return abs(x);
            } else if (expY > expX + 27) {
               return abs(y);
            } else {
               int middleExp = (expX + expY) / 2;
               double scaledX = scalb(x, -middleExp);
               double scaledY = scalb(y, -middleExp);
               double scaledH = sqrt(scaledX * scaledX + scaledY * scaledY);
               return scalb(scaledH, middleExp);
            }
         } else {
            return Double.NaN;
         }
      } else {
         return Double.POSITIVE_INFINITY;
      }
   }

   public static double IEEEremainder(double dividend, double divisor) {
      return StrictMath.IEEEremainder(dividend, divisor);
   }

   public static double copySign(double magnitude, double sign) {
      long m = Double.doubleToRawLongBits(magnitude);
      long s = Double.doubleToRawLongBits(sign);
      return (m ^ s) >= 0L ? magnitude : -magnitude;
   }

   public static float copySign(float magnitude, float sign) {
      int m = Float.floatToRawIntBits(magnitude);
      int s = Float.floatToRawIntBits(sign);
      return (m ^ s) >= 0 ? magnitude : -magnitude;
   }

   public static int getExponent(double d) {
      return (int)(Double.doubleToRawLongBits(d) >>> 52 & 2047L) - 1023;
   }

   public static int getExponent(float f) {
      return (Float.floatToRawIntBits(f) >>> 23 & 255) - 127;
   }

   public static void main(String[] a) {
      PrintStream out = System.out;
      FastMathCalc.printarray(out, "EXP_INT_TABLE_A", 1500, (double[])FastMath.ExpIntTable.EXP_INT_TABLE_A);
      FastMathCalc.printarray(out, "EXP_INT_TABLE_B", 1500, (double[])FastMath.ExpIntTable.EXP_INT_TABLE_B);
      FastMathCalc.printarray(out, "EXP_FRAC_TABLE_A", 1025, (double[])FastMath.ExpFracTable.EXP_FRAC_TABLE_A);
      FastMathCalc.printarray(out, "EXP_FRAC_TABLE_B", 1025, (double[])FastMath.ExpFracTable.EXP_FRAC_TABLE_B);
      FastMathCalc.printarray(out, "LN_MANT", 1024, (double[][])FastMath.lnMant.LN_MANT);
      FastMathCalc.printarray(out, "SINE_TABLE_A", 14, (double[])SINE_TABLE_A);
      FastMathCalc.printarray(out, "SINE_TABLE_B", 14, (double[])SINE_TABLE_B);
      FastMathCalc.printarray(out, "COSINE_TABLE_A", 14, (double[])COSINE_TABLE_A);
      FastMathCalc.printarray(out, "COSINE_TABLE_B", 14, (double[])COSINE_TABLE_B);
      FastMathCalc.printarray(out, "TANGENT_TABLE_A", 14, (double[])TANGENT_TABLE_A);
      FastMathCalc.printarray(out, "TANGENT_TABLE_B", 14, (double[])TANGENT_TABLE_B);
   }

   private static class CodyWaite {
      private final int finalK;
      private final double finalRemA;
      private final double finalRemB;

      CodyWaite(double xa) {
         int k = (int)(xa * 0.6366197723675814D);

         while(true) {
            double a = (double)(-k) * 1.570796251296997D;
            double remA = xa + a;
            double remB = -(remA - xa - a);
            a = (double)(-k) * 7.549789948768648E-8D;
            double b = remA;
            remA += a;
            remB += -(remA - b - a);
            a = (double)(-k) * 6.123233995736766E-17D;
            b = remA;
            remA += a;
            remB += -(remA - b - a);
            if (remA > 0.0D) {
               this.finalK = k;
               this.finalRemA = remA;
               this.finalRemB = remB;
               return;
            }

            --k;
         }
      }

      int getK() {
         return this.finalK;
      }

      double getRemA() {
         return this.finalRemA;
      }

      double getRemB() {
         return this.finalRemB;
      }
   }

   private static class lnMant {
      private static final double[][] LN_MANT = FastMathLiteralArrays.loadLnMant();
   }

   private static class ExpFracTable {
      private static final double[] EXP_FRAC_TABLE_A = FastMathLiteralArrays.loadExpFracA();
      private static final double[] EXP_FRAC_TABLE_B = FastMathLiteralArrays.loadExpFracB();
   }

   private static class ExpIntTable {
      private static final double[] EXP_INT_TABLE_A = FastMathLiteralArrays.loadExpIntA();
      private static final double[] EXP_INT_TABLE_B = FastMathLiteralArrays.loadExpIntB();
   }
}
