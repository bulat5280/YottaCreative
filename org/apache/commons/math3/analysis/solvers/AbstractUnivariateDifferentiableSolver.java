package org.apache.commons.math3.analysis.solvers;

import org.apache.commons.math3.analysis.differentiation.DerivativeStructure;
import org.apache.commons.math3.analysis.differentiation.UnivariateDifferentiableFunction;
import org.apache.commons.math3.exception.TooManyEvaluationsException;

public abstract class AbstractUnivariateDifferentiableSolver extends BaseAbstractUnivariateSolver<UnivariateDifferentiableFunction> implements UnivariateDifferentiableSolver {
   private UnivariateDifferentiableFunction function;

   protected AbstractUnivariateDifferentiableSolver(double absoluteAccuracy) {
      super(absoluteAccuracy);
   }

   protected AbstractUnivariateDifferentiableSolver(double relativeAccuracy, double absoluteAccuracy, double functionValueAccuracy) {
      super(relativeAccuracy, absoluteAccuracy, functionValueAccuracy);
   }

   protected DerivativeStructure computeObjectiveValueAndDerivative(double point) throws TooManyEvaluationsException {
      this.incrementEvaluationCount();
      return this.function.value(new DerivativeStructure(1, 1, 0, point));
   }

   protected void setup(int maxEval, UnivariateDifferentiableFunction f, double min, double max, double startValue) {
      super.setup(maxEval, f, min, max, startValue);
      this.function = f;
   }
}
