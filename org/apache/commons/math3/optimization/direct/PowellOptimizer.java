package org.apache.commons.math3.optimization.direct;

import org.apache.commons.math3.analysis.MultivariateFunction;
import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.exception.NotStrictlyPositiveException;
import org.apache.commons.math3.exception.NumberIsTooSmallException;
import org.apache.commons.math3.optimization.ConvergenceChecker;
import org.apache.commons.math3.optimization.GoalType;
import org.apache.commons.math3.optimization.MultivariateOptimizer;
import org.apache.commons.math3.optimization.PointValuePair;
import org.apache.commons.math3.optimization.univariate.BracketFinder;
import org.apache.commons.math3.optimization.univariate.BrentOptimizer;
import org.apache.commons.math3.optimization.univariate.SimpleUnivariateValueChecker;
import org.apache.commons.math3.optimization.univariate.UnivariatePointValuePair;
import org.apache.commons.math3.util.FastMath;
import org.apache.commons.math3.util.MathArrays;

/** @deprecated */
@Deprecated
public class PowellOptimizer extends BaseAbstractMultivariateOptimizer<MultivariateFunction> implements MultivariateOptimizer {
   private static final double MIN_RELATIVE_TOLERANCE = 2.0D * FastMath.ulp(1.0D);
   private final double relativeThreshold;
   private final double absoluteThreshold;
   private final PowellOptimizer.LineSearch line;

   public PowellOptimizer(double rel, double abs, ConvergenceChecker<PointValuePair> checker) {
      this(rel, abs, FastMath.sqrt(rel), FastMath.sqrt(abs), checker);
   }

   public PowellOptimizer(double rel, double abs, double lineRel, double lineAbs, ConvergenceChecker<PointValuePair> checker) {
      super(checker);
      if (rel < MIN_RELATIVE_TOLERANCE) {
         throw new NumberIsTooSmallException(rel, MIN_RELATIVE_TOLERANCE, true);
      } else if (abs <= 0.0D) {
         throw new NotStrictlyPositiveException(abs);
      } else {
         this.relativeThreshold = rel;
         this.absoluteThreshold = abs;
         this.line = new PowellOptimizer.LineSearch(lineRel, lineAbs);
      }
   }

   public PowellOptimizer(double rel, double abs) {
      this(rel, abs, (ConvergenceChecker)null);
   }

   public PowellOptimizer(double rel, double abs, double lineRel, double lineAbs) {
      this(rel, abs, lineRel, lineAbs, (ConvergenceChecker)null);
   }

   protected PointValuePair doOptimize() {
      GoalType goal = this.getGoalType();
      double[] guess = this.getStartPoint();
      int n = guess.length;
      double[][] direc = new double[n][n];

      for(int i = 0; i < n; ++i) {
         direc[i][i] = 1.0D;
      }

      ConvergenceChecker<PointValuePair> checker = this.getConvergenceChecker();
      double[] x = guess;
      double fVal = this.computeObjectiveValue(guess);
      double[] x1 = (double[])guess.clone();
      int iter = 0;

      while(true) {
         ++iter;
         double fX = fVal;
         double fX2 = 0.0D;
         double delta = 0.0D;
         int bigInd = 0;
         double alphaMin = 0.0D;

         for(int i = 0; i < n; ++i) {
            double[] d = MathArrays.copyOf(direc[i]);
            fX2 = fVal;
            UnivariatePointValuePair optimum = this.line.search(x, d);
            fVal = optimum.getValue();
            alphaMin = optimum.getPoint();
            double[][] result = this.newPointAndDirection(x, d, alphaMin);
            x = result[0];
            if (fX2 - fVal > delta) {
               delta = fX2 - fVal;
               bigInd = i;
            }
         }

         boolean stop = 2.0D * (fVal - fVal) <= this.relativeThreshold * (FastMath.abs(fX) + FastMath.abs(fVal)) + this.absoluteThreshold;
         PointValuePair previous = new PointValuePair(x1, fX);
         PointValuePair current = new PointValuePair(x, fVal);
         if (!stop && checker != null) {
            stop = checker.converged(iter, previous, current);
         }

         if (stop) {
            if (goal == GoalType.MINIMIZE) {
               return fVal < fX ? current : previous;
            }

            return fVal > fX ? current : previous;
         }

         double[] d = new double[n];
         double[] x2 = new double[n];

         for(int i = 0; i < n; ++i) {
            d[i] = x[i] - x1[i];
            x2[i] = 2.0D * x[i] - x1[i];
         }

         x1 = (double[])x.clone();
         fX2 = this.computeObjectiveValue(x2);
         if (fX > fX2) {
            double t = 2.0D * (fX + fX2 - 2.0D * fVal);
            double temp = fX - fVal - delta;
            t *= temp * temp;
            temp = fX - fX2;
            t -= delta * temp * temp;
            if (t < 0.0D) {
               UnivariatePointValuePair optimum = this.line.search(x, d);
               fVal = optimum.getValue();
               alphaMin = optimum.getPoint();
               double[][] result = this.newPointAndDirection(x, d, alphaMin);
               x = result[0];
               int lastInd = n - 1;
               direc[bigInd] = direc[lastInd];
               direc[lastInd] = result[1];
            }
         }
      }
   }

   private double[][] newPointAndDirection(double[] p, double[] d, double optimum) {
      int n = p.length;
      double[] nP = new double[n];
      double[] nD = new double[n];

      for(int i = 0; i < n; ++i) {
         nD[i] = d[i] * optimum;
         nP[i] = p[i] + nD[i];
      }

      double[][] result = new double[][]{nP, nD};
      return result;
   }

   private class LineSearch extends BrentOptimizer {
      private static final double REL_TOL_UNUSED = 1.0E-15D;
      private static final double ABS_TOL_UNUSED = Double.MIN_VALUE;
      private final BracketFinder bracket = new BracketFinder();

      LineSearch(double rel, double abs) {
         super(1.0E-15D, Double.MIN_VALUE, new SimpleUnivariateValueChecker(rel, abs));
      }

      public UnivariatePointValuePair search(final double[] p, final double[] d) {
         final int n = p.length;
         UnivariateFunction f = new UnivariateFunction() {
            public double value(double alpha) {
               double[] x = new double[n];

               for(int i = 0; i < n; ++i) {
                  x[i] = p[i] + alpha * d[i];
               }

               double obj = PowellOptimizer.this.computeObjectiveValue(x);
               return obj;
            }
         };
         GoalType goal = PowellOptimizer.this.getGoalType();
         this.bracket.search(f, goal, 0.0D, 1.0D);
         return this.optimize(Integer.MAX_VALUE, f, goal, this.bracket.getLo(), this.bracket.getHi(), this.bracket.getMid());
      }
   }
}
