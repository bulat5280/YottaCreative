package org.apache.commons.math3.analysis.interpolation;

import org.apache.commons.math3.analysis.TrivariateFunction;
import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.NoDataException;
import org.apache.commons.math3.exception.NonMonotonicSequenceException;
import org.apache.commons.math3.exception.OutOfRangeException;
import org.apache.commons.math3.util.MathArrays;

public class TricubicSplineInterpolatingFunction implements TrivariateFunction {
   private static final double[][] AINV = new double[][]{{1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D}, {0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D}, {-3.0D, 3.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, -2.0D, -1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D}, {2.0D, -2.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D}, {0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D}, {0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D}, {0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, -3.0D, 3.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, -2.0D, -1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D}, {0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 2.0D, -2.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D}, {-3.0D, 0.0D, 3.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, -2.0D, 0.0D, -1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D}, {0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, -3.0D, 0.0D, 3.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, -2.0D, 0.0D, -1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D}, {9.0D, -9.0D, -9.0D, 9.0D, 0.0D, 0.0D, 0.0D, 0.0D, 6.0D, 3.0D, -6.0D, -3.0D, 0.0D, 0.0D, 0.0D, 0.0D, 6.0D, -6.0D, 3.0D, -3.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 4.0D, 2.0D, 2.0D, 1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D}, {-6.0D, 6.0D, 6.0D, -6.0D, 0.0D, 0.0D, 0.0D, 0.0D, -3.0D, -3.0D, 3.0D, 3.0D, 0.0D, 0.0D, 0.0D, 0.0D, -4.0D, 4.0D, -2.0D, 2.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, -2.0D, -2.0D, -1.0D, -1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D}, {2.0D, 0.0D, -2.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, 1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D}, {0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 2.0D, 0.0D, -2.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, 1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D}, {-6.0D, 6.0D, 6.0D, -6.0D, 0.0D, 0.0D, 0.0D, 0.0D, -4.0D, -2.0D, 4.0D, 2.0D, 0.0D, 0.0D, 0.0D, 0.0D, -3.0D, 3.0D, -3.0D, 3.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, -2.0D, -1.0D, -2.0D, -1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D}, {4.0D, -4.0D, -4.0D, 4.0D, 0.0D, 0.0D, 0.0D, 0.0D, 2.0D, 2.0D, -2.0D, -2.0D, 0.0D, 0.0D, 0.0D, 0.0D, 2.0D, -2.0D, 2.0D, -2.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D, 1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D}, {0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D}, {0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D}, {0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, -3.0D, 3.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, -2.0D, -1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D}, {0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 2.0D, -2.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D}, {0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D}, {0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D}, {0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, -3.0D, 3.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, -2.0D, -1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D}, {0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 2.0D, -2.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D}, {0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, -3.0D, 0.0D, 3.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, -2.0D, 0.0D, -1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D}, {0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, -3.0D, 0.0D, 3.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, -2.0D, 0.0D, -1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D}, {0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 9.0D, -9.0D, -9.0D, 9.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 6.0D, 3.0D, -6.0D, -3.0D, 0.0D, 0.0D, 0.0D, 0.0D, 6.0D, -6.0D, 3.0D, -3.0D, 0.0D, 0.0D, 0.0D, 0.0D, 4.0D, 2.0D, 2.0D, 1.0D, 0.0D, 0.0D, 0.0D, 0.0D}, {0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, -6.0D, 6.0D, 6.0D, -6.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, -3.0D, -3.0D, 3.0D, 3.0D, 0.0D, 0.0D, 0.0D, 0.0D, -4.0D, 4.0D, -2.0D, 2.0D, 0.0D, 0.0D, 0.0D, 0.0D, -2.0D, -2.0D, -1.0D, -1.0D, 0.0D, 0.0D, 0.0D, 0.0D}, {0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 2.0D, 0.0D, -2.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, 1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D}, {0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 2.0D, 0.0D, -2.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, 1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D}, {0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, -6.0D, 6.0D, 6.0D, -6.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, -4.0D, -2.0D, 4.0D, 2.0D, 0.0D, 0.0D, 0.0D, 0.0D, -3.0D, 3.0D, -3.0D, 3.0D, 0.0D, 0.0D, 0.0D, 0.0D, -2.0D, -1.0D, -2.0D, -1.0D, 0.0D, 0.0D, 0.0D, 0.0D}, {0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 4.0D, -4.0D, -4.0D, 4.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 2.0D, 2.0D, -2.0D, -2.0D, 0.0D, 0.0D, 0.0D, 0.0D, 2.0D, -2.0D, 2.0D, -2.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D, 1.0D, 0.0D, 0.0D, 0.0D, 0.0D}, {-3.0D, 0.0D, 0.0D, 0.0D, 3.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, -2.0D, 0.0D, 0.0D, 0.0D, -1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D}, {0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, -3.0D, 0.0D, 0.0D, 0.0D, 3.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, -2.0D, 0.0D, 0.0D, 0.0D, -1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D}, {9.0D, -9.0D, 0.0D, 0.0D, -9.0D, 9.0D, 0.0D, 0.0D, 6.0D, 3.0D, 0.0D, 0.0D, -6.0D, -3.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 6.0D, -6.0D, 0.0D, 0.0D, 3.0D, -3.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 4.0D, 2.0D, 0.0D, 0.0D, 2.0D, 1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D}, {-6.0D, 6.0D, 0.0D, 0.0D, 6.0D, -6.0D, 0.0D, 0.0D, -3.0D, -3.0D, 0.0D, 0.0D, 3.0D, 3.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, -4.0D, 4.0D, 0.0D, 0.0D, -2.0D, 2.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, -2.0D, -2.0D, 0.0D, 0.0D, -1.0D, -1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D}, {0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, -3.0D, 0.0D, 0.0D, 0.0D, 3.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, -2.0D, 0.0D, 0.0D, 0.0D, -1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D}, {0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, -3.0D, 0.0D, 0.0D, 0.0D, 3.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, -2.0D, 0.0D, 0.0D, 0.0D, -1.0D, 0.0D, 0.0D, 0.0D}, {0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 9.0D, -9.0D, 0.0D, 0.0D, -9.0D, 9.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 6.0D, 3.0D, 0.0D, 0.0D, -6.0D, -3.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 6.0D, -6.0D, 0.0D, 0.0D, 3.0D, -3.0D, 0.0D, 0.0D, 4.0D, 2.0D, 0.0D, 0.0D, 2.0D, 1.0D, 0.0D, 0.0D}, {0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, -6.0D, 6.0D, 0.0D, 0.0D, 6.0D, -6.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, -3.0D, -3.0D, 0.0D, 0.0D, 3.0D, 3.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, -4.0D, 4.0D, 0.0D, 0.0D, -2.0D, 2.0D, 0.0D, 0.0D, -2.0D, -2.0D, 0.0D, 0.0D, -1.0D, -1.0D, 0.0D, 0.0D}, {9.0D, 0.0D, -9.0D, 0.0D, -9.0D, 0.0D, 9.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 6.0D, 0.0D, 3.0D, 0.0D, -6.0D, 0.0D, -3.0D, 0.0D, 6.0D, 0.0D, -6.0D, 0.0D, 3.0D, 0.0D, -3.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 4.0D, 0.0D, 2.0D, 0.0D, 2.0D, 0.0D, 1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D}, {0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 9.0D, 0.0D, -9.0D, 0.0D, -9.0D, 0.0D, 9.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 6.0D, 0.0D, 3.0D, 0.0D, -6.0D, 0.0D, -3.0D, 0.0D, 6.0D, 0.0D, -6.0D, 0.0D, 3.0D, 0.0D, -3.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 4.0D, 0.0D, 2.0D, 0.0D, 2.0D, 0.0D, 1.0D, 0.0D}, {-27.0D, 27.0D, 27.0D, -27.0D, 27.0D, -27.0D, -27.0D, 27.0D, -18.0D, -9.0D, 18.0D, 9.0D, 18.0D, 9.0D, -18.0D, -9.0D, -18.0D, 18.0D, -9.0D, 9.0D, 18.0D, -18.0D, 9.0D, -9.0D, -18.0D, 18.0D, 18.0D, -18.0D, -9.0D, 9.0D, 9.0D, -9.0D, -12.0D, -6.0D, -6.0D, -3.0D, 12.0D, 6.0D, 6.0D, 3.0D, -12.0D, -6.0D, 12.0D, 6.0D, -6.0D, -3.0D, 6.0D, 3.0D, -12.0D, 12.0D, -6.0D, 6.0D, -6.0D, 6.0D, -3.0D, 3.0D, -8.0D, -4.0D, -4.0D, -2.0D, -4.0D, -2.0D, -2.0D, -1.0D}, {18.0D, -18.0D, -18.0D, 18.0D, -18.0D, 18.0D, 18.0D, -18.0D, 9.0D, 9.0D, -9.0D, -9.0D, -9.0D, -9.0D, 9.0D, 9.0D, 12.0D, -12.0D, 6.0D, -6.0D, -12.0D, 12.0D, -6.0D, 6.0D, 12.0D, -12.0D, -12.0D, 12.0D, 6.0D, -6.0D, -6.0D, 6.0D, 6.0D, 6.0D, 3.0D, 3.0D, -6.0D, -6.0D, -3.0D, -3.0D, 6.0D, 6.0D, -6.0D, -6.0D, 3.0D, 3.0D, -3.0D, -3.0D, 8.0D, -8.0D, 4.0D, -4.0D, 4.0D, -4.0D, 2.0D, -2.0D, 4.0D, 4.0D, 2.0D, 2.0D, 2.0D, 2.0D, 1.0D, 1.0D}, {-6.0D, 0.0D, 6.0D, 0.0D, 6.0D, 0.0D, -6.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, -3.0D, 0.0D, -3.0D, 0.0D, 3.0D, 0.0D, 3.0D, 0.0D, -4.0D, 0.0D, 4.0D, 0.0D, -2.0D, 0.0D, 2.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, -2.0D, 0.0D, -2.0D, 0.0D, -1.0D, 0.0D, -1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D}, {0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, -6.0D, 0.0D, 6.0D, 0.0D, 6.0D, 0.0D, -6.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, -3.0D, 0.0D, -3.0D, 0.0D, 3.0D, 0.0D, 3.0D, 0.0D, -4.0D, 0.0D, 4.0D, 0.0D, -2.0D, 0.0D, 2.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, -2.0D, 0.0D, -2.0D, 0.0D, -1.0D, 0.0D, -1.0D, 0.0D}, {18.0D, -18.0D, -18.0D, 18.0D, -18.0D, 18.0D, 18.0D, -18.0D, 12.0D, 6.0D, -12.0D, -6.0D, -12.0D, -6.0D, 12.0D, 6.0D, 9.0D, -9.0D, 9.0D, -9.0D, -9.0D, 9.0D, -9.0D, 9.0D, 12.0D, -12.0D, -12.0D, 12.0D, 6.0D, -6.0D, -6.0D, 6.0D, 6.0D, 3.0D, 6.0D, 3.0D, -6.0D, -3.0D, -6.0D, -3.0D, 8.0D, 4.0D, -8.0D, -4.0D, 4.0D, 2.0D, -4.0D, -2.0D, 6.0D, -6.0D, 6.0D, -6.0D, 3.0D, -3.0D, 3.0D, -3.0D, 4.0D, 2.0D, 4.0D, 2.0D, 2.0D, 1.0D, 2.0D, 1.0D}, {-12.0D, 12.0D, 12.0D, -12.0D, 12.0D, -12.0D, -12.0D, 12.0D, -6.0D, -6.0D, 6.0D, 6.0D, 6.0D, 6.0D, -6.0D, -6.0D, -6.0D, 6.0D, -6.0D, 6.0D, 6.0D, -6.0D, 6.0D, -6.0D, -8.0D, 8.0D, 8.0D, -8.0D, -4.0D, 4.0D, 4.0D, -4.0D, -3.0D, -3.0D, -3.0D, -3.0D, 3.0D, 3.0D, 3.0D, 3.0D, -4.0D, -4.0D, 4.0D, 4.0D, -2.0D, -2.0D, 2.0D, 2.0D, -4.0D, 4.0D, -4.0D, 4.0D, -2.0D, 2.0D, -2.0D, 2.0D, -2.0D, -2.0D, -2.0D, -2.0D, -1.0D, -1.0D, -1.0D, -1.0D}, {2.0D, 0.0D, 0.0D, 0.0D, -2.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D}, {0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 2.0D, 0.0D, 0.0D, 0.0D, -2.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D}, {-6.0D, 6.0D, 0.0D, 0.0D, 6.0D, -6.0D, 0.0D, 0.0D, -4.0D, -2.0D, 0.0D, 0.0D, 4.0D, 2.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, -3.0D, 3.0D, 0.0D, 0.0D, -3.0D, 3.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, -2.0D, -1.0D, 0.0D, 0.0D, -2.0D, -1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D}, {4.0D, -4.0D, 0.0D, 0.0D, -4.0D, 4.0D, 0.0D, 0.0D, 2.0D, 2.0D, 0.0D, 0.0D, -2.0D, -2.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 2.0D, -2.0D, 0.0D, 0.0D, 2.0D, -2.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D}, {0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 2.0D, 0.0D, 0.0D, 0.0D, -2.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D}, {0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 2.0D, 0.0D, 0.0D, 0.0D, -2.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, 0.0D, 0.0D}, {0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, -6.0D, 6.0D, 0.0D, 0.0D, 6.0D, -6.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, -4.0D, -2.0D, 0.0D, 0.0D, 4.0D, 2.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, -3.0D, 3.0D, 0.0D, 0.0D, -3.0D, 3.0D, 0.0D, 0.0D, -2.0D, -1.0D, 0.0D, 0.0D, -2.0D, -1.0D, 0.0D, 0.0D}, {0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 4.0D, -4.0D, 0.0D, 0.0D, -4.0D, 4.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 2.0D, 2.0D, 0.0D, 0.0D, -2.0D, -2.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 2.0D, -2.0D, 0.0D, 0.0D, 2.0D, -2.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.0D, 0.0D}, {-6.0D, 0.0D, 6.0D, 0.0D, 6.0D, 0.0D, -6.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, -4.0D, 0.0D, -2.0D, 0.0D, 4.0D, 0.0D, 2.0D, 0.0D, -3.0D, 0.0D, 3.0D, 0.0D, -3.0D, 0.0D, 3.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, -2.0D, 0.0D, -1.0D, 0.0D, -2.0D, 0.0D, -1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D}, {0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, -6.0D, 0.0D, 6.0D, 0.0D, 6.0D, 0.0D, -6.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, -4.0D, 0.0D, -2.0D, 0.0D, 4.0D, 0.0D, 2.0D, 0.0D, -3.0D, 0.0D, 3.0D, 0.0D, -3.0D, 0.0D, 3.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, -2.0D, 0.0D, -1.0D, 0.0D, -2.0D, 0.0D, -1.0D, 0.0D}, {18.0D, -18.0D, -18.0D, 18.0D, -18.0D, 18.0D, 18.0D, -18.0D, 12.0D, 6.0D, -12.0D, -6.0D, -12.0D, -6.0D, 12.0D, 6.0D, 12.0D, -12.0D, 6.0D, -6.0D, -12.0D, 12.0D, -6.0D, 6.0D, 9.0D, -9.0D, -9.0D, 9.0D, 9.0D, -9.0D, -9.0D, 9.0D, 8.0D, 4.0D, 4.0D, 2.0D, -8.0D, -4.0D, -4.0D, -2.0D, 6.0D, 3.0D, -6.0D, -3.0D, 6.0D, 3.0D, -6.0D, -3.0D, 6.0D, -6.0D, 3.0D, -3.0D, 6.0D, -6.0D, 3.0D, -3.0D, 4.0D, 2.0D, 2.0D, 1.0D, 4.0D, 2.0D, 2.0D, 1.0D}, {-12.0D, 12.0D, 12.0D, -12.0D, 12.0D, -12.0D, -12.0D, 12.0D, -6.0D, -6.0D, 6.0D, 6.0D, 6.0D, 6.0D, -6.0D, -6.0D, -8.0D, 8.0D, -4.0D, 4.0D, 8.0D, -8.0D, 4.0D, -4.0D, -6.0D, 6.0D, 6.0D, -6.0D, -6.0D, 6.0D, 6.0D, -6.0D, -4.0D, -4.0D, -2.0D, -2.0D, 4.0D, 4.0D, 2.0D, 2.0D, -3.0D, -3.0D, 3.0D, 3.0D, -3.0D, -3.0D, 3.0D, 3.0D, -4.0D, 4.0D, -2.0D, 2.0D, -4.0D, 4.0D, -2.0D, 2.0D, -2.0D, -2.0D, -1.0D, -1.0D, -2.0D, -2.0D, -1.0D, -1.0D}, {4.0D, 0.0D, -4.0D, 0.0D, -4.0D, 0.0D, 4.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 2.0D, 0.0D, 2.0D, 0.0D, -2.0D, 0.0D, -2.0D, 0.0D, 2.0D, 0.0D, -2.0D, 0.0D, 2.0D, 0.0D, -2.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, 1.0D, 0.0D, 1.0D, 0.0D, 1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D}, {0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 4.0D, 0.0D, -4.0D, 0.0D, -4.0D, 0.0D, 4.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 2.0D, 0.0D, 2.0D, 0.0D, -2.0D, 0.0D, -2.0D, 0.0D, 2.0D, 0.0D, -2.0D, 0.0D, 2.0D, 0.0D, -2.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, 1.0D, 0.0D, 1.0D, 0.0D, 1.0D, 0.0D}, {-12.0D, 12.0D, 12.0D, -12.0D, 12.0D, -12.0D, -12.0D, 12.0D, -8.0D, -4.0D, 8.0D, 4.0D, 8.0D, 4.0D, -8.0D, -4.0D, -6.0D, 6.0D, -6.0D, 6.0D, 6.0D, -6.0D, 6.0D, -6.0D, -6.0D, 6.0D, 6.0D, -6.0D, -6.0D, 6.0D, 6.0D, -6.0D, -4.0D, -2.0D, -4.0D, -2.0D, 4.0D, 2.0D, 4.0D, 2.0D, -4.0D, -2.0D, 4.0D, 2.0D, -4.0D, -2.0D, 4.0D, 2.0D, -3.0D, 3.0D, -3.0D, 3.0D, -3.0D, 3.0D, -3.0D, 3.0D, -2.0D, -1.0D, -2.0D, -1.0D, -2.0D, -1.0D, -2.0D, -1.0D}, {8.0D, -8.0D, -8.0D, 8.0D, -8.0D, 8.0D, 8.0D, -8.0D, 4.0D, 4.0D, -4.0D, -4.0D, -4.0D, -4.0D, 4.0D, 4.0D, 4.0D, -4.0D, 4.0D, -4.0D, -4.0D, 4.0D, -4.0D, 4.0D, 4.0D, -4.0D, -4.0D, 4.0D, 4.0D, -4.0D, -4.0D, 4.0D, 2.0D, 2.0D, 2.0D, 2.0D, -2.0D, -2.0D, -2.0D, -2.0D, 2.0D, 2.0D, -2.0D, -2.0D, 2.0D, 2.0D, -2.0D, -2.0D, 2.0D, -2.0D, 2.0D, -2.0D, 2.0D, -2.0D, 2.0D, -2.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D}};
   private final double[] xval;
   private final double[] yval;
   private final double[] zval;
   private final TricubicSplineFunction[][][] splines;

   public TricubicSplineInterpolatingFunction(double[] x, double[] y, double[] z, double[][][] f, double[][][] dFdX, double[][][] dFdY, double[][][] dFdZ, double[][][] d2FdXdY, double[][][] d2FdXdZ, double[][][] d2FdYdZ, double[][][] d3FdXdYdZ) throws NoDataException, DimensionMismatchException, NonMonotonicSequenceException {
      int xLen = x.length;
      int yLen = y.length;
      int zLen = z.length;
      if (xLen != 0 && yLen != 0 && z.length != 0 && f.length != 0 && f[0].length != 0) {
         if (xLen != f.length) {
            throw new DimensionMismatchException(xLen, f.length);
         } else if (xLen != dFdX.length) {
            throw new DimensionMismatchException(xLen, dFdX.length);
         } else if (xLen != dFdY.length) {
            throw new DimensionMismatchException(xLen, dFdY.length);
         } else if (xLen != dFdZ.length) {
            throw new DimensionMismatchException(xLen, dFdZ.length);
         } else if (xLen != d2FdXdY.length) {
            throw new DimensionMismatchException(xLen, d2FdXdY.length);
         } else if (xLen != d2FdXdZ.length) {
            throw new DimensionMismatchException(xLen, d2FdXdZ.length);
         } else if (xLen != d2FdYdZ.length) {
            throw new DimensionMismatchException(xLen, d2FdYdZ.length);
         } else if (xLen != d3FdXdYdZ.length) {
            throw new DimensionMismatchException(xLen, d3FdXdYdZ.length);
         } else {
            MathArrays.checkOrder(x);
            MathArrays.checkOrder(y);
            MathArrays.checkOrder(z);
            this.xval = (double[])x.clone();
            this.yval = (double[])y.clone();
            this.zval = (double[])z.clone();
            int lastI = xLen - 1;
            int lastJ = yLen - 1;
            int lastK = zLen - 1;
            this.splines = new TricubicSplineFunction[lastI][lastJ][lastK];

            for(int i = 0; i < lastI; ++i) {
               if (f[i].length != yLen) {
                  throw new DimensionMismatchException(f[i].length, yLen);
               }

               if (dFdX[i].length != yLen) {
                  throw new DimensionMismatchException(dFdX[i].length, yLen);
               }

               if (dFdY[i].length != yLen) {
                  throw new DimensionMismatchException(dFdY[i].length, yLen);
               }

               if (dFdZ[i].length != yLen) {
                  throw new DimensionMismatchException(dFdZ[i].length, yLen);
               }

               if (d2FdXdY[i].length != yLen) {
                  throw new DimensionMismatchException(d2FdXdY[i].length, yLen);
               }

               if (d2FdXdZ[i].length != yLen) {
                  throw new DimensionMismatchException(d2FdXdZ[i].length, yLen);
               }

               if (d2FdYdZ[i].length != yLen) {
                  throw new DimensionMismatchException(d2FdYdZ[i].length, yLen);
               }

               if (d3FdXdYdZ[i].length != yLen) {
                  throw new DimensionMismatchException(d3FdXdYdZ[i].length, yLen);
               }

               int ip1 = i + 1;

               for(int j = 0; j < lastJ; ++j) {
                  if (f[i][j].length != zLen) {
                     throw new DimensionMismatchException(f[i][j].length, zLen);
                  }

                  if (dFdX[i][j].length != zLen) {
                     throw new DimensionMismatchException(dFdX[i][j].length, zLen);
                  }

                  if (dFdY[i][j].length != zLen) {
                     throw new DimensionMismatchException(dFdY[i][j].length, zLen);
                  }

                  if (dFdZ[i][j].length != zLen) {
                     throw new DimensionMismatchException(dFdZ[i][j].length, zLen);
                  }

                  if (d2FdXdY[i][j].length != zLen) {
                     throw new DimensionMismatchException(d2FdXdY[i][j].length, zLen);
                  }

                  if (d2FdXdZ[i][j].length != zLen) {
                     throw new DimensionMismatchException(d2FdXdZ[i][j].length, zLen);
                  }

                  if (d2FdYdZ[i][j].length != zLen) {
                     throw new DimensionMismatchException(d2FdYdZ[i][j].length, zLen);
                  }

                  if (d3FdXdYdZ[i][j].length != zLen) {
                     throw new DimensionMismatchException(d3FdXdYdZ[i][j].length, zLen);
                  }

                  int jp1 = j + 1;

                  for(int k = 0; k < lastK; ++k) {
                     int kp1 = k + 1;
                     double[] beta = new double[]{f[i][j][k], f[ip1][j][k], f[i][jp1][k], f[ip1][jp1][k], f[i][j][kp1], f[ip1][j][kp1], f[i][jp1][kp1], f[ip1][jp1][kp1], dFdX[i][j][k], dFdX[ip1][j][k], dFdX[i][jp1][k], dFdX[ip1][jp1][k], dFdX[i][j][kp1], dFdX[ip1][j][kp1], dFdX[i][jp1][kp1], dFdX[ip1][jp1][kp1], dFdY[i][j][k], dFdY[ip1][j][k], dFdY[i][jp1][k], dFdY[ip1][jp1][k], dFdY[i][j][kp1], dFdY[ip1][j][kp1], dFdY[i][jp1][kp1], dFdY[ip1][jp1][kp1], dFdZ[i][j][k], dFdZ[ip1][j][k], dFdZ[i][jp1][k], dFdZ[ip1][jp1][k], dFdZ[i][j][kp1], dFdZ[ip1][j][kp1], dFdZ[i][jp1][kp1], dFdZ[ip1][jp1][kp1], d2FdXdY[i][j][k], d2FdXdY[ip1][j][k], d2FdXdY[i][jp1][k], d2FdXdY[ip1][jp1][k], d2FdXdY[i][j][kp1], d2FdXdY[ip1][j][kp1], d2FdXdY[i][jp1][kp1], d2FdXdY[ip1][jp1][kp1], d2FdXdZ[i][j][k], d2FdXdZ[ip1][j][k], d2FdXdZ[i][jp1][k], d2FdXdZ[ip1][jp1][k], d2FdXdZ[i][j][kp1], d2FdXdZ[ip1][j][kp1], d2FdXdZ[i][jp1][kp1], d2FdXdZ[ip1][jp1][kp1], d2FdYdZ[i][j][k], d2FdYdZ[ip1][j][k], d2FdYdZ[i][jp1][k], d2FdYdZ[ip1][jp1][k], d2FdYdZ[i][j][kp1], d2FdYdZ[ip1][j][kp1], d2FdYdZ[i][jp1][kp1], d2FdYdZ[ip1][jp1][kp1], d3FdXdYdZ[i][j][k], d3FdXdYdZ[ip1][j][k], d3FdXdYdZ[i][jp1][k], d3FdXdYdZ[ip1][jp1][k], d3FdXdYdZ[i][j][kp1], d3FdXdYdZ[ip1][j][kp1], d3FdXdYdZ[i][jp1][kp1], d3FdXdYdZ[ip1][jp1][kp1]};
                     this.splines[i][j][k] = new TricubicSplineFunction(this.computeSplineCoefficients(beta));
                  }
               }
            }

         }
      } else {
         throw new NoDataException();
      }
   }

   public double value(double x, double y, double z) throws OutOfRangeException {
      int i = this.searchIndex(x, this.xval);
      if (i == -1) {
         throw new OutOfRangeException(x, this.xval[0], this.xval[this.xval.length - 1]);
      } else {
         int j = this.searchIndex(y, this.yval);
         if (j == -1) {
            throw new OutOfRangeException(y, this.yval[0], this.yval[this.yval.length - 1]);
         } else {
            int k = this.searchIndex(z, this.zval);
            if (k == -1) {
               throw new OutOfRangeException(z, this.zval[0], this.zval[this.zval.length - 1]);
            } else {
               double xN = (x - this.xval[i]) / (this.xval[i + 1] - this.xval[i]);
               double yN = (y - this.yval[j]) / (this.yval[j + 1] - this.yval[j]);
               double zN = (z - this.zval[k]) / (this.zval[k + 1] - this.zval[k]);
               return this.splines[i][j][k].value(xN, yN, zN);
            }
         }
      }
   }

   private int searchIndex(double c, double[] val) {
      if (c < val[0]) {
         return -1;
      } else {
         int max = val.length;

         for(int i = 1; i < max; ++i) {
            if (c <= val[i]) {
               return i - 1;
            }
         }

         return -1;
      }
   }

   private double[] computeSplineCoefficients(double[] beta) {
      int sz = true;
      double[] a = new double[64];

      for(int i = 0; i < 64; ++i) {
         double result = 0.0D;
         double[] row = AINV[i];

         for(int j = 0; j < 64; ++j) {
            result += row[j] * beta[j];
         }

         a[i] = result;
      }

      return a;
   }
}
