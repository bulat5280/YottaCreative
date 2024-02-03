package org.apache.commons.math3.optimization.fitting;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.math3.analysis.DifferentiableMultivariateVectorFunction;
import org.apache.commons.math3.analysis.MultivariateMatrixFunction;
import org.apache.commons.math3.analysis.ParametricUnivariateFunction;
import org.apache.commons.math3.analysis.differentiation.DerivativeStructure;
import org.apache.commons.math3.analysis.differentiation.MultivariateDifferentiableVectorFunction;
import org.apache.commons.math3.optimization.DifferentiableMultivariateVectorOptimizer;
import org.apache.commons.math3.optimization.MultivariateDifferentiableVectorOptimizer;
import org.apache.commons.math3.optimization.PointVectorValuePair;

/** @deprecated */
@Deprecated
public class CurveFitter<T extends ParametricUnivariateFunction> {
   /** @deprecated */
   @Deprecated
   private final DifferentiableMultivariateVectorOptimizer oldOptimizer;
   private final MultivariateDifferentiableVectorOptimizer optimizer;
   private final List<WeightedObservedPoint> observations;

   /** @deprecated */
   public CurveFitter(DifferentiableMultivariateVectorOptimizer optimizer) {
      this.oldOptimizer = optimizer;
      this.optimizer = null;
      this.observations = new ArrayList();
   }

   public CurveFitter(MultivariateDifferentiableVectorOptimizer optimizer) {
      this.oldOptimizer = null;
      this.optimizer = optimizer;
      this.observations = new ArrayList();
   }

   public void addObservedPoint(double x, double y) {
      this.addObservedPoint(1.0D, x, y);
   }

   public void addObservedPoint(double weight, double x, double y) {
      this.observations.add(new WeightedObservedPoint(weight, x, y));
   }

   public void addObservedPoint(WeightedObservedPoint observed) {
      this.observations.add(observed);
   }

   public WeightedObservedPoint[] getObservations() {
      return (WeightedObservedPoint[])this.observations.toArray(new WeightedObservedPoint[this.observations.size()]);
   }

   public void clearObservations() {
      this.observations.clear();
   }

   public double[] fit(T f, double[] initialGuess) {
      return this.fit(Integer.MAX_VALUE, f, initialGuess);
   }

   public double[] fit(int maxEval, T f, double[] initialGuess) {
      double[] target = new double[this.observations.size()];
      double[] weights = new double[this.observations.size()];
      int i = 0;

      for(Iterator i$ = this.observations.iterator(); i$.hasNext(); ++i) {
         WeightedObservedPoint point = (WeightedObservedPoint)i$.next();
         target[i] = point.getY();
         weights[i] = point.getWeight();
      }

      PointVectorValuePair optimum;
      if (this.optimizer == null) {
         optimum = this.oldOptimizer.optimize(maxEval, new CurveFitter.OldTheoreticalValuesFunction(f), target, weights, initialGuess);
      } else {
         optimum = this.optimizer.optimize(maxEval, new CurveFitter.TheoreticalValuesFunction(f), target, weights, initialGuess);
      }

      return optimum.getPointRef();
   }

   private class TheoreticalValuesFunction implements MultivariateDifferentiableVectorFunction {
      private final ParametricUnivariateFunction f;

      public TheoreticalValuesFunction(ParametricUnivariateFunction f) {
         this.f = f;
      }

      public double[] value(double[] point) {
         double[] values = new double[CurveFitter.this.observations.size()];
         int i = 0;

         WeightedObservedPoint observed;
         for(Iterator i$ = CurveFitter.this.observations.iterator(); i$.hasNext(); values[i++] = this.f.value(observed.getX(), point)) {
            observed = (WeightedObservedPoint)i$.next();
         }

         return values;
      }

      public DerivativeStructure[] value(DerivativeStructure[] point) {
         double[] parameters = new double[point.length];

         for(int kx = 0; kx < point.length; ++kx) {
            parameters[kx] = point[kx].getValue();
         }

         DerivativeStructure[] values = new DerivativeStructure[CurveFitter.this.observations.size()];
         int i = 0;

         DerivativeStructure vi;
         for(Iterator i$ = CurveFitter.this.observations.iterator(); i$.hasNext(); values[i++] = vi) {
            WeightedObservedPoint observed = (WeightedObservedPoint)i$.next();
            vi = new DerivativeStructure(point.length, 1, this.f.value(observed.getX(), parameters));

            for(int k = 0; k < point.length; ++k) {
               vi = vi.add(new DerivativeStructure(point.length, 1, k, 0.0D));
            }
         }

         return values;
      }
   }

   /** @deprecated */
   @Deprecated
   private class OldTheoreticalValuesFunction implements DifferentiableMultivariateVectorFunction {
      private final ParametricUnivariateFunction f;

      public OldTheoreticalValuesFunction(ParametricUnivariateFunction f) {
         this.f = f;
      }

      public MultivariateMatrixFunction jacobian() {
         return new MultivariateMatrixFunction() {
            public double[][] value(double[] point) {
               double[][] jacobian = new double[CurveFitter.this.observations.size()][];
               int i = 0;

               WeightedObservedPoint observed;
               for(Iterator i$ = CurveFitter.this.observations.iterator(); i$.hasNext(); jacobian[i++] = OldTheoreticalValuesFunction.this.f.gradient(observed.getX(), point)) {
                  observed = (WeightedObservedPoint)i$.next();
               }

               return jacobian;
            }
         };
      }

      public double[] value(double[] point) {
         double[] values = new double[CurveFitter.this.observations.size()];
         int i = 0;

         WeightedObservedPoint observed;
         for(Iterator i$ = CurveFitter.this.observations.iterator(); i$.hasNext(); values[i++] = this.f.value(observed.getX(), point)) {
            observed = (WeightedObservedPoint)i$.next();
         }

         return values;
      }
   }
}
