package org.apache.commons.math3.distribution.fitting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.math3.distribution.MixtureMultivariateNormalDistribution;
import org.apache.commons.math3.distribution.MultivariateNormalDistribution;
import org.apache.commons.math3.exception.ConvergenceException;
import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.NotStrictlyPositiveException;
import org.apache.commons.math3.exception.NumberIsTooLargeException;
import org.apache.commons.math3.exception.NumberIsTooSmallException;
import org.apache.commons.math3.exception.util.LocalizedFormats;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.SingularMatrixException;
import org.apache.commons.math3.stat.correlation.Covariance;
import org.apache.commons.math3.util.MathArrays;
import org.apache.commons.math3.util.Pair;

public class MultivariateNormalMixtureExpectationMaximization {
   private static final int DEFAULT_MAX_ITERATIONS = 1000;
   private static final double DEFAULT_THRESHOLD = 1.0E-5D;
   private final double[][] data;
   private MixtureMultivariateNormalDistribution fittedModel;
   private double logLikelihood = 0.0D;

   public MultivariateNormalMixtureExpectationMaximization(double[][] data) throws NotStrictlyPositiveException, DimensionMismatchException, NumberIsTooSmallException {
      if (data.length < 1) {
         throw new NotStrictlyPositiveException(data.length);
      } else {
         this.data = new double[data.length][data[0].length];

         for(int i = 0; i < data.length; ++i) {
            if (data[i].length != data[0].length) {
               throw new DimensionMismatchException(data[i].length, data[0].length);
            }

            if (data[i].length < 2) {
               throw new NumberIsTooSmallException(LocalizedFormats.NUMBER_TOO_SMALL, data[i].length, 2, true);
            }

            this.data[i] = MathArrays.copyOf(data[i], data[i].length);
         }

      }
   }

   public void fit(MixtureMultivariateNormalDistribution initialMixture, int maxIterations, double threshold) throws SingularMatrixException, NotStrictlyPositiveException, DimensionMismatchException {
      if (maxIterations < 1) {
         throw new NotStrictlyPositiveException(maxIterations);
      } else if (threshold < Double.MIN_VALUE) {
         throw new NotStrictlyPositiveException(threshold);
      } else {
         int n = this.data.length;
         int numCols = this.data[0].length;
         int k = initialMixture.getComponents().size();
         int numMeanColumns = ((MultivariateNormalDistribution)((Pair)initialMixture.getComponents().get(0)).getSecond()).getMeans().length;
         if (numMeanColumns != numCols) {
            throw new DimensionMismatchException(numMeanColumns, numCols);
         } else {
            int numIterations = 0;
            double previousLogLikelihood = 0.0D;
            this.logLikelihood = Double.NEGATIVE_INFINITY;

            double[] newWeights;
            double[][] newMeans;
            double[][][] newCovMatArrays;
            for(this.fittedModel = new MixtureMultivariateNormalDistribution(initialMixture.getComponents()); numIterations++ <= maxIterations && Math.abs(previousLogLikelihood - this.logLikelihood) > threshold; this.fittedModel = new MixtureMultivariateNormalDistribution(newWeights, newMeans, newCovMatArrays)) {
               previousLogLikelihood = this.logLikelihood;
               double sumLogLikelihood = 0.0D;
               List<Pair<Double, MultivariateNormalDistribution>> components = this.fittedModel.getComponents();
               double[] weights = new double[k];
               MultivariateNormalDistribution[] mvns = new MultivariateNormalDistribution[k];

               for(int j = 0; j < k; ++j) {
                  weights[j] = (Double)((Pair)components.get(j)).getFirst();
                  mvns[j] = (MultivariateNormalDistribution)((Pair)components.get(j)).getSecond();
               }

               double[][] gamma = new double[n][k];
               double[] gammaSums = new double[k];
               double[][] gammaDataProdSums = new double[k][numCols];

               int i;
               int j;
               for(int i = 0; i < n; ++i) {
                  double rowDensity = this.fittedModel.density(this.data[i]);
                  sumLogLikelihood += Math.log(rowDensity);

                  for(i = 0; i < k; ++i) {
                     gamma[i][i] = weights[i] * mvns[i].density(this.data[i]) / rowDensity;
                     gammaSums[i] += gamma[i][i];

                     for(j = 0; j < numCols; ++j) {
                        gammaDataProdSums[i][j] += gamma[i][i] * this.data[i][j];
                     }
                  }
               }

               this.logLikelihood = sumLogLikelihood / (double)n;
               newWeights = new double[k];
               newMeans = new double[k][numCols];

               for(int j = 0; j < k; ++j) {
                  newWeights[j] = gammaSums[j] / (double)n;

                  for(i = 0; i < numCols; ++i) {
                     newMeans[j][i] = gammaDataProdSums[j][i] / gammaSums[j];
                  }
               }

               RealMatrix[] newCovMats = new RealMatrix[k];

               for(i = 0; i < k; ++i) {
                  newCovMats[i] = new Array2DRowRealMatrix(numCols, numCols);
               }

               for(i = 0; i < n; ++i) {
                  for(j = 0; j < k; ++j) {
                     RealMatrix vec = new Array2DRowRealMatrix(MathArrays.ebeSubtract(this.data[i], newMeans[j]));
                     RealMatrix dataCov = vec.multiply(vec.transpose()).scalarMultiply(gamma[i][j]);
                     newCovMats[j] = newCovMats[j].add(dataCov);
                  }
               }

               newCovMatArrays = new double[k][numCols][numCols];

               for(j = 0; j < k; ++j) {
                  newCovMats[j] = newCovMats[j].scalarMultiply(1.0D / gammaSums[j]);
                  newCovMatArrays[j] = newCovMats[j].getData();
               }
            }

            if (Math.abs(previousLogLikelihood - this.logLikelihood) > threshold) {
               throw new ConvergenceException();
            }
         }
      }
   }

   public void fit(MixtureMultivariateNormalDistribution initialMixture) throws SingularMatrixException, NotStrictlyPositiveException {
      this.fit(initialMixture, 1000, 1.0E-5D);
   }

   public static MixtureMultivariateNormalDistribution estimate(double[][] data, int numComponents) throws NotStrictlyPositiveException, DimensionMismatchException {
      if (data.length < 2) {
         throw new NotStrictlyPositiveException(data.length);
      } else if (numComponents < 2) {
         throw new NumberIsTooSmallException(numComponents, 2, true);
      } else if (numComponents > data.length) {
         throw new NumberIsTooLargeException(numComponents, data.length, true);
      } else {
         int numRows = data.length;
         int numCols = data[0].length;
         MultivariateNormalMixtureExpectationMaximization.DataRow[] sortedData = new MultivariateNormalMixtureExpectationMaximization.DataRow[numRows];

         for(int i = 0; i < numRows; ++i) {
            sortedData[i] = new MultivariateNormalMixtureExpectationMaximization.DataRow(data[i]);
         }

         Arrays.sort(sortedData);
         double weight = 1.0D / (double)numComponents;
         List<Pair<Double, MultivariateNormalDistribution>> components = new ArrayList();

         for(int binIndex = 0; binIndex < numComponents; ++binIndex) {
            int minIndex = binIndex * numRows / numComponents;
            int maxIndex = (binIndex + 1) * numRows / numComponents;
            int numBinRows = maxIndex - minIndex;
            double[][] binData = new double[numBinRows][numCols];
            double[] columnMeans = new double[numCols];
            int i = minIndex;

            for(int iBin = 0; i < maxIndex; ++iBin) {
               for(int j = 0; j < numCols; ++j) {
                  double val = sortedData[i].getRow()[j];
                  columnMeans[j] += val;
                  binData[iBin][j] = val;
               }

               ++i;
            }

            MathArrays.scaleInPlace(1.0D / (double)numBinRows, columnMeans);
            double[][] covMat = (new Covariance(binData)).getCovarianceMatrix().getData();
            MultivariateNormalDistribution mvn = new MultivariateNormalDistribution(columnMeans, covMat);
            components.add(new Pair(weight, mvn));
         }

         return new MixtureMultivariateNormalDistribution(components);
      }
   }

   public double getLogLikelihood() {
      return this.logLikelihood;
   }

   public MixtureMultivariateNormalDistribution getFittedModel() {
      return new MixtureMultivariateNormalDistribution(this.fittedModel.getComponents());
   }

   private static class DataRow implements Comparable<MultivariateNormalMixtureExpectationMaximization.DataRow> {
      private final double[] row;
      private Double mean;

      DataRow(double[] data) {
         this.row = data;
         this.mean = 0.0D;

         for(int i = 0; i < data.length; ++i) {
            this.mean = this.mean + data[i];
         }

         this.mean = this.mean / (double)data.length;
      }

      public int compareTo(MultivariateNormalMixtureExpectationMaximization.DataRow other) {
         return this.mean.compareTo(other.mean);
      }

      public boolean equals(Object other) {
         if (this == other) {
            return true;
         } else {
            return other instanceof MultivariateNormalMixtureExpectationMaximization.DataRow ? MathArrays.equals(this.row, ((MultivariateNormalMixtureExpectationMaximization.DataRow)other).row) : false;
         }
      }

      public int hashCode() {
         return Arrays.hashCode(this.row);
      }

      public double[] getRow() {
         return this.row;
      }
   }
}
