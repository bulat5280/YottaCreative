package org.apache.commons.math3.optim.nonlinear.scalar;

import org.apache.commons.math3.analysis.MultivariateFunction;
import org.apache.commons.math3.exception.TooManyEvaluationsException;
import org.apache.commons.math3.optim.BaseMultivariateOptimizer;
import org.apache.commons.math3.optim.ConvergenceChecker;
import org.apache.commons.math3.optim.OptimizationData;
import org.apache.commons.math3.optim.PointValuePair;

public abstract class MultivariateOptimizer extends BaseMultivariateOptimizer<PointValuePair> {
   private MultivariateFunction function;
   private GoalType goal;

   protected MultivariateOptimizer(ConvergenceChecker<PointValuePair> checker) {
      super(checker);
   }

   public PointValuePair optimize(OptimizationData... optData) throws TooManyEvaluationsException {
      return (PointValuePair)super.optimize(optData);
   }

   protected void parseOptimizationData(OptimizationData... optData) {
      super.parseOptimizationData(optData);
      OptimizationData[] arr$ = optData;
      int len$ = optData.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         OptimizationData data = arr$[i$];
         if (data instanceof GoalType) {
            this.goal = (GoalType)data;
         } else if (data instanceof ObjectiveFunction) {
            this.function = ((ObjectiveFunction)data).getObjectiveFunction();
         }
      }

   }

   public GoalType getGoalType() {
      return this.goal;
   }

   protected double computeObjectiveValue(double[] params) {
      super.incrementEvaluationCount();
      return this.function.value(params);
   }
}
