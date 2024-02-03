package org.apache.commons.math3.ml.distance;

import org.apache.commons.math3.util.FastMath;

public class CanberraDistance implements DistanceMeasure {
   private static final long serialVersionUID = -6972277381587032228L;

   public double compute(double[] a, double[] b) {
      double sum = 0.0D;

      for(int i = 0; i < a.length; ++i) {
         double num = FastMath.abs(a[i] - b[i]);
         double denom = FastMath.abs(a[i]) + FastMath.abs(b[i]);
         sum += num == 0.0D && denom == 0.0D ? 0.0D : num / denom;
      }

      return sum;
   }
}
