package org.apache.commons.math3.random;

import org.apache.commons.math3.util.FastMath;

public class UniformRandomGenerator implements NormalizedRandomGenerator {
   private static final double SQRT3 = FastMath.sqrt(3.0D);
   private final RandomGenerator generator;

   public UniformRandomGenerator(RandomGenerator generator) {
      this.generator = generator;
   }

   public double nextNormalizedDouble() {
      return SQRT3 * (2.0D * this.generator.nextDouble() - 1.0D);
   }
}
