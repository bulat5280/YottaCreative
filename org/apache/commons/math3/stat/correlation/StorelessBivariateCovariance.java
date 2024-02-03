package org.apache.commons.math3.stat.correlation;

import org.apache.commons.math3.exception.NumberIsTooSmallException;
import org.apache.commons.math3.exception.util.LocalizedFormats;

class StorelessBivariateCovariance {
   private double meanX;
   private double meanY;
   private double n;
   private double covarianceNumerator;
   private boolean biasCorrected;

   public StorelessBivariateCovariance() {
      this(true);
   }

   public StorelessBivariateCovariance(boolean biasCorrection) {
      this.meanX = this.meanY = 0.0D;
      this.n = 0.0D;
      this.covarianceNumerator = 0.0D;
      this.biasCorrected = biasCorrection;
   }

   public void increment(double x, double y) {
      ++this.n;
      double deltaX = x - this.meanX;
      double deltaY = y - this.meanY;
      this.meanX += deltaX / this.n;
      this.meanY += deltaY / this.n;
      this.covarianceNumerator += (this.n - 1.0D) / this.n * deltaX * deltaY;
   }

   public double getN() {
      return this.n;
   }

   public double getResult() throws NumberIsTooSmallException {
      if (this.n < 2.0D) {
         throw new NumberIsTooSmallException(LocalizedFormats.INSUFFICIENT_DIMENSION, this.n, 2, true);
      } else {
         return this.biasCorrected ? this.covarianceNumerator / (this.n - 1.0D) : this.covarianceNumerator / this.n;
      }
   }
}
