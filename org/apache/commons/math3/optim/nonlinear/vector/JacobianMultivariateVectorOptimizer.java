package org.apache.commons.math3.optim.nonlinear.vector;

import org.apache.commons.math3.analysis.MultivariateMatrixFunction;
import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.TooManyEvaluationsException;
import org.apache.commons.math3.optim.ConvergenceChecker;
import org.apache.commons.math3.optim.OptimizationData;
import org.apache.commons.math3.optim.PointVectorValuePair;

public abstract class JacobianMultivariateVectorOptimizer extends MultivariateVectorOptimizer {
   private MultivariateMatrixFunction jacobian;

   protected JacobianMultivariateVectorOptimizer(ConvergenceChecker<PointVectorValuePair> checker) {
      super(checker);
   }

   protected double[][] computeJacobian(double[] params) {
      return this.jacobian.value(params);
   }

   public PointVectorValuePair optimize(OptimizationData... optData) throws TooManyEvaluationsException, DimensionMismatchException {
      return super.optimize(optData);
   }

   protected void parseOptimizationData(OptimizationData... optData) {
      super.parseOptimizationData(optData);
      OptimizationData[] arr$ = optData;
      int len$ = optData.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         OptimizationData data = arr$[i$];
         if (data instanceof ModelFunctionJacobian) {
            this.jacobian = ((ModelFunctionJacobian)data).getModelFunctionJacobian();
            break;
         }
      }

   }
}
