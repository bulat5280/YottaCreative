package org.apache.commons.math3.optimization.general;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.solvers.BrentSolver;
import org.apache.commons.math3.analysis.solvers.UnivariateSolver;
import org.apache.commons.math3.exception.MathIllegalStateException;
import org.apache.commons.math3.exception.util.LocalizedFormats;
import org.apache.commons.math3.optimization.ConvergenceChecker;
import org.apache.commons.math3.optimization.GoalType;
import org.apache.commons.math3.optimization.PointValuePair;
import org.apache.commons.math3.optimization.SimpleValueChecker;
import org.apache.commons.math3.util.FastMath;

/** @deprecated */
@Deprecated
public class NonLinearConjugateGradientOptimizer extends AbstractScalarDifferentiableOptimizer {
   private final ConjugateGradientFormula updateFormula;
   private final Preconditioner preconditioner;
   private final UnivariateSolver solver;
   private double initialStep;
   private double[] point;

   /** @deprecated */
   @Deprecated
   public NonLinearConjugateGradientOptimizer(ConjugateGradientFormula updateFormula) {
      this(updateFormula, new SimpleValueChecker());
   }

   public NonLinearConjugateGradientOptimizer(ConjugateGradientFormula updateFormula, ConvergenceChecker<PointValuePair> checker) {
      this(updateFormula, checker, new BrentSolver(), new NonLinearConjugateGradientOptimizer.IdentityPreconditioner());
   }

   public NonLinearConjugateGradientOptimizer(ConjugateGradientFormula updateFormula, ConvergenceChecker<PointValuePair> checker, UnivariateSolver lineSearchSolver) {
      this(updateFormula, checker, lineSearchSolver, new NonLinearConjugateGradientOptimizer.IdentityPreconditioner());
   }

   public NonLinearConjugateGradientOptimizer(ConjugateGradientFormula updateFormula, ConvergenceChecker<PointValuePair> checker, UnivariateSolver lineSearchSolver, Preconditioner preconditioner) {
      super(checker);
      this.updateFormula = updateFormula;
      this.solver = lineSearchSolver;
      this.preconditioner = preconditioner;
      this.initialStep = 1.0D;
   }

   public void setInitialStep(double initialStep) {
      if (initialStep <= 0.0D) {
         this.initialStep = 1.0D;
      } else {
         this.initialStep = initialStep;
      }

   }

   protected PointValuePair doOptimize() {
      ConvergenceChecker<PointValuePair> checker = this.getConvergenceChecker();
      this.point = this.getStartPoint();
      GoalType goal = this.getGoalType();
      int n = this.point.length;
      double[] r = this.computeObjectiveGradient(this.point);
      if (goal == GoalType.MINIMIZE) {
         for(int i = 0; i < n; ++i) {
            r[i] = -r[i];
         }
      }

      double[] steepestDescent = this.preconditioner.precondition(this.point, r);
      double[] searchDirection = (double[])steepestDescent.clone();
      double delta = 0.0D;

      for(int i = 0; i < n; ++i) {
         delta += r[i] * searchDirection[i];
      }

      PointValuePair current = null;
      int iter = 0;
      int maxEval = this.getMaxEvaluations();

      while(true) {
         while(true) {
            ++iter;
            double objective = this.computeObjectiveValue(this.point);
            PointValuePair previous = current;
            current = new PointValuePair(this.point, objective);
            if (previous != null && checker.converged(iter, previous, current)) {
               return current;
            }

            UnivariateFunction lsf = new NonLinearConjugateGradientOptimizer.LineSearchFunction(searchDirection);
            double uB = this.findUpperBound(lsf, 0.0D, this.initialStep);
            double step = this.solver.solve(maxEval, lsf, 0.0D, uB, 1.0E-15D);
            maxEval -= this.solver.getEvaluations();

            int i;
            for(i = 0; i < this.point.length; ++i) {
               double[] var10000 = this.point;
               var10000[i] += step * searchDirection[i];
            }

            r = this.computeObjectiveGradient(this.point);
            if (goal == GoalType.MINIMIZE) {
               for(i = 0; i < n; ++i) {
                  r[i] = -r[i];
               }
            }

            double deltaOld = delta;
            double[] newSteepestDescent = this.preconditioner.precondition(this.point, r);
            delta = 0.0D;

            for(int i = 0; i < n; ++i) {
               delta += r[i] * newSteepestDescent[i];
            }

            double beta;
            if (this.updateFormula == ConjugateGradientFormula.FLETCHER_REEVES) {
               beta = delta / deltaOld;
            } else {
               double deltaMid = 0.0D;

               for(int i = 0; i < r.length; ++i) {
                  deltaMid += r[i] * steepestDescent[i];
               }

               beta = (delta - deltaMid) / deltaOld;
            }

            steepestDescent = newSteepestDescent;
            if (iter % n != 0 && !(beta < 0.0D)) {
               for(int i = 0; i < n; ++i) {
                  searchDirection[i] = steepestDescent[i] + beta * searchDirection[i];
               }
            } else {
               searchDirection = (double[])newSteepestDescent.clone();
            }
         }
      }
   }

   private double findUpperBound(UnivariateFunction f, double a, double h) {
      double yA = f.value(a);

      double yB;
      for(double step = h; step < Double.MAX_VALUE; step *= FastMath.max(2.0D, yA / yB)) {
         double b = a + step;
         yB = f.value(b);
         if (yA * yB <= 0.0D) {
            return b;
         }
      }

      throw new MathIllegalStateException(LocalizedFormats.UNABLE_TO_BRACKET_OPTIMUM_IN_LINE_SEARCH, new Object[0]);
   }

   private class LineSearchFunction implements UnivariateFunction {
      private final double[] searchDirection;

      public LineSearchFunction(double[] searchDirection) {
         this.searchDirection = searchDirection;
      }

      public double value(double x) {
         double[] shiftedPoint = (double[])NonLinearConjugateGradientOptimizer.this.point.clone();

         for(int ix = 0; ix < shiftedPoint.length; ++ix) {
            shiftedPoint[ix] += x * this.searchDirection[ix];
         }

         double[] gradient = NonLinearConjugateGradientOptimizer.this.computeObjectiveGradient(shiftedPoint);
         double dotProduct = 0.0D;

         for(int i = 0; i < gradient.length; ++i) {
            dotProduct += gradient[i] * this.searchDirection[i];
         }

         return dotProduct;
      }
   }

   public static class IdentityPreconditioner implements Preconditioner {
      public double[] precondition(double[] variables, double[] r) {
         return (double[])r.clone();
      }
   }
}
