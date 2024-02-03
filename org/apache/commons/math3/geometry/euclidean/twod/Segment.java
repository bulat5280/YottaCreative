package org.apache.commons.math3.geometry.euclidean.twod;

import org.apache.commons.math3.util.FastMath;

public class Segment {
   private final Vector2D start;
   private final Vector2D end;
   private final Line line;

   public Segment(Vector2D start, Vector2D end, Line line) {
      this.start = start;
      this.end = end;
      this.line = line;
   }

   public Vector2D getStart() {
      return this.start;
   }

   public Vector2D getEnd() {
      return this.end;
   }

   public Line getLine() {
      return this.line;
   }

   public double distance(Vector2D p) {
      double deltaX = this.end.getX() - this.start.getX();
      double deltaY = this.end.getY() - this.start.getY();
      double r = ((p.getX() - this.start.getX()) * deltaX + (p.getY() - this.start.getY()) * deltaY) / (deltaX * deltaX + deltaY * deltaY);
      double dist1;
      double dist2;
      if (!(r < 0.0D) && !(r > 1.0D)) {
         dist1 = this.start.getX() + r * deltaX;
         dist2 = this.start.getY() + r * deltaY;
         Vector2D interPt = new Vector2D(dist1, dist2);
         return interPt.distance(p);
      } else {
         dist1 = this.getStart().distance(p);
         dist2 = this.getEnd().distance(p);
         return FastMath.min(dist1, dist2);
      }
   }
}
