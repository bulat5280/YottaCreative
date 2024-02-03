package org.apache.commons.math3.stat.descriptive;

import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.exception.NotPositiveException;
import org.apache.commons.math3.exception.NullArgumentException;
import org.apache.commons.math3.exception.NumberIsTooLargeException;
import org.apache.commons.math3.exception.util.LocalizedFormats;

public abstract class AbstractUnivariateStatistic implements UnivariateStatistic {
   private double[] storedData;

   public void setData(double[] values) {
      this.storedData = values == null ? null : (double[])values.clone();
   }

   public double[] getData() {
      return this.storedData == null ? null : (double[])this.storedData.clone();
   }

   protected double[] getDataRef() {
      return this.storedData;
   }

   public void setData(double[] values, int begin, int length) throws MathIllegalArgumentException {
      if (values == null) {
         throw new NullArgumentException(LocalizedFormats.INPUT_ARRAY, new Object[0]);
      } else if (begin < 0) {
         throw new NotPositiveException(LocalizedFormats.START_POSITION, begin);
      } else if (length < 0) {
         throw new NotPositiveException(LocalizedFormats.LENGTH, length);
      } else if (begin + length > values.length) {
         throw new NumberIsTooLargeException(LocalizedFormats.SUBARRAY_ENDS_AFTER_ARRAY_END, begin + length, values.length, true);
      } else {
         this.storedData = new double[length];
         System.arraycopy(values, begin, this.storedData, 0, length);
      }
   }

   public double evaluate() throws MathIllegalArgumentException {
      return this.evaluate(this.storedData);
   }

   public double evaluate(double[] values) throws MathIllegalArgumentException {
      this.test(values, 0, 0);
      return this.evaluate(values, 0, values.length);
   }

   public abstract double evaluate(double[] var1, int var2, int var3) throws MathIllegalArgumentException;

   public abstract UnivariateStatistic copy();

   protected boolean test(double[] values, int begin, int length) throws MathIllegalArgumentException {
      return this.test(values, begin, length, false);
   }

   protected boolean test(double[] values, int begin, int length, boolean allowEmpty) throws MathIllegalArgumentException {
      if (values == null) {
         throw new NullArgumentException(LocalizedFormats.INPUT_ARRAY, new Object[0]);
      } else if (begin < 0) {
         throw new NotPositiveException(LocalizedFormats.START_POSITION, begin);
      } else if (length < 0) {
         throw new NotPositiveException(LocalizedFormats.LENGTH, length);
      } else if (begin + length > values.length) {
         throw new NumberIsTooLargeException(LocalizedFormats.SUBARRAY_ENDS_AFTER_ARRAY_END, begin + length, values.length, true);
      } else {
         return length != 0 || allowEmpty;
      }
   }

   protected boolean test(double[] values, double[] weights, int begin, int length) throws MathIllegalArgumentException {
      return this.test(values, weights, begin, length, false);
   }

   protected boolean test(double[] values, double[] weights, int begin, int length, boolean allowEmpty) throws MathIllegalArgumentException {
      if (weights != null && values != null) {
         if (weights.length != values.length) {
            throw new DimensionMismatchException(weights.length, values.length);
         } else {
            boolean containsPositiveWeight = false;

            for(int i = begin; i < begin + length; ++i) {
               if (Double.isNaN(weights[i])) {
                  throw new MathIllegalArgumentException(LocalizedFormats.NAN_ELEMENT_AT_INDEX, new Object[]{i});
               }

               if (Double.isInfinite(weights[i])) {
                  throw new MathIllegalArgumentException(LocalizedFormats.INFINITE_ARRAY_ELEMENT, new Object[]{weights[i], i});
               }

               if (weights[i] < 0.0D) {
                  throw new MathIllegalArgumentException(LocalizedFormats.NEGATIVE_ELEMENT_AT_INDEX, new Object[]{i, weights[i]});
               }

               if (!containsPositiveWeight && weights[i] > 0.0D) {
                  containsPositiveWeight = true;
               }
            }

            if (!containsPositiveWeight) {
               throw new MathIllegalArgumentException(LocalizedFormats.WEIGHT_AT_LEAST_ONE_NON_ZERO, new Object[0]);
            } else {
               return this.test(values, begin, length, allowEmpty);
            }
         }
      } else {
         throw new NullArgumentException(LocalizedFormats.INPUT_ARRAY, new Object[0]);
      }
   }
}
