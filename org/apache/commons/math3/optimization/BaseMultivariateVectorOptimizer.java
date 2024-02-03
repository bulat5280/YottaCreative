package org.apache.commons.math3.optimization;

import org.apache.commons.math3.analysis.MultivariateVectorFunction;

/** @deprecated */
@Deprecated
public interface BaseMultivariateVectorOptimizer<FUNC extends MultivariateVectorFunction> extends BaseOptimizer<PointVectorValuePair> {
   /** @deprecated */
   @Deprecated
   PointVectorValuePair optimize(int var1, FUNC var2, double[] var3, double[] var4, double[] var5);
}
