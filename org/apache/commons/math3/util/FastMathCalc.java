package org.apache.commons.math3.util;

import java.io.PrintStream;
import org.apache.commons.math3.exception.DimensionMismatchException;

class FastMathCalc {
   private static final long HEX_40000000 = 1073741824L;
   private static final double[] FACT = new double[]{1.0D, 1.0D, 2.0D, 6.0D, 24.0D, 120.0D, 720.0D, 5040.0D, 40320.0D, 362880.0D, 3628800.0D, 3.99168E7D, 4.790016E8D, 6.2270208E9D, 8.71782912E10D, 1.307674368E12D, 2.0922789888E13D, 3.55687428096E14D, 6.402373705728E15D, 1.21645100408832E17D};
   private static final double[][] LN_SPLIT_COEF = new double[][]{{2.0D, 0.0D}, {0.6666666269302368D, 3.9736429850260626E-8D}, {0.3999999761581421D, 2.3841857910019882E-8D}, {0.2857142686843872D, 1.7029898543501842E-8D}, {0.2222222089767456D, 1.3245471311735498E-8D}, {0.1818181574344635D, 2.4384203044354907E-8D}, {0.1538461446762085D, 9.140260083262505E-9D}, {0.13333332538604736D, 9.220590270857665E-9D}, {0.11764700710773468D, 1.2393345855018391E-8D}, {0.10526403784751892D, 8.251545029714408E-9D}, {0.0952233225107193D, 1.2675934823758863E-8D}, {0.08713622391223907D, 1.1430250008909141E-8D}, {0.07842259109020233D, 2.404307984052299E-9D}, {0.08371849358081818D, 1.176342548272881E-8D}, {0.030589580535888672D, 1.2958646899018938E-9D}, {0.14982303977012634D, 1.225743062930824E-8D}};
   private static final String TABLE_START_DECL = "    {";
   private static final String TABLE_END_DECL = "    };";

   private FastMathCalc() {
   }

   private static void buildSinCosTables(double[] SINE_TABLE_A, double[] SINE_TABLE_B, double[] COSINE_TABLE_A, double[] COSINE_TABLE_B, int SINE_TABLE_LEN, double[] TANGENT_TABLE_A, double[] TANGENT_TABLE_B) {
      double[] result = new double[2];

      int i;
      for(i = 0; i < 7; ++i) {
         double x = (double)i / 8.0D;
         slowSin(x, result);
         SINE_TABLE_A[i] = result[0];
         SINE_TABLE_B[i] = result[1];
         slowCos(x, result);
         COSINE_TABLE_A[i] = result[0];
         COSINE_TABLE_B[i] = result[1];
      }

      double[] ys;
      double[] as;
      double[] xs;
      for(i = 7; i < SINE_TABLE_LEN; ++i) {
         xs = new double[2];
         ys = new double[2];
         as = new double[2];
         double[] bs = new double[2];
         double[] temps = new double[2];
         if ((i & 1) == 0) {
            xs[0] = SINE_TABLE_A[i / 2];
            xs[1] = SINE_TABLE_B[i / 2];
            ys[0] = COSINE_TABLE_A[i / 2];
            ys[1] = COSINE_TABLE_B[i / 2];
            splitMult(xs, ys, result);
            SINE_TABLE_A[i] = result[0] * 2.0D;
            SINE_TABLE_B[i] = result[1] * 2.0D;
            splitMult(ys, ys, as);
            splitMult(xs, xs, temps);
            temps[0] = -temps[0];
            temps[1] = -temps[1];
            splitAdd(as, temps, result);
            COSINE_TABLE_A[i] = result[0];
            COSINE_TABLE_B[i] = result[1];
         } else {
            xs[0] = SINE_TABLE_A[i / 2];
            xs[1] = SINE_TABLE_B[i / 2];
            ys[0] = COSINE_TABLE_A[i / 2];
            ys[1] = COSINE_TABLE_B[i / 2];
            as[0] = SINE_TABLE_A[i / 2 + 1];
            as[1] = SINE_TABLE_B[i / 2 + 1];
            bs[0] = COSINE_TABLE_A[i / 2 + 1];
            bs[1] = COSINE_TABLE_B[i / 2 + 1];
            splitMult(xs, bs, temps);
            splitMult(ys, as, result);
            splitAdd(result, temps, result);
            SINE_TABLE_A[i] = result[0];
            SINE_TABLE_B[i] = result[1];
            splitMult(ys, bs, result);
            splitMult(xs, as, temps);
            temps[0] = -temps[0];
            temps[1] = -temps[1];
            splitAdd(result, temps, result);
            COSINE_TABLE_A[i] = result[0];
            COSINE_TABLE_B[i] = result[1];
         }
      }

      for(i = 0; i < SINE_TABLE_LEN; ++i) {
         xs = new double[2];
         ys = new double[2];
         as = new double[]{COSINE_TABLE_A[i], COSINE_TABLE_B[i]};
         splitReciprocal(as, ys);
         xs[0] = SINE_TABLE_A[i];
         xs[1] = SINE_TABLE_B[i];
         splitMult(xs, ys, as);
         TANGENT_TABLE_A[i] = as[0];
         TANGENT_TABLE_B[i] = as[1];
      }

   }

   static double slowCos(double x, double[] result) {
      double[] xs = new double[2];
      double[] ys = new double[2];
      double[] facts = new double[2];
      double[] as = new double[2];
      split(x, xs);
      ys[0] = ys[1] = 0.0D;

      for(int i = FACT.length - 1; i >= 0; --i) {
         splitMult(xs, ys, as);
         ys[0] = as[0];
         ys[1] = as[1];
         if ((i & 1) == 0) {
            split(FACT[i], as);
            splitReciprocal(as, facts);
            if ((i & 2) != 0) {
               facts[0] = -facts[0];
               facts[1] = -facts[1];
            }

            splitAdd(ys, facts, as);
            ys[0] = as[0];
            ys[1] = as[1];
         }
      }

      if (result != null) {
         result[0] = ys[0];
         result[1] = ys[1];
      }

      return ys[0] + ys[1];
   }

   static double slowSin(double x, double[] result) {
      double[] xs = new double[2];
      double[] ys = new double[2];
      double[] facts = new double[2];
      double[] as = new double[2];
      split(x, xs);
      ys[0] = ys[1] = 0.0D;

      for(int i = FACT.length - 1; i >= 0; --i) {
         splitMult(xs, ys, as);
         ys[0] = as[0];
         ys[1] = as[1];
         if ((i & 1) != 0) {
            split(FACT[i], as);
            splitReciprocal(as, facts);
            if ((i & 2) != 0) {
               facts[0] = -facts[0];
               facts[1] = -facts[1];
            }

            splitAdd(ys, facts, as);
            ys[0] = as[0];
            ys[1] = as[1];
         }
      }

      if (result != null) {
         result[0] = ys[0];
         result[1] = ys[1];
      }

      return ys[0] + ys[1];
   }

   static double slowexp(double x, double[] result) {
      double[] xs = new double[2];
      double[] ys = new double[2];
      double[] facts = new double[2];
      double[] as = new double[2];
      split(x, xs);
      ys[0] = ys[1] = 0.0D;

      for(int i = FACT.length - 1; i >= 0; --i) {
         splitMult(xs, ys, as);
         ys[0] = as[0];
         ys[1] = as[1];
         split(FACT[i], as);
         splitReciprocal(as, facts);
         splitAdd(ys, facts, as);
         ys[0] = as[0];
         ys[1] = as[1];
      }

      if (result != null) {
         result[0] = ys[0];
         result[1] = ys[1];
      }

      return ys[0] + ys[1];
   }

   private static void split(double d, double[] split) {
      double a;
      if (d < 8.0E298D && d > -8.0E298D) {
         a = d * 1.073741824E9D;
         split[0] = d + a - a;
         split[1] = d - split[0];
      } else {
         a = d * 9.313225746154785E-10D;
         split[0] = (d + a - d) * 1.073741824E9D;
         split[1] = d - split[0];
      }

   }

   private static void resplit(double[] a) {
      double c = a[0] + a[1];
      double d = -(c - a[0] - a[1]);
      double z;
      if (c < 8.0E298D && c > -8.0E298D) {
         z = c * 1.073741824E9D;
         a[0] = c + z - z;
         a[1] = c - a[0] + d;
      } else {
         z = c * 9.313225746154785E-10D;
         a[0] = (c + z - c) * 1.073741824E9D;
         a[1] = c - a[0] + d;
      }

   }

   private static void splitMult(double[] a, double[] b, double[] ans) {
      ans[0] = a[0] * b[0];
      ans[1] = a[0] * b[1] + a[1] * b[0] + a[1] * b[1];
      resplit(ans);
   }

   private static void splitAdd(double[] a, double[] b, double[] ans) {
      ans[0] = a[0] + b[0];
      ans[1] = a[1] + b[1];
      resplit(ans);
   }

   static void splitReciprocal(double[] in, double[] result) {
      double b = 2.384185791015625E-7D;
      double a = 0.9999997615814209D;
      if (in[0] == 0.0D) {
         in[0] = in[1];
         in[1] = 0.0D;
      }

      result[0] = 0.9999997615814209D / in[0];
      result[1] = (2.384185791015625E-7D * in[0] - 0.9999997615814209D * in[1]) / (in[0] * in[0] + in[0] * in[1]);
      if (result[1] != result[1]) {
         result[1] = 0.0D;
      }

      resplit(result);

      for(int i = 0; i < 2; ++i) {
         double err = 1.0D - result[0] * in[0] - result[0] * in[1] - result[1] * in[0] - result[1] * in[1];
         err *= result[0] + result[1];
         result[1] += err;
      }

   }

   private static void quadMult(double[] a, double[] b, double[] result) {
      double[] xs = new double[2];
      double[] ys = new double[2];
      double[] zs = new double[2];
      split(a[0], xs);
      split(b[0], ys);
      splitMult(xs, ys, zs);
      result[0] = zs[0];
      result[1] = zs[1];
      split(b[1], ys);
      splitMult(xs, ys, zs);
      double tmp = result[0] + zs[0];
      result[1] -= tmp - result[0] - zs[0];
      result[0] = tmp;
      tmp = result[0] + zs[1];
      result[1] -= tmp - result[0] - zs[1];
      result[0] = tmp;
      split(a[1], xs);
      split(b[0], ys);
      splitMult(xs, ys, zs);
      tmp = result[0] + zs[0];
      result[1] -= tmp - result[0] - zs[0];
      result[0] = tmp;
      tmp = result[0] + zs[1];
      result[1] -= tmp - result[0] - zs[1];
      result[0] = tmp;
      split(a[1], xs);
      split(b[1], ys);
      splitMult(xs, ys, zs);
      tmp = result[0] + zs[0];
      result[1] -= tmp - result[0] - zs[0];
      result[0] = tmp;
      tmp = result[0] + zs[1];
      result[1] -= tmp - result[0] - zs[1];
      result[0] = tmp;
   }

   static double expint(int p, double[] result) {
      double[] xs = new double[2];
      double[] as = new double[2];
      double[] ys = new double[2];
      xs[0] = 2.718281828459045D;
      xs[1] = 1.4456468917292502E-16D;
      split(1.0D, ys);

      while(p > 0) {
         if ((p & 1) != 0) {
            quadMult(ys, xs, as);
            ys[0] = as[0];
            ys[1] = as[1];
         }

         quadMult(xs, xs, as);
         xs[0] = as[0];
         xs[1] = as[1];
         p >>= 1;
      }

      if (result != null) {
         result[0] = ys[0];
         result[1] = ys[1];
         resplit(result);
      }

      return ys[0] + ys[1];
   }

   static double[] slowLog(double xi) {
      double[] x = new double[2];
      double[] x2 = new double[2];
      double[] y = new double[2];
      double[] a = new double[2];
      split(xi, x);
      int var10002 = x[0]++;
      resplit(x);
      splitReciprocal(x, a);
      x[0] -= 2.0D;
      resplit(x);
      splitMult(x, a, y);
      x[0] = y[0];
      x[1] = y[1];
      splitMult(x, x, x2);
      y[0] = LN_SPLIT_COEF[LN_SPLIT_COEF.length - 1][0];
      y[1] = LN_SPLIT_COEF[LN_SPLIT_COEF.length - 1][1];

      for(int i = LN_SPLIT_COEF.length - 2; i >= 0; --i) {
         splitMult(y, x2, a);
         y[0] = a[0];
         y[1] = a[1];
         splitAdd(y, LN_SPLIT_COEF[i], a);
         y[0] = a[0];
         y[1] = a[1];
      }

      splitMult(y, x, a);
      y[0] = a[0];
      y[1] = a[1];
      return y;
   }

   static void printarray(PrintStream out, String name, int expectedLen, double[][] array2d) {
      out.println(name);
      checkLen(expectedLen, array2d.length);
      out.println("    { ");
      int i = 0;
      double[][] arr$ = array2d;
      int len$ = array2d.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         double[] array = arr$[i$];
         out.print("        {");
         double[] arr$ = array;
         int len$ = array.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            double d = arr$[i$];
            out.printf("%-25.25s", format(d));
         }

         out.println("}, // " + i++);
      }

      out.println("    };");
   }

   static void printarray(PrintStream out, String name, int expectedLen, double[] array) {
      out.println(name + "=");
      checkLen(expectedLen, array.length);
      out.println("    {");
      double[] arr$ = array;
      int len$ = array.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         double d = arr$[i$];
         out.printf("        %s%n", format(d));
      }

      out.println("    };");
   }

   static String format(double d) {
      return d != d ? "Double.NaN," : (d >= 0.0D ? "+" : "") + Double.toString(d) + "d,";
   }

   private static void checkLen(int expectedLen, int actual) throws DimensionMismatchException {
      if (expectedLen != actual) {
         throw new DimensionMismatchException(actual, expectedLen);
      }
   }
}
