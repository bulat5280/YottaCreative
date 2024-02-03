package org.apache.commons.math3.geometry.partitioning;

import org.apache.commons.math3.geometry.Space;

public class RegionFactory<S extends Space> {
   private final RegionFactory<S>.NodesCleaner nodeCleaner = new RegionFactory.NodesCleaner();

   public Region<S> buildConvex(Hyperplane<S>... hyperplanes) {
      if (hyperplanes != null && hyperplanes.length != 0) {
         Region<S> region = hyperplanes[0].wholeSpace();
         BSPTree<S> node = region.getTree(false);
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

         return region;
      } else {
         return null;
      }
   }

   public Region<S> union(Region<S> region1, Region<S> region2) {
      BSPTree<S> tree = region1.getTree(false).merge(region2.getTree(false), new RegionFactory.UnionMerger());
      tree.visit(this.nodeCleaner);
      return region1.buildNew(tree);
   }

   public Region<S> intersection(Region<S> region1, Region<S> region2) {
      BSPTree<S> tree = region1.getTree(false).merge(region2.getTree(false), new RegionFactory.IntersectionMerger());
      tree.visit(this.nodeCleaner);
      return region1.buildNew(tree);
   }

   public Region<S> xor(Region<S> region1, Region<S> region2) {
      BSPTree<S> tree = region1.getTree(false).merge(region2.getTree(false), new RegionFactory.XorMerger());
      tree.visit(this.nodeCleaner);
      return region1.buildNew(tree);
   }

   public Region<S> difference(Region<S> region1, Region<S> region2) {
      BSPTree<S> tree = region1.getTree(false).merge(region2.getTree(false), new RegionFactory.DifferenceMerger());
      tree.visit(this.nodeCleaner);
      return region1.buildNew(tree);
   }

   public Region<S> getComplement(Region<S> region) {
      return region.buildNew(this.recurseComplement(region.getTree(false)));
   }

   private BSPTree<S> recurseComplement(BSPTree<S> node) {
      if (node.getCut() == null) {
         return new BSPTree((Boolean)node.getAttribute() ? Boolean.FALSE : Boolean.TRUE);
      } else {
         BoundaryAttribute<S> attribute = (BoundaryAttribute)node.getAttribute();
         if (attribute != null) {
            SubHyperplane<S> plusOutside = attribute.getPlusInside() == null ? null : attribute.getPlusInside().copySelf();
            SubHyperplane<S> plusInside = attribute.getPlusOutside() == null ? null : attribute.getPlusOutside().copySelf();
            attribute = new BoundaryAttribute(plusOutside, plusInside);
         }

         return new BSPTree(node.getCut().copySelf(), this.recurseComplement(node.getPlus()), this.recurseComplement(node.getMinus()), attribute);
      }
   }

   private class NodesCleaner implements BSPTreeVisitor<S> {
      private NodesCleaner() {
      }

      public BSPTreeVisitor.Order visitOrder(BSPTree<S> node) {
         return BSPTreeVisitor.Order.PLUS_SUB_MINUS;
      }

      public void visitInternalNode(BSPTree<S> node) {
         node.setAttribute((Object)null);
      }

      public void visitLeafNode(BSPTree<S> node) {
      }

      // $FF: synthetic method
      NodesCleaner(Object x1) {
         this();
      }
   }

   private class DifferenceMerger implements BSPTree.LeafMerger<S> {
      private DifferenceMerger() {
      }

      public BSPTree<S> merge(BSPTree<S> leaf, BSPTree<S> tree, BSPTree<S> parentTree, boolean isPlusChild, boolean leafFromInstance) {
         BSPTree instanceTree;
         if ((Boolean)leaf.getAttribute()) {
            instanceTree = RegionFactory.this.recurseComplement(leafFromInstance ? tree : leaf);
            instanceTree.insertInTree(parentTree, isPlusChild);
            return instanceTree;
         } else {
            instanceTree = leafFromInstance ? leaf : tree;
            instanceTree.insertInTree(parentTree, isPlusChild);
            return instanceTree;
         }
      }

      // $FF: synthetic method
      DifferenceMerger(Object x1) {
         this();
      }
   }

   private class XorMerger implements BSPTree.LeafMerger<S> {
      private XorMerger() {
      }

      public BSPTree<S> merge(BSPTree<S> leaf, BSPTree<S> tree, BSPTree<S> parentTree, boolean isPlusChild, boolean leafFromInstance) {
         BSPTree<S> t = tree;
         if ((Boolean)leaf.getAttribute()) {
            t = RegionFactory.this.recurseComplement(tree);
         }

         t.insertInTree(parentTree, isPlusChild);
         return t;
      }

      // $FF: synthetic method
      XorMerger(Object x1) {
         this();
      }
   }

   private class IntersectionMerger implements BSPTree.LeafMerger<S> {
      private IntersectionMerger() {
      }

      public BSPTree<S> merge(BSPTree<S> leaf, BSPTree<S> tree, BSPTree<S> parentTree, boolean isPlusChild, boolean leafFromInstance) {
         if ((Boolean)leaf.getAttribute()) {
            tree.insertInTree(parentTree, isPlusChild);
            return tree;
         } else {
            leaf.insertInTree(parentTree, isPlusChild);
            return leaf;
         }
      }

      // $FF: synthetic method
      IntersectionMerger(Object x1) {
         this();
      }
   }

   private class UnionMerger implements BSPTree.LeafMerger<S> {
      private UnionMerger() {
      }

      public BSPTree<S> merge(BSPTree<S> leaf, BSPTree<S> tree, BSPTree<S> parentTree, boolean isPlusChild, boolean leafFromInstance) {
         if ((Boolean)leaf.getAttribute()) {
            leaf.insertInTree(parentTree, isPlusChild);
            return leaf;
         } else {
            tree.insertInTree(parentTree, isPlusChild);
            return tree;
         }
      }

      // $FF: synthetic method
      UnionMerger(Object x1) {
         this();
      }
   }
}
