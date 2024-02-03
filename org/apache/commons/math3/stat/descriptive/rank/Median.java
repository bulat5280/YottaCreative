package org.apache.commons.math3.stat.descriptive.rank;

import java.io.Serializable;
import org.apache.commons.math3.exception.NullArgumentException;

public class Median extends Percentile implements Serializable {
   private static final long serialVersionUID = -3961477041290915687L;

   public Median() {
      super(50.0D);
   }

   public Median(Median original) throws NullArgumentException {
      super(original);
   }
}
