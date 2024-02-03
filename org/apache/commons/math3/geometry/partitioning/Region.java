package org.apache.commons.math3.geometry.partitioning;

import org.apache.commons.math3.geometry.Space;
import org.apache.commons.math3.geometry.Vector;

public interface Region<S extends Space> {
   Region<S> buildNew(BSPTree<S> var1);

   Region<S> copySelf();

   boolean isEmpty();

   boolean isEmpty(BSPTree<S> var1);

   boolean contains(Region<S> var1);

   Region.Location checkPoint(Vector<S> var1);

   BSPTree<S> getTree(boolean var1);

   double getBoundarySize();

   double getSize();

   Vector<S> getBarycenter();

   Side side(Hyperplane<S> var1);

   SubHyperplane<S> intersection(SubHyperplane<S> var1);

   public static enum Location {
      INSIDE,
      OUTSIDE,
      BOUNDARY;
   }
}
