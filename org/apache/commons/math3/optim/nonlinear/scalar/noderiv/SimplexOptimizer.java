package org.apache.commons.math3.optim.nonlinear.scalar.noderiv;

import java.util.Comparator;
import org.apache.commons.math3.analysis.MultivariateFunction;
import org.apache.commons.math3.exception.MathUnsupportedOperationException;
import org.apache.commons.math3.exception.NullArgumentException;
import org.apache.commons.math3.exception.util.LocalizedFormats;
import org.apache.commons.math3.optim.ConvergenceChecker;
import org.apache.commons.math3.optim.OptimizationData;
import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.SimpleValueChecker;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;
import org.apache.commons.math3.optim.nonlinear.scalar.MultivariateOptimizer;

public class SimplexOptimizer extends MultivariateOptimizer {
   private AbstractSimplex simplex;

   public SimplexOptimizer(ConvergenceChecker<PointValuePair> checker) {
      super(checker);
   }

   public SimplexOptimizer(double rel, double abs) {
      this(new SimpleValueChecker(rel, abs));
   }

   public PointValuePair optimize(OptimizationData... optData) {
      return super.optimize(optData);
   }

   protected PointValuePair doOptimize() {
      this.checkParameters();
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
         if (this.getIterations() > 0) {
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
         this.incrementIterationCount();
      }
   }

   protected void parseOptimizationData(OptimizationData... optData) {
      super.parseOptimizationData(optData);
      OptimizationData[] arr$ = optData;
      int len$ = optData.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         OptimizationData data = arr$[i$];
         if (data instanceof AbstractSimplex) {
            this.simplex = (AbstractSimplex)data;
            break;
         }
      }

   }

   private void checkParameters() {
      if (this.simplex == null) {
         throw new NullArgumentException();
      } else if (this.getLowerBound() != null || this.getUpperBound() != null) {
         throw new MathUnsupportedOperationException(LocalizedFormats.CONSTRAINT, new Object[0]);
      }
   }
}
