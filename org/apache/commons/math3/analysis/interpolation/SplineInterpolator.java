package org.apache.commons.math3.analysis.interpolation;

import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;
import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.NonMonotonicSequenceException;
import org.apache.commons.math3.exception.NumberIsTooSmallException;
import org.apache.commons.math3.exception.util.LocalizedFormats;
import org.apache.commons.math3.util.MathArrays;

public class SplineInterpolator implements UnivariateInterpolator {
   public PolynomialSplineFunction interpolate(double[] x, double[] y) throws DimensionMismatchException, NumberIsTooSmallException, NonMonotonicSequenceException {
      if (x.length != y.length) {
         throw new DimensionMismatchException(x.length, y.length);
      } else if (x.length < 3) {
         throw new NumberIsTooSmallException(LocalizedFormats.NUMBER_OF_POINTS, x.length, 3, true);
      } else {
         int n = x.length - 1;
         MathArrays.checkOrder(x);
         double[] h = new double[n];

         for(int i = 0; i < n; ++i) {
            h[i] = x[i + 1] - x[i];
         }

         double[] mu = new double[n];
         double[] z = new double[n + 1];
         mu[0] = 0.0D;
         z[0] = 0.0D;
         double g = 0.0D;

         for(int i = 1; i < n; ++i) {
            g = 2.0D * (x[i + 1] - x[i - 1]) - h[i - 1] * mu[i - 1];
            mu[i] = h[i] / g;
            z[i] = (3.0D * (y[i + 1] * h[i - 1] - y[i] * (x[i + 1] - x[i - 1]) + y[i - 1] * h[i]) / (h[i - 1] * h[i]) - h[i - 1] * z[i - 1]) / g;
         }

         double[] b = new double[n];
         double[] c = new double[n + 1];
         double[] d = new double[n];
         z[n] = 0.0D;
         c[n] = 0.0D;

         for(int j = n - 1; j >= 0; --j) {
            c[j] = z[j] - mu[j] * c[j + 1];
            b[j] = (y[j + 1] - y[j]) / h[j] - h[j] * (c[j + 1] + 2.0D * c[j]) / 3.0D;
            d[j] = (c[j + 1] - c[j]) / (3.0D * h[j]);
         }

         PolynomialFunction[] polynomials = new PolynomialFunction[n];
         double[] coefficients = new double[4];

         for(int i = 0; i < n; ++i) {
            coefficients[0] = y[i];
            coefficients[1] = b[i];
            coefficients[2] = c[i];
            coefficients[3] = d[i];
            polynomials[i] = new PolynomialFunction(coefficients);
         }

         return new PolynomialSplineFunction(x, polynomials);
      }
   }
}
