package org.apache.commons.math3.stat.inference;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import org.apache.commons.math3.distribution.FDistribution;
import org.apache.commons.math3.exception.ConvergenceException;
import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.MaxCountExceededException;
import org.apache.commons.math3.exception.NullArgumentException;
import org.apache.commons.math3.exception.OutOfRangeException;
import org.apache.commons.math3.exception.util.LocalizedFormats;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;
import org.apache.commons.math3.util.MathUtils;

public class OneWayAnova {
   public double anovaFValue(Collection<double[]> categoryData) throws NullArgumentException, DimensionMismatchException {
      OneWayAnova.AnovaStats a = this.anovaStats(categoryData);
      return a.F;
   }

   public double anovaPValue(Collection<double[]> categoryData) throws NullArgumentException, DimensionMismatchException, ConvergenceException, MaxCountExceededException {
      OneWayAnova.AnovaStats a = this.anovaStats(categoryData);
      FDistribution fdist = new FDistribution((double)a.dfbg, (double)a.dfwg);
      return 1.0D - fdist.cumulativeProbability(a.F);
   }

   public double anovaPValue(Collection<SummaryStatistics> categoryData, boolean allowOneElementData) throws NullArgumentException, DimensionMismatchException, ConvergenceException, MaxCountExceededException {
      OneWayAnova.AnovaStats a = this.anovaStats(categoryData, allowOneElementData);
      FDistribution fdist = new FDistribution((double)a.dfbg, (double)a.dfwg);
      return 1.0D - fdist.cumulativeProbability(a.F);
   }

   private OneWayAnova.AnovaStats anovaStats(Collection<double[]> categoryData) throws NullArgumentException, DimensionMismatchException {
      MathUtils.checkNotNull(categoryData);
      Collection<SummaryStatistics> categoryDataSummaryStatistics = new ArrayList(categoryData.size());
      Iterator i$ = categoryData.iterator();

      while(i$.hasNext()) {
         double[] data = (double[])i$.next();
         SummaryStatistics dataSummaryStatistics = new SummaryStatistics();
         categoryDataSummaryStatistics.add(dataSummaryStatistics);
         double[] arr$ = data;
         int len$ = data.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            double val = arr$[i$];
            dataSummaryStatistics.addValue(val);
         }
      }

      return this.anovaStats(categoryDataSummaryStatistics, false);
   }

   public boolean anovaTest(Collection<double[]> categoryData, double alpha) throws NullArgumentException, DimensionMismatchException, OutOfRangeException, ConvergenceException, MaxCountExceededException {
      if (!(alpha <= 0.0D) && !(alpha > 0.5D)) {
         return this.anovaPValue(categoryData) < alpha;
      } else {
         throw new OutOfRangeException(LocalizedFormats.OUT_OF_BOUND_SIGNIFICANCE_LEVEL, alpha, 0, 0.5D);
      }
   }

   private OneWayAnova.AnovaStats anovaStats(Collection<SummaryStatistics> categoryData, boolean allowOneElementData) throws NullArgumentException, DimensionMismatchException {
      MathUtils.checkNotNull(categoryData);
      if (!allowOneElementData) {
         if (categoryData.size() < 2) {
            throw new DimensionMismatchException(LocalizedFormats.TWO_OR_MORE_CATEGORIES_REQUIRED, categoryData.size(), 2);
         }

         Iterator i$ = categoryData.iterator();

         while(i$.hasNext()) {
            SummaryStatistics array = (SummaryStatistics)i$.next();
            if (array.getN() <= 1L) {
               throw new DimensionMismatchException(LocalizedFormats.TWO_OR_MORE_VALUES_IN_CATEGORY_REQUIRED, (int)array.getN(), 2);
            }
         }
      }

      int dfwg = 0;
      double sswg = 0.0D;
      double totsum = 0.0D;
      double totsumsq = 0.0D;
      int totnum = 0;

      double ssbg;
      double ss;
      for(Iterator i$ = categoryData.iterator(); i$.hasNext(); sswg += ss) {
         SummaryStatistics data = (SummaryStatistics)i$.next();
         ssbg = data.getSum();
         double sumsq = data.getSumsq();
         int num = (int)data.getN();
         totnum += num;
         totsum += ssbg;
         totsumsq += sumsq;
         dfwg += num - 1;
         ss = sumsq - ssbg * ssbg / (double)num;
      }

      double sst = totsumsq - totsum * totsum / (double)totnum;
      ssbg = sst - sswg;
      int dfbg = categoryData.size() - 1;
      double msbg = ssbg / (double)dfbg;
      ss = sswg / (double)dfwg;
      double F = msbg / ss;
      return new OneWayAnova.AnovaStats(dfbg, dfwg, F);
   }

   private static class AnovaStats {
      private final int dfbg;
      private final int dfwg;
      private final double F;

      private AnovaStats(int dfbg, int dfwg, double F) {
         this.dfbg = dfbg;
         this.dfwg = dfwg;
         this.F = F;
      }

      // $FF: synthetic method
      AnovaStats(int x0, int x1, double x2, Object x3) {
         this(x0, x1, x2);
      }
   }
}
