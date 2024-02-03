package org.apache.commons.math3.analysis.interpolation;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.MathIllegalArgumentException;

public interface UnivariateInterpolator {
   UnivariateFunction interpolate(double[] var1, double[] var2) throws MathIllegalArgumentException, DimensionMismatchException;
}
