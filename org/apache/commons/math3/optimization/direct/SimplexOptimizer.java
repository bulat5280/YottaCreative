package org.apache.commons.math3.optimization.direct;

import java.util.Comparator;
import org.apache.commons.math3.analysis.MultivariateFunction;
import org.apache.commons.math3.exception.NullArgumentException;
import org.apache.commons.math3.optimization.ConvergenceChecker;
import org.apache.commons.math3.optimization.GoalType;
import org.apache.commons.math3.optimization.MultivariateOptimizer;
import org.apache.commons.math3.optimization.OptimizationData;
import org.apache.commons.math3.optimization.PointValuePair;
import org.apache.commons.math3.optimization.SimpleValueChecker;

/** @deprecated */
@Deprecated
public class SimplexOptimizer extends BaseAbstractMultivariateOptimizer<MultivariateFunction> implements MultivariateOptimizer {
   private AbstractSimplex simplex;

   /** @deprecated */
   @Deprecated
   public SimplexOptimizer() {
      this(new SimpleValueChecker());
   }

   public SimplexOptimizer(ConvergenceChecker<PointValuePair> checker) {
      super(checker);
   }

   public SimplexOptimizer(double rel, double abs) {
      this(new SimpleValueChecker(rel, abs));
   }

   /** @deprecated */
   @Deprecated
   public void setSimplex(AbstractSimplex simplex) {
      this.parseOptimizationData(simplex);
   }

   protected PointValuePair optimizeInternal(int maxEval, MultivariateFunction f, GoalType goalType, OptimizationData... optData) {
      this.parseOptimizationData(optData);
      return super.optimizeInternal(maxEval, f, goalType, optData);
   }

   private void parseOptimizationData(OptimizationData... optData) {
      OptimizationData[] arr$ = optData;
      int len$ = optData.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         OptimizationData data = arr$[i$];
         if (data instanceof AbstractSimplex) {
            this.simplex = (AbstractSimplex)data;
         }
      }

   }

   protected PointValuePair doOptimize() {
      if (this.simplex == null) {
         throw new NullArgumentException();
      } else {
         MultivariateFunction evalFunc = new MultivariateFunction() {
            public double value(double[] point) {
               return SimplexOptimizer.this.computeObjectiveValue(point);
            }
         };
         final boolean isMinim = this.getGoalType() == GoalType.MINIMIZE;
         Comparator<PointValuePair> comparator = new Comparator<PointValuePair>() {
            public int compare(PointValuePair o1, PointValuePair o2) {
               double v1 = (Double)o1.getValue();
               double v2 = (Double)o2.getValue();
               return isMinim ? Double.compare(v1, v2) : Double.compare(v2, v1);
            }
         };
         this.simplex.build(this.getStartPoint());
         this.simplex.evaluate(evalFunc, comparator);
         PointValuePair[] previous = null;
         int iteration = 0;
         ConvergenceChecker checker = this.getConvergenceChecker();

         while(true) {
            if (iteration > 0) {
               boolean converged = true;
               int i = 0;

               while(true) {
                  if (i >= this.simplex.getSize()) {
                     if (converged) {
                        return this.simplex.getPoint(0);
                     }
                     break;
                  }

                  PointValuePair prev = previous[i];
                  converged = converged && checker.converged(iteration, prev, this.simplex.getPoint(i));
                  ++i;
               }
            }

            previous = this.simplex.getPoints();
            this.simplex.iterate(evalFunc, comparator);
            ++iteration;
         }
      }
   }
}
