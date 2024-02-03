package org.apache.commons.math3.stat.regression;

import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.QRDecomposition;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.apache.commons.math3.stat.StatUtils;
import org.apache.commons.math3.stat.descriptive.moment.SecondMoment;

public class OLSMultipleLinearRegression extends AbstractMultipleLinearRegression {
   private QRDecomposition qr = null;

   public void newSampleData(double[] y, double[][] x) throws MathIllegalArgumentException {
      this.validateSampleData(x, y);
      this.newYSampleData(y);
      this.newXSampleData(x);
   }

   public void newSampleData(double[] data, int nobs, int nvars) {
      super.newSampleData(data, nobs, nvars);
      this.qr = new QRDecomposition(this.getX());
   }

   public RealMatrix calculateHat() {
      RealMatrix Q = this.qr.getQ();
      int p = this.qr.getR().getColumnDimension();
      int n = Q.getColumnDimension();
      Array2DRowRealMatrix augI = new Array2DRowRealMatrix(n, n);
      double[][] augIData = augI.getDataRef();

      for(int i = 0; i < n; ++i) {
         for(int j = 0; j < n; ++j) {
            if (i == j && i < p) {
               augIData[i][j] = 1.0D;
            } else {
               augIData[i][j] = 0.0D;
            }
         }
      }

      return Q.multiply(augI).multiply(Q.transpose());
   }

   public double calculateTotalSumOfSquares() throws MathIllegalArgumentException {
      return this.isNoIntercept() ? StatUtils.sumSq(this.getY().toArray()) : (new SecondMoment()).evaluate(this.getY().toArray());
   }

   public double calculateResidualSumOfSquares() {
      RealVector residuals = this.calculateResiduals();
      return residuals.dotProduct(residuals);
   }

   public double calculateRSquared() throws MathIllegalArgumentException {
      return 1.0D - this.calculateResidualSumOfSquares() / this.calculateTotalSumOfSquares();
   }

   public double calculateAdjustedRSquared() throws MathIllegalArgumentException {
      double n = (double)this.getX().getRowDimension();
      return this.isNoIntercept() ? 1.0D - (1.0D - this.calculateRSquared()) * (n / (n - (double)this.getX().getColumnDimension())) : 1.0D - this.calculateResidualSumOfSquares() * (n - 1.0D) / (this.calculateTotalSumOfSquares() * (n - (double)this.getX().getColumnDimension()));
   }

   protected void newXSampleData(double[][] x) {
      super.newXSampleData(x);
      this.qr = new QRDecomposition(this.getX());
   }

   protected RealVector calculateBeta() {
      return this.qr.getSolver().solve(this.getY());
   }

   protected RealMatrix calculateBetaVariance() {
      int p = this.getX().getColumnDimension();
      RealMatrix Raug = this.qr.getR().getSubMatrix(0, p - 1, 0, p - 1);
      RealMatrix Rinv = (new LUDecomposition(Raug)).getSolver().getInverse();
      return Rinv.multiply(Rinv.transpose());
   }
}
