package org.apache.commons.math3.geometry.euclidean.twod;

import java.util.ArrayList;
import java.util.Iterator;
import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.exception.util.LocalizedFormats;
import org.apache.commons.math3.geometry.euclidean.oned.IntervalsSet;
import org.apache.commons.math3.geometry.partitioning.Region;
import org.apache.commons.math3.geometry.partitioning.RegionFactory;
import org.apache.commons.math3.geometry.partitioning.SubHyperplane;

class NestedLoops {
   private Vector2D[] loop;
   private ArrayList<NestedLoops> surrounded;
   private Region<Euclidean2D> polygon;
   private boolean originalIsClockwise;

   public NestedLoops() {
      this.surrounded = new ArrayList();
   }

   private NestedLoops(Vector2D[] loop) throws MathIllegalArgumentException {
      if (loop[0] == null) {
         throw new MathIllegalArgumentException(LocalizedFormats.OUTLINE_BOUNDARY_LOOP_OPEN, new Object[0]);
      } else {
         this.loop = loop;
         this.surrounded = new ArrayList();
         ArrayList<SubHyperplane<Euclidean2D>> edges = new ArrayList();
         Vector2D current = loop[loop.length - 1];

         for(int i = 0; i < loop.length; ++i) {
            Vector2D previous = current;
            current = loop[i];
            Line line = new Line(previous, current);
            IntervalsSet region = new IntervalsSet(line.toSubSpace(previous).getX(), line.toSubSpace(current).getX());
            edges.add(new SubLine(line, region));
         }

         this.polygon = new PolygonsSet(edges);
         if (Double.isInfinite(this.polygon.getSize())) {
            this.polygon = (new RegionFactory()).getComplement(this.polygon);
            this.originalIsClockwise = false;
         } else {
            this.originalIsClockwise = true;
         }

      }
   }

   public void add(Vector2D[] bLoop) throws MathIllegalArgumentException {
      this.add(new NestedLoops(bLoop));
   }

   private void add(NestedLoops node) throws MathIllegalArgumentException {
      Iterator iterator = this.surrounded.iterator();

      NestedLoops child;
      do {
         if (!iterator.hasNext()) {
            iterator = this.surrounded.iterator();

            while(iterator.hasNext()) {
               child = (NestedLoops)iterator.next();
               if (node.polygon.contains(child.polygon)) {
                  node.surrounded.add(child);
                  iterator.remove();
               }
            }

            RegionFactory<Euclidean2D> factory = new RegionFactory();
            Iterator i$ = this.surrounded.iterator();

            NestedLoops child;
            do {
               if (!i$.hasNext()) {
                  this.surrounded.add(node);
                  return;
               }

               child = (NestedLoops)i$.next();
            } while(factory.intersection(node.polygon, child.polygon).isEmpty());

            throw new MathIllegalArgumentException(LocalizedFormats.CROSSING_BOUNDARY_LOOPS, new Object[0]);
         }

         child = (NestedLoops)iterator.next();
      } while(!child.polygon.contains(node.polygon));

      child.add(node);
   }

   public void correctOrientation() {
      Iterator i$ = this.surrounded.iterator();

      while(i$.hasNext()) {
         NestedLoops child = (NestedLoops)i$.next();
         child.setClockWise(true);
      }

   }

   private void setClockWise(boolean clockwise) {
      if (this.originalIsClockwise ^ clockwise) {
         int min = -1;
         int max = this.loop.length;

         while(true) {
            ++min;
            --max;
            if (min >= max) {
               break;
            }

            Vector2D tmp = this.loop[min];
            this.loop[min] = this.loop[max];
            this.loop[max] = tmp;
         }
      }

      Iterator i$ = this.surrounded.iterator();

      while(i$.hasNext()) {
         NestedLoops child = (NestedLoops)i$.next();
         child.setClockWise(!clockwise);
      }

   }
}
