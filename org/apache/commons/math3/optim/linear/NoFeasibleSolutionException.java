package org.apache.commons.math3.optim.linear;

import org.apache.commons.math3.exception.MathIllegalStateException;
import org.apache.commons.math3.exception.util.LocalizedFormats;

public class NoFeasibleSolutionException extends MathIllegalStateException {
   private static final long serialVersionUID = -3044253632189082760L;

   public NoFeasibleSolutionException() {
      super(LocalizedFormats.NO_FEASIBLE_SOLUTION);
   }
}
