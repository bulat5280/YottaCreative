package org.apache.commons.math3.linear;

import java.io.Serializable;
import org.apache.commons.math3.Field;
import org.apache.commons.math3.FieldElement;
import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.MathArithmeticException;
import org.apache.commons.math3.exception.NotPositiveException;
import org.apache.commons.math3.exception.NullArgumentException;
import org.apache.commons.math3.exception.OutOfRangeException;
import org.apache.commons.math3.exception.util.LocalizedFormats;
import org.apache.commons.math3.util.MathArrays;
import org.apache.commons.math3.util.MathUtils;
import org.apache.commons.math3.util.OpenIntToFieldHashMap;

/** @deprecated */
@Deprecated
public class SparseFieldVector<T extends FieldElement<T>> implements FieldVector<T>, Serializable {
   private static final long serialVersionUID = 7841233292190413362L;
   private final Field<T> field;
   private final OpenIntToFieldHashMap<T> entries;
   private final int virtualSize;

   public SparseFieldVector(Field<T> field) {
      this((Field)field, 0);
   }

   public SparseFieldVector(Field<T> field, int dimension) {
      this.field = field;
      this.virtualSize = dimension;
      this.entries = new OpenIntToFieldHashMap(field);
   }

   protected SparseFieldVector(SparseFieldVector<T> v, int resize) {
      this.field = v.field;
      this.virtualSize = v.getDimension() + resize;
      this.entries = new OpenIntToFieldHashMap(v.entries);
   }

   public SparseFieldVector(Field<T> field, int dimension, int expectedSize) {
      this.field = field;
      this.virtualSize = dimension;
      this.entries = new OpenIntToFieldHashMap(field, expectedSize);
   }

   public SparseFieldVector(Field<T> field, T[] values) throws NullArgumentException {
      MathUtils.checkNotNull(values);
      this.field = field;
      this.virtualSize = values.length;
      this.entries = new OpenIntToFieldHashMap(field);

      for(int key = 0; key < values.length; ++key) {
         T value = values[key];
         this.entries.put(key, value);
      }

   }

   public SparseFieldVector(SparseFieldVector<T> v) {
      this.field = v.field;
      this.virtualSize = v.getDimension();
      this.entries = new OpenIntToFieldHashMap(v.getEntries());
   }

   private OpenIntToFieldHashMap<T> getEntries() {
      return this.entries;
   }

   public FieldVector<T> add(SparseFieldVector<T> v) throws DimensionMismatchException {
      this.checkVectorDimensions(v.getDimension());
      SparseFieldVector<T> res = (SparseFieldVector)this.copy();
      OpenIntToFieldHashMap.Iterator iter = v.getEntries().iterator();

      while(iter.hasNext()) {
         iter.advance();
         int key = iter.key();
         T value = iter.value();
         if (this.entries.containsKey(key)) {
            res.setEntry(key, (FieldElement)this.entries.get(key).add(value));
         } else {
            res.setEntry(key, value);
         }
      }

      return res;
   }

   public FieldVector<T> append(SparseFieldVector<T> v) {
      SparseFieldVector<T> res = new SparseFieldVector(this, v.getDimension());
      OpenIntToFieldHashMap.Iterator iter = v.entries.iterator();

      while(iter.hasNext()) {
         iter.advance();
         res.setEntry(iter.key() + this.virtualSize, iter.value());
      }

      return res;
   }

   public FieldVector<T> append(FieldVector<T> v) {
      if (v instanceof SparseFieldVector) {
         return this.append((SparseFieldVector)v);
      } else {
         int n = v.getDimension();
         FieldVector<T> res = new SparseFieldVector(this, n);

         for(int i = 0; i < n; ++i) {
            res.setEntry(i + this.virtualSize, v.getEntry(i));
         }

         return res;
      }
   }

   public FieldVector<T> append(T d) throws NullArgumentException {
      MathUtils.checkNotNull(d);
      FieldVector<T> res = new SparseFieldVector(this, 1);
      res.setEntry(this.virtualSize, d);
      return res;
   }

   public FieldVector<T> copy() {
      return new SparseFieldVector(this);
   }

   public T dotProduct(FieldVector<T> v) throws DimensionMismatchException {
      this.checkVectorDimensions(v.getDimension());
      T res = (FieldElement)this.field.getZero();

      for(OpenIntToFieldHashMap.Iterator iter = this.entries.iterator(); iter.hasNext(); res = (FieldElement)res.add(v.getEntry(iter.key()).multiply(iter.value()))) {
         iter.advance();
      }

      return res;
   }

   public FieldVector<T> ebeDivide(FieldVector<T> v) throws DimensionMismatchException, MathArithmeticException {
      this.checkVectorDimensions(v.getDimension());
      SparseFieldVector<T> res = new SparseFieldVector(this);
      OpenIntToFieldHashMap.Iterator iter = res.entries.iterator();

      while(iter.hasNext()) {
         iter.advance();
         res.setEntry(iter.key(), (FieldElement)iter.value().divide(v.getEntry(iter.key())));
      }

      return res;
   }

   public FieldVector<T> ebeMultiply(FieldVector<T> v) throws DimensionMismatchException {
      this.checkVectorDimensions(v.getDimension());
      SparseFieldVector<T> res = new SparseFieldVector(this);
      OpenIntToFieldHashMap.Iterator iter = res.entries.iterator();

      while(iter.hasNext()) {
         iter.advance();
         res.setEntry(iter.key(), (FieldElement)iter.value().multiply(v.getEntry(iter.key())));
      }

      return res;
   }

   /** @deprecated */
   @Deprecated
   public T[] getData() {
      return this.toArray();
   }

   public int getDimension() {
      return this.virtualSize;
   }

   public T getEntry(int index) throws OutOfRangeException {
      this.checkIndex(index);
      return this.entries.get(index);
   }

   public Field<T> getField() {
      return this.field;
   }

   public FieldVector<T> getSubVector(int index, int n) throws OutOfRangeException, NotPositiveException {
      if (n < 0) {
         throw new NotPositiveException(LocalizedFormats.NUMBER_OF_ELEMENTS_SHOULD_BE_POSITIVE, n);
      } else {
         this.checkIndex(index);
         this.checkIndex(index + n - 1);
         SparseFieldVector<T> res = new SparseFieldVector(this.field, n);
         int end = index + n;
         OpenIntToFieldHashMap.Iterator iter = this.entries.iterator();

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

   public FieldVector<T> mapAdd(T d) throws NullArgumentException {
      return this.copy().mapAddToSelf(d);
   }

   public FieldVector<T> mapAddToSelf(T d) throws NullArgumentException {
      for(int i = 0; i < this.virtualSize; ++i) {
         this.setEntry(i, (FieldElement)this.getEntry(i).add(d));
      }

      return this;
   }

   public FieldVector<T> mapDivide(T d) throws NullArgumentException, MathArithmeticException {
      return this.copy().mapDivideToSelf(d);
   }

   public FieldVector<T> mapDivideToSelf(T d) throws NullArgumentException, MathArithmeticException {
      OpenIntToFieldHashMap.Iterator iter = this.entries.iterator();

      while(iter.hasNext()) {
         iter.advance();
         this.entries.put(iter.key(), (FieldElement)iter.value().divide(d));
      }

      return this;
   }

   public FieldVector<T> mapInv() throws MathArithmeticException {
      return this.copy().mapInvToSelf();
   }

   public FieldVector<T> mapInvToSelf() throws MathArithmeticException {
      for(int i = 0; i < this.virtualSize; ++i) {
         this.setEntry(i, (FieldElement)((FieldElement)this.field.getOne()).divide(this.getEntry(i)));
      }

      return this;
   }

   public FieldVector<T> mapMultiply(T d) throws NullArgumentException {
      return this.copy().mapMultiplyToSelf(d);
   }

   public FieldVector<T> mapMultiplyToSelf(T d) throws NullArgumentException {
      OpenIntToFieldHashMap.Iterator iter = this.entries.iterator();

      while(iter.hasNext()) {
         iter.advance();
         this.entries.put(iter.key(), (FieldElement)iter.value().multiply(d));
      }

      return this;
   }

   public FieldVector<T> mapSubtract(T d) throws NullArgumentException {
      return this.copy().mapSubtractToSelf(d);
   }

   public FieldVector<T> mapSubtractToSelf(T d) throws NullArgumentException {
      return this.mapAddToSelf((FieldElement)((FieldElement)this.field.getZero()).subtract(d));
   }

   public FieldMatrix<T> outerProduct(SparseFieldVector<T> v) {
      int n = v.getDimension();
      SparseFieldMatrix<T> res = new SparseFieldMatrix(this.field, this.virtualSize, n);
      OpenIntToFieldHashMap.Iterator iter = this.entries.iterator();

      while(iter.hasNext()) {
         iter.advance();
         OpenIntToFieldHashMap.Iterator iter2 = v.entries.iterator();

         while(iter2.hasNext()) {
            iter2.advance();
            res.setEntry(iter.key(), iter2.key(), (FieldElement)iter.value().multiply(iter2.value()));
         }
      }

      return res;
   }

   public FieldMatrix<T> outerProduct(FieldVector<T> v) {
      if (v instanceof SparseFieldVector) {
         return this.outerProduct((SparseFieldVector)v);
      } else {
         int n = v.getDimension();
         FieldMatrix<T> res = new SparseFieldMatrix(this.field, this.virtualSize, n);
         OpenIntToFieldHashMap.Iterator iter = this.entries.iterator();

         while(iter.hasNext()) {
            iter.advance();
            int row = iter.key();
            FieldElement<T> value = iter.value();

            for(int col = 0; col < n; ++col) {
               res.setEntry(row, col, (FieldElement)value.multiply(v.getEntry(col)));
            }
         }

         return res;
      }
   }

   public FieldVector<T> projection(FieldVector<T> v) throws DimensionMismatchException, MathArithmeticException {
      this.checkVectorDimensions(v.getDimension());
      return v.mapMultiply((FieldElement)this.dotProduct(v).divide(v.dotProduct(v)));
   }

   public void set(T value) {
      MathUtils.checkNotNull(value);

      for(int i = 0; i < this.virtualSize; ++i) {
         this.setEntry(i, value);
      }

   }

   public void setEntry(int index, T value) throws NullArgumentException, OutOfRangeException {
      MathUtils.checkNotNull(value);
      this.checkIndex(index);
      this.entries.put(index, value);
   }

   public void setSubVector(int index, FieldVector<T> v) throws OutOfRangeException {
      this.checkIndex(index);
      this.checkIndex(index + v.getDimension() - 1);
      int n = v.getDimension();

      for(int i = 0; i < n; ++i) {
         this.setEntry(i + index, v.getEntry(i));
      }

   }

   public SparseFieldVector<T> subtract(SparseFieldVector<T> v) throws DimensionMismatchException {
      this.checkVectorDimensions(v.getDimension());
      SparseFieldVector<T> res = (SparseFieldVector)this.copy();
      OpenIntToFieldHashMap.Iterator iter = v.getEntries().iterator();

      while(iter.hasNext()) {
         iter.advance();
         int key = iter.key();
         if (this.entries.containsKey(key)) {
            res.setEntry(key, (FieldElement)this.entries.get(key).subtract(iter.value()));
         } else {
            res.setEntry(key, (FieldElement)((FieldElement)this.field.getZero()).subtract(iter.value()));
         }
      }

      return res;
   }

   public FieldVector<T> subtract(FieldVector<T> v) throws DimensionMismatchException {
      if (v instanceof SparseFieldVector) {
         return this.subtract((SparseFieldVector)v);
      } else {
         int n = v.getDimension();
         this.checkVectorDimensions(n);
         SparseFieldVector<T> res = new SparseFieldVector(this);

         for(int i = 0; i < n; ++i) {
            if (this.entries.containsKey(i)) {
               res.setEntry(i, (FieldElement)this.entries.get(i).subtract(v.getEntry(i)));
            } else {
               res.setEntry(i, (FieldElement)((FieldElement)this.field.getZero()).subtract(v.getEntry(i)));
            }
         }

         return res;
      }
   }

   public T[] toArray() {
      T[] res = (FieldElement[])MathArrays.buildArray(this.field, this.virtualSize);

      for(OpenIntToFieldHashMap.Iterator iter = this.entries.iterator(); iter.hasNext(); res[iter.key()] = iter.value()) {
         iter.advance();
      }

      return res;
   }

   private void checkIndex(int index) throws OutOfRangeException {
      if (index < 0 || index >= this.getDimension()) {
         throw new OutOfRangeException(index, 0, this.getDimension() - 1);
      }
   }

   protected void checkVectorDimensions(int n) throws DimensionMismatchException {
      if (this.getDimension() != n) {
         throw new DimensionMismatchException(this.getDimension(), n);
      }
   }

   public FieldVector<T> add(FieldVector<T> v) throws DimensionMismatchException {
      if (v instanceof SparseFieldVector) {
         return this.add((SparseFieldVector)v);
      } else {
         int n = v.getDimension();
         this.checkVectorDimensions(n);
         SparseFieldVector<T> res = new SparseFieldVector(this.field, this.getDimension());

         for(int i = 0; i < n; ++i) {
            res.setEntry(i, (FieldElement)v.getEntry(i).add(this.getEntry(i)));
         }

         return res;
      }
   }

   public int hashCode() {
      int prime = true;
      int result = 1;
      int result = 31 * result + (this.field == null ? 0 : this.field.hashCode());
      result = 31 * result + this.virtualSize;

      int temp;
      for(OpenIntToFieldHashMap.Iterator iter = this.entries.iterator(); iter.hasNext(); result = 31 * result + temp) {
         iter.advance();
         temp = iter.value().hashCode();
      }

      return result;
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (!(obj instanceof SparseFieldVector)) {
         return false;
      } else {
         SparseFieldVector<T> other = (SparseFieldVector)obj;
         if (this.field == null) {
            if (other.field != null) {
               return false;
            }
         } else if (!this.field.equals(other.field)) {
            return false;
         }

         if (this.virtualSize != other.virtualSize) {
            return false;
         } else {
            OpenIntToFieldHashMap.Iterator iter = this.entries.iterator();

            FieldElement test;
            do {
               if (!iter.hasNext()) {
                  iter = other.getEntries().iterator();

                  do {
                     if (!iter.hasNext()) {
                        return true;
                     }

                     iter.advance();
                     test = iter.value();
                  } while(test.equals(this.getEntry(iter.key())));

                  return false;
               }

               iter.advance();
               test = other.getEntry(iter.key());
            } while(test.equals(iter.value()));

            return false;
         }
      }
   }
}
