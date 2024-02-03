package org.apache.commons.math3.ml.clustering;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.math3.exception.ConvergenceException;
import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.stat.descriptive.moment.Variance;

public class MultiKMeansPlusPlusClusterer<T extends Clusterable> extends Clusterer<T> {
   private final KMeansPlusPlusClusterer<T> clusterer;
   private final int numTrials;

   public MultiKMeansPlusPlusClusterer(KMeansPlusPlusClusterer<T> clusterer, int numTrials) {
      super(clusterer.getDistanceMeasure());
      this.clusterer = clusterer;
      this.numTrials = numTrials;
   }

   public KMeansPlusPlusClusterer<T> getClusterer() {
      return this.clusterer;
   }

   public int getNumTrials() {
      return this.numTrials;
   }

   public List<CentroidCluster<T>> cluster(Collection<T> points) throws MathIllegalArgumentException, ConvergenceException {
      List<CentroidCluster<T>> best = null;
      double bestVarianceSum = Double.POSITIVE_INFINITY;

      label38:
      for(int i = 0; i < this.numTrials; ++i) {
         List<CentroidCluster<T>> clusters = this.clusterer.cluster(points);
         double varianceSum = 0.0D;
         Iterator i$ = clusters.iterator();

         while(true) {
            CentroidCluster cluster;
            do {
               if (!i$.hasNext()) {
                  if (varianceSum <= bestVarianceSum) {
                     best = clusters;
                     bestVarianceSum = varianceSum;
                  }
                  continue label38;
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

            varianceSum += stat.getResult();
         }
      }

      return best;
   }
}
