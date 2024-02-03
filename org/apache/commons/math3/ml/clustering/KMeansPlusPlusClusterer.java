package org.apache.commons.math3.ml.clustering;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.math3.exception.ConvergenceException;
import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.exception.NumberIsTooSmallException;
import org.apache.commons.math3.exception.util.LocalizedFormats;
import org.apache.commons.math3.ml.distance.DistanceMeasure;
import org.apache.commons.math3.ml.distance.EuclideanDistance;
import org.apache.commons.math3.random.JDKRandomGenerator;
import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.stat.descriptive.moment.Variance;
import org.apache.commons.math3.util.MathUtils;

public class KMeansPlusPlusClusterer<T extends Clusterable> extends Clusterer<T> {
   private final int k;
   private final int maxIterations;
   private final RandomGenerator random;
   private final KMeansPlusPlusClusterer.EmptyClusterStrategy emptyStrategy;

   public KMeansPlusPlusClusterer(int k) {
      this(k, -1);
   }

   public KMeansPlusPlusClusterer(int k, int maxIterations) {
      this(k, maxIterations, new EuclideanDistance());
   }

   public KMeansPlusPlusClusterer(int k, int maxIterations, DistanceMeasure measure) {
      this(k, maxIterations, measure, new JDKRandomGenerator());
   }

   public KMeansPlusPlusClusterer(int k, int maxIterations, DistanceMeasure measure, RandomGenerator random) {
      this(k, maxIterations, measure, random, KMeansPlusPlusClusterer.EmptyClusterStrategy.LARGEST_VARIANCE);
   }

   public KMeansPlusPlusClusterer(int k, int maxIterations, DistanceMeasure measure, RandomGenerator random, KMeansPlusPlusClusterer.EmptyClusterStrategy emptyStrategy) {
      super(measure);
      this.k = k;
      this.maxIterations = maxIterations;
      this.random = random;
      this.emptyStrategy = emptyStrategy;
   }

   public int getK() {
      return this.k;
   }

   public int getMaxIterations() {
      return this.maxIterations;
   }

   public RandomGenerator getRandomGenerator() {
      return this.random;
   }

   public KMeansPlusPlusClusterer.EmptyClusterStrategy getEmptyClusterStrategy() {
      return this.emptyStrategy;
   }

   public List<CentroidCluster<T>> cluster(Collection<T> points) throws MathIllegalArgumentException, ConvergenceException {
      MathUtils.checkNotNull(points);
      if (points.size() < this.k) {
         throw new NumberIsTooSmallException(points.size(), this.k, false);
      } else {
         List<CentroidCluster<T>> clusters = this.chooseInitialCenters(points);
         int[] assignments = new int[points.size()];
         this.assignPointsToClusters((List)clusters, points, assignments);
         int max = this.maxIterations < 0 ? Integer.MAX_VALUE : this.maxIterations;

         for(int count = 0; count < max; ++count) {
            boolean emptyCluster = false;
            List<CentroidCluster<T>> newClusters = new ArrayList();

            Clusterable newCenter;
            for(Iterator i$ = ((List)clusters).iterator(); i$.hasNext(); newClusters.add(new CentroidCluster(newCenter))) {
               CentroidCluster<T> cluster = (CentroidCluster)i$.next();
               if (cluster.getPoints().isEmpty()) {
                  switch(this.emptyStrategy) {
                  case LARGEST_VARIANCE:
                     newCenter = this.getPointFromLargestVarianceCluster((Collection)clusters);
                     break;
                  case LARGEST_POINTS_NUMBER:
                     newCenter = this.getPointFromLargestNumberCluster((Collection)clusters);
                     break;
                  case FARTHEST_POINT:
                     newCenter = this.getFarthestPoint((Collection)clusters);
                     break;
                  default:
                     throw new ConvergenceException(LocalizedFormats.EMPTY_CLUSTER_IN_K_MEANS, new Object[0]);
                  }

                  emptyCluster = true;
               } else {
                  newCenter = this.centroidOf(cluster.getPoints(), cluster.getCenter().getPoint().length);
               }
            }

            int changes = this.assignPointsToClusters(newClusters, points, assignments);
            clusters = newClusters;
            if (changes == 0 && !emptyCluster) {
               return newClusters;
            }
         }

         return (List)clusters;
      }
   }

   private int assignPointsToClusters(List<CentroidCluster<T>> clusters, Collection<T> points, int[] assignments) {
      int assignedDifferently = 0;
      int pointIndex = 0;

      int clusterIndex;
      for(Iterator i$ = points.iterator(); i$.hasNext(); assignments[pointIndex++] = clusterIndex) {
         T p = (Clusterable)i$.next();
         clusterIndex = this.getNearestCluster(clusters, p);
         if (clusterIndex != assignments[pointIndex]) {
            ++assignedDifferently;
         }

         CentroidCluster<T> cluster = (CentroidCluster)clusters.get(clusterIndex);
         cluster.addPoint(p);
      }

      return assignedDifferently;
   }

   private List<CentroidCluster<T>> chooseInitialCenters(Collection<T> points) {
      List<T> pointList = Collections.unmodifiableList(new ArrayList(points));
      int numPoints = pointList.size();
      boolean[] taken = new boolean[numPoints];
      List<CentroidCluster<T>> resultSet = new ArrayList();
      int firstPointIndex = this.random.nextInt(numPoints);
      T firstPoint = (Clusterable)pointList.get(firstPointIndex);
      resultSet.add(new CentroidCluster(firstPoint));
      taken[firstPointIndex] = true;
      double[] minDistSquared = new double[numPoints];

      for(int i = 0; i < numPoints; ++i) {
         if (i != firstPointIndex) {
            double d = this.distance(firstPoint, (Clusterable)pointList.get(i));
            minDistSquared[i] = d * d;
         }
      }

      while(resultSet.size() < this.k) {
         double distSqSum = 0.0D;

         for(int i = 0; i < numPoints; ++i) {
            if (!taken[i]) {
               distSqSum += minDistSquared[i];
            }
         }

         double r = this.random.nextDouble() * distSqSum;
         int nextPointIndex = -1;
         double sum = 0.0D;

         int i;
         for(i = 0; i < numPoints; ++i) {
            if (!taken[i]) {
               sum += minDistSquared[i];
               if (sum >= r) {
                  nextPointIndex = i;
                  break;
               }
            }
         }

         if (nextPointIndex == -1) {
            for(i = numPoints - 1; i >= 0; --i) {
               if (!taken[i]) {
                  nextPointIndex = i;
                  break;
               }
            }
         }

         if (nextPointIndex < 0) {
            break;
         }

         T p = (Clusterable)pointList.get(nextPointIndex);
         resultSet.add(new CentroidCluster(p));
         taken[nextPointIndex] = true;
         if (resultSet.size() < this.k) {
            for(int j = 0; j < numPoints; ++j) {
               if (!taken[j]) {
                  double d = this.distance(p, (Clusterable)pointList.get(j));
                  double d2 = d * d;
                  if (d2 < minDistSquared[j]) {
                     minDistSquared[j] = d2;
                  }
               }
            }
         }
      }

      return resultSet;
   }

   private T getPointFromLargestVarianceCluster(Collection<CentroidCluster<T>> clusters) throws ConvergenceException {
      double maxVariance = Double.NEGATIVE_INFINITY;
      Cluster<T> selected = null;
      Iterator i$ = clusters.iterator();

      while(true) {
         CentroidCluster cluster;
         do {
            if (!i$.hasNext()) {
               if (selected == null) {
                  throw new ConvergenceException(LocalizedFormats.EMPTY_CLUSTER_IN_K_MEANS, new Object[0]);
               }

               List<T> selectedPoints = selected.getPoints();
               return (Clusterable)selectedPoints.remove(this.random.nextInt(selectedPoints.size()));
            }

            cluster = (CentroidCluster)i$.next();
         } while(cluster.getPoints().isEmpty());

         Clusterable center = cluster.getCenter();
         Variance stat = new Variance();
         Iterator i$ = cluster.getPoints().iterator();

         while(i$.hasNext()) {
            T point = (Clusterable)i$.next();
            stat.increment(this.distance(point, center));
         }

         double variance = stat.getResult();
         if (variance > maxVariance) {
            maxVariance = variance;
            selected = cluster;
         }
      }
   }

   private T getPointFromLargestNumberCluster(Collection<? extends Cluster<T>> clusters) throws ConvergenceException {
      int maxNumber = 0;
      Cluster<T> selected = null;
      Iterator i$ = clusters.iterator();

      while(i$.hasNext()) {
         Cluster<T> cluster = (Cluster)i$.next();
         int number = cluster.getPoints().size();
         if (number > maxNumber) {
            maxNumber = number;
            selected = cluster;
         }
      }

      if (selected == null) {
         throw new ConvergenceException(LocalizedFormats.EMPTY_CLUSTER_IN_K_MEANS, new Object[0]);
      } else {
         List<T> selectedPoints = selected.getPoints();
         return (Clusterable)selectedPoints.remove(this.random.nextInt(selectedPoints.size()));
      }
   }

   private T getFarthestPoint(Collection<CentroidCluster<T>> clusters) throws ConvergenceException {
      double maxDistance = Double.NEGATIVE_INFINITY;
      Cluster<T> selectedCluster = null;
      int selectedPoint = -1;
      Iterator i$ = clusters.iterator();

      while(i$.hasNext()) {
         CentroidCluster<T> cluster = (CentroidCluster)i$.next();
         Clusterable center = cluster.getCenter();
         List<T> points = cluster.getPoints();

         for(int i = 0; i < points.size(); ++i) {
            double distance = this.distance((Clusterable)points.get(i), center);
            if (distance > maxDistance) {
               maxDistance = distance;
               selectedCluster = cluster;
               selectedPoint = i;
            }
         }
      }

      if (selectedCluster == null) {
         throw new ConvergenceException(LocalizedFormats.EMPTY_CLUSTER_IN_K_MEANS, new Object[0]);
      } else {
         return (Clusterable)selectedCluster.getPoints().remove(selectedPoint);
      }
   }

   private int getNearestCluster(Collection<CentroidCluster<T>> clusters, T point) {
      double minDistance = Double.MAX_VALUE;
      int clusterIndex = 0;
      int minCluster = 0;

      for(Iterator i$ = clusters.iterator(); i$.hasNext(); ++clusterIndex) {
         CentroidCluster<T> c = (CentroidCluster)i$.next();
         double distance = this.distance(point, c.getCenter());
         if (distance < minDistance) {
            minDistance = distance;
            minCluster = clusterIndex;
         }
      }

      return minCluster;
   }

   private Clusterable centroidOf(Collection<T> points, int dimension) {
      double[] centroid = new double[dimension];
      Iterator i$ = points.iterator();

      while(i$.hasNext()) {
         T p = (Clusterable)i$.next();
         double[] point = p.getPoint();

         for(int i = 0; i < centroid.length; ++i) {
            centroid[i] += point[i];
         }
      }

      for(int i = 0; i < centroid.length; ++i) {
         centroid[i] /= (double)points.size();
      }

      return new DoublePoint(centroid);
   }

   public static enum EmptyClusterStrategy {
      LARGEST_VARIANCE,
      LARGEST_POINTS_NUMBER,
      FARTHEST_POINT,
      ERROR;
   }
}
