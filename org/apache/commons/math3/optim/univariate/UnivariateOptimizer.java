package org.apache.commons.math3.optim.univariate;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.exception.TooManyEvaluationsException;
import org.apache.commons.math3.optim.BaseOptimizer;
import org.apache.commons.math3.optim.ConvergenceChecker;
import org.apache.commons.math3.optim.OptimizationData;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;

public abstract class UnivariateOptimizer extends BaseOptimizer<UnivariatePointValuePair> {
   private UnivariateFunction function;
   private GoalType goal;
   private double start;
   private double min;
   private double max;

   protected UnivariateOptimizer(ConvergenceChecker<UnivariatePointValuePair> checker) {
      super(checker);
   }

   public UnivariatePointValuePair optimize(OptimizationData... optData) throws TooManyEvaluationsException {
      return (UnivariatePointValuePair)super.optimize(optData);
   }

   public GoalType getGoalType() {
      return this.goal;
   }

   protected void parseOptimizationData(OptimizationData... optData) {
      super.parseOptimizationData(optData);
      OptimizationData[] arr$ = optData;
      int len$ = optData.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         OptimizationData data = arr$[i$];
         if (data instanceof SearchInterval) {
            SearchInterval interval = (SearchInterval)data;
            this.min = interval.getMin();
            this.max = interval.getMax();
            this.start = interval.getStartValue();
         } else if (data instanceof UnivariateObjectiveFunction) {
            this.function = ((UnivariateObjectiveFunction)data).getObjectiveFunction();
         } else if (data instanceof GoalType) {
            this.goal = (GoalType)data;
         }
      }

   }

   public double getStartValue() {
      return this.start;
   }

   public double getMin() {
      return this.min;
   }

   public double getMax() {
      return this.max;
   }

   protected double computeObjectiveValue(double x) {
      super.incrementEvaluationCount();
      return this.function.value(x);
   }
}
