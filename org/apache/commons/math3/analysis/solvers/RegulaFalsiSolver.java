package org.apache.commons.math3.analysis.solvers;

public class RegulaFalsiSolver extends BaseSecantSolver {
   public RegulaFalsiSolver() {
      super(1.0E-6D, BaseSecantSolver.Method.REGULA_FALSI);
   }

   public RegulaFalsiSolver(double absoluteAccuracy) {
      super(absoluteAccuracy, BaseSecantSolver.Method.REGULA_FALSI);
   }

   public RegulaFalsiSolver(double relativeAccuracy, double absoluteAccuracy) {
      super(relativeAccuracy, absoluteAccuracy, BaseSecantSolver.Method.REGULA_FALSI);
   }

   public RegulaFalsiSolver(double relativeAccuracy, double absoluteAccuracy, double functionValueAccuracy) {
      super(relativeAccuracy, absoluteAccuracy, functionValueAccuracy, BaseSecantSolver.Method.REGULA_FALSI);
   }
}
