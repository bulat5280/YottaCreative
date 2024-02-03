package org.apache.commons.math3.optim.nonlinear.scalar.noderiv;

import org.apache.commons.math3.exception.MathIllegalStateException;
import org.apache.commons.math3.exception.NumberIsTooSmallException;
import org.apache.commons.math3.exception.OutOfRangeException;
import org.apache.commons.math3.exception.util.LocalizedFormats;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;
import org.apache.commons.math3.optim.ConvergenceChecker;
import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;
import org.apache.commons.math3.optim.nonlinear.scalar.MultivariateOptimizer;

public class BOBYQAOptimizer extends MultivariateOptimizer {
   public static final int MINIMUM_PROBLEM_DIMENSION = 2;
   public static final double DEFAULT_INITIAL_RADIUS = 10.0D;
   public static final double DEFAULT_STOPPING_RADIUS = 1.0E-8D;
   private static final double ZERO = 0.0D;
   private static final double ONE = 1.0D;
   private static final double TWO = 2.0D;
   private static final double TEN = 10.0D;
   private static final double SIXTEEN = 16.0D;
   private static final double TWO_HUNDRED_FIFTY = 250.0D;
   private static final double MINUS_ONE = -1.0D;
   private static final double HALF = 0.5D;
   private static final double ONE_OVER_FOUR = 0.25D;
   private static final double ONE_OVER_EIGHT = 0.125D;
   private static final double ONE_OVER_TEN = 0.1D;
   private static final double ONE_OVER_A_THOUSAND = 0.001D;
   private final int numberOfInterpolationPoints;
   private double initialTrustRegionRadius;
   private final double stoppingTrustRegionRadius;
   private boolean isMinimize;
   private ArrayRealVector currentBest;
   private double[] boundDifference;
   private int trustRegionCenterInterpolationPointIndex;
   private Array2DRowRealMatrix bMatrix;
   private Array2DRowRealMatrix zMatrix;
   private Array2DRowRealMatrix interpolationPoints;
   private ArrayRealVector originShift;
   private ArrayRealVector fAtInterpolationPoints;
   private ArrayRealVector trustRegionCenterOffset;
   private ArrayRealVector gradientAtTrustRegionCenter;
   private ArrayRealVector lowerDifference;
   private ArrayRealVector upperDifference;
   private ArrayRealVector modelSecondDerivativesParameters;
   private ArrayRealVector newPoint;
   private ArrayRealVector alternativeNewPoint;
   private ArrayRealVector trialStepPoint;
   private ArrayRealVector lagrangeValuesAtNewPoint;
   private ArrayRealVector modelSecondDerivativesValues;

   public BOBYQAOptimizer(int numberOfInterpolationPoints) {
      this(numberOfInterpolationPoints, 10.0D, 1.0E-8D);
   }

   public BOBYQAOptimizer(int numberOfInterpolationPoints, double initialTrustRegionRadius, double stoppingTrustRegionRadius) {
      super((ConvergenceChecker)null);
      this.numberOfInterpolationPoints = numberOfInterpolationPoints;
      this.initialTrustRegionRadius = initialTrustRegionRadius;
      this.stoppingTrustRegionRadius = stoppingTrustRegionRadius;
   }

   protected PointValuePair doOptimize() {
      double[] lowerBound = this.getLowerBound();
      double[] upperBound = this.getUpperBound();
      this.setup(lowerBound, upperBound);
      this.isMinimize = this.getGoalType() == GoalType.MINIMIZE;
      this.currentBest = new ArrayRealVector(this.getStartPoint());
      double value = this.bobyqa(lowerBound, upperBound);
      return new PointValuePair(this.currentBest.getDataRef(), this.isMinimize ? value : -value);
   }

   private double bobyqa(double[] lowerBound, double[] upperBound) {
      printMethod();
      int n = this.currentBest.getDimension();

      for(int j = 0; j < n; ++j) {
         double boundDiff = this.boundDifference[j];
         this.lowerDifference.setEntry(j, lowerBound[j] - this.currentBest.getEntry(j));
         this.upperDifference.setEntry(j, upperBound[j] - this.currentBest.getEntry(j));
         double deltaOne;
         if (this.lowerDifference.getEntry(j) >= -this.initialTrustRegionRadius) {
            if (this.lowerDifference.getEntry(j) >= 0.0D) {
               this.currentBest.setEntry(j, lowerBound[j]);
               this.lowerDifference.setEntry(j, 0.0D);
               this.upperDifference.setEntry(j, boundDiff);
            } else {
               this.currentBest.setEntry(j, lowerBound[j] + this.initialTrustRegionRadius);
               this.lowerDifference.setEntry(j, -this.initialTrustRegionRadius);
               deltaOne = upperBound[j] - this.currentBest.getEntry(j);
               this.upperDifference.setEntry(j, Math.max(deltaOne, this.initialTrustRegionRadius));
            }
         } else if (this.upperDifference.getEntry(j) <= this.initialTrustRegionRadius) {
            if (this.upperDifference.getEntry(j) <= 0.0D) {
               this.currentBest.setEntry(j, upperBound[j]);
               this.lowerDifference.setEntry(j, -boundDiff);
               this.upperDifference.setEntry(j, 0.0D);
            } else {
               this.currentBest.setEntry(j, upperBound[j] - this.initialTrustRegionRadius);
               deltaOne = lowerBound[j] - this.currentBest.getEntry(j);
               double deltaTwo = -this.initialTrustRegionRadius;
               this.lowerDifference.setEntry(j, Math.min(deltaOne, deltaTwo));
               this.upperDifference.setEntry(j, this.initialTrustRegionRadius);
            }
         }
      }

      return this.bobyqb(lowerBound, upperBound);
   }

   private double bobyqb(double[] lowerBound, double[] upperBound) {
      printMethod();
      int n = this.currentBest.getDimension();
      int npt = this.numberOfInterpolationPoints;
      int np = n + 1;
      int nptm = npt - np;
      int nh = n * np / 2;
      ArrayRealVector work1 = new ArrayRealVector(n);
      ArrayRealVector work2 = new ArrayRealVector(npt);
      ArrayRealVector work3 = new ArrayRealVector(npt);
      double cauchy = Double.NaN;
      double alpha = Double.NaN;
      double dsq = Double.NaN;
      double crvmin = Double.NaN;
      this.trustRegionCenterInterpolationPointIndex = 0;
      this.prelim(lowerBound, upperBound);
      double xoptsq = 0.0D;

      for(int i = 0; i < n; ++i) {
         this.trustRegionCenterOffset.setEntry(i, this.interpolationPoints.getEntry(this.trustRegionCenterInterpolationPointIndex, i));
         double deltaOne = this.trustRegionCenterOffset.getEntry(i);
         xoptsq += deltaOne * deltaOne;
      }

      double fsave = this.fAtInterpolationPoints.getEntry(0);
      int kbase = false;
      int ntrits = 0;
      int itest = 0;
      int knew = 0;
      int nfsav = this.getEvaluations();
      double rho = this.initialTrustRegionRadius;
      double delta = rho;
      double diffa = 0.0D;
      double diffb = 0.0D;
      double diffc = 0.0D;
      double f = 0.0D;
      double beta = 0.0D;
      double adelt = 0.0D;
      double denom = 0.0D;
      double ratio = 0.0D;
      double dnorm = 0.0D;
      double scaden = 0.0D;
      double biglsq = 0.0D;
      double distsq = 0.0D;
      short state = 20;

      while(true) {
         double vquad;
         int ih;
         double diff;
         int k;
         int i;
         int j;
         int ip;
         double gisq;
         int i;
         double fopt;
         int j;
         double hDelta;
         double sum;
         int k;
         label887: {
            double sumb;
            int m;
            double sum;
            label872:
            while(true) {
               double d2;
               double delsq;
               double sum;
               int k;
               double d1;
               double den;
               int j;
               double d2;
               double d1;
               label870:
               while(true) {
                  label867:
                  while(true) {
                     int k;
                     double delsq;
                     label865:
                     while(true) {
                        double hdiag;
                        double gqsq;
                        label863:
                        while(true) {
                           int k;
                           switch(state) {
                           case 20:
                              printState(20);
                              if (this.trustRegionCenterInterpolationPointIndex != 0) {
                                 m = 0;

                                 for(k = 0; k < n; ++k) {
                                    for(int i = 0; i <= k; ++i) {
                                       if (i < k) {
                                          this.gradientAtTrustRegionCenter.setEntry(k, this.gradientAtTrustRegionCenter.getEntry(k) + this.modelSecondDerivativesValues.getEntry(m) * this.trustRegionCenterOffset.getEntry(i));
                                       }

                                       this.gradientAtTrustRegionCenter.setEntry(i, this.gradientAtTrustRegionCenter.getEntry(i) + this.modelSecondDerivativesValues.getEntry(m) * this.trustRegionCenterOffset.getEntry(k));
                                       ++m;
                                    }
                                 }

                                 if (this.getEvaluations() > npt) {
                                    for(k = 0; k < npt; ++k) {
                                       vquad = 0.0D;

                                       for(ih = 0; ih < n; ++ih) {
                                          vquad += this.interpolationPoints.getEntry(k, ih) * this.trustRegionCenterOffset.getEntry(ih);
                                       }

                                       vquad *= this.modelSecondDerivativesParameters.getEntry(k);

                                       for(ih = 0; ih < n; ++ih) {
                                          this.gradientAtTrustRegionCenter.setEntry(ih, this.gradientAtTrustRegionCenter.getEntry(ih) + vquad * this.interpolationPoints.getEntry(k, ih));
                                       }
                                    }
                                 }
                              }
                           case 60:
                              break label870;
                           case 90:
                              break label887;
                           case 210:
                              printState(210);
                              double[] alphaCauchy = this.altmov(knew, adelt);
                              alpha = alphaCauchy[0];
                              cauchy = alphaCauchy[1];

                              for(k = 0; k < n; ++k) {
                                 this.trialStepPoint.setEntry(k, this.newPoint.getEntry(k) - this.trustRegionCenterOffset.getEntry(k));
                              }
                           case 230:
                              printState(230);

                              for(m = 0; m < npt; ++m) {
                                 sum = 0.0D;
                                 sumb = 0.0D;
                                 diff = 0.0D;

                                 for(i = 0; i < n; ++i) {
                                    sum += this.interpolationPoints.getEntry(m, i) * this.trialStepPoint.getEntry(i);
                                    sumb += this.interpolationPoints.getEntry(m, i) * this.trustRegionCenterOffset.getEntry(i);
                                    diff += this.bMatrix.getEntry(m, i) * this.trialStepPoint.getEntry(i);
                                 }

                                 work3.setEntry(m, sum * (0.5D * sum + sumb));
                                 this.lagrangeValuesAtNewPoint.setEntry(m, diff);
                                 work2.setEntry(m, sum);
                              }

                              beta = 0.0D;

                              for(m = 0; m < nptm; ++m) {
                                 sum = 0.0D;

                                 for(k = 0; k < npt; ++k) {
                                    sum += this.zMatrix.getEntry(k, m) * work3.getEntry(k);
                                 }

                                 beta -= sum * sum;

                                 for(k = 0; k < npt; ++k) {
                                    this.lagrangeValuesAtNewPoint.setEntry(k, this.lagrangeValuesAtNewPoint.getEntry(k) + sum * this.zMatrix.getEntry(k, m));
                                 }
                              }

                              dsq = 0.0D;
                              fopt = 0.0D;
                              vquad = 0.0D;

                              for(ih = 0; ih < n; ++ih) {
                                 diff = this.trialStepPoint.getEntry(ih);
                                 dsq += diff * diff;
                                 hDelta = 0.0D;

                                 for(j = 0; j < npt; ++j) {
                                    hDelta += work3.getEntry(j) * this.bMatrix.getEntry(j, ih);
                                 }

                                 fopt += hDelta * this.trialStepPoint.getEntry(ih);
                                 j = npt + ih;

                                 for(ip = 0; ip < n; ++ip) {
                                    hDelta += this.bMatrix.getEntry(j, ip) * this.trialStepPoint.getEntry(ip);
                                 }

                                 this.lagrangeValuesAtNewPoint.setEntry(j, hDelta);
                                 fopt += hDelta * this.trialStepPoint.getEntry(ih);
                                 vquad += this.trialStepPoint.getEntry(ih) * this.trustRegionCenterOffset.getEntry(ih);
                              }

                              beta = vquad * vquad + dsq * (xoptsq + vquad + vquad + 0.5D * dsq) + beta - fopt;
                              this.lagrangeValuesAtNewPoint.setEntry(this.trustRegionCenterInterpolationPointIndex, this.lagrangeValuesAtNewPoint.getEntry(this.trustRegionCenterInterpolationPointIndex) + 1.0D);
                              if (ntrits == 0) {
                                 delsq = this.lagrangeValuesAtNewPoint.getEntry(knew);
                                 denom = delsq * delsq + alpha * beta;
                                 if (denom < cauchy && cauchy > 0.0D) {
                                    for(k = 0; k < n; ++k) {
                                       this.newPoint.setEntry(k, this.alternativeNewPoint.getEntry(k));
                                       this.trialStepPoint.setEntry(k, this.newPoint.getEntry(k) - this.trustRegionCenterOffset.getEntry(k));
                                    }

                                    cauchy = 0.0D;
                                    state = 230;
                                    break;
                                 }
                              } else {
                                 delsq = delta * delta;
                                 scaden = 0.0D;
                                 biglsq = 0.0D;
                                 knew = 0;

                                 for(k = 0; k < npt; ++k) {
                                    if (k != this.trustRegionCenterInterpolationPointIndex) {
                                       hDelta = 0.0D;

                                       for(j = 0; j < nptm; ++j) {
                                          sum = this.zMatrix.getEntry(k, j);
                                          hDelta += sum * sum;
                                       }

                                       gqsq = this.lagrangeValuesAtNewPoint.getEntry(k);
                                       gisq = beta * hDelta + gqsq * gqsq;
                                       distsq = 0.0D;

                                       for(i = 0; i < n; ++i) {
                                          sum = this.interpolationPoints.getEntry(k, i) - this.trustRegionCenterOffset.getEntry(i);
                                          distsq += sum * sum;
                                       }

                                       double d4 = distsq / delsq;
                                       hdiag = Math.max(1.0D, d4 * d4);
                                       if (hdiag * gisq > scaden) {
                                          scaden = hdiag * gisq;
                                          knew = k;
                                          denom = gisq;
                                       }

                                       d1 = this.lagrangeValuesAtNewPoint.getEntry(k);
                                       biglsq = Math.max(biglsq, hdiag * d1 * d1);
                                    }
                                 }
                              }
                           case 360:
                              break label863;
                           case 650:
                              break label865;
                           case 680:
                              break label867;
                           case 720:
                              break label872;
                           default:
                              throw new MathIllegalStateException(LocalizedFormats.SIMPLE_MESSAGE, new Object[]{"bobyqb"});
                           }
                        }

                        printState(360);

                        for(m = 0; m < n; ++m) {
                           sum = lowerBound[m];
                           sumb = this.originShift.getEntry(m) + this.newPoint.getEntry(m);
                           diff = Math.max(sum, sumb);
                           hDelta = upperBound[m];
                           this.currentBest.setEntry(m, Math.min(diff, hDelta));
                           if (this.newPoint.getEntry(m) == this.lowerDifference.getEntry(m)) {
                              this.currentBest.setEntry(m, lowerBound[m]);
                           }

                           if (this.newPoint.getEntry(m) == this.upperDifference.getEntry(m)) {
                              this.currentBest.setEntry(m, upperBound[m]);
                           }
                        }

                        f = this.computeObjectiveValue(this.currentBest.toArray());
                        if (!this.isMinimize) {
                           f = -f;
                        }

                        if (ntrits == -1) {
                           fsave = f;
                           state = 720;
                        } else {
                           fopt = this.fAtInterpolationPoints.getEntry(this.trustRegionCenterInterpolationPointIndex);
                           vquad = 0.0D;
                           ih = 0;

                           for(j = 0; j < n; ++j) {
                              vquad += this.trialStepPoint.getEntry(j) * this.gradientAtTrustRegionCenter.getEntry(j);

                              for(k = 0; k <= j; ++k) {
                                 hDelta = this.trialStepPoint.getEntry(k) * this.trialStepPoint.getEntry(j);
                                 if (k == j) {
                                    hDelta *= 0.5D;
                                 }

                                 vquad += this.modelSecondDerivativesValues.getEntry(ih) * hDelta;
                                 ++ih;
                              }
                           }

                           for(j = 0; j < npt; ++j) {
                              d1 = work2.getEntry(j);
                              d2 = d1 * d1;
                              vquad += 0.5D * this.modelSecondDerivativesParameters.getEntry(j) * d2;
                           }

                           diff = f - fopt - vquad;
                           diffc = diffb;
                           diffb = diffa;
                           diffa = Math.abs(diff);
                           if (dnorm > rho) {
                              nfsav = this.getEvaluations();
                           }

                           double d2;
                           int max;
                           if (ntrits > 0) {
                              if (vquad >= 0.0D) {
                                 throw new MathIllegalStateException(LocalizedFormats.TRUST_REGION_STEP_FAILED, new Object[]{vquad});
                              }

                              ratio = (f - fopt) / vquad;
                              hDelta = 0.5D * delta;
                              if (ratio <= 0.1D) {
                                 delta = Math.min(hDelta, dnorm);
                              } else if (ratio <= 0.7D) {
                                 delta = Math.max(hDelta, dnorm);
                              } else {
                                 delta = Math.max(hDelta, 2.0D * dnorm);
                              }

                              if (delta <= rho * 1.5D) {
                                 delta = rho;
                              }

                              if (f < fopt) {
                                 j = knew;
                                 delsq = delta * delta;
                                 scaden = 0.0D;
                                 biglsq = 0.0D;
                                 knew = 0;
                                 max = 0;

                                 while(true) {
                                    if (max >= npt) {
                                       if (scaden <= 0.5D * biglsq) {
                                          knew = j;
                                          denom = denom;
                                       }
                                       break;
                                    }

                                    hdiag = 0.0D;

                                    for(int m = 0; m < nptm; ++m) {
                                       d2 = this.zMatrix.getEntry(max, m);
                                       hdiag += d2 * d2;
                                    }

                                    d1 = this.lagrangeValuesAtNewPoint.getEntry(max);
                                    den = beta * hdiag + d1 * d1;
                                    distsq = 0.0D;

                                    for(j = 0; j < n; ++j) {
                                       d2 = this.interpolationPoints.getEntry(max, j) - this.newPoint.getEntry(j);
                                       distsq += d2 * d2;
                                    }

                                    double d3 = distsq / delsq;
                                    double temp = Math.max(1.0D, d3 * d3);
                                    if (temp * den > scaden) {
                                       scaden = temp * den;
                                       knew = max;
                                       denom = den;
                                    }

                                    double d4 = this.lagrangeValuesAtNewPoint.getEntry(max);
                                    double d5 = temp * d4 * d4;
                                    biglsq = Math.max(biglsq, d5);
                                    ++max;
                                 }
                              }
                           }

                           this.update(beta, denom, knew);
                           ih = 0;
                           hDelta = this.modelSecondDerivativesParameters.getEntry(knew);
                           this.modelSecondDerivativesParameters.setEntry(knew, 0.0D);

                           for(j = 0; j < n; ++j) {
                              sum = hDelta * this.interpolationPoints.getEntry(knew, j);

                              for(k = 0; k <= j; ++k) {
                                 this.modelSecondDerivativesValues.setEntry(ih, this.modelSecondDerivativesValues.getEntry(ih) + sum * this.interpolationPoints.getEntry(knew, k));
                                 ++ih;
                              }
                           }

                           for(j = 0; j < nptm; ++j) {
                              sum = diff * this.zMatrix.getEntry(knew, j);

                              for(k = 0; k < npt; ++k) {
                                 this.modelSecondDerivativesParameters.setEntry(k, this.modelSecondDerivativesParameters.getEntry(k) + sum * this.zMatrix.getEntry(k, j));
                              }
                           }

                           this.fAtInterpolationPoints.setEntry(knew, f);

                           for(j = 0; j < n; ++j) {
                              this.interpolationPoints.setEntry(knew, j, this.newPoint.getEntry(j));
                              work1.setEntry(j, this.bMatrix.getEntry(knew, j));
                           }

                           for(j = 0; j < npt; ++j) {
                              sum = 0.0D;

                              for(k = 0; k < nptm; ++k) {
                                 sum += this.zMatrix.getEntry(knew, k) * this.zMatrix.getEntry(j, k);
                              }

                              delsq = 0.0D;

                              for(max = 0; max < n; ++max) {
                                 delsq += this.interpolationPoints.getEntry(j, max) * this.trustRegionCenterOffset.getEntry(max);
                              }

                              sum = sum * delsq;

                              for(k = 0; k < n; ++k) {
                                 work1.setEntry(k, work1.getEntry(k) + sum * this.interpolationPoints.getEntry(j, k));
                              }
                           }

                           for(j = 0; j < n; ++j) {
                              this.gradientAtTrustRegionCenter.setEntry(j, this.gradientAtTrustRegionCenter.getEntry(j) + diff * work1.getEntry(j));
                           }

                           if (f < fopt) {
                              this.trustRegionCenterInterpolationPointIndex = knew;
                              xoptsq = 0.0D;
                              ih = 0;
                              j = 0;

                              label657:
                              while(true) {
                                 if (j >= n) {
                                    j = 0;

                                    while(true) {
                                       if (j >= npt) {
                                          break label657;
                                       }

                                       sum = 0.0D;

                                       for(k = 0; k < n; ++k) {
                                          sum += this.interpolationPoints.getEntry(j, k) * this.trialStepPoint.getEntry(k);
                                       }

                                       sum *= this.modelSecondDerivativesParameters.getEntry(j);

                                       for(k = 0; k < n; ++k) {
                                          this.gradientAtTrustRegionCenter.setEntry(k, this.gradientAtTrustRegionCenter.getEntry(k) + sum * this.interpolationPoints.getEntry(j, k));
                                       }

                                       ++j;
                                    }
                                 }

                                 this.trustRegionCenterOffset.setEntry(j, this.newPoint.getEntry(j));
                                 sum = this.trustRegionCenterOffset.getEntry(j);
                                 xoptsq += sum * sum;

                                 for(k = 0; k <= j; ++k) {
                                    if (k < j) {
                                       this.gradientAtTrustRegionCenter.setEntry(j, this.gradientAtTrustRegionCenter.getEntry(j) + this.modelSecondDerivativesValues.getEntry(ih) * this.trialStepPoint.getEntry(k));
                                    }

                                    this.gradientAtTrustRegionCenter.setEntry(k, this.gradientAtTrustRegionCenter.getEntry(k) + this.modelSecondDerivativesValues.getEntry(ih) * this.trialStepPoint.getEntry(j));
                                    ++ih;
                                 }

                                 ++j;
                              }
                           }

                           if (ntrits > 0) {
                              for(j = 0; j < npt; ++j) {
                                 this.lagrangeValuesAtNewPoint.setEntry(j, this.fAtInterpolationPoints.getEntry(j) - this.fAtInterpolationPoints.getEntry(this.trustRegionCenterInterpolationPointIndex));
                                 work3.setEntry(j, 0.0D);
                              }

                              j = 0;

                              label621:
                              while(true) {
                                 if (j >= nptm) {
                                    for(j = 0; j < npt; ++j) {
                                       sum = 0.0D;

                                       for(k = 0; k < n; ++k) {
                                          sum += this.interpolationPoints.getEntry(j, k) * this.trustRegionCenterOffset.getEntry(k);
                                       }

                                       work2.setEntry(j, work3.getEntry(j));
                                       work3.setEntry(j, sum * work3.getEntry(j));
                                    }

                                    gqsq = 0.0D;
                                    gisq = 0.0D;

                                    for(i = 0; i < n; ++i) {
                                       sum = 0.0D;

                                       for(k = 0; k < npt; ++k) {
                                          sum += this.bMatrix.getEntry(k, i) * this.lagrangeValuesAtNewPoint.getEntry(k) + this.interpolationPoints.getEntry(k, i) * work3.getEntry(k);
                                       }

                                       double d1;
                                       if (this.trustRegionCenterOffset.getEntry(i) == this.lowerDifference.getEntry(i)) {
                                          d1 = Math.min(0.0D, this.gradientAtTrustRegionCenter.getEntry(i));
                                          gqsq += d1 * d1;
                                          d2 = Math.min(0.0D, sum);
                                          gisq += d2 * d2;
                                       } else if (this.trustRegionCenterOffset.getEntry(i) == this.upperDifference.getEntry(i)) {
                                          d1 = Math.max(0.0D, this.gradientAtTrustRegionCenter.getEntry(i));
                                          gqsq += d1 * d1;
                                          d2 = Math.max(0.0D, sum);
                                          gisq += d2 * d2;
                                       } else {
                                          d1 = this.gradientAtTrustRegionCenter.getEntry(i);
                                          gqsq += d1 * d1;
                                          gisq += sum * sum;
                                       }

                                       this.lagrangeValuesAtNewPoint.setEntry(npt + i, sum);
                                    }

                                    ++itest;
                                    if (gqsq < 10.0D * gisq) {
                                       itest = 0;
                                    }

                                    if (itest < 3) {
                                       break;
                                    }

                                    i = 0;
                                    max = Math.max(npt, nh);

                                    while(true) {
                                       if (i >= max) {
                                          break label621;
                                       }

                                       if (i < n) {
                                          this.gradientAtTrustRegionCenter.setEntry(i, this.lagrangeValuesAtNewPoint.getEntry(npt + i));
                                       }

                                       if (i < npt) {
                                          this.modelSecondDerivativesParameters.setEntry(i, work2.getEntry(i));
                                       }

                                       if (i < nh) {
                                          this.modelSecondDerivativesValues.setEntry(i, 0.0D);
                                       }

                                       itest = 0;
                                       ++i;
                                    }
                                 }

                                 sum = 0.0D;

                                 for(k = 0; k < npt; ++k) {
                                    sum += this.zMatrix.getEntry(k, j) * this.lagrangeValuesAtNewPoint.getEntry(k);
                                 }

                                 for(k = 0; k < npt; ++k) {
                                    work3.setEntry(k, work3.getEntry(k) + sum * this.zMatrix.getEntry(k, j));
                                 }

                                 ++j;
                              }
                           }

                           if (ntrits == 0) {
                              state = 60;
                           } else {
                              if (!(f <= fopt + 0.1D * vquad)) {
                                 gqsq = 2.0D * delta;
                                 gisq = 10.0D * rho;
                                 distsq = Math.max(gqsq * gqsq, gisq * gisq);
                                 break;
                              }

                              state = 60;
                           }
                        }
                     }

                     printState(650);
                     knew = -1;

                     for(m = 0; m < npt; ++m) {
                        sum = 0.0D;

                        for(k = 0; k < n; ++k) {
                           delsq = this.interpolationPoints.getEntry(m, k) - this.trustRegionCenterOffset.getEntry(k);
                           sum += delsq * delsq;
                        }

                        if (sum > distsq) {
                           knew = m;
                           distsq = sum;
                        }
                     }

                     if (knew >= 0) {
                        fopt = Math.sqrt(distsq);
                        if (ntrits == -1) {
                           delta = Math.min(0.1D * delta, 0.5D * fopt);
                           if (delta <= rho * 1.5D) {
                              delta = rho;
                           }
                        }

                        ntrits = 0;
                        vquad = Math.min(0.1D * fopt, delta);
                        adelt = Math.max(vquad, rho);
                        dsq = adelt * adelt;
                        state = 90;
                     } else if (ntrits == -1) {
                        state = 680;
                     } else if (ratio > 0.0D) {
                        state = 60;
                     } else {
                        if (Math.max(delta, dnorm) > rho) {
                           state = 60;
                           continue;
                        }
                        break;
                     }
                  }

                  printState(680);
                  if (rho > this.stoppingTrustRegionRadius) {
                     delta = 0.5D * rho;
                     ratio = rho / this.stoppingTrustRegionRadius;
                     if (ratio <= 16.0D) {
                        rho = this.stoppingTrustRegionRadius;
                     } else if (ratio <= 250.0D) {
                        rho = Math.sqrt(ratio) * this.stoppingTrustRegionRadius;
                     } else {
                        rho *= 0.1D;
                     }

                     delta = Math.max(delta, rho);
                     ntrits = 0;
                     nfsav = this.getEvaluations();
                     state = 60;
                  } else {
                     if (ntrits == -1) {
                        state = 360;
                        continue;
                     }
                     break label872;
                  }
               }

               printState(60);
               ArrayRealVector gnew = new ArrayRealVector(n);
               ArrayRealVector xbdi = new ArrayRealVector(n);
               ArrayRealVector s = new ArrayRealVector(n);
               ArrayRealVector hs = new ArrayRealVector(n);
               ArrayRealVector hred = new ArrayRealVector(n);
               double[] dsqCrvmin = this.trsbox(delta, gnew, xbdi, s, hs, hred);
               dsq = dsqCrvmin[0];
               crvmin = dsqCrvmin[1];
               d2 = Math.sqrt(dsq);
               dnorm = Math.min(delta, d2);
               if (!(dnorm < 0.5D * rho)) {
                  ++ntrits;
                  break label887;
               }

               ntrits = -1;
               d1 = 10.0D * rho;
               distsq = d1 * d1;
               if (this.getEvaluations() <= nfsav + 2) {
                  state = 650;
               } else {
                  d1 = Math.max(diffa, diffb);
                  sum = Math.max(d1, diffc);
                  delsq = rho * 0.125D * rho;
                  if (crvmin > 0.0D && sum > delsq * crvmin) {
                     state = 650;
                  } else {
                     sum = sum / rho;

                     for(k = 0; k < n; ++k) {
                        d1 = sum;
                        if (this.newPoint.getEntry(k) == this.lowerDifference.getEntry(k)) {
                           d1 = work1.getEntry(k);
                        }

                        if (this.newPoint.getEntry(k) == this.upperDifference.getEntry(k)) {
                           d1 = -work1.getEntry(k);
                        }

                        if (d1 < sum) {
                           den = this.modelSecondDerivativesValues.getEntry((k + k * k) / 2);

                           for(j = 0; j < npt; ++j) {
                              d2 = this.interpolationPoints.getEntry(j, k);
                              den += this.modelSecondDerivativesParameters.getEntry(j) * d2 * d2;
                           }

                           d1 += 0.5D * den * rho;
                           if (d1 < sum) {
                              boolean var90 = true;
                              break;
                           }
                        }
                     }

                     state = 680;
                  }
               }
            }

            printState(720);
            if (this.fAtInterpolationPoints.getEntry(this.trustRegionCenterInterpolationPointIndex) <= fsave) {
               for(m = 0; m < n; ++m) {
                  sum = lowerBound[m];
                  sumb = this.originShift.getEntry(m) + this.trustRegionCenterOffset.getEntry(m);
                  diff = Math.max(sum, sumb);
                  hDelta = upperBound[m];
                  this.currentBest.setEntry(m, Math.min(diff, hDelta));
                  if (this.trustRegionCenterOffset.getEntry(m) == this.lowerDifference.getEntry(m)) {
                     this.currentBest.setEntry(m, lowerBound[m]);
                  }

                  if (this.trustRegionCenterOffset.getEntry(m) == this.upperDifference.getEntry(m)) {
                     this.currentBest.setEntry(m, upperBound[m]);
                  }
               }

               f = this.fAtInterpolationPoints.getEntry(this.trustRegionCenterInterpolationPointIndex);
            }

            return f;
         }

         printState(90);
         if (dsq <= xoptsq * 0.001D) {
            fopt = xoptsq * 0.25D;
            vquad = 0.0D;

            for(ih = 0; ih < npt; ++ih) {
               vquad += this.modelSecondDerivativesParameters.getEntry(ih);
               diff = -0.5D * xoptsq;

               for(i = 0; i < n; ++i) {
                  diff += this.interpolationPoints.getEntry(ih, i) * this.trustRegionCenterOffset.getEntry(i);
               }

               work2.setEntry(ih, diff);
               hDelta = fopt - 0.5D * diff;

               for(j = 0; j < n; ++j) {
                  work1.setEntry(j, this.bMatrix.getEntry(ih, j));
                  this.lagrangeValuesAtNewPoint.setEntry(j, diff * this.interpolationPoints.getEntry(ih, j) + hDelta * this.trustRegionCenterOffset.getEntry(j));
                  ip = npt + j;

                  for(int j = 0; j <= j; ++j) {
                     this.bMatrix.setEntry(ip, j, this.bMatrix.getEntry(ip, j) + work1.getEntry(j) * this.lagrangeValuesAtNewPoint.getEntry(j) + this.lagrangeValuesAtNewPoint.getEntry(j) * work1.getEntry(j));
                  }
               }
            }

            for(ih = 0; ih < nptm; ++ih) {
               diff = 0.0D;
               hDelta = 0.0D;

               for(j = 0; j < npt; ++j) {
                  diff += this.zMatrix.getEntry(j, ih);
                  this.lagrangeValuesAtNewPoint.setEntry(j, work2.getEntry(j) * this.zMatrix.getEntry(j, ih));
                  hDelta += this.lagrangeValuesAtNewPoint.getEntry(j);
               }

               for(j = 0; j < n; ++j) {
                  sum = (fopt * diff - 0.5D * hDelta) * this.trustRegionCenterOffset.getEntry(j);

                  for(k = 0; k < npt; ++k) {
                     sum += this.lagrangeValuesAtNewPoint.getEntry(k) * this.interpolationPoints.getEntry(k, j);
                  }

                  work1.setEntry(j, sum);

                  for(k = 0; k < npt; ++k) {
                     this.bMatrix.setEntry(k, j, this.bMatrix.getEntry(k, j) + sum * this.zMatrix.getEntry(k, ih));
                  }
               }

               for(j = 0; j < n; ++j) {
                  ip = j + npt;
                  gisq = work1.getEntry(j);

                  for(i = 0; i <= j; ++i) {
                     this.bMatrix.setEntry(ip, i, this.bMatrix.getEntry(ip, i) + gisq * work1.getEntry(i));
                  }
               }
            }

            ih = 0;

            for(j = 0; j < n; ++j) {
               work1.setEntry(j, -0.5D * vquad * this.trustRegionCenterOffset.getEntry(j));

               for(k = 0; k < npt; ++k) {
                  work1.setEntry(j, work1.getEntry(j) + this.modelSecondDerivativesParameters.getEntry(k) * this.interpolationPoints.getEntry(k, j));
                  this.interpolationPoints.setEntry(k, j, this.interpolationPoints.getEntry(k, j) - this.trustRegionCenterOffset.getEntry(j));
               }

               for(k = 0; k <= j; ++k) {
                  this.modelSecondDerivativesValues.setEntry(ih, this.modelSecondDerivativesValues.getEntry(ih) + work1.getEntry(k) * this.trustRegionCenterOffset.getEntry(j) + this.trustRegionCenterOffset.getEntry(k) * work1.getEntry(j));
                  this.bMatrix.setEntry(npt + k, j, this.bMatrix.getEntry(npt + j, k));
                  ++ih;
               }
            }

            for(j = 0; j < n; ++j) {
               this.originShift.setEntry(j, this.originShift.getEntry(j) + this.trustRegionCenterOffset.getEntry(j));
               this.newPoint.setEntry(j, this.newPoint.getEntry(j) - this.trustRegionCenterOffset.getEntry(j));
               this.lowerDifference.setEntry(j, this.lowerDifference.getEntry(j) - this.trustRegionCenterOffset.getEntry(j));
               this.upperDifference.setEntry(j, this.upperDifference.getEntry(j) - this.trustRegionCenterOffset.getEntry(j));
               this.trustRegionCenterOffset.setEntry(j, 0.0D);
            }

            xoptsq = 0.0D;
         }

         if (ntrits == 0) {
            state = 210;
         } else {
            state = 230;
         }
      }
   }

   private double[] altmov(int knew, double adelt) {
      printMethod();
      int n = this.currentBest.getDimension();
      int npt = this.numberOfInterpolationPoints;
      ArrayRealVector glag = new ArrayRealVector(n);
      ArrayRealVector hcol = new ArrayRealVector(npt);
      ArrayRealVector work1 = new ArrayRealVector(n);
      ArrayRealVector work2 = new ArrayRealVector(n);

      int j;
      for(j = 0; j < npt; ++j) {
         hcol.setEntry(j, 0.0D);
      }

      j = 0;

      double ha;
      int k;
      for(int max = npt - n - 1; j < max; ++j) {
         ha = this.zMatrix.getEntry(knew, j);

         for(k = 0; k < npt; ++k) {
            hcol.setEntry(k, hcol.getEntry(k) + ha * this.zMatrix.getEntry(k, j));
         }
      }

      double alpha = hcol.getEntry(knew);
      ha = 0.5D * alpha;

      for(k = 0; k < n; ++k) {
         glag.setEntry(k, this.bMatrix.getEntry(knew, k));
      }

      for(k = 0; k < npt; ++k) {
         double tmp = 0.0D;

         int i;
         for(i = 0; i < n; ++i) {
            tmp += this.interpolationPoints.getEntry(k, i) * this.trustRegionCenterOffset.getEntry(i);
         }

         tmp *= hcol.getEntry(k);

         for(i = 0; i < n; ++i) {
            glag.setEntry(i, glag.getEntry(i) + tmp * this.interpolationPoints.getEntry(k, i));
         }
      }

      double presav = 0.0D;
      double step = Double.NaN;
      int ksav = 0;
      int ibdsav = 0;
      double stpsav = 0.0D;

      int k;
      double dderiv;
      double cauchy;
      double slbd;
      double sumin;
      int i;
      double tmp2;
      double d1;
      double tmp;
      double tmp;
      double csave;
      for(k = 0; k < npt; ++k) {
         if (k != this.trustRegionCenterInterpolationPointIndex) {
            dderiv = 0.0D;
            cauchy = 0.0D;

            for(int i = 0; i < n; ++i) {
               double tmp = this.interpolationPoints.getEntry(k, i) - this.trustRegionCenterOffset.getEntry(i);
               dderiv += glag.getEntry(i) * tmp;
               cauchy += tmp * tmp;
            }

            csave = adelt / Math.sqrt(cauchy);
            slbd = -csave;
            int ilbd = 0;
            int iubd = 0;
            sumin = Math.min(1.0D, csave);

            for(i = 0; i < n; ++i) {
               tmp2 = this.interpolationPoints.getEntry(k, i) - this.trustRegionCenterOffset.getEntry(i);
               if (tmp2 > 0.0D) {
                  if (slbd * tmp2 < this.lowerDifference.getEntry(i) - this.trustRegionCenterOffset.getEntry(i)) {
                     slbd = (this.lowerDifference.getEntry(i) - this.trustRegionCenterOffset.getEntry(i)) / tmp2;
                     ilbd = -i - 1;
                  }

                  if (csave * tmp2 > this.upperDifference.getEntry(i) - this.trustRegionCenterOffset.getEntry(i)) {
                     csave = Math.max(sumin, (this.upperDifference.getEntry(i) - this.trustRegionCenterOffset.getEntry(i)) / tmp2);
                     iubd = i + 1;
                  }
               } else if (tmp2 < 0.0D) {
                  if (slbd * tmp2 > this.upperDifference.getEntry(i) - this.trustRegionCenterOffset.getEntry(i)) {
                     slbd = (this.upperDifference.getEntry(i) - this.trustRegionCenterOffset.getEntry(i)) / tmp2;
                     ilbd = i + 1;
                  }

                  if (csave * tmp2 < this.lowerDifference.getEntry(i) - this.trustRegionCenterOffset.getEntry(i)) {
                     csave = Math.max(sumin, (this.lowerDifference.getEntry(i) - this.trustRegionCenterOffset.getEntry(i)) / tmp2);
                     iubd = -i - 1;
                  }
               }
            }

            step = slbd;
            i = ilbd;
            tmp2 = Double.NaN;
            if (k == knew) {
               d1 = dderiv - 1.0D;
               tmp2 = slbd * (dderiv - slbd * d1);
               tmp = csave * (dderiv - csave * d1);
               if (Math.abs(tmp) > Math.abs(tmp2)) {
                  step = csave;
                  tmp2 = tmp;
                  i = iubd;
               }

               tmp = 0.5D * dderiv;
               double d3 = tmp - d1 * slbd;
               double d4 = tmp - d1 * csave;
               if (d3 * d4 < 0.0D) {
                  double d5 = tmp * tmp / d1;
                  if (Math.abs(d5) > Math.abs(tmp2)) {
                     step = tmp / d1;
                     tmp2 = d5;
                     i = 0;
                  }
               }
            } else {
               tmp2 = slbd * (1.0D - slbd);
               d1 = csave * (1.0D - csave);
               if (Math.abs(d1) > Math.abs(tmp2)) {
                  step = csave;
                  tmp2 = d1;
                  i = iubd;
               }

               if (csave > 0.5D && Math.abs(tmp2) < 0.25D) {
                  step = 0.5D;
                  tmp2 = 0.25D;
                  i = 0;
               }

               tmp2 *= dderiv;
            }

            d1 = step * (1.0D - step) * cauchy;
            tmp = tmp2 * tmp2 * (tmp2 * tmp2 + ha * d1 * d1);
            if (tmp > presav) {
               presav = tmp;
               ksav = k;
               stpsav = step;
               ibdsav = i;
            }
         }
      }

      for(k = 0; k < n; ++k) {
         dderiv = this.trustRegionCenterOffset.getEntry(k) + stpsav * (this.interpolationPoints.getEntry(ksav, k) - this.trustRegionCenterOffset.getEntry(k));
         this.newPoint.setEntry(k, Math.max(this.lowerDifference.getEntry(k), Math.min(this.upperDifference.getEntry(k), dderiv)));
      }

      if (ibdsav < 0) {
         this.newPoint.setEntry(-ibdsav - 1, this.lowerDifference.getEntry(-ibdsav - 1));
      }

      if (ibdsav > 0) {
         this.newPoint.setEntry(ibdsav - 1, this.upperDifference.getEntry(ibdsav - 1));
      }

      double bigstp = adelt + adelt;
      int iflag = false;
      cauchy = Double.NaN;
      csave = 0.0D;

      while(true) {
         slbd = 0.0D;
         double ggfree = 0.0D;

         for(int i = 0; i < n; ++i) {
            double glagValue = glag.getEntry(i);
            work1.setEntry(i, 0.0D);
            if (Math.min(this.trustRegionCenterOffset.getEntry(i) - this.lowerDifference.getEntry(i), glagValue) > 0.0D || Math.max(this.trustRegionCenterOffset.getEntry(i) - this.upperDifference.getEntry(i), glagValue) < 0.0D) {
               work1.setEntry(i, bigstp);
               ggfree += glagValue * glagValue;
            }
         }

         if (ggfree == 0.0D) {
            return new double[]{alpha, 0.0D};
         }

         sumin = adelt * adelt - slbd;
         if (sumin > 0.0D) {
            step = Math.sqrt(sumin / ggfree);
            ggfree = 0.0D;

            for(i = 0; i < n; ++i) {
               if (work1.getEntry(i) == bigstp) {
                  tmp2 = this.trustRegionCenterOffset.getEntry(i) - step * glag.getEntry(i);
                  if (tmp2 <= this.lowerDifference.getEntry(i)) {
                     work1.setEntry(i, this.lowerDifference.getEntry(i) - this.trustRegionCenterOffset.getEntry(i));
                     d1 = work1.getEntry(i);
                     slbd += d1 * d1;
                  } else if (tmp2 >= this.upperDifference.getEntry(i)) {
                     work1.setEntry(i, this.upperDifference.getEntry(i) - this.trustRegionCenterOffset.getEntry(i));
                     d1 = work1.getEntry(i);
                     slbd += d1 * d1;
                  } else {
                     d1 = glag.getEntry(i);
                     ggfree += d1 * d1;
                  }
               }
            }
         }

         double gw = 0.0D;

         for(int i = 0; i < n; ++i) {
            d1 = glag.getEntry(i);
            if (work1.getEntry(i) == bigstp) {
               work1.setEntry(i, -step * d1);
               tmp = Math.min(this.upperDifference.getEntry(i), this.trustRegionCenterOffset.getEntry(i) + work1.getEntry(i));
               this.alternativeNewPoint.setEntry(i, Math.max(this.lowerDifference.getEntry(i), tmp));
            } else if (work1.getEntry(i) == 0.0D) {
               this.alternativeNewPoint.setEntry(i, this.trustRegionCenterOffset.getEntry(i));
            } else if (d1 > 0.0D) {
               this.alternativeNewPoint.setEntry(i, this.lowerDifference.getEntry(i));
            } else {
               this.alternativeNewPoint.setEntry(i, this.upperDifference.getEntry(i));
            }

            gw += d1 * work1.getEntry(i);
         }

         double curv = 0.0D;

         int i;
         for(i = 0; i < npt; ++i) {
            tmp = 0.0D;

            for(int j = 0; j < n; ++j) {
               tmp += this.interpolationPoints.getEntry(i, j) * work1.getEntry(j);
            }

            curv += hcol.getEntry(i) * tmp * tmp;
         }

         if (iflag) {
            curv = -curv;
         }

         double scale;
         if (curv > -gw && curv < -gw * (1.0D + Math.sqrt(2.0D))) {
            scale = -gw / curv;

            for(int i = 0; i < n; ++i) {
               tmp = this.trustRegionCenterOffset.getEntry(i) + scale * work1.getEntry(i);
               this.alternativeNewPoint.setEntry(i, Math.max(this.lowerDifference.getEntry(i), Math.min(this.upperDifference.getEntry(i), tmp)));
            }

            double d1 = 0.5D * gw * scale;
            cauchy = d1 * d1;
         } else {
            scale = gw + 0.5D * curv;
            cauchy = scale * scale;
         }

         if (iflag) {
            if (csave > cauchy) {
               for(int i = 0; i < n; ++i) {
                  this.alternativeNewPoint.setEntry(i, work2.getEntry(i));
               }

               cauchy = csave;
            }

            return new double[]{alpha, cauchy};
         }

         for(i = 0; i < n; ++i) {
            glag.setEntry(i, -glag.getEntry(i));
            work2.setEntry(i, this.alternativeNewPoint.getEntry(i));
         }

         csave = cauchy;
         iflag = true;
      }
   }

   private void prelim(double[] lowerBound, double[] upperBound) {
      printMethod();
      int n = this.currentBest.getDimension();
      int npt = this.numberOfInterpolationPoints;
      int ndim = this.bMatrix.getRowDimension();
      double rhosq = this.initialTrustRegionRadius * this.initialTrustRegionRadius;
      double recip = 1.0D / rhosq;
      int np = n + 1;

      int ipt;
      int jpt;
      for(ipt = 0; ipt < n; ++ipt) {
         this.originShift.setEntry(ipt, this.currentBest.getEntry(ipt));

         for(jpt = 0; jpt < npt; ++jpt) {
            this.interpolationPoints.setEntry(jpt, ipt, 0.0D);
         }

         for(jpt = 0; jpt < ndim; ++jpt) {
            this.bMatrix.setEntry(jpt, ipt, 0.0D);
         }
      }

      ipt = 0;

      for(jpt = n * np / 2; ipt < jpt; ++ipt) {
         this.modelSecondDerivativesValues.setEntry(ipt, 0.0D);
      }

      for(ipt = 0; ipt < npt; ++ipt) {
         this.modelSecondDerivativesParameters.setEntry(ipt, 0.0D);
         jpt = 0;

         for(int max = npt - np; jpt < max; ++jpt) {
            this.zMatrix.setEntry(ipt, jpt, 0.0D);
         }
      }

      ipt = 0;
      jpt = 0;
      double fbeg = Double.NaN;

      do {
         int nfm = this.getEvaluations();
         int nfx = nfm - n;
         int nfmm = nfm - 1;
         int nfxm = nfx - 1;
         double stepa = 0.0D;
         double stepb = 0.0D;
         int j;
         if (nfm <= 2 * n) {
            if (nfm >= 1 && nfm <= n) {
               stepa = this.initialTrustRegionRadius;
               if (this.upperDifference.getEntry(nfmm) == 0.0D) {
                  stepa = -stepa;
               }

               this.interpolationPoints.setEntry(nfm, nfmm, stepa);
            } else if (nfm > n) {
               stepa = this.interpolationPoints.getEntry(nfx, nfxm);
               stepb = -this.initialTrustRegionRadius;
               if (this.lowerDifference.getEntry(nfxm) == 0.0D) {
                  stepb = Math.min(2.0D * this.initialTrustRegionRadius, this.upperDifference.getEntry(nfxm));
               }

               if (this.upperDifference.getEntry(nfxm) == 0.0D) {
                  stepb = Math.max(-2.0D * this.initialTrustRegionRadius, this.lowerDifference.getEntry(nfxm));
               }

               this.interpolationPoints.setEntry(nfm, nfxm, stepb);
            }
         } else {
            j = (nfm - np) / n;
            jpt = nfm - j * n - n;
            ipt = jpt + j;
            int iptMinus1;
            if (ipt > n) {
               iptMinus1 = jpt;
               jpt = ipt - n;
               ipt = iptMinus1;
            }

            iptMinus1 = ipt - 1;
            int jptMinus1 = jpt - 1;
            this.interpolationPoints.setEntry(nfm, iptMinus1, this.interpolationPoints.getEntry(ipt, iptMinus1));
            this.interpolationPoints.setEntry(nfm, jptMinus1, this.interpolationPoints.getEntry(jpt, jptMinus1));
         }

         for(j = 0; j < n; ++j) {
            this.currentBest.setEntry(j, Math.min(Math.max(lowerBound[j], this.originShift.getEntry(j) + this.interpolationPoints.getEntry(nfm, j)), upperBound[j]));
            if (this.interpolationPoints.getEntry(nfm, j) == this.lowerDifference.getEntry(j)) {
               this.currentBest.setEntry(j, lowerBound[j]);
            }

            if (this.interpolationPoints.getEntry(nfm, j) == this.upperDifference.getEntry(j)) {
               this.currentBest.setEntry(j, upperBound[j]);
            }
         }

         double objectiveValue = this.computeObjectiveValue(this.currentBest.toArray());
         double f = this.isMinimize ? objectiveValue : -objectiveValue;
         int numEval = this.getEvaluations();
         this.fAtInterpolationPoints.setEntry(nfm, f);
         if (numEval == 1) {
            fbeg = f;
            this.trustRegionCenterInterpolationPointIndex = 0;
         } else if (f < this.fAtInterpolationPoints.getEntry(this.trustRegionCenterInterpolationPointIndex)) {
            this.trustRegionCenterInterpolationPointIndex = nfm;
         }

         int ih;
         double tmp;
         if (numEval <= 2 * n + 1) {
            if (numEval >= 2 && numEval <= n + 1) {
               this.gradientAtTrustRegionCenter.setEntry(nfmm, (f - fbeg) / stepa);
               if (npt < numEval + n) {
                  double oneOverStepA = 1.0D / stepa;
                  this.bMatrix.setEntry(0, nfmm, -oneOverStepA);
                  this.bMatrix.setEntry(nfm, nfmm, oneOverStepA);
                  this.bMatrix.setEntry(npt + nfmm, nfmm, -0.5D * rhosq);
               }
            } else if (numEval >= n + 2) {
               ih = nfx * (nfx + 1) / 2 - 1;
               tmp = (f - fbeg) / stepb;
               double diff = stepb - stepa;
               this.modelSecondDerivativesValues.setEntry(ih, 2.0D * (tmp - this.gradientAtTrustRegionCenter.getEntry(nfxm)) / diff);
               this.gradientAtTrustRegionCenter.setEntry(nfxm, (this.gradientAtTrustRegionCenter.getEntry(nfxm) * stepb - tmp * stepa) / diff);
               if (stepa * stepb < 0.0D && f < this.fAtInterpolationPoints.getEntry(nfm - n)) {
                  this.fAtInterpolationPoints.setEntry(nfm, this.fAtInterpolationPoints.getEntry(nfm - n));
                  this.fAtInterpolationPoints.setEntry(nfm - n, f);
                  if (this.trustRegionCenterInterpolationPointIndex == nfm) {
                     this.trustRegionCenterInterpolationPointIndex = nfm - n;
                  }

                  this.interpolationPoints.setEntry(nfm - n, nfxm, stepb);
                  this.interpolationPoints.setEntry(nfm, nfxm, stepa);
               }

               this.bMatrix.setEntry(0, nfxm, -(stepa + stepb) / (stepa * stepb));
               this.bMatrix.setEntry(nfm, nfxm, -0.5D / this.interpolationPoints.getEntry(nfm - n, nfxm));
               this.bMatrix.setEntry(nfm - n, nfxm, -this.bMatrix.getEntry(0, nfxm) - this.bMatrix.getEntry(nfm, nfxm));
               this.zMatrix.setEntry(0, nfxm, Math.sqrt(2.0D) / (stepa * stepb));
               this.zMatrix.setEntry(nfm, nfxm, Math.sqrt(0.5D) / rhosq);
               this.zMatrix.setEntry(nfm - n, nfxm, -this.zMatrix.getEntry(0, nfxm) - this.zMatrix.getEntry(nfm, nfxm));
            }
         } else {
            this.zMatrix.setEntry(0, nfxm, recip);
            this.zMatrix.setEntry(nfm, nfxm, recip);
            this.zMatrix.setEntry(ipt, nfxm, -recip);
            this.zMatrix.setEntry(jpt, nfxm, -recip);
            ih = ipt * (ipt - 1) / 2 + jpt - 1;
            tmp = this.interpolationPoints.getEntry(nfm, ipt - 1) * this.interpolationPoints.getEntry(nfm, jpt - 1);
            this.modelSecondDerivativesValues.setEntry(ih, (fbeg - this.fAtInterpolationPoints.getEntry(ipt) - this.fAtInterpolationPoints.getEntry(jpt) + f) / tmp);
         }
      } while(this.getEvaluations() < npt);

   }

   private double[] trsbox(double delta, ArrayRealVector gnew, ArrayRealVector xbdi, ArrayRealVector s, ArrayRealVector hs, ArrayRealVector hred) {
      printMethod();
      int n = this.currentBest.getDimension();
      int npt = this.numberOfInterpolationPoints;
      double dsq = Double.NaN;
      double crvmin = Double.NaN;
      double beta = 0.0D;
      int iact = -1;
      int nact = false;
      double angt = 0.0D;
      double temp = 0.0D;
      double xsav = 0.0D;
      double xsum = 0.0D;
      double angbd = 0.0D;
      double dredg = 0.0D;
      double sredg = 0.0D;
      double resid = 0.0D;
      double delsq = 0.0D;
      double ggsav = 0.0D;
      double tempa = 0.0D;
      double tempb = 0.0D;
      double redmax = 0.0D;
      double dredsq = 0.0D;
      double redsav = 0.0D;
      double gredsq = 0.0D;
      double rednew = 0.0D;
      int itcsav = 0;
      double rdprev = 0.0D;
      double rdnext = 0.0D;
      double stplen = 0.0D;
      double stepsq = 0.0D;
      int itermax = 0;
      int iterc = 0;
      int nact = 0;

      for(int i = 0; i < n; ++i) {
         xbdi.setEntry(i, 0.0D);
         if (this.trustRegionCenterOffset.getEntry(i) <= this.lowerDifference.getEntry(i)) {
            if (this.gradientAtTrustRegionCenter.getEntry(i) >= 0.0D) {
               xbdi.setEntry(i, -1.0D);
            }
         } else if (this.trustRegionCenterOffset.getEntry(i) >= this.upperDifference.getEntry(i) && this.gradientAtTrustRegionCenter.getEntry(i) <= 0.0D) {
            xbdi.setEntry(i, 1.0D);
         }

         if (xbdi.getEntry(i) != 0.0D) {
            ++nact;
         }

         this.trialStepPoint.setEntry(i, 0.0D);
         gnew.setEntry(i, this.gradientAtTrustRegionCenter.getEntry(i));
      }

      delsq = delta * delta;
      double qred = 0.0D;
      crvmin = -1.0D;
      short state = 20;

      int i;
      double d1;
      double d2;
      label382:
      while(true) {
         label379:
         while(true) {
            label377:
            while(true) {
               while(true) {
                  while(true) {
                     double shs;
                     double sdec;
                     switch(state) {
                     case 20:
                        printState(20);
                        beta = 0.0D;
                     case 30:
                        break label377;
                     case 50:
                        printState(50);
                        resid = delsq;
                        double ds = 0.0D;
                        shs = 0.0D;

                        for(i = 0; i < n; ++i) {
                           if (xbdi.getEntry(i) == 0.0D) {
                              d1 = this.trialStepPoint.getEntry(i);
                              resid -= d1 * d1;
                              ds += s.getEntry(i) * this.trialStepPoint.getEntry(i);
                              shs += s.getEntry(i) * hs.getEntry(i);
                           }
                        }

                        if (resid <= 0.0D) {
                           state = 90;
                           break;
                        } else {
                           temp = Math.sqrt(stepsq * resid + ds * ds);
                           double blen;
                           if (ds < 0.0D) {
                              blen = (temp - ds) / stepsq;
                           } else {
                              blen = resid / (temp + ds);
                           }

                           stplen = blen;
                           if (shs > 0.0D) {
                              stplen = Math.min(blen, gredsq / shs);
                           }

                           iact = -1;

                           for(i = 0; i < n; ++i) {
                              if (s.getEntry(i) != 0.0D) {
                                 xsum = this.trustRegionCenterOffset.getEntry(i) + this.trialStepPoint.getEntry(i);
                                 if (s.getEntry(i) > 0.0D) {
                                    temp = (this.upperDifference.getEntry(i) - xsum) / s.getEntry(i);
                                 } else {
                                    temp = (this.lowerDifference.getEntry(i) - xsum) / s.getEntry(i);
                                 }

                                 if (temp < stplen) {
                                    stplen = temp;
                                    iact = i;
                                 }
                              }
                           }

                           sdec = 0.0D;
                           double d1;
                           if (stplen > 0.0D) {
                              ++iterc;
                              temp = shs / stepsq;
                              if (iact == -1 && temp > 0.0D) {
                                 crvmin = Math.min(crvmin, temp);
                                 if (crvmin == -1.0D) {
                                    crvmin = temp;
                                 }
                              }

                              ggsav = gredsq;
                              gredsq = 0.0D;

                              for(i = 0; i < n; ++i) {
                                 gnew.setEntry(i, gnew.getEntry(i) + stplen * hs.getEntry(i));
                                 if (xbdi.getEntry(i) == 0.0D) {
                                    d1 = gnew.getEntry(i);
                                    gredsq += d1 * d1;
                                 }

                                 this.trialStepPoint.setEntry(i, this.trialStepPoint.getEntry(i) + stplen * s.getEntry(i));
                              }

                              d1 = stplen * (ggsav - 0.5D * stplen * shs);
                              sdec = Math.max(d1, 0.0D);
                              qred += sdec;
                           }

                           if (iact >= 0) {
                              ++nact;
                              xbdi.setEntry(iact, 1.0D);
                              if (s.getEntry(iact) < 0.0D) {
                                 xbdi.setEntry(iact, -1.0D);
                              }

                              d1 = this.trialStepPoint.getEntry(iact);
                              delsq -= d1 * d1;
                              if (delsq <= 0.0D) {
                                 state = 190;
                              } else {
                                 state = 20;
                              }
                              break;
                           } else if (stplen < blen) {
                              if (iterc == itermax) {
                                 state = 190;
                              } else if (sdec <= qred * 0.01D) {
                                 state = 190;
                              } else {
                                 beta = gredsq / ggsav;
                                 state = 30;
                              }
                              break;
                           }
                        }
                     case 90:
                        printState(90);
                        crvmin = 0.0D;
                     case 100:
                        break label379;
                     case 120:
                        printState(120);
                        ++iterc;
                        temp = gredsq * dredsq - dredg * dredg;
                        if (temp <= qred * 1.0E-4D * qred) {
                           state = 190;
                        } else {
                           temp = Math.sqrt(temp);

                           for(i = 0; i < n; ++i) {
                              if (xbdi.getEntry(i) == 0.0D) {
                                 s.setEntry(i, (dredg * this.trialStepPoint.getEntry(i) - dredsq * gnew.getEntry(i)) / temp);
                              } else {
                                 s.setEntry(i, 0.0D);
                              }
                           }

                           sredg = -temp;
                           angbd = 1.0D;
                           iact = -1;

                           for(i = 0; i < n; ++i) {
                              if (xbdi.getEntry(i) == 0.0D) {
                                 tempa = this.trustRegionCenterOffset.getEntry(i) + this.trialStepPoint.getEntry(i) - this.lowerDifference.getEntry(i);
                                 tempb = this.upperDifference.getEntry(i) - this.trustRegionCenterOffset.getEntry(i) - this.trialStepPoint.getEntry(i);
                                 boolean var95;
                                 if (tempa <= 0.0D) {
                                    ++nact;
                                    xbdi.setEntry(i, -1.0D);
                                    var95 = true;
                                    break;
                                 }

                                 if (tempb <= 0.0D) {
                                    ++nact;
                                    xbdi.setEntry(i, 1.0D);
                                    var95 = true;
                                    break;
                                 }

                                 d1 = this.trialStepPoint.getEntry(i);
                                 d2 = s.getEntry(i);
                                 double ssq = d1 * d1 + d2 * d2;
                                 d1 = this.trustRegionCenterOffset.getEntry(i) - this.lowerDifference.getEntry(i);
                                 temp = ssq - d1 * d1;
                                 if (temp > 0.0D) {
                                    temp = Math.sqrt(temp) - s.getEntry(i);
                                    if (angbd * temp > tempa) {
                                       angbd = tempa / temp;
                                       iact = i;
                                       xsav = -1.0D;
                                    }
                                 }

                                 d1 = this.upperDifference.getEntry(i) - this.trustRegionCenterOffset.getEntry(i);
                                 temp = ssq - d1 * d1;
                                 if (temp > 0.0D) {
                                    temp = Math.sqrt(temp) + s.getEntry(i);
                                    if (angbd * temp > tempb) {
                                       angbd = tempb / temp;
                                       iact = i;
                                       xsav = 1.0D;
                                    }
                                 }
                              }
                           }

                           state = 210;
                        }
                        break;
                     case 150:
                        printState(150);
                        shs = 0.0D;
                        double dhs = 0.0D;
                        double dhd = 0.0D;

                        for(i = 0; i < n; ++i) {
                           if (xbdi.getEntry(i) == 0.0D) {
                              shs += s.getEntry(i) * hs.getEntry(i);
                              dhs += this.trialStepPoint.getEntry(i) * hs.getEntry(i);
                              dhd += this.trialStepPoint.getEntry(i) * hred.getEntry(i);
                           }
                        }

                        redmax = 0.0D;
                        int isav = -1;
                        redsav = 0.0D;
                        int iu = (int)(angbd * 17.0D + 3.1D);

                        double sth;
                        for(i = 0; i < iu; ++i) {
                           angt = angbd * (double)i / (double)iu;
                           sth = (angt + angt) / (1.0D + angt * angt);
                           temp = shs + angt * (angt * dhd - dhs - dhs);
                           rednew = sth * (angt * dredg - sredg - 0.5D * sth * temp);
                           if (rednew > redmax) {
                              redmax = rednew;
                              isav = i;
                              rdprev = redsav;
                           } else if (i == isav + 1) {
                              rdnext = rednew;
                           }

                           redsav = rednew;
                        }

                        if (isav < 0) {
                           state = 190;
                           break;
                        } else {
                           if (isav < iu) {
                              temp = (rdnext - rdprev) / (redmax + redmax - rdprev - rdnext);
                              angt = angbd * ((double)isav + 0.5D * temp) / (double)iu;
                           }

                           double cth = (1.0D - angt * angt) / (1.0D + angt * angt);
                           sth = (angt + angt) / (1.0D + angt * angt);
                           temp = shs + angt * (angt * dhd - dhs - dhs);
                           sdec = sth * (angt * dredg - sredg - 0.5D * sth * temp);
                           if (sdec <= 0.0D) {
                              state = 190;
                              break;
                           } else {
                              dredg = 0.0D;
                              gredsq = 0.0D;

                              for(i = 0; i < n; ++i) {
                                 gnew.setEntry(i, gnew.getEntry(i) + (cth - 1.0D) * hred.getEntry(i) + sth * hs.getEntry(i));
                                 if (xbdi.getEntry(i) == 0.0D) {
                                    this.trialStepPoint.setEntry(i, cth * this.trialStepPoint.getEntry(i) + sth * s.getEntry(i));
                                    dredg += this.trialStepPoint.getEntry(i) * gnew.getEntry(i);
                                    d1 = gnew.getEntry(i);
                                    gredsq += d1 * d1;
                                 }

                                 hred.setEntry(i, cth * hred.getEntry(i) + sth * hs.getEntry(i));
                              }

                              qred += sdec;
                              if (iact >= 0 && isav == iu) {
                                 ++nact;
                                 xbdi.setEntry(iact, xsav);
                                 state = 100;
                                 break;
                              } else if (sdec > qred * 0.01D) {
                                 state = 120;
                                 break;
                              }
                           }
                        }
                     case 190:
                        break label382;
                     case 210:
                        printState(210);
                        i = 0;

                        int i;
                        for(int j = 0; j < n; ++j) {
                           hs.setEntry(j, 0.0D);

                           for(i = 0; i <= j; ++i) {
                              if (i < j) {
                                 hs.setEntry(j, hs.getEntry(j) + this.modelSecondDerivativesValues.getEntry(i) * s.getEntry(i));
                              }

                              hs.setEntry(i, hs.getEntry(i) + this.modelSecondDerivativesValues.getEntry(i) * s.getEntry(j));
                              ++i;
                           }
                        }

                        RealVector tmp = this.interpolationPoints.operate(s).ebeMultiply(this.modelSecondDerivativesParameters);

                        for(i = 0; i < npt; ++i) {
                           if (this.modelSecondDerivativesParameters.getEntry(i) != 0.0D) {
                              for(int i = 0; i < n; ++i) {
                                 hs.setEntry(i, hs.getEntry(i) + tmp.getEntry(i) * this.interpolationPoints.getEntry(i, i));
                              }
                           }
                        }

                        if (crvmin != 0.0D) {
                           state = 50;
                        } else if (iterc > itcsav) {
                           state = 150;
                        } else {
                           for(i = 0; i < n; ++i) {
                              hred.setEntry(i, hs.getEntry(i));
                           }

                           state = 120;
                        }
                        break;
                     default:
                        throw new MathIllegalStateException(LocalizedFormats.SIMPLE_MESSAGE, new Object[]{"trsbox"});
                     }
                  }
               }
            }

            printState(30);
            stepsq = 0.0D;

            for(i = 0; i < n; ++i) {
               if (xbdi.getEntry(i) != 0.0D) {
                  s.setEntry(i, 0.0D);
               } else if (beta == 0.0D) {
                  s.setEntry(i, -gnew.getEntry(i));
               } else {
                  s.setEntry(i, beta * s.getEntry(i) - gnew.getEntry(i));
               }

               d1 = s.getEntry(i);
               stepsq += d1 * d1;
            }

            if (stepsq == 0.0D) {
               state = 190;
            } else {
               if (beta == 0.0D) {
                  gredsq = stepsq;
                  itermax = iterc + n - nact;
               }

               if (gredsq * delsq <= qred * 1.0E-4D * qred) {
                  state = 190;
               } else {
                  state = 210;
               }
            }
         }

         printState(100);
         if (nact >= n - 1) {
            state = 190;
         } else {
            dredsq = 0.0D;
            dredg = 0.0D;
            gredsq = 0.0D;

            for(i = 0; i < n; ++i) {
               if (xbdi.getEntry(i) == 0.0D) {
                  d1 = this.trialStepPoint.getEntry(i);
                  dredsq += d1 * d1;
                  dredg += this.trialStepPoint.getEntry(i) * gnew.getEntry(i);
                  d1 = gnew.getEntry(i);
                  gredsq += d1 * d1;
                  s.setEntry(i, this.trialStepPoint.getEntry(i));
               } else {
                  s.setEntry(i, 0.0D);
               }
            }

            itcsav = iterc;
            state = 210;
         }
      }

      printState(190);
      dsq = 0.0D;

      for(i = 0; i < n; ++i) {
         d1 = Math.min(this.trustRegionCenterOffset.getEntry(i) + this.trialStepPoint.getEntry(i), this.upperDifference.getEntry(i));
         this.newPoint.setEntry(i, Math.max(d1, this.lowerDifference.getEntry(i)));
         if (xbdi.getEntry(i) == -1.0D) {
            this.newPoint.setEntry(i, this.lowerDifference.getEntry(i));
         }

         if (xbdi.getEntry(i) == 1.0D) {
            this.newPoint.setEntry(i, this.upperDifference.getEntry(i));
         }

         this.trialStepPoint.setEntry(i, this.newPoint.getEntry(i) - this.trustRegionCenterOffset.getEntry(i));
         d2 = this.trialStepPoint.getEntry(i);
         dsq += d2 * d2;
      }

      return new double[]{dsq, crvmin};
   }

   private void update(double beta, double denom, int knew) {
      printMethod();
      int n = this.currentBest.getDimension();
      int npt = this.numberOfInterpolationPoints;
      int nptm = npt - n - 1;
      ArrayRealVector work = new ArrayRealVector(npt + n);
      double ztest = 0.0D;

      int j;
      for(j = 0; j < npt; ++j) {
         for(int j = 0; j < nptm; ++j) {
            ztest = Math.max(ztest, Math.abs(this.zMatrix.getEntry(j, j)));
         }
      }

      ztest *= 1.0E-20D;

      double d4;
      for(j = 1; j < nptm; ++j) {
         double d1 = this.zMatrix.getEntry(knew, j);
         if (Math.abs(d1) > ztest) {
            double d2 = this.zMatrix.getEntry(knew, 0);
            double d3 = this.zMatrix.getEntry(knew, j);
            double d4 = Math.sqrt(d2 * d2 + d3 * d3);
            double d5 = this.zMatrix.getEntry(knew, 0) / d4;
            double d6 = this.zMatrix.getEntry(knew, j) / d4;

            for(int i = 0; i < npt; ++i) {
               d4 = d5 * this.zMatrix.getEntry(i, 0) + d6 * this.zMatrix.getEntry(i, j);
               this.zMatrix.setEntry(i, j, d5 * this.zMatrix.getEntry(i, j) - d6 * this.zMatrix.getEntry(i, 0));
               this.zMatrix.setEntry(i, 0, d4);
            }
         }

         this.zMatrix.setEntry(knew, j, 0.0D);
      }

      for(j = 0; j < npt; ++j) {
         work.setEntry(j, this.zMatrix.getEntry(knew, 0) * this.zMatrix.getEntry(j, 0));
      }

      double alpha = work.getEntry(knew);
      double tau = this.lagrangeValuesAtNewPoint.getEntry(knew);
      this.lagrangeValuesAtNewPoint.setEntry(knew, this.lagrangeValuesAtNewPoint.getEntry(knew) - 1.0D);
      double sqrtDenom = Math.sqrt(denom);
      double d1 = tau / sqrtDenom;
      double d2 = this.zMatrix.getEntry(knew, 0) / sqrtDenom;

      int j;
      for(j = 0; j < npt; ++j) {
         this.zMatrix.setEntry(j, 0, d1 * this.zMatrix.getEntry(j, 0) - d2 * this.lagrangeValuesAtNewPoint.getEntry(j));
      }

      for(j = 0; j < n; ++j) {
         int jp = npt + j;
         work.setEntry(jp, this.bMatrix.getEntry(knew, j));
         double d3 = (alpha * this.lagrangeValuesAtNewPoint.getEntry(jp) - tau * work.getEntry(jp)) / denom;
         d4 = (-beta * work.getEntry(jp) - tau * this.lagrangeValuesAtNewPoint.getEntry(jp)) / denom;

         for(int i = 0; i <= jp; ++i) {
            this.bMatrix.setEntry(i, j, this.bMatrix.getEntry(i, j) + d3 * this.lagrangeValuesAtNewPoint.getEntry(i) + d4 * work.getEntry(i));
            if (i >= npt) {
               this.bMatrix.setEntry(jp, i - npt, this.bMatrix.getEntry(i, j));
            }
         }
      }

   }

   private void setup(double[] lowerBound, double[] upperBound) {
      printMethod();
      double[] init = this.getStartPoint();
      int dimension = init.length;
      if (dimension < 2) {
         throw new NumberIsTooSmallException(dimension, 2, true);
      } else {
         int[] nPointsInterval = new int[]{dimension + 2, (dimension + 2) * (dimension + 1) / 2};
         if (this.numberOfInterpolationPoints >= nPointsInterval[0] && this.numberOfInterpolationPoints <= nPointsInterval[1]) {
            this.boundDifference = new double[dimension];
            double requiredMinDiff = 2.0D * this.initialTrustRegionRadius;
            double minDiff = Double.POSITIVE_INFINITY;

            for(int i = 0; i < dimension; ++i) {
               this.boundDifference[i] = upperBound[i] - lowerBound[i];
               minDiff = Math.min(minDiff, this.boundDifference[i]);
            }

            if (minDiff < requiredMinDiff) {
               this.initialTrustRegionRadius = minDiff / 3.0D;
            }

            this.bMatrix = new Array2DRowRealMatrix(dimension + this.numberOfInterpolationPoints, dimension);
            this.zMatrix = new Array2DRowRealMatrix(this.numberOfInterpolationPoints, this.numberOfInterpolationPoints - dimension - 1);
            this.interpolationPoints = new Array2DRowRealMatrix(this.numberOfInterpolationPoints, dimension);
            this.originShift = new ArrayRealVector(dimension);
            this.fAtInterpolationPoints = new ArrayRealVector(this.numberOfInterpolationPoints);
            this.trustRegionCenterOffset = new ArrayRealVector(dimension);
            this.gradientAtTrustRegionCenter = new ArrayRealVector(dimension);
            this.lowerDifference = new ArrayRealVector(dimension);
            this.upperDifference = new ArrayRealVector(dimension);
            this.modelSecondDerivativesParameters = new ArrayRealVector(this.numberOfInterpolationPoints);
            this.newPoint = new ArrayRealVector(dimension);
            this.alternativeNewPoint = new ArrayRealVector(dimension);
            this.trialStepPoint = new ArrayRealVector(dimension);
            this.lagrangeValuesAtNewPoint = new ArrayRealVector(dimension + this.numberOfInterpolationPoints);
            this.modelSecondDerivativesValues = new ArrayRealVector(dimension * (dimension + 1) / 2);
         } else {
            throw new OutOfRangeException(LocalizedFormats.NUMBER_OF_INTERPOLATION_POINTS, this.numberOfInterpolationPoints, nPointsInterval[0], nPointsInterval[1]);
         }
      }
   }

   private static String caller(int n) {
      Throwable t = new Throwable();
      StackTraceElement[] elements = t.getStackTrace();
      StackTraceElement e = elements[n];
      return e.getMethodName() + " (at line " + e.getLineNumber() + ")";
   }

   private static void printState(int s) {
   }

   private static void printMethod() {
   }

   private static class PathIsExploredException extends RuntimeException {
      private static final long serialVersionUID = 745350979634801853L;
      private static final String PATH_IS_EXPLORED = "If this exception is thrown, just remove it from the code";

      PathIsExploredException() {
         super("If this exception is thrown, just remove it from the code " + BOBYQAOptimizer.caller(3));
      }
   }
}
