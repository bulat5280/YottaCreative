package org.apache.commons.math3.geometry.partitioning;

import org.apache.commons.math3.exception.MathInternalError;
import org.apache.commons.math3.geometry.Space;
import org.apache.commons.math3.geometry.Vector;
import org.apache.commons.math3.util.FastMath;

public class BSPTree<S extends Space> {
   private SubHyperplane<S> cut;
   private BSPTree<S> plus;
   private BSPTree<S> minus;
   private BSPTree<S> parent;
   private Object attribute;

   public BSPTree() {
      this.cut = null;
      this.plus = null;
      this.minus = null;
      this.parent = null;
      this.attribute = null;
   }

   public BSPTree(Object attribute) {
      this.cut = null;
      this.plus = null;
      this.minus = null;
      this.parent = null;
      this.attribute = attribute;
   }

   public BSPTree(SubHyperplane<S> cut, BSPTree<S> plus, BSPTree<S> minus, Object attribute) {
      this.cut = cut;
      this.plus = plus;
      this.minus = minus;
      this.parent = null;
      this.attribute = attribute;
      plus.parent = this;
      minus.parent = this;
   }

   public boolean insertCut(Hyperplane<S> hyperplane) {
      if (this.cut != null) {
         this.plus.parent = null;
         this.minus.parent = null;
      }

      SubHyperplane<S> chopped = this.fitToCell(hyperplane.wholeHyperplane());
      if (chopped != null && !chopped.isEmpty()) {
         this.cut = chopped;
         this.plus = new BSPTree();
         this.plus.parent = this;
         this.minus = new BSPTree();
         this.minus.parent = this;
         return true;
      } else {
         this.cut = null;
         this.plus = null;
         this.minus = null;
         return false;
      }
   }

   public BSPTree<S> copySelf() {
      return this.cut == null ? new BSPTree(this.attribute) : new BSPTree(this.cut.copySelf(), this.plus.copySelf(), this.minus.copySelf(), this.attribute);
   }

   public SubHyperplane<S> getCut() {
      return this.cut;
   }

   public BSPTree<S> getPlus() {
      return this.plus;
   }

   public BSPTree<S> getMinus() {
      return this.minus;
   }

   public BSPTree<S> getParent() {
      return this.parent;
   }

   public void setAttribute(Object attribute) {
      this.attribute = attribute;
   }

   public Object getAttribute() {
      return this.attribute;
   }

   public void visit(BSPTreeVisitor<S> visitor) {
      if (this.cut == null) {
         visitor.visitLeafNode(this);
      } else {
         switch(visitor.visitOrder(this)) {
         case PLUS_MINUS_SUB:
            this.plus.visit(visitor);
            this.minus.visit(visitor);
            visitor.visitInternalNode(this);
            break;
         case PLUS_SUB_MINUS:
            this.plus.visit(visitor);
            visitor.visitInternalNode(this);
            this.minus.visit(visitor);
            break;
         case MINUS_PLUS_SUB:
            this.minus.visit(visitor);
            this.plus.visit(visitor);
            visitor.visitInternalNode(this);
            break;
         case MINUS_SUB_PLUS:
            this.minus.visit(visitor);
            visitor.visitInternalNode(this);
            this.plus.visit(visitor);
            break;
         case SUB_PLUS_MINUS:
            visitor.visitInternalNode(this);
            this.plus.visit(visitor);
            this.minus.visit(visitor);
            break;
         case SUB_MINUS_PLUS:
            visitor.visitInternalNode(this);
            this.minus.visit(visitor);
            this.plus.visit(visitor);
            break;
         default:
            throw new MathInternalError();
         }
      }

   }

   private SubHyperplane<S> fitToCell(SubHyperplane<S> sub) {
      SubHyperplane<S> s = sub;

      for(BSPTree tree = this; tree.parent != null; tree = tree.parent) {
         if (tree == tree.parent.plus) {
            s = s.split(tree.parent.cut.getHyperplane()).getPlus();
         } else {
            s = s.split(tree.parent.cut.getHyperplane()).getMinus();
         }
      }

      return s;
   }

   public BSPTree<S> getCell(Vector<S> point) {
      if (this.cut == null) {
         return this;
      } else {
         double offset = this.cut.getHyperplane().getOffset(point);
         if (FastMath.abs(offset) < 1.0E-10D) {
            return this;
         } else {
            return offset <= 0.0D ? this.minus.getCell(point) : this.plus.getCell(point);
         }
      }
   }

   private void condense() {
      if (this.cut != null && this.plus.cut == null && this.minus.cut == null && (this.plus.attribute == null && this.minus.attribute == null || this.plus.attribute != null && this.plus.attribute.equals(this.minus.attribute))) {
         this.attribute = this.plus.attribute == null ? this.minus.attribute : this.plus.attribute;
         this.cut = null;
         this.plus = null;
         this.minus = null;
      }

   }

   public BSPTree<S> merge(BSPTree<S> tree, BSPTree.LeafMerger<S> leafMerger) {
      return this.merge(tree, leafMerger, (BSPTree)null, false);
   }

   private BSPTree<S> merge(BSPTree<S> tree, BSPTree.LeafMerger<S> leafMerger, BSPTree<S> parentTree, boolean isPlusChild) {
      if (this.cut == null) {
         return leafMerger.merge(this, tree, parentTree, isPlusChild, true);
      } else if (tree.cut == null) {
         return leafMerger.merge(tree, this, parentTree, isPlusChild, false);
      } else {
         BSPTree<S> merged = tree.split(this.cut);
         if (parentTree != null) {
            merged.parent = parentTree;
            if (isPlusChild) {
               parentTree.plus = merged;
            } else {
               parentTree.minus = merged;
            }
         }

         this.plus.merge(merged.plus, leafMerger, merged, true);
         this.minus.merge(merged.minus, leafMerger, merged, false);
         merged.condense();
         if (merged.cut != null) {
            merged.cut = merged.fitToCell(merged.cut.getHyperplane().wholeHyperplane());
         }

         return merged;
      }
   }

   public BSPTree<S> split(SubHyperplane<S> sub) {
      if (this.cut == null) {
         return new BSPTree(sub, this.copySelf(), new BSPTree(this.attribute), (Object)null);
      } else {
         Hyperplane<S> cHyperplane = this.cut.getHyperplane();
         Hyperplane<S> sHyperplane = sub.getHyperplane();
         BSPTree split;
         switch(sub.side(cHyperplane)) {
         case PLUS:
            split = this.plus.split(sub);
            if (this.cut.side(sHyperplane) == Side.PLUS) {
               split.plus = new BSPTree(this.cut.copySelf(), split.plus, this.minus.copySelf(), this.attribute);
               split.plus.condense();
               split.plus.parent = split;
            } else {
               split.minus = new BSPTree(this.cut.copySelf(), split.minus, this.minus.copySelf(), this.attribute);
               split.minus.condense();
               split.minus.parent = split;
            }

            return split;
         case MINUS:
            split = this.minus.split(sub);
            if (this.cut.side(sHyperplane) == Side.PLUS) {
               split.plus = new BSPTree(this.cut.copySelf(), this.plus.copySelf(), split.plus, this.attribute);
               split.plus.condense();
               split.plus.parent = split;
            } else {
               split.minus = new BSPTree(this.cut.copySelf(), this.plus.copySelf(), split.minus, this.attribute);
               split.minus.condense();
               split.minus.parent = split;
            }

            return split;
         case BOTH:
            SubHyperplane.SplitSubHyperplane<S> cutParts = this.cut.split(sHyperplane);
            SubHyperplane.SplitSubHyperplane<S> subParts = sub.split(cHyperplane);
            BSPTree<S> split = new BSPTree(sub, this.plus.split(subParts.getPlus()), this.minus.split(subParts.getMinus()), (Object)null);
            split.plus.cut = cutParts.getPlus();
            split.minus.cut = cutParts.getMinus();
            BSPTree<S> tmp = split.plus.minus;
            split.plus.minus = split.minus.plus;
            split.plus.minus.parent = split.plus;
            split.minus.plus = tmp;
            split.minus.plus.parent = split.minus;
            split.plus.condense();
            split.minus.condense();
            return split;
         default:
            return cHyperplane.sameOrientationAs(sHyperplane) ? new BSPTree(sub, this.plus.copySelf(), this.minus.copySelf(), this.attribute) : new BSPTree(sub, this.minus.copySelf(), this.plus.copySelf(), this.attribute);
         }
      }
   }

   public void insertInTree(BSPTree<S> parentTree, boolean isPlusChild) {
      this.parent = parentTree;
      if (parentTree != null) {
         if (isPlusChild) {
            parentTree.plus = this;
         } else {
            parentTree.minus = this;
         }
      }

      if (this.cut != null) {
         for(BSPTree tree = this; tree.parent != null; tree = tree.parent) {
            Hyperplane<S> hyperplane = tree.parent.cut.getHyperplane();
            if (tree == tree.parent.plus) {
               this.cut = this.cut.split(hyperplane).getPlus();
               this.plus.chopOffMinus(hyperplane);
               this.minus.chopOffMinus(hyperplane);
            } else {
               this.cut = this.cut.split(hyperplane).getMinus();
               this.plus.chopOffPlus(hyperplane);
               this.minus.chopOffPlus(hyperplane);
            }
         }

         this.condense();
      }

   }

   private void chopOffMinus(Hyperplane<S> hyperplane) {
      if (this.cut != null) {
         this.cut = this.cut.split(hyperplane).getPlus();
         this.plus.chopOffMinus(hyperplane);
         this.minus.chopOffMinus(hyperplane);
      }

   }

   private void chopOffPlus(Hyperplane<S> hyperplane) {
      if (this.cut != null) {
         this.cut = this.cut.split(hyperplane).getMinus();
         this.plus.chopOffPlus(hyperplane);
         this.minus.chopOffPlus(hyperplane);
      }

   }

   public interface LeafMerger<S extends Space> {
      BSPTree<S> merge(BSPTree<S> var1, BSPTree<S> var2, BSPTree<S> var3, boolean var4, boolean var5);
   }
}
