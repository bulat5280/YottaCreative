package org.apache.commons.math3.transform;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.exception.NonMonotonicSequenceException;
import org.apache.commons.math3.exception.NotStrictlyPositiveException;

public interface RealTransformer {
   double[] transform(double[] var1, TransformType var2) throws MathIllegalArgumentException;

   double[] transform(UnivariateFunction var1, double var2, double var4, int var6, TransformType var7) throws NonMonotonicSequenceException, NotStrictlyPositiveException, MathIllegalArgumentException;
}
