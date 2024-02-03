package org.apache.commons.math3.optimization.general;

/** @deprecated */
@Deprecated
public interface Preconditioner {
   double[] precondition(double[] var1, double[] var2);
}
