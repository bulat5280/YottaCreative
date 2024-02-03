package org.apache.commons.math3.analysis.interpolation;

import org.apache.commons.math3.analysis.TrivariateFunction;
import org.apache.commons.math3.exception.OutOfRangeException;

class TricubicSplineFunction implements TrivariateFunction {
   private static final short N = 4;
   private final double[][][] a = new double[4][4][4];

   public TricubicSplineFunction(double[] aV) {
      for(int i = 0; i < 4; ++i) {
         for(int j = 0; j < 4; ++j) {
            for(int k = 0; k < 4; ++k) {
               this.a[i][j][k] = aV[i + 4 * (j + 4 * k)];
            }
         }
      }

   }

   public double value(double x, double y, double z) throws OutOfRangeException {
      if (!(x < 0.0D) && !(x > 1.0D)) {
         if (!(y < 0.0D) && !(y > 1.0D)) {
            if (!(z < 0.0D) && !(z > 1.0D)) {
               double x2 = x * x;
               double x3 = x2 * x;
               double[] pX = new double[]{1.0D, x, x2, x3};
               double y2 = y * y;
               double y3 = y2 * y;
               double[] pY = new double[]{1.0D, y, y2, y3};
               double z2 = z * z;
               double z3 = z2 * z;
               double[] pZ = new double[]{1.0D, z, z2, z3};
               double result = 0.0D;

               for(int i = 0; i < 4; ++i) {
                  for(int j = 0; j < 4; ++j) {
                     for(int k = 0; k < 4; ++k) {
                        result += this.a[i][j][k] * pX[i] * pY[j] * pZ[k];
                     }
                  }
               }

               return result;
            } else {
               throw new OutOfRangeException(z, 0, 1);
            }
         } else {
            throw new OutOfRangeException(y, 0, 1);
         }
      } else {
         throw new OutOfRangeException(x, 0, 1);
      }
   }
}
