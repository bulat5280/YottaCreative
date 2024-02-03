package org.apache.commons.math3.optim.nonlinear.scalar;

import org.apache.commons.math3.analysis.MultivariateVectorFunction;
import org.apache.commons.math3.exception.TooManyEvaluationsException;
import org.apache.commons.math3.optim.ConvergenceChecker;
import org.apache.commons.math3.optim.OptimizationData;
import org.apache.commons.math3.optim.PointValuePair;

public abstract class GradientMultivariateOptimizer extends MultivariateOptimizer {
   private MultivariateVectorFunction gradient;

   protected GradientMultivariateOptimizer(ConvergenceChecker<PointValuePair> checker) {
      super(checker);
   }

   protected double[] computeObjectiveGradient(double[] params) {
      return this.gradient.value(params);
   }

   public PointValuePair optimize(OptimizationData... optData) throws TooManyEvaluationsException {
      return super.optimize(optData);
   }

   protected void parseOptimizationData(OptimizationData... optData) {
      super.parseOptimizationData(optData);
      OptimizationData[] arr$ = optData;
      int len$ = optData.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         OptimizationData data = arr$[i$];
         if (data instanceof ObjectiveFunctionGradient) {
            this.gradient = ((ObjectiveFunctionGradient)data).getObjectiveFunctionGradient();
            break;
         }
      }

   }
}
