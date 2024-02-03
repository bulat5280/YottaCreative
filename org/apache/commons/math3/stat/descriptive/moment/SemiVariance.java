package org.apache.commons.math3.stat.descriptive.moment;

import java.io.Serializable;
import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.exception.NullArgumentException;
import org.apache.commons.math3.stat.descriptive.AbstractUnivariateStatistic;
import org.apache.commons.math3.util.MathUtils;

public class SemiVariance extends AbstractUnivariateStatistic implements Serializable {
   public static final SemiVariance.Direction UPSIDE_VARIANCE;
   public static final SemiVariance.Direction DOWNSIDE_VARIANCE;
   private static final long serialVersionUID = -2653430366886024994L;
   private boolean biasCorrected = true;
   private SemiVariance.Direction varianceDirection;

   public SemiVariance() {
      this.varianceDirection = SemiVariance.Direction.DOWNSIDE;
   }

   public SemiVariance(boolean biasCorrected) {
      this.varianceDirection = SemiVariance.Direction.DOWNSIDE;
      this.biasCorrected = biasCorrected;
   }

   public SemiVariance(SemiVariance.Direction direction) {
      this.varianceDirection = SemiVariance.Direction.DOWNSIDE;
      this.varianceDirection = direction;
   }

   public SemiVariance(boolean corrected, SemiVariance.Direction direction) {
      this.varianceDirection = SemiVariance.Direction.DOWNSIDE;
      this.biasCorrected = corrected;
      this.varianceDirection = direction;
   }

   public SemiVariance(SemiVariance original) throws NullArgumentException {
      this.varianceDirection = SemiVariance.Direction.DOWNSIDE;
      copy(original, this);
   }

   public SemiVariance copy() {
      SemiVariance result = new SemiVariance();
      copy(this, result);
      return result;
   }

   public static void copy(SemiVariance source, SemiVariance dest) throws NullArgumentException {
      MathUtils.checkNotNull(source);
      MathUtils.checkNotNull(dest);
      dest.setData(source.getDataRef());
      dest.biasCorrected = source.biasCorrected;
      dest.varianceDirection = source.varianceDirection;
   }

   public double evaluate(double[] values, int start, int length) throws MathIllegalArgumentException {
      double m = (new Mean()).evaluate(values, start, length);
      return this.evaluate(values, m, this.varianceDirection, this.biasCorrected, 0, values.length);
   }

   public double evaluate(double[] values, SemiVariance.Direction direction) throws MathIllegalArgumentException {
      double m = (new Mean()).evaluate(values);
      return this.evaluate(values, m, direction, this.biasCorrected, 0, values.length);
   }

   public double evaluate(double[] values, double cutoff) throws MathIllegalArgumentException {
      return this.evaluate(values, cutoff, this.varianceDirection, this.biasCorrected, 0, values.length);
   }

   public double evaluate(double[] values, double cutoff, SemiVariance.Direction direction) throws MathIllegalArgumentException {
      return this.evaluate(values, cutoff, direction, this.biasCorrected, 0, values.length);
   }

   public double evaluate(double[] values, double cutoff, SemiVariance.Direction direction, boolean corrected, int start, int length) throws MathIllegalArgumentException {
      this.test(values, start, length);
      if (values.length == 0) {
         return Double.NaN;
      } else if (values.length == 1) {
         return 0.0D;
      } else {
         boolean booleanDirection = direction.getDirection();
         double dev = 0.0D;
         double sumsq = 0.0D;

         for(int i = start; i < length; ++i) {
            if (values[i] > cutoff == booleanDirection) {
               dev = values[i] - cutoff;
               sumsq += dev * dev;
            }
         }

         if (corrected) {
            return sumsq / ((double)length - 1.0D);
         } else {
            return sumsq / (double)length;
         }
      }
   }

   public boolean isBiasCorrected() {
      return this.biasCorrected;
   }

   public void setBiasCorrected(boolean biasCorrected) {
      this.biasCorrected = biasCorrected;
   }

   public SemiVariance.Direction getVarianceDirection() {
      return this.varianceDirection;
   }

   public void setVarianceDirection(SemiVariance.Direction varianceDirection) {
      this.varianceDirection = varianceDirection;
   }

   static {
      UPSIDE_VARIANCE = SemiVariance.Direction.UPSIDE;
      DOWNSIDE_VARIANCE = SemiVariance.Direction.DOWNSIDE;
   }

   public static enum Direction {
      UPSIDE(true),
      DOWNSIDE(false);

      private boolean direction;

      private Direction(boolean b) {
         this.direction = b;
      }

      boolean getDirection() {
         return this.direction;
      }
   }
}
