package org.apache.commons.math3.optim.nonlinear.scalar;

import org.apache.commons.math3.analysis.MultivariateFunction;
import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.function.Logit;
import org.apache.commons.math3.analysis.function.Sigmoid;
import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.NumberIsTooSmallException;
import org.apache.commons.math3.util.FastMath;
import org.apache.commons.math3.util.MathUtils;

public class MultivariateFunctionMappingAdapter implements MultivariateFunction {
   private final MultivariateFunction bounded;
   private final MultivariateFunctionMappingAdapter.Mapper[] mappers;

   public MultivariateFunctionMappingAdapter(MultivariateFunction bounded, double[] lower, double[] upper) {
      MathUtils.checkNotNull(lower);
      MathUtils.checkNotNull(upper);
      if (lower.length != upper.length) {
         throw new DimensionMismatchException(lower.length, upper.length);
      } else {
         int i;
         for(i = 0; i < lower.length; ++i) {
            if (!(upper[i] >= lower[i])) {
               throw new NumberIsTooSmallException(upper[i], lower[i], true);
            }
         }

         this.bounded = bounded;
         this.mappers = new MultivariateFunctionMappingAdapter.Mapper[lower.length];

         for(i = 0; i < this.mappers.length; ++i) {
            if (Double.isInfinite(lower[i])) {
               if (Double.isInfinite(upper[i])) {
                  this.mappers[i] = new MultivariateFunctionMappingAdapter.NoBoundsMapper();
               } else {
                  this.mappers[i] = new MultivariateFunctionMappingAdapter.UpperBoundMapper(upper[i]);
               }
            } else if (Double.isInfinite(upper[i])) {
               this.mappers[i] = new MultivariateFunctionMappingAdapter.LowerBoundMapper(lower[i]);
            } else {
               this.mappers[i] = new MultivariateFunctionMappingAdapter.LowerUpperBoundMapper(lower[i], upper[i]);
            }
         }

      }
   }

   public double[] unboundedToBounded(double[] point) {
      double[] mapped = new double[this.mappers.length];

      for(int i = 0; i < this.mappers.length; ++i) {
         mapped[i] = this.mappers[i].unboundedToBounded(point[i]);
      }

      return mapped;
   }

   public double[] boundedToUnbounded(double[] point) {
      double[] mapped = new double[this.mappers.length];

      for(int i = 0; i < this.mappers.length; ++i) {
         mapped[i] = this.mappers[i].boundedToUnbounded(point[i]);
      }

      return mapped;
   }

   public double value(double[] point) {
      return this.bounded.value(this.unboundedToBounded(point));
   }

   private static class LowerUpperBoundMapper implements MultivariateFunctionMappingAdapter.Mapper {
      private final UnivariateFunction boundingFunction;
      private final UnivariateFunction unboundingFunction;

      public LowerUpperBoundMapper(double lower, double upper) {
         this.boundingFunction = new Sigmoid(lower, upper);
         this.unboundingFunction = new Logit(lower, upper);
      }

      public double unboundedToBounded(double y) {
         return this.boundingFunction.value(y);
      }

      public double boundedToUnbounded(double x) {
         return this.unboundingFunction.value(x);
      }
   }

   private static class UpperBoundMapper implements MultivariateFunctionMappingAdapter.Mapper {
      private final double upper;

      public UpperBoundMapper(double upper) {
         this.upper = upper;
      }

      public double unboundedToBounded(double y) {
         return this.upper - FastMath.exp(-y);
      }

      public double boundedToUnbounded(double x) {
         return -FastMath.log(this.upper - x);
      }
   }

   private static class LowerBoundMapper implements MultivariateFunctionMappingAdapter.Mapper {
      private final double lower;

      public LowerBoundMapper(double lower) {
         this.lower = lower;
      }

      public double unboundedToBounded(double y) {
         return this.lower + FastMath.exp(y);
      }

      public double boundedToUnbounded(double x) {
         return FastMath.log(x - this.lower);
      }
   }

   private static class NoBoundsMapper implements MultivariateFunctionMappingAdapter.Mapper {
      private NoBoundsMapper() {
      }

      public double unboundedToBounded(double y) {
         return y;
      }

      public double boundedToUnbounded(double x) {
         return x;
      }

      // $FF: synthetic method
      NoBoundsMapper(Object x0) {
         this();
      }
   }

   private interface Mapper {
      double unboundedToBounded(double var1);

      double boundedToUnbounded(double var1);
   }
}
