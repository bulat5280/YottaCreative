package org.apache.commons.math3.stat.descriptive;

import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.exception.NullArgumentException;
import org.apache.commons.math3.exception.util.LocalizedFormats;
import org.apache.commons.math3.util.MathUtils;
import org.apache.commons.math3.util.Precision;

public abstract class AbstractStorelessUnivariateStatistic extends AbstractUnivariateStatistic implements StorelessUnivariateStatistic {
   public double evaluate(double[] values) throws MathIllegalArgumentException {
      if (values == null) {
         throw new NullArgumentException(LocalizedFormats.INPUT_ARRAY, new Object[0]);
      } else {
         return this.evaluate(values, 0, values.length);
      }
   }

   public double evaluate(double[] values, int begin, int length) throws MathIllegalArgumentException {
      if (this.test(values, begin, length)) {
         this.clear();
         this.incrementAll(values, begin, length);
      }

      return this.getResult();
   }

   public abstract StorelessUnivariateStatistic copy();

   public abstract void clear();

   public abstract double getResult();

   public abstract void increment(double var1);

   public void incrementAll(double[] values) throws MathIllegalArgumentException {
      if (values == null) {
         throw new NullArgumentException(LocalizedFormats.INPUT_ARRAY, new Object[0]);
      } else {
         this.incrementAll(values, 0, values.length);
      }
   }

   public void incrementAll(double[] values, int begin, int length) throws MathIllegalArgumentException {
      if (this.test(values, begin, length)) {
         int k = begin + length;

         for(int i = begin; i < k; ++i) {
            this.increment(values[i]);
         }
      }

   }

   public boolean equals(Object object) {
      if (object == this) {
         return true;
      } else if (!(object instanceof AbstractStorelessUnivariateStatistic)) {
         return false;
      } else {
         AbstractStorelessUnivariateStatistic stat = (AbstractStorelessUnivariateStatistic)object;
         return Precision.equalsIncludingNaN(stat.getResult(), this.getResult()) && Precision.equalsIncludingNaN((float)stat.getN(), (float)this.getN());
      }
   }

   public int hashCode() {
      return 31 * (31 + MathUtils.hash(this.getResult())) + MathUtils.hash((double)this.getN());
   }
}
