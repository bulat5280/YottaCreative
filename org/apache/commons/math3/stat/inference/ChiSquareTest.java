package org.apache.commons.math3.stat.inference;

import org.apache.commons.math3.distribution.ChiSquaredDistribution;
import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.MaxCountExceededException;
import org.apache.commons.math3.exception.NotPositiveException;
import org.apache.commons.math3.exception.NotStrictlyPositiveException;
import org.apache.commons.math3.exception.NullArgumentException;
import org.apache.commons.math3.exception.OutOfRangeException;
import org.apache.commons.math3.exception.ZeroException;
import org.apache.commons.math3.exception.util.LocalizedFormats;
import org.apache.commons.math3.util.FastMath;
import org.apache.commons.math3.util.MathArrays;

public class ChiSquareTest {
   public double chiSquare(double[] expected, long[] observed) throws NotPositiveException, NotStrictlyPositiveException, DimensionMismatchException {
      if (expected.length < 2) {
         throw new DimensionMismatchException(expected.length, 2);
      } else if (expected.length != observed.length) {
         throw new DimensionMismatchException(expected.length, observed.length);
      } else {
         MathArrays.checkPositive(expected);
         MathArrays.checkNonNegative(observed);
         double sumExpected = 0.0D;
         double sumObserved = 0.0D;

         for(int i = 0; i < observed.length; ++i) {
            sumExpected += expected[i];
            sumObserved += (double)observed[i];
         }

         double ratio = 1.0D;
         boolean rescale = false;
         if (FastMath.abs(sumExpected - sumObserved) > 1.0E-5D) {
            ratio = sumObserved / sumExpected;
            rescale = true;
         }

         double sumSq = 0.0D;

         for(int i = 0; i < observed.length; ++i) {
            double dev;
            if (rescale) {
               dev = (double)observed[i] - ratio * expected[i];
               sumSq += dev * dev / (ratio * expected[i]);
            } else {
               dev = (double)observed[i] - expected[i];
               sumSq += dev * dev / expected[i];
            }
         }

         return sumSq;
      }
   }

   public double chiSquareTest(double[] expected, long[] observed) throws NotPositiveException, NotStrictlyPositiveException, DimensionMismatchException, MaxCountExceededException {
      ChiSquaredDistribution distribution = new ChiSquaredDistribution((double)expected.length - 1.0D);
      return 1.0D - distribution.cumulativeProbability(this.chiSquare(expected, observed));
   }

   public boolean chiSquareTest(double[] expected, long[] observed, double alpha) throws NotPositiveException, NotStrictlyPositiveException, DimensionMismatchException, OutOfRangeException, MaxCountExceededException {
      if (!(alpha <= 0.0D) && !(alpha > 0.5D)) {
         return this.chiSquareTest(expected, observed) < alpha;
      } else {
         throw new OutOfRangeException(LocalizedFormats.OUT_OF_BOUND_SIGNIFICANCE_LEVEL, alpha, 0, 0.5D);
      }
   }

   public double chiSquare(long[][] counts) throws NullArgumentException, NotPositiveException, DimensionMismatchException {
      this.checkArray(counts);
      int nRows = counts.length;
      int nCols = counts[0].length;
      double[] rowSum = new double[nRows];
      double[] colSum = new double[nCols];
      double total = 0.0D;

      for(int row = 0; row < nRows; ++row) {
         for(int col = 0; col < nCols; ++col) {
            rowSum[row] += (double)counts[row][col];
            colSum[col] += (double)counts[row][col];
            total += (double)counts[row][col];
         }
      }

      double sumSq = 0.0D;
      double expected = 0.0D;

      for(int row = 0; row < nRows; ++row) {
         for(int col = 0; col < nCols; ++col) {
            expected = rowSum[row] * colSum[col] / total;
            sumSq += ((double)counts[row][col] - expected) * ((double)counts[row][col] - expected) / expected;
         }
      }

      return sumSq;
   }

   public double chiSquareTest(long[][] counts) throws NullArgumentException, DimensionMismatchException, NotPositiveException, MaxCountExceededException {
      this.checkArray(counts);
      double df = ((double)counts.length - 1.0D) * ((double)counts[0].length - 1.0D);
      ChiSquaredDistribution distribution = new ChiSquaredDistribution(df);
      return 1.0D - distribution.cumulativeProbability(this.chiSquare(counts));
   }

   public boolean chiSquareTest(long[][] counts, double alpha) throws NullArgumentException, DimensionMismatchException, NotPositiveException, OutOfRangeException, MaxCountExceededException {
      if (!(alpha <= 0.0D) && !(alpha > 0.5D)) {
         return this.chiSquareTest(counts) < alpha;
      } else {
         throw new OutOfRangeException(LocalizedFormats.OUT_OF_BOUND_SIGNIFICANCE_LEVEL, alpha, 0, 0.5D);
      }
   }

   public double chiSquareDataSetsComparison(long[] observed1, long[] observed2) throws DimensionMismatchException, NotPositiveException, ZeroException {
      if (observed1.length < 2) {
         throw new DimensionMismatchException(observed1.length, 2);
      } else if (observed1.length != observed2.length) {
         throw new DimensionMismatchException(observed1.length, observed2.length);
      } else {
         MathArrays.checkNonNegative(observed1);
         MathArrays.checkNonNegative(observed2);
         long countSum1 = 0L;
         long countSum2 = 0L;
         boolean unequalCounts = false;
         double weight = 0.0D;

         for(int i = 0; i < observed1.length; ++i) {
            countSum1 += observed1[i];
            countSum2 += observed2[i];
         }

         if (countSum1 != 0L && countSum2 != 0L) {
            unequalCounts = countSum1 != countSum2;
            if (unequalCounts) {
               weight = FastMath.sqrt((double)countSum1 / (double)countSum2);
            }

            double sumSq = 0.0D;
            double dev = 0.0D;
            double obs1 = 0.0D;
            double obs2 = 0.0D;

            for(int i = 0; i < observed1.length; ++i) {
               if (observed1[i] == 0L && observed2[i] == 0L) {
                  throw new ZeroException(LocalizedFormats.OBSERVED_COUNTS_BOTTH_ZERO_FOR_ENTRY, new Object[]{i});
               }

               obs1 = (double)observed1[i];
               obs2 = (double)observed2[i];
               if (unequalCounts) {
                  dev = obs1 / weight - obs2 * weight;
               } else {
                  dev = obs1 - obs2;
               }

               sumSq += dev * dev / (obs1 + obs2);
            }

            return sumSq;
         } else {
            throw new ZeroException();
         }
      }
   }

   public double chiSquareTestDataSetsComparison(long[] observed1, long[] observed2) throws DimensionMismatchException, NotPositiveException, ZeroException, MaxCountExceededException {
      ChiSquaredDistribution distribution = new ChiSquaredDistribution((double)observed1.length - 1.0D);
      return 1.0D - distribution.cumulativeProbability(this.chiSquareDataSetsComparison(observed1, observed2));
   }

   public boolean chiSquareTestDataSetsComparison(long[] observed1, long[] observed2, double alpha) throws DimensionMismatchException, NotPositiveException, ZeroException, OutOfRangeException, MaxCountExceededException {
      if (!(alpha <= 0.0D) && !(alpha > 0.5D)) {
         return this.chiSquareTestDataSetsComparison(observed1, observed2) < alpha;
      } else {
         throw new OutOfRangeException(LocalizedFormats.OUT_OF_BOUND_SIGNIFICANCE_LEVEL, alpha, 0, 0.5D);
      }
   }

   private void checkArray(long[][] in) throws NullArgumentException, DimensionMismatchException, NotPositiveException {
      if (in.length < 2) {
         throw new DimensionMismatchException(in.length, 2);
      } else if (in[0].length < 2) {
         throw new DimensionMismatchException(in[0].length, 2);
      } else {
         MathArrays.checkRectangular(in);
         MathArrays.checkNonNegative(in);
      }
   }
}
