package org.apache.commons.math3.stat.descriptive.moment;

import java.io.Serializable;
import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.exception.NullArgumentException;
import org.apache.commons.math3.stat.descriptive.AbstractStorelessUnivariateStatistic;
import org.apache.commons.math3.util.FastMath;
import org.apache.commons.math3.util.MathUtils;

public class Kurtosis extends AbstractStorelessUnivariateStatistic implements Serializable {
   private static final long serialVersionUID = 2784465764798260919L;
   protected FourthMoment moment;
   protected boolean incMoment;

   public Kurtosis() {
      this.incMoment = true;
      this.moment = new FourthMoment();
   }

   public Kurtosis(FourthMoment m4) {
      this.incMoment = false;
      this.moment = m4;
   }

   public Kurtosis(Kurtosis original) throws NullArgumentException {
      copy(original, this);
   }

   public void increment(double d) {
      if (this.incMoment) {
         this.moment.increment(d);
      }

   }

   public double getResult() {
      double kurtosis = Double.NaN;
      if (this.moment.getN() > 3L) {
         double variance = this.moment.m2 / (double)(this.moment.n - 1L);
         if (this.moment.n > 3L && !(variance < 1.0E-19D)) {
            double n = (double)this.moment.n;
            kurtosis = (n * (n + 1.0D) * this.moment.getResult() - 3.0D * this.moment.m2 * this.moment.m2 * (n - 1.0D)) / ((n - 1.0D) * (n - 2.0D) * (n - 3.0D) * variance * variance);
         } else {
            kurtosis = 0.0D;
         }
      }

      return kurtosis;
   }

   public void clear() {
      if (this.incMoment) {
         this.moment.clear();
      }

   }

   public long getN() {
      return this.moment.getN();
   }

   public double evaluate(double[] values, int begin, int length) throws MathIllegalArgumentException {
      double kurt = Double.NaN;
      if (this.test(values, begin, length) && length > 3) {
         Variance variance = new Variance();
         variance.incrementAll(values, begin, length);
         double mean = variance.moment.m1;
         double stdDev = FastMath.sqrt(variance.getResult());
         double accum3 = 0.0D;

         for(int i = begin; i < begin + length; ++i) {
            accum3 += FastMath.pow(values[i] - mean, 4.0D);
         }

         accum3 /= FastMath.pow(stdDev, 4.0D);
         double n0 = (double)length;
         double coefficientOne = n0 * (n0 + 1.0D) / ((n0 - 1.0D) * (n0 - 2.0D) * (n0 - 3.0D));
         double termTwo = 3.0D * FastMath.pow(n0 - 1.0D, 2.0D) / ((n0 - 2.0D) * (n0 - 3.0D));
         kurt = coefficientOne * accum3 - termTwo;
      }

      return kurt;
   }

   public Kurtosis copy() {
      Kurtosis result = new Kurtosis();
      copy(this, result);
      return result;
   }

   public static void copy(Kurtosis source, Kurtosis dest) throws NullArgumentException {
      MathUtils.checkNotNull(source);
      MathUtils.checkNotNull(dest);
      dest.setData(source.getDataRef());
      dest.moment = source.moment.copy();
      dest.incMoment = source.incMoment;
   }
}
