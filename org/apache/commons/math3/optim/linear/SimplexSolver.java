package org.apache.commons.math3.optim.linear;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.math3.exception.TooManyIterationsException;
import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.util.Precision;

public class SimplexSolver extends LinearOptimizer {
   static final int DEFAULT_ULPS = 10;
   static final double DEFAULT_CUT_OFF = 1.0E-12D;
   private static final double DEFAULT_EPSILON = 1.0E-6D;
   private final double epsilon;
   private final int maxUlps;
   private final double cutOff;

   public SimplexSolver() {
      this(1.0E-6D, 10, 1.0E-12D);
   }

   public SimplexSolver(double epsilon) {
      this(epsilon, 10, 1.0E-12D);
   }

   public SimplexSolver(double epsilon, int maxUlps) {
      this(epsilon, maxUlps, 1.0E-12D);
   }

   public SimplexSolver(double epsilon, int maxUlps, double cutOff) {
      this.epsilon = epsilon;
      this.maxUlps = maxUlps;
      this.cutOff = cutOff;
   }

   private Integer getPivotColumn(SimplexTableau tableau) {
      double minValue = 0.0D;
      Integer minPos = null;

      for(int i = tableau.getNumObjectiveFunctions(); i < tableau.getWidth() - 1; ++i) {
         double entry = tableau.getEntry(0, i);
         if (entry < minValue) {
            minValue = entry;
            minPos = i;
         }
      }

      return minPos;
   }

   private Integer getPivotRow(SimplexTableau tableau, int col) {
      List<Integer> minRatioPositions = new ArrayList();
      double minRatio = Double.MAX_VALUE;

      for(int i = tableau.getNumObjectiveFunctions(); i < tableau.getHeight(); ++i) {
         double rhs = tableau.getEntry(i, tableau.getWidth() - 1);
         double entry = tableau.getEntry(i, col);
         if (Precision.compareTo(entry, 0.0D, this.maxUlps) > 0) {
            double ratio = rhs / entry;
            int cmp = Double.compare(ratio, minRatio);
            if (cmp == 0) {
               minRatioPositions.add(i);
            } else if (cmp < 0) {
               minRatio = ratio;
               minRatioPositions = new ArrayList();
               minRatioPositions.add(i);
            }
         }
      }

      if (minRatioPositions.size() == 0) {
         return null;
      } else {
         if (minRatioPositions.size() > 1) {
            int varStart;
            int varEnd;
            if (tableau.getNumArtificialVariables() > 0) {
               Iterator i$ = minRatioPositions.iterator();

               while(i$.hasNext()) {
                  Integer row = (Integer)i$.next();

                  for(varStart = 0; varStart < tableau.getNumArtificialVariables(); ++varStart) {
                     varEnd = varStart + tableau.getArtificialVariableOffset();
                     double entry = tableau.getEntry(row, varEnd);
                     if (Precision.equals(entry, 1.0D, this.maxUlps) && row.equals(tableau.getBasicRow(varEnd))) {
                        return row;
                     }
                  }
               }
            }

            if (this.getEvaluations() < this.getMaxEvaluations() / 2) {
               Integer minRow = null;
               int minIndex = tableau.getWidth();
               varStart = tableau.getNumObjectiveFunctions();
               varEnd = tableau.getWidth() - 1;
               Iterator i$ = minRatioPositions.iterator();

               while(i$.hasNext()) {
                  Integer row = (Integer)i$.next();

                  for(int i = varStart; i < varEnd && !row.equals(minRow); ++i) {
                     Integer basicRow = tableau.getBasicRow(i);
                     if (basicRow != null && basicRow.equals(row) && i < minIndex) {
                        minIndex = i;
                        minRow = row;
                     }
                  }
               }

               return minRow;
            }
         }

         return (Integer)minRatioPositions.get(0);
      }
   }

   protected void doIteration(SimplexTableau tableau) throws TooManyIterationsException, UnboundedSolutionException {
      this.incrementIterationCount();
      Integer pivotCol = this.getPivotColumn(tableau);
      Integer pivotRow = this.getPivotRow(tableau, pivotCol);
      if (pivotRow == null) {
         throw new UnboundedSolutionException();
      } else {
         double pivotVal = tableau.getEntry(pivotRow, pivotCol);
         tableau.divideRow(pivotRow, pivotVal);

         for(int i = 0; i < tableau.getHeight(); ++i) {
            if (i != pivotRow) {
               double multiplier = tableau.getEntry(i, pivotCol);
               tableau.subtractRow(i, pivotRow, multiplier);
            }
         }

      }
   }

   protected void solvePhase1(SimplexTableau tableau) throws TooManyIterationsException, UnboundedSolutionException, NoFeasibleSolutionException {
      if (tableau.getNumArtificialVariables() != 0) {
         while(!tableau.isOptimal()) {
            this.doIteration(tableau);
         }

         if (!Precision.equals(tableau.getEntry(0, tableau.getRhsOffset()), 0.0D, this.epsilon)) {
            throw new NoFeasibleSolutionException();
         }
      }
   }

   public PointValuePair doOptimize() throws TooManyIterationsException, UnboundedSolutionException, NoFeasibleSolutionException {
      SimplexTableau tableau = new SimplexTableau(this.getFunction(), this.getConstraints(), this.getGoalType(), this.isRestrictedToNonNegative(), this.epsilon, this.maxUlps, this.cutOff);
      this.solvePhase1(tableau);
      tableau.dropPhase1Objective();

      while(!tableau.isOptimal()) {
         this.doIteration(tableau);
      }

      return tableau.getSolution();
   }
}
