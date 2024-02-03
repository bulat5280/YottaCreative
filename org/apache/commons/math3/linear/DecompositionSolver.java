package org.apache.commons.math3.linear;

public interface DecompositionSolver {
   RealVector solve(RealVector var1);

   RealMatrix solve(RealMatrix var1);

   boolean isNonSingular();

   RealMatrix getInverse();
}
