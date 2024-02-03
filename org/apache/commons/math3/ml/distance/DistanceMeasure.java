package org.apache.commons.math3.ml.distance;

import java.io.Serializable;

public interface DistanceMeasure extends Serializable {
   double compute(double[] var1, double[] var2);
}
