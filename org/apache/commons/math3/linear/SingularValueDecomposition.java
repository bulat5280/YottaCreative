package org.apache.commons.math3.linear;

import org.apache.commons.math3.exception.NumberIsTooLargeException;
import org.apache.commons.math3.exception.util.LocalizedFormats;
import org.apache.commons.math3.util.FastMath;
import org.apache.commons.math3.util.Precision;

public class SingularValueDecomposition {
   private static final double EPS = 2.220446049250313E-16D;
   private static final double TINY = 1.6033346880071782E-291D;
   private final double[] singularValues;
   private final int m;
   private final int n;
   private final boolean transposed;
   private final RealMatrix cachedU;
   private RealMatrix cachedUt;
   private RealMatrix cachedS;
   private final RealMatrix cachedV;
   private RealMatrix cachedVt;
   private final double tol;

   public SingularValueDecomposition(RealMatrix matrix) {
      double[][] A;
      if (matrix.getRowDimension() < matrix.getColumnDimension()) {
         this.transposed = true;
         A = matrix.transpose().getData();
         this.m = matrix.getColumnDimension();
         this.n = matrix.getRowDimension();
      } else {
         this.transposed = false;
         A = matrix.getData();
         this.m = matrix.getRowDimension();
         this.n = matrix.getColumnDimension();
      }

      this.singularValues = new double[this.n];
      double[][] U = new double[this.m][this.n];
      double[][] V = new double[this.n][this.n];
      double[] e = new double[this.n];
      double[] work = new double[this.m];
      int nct = FastMath.min(this.m - 1, this.n);
      int nrt = FastMath.max(0, this.n - 2);

      int p;
      int pp;
      int iter;
      for(p = 0; p < FastMath.max(nct, nrt); ++p) {
         if (p < nct) {
            this.singularValues[p] = 0.0D;

            for(pp = p; pp < this.m; ++pp) {
               this.singularValues[p] = FastMath.hypot(this.singularValues[p], A[pp][p]);
            }

            if (this.singularValues[p] != 0.0D) {
               if (A[p][p] < 0.0D) {
                  this.singularValues[p] = -this.singularValues[p];
               }

               for(pp = p; pp < this.m; ++pp) {
                  A[pp][p] /= this.singularValues[p];
               }

               int var10002 = A[p][p]++;
            }

            this.singularValues[p] = -this.singularValues[p];
         }

         double t;
         int i;
         for(pp = p + 1; pp < this.n; ++pp) {
            if (p < nct && this.singularValues[p] != 0.0D) {
               t = 0.0D;

               for(i = p; i < this.m; ++i) {
                  t += A[i][p] * A[i][pp];
               }

               t = -t / A[p][p];

               for(i = p; i < this.m; ++i) {
                  A[i][pp] += t * A[i][p];
               }
            }

            e[pp] = A[p][pp];
         }

         if (p < nct) {
            for(pp = p; pp < this.m; ++pp) {
               U[pp][p] = A[pp][p];
            }
         }

         if (p < nrt) {
            e[p] = 0.0D;

            for(pp = p + 1; pp < this.n; ++pp) {
               e[p] = FastMath.hypot(e[p], e[pp]);
            }

            if (e[p] != 0.0D) {
               if (e[p + 1] < 0.0D) {
                  e[p] = -e[p];
               }

               for(pp = p + 1; pp < this.n; ++pp) {
                  e[pp] /= e[p];
               }

               ++e[p + 1];
            }

            e[p] = -e[p];
            if (p + 1 < this.m && e[p] != 0.0D) {
               for(pp = p + 1; pp < this.m; ++pp) {
                  work[pp] = 0.0D;
               }

               for(pp = p + 1; pp < this.n; ++pp) {
                  for(iter = p + 1; iter < this.m; ++iter) {
                     work[iter] += e[pp] * A[iter][pp];
                  }
               }

               for(pp = p + 1; pp < this.n; ++pp) {
                  t = -e[pp] / e[p + 1];

                  for(i = p + 1; i < this.m; ++i) {
                     A[i][pp] += t * work[i];
                  }
               }
            }

            for(pp = p + 1; pp < this.n; ++pp) {
               V[pp][p] = e[pp];
            }
         }
      }

      p = this.n;
      if (nct < this.n) {
         this.singularValues[nct] = A[nct][nct];
      }

      if (this.m < p) {
         this.singularValues[p - 1] = 0.0D;
      }

      if (nrt + 1 < p) {
         e[nrt] = A[nrt][p - 1];
      }

      e[p - 1] = 0.0D;

      for(pp = nct; pp < this.n; ++pp) {
         for(iter = 0; iter < this.m; ++iter) {
            U[iter][pp] = 0.0D;
         }

         U[pp][pp] = 1.0D;
      }

      double t;
      int i;
      for(pp = nct - 1; pp >= 0; --pp) {
         if (this.singularValues[pp] != 0.0D) {
            for(iter = pp + 1; iter < this.n; ++iter) {
               t = 0.0D;

               for(i = pp; i < this.m; ++i) {
                  t += U[i][pp] * U[i][iter];
               }

               t = -t / U[pp][pp];

               for(i = pp; i < this.m; ++i) {
                  U[i][iter] += t * U[i][pp];
               }
            }

            for(iter = pp; iter < this.m; ++iter) {
               U[iter][pp] = -U[iter][pp];
            }

            ++U[pp][pp];

            for(iter = 0; iter < pp - 1; ++iter) {
               U[iter][pp] = 0.0D;
            }
         } else {
            for(iter = 0; iter < this.m; ++iter) {
               U[iter][pp] = 0.0D;
            }

            U[pp][pp] = 1.0D;
         }
      }

      for(pp = this.n - 1; pp >= 0; --pp) {
         if (pp < nrt && e[pp] != 0.0D) {
            for(iter = pp + 1; iter < this.n; ++iter) {
               t = 0.0D;

               for(i = pp + 1; i < this.n; ++i) {
                  t += V[i][pp] * V[i][iter];
               }

               t = -t / V[pp + 1][pp];

               for(i = pp + 1; i < this.n; ++i) {
                  V[i][iter] += t * V[i][pp];
               }
            }
         }

         for(iter = 0; iter < this.n; ++iter) {
            V[iter][pp] = 0.0D;
         }

         V[pp][pp] = 1.0D;
      }

      pp = p - 1;
      iter = 0;

      while(true) {
         label315:
         while(p > 0) {
            int k;
            double t;
            for(k = p - 2; k >= 0; --k) {
               t = 1.6033346880071782E-291D + 2.220446049250313E-16D * (FastMath.abs(this.singularValues[k]) + FastMath.abs(this.singularValues[k + 1]));
               if (!(FastMath.abs(e[k]) > t)) {
                  e[k] = 0.0D;
                  break;
               }
            }

            byte kase;
            if (k == p - 2) {
               kase = 4;
            } else {
               for(i = p - 1; i >= k && i != k; --i) {
                  double t = (i != p ? FastMath.abs(e[i]) : 0.0D) + (i != k + 1 ? FastMath.abs(e[i - 1]) : 0.0D);
                  if (FastMath.abs(this.singularValues[i]) <= 1.6033346880071782E-291D + 2.220446049250313E-16D * t) {
                     this.singularValues[i] = 0.0D;
                     break;
                  }
               }

               if (i == k) {
                  kase = 3;
               } else if (i == p - 1) {
                  kase = 1;
               } else {
                  kase = 2;
                  k = i;
               }
            }

            ++k;
            double t;
            double cs;
            double sn;
            int i;
            int i;
            switch(kase) {
            case 1:
               t = e[p - 2];
               e[p - 2] = 0.0D;
               i = p - 2;

               while(true) {
                  if (i < k) {
                     continue label315;
                  }

                  t = FastMath.hypot(this.singularValues[i], t);
                  cs = this.singularValues[i] / t;
                  sn = t / t;
                  this.singularValues[i] = t;
                  if (i != k) {
                     t = -sn * e[i - 1];
                     e[i - 1] = cs * e[i - 1];
                  }

                  for(i = 0; i < this.n; ++i) {
                     t = cs * V[i][i] + sn * V[i][p - 1];
                     V[i][p - 1] = -sn * V[i][i] + cs * V[i][p - 1];
                     V[i][i] = t;
                  }

                  --i;
               }
            case 2:
               t = e[k - 1];
               e[k - 1] = 0.0D;
               i = k;

               while(true) {
                  if (i >= p) {
                     continue label315;
                  }

                  t = FastMath.hypot(this.singularValues[i], t);
                  cs = this.singularValues[i] / t;
                  sn = t / t;
                  this.singularValues[i] = t;
                  t = -sn * e[i];
                  e[i] = cs * e[i];

                  for(i = 0; i < this.m; ++i) {
                     t = cs * U[i][i] + sn * U[i][k - 1];
                     U[i][k - 1] = -sn * U[i][i] + cs * U[i][k - 1];
                     U[i][i] = t;
                  }

                  ++i;
               }
            case 3:
               t = FastMath.max(FastMath.abs(this.singularValues[p - 1]), FastMath.abs(this.singularValues[p - 2]));
               double scale = FastMath.max(FastMath.max(FastMath.max(t, FastMath.abs(e[p - 2])), FastMath.abs(this.singularValues[k])), FastMath.abs(e[k]));
               double sp = this.singularValues[p - 1] / scale;
               double spm1 = this.singularValues[p - 2] / scale;
               double epm1 = e[p - 2] / scale;
               double sk = this.singularValues[k] / scale;
               double ek = e[k] / scale;
               double b = ((spm1 + sp) * (spm1 - sp) + epm1 * epm1) / 2.0D;
               double c = sp * epm1 * sp * epm1;
               double shift = 0.0D;
               if (b != 0.0D || c != 0.0D) {
                  shift = FastMath.sqrt(b * b + c);
                  if (b < 0.0D) {
                     shift = -shift;
                  }

                  shift = c / (b + shift);
               }

               double f = (sk + sp) * (sk - sp) + shift;
               double g = sk * ek;

               for(int j = k; j < p - 1; ++j) {
                  double t = FastMath.hypot(f, g);
                  double cs = f / t;
                  double sn = g / t;
                  if (j != k) {
                     e[j - 1] = t;
                  }

                  f = cs * this.singularValues[j] + sn * e[j];
                  e[j] = cs * e[j] - sn * this.singularValues[j];
                  g = sn * this.singularValues[j + 1];
                  this.singularValues[j + 1] = cs * this.singularValues[j + 1];

                  int i;
                  for(i = 0; i < this.n; ++i) {
                     t = cs * V[i][j] + sn * V[i][j + 1];
                     V[i][j + 1] = -sn * V[i][j] + cs * V[i][j + 1];
                     V[i][j] = t;
                  }

                  t = FastMath.hypot(f, g);
                  cs = f / t;
                  sn = g / t;
                  this.singularValues[j] = t;
                  f = cs * e[j] + sn * this.singularValues[j + 1];
                  this.singularValues[j + 1] = -sn * e[j] + cs * this.singularValues[j + 1];
                  g = sn * e[j + 1];
                  e[j + 1] = cs * e[j + 1];
                  if (j < this.m - 1) {
                     for(i = 0; i < this.m; ++i) {
                        t = cs * U[i][j] + sn * U[i][j + 1];
                        U[i][j + 1] = -sn * U[i][j] + cs * U[i][j + 1];
                        U[i][j] = t;
                     }
                  }
               }

               e[p - 2] = f;
               ++iter;
               continue;
            }

            if (this.singularValues[k] <= 0.0D) {
               this.singularValues[k] = this.singularValues[k] < 0.0D ? -this.singularValues[k] : 0.0D;

               for(i = 0; i <= pp; ++i) {
                  V[i][k] = -V[i][k];
               }
            }

            for(; k < pp && !(this.singularValues[k] >= this.singularValues[k + 1]); ++k) {
               t = this.singularValues[k];
               this.singularValues[k] = this.singularValues[k + 1];
               this.singularValues[k + 1] = t;
               if (k < this.n - 1) {
                  for(i = 0; i < this.n; ++i) {
                     t = V[i][k + 1];
                     V[i][k + 1] = V[i][k];
                     V[i][k] = t;
                  }
               }

               if (k < this.m - 1) {
                  for(i = 0; i < this.m; ++i) {
                     t = U[i][k + 1];
                     U[i][k + 1] = U[i][k];
                     U[i][k] = t;
                  }
               }
            }

            iter = 0;
            --p;
         }

         this.tol = FastMath.max((double)this.m * this.singularValues[0] * 2.220446049250313E-16D, FastMath.sqrt(Precision.SAFE_MIN));
         if (!this.transposed) {
            this.cachedU = MatrixUtils.createRealMatrix(U);
            this.cachedV = MatrixUtils.createRealMatrix(V);
         } else {
            this.cachedU = MatrixUtils.createRealMatrix(V);
            this.cachedV = MatrixUtils.createRealMatrix(U);
         }

         return;
      }
   }

   public RealMatrix getU() {
      return this.cachedU;
   }

   public RealMatrix getUT() {
      if (this.cachedUt == null) {
         this.cachedUt = this.getU().transpose();
      }

      return this.cachedUt;
   }

   public RealMatrix getS() {
      if (this.cachedS == null) {
         this.cachedS = MatrixUtils.createRealDiagonalMatrix(this.singularValues);
      }

      return this.cachedS;
   }

   public double[] getSingularValues() {
      return (double[])this.singularValues.clone();
   }

   public RealMatrix getV() {
      return this.cachedV;
   }

   public RealMatrix getVT() {
      if (this.cachedVt == null) {
         this.cachedVt = this.getV().transpose();
      }

      return this.cachedVt;
   }

   public RealMatrix getCovariance(double minSingularValue) {
      int p = this.singularValues.length;

      int dimension;
      for(dimension = 0; dimension < p && this.singularValues[dimension] >= minSingularValue; ++dimension) {
      }

      if (dimension == 0) {
         throw new NumberIsTooLargeException(LocalizedFormats.TOO_LARGE_CUTOFF_SINGULAR_VALUE, minSingularValue, this.singularValues[0], true);
      } else {
         final double[][] data = new double[dimension][p];
         this.getVT().walkInOptimizedOrder((RealMatrixPreservingVisitor)(new DefaultRealMatrixPreservingVisitor() {
            public void visit(int row, int column, double value) {
               data[row][column] = value / SingularValueDecomposition.this.singularValues[row];
            }
         }), 0, dimension - 1, 0, p - 1);
         RealMatrix jv = new Array2DRowRealMatrix(data, false);
         return jv.transpose().multiply(jv);
      }
   }

   public double getNorm() {
      return this.singularValues[0];
   }

   public double getConditionNumber() {
      return this.singularValues[0] / this.singularValues[this.n - 1];
   }

   public double getInverseConditionNumber() {
      return this.singularValues[this.n - 1] / this.singularValues[0];
   }

   public int getRank() {
      int r = 0;

      for(int i = 0; i < this.singularValues.length; ++i) {
         if (this.singularValues[i] > this.tol) {
            ++r;
         }
      }

      return r;
   }

   public DecompositionSolver getSolver() {
      return new SingularValueDecomposition.Solver(this.singularValues, this.getUT(), this.getV(), this.getRank() == this.m, this.tol);
   }

   private static class Solver implements DecompositionSolver {
      private final RealMatrix pseudoInverse;
      private boolean nonSingular;

      private Solver(double[] singularValues, RealMatrix uT, RealMatrix v, boolean nonSingular, double tol) {
         double[][] suT = uT.getData();

         for(int i = 0; i < singularValues.length; ++i) {
            double a;
            if (singularValues[i] > tol) {
               a = 1.0D / singularValues[i];
            } else {
               a = 0.0D;
            }

            double[] suTi = suT[i];

            for(int j = 0; j < suTi.length; ++j) {
               suTi[j] *= a;
            }
         }

         this.pseudoInverse = v.multiply(new Array2DRowRealMatrix(suT, false));
         this.nonSingular = nonSingular;
      }

      public RealVector solve(RealVector b) {
         return this.pseudoInverse.operate(b);
      }

      public RealMatrix solve(RealMatrix b) {
         return this.pseudoInverse.multiply(b);
      }

      public boolean isNonSingular() {
         return this.nonSingular;
      }

      public RealMatrix getInverse() {
         return this.pseudoInverse;
      }

      // $FF: synthetic method
      Solver(double[] x0, RealMatrix x1, RealMatrix x2, boolean x3, double x4, Object x5) {
         this(x0, x1, x2, x3, x4);
      }
   }
}
