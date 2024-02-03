package org.apache.commons.math3.fitting;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.math3.analysis.MultivariateMatrixFunction;
import org.apache.commons.math3.analysis.MultivariateVectorFunction;
import org.apache.commons.math3.analysis.ParametricUnivariateFunction;
import org.apache.commons.math3.optim.InitialGuess;
import org.apache.commons.math3.optim.MaxEval;
import org.apache.commons.math3.optim.PointVectorValuePair;
import org.apache.commons.math3.optim.nonlinear.vector.ModelFunction;
import org.apache.commons.math3.optim.nonlinear.vector.ModelFunctionJacobian;
import org.apache.commons.math3.optim.nonlinear.vector.MultivariateVectorOptimizer;
import org.apache.commons.math3.optim.nonlinear.vector.Target;
import org.apache.commons.math3.optim.nonlinear.vector.Weight;

public class CurveFitter<T extends ParametricUnivariateFunction> {
   private final MultivariateVectorOptimizer optimizer;
   private final List<WeightedObservedPoint> observations;

   public CurveFitter(MultivariateVectorOptimizer optimizer) {
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

      CurveFitter<T>.TheoreticalValuesFunction model = new CurveFitter.TheoreticalValuesFunction(f);
      PointVectorValuePair optimum = this.optimizer.optimize(new MaxEval(maxEval), model.getModelFunction(), model.getModelFunctionJacobian(), new Target(target), new Weight(weights), new InitialGuess(initialGuess));
      return optimum.getPointRef();
   }

   private class TheoreticalValuesFunction {
      private final ParametricUnivariateFunction f;

      public TheoreticalValuesFunction(ParametricUnivariateFunction f) {
         this.f = f;
      }

      public ModelFunction getModelFunction() {
         return new ModelFunction(new MultivariateVectorFunction() {
            public double[] value(double[] point) {
               double[] values = new double[CurveFitter.this.observations.size()];
               int i = 0;

               WeightedObservedPoint observed;
               for(Iterator i$ = CurveFitter.this.observations.iterator(); i$.hasNext(); values[i++] = TheoreticalValuesFunction.this.f.value(observed.getX(), point)) {
                  observed = (WeightedObservedPoint)i$.next();
               }

               return values;
            }
         });
      }

      public ModelFunctionJacobian getModelFunctionJacobian() {
         return new ModelFunctionJacobian(new MultivariateMatrixFunction() {
            public double[][] value(double[] point) {
               double[][] jacobian = new double[CurveFitter.this.observations.size()][];
               int i = 0;

               WeightedObservedPoint observed;
               for(Iterator i$ = CurveFitter.this.observations.iterator(); i$.hasNext(); jacobian[i++] = TheoreticalValuesFunction.this.f.gradient(observed.getX(), point)) {
                  observed = (WeightedObservedPoint)i$.next();
               }

               return jacobian;
            }
         });
      }
   }
}
