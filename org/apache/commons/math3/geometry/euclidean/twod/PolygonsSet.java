package org.apache.commons.math3.geometry.euclidean.twod;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.math3.exception.MathInternalError;
import org.apache.commons.math3.geometry.Vector;
import org.apache.commons.math3.geometry.euclidean.oned.Euclidean1D;
import org.apache.commons.math3.geometry.euclidean.oned.Interval;
import org.apache.commons.math3.geometry.euclidean.oned.IntervalsSet;
import org.apache.commons.math3.geometry.euclidean.oned.Vector1D;
import org.apache.commons.math3.geometry.partitioning.AbstractRegion;
import org.apache.commons.math3.geometry.partitioning.AbstractSubHyperplane;
import org.apache.commons.math3.geometry.partitioning.BSPTree;
import org.apache.commons.math3.geometry.partitioning.BSPTreeVisitor;
import org.apache.commons.math3.geometry.partitioning.BoundaryAttribute;
import org.apache.commons.math3.geometry.partitioning.Hyperplane;
import org.apache.commons.math3.geometry.partitioning.Side;
import org.apache.commons.math3.geometry.partitioning.SubHyperplane;
import org.apache.commons.math3.geometry.partitioning.utilities.AVLTree;
import org.apache.commons.math3.geometry.partitioning.utilities.OrderedTuple;
import org.apache.commons.math3.util.FastMath;

public class PolygonsSet extends AbstractRegion<Euclidean2D, Euclidean1D> {
   private Vector2D[][] vertices;

   public PolygonsSet() {
   }

   public PolygonsSet(BSPTree<Euclidean2D> tree) {
      super(tree);
   }

   public PolygonsSet(Collection<SubHyperplane<Euclidean2D>> boundary) {
      super(boundary);
   }

   public PolygonsSet(double xMin, double xMax, double yMin, double yMax) {
      super((Hyperplane[])boxBoundary(xMin, xMax, yMin, yMax));
   }

   public PolygonsSet(double hyperplaneThickness, Vector2D... vertices) {
      super(verticesToTree(hyperplaneThickness, vertices));
   }

   private static Line[] boxBoundary(double xMin, double xMax, double yMin, double yMax) {
      Vector2D minMin = new Vector2D(xMin, yMin);
      Vector2D minMax = new Vector2D(xMin, yMax);
      Vector2D maxMin = new Vector2D(xMax, yMin);
      Vector2D maxMax = new Vector2D(xMax, yMax);
      return new Line[]{new Line(minMin, maxMin), new Line(maxMin, maxMax), new Line(maxMax, minMax), new Line(minMax, minMin)};
   }

   private static BSPTree<Euclidean2D> verticesToTree(double hyperplaneThickness, Vector2D... vertices) {
      int n = vertices.length;
      if (n == 0) {
         return new BSPTree(Boolean.TRUE);
      } else {
         PolygonsSet.Vertex[] vArray = new PolygonsSet.Vertex[n];

         for(int i = 0; i < n; ++i) {
            vArray[i] = new PolygonsSet.Vertex(vertices[i]);
         }

         List<PolygonsSet.Edge> edges = new ArrayList();

         for(int i = 0; i < n; ++i) {
            PolygonsSet.Vertex start = vArray[i];
            PolygonsSet.Vertex end = vArray[(i + 1) % n];
            Line line = start.sharedLineWith(end);
            if (line == null) {
               line = new Line(start.getLocation(), end.getLocation());
            }

            edges.add(new PolygonsSet.Edge(start, end, line));
            PolygonsSet.Vertex[] arr$ = vArray;
            int len$ = vArray.length;

            for(int i$ = 0; i$ < len$; ++i$) {
               PolygonsSet.Vertex vertex = arr$[i$];
               if (vertex != start && vertex != end && FastMath.abs(line.getOffset((Vector)vertex.getLocation())) <= hyperplaneThickness) {
                  vertex.bindWith(line);
               }
            }
         }

         BSPTree<Euclidean2D> tree = new BSPTree();
         insertEdges(hyperplaneThickness, tree, edges);
         return tree;
      }
   }

   private static void insertEdges(double hyperplaneThickness, BSPTree<Euclidean2D> node, List<PolygonsSet.Edge> edges) {
      int index = 0;
      PolygonsSet.Edge inserted = null;

      while(inserted == null && index < edges.size()) {
         inserted = (PolygonsSet.Edge)edges.get(index++);
         if (inserted.getNode() == null) {
            if (node.insertCut(inserted.getLine())) {
               inserted.setNode(node);
            } else {
               inserted = null;
            }
         } else {
            inserted = null;
         }
      }

      if (inserted != null) {
         List<PolygonsSet.Edge> plusList = new ArrayList();
         List<PolygonsSet.Edge> minusList = new ArrayList();
         Iterator i$ = edges.iterator();

         while(i$.hasNext()) {
            PolygonsSet.Edge edge = (PolygonsSet.Edge)i$.next();
            if (edge != inserted) {
               double startOffset = inserted.getLine().getOffset((Vector)edge.getStart().getLocation());
               double endOffset = inserted.getLine().getOffset((Vector)edge.getEnd().getLocation());
               Side startSide = FastMath.abs(startOffset) <= hyperplaneThickness ? Side.HYPER : (startOffset < 0.0D ? Side.MINUS : Side.PLUS);
               Side endSide = FastMath.abs(endOffset) <= hyperplaneThickness ? Side.HYPER : (endOffset < 0.0D ? Side.MINUS : Side.PLUS);
               PolygonsSet.Vertex splitPoint;
               switch(startSide) {
               case PLUS:
                  if (endSide == Side.MINUS) {
                     splitPoint = edge.split(inserted.getLine());
                     minusList.add(splitPoint.getOutgoing());
                     plusList.add(splitPoint.getIncoming());
                  } else {
                     plusList.add(edge);
                  }
                  break;
               case MINUS:
                  if (endSide == Side.PLUS) {
                     splitPoint = edge.split(inserted.getLine());
                     minusList.add(splitPoint.getIncoming());
                     plusList.add(splitPoint.getOutgoing());
                  } else {
                     minusList.add(edge);
                  }
                  break;
               default:
                  if (endSide == Side.PLUS) {
                     plusList.add(edge);
                  } else if (endSide == Side.MINUS) {
                     minusList.add(edge);
                  }
               }
            }
         }

         if (!plusList.isEmpty()) {
            insertEdges(hyperplaneThickness, node.getPlus(), plusList);
         } else {
            node.getPlus().setAttribute(Boolean.FALSE);
         }

         if (!minusList.isEmpty()) {
            insertEdges(hyperplaneThickness, node.getMinus(), minusList);
         } else {
            node.getMinus().setAttribute(Boolean.TRUE);
         }

      } else {
         BSPTree<Euclidean2D> parent = node.getParent();
         if (parent != null && node != parent.getMinus()) {
            node.setAttribute(Boolean.FALSE);
         } else {
            node.setAttribute(Boolean.TRUE);
         }

      }
   }

   public PolygonsSet buildNew(BSPTree<Euclidean2D> tree) {
      return new PolygonsSet(tree);
   }

   protected void computeGeometricalProperties() {
      Vector2D[][] v = this.getVertices();
      if (v.length == 0) {
         BSPTree<Euclidean2D> tree = this.getTree(false);
         if (tree.getCut() == null && (Boolean)tree.getAttribute()) {
            this.setSize(Double.POSITIVE_INFINITY);
            this.setBarycenter(Vector2D.NaN);
         } else {
            this.setSize(0.0D);
            this.setBarycenter(new Vector2D(0.0D, 0.0D));
         }
      } else if (v[0][0] == null) {
         this.setSize(Double.POSITIVE_INFINITY);
         this.setBarycenter(Vector2D.NaN);
      } else {
         double sum = 0.0D;
         double sumX = 0.0D;
         double sumY = 0.0D;
         Vector2D[][] arr$ = v;
         int len$ = v.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            Vector2D[] loop = arr$[i$];
            double x1 = loop[loop.length - 1].getX();
            double y1 = loop[loop.length - 1].getY();
            Vector2D[] arr$ = loop;
            int len$ = loop.length;

            for(int i$ = 0; i$ < len$; ++i$) {
               Vector2D point = arr$[i$];
               double x0 = x1;
               double y0 = y1;
               x1 = point.getX();
               y1 = point.getY();
               double factor = x0 * y1 - y0 * x1;
               sum += factor;
               sumX += factor * (x0 + x1);
               sumY += factor * (y0 + y1);
            }
         }

         if (sum < 0.0D) {
            this.setSize(Double.POSITIVE_INFINITY);
            this.setBarycenter(Vector2D.NaN);
         } else {
            this.setSize(sum / 2.0D);
            this.setBarycenter(new Vector2D(sumX / (3.0D * sum), sumY / (3.0D * sum)));
         }
      }

   }

   public Vector2D[][] getVertices() {
      if (this.vertices == null) {
         if (this.getTree(false).getCut() == null) {
            this.vertices = new Vector2D[0][];
         } else {
            PolygonsSet.SegmentsBuilder visitor = new PolygonsSet.SegmentsBuilder();
            this.getTree(true).visit(visitor);
            AVLTree<PolygonsSet.ComparableSegment> sorted = visitor.getSorted();
            ArrayList loops = new ArrayList();

            while(!sorted.isEmpty()) {
               AVLTree<PolygonsSet.ComparableSegment>.Node node = sorted.getSmallest();
               List<PolygonsSet.ComparableSegment> loop = this.followLoop(node, sorted);
               if (loop != null) {
                  loops.add(loop);
               }
            }

            this.vertices = new Vector2D[loops.size()][];
            int i = 0;
            Iterator i$ = loops.iterator();

            while(true) {
               while(i$.hasNext()) {
                  List<PolygonsSet.ComparableSegment> loop = (List)i$.next();
                  if (loop.size() < 2) {
                     Line line = ((PolygonsSet.ComparableSegment)loop.get(0)).getLine();
                     this.vertices[i++] = new Vector2D[]{null, line.toSpace(new Vector1D(-3.4028234663852886E38D)), line.toSpace(new Vector1D(3.4028234663852886E38D))};
                  } else {
                     Vector2D[] array;
                     int j;
                     Iterator i$;
                     PolygonsSet.ComparableSegment segment;
                     if (((PolygonsSet.ComparableSegment)loop.get(0)).getStart() == null) {
                        array = new Vector2D[loop.size() + 2];
                        j = 0;
                        i$ = loop.iterator();

                        while(i$.hasNext()) {
                           segment = (PolygonsSet.ComparableSegment)i$.next();
                           double x;
                           if (j == 0) {
                              x = segment.getLine().toSubSpace(segment.getEnd()).getX();
                              x -= FastMath.max(1.0D, FastMath.abs(x / 2.0D));
                              array[j++] = null;
                              array[j++] = segment.getLine().toSpace(new Vector1D(x));
                           }

                           if (j < array.length - 1) {
                              array[j++] = segment.getEnd();
                           }

                           if (j == array.length - 1) {
                              x = segment.getLine().toSubSpace(segment.getStart()).getX();
                              x += FastMath.max(1.0D, FastMath.abs(x / 2.0D));
                              array[j++] = segment.getLine().toSpace(new Vector1D(x));
                           }
                        }

                        this.vertices[i++] = array;
                     } else {
                        array = new Vector2D[loop.size()];
                        j = 0;

                        for(i$ = loop.iterator(); i$.hasNext(); array[j++] = segment.getStart()) {
                           segment = (PolygonsSet.ComparableSegment)i$.next();
                        }

                        this.vertices[i++] = array;
                     }
                  }
               }

               return (Vector2D[][])this.vertices.clone();
            }
         }
      }

      return (Vector2D[][])this.vertices.clone();
   }

   private List<PolygonsSet.ComparableSegment> followLoop(AVLTree<PolygonsSet.ComparableSegment>.Node node, AVLTree<PolygonsSet.ComparableSegment> sorted) {
      ArrayList<PolygonsSet.ComparableSegment> loop = new ArrayList();
      PolygonsSet.ComparableSegment segment = (PolygonsSet.ComparableSegment)node.getElement();
      loop.add(segment);
      Vector2D globalStart = segment.getStart();
      Vector2D end = segment.getEnd();
      node.delete();
      boolean open = segment.getStart() == null;

      while(end != null && (open || globalStart.distance(end) > 1.0E-10D)) {
         AVLTree<PolygonsSet.ComparableSegment>.Node selectedNode = null;
         PolygonsSet.ComparableSegment selectedSegment = null;
         double selectedDistance = Double.POSITIVE_INFINITY;
         PolygonsSet.ComparableSegment lowerLeft = new PolygonsSet.ComparableSegment(end, -1.0E-10D, -1.0E-10D);
         PolygonsSet.ComparableSegment upperRight = new PolygonsSet.ComparableSegment(end, 1.0E-10D, 1.0E-10D);

         for(AVLTree.Node n = sorted.getNotSmaller(lowerLeft); n != null && ((PolygonsSet.ComparableSegment)n.getElement()).compareTo(upperRight) <= 0; n = n.getNext()) {
            segment = (PolygonsSet.ComparableSegment)n.getElement();
            double distance = end.distance(segment.getStart());
            if (distance < selectedDistance) {
               selectedNode = n;
               selectedSegment = segment;
               selectedDistance = distance;
            }
         }

         if (selectedDistance > 1.0E-10D) {
            return null;
         }

         end = selectedSegment.getEnd();
         loop.add(selectedSegment);
         selectedNode.delete();
      }

      if (loop.size() == 2 && !open) {
         return null;
      } else if (end == null && !open) {
         throw new MathInternalError();
      } else {
         return loop;
      }
   }

   private static class SegmentsBuilder implements BSPTreeVisitor<Euclidean2D> {
      private AVLTree<PolygonsSet.ComparableSegment> sorted = new AVLTree();

      public SegmentsBuilder() {
      }

      public BSPTreeVisitor.Order visitOrder(BSPTree<Euclidean2D> node) {
         return BSPTreeVisitor.Order.MINUS_SUB_PLUS;
      }

      public void visitInternalNode(BSPTree<Euclidean2D> node) {
         BoundaryAttribute<Euclidean2D> attribute = (BoundaryAttribute)node.getAttribute();
         if (attribute.getPlusOutside() != null) {
            this.addContribution(attribute.getPlusOutside(), false);
         }

         if (attribute.getPlusInside() != null) {
            this.addContribution(attribute.getPlusInside(), true);
         }

      }

      public void visitLeafNode(BSPTree<Euclidean2D> node) {
      }

      private void addContribution(SubHyperplane<Euclidean2D> sub, boolean reversed) {
         AbstractSubHyperplane<Euclidean2D, Euclidean1D> absSub = (AbstractSubHyperplane)sub;
         Line line = (Line)sub.getHyperplane();
         List<Interval> intervals = ((IntervalsSet)absSub.getRemainingRegion()).asList();
         Iterator i$ = intervals.iterator();

         while(i$.hasNext()) {
            Interval i = (Interval)i$.next();
            Vector2D start = Double.isInfinite(i.getInf()) ? null : line.toSpace(new Vector1D(i.getInf()));
            Vector2D end = Double.isInfinite(i.getSup()) ? null : line.toSpace(new Vector1D(i.getSup()));
            if (reversed) {
               this.sorted.insert(new PolygonsSet.ComparableSegment(end, start, line.getReverse()));
            } else {
               this.sorted.insert(new PolygonsSet.ComparableSegment(start, end, line));
            }
         }

      }

      public AVLTree<PolygonsSet.ComparableSegment> getSorted() {
         return this.sorted;
      }
   }

   private static class ComparableSegment extends Segment implements Comparable<PolygonsSet.ComparableSegment> {
      private OrderedTuple sortingKey;

      public ComparableSegment(Vector2D start, Vector2D end, Line line) {
         super(start, end, line);
         this.sortingKey = start == null ? new OrderedTuple(new double[]{Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY}) : new OrderedTuple(new double[]{start.getX(), start.getY()});
      }

      public ComparableSegment(Vector2D start, double dx, double dy) {
         super((Vector2D)null, (Vector2D)null, (Line)null);
         this.sortingKey = new OrderedTuple(new double[]{start.getX() + dx, start.getY() + dy});
      }

      public int compareTo(PolygonsSet.ComparableSegment o) {
         return this.sortingKey.compareTo(o.sortingKey);
      }

      public boolean equals(Object other) {
         if (this == other) {
            return true;
         } else if (other instanceof PolygonsSet.ComparableSegment) {
            return this.compareTo((PolygonsSet.ComparableSegment)other) == 0;
         } else {
            return false;
         }
      }

      public int hashCode() {
         return this.getStart().hashCode() ^ this.getEnd().hashCode() ^ this.getLine().hashCode() ^ this.sortingKey.hashCode();
      }
   }

   private static class Edge {
      private final PolygonsSet.Vertex start;
      private final PolygonsSet.Vertex end;
      private final Line line;
      private BSPTree<Euclidean2D> node;

      public Edge(PolygonsSet.Vertex start, PolygonsSet.Vertex end, Line line) {
         this.start = start;
         this.end = end;
         this.line = line;
         this.node = null;
         start.setOutgoing(this);
         end.setIncoming(this);
      }

      public PolygonsSet.Vertex getStart() {
         return this.start;
      }

      public PolygonsSet.Vertex getEnd() {
         return this.end;
      }

      public Line getLine() {
         return this.line;
      }

      public void setNode(BSPTree<Euclidean2D> node) {
         this.node = node;
      }

      public BSPTree<Euclidean2D> getNode() {
         return this.node;
      }

      public PolygonsSet.Vertex split(Line splitLine) {
         PolygonsSet.Vertex splitVertex = new PolygonsSet.Vertex(this.line.intersection(splitLine));
         splitVertex.bindWith(splitLine);
         PolygonsSet.Edge startHalf = new PolygonsSet.Edge(this.start, splitVertex, this.line);
         PolygonsSet.Edge endHalf = new PolygonsSet.Edge(splitVertex, this.end, this.line);
         startHalf.node = this.node;
         endHalf.node = this.node;
         return splitVertex;
      }
   }

   private static class Vertex {
      private final Vector2D location;
      private PolygonsSet.Edge incoming;
      private PolygonsSet.Edge outgoing;
      private final List<Line> lines;

      public Vertex(Vector2D location) {
         this.location = location;
         this.incoming = null;
         this.outgoing = null;
         this.lines = new ArrayList();
      }

      public Vector2D getLocation() {
         return this.location;
      }

      public void bindWith(Line line) {
         this.lines.add(line);
      }

      public Line sharedLineWith(PolygonsSet.Vertex vertex) {
         Iterator i$ = this.lines.iterator();

         while(i$.hasNext()) {
            Line line1 = (Line)i$.next();
            Iterator i$ = vertex.lines.iterator();

            while(i$.hasNext()) {
               Line line2 = (Line)i$.next();
               if (line1 == line2) {
                  return line1;
               }
            }
         }

         return null;
      }

      public void setIncoming(PolygonsSet.Edge incoming) {
         this.incoming = incoming;
         this.bindWith(incoming.getLine());
      }

      public PolygonsSet.Edge getIncoming() {
         return this.incoming;
      }

      public void setOutgoing(PolygonsSet.Edge outgoing) {
         this.outgoing = outgoing;
         this.bindWith(outgoing.getLine());
      }

      public PolygonsSet.Edge getOutgoing() {
         return this.outgoing;
      }
   }
}
