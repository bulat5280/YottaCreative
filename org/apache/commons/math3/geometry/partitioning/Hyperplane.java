package org.apache.commons.math3.geometry.partitioning;

import org.apache.commons.math3.geometry.Space;
import org.apache.commons.math3.geometry.Vector;

public interface Hyperplane<S extends Space> {
   Hyperplane<S> copySelf();

   double getOffset(Vector<S> var1);

   boolean sameOrientationAs(Hyperplane<S> var1);

   SubHyperplane<S> wholeHyperplane();

   Region<S> wholeSpace();
}
