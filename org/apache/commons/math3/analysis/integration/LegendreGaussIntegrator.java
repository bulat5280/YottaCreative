package org.apache.commons.math3.analysis.integration;

import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.exception.MaxCountExceededException;
import org.apache.commons.math3.exception.NotStrictlyPositiveException;
import org.apache.commons.math3.exception.NumberIsTooSmallException;
import org.apache.commons.math3.exception.TooManyEvaluationsException;
import org.apache.commons.math3.exception.util.LocalizedFormats;
import org.apache.commons.math3.util.FastMath;

/** @deprecated */
@Deprecated
public class LegendreGaussIntegrator extends BaseAbstractUnivariateIntegrator {
   private static final double[] ABSCISSAS_2 = new double[]{-1.0D / FastMath.sqrt(3.0D), 1.0D / FastMath.sqrt(3.0D)};
   private static final double[] WEIGHTS_2 = new double[]{1.0D, 1.0D};
   private static final double[] ABSCISSAS_3 = new double[]{-FastMath.sqrt(0.6D), 0.0D, FastMath.sqrt(0.6D)};
   private static final double[] WEIGHTS_3 = new double[]{0.5555555555555556D, 0.8888888888888888D, 0.5555555555555556D};
   private static final double[] ABSCISSAS_4 = new double[]{-FastMath.sqrt((15.0D + 2.0D * FastMath.sqrt(30.0D)) / 35.0D), -FastMath.sqrt((15.0D - 2.0D * FastMath.sqrt(30.0D)) / 35.0D), FastMath.sqrt((15.0D - 2.0D * FastMath.sqrt(30.0D)) / 35.0D), FastMath.sqrt((15.0D + 2.0D * FastMath.sqrt(30.0D)) / 35.0D)};
   private static final double[] WEIGHTS_4 = new double[]{(90.0D - 5.0D * FastMath.sqrt(30.0D)) / 180.0D, (90.0D + 5.0D * FastMath.sqrt(30.0D)) / 180.0D, (90.0D + 5.0D * FastMath.sqrt(30.0D)) / 180.0D, (90.0D - 5.0D * FastMath.sqrt(30.0D)) / 180.0D};
   private static final double[] ABSCISSAS_5 = new double[]{-FastMath.sqrt((35.0D + 2.0D * FastMath.sqrt(70.0D)) / 63.0D), -FastMath.sqrt((35.0D - 2.0D * FastMath.sqrt(70.0D)) / 63.0D), 0.0D, FastMath.sqrt((35.0D - 2.0D * FastMath.sqrt(70.0D)) / 63.0D), FastMath.sqrt((35.0D + 2.0D * FastMath.sqrt(70.0D)) / 63.0D)};
   private static final double[] WEIGHTS_5 = new double[]{(322.0D - 13.0D * FastMath.sqrt(70.0D)) / 900.0D, (322.0D + 13.0D * FastMath.sqrt(70.0D)) / 900.0D, 0.5688888888888889D, (322.0D + 13.0D * FastMath.sqrt(70.0D)) / 900.0D, (322.0D - 13.0D * FastMath.sqrt(70.0D)) / 900.0D};
   private final double[] abscissas;
   private final double[] weights;

   public LegendreGaussIntegrator(int n, double relativeAccuracy, double absoluteAccuracy, int minimalIterationCount, int maximalIterationCount) throws MathIllegalArgumentException, NotStrictlyPositiveException, NumberIsTooSmallException {
      super(relativeAccuracy, absoluteAccuracy, minimalIterationCount, maximalIterationCount);
      switch(n) {
      case 2:
         this.abscissas = ABSCISSAS_2;
         this.weights = WEIGHTS_2;
         break;
      case 3:
         this.abscissas = ABSCISSAS_3;
         this.weights = WEIGHTS_3;
         break;
      case 4:
         this.abscissas = ABSCISSAS_4;
         this.weights = WEIGHTS_4;
         break;
      case 5:
         this.abscissas = ABSCISSAS_5;
         this.weights = WEIGHTS_5;
         break;
      default:
         throw new MathIllegalArgumentException(LocalizedFormats.N_POINTS_GAUSS_LEGENDRE_INTEGRATOR_NOT_SUPPORTED, new Object[]{n, 2, 5});
      }

   }

   public LegendreGaussIntegrator(int n, double relativeAccuracy, double absoluteAccuracy) throws MathIllegalArgumentException {
      this(n, relativeAccuracy, absoluteAccuracy, 3, Integer.MAX_VALUE);
   }

   public LegendreGaussIntegrator(int n, int minimalIterationCount, int maximalIterationCount) throws MathIllegalArgumentException {
      this(n, 1.0E-6D, 1.0E-15D, minimalIterationCount, maximalIterationCount);
   }

   protected double doIntegrate() throws MathIllegalArgumentException, TooManyEvaluationsException, MaxCountExceededException {
      double oldt = this.stage(1);
      int n = 2;

      while(true) {
         double t = this.stage(n);
         double delta = FastMath.abs(t - oldt);
         double limit = FastMath.max(this.getAbsoluteAccuracy(), this.getRelativeAccuracy() * (FastMath.abs(oldt) + FastMath.abs(t)) * 0.5D);
         if (this.iterations.getCount() + 1 >= this.getMinimalIterationCount() && delta <= limit) {
            return t;
         }

         double ratio = FastMath.min(4.0D, FastMath.pow(delta / limit, 0.5D / (double)this.abscissas.length));
         n = FastMath.max((int)(ratio * (double)n), n + 1);
         oldt = t;
         this.iterations.incrementCount();
      }
   }

   private double stage(int n) throws TooManyEvaluationsException {
      double step = (this.getMax() - this.getMin()) / (double)n;
      double halfStep = step / 2.0D;
      double midPoint = this.getMin() + halfStep;
      double sum = 0.0D;

      for(int i = 0; i < n; ++i) {
         for(int j = 0; j < this.abscissas.length; ++j) {
            sum += this.weights[j] * this.computeObjectiveValue(midPoint + halfStep * this.abscissas[j]);
         }

         midPoint += step;
      }

      return halfStep * sum;
   }
}
