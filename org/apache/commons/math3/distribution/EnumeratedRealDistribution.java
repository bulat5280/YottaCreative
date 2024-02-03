package org.apache.commons.math3.distribution;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.MathArithmeticException;
import org.apache.commons.math3.exception.NotANumberException;
import org.apache.commons.math3.exception.NotFiniteNumberException;
import org.apache.commons.math3.exception.NotPositiveException;
import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.random.Well19937c;
import org.apache.commons.math3.util.Pair;

public class EnumeratedRealDistribution extends AbstractRealDistribution {
   private static final long serialVersionUID = 20130308L;
   protected final EnumeratedDistribution<Double> innerDistribution;

   public EnumeratedRealDistribution(double[] singletons, double[] probabilities) throws DimensionMismatchException, NotPositiveException, MathArithmeticException, NotFiniteNumberException, NotANumberException {
      this(new Well19937c(), singletons, probabilities);
   }

   public EnumeratedRealDistribution(RandomGenerator rng, double[] singletons, double[] probabilities) throws DimensionMismatchException, NotPositiveException, MathArithmeticException, NotFiniteNumberException, NotANumberException {
      super(rng);
      if (singletons.length != probabilities.length) {
         throw new DimensionMismatchException(probabilities.length, singletons.length);
      } else {
         List<Pair<Double, Double>> samples = new ArrayList(singletons.length);

         for(int i = 0; i < singletons.length; ++i) {
            samples.add(new Pair(singletons[i], probabilities[i]));
         }

         this.innerDistribution = new EnumeratedDistribution(rng, samples);
      }
   }

   public double probability(double x) {
      return this.innerDistribution.probability(x);
   }

   public double density(double x) {
      return this.probability(x);
   }

   public double cumulativeProbability(double x) {
      double probability = 0.0D;
      Iterator i$ = this.innerDistribution.getPmf().iterator();

      while(i$.hasNext()) {
         Pair<Double, Double> sample = (Pair)i$.next();
         if ((Double)sample.getKey() <= x) {
            probability += (Double)sample.getValue();
         }
      }

      return probability;
   }

   public double getNumericalMean() {
      double mean = 0.0D;

      Pair sample;
      for(Iterator i$ = this.innerDistribution.getPmf().iterator(); i$.hasNext(); mean += (Double)sample.getValue() * (Double)sample.getKey()) {
         sample = (Pair)i$.next();
      }

      return mean;
   }

   public double getNumericalVariance() {
      double mean = 0.0D;
      double meanOfSquares = 0.0D;

      Pair sample;
      for(Iterator i$ = this.innerDistribution.getPmf().iterator(); i$.hasNext(); meanOfSquares += (Double)sample.getValue() * (Double)sample.getKey() * (Double)sample.getKey()) {
         sample = (Pair)i$.next();
         mean += (Double)sample.getValue() * (Double)sample.getKey();
      }

      return meanOfSquares - mean * mean;
   }

   public double getSupportLowerBound() {
      double min = Double.POSITIVE_INFINITY;
      Iterator i$ = this.innerDistribution.getPmf().iterator();

      while(i$.hasNext()) {
         Pair<Double, Double> sample = (Pair)i$.next();
         if ((Double)sample.getKey() < min && (Double)sample.getValue() > 0.0D) {
            min = (Double)sample.getKey();
         }
      }

      return min;
   }

   public double getSupportUpperBound() {
      double max = Double.NEGATIVE_INFINITY;
      Iterator i$ = this.innerDistribution.getPmf().iterator();

      while(i$.hasNext()) {
         Pair<Double, Double> sample = (Pair)i$.next();
         if ((Double)sample.getKey() > max && (Double)sample.getValue() > 0.0D) {
            max = (Double)sample.getKey();
         }
      }

      return max;
   }

   public boolean isSupportLowerBoundInclusive() {
      return true;
   }

   public boolean isSupportUpperBoundInclusive() {
      return true;
   }

   public boolean isSupportConnected() {
      return true;
   }

   public double sample() {
      return (Double)this.innerDistribution.sample();
   }
}
