package org.apache.commons.math3.stat.clustering;

import java.util.Collection;

/** @deprecated */
@Deprecated
public interface Clusterable<T> {
   double distanceFrom(T var1);

   T centroidOf(Collection<T> var1);
}
