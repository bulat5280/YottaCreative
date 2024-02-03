package org.apache.commons.math3.geometry.euclidean.twod;

import java.awt.geom.AffineTransform;
import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.exception.util.LocalizedFormats;
import org.apache.commons.math3.geometry.Vector;
import org.apache.commons.math3.geometry.euclidean.oned.Euclidean1D;
import org.apache.commons.math3.geometry.euclidean.oned.IntervalsSet;
import org.apache.commons.math3.geometry.euclidean.oned.OrientedPoint;
import org.apache.commons.math3.geometry.euclidean.oned.Vector1D;
import org.apache.commons.math3.geometry.partitioning.Embedding;
import org.apache.commons.math3.geometry.partitioning.Hyperplane;
import org.apache.commons.math3.geometry.partitioning.SubHyperplane;
import org.apache.commons.math3.geometry.partitioning.Transform;
import org.apache.commons.math3.util.FastMath;
import org.apache.commons.math3.util.MathUtils;

public class Line implements Hyperplane<Euclidean2D>, Embedding<Euclidean2D, Euclidean1D> {
   private double angle;
   private double cos;
   private double sin;
   private double originOffset;

   public Line(Vector2D p1, Vector2D p2) {
      this.reset(p1, p2);
   }

   public Line(Vector2D p, double angle) {
      this.reset(p, angle);
   }

   private Line(double angle, double cos, double sin, double originOffset) {
      this.angle = angle;
      this.cos = cos;
      this.sin = sin;
      this.originOffset = originOffset;
   }

   public Line(Line line) {
      this.angle = MathUtils.normalizeAngle(line.angle, 3.141592653589793D);
      this.cos = FastMath.cos(this.angle);
      this.sin = FastMath.sin(this.angle);
      this.originOffset = line.originOffset;
   }

   public Line copySelf() {
      return new Line(this);
   }

   public void reset(Vector2D p1, Vector2D p2) {
      double dx = p2.getX() - p1.getX();
      double dy = p2.getY() - p1.getY();
      double d = FastMath.hypot(dx, dy);
      if (d == 0.0D) {
         this.angle = 0.0D;
         this.cos = 1.0D;
         this.sin = 0.0D;
         this.originOffset = p1.getY();
      } else {
         this.angle = 3.141592653589793D + FastMath.atan2(-dy, -dx);
         this.cos = FastMath.cos(this.angle);
         this.sin = FastMath.sin(this.angle);
         this.originOffset = (p2.getX() * p1.getY() - p1.getX() * p2.getY()) / d;
      }

   }

   public void reset(Vector2D p, double alpha) {
      this.angle = MathUtils.normalizeAngle(alpha, 3.141592653589793D);
      this.cos = FastMath.cos(this.angle);
      this.sin = FastMath.sin(this.angle);
      this.originOffset = this.cos * p.getY() - this.sin * p.getX();
   }

   public void revertSelf() {
      if (this.angle < 3.141592653589793D) {
         this.angle += 3.141592653589793D;
      } else {
         this.angle -= 3.141592653589793D;
      }

      this.cos = -this.cos;
      this.sin = -this.sin;
      this.originOffset = -this.originOffset;
   }

   public Line getReverse() {
      return new Line(this.angle < 3.141592653589793D ? this.angle + 3.141592653589793D : this.angle - 3.141592653589793D, -this.cos, -this.sin, -this.originOffset);
   }

   public Vector1D toSubSpace(Vector<Euclidean2D> point) {
      Vector2D p2 = (Vector2D)point;
      return new Vector1D(this.cos * p2.getX() + this.sin * p2.getY());
   }

   public Vector2D toSpace(Vector<Euclidean1D> point) {
      double abscissa = ((Vector1D)point).getX();
      return new Vector2D(abscissa * this.cos - this.originOffset * this.sin, abscissa * this.sin + this.originOffset * this.cos);
   }

   public Vector2D intersection(Line other) {
      double d = this.sin * other.cos - other.sin * this.cos;
      return FastMath.abs(d) < 1.0E-10D ? null : new Vector2D((this.cos * other.originOffset - other.cos * this.originOffset) / d, (this.sin * other.originOffset - other.sin * this.originOffset) / d);
   }

   public SubLine wholeHyperplane() {
      return new SubLine(this, new IntervalsSet());
   }

   public PolygonsSet wholeSpace() {
      return new PolygonsSet();
   }

   public double getOffset(Line line) {
      return this.originOffset + (this.cos * line.cos + this.sin * line.sin > 0.0D ? -line.originOffset : line.originOffset);
   }

   public double getOffset(Vector<Euclidean2D> point) {
      Vector2D p2 = (Vector2D)point;
      return this.sin * p2.getX() - this.cos * p2.getY() + this.originOffset;
   }

   public boolean sameOrientationAs(Hyperplane<Euclidean2D> other) {
      Line otherL = (Line)other;
      return this.sin * otherL.sin + this.cos * otherL.cos >= 0.0D;
   }

   public Vector2D getPointAt(Vector1D abscissa, double offset) {
      double x = abscissa.getX();
      double dOffset = offset - this.originOffset;
      return new Vector2D(x * this.cos + dOffset * this.sin, x * this.sin - dOffset * this.cos);
   }

   public boolean contains(Vector2D p) {
      return FastMath.abs(this.getOffset((Vector)p)) < 1.0E-10D;
   }

   public double distance(Vector2D p) {
      return FastMath.abs(this.getOffset((Vector)p));
   }

   public boolean isParallelTo(Line line) {
      return FastMath.abs(this.sin * line.cos - this.cos * line.sin) < 1.0E-10D;
   }

   public void translateToPoint(Vector2D p) {
      this.originOffset = this.cos * p.getY() - this.sin * p.getX();
   }

   public double getAngle() {
      return MathUtils.normalizeAngle(this.angle, 3.141592653589793D);
   }

   public void setAngle(double angle) {
      this.angle = MathUtils.normalizeAngle(angle, 3.141592653589793D);
      this.cos = FastMath.cos(this.angle);
      this.sin = FastMath.sin(this.angle);
   }

   public double getOriginOffset() {
      return this.originOffset;
   }

   public void setOriginOffset(double offset) {
      this.originOffset = offset;
   }

   public static Transform<Euclidean2D, Euclidean1D> getTransform(AffineTransform transform) throws MathIllegalArgumentException {
      return new Line.LineTransform(transform);
   }

   // $FF: synthetic method
   Line(double x0, double x1, double x2, double x3, Object x4) {
      this(x0, x1, x2, x3);
   }

   private static class LineTransform implements Transform<Euclidean2D, Euclidean1D> {
      private double cXX;
      private double cXY;
      private double cX1;
      private double cYX;
      private double cYY;
      private double cY1;
      private double c1Y;
      private double c1X;
      private double c11;

      public LineTransform(AffineTransform transform) throws MathIllegalArgumentException {
         double[] m = new double[6];
         transform.getMatrix(m);
         this.cXX = m[0];
         this.cXY = m[2];
         this.cX1 = m[4];
         this.cYX = m[1];
         this.cYY = m[3];
         this.cY1 = m[5];
         this.c1Y = this.cXY * this.cY1 - this.cYY * this.cX1;
         this.c1X = this.cXX * this.cY1 - this.cYX * this.cX1;
         this.c11 = this.cXX * this.cYY - this.cYX * this.cXY;
         if (FastMath.abs(this.c11) < 1.0E-20D) {
            throw new MathIllegalArgumentException(LocalizedFormats.NON_INVERTIBLE_TRANSFORM, new Object[0]);
         }
      }

      public Vector2D apply(Vector<Euclidean2D> point) {
         Vector2D p2D = (Vector2D)point;
         double x = p2D.getX();
         double y = p2D.getY();
         return new Vector2D(this.cXX * x + this.cXY * y + this.cX1, this.cYX * x + this.cYY * y + this.cY1);
      }

      public Line apply(Hyperplane<Euclidean2D> hyperplane) {
         Line line = (Line)hyperplane;
         double rOffset = this.c1X * line.cos + this.c1Y * line.sin + this.c11 * line.originOffset;
         double rCos = this.cXX * line.cos + this.cXY * line.sin;
         double rSin = this.cYX * line.cos + this.cYY * line.sin;
         double inv = 1.0D / FastMath.sqrt(rSin * rSin + rCos * rCos);
         return new Line(3.141592653589793D + FastMath.atan2(-rSin, -rCos), inv * rCos, inv * rSin, inv * rOffset);
      }

      public SubHyperplane<Euclidean1D> apply(SubHyperplane<Euclidean1D> sub, Hyperplane<Euclidean2D> original, Hyperplane<Euclidean2D> transformed) {
         OrientedPoint op = (OrientedPoint)sub.getHyperplane();
         Line originalLine = (Line)original;
         Line transformedLine = (Line)transformed;
         Vector1D newLoc = transformedLine.toSubSpace(this.apply((Vector)originalLine.toSpace(op.getLocation())));
         return (new OrientedPoint(newLoc, op.isDirect())).wholeHyperplane();
      }
   }
}
