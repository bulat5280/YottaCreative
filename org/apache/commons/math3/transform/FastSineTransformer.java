package org.apache.commons.math3.transform;

import java.io.Serializable;
import org.apache.commons.math3.analysis.FunctionUtils;
import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.exception.util.LocalizedFormats;
import org.apache.commons.math3.util.ArithmeticUtils;
import org.apache.commons.math3.util.FastMath;

public class FastSineTransformer implements RealTransformer, Serializable {
   static final long serialVersionUID = 20120211L;
   private final DstNormalization normalization;

   public FastSineTransformer(DstNormalization normalization) {
      this.normalization = normalization;
   }

   public double[] transform(double[] f, TransformType type) {
      double s;
      if (this.normalization == DstNormalization.ORTHOGONAL_DST_I) {
         s = FastMath.sqrt(2.0D / (double)f.length);
         return TransformUtils.scaleArray(this.fst(f), s);
      } else if (type == TransformType.FORWARD) {
         return this.fst(f);
      } else {
         s = 2.0D / (double)f.length;
         return TransformUtils.scaleArray(this.fst(f), s);
      }
   }

   public double[] transform(UnivariateFunction f, double min, double max, int n, TransformType type) {
      double[] data = FunctionUtils.sample(f, min, max, n);
      data[0] = 0.0D;
      return this.transform(data, type);
   }

   protected double[] fst(double[] f) throws MathIllegalArgumentException {
      double[] transformed = new double[f.length];
      if (!ArithmeticUtils.isPowerOfTwo((long)f.length)) {
         throw new MathIllegalArgumentException(LocalizedFormats.NOT_POWER_OF_TWO_CONSIDER_PADDING, new Object[]{f.length});
      } else if (f[0] != 0.0D) {
         throw new MathIllegalArgumentException(LocalizedFormats.FIRST_ELEMENT_NOT_ZERO, new Object[]{f[0]});
      } else {
         int n = f.length;
         if (n == 1) {
            transformed[0] = 0.0D;
            return transformed;
         } else {
            double[] x = new double[n];
            x[0] = 0.0D;
            x[n >> 1] = 2.0D * f[n >> 1];

            for(int i = 1; i < n >> 1; ++i) {
               double a = FastMath.sin((double)i * 3.141592653589793D / (double)n) * (f[i] + f[n - i]);
               double b = 0.5D * (f[i] - f[n - i]);
               x[i] = a + b;
               x[n - i] = a - b;
            }

            FastFourierTransformer transformer = new FastFourierTransformer(DftNormalization.STANDARD);
            Complex[] y = transformer.transform(x, TransformType.FORWARD);
            transformed[0] = 0.0D;
            transformed[1] = 0.5D * y[0].getReal();

            for(int i = 1; i < n >> 1; ++i) {
               transformed[2 * i] = -y[i].getImaginary();
               transformed[2 * i + 1] = y[i].getReal() + transformed[2 * i - 1];
            }

            return transformed;
         }
      }
   }
}
