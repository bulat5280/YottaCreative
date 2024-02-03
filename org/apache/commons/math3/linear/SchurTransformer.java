package org.apache.commons.math3.linear;

import org.apache.commons.math3.exception.MaxCountExceededException;
import org.apache.commons.math3.exception.util.LocalizedFormats;
import org.apache.commons.math3.util.FastMath;
import org.apache.commons.math3.util.Precision;

class SchurTransformer {
   private static final int MAX_ITERATIONS = 100;
   private final double[][] matrixP;
   private final double[][] matrixT;
   private RealMatrix cachedP;
   private RealMatrix cachedT;
   private RealMatrix cachedPt;
   private final double epsilon;

   public SchurTransformer(RealMatrix matrix) {
      this.epsilon = Precision.EPSILON;
      if (!matrix.isSquare()) {
         throw new NonSquareMatrixException(matrix.getRowDimension(), matrix.getColumnDimension());
      } else {
         HessenbergTransformer transformer = new HessenbergTransformer(matrix);
         this.matrixT = transformer.getH().getData();
         this.matrixP = transformer.getP().getData();
         this.cachedT = null;
         this.cachedP = null;
         this.cachedPt = null;
         this.transform();
      }
   }

   public RealMatrix getP() {
      if (this.cachedP == null) {
         this.cachedP = MatrixUtils.createRealMatrix(this.matrixP);
      }

      return this.cachedP;
   }

   public RealMatrix getPT() {
      if (this.cachedPt == null) {
         this.cachedPt = this.getP().transpose();
      }

      return this.cachedPt;
   }

   public RealMatrix getT() {
      if (this.cachedT == null) {
         this.cachedT = MatrixUtils.createRealMatrix(this.matrixT);
      }

      return this.cachedT;
   }

   private void transform() {
      int n = this.matrixT.length;
      double norm = this.getNorm();
      SchurTransformer.ShiftInfo shift = new SchurTransformer.ShiftInfo();
      int iteration = 0;
      int iu = n - 1;

      while(true) {
         while(iu >= 0) {
            int il = this.findSmallSubDiagonalElement(iu, norm);
            if (il == iu) {
               this.matrixT[iu][iu] += shift.exShift;
               --iu;
               iteration = 0;
            } else if (il != iu - 1) {
               this.computeShift(il, iu, iteration, shift);
               ++iteration;
               if (iteration > 100) {
                  throw new MaxCountExceededException(LocalizedFormats.CONVERGENCE_FAILED, 100, new Object[0]);
               }

               double[] hVec = new double[3];
               int im = this.initQRStep(il, iu, shift, hVec);
               this.performDoubleQRStep(il, im, iu, shift, hVec);
            } else {
               double p = (this.matrixT[iu - 1][iu - 1] - this.matrixT[iu][iu]) / 2.0D;
               double q = p * p + this.matrixT[iu][iu - 1] * this.matrixT[iu - 1][iu];
               double[] var10000 = this.matrixT[iu];
               var10000[iu] += shift.exShift;
               var10000 = this.matrixT[iu - 1];
               var10000[iu - 1] += shift.exShift;
               if (q >= 0.0D) {
                  double z = FastMath.sqrt(FastMath.abs(q));
                  if (p >= 0.0D) {
                     z += p;
                  } else {
                     z = p - z;
                  }

                  double x = this.matrixT[iu][iu - 1];
                  double s = FastMath.abs(x) + FastMath.abs(z);
                  p = x / s;
                  q = z / s;
                  double r = FastMath.sqrt(p * p + q * q);
                  p /= r;
                  q /= r;

                  int i;
                  for(i = iu - 1; i < n; ++i) {
                     z = this.matrixT[iu - 1][i];
                     this.matrixT[iu - 1][i] = q * z + p * this.matrixT[iu][i];
                     this.matrixT[iu][i] = q * this.matrixT[iu][i] - p * z;
                  }

                  for(i = 0; i <= iu; ++i) {
                     z = this.matrixT[i][iu - 1];
                     this.matrixT[i][iu - 1] = q * z + p * this.matrixT[i][iu];
                     this.matrixT[i][iu] = q * this.matrixT[i][iu] - p * z;
                  }

                  for(i = 0; i <= n - 1; ++i) {
                     z = this.matrixP[i][iu - 1];
                     this.matrixP[i][iu - 1] = q * z + p * this.matrixP[i][iu];
                     this.matrixP[i][iu] = q * this.matrixP[i][iu] - p * z;
                  }
               }

               iu -= 2;
               iteration = 0;
            }
         }

         return;
      }
   }

   private double getNorm() {
      double norm = 0.0D;

      for(int i = 0; i < this.matrixT.length; ++i) {
         for(int j = FastMath.max(i - 1, 0); j < this.matrixT.length; ++j) {
            norm += FastMath.abs(this.matrixT[i][j]);
         }
      }

      return norm;
   }

   private int findSmallSubDiagonalElement(int startIdx, double norm) {
      int l;
      for(l = startIdx; l > 0; --l) {
         double s = FastMath.abs(this.matrixT[l - 1][l - 1]) + FastMath.abs(this.matrixT[l][l]);
         if (s == 0.0D) {
            s = norm;
         }

         if (FastMath.abs(this.matrixT[l][l - 1]) < this.epsilon * s) {
            break;
         }
      }

      return l;
   }

   private void computeShift(int l, int idx, int iteration, SchurTransformer.ShiftInfo shift) {
      shift.x = this.matrixT[idx][idx];
      shift.y = shift.w = 0.0D;
      if (l < idx) {
         shift.y = this.matrixT[idx - 1][idx - 1];
         shift.w = this.matrixT[idx][idx - 1] * this.matrixT[idx - 1][idx];
      }

      double[] var10000;
      double s;
      if (iteration == 10) {
         shift.exShift += shift.x;

         for(int i = 0; i <= idx; ++i) {
            var10000 = this.matrixT[i];
            var10000[i] -= shift.x;
         }

         s = FastMath.abs(this.matrixT[idx][idx - 1]) + FastMath.abs(this.matrixT[idx - 1][idx - 2]);
         shift.x = 0.75D * s;
         shift.y = 0.75D * s;
         shift.w = -0.4375D * s * s;
      }

      if (iteration == 30) {
         s = (shift.y - shift.x) / 2.0D;
         s = s * s + shift.w;
         if (s > 0.0D) {
            s = FastMath.sqrt(s);
            if (shift.y < shift.x) {
               s = -s;
            }

            s = shift.x - shift.w / ((shift.y - shift.x) / 2.0D + s);

            for(int i = 0; i <= idx; ++i) {
               var10000 = this.matrixT[i];
               var10000[i] -= s;
            }

            shift.exShift += s;
            shift.x = shift.y = shift.w = 0.964D;
         }
      }

   }

   private int initQRStep(int il, int iu, SchurTransformer.ShiftInfo shift, double[] hVec) {
      int im;
      for(im = iu - 2; im >= il; --im) {
         double z = this.matrixT[im][im];
         double r = shift.x - z;
         double s = shift.y - z;
         hVec[0] = (r * s - shift.w) / this.matrixT[im + 1][im] + this.matrixT[im][im + 1];
         hVec[1] = this.matrixT[im + 1][im + 1] - z - r - s;
         hVec[2] = this.matrixT[im + 2][im + 1];
         if (im == il) {
            break;
         }

         double lhs = FastMath.abs(this.matrixT[im][im - 1]) * (FastMath.abs(hVec[1]) + FastMath.abs(hVec[2]));
         double rhs = FastMath.abs(hVec[0]) * (FastMath.abs(this.matrixT[im - 1][im - 1]) + FastMath.abs(z) + FastMath.abs(this.matrixT[im + 1][im + 1]));
         if (lhs < this.epsilon * rhs) {
            break;
         }
      }

      return im;
   }

   private void performDoubleQRStep(int il, int im, int iu, SchurTransformer.ShiftInfo shift, double[] hVec) {
      int n = this.matrixT.length;
      double p = hVec[0];
      double q = hVec[1];
      double r = hVec[2];

      int k;
      for(k = im; k <= iu - 1; ++k) {
         boolean notlast = k != iu - 1;
         if (k != im) {
            p = this.matrixT[k][k - 1];
            q = this.matrixT[k + 1][k - 1];
            r = notlast ? this.matrixT[k + 2][k - 1] : 0.0D;
            shift.x = FastMath.abs(p) + FastMath.abs(q) + FastMath.abs(r);
            if (!Precision.equals(shift.x, 0.0D, this.epsilon)) {
               p /= shift.x;
               q /= shift.x;
               r /= shift.x;
            }
         }

         if (shift.x == 0.0D) {
            break;
         }

         double s = FastMath.sqrt(p * p + q * q + r * r);
         if (p < 0.0D) {
            s = -s;
         }

         if (s != 0.0D) {
            if (k != im) {
               this.matrixT[k][k - 1] = -s * shift.x;
            } else if (il != im) {
               this.matrixT[k][k - 1] = -this.matrixT[k][k - 1];
            }

            p += s;
            shift.x = p / s;
            shift.y = q / s;
            double z = r / s;
            q /= p;
            r /= p;

            int high;
            for(high = k; high < n; ++high) {
               p = this.matrixT[k][high] + q * this.matrixT[k + 1][high];
               if (notlast) {
                  p += r * this.matrixT[k + 2][high];
                  this.matrixT[k + 2][high] -= p * z;
               }

               this.matrixT[k][high] -= p * shift.x;
               this.matrixT[k + 1][high] -= p * shift.y;
            }

            for(high = 0; high <= FastMath.min(iu, k + 3); ++high) {
               p = shift.x * this.matrixT[high][k] + shift.y * this.matrixT[high][k + 1];
               if (notlast) {
                  p += z * this.matrixT[high][k + 2];
                  this.matrixT[high][k + 2] -= p * r;
               }

               this.matrixT[high][k] -= p;
               this.matrixT[high][k + 1] -= p * q;
            }

            high = this.matrixT.length - 1;

            for(int i = 0; i <= high; ++i) {
               p = shift.x * this.matrixP[i][k] + shift.y * this.matrixP[i][k + 1];
               if (notlast) {
                  p += z * this.matrixP[i][k + 2];
                  this.matrixP[i][k + 2] -= p * r;
               }

               this.matrixP[i][k] -= p;
               this.matrixP[i][k + 1] -= p * q;
            }
         }
      }

      for(k = im + 2; k <= iu; ++k) {
         this.matrixT[k][k - 2] = 0.0D;
         if (k > im + 2) {
            this.matrixT[k][k - 3] = 0.0D;
         }
      }

   }

   private static class ShiftInfo {
      double x;
      double y;
      double w;
      double exShift;

      private ShiftInfo() {
      }

      // $FF: synthetic method
      ShiftInfo(Object x0) {
         this();
      }
   }
}
