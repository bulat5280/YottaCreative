package org.apache.commons.math3.linear;

import org.apache.commons.math3.Field;
import org.apache.commons.math3.FieldElement;
import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.util.MathArrays;

public class FieldLUDecomposition<T extends FieldElement<T>> {
   private final Field<T> field;
   private T[][] lu;
   private int[] pivot;
   private boolean even;
   private boolean singular;
   private FieldMatrix<T> cachedL;
   private FieldMatrix<T> cachedU;
   private FieldMatrix<T> cachedP;

   public FieldLUDecomposition(FieldMatrix<T> matrix) {
      if (!matrix.isSquare()) {
         throw new NonSquareMatrixException(matrix.getRowDimension(), matrix.getColumnDimension());
      } else {
         int m = matrix.getColumnDimension();
         this.field = matrix.getField();
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
            T sum = (FieldElement)this.field.getZero();

            int nonZero;
            int row;
            for(nonZero = 0; nonZero < col; ++nonZero) {
               T[] luRow = this.lu[nonZero];
               sum = luRow[col];

               for(row = 0; row < nonZero; ++row) {
                  sum = (FieldElement)sum.subtract(luRow[row].multiply(this.lu[row][col]));
               }

               luRow[col] = sum;
            }

            nonZero = col;

            for(int row = col; row < m; ++row) {
               T[] luRow = this.lu[row];
               sum = luRow[col];

               for(int i = 0; i < col; ++i) {
                  sum = (FieldElement)sum.subtract(luRow[i].multiply(this.lu[i][col]));
               }

               luRow[col] = sum;
               if (this.lu[nonZero][col].equals(this.field.getZero())) {
                  ++nonZero;
               }
            }

            if (nonZero >= m) {
               this.singular = true;
               return;
            }

            FieldElement luDiag;
            if (nonZero != col) {
               luDiag = (FieldElement)this.field.getZero();

               for(row = 0; row < m; ++row) {
                  luDiag = this.lu[nonZero][row];
                  this.lu[nonZero][row] = this.lu[col][row];
                  this.lu[col][row] = luDiag;
               }

               row = this.pivot[nonZero];
               this.pivot[nonZero] = this.pivot[col];
               this.pivot[col] = row;
               this.even = !this.even;
            }

            luDiag = this.lu[col][col];

            for(row = col + 1; row < m; ++row) {
               T[] luRow = this.lu[row];
               luRow[col] = (FieldElement)luRow[col].divide(luDiag);
            }
         }

      }
   }

   public FieldMatrix<T> getL() {
      if (this.cachedL == null && !this.singular) {
         int m = this.pivot.length;
         this.cachedL = new Array2DRowFieldMatrix(this.field, m, m);

         for(int i = 0; i < m; ++i) {
            T[] luI = this.lu[i];

            for(int j = 0; j < i; ++j) {
               this.cachedL.setEntry(i, j, luI[j]);
            }

            this.cachedL.setEntry(i, i, (FieldElement)this.field.getOne());
         }
      }

      return this.cachedL;
   }

   public FieldMatrix<T> getU() {
      if (this.cachedU == null && !this.singular) {
         int m = this.pivot.length;
         this.cachedU = new Array2DRowFieldMatrix(this.field, m, m);

         for(int i = 0; i < m; ++i) {
            T[] luI = this.lu[i];

            for(int j = i; j < m; ++j) {
               this.cachedU.setEntry(i, j, luI[j]);
            }
         }
      }

      return this.cachedU;
   }

   public FieldMatrix<T> getP() {
      if (this.cachedP == null && !this.singular) {
         int m = this.pivot.length;
         this.cachedP = new Array2DRowFieldMatrix(this.field, m, m);

         for(int i = 0; i < m; ++i) {
            this.cachedP.setEntry(i, this.pivot[i], (FieldElement)this.field.getOne());
         }
      }

      return this.cachedP;
   }

   public int[] getPivot() {
      return (int[])this.pivot.clone();
   }

   public T getDeterminant() {
      if (this.singular) {
         return (FieldElement)this.field.getZero();
      } else {
         int m = this.pivot.length;
         T determinant = this.even ? (FieldElement)this.field.getOne() : (FieldElement)((FieldElement)this.field.getZero()).subtract(this.field.getOne());

         for(int i = 0; i < m; ++i) {
            determinant = (FieldElement)determinant.multiply(this.lu[i][i]);
         }

         return determinant;
      }
   }

   public FieldDecompositionSolver<T> getSolver() {
      return new FieldLUDecomposition.Solver(this.field, this.lu, this.pivot, this.singular);
   }

   private static class Solver<T extends FieldElement<T>> implements FieldDecompositionSolver<T> {
      private final Field<T> field;
      private final T[][] lu;
      private final int[] pivot;
      private final boolean singular;

      private Solver(Field<T> field, T[][] lu, int[] pivot, boolean singular) {
         this.field = field;
         this.lu = lu;
         this.pivot = pivot;
         this.singular = singular;
      }

      public boolean isNonSingular() {
         return !this.singular;
      }

      public FieldVector<T> solve(FieldVector<T> b) {
         try {
            return this.solve((ArrayFieldVector)b);
         } catch (ClassCastException var8) {
            int m = this.pivot.length;
            if (b.getDimension() != m) {
               throw new DimensionMismatchException(b.getDimension(), m);
            } else if (this.singular) {
               throw new SingularMatrixException();
            } else {
               T[] bp = (FieldElement[])MathArrays.buildArray(this.field, m);

               int col;
               for(col = 0; col < m; ++col) {
                  bp[col] = b.getEntry(this.pivot[col]);
               }

               FieldElement bpCol;
               int i;
               for(col = 0; col < m; ++col) {
                  bpCol = bp[col];

                  for(i = col + 1; i < m; ++i) {
                     bp[i] = (FieldElement)bp[i].subtract(bpCol.multiply(this.lu[i][col]));
                  }
               }

               for(col = m - 1; col >= 0; --col) {
                  bp[col] = (FieldElement)bp[col].divide(this.lu[col][col]);
                  bpCol = bp[col];

                  for(i = 0; i < col; ++i) {
                     bp[i] = (FieldElement)bp[i].subtract(bpCol.multiply(this.lu[i][col]));
                  }
               }

               return new ArrayFieldVector(this.field, bp, false);
            }
         }
      }

      public ArrayFieldVector<T> solve(ArrayFieldVector<T> b) {
         int m = this.pivot.length;
         int length = b.getDimension();
         if (length != m) {
            throw new DimensionMismatchException(length, m);
         } else if (this.singular) {
            throw new SingularMatrixException();
         } else {
            T[] bp = (FieldElement[])MathArrays.buildArray(this.field, m);

            int col;
            for(col = 0; col < m; ++col) {
               bp[col] = b.getEntry(this.pivot[col]);
            }

            FieldElement bpCol;
            int i;
            for(col = 0; col < m; ++col) {
               bpCol = bp[col];

               for(i = col + 1; i < m; ++i) {
                  bp[i] = (FieldElement)bp[i].subtract(bpCol.multiply(this.lu[i][col]));
               }
            }

            for(col = m - 1; col >= 0; --col) {
               bp[col] = (FieldElement)bp[col].divide(this.lu[col][col]);
               bpCol = bp[col];

               for(i = 0; i < col; ++i) {
                  bp[i] = (FieldElement)bp[i].subtract(bpCol.multiply(this.lu[i][col]));
               }
            }

            return new ArrayFieldVector(bp, false);
         }
      }

      public FieldMatrix<T> solve(FieldMatrix<T> b) {
         int m = this.pivot.length;
         if (b.getRowDimension() != m) {
            throw new DimensionMismatchException(b.getRowDimension(), m);
         } else if (this.singular) {
            throw new SingularMatrixException();
         } else {
            int nColB = b.getColumnDimension();
            T[][] bp = (FieldElement[][])MathArrays.buildArray(this.field, m, nColB);

            int col;
            FieldElement[] bpCol;
            int i;
            int i;
            for(col = 0; col < m; ++col) {
               bpCol = bp[col];
               i = this.pivot[col];

               for(i = 0; i < nColB; ++i) {
                  bpCol[i] = b.getEntry(i, i);
               }
            }

            for(col = 0; col < m; ++col) {
               bpCol = bp[col];

               for(i = col + 1; i < m; ++i) {
                  T[] bpI = bp[i];
                  T luICol = this.lu[i][col];

                  for(int j = 0; j < nColB; ++j) {
                     bpI[j] = (FieldElement)bpI[j].subtract(bpCol[j].multiply(luICol));
                  }
               }
            }

            for(col = m - 1; col >= 0; --col) {
               bpCol = bp[col];
               T luDiag = this.lu[col][col];

               for(i = 0; i < nColB; ++i) {
                  bpCol[i] = (FieldElement)bpCol[i].divide(luDiag);
               }

               for(i = 0; i < col; ++i) {
                  T[] bpI = bp[i];
                  T luICol = this.lu[i][col];

                  for(int j = 0; j < nColB; ++j) {
                     bpI[j] = (FieldElement)bpI[j].subtract(bpCol[j].multiply(luICol));
                  }
               }
            }

            return new Array2DRowFieldMatrix(this.field, bp, false);
         }
      }

      public FieldMatrix<T> getInverse() {
         int m = this.pivot.length;
         T one = (FieldElement)this.field.getOne();
         FieldMatrix<T> identity = new Array2DRowFieldMatrix(this.field, m, m);

         for(int i = 0; i < m; ++i) {
            identity.setEntry(i, i, one);
         }

         return this.solve((FieldMatrix)identity);
      }

      // $FF: synthetic method
      Solver(Field x0, FieldElement[][] x1, int[] x2, boolean x3, Object x4) {
         this(x0, x1, x2, x3);
      }
   }
}
