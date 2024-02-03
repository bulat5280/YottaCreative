package org.apache.commons.math3.ode;

import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.MaxCountExceededException;

public interface SecondaryEquations {
   int getDimension();

   void computeDerivatives(double var1, double[] var3, double[] var4, double[] var5, double[] var6) throws MaxCountExceededException, DimensionMismatchException;
}
