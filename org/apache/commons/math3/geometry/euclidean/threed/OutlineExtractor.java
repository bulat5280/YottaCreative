package org.apache.commons.math3.geometry.euclidean.threed;

import java.util.ArrayList;
import org.apache.commons.math3.geometry.euclidean.twod.Euclidean2D;
import org.apache.commons.math3.geometry.euclidean.twod.PolygonsSet;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.apache.commons.math3.geometry.partitioning.AbstractSubHyperplane;
import org.apache.commons.math3.geometry.partitioning.BSPTree;
import org.apache.commons.math3.geometry.partitioning.BSPTreeVisitor;
import org.apache.commons.math3.geometry.partitioning.BoundaryAttribute;
import org.apache.commons.math3.geometry.partitioning.RegionFactory;
import org.apache.commons.math3.geometry.partitioning.SubHyperplane;
import org.apache.commons.math3.util.FastMath;

public class OutlineExtractor {
   private Vector3D u;
   private Vector3D v;
   private Vector3D w;

   public OutlineExtractor(Vector3D u, Vector3D v) {
      this.u = u;
      this.v = v;
      this.w = Vector3D.crossProduct(u, v);
   }

   public Vector2D[][] getOutline(PolyhedronsSet polyhedronsSet) {
      OutlineExtractor.BoundaryProjector projector = new OutlineExtractor.BoundaryProjector();
      polyhedronsSet.getTree(true).visit(projector);
      PolygonsSet projected = projector.getProjected();
      Vector2D[][] outline = projected.getVertices();

      for(int i = 0; i < outline.length; ++i) {
         Vector2D[] rawLoop = outline[i];
         int end = rawLoop.length;
         int j = 0;

         while(true) {
            while(j < end) {
               if (this.pointIsBetween(rawLoop, end, j)) {
                  for(int k = j; k < end - 1; ++k) {
                     rawLoop[k] = rawLoop[k + 1];
                  }

                  --end;
               } else {
                  ++j;
               }
            }

            if (end != rawLoop.length) {
               outline[i] = new Vector2D[end];
               System.arraycopy(rawLoop, 0, outline[i], 0, end);
            }
            break;
         }
      }

      return outline;
   }

   private boolean pointIsBetween(Vector2D[] loop, int n, int i) {
      Vector2D previous = loop[(i + n - 1) % n];
      Vector2D current = loop[i];
      Vector2D next = loop[(i + 1) % n];
      double dx1 = current.getX() - previous.getX();
      double dy1 = current.getY() - previous.getY();
      double dx2 = next.getX() - current.getX();
      double dy2 = next.getY() - current.getY();
      double cross = dx1 * dy2 - dx2 * dy1;
      double dot = dx1 * dx2 + dy1 * dy2;
      double d1d2 = FastMath.sqrt((dx1 * dx1 + dy1 * dy1) * (dx2 * dx2 + dy2 * dy2));
      return FastMath.abs(cross) <= 1.0E-6D * d1d2 && dot >= 0.0D;
   }

   private class BoundaryProjector implements BSPTreeVisitor<Euclidean3D> {
      private PolygonsSet projected;

      public BoundaryProjector() {
         this.projected = new PolygonsSet(new BSPTree(Boolean.FALSE));
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
         AbstractSubHyperplane<Euclidean3D, Euclidean2D> absFacet = (AbstractSubHyperplane)facet;
         Plane plane = (Plane)facet.getHyperplane();
         double scal = plane.getNormal().dotProduct(OutlineExtractor.this.w);
         if (FastMath.abs(scal) > 0.001D) {
            Vector2D[][] vertices = ((PolygonsSet)absFacet.getRemainingRegion()).getVertices();
            if (scal < 0.0D ^ reversed) {
               Vector2D[][] newVertices = new Vector2D[vertices.length][];
               int i = 0;

               while(true) {
                  if (i >= vertices.length) {
                     vertices = newVertices;
                     break;
                  }

                  Vector2D[] loopx = vertices[i];
                  Vector2D[] newLoop = new Vector2D[loopx.length];
                  int j;
                  if (loopx[0] == null) {
                     newLoop[0] = null;

                     for(j = 1; j < loopx.length; ++j) {
                        newLoop[j] = loopx[loopx.length - j];
                     }
                  } else {
                     for(j = 0; j < loopx.length; ++j) {
                        newLoop[j] = loopx[loopx.length - (j + 1)];
                     }
                  }

                  newVertices[i] = newLoop;
                  ++i;
               }
            }

            ArrayList<SubHyperplane<Euclidean2D>> edges = new ArrayList();
            Vector2D[][] arr$ = vertices;
            int len$ = vertices.length;

            for(int i$ = 0; i$ < len$; ++i$) {
               Vector2D[] loop = arr$[i$];
               boolean closed = loop[0] != null;
               int previous = closed ? loop.length - 1 : 1;
               Vector3D previous3D = plane.toSpace(loop[previous]);
               int current = (previous + 1) % loop.length;

               Vector2D cPoint;
               for(Vector2D pPoint = new Vector2D(previous3D.dotProduct(OutlineExtractor.this.u), previous3D.dotProduct(OutlineExtractor.this.v)); current < loop.length; pPoint = cPoint) {
                  Vector3D current3D = plane.toSpace(loop[current]);
                  cPoint = new Vector2D(current3D.dotProduct(OutlineExtractor.this.u), current3D.dotProduct(OutlineExtractor.this.v));
                  org.apache.commons.math3.geometry.euclidean.twod.Line line = new org.apache.commons.math3.geometry.euclidean.twod.Line(pPoint, cPoint);
                  SubHyperplane<Euclidean2D> edge = line.wholeHyperplane();
                  double angle;
                  org.apache.commons.math3.geometry.euclidean.twod.Line l;
                  if (closed || previous != 1) {
                     angle = line.getAngle() + 1.5707963267948966D;
                     l = new org.apache.commons.math3.geometry.euclidean.twod.Line(pPoint, angle);
                     edge = ((SubHyperplane)edge).split(l).getPlus();
                  }

                  if (closed || current != loop.length - 1) {
                     angle = line.getAngle() + 1.5707963267948966D;
                     l = new org.apache.commons.math3.geometry.euclidean.twod.Line(cPoint, angle);
                     edge = ((SubHyperplane)edge).split(l).getMinus();
                  }

                  edges.add(edge);
                  previous = current++;
               }
            }

            PolygonsSet projectedFacet = new PolygonsSet(edges);
            this.projected = (PolygonsSet)(new RegionFactory()).union(this.projected, projectedFacet);
         }

      }

      public PolygonsSet getProjected() {
         return this.projected;
      }
   }
}
