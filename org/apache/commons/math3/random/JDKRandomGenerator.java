package org.apache.commons.math3.random;

import java.util.Random;

public class JDKRandomGenerator extends Random implements RandomGenerator {
   private static final long serialVersionUID = -7745277476784028798L;

   public void setSeed(int seed) {
      this.setSeed((long)seed);
   }

   public void setSeed(int[] seed) {
      long prime = 4294967291L;
      long combined = 0L;
      int[] arr$ = seed;
      int len$ = seed.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         int s = arr$[i$];
         combined = combined * 4294967291L + (long)s;
      }

      this.setSeed(combined);
   }
}
