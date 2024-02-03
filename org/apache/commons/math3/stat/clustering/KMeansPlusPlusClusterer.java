package org.apache.commons.math3.stat.clustering;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import org.apache.commons.math3.exception.ConvergenceException;
import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.exception.NumberIsTooSmallException;
import org.apache.commons.math3.exception.util.LocalizedFormats;
import org.apache.commons.math3.stat.descriptive.moment.Variance;
import org.apache.commons.math3.util.MathUtils;

/** @deprecated */
@Deprecated
public class KMeansPlusPlusClusterer<T extends Clusterable<T>> {
   private final Random random;
   private final KMeansPlusPlusClusterer.EmptyClusterStrategy emptyStrategy;

   public KMeansPlusPlusClusterer(Random random) {
      this(random, KMeansPlusPlusClusterer.EmptyClusterStrategy.LARGEST_VARIANCE);
   }

   public KMeansPlusPlusClusterer(Random random, KMeansPlusPlusClusterer.EmptyClusterStrategy emptyStrategy) {
      this.random = random;
      this.emptyStrategy = emptyStrategy;
   }

   public List<Cluster<T>> cluster(Collection<T> points, int k, int numTrials, int maxIterationsPerTrial) throws MathIllegalArgumentException, ConvergenceException {
      List<Cluster<T>> best = null;
      double bestVarianceSum = Double.POSITIVE_INFINITY;

      label38:
      for(int i = 0; i < numTrials; ++i) {
         List<Cluster<T>> clusters = this.cluster(points, k, maxIterationsPerTrial);
         double varianceSum = 0.0D;
         Iterator i$ = clusters.iterator();

         while(true) {
            Cluster cluster;
            do {
               if (!i$.hasNext()) {
                  if (varianceSum <= bestVarianceSum) {
                     best = clusters;
                     bestVarianceSum = varianceSum;
                  }
                  continue label38;
               }

               cluster = (Cluster)i$.next();
            } while(cluster.getPoints().isEmpty());

            T center = cluster.getCenter();
            Variance stat = new Variance();
            Iterator i$ = cluster.getPoints().iterator();

            while(i$.hasNext()) {
               T point = (Clusterable)i$.next();
               stat.increment(point.distanceFrom(center));
            }

            varianceSum += stat.getResult();
         }
      }

      return best;
   }

   public List<Cluster<T>> cluster(Collection<T> points, int k, int maxIterations) throws MathIllegalArgumentException, ConvergenceException {
      MathUtils.checkNotNull(points);
      if (points.size() < k) {
         throw new NumberIsTooSmallException(points.size(), k, false);
      } else {
         List<Cluster<T>> clusters = chooseInitialCenters(points, k, this.random);
         int[] assignments = new int[points.size()];
         assignPointsToClusters((List)clusters, points, assignments);
         int max = maxIterations < 0 ? Integer.MAX_VALUE : maxIterations;

         for(int count = 0; count < max; ++count) {
            boolean emptyCluster = false;
            List<Cluster<T>> newClusters = new ArrayList();

            Clusterable newCenter;
            for(Iterator i$ = ((List)clusters).iterator(); i$.hasNext(); newClusters.add(new Cluster(newCenter))) {
               Cluster<T> cluster = (Cluster)i$.next();
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
                  newCenter = (Clusterable)cluster.getCenter().centroidOf(cluster.getPoints());
               }
            }

            int changes = assignPointsToClusters(newClusters, points, assignments);
            clusters = newClusters;
            if (changes == 0 && !emptyCluster) {
               return newClusters;
            }
         }

         return (List)clusters;
      }
   }

   private static <T extends Clusterable<T>> int assignPointsToClusters(List<Cluster<T>> clusters, Collection<T> points, int[] assignments) {
      int assignedDifferently = 0;
      int pointIndex = 0;

      int clusterIndex;
      for(Iterator i$ = points.iterator(); i$.hasNext(); assignments[pointIndex++] = clusterIndex) {
         T p = (Clusterable)i$.next();
         clusterIndex = getNearestCluster(clusters, p);
         if (clusterIndex != assignments[pointIndex]) {
            ++assignedDifferently;
         }

         Cluster<T> cluster = (Cluster)clusters.get(clusterIndex);
         cluster.addPoint(p);
      }

      return assignedDifferently;
   }

   private static <T extends Clusterable<T>> List<Cluster<T>> chooseInitialCenters(Collection<T> points, int k, Random random) {
      List<T> pointList = Collections.unmodifiableList(new ArrayList(points));
      int numPoints = pointList.size();
      boolean[] taken = new boolean[numPoints];
      List<Cluster<T>> resultSet = new ArrayList();
      int firstPointIndex = random.nextInt(numPoints);
      T firstPoint = (Clusterable)pointList.get(firstPointIndex);
      resultSet.add(new Cluster(firstPoint));
      taken[firstPointIndex] = true;
      double[] minDistSquared = new double[numPoints];

      for(int i = 0; i < numPoints; ++i) {
         if (i != firstPointIndex) {
            double d = firstPoint.distanceFrom(pointList.get(i));
            minDistSquared[i] = d * d;
         }
      }

      while(resultSet.size() < k) {
         double distSqSum = 0.0D;

         for(int i = 0; i < numPoints; ++i) {
            if (!taken[i]) {
               distSqSum += minDistSquared[i];
            }
         }

         double r = random.nextDouble() * distSqSum;
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
         resultSet.add(new Cluster(p));
         taken[nextPointIndex] = true;
         if (resultSet.size() < k) {
            for(int j = 0; j < numPoints; ++j) {
               if (!taken[j]) {
                  double d = p.distanceFrom(pointList.get(j));
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

   private T getPointFromLargestVarianceCluster(Collection<Cluster<T>> clusters) throws ConvergenceException {
      double maxVariance = Double.NEGATIVE_INFINITY;
      Cluster<T> selected = null;
      Iterator i$ = clusters.iterator();

      while(true) {
         Cluster cluster;
         do {
            if (!i$.hasNext()) {
               if (selected == null) {
                  throw new ConvergenceException(LocalizedFormats.EMPTY_CLUSTER_IN_K_MEANS, new Object[0]);
               }

               List<T> selectedPoints = selected.getPoints();
               return (Clusterable)selectedPoints.remove(this.random.nextInt(selectedPoints.size()));
            }

            cluster = (Cluster)i$.next();
         } while(cluster.getPoints().isEmpty());

         T center = cluster.getCenter();
         Variance stat = new Variance();
         Iterator i$ = cluster.getPoints().iterator();

         while(i$.hasNext()) {
            T point = (Clusterable)i$.next();
            stat.increment(point.distanceFrom(center));
         }

         double variance = stat.getResult();
         if (variance > maxVariance) {
            maxVariance = variance;
            selected = cluster;
         }
      }
   }

   private T getPointFromLargestNumberCluster(Collection<Cluster<T>> clusters) throws ConvergenceException {
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

   private T getFarthestPoint(Collection<Cluster<T>> clusters) throws ConvergenceException {
      double maxDistance = Double.NEGATIVE_INFINITY;
      Cluster<T> selectedCluster = null;
      int selectedPoint = -1;
      Iterator i$ = clusters.iterator();

      while(i$.hasNext()) {
         Cluster<T> cluster = (Cluster)i$.next();
         T center = cluster.getCenter();
         List<T> points = cluster.getPoints();

         for(int i = 0; i < points.size(); ++i) {
            double distance = ((Clusterable)points.get(i)).distanceFrom(center);
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

   private static <T extends Clusterable<T>> int getNearestCluster(Collection<Cluster<T>> clusters, T point) {
      double minDistance = Double.MAX_VALUE;
      int clusterIndex = 0;
      int minCluster = 0;

      for(Iterator i$ = clusters.iterator(); i$.hasNext(); ++clusterIndex) {
         Cluster<T> c = (Cluster)i$.next();
         double distance = point.distanceFrom(c.getCenter());
         if (distance < minDistance) {
            minDistance = distance;
            minCluster = clusterIndex;
         }
      }

      return minCluster;
   }

   public static enum EmptyClusterStrategy {
      LARGEST_VARIANCE,
      LARGEST_POINTS_NUMBER,
      FARTHEST_POINT,
      ERROR;
   }
}
