package org.apache.commons.math3.optimization;

/** @deprecated */
@Deprecated
public interface BaseOptimizer<PAIR> {
   int getMaxEvaluations();

   int getEvaluations();

   ConvergenceChecker<PAIR> getConvergenceChecker();
}
