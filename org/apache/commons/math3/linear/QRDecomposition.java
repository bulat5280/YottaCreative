package org.apache.commons.math3.linear;

import java.util.Arrays;
import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.util.FastMath;

public class QRDecomposition {
   private double[][] qrt;
   private double[] rDiag;
   private RealMatrix cachedQ;
   private RealMatrix cachedQT;
   private RealMatrix cachedR;
   private RealMatrix cachedH;
   private final double threshold;

   public QRDecomposition(RealMatrix matrix) {
      this(matrix, 0.0D);
   }

   public QRDecomposition(RealMatrix matrix, double threshold) {
      this.threshold = threshold;
      int m = matrix.getRowDimension();
      int n = matrix.getColumnDimension();
      this.qrt = matrix.transpose().getData();
      this.rDiag = new double[FastMath.min(m, n)];
      this.cachedQ = null;
      this.cachedQT = null;
      this.cachedR = null;
      this.cachedH = null;
      this.decompose(this.qrt);
   }

   protected void decompose(double[][] matrix) {
      for(int minor = 0; minor < FastMath.min(this.qrt.length, this.qrt[0].length); ++minor) {
         this.performHouseholderReflection(minor, this.qrt);
      }

   }

   protected void performHouseholderReflection(int minor, double[][] matrix) {
      double[] qrtMinor = this.qrt[minor];
      double xNormSqr = 0.0D;

      for(int row = minor; row < qrtMinor.length; ++row) {
         double c = qrtMinor[row];
         xNormSqr += c * c;
      }

      double a = qrtMinor[minor] > 0.0D ? -FastMath.sqrt(xNormSqr) : FastMath.sqrt(xNormSqr);
      this.rDiag[minor] = a;
      if (a != 0.0D) {
         qrtMinor[minor] -= a;

         for(int col = minor + 1; col < this.qrt.length; ++col) {
            double[] qrtCol = this.qrt[col];
            double alpha = 0.0D;

            int row;
            for(row = minor; row < qrtCol.length; ++row) {
               alpha -= qrtCol[row] * qrtMinor[row];
            }

            alpha /= a * qrtMinor[minor];

            for(row = minor; row < qrtCol.length; ++row) {
               qrtCol[row] -= alpha * qrtMinor[row];
            }
         }
      }

   }

   public RealMatrix getR() {
      if (this.cachedR == null) {
         int n = this.qrt.length;
         int m = this.qrt[0].length;
         double[][] ra = new double[m][n];

         for(int row = FastMath.min(m, n) - 1; row >= 0; --row) {
            ra[row][row] = this.rDiag[row];

            for(int col = row + 1; col < n; ++col) {
               ra[row][col] = this.qrt[col][row];
            }
         }

         this.cachedR = MatrixUtils.createRealMatrix(ra);
      }

      return this.cachedR;
   }

   public RealMatrix getQ() {
      if (this.cachedQ == null) {
         this.cachedQ = this.getQT().transpose();
      }

      return this.cachedQ;
   }

   public RealMatrix getQT() {
      if (this.cachedQT == null) {
         int n = this.qrt.length;
         int m = this.qrt[0].length;
         double[][] qta = new double[m][m];

         int minor;
         for(minor = m - 1; minor >= FastMath.min(m, n); --minor) {
            qta[minor][minor] = 1.0D;
         }

         for(minor = FastMath.min(m, n) - 1; minor >= 0; --minor) {
            double[] qrtMinor = this.qrt[minor];
            qta[minor][minor] = 1.0D;
            if (qrtMinor[minor] != 0.0D) {
               for(int col = minor; col < m; ++col) {
                  double alpha = 0.0D;

                  int row;
                  for(row = minor; row < m; ++row) {
                     alpha -= qta[col][row] * qrtMinor[row];
                  }

                  alpha /= this.rDiag[minor] * qrtMinor[minor];

                  for(row = minor; row < m; ++row) {
                     qta[col][row] += -alpha * qrtMinor[row];
                  }
               }
            }
         }

         this.cachedQT = MatrixUtils.createRealMatrix(qta);
      }

      return this.cachedQT;
   }

   public RealMatrix getH() {
      if (this.cachedH == null) {
         int n = this.qrt.length;
         int m = this.qrt[0].length;
         double[][] ha = new double[m][n];

         for(int i = 0; i < m; ++i) {
            for(int j = 0; j < FastMath.min(i + 1, n); ++j) {
               ha[i][j] = this.qrt[j][i] / -this.rDiag[j];
            }
         }

         this.cachedH = MatrixUtils.createRealMatrix(ha);
      }

      return this.cachedH;
   }

   public DecompositionSolver getSolver() {
      return new QRDecomposition.Solver(this.qrt, this.rDiag, this.threshold);
   }

   private static class Solver implements DecompositionSolver {
      private final double[][] qrt;
      private final double[] rDiag;
      private final double threshold;

      private Solver(double[][] qrt, double[] rDiag, double threshold) {
         this.qrt = qrt;
         this.rDiag = rDiag;
         this.threshold = threshold;
      }

      public boolean isNonSingular() {
         double[] arr$ = this.rDiag;
         int len$ = arr$.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            double diag = arr$[i$];
            if (FastMath.abs(diag) <= this.threshold) {
               return false;
            }
         }

         return true;
      }

      public RealVector solve(RealVector b) {
         int n = this.qrt.length;
         int m = this.qrt[0].length;
         if (b.getDimension() != m) {
            throw new DimensionMismatchException(b.getDimension(), m);
         } else if (!this.isNonSingular()) {
            throw new SingularMatrixException();
         } else {
            double[] x = new double[n];
            double[] y = b.toArray();

            int row;
            int row;
            for(row = 0; row < FastMath.min(m, n); ++row) {
               double[] qrtMinor = this.qrt[row];
               double dotProduct = 0.0D;

               for(row = row; row < m; ++row) {
                  dotProduct += y[row] * qrtMinor[row];
               }

               dotProduct /= this.rDiag[row] * qrtMinor[row];

               for(row = row; row < m; ++row) {
                  y[row] += dotProduct * qrtMinor[row];
               }
            }

            for(row = this.rDiag.length - 1; row >= 0; --row) {
               y[row] /= this.rDiag[row];
               double yRow = y[row];
               double[] qrtRow = this.qrt[row];
               x[row] = yRow;

               for(row = 0; row < row; ++row) {
                  y[row] -= yRow * qrtRow[row];
               }
            }

            return new ArrayRealVector(x, false);
         }
      }

      public RealMatrix solve(RealMatrix b) {
         int n = this.qrt.length;
         int m = this.qrt[0].length;
         if (b.getRowDimension() != m) {
            throw new DimensionMismatchException(b.getRowDimension(), m);
         } else if (!this.isNonSingular()) {
            throw new SingularMatrixException();
         } else {
            int columns = b.getColumnDimension();
            int blockSize = true;
            int cBlocks = (columns + 52 - 1) / 52;
            double[][] xBlocks = BlockRealMatrix.createBlocksLayout(n, columns);
            double[][] y = new double[b.getRowDimension()][52];
            double[] alpha = new double[52];

            for(int kBlock = 0; kBlock < cBlocks; ++kBlock) {
               int kStart = kBlock * 52;
               int kEnd = FastMath.min(kStart + 52, columns);
               int kWidth = kEnd - kStart;
               b.copySubMatrix(0, m - 1, kStart, kEnd - 1, y);

               int j;
               int k;
               for(j = 0; j < FastMath.min(m, n); ++j) {
                  double[] qrtMinor = this.qrt[j];
                  double factor = 1.0D / (this.rDiag[j] * qrtMinor[j]);
                  Arrays.fill(alpha, 0, kWidth, 0.0D);

                  int row;
                  double d;
                  double[] yRow;
                  for(row = j; row < m; ++row) {
                     d = qrtMinor[row];
                     yRow = y[row];

                     for(k = 0; k < kWidth; ++k) {
                        alpha[k] += d * yRow[k];
                     }
                  }

                  for(row = 0; row < kWidth; ++row) {
                     alpha[row] *= factor;
                  }

                  for(row = j; row < m; ++row) {
                     d = qrtMinor[row];
                     yRow = y[row];

                     for(k = 0; k < kWidth; ++k) {
                        yRow[k] += alpha[k] * d;
                     }
                  }
               }

               for(j = this.rDiag.length - 1; j >= 0; --j) {
                  int jBlock = j / 52;
                  int jStart = jBlock * 52;
                  double factor = 1.0D / this.rDiag[j];
                  double[] yJ = y[j];
                  double[] xBlock = xBlocks[jBlock * cBlocks + kBlock];
                  int index = (j - jStart) * kWidth;

                  for(k = 0; k < kWidth; ++k) {
                     yJ[k] *= factor;
                     xBlock[index++] = yJ[k];
                  }

                  double[] qrtJ = this.qrt[j];

                  for(int i = 0; i < j; ++i) {
                     double rIJ = qrtJ[i];
                     double[] yI = y[i];

                     for(int k = 0; k < kWidth; ++k) {
                        yI[k] -= yJ[k] * rIJ;
                     }
                  }
               }
            }

            return new BlockRealMatrix(n, columns, xBlocks, false);
         }
      }

      public RealMatrix getInverse() {
         return this.solve(MatrixUtils.createRealIdentityMatrix(this.rDiag.length));
      }

      // $FF: synthetic method
      Solver(double[][] x0, double[] x1, double x2, Object x3) {
         this(x0, x1, x2);
      }
   }
}
