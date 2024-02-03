package org.apache.commons.math3.geometry.partitioning;

import org.apache.commons.math3.geometry.Space;

public abstract class AbstractSubHyperplane<S extends Space, T extends Space> implements SubHyperplane<S> {
   private final Hyperplane<S> hyperplane;
   private final Region<T> remainingRegion;

   protected AbstractSubHyperplane(Hyperplane<S> hyperplane, Region<T> remainingRegion) {
      this.hyperplane = hyperplane;
      this.remainingRegion = remainingRegion;
   }

   protected abstract AbstractSubHyperplane<S, T> buildNew(Hyperplane<S> var1, Region<T> var2);

   public AbstractSubHyperplane<S, T> copySelf() {
      return this.buildNew(this.hyperplane, this.remainingRegion);
   }

   public Hyperplane<S> getHyperplane() {
      return this.hyperplane;
   }

   public Region<T> getRemainingRegion() {
      return this.remainingRegion;
   }

   public double getSize() {
      return this.remainingRegion.getSize();
   }

   public AbstractSubHyperplane<S, T> reunite(SubHyperplane<S> other) {
      AbstractSubHyperplane<S, T> o = (AbstractSubHyperplane)other;
      return this.buildNew(this.hyperplane, (new RegionFactory()).union(this.remainingRegion, o.remainingRegion));
   }

   public AbstractSubHyperplane<S, T> applyTransform(Transform<S, T> transform) {
      Hyperplane<S> tHyperplane = transform.apply(this.hyperplane);
      BSPTree<T> tTree = this.recurseTransform(this.remainingRegion.getTree(false), tHyperplane, transform);
      return this.buildNew(tHyperplane, this.remainingRegion.buildNew(tTree));
   }

   private BSPTree<T> recurseTransform(BSPTree<T> node, Hyperplane<S> transformed, Transform<S, T> transform) {
      if (node.getCut() == null) {
         return new BSPTree(node.getAttribute());
      } else {
         BoundaryAttribute<T> attribute = (BoundaryAttribute)node.getAttribute();
         if (attribute != null) {
            SubHyperplane<T> tPO = attribute.getPlusOutside() == null ? null : transform.apply(attribute.getPlusOutside(), this.hyperplane, transformed);
            SubHyperplane<T> tPI = attribute.getPlusInside() == null ? null : transform.apply(attribute.getPlusInside(), this.hyperplane, transformed);
            attribute = new BoundaryAttribute(tPO, tPI);
         }

         return new BSPTree(transform.apply(node.getCut(), this.hyperplane, transformed), this.recurseTransform(node.getPlus(), transformed, transform), this.recurseTransform(node.getMinus(), transformed, transform), attribute);
      }
   }

   public abstract Side side(Hyperplane<S> var1);

   public abstract SubHyperplane.SplitSubHyperplane<S> split(Hyperplane<S> var1);

   public boolean isEmpty() {
      return this.remainingRegion.isEmpty();
   }
}
