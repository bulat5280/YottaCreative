package org.apache.commons.math3.distribution;

import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.random.Well19937c;

public class ChiSquaredDistribution extends AbstractRealDistribution {
   public static final double DEFAULT_INVERSE_ABSOLUTE_ACCURACY = 1.0E-9D;
   private static final long serialVersionUID = -8352658048349159782L;
   private final GammaDistribution gamma;
   private final double solverAbsoluteAccuracy;

   public ChiSquaredDistribution(double degreesOfFreedom) {
      this(degreesOfFreedom, 1.0E-9D);
   }

   public ChiSquaredDistribution(double degreesOfFreedom, double inverseCumAccuracy) {
      this(new Well19937c(), degreesOfFreedom, inverseCumAccuracy);
   }

   public ChiSquaredDistribution(RandomGenerator rng, double degreesOfFreedom, double inverseCumAccuracy) {
      super(rng);
      this.gamma = new GammaDistribution(degreesOfFreedom / 2.0D, 2.0D);
      this.solverAbsoluteAccuracy = inverseCumAccuracy;
   }

   public double getDegreesOfFreedom() {
      return this.gamma.getShape() * 2.0D;
   }

   public double density(double x) {
      return this.gamma.density(x);
   }

   public double cumulativeProbability(double x) {
      return this.gamma.cumulativeProbability(x);
   }

   protected double getSolverAbsoluteAccuracy() {
      return this.solverAbsoluteAccuracy;
   }

   public double getNumericalMean() {
      return this.getDegreesOfFreedom();
   }

   public double getNumericalVariance() {
      return 2.0D * this.getDegreesOfFreedom();
   }

   public double getSupportLowerBound() {
      return 0.0D;
   }

   public double getSupportUpperBound() {
      return Double.POSITIVE_INFINITY;
   }

   public boolean isSupportLowerBoundInclusive() {
      return true;
   }

   public boolean isSupportUpperBoundInclusive() {
      return false;
   }

   public boolean isSupportConnected() {
      return true;
   }
}
