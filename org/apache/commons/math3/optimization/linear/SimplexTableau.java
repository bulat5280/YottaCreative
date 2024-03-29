package org.apache.commons.math3.optimization.linear;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.apache.commons.math3.optimization.GoalType;
import org.apache.commons.math3.optimization.PointValuePair;
import org.apache.commons.math3.util.FastMath;
import org.apache.commons.math3.util.Precision;

/** @deprecated */
class SimplexTableau implements Serializable {
   private static final String NEGATIVE_VAR_COLUMN_LABEL = "x-";
   private static final int DEFAULT_ULPS = 10;
   private static final double CUTOFF_THRESHOLD = 1.0E-12D;
   private static final long serialVersionUID = -1369660067587938365L;
   private final LinearObjectiveFunction f;
   private final List<LinearConstraint> constraints;
   private final boolean restrictToNonNegative;
   private final List<String> columnLabels;
   private transient RealMatrix tableau;
   private final int numDecisionVariables;
   private final int numSlackVariables;
   private int numArtificialVariables;
   private final double epsilon;
   private final int maxUlps;

   SimplexTableau(LinearObjectiveFunction f, Collection<LinearConstraint> constraints, GoalType goalType, boolean restrictToNonNegative, double epsilon) {
      this(f, constraints, goalType, restrictToNonNegative, epsilon, 10);
   }

   SimplexTableau(LinearObjectiveFunction f, Collection<LinearConstraint> constraints, GoalType goalType, boolean restrictToNonNegative, double epsilon, int maxUlps) {
      this.columnLabels = new ArrayList();
      this.f = f;
      this.constraints = this.normalizeConstraints(constraints);
      this.restrictToNonNegative = restrictToNonNegative;
      this.epsilon = epsilon;
      this.maxUlps = maxUlps;
      this.numDecisionVariables = f.getCoefficients().getDimension() + (restrictToNonNegative ? 0 : 1);
      this.numSlackVariables = this.getConstraintTypeCounts(Relationship.LEQ) + this.getConstraintTypeCounts(Relationship.GEQ);
      this.numArtificialVariables = this.getConstraintTypeCounts(Relationship.EQ) + this.getConstraintTypeCounts(Relationship.GEQ);
      this.tableau = this.createTableau(goalType == GoalType.MAXIMIZE);
      this.initializeColumnLabels();
   }

   protected void initializeColumnLabels() {
      if (this.getNumObjectiveFunctions() == 2) {
         this.columnLabels.add("W");
      }

      this.columnLabels.add("Z");

      int i;
      for(i = 0; i < this.getOriginalNumDecisionVariables(); ++i) {
         this.columnLabels.add("x" + i);
      }

      if (!this.restrictToNonNegative) {
         this.columnLabels.add("x-");
      }

      for(i = 0; i < this.getNumSlackVariables(); ++i) {
         this.columnLabels.add("s" + i);
      }

      for(i = 0; i < this.getNumArtificialVariables(); ++i) {
         this.columnLabels.add("a" + i);
      }

      this.columnLabels.add("RHS");
   }

   protected RealMatrix createTableau(boolean maximize) {
      int width = this.numDecisionVariables + this.numSlackVariables + this.numArtificialVariables + this.getNumObjectiveFunctions() + 1;
      int height = this.constraints.size() + this.getNumObjectiveFunctions();
      Array2DRowRealMatrix matrix = new Array2DRowRealMatrix(height, width);
      if (this.getNumObjectiveFunctions() == 2) {
         matrix.setEntry(0, 0, -1.0D);
      }

      int zIndex = this.getNumObjectiveFunctions() == 1 ? 0 : 1;
      matrix.setEntry(zIndex, zIndex, maximize ? 1.0D : -1.0D);
      RealVector objectiveCoefficients = maximize ? this.f.getCoefficients().mapMultiply(-1.0D) : this.f.getCoefficients();
      this.copyArray(objectiveCoefficients.toArray(), matrix.getDataRef()[zIndex]);
      matrix.setEntry(zIndex, width - 1, maximize ? this.f.getConstantTerm() : -1.0D * this.f.getConstantTerm());
      if (!this.restrictToNonNegative) {
         matrix.setEntry(zIndex, this.getSlackVariableOffset() - 1, getInvertedCoefficientSum(objectiveCoefficients));
      }

      int slackVar = 0;
      int artificialVar = 0;

      for(int i = 0; i < this.constraints.size(); ++i) {
         LinearConstraint constraint = (LinearConstraint)this.constraints.get(i);
         int row = this.getNumObjectiveFunctions() + i;
         this.copyArray(constraint.getCoefficients().toArray(), matrix.getDataRef()[row]);
         if (!this.restrictToNonNegative) {
            matrix.setEntry(row, this.getSlackVariableOffset() - 1, getInvertedCoefficientSum(constraint.getCoefficients()));
         }

         matrix.setEntry(row, width - 1, constraint.getValue());
         if (constraint.getRelationship() == Relationship.LEQ) {
            matrix.setEntry(row, this.getSlackVariableOffset() + slackVar++, 1.0D);
         } else if (constraint.getRelationship() == Relationship.GEQ) {
            matrix.setEntry(row, this.getSlackVariableOffset() + slackVar++, -1.0D);
         }

         if (constraint.getRelationship() == Relationship.EQ || constraint.getRelationship() == Relationship.GEQ) {
            matrix.setEntry(0, this.getArtificialVariableOffset() + artificialVar, 1.0D);
            matrix.setEntry(row, this.getArtificialVariableOffset() + artificialVar++, 1.0D);
            matrix.setRowVector(0, matrix.getRowVector(0).subtract(matrix.getRowVector(row)));
         }
      }

      return matrix;
   }

   public List<LinearConstraint> normalizeConstraints(Collection<LinearConstraint> originalConstraints) {
      List<LinearConstraint> normalized = new ArrayList();
      Iterator i$ = originalConstraints.iterator();

      while(i$.hasNext()) {
         LinearConstraint constraint = (LinearConstraint)i$.next();
         normalized.add(this.normalize(constraint));
      }

      return normalized;
   }

   private LinearConstraint normalize(LinearConstraint constraint) {
      return constraint.getValue() < 0.0D ? new LinearConstraint(constraint.getCoefficients().mapMultiply(-1.0D), constraint.getRelationship().oppositeRelationship(), -1.0D * constraint.getValue()) : new LinearConstraint(constraint.getCoefficients(), constraint.getRelationship(), constraint.getValue());
   }

   protected final int getNumObjectiveFunctions() {
      return this.numArtificialVariables > 0 ? 2 : 1;
   }

   private int getConstraintTypeCounts(Relationship relationship) {
      int count = 0;
      Iterator i$ = this.constraints.iterator();

      while(i$.hasNext()) {
         LinearConstraint constraint = (LinearConstraint)i$.next();
         if (constraint.getRelationship() == relationship) {
            ++count;
         }
      }

      return count;
   }

   protected static double getInvertedCoefficientSum(RealVector coefficients) {
      double sum = 0.0D;
      double[] arr$ = coefficients.toArray();
      int len$ = arr$.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         double coefficient = arr$[i$];
         sum -= coefficient;
      }

      return sum;
   }

   protected Integer getBasicRow(int col) {
      Integer row = null;

      for(int i = 0; i < this.getHeight(); ++i) {
         double entry = this.getEntry(i, col);
         if (Precision.equals(entry, 1.0D, this.maxUlps) && row == null) {
            row = i;
         } else if (!Precision.equals(entry, 0.0D, this.maxUlps)) {
            return null;
         }
      }

      return row;
   }

   protected void dropPhase1Objective() {
      if (this.getNumObjectiveFunctions() != 1) {
         Set<Integer> columnsToDrop = new TreeSet();
         columnsToDrop.add(0);

         int i;
         for(i = this.getNumObjectiveFunctions(); i < this.getArtificialVariableOffset(); ++i) {
            double entry = this.tableau.getEntry(0, i);
            if (Precision.compareTo(entry, 0.0D, this.epsilon) > 0) {
               columnsToDrop.add(i);
            }
         }

         int i;
         for(i = 0; i < this.getNumArtificialVariables(); ++i) {
            i = i + this.getArtificialVariableOffset();
            if (this.getBasicRow(i) == null) {
               columnsToDrop.add(i);
            }
         }

         double[][] matrix = new double[this.getHeight() - 1][this.getWidth() - columnsToDrop.size()];

         int i;
         for(i = 1; i < this.getHeight(); ++i) {
            i = 0;

            for(int j = 0; j < this.getWidth(); ++j) {
               if (!columnsToDrop.contains(j)) {
                  matrix[i - 1][i++] = this.tableau.getEntry(i, j);
               }
            }
         }

         Integer[] drop = (Integer[])columnsToDrop.toArray(new Integer[columnsToDrop.size()]);

         for(i = drop.length - 1; i >= 0; --i) {
            this.columnLabels.remove(drop[i]);
         }

         this.tableau = new Array2DRowRealMatrix(matrix);
         this.numArtificialVariables = 0;
      }
   }

   private void copyArray(double[] src, double[] dest) {
      System.arraycopy(src, 0, dest, this.getNumObjectiveFunctions(), src.length);
   }

   boolean isOptimal() {
      for(int i = this.getNumObjectiveFunctions(); i < this.getWidth() - 1; ++i) {
         double entry = this.tableau.getEntry(0, i);
         if (Precision.compareTo(entry, 0.0D, this.epsilon) < 0) {
            return false;
         }
      }

      return true;
   }

   protected PointValuePair getSolution() {
      int negativeVarColumn = this.columnLabels.indexOf("x-");
      Integer negativeVarBasicRow = negativeVarColumn > 0 ? this.getBasicRow(negativeVarColumn) : null;
      double mostNegative = negativeVarBasicRow == null ? 0.0D : this.getEntry(negativeVarBasicRow, this.getRhsOffset());
      Set<Integer> basicRows = new HashSet();
      double[] coefficients = new double[this.getOriginalNumDecisionVariables()];

      for(int i = 0; i < coefficients.length; ++i) {
         int colIndex = this.columnLabels.indexOf("x" + i);
         if (colIndex < 0) {
            coefficients[i] = 0.0D;
         } else {
            Integer basicRow = this.getBasicRow(colIndex);
            if (basicRow != null && basicRow == 0) {
               coefficients[i] = 0.0D;
            } else if (basicRows.contains(basicRow)) {
               coefficients[i] = 0.0D - (this.restrictToNonNegative ? 0.0D : mostNegative);
            } else {
               basicRows.add(basicRow);
               coefficients[i] = (basicRow == null ? 0.0D : this.getEntry(basicRow, this.getRhsOffset())) - (this.restrictToNonNegative ? 0.0D : mostNegative);
            }
         }
      }

      return new PointValuePair(coefficients, this.f.getValue(coefficients));
   }

   protected void divideRow(int dividendRow, double divisor) {
      for(int j = 0; j < this.getWidth(); ++j) {
         this.tableau.setEntry(dividendRow, j, this.tableau.getEntry(dividendRow, j) / divisor);
      }

   }

   protected void subtractRow(int minuendRow, int subtrahendRow, double multiple) {
      for(int i = 0; i < this.getWidth(); ++i) {
         double result = this.tableau.getEntry(minuendRow, i) - this.tableau.getEntry(subtrahendRow, i) * multiple;
         if (FastMath.abs(result) < 1.0E-12D) {
            result = 0.0D;
         }

         this.tableau.setEntry(minuendRow, i, result);
      }

   }

   protected final int getWidth() {
      return this.tableau.getColumnDimension();
   }

   protected final int getHeight() {
      return this.tableau.getRowDimension();
   }

   protected final double getEntry(int row, int column) {
      return this.tableau.getEntry(row, column);
   }

   protected final void setEntry(int row, int column, double value) {
      this.tableau.setEntry(row, column, value);
   }

   protected final int getSlackVariableOffset() {
      return this.getNumObjectiveFunctions() + this.numDecisionVariables;
   }

   protected final int getArtificialVariableOffset() {
      return this.getNumObjectiveFunctions() + this.numDecisionVariables + this.numSlackVariables;
   }

   protected final int getRhsOffset() {
      return this.getWidth() - 1;
   }

   protected final int getNumDecisionVariables() {
      return this.numDecisionVariables;
   }

   protected final int getOriginalNumDecisionVariables() {
      return this.f.getCoefficients().getDimension();
   }

   protected final int getNumSlackVariables() {
      return this.numSlackVariables;
   }

   protected final int getNumArtificialVariables() {
      return this.numArtificialVariables;
   }

   protected final double[][] getData() {
      return this.tableau.getData();
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof SimplexTableau)) {
         return false;
      } else {
         SimplexTableau rhs = (SimplexTableau)other;
         return this.restrictToNonNegative == rhs.restrictToNonNegative && this.numDecisionVariables == rhs.numDecisionVariables && this.numSlackVariables == rhs.numSlackVariables && this.numArtificialVariables == rhs.numArtificialVariables && this.epsilon == rhs.epsilon && this.maxUlps == rhs.maxUlps && this.f.equals(rhs.f) && this.constraints.equals(rhs.constraints) && this.tableau.equals(rhs.tableau);
      }
   }

   public int hashCode() {
      return Boolean.valueOf(this.restrictToNonNegative).hashCode() ^ this.numDecisionVariables ^ this.numSlackVariables ^ this.numArtificialVariables ^ Double.valueOf(this.epsilon).hashCode() ^ this.maxUlps ^ this.f.hashCode() ^ this.constraints.hashCode() ^ this.tableau.hashCode();
   }

   private void writeObject(ObjectOutputStream oos) throws IOException {
      oos.defaultWriteObject();
      MatrixUtils.serializeRealMatrix(this.tableau, oos);
   }

   private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
      ois.defaultReadObject();
      MatrixUtils.deserializeRealMatrix(this, "tableau", ois);
   }
}
