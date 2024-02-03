package org.apache.commons.math3.complex;

import java.io.Serializable;
import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.exception.MathIllegalStateException;
import org.apache.commons.math3.exception.OutOfRangeException;
import org.apache.commons.math3.exception.ZeroException;
import org.apache.commons.math3.exception.util.LocalizedFormats;
import org.apache.commons.math3.util.FastMath;

public class RootsOfUnity implements Serializable {
   private static final long serialVersionUID = 20120201L;
   private int omegaCount = 0;
   private double[] omegaReal = null;
   private double[] omegaImaginaryCounterClockwise = null;
   private double[] omegaImaginaryClockwise = null;
   private boolean isCounterClockWise = true;

   public synchronized boolean isCounterClockWise() throws MathIllegalStateException {
      if (this.omegaCount == 0) {
         throw new MathIllegalStateException(LocalizedFormats.ROOTS_OF_UNITY_NOT_COMPUTED_YET, new Object[0]);
      } else {
         return this.isCounterClockWise;
      }
   }

   public synchronized void computeRoots(int n) throws ZeroException {
      if (n == 0) {
         throw new ZeroException(LocalizedFormats.CANNOT_COMPUTE_0TH_ROOT_OF_UNITY, new Object[0]);
      } else {
         this.isCounterClockWise = n > 0;
         int absN = FastMath.abs(n);
         if (absN != this.omegaCount) {
            double t = 6.283185307179586D / (double)absN;
            double cosT = FastMath.cos(t);
            double sinT = FastMath.sin(t);
            this.omegaReal = new double[absN];
            this.omegaImaginaryCounterClockwise = new double[absN];
            this.omegaImaginaryClockwise = new double[absN];
            this.omegaReal[0] = 1.0D;
            this.omegaImaginaryCounterClockwise[0] = 0.0D;
            this.omegaImaginaryClockwise[0] = 0.0D;

            for(int i = 1; i < absN; ++i) {
               this.omegaReal[i] = this.omegaReal[i - 1] * cosT - this.omegaImaginaryCounterClockwise[i - 1] * sinT;
               this.omegaImaginaryCounterClockwise[i] = this.omegaReal[i - 1] * sinT + this.omegaImaginaryCounterClockwise[i - 1] * cosT;
               this.omegaImaginaryClockwise[i] = -this.omegaImaginaryCounterClockwise[i];
            }

            this.omegaCount = absN;
         }
      }
   }

   public synchronized double getReal(int k) throws MathIllegalStateException, MathIllegalArgumentException {
      if (this.omegaCount == 0) {
         throw new MathIllegalStateException(LocalizedFormats.ROOTS_OF_UNITY_NOT_COMPUTED_YET, new Object[0]);
      } else if (k >= 0 && k < this.omegaCount) {
         return this.omegaReal[k];
      } else {
         throw new OutOfRangeException(LocalizedFormats.OUT_OF_RANGE_ROOT_OF_UNITY_INDEX, k, 0, this.omegaCount - 1);
      }
   }

   public synchronized double getImaginary(int k) throws MathIllegalStateException, OutOfRangeException {
      if (this.omegaCount == 0) {
         throw new MathIllegalStateException(LocalizedFormats.ROOTS_OF_UNITY_NOT_COMPUTED_YET, new Object[0]);
      } else if (k >= 0 && k < this.omegaCount) {
         return this.isCounterClockWise ? this.omegaImaginaryCounterClockwise[k] : this.omegaImaginaryClockwise[k];
      } else {
         throw new OutOfRangeException(LocalizedFormats.OUT_OF_RANGE_ROOT_OF_UNITY_INDEX, k, 0, this.omegaCount - 1);
      }
   }

   public synchronized int getNumberOfRoots() {
      return this.omegaCount;
   }
}
