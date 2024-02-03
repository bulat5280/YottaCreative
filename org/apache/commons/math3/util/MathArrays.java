package org.apache.commons.math3.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.apache.commons.math3.Field;
import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.MathArithmeticException;
import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.exception.MathInternalError;
import org.apache.commons.math3.exception.NonMonotonicSequenceException;
import org.apache.commons.math3.exception.NotPositiveException;
import org.apache.commons.math3.exception.NotStrictlyPositiveException;
import org.apache.commons.math3.exception.NullArgumentException;
import org.apache.commons.math3.exception.util.LocalizedFormats;

public class MathArrays {
   private static final int SPLIT_FACTOR = 134217729;

   private MathArrays() {
   }

   public static double[] scale(double val, double[] arr) {
      double[] newArr = new double[arr.length];

      for(int i = 0; i < arr.length; ++i) {
         newArr[i] = arr[i] * val;
      }

      return newArr;
   }

   public static void scaleInPlace(double val, double[] arr) {
      for(int i = 0; i < arr.length; ++i) {
         arr[i] *= val;
      }

   }

   public static double[] ebeAdd(double[] a, double[] b) throws DimensionMismatchException {
      if (a.length != b.length) {
         throw new DimensionMismatchException(a.length, b.length);
      } else {
         double[] result = (double[])a.clone();

         for(int i = 0; i < a.length; ++i) {
            result[i] += b[i];
         }

         return result;
      }
   }

   public static double[] ebeSubtract(double[] a, double[] b) throws DimensionMismatchException {
      if (a.length != b.length) {
         throw new DimensionMismatchException(a.length, b.length);
      } else {
         double[] result = (double[])a.clone();

         for(int i = 0; i < a.length; ++i) {
            result[i] -= b[i];
         }

         return result;
      }
   }

   public static double[] ebeMultiply(double[] a, double[] b) throws DimensionMismatchException {
      if (a.length != b.length) {
         throw new DimensionMismatchException(a.length, b.length);
      } else {
         double[] result = (double[])a.clone();

         for(int i = 0; i < a.length; ++i) {
            result[i] *= b[i];
         }

         return result;
      }
   }

   public static double[] ebeDivide(double[] a, double[] b) throws DimensionMismatchException {
      if (a.length != b.length) {
         throw new DimensionMismatchException(a.length, b.length);
      } else {
         double[] result = (double[])a.clone();

         for(int i = 0; i < a.length; ++i) {
            result[i] /= b[i];
         }

         return result;
      }
   }

   public static double distance1(double[] p1, double[] p2) {
      double sum = 0.0D;

      for(int i = 0; i < p1.length; ++i) {
         sum += FastMath.abs(p1[i] - p2[i]);
      }

      return sum;
   }

   public static int distance1(int[] p1, int[] p2) {
      int sum = 0;

      for(int i = 0; i < p1.length; ++i) {
         sum += FastMath.abs(p1[i] - p2[i]);
      }

      return sum;
   }

   public static double distance(double[] p1, double[] p2) {
      double sum = 0.0D;

      for(int i = 0; i < p1.length; ++i) {
         double dp = p1[i] - p2[i];
         sum += dp * dp;
      }

      return FastMath.sqrt(sum);
   }

   public static double distance(int[] p1, int[] p2) {
      double sum = 0.0D;

      for(int i = 0; i < p1.length; ++i) {
         double dp = (double)(p1[i] - p2[i]);
         sum += dp * dp;
      }

      return FastMath.sqrt(sum);
   }

   public static double distanceInf(double[] p1, double[] p2) {
      double max = 0.0D;

      for(int i = 0; i < p1.length; ++i) {
         max = FastMath.max(max, FastMath.abs(p1[i] - p2[i]));
      }

      return max;
   }

   public static int distanceInf(int[] p1, int[] p2) {
      int max = 0;

      for(int i = 0; i < p1.length; ++i) {
         max = FastMath.max(max, FastMath.abs(p1[i] - p2[i]));
      }

      return max;
   }

   public static <T extends Comparable<? super T>> boolean isMonotonic(T[] val, MathArrays.OrderDirection dir, boolean strict) {
      T previous = val[0];
      int max = val.length;

      for(int i = 1; i < max; ++i) {
         int comp;
         switch(dir) {
         case INCREASING:
            comp = previous.compareTo(val[i]);
            if (strict) {
               if (comp >= 0) {
                  return false;
               }
            } else if (comp > 0) {
               return false;
            }
            break;
         case DECREASING:
            comp = val[i].compareTo(previous);
            if (strict) {
               if (comp >= 0) {
                  return false;
               }
            } else if (comp > 0) {
               return false;
            }
            break;
         default:
            throw new MathInternalError();
         }

         previous = val[i];
      }

      return true;
   }

   public static boolean isMonotonic(double[] val, MathArrays.OrderDirection dir, boolean strict) {
      return checkOrder(val, dir, strict, false);
   }

   public static boolean checkOrder(double[] val, MathArrays.OrderDirection dir, boolean strict, boolean abort) throws NonMonotonicSequenceException {
      double previous = val[0];
      int max = val.length;

      int index;
      label40:
      for(index = 1; index < max; ++index) {
         switch(dir) {
         case INCREASING:
            if (strict) {
               if (val[index] <= previous) {
                  break label40;
               }
            } else if (val[index] < previous) {
               break label40;
            }
            break;
         case DECREASING:
            if (strict) {
               if (val[index] >= previous) {
                  break label40;
               }
            } else if (val[index] > previous) {
               break label40;
            }
            break;
         default:
            throw new MathInternalError();
         }

         previous = val[index];
      }

      if (index == max) {
         return true;
      } else if (abort) {
         throw new NonMonotonicSequenceException(val[index], previous, index, dir, strict);
      } else {
         return false;
      }
   }

   public static void checkOrder(double[] val, MathArrays.OrderDirection dir, boolean strict) throws NonMonotonicSequenceException {
      checkOrder(val, dir, strict, true);
   }

   public static void checkOrder(double[] val) throws NonMonotonicSequenceException {
      checkOrder(val, MathArrays.OrderDirection.INCREASING, true);
   }

   public static void checkRectangular(long[][] in) throws NullArgumentException, DimensionMismatchException {
      MathUtils.checkNotNull(in);

      for(int i = 1; i < in.length; ++i) {
         if (in[i].length != in[0].length) {
            throw new DimensionMismatchException(LocalizedFormats.DIFFERENT_ROWS_LENGTHS, in[i].length, in[0].length);
         }
      }

   }

   public static void checkPositive(double[] in) throws NotStrictlyPositiveException {
      for(int i = 0; i < in.length; ++i) {
         if (in[i] <= 0.0D) {
            throw new NotStrictlyPositiveException(in[i]);
         }
      }

   }

   public static void checkNonNegative(long[] in) throws NotPositiveException {
      for(int i = 0; i < in.length; ++i) {
         if (in[i] < 0L) {
            throw new NotPositiveException(in[i]);
         }
      }

   }

   public static void checkNonNegative(long[][] in) throws NotPositiveException {
      for(int i = 0; i < in.length; ++i) {
         for(int j = 0; j < in[i].length; ++j) {
            if (in[i][j] < 0L) {
               throw new NotPositiveException(in[i][j]);
            }
         }
      }

   }

   public static double safeNorm(double[] v) {
      double rdwarf = 3.834E-20D;
      double rgiant = 1.304E19D;
      double s1 = 0.0D;
      double s2 = 0.0D;
      double s3 = 0.0D;
      double x1max = 0.0D;
      double x3max = 0.0D;
      double floatn = (double)v.length;
      double agiant = rgiant / floatn;

      for(int i = 0; i < v.length; ++i) {
         double xabs = Math.abs(v[i]);
         if (!(xabs < rdwarf) && !(xabs > agiant)) {
            s2 += xabs * xabs;
         } else {
            double r;
            if (xabs > rdwarf) {
               if (xabs > x1max) {
                  r = x1max / xabs;
                  s1 = 1.0D + s1 * r * r;
                  x1max = xabs;
               } else {
                  r = xabs / x1max;
                  s1 += r * r;
               }
            } else if (xabs > x3max) {
               r = x3max / xabs;
               s3 = 1.0D + s3 * r * r;
               x3max = xabs;
            } else if (xabs != 0.0D) {
               r = xabs / x3max;
               s3 += r * r;
            }
         }
      }

      double norm;
      if (s1 != 0.0D) {
         norm = x1max * Math.sqrt(s1 + s2 / x1max / x1max);
      } else if (s2 == 0.0D) {
         norm = x3max * Math.sqrt(s3);
      } else if (s2 >= x3max) {
         norm = Math.sqrt(s2 * (1.0D + x3max / s2 * x3max * s3));
      } else {
         norm = Math.sqrt(x3max * (s2 / x3max + x3max * s3));
      }

      return norm;
   }

   public static void sortInPlace(double[] x, double[]... yList) throws DimensionMismatchException, NullArgumentException {
      sortInPlace(x, MathArrays.OrderDirection.INCREASING, yList);
   }

   public static void sortInPlace(double[] x, final MathArrays.OrderDirection dir, double[]... yList) throws NullArgumentException, DimensionMismatchException {
      if (x == null) {
         throw new NullArgumentException();
      } else {
         int len = x.length;
         List<Pair<Double, double[]>> list = new ArrayList(len);
         int yListLen = yList.length;

         double[] y;
         for(int i = 0; i < len; ++i) {
            double[] yValues = new double[yListLen];

            for(int j = 0; j < yListLen; ++j) {
               y = yList[j];
               if (y == null) {
                  throw new NullArgumentException();
               }

               if (y.length != len) {
                  throw new DimensionMismatchException(y.length, len);
               }

               yValues[j] = y[i];
            }

            list.add(new Pair(x[i], yValues));
         }

         Comparator<Pair<Double, double[]>> comp = new Comparator<Pair<Double, double[]>>() {
            public int compare(Pair<Double, double[]> o1, Pair<Double, double[]> o2) {
               int val;
               switch(dir) {
               case INCREASING:
                  val = ((Double)o1.getKey()).compareTo((Double)o2.getKey());
                  break;
               case DECREASING:
                  val = ((Double)o2.getKey()).compareTo((Double)o1.getKey());
                  break;
               default:
                  throw new MathInternalError();
               }

               return val;
            }
         };
         Collections.sort(list, comp);

         for(int i = 0; i < len; ++i) {
            Pair<Double, double[]> e = (Pair)list.get(i);
            x[i] = (Double)e.getKey();
            y = (double[])e.getValue();

            for(int j = 0; j < yListLen; ++j) {
               yList[j][i] = y[j];
            }
         }

      }
   }

   public static int[] copyOf(int[] source) {
      return copyOf(source, source.length);
   }

   public static double[] copyOf(double[] source) {
      return copyOf(source, source.length);
   }

   public static int[] copyOf(int[] source, int len) {
      int[] output = new int[len];
      System.arraycopy(source, 0, output, 0, FastMath.min(len, source.length));
      return output;
   }

   public static double[] copyOf(double[] source, int len) {
      double[] output = new double[len];
      System.arraycopy(source, 0, output, 0, FastMath.min(len, source.length));
      return output;
   }

   public static double linearCombination(double[] a, double[] b) throws DimensionMismatchException {
      int len = a.length;
      if (len != b.length) {
         throw new DimensionMismatchException(len, b.length);
      } else {
         double[] prodHigh = new double[len];
         double prodLowSum = 0.0D;

         double result;
         for(int i = 0; i < len; ++i) {
            double ai = a[i];
            double ca = 1.34217729E8D * ai;
            double aHigh = ca - (ca - ai);
            double aLow = ai - aHigh;
            double bi = b[i];
            result = 1.34217729E8D * bi;
            double bHigh = result - (result - bi);
            double bLow = bi - bHigh;
            prodHigh[i] = ai * bi;
            double prodLow = aLow * bLow - (prodHigh[i] - aHigh * bHigh - aLow * bHigh - aHigh * bLow);
            prodLowSum += prodLow;
         }

         double prodHighCur = prodHigh[0];
         double prodHighNext = prodHigh[1];
         double sHighPrev = prodHighCur + prodHighNext;
         double sPrime = sHighPrev - prodHighNext;
         double sLowSum = prodHighNext - (sHighPrev - sPrime) + (prodHighCur - sPrime);
         int lenMinusOne = len - 1;

         for(int i = 1; i < lenMinusOne; ++i) {
            prodHighNext = prodHigh[i + 1];
            double sHighCur = sHighPrev + prodHighNext;
            sPrime = sHighCur - prodHighNext;
            sLowSum += prodHighNext - (sHighCur - sPrime) + (sHighPrev - sPrime);
            sHighPrev = sHighCur;
         }

         result = sHighPrev + prodLowSum + sLowSum;
         if (Double.isNaN(result)) {
            result = 0.0D;

            for(int i = 0; i < len; ++i) {
               result += a[i] * b[i];
            }
         }

         return result;
      }
   }

   public static double linearCombination(double a1, double b1, double a2, double b2) {
      double ca1 = 1.34217729E8D * a1;
      double a1High = ca1 - (ca1 - a1);
      double a1Low = a1 - a1High;
      double cb1 = 1.34217729E8D * b1;
      double b1High = cb1 - (cb1 - b1);
      double b1Low = b1 - b1High;
      double prod1High = a1 * b1;
      double prod1Low = a1Low * b1Low - (prod1High - a1High * b1High - a1Low * b1High - a1High * b1Low);
      double ca2 = 1.34217729E8D * a2;
      double a2High = ca2 - (ca2 - a2);
      double a2Low = a2 - a2High;
      double cb2 = 1.34217729E8D * b2;
      double b2High = cb2 - (cb2 - b2);
      double b2Low = b2 - b2High;
      double prod2High = a2 * b2;
      double prod2Low = a2Low * b2Low - (prod2High - a2High * b2High - a2Low * b2High - a2High * b2Low);
      double s12High = prod1High + prod2High;
      double s12Prime = s12High - prod2High;
      double s12Low = prod2High - (s12High - s12Prime) + (prod1High - s12Prime);
      double result = s12High + prod1Low + prod2Low + s12Low;
      if (Double.isNaN(result)) {
         result = a1 * b1 + a2 * b2;
      }

      return result;
   }

   public static double linearCombination(double a1, double b1, double a2, double b2, double a3, double b3) {
      double ca1 = 1.34217729E8D * a1;
      double a1High = ca1 - (ca1 - a1);
      double a1Low = a1 - a1High;
      double cb1 = 1.34217729E8D * b1;
      double b1High = cb1 - (cb1 - b1);
      double b1Low = b1 - b1High;
      double prod1High = a1 * b1;
      double prod1Low = a1Low * b1Low - (prod1High - a1High * b1High - a1Low * b1High - a1High * b1Low);
      double ca2 = 1.34217729E8D * a2;
      double a2High = ca2 - (ca2 - a2);
      double a2Low = a2 - a2High;
      double cb2 = 1.34217729E8D * b2;
      double b2High = cb2 - (cb2 - b2);
      double b2Low = b2 - b2High;
      double prod2High = a2 * b2;
      double prod2Low = a2Low * b2Low - (prod2High - a2High * b2High - a2Low * b2High - a2High * b2Low);
      double ca3 = 1.34217729E8D * a3;
      double a3High = ca3 - (ca3 - a3);
      double a3Low = a3 - a3High;
      double cb3 = 1.34217729E8D * b3;
      double b3High = cb3 - (cb3 - b3);
      double b3Low = b3 - b3High;
      double prod3High = a3 * b3;
      double prod3Low = a3Low * b3Low - (prod3High - a3High * b3High - a3Low * b3High - a3High * b3Low);
      double s12High = prod1High + prod2High;
      double s12Prime = s12High - prod2High;
      double s12Low = prod2High - (s12High - s12Prime) + (prod1High - s12Prime);
      double s123High = s12High + prod3High;
      double s123Prime = s123High - prod3High;
      double s123Low = prod3High - (s123High - s123Prime) + (s12High - s123Prime);
      double result = s123High + prod1Low + prod2Low + prod3Low + s12Low + s123Low;
      if (Double.isNaN(result)) {
         result = a1 * b1 + a2 * b2 + a3 * b3;
      }

      return result;
   }

   public static double linearCombination(double a1, double b1, double a2, double b2, double a3, double b3, double a4, double b4) {
      double ca1 = 1.34217729E8D * a1;
      double a1High = ca1 - (ca1 - a1);
      double a1Low = a1 - a1High;
      double cb1 = 1.34217729E8D * b1;
      double b1High = cb1 - (cb1 - b1);
      double b1Low = b1 - b1High;
      double prod1High = a1 * b1;
      double prod1Low = a1Low * b1Low - (prod1High - a1High * b1High - a1Low * b1High - a1High * b1Low);
      double ca2 = 1.34217729E8D * a2;
      double a2High = ca2 - (ca2 - a2);
      double a2Low = a2 - a2High;
      double cb2 = 1.34217729E8D * b2;
      double b2High = cb2 - (cb2 - b2);
      double b2Low = b2 - b2High;
      double prod2High = a2 * b2;
      double prod2Low = a2Low * b2Low - (prod2High - a2High * b2High - a2Low * b2High - a2High * b2Low);
      double ca3 = 1.34217729E8D * a3;
      double a3High = ca3 - (ca3 - a3);
      double a3Low = a3 - a3High;
      double cb3 = 1.34217729E8D * b3;
      double b3High = cb3 - (cb3 - b3);
      double b3Low = b3 - b3High;
      double prod3High = a3 * b3;
      double prod3Low = a3Low * b3Low - (prod3High - a3High * b3High - a3Low * b3High - a3High * b3Low);
      double ca4 = 1.34217729E8D * a4;
      double a4High = ca4 - (ca4 - a4);
      double a4Low = a4 - a4High;
      double cb4 = 1.34217729E8D * b4;
      double b4High = cb4 - (cb4 - b4);
      double b4Low = b4 - b4High;
      double prod4High = a4 * b4;
      double prod4Low = a4Low * b4Low - (prod4High - a4High * b4High - a4Low * b4High - a4High * b4Low);
      double s12High = prod1High + prod2High;
      double s12Prime = s12High - prod2High;
      double s12Low = prod2High - (s12High - s12Prime) + (prod1High - s12Prime);
      double s123High = s12High + prod3High;
      double s123Prime = s123High - prod3High;
      double s123Low = prod3High - (s123High - s123Prime) + (s12High - s123Prime);
      double s1234High = s123High + prod4High;
      double s1234Prime = s1234High - prod4High;
      double s1234Low = prod4High - (s1234High - s1234Prime) + (s123High - s1234Prime);
      double result = s1234High + prod1Low + prod2Low + prod3Low + prod4Low + s12Low + s123Low + s1234Low;
      if (Double.isNaN(result)) {
         result = a1 * b1 + a2 * b2 + a3 * b3 + a4 * b4;
      }

      return result;
   }

   public static boolean equals(float[] x, float[] y) {
      if (x != null && y != null) {
         if (x.length != y.length) {
            return false;
         } else {
            for(int i = 0; i < x.length; ++i) {
               if (!Precision.equals(x[i], y[i])) {
                  return false;
               }
            }

            return true;
         }
      } else {
         return !(x == null ^ y == null);
      }
   }

   public static boolean equalsIncludingNaN(float[] x, float[] y) {
      if (x != null && y != null) {
         if (x.length != y.length) {
            return false;
         } else {
            for(int i = 0; i < x.length; ++i) {
               if (!Precision.equalsIncludingNaN(x[i], y[i])) {
                  return false;
               }
            }

            return true;
         }
      } else {
         return !(x == null ^ y == null);
      }
   }

   public static boolean equals(double[] x, double[] y) {
      if (x != null && y != null) {
         if (x.length != y.length) {
            return false;
         } else {
            for(int i = 0; i < x.length; ++i) {
               if (!Precision.equals(x[i], y[i])) {
                  return false;
               }
            }

            return true;
         }
      } else {
         return !(x == null ^ y == null);
      }
   }

   public static boolean equalsIncludingNaN(double[] x, double[] y) {
      if (x != null && y != null) {
         if (x.length != y.length) {
            return false;
         } else {
            for(int i = 0; i < x.length; ++i) {
               if (!Precision.equalsIncludingNaN(x[i], y[i])) {
                  return false;
               }
            }

            return true;
         }
      } else {
         return !(x == null ^ y == null);
      }
   }

   public static double[] normalizeArray(double[] values, double normalizedSum) throws MathIllegalArgumentException, MathArithmeticException {
      if (Double.isInfinite(normalizedSum)) {
         throw new MathIllegalArgumentException(LocalizedFormats.NORMALIZE_INFINITE, new Object[0]);
      } else if (Double.isNaN(normalizedSum)) {
         throw new MathIllegalArgumentException(LocalizedFormats.NORMALIZE_NAN, new Object[0]);
      } else {
         double sum = 0.0D;
         int len = values.length;
         double[] out = new double[len];

         int i;
         for(i = 0; i < len; ++i) {
            if (Double.isInfinite(values[i])) {
               throw new MathIllegalArgumentException(LocalizedFormats.INFINITE_ARRAY_ELEMENT, new Object[]{values[i], i});
            }

            if (!Double.isNaN(values[i])) {
               sum += values[i];
            }
         }

         if (sum == 0.0D) {
            throw new MathArithmeticException(LocalizedFormats.ARRAY_SUMS_TO_ZERO, new Object[0]);
         } else {
            for(i = 0; i < len; ++i) {
               if (Double.isNaN(values[i])) {
                  out[i] = Double.NaN;
               } else {
                  out[i] = values[i] * normalizedSum / sum;
               }
            }

            return out;
         }
      }
   }

   public static <T> T[] buildArray(Field<T> field, int length) {
      T[] array = (Object[])((Object[])Array.newInstance(field.getRuntimeClass(), length));
      Arrays.fill(array, field.getZero());
      return array;
   }

   public static <T> T[][] buildArray(Field<T> field, int rows, int columns) {
      Object[][] array;
      if (columns < 0) {
         T[] dummyRow = buildArray(field, 0);
         array = (Object[][])((Object[][])Array.newInstance(dummyRow.getClass(), rows));
      } else {
         array = (Object[][])((Object[][])Array.newInstance(field.getRuntimeClass(), new int[]{rows, columns}));

         for(int i = 0; i < rows; ++i) {
            Arrays.fill(array[i], field.getZero());
         }
      }

      return array;
   }

   public static enum OrderDirection {
      INCREASING,
      DECREASING;
   }

   public interface Function {
      double evaluate(double[] var1);

      double evaluate(double[] var1, int var2, int var3);
   }
}
