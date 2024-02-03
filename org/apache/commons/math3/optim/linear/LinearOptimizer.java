package org.apache.commons.math3.optim.linear;

import java.util.Collection;
import java.util.Collections;
import org.apache.commons.math3.exception.TooManyIterationsException;
import org.apache.commons.math3.optim.ConvergenceChecker;
import org.apache.commons.math3.optim.OptimizationData;
import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.nonlinear.scalar.MultivariateOptimizer;

public abstract class LinearOptimizer extends MultivariateOptimizer {
   private LinearObjectiveFunction function;
   private Collection<LinearConstraint> linearConstraints;
   private boolean nonNegative;

   protected LinearOptimizer() {
      super((ConvergenceChecker)null);
   }

   protected boolean isRestrictedToNonNegative() {
      return this.nonNegative;
   }

   protected LinearObjectiveFunction getFunction() {
      return this.function;
   }

   protected Collection<LinearConstraint> getConstraints() {
      return Collections.unmodifiableCollection(this.linearConstraints);
   }

   public PointValuePair optimize(OptimizationData... optData) throws TooManyIterationsException {
      return super.optimize(optData);
   }

   protected void parseOptimizationData(OptimizationData... optData) {
      super.parseOptimizationData(optData);
      OptimizationData[] arr$ = optData;
      int len$ = optData.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         OptimizationData data = arr$[i$];
         if (data instanceof LinearObjectiveFunction) {
            this.function = (LinearObjectiveFunction)data;
         } else if (data instanceof LinearConstraintSet) {
            this.linearConstraints = ((LinearConstraintSet)data).getConstraints();
         } else if (data instanceof NonNegativeConstraint) {
            this.nonNegative = ((NonNegativeConstraint)data).isRestrictedToNonNegative();
         }
      }

   }
}
