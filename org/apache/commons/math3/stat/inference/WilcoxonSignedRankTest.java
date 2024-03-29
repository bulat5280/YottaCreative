package org.apache.commons.math3.stat.inference;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.exception.ConvergenceException;
import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.MaxCountExceededException;
import org.apache.commons.math3.exception.NoDataException;
import org.apache.commons.math3.exception.NullArgumentException;
import org.apache.commons.math3.exception.NumberIsTooLargeException;
import org.apache.commons.math3.stat.ranking.NaNStrategy;
import org.apache.commons.math3.stat.ranking.NaturalRanking;
import org.apache.commons.math3.stat.ranking.TiesStrategy;
import org.apache.commons.math3.util.FastMath;

public class WilcoxonSignedRankTest {
   private NaturalRanking naturalRanking;

   public WilcoxonSignedRankTest() {
      this.naturalRanking = new NaturalRanking(NaNStrategy.FIXED, TiesStrategy.AVERAGE);
   }

   public WilcoxonSignedRankTest(NaNStrategy nanStrategy, TiesStrategy tiesStrategy) {
      this.naturalRanking = new NaturalRanking(nanStrategy, tiesStrategy);
   }

   private void ensureDataConformance(double[] x, double[] y) throws NullArgumentException, NoDataException, DimensionMismatchException {
      if (x != null && y != null) {
         if (x.length != 0 && y.length != 0) {
            if (y.length != x.length) {
               throw new DimensionMismatchException(y.length, x.length);
            }
         } else {
            throw new NoDataException();
         }
      } else {
         throw new NullArgumentException();
      }
   }

   private double[] calculateDifferences(double[] x, double[] y) {
      double[] z = new double[x.length];

      for(int i = 0; i < x.length; ++i) {
         z[i] = y[i] - x[i];
      }

      return z;
   }

   private double[] calculateAbsoluteDifferences(double[] z) throws NullArgumentException, NoDataException {
      if (z == null) {
         throw new NullArgumentException();
      } else if (z.length == 0) {
         throw new NoDataException();
      } else {
         double[] zAbs = new double[z.length];

         for(int i = 0; i < z.length; ++i) {
            zAbs[i] = FastMath.abs(z[i]);
         }

         return zAbs;
      }
   }

   public double wilcoxonSignedRank(double[] x, double[] y) throws NullArgumentException, NoDataException, DimensionMismatchException {
      this.ensureDataConformance(x, y);
      double[] z = this.calculateDifferences(x, y);
      double[] zAbs = this.calculateAbsoluteDifferences(z);
      double[] ranks = this.naturalRanking.rank(zAbs);
      double Wplus = 0.0D;

      int i;
      for(i = 0; i < z.length; ++i) {
         if (z[i] > 0.0D) {
            Wplus += ranks[i];
         }
      }

      i = x.length;
      double Wminus = (double)(i * (i + 1)) / 2.0D - Wplus;
      return FastMath.max(Wplus, Wminus);
   }

   private double calculateExactPValue(double Wmax, int N) {
      int m = 1 << N;
      int largerRankSums = 0;

      for(int i = 0; i < m; ++i) {
         int rankSum = 0;

         for(int j = 0; j < N; ++j) {
            if ((i >> j & 1) == 1) {
               rankSum += j + 1;
            }
         }

         if ((double)rankSum >= Wmax) {
            ++largerRankSums;
         }
      }

      return 2.0D * (double)largerRankSums / (double)m;
   }

   private double calculateAsymptoticPValue(double Wmin, int N) {
      double ES = (double)(N * (N + 1)) / 4.0D;
      double VarS = ES * ((double)(2 * N + 1) / 6.0D);
      double z = (Wmin - ES - 0.5D) / FastMath.sqrt(VarS);
      NormalDistribution standardNormal = new NormalDistribution(0.0D, 1.0D);
      return 2.0D * standardNormal.cumulativeProbability(z);
   }

   public double wilcoxonSignedRankTest(double[] x, double[] y, boolean exactPValue) throws NullArgumentException, NoDataException, DimensionMismatchException, NumberIsTooLargeException, ConvergenceException, MaxCountExceededException {
      this.ensureDataConformance(x, y);
      int N = x.length;
      double Wmax = this.wilcoxonSignedRank(x, y);
      if (exactPValue && N > 30) {
         throw new NumberIsTooLargeException(N, 30, true);
      } else if (exactPValue) {
         return this.calculateExactPValue(Wmax, N);
      } else {
         double Wmin = (double)(N * (N + 1)) / 2.0D - Wmax;
         return this.calculateAsymptoticPValue(Wmin, N);
      }
   }
}
