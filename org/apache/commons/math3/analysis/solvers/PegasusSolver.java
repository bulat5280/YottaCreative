package org.apache.commons.math3.analysis.solvers;

public class PegasusSolver extends BaseSecantSolver {
   public PegasusSolver() {
      super(1.0E-6D, BaseSecantSolver.Method.PEGASUS);
   }

   public PegasusSolver(double absoluteAccuracy) {
      super(absoluteAccuracy, BaseSecantSolver.Method.PEGASUS);
   }

   public PegasusSolver(double relativeAccuracy, double absoluteAccuracy) {
      super(relativeAccuracy, absoluteAccuracy, BaseSecantSolver.Method.PEGASUS);
   }

   public PegasusSolver(double relativeAccuracy, double absoluteAccuracy, double functionValueAccuracy) {
      super(relativeAccuracy, absoluteAccuracy, functionValueAccuracy, BaseSecantSolver.Method.PEGASUS);
   }
}
