package org.apache.commons.math3.linear;

import org.apache.commons.math3.analysis.function.Sqrt;
import org.apache.commons.math3.util.MathArrays;

public class JacobiPreconditioner extends RealLinearOperator {
   private final ArrayRealVector diag;

   public JacobiPreconditioner(double[] diag, boolean deep) {
      this.diag = new ArrayRealVector(diag, deep);
   }

   public static JacobiPreconditioner create(RealLinearOperator a) throws NonSquareOperatorException {
      int n = a.getColumnDimension();
      if (a.getRowDimension() != n) {
         throw new NonSquareOperatorException(a.getRowDimension(), n);
      } else {
         double[] diag = new double[n];
         int i;
         if (a instanceof AbstractRealMatrix) {
            AbstractRealMatrix m = (AbstractRealMatrix)a;

            for(i = 0; i < n; ++i) {
               diag[i] = m.getEntry(i, i);
            }
         } else {
            ArrayRealVector x = new ArrayRealVector(n);

            for(i = 0; i < n; ++i) {
               x.set(0.0D);
               x.setEntry(i, 1.0D);
               diag[i] = a.operate(x).getEntry(i);
            }
         }

         return new JacobiPreconditioner(diag, false);
      }
   }

   public int getColumnDimension() {
      return this.diag.getDimension();
   }

   public int getRowDimension() {
      return this.diag.getDimension();
   }

   public RealVector operate(RealVector x) {
      return new ArrayRealVector(MathArrays.ebeDivide(x.toArray(), this.diag.toArray()), false);
   }

   public RealLinearOperator sqrt() {
      final RealVector sqrtDiag = this.diag.map(new Sqrt());
      return new RealLinearOperator() {
         public RealVector operate(RealVector x) {
            return new ArrayRealVector(MathArrays.ebeDivide(x.toArray(), sqrtDiag.toArray()), false);
         }

         public int getRowDimension() {
            return sqrtDiag.getDimension();
         }

         public int getColumnDimension() {
            return sqrtDiag.getDimension();
         }
      };
   }
}
