package org.apache.commons.math3.optim.nonlinear.vector.jacobian;

import java.util.Arrays;
import org.apache.commons.math3.exception.ConvergenceException;
import org.apache.commons.math3.exception.MathUnsupportedOperationException;
import org.apache.commons.math3.exception.util.LocalizedFormats;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.optim.ConvergenceChecker;
import org.apache.commons.math3.optim.PointVectorValuePair;
import org.apache.commons.math3.util.FastMath;
import org.apache.commons.math3.util.Precision;

public class LevenbergMarquardtOptimizer extends AbstractLeastSquaresOptimizer {
   private static final double TWO_EPS;
   private int solvedCols;
   private double[] diagR;
   private double[] jacNorm;
   private double[] beta;
   private int[] permutation;
   private int rank;
   private double lmPar;
   private double[] lmDir;
   private final double initialStepBoundFactor;
   private final double costRelativeTolerance;
   private final double parRelativeTolerance;
   private final double orthoTolerance;
   private final double qrRankingThreshold;
   private double[] weightedResidual;
   private double[][] weightedJacobian;

   public LevenbergMarquardtOptimizer() {
      this(100.0D, 1.0E-10D, 1.0E-10D, 1.0E-10D, Precision.SAFE_MIN);
   }

   public LevenbergMarquardtOptimizer(ConvergenceChecker<PointVectorValuePair> checker) {
      this(100.0D, checker, 1.0E-10D, 1.0E-10D, 1.0E-10D, Precision.SAFE_MIN);
   }

   public LevenbergMarquardtOptimizer(double initialStepBoundFactor, ConvergenceChecker<PointVectorValuePair> checker, double costRelativeTolerance, double parRelativeTolerance, double orthoTolerance, double threshold) {
      super(checker);
      this.initialStepBoundFactor = initialStepBoundFactor;
      this.costRelativeTolerance = costRelativeTolerance;
      this.parRelativeTolerance = parRelativeTolerance;
      this.orthoTolerance = orthoTolerance;
      this.qrRankingThreshold = threshold;
   }

   public LevenbergMarquardtOptimizer(double costRelativeTolerance, double parRelativeTolerance, double orthoTolerance) {
      this(100.0D, costRelativeTolerance, parRelativeTolerance, orthoTolerance, Precision.SAFE_MIN);
   }

   public LevenbergMarquardtOptimizer(double initialStepBoundFactor, double costRelativeTolerance, double parRelativeTolerance, double orthoTolerance, double threshold) {
      super((ConvergenceChecker)null);
      this.initialStepBoundFactor = initialStepBoundFactor;
      this.costRelativeTolerance = costRelativeTolerance;
      this.parRelativeTolerance = parRelativeTolerance;
      this.orthoTolerance = orthoTolerance;
      this.qrRankingThreshold = threshold;
   }

   protected PointVectorValuePair doOptimize() {
      this.checkParameters();
      int nR = this.getTarget().length;
      double[] currentPoint = this.getStartPoint();
      int nC = currentPoint.length;
      this.solvedCols = FastMath.min(nR, nC);
      this.diagR = new double[nC];
      this.jacNorm = new double[nC];
      this.beta = new double[nC];
      this.permutation = new int[nC];
      this.lmDir = new double[nC];
      double delta = 0.0D;
      double xNorm = 0.0D;
      double[] diag = new double[nC];
      double[] oldX = new double[nC];
      double[] oldRes = new double[nR];
      double[] oldObj = new double[nR];
      double[] qtf = new double[nR];
      double[] work1 = new double[nC];
      double[] work2 = new double[nC];
      double[] work3 = new double[nC];
      RealMatrix weightMatrixSqrt = this.getWeightSquareRoot();
      double[] currentObjective = this.computeObjectiveValue(currentPoint);
      double[] currentResiduals = this.computeResiduals(currentObjective);
      PointVectorValuePair current = new PointVectorValuePair(currentPoint, currentObjective);
      double currentCost = this.computeCost(currentResiduals);
      this.lmPar = 0.0D;
      boolean firstIteration = true;
      ConvergenceChecker checker = this.getConvergenceChecker();

      while(true) {
         this.incrementIterationCount();
         PointVectorValuePair previous = current;
         this.qrDecomposition(this.computeWeightedJacobian(currentPoint));
         this.weightedResidual = weightMatrixSqrt.operate(currentResiduals);

         int k;
         for(k = 0; k < nR; ++k) {
            qtf[k] = this.weightedResidual[k];
         }

         this.qTy(qtf);

         for(k = 0; k < this.solvedCols; ++k) {
            int pk = this.permutation[k];
            this.weightedJacobian[k][pk] = this.diagR[pk];
         }

         if (firstIteration) {
            xNorm = 0.0D;

            for(k = 0; k < nC; ++k) {
               double dk = this.jacNorm[k];
               if (dk == 0.0D) {
                  dk = 1.0D;
               }

               double xk = dk * currentPoint[k];
               xNorm += xk * xk;
               diag[k] = dk;
            }

            xNorm = FastMath.sqrt(xNorm);
            delta = xNorm == 0.0D ? this.initialStepBoundFactor : this.initialStepBoundFactor * xNorm;
         }

         double maxCosine = 0.0D;
         int j;
         double previousCost;
         if (currentCost != 0.0D) {
            for(j = 0; j < this.solvedCols; ++j) {
               int pj = this.permutation[j];
               previousCost = this.jacNorm[pj];
               if (previousCost != 0.0D) {
                  double sum = 0.0D;

                  for(int i = 0; i <= j; ++i) {
                     sum += this.weightedJacobian[i][pj] * qtf[i];
                  }

                  maxCosine = FastMath.max(maxCosine, FastMath.abs(sum) / (previousCost * currentCost));
               }
            }
         }

         if (maxCosine <= this.orthoTolerance) {
            this.setCost(currentCost);
            return current;
         }

         for(j = 0; j < nC; ++j) {
            diag[j] = FastMath.max(diag[j], this.jacNorm[j]);
         }

         double ratio = 0.0D;

         while(ratio < 1.0E-4D) {
            for(int j = 0; j < this.solvedCols; ++j) {
               int pj = this.permutation[j];
               oldX[pj] = currentPoint[pj];
            }

            previousCost = currentCost;
            double[] tmpVec = this.weightedResidual;
            this.weightedResidual = oldRes;
            oldRes = tmpVec;
            oldObj = currentObjective;
            this.determineLMParameter(qtf, delta, diag, work1, work2, work3);
            double lmNorm = 0.0D;

            double coeff1;
            for(int j = 0; j < this.solvedCols; ++j) {
               int pj = this.permutation[j];
               this.lmDir[pj] = -this.lmDir[pj];
               currentPoint[pj] = oldX[pj] + this.lmDir[pj];
               coeff1 = diag[pj] * this.lmDir[pj];
               lmNorm += coeff1 * coeff1;
            }

            lmNorm = FastMath.sqrt(lmNorm);
            if (firstIteration) {
               delta = FastMath.min(delta, lmNorm);
            }

            currentObjective = this.computeObjectiveValue(currentPoint);
            currentResiduals = this.computeResiduals(currentObjective);
            current = new PointVectorValuePair(currentPoint, currentObjective);
            currentCost = this.computeCost(currentResiduals);
            double actRed = -1.0D;
            if (0.1D * currentCost < previousCost) {
               coeff1 = currentCost / previousCost;
               actRed = 1.0D - coeff1 * coeff1;
            }

            double pc2;
            for(int j = 0; j < this.solvedCols; ++j) {
               int pj = this.permutation[j];
               pc2 = this.lmDir[pj];
               work1[j] = 0.0D;

               for(int i = 0; i <= j; ++i) {
                  work1[i] += this.weightedJacobian[i][pj] * pc2;
               }
            }

            coeff1 = 0.0D;

            for(int j = 0; j < this.solvedCols; ++j) {
               coeff1 += work1[j] * work1[j];
            }

            pc2 = previousCost * previousCost;
            coeff1 /= pc2;
            double coeff2 = this.lmPar * lmNorm * lmNorm / pc2;
            double preRed = coeff1 + 2.0D * coeff2;
            double dirDer = -(coeff1 + coeff2);
            ratio = preRed == 0.0D ? 0.0D : actRed / preRed;
            if (ratio <= 0.25D) {
               double tmp = actRed < 0.0D ? 0.5D * dirDer / (dirDer + 0.5D * actRed) : 0.5D;
               if (0.1D * currentCost >= previousCost || tmp < 0.1D) {
                  tmp = 0.1D;
               }

               delta = tmp * FastMath.min(delta, 10.0D * lmNorm);
               this.lmPar /= tmp;
            } else if (this.lmPar == 0.0D || ratio >= 0.75D) {
               delta = 2.0D * lmNorm;
               this.lmPar *= 0.5D;
            }

            int j;
            if (ratio >= 1.0E-4D) {
               firstIteration = false;
               xNorm = 0.0D;

               for(j = 0; j < nC; ++j) {
                  double xK = diag[j] * currentPoint[j];
                  xNorm += xK * xK;
               }

               xNorm = FastMath.sqrt(xNorm);
               if (checker != null && checker.converged(this.getIterations(), previous, current)) {
                  this.setCost(currentCost);
                  return current;
               }
            } else {
               currentCost = previousCost;

               for(j = 0; j < this.solvedCols; ++j) {
                  int pj = this.permutation[j];
                  currentPoint[pj] = oldX[pj];
               }

               tmpVec = this.weightedResidual;
               this.weightedResidual = oldRes;
               oldRes = tmpVec;
               currentObjective = oldObj;
               current = new PointVectorValuePair(currentPoint, oldObj);
            }

            if (FastMath.abs(actRed) <= this.costRelativeTolerance && preRed <= this.costRelativeTolerance && ratio <= 2.0D || delta <= this.parRelativeTolerance * xNorm) {
               this.setCost(currentCost);
               return current;
            }

            if (FastMath.abs(actRed) <= TWO_EPS && preRed <= TWO_EPS && ratio <= 2.0D) {
               throw new ConvergenceException(LocalizedFormats.TOO_SMALL_COST_RELATIVE_TOLERANCE, new Object[]{this.costRelativeTolerance});
            }

            if (delta <= TWO_EPS * xNorm) {
               throw new ConvergenceException(LocalizedFormats.TOO_SMALL_PARAMETERS_RELATIVE_TOLERANCE, new Object[]{this.parRelativeTolerance});
            }

            if (maxCosine <= TWO_EPS) {
               throw new ConvergenceException(LocalizedFormats.TOO_SMALL_ORTHOGONALITY_TOLERANCE, new Object[]{this.orthoTolerance});
            }
         }
      }
   }

   private void determineLMParameter(double[] qy, double delta, double[] diag, double[] work1, double[] work2, double[] work3) {
      int nC = this.weightedJacobian[0].length;

      int k;
      for(k = 0; k < this.rank; ++k) {
         this.lmDir[this.permutation[k]] = qy[k];
      }

      for(k = this.rank; k < nC; ++k) {
         this.lmDir[this.permutation[k]] = 0.0D;
      }

      double fp;
      int var10001;
      for(k = this.rank - 1; k >= 0; --k) {
         int pk = this.permutation[k];
         fp = this.lmDir[pk] / this.diagR[pk];

         for(int i = 0; i < k; ++i) {
            double[] var10000 = this.lmDir;
            var10001 = this.permutation[i];
            var10000[var10001] -= fp * this.weightedJacobian[i][pk];
         }

         this.lmDir[pk] = fp;
      }

      double dxNorm = 0.0D;

      double sum2;
      for(int j = 0; j < this.solvedCols; ++j) {
         int pj = this.permutation[j];
         sum2 = diag[pj] * this.lmDir[pj];
         work1[pj] = sum2;
         dxNorm += sum2 * sum2;
      }

      dxNorm = FastMath.sqrt(dxNorm);
      fp = dxNorm - delta;
      if (fp <= 0.1D * delta) {
         this.lmPar = 0.0D;
      } else {
         double parl = 0.0D;
         int j;
         int pj;
         double paru;
         int countdown;
         if (this.rank == this.solvedCols) {
            for(j = 0; j < this.solvedCols; ++j) {
               pj = this.permutation[j];
               work1[pj] *= diag[pj] / dxNorm;
            }

            sum2 = 0.0D;

            for(j = 0; j < this.solvedCols; ++j) {
               pj = this.permutation[j];
               paru = 0.0D;

               for(countdown = 0; countdown < j; ++countdown) {
                  paru += this.weightedJacobian[countdown][pj] * work1[this.permutation[countdown]];
               }

               double s = (work1[pj] - paru) / this.diagR[pj];
               work1[pj] = s;
               sum2 += s * s;
            }

            parl = fp / (delta * sum2);
         }

         sum2 = 0.0D;

         for(j = 0; j < this.solvedCols; ++j) {
            pj = this.permutation[j];
            paru = 0.0D;

            for(countdown = 0; countdown <= j; ++countdown) {
               paru += this.weightedJacobian[countdown][pj] * qy[countdown];
            }

            paru /= diag[pj];
            sum2 += paru * paru;
         }

         double gNorm = FastMath.sqrt(sum2);
         paru = gNorm / delta;
         if (paru == 0.0D) {
            paru = Precision.SAFE_MIN / FastMath.min(delta, 0.1D);
         }

         this.lmPar = FastMath.min(paru, FastMath.max(this.lmPar, parl));
         if (this.lmPar == 0.0D) {
            this.lmPar = gNorm / dxNorm;
         }

         for(countdown = 10; countdown >= 0; --countdown) {
            if (this.lmPar == 0.0D) {
               this.lmPar = FastMath.max(Precision.SAFE_MIN, 0.001D * paru);
            }

            double sPar = FastMath.sqrt(this.lmPar);

            int j;
            int pj;
            for(j = 0; j < this.solvedCols; ++j) {
               pj = this.permutation[j];
               work1[pj] = sPar * diag[pj];
            }

            this.determineLMDirection(qy, work1, work2, work3);
            dxNorm = 0.0D;

            double correction;
            for(j = 0; j < this.solvedCols; ++j) {
               pj = this.permutation[j];
               correction = diag[pj] * this.lmDir[pj];
               work3[pj] = correction;
               dxNorm += correction * correction;
            }

            dxNorm = FastMath.sqrt(dxNorm);
            double previousFP = fp;
            fp = dxNorm - delta;
            if (FastMath.abs(fp) <= 0.1D * delta || parl == 0.0D && fp <= previousFP && previousFP < 0.0D) {
               return;
            }

            int pj;
            int j;
            for(j = 0; j < this.solvedCols; ++j) {
               pj = this.permutation[j];
               work1[pj] = work3[pj] * diag[pj] / dxNorm;
            }

            for(j = 0; j < this.solvedCols; ++j) {
               pj = this.permutation[j];
               work1[pj] /= work2[j];
               double tmp = work1[pj];

               for(int i = j + 1; i < this.solvedCols; ++i) {
                  var10001 = this.permutation[i];
                  work1[var10001] -= this.weightedJacobian[i][pj] * tmp;
               }
            }

            sum2 = 0.0D;

            for(j = 0; j < this.solvedCols; ++j) {
               double s = work1[this.permutation[j]];
               sum2 += s * s;
            }

            correction = fp / (delta * sum2);
            if (fp > 0.0D) {
               parl = FastMath.max(parl, this.lmPar);
            } else if (fp < 0.0D) {
               paru = FastMath.min(paru, this.lmPar);
            }

            this.lmPar = FastMath.max(parl, this.lmPar + correction);
         }

      }
   }

   private void determineLMDirection(double[] qy, double[] diag, double[] lmDiag, double[] work) {
      int nSing;
      int j;
      int pj;
      for(nSing = 0; nSing < this.solvedCols; ++nSing) {
         j = this.permutation[nSing];

         for(pj = nSing + 1; pj < this.solvedCols; ++pj) {
            this.weightedJacobian[pj][j] = this.weightedJacobian[nSing][this.permutation[pj]];
         }

         this.lmDir[nSing] = this.diagR[j];
         work[nSing] = qy[nSing];
      }

      for(nSing = 0; nSing < this.solvedCols; ++nSing) {
         j = this.permutation[nSing];
         double dpj = diag[j];
         if (dpj != 0.0D) {
            Arrays.fill(lmDiag, nSing + 1, lmDiag.length, 0.0D);
         }

         lmDiag[nSing] = dpj;
         double qtbpj = 0.0D;

         for(int k = nSing; k < this.solvedCols; ++k) {
            int pk = this.permutation[k];
            if (lmDiag[k] != 0.0D) {
               double rkk = this.weightedJacobian[k][pk];
               double sin;
               double cos;
               double temp;
               if (FastMath.abs(rkk) < FastMath.abs(lmDiag[k])) {
                  temp = rkk / lmDiag[k];
                  sin = 1.0D / FastMath.sqrt(1.0D + temp * temp);
                  cos = sin * temp;
               } else {
                  temp = lmDiag[k] / rkk;
                  cos = 1.0D / FastMath.sqrt(1.0D + temp * temp);
                  sin = cos * temp;
               }

               this.weightedJacobian[k][pk] = cos * rkk + sin * lmDiag[k];
               temp = cos * work[k] + sin * qtbpj;
               qtbpj = -sin * work[k] + cos * qtbpj;
               work[k] = temp;

               for(int i = k + 1; i < this.solvedCols; ++i) {
                  double rik = this.weightedJacobian[i][pk];
                  double temp2 = cos * rik + sin * lmDiag[i];
                  lmDiag[i] = -sin * rik + cos * lmDiag[i];
                  this.weightedJacobian[i][pk] = temp2;
               }
            }
         }

         lmDiag[nSing] = this.weightedJacobian[nSing][this.permutation[nSing]];
         this.weightedJacobian[nSing][this.permutation[nSing]] = this.lmDir[nSing];
      }

      nSing = this.solvedCols;

      for(j = 0; j < this.solvedCols; ++j) {
         if (lmDiag[j] == 0.0D && nSing == this.solvedCols) {
            nSing = j;
         }

         if (nSing < this.solvedCols) {
            work[j] = 0.0D;
         }
      }

      if (nSing > 0) {
         for(j = nSing - 1; j >= 0; --j) {
            pj = this.permutation[j];
            double sum = 0.0D;

            for(int i = j + 1; i < nSing; ++i) {
               sum += this.weightedJacobian[i][pj] * work[i];
            }

            work[j] = (work[j] - sum) / lmDiag[j];
         }
      }

      for(j = 0; j < this.lmDir.length; ++j) {
         this.lmDir[this.permutation[j]] = work[j];
      }

   }

   private void qrDecomposition(RealMatrix jacobian) throws ConvergenceException {
      this.weightedJacobian = jacobian.scalarMultiply(-1.0D).getData();
      int nR = this.weightedJacobian.length;
      int nC = this.weightedJacobian[0].length;

      int k;
      for(k = 0; k < nC; ++k) {
         this.permutation[k] = k;
         double norm2 = 0.0D;

         for(int i = 0; i < nR; ++i) {
            double akk = this.weightedJacobian[i][k];
            norm2 += akk * akk;
         }

         this.jacNorm[k] = FastMath.sqrt(norm2);
      }

      for(k = 0; k < nC; ++k) {
         int nextColumn = -1;
         double ak2 = Double.NEGATIVE_INFINITY;

         double norm2;
         int pk;
         for(pk = k; pk < nC; ++pk) {
            norm2 = 0.0D;

            for(int j = k; j < nR; ++j) {
               double aki = this.weightedJacobian[j][this.permutation[pk]];
               norm2 += aki * aki;
            }

            if (Double.isInfinite(norm2) || Double.isNaN(norm2)) {
               throw new ConvergenceException(LocalizedFormats.UNABLE_TO_PERFORM_QR_DECOMPOSITION_ON_JACOBIAN, new Object[]{nR, nC});
            }

            if (norm2 > ak2) {
               nextColumn = pk;
               ak2 = norm2;
            }
         }

         if (ak2 <= this.qrRankingThreshold) {
            this.rank = k;
            return;
         }

         pk = this.permutation[nextColumn];
         this.permutation[nextColumn] = this.permutation[k];
         this.permutation[k] = pk;
         norm2 = this.weightedJacobian[k][pk];
         double alpha = norm2 > 0.0D ? -FastMath.sqrt(ak2) : FastMath.sqrt(ak2);
         double betak = 1.0D / (ak2 - norm2 * alpha);
         this.beta[pk] = betak;
         this.diagR[pk] = alpha;
         double[] var10000 = this.weightedJacobian[k];
         var10000[pk] -= alpha;

         for(int dk = nC - 1 - k; dk > 0; --dk) {
            double gamma = 0.0D;

            int j;
            for(j = k; j < nR; ++j) {
               gamma += this.weightedJacobian[j][pk] * this.weightedJacobian[j][this.permutation[k + dk]];
            }

            gamma *= betak;

            for(j = k; j < nR; ++j) {
               var10000 = this.weightedJacobian[j];
               int var10001 = this.permutation[k + dk];
               var10000[var10001] -= gamma * this.weightedJacobian[j][pk];
            }
         }
      }

      this.rank = this.solvedCols;
   }

   private void qTy(double[] y) {
      int nR = this.weightedJacobian.length;
      int nC = this.weightedJacobian[0].length;

      for(int k = 0; k < nC; ++k) {
         int pk = this.permutation[k];
         double gamma = 0.0D;

         int i;
         for(i = k; i < nR; ++i) {
            gamma += this.weightedJacobian[i][pk] * y[i];
         }

         gamma *= this.beta[pk];

         for(i = k; i < nR; ++i) {
            y[i] -= gamma * this.weightedJacobian[i][pk];
         }
      }

   }

   private void checkParameters() {
      if (this.getLowerBound() != null || this.getUpperBound() != null) {
         throw new MathUnsupportedOperationException(LocalizedFormats.CONSTRAINT, new Object[0]);
      }
   }

   static {
      TWO_EPS = 2.0D * Precision.EPSILON;
   }
}
