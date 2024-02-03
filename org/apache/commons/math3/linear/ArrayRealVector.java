package org.apache.commons.math3.linear;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.NotPositiveException;
import org.apache.commons.math3.exception.NullArgumentException;
import org.apache.commons.math3.exception.NumberIsTooLargeException;
import org.apache.commons.math3.exception.NumberIsTooSmallException;
import org.apache.commons.math3.exception.OutOfRangeException;
import org.apache.commons.math3.exception.util.LocalizedFormats;
import org.apache.commons.math3.util.FastMath;
import org.apache.commons.math3.util.MathUtils;

public class ArrayRealVector extends RealVector implements Serializable {
   private static final long serialVersionUID = -1097961340710804027L;
   private static final RealVectorFormat DEFAULT_FORMAT = RealVectorFormat.getInstance();
   private double[] data;

   public ArrayRealVector() {
      this.data = new double[0];
   }

   public ArrayRealVector(int size) {
      this.data = new double[size];
   }

   public ArrayRealVector(int size, double preset) {
      this.data = new double[size];
      Arrays.fill(this.data, preset);
   }

   public ArrayRealVector(double[] d) {
      this.data = (double[])d.clone();
   }

   public ArrayRealVector(double[] d, boolean copyArray) throws NullArgumentException {
      if (d == null) {
         throw new NullArgumentException();
      } else {
         this.data = copyArray ? (double[])d.clone() : d;
      }
   }

   public ArrayRealVector(double[] d, int pos, int size) throws NullArgumentException, NumberIsTooLargeException {
      if (d == null) {
         throw new NullArgumentException();
      } else if (d.length < pos + size) {
         throw new NumberIsTooLargeException(pos + size, d.length, true);
      } else {
         this.data = new double[size];
         System.arraycopy(d, pos, this.data, 0, size);
      }
   }

   public ArrayRealVector(Double[] d) {
      this.data = new double[d.length];

      for(int i = 0; i < d.length; ++i) {
         this.data[i] = d[i];
      }

   }

   public ArrayRealVector(Double[] d, int pos, int size) throws NullArgumentException, NumberIsTooLargeException {
      if (d == null) {
         throw new NullArgumentException();
      } else if (d.length < pos + size) {
         throw new NumberIsTooLargeException(pos + size, d.length, true);
      } else {
         this.data = new double[size];

         for(int i = pos; i < pos + size; ++i) {
            this.data[i - pos] = d[i];
         }

      }
   }

   public ArrayRealVector(RealVector v) throws NullArgumentException {
      if (v == null) {
         throw new NullArgumentException();
      } else {
         this.data = new double[v.getDimension()];

         for(int i = 0; i < this.data.length; ++i) {
            this.data[i] = v.getEntry(i);
         }

      }
   }

   public ArrayRealVector(ArrayRealVector v) throws NullArgumentException {
      this(v, true);
   }

   public ArrayRealVector(ArrayRealVector v, boolean deep) {
      this.data = deep ? (double[])v.data.clone() : v.data;
   }

   public ArrayRealVector(ArrayRealVector v1, ArrayRealVector v2) {
      this.data = new double[v1.data.length + v2.data.length];
      System.arraycopy(v1.data, 0, this.data, 0, v1.data.length);
      System.arraycopy(v2.data, 0, this.data, v1.data.length, v2.data.length);
   }

   public ArrayRealVector(ArrayRealVector v1, RealVector v2) {
      int l1 = v1.data.length;
      int l2 = v2.getDimension();
      this.data = new double[l1 + l2];
      System.arraycopy(v1.data, 0, this.data, 0, l1);

      for(int i = 0; i < l2; ++i) {
         this.data[l1 + i] = v2.getEntry(i);
      }

   }

   public ArrayRealVector(RealVector v1, ArrayRealVector v2) {
      int l1 = v1.getDimension();
      int l2 = v2.data.length;
      this.data = new double[l1 + l2];

      for(int i = 0; i < l1; ++i) {
         this.data[i] = v1.getEntry(i);
      }

      System.arraycopy(v2.data, 0, this.data, l1, l2);
   }

   public ArrayRealVector(ArrayRealVector v1, double[] v2) {
      int l1 = v1.getDimension();
      int l2 = v2.length;
      this.data = new double[l1 + l2];
      System.arraycopy(v1.data, 0, this.data, 0, l1);
      System.arraycopy(v2, 0, this.data, l1, l2);
   }

   public ArrayRealVector(double[] v1, ArrayRealVector v2) {
      int l1 = v1.length;
      int l2 = v2.getDimension();
      this.data = new double[l1 + l2];
      System.arraycopy(v1, 0, this.data, 0, l1);
      System.arraycopy(v2.data, 0, this.data, l1, l2);
   }

   public ArrayRealVector(double[] v1, double[] v2) {
      int l1 = v1.length;
      int l2 = v2.length;
      this.data = new double[l1 + l2];
      System.arraycopy(v1, 0, this.data, 0, l1);
      System.arraycopy(v2, 0, this.data, l1, l2);
   }

   public ArrayRealVector copy() {
      return new ArrayRealVector(this, true);
   }

   public ArrayRealVector add(RealVector v) throws DimensionMismatchException {
      double[] out;
      if (v instanceof ArrayRealVector) {
         out = ((ArrayRealVector)v).data;
         int dim = out.length;
         this.checkVectorDimensions(dim);
         ArrayRealVector result = new ArrayRealVector(dim);
         double[] resultData = result.data;

         for(int i = 0; i < dim; ++i) {
            resultData[i] = this.data[i] + out[i];
         }

         return result;
      } else {
         this.checkVectorDimensions(v);
         out = (double[])this.data.clone();

         int var10001;
         RealVector.Entry e;
         for(Iterator it = v.iterator(); it.hasNext(); out[var10001] += e.getValue()) {
            e = (RealVector.Entry)it.next();
            var10001 = e.getIndex();
         }

         return new ArrayRealVector(out, false);
      }
   }

   public ArrayRealVector subtract(RealVector v) throws DimensionMismatchException {
      double[] out;
      if (v instanceof ArrayRealVector) {
         out = ((ArrayRealVector)v).data;
         int dim = out.length;
         this.checkVectorDimensions(dim);
         ArrayRealVector result = new ArrayRealVector(dim);
         double[] resultData = result.data;

         for(int i = 0; i < dim; ++i) {
            resultData[i] = this.data[i] - out[i];
         }

         return result;
      } else {
         this.checkVectorDimensions(v);
         out = (double[])this.data.clone();

         int var10001;
         RealVector.Entry e;
         for(Iterator it = v.iterator(); it.hasNext(); out[var10001] -= e.getValue()) {
            e = (RealVector.Entry)it.next();
            var10001 = e.getIndex();
         }

         return new ArrayRealVector(out, false);
      }
   }

   public ArrayRealVector map(UnivariateFunction function) {
      return this.copy().mapToSelf(function);
   }

   public ArrayRealVector mapToSelf(UnivariateFunction function) {
      for(int i = 0; i < this.data.length; ++i) {
         this.data[i] = function.value(this.data[i]);
      }

      return this;
   }

   public RealVector mapAddToSelf(double d) {
      for(int i = 0; i < this.data.length; ++i) {
         this.data[i] += d;
      }

      return this;
   }

   public RealVector mapSubtractToSelf(double d) {
      for(int i = 0; i < this.data.length; ++i) {
         this.data[i] -= d;
      }

      return this;
   }

   public RealVector mapMultiplyToSelf(double d) {
      for(int i = 0; i < this.data.length; ++i) {
         this.data[i] *= d;
      }

      return this;
   }

   public RealVector mapDivideToSelf(double d) {
      for(int i = 0; i < this.data.length; ++i) {
         this.data[i] /= d;
      }

      return this;
   }

   public ArrayRealVector ebeMultiply(RealVector v) throws DimensionMismatchException {
      double[] out;
      int i;
      if (v instanceof ArrayRealVector) {
         out = ((ArrayRealVector)v).data;
         i = out.length;
         this.checkVectorDimensions(i);
         ArrayRealVector result = new ArrayRealVector(i);
         double[] resultData = result.data;

         for(int i = 0; i < i; ++i) {
            resultData[i] = this.data[i] * out[i];
         }

         return result;
      } else {
         this.checkVectorDimensions(v);
         out = (double[])this.data.clone();

         for(i = 0; i < this.data.length; ++i) {
            out[i] *= v.getEntry(i);
         }

         return new ArrayRealVector(out, false);
      }
   }

   public ArrayRealVector ebeDivide(RealVector v) throws DimensionMismatchException {
      double[] out;
      int i;
      if (v instanceof ArrayRealVector) {
         out = ((ArrayRealVector)v).data;
         i = out.length;
         this.checkVectorDimensions(i);
         ArrayRealVector result = new ArrayRealVector(i);
         double[] resultData = result.data;

         for(int i = 0; i < i; ++i) {
            resultData[i] = this.data[i] / out[i];
         }

         return result;
      } else {
         this.checkVectorDimensions(v);
         out = (double[])this.data.clone();

         for(i = 0; i < this.data.length; ++i) {
            out[i] /= v.getEntry(i);
         }

         return new ArrayRealVector(out, false);
      }
   }

   public double[] getDataRef() {
      return this.data;
   }

   public double dotProduct(RealVector v) throws DimensionMismatchException {
      if (!(v instanceof ArrayRealVector)) {
         return super.dotProduct(v);
      } else {
         double[] vData = ((ArrayRealVector)v).data;
         this.checkVectorDimensions(vData.length);
         double dot = 0.0D;

         for(int i = 0; i < this.data.length; ++i) {
            dot += this.data[i] * vData[i];
         }

         return dot;
      }
   }

   public double getNorm() {
      double sum = 0.0D;
      double[] arr$ = this.data;
      int len$ = arr$.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         double a = arr$[i$];
         sum += a * a;
      }

      return FastMath.sqrt(sum);
   }

   public double getL1Norm() {
      double sum = 0.0D;
      double[] arr$ = this.data;
      int len$ = arr$.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         double a = arr$[i$];
         sum += FastMath.abs(a);
      }

      return sum;
   }

   public double getLInfNorm() {
      double max = 0.0D;
      double[] arr$ = this.data;
      int len$ = arr$.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         double a = arr$[i$];
         max = FastMath.max(max, FastMath.abs(a));
      }

      return max;
   }

   public double getDistance(RealVector v) throws DimensionMismatchException {
      if (v instanceof ArrayRealVector) {
         double[] vData = ((ArrayRealVector)v).data;
         this.checkVectorDimensions(vData.length);
         double sum = 0.0D;

         for(int i = 0; i < this.data.length; ++i) {
            double delta = this.data[i] - vData[i];
            sum += delta * delta;
         }

         return FastMath.sqrt(sum);
      } else {
         this.checkVectorDimensions(v);
         double sum = 0.0D;

         for(int i = 0; i < this.data.length; ++i) {
            double delta = this.data[i] - v.getEntry(i);
            sum += delta * delta;
         }

         return FastMath.sqrt(sum);
      }
   }

   public double getL1Distance(RealVector v) throws DimensionMismatchException {
      if (v instanceof ArrayRealVector) {
         double[] vData = ((ArrayRealVector)v).data;
         this.checkVectorDimensions(vData.length);
         double sum = 0.0D;

         for(int i = 0; i < this.data.length; ++i) {
            double delta = this.data[i] - vData[i];
            sum += FastMath.abs(delta);
         }

         return sum;
      } else {
         this.checkVectorDimensions(v);
         double sum = 0.0D;

         for(int i = 0; i < this.data.length; ++i) {
            double delta = this.data[i] - v.getEntry(i);
            sum += FastMath.abs(delta);
         }

         return sum;
      }
   }

   public double getLInfDistance(RealVector v) throws DimensionMismatchException {
      if (v instanceof ArrayRealVector) {
         double[] vData = ((ArrayRealVector)v).data;
         this.checkVectorDimensions(vData.length);
         double max = 0.0D;

         for(int i = 0; i < this.data.length; ++i) {
            double delta = this.data[i] - vData[i];
            max = FastMath.max(max, FastMath.abs(delta));
         }

         return max;
      } else {
         this.checkVectorDimensions(v);
         double max = 0.0D;

         for(int i = 0; i < this.data.length; ++i) {
            double delta = this.data[i] - v.getEntry(i);
            max = FastMath.max(max, FastMath.abs(delta));
         }

         return max;
      }
   }

   public RealMatrix outerProduct(RealVector v) {
      int n;
      int j;
      if (v instanceof ArrayRealVector) {
         double[] vData = ((ArrayRealVector)v).data;
         n = this.data.length;
         int n = vData.length;
         RealMatrix out = MatrixUtils.createRealMatrix(n, n);

         for(j = 0; j < n; ++j) {
            for(int j = 0; j < n; ++j) {
               out.setEntry(j, j, this.data[j] * vData[j]);
            }
         }

         return out;
      } else {
         int m = this.data.length;
         n = v.getDimension();
         RealMatrix out = MatrixUtils.createRealMatrix(m, n);

         for(int i = 0; i < m; ++i) {
            for(j = 0; j < n; ++j) {
               out.setEntry(i, j, this.data[i] * v.getEntry(j));
            }
         }

         return out;
      }
   }

   public double getEntry(int index) throws OutOfRangeException {
      try {
         return this.data[index];
      } catch (IndexOutOfBoundsException var3) {
         throw new OutOfRangeException(LocalizedFormats.INDEX, index, 0, this.getDimension() - 1);
      }
   }

   public int getDimension() {
      return this.data.length;
   }

   public RealVector append(RealVector v) {
      try {
         return new ArrayRealVector(this, (ArrayRealVector)v);
      } catch (ClassCastException var3) {
         return new ArrayRealVector(this, v);
      }
   }

   public ArrayRealVector append(ArrayRealVector v) {
      return new ArrayRealVector(this, v);
   }

   public RealVector append(double in) {
      double[] out = new double[this.data.length + 1];
      System.arraycopy(this.data, 0, out, 0, this.data.length);
      out[this.data.length] = in;
      return new ArrayRealVector(out, false);
   }

   public RealVector getSubVector(int index, int n) throws OutOfRangeException, NotPositiveException {
      if (n < 0) {
         throw new NotPositiveException(LocalizedFormats.NUMBER_OF_ELEMENTS_SHOULD_BE_POSITIVE, n);
      } else {
         ArrayRealVector out = new ArrayRealVector(n);

         try {
            System.arraycopy(this.data, index, out.data, 0, n);
         } catch (IndexOutOfBoundsException var5) {
            this.checkIndex(index);
            this.checkIndex(index + n - 1);
         }

         return out;
      }
   }

   public void setEntry(int index, double value) throws OutOfRangeException {
      try {
         this.data[index] = value;
      } catch (IndexOutOfBoundsException var5) {
         this.checkIndex(index);
      }

   }

   public void addToEntry(int index, double increment) throws OutOfRangeException {
      try {
         double[] var10000 = this.data;
         var10000[index] += increment;
      } catch (IndexOutOfBoundsException var5) {
         throw new OutOfRangeException(LocalizedFormats.INDEX, index, 0, this.data.length - 1);
      }
   }

   public void setSubVector(int index, RealVector v) throws OutOfRangeException {
      if (v instanceof ArrayRealVector) {
         this.setSubVector(index, ((ArrayRealVector)v).data);
      } else {
         try {
            for(int i = index; i < index + v.getDimension(); ++i) {
               this.data[i] = v.getEntry(i - index);
            }
         } catch (IndexOutOfBoundsException var4) {
            this.checkIndex(index);
            this.checkIndex(index + v.getDimension() - 1);
         }
      }

   }

   public void setSubVector(int index, double[] v) throws OutOfRangeException {
      try {
         System.arraycopy(v, 0, this.data, index, v.length);
      } catch (IndexOutOfBoundsException var4) {
         this.checkIndex(index);
         this.checkIndex(index + v.length - 1);
      }

   }

   public void set(double value) {
      Arrays.fill(this.data, value);
   }

   public double[] toArray() {
      return (double[])this.data.clone();
   }

   public String toString() {
      return DEFAULT_FORMAT.format(this);
   }

   protected void checkVectorDimensions(RealVector v) throws DimensionMismatchException {
      this.checkVectorDimensions(v.getDimension());
   }

   protected void checkVectorDimensions(int n) throws DimensionMismatchException {
      if (this.data.length != n) {
         throw new DimensionMismatchException(this.data.length, n);
      }
   }

   public boolean isNaN() {
      double[] arr$ = this.data;
      int len$ = arr$.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         double v = arr$[i$];
         if (Double.isNaN(v)) {
            return true;
         }
      }

      return false;
   }

   public boolean isInfinite() {
      if (this.isNaN()) {
         return false;
      } else {
         double[] arr$ = this.data;
         int len$ = arr$.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            double v = arr$[i$];
            if (Double.isInfinite(v)) {
               return true;
            }
         }

         return false;
      }
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof RealVector)) {
         return false;
      } else {
         RealVector rhs = (RealVector)other;
         if (this.data.length != rhs.getDimension()) {
            return false;
         } else if (rhs.isNaN()) {
            return this.isNaN();
         } else {
            for(int i = 0; i < this.data.length; ++i) {
               if (this.data[i] != rhs.getEntry(i)) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   public int hashCode() {
      return this.isNaN() ? 9 : MathUtils.hash(this.data);
   }

   public ArrayRealVector combine(double a, double b, RealVector y) throws DimensionMismatchException {
      return this.copy().combineToSelf(a, b, y);
   }

   public ArrayRealVector combineToSelf(double a, double b, RealVector y) throws DimensionMismatchException {
      if (y instanceof ArrayRealVector) {
         double[] yData = ((ArrayRealVector)y).data;
         this.checkVectorDimensions(yData.length);

         for(int i = 0; i < this.data.length; ++i) {
            this.data[i] = a * this.data[i] + b * yData[i];
         }
      } else {
         this.checkVectorDimensions(y);

         for(int i = 0; i < this.data.length; ++i) {
            this.data[i] = a * this.data[i] + b * y.getEntry(i);
         }
      }

      return this;
   }

   public double walkInDefaultOrder(RealVectorPreservingVisitor visitor) {
      visitor.start(this.data.length, 0, this.data.length - 1);

      for(int i = 0; i < this.data.length; ++i) {
         visitor.visit(i, this.data[i]);
      }

      return visitor.end();
   }

   public double walkInDefaultOrder(RealVectorPreservingVisitor visitor, int start, int end) throws NumberIsTooSmallException, OutOfRangeException {
      this.checkIndices(start, end);
      visitor.start(this.data.length, start, end);

      for(int i = start; i <= end; ++i) {
         visitor.visit(i, this.data[i]);
      }

      return visitor.end();
   }

   public double walkInOptimizedOrder(RealVectorPreservingVisitor visitor) {
      return this.walkInDefaultOrder(visitor);
   }

   public double walkInOptimizedOrder(RealVectorPreservingVisitor visitor, int start, int end) throws NumberIsTooSmallException, OutOfRangeException {
      return this.walkInDefaultOrder(visitor, start, end);
   }

   public double walkInDefaultOrder(RealVectorChangingVisitor visitor) {
      visitor.start(this.data.length, 0, this.data.length - 1);

      for(int i = 0; i < this.data.length; ++i) {
         this.data[i] = visitor.visit(i, this.data[i]);
      }

      return visitor.end();
   }

   public double walkInDefaultOrder(RealVectorChangingVisitor visitor, int start, int end) throws NumberIsTooSmallException, OutOfRangeException {
      this.checkIndices(start, end);
      visitor.start(this.data.length, start, end);

      for(int i = start; i <= end; ++i) {
         this.data[i] = visitor.visit(i, this.data[i]);
      }

      return visitor.end();
   }

   public double walkInOptimizedOrder(RealVectorChangingVisitor visitor) {
      return this.walkInDefaultOrder(visitor);
   }

   public double walkInOptimizedOrder(RealVectorChangingVisitor visitor, int start, int end) throws NumberIsTooSmallException, OutOfRangeException {
      return this.walkInDefaultOrder(visitor, start, end);
   }
}
