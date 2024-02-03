package org.apache.commons.math3.optim.nonlinear.scalar;

import org.apache.commons.math3.analysis.MultivariateFunction;
import org.apache.commons.math3.analysis.MultivariateVectorFunction;
import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.linear.RealMatrix;

public class LeastSquaresConverter implements MultivariateFunction {
   private final MultivariateVectorFunction function;
   private final double[] observations;
   private final double[] weights;
   private final RealMatrix scale;

   public LeastSquaresConverter(MultivariateVectorFunction function, double[] observations) {
      this.function = function;
      this.observations = (double[])observations.clone();
      this.weights = null;
      this.scale = null;
   }

   public LeastSquaresConverter(MultivariateVectorFunction function, double[] observations, double[] weights) {
      if (observations.length != weights.length) {
         throw new DimensionMismatchException(observations.length, weights.length);
      } else {
         this.function = function;
         this.observations = (double[])observations.clone();
         this.weights = (double[])weights.clone();
         this.scale = null;
      }
   }

   public LeastSquaresConverter(MultivariateVectorFunction function, double[] observations, RealMatrix scale) {
      if (observations.length != scale.getColumnDimension()) {
         throw new DimensionMismatchException(observations.length, scale.getColumnDimension());
      } else {
         this.function = function;
         this.observations = (double[])observations.clone();
         this.weights = null;
         this.scale = scale.copy();
      }
   }

   public double value(double[] point) {
      double[] residuals = this.function.value(point);
      if (residuals.length != this.observations.length) {
         throw new DimensionMismatchException(residuals.length, this.observations.length);
      } else {
         for(int i = 0; i < residuals.length; ++i) {
            residuals[i] -= this.observations[i];
         }

         double sumSquares = 0.0D;
         if (this.weights != null) {
            for(int i = 0; i < residuals.length; ++i) {
               double ri = residuals[i];
               sumSquares += this.weights[i] * ri * ri;
            }
         } else {
            int i$;
            double yi;
            double[] arr$;
            int len$;
            if (this.scale != null) {
               arr$ = this.scale.operate(residuals);
               len$ = arr$.length;

               for(i$ = 0; i$ < len$; ++i$) {
                  yi = arr$[i$];
                  sumSquares += yi * yi;
               }
            } else {
               arr$ = residuals;
               len$ = residuals.length;

               for(i$ = 0; i$ < len$; ++i$) {
                  yi = arr$[i$];
                  sumSquares += yi * yi;
               }
            }
         }

         return sumSquares;
      }
   }
}
