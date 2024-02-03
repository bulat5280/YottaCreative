package org.apache.commons.math3.analysis.interpolation;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;
import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.NoDataException;
import org.apache.commons.math3.exception.NonMonotonicSequenceException;
import org.apache.commons.math3.exception.NumberIsTooSmallException;
import org.apache.commons.math3.util.MathArrays;

public class BicubicSplineInterpolator implements BivariateGridInterpolator {
   public BicubicSplineInterpolatingFunction interpolate(double[] xval, double[] yval, double[][] fval) throws NoDataException, DimensionMismatchException, NonMonotonicSequenceException, NumberIsTooSmallException {
      if (xval.length != 0 && yval.length != 0 && fval.length != 0) {
         if (xval.length != fval.length) {
            throw new DimensionMismatchException(xval.length, fval.length);
         } else {
            MathArrays.checkOrder(xval);
            MathArrays.checkOrder(yval);
            int xLen = xval.length;
            int yLen = yval.length;
            double[][] fX = new double[yLen][xLen];

            for(int i = 0; i < xLen; ++i) {
               if (fval[i].length != yLen) {
                  throw new DimensionMismatchException(fval[i].length, yLen);
               }

               for(int j = 0; j < yLen; ++j) {
                  fX[j][i] = fval[i][j];
               }
            }

            SplineInterpolator spInterpolator = new SplineInterpolator();
            PolynomialSplineFunction[] ySplineX = new PolynomialSplineFunction[yLen];

            for(int j = 0; j < yLen; ++j) {
               ySplineX[j] = spInterpolator.interpolate(xval, fX[j]);
            }

            PolynomialSplineFunction[] xSplineY = new PolynomialSplineFunction[xLen];

            for(int i = 0; i < xLen; ++i) {
               xSplineY[i] = spInterpolator.interpolate(yval, fval[i]);
            }

            double[][] dFdX = new double[xLen][yLen];

            int i;
            for(int j = 0; j < yLen; ++j) {
               UnivariateFunction f = ySplineX[j].derivative();

               for(i = 0; i < xLen; ++i) {
                  dFdX[i][j] = f.value(xval[i]);
               }
            }

            double[][] dFdY = new double[xLen][yLen];

            int nI;
            for(int i = 0; i < xLen; ++i) {
               UnivariateFunction f = xSplineY[i].derivative();

               for(nI = 0; nI < yLen; ++nI) {
                  dFdY[i][nI] = f.value(yval[nI]);
               }
            }

            double[][] d2FdXdY = new double[xLen][yLen];

            for(i = 0; i < xLen; ++i) {
               nI = this.nextIndex(i, xLen);
               int pI = this.previousIndex(i);

               for(int j = 0; j < yLen; ++j) {
                  int nJ = this.nextIndex(j, yLen);
                  int pJ = this.previousIndex(j);
                  d2FdXdY[i][j] = (fval[nI][nJ] - fval[nI][pJ] - fval[pI][nJ] + fval[pI][pJ]) / ((xval[nI] - xval[pI]) * (yval[nJ] - yval[pJ]));
               }
            }

            return new BicubicSplineInterpolatingFunction(xval, yval, fval, dFdX, dFdY, d2FdXdY);
         }
      } else {
         throw new NoDataException();
      }
   }

   private int nextIndex(int i, int max) {
      int index = i + 1;
      return index < max ? index : index - 1;
   }

   private int previousIndex(int i) {
      int index = i - 1;
      return index >= 0 ? index : 0;
   }
}
