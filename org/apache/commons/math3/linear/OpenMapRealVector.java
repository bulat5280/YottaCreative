package org.apache.commons.math3.linear;

import java.io.Serializable;
import java.util.Iterator;
import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.MathArithmeticException;
import org.apache.commons.math3.exception.NotPositiveException;
import org.apache.commons.math3.exception.OutOfRangeException;
import org.apache.commons.math3.exception.util.LocalizedFormats;
import org.apache.commons.math3.util.FastMath;
import org.apache.commons.math3.util.OpenIntToDoubleHashMap;

/** @deprecated */
@Deprecated
public class OpenMapRealVector extends SparseRealVector implements Serializable {
   public static final double DEFAULT_ZERO_TOLERANCE = 1.0E-12D;
   private static final long serialVersionUID = 8772222695580707260L;
   private final OpenIntToDoubleHashMap entries;
   private final int virtualSize;
   private final double epsilon;

   public OpenMapRealVector() {
      this(0, 1.0E-12D);
   }

   public OpenMapRealVector(int dimension) {
      this(dimension, 1.0E-12D);
   }

   public OpenMapRealVector(int dimension, double epsilon) {
      this.virtualSize = dimension;
      this.entries = new OpenIntToDoubleHashMap(0.0D);
      this.epsilon = epsilon;
   }

   protected OpenMapRealVector(OpenMapRealVector v, int resize) {
      this.virtualSize = v.getDimension() + resize;
      this.entries = new OpenIntToDoubleHashMap(v.entries);
      this.epsilon = v.epsilon;
   }

   public OpenMapRealVector(int dimension, int expectedSize) {
      this(dimension, expectedSize, 1.0E-12D);
   }

   public OpenMapRealVector(int dimension, int expectedSize, double epsilon) {
      this.virtualSize = dimension;
      this.entries = new OpenIntToDoubleHashMap(expectedSize, 0.0D);
      this.epsilon = epsilon;
   }

   public OpenMapRealVector(double[] values) {
      this(values, 1.0E-12D);
   }

   public OpenMapRealVector(double[] values, double epsilon) {
      this.virtualSize = values.length;
      this.entries = new OpenIntToDoubleHashMap(0.0D);
      this.epsilon = epsilon;

      for(int key = 0; key < values.length; ++key) {
         double value = values[key];
         if (!this.isDefaultValue(value)) {
            this.entries.put(key, value);
         }
      }

   }

   public OpenMapRealVector(Double[] values) {
      this(values, 1.0E-12D);
   }

   public OpenMapRealVector(Double[] values, double epsilon) {
      this.virtualSize = values.length;
      this.entries = new OpenIntToDoubleHashMap(0.0D);
      this.epsilon = epsilon;

      for(int key = 0; key < values.length; ++key) {
         double value = values[key];
         if (!this.isDefaultValue(value)) {
            this.entries.put(key, value);
         }
      }

   }

   public OpenMapRealVector(OpenMapRealVector v) {
      this.virtualSize = v.getDimension();
      this.entries = new OpenIntToDoubleHashMap(v.getEntries());
      this.epsilon = v.epsilon;
   }

   public OpenMapRealVector(RealVector v) {
      this.virtualSize = v.getDimension();
      this.entries = new OpenIntToDoubleHashMap(0.0D);
      this.epsilon = 1.0E-12D;

      for(int key = 0; key < this.virtualSize; ++key) {
         double value = v.getEntry(key);
         if (!this.isDefaultValue(value)) {
            this.entries.put(key, value);
         }
      }

   }

   private OpenIntToDoubleHashMap getEntries() {
      return this.entries;
   }

   protected boolean isDefaultValue(double value) {
      return FastMath.abs(value) < this.epsilon;
   }

   public RealVector add(RealVector v) throws DimensionMismatchException {
      this.checkVectorDimensions(v.getDimension());
      return (RealVector)(v instanceof OpenMapRealVector ? this.add((OpenMapRealVector)v) : super.add(v));
   }

   public OpenMapRealVector add(OpenMapRealVector v) throws DimensionMismatchException {
      this.checkVectorDimensions(v.getDimension());
      boolean copyThis = this.entries.size() > v.entries.size();
      OpenMapRealVector res = copyThis ? this.copy() : v.copy();
      OpenIntToDoubleHashMap.Iterator iter = copyThis ? v.entries.iterator() : this.entries.iterator();
      OpenIntToDoubleHashMap randomAccess = copyThis ? this.entries : v.entries;

      while(iter.hasNext()) {
         iter.advance();
         int key = iter.key();
         if (randomAccess.containsKey(key)) {
            res.setEntry(key, randomAccess.get(key) + iter.value());
         } else {
            res.setEntry(key, iter.value());
         }
      }

      return res;
   }

   public OpenMapRealVector append(OpenMapRealVector v) {
      OpenMapRealVector res = new OpenMapRealVector(this, v.getDimension());
      OpenIntToDoubleHashMap.Iterator iter = v.entries.iterator();

      while(iter.hasNext()) {
         iter.advance();
         res.setEntry(iter.key() + this.virtualSize, iter.value());
      }

      return res;
   }

   public OpenMapRealVector append(RealVector v) {
      if (v instanceof OpenMapRealVector) {
         return this.append((OpenMapRealVector)v);
      } else {
         OpenMapRealVector res = new OpenMapRealVector(this, v.getDimension());

         for(int i = 0; i < v.getDimension(); ++i) {
            res.setEntry(i + this.virtualSize, v.getEntry(i));
         }

         return res;
      }
   }

   public OpenMapRealVector append(double d) {
      OpenMapRealVector res = new OpenMapRealVector(this, 1);
      res.setEntry(this.virtualSize, d);
      return res;
   }

   public OpenMapRealVector copy() {
      return new OpenMapRealVector(this);
   }

   /** @deprecated */
   @Deprecated
   public double dotProduct(OpenMapRealVector v) throws DimensionMismatchException {
      return this.dotProduct(v);
   }

   public OpenMapRealVector ebeDivide(RealVector v) throws DimensionMismatchException {
      this.checkVectorDimensions(v.getDimension());
      OpenMapRealVector res = new OpenMapRealVector(this);
      int n = this.getDimension();

      for(int i = 0; i < n; ++i) {
         res.setEntry(i, this.getEntry(i) / v.getEntry(i));
      }

      return res;
   }

   public OpenMapRealVector ebeMultiply(RealVector v) throws DimensionMismatchException {
      this.checkVectorDimensions(v.getDimension());
      OpenMapRealVector res = new OpenMapRealVector(this);
      OpenIntToDoubleHashMap.Iterator iter = this.entries.iterator();

      while(iter.hasNext()) {
         iter.advance();
         res.setEntry(iter.key(), iter.value() * v.getEntry(iter.key()));
      }

      if (v.isNaN() || v.isInfinite()) {
         int n = this.getDimension();

         for(int i = 0; i < n; ++i) {
            double y = v.getEntry(i);
            if (Double.isNaN(y)) {
               res.setEntry(i, Double.NaN);
            } else if (Double.isInfinite(y)) {
               double x = this.getEntry(i);
               res.setEntry(i, x * y);
            }
         }
      }

      return res;
   }

   public OpenMapRealVector getSubVector(int index, int n) throws NotPositiveException, OutOfRangeException {
      this.checkIndex(index);
      if (n < 0) {
         throw new NotPositiveException(LocalizedFormats.NUMBER_OF_ELEMENTS_SHOULD_BE_POSITIVE, n);
      } else {
         this.checkIndex(index + n - 1);
         OpenMapRealVector res = new OpenMapRealVector(n);
         int end = index + n;
         OpenIntToDoubleHashMap.Iterator iter = this.entries.iterator();

         while(iter.hasNext()) {
            iter.advance();
            int key = iter.key();
            if (key >= index && key < end) {
               res.setEntry(key - index, iter.value());
            }
         }

         return res;
      }
   }

   public int getDimension() {
      return this.virtualSize;
   }

   public double getDistance(OpenMapRealVector v) throws DimensionMismatchException {
      this.checkVectorDimensions(v.getDimension());
      OpenIntToDoubleHashMap.Iterator iter = this.entries.iterator();

      double res;
      int key;
      double value;
      for(res = 0.0D; iter.hasNext(); res += value * value) {
         iter.advance();
         key = iter.key();
         value = iter.value() - v.getEntry(key);
      }

      iter = v.getEntries().iterator();

      while(iter.hasNext()) {
         iter.advance();
         key = iter.key();
         if (!this.entries.containsKey(key)) {
            value = iter.value();
            res += value * value;
         }
      }

      return FastMath.sqrt(res);
   }

   public double getDistance(RealVector v) throws DimensionMismatchException {
      this.checkVectorDimensions(v.getDimension());
      return v instanceof OpenMapRealVector ? this.getDistance((OpenMapRealVector)v) : super.getDistance(v);
   }

   public double getEntry(int index) throws OutOfRangeException {
      this.checkIndex(index);
      return this.entries.get(index);
   }

   public double getL1Distance(OpenMapRealVector v) throws DimensionMismatchException {
      this.checkVectorDimensions(v.getDimension());
      double max = 0.0D;

      OpenIntToDoubleHashMap.Iterator iter;
      double delta;
      for(iter = this.entries.iterator(); iter.hasNext(); max += delta) {
         iter.advance();
         delta = FastMath.abs(iter.value() - v.getEntry(iter.key()));
      }

      iter = v.getEntries().iterator();

      while(iter.hasNext()) {
         iter.advance();
         int key = iter.key();
         if (!this.entries.containsKey(key)) {
            double delta = FastMath.abs(iter.value());
            max += FastMath.abs(delta);
         }
      }

      return max;
   }

   public double getL1Distance(RealVector v) throws DimensionMismatchException {
      this.checkVectorDimensions(v.getDimension());
      return v instanceof OpenMapRealVector ? this.getL1Distance((OpenMapRealVector)v) : super.getL1Distance(v);
   }

   private double getLInfDistance(OpenMapRealVector v) throws DimensionMismatchException {
      this.checkVectorDimensions(v.getDimension());
      double max = 0.0D;
      OpenIntToDoubleHashMap.Iterator iter = this.entries.iterator();

      while(iter.hasNext()) {
         iter.advance();
         double delta = FastMath.abs(iter.value() - v.getEntry(iter.key()));
         if (delta > max) {
            max = delta;
         }
      }

      iter = v.getEntries().iterator();

      while(iter.hasNext()) {
         iter.advance();
         int key = iter.key();
         if (!this.entries.containsKey(key) && iter.value() > max) {
            max = iter.value();
         }
      }

      return max;
   }

   public double getLInfDistance(RealVector v) throws DimensionMismatchException {
      this.checkVectorDimensions(v.getDimension());
      return v instanceof OpenMapRealVector ? this.getLInfDistance((OpenMapRealVector)v) : super.getLInfDistance(v);
   }

   public boolean isInfinite() {
      boolean infiniteFound = false;
      OpenIntToDoubleHashMap.Iterator iter = this.entries.iterator();

      while(iter.hasNext()) {
         iter.advance();
         double value = iter.value();
         if (Double.isNaN(value)) {
            return false;
         }

         if (Double.isInfinite(value)) {
            infiniteFound = true;
         }
      }

      return infiniteFound;
   }

   public boolean isNaN() {
      OpenIntToDoubleHashMap.Iterator iter = this.entries.iterator();

      do {
         if (!iter.hasNext()) {
            return false;
         }

         iter.advance();
      } while(!Double.isNaN(iter.value()));

      return true;
   }

   public OpenMapRealVector mapAdd(double d) {
      return this.copy().mapAddToSelf(d);
   }

   public OpenMapRealVector mapAddToSelf(double d) {
      for(int i = 0; i < this.virtualSize; ++i) {
         this.setEntry(i, this.getEntry(i) + d);
      }

      return this;
   }

   public void setEntry(int index, double value) throws OutOfRangeException {
      this.checkIndex(index);
      if (!this.isDefaultValue(value)) {
         this.entries.put(index, value);
      } else if (this.entries.containsKey(index)) {
         this.entries.remove(index);
      }

   }

   public void setSubVector(int index, RealVector v) throws OutOfRangeException {
      this.checkIndex(index);
      this.checkIndex(index + v.getDimension() - 1);

      for(int i = 0; i < v.getDimension(); ++i) {
         this.setEntry(i + index, v.getEntry(i));
      }

   }

   public void set(double value) {
      for(int i = 0; i < this.virtualSize; ++i) {
         this.setEntry(i, value);
      }

   }

   public OpenMapRealVector subtract(OpenMapRealVector v) throws DimensionMismatchException {
      this.checkVectorDimensions(v.getDimension());
      OpenMapRealVector res = this.copy();
      OpenIntToDoubleHashMap.Iterator iter = v.getEntries().iterator();

      while(iter.hasNext()) {
         iter.advance();
         int key = iter.key();
         if (this.entries.containsKey(key)) {
            res.setEntry(key, this.entries.get(key) - iter.value());
         } else {
            res.setEntry(key, -iter.value());
         }
      }

      return res;
   }

   public RealVector subtract(RealVector v) throws DimensionMismatchException {
      this.checkVectorDimensions(v.getDimension());
      return (RealVector)(v instanceof OpenMapRealVector ? this.subtract((OpenMapRealVector)v) : super.subtract(v));
   }

   public OpenMapRealVector unitVector() throws MathArithmeticException {
      OpenMapRealVector res = this.copy();
      res.unitize();
      return res;
   }

   public void unitize() throws MathArithmeticException {
      double norm = this.getNorm();
      if (this.isDefaultValue(norm)) {
         throw new MathArithmeticException(LocalizedFormats.ZERO_NORM, new Object[0]);
      } else {
         OpenIntToDoubleHashMap.Iterator iter = this.entries.iterator();

         while(iter.hasNext()) {
            iter.advance();
            this.entries.put(iter.key(), iter.value() / norm);
         }

      }
   }

   public double[] toArray() {
      double[] res = new double[this.virtualSize];

      for(OpenIntToDoubleHashMap.Iterator iter = this.entries.iterator(); iter.hasNext(); res[iter.key()] = iter.value()) {
         iter.advance();
      }

      return res;
   }

   public int hashCode() {
      int prime = true;
      int result = 1;
      long temp = Double.doubleToLongBits(this.epsilon);
      int result = 31 * result + (int)(temp ^ temp >>> 32);
      result = 31 * result + this.virtualSize;

      for(OpenIntToDoubleHashMap.Iterator iter = this.entries.iterator(); iter.hasNext(); result = 31 * result + (int)(temp ^ temp >> 32)) {
         iter.advance();
         temp = Double.doubleToLongBits(iter.value());
      }

      return result;
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (!(obj instanceof OpenMapRealVector)) {
         return false;
      } else {
         OpenMapRealVector other = (OpenMapRealVector)obj;
         if (this.virtualSize != other.virtualSize) {
            return false;
         } else if (Double.doubleToLongBits(this.epsilon) != Double.doubleToLongBits(other.epsilon)) {
            return false;
         } else {
            OpenIntToDoubleHashMap.Iterator iter = this.entries.iterator();

            double test;
            do {
               if (!iter.hasNext()) {
                  iter = other.getEntries().iterator();

                  do {
                     if (!iter.hasNext()) {
                        return true;
                     }

                     iter.advance();
                     test = iter.value();
                  } while(Double.doubleToLongBits(test) == Double.doubleToLongBits(this.getEntry(iter.key())));

                  return false;
               }

               iter.advance();
               test = other.getEntry(iter.key());
            } while(Double.doubleToLongBits(test) == Double.doubleToLongBits(iter.value()));

            return false;
         }
      }
   }

   public double getSparsity() {
      return (double)this.entries.size() / (double)this.getDimension();
   }

   public Iterator<RealVector.Entry> sparseIterator() {
      return new OpenMapRealVector.OpenMapSparseIterator();
   }

   protected class OpenMapSparseIterator implements Iterator<RealVector.Entry> {
      private final OpenIntToDoubleHashMap.Iterator iter;
      private final RealVector.Entry current;

      protected OpenMapSparseIterator() {
         this.iter = OpenMapRealVector.this.entries.iterator();
         this.current = OpenMapRealVector.this.new OpenMapEntry(this.iter);
      }

      public boolean hasNext() {
         return this.iter.hasNext();
      }

      public RealVector.Entry next() {
         this.iter.advance();
         return this.current;
      }

      public void remove() {
         throw new UnsupportedOperationException("Not supported");
      }
   }

   protected class OpenMapEntry extends RealVector.Entry {
      private final OpenIntToDoubleHashMap.Iterator iter;

      protected OpenMapEntry(OpenIntToDoubleHashMap.Iterator iter) {
         super();
         this.iter = iter;
      }

      public double getValue() {
         return this.iter.value();
      }

      public void setValue(double value) {
         OpenMapRealVector.this.entries.put(this.iter.key(), value);
      }

      public int getIndex() {
         return this.iter.key();
      }
   }
}
