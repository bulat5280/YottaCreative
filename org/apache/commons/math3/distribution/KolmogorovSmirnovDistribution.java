package org.apache.commons.math3.distribution;

import java.io.Serializable;
import org.apache.commons.math3.exception.MathArithmeticException;
import org.apache.commons.math3.exception.NotStrictlyPositiveException;
import org.apache.commons.math3.exception.NumberIsTooLargeException;
import org.apache.commons.math3.exception.util.LocalizedFormats;
import org.apache.commons.math3.fraction.BigFraction;
import org.apache.commons.math3.fraction.BigFractionField;
import org.apache.commons.math3.fraction.FractionConversionException;
import org.apache.commons.math3.linear.Array2DRowFieldMatrix;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.FieldMatrix;
import org.apache.commons.math3.linear.RealMatrix;

public class KolmogorovSmirnovDistribution implements Serializable {
   private static final long serialVersionUID = -4670676796862967187L;
   private int n;

   public KolmogorovSmirnovDistribution(int n) throws NotStrictlyPositiveException {
      if (n <= 0) {
         throw new NotStrictlyPositiveException(LocalizedFormats.NOT_POSITIVE_NUMBER_OF_SAMPLES, n);
      } else {
         this.n = n;
      }
   }

   public double cdf(double d) throws MathArithmeticException {
      return this.cdf(d, false);
   }

   public double cdfExact(double d) throws MathArithmeticException {
      return this.cdf(d, true);
   }

   public double cdf(double d, boolean exact) throws MathArithmeticException {
      double ninv = 1.0D / (double)this.n;
      double ninvhalf = 0.5D * ninv;
      if (d <= ninvhalf) {
         return 0.0D;
      } else if (ninvhalf < d && d <= ninv) {
         double res = 1.0D;
         double f = 2.0D * d - ninv;

         for(int i = 1; i <= this.n; ++i) {
            res *= (double)i * f;
         }

         return res;
      } else if (1.0D - ninv <= d && d < 1.0D) {
         return 1.0D - 2.0D * Math.pow(1.0D - d, (double)this.n);
      } else if (1.0D <= d) {
         return 1.0D;
      } else {
         return exact ? this.exactK(d) : this.roundedK(d);
      }
   }

   private double exactK(double d) throws MathArithmeticException {
      int k = (int)Math.ceil((double)this.n * d);
      FieldMatrix<BigFraction> H = this.createH(d);
      FieldMatrix<BigFraction> Hpower = H.power(this.n);
      BigFraction pFrac = (BigFraction)Hpower.getEntry(k - 1, k - 1);

      for(int i = 1; i <= this.n; ++i) {
         pFrac = pFrac.multiply(i).divide(this.n);
      }

      return pFrac.bigDecimalValue(20, 4).doubleValue();
   }

   private double roundedK(double d) throws MathArithmeticException {
      int k = (int)Math.ceil((double)this.n * d);
      FieldMatrix<BigFraction> HBigFraction = this.createH(d);
      int m = HBigFraction.getRowDimension();
      RealMatrix H = new Array2DRowRealMatrix(m, m);

      for(int i = 0; i < m; ++i) {
         for(int j = 0; j < m; ++j) {
            H.setEntry(i, j, ((BigFraction)HBigFraction.getEntry(i, j)).doubleValue());
         }
      }

      RealMatrix Hpower = H.power(this.n);
      double pFrac = Hpower.getEntry(k - 1, k - 1);

      for(int i = 1; i <= this.n; ++i) {
         pFrac *= (double)i / (double)this.n;
      }

      return pFrac;
   }

   private FieldMatrix<BigFraction> createH(double d) throws NumberIsTooLargeException, FractionConversionException {
      int k = (int)Math.ceil((double)this.n * d);
      int m = 2 * k - 1;
      double hDouble = (double)k - (double)this.n * d;
      if (hDouble >= 1.0D) {
         throw new NumberIsTooLargeException(hDouble, 1.0D, false);
      } else {
         BigFraction h = null;

         try {
            h = new BigFraction(hDouble, 1.0E-20D, 10000);
         } catch (FractionConversionException var14) {
            try {
               h = new BigFraction(hDouble, 1.0E-10D, 10000);
            } catch (FractionConversionException var13) {
               h = new BigFraction(hDouble, 1.0E-5D, 10000);
            }
         }

         BigFraction[][] Hdata = new BigFraction[m][m];

         int i;
         for(int i = 0; i < m; ++i) {
            for(i = 0; i < m; ++i) {
               if (i - i + 1 < 0) {
                  Hdata[i][i] = BigFraction.ZERO;
               } else {
                  Hdata[i][i] = BigFraction.ONE;
               }
            }
         }

         BigFraction[] hPowers = new BigFraction[m];
         hPowers[0] = h;

         for(i = 1; i < m; ++i) {
            hPowers[i] = h.multiply(hPowers[i - 1]);
         }

         for(i = 0; i < m; ++i) {
            Hdata[i][0] = Hdata[i][0].subtract(hPowers[i]);
            Hdata[m - 1][i] = Hdata[m - 1][i].subtract(hPowers[m - i - 1]);
         }

         if (h.compareTo(BigFraction.ONE_HALF) == 1) {
            Hdata[m - 1][0] = Hdata[m - 1][0].add(h.multiply(2).subtract(1).pow(m));
         }

         for(i = 0; i < m; ++i) {
            for(int j = 0; j < i + 1; ++j) {
               if (i - j + 1 > 0) {
                  for(int g = 2; g <= i - j + 1; ++g) {
                     Hdata[i][j] = Hdata[i][j].divide(g);
                  }
               }
            }
         }

         return new Array2DRowFieldMatrix(BigFractionField.getInstance(), Hdata);
      }
   }
}
