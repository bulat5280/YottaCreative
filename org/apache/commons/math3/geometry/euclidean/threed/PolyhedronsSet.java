package org.apache.commons.math3.geometry.euclidean.threed;

import java.awt.geom.AffineTransform;
import java.util.Collection;
import org.apache.commons.math3.geometry.Vector;
import org.apache.commons.math3.geometry.euclidean.oned.Euclidean1D;
import org.apache.commons.math3.geometry.euclidean.twod.Euclidean2D;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.apache.commons.math3.geometry.partitioning.AbstractRegion;
import org.apache.commons.math3.geometry.partitioning.BSPTree;
import org.apache.commons.math3.geometry.partitioning.BSPTreeVisitor;
import org.apache.commons.math3.geometry.partitioning.BoundaryAttribute;
import org.apache.commons.math3.geometry.partitioning.Hyperplane;
import org.apache.commons.math3.geometry.partitioning.Region;
import org.apache.commons.math3.geometry.partitioning.RegionFactory;
import org.apache.commons.math3.geometry.partitioning.SubHyperplane;
import org.apache.commons.math3.geometry.partitioning.Transform;
import org.apache.commons.math3.util.FastMath;

public class PolyhedronsSet extends AbstractRegion<Euclidean3D, Euclidean2D> {
   public PolyhedronsSet() {
   }

   public PolyhedronsSet(BSPTree<Euclidean3D> tree) {
      super(tree);
   }

   public PolyhedronsSet(Collection<SubHyperplane<Euclidean3D>> boundary) {
      super(boundary);
   }

   public PolyhedronsSet(double xMin, double xMax, double yMin, double yMax, double zMin, double zMax) {
      super(buildBoundary(xMin, xMax, yMin, yMax, zMin, zMax));
   }

   private static BSPTree<Euclidean3D> buildBoundary(double xMin, double xMax, double yMin, double yMax, double zMin, double zMax) {
      Plane pxMin = new Plane(new Vector3D(xMin, 0.0D, 0.0D), Vector3D.MINUS_I);
      Plane pxMax = new Plane(new Vector3D(xMax, 0.0D, 0.0D), Vector3D.PLUS_I);
      Plane pyMin = new Plane(new Vector3D(0.0D, yMin, 0.0D), Vector3D.MINUS_J);
      Plane pyMax = new Plane(new Vector3D(0.0D, yMax, 0.0D), Vector3D.PLUS_J);
      Plane pzMin = new Plane(new Vector3D(0.0D, 0.0D, zMin), Vector3D.MINUS_K);
      Plane pzMax = new Plane(new Vector3D(0.0D, 0.0D, zMax), Vector3D.PLUS_K);
      Region<Euclidean3D> boundary = (new RegionFactory()).buildConvex(pxMin, pxMax, pyMin, pyMax, pzMin, pzMax);
      return boundary.getTree(false);
   }

   public PolyhedronsSet buildNew(BSPTree<Euclidean3D> tree) {
      return new PolyhedronsSet(tree);
   }

   protected void computeGeometricalProperties() {
      this.getTree(true).visit(new PolyhedronsSet.FacetsContributionVisitor());
      if (this.getSize() < 0.0D) {
         this.setSize(Double.POSITIVE_INFINITY);
         this.setBarycenter(Vector3D.NaN);
      } else {
         this.setSize(this.getSize() / 3.0D);
         this.setBarycenter(new Vector3D(1.0D / (4.0D * this.getSize()), (Vector3D)this.getBarycenter()));
      }

   }

   public SubHyperplane<Euclidean3D> firstIntersection(Vector3D point, Line line) {
      return this.recurseFirstIntersection(this.getTree(true), point, line);
   }

   private SubHyperplane<Euclidean3D> recurseFirstIntersection(BSPTree<Euclidean3D> node, Vector3D point, Line line) {
      SubHyperplane<Euclidean3D> cut = node.getCut();
      if (cut == null) {
         return null;
      } else {
         BSPTree<Euclidean3D> minus = node.getMinus();
         BSPTree<Euclidean3D> plus = node.getPlus();
         Plane plane = (Plane)cut.getHyperplane();
         double offset = plane.getOffset((Vector)point);
         boolean in = FastMath.abs(offset) < 1.0E-10D;
         BSPTree near;
         BSPTree far;
         if (offset < 0.0D) {
            near = minus;
            far = plus;
         } else {
            near = plus;
            far = minus;
         }

         SubHyperplane crossed;
         if (in) {
            crossed = this.boundaryFacet(point, node);
            if (crossed != null) {
               return crossed;
            }
         }

         crossed = this.recurseFirstIntersection(near, point, line);
         if (crossed != null) {
            return crossed;
         } else {
            if (!in) {
               Vector3D hit3D = plane.intersection(line);
               if (hit3D != null) {
                  SubHyperplane<Euclidean3D> facet = this.boundaryFacet(hit3D, node);
                  if (facet != null) {
                     return facet;
                  }
               }
            }

            return this.recurseFirstIntersection(far, point, line);
         }
      }
   }

   private SubHyperplane<Euclidean3D> boundaryFacet(Vector3D point, BSPTree<Euclidean3D> node) {
      Vector2D point2D = ((Plane)node.getCut().getHyperplane()).toSubSpace(point);
      BoundaryAttribute<Euclidean3D> attribute = (BoundaryAttribute)node.getAttribute();
      if (attribute.getPlusOutside() != null && ((SubPlane)attribute.getPlusOutside()).getRemainingRegion().checkPoint(point2D) == Region.Location.INSIDE) {
         return attribute.getPlusOutside();
      } else {
         return attribute.getPlusInside() != null && ((SubPlane)attribute.getPlusInside()).getRemainingRegion().checkPoint(point2D) == Region.Location.INSIDE ? attribute.getPlusInside() : null;
      }
   }

   public PolyhedronsSet rotate(Vector3D center, Rotation rotation) {
      return (PolyhedronsSet)this.applyTransform(new PolyhedronsSet.RotationTransform(center, rotation));
   }

   public PolyhedronsSet translate(Vector3D translation) {
      return (PolyhedronsSet)this.applyTransform(new PolyhedronsSet.TranslationTransform(translation));
   }

   private static class TranslationTransform implements Transform<Euclidean3D, Euclidean2D> {
      private Vector3D translation;
      private Plane cachedOriginal;
      private Transform<Euclidean2D, Euclidean1D> cachedTransform;

      public TranslationTransform(Vector3D translation) {
         this.translation = translation;
      }

      public Vector3D apply(Vector<Euclidean3D> point) {
         return new Vector3D(1.0D, (Vector3D)point, 1.0D, this.translation);
      }

      public Plane apply(Hyperplane<Euclidean3D> hyperplane) {
         return ((Plane)hyperplane).translate(this.translation);
      }

      public SubHyperplane<Euclidean2D> apply(SubHyperplane<Euclidean2D> sub, Hyperplane<Euclidean3D> original, Hyperplane<Euclidean3D> transformed) {
         if (original != this.cachedOriginal) {
            Plane oPlane = (Plane)original;
            Plane tPlane = (Plane)transformed;
            Vector2D shift = tPlane.toSubSpace(this.apply((Vector)oPlane.getOrigin()));
            AffineTransform at = AffineTransform.getTranslateInstance(shift.getX(), shift.getY());
            this.cachedOriginal = (Plane)original;
            this.cachedTransform = org.apache.commons.math3.geometry.euclidean.twod.Line.getTransform(at);
         }

         return ((org.apache.commons.math3.geometry.euclidean.twod.SubLine)sub).applyTransform(this.cachedTransform);
      }
   }

   private static class RotationTransform implements Transform<Euclidean3D, Euclidean2D> {
      private Vector3D center;
      private Rotation rotation;
      private Plane cachedOriginal;
      private Transform<Euclidean2D, Euclidean1D> cachedTransform;

      public RotationTransform(Vector3D center, Rotation rotation) {
         this.center = center;
         this.rotation = rotation;
      }

      public Vector3D apply(Vector<Euclidean3D> point) {
         Vector3D delta = ((Vector3D)point).subtract(this.center);
         return new Vector3D(1.0D, this.center, 1.0D, this.rotation.applyTo(delta));
      }

      public Plane apply(Hyperplane<Euclidean3D> hyperplane) {
         return ((Plane)hyperplane).rotate(this.center, this.rotation);
      }

      public SubHyperplane<Euclidean2D> apply(SubHyperplane<Euclidean2D> sub, Hyperplane<Euclidean3D> original, Hyperplane<Euclidean3D> transformed) {
         if (original != this.cachedOriginal) {
            Plane oPlane = (Plane)original;
            Plane tPlane = (Plane)transformed;
            Vector3D p00 = oPlane.getOrigin();
            Vector3D p10 = oPlane.toSpace(new Vector2D(1.0D, 0.0D));
            Vector3D p01 = oPlane.toSpace(new Vector2D(0.0D, 1.0D));
            Vector2D tP00 = tPlane.toSubSpace(this.apply((Vector)p00));
            Vector2D tP10 = tPlane.toSubSpace(this.apply((Vector)p10));
            Vector2D tP01 = tPlane.toSubSpace(this.apply((Vector)p01));
            AffineTransform at = new AffineTransform(tP10.getX() - tP00.getX(), tP10.getY() - tP00.getY(), tP01.getX() - tP00.getX(), tP01.getY() - tP00.getY(), tP00.getX(), tP00.getY());
            this.cachedOriginal = (Plane)original;
            this.cachedTransform = org.apache.commons.math3.geometry.euclidean.twod.Line.getTransform(at);
         }

         return ((org.apache.commons.math3.geometry.euclidean.twod.SubLine)sub).applyTransform(this.cachedTransform);
      }
   }

   private class FacetsContributionVisitor implements BSPTreeVisitor<Euclidean3D> {
      public FacetsContributionVisitor() {
         PolyhedronsSet.this.setSize(0.0D);
         PolyhedronsSet.this.setBarycenter(new Vector3D(0.0D, 0.0D, 0.0D));
      }

      public BSPTreeVisitor.Order visitOrder(BSPTree<Euclidean3D> node) {
         return BSPTreeVisitor.Order.MINUS_SUB_PLUS;
      }

      public void visitInternalNode(BSPTree<Euclidean3D> node) {
         BoundaryAttribute<Euclidean3D> attribute = (BoundaryAttribute)node.getAttribute();
         if (attribute.getPlusOutside() != null) {
            this.addContribution(attribute.getPlusOutside(), false);
         }

         if (attribute.getPlusInside() != null) {
            this.addContribution(attribute.getPlusInside(), true);
         }

      }

      public void visitLeafNode(BSPTree<Euclidean3D> node) {
      }

      private void addContribution(SubHyperplane<Euclidean3D> facet, boolean reversed) {
         Region<Euclidean2D> polygon = ((SubPlane)facet).getRemainingRegion();
         double area = polygon.getSize();
         if (Double.isInfinite(area)) {
            PolyhedronsSet.this.setSize(Double.POSITIVE_INFINITY);
            PolyhedronsSet.this.setBarycenter(Vector3D.NaN);
         } else {
            Plane plane = (Plane)facet.getHyperplane();
            Vector3D facetB = plane.toSpace(polygon.getBarycenter());
            double scaled = area * facetB.dotProduct(plane.getNormal());
            if (reversed) {
               scaled = -scaled;
            }

            PolyhedronsSet.this.setSize(PolyhedronsSet.this.getSize() + scaled);
            PolyhedronsSet.this.setBarycenter(new Vector3D(1.0D, (Vector3D)PolyhedronsSet.this.getBarycenter(), scaled, facetB));
         }

      }
   }
}
