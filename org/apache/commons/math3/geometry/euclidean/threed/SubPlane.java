package org.apache.commons.math3.geometry.euclidean.threed;

import org.apache.commons.math3.geometry.euclidean.oned.Vector1D;
import org.apache.commons.math3.geometry.euclidean.twod.Euclidean2D;
import org.apache.commons.math3.geometry.euclidean.twod.PolygonsSet;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.apache.commons.math3.geometry.partitioning.AbstractSubHyperplane;
import org.apache.commons.math3.geometry.partitioning.BSPTree;
import org.apache.commons.math3.geometry.partitioning.Hyperplane;
import org.apache.commons.math3.geometry.partitioning.Region;
import org.apache.commons.math3.geometry.partitioning.Side;
import org.apache.commons.math3.geometry.partitioning.SubHyperplane;

public class SubPlane extends AbstractSubHyperplane<Euclidean3D, Euclidean2D> {
   public SubPlane(Hyperplane<Euclidean3D> hyperplane, Region<Euclidean2D> remainingRegion) {
      super(hyperplane, remainingRegion);
   }

   protected AbstractSubHyperplane<Euclidean3D, Euclidean2D> buildNew(Hyperplane<Euclidean3D> hyperplane, Region<Euclidean2D> remainingRegion) {
      return new SubPlane(hyperplane, remainingRegion);
   }

   public Side side(Hyperplane<Euclidean3D> hyperplane) {
      Plane otherPlane = (Plane)hyperplane;
      Plane thisPlane = (Plane)this.getHyperplane();
      Line inter = otherPlane.intersection(thisPlane);
      if (inter == null) {
         double global = otherPlane.getOffset(thisPlane);
         return global < -1.0E-10D ? Side.MINUS : (global > 1.0E-10D ? Side.PLUS : Side.HYPER);
      } else {
         Vector2D p = thisPlane.toSubSpace(inter.toSpace(Vector1D.ZERO));
         Vector2D q = thisPlane.toSubSpace(inter.toSpace(Vector1D.ONE));
         Vector3D crossP = Vector3D.crossProduct(inter.getDirection(), thisPlane.getNormal());
         if (crossP.dotProduct(otherPlane.getNormal()) < 0.0D) {
            Vector2D tmp = p;
            p = q;
            q = tmp;
         }

         org.apache.commons.math3.geometry.euclidean.twod.Line line2D = new org.apache.commons.math3.geometry.euclidean.twod.Line(p, q);
         return this.getRemainingRegion().side(line2D);
      }
   }

   public SubHyperplane.SplitSubHyperplane<Euclidean3D> split(Hyperplane<Euclidean3D> hyperplane) {
      Plane otherPlane = (Plane)hyperplane;
      Plane thisPlane = (Plane)this.getHyperplane();
      Line inter = otherPlane.intersection(thisPlane);
      if (inter == null) {
         double global = otherPlane.getOffset(thisPlane);
         return global < -1.0E-10D ? new SubHyperplane.SplitSubHyperplane((SubHyperplane)null, this) : new SubHyperplane.SplitSubHyperplane(this, (SubHyperplane)null);
      } else {
         Vector2D p = thisPlane.toSubSpace(inter.toSpace(Vector1D.ZERO));
         Vector2D q = thisPlane.toSubSpace(inter.toSpace(Vector1D.ONE));
         Vector3D crossP = Vector3D.crossProduct(inter.getDirection(), thisPlane.getNormal());
         if (crossP.dotProduct(otherPlane.getNormal()) < 0.0D) {
            Vector2D tmp = p;
            p = q;
            q = tmp;
         }

         SubHyperplane<Euclidean2D> l2DMinus = (new org.apache.commons.math3.geometry.euclidean.twod.Line(p, q)).wholeHyperplane();
         SubHyperplane<Euclidean2D> l2DPlus = (new org.apache.commons.math3.geometry.euclidean.twod.Line(q, p)).wholeHyperplane();
         BSPTree<Euclidean2D> splitTree = this.getRemainingRegion().getTree(false).split(l2DMinus);
         BSPTree<Euclidean2D> plusTree = this.getRemainingRegion().isEmpty(splitTree.getPlus()) ? new BSPTree(Boolean.FALSE) : new BSPTree(l2DPlus, new BSPTree(Boolean.FALSE), splitTree.getPlus(), (Object)null);
         BSPTree<Euclidean2D> minusTree = this.getRemainingRegion().isEmpty(splitTree.getMinus()) ? new BSPTree(Boolean.FALSE) : new BSPTree(l2DMinus, new BSPTree(Boolean.FALSE), splitTree.getMinus(), (Object)null);
         return new SubHyperplane.SplitSubHyperplane(new SubPlane(thisPlane.copySelf(), new PolygonsSet(plusTree)), new SubPlane(thisPlane.copySelf(), new PolygonsSet(minusTree)));
      }
   }
}
