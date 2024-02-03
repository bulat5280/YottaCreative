package org.apache.commons.math3.optimization;

import org.apache.commons.math3.analysis.MultivariateFunction;

/** @deprecated */
@Deprecated
public interface BaseMultivariateOptimizer<FUNC extends MultivariateFunction> extends BaseOptimizer<PointValuePair> {
   /** @deprecated */
   @Deprecated
   PointValuePair optimize(int var1, FUNC var2, GoalType var3, double[] var4);
}
