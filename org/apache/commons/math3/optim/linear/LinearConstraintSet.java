package org.apache.commons.math3.optim.linear;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.math3.optim.OptimizationData;

public class LinearConstraintSet implements OptimizationData {
   private final Set<LinearConstraint> linearConstraints = new HashSet();

   public LinearConstraintSet(LinearConstraint... constraints) {
      LinearConstraint[] arr$ = constraints;
      int len$ = constraints.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         LinearConstraint c = arr$[i$];
         this.linearConstraints.add(c);
      }

   }

   public LinearConstraintSet(Collection<LinearConstraint> constraints) {
      this.linearConstraints.addAll(constraints);
   }

   public Collection<LinearConstraint> getConstraints() {
      return Collections.unmodifiableSet(this.linearConstraints);
   }
}
