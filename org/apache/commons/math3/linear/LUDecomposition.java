package org.apache.commons.math3.linear;

import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.util.FastMath;

public class LUDecomposition {
   private static final double DEFAULT_TOO_SMALL = 1.0E-11D;
   private final double[][] lu;
   private final int[] pivot;
   private boolean even;
   private boolean singular;
   private RealMatrix cachedL;
   private RealMatrix cachedU;
   private RealMatrix cachedP;

   public LUDecomposition(RealMatrix matrix) {
      this(matrix, 1.0E-11D);
   }

   public LUDecomposition(RealMatrix matrix, double singularityThreshold) {
      if (!matrix.isSquare()) {
         throw new NonSquareMatrixException(matrix.getRowDimension(), matrix.getColumnDimension());
      } else {
         int m = matrix.getColumnDimension();
         this.lu = matrix.getData();
         this.pivot = new int[m];
         this.cachedL = null;
         this.cachedU = null;
         this.cachedP = null;

         int col;
         for(col = 0; col < m; this.pivot[col] = col++) {
         }

         this.even = true;
         this.singular = false;

         for(col = 0; col < m; ++col) {
            int max;
            for(max = 0; max < col; ++max) {
               double[] luRow = this.lu[max];
               double sum = luRow[col];

               for(int i = 0; i < max; ++i) {
                  sum -= luRow[i] * this.lu[i][col];
               }

               luRow[col] = sum;
            }

            max = col;
            double largest = Double.NEGATIVE_INFINITY;

            int temp;
            for(int row = col; row < m; ++row) {
               double[] luRow = this.lu[row];
               double sum = luRow[col];

               for(temp = 0; temp < col; ++temp) {
                  sum -= luRow[temp] * this.lu[temp][col];
               }

               luRow[col] = sum;
               if (FastMath.abs(sum) > largest) {
                  largest = FastMath.abs(sum);
                  max = row;
               }
            }

            if (FastMath.abs(this.lu[max][col]) < singularityThreshold) {
               this.singular = true;
               return;
            }

            double luDiag;
            if (max != col) {
               luDiag = 0.0D;
               double[] luMax = this.lu[max];
               double[] luCol = this.lu[col];

               for(temp = 0; temp < m; ++temp) {
                  luDiag = luMax[temp];
                  luMax[temp] = luCol[temp];
                  luCol[temp] = luDiag;
               }

               temp = this.pivot[max];
               this.pivot[max] = this.pivot[col];
               this.pivot[col] = temp;
               this.even = !this.even;
            }

            luDiag = this.lu[col][col];

            for(int row = col + 1; row < m; ++row) {
               double[] var10000 = this.lu[row];
               var10000[col] /= luDiag;
            }
         }

      }
   }

   public RealMatrix getL() {
      if (this.cachedL == null && !this.singular) {
         int m = this.pivot.length;
         this.cachedL = MatrixUtils.createRealMatrix(m, m);

         for(int i = 0; i < m; ++i) {
            double[] luI = this.lu[i];

            for(int j = 0; j < i; ++j) {
               this.cachedL.setEntry(i, j, luI[j]);
            }

            this.cachedL.setEntry(i, i, 1.0D);
         }
      }

      return this.cachedL;
   }

   public RealMatrix getU() {
      if (this.cachedU == null && !this.singular) {
         int m = this.pivot.length;
         this.cachedU = MatrixUtils.createRealMatrix(m, m);

         for(int i = 0; i < m; ++i) {
            double[] luI = this.lu[i];

            for(int j = i; j < m; ++j) {
               this.cachedU.setEntry(i, j, luI[j]);
            }
         }
      }

      return this.cachedU;
   }

   public RealMatrix getP() {
      if (this.cachedP == null && !this.singular) {
         int m = this.pivot.length;
         this.cachedP = MatrixUtils.createRealMatrix(m, m);

         for(int i = 0; i < m; ++i) {
            this.cachedP.setEntry(i, this.pivot[i], 1.0D);
         }
      }

      return this.cachedP;
   }

   public int[] getPivot() {
      return (int[])this.pivot.clone();
   }

   public double getDeterminant() {
      if (this.singular) {
         return 0.0D;
      } else {
         int m = this.pivot.length;
         double determinant = this.even ? 1.0D : -1.0D;

         for(int i = 0; i < m; ++i) {
            determinant *= this.lu[i][i];
         }

         return determinant;
      }
   }

   public DecompositionSolver getSolver() {
      return new LUDecomposition.Solver(this.lu, this.pivot, this.singular);
   }

   private static class Solver implements DecompositionSolver {
      private final double[][] lu;
      private final int[] pivot;
      private final boolean singular;

      private Solver(double[][] lu, int[] pivot, boolean singular) {
         this.lu = lu;
         this.pivot = pivot;
         this.singular = singular;
      }

      public boolean isNonSingular() {
         return !this.singular;
      }

      public RealVector solve(RealVector b) {
         int m = this.pivot.length;
         if (b.getDimension() != m) {
            throw new DimensionMismatchException(b.getDimension(), m);
         } else if (this.singular) {
            throw new SingularMatrixException();
         } else {
            double[] bp = new double[m];

            int col;
            for(col = 0; col < m; ++col) {
               bp[col] = b.getEntry(this.pivot[col]);
            }

            double bpCol;
            int i;
            for(col = 0; col < m; ++col) {
               bpCol = bp[col];

               for(i = col + 1; i < m; ++i) {
                  bp[i] -= bpCol * this.lu[i][col];
               }
            }

            for(col = m - 1; col >= 0; --col) {
               bp[col] /= this.lu[col][col];
               bpCol = bp[col];

               for(i = 0; i < col; ++i) {
                  bp[i] -= bpCol * this.lu[i][col];
               }
            }

            return new ArrayRealVector(bp, false);
         }
      }

      public RealMatrix solve(RealMatrix b) {
         int m = this.pivot.length;
         if (b.getRowDimension() != m) {
            throw new DimensionMismatchException(b.getRowDimension(), m);
         } else if (this.singular) {
            throw new SingularMatrixException();
         } else {
            int nColB = b.getColumnDimension();
            double[][] bp = new double[m][nColB];

            int col;
            double[] bpCol;
            int i;
            for(col = 0; col < m; ++col) {
               bpCol = bp[col];
               i = this.pivot[col];

               for(int col = 0; col < nColB; ++col) {
                  bpCol[col] = b.getEntry(i, col);
               }
            }

            for(col = 0; col < m; ++col) {
               bpCol = bp[col];

               for(i = col + 1; i < m; ++i) {
                  double[] bpI = bp[i];
                  double luICol = this.lu[i][col];

                  for(int j = 0; j < nColB; ++j) {
                     bpI[j] -= bpCol[j] * luICol;
                  }
               }
            }

            for(col = m - 1; col >= 0; --col) {
               bpCol = bp[col];
               double luDiag = this.lu[col][col];

               int i;
               for(i = 0; i < nColB; ++i) {
                  bpCol[i] /= luDiag;
               }

               for(i = 0; i < col; ++i) {
                  double[] bpI = bp[i];
                  double luICol = this.lu[i][col];

                  for(int j = 0; j < nColB; ++j) {
                     bpI[j] -= bpCol[j] * luICol;
                  }
               }
            }

            return new Array2DRowRealMatrix(bp, false);
         }
      }

      public RealMatrix getInverse() {
         return this.solve(MatrixUtils.createRealIdentityMatrix(this.pivot.length));
      }

      // $FF: synthetic method
      Solver(double[][] x0, int[] x1, boolean x2, Object x3) {
         this(x0, x1, x2);
      }
   }
}
