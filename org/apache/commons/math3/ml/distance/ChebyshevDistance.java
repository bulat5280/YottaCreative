package org.apache.commons.math3.ml.distance;

import org.apache.commons.math3.util.MathArrays;

public class ChebyshevDistance implements DistanceMeasure {
   private static final long serialVersionUID = -4694868171115238296L;

   public double compute(double[] a, double[] b) {
      return MathArrays.distanceInf(a, b);
   }
}
