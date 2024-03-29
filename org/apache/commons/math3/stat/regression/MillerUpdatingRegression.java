package org.apache.commons.math3.stat.regression;

import java.util.Arrays;
import org.apache.commons.math3.exception.util.LocalizedFormats;
import org.apache.commons.math3.util.FastMath;
import org.apache.commons.math3.util.MathArrays;
import org.apache.commons.math3.util.Precision;

public class MillerUpdatingRegression implements UpdatingMultipleLinearRegression {
   private final int nvars;
   private final double[] d;
   private final double[] rhs;
   private final double[] r;
   private final double[] tol;
   private final double[] rss;
   private final int[] vorder;
   private final double[] work_tolset;
   private long nobs;
   private double sserr;
   private boolean rss_set;
   private boolean tol_set;
   private final boolean[] lindep;
   private final double[] x_sing;
   private final double[] work_sing;
   private double sumy;
   private double sumsqy;
   private boolean hasIntercept;
   private final double epsilon;

   private MillerUpdatingRegression() {
      this(-1, false, Double.NaN);
   }

   public MillerUpdatingRegression(int numberOfVariables, boolean includeConstant, double errorTolerance) throws ModelSpecificationException {
      this.nobs = 0L;
      this.sserr = 0.0D;
      this.rss_set = false;
      this.tol_set = false;
      this.sumy = 0.0D;
      this.sumsqy = 0.0D;
      if (numberOfVariables < 1) {
         throw new ModelSpecificationException(LocalizedFormats.NO_REGRESSORS, new Object[0]);
      } else {
         if (includeConstant) {
            this.nvars = numberOfVariables + 1;
         } else {
            this.nvars = numberOfVariables;
         }

         this.hasIntercept = includeConstant;
         this.nobs = 0L;
         this.d = new double[this.nvars];
         this.rhs = new double[this.nvars];
         this.r = new double[this.nvars * (this.nvars - 1) / 2];
         this.tol = new double[this.nvars];
         this.rss = new double[this.nvars];
         this.vorder = new int[this.nvars];
         this.x_sing = new double[this.nvars];
         this.work_sing = new double[this.nvars];
         this.work_tolset = new double[this.nvars];
         this.lindep = new boolean[this.nvars];

         for(int i = 0; i < this.nvars; this.vorder[i] = i++) {
         }

         if (errorTolerance > 0.0D) {
            this.epsilon = errorTolerance;
         } else {
            this.epsilon = -errorTolerance;
         }

      }
   }

   public MillerUpdatingRegression(int numberOfVariables, boolean includeConstant) throws ModelSpecificationException {
      this(numberOfVariables, includeConstant, Precision.EPSILON);
   }

   public boolean hasIntercept() {
      return this.hasIntercept;
   }

   public long getN() {
      return this.nobs;
   }

   public void addObservation(double[] x, double y) throws ModelSpecificationException {
      if ((this.hasIntercept || x.length == this.nvars) && (!this.hasIntercept || x.length + 1 == this.nvars)) {
         if (!this.hasIntercept) {
            this.include(MathArrays.copyOf(x, x.length), 1.0D, y);
         } else {
            double[] tmp = new double[x.length + 1];
            System.arraycopy(x, 0, tmp, 1, x.length);
            tmp[0] = 1.0D;
            this.include(tmp, 1.0D, y);
         }

         ++this.nobs;
      } else {
         throw new ModelSpecificationException(LocalizedFormats.INVALID_REGRESSION_OBSERVATION, new Object[]{x.length, this.nvars});
      }
   }

   public void addObservations(double[][] x, double[] y) throws ModelSpecificationException {
      if (x != null && y != null && x.length == y.length) {
         if (x.length == 0) {
            throw new ModelSpecificationException(LocalizedFormats.NO_DATA, new Object[0]);
         } else if (x[0].length + 1 > x.length) {
            throw new ModelSpecificationException(LocalizedFormats.NOT_ENOUGH_DATA_FOR_NUMBER_OF_PREDICTORS, new Object[]{x.length, x[0].length});
         } else {
            for(int i = 0; i < x.length; ++i) {
               this.addObservation(x[i], y[i]);
            }

         }
      } else {
         throw new ModelSpecificationException(LocalizedFormats.DIMENSIONS_MISMATCH_SIMPLE, new Object[]{x == null ? 0 : x.length, y == null ? 0 : y.length});
      }
   }

   private void include(double[] x, double wi, double yi) {
      int nextr = 0;
      double w = wi;
      double y = yi;
      this.rss_set = false;
      this.sumy = this.smartAdd(yi, this.sumy);
      this.sumsqy = this.smartAdd(this.sumsqy, yi * yi);

      for(int i = 0; i < x.length; ++i) {
         if (w == 0.0D) {
            return;
         }

         double xi = x[i];
         if (xi == 0.0D) {
            nextr += this.nvars - i - 1;
         } else {
            double di = this.d[i];
            double wxi = w * xi;
            double _w = w;
            double dpi;
            if (di != 0.0D) {
               dpi = this.smartAdd(di, wxi * xi);
               double tmp = wxi * xi / di;
               if (FastMath.abs(tmp) > Precision.EPSILON) {
                  w = di * w / dpi;
               }
            } else {
               dpi = wxi * xi;
               w = 0.0D;
            }

            this.d[i] = dpi;

            double xk;
            for(int k = i + 1; k < this.nvars; ++k) {
               xk = x[k];
               x[k] = this.smartAdd(xk, -xi * this.r[nextr]);
               if (di != 0.0D) {
                  this.r[nextr] = this.smartAdd(di * this.r[nextr], _w * xi * xk) / dpi;
               } else {
                  this.r[nextr] = xk / xi;
               }

               ++nextr;
            }

            xk = y;
            y = this.smartAdd(y, -xi * this.rhs[i]);
            if (di != 0.0D) {
               this.rhs[i] = this.smartAdd(di * this.rhs[i], wxi * xk) / dpi;
            } else {
               this.rhs[i] = xk / xi;
            }
         }
      }

      this.sserr = this.smartAdd(this.sserr, w * y * y);
   }

   private double smartAdd(double a, double b) {
      double _a = FastMath.abs(a);
      double _b = FastMath.abs(b);
      double eps;
      if (_a > _b) {
         eps = _a * Precision.EPSILON;
         return _b > eps ? a + b : a;
      } else {
         eps = _b * Precision.EPSILON;
         return _a > eps ? a + b : b;
      }
   }

   public void clear() {
      Arrays.fill(this.d, 0.0D);
      Arrays.fill(this.rhs, 0.0D);
      Arrays.fill(this.r, 0.0D);
      Arrays.fill(this.tol, 0.0D);
      Arrays.fill(this.rss, 0.0D);
      Arrays.fill(this.work_tolset, 0.0D);
      Arrays.fill(this.work_sing, 0.0D);
      Arrays.fill(this.x_sing, 0.0D);
      Arrays.fill(this.lindep, false);

      for(int i = 0; i < this.nvars; this.vorder[i] = i++) {
      }

      this.nobs = 0L;
      this.sserr = 0.0D;
      this.sumy = 0.0D;
      this.sumsqy = 0.0D;
      this.rss_set = false;
      this.tol_set = false;
   }

   private void tolset() {
      double eps = this.epsilon;

      int col;
      for(col = 0; col < this.nvars; ++col) {
         this.work_tolset[col] = Math.sqrt(this.d[col]);
      }

      this.tol[0] = eps * this.work_tolset[0];

      for(col = 1; col < this.nvars; ++col) {
         int pos = col - 1;
         double total = this.work_tolset[col];

         for(int row = 0; row < col; ++row) {
            total += Math.abs(this.r[pos]) * this.work_tolset[row];
            pos += this.nvars - row - 2;
         }

         this.tol[col] = eps * total;
      }

      this.tol_set = true;
   }

   private double[] regcf(int nreq) throws ModelSpecificationException {
      if (nreq < 1) {
         throw new ModelSpecificationException(LocalizedFormats.NO_REGRESSORS, new Object[0]);
      } else if (nreq > this.nvars) {
         throw new ModelSpecificationException(LocalizedFormats.TOO_MANY_REGRESSORS, new Object[]{nreq, this.nvars});
      } else {
         if (!this.tol_set) {
            this.tolset();
         }

         double[] ret = new double[nreq];
         boolean rankProblem = false;

         int i;
         for(i = nreq - 1; i > -1; --i) {
            if (Math.sqrt(this.d[i]) < this.tol[i]) {
               ret[i] = 0.0D;
               this.d[i] = 0.0D;
               rankProblem = true;
            } else {
               ret[i] = this.rhs[i];
               int nextr = i * (this.nvars + this.nvars - i - 1) / 2;

               for(int j = i + 1; j < nreq; ++j) {
                  ret[i] = this.smartAdd(ret[i], -this.r[nextr] * ret[j]);
                  ++nextr;
               }
            }
         }

         if (rankProblem) {
            for(i = 0; i < nreq; ++i) {
               if (this.lindep[i]) {
                  ret[i] = Double.NaN;
               }
            }
         }

         return ret;
      }
   }

   private void singcheck() {
      int col;
      for(col = 0; col < this.nvars; ++col) {
         this.work_sing[col] = Math.sqrt(this.d[col]);
      }

      for(col = 0; col < this.nvars; ++col) {
         double temp = this.tol[col];
         int pos = col - 1;

         int _pi;
         for(_pi = 0; _pi < col - 1; ++_pi) {
            if (Math.abs(this.r[pos]) * this.work_sing[_pi] < temp) {
               this.r[pos] = 0.0D;
            }

            pos += this.nvars - _pi - 2;
         }

         this.lindep[col] = false;
         if (this.work_sing[col] < temp) {
            this.lindep[col] = true;
            if (col >= this.nvars - 1) {
               this.sserr += this.d[col] * this.rhs[col] * this.rhs[col];
            } else {
               Arrays.fill(this.x_sing, 0.0D);
               _pi = col * (this.nvars + this.nvars - col - 1) / 2;

               for(int _xi = col + 1; _xi < this.nvars; ++_pi) {
                  this.x_sing[_xi] = this.r[_pi];
                  this.r[_pi] = 0.0D;
                  ++_xi;
               }

               double y = this.rhs[col];
               double weight = this.d[col];
               this.d[col] = 0.0D;
               this.rhs[col] = 0.0D;
               this.include(this.x_sing, weight, y);
            }
         }
      }

   }

   private void ss() {
      double total = this.sserr;
      this.rss[this.nvars - 1] = this.sserr;

      for(int i = this.nvars - 1; i > 0; --i) {
         total += this.d[i] * this.rhs[i] * this.rhs[i];
         this.rss[i - 1] = total;
      }

      this.rss_set = true;
   }

   private double[] cov(int nreq) {
      if (this.nobs <= (long)nreq) {
         return null;
      } else {
         double rnk = 0.0D;

         for(int i = 0; i < nreq; ++i) {
            if (!this.lindep[i]) {
               ++rnk;
            }
         }

         double var = this.rss[nreq - 1] / ((double)this.nobs - rnk);
         double[] rinv = new double[nreq * (nreq - 1) / 2];
         this.inverse(rinv, nreq);
         double[] covmat = new double[nreq * (nreq + 1) / 2];
         Arrays.fill(covmat, Double.NaN);
         int start = 0;
         double total = 0.0D;

         for(int row = 0; row < nreq; ++row) {
            int pos2 = start;
            if (!this.lindep[row]) {
               for(int col = row; col < nreq; ++col) {
                  if (this.lindep[col]) {
                     pos2 += nreq - col - 1;
                  } else {
                     int pos1 = start + col - row;
                     if (row == col) {
                        total = 1.0D / this.d[col];
                     } else {
                        total = rinv[pos1 - 1] / this.d[col];
                     }

                     for(int k = col + 1; k < nreq; ++k) {
                        if (!this.lindep[k]) {
                           total += rinv[pos1] * rinv[pos2] / this.d[k];
                        }

                        ++pos1;
                        ++pos2;
                     }

                     covmat[(col + 1) * col / 2 + row] = total * var;
                  }
               }
            }

            start += nreq - row - 1;
         }

         return covmat;
      }
   }

   private void inverse(double[] rinv, int nreq) {
      int pos = nreq * (nreq - 1) / 2 - 1;
      int pos1 = true;
      int pos2 = true;
      double total = 0.0D;
      Arrays.fill(rinv, Double.NaN);

      for(int row = nreq - 1; row > 0; --row) {
         if (this.lindep[row]) {
            pos -= nreq - row;
         } else {
            int start = (row - 1) * (this.nvars + this.nvars - row) / 2;

            for(int col = nreq; col > row; --col) {
               int pos1 = start;
               int pos2 = pos;
               total = 0.0D;

               for(int k = row; k < col - 1; ++k) {
                  pos2 += nreq - k - 1;
                  if (!this.lindep[k]) {
                     total += -this.r[pos1] * rinv[pos2];
                  }

                  ++pos1;
               }

               rinv[pos] = total - this.r[pos1];
               --pos;
            }
         }
      }

   }

   public double[] getPartialCorrelations(int in) {
      double[] output = new double[(this.nvars - in + 1) * (this.nvars - in) / 2];
      int rms_off = -in;
      int wrk_off = -(in + 1);
      double[] rms = new double[this.nvars - in];
      double[] work = new double[this.nvars - in - 1];
      int offXX = (this.nvars - in) * (this.nvars - in - 1) / 2;
      if (in >= -1 && in < this.nvars) {
         int nvm = this.nvars - 1;
         int base_pos = this.r.length - (nvm - in) * (nvm - in + 1) / 2;
         if (this.d[in] > 0.0D) {
            rms[in + rms_off] = 1.0D / Math.sqrt(this.d[in]);
         }

         int pos;
         int col1;
         int col2;
         for(col1 = in + 1; col1 < this.nvars; ++col1) {
            pos = base_pos + col1 - 1 - in;
            double sumxx = this.d[col1];

            for(col2 = in; col2 < col1; ++col2) {
               sumxx += this.d[col2] * this.r[pos] * this.r[pos];
               pos += this.nvars - col2 - 2;
            }

            if (sumxx > 0.0D) {
               rms[col1 + rms_off] = 1.0D / Math.sqrt(sumxx);
            } else {
               rms[col1 + rms_off] = 0.0D;
            }
         }

         double sumyy = this.sserr;

         for(col1 = in; col1 < this.nvars; ++col1) {
            sumyy += this.d[col1] * this.rhs[col1] * this.rhs[col1];
         }

         if (sumyy > 0.0D) {
            sumyy = 1.0D / Math.sqrt(sumyy);
         }

         pos = 0;

         for(col1 = in; col1 < this.nvars; ++col1) {
            double sumxy = 0.0D;
            Arrays.fill(work, 0.0D);
            int pos1 = base_pos + col1 - in - 1;

            int pos2;
            for(col2 = in; col2 < col1; ++col2) {
               pos2 = pos1 + 1;

               for(int col2 = col1 + 1; col2 < this.nvars; ++col2) {
                  work[col2 + wrk_off] += this.d[col2] * this.r[pos1] * this.r[pos2];
                  ++pos2;
               }

               sumxy += this.d[col2] * this.r[pos1] * this.rhs[col2];
               pos1 += this.nvars - col2 - 2;
            }

            pos2 = pos1 + 1;

            for(col2 = col1 + 1; col2 < this.nvars; ++col2) {
               work[col2 + wrk_off] += this.d[col1] * this.r[pos2];
               ++pos2;
               output[(col2 - 1 - in) * (col2 - in) / 2 + col1 - in] = work[col2 + wrk_off] * rms[col1 + rms_off] * rms[col2 + rms_off];
               ++pos;
            }

            sumxy += this.d[col1] * this.rhs[col1];
            output[col1 + rms_off + offXX] = sumxy * rms[col1 + rms_off] * sumyy;
         }

         return output;
      } else {
         return null;
      }
   }

   private void vmove(int from, int to) {
      boolean bSkipTo40 = false;
      if (from != to) {
         if (!this.rss_set) {
            this.ss();
         }

         int count = false;
         int first;
         byte inc;
         int count;
         if (from < to) {
            first = from;
            inc = 1;
            count = to - from;
         } else {
            first = from - 1;
            inc = -1;
            count = from - to;
         }

         int m = first;

         for(int idx = 0; idx < count; ++idx) {
            int m1 = m * (this.nvars + this.nvars - m - 1) / 2;
            int m2 = m1 + this.nvars - m - 1;
            int mp1 = m + 1;
            double d1 = this.d[m];
            double d2 = this.d[mp1];
            double X;
            int col;
            if (d1 > this.epsilon || d2 > this.epsilon) {
               X = this.r[m1];
               if (Math.abs(X) * Math.sqrt(d1) < this.tol[mp1]) {
                  X = 0.0D;
               }

               if (!(d1 < this.epsilon) && !(Math.abs(X) < this.epsilon)) {
                  if (d2 < this.epsilon) {
                     this.d[m] = d1 * X * X;
                     this.r[m1] = 1.0D / X;

                     for(col = m1 + 1; col < m1 + this.nvars - m - 1; ++col) {
                        double[] var10000 = this.r;
                        var10000[col] /= X;
                     }

                     this.rhs[m] /= X;
                     bSkipTo40 = true;
                  }
               } else {
                  this.d[m] = d2;
                  this.d[mp1] = d1;
                  this.r[m1] = 0.0D;

                  for(col = m + 2; col < this.nvars; ++col) {
                     ++m1;
                     X = this.r[m1];
                     this.r[m1] = this.r[m2];
                     this.r[m2] = X;
                     ++m2;
                  }

                  X = this.rhs[m];
                  this.rhs[m] = this.rhs[mp1];
                  this.rhs[mp1] = X;
                  bSkipTo40 = true;
               }

               if (!bSkipTo40) {
                  double d1new = d2 + d1 * X * X;
                  double cbar = d2 / d1new;
                  double sbar = X * d1 / d1new;
                  double d2new = d1 * cbar;
                  this.d[m] = d1new;
                  this.d[mp1] = d2new;
                  this.r[m1] = sbar;

                  double Y;
                  for(col = m + 2; col < this.nvars; ++col) {
                     ++m1;
                     Y = this.r[m1];
                     this.r[m1] = cbar * this.r[m2] + sbar * Y;
                     this.r[m2] = Y - X * this.r[m2];
                     ++m2;
                  }

                  Y = this.rhs[m];
                  this.rhs[m] = cbar * this.rhs[mp1] + sbar * Y;
                  this.rhs[mp1] = Y - X * this.rhs[mp1];
               }
            }

            if (m > 0) {
               int pos = m;

               for(col = 0; col < m; ++col) {
                  X = this.r[pos];
                  this.r[pos] = this.r[pos - 1];
                  this.r[pos - 1] = X;
                  pos += this.nvars - col - 2;
               }
            }

            m1 = this.vorder[m];
            this.vorder[m] = this.vorder[mp1];
            this.vorder[mp1] = m1;
            X = this.tol[m];
            this.tol[m] = this.tol[mp1];
            this.tol[mp1] = X;
            this.rss[m] = this.rss[mp1] + this.d[mp1] * this.rhs[mp1] * this.rhs[mp1];
            m += inc;
         }

      }
   }

   private int reorderRegressors(int[] list, int pos1) {
      if (list.length >= 1 && list.length <= this.nvars + 1 - pos1) {
         int next = pos1;

         for(int i = pos1; i < this.nvars; ++i) {
            int l = this.vorder[i];

            for(int j = 0; j < list.length; ++j) {
               if (l == list[j] && i > next) {
                  this.vmove(i, next);
                  ++next;
                  if (next >= list.length + pos1) {
                     return 0;
                  }
                  break;
               }
            }
         }

         return 0;
      } else {
         return -1;
      }
   }

   public double getDiagonalOfHatMatrix(double[] row_data) {
      double[] wk = new double[this.nvars];
      if (row_data.length > this.nvars) {
         return Double.NaN;
      } else {
         double[] xrow;
         if (this.hasIntercept) {
            xrow = new double[row_data.length + 1];
            xrow[0] = 1.0D;
            System.arraycopy(row_data, 0, xrow, 1, row_data.length);
         } else {
            xrow = row_data;
         }

         double hii = 0.0D;

         for(int col = 0; col < xrow.length; ++col) {
            if (Math.sqrt(this.d[col]) < this.tol[col]) {
               wk[col] = 0.0D;
            } else {
               int pos = col - 1;
               double total = xrow[col];

               for(int row = 0; row < col; ++row) {
                  total = this.smartAdd(total, -wk[row] * this.r[pos]);
                  pos += this.nvars - row - 2;
               }

               wk[col] = total;
               hii = this.smartAdd(hii, total * total / this.d[col]);
            }
         }

         return hii;
      }
   }

   public int[] getOrderOfRegressors() {
      return MathArrays.copyOf(this.vorder);
   }

   public RegressionResults regress() throws ModelSpecificationException {
      return this.regress(this.nvars);
   }

   public RegressionResults regress(int numberOfRegressors) throws ModelSpecificationException {
      if (this.nobs <= (long)numberOfRegressors) {
         throw new ModelSpecificationException(LocalizedFormats.NOT_ENOUGH_DATA_FOR_NUMBER_OF_PREDICTORS, new Object[]{this.nobs, numberOfRegressors});
      } else if (numberOfRegressors > this.nvars) {
         throw new ModelSpecificationException(LocalizedFormats.TOO_MANY_REGRESSORS, new Object[]{numberOfRegressors, this.nvars});
      } else {
         this.tolset();
         this.singcheck();
         double[] beta = this.regcf(numberOfRegressors);
         this.ss();
         double[] cov = this.cov(numberOfRegressors);
         int rnk = 0;

         for(int i = 0; i < this.lindep.length; ++i) {
            if (!this.lindep[i]) {
               ++rnk;
            }
         }

         boolean needsReorder = false;

         for(int i = 0; i < numberOfRegressors; ++i) {
            if (this.vorder[i] != i) {
               needsReorder = true;
               break;
            }
         }

         if (!needsReorder) {
            return new RegressionResults(beta, new double[][]{cov}, true, this.nobs, rnk, this.sumy, this.sumsqy, this.sserr, this.hasIntercept, false);
         } else {
            double[] betaNew = new double[beta.length];
            double[] covNew = new double[cov.length];
            int[] newIndices = new int[beta.length];

            int idx1;
            int idx2;
            for(idx1 = 0; idx1 < this.nvars; ++idx1) {
               for(idx2 = 0; idx2 < numberOfRegressors; ++idx2) {
                  if (this.vorder[idx2] == idx1) {
                     betaNew[idx1] = beta[idx2];
                     newIndices[idx1] = idx2;
                  }
               }
            }

            idx1 = 0;

            for(int i = 0; i < beta.length; ++i) {
               int _i = newIndices[i];

               for(int j = 0; j <= i; ++idx1) {
                  int _j = newIndices[j];
                  if (_i > _j) {
                     idx2 = _i * (_i + 1) / 2 + _j;
                  } else {
                     idx2 = _j * (_j + 1) / 2 + _i;
                  }

                  covNew[idx1] = cov[idx2];
                  ++j;
               }
            }

            return new RegressionResults(betaNew, new double[][]{covNew}, true, this.nobs, rnk, this.sumy, this.sumsqy, this.sserr, this.hasIntercept, false);
         }
      }
   }

   public RegressionResults regress(int[] variablesToInclude) throws ModelSpecificationException {
      if (variablesToInclude.length > this.nvars) {
         throw new ModelSpecificationException(LocalizedFormats.TOO_MANY_REGRESSORS, new Object[]{variablesToInclude.length, this.nvars});
      } else if (this.nobs <= (long)this.nvars) {
         throw new ModelSpecificationException(LocalizedFormats.NOT_ENOUGH_DATA_FOR_NUMBER_OF_PREDICTORS, new Object[]{this.nobs, this.nvars});
      } else {
         Arrays.sort(variablesToInclude);
         int iExclude = 0;

         for(int i = 0; i < variablesToInclude.length; ++i) {
            if (i >= this.nvars) {
               throw new ModelSpecificationException(LocalizedFormats.INDEX_LARGER_THAN_MAX, new Object[]{i, this.nvars});
            }

            if (i > 0 && variablesToInclude[i] == variablesToInclude[i - 1]) {
               variablesToInclude[i] = -1;
               ++iExclude;
            }
         }

         int[] series;
         if (iExclude > 0) {
            int j = 0;
            series = new int[variablesToInclude.length - iExclude];

            for(int i = 0; i < variablesToInclude.length; ++i) {
               if (variablesToInclude[i] > -1) {
                  series[j] = variablesToInclude[i];
                  ++j;
               }
            }
         } else {
            series = variablesToInclude;
         }

         this.reorderRegressors(series, 0);
         this.tolset();
         this.singcheck();
         double[] beta = this.regcf(series.length);
         this.ss();
         double[] cov = this.cov(series.length);
         int rnk = 0;

         for(int i = 0; i < this.lindep.length; ++i) {
            if (!this.lindep[i]) {
               ++rnk;
            }
         }

         boolean needsReorder = false;

         for(int i = 0; i < this.nvars; ++i) {
            if (this.vorder[i] != series[i]) {
               needsReorder = true;
               break;
            }
         }

         if (!needsReorder) {
            return new RegressionResults(beta, new double[][]{cov}, true, this.nobs, rnk, this.sumy, this.sumsqy, this.sserr, this.hasIntercept, false);
         } else {
            double[] betaNew = new double[beta.length];
            int[] newIndices = new int[beta.length];

            int idx1;
            for(int i = 0; i < series.length; ++i) {
               for(idx1 = 0; idx1 < this.vorder.length; ++idx1) {
                  if (this.vorder[idx1] == series[i]) {
                     betaNew[i] = beta[idx1];
                     newIndices[i] = idx1;
                  }
               }
            }

            double[] covNew = new double[cov.length];
            idx1 = 0;

            for(int i = 0; i < beta.length; ++i) {
               int _i = newIndices[i];

               for(int j = 0; j <= i; ++idx1) {
                  int _j = newIndices[j];
                  int idx2;
                  if (_i > _j) {
                     idx2 = _i * (_i + 1) / 2 + _j;
                  } else {
                     idx2 = _j * (_j + 1) / 2 + _i;
                  }

                  covNew[idx1] = cov[idx2];
                  ++j;
               }
            }

            return new RegressionResults(betaNew, new double[][]{covNew}, true, this.nobs, rnk, this.sumy, this.sumsqy, this.sserr, this.hasIntercept, false);
         }
      }
   }
}
