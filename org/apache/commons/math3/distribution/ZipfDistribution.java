package org.apache.commons.math3.distribution;

import org.apache.commons.math3.exception.NotStrictlyPositiveException;
import org.apache.commons.math3.exception.util.LocalizedFormats;
import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.random.Well19937c;
import org.apache.commons.math3.util.FastMath;

public class ZipfDistribution extends AbstractIntegerDistribution {
   private static final long serialVersionUID = -140627372283420404L;
   private final int numberOfElements;
   private final double exponent;
   private double numericalMean;
   private boolean numericalMeanIsCalculated;
   private double numericalVariance;
   private boolean numericalVarianceIsCalculated;

   public ZipfDistribution(int numberOfElements, double exponent) {
      this(new Well19937c(), numberOfElements, exponent);
   }

   public ZipfDistribution(RandomGenerator rng, int numberOfElements, double exponent) throws NotStrictlyPositiveException {
      super(rng);
      this.numericalMean = Double.NaN;
      this.numericalMeanIsCalculated = false;
      this.numericalVariance = Double.NaN;
      this.numericalVarianceIsCalculated = false;
      if (numberOfElements <= 0) {
         throw new NotStrictlyPositiveException(LocalizedFormats.DIMENSION, numberOfElements);
      } else if (exponent <= 0.0D) {
         throw new NotStrictlyPositiveException(LocalizedFormats.EXPONENT, exponent);
      } else {
         this.numberOfElements = numberOfElements;
         this.exponent = exponent;
      }
   }

   public int getNumberOfElements() {
      return this.numberOfElements;
   }

   public double getExponent() {
      return this.exponent;
   }

   public double probability(int x) {
      return x > 0 && x <= this.numberOfElements ? 1.0D / FastMath.pow((double)x, this.exponent) / this.generalizedHarmonic(this.numberOfElements, this.exponent) : 0.0D;
   }

   public double cumulativeProbability(int x) {
      if (x <= 0) {
         return 0.0D;
      } else {
         return x >= this.numberOfElements ? 1.0D : this.generalizedHarmonic(x, this.exponent) / this.generalizedHarmonic(this.numberOfElements, this.exponent);
      }
   }

   public double getNumericalMean() {
      if (!this.numericalMeanIsCalculated) {
         this.numericalMean = this.calculateNumericalMean();
         this.numericalMeanIsCalculated = true;
      }

      return this.numericalMean;
   }

   protected double calculateNumericalMean() {
      int N = this.getNumberOfElements();
      double s = this.getExponent();
      double Hs1 = this.generalizedHarmonic(N, s - 1.0D);
      double Hs = this.generalizedHarmonic(N, s);
      return Hs1 / Hs;
   }

   public double getNumericalVariance() {
      if (!this.numericalVarianceIsCalculated) {
         this.numericalVariance = this.calculateNumericalVariance();
         this.numericalVarianceIsCalculated = true;
      }

      return this.numericalVariance;
   }

   protected double calculateNumericalVariance() {
      int N = this.getNumberOfElements();
      double s = this.getExponent();
      double Hs2 = this.generalizedHarmonic(N, s - 2.0D);
      double Hs1 = this.generalizedHarmonic(N, s - 1.0D);
      double Hs = this.generalizedHarmonic(N, s);
      return Hs2 / Hs - Hs1 * Hs1 / (Hs * Hs);
   }

   private double generalizedHarmonic(int n, double m) {
      double value = 0.0D;

      for(int k = n; k > 0; --k) {
         value += 1.0D / FastMath.pow((double)k, m);
      }

      return value;
   }

   public int getSupportLowerBound() {
      return 1;
   }

   public int getSupportUpperBound() {
      return this.getNumberOfElements();
   }

   public boolean isSupportConnected() {
      return true;
   }
}
