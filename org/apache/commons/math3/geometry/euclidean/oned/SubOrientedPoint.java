package org.apache.commons.math3.geometry.euclidean.oned;

import org.apache.commons.math3.geometry.partitioning.AbstractSubHyperplane;
import org.apache.commons.math3.geometry.partitioning.Hyperplane;
import org.apache.commons.math3.geometry.partitioning.Region;
import org.apache.commons.math3.geometry.partitioning.Side;
import org.apache.commons.math3.geometry.partitioning.SubHyperplane;

public class SubOrientedPoint extends AbstractSubHyperplane<Euclidean1D, Euclidean1D> {
   public SubOrientedPoint(Hyperplane<Euclidean1D> hyperplane, Region<Euclidean1D> remainingRegion) {
      super(hyperplane, remainingRegion);
   }

   public double getSize() {
      return 0.0D;
   }

   protected AbstractSubHyperplane<Euclidean1D, Euclidean1D> buildNew(Hyperplane<Euclidean1D> hyperplane, Region<Euclidean1D> remainingRegion) {
      return new SubOrientedPoint(hyperplane, remainingRegion);
   }

   public Side side(Hyperplane<Euclidean1D> hyperplane) {
      double global = hyperplane.getOffset(((OrientedPoint)this.getHyperplane()).getLocation());
      return global < -1.0E-10D ? Side.MINUS : (global > 1.0E-10D ? Side.PLUS : Side.HYPER);
   }

   public SubHyperplane.SplitSubHyperplane<Euclidean1D> split(Hyperplane<Euclidean1D> hyperplane) {
      double global = hyperplane.getOffset(((OrientedPoint)this.getHyperplane()).getLocation());
      return global < -1.0E-10D ? new SubHyperplane.SplitSubHyperplane((SubHyperplane)null, this) : new SubHyperplane.SplitSubHyperplane(this, (SubHyperplane)null);
   }
}
