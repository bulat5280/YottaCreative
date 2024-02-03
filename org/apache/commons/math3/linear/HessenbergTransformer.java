package org.apache.commons.math3.linear;

import org.apache.commons.math3.util.FastMath;
import org.apache.commons.math3.util.Precision;

class HessenbergTransformer {
   private final double[][] householderVectors;
   private final double[] ort;
   private RealMatrix cachedP;
   private RealMatrix cachedPt;
   private RealMatrix cachedH;

   public HessenbergTransformer(RealMatrix matrix) {
      if (!matrix.isSquare()) {
         throw new NonSquareMatrixException(matrix.getRowDimension(), matrix.getColumnDimension());
      } else {
         int m = matrix.getRowDimension();
         this.householderVectors = matrix.getData();
         this.ort = new double[m];
         this.cachedP = null;
         this.cachedPt = null;
         this.cachedH = null;
         this.transform();
      }
   }

   public RealMatrix getP() {
      if (this.cachedP == null) {
         int n = this.householderVectors.length;
         int high = n - 1;
         double[][] pa = new double[n][n];

         int m;
         int j;
         for(m = 0; m < n; ++m) {
            for(j = 0; j < n; ++j) {
               pa[m][j] = m == j ? 1.0D : 0.0D;
            }
         }

         for(m = high - 1; m >= 1; --m) {
            if (this.householderVectors[m][m - 1] != 0.0D) {
               for(j = m + 1; j <= high; ++j) {
                  this.ort[j] = this.householderVectors[j][m - 1];
               }

               for(j = m; j <= high; ++j) {
                  double g = 0.0D;

                  int i;
                  for(i = m; i <= high; ++i) {
                     g += this.ort[i] * pa[i][j];
                  }

                  g = g / this.ort[m] / this.householderVectors[m][m - 1];

                  for(i = m; i <= high; ++i) {
                     pa[i][j] += g * this.ort[i];
                  }
               }
            }
         }

         this.cachedP = MatrixUtils.createRealMatrix(pa);
      }

      return this.cachedP;
   }

   public RealMatrix getPT() {
      if (this.cachedPt == null) {
         this.cachedPt = this.getP().transpose();
      }

      return this.cachedPt;
   }

   public RealMatrix getH() {
      if (this.cachedH == null) {
         int m = this.householderVectors.length;
         double[][] h = new double[m][m];

         for(int i = 0; i < m; ++i) {
            if (i > 0) {
               h[i][i - 1] = this.householderVectors[i][i - 1];
            }

            for(int j = i; j < m; ++j) {
               h[i][j] = this.householderVectors[i][j];
            }
         }

         this.cachedH = MatrixUtils.createRealMatrix(h);
      }

      return this.cachedH;
   }

   double[][] getHouseholderVectorsRef() {
      return this.householderVectors;
   }

   private void transform() {
      int n = this.householderVectors.length;
      int high = n - 1;

      for(int m = 1; m <= high - 1; ++m) {
         double scale = 0.0D;

         for(int i = m; i <= high; ++i) {
            scale += FastMath.abs(this.householderVectors[i][m - 1]);
         }

         if (!Precision.equals(scale, 0.0D)) {
            double h = 0.0D;

            for(int i = high; i >= m; --i) {
               this.ort[i] = this.householderVectors[i][m - 1] / scale;
               h += this.ort[i] * this.ort[i];
            }

            double g = this.ort[m] > 0.0D ? -FastMath.sqrt(h) : FastMath.sqrt(h);
            h -= this.ort[m] * g;
            this.ort[m] -= g;

            int i;
            double f;
            int j;
            double[] var10000;
            for(i = m; i < n; ++i) {
               f = 0.0D;

               for(j = high; j >= m; --j) {
                  f += this.ort[j] * this.householderVectors[j][i];
               }

               f /= h;

               for(j = m; j <= high; ++j) {
                  var10000 = this.householderVectors[j];
                  var10000[i] -= f * this.ort[j];
               }
            }

            for(i = 0; i <= high; ++i) {
               f = 0.0D;

               for(j = high; j >= m; --j) {
                  f += this.ort[j] * this.householderVectors[i][j];
               }

               f /= h;

               for(j = m; j <= high; ++j) {
                  var10000 = this.householderVectors[i];
                  var10000[j] -= f * this.ort[j];
               }
            }

            this.ort[m] = scale * this.ort[m];
            this.householderVectors[m][m - 1] = scale * g;
         }
      }

   }
}
