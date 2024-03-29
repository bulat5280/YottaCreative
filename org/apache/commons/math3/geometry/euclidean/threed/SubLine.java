package org.apache.commons.math3.geometry.euclidean.threed;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.geometry.euclidean.oned.Interval;
import org.apache.commons.math3.geometry.euclidean.oned.IntervalsSet;
import org.apache.commons.math3.geometry.euclidean.oned.Vector1D;
import org.apache.commons.math3.geometry.partitioning.Region;

public class SubLine {
   private final Line line;
   private final IntervalsSet remainingRegion;

   public SubLine(Line line, IntervalsSet remainingRegion) {
      this.line = line;
      this.remainingRegion = remainingRegion;
   }

   public SubLine(Vector3D start, Vector3D end) throws MathIllegalArgumentException {
      this(new Line(start, end), buildIntervalSet(start, end));
   }

   public SubLine(Segment segment) throws MathIllegalArgumentException {
      this(segment.getLine(), buildIntervalSet(segment.getStart(), segment.getEnd()));
   }

   public List<Segment> getSegments() {
      List<Interval> list = this.remainingRegion.asList();
      List<Segment> segments = new ArrayList();
      Iterator i$ = list.iterator();

      while(i$.hasNext()) {
         Interval interval = (Interval)i$.next();
         Vector3D start = this.line.toSpace(new Vector1D(interval.getInf()));
         Vector3D end = this.line.toSpace(new Vector1D(interval.getSup()));
         segments.add(new Segment(start, end, this.line));
      }

      return segments;
   }

   public Vector3D intersection(SubLine subLine, boolean includeEndPoints) {
      Vector3D v1D = this.line.intersection(subLine.line);
      Region.Location loc1 = this.remainingRegion.checkPoint(this.line.toSubSpace(v1D));
      Region.Location loc2 = subLine.remainingRegion.checkPoint(subLine.line.toSubSpace(v1D));
      if (includeEndPoints) {
         return loc1 != Region.Location.OUTSIDE && loc2 != Region.Location.OUTSIDE ? v1D : null;
      } else {
         return loc1 == Region.Location.INSIDE && loc2 == Region.Location.INSIDE ? v1D : null;
      }
   }

   private static IntervalsSet buildIntervalSet(Vector3D start, Vector3D end) throws MathIllegalArgumentException {
      Line line = new Line(start, end);
      return new IntervalsSet(line.toSubSpace(start).getX(), line.toSubSpace(end).getX());
   }
}
