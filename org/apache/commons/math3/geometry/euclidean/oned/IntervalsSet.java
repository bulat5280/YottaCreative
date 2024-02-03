package org.apache.commons.math3.geometry.euclidean.oned;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.math3.geometry.partitioning.AbstractRegion;
import org.apache.commons.math3.geometry.partitioning.BSPTree;
import org.apache.commons.math3.geometry.partitioning.Region;
import org.apache.commons.math3.geometry.partitioning.SubHyperplane;
import org.apache.commons.math3.util.Precision;

public class IntervalsSet extends AbstractRegion<Euclidean1D, Euclidean1D> {
   public IntervalsSet() {
   }

   public IntervalsSet(double lower, double upper) {
      super(buildTree(lower, upper));
   }

   public IntervalsSet(BSPTree<Euclidean1D> tree) {
      super(tree);
   }

   public IntervalsSet(Collection<SubHyperplane<Euclidean1D>> boundary) {
      super(boundary);
   }

   private static BSPTree<Euclidean1D> buildTree(double lower, double upper) {
      SubOrientedPoint lowerCut;
      if (Double.isInfinite(lower) && lower < 0.0D) {
         if (Double.isInfinite(upper) && upper > 0.0D) {
            return new BSPTree(Boolean.TRUE);
         } else {
            lowerCut = (new OrientedPoint(new Vector1D(upper), true)).wholeHyperplane();
            return new BSPTree(lowerCut, new BSPTree(Boolean.FALSE), new BSPTree(Boolean.TRUE), (Object)null);
         }
      } else {
         lowerCut = (new OrientedPoint(new Vector1D(lower), false)).wholeHyperplane();
         if (Double.isInfinite(upper) && upper > 0.0D) {
            return new BSPTree(lowerCut, new BSPTree(Boolean.FALSE), new BSPTree(Boolean.TRUE), (Object)null);
         } else {
            SubHyperplane<Euclidean1D> upperCut = (new OrientedPoint(new Vector1D(upper), true)).wholeHyperplane();
            return new BSPTree(lowerCut, new BSPTree(Boolean.FALSE), new BSPTree(upperCut, new BSPTree(Boolean.FALSE), new BSPTree(Boolean.TRUE), (Object)null), (Object)null);
         }
      }
   }

   public IntervalsSet buildNew(BSPTree<Euclidean1D> tree) {
      return new IntervalsSet(tree);
   }

   protected void computeGeometricalProperties() {
      if (this.getTree(false).getCut() == null) {
         this.setBarycenter(Vector1D.NaN);
         this.setSize((Boolean)this.getTree(false).getAttribute() ? Double.POSITIVE_INFINITY : 0.0D);
      } else {
         double size = 0.0D;
         double sum = 0.0D;

         Interval interval;
         for(Iterator i$ = this.asList().iterator(); i$.hasNext(); sum += interval.getSize() * interval.getBarycenter()) {
            interval = (Interval)i$.next();
            size += interval.getSize();
         }

         this.setSize(size);
         if (Double.isInfinite(size)) {
            this.setBarycenter(Vector1D.NaN);
         } else if (size >= Precision.SAFE_MIN) {
            this.setBarycenter(new Vector1D(sum / size));
         } else {
            this.setBarycenter(((OrientedPoint)this.getTree(false).getCut().getHyperplane()).getLocation());
         }
      }

   }

   public double getInf() {
      BSPTree<Euclidean1D> node = this.getTree(false);

      double inf;
      OrientedPoint op;
      for(inf = Double.POSITIVE_INFINITY; node.getCut() != null; node = op.isDirect() ? node.getMinus() : node.getPlus()) {
         op = (OrientedPoint)node.getCut().getHyperplane();
         inf = op.getLocation().getX();
      }

      return (Boolean)node.getAttribute() ? Double.NEGATIVE_INFINITY : inf;
   }

   public double getSup() {
      BSPTree<Euclidean1D> node = this.getTree(false);

      double sup;
      OrientedPoint op;
      for(sup = Double.NEGATIVE_INFINITY; node.getCut() != null; node = op.isDirect() ? node.getPlus() : node.getMinus()) {
         op = (OrientedPoint)node.getCut().getHyperplane();
         sup = op.getLocation().getX();
      }

      return (Boolean)node.getAttribute() ? Double.POSITIVE_INFINITY : sup;
   }

   public List<Interval> asList() {
      List<Interval> list = new ArrayList();
      this.recurseList(this.getTree(false), list, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
      return list;
   }

   private void recurseList(BSPTree<Euclidean1D> node, List<Interval> list, double lower, double upper) {
      if (node.getCut() == null) {
         if ((Boolean)node.getAttribute()) {
            list.add(new Interval(lower, upper));
         }
      } else {
         OrientedPoint op = (OrientedPoint)node.getCut().getHyperplane();
         Vector1D loc = op.getLocation();
         double x = loc.getX();
         BSPTree<Euclidean1D> low = op.isDirect() ? node.getMinus() : node.getPlus();
         BSPTree<Euclidean1D> high = op.isDirect() ? node.getPlus() : node.getMinus();
         this.recurseList(low, list, lower, x);
         if (this.checkPoint(low, loc) == Region.Location.INSIDE && this.checkPoint(high, loc) == Region.Location.INSIDE) {
            x = ((Interval)list.remove(list.size() - 1)).getInf();
         }

         this.recurseList(high, list, x, upper);
      }

   }
}
