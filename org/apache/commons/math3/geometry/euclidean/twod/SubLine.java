package org.apache.commons.math3.geometry.euclidean.twod;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.math3.geometry.euclidean.oned.Euclidean1D;
import org.apache.commons.math3.geometry.euclidean.oned.Interval;
import org.apache.commons.math3.geometry.euclidean.oned.IntervalsSet;
import org.apache.commons.math3.geometry.euclidean.oned.OrientedPoint;
import org.apache.commons.math3.geometry.euclidean.oned.Vector1D;
import org.apache.commons.math3.geometry.partitioning.AbstractSubHyperplane;
import org.apache.commons.math3.geometry.partitioning.BSPTree;
import org.apache.commons.math3.geometry.partitioning.Hyperplane;
import org.apache.commons.math3.geometry.partitioning.Region;
import org.apache.commons.math3.geometry.partitioning.Side;
import org.apache.commons.math3.geometry.partitioning.SubHyperplane;
import org.apache.commons.math3.util.FastMath;

public class SubLine extends AbstractSubHyperplane<Euclidean2D, Euclidean1D> {
   public SubLine(Hyperplane<Euclidean2D> hyperplane, Region<Euclidean1D> remainingRegion) {
      super(hyperplane, remainingRegion);
   }

   public SubLine(Vector2D start, Vector2D end) {
      super(new Line(start, end), buildIntervalSet(start, end));
   }

   public SubLine(Segment segment) {
      super(segment.getLine(), buildIntervalSet(segment.getStart(), segment.getEnd()));
   }

   public List<Segment> getSegments() {
      Line line = (Line)this.getHyperplane();
      List<Interval> list = ((IntervalsSet)this.getRemainingRegion()).asList();
      List<Segment> segments = new ArrayList();
      Iterator i$ = list.iterator();

      while(i$.hasNext()) {
         Interval interval = (Interval)i$.next();
         Vector2D start = line.toSpace(new Vector1D(interval.getInf()));
         Vector2D end = line.toSpace(new Vector1D(interval.getSup()));
         segments.add(new Segment(start, end, line));
      }

      return segments;
   }

   public Vector2D intersection(SubLine subLine, boolean includeEndPoints) {
      Line line1 = (Line)this.getHyperplane();
      Line line2 = (Line)subLine.getHyperplane();
      Vector2D v2D = line1.intersection(line2);
      Region.Location loc1 = this.getRemainingRegion().checkPoint(line1.toSubSpace(v2D));
      Region.Location loc2 = subLine.getRemainingRegion().checkPoint(line2.toSubSpace(v2D));
      if (includeEndPoints) {
         return loc1 != Region.Location.OUTSIDE && loc2 != Region.Location.OUTSIDE ? v2D : null;
      } else {
         return loc1 == Region.Location.INSIDE && loc2 == Region.Location.INSIDE ? v2D : null;
      }
   }

   private static IntervalsSet buildIntervalSet(Vector2D start, Vector2D end) {
      Line line = new Line(start, end);
      return new IntervalsSet(line.toSubSpace(start).getX(), line.toSubSpace(end).getX());
   }

   protected AbstractSubHyperplane<Euclidean2D, Euclidean1D> buildNew(Hyperplane<Euclidean2D> hyperplane, Region<Euclidean1D> remainingRegion) {
      return new SubLine(hyperplane, remainingRegion);
   }

   public Side side(Hyperplane<Euclidean2D> hyperplane) {
      Line thisLine = (Line)this.getHyperplane();
      Line otherLine = (Line)hyperplane;
      Vector2D crossing = thisLine.intersection(otherLine);
      if (crossing == null) {
         double global = otherLine.getOffset(thisLine);
         return global < -1.0E-10D ? Side.MINUS : (global > 1.0E-10D ? Side.PLUS : Side.HYPER);
      } else {
         boolean direct = FastMath.sin(thisLine.getAngle() - otherLine.getAngle()) < 0.0D;
         Vector1D x = thisLine.toSubSpace(crossing);
         return this.getRemainingRegion().side(new OrientedPoint(x, direct));
      }
   }

   public SubHyperplane.SplitSubHyperplane<Euclidean2D> split(Hyperplane<Euclidean2D> hyperplane) {
      Line thisLine = (Line)this.getHyperplane();
      Line otherLine = (Line)hyperplane;
      Vector2D crossing = thisLine.intersection(otherLine);
      if (crossing == null) {
         double global = otherLine.getOffset(thisLine);
         return global < -1.0E-10D ? new SubHyperplane.SplitSubHyperplane((SubHyperplane)null, this) : new SubHyperplane.SplitSubHyperplane(this, (SubHyperplane)null);
      } else {
         boolean direct = FastMath.sin(thisLine.getAngle() - otherLine.getAngle()) < 0.0D;
         Vector1D x = thisLine.toSubSpace(crossing);
         SubHyperplane<Euclidean1D> subPlus = (new OrientedPoint(x, !direct)).wholeHyperplane();
         SubHyperplane<Euclidean1D> subMinus = (new OrientedPoint(x, direct)).wholeHyperplane();
         BSPTree<Euclidean1D> splitTree = this.getRemainingRegion().getTree(false).split(subMinus);
         BSPTree<Euclidean1D> plusTree = this.getRemainingRegion().isEmpty(splitTree.getPlus()) ? new BSPTree(Boolean.FALSE) : new BSPTree(subPlus, new BSPTree(Boolean.FALSE), splitTree.getPlus(), (Object)null);
         BSPTree<Euclidean1D> minusTree = this.getRemainingRegion().isEmpty(splitTree.getMinus()) ? new BSPTree(Boolean.FALSE) : new BSPTree(subMinus, new BSPTree(Boolean.FALSE), splitTree.getMinus(), (Object)null);
         return new SubHyperplane.SplitSubHyperplane(new SubLine(thisLine.copySelf(), new IntervalsSet(plusTree)), new SubLine(thisLine.copySelf(), new IntervalsSet(minusTree)));
      }
   }
}
