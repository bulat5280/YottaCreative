package org.apache.commons.math3.stat.clustering;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import org.apache.commons.math3.util.MathArrays;

/** @deprecated */
@Deprecated
public class EuclideanIntegerPoint implements Clusterable<EuclideanIntegerPoint>, Serializable {
   private static final long serialVersionUID = 3946024775784901369L;
   private final int[] point;

   public EuclideanIntegerPoint(int[] point) {
      this.point = point;
   }

   public int[] getPoint() {
      return this.point;
   }

   public double distanceFrom(EuclideanIntegerPoint p) {
      return MathArrays.distance(this.point, p.getPoint());
   }

   public EuclideanIntegerPoint centroidOf(Collection<EuclideanIntegerPoint> points) {
      int[] centroid = new int[this.getPoint().length];
      Iterator i$ = points.iterator();

      while(i$.hasNext()) {
         EuclideanIntegerPoint p = (EuclideanIntegerPoint)i$.next();

         for(int i = 0; i < centroid.length; ++i) {
            centroid[i] += p.getPoint()[i];
         }
      }

      for(int i = 0; i < centroid.length; ++i) {
         centroid[i] /= points.size();
      }

      return new EuclideanIntegerPoint(centroid);
   }

   public boolean equals(Object other) {
      return !(other instanceof EuclideanIntegerPoint) ? false : Arrays.equals(this.point, ((EuclideanIntegerPoint)other).point);
   }

   public int hashCode() {
      return Arrays.hashCode(this.point);
   }

   public String toString() {
      return Arrays.toString(this.point);
   }
}
