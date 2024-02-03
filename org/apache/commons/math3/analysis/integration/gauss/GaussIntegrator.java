package org.apache.commons.math3.analysis.integration.gauss;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.NonMonotonicSequenceException;
import org.apache.commons.math3.util.MathArrays;
import org.apache.commons.math3.util.Pair;

public class GaussIntegrator {
   private final double[] points;
   private final double[] weights;

   public GaussIntegrator(double[] points, double[] weights) throws NonMonotonicSequenceException, DimensionMismatchException {
      if (points.length != weights.length) {
         throw new DimensionMismatchException(points.length, weights.length);
      } else {
         MathArrays.checkOrder(points, MathArrays.OrderDirection.INCREASING, true, true);
         this.points = (double[])points.clone();
         this.weights = (double[])weights.clone();
      }
   }

   public GaussIntegrator(Pair<double[], double[]> pointsAndWeights) throws NonMonotonicSequenceException {
      this((double[])pointsAndWeights.getFirst(), (double[])pointsAndWeights.getSecond());
   }

   public double integrate(UnivariateFunction f) {
      double s = 0.0D;
      double c = 0.0D;

      for(int i = 0; i < this.points.length; ++i) {
         double x = this.points[i];
         double w = this.weights[i];
         double y = w * f.value(x) - c;
         double t = s + y;
         c = t - s - y;
         s = t;
      }

      return s;
   }

   public int getNumberOfPoints() {
      return this.points.length;
   }
}
