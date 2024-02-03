package org.apache.commons.math3.geometry.partitioning;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;
import org.apache.commons.math3.exception.MathInternalError;
import org.apache.commons.math3.geometry.Space;
import org.apache.commons.math3.geometry.Vector;

public abstract class AbstractRegion<S extends Space, T extends Space> implements Region<S> {
   private BSPTree<S> tree;
   private double size;
   private Vector<S> barycenter;

   protected AbstractRegion() {
      this.tree = new BSPTree(Boolean.TRUE);
   }

   protected AbstractRegion(BSPTree<S> tree) {
      this.tree = tree;
   }

   protected AbstractRegion(Collection<SubHyperplane<S>> boundary) {
      if (boundary.size() == 0) {
         this.tree = new BSPTree(Boolean.TRUE);
      } else {
         TreeSet<SubHyperplane<S>> ordered = new TreeSet(new Comparator<SubHyperplane<S>>() {
            public int compare(SubHyperplane<S> o1, SubHyperplane<S> o2) {
               double size1 = o1.getSize();
               double size2 = o2.getSize();
               return size2 < size1 ? -1 : (o1 == o2 ? 0 : 1);
            }
         });
         ordered.addAll(boundary);
         this.tree = new BSPTree();
         this.insertCuts(this.tree, ordered);
         this.tree.visit(new BSPTreeVisitor<S>() {
            public BSPTreeVisitor.Order visitOrder(BSPTree<S> node) {
               return BSPTreeVisitor.Order.PLUS_SUB_MINUS;
            }

            public void visitInternalNode(BSPTree<S> node) {
            }

            public void visitLeafNode(BSPTree<S> node) {
               node.setAttribute(node == node.getParent().getPlus() ? Boolean.FALSE : Boolean.TRUE);
            }
         });
      }

   }

   public AbstractRegion(Hyperplane<S>[] hyperplanes) {
      if (hyperplanes != null && hyperplanes.length != 0) {
         this.tree = hyperplanes[0].wholeSpace().getTree(false);
         BSPTree<S> node = this.tree;
         node.setAttribute(Boolean.TRUE);
         Hyperplane[] arr$ = hyperplanes;
         int len$ = hyperplanes.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            Hyperplane<S> hyperplane = arr$[i$];
            if (node.insertCut(hyperplane)) {
               node.setAttribute((Object)null);
               node.getPlus().setAttribute(Boolean.FALSE);
               node = node.getMinus();
               node.setAttribute(Boolean.TRUE);
            }
         }
      } else {
         this.tree = new BSPTree(Boolean.FALSE);
      }

   }

   public abstract AbstractRegion<S, T> buildNew(BSPTree<S> var1);

   private void insertCuts(BSPTree<S> node, Collection<SubHyperplane<S>> boundary) {
      Iterator<SubHyperplane<S>> iterator = boundary.iterator();
      Hyperplane inserted = null;

      while(inserted == null && iterator.hasNext()) {
         inserted = ((SubHyperplane)iterator.next()).getHyperplane();
         if (!node.insertCut(inserted.copySelf())) {
            inserted = null;
         }
      }

      if (iterator.hasNext()) {
         ArrayList<SubHyperplane<S>> plusList = new ArrayList();
         ArrayList minusList = new ArrayList();

         while(iterator.hasNext()) {
            SubHyperplane<S> other = (SubHyperplane)iterator.next();
            switch(other.side(inserted)) {
            case PLUS:
               plusList.add(other);
               break;
            case MINUS:
               minusList.add(other);
               break;
            case BOTH:
               SubHyperplane.SplitSubHyperplane<S> split = other.split(inserted);
               plusList.add(split.getPlus());
               minusList.add(split.getMinus());
            }
         }

         this.insertCuts(node.getPlus(), plusList);
         this.insertCuts(node.getMinus(), minusList);
      }
   }

   public AbstractRegion<S, T> copySelf() {
      return this.buildNew(this.tree.copySelf());
   }

   public boolean isEmpty() {
      return this.isEmpty(this.tree);
   }

   public boolean isEmpty(BSPTree<S> node) {
      if (node.getCut() == null) {
         return !(Boolean)node.getAttribute();
      } else {
         return this.isEmpty(node.getMinus()) && this.isEmpty(node.getPlus());
      }
   }

   public boolean contains(Region<S> region) {
      return (new RegionFactory()).difference(region, this).isEmpty();
   }

   public Region.Location checkPoint(Vector<S> point) {
      return this.checkPoint(this.tree, point);
   }

   protected Region.Location checkPoint(BSPTree<S> node, Vector<S> point) {
      BSPTree<S> cell = node.getCell(point);
      if (cell.getCut() == null) {
         return (Boolean)cell.getAttribute() ? Region.Location.INSIDE : Region.Location.OUTSIDE;
      } else {
         Region.Location minusCode = this.checkPoint(cell.getMinus(), point);
         Region.Location plusCode = this.checkPoint(cell.getPlus(), point);
         return minusCode == plusCode ? minusCode : Region.Location.BOUNDARY;
      }
   }

   public BSPTree<S> getTree(boolean includeBoundaryAttributes) {
      if (includeBoundaryAttributes && this.tree.getCut() != null && this.tree.getAttribute() == null) {
         this.tree.visit(new AbstractRegion.BoundaryBuilder());
      }

      return this.tree;
   }

   public double getBoundarySize() {
      BoundarySizeVisitor<S> visitor = new BoundarySizeVisitor();
      this.getTree(true).visit(visitor);
      return visitor.getSize();
   }

   public double getSize() {
      if (this.barycenter == null) {
         this.computeGeometricalProperties();
      }

      return this.size;
   }

   protected void setSize(double size) {
      this.size = size;
   }

   public Vector<S> getBarycenter() {
      if (this.barycenter == null) {
         this.computeGeometricalProperties();
      }

      return this.barycenter;
   }

   protected void setBarycenter(Vector<S> barycenter) {
      this.barycenter = barycenter;
   }

   protected abstract void computeGeometricalProperties();

   public Side side(Hyperplane<S> hyperplane) {
      AbstractRegion.Sides sides = new AbstractRegion.Sides();
      this.recurseSides(this.tree, hyperplane.wholeHyperplane(), sides);
      return sides.plusFound() ? (sides.minusFound() ? Side.BOTH : Side.PLUS) : (sides.minusFound() ? Side.MINUS : Side.HYPER);
   }

   private void recurseSides(BSPTree<S> node, SubHyperplane<S> sub, AbstractRegion.Sides sides) {
      if (node.getCut() == null) {
         if ((Boolean)node.getAttribute()) {
            sides.rememberPlusFound();
            sides.rememberMinusFound();
         }

      } else {
         Hyperplane<S> hyperplane = node.getCut().getHyperplane();
         switch(sub.side(hyperplane)) {
         case PLUS:
            if (node.getCut().side(sub.getHyperplane()) == Side.PLUS) {
               if (!this.isEmpty(node.getMinus())) {
                  sides.rememberPlusFound();
               }
            } else if (!this.isEmpty(node.getMinus())) {
               sides.rememberMinusFound();
            }

            if (!sides.plusFound() || !sides.minusFound()) {
               this.recurseSides(node.getPlus(), sub, sides);
            }
            break;
         case MINUS:
            if (node.getCut().side(sub.getHyperplane()) == Side.PLUS) {
               if (!this.isEmpty(node.getPlus())) {
                  sides.rememberPlusFound();
               }
            } else if (!this.isEmpty(node.getPlus())) {
               sides.rememberMinusFound();
            }

            if (!sides.plusFound() || !sides.minusFound()) {
               this.recurseSides(node.getMinus(), sub, sides);
            }
            break;
         case BOTH:
            SubHyperplane.SplitSubHyperplane<S> split = sub.split(hyperplane);
            this.recurseSides(node.getPlus(), split.getPlus(), sides);
            if (!sides.plusFound() || !sides.minusFound()) {
               this.recurseSides(node.getMinus(), split.getMinus(), sides);
            }
            break;
         default:
            if (node.getCut().getHyperplane().sameOrientationAs(sub.getHyperplane())) {
               if (node.getPlus().getCut() != null || (Boolean)node.getPlus().getAttribute()) {
                  sides.rememberPlusFound();
               }

               if (node.getMinus().getCut() != null || (Boolean)node.getMinus().getAttribute()) {
                  sides.rememberMinusFound();
               }
            } else {
               if (node.getPlus().getCut() != null || (Boolean)node.getPlus().getAttribute()) {
                  sides.rememberMinusFound();
               }

               if (node.getMinus().getCut() != null || (Boolean)node.getMinus().getAttribute()) {
                  sides.rememberPlusFound();
               }
            }
         }

      }
   }

   public SubHyperplane<S> intersection(SubHyperplane<S> sub) {
      return this.recurseIntersection(this.tree, sub);
   }

   private SubHyperplane<S> recurseIntersection(BSPTree<S> node, SubHyperplane<S> sub) {
      if (node.getCut() == null) {
         return (Boolean)node.getAttribute() ? sub.copySelf() : null;
      } else {
         Hyperplane<S> hyperplane = node.getCut().getHyperplane();
         switch(sub.side(hyperplane)) {
         case PLUS:
            return this.recurseIntersection(node.getPlus(), sub);
         case MINUS:
            return this.recurseIntersection(node.getMinus(), sub);
         case BOTH:
            SubHyperplane.SplitSubHyperplane<S> split = sub.split(hyperplane);
            SubHyperplane<S> plus = this.recurseIntersection(node.getPlus(), split.getPlus());
            SubHyperplane<S> minus = this.recurseIntersection(node.getMinus(), split.getMinus());
            if (plus == null) {
               return minus;
            } else {
               if (minus == null) {
                  return plus;
               }

               return plus.reunite(minus);
            }
         default:
            return this.recurseIntersection(node.getPlus(), this.recurseIntersection(node.getMinus(), sub));
         }
      }
   }

   public AbstractRegion<S, T> applyTransform(Transform<S, T> transform) {
      return this.buildNew(this.recurseTransform(this.getTree(false), transform));
   }

   private BSPTree<S> recurseTransform(BSPTree<S> node, Transform<S, T> transform) {
      if (node.getCut() == null) {
         return new BSPTree(node.getAttribute());
      } else {
         SubHyperplane<S> sub = node.getCut();
         SubHyperplane<S> tSub = ((AbstractSubHyperplane)sub).applyTransform(transform);
         BoundaryAttribute<S> attribute = (BoundaryAttribute)node.getAttribute();
         if (attribute != null) {
            SubHyperplane<S> tPO = attribute.getPlusOutside() == null ? null : ((AbstractSubHyperplane)attribute.getPlusOutside()).applyTransform(transform);
            SubHyperplane<S> tPI = attribute.getPlusInside() == null ? null : ((AbstractSubHyperplane)attribute.getPlusInside()).applyTransform(transform);
            attribute = new BoundaryAttribute(tPO, tPI);
         }

         return new BSPTree(tSub, this.recurseTransform(node.getPlus(), transform), this.recurseTransform(node.getMinus(), transform), attribute);
      }
   }

   private static final class Sides {
      private boolean plusFound = false;
      private boolean minusFound = false;

      public Sides() {
      }

      public void rememberPlusFound() {
         this.plusFound = true;
      }

      public boolean plusFound() {
         return this.plusFound;
      }

      public void rememberMinusFound() {
         this.minusFound = true;
      }

      public boolean minusFound() {
         return this.minusFound;
      }
   }

   private static class BoundaryBuilder<S extends Space> implements BSPTreeVisitor<S> {
      private BoundaryBuilder() {
      }

      public BSPTreeVisitor.Order visitOrder(BSPTree<S> node) {
         return BSPTreeVisitor.Order.PLUS_MINUS_SUB;
      }

      public void visitInternalNode(BSPTree<S> node) {
         SubHyperplane<S> plusOutside = null;
         SubHyperplane<S> plusInside = null;
         SubHyperplane<S>[] plusChar = (SubHyperplane[])((SubHyperplane[])Array.newInstance(SubHyperplane.class, 2));
         this.characterize(node.getPlus(), node.getCut().copySelf(), plusChar);
         SubHyperplane[] minusChar;
         if (plusChar[0] != null && !plusChar[0].isEmpty()) {
            minusChar = (SubHyperplane[])((SubHyperplane[])Array.newInstance(SubHyperplane.class, 2));
            this.characterize(node.getMinus(), plusChar[0], minusChar);
            if (minusChar[1] != null && !minusChar[1].isEmpty()) {
               plusOutside = minusChar[1];
            }
         }

         if (plusChar[1] != null && !plusChar[1].isEmpty()) {
            minusChar = (SubHyperplane[])((SubHyperplane[])Array.newInstance(SubHyperplane.class, 2));
            this.characterize(node.getMinus(), plusChar[1], minusChar);
            if (minusChar[0] != null && !minusChar[0].isEmpty()) {
               plusInside = minusChar[0];
            }
         }

         node.setAttribute(new BoundaryAttribute(plusOutside, plusInside));
      }

      public void visitLeafNode(BSPTree<S> node) {
      }

      private void characterize(BSPTree<S> node, SubHyperplane<S> sub, SubHyperplane<S>[] characterization) {
         if (node.getCut() == null) {
            boolean inside = (Boolean)node.getAttribute();
            if (inside) {
               if (characterization[1] == null) {
                  characterization[1] = sub;
               } else {
                  characterization[1] = characterization[1].reunite(sub);
               }
            } else if (characterization[0] == null) {
               characterization[0] = sub;
            } else {
               characterization[0] = characterization[0].reunite(sub);
            }
         } else {
            Hyperplane<S> hyperplane = node.getCut().getHyperplane();
            switch(sub.side(hyperplane)) {
            case PLUS:
               this.characterize(node.getPlus(), sub, characterization);
               break;
            case MINUS:
               this.characterize(node.getMinus(), sub, characterization);
               break;
            case BOTH:
               SubHyperplane.SplitSubHyperplane<S> split = sub.split(hyperplane);
               this.characterize(node.getPlus(), split.getPlus(), characterization);
               this.characterize(node.getMinus(), split.getMinus(), characterization);
               break;
            default:
               throw new MathInternalError();
            }
         }

      }

      // $FF: synthetic method
      BoundaryBuilder(Object x0) {
         this();
      }
   }
}
