package org.apache.commons.math3.optim.nonlinear.scalar.gradient;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.solvers.BrentSolver;
import org.apache.commons.math3.analysis.solvers.UnivariateSolver;
import org.apache.commons.math3.exception.MathIllegalStateException;
import org.apache.commons.math3.exception.MathInternalError;
import org.apache.commons.math3.exception.MathUnsupportedOperationException;
import org.apache.commons.math3.exception.TooManyEvaluationsException;
import org.apache.commons.math3.exception.util.LocalizedFormats;
import org.apache.commons.math3.optim.ConvergenceChecker;
import org.apache.commons.math3.optim.OptimizationData;
import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;
import org.apache.commons.math3.optim.nonlinear.scalar.GradientMultivariateOptimizer;
import org.apache.commons.math3.util.FastMath;

public class NonLinearConjugateGradientOptimizer extends GradientMultivariateOptimizer {
   private final NonLinearConjugateGradientOptimizer.Formula updateFormula;
   private final Preconditioner preconditioner;
   private final UnivariateSolver solver;
   private double initialStep;

   public NonLinearConjugateGradientOptimizer(NonLinearConjugateGradientOptimizer.Formula updateFormula, ConvergenceChecker<PointValuePair> checker) {
      this(updateFormula, checker, new BrentSolver(), new NonLinearConjugateGradientOptimizer.IdentityPreconditioner());
   }

   public NonLinearConjugateGradientOptimizer(NonLinearConjugateGradientOptimizer.Formula updateFormula, ConvergenceChecker<PointValuePair> checker, UnivariateSolver lineSearchSolver) {
      this(updateFormula, checker, lineSearchSolver, new NonLinearConjugateGradientOptimizer.IdentityPreconditioner());
   }

   public NonLinearConjugateGradientOptimizer(NonLinearConjugateGradientOptimizer.Formula updateFormula, ConvergenceChecker<PointValuePair> checker, UnivariateSolver lineSearchSolver, Preconditioner preconditioner) {
      super(checker);
      this.initialStep = 1.0D;
      this.updateFormula = updateFormula;
      this.solver = lineSearchSolver;
      this.preconditioner = preconditioner;
      this.initialStep = 1.0D;
   }

   public PointValuePair optimize(OptimizationData... optData) throws TooManyEvaluationsException {
      return super.optimize(optData);
   }

   protected PointValuePair doOptimize() {
      ConvergenceChecker<PointValuePair> checker = this.getConvergenceChecker();
      double[] point = this.getStartPoint();
      GoalType goal = this.getGoalType();
      int n = point.length;
      double[] r = this.computeObjectiveGradient(point);
      if (goal == GoalType.MINIMIZE) {
         for(int i = 0; i < n; ++i) {
            r[i] = -r[i];
         }
      }

      double[] steepestDescent = this.preconditioner.precondition(point, r);
      double[] searchDirection = (double[])steepestDescent.clone();
      double delta = 0.0D;

      for(int i = 0; i < n; ++i) {
         delta += r[i] * searchDirection[i];
      }

      PointValuePair current = null;
      int maxEval = this.getMaxEvaluations();

      while(true) {
         this.incrementIterationCount();
         double objective = this.computeObjectiveValue(point);
         PointValuePair previous = current;
         current = new PointValuePair(point, objective);
         if (previous != null && checker.converged(this.getIterations(), previous, current)) {
            return current;
         }

         UnivariateFunction lsf = new NonLinearConjugateGradientOptimizer.LineSearchFunction(point, searchDirection);
         double uB = this.findUpperBound(lsf, 0.0D, this.initialStep);
         double step = this.solver.solve(maxEval, lsf, 0.0D, uB, 1.0E-15D);
         maxEval -= this.solver.getEvaluations();

         int i;
         for(i = 0; i < point.length; ++i) {
            point[i] += step * searchDirection[i];
         }

         r = this.computeObjectiveGradient(point);
         if (goal == GoalType.MINIMIZE) {
            for(i = 0; i < n; ++i) {
               r[i] = -r[i];
            }
         }

         double deltaOld = delta;
         double[] newSteepestDescent = this.preconditioner.precondition(point, r);
         delta = 0.0D;

         for(int i = 0; i < n; ++i) {
            delta += r[i] * newSteepestDescent[i];
         }

         double beta;
         switch(this.updateFormula) {
         case FLETCHER_REEVES:
            beta = delta / deltaOld;
            break;
         case POLAK_RIBIERE:
            double deltaMid = 0.0D;

            for(int i = 0; i < r.length; ++i) {
               deltaMid += r[i] * steepestDescent[i];
            }

            beta = (delta - deltaMid) / deltaOld;
            break;
         default:
            throw new MathInternalError();
         }

         steepestDescent = newSteepestDescent;
         if (this.getIterations() % n != 0 && !(beta < 0.0D)) {
            for(int i = 0; i < n; ++i) {
               searchDirection[i] = steepestDescent[i] + beta * searchDirection[i];
            }
         } else {
            searchDirection = (double[])newSteepestDescent.clone();
         }
      }
   }

   protected void parseOptimizationData(OptimizationData... optData) {
      super.parseOptimizationData(optData);
      OptimizationData[] arr$ = optData;
      int len$ = optData.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         OptimizationData data = arr$[i$];
         if (data instanceof NonLinearConjugateGradientOptimizer.BracketingStep) {
            this.initialStep = ((NonLinearConjugateGradientOptimizer.BracketingStep)data).getBracketingStep();
            break;
         }
      }

      this.checkParameters();
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

   private void checkParameters() {
      if (this.getLowerBound() != null || this.getUpperBound() != null) {
         throw new MathUnsupportedOperationException(LocalizedFormats.CONSTRAINT, new Object[0]);
      }
   }

   private class LineSearchFunction implements UnivariateFunction {
      private final double[] currentPoint;
      private final double[] searchDirection;

      public LineSearchFunction(double[] point, double[] direction) {
         this.currentPoint = (double[])point.clone();
         this.searchDirection = (double[])direction.clone();
      }

      public double value(double x) {
         double[] shiftedPoint = (double[])this.currentPoint.clone();

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

   public static class BracketingStep implements OptimizationData {
      private final double initialStep;

      public BracketingStep(double step) {
         this.initialStep = step;
      }

      public double getBracketingStep() {
         return this.initialStep;
      }
   }

   public static enum Formula {
      FLETCHER_REEVES,
      POLAK_RIBIERE;
   }
}
