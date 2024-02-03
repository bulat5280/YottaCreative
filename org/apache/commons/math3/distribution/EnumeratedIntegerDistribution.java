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

public class EnumeratedIntegerDistribution extends AbstractIntegerDistribution {
   private static final long serialVersionUID = 20130308L;
   protected final EnumeratedDistribution<Integer> innerDistribution;

   public EnumeratedIntegerDistribution(int[] singletons, double[] probabilities) throws DimensionMismatchException, NotPositiveException, MathArithmeticException, NotFiniteNumberException, NotANumberException {
      this(new Well19937c(), singletons, probabilities);
   }

   public EnumeratedIntegerDistribution(RandomGenerator rng, int[] singletons, double[] probabilities) throws DimensionMismatchException, NotPositiveException, MathArithmeticException, NotFiniteNumberException, NotANumberException {
      super(rng);
      if (singletons.length != probabilities.length) {
         throw new DimensionMismatchException(probabilities.length, singletons.length);
      } else {
         List<Pair<Integer, Double>> samples = new ArrayList(singletons.length);

         for(int i = 0; i < singletons.length; ++i) {
            samples.add(new Pair(singletons[i], probabilities[i]));
         }

         this.innerDistribution = new EnumeratedDistribution(rng, samples);
      }
   }

   public double probability(int x) {
      return this.innerDistribution.probability(x);
   }

   public double cumulativeProbability(int x) {
      double probability = 0.0D;
      Iterator i$ = this.innerDistribution.getPmf().iterator();

      while(i$.hasNext()) {
         Pair<Integer, Double> sample = (Pair)i$.next();
         if ((Integer)sample.getKey() <= x) {
            probability += (Double)sample.getValue();
         }
      }

      return probability;
   }

   public double getNumericalMean() {
      double mean = 0.0D;

      Pair sample;
      for(Iterator i$ = this.innerDistribution.getPmf().iterator(); i$.hasNext(); mean += (Double)sample.getValue() * (double)(Integer)sample.getKey()) {
         sample = (Pair)i$.next();
      }

      return mean;
   }

   public double getNumericalVariance() {
      double mean = 0.0D;
      double meanOfSquares = 0.0D;

      Pair sample;
      for(Iterator i$ = this.innerDistribution.getPmf().iterator(); i$.hasNext(); meanOfSquares += (Double)sample.getValue() * (double)(Integer)sample.getKey() * (double)(Integer)sample.getKey()) {
         sample = (Pair)i$.next();
         mean += (Double)sample.getValue() * (double)(Integer)sample.getKey();
      }

      return meanOfSquares - mean * mean;
   }

   public int getSupportLowerBound() {
      int min = Integer.MAX_VALUE;
      Iterator i$ = this.innerDistribution.getPmf().iterator();

      while(i$.hasNext()) {
         Pair<Integer, Double> sample = (Pair)i$.next();
         if ((Integer)sample.getKey() < min && (Double)sample.getValue() > 0.0D) {
            min = (Integer)sample.getKey();
         }
      }

      return min;
   }

   public int getSupportUpperBound() {
      int max = Integer.MIN_VALUE;
      Iterator i$ = this.innerDistribution.getPmf().iterator();

      while(i$.hasNext()) {
         Pair<Integer, Double> sample = (Pair)i$.next();
         if ((Integer)sample.getKey() > max && (Double)sample.getValue() > 0.0D) {
            max = (Integer)sample.getKey();
         }
      }

      return max;
   }

   public boolean isSupportConnected() {
      return true;
   }

   public int sample() {
      return (Integer)this.innerDistribution.sample();
   }
}
