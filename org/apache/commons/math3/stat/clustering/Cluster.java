package org.apache.commons.math3.stat.clustering;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/** @deprecated */
@Deprecated
public class Cluster<T extends Clusterable<T>> implements Serializable {
   private static final long serialVersionUID = -3442297081515880464L;
   private final List<T> points;
   private final T center;

   public Cluster(T center) {
      this.center = center;
      this.points = new ArrayList();
   }

   public void addPoint(T point) {
      this.points.add(point);
   }

   public List<T> getPoints() {
      return this.points;
   }

   public T getCenter() {
      return this.center;
   }
}
